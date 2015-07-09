package com.bemobi.smstools.domain;

import java.util.Date;

/**
 * Created by rodrigo.bacellar on 07/07/2015.
 */
public class SMS
{

    private String la;

    private String command;

    private Date sentDate;

    private Date receivedDate;

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "la='" + la + '\'' +
                ", command='" + command + '\'' +
                ", sentDate=" + sentDate +
                ", receivedDate=" + receivedDate +
                '}';
    }
}
