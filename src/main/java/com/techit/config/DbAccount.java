package com.techit.config;

import lombok.Getter;

import java.io.InputStream;
import java.util.Properties;

@Getter
public class DbAccount {
    private static final String PROPERTIES_FILE = "db.properties";
    private String driver;
    private String url;
    private String id;
    private String pw;

    public DbAccount() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = DbAccount.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
            properties.load(inputStream);

            driver = properties.getProperty("db.driver");
            url = properties.getProperty("db.url");
            id = properties.getProperty("db.id");
            pw = properties.getProperty("db.pw");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
