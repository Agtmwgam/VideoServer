package com.tw.service;


import com.tw.dao.UserDeviceRelateDao;
import com.tw.entity.UserDeviceRelate;
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

	// 逻辑删除用户
	@Transactional
	public Integer delUserDevice(UserDeviceRelate udr) {
		Integer num=0;
		try {
		num=	userDeviceRelateDao.delUserDevice(udr);
		}catch (Exception e){
			log.error("删除用户设备错误！");
			log.error(e.toString());
			e.printStackTrace();
		}

		return num;
	}




}
