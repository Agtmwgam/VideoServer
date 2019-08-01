package com.tw.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/test/")
public class ConfigTest {
	
	//把配置文件中的值赋给字符串
	@Value(value = "${systemName}")
	private String testString;
	
	@ResponseBody
	@RequestMapping(value="testConfig")
	public String testConfig(){
		return testString;
	}
}
