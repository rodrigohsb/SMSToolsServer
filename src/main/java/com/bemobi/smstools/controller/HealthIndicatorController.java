package com.bemobi.smstools.controller;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
public class HealthIndicatorController extends AbstractHealthIndicator
{

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception
    {
        builder.withDetail("Mensagem","De pé esperando uma posição sua...").up();
    }
}
