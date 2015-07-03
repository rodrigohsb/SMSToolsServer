package com.bemobi.smstools.controller;

import com.bemobi.smstools.service.DeviceService;
import com.bemobi.smstools.constants.Constants;
import com.bemobi.smstools.domain.Message;
import com.bemobi.smstools.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@RestController
@RequestMapping("/sms")
public class SMSControlller
{

    private static final Logger logger = LoggerFactory.getLogger(SMSControlller.class);

    @Value("${sms.tools.api.key}")
    private String apiKey;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/send/{la}", method = RequestMethod.POST)
    public void sendSMS(@PathVariable String la, @RequestParam("msg") String msg) throws IOException
    {
        Message message = new Message();

        message.setTimeToLive(null);
        //TODO ver o que é
        message.setData(null);
        message.setCollapseKey(null);
        message.setDelayWhileIdle(false);
        message.setDelayWhileIdle(false);
        message.setRestrictedPackageName(null);
        message.setDryRun(false);

        String destination = deviceService.getAll();
        try
        {
            sendHttpPlaintextDownstreamMessage(destination, message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/log/{la}", method = RequestMethod.GET)
    public void reveiveSMS(@PathVariable String la, @RequestParam("msg") String msg, @RequestParam("date") Date date)
    {
        logger.info("Mensagem recebida. LA[{}], MSG[{}], DATE[{}].", la, msg, date);
    }

    /**
     * Send a downstream message via HTTP plain text.
     *
     * @param destination the registration id of the recipient app.
     * @param message     the message to be sent
     * @throws java.io.IOException
     */
    public void sendHttpPlaintextDownstreamMessage(String destination, Message message) throws IOException
    {
        StringBuilder request = new StringBuilder();
        request.append(Constants.PARAM_TO).append('=').append(destination);
        addOptParameter(request, Constants.PARAM_DELAY_WHILE_IDLE, message.getDelayWhileIdle());
        addOptParameter(request, Constants.PARAM_DRY_RUN, message.getDryRun());
        addOptParameter(request, Constants.PARAM_COLLAPSE_KEY, message.getCollapseKey());
        addOptParameter(request, Constants.PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
        addOptParameter(request, Constants.PARAM_TIME_TO_LIVE, message.getTimeToLive());

        for (Map.Entry<String, String> entry : message.getData().entrySet())
        {
            if (entry.getKey() != null && entry.getValue() != null)
            {
                String prefixedKey = Constants.PARAM_PLAINTEXT_PAYLOAD_PREFIX + entry.getKey();
                addOptParameter(request, prefixedKey, URLEncoder.encode(entry.getValue(), Constants.UTF8));
            }
        }

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHeader(Constants.HEADER_CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        httpRequest.setHeader(Constants.HEADER_AUTHORIZATION, "key=" + apiKey);
        httpRequest.doPost(request.toString());

        if (httpRequest.getResponseCode() != 200)
        {
            throw new IOException("Invalid request."+ "\nStatus: " + httpRequest.getResponseCode()+ "\nResponse: " + httpRequest.getResponseBody());
        }

        String[] lines = httpRequest.getResponseBody().split("\n");
        if (lines.length == 0 || lines[0].equals(""))
        {
            throw new IOException("Received empty response from GCM service.");
        }

        String[] firstLineValues = lines[0].split("=");

        if (firstLineValues.length != 2)
        {
            throw new IOException("Invalid response from GCM: " + httpRequest.getResponseBody());
        }

        switch (firstLineValues[0])
        {
            case Constants.RESPONSE_PLAINTEXT_MESSAGE_ID:
                logger.info("Message sent.\nid: " + firstLineValues[1]);
                // check for canonical registration id
                if (lines.length > 1)
                {
                    // If the response includes a 2nd line we expect it to be the CANONICAL REG ID
                    String[] secondLineValues = lines[1].split("=");

                    if (secondLineValues.length == 2 && secondLineValues[0].equals(Constants.RESPONSE_PLAINTEXT_CANONICAL_REG_ID))
                    {
                        logger.info("Message sent: canonical registration id = " + secondLineValues[1]);
                    }
                    else
                    {
                        logger.info("Invalid response from GCM." + "\nResponse: " + httpRequest.getResponseBody());
                    }
                }
                break;
            case Constants.RESPONSE_PLAINTEXT_ERROR:
                logger.info("Message failed.\nError: " + firstLineValues[1]);
                break;
            default:
                logger.info("Invalid response from GCM."+ "\nResponse: " + httpRequest.getResponseBody());
                break;
        }
    }

    /**
     * Adds a new parameter to the HTTP POST body without doing any encoding.
     *
     * @param body  HTTP POST body.
     * @param name  parameter's name.
     * @param value parameter's value.
     */
    private static void addOptParameter(StringBuilder body, String name, Object value)
    {
        if (value != null)
        {
            String encodedValue = value.toString();
            if (value instanceof Boolean)
            {
                encodedValue = ((Boolean) value) ? "1" : "0";
            }
            body.append('&').append(name).append('=').append(encodedValue);
        }
    }

}
