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
        registry.addResourceHandler("/uploads2/**").addResourceLocations("file:D:/uploads2/");
        //Linux下
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:/Users/liuyanzhao/Documents/uploads/");
    }

}
