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

    public List<UserDeviceGroupRelate> getGroupListByUserId(int userId) {
        return userDeviceGroupRelateDao.getGroupListByUserId(userId);
    }
}
