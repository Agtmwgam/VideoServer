package com.tw.test;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class ConfigTest {
	@Value("new Date(System.currentTimeMillis())")
	public static Date name;

	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		System.out.println(date);
	}
}
