package com.bemobi.smstools;

import com.bemobi.smstools.config.CouchDBConfig;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.net.MalformedURLException;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
@Configuration
@ComponentScan(basePackages = "com.bemobi.smstools")
@EnableConfigurationProperties(CouchDBConfig.class)
@EnableAutoConfiguration(exclude = {JpaBaseConfiguration.class, HibernateJpaAutoConfiguration.class, SolrAutoConfiguration.class})
public class AutoConfiguration
{

    @Autowired
    private CouchDBConfig couchDBConfig;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AutoConfiguration.class, args);
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(1);
        messageSource.setBasenames("file:./resources/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CouchDbConnector createConnector() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url(couchDBConfig.getUrl()).username(couchDBConfig.getUser())
                .password(couchDBConfig.getPassword()).build();
        CouchDbInstance couchDbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector connector = new StdCouchDbConnector(couchDBConfig.getDataBase(), couchDbInstance);
        connector.createDatabaseIfNotExists();
        return connector;

    }

}
