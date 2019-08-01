package com.tw.api;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tw.entity.User;
import com.tw.service.UserService;
import com.tw.util.ResponseInfo;

@RestController
@RequestMapping(value = "/user/")
public class UserApi {
	private static Logger log = Logger.getLogger(UserApi.class);

	@Autowired
	private UserService userService;

	// 新建一个用户
	@RequestMapping(value = "creatUser")
	public ResponseInfo<String> creatUser(@RequestBody User user) {
		// System.out.println(request);
		System.out.println("--------------开始创建用户----------------");
		log.info("------------------>creatUser()" + user);
		user.setId(user.creatId());
		userService.creatUser(user);
		System.out.println("--------------结束创建用户----------------");
		return ResponseInfo.success("创建成功");
	}

	// 删除客户
	@ResponseBody
	@RequestMapping(value = "delUser")
	public ResponseInfo<String> delUser(@RequestBody User user) {
		System.out.println("-------------------删除客户信息开始-----------------------");
		ResponseInfo<String> response = userService.delUser(user);
		System.out.println("-------------------删除客户信息开始-----------------------");
		return response;
	}

	// 更新客户信息
	@RequestMapping(value = "modifyUser")
	public ResponseInfo<String> modifyUser(@RequestBody User user) {
		System.out.println("-------------------更新客户信息开始-----------------------");
		ResponseInfo<User> userInfo=null;
		try {
			userInfo = userService.queryUserById(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userInfo.getData() == null) {
			return ResponseInfo.error("错误，无此用户ID！");
		}
		ResponseInfo<String> modifyInfo = userService.modifyUser(user);
		System.out.println("-------------------更新客户信息结束-----------------------");
		return modifyInfo;
	}

	// 根据传入的信息查找对应的用户信息
	@RequestMapping(value = "queryUsers")
	public ArrayList<User> queryUser(@RequestBody User user) {
		System.out.println("--------------开始搜索用户----------------");
		ArrayList<User> user2=null;
		try {
			user2 = userService.queryUsers(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------------结束搜索用户----------------");
		return user2;
	}

	// 根据传入的ID查找符合条件的唯一用户信息
	@RequestMapping(value = "queryUserById")
	public User queryUserById(User user) {
		ResponseInfo<User> userInfo=null;
		try {
			userInfo = userService.queryUserById(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user=userInfo.getData();
		return user;
	}

}
