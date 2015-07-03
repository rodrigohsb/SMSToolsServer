package com.bemobi.smstools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@ConfigurationProperties(prefix = "couchdb")
public class CouchDBConfig
{

    private String dataBase;

    private String user;

    private String password;

    private String url;

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
