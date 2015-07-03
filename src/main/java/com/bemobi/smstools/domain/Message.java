package com.bemobi.smstools.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
public class Message implements Serializable {

    private String collapseKey;
    private Boolean delayWhileIdle;
    private Integer timeToLive;
    private Map<String, String> data;
    private Map<String, String> notificationParams;
    private Boolean dryRun;
    private String restrictedPackageName;

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public Boolean getDelayWhileIdle() {
        return delayWhileIdle;
    }

    public void setDelayWhileIdle(Boolean delayWhileIdle) {
        this.delayWhileIdle = delayWhileIdle;
    }

    public Integer getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Integer timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getNotificationParams() {
        return notificationParams;
    }

    public void setNotificationParams(Map<String, String> notificationParams) {
        this.notificationParams = notificationParams;
    }

    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    public void setRestrictedPackageName(String restrictedPackageName) {
        this.restrictedPackageName = restrictedPackageName;
    }

    @Override
    public String toString() {
        return "Message{" +
                "collapseKey='" + collapseKey + '\'' +
                ", delayWhileIdle=" + delayWhileIdle +
                ", timeToLive=" + timeToLive +
                ", data=" + data +
                ", notificationParams=" + notificationParams +
                ", dryRun=" + dryRun +
                ", restrictedPackageName='" + restrictedPackageName + '\'' +
                '}';
    }

}