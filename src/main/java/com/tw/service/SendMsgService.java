package com.tw.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
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
