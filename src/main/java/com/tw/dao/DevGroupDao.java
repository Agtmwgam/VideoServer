package com.tw.dao;

import com.tw.entity.DeviceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DevGroupDao {

    int addDevGroup(DeviceGroup deviceGroup);

    int deleteDevGroupById(int devGroupId);

    int updateDevGroup(DeviceGroup deviceGroup);

    DeviceGroup getDevGroupById(int devGroupId);

    List<DeviceGroup> getDevGroupByGroupName(String devGroupName);

    DeviceGroup getDevGroupBySerial(String serial);
}
