package com.tw.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.service.MessageService;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@RestController
public class SendMsgController {
	
	@Autowired
	private MessageService sendMsgService;
		
	@RequestMapping(value="/sendMsg")
	public String sendMsg(){
		System.out.println("=======调用开始1=====");
		sendMsgService.sendMsg();
		System.out.println("=======调用结束4=====");
		return "success";
	}
}
