package com.tw.dao;

import com.tw.entity.DeviceGroupRelate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceGroupRelateDao {

    List<DeviceGroupRelate> getDeviceGroupByGroupId(int groupId);

    List<DeviceGroupRelate> getDeviceGroupRelateByCondition(DeviceGroupRelate deviceGroupRelate);

    //根据设备组ID或者设备ID获得设备组与设备的唯一关系（每个设备只能属于同一用户的设备组下） --by liutianwen
    DeviceGroupRelate getDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int addDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int deleteByDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int updateDeviceGroupRelateBy(DeviceGroupRelate deviceGroupRelate);

    int deleteDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int linkGroupCount(int deviceId);
}
