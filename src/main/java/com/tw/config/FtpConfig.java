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
@ConfigurationProperties(prefix = "ftp")
@PropertySource("classpath:application.properties")
public  class FtpConfig {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.basePath}")
    private String basePath;

    @Value("${ftp.logsPath}")
    private String logsPath;

    @Value("${ftp.picturePath}")
    private String picturePath;

    @Value("${ftp.videoPath}")
    private String videoPath;

    @Value("${ftp.firmwarePath}")
    private String firmwarePath;

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

    public String getLogsPath() {
        return logsPath;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setLogsPath(String logsPath) {
        this.logsPath = logsPath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }


    public String getFirmwarePath() {
        return firmwarePath;
    }

    public void setFirmwarePath(String firmwarePath) {
        this.firmwarePath = firmwarePath;
    }


}