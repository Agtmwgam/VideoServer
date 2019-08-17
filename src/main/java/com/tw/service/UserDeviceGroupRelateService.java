package com.tw.service;

import com.tw.dao.UserDeviceGroupRelateDao;
import com.tw.entity.UserDeviceGroupRelate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceGroupRelateService {

    @Autowired
    private UserDeviceGroupRelateDao userDeviceGroupRelateDao;

    //添加分组
    public int addUserDeviceGroupRelate(UserDeviceGroupRelate userDeviceGroupRelate) {
        return userDeviceGroupRelateDao.addUserDeviceGroupRelate(userDeviceGroupRelate);
    }

    //根据用户id获得对象
    public List<UserDeviceGroupRelate> getGroupListByUserId(int userId) {
        return userDeviceGroupRelateDao.getGroupListByUserId(userId);
    }


    //删除用户和组的关系
    public int delUserGroupRelate(UserDeviceGroupRelate userDeviceGroupRelate) {
        return userDeviceGroupRelateDao.delUserGroupRelate(userDeviceGroupRelate);
    }
}
