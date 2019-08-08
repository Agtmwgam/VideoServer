package com.tw.service;


import com.tw.dao.VUserDao;
import com.tw.entity.VUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Service
public class VUserService {

//	@Resource
	@Autowired
	private VUserDao vUserDao;

	// 新建一个用户
	@Transactional
	public Boolean creatUser(VUser user) {
		System.out.println("service");
		vUserDao.creatUser(user);
		return true;
	}

//	寻找用户
	@Transactional
	public Boolean queryUser(VUser user) {
		System.out.println("service");
		vUserDao.queryUser(user);
		return true;
	}


}
