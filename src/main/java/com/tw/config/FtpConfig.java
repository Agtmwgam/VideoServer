package com.tw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */
@Configuration
@ConfigurationProperties(prefix = "ftpCfg")
@PropertySource("classpath:application.properties")
public  class FtpConfig {


    @Value("${ftpCfg.host}")
    private String host;

    @Value("${ftpCfg.port}")
    private int port;

    @Value("${ftpCfg.username}")
    private String username;

    @Value("${ftpCfg.password}")
    private String password;

    @Value("${ftpCfg.basePath}")
    private String basePath;

    @Value("${ftpCfg.filePath}")
    private String filePath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }



    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}