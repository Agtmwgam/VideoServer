package com.tw.service;

import com.tw.dao.DevGroupDao;
import com.tw.entity.DevGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevGroupService {

    @Autowired
    private DevGroupDao devGroupDao;

    //添加
    public int addDevGroup(DevGroup devGroup) {
        return devGroupDao.addDevGroup(devGroup);

    }


    //修改
    public int updateDevGroup(DevGroup devGroup) {
        return devGroupDao.updateDevGroup(devGroup);
    }


    //删除
    public int deleteDevGroupById(int devGroupId) {
        return devGroupDao.deleteDevGroupById(devGroupId);
    }


    //根据id查找
    public DevGroup getDevGroupById(int devGroupId) {
        return devGroupDao.getDevGroupById(devGroupId);
    }


    //根据groupName查找
    public List<DevGroup> getDevGroupByGroupName(String devGroupName) {
        return devGroupDao.getDevGroupByGroupName(devGroupName);
    }
}
