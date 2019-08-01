package com.tw.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMsgService {
	
	@Async //异步注解
	public void sendMsg() {
		System.out.println("开始发送短信2");
		for (int i = 0; i < 3; i++) {
			System.out.println(i);
			if (i == 2) {
				System.out.println("短信发送完毕3");
			}
		}
	}
}
