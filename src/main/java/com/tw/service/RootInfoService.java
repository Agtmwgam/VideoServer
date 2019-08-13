package com.tw.service;

import com.tw.dao.RootInfoDao;
import com.tw.entity.RootInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RootInfoService {

    @Autowired
    private RootInfoDao rootInfoDao;


    public List<RootInfo> getRootInfo(RootInfo rootInfo) {
        return rootInfoDao.getRootInfo(rootInfo);
    }
}
