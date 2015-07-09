package com.bemobi.smstools.utils;

import com.bemobi.smstools.constants.Constants;
import com.bemobi.smstools.domain.Message;
import com.bemobi.smstools.domain.Result;
import com.bemobi.smstools.exception.InvalidRequestException;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rodrigo.bacellar on 09/07/2015.
 */
@Configuration
public class Sender
{

    protected static final Logger logger = Logger.getLogger(Sender.class.getName());

    public Result send(Message message, String registrationId) throws IOException
    {

        StringBuilder body = newBody(Constants.PARAM_REGISTRATION_ID, registrationId);

        Boolean delayWhileIdle = message.isDelayWhileIdle();
        if (delayWhileIdle != null)
        {
            addParameter(body, Constants.PARAM_DELAY_WHILE_IDLE, delayWhileIdle ? "1" : "0");
        }

        Boolean dryRun = message.isDryRun();
        if (dryRun != null)
        {
            addParameter(body, Constants.PARAM_DRY_RUN, dryRun ? "1" : "0");
        }

        String collapseKey = message.getCollapseKey();
        if (collapseKey != null)
        {
            addParameter(body, Constants.PARAM_COLLAPSE_KEY, collapseKey);
        }

        String restrictedPackageName = message.getRestrictedPackageName();
        if (restrictedPackageName != null)
        {
            addParameter(body, Constants.PARAM_RESTRICTED_PACKAGE_NAME, restrictedPackageName);
        }

        Integer timeToLive = message.getTimeToLive();
        if (timeToLive != null)
        {
            addParameter(body, Constants.PARAM_TIME_TO_LIVE, Integer.toString(timeToLive));
        }

        for (Map.Entry<String, String> entry : message.getData().entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null)
            {
                logger.warning("Ignoring payload entry thas has null: " + entry);
            }
            else
            {
                key = Constants.PARAM_PAYLOAD_PREFIX + key;
                addParameter(body, key, URLEncoder.encode(value, Constants.UTF8));
            }
        }

        String requestBody = body.toString();
        logger.finest("Request body: " + requestBody);
        HttpURLConnection conn;
        int status;

        try
        {
            conn = post(Constants.GCM_SEND_ENDPOINT, requestBody);
            status = conn.getResponseCode();
        }
        catch (IOException e)
        {
            logger.log(Level.FINE, "IOException posting to GCM", e);
            return null;
        }

        if (status / 100 == 5)
        {
            logger.fine("GCM service is unavailable (status " + status + ")");
            return null;
        }

        String responseBody;

        if (status != 200)
        {
            try
            {
                responseBody = getAndClose(conn.getErrorStream());
                logger.finest("Plain post error response: " + responseBody);
            }
            catch (IOException e)
            {
                // ignore the exception since it will thrown an InvalidRequestException
                // anyways
                responseBody = "N/A";
                logger.log(Level.FINE, "Exception reading response: ", e);
            }
            throw new InvalidRequestException(status, responseBody);
        }
        else
        {
            try
            {
                responseBody = getAndClose(conn.getInputStream());
            }
            catch (IOException e)
            {
                logger.log(Level.WARNING, "Exception reading response: ", e);
                // return null so it can retry
                return null;
            }
        }

        String[] lines = responseBody.split("\n");

        if (lines.length == 0 || lines[0].equals(""))
        {
            throw new IOException("Received empty response from GCM service.");
        }

        String firstLine = lines[0];
        String[] responseParts = split(firstLine);
        String token = responseParts[0];
        String value = responseParts[1];

        if (token.equals(Constants.TOKEN_MESSAGE_ID))
        {
            Result.Builder builder = new Result.Builder().messageId(value);
            // check for canonical registration id
            if (lines.length > 1)
            {
                String secondLine = lines[1];
                responseParts = split(secondLine);
                token = responseParts[0];
                value = responseParts[1];
                if (token.equals(Constants.TOKEN_CANONICAL_REG_ID))
                {
                    builder.canonicalRegistrationId(value);
                }
                else
                {
                    logger.warning("Invalid response from GCM: " + responseBody);
                }
            }

            Result result = builder.build();
            if (logger.isLoggable(Level.FINE))
            {
                logger.fine("Message created succesfully (" + result + ")");
            }

            return result;
        }
        else if (token.equals(Constants.TOKEN_ERROR))
        {
            return new Result.Builder().errorCode(value).build();
        }
        else
        {
            throw new IOException("Invalid response from GCM: " + responseBody);
        }
    }

    /**
     * Creates a {@link StringBuilder} to be used as the body of an HTTP POST.
     *
     * @param name initial parameter for the POST.
     * @param value initial value for that parameter.
     * @return StringBuilder to be used an HTTP POST body.
     */
    protected static StringBuilder newBody(String name, String value)
    {
        return new StringBuilder(nonNull(name)).append('=').append(nonNull(value));
    }

    protected static void addParameter(StringBuilder body, String name, String value)
    {
        nonNull(body).append('&').append(nonNull(name)).append('=').append(nonNull(value));
    }

    private String[] split(String line) throws IOException
    {
        String[] split = line.split("=", 2);
        if (split.length != 2)
        {
            throw new IOException("Received invalid response line from GCM: " + line);
        }
        return split;
    }

    /**
     * Make an HTTP post to a given URL.
     *
     * @return HTTP response.
     */
    protected HttpURLConnection post(String url, String body) throws IOException
    {
        return post(url, "application/x-www-form-urlencoded;charset=UTF-8", body);
    }

    /**
     * Makes an HTTP POST request to a given endpoint.
     *
     * <p>
     * <strong>Note: </strong> the returned connected should not be disconnected,
     * otherwise it would kill persistent connections made using Keep-Alive.
     *
     * @param url endpoint to post the request.
     * @param contentType type of request.
     * @param body body of the request.
     *
     * @return the underlying connection.
     *
     * @throws IOException propagated from underlying methods.
     */
    protected HttpURLConnection post(String url, String contentType, String body) throws IOException
    {
        if (url == null || body == null)
        {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        if (!url.startsWith("https://"))
        {
            logger.warning("URL does not use https: " + url);
        }

        logger.fine("Sending POST to " + url);
        logger.finest("POST body: " + body);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = getConnection(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + Constants.API_KEY);
        OutputStream out = conn.getOutputStream();
        try
        {
            out.write(bytes);
        }
        finally
        {
            close(out);
        }
        return conn;
    }

    /**
     * Gets an {@link HttpURLConnection} given an URL.
     */
    protected HttpURLConnection getConnection(String url) throws IOException
    {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    private static String getAndClose(InputStream stream) throws IOException {
        try
        {
            return getString(stream);
        }
        finally
        {
            if (stream != null)
            {
                close(stream);
            }
        }
    }
    protected static String getString(InputStream stream) throws IOException
    {
        if (stream == null)
        {
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder content = new StringBuilder();
        String newLine;

        do
        {
            newLine = reader.readLine();
            if (newLine != null)
            {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);

        if (content.length() > 0)
        {
            // strip last newline
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }


    static <T> T nonNull(T argument)
    {
        if (argument == null)
        {
            throw new IllegalArgumentException("argument cannot be null");
        }
        return argument;
    }

    private static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                // ignore error
                logger.log(Level.FINEST, "IOException closing stream", e);
            }
        }
    }

}
