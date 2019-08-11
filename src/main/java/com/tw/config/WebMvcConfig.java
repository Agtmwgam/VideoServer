package com.tw.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @Author: lushiqin
 * @Description:FTP服务器资源与web服务器资源映射类
 * @Date: 2019/8/6
 * @return:
 */
@Configuration
@EnableAutoConfiguration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下（本地测试用）
        registry.addResourceHandler("/video/**").addResourceLocations("file:D:/testVideo/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/testPicture/");
        registry.addResourceHandler("/logs/**").addResourceLocations("file:D:/testVideo/");
        //Linux下（正式环境）将url访问路径映射到ftp服务器
        registry.addResourceHandler("/video/**").addResourceLocations("file:/home/ftp123/video/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:/home/ftp123/picture/");

    }

}
