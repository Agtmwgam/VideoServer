package com.tw.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tw.dao.UserDao;
import com.tw.entity.User;
import com.tw.util.ResponseInfo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	// 新建一个用户
	@Transactional
	public Boolean creatUser(User user) {
		/*
		 * String ID = user.getName() + user.getAge(); user.setId(ID);
		 */
		System.out.println("service");
		userDao.creatUser(user);
		// int a=1/0;
		return true;
	}

	// 删除用户
	@Transactional
	public ResponseInfo<String> delUser(User user) {
		try {
			String ID = user.getId();
			if (ID == null) {
				return ResponseInfo.error("查询用户ID不存在！");
			}
			userDao.delUser(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ResponseInfo.success("删除用户成功！");
	}

	// 更新客户信息
	@Transactional
	public ResponseInfo<String> modifyUser(User user) {
		String result = null;
		try {
			String ID = user.getId();
			if (ID == null) {
				result = "更新失败,用户ID不能为空!";
				return ResponseInfo.error(result);
			}
			userDao.modifyUser(user);
			result = "更新成功!";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseInfo.error(e.getMessage());
		}
		return ResponseInfo.success(result);
	}

	public int countUser(User user) {
		int count = 0;
		try {
			count = userDao.countUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 查找所有用户信息
	public ArrayList<User> queryAllUser() {
		try {
			ArrayList<User> userList = userDao.queryAllUser();
			return userList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// 根据传入的信息查找符合条件的所有用户信息
	public ArrayList<User> queryUsers(User user) throws Exception {
		ArrayList<User> user2 = userDao.queryUsers(user);
		return user2;
	}

	// 根据传入的用户ID或者抽奖编号查找符合条件的唯一用户信息
	public ResponseInfo<User> queryUserById(User user) throws Exception {
		user = userDao.queryUserById(user);
		return ResponseInfo.success(user);
	}
	
}
