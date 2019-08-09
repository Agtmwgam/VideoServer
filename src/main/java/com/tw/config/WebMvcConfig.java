package com.tw.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/6
 * @param: null
 * @return:
 */
@Configuration
@EnableAutoConfiguration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下
        registry.addResourceHandler("/video/**").addResourceLocations("file:D:/testVideo/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/testPicture/");
        registry.addResourceHandler("/logs/**").addResourceLocations("file:D:/testVideo/");
        //Linux下
        registry.addResourceHandler("/video/**").addResourceLocations("file:/home/ftp123/video/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:/home/ftp123/picture/");

    }

}
