package com.tw.service;


import com.tw.dao.UserDeviceRelateDao;
import com.tw.entity.UserDeviceRelate;
import com.tw.entity.VUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Service
public class UserDeviceRelateService {

	@Autowired
	private UserDeviceRelateDao userDeviceRelateDao;


	private static Logger log = Logger.getLogger(UserDeviceRelateService.class);

	// 新建用户设备
	@Transactional
	public void creatUserDevice(UserDeviceRelate udr) {
		try {
			userDeviceRelateDao.creatUserDevice(udr);
		}catch (Exception e){
			log.error("创建用户设备！");
			log.error(e.toString());
			e.printStackTrace();
		}
	}


	//删除该用户下的指定deviceId的设备
	@Transactional
	public Integer delUserDevice(int userId, int deviceId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("deviceId", deviceId);
		return userDeviceRelateDao.delUserDevice(param);
	}

	//	根据deviceId找到对应的user
	@Transactional
	public VUser getUserByDeviceID(int  deviceId) {
		return  userDeviceRelateDao.getUserByDeviceID(deviceId);
	}

}
