package com.tw.dao;

import com.tw.entity.Device;
import com.tw.entity.VUser;
import org.apache.ibatis.annotations.Mapper;
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
@Repository
public interface DeviceDao {

    @Transient
    Integer addDevice(Device device);

    Integer deleteDevice(Map<String, Object> param);

    Integer updateDevice(Device device);

    Device getDeviceById(Map<String, Object> param);

    List<Device> getDeviceByCodition(Device device);

    /**
     * @author liutianwen
     * @desc  根据传入vuser信息查看设备号
     * @param VUser
     * @return
     */
   List<String> getDeviceByUser(VUser user);
}
