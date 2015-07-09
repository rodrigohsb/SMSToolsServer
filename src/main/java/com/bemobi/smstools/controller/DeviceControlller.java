package com.bemobi.smstools.controller;

import com.bemobi.smstools.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@RestController
@RequestMapping("/device")
public class DeviceControlller {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/register/{regId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@PathVariable String regId)
    {
        deviceService.create(regId);
    }
}
