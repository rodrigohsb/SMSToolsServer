package com.bemobi.smstools.service;

import com.bemobi.smstools.domain.Device;
import com.bemobi.smstools.repository.DeviceRepository;
import org.ektorp.UpdateConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@Service
public class DeviceService
{
    @Autowired
    private DeviceRepository deviceRepository;

    public void create(Device device)
    {
        deviceRepository.create(device);
    }

    public DeviceRepository get(String regId)
    {
        return deviceRepository.get(regId);
    }

    public String getAll()
    {
        return deviceRepository.getAll().get(0);
    }

    public void update(Device device)
    {
        deviceRepository.update(device);
    }

    public void delete(String regId, String rev) throws UpdateConflictException
    {
        deviceRepository.delete(regId, rev);
    }
}
