package com.tw.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.service.SendMsgService;

/**
 * 
* @author liutianwen   
* @Description: 异步发送短信
* @date 2018年8月28日
* @demandNO	1080  
* @branch
 */
@RestController
public class SendMsgController {
	
	@Autowired
	private SendMsgService sendMsgService;
		
	@RequestMapping(value="/sendMsg")
	public String sendMsg(){
		System.out.println("=======调用开始1=====");
		sendMsgService.sendMsg();
		System.out.println("=======调用结束4=====");
		return "success";
	}
}
