package com.tw.service;

import com.tw.dao.DevicePictureDao;
import com.tw.entity.DevicePicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return 返回最新的一张密度图片路径
     */
    public List<DevicePicture> getDensityPicture(String serial) {

        return dao.getDensityPicture(serial);
    }



    /***
     * 密度分析图接口
     * @param serial
     * @return 返回密度图片数据集 -从终端传过来的告警信息中获取targetLocation。是密度图片所需要的数据集
     *
     */
    public List<String> getDensityPictureData(String serial) {

        return dao.getDensityPictureData(serial);
    }



    /***
     * 增加密度分析灰度图
     * @return
     */
    public int AddPicture(DevicePicture devicePicture) {

        return dao.AddPicture(devicePicture);
    }
}
