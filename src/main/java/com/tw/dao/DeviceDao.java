package com.tw.dao;

import com.tw.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Map;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Mapper
public interface DeviceDao {

    @Transient
    int addDevice(Device device);
}
