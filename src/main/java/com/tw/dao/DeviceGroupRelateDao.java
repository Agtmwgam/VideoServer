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

    int addDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int deleteByDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);

    int updateDeviceGroupRelateBy(DeviceGroupRelate deviceGroupRelate);

    int deleteDeviceGroupRelate(DeviceGroupRelate deviceGroupRelate);
}
