package com.tw.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tw.entity.User;
import com.tw.service.UserService;
import com.tw.util.ResponseInfo;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@RequestMapping(value = "/user/")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @Author: John
	 * @Description: 创建用户
	 * @Date:  2019/8/4 13:38
	 * @param: null
	 * @return:
	 */
	@RequestMapping(value = "createUser")
	public String createUser(User user) {
		System.out.println("--------------开始创建用户----------------");
		System.out.println(user);
		userService.creatUser(user);

		return "redirect:/user/userCenter";
	}


	/** 
	 * @Author: John
	 * @Description: 删除用户
	 * @Date:  2019/8/4 13:46
	 * @param: null
	 * @return: 
	 */
	@PostMapping("/deleteUser")
	public ResponseInfo<String> deleteUser(@RequestParam(value = "userId") String userId) {
		ResponseInfo responseInfo = new ResponseInfo();
		User user = new User();
		user.setId(userId);
		return userService.delUser(user);
	}


	@RequestMapping(value = "userCenter")
	public String userCenter(Model model) {
		System.out.println("--------------开始搜索用户----------------");
		ArrayList<User> userList = userService.queryAllUser();
		model.addAttribute("userList", userList);
		System.out.println("--------------结束搜索用户----------------");
		return "userCenter";
	}

	@RequestMapping(value = "test")
	public String test(Model model) {
		System.out.println("--------------测试弹窗----------------");
		return "test";
	}


	@SuppressWarnings("rawtypes")
	@PostMapping(value = "testDataBase1")
	// public ResponseInfo testDataBase1(@Param(value = "user") User user)
	// throws Exception{
	// public ResponseInfo testDataBase1(@RequestParam(value = "user") User
	// user) throws Exception{
	public ResponseInfo testDataBase1(@RequestBody String user) throws Exception {
		// User user=new User();
		ResponseInfo responseInfo = null;
		// userService.queryUserById(user);
		return responseInfo;
	}

}
