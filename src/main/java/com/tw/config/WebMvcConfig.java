package com.tw.config;

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
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下
        registry.addResourceHandler("/video/**").addResourceLocations("file:D:/testVideo/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:D:/testPicture/");
        registry.addResourceHandler("/logs/**").addResourceLocations("file:D:/testVideo/");
        //Linux下
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:/Users/liuyanzhao/Documents/uploads/");

    }

}
