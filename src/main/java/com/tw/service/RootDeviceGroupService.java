package com.tw.service;

import com.tw.dao.RootDeviceGroupDao;
import com.tw.entity.RootDeviceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RootDeviceGroupService {
    @Autowired
    private RootDeviceGroupDao rootDeviceGroupDao;

    public List<RootDeviceGroup> getAllRootDeviceGroup() {
        return rootDeviceGroupDao.getAllRootDeviceGroup();
    }
}
