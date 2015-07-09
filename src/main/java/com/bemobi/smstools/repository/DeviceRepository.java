package com.bemobi.smstools.repository;

import com.bemobi.smstools.domain.Device;
import org.ektorp.CouchDbConnector;
import org.ektorp.UpdateConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@Repository
public class DeviceRepository
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRepository.class);

    @Autowired
    private CouchDbConnector couchDbConnector;


    public void create(String regId)
    {

        Device device = new Device();
        device.setId(regId);
        try
        {
            couchDbConnector.create(device);
            LOGGER.info("GCMDevice [{}] armazenado com sucesso.", device.getId());
        }
        catch (UpdateConflictException e)
        {
            LOGGER.warn("GCMDevice [{}] já armazenado anteriormente.", device.getId());
        }

    }

    public void update(Device device)
    {
        couchDbConnector.update(device);
        LOGGER.info("GCMDevice [{}] atualizado com sucesso.", device.getId());
    }

    public void delete(String regId, String rev) throws UpdateConflictException
    {
        couchDbConnector.delete(regId, rev);
        LOGGER.info("GCMDevice [{}] deletado com sucesso.", regId);
    }

    public DeviceRepository get(String regId)
    {
        return couchDbConnector.get(DeviceRepository.class, regId);
    }

    public List<String> getAll()
    {
        return couchDbConnector.getAllDocIds();
    }

}
