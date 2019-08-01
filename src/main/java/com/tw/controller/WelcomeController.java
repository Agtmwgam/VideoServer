package com.tw.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tw.entity.User;

@Controller
public class WelcomeController {
	
	// 欢迎界面
	@RequestMapping("/")
	public String welcome(Model model){
		System.out.println("--------------------欢迎访问---------------------");
		User user=new User();
		user.setName("Bertram");
		model.addAttribute("user", user);
		return "welcome";
	}
}
