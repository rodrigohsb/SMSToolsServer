package com.bemobi.smstools.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@Component
public class HttpRequest{

    @Value("${cgm.endpoint.url}")
    private String gcmUrl;

    private Map<String, String> mHeaders = new HashMap<>();
    private int responseCode;
    private String responseBody;

    /**
     * Add a request header
     *
     * @param name  the header's name
     * @param value the header's value
     */
    public void setHeader(String name, String value)
    {
        this.mHeaders.put(name, value);
    }

    /**
     * @return this request's response code
     */
    public int getResponseCode()
    {
        return responseCode;
    }

    /**
     * @return this request's response body
     */
    public String getResponseBody()
    {
        return responseBody;
    }

    /**
     * Post the request
     *
     * @param requestBody the body of the request
     * @throws java.io.IOException
     */
    public void doPost(String requestBody) throws IOException
    {

        HttpURLConnection conn = (HttpURLConnection) new URL(gcmUrl).openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(requestBody.getBytes().length);
        conn.setRequestMethod("POST");

        Set<String> keySets = mHeaders.keySet();

        for (String key : keySets)
        {
            String value = mHeaders.get(key);
            conn.setRequestProperty(key, value);
        }

        OutputStream out = null;

        try
        {
            out = conn.getOutputStream();
            out.write(requestBody.getBytes());
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    // Ignore.
                }
            }
        }

        responseCode = conn.getResponseCode();

        InputStream inputStream = null;

        try
        {
            if (responseCode == 200)
            {
                inputStream = conn.getInputStream();
            }
            else
            {
                inputStream = conn.getErrorStream();
            }
            responseBody = getString(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    // Ignore.
                }
            }
        }

        conn.disconnect();
    }

    /**
     * Convenience method to convert an InputStream to a String.
     * <p/>
     * If the stream ends in a newline character, it will be stripped.
     * <p/>
     * If the stream is {@literal null}, returns an empty string.
     */
    private String getString(InputStream stream) throws IOException
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
        }while (newLine != null);

        if (content.length() > 0)
        {
            // strip last newline
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }

}
