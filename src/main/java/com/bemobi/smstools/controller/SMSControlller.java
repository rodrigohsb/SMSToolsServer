package com.bemobi.smstools.controller;

import com.bemobi.smstools.constants.Constants;
import com.bemobi.smstools.domain.Message;
import com.bemobi.smstools.domain.SMS;
import com.bemobi.smstools.service.DeviceService;
import com.bemobi.smstools.utils.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@RestController
@RequestMapping("/sms")
public class SMSControlller
{

    private static final Logger logger = LoggerFactory.getLogger(SMSControlller.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Sender sender;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/send/{la}", method = RequestMethod.POST)
    public void sendSMS(@PathVariable String la, @RequestParam("text") String text)
    {

        String destination = deviceService.getAll();

        Message message = new Message.Builder()
                .collapseKey(Constants.COLLAPSE_KEY).delayWhileIdle(Constants.DELAY_WHILE_IDLE)
                .dryRun(Constants.DRY_RUN).restrictedPackageName(Constants.RESTRICTED_PACKAGE_NAME)
                .timeToLive(Constants.TTL).addData(Constants.LA, la).addData(Constants.TEXT,text).build();

        try
        {
            sender.send(message, destination);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/sent/{la}", method = RequestMethod.POST)
    public void sentSMS(@PathVariable String la, @RequestParam("text") String text, @RequestParam("sendDate") String sendDate, HttpServletResponse response)
    {
        try
        {
            logger.info("[sentSMS] SMS enviado. LA[{}], TEXT[{}], SEND_DATE[{}].", la , text, format.parse(sendDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        response.setStatus(response.SC_OK);
    }

    @RequestMapping(value = "/received/{la}", method = RequestMethod.POST)
    public void receivedSMS(@PathVariable String la, @RequestParam("text") String text, @RequestParam("receiveDate") String receiveDate, HttpServletResponse response)
    {
        try
        {
            logger.info("[receivedSMS] SMS recebido. LA[{}], TEXT[{}], RECEIVED_DATE[{}].", la , text, format.parse(receiveDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        response.setStatus(response.SC_OK);
    }

}
