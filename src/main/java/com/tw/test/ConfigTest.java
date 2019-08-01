package com.tw.test;

import org.springframework.beans.factory.annotation.Value;

public class ConfigTest {
	@Value("${userName}")
	public static String name;

	public static void main(String[] args) {
		
		System.out.println(name);
	}
}
