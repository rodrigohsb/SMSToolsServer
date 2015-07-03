package com.bemobi.smstools.controller;

import com.bemobi.smstools.domain.Device;
import com.bemobi.smstools.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@RestController
@RequestMapping("/device")
public class DeviceControlller {

    @Autowired
    private DeviceRepository repository;

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public void register(@PathVariable String regId) {

        Device device = new Device();
        device.setId(regId);
        repository.create(device);
    }

}
