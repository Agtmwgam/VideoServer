package com.tw.service;


import com.tw.dao.VUserDao;
import com.tw.entity.VUser;
import org.apache.log4j.Logger;
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

	@Autowired
	private VUserDao vUserDao;

	private static Logger log = Logger.getLogger(VUserService.class);

	// 新建一个用户
	@Transactional
	public void creatUser(VUser user) {
		try {
			vUserDao.creatUser(user);
		}catch (Exception e){
			log.error("创建用户失败！");
			log.error(e.toString());
		}
	}

//	寻找用户
	@Transactional
	public VUser queryUser(VUser user) {
		return vUserDao.queryUser(user);
	}

	//更新客户
	public Integer modifyUser(VUser user) {
		Integer num=0;
		try {
			num=vUserDao.modifyUser(user);
		}catch (Exception e){
			log.error("更新用户错误！");
			log.error(e.toString());
		}
		return num;
	}


}
