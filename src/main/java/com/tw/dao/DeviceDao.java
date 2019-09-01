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

    List<Device> getDeviceBySerial(String serial);

    List<Device> getDeviceByCoditionPage(Map<String, Object> param);

    /**
     * @author liutianwen
     * @desc  根据传入device信息查找device实体
     * @param user
     * @return
     */
    Device getDeviceInfo(Device device);

    List<Device> getDeviceByCodition(Device device);

    List<Device> getDeviceLikeCondition(Map<String, Object> param);

    /**
     * @author liutianwen
     * @desc  根据传入vuser信息查看设备号
     * @param user
     * @return
     */
    List<String> getDeviceByUser(VUser user);

    int getTotalOfCondition(Device device);

    int getCountOfLikCondition(Device device);

    List<Device> getDeviceByGroupId(int groupId);

    int updateDeviceName(Device device);

    int updateDeviceStatus(Map<String, Object> param);
}
