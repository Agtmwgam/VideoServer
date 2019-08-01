package com.tw.entity;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class Person {
	private static int count = 0;
	int id;
	@Value("${testConfigString}")
	String name;
	public static int age;

	public Person(int id,String name){
		this.id=id;
		this.name=name;
	}
	
	public Person() {
		id = ++count;
	}
	
	public String toString() {
		return "count:"+count+",Id:" + id + ", Name:" + name + ", Age:" + age;
	}
}
