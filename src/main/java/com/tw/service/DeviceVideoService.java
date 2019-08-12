package com.tw.service;

import com.tw.dao.DeviceVideoDao;
import com.tw.entity.DeviceVideo;
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
public class DeviceVideoService {

    @Autowired
    private DeviceVideoDao dao;

    /***
     * 告警视频详情接口
     * @param serial
     * @return
     */
    public String getWarningInfoDesc(String serial,String eventId) {
        return dao.getWarningInfoDesc(serial,eventId);
    }

    /***
     * 告警视频列表接口
     * @param serial ,eventId
     * @return
     */
    public List<DeviceVideo> getWarningInfoList(String serial,Integer pageSize, Integer pageNo) {

        return dao.getWarningInfoList(serial,pageSize,pageNo);
    }

    /***
     * 增加告警视频路径映射
     * @param video
     * @return
     */
    public int AddVideo(DeviceVideo video) {
        return dao.AddVideo(video);
    }
}
