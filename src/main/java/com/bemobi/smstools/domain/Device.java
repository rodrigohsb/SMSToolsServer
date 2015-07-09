package com.bemobi.smstools.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
public class Device {

    @JsonProperty("id")
    private String id;

    @JsonProperty("_rev")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String revision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", revision='" + revision + '\'' +
                '}';
    }
}
