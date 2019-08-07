package com.tw.service;

import com.tw.dao.DevicePictureDao;
import com.tw.dao.DeviceVideoDao;
import com.tw.entity.DevicePicture;
import com.tw.entity.DeviceVideo;
import com.tw.entity.common.ConstantParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lushiqin
 * @Description: 视频的service类
 * @Date: 2019/8/6
 * @param: null
 * @return:
 */
@Service
public class DevicePictureService {

    @Autowired
    private DevicePictureDao dao;

    /***

    /***
     * 密度分析图接口
     * @param serial
     * @return
     */
    public List<DevicePicture> getDensityPicture(String serial) {

        return dao.getDensityPicture(serial);
    }

}
