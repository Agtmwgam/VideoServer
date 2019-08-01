package com.tw.dao;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tw.entity.User;

@Mapper
public interface UserDao  {
	
	//计算客户总数
	int countUser(User user);
	
	//查找所有用户信息
	ArrayList<User> queryAllUser();
	
	//根据传入的信息查找对应的用户信息
	ArrayList<User> queryUsers(User user);
	
	// 根据传入的用户ID和抽奖编号查找符合条件的唯一用户信息
	User queryUserById(User user);
	
	//新建一个用户
	@Transactional
	void creatUser(User user);
	
	//更新客户信息 
	void modifyUser(User user);

	//删除用户
	void delUser(User user);
}
