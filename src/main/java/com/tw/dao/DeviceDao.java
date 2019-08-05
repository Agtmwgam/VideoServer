package com.tw.dao;

import com.tw.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Mapper
public interface DeviceDao {

    @Transient
    Integer addDevice(Device device);

    Integer deleteDevice(Map<String, Object> param);

    Integer updateDevice(Device device);

    Device getDeviceById(Map<String, Object> param);

    List<Device> getDeviceByCodition(Device device);
}
