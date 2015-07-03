package com.bemobi.smstools.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
public class Device
{

    @JsonProperty("id")
    private String id;

    @JsonProperty("_rev")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String rev;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    @Override
    public String toString() {
        return "GCMDevice{" +
                "id='" + id + '\'' +
                ", rev='" + rev + '\'' +
                '}';
    }

}
