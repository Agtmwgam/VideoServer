package com.tw.dao;

import com.tw.entity.DeviceGroupRelate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DeviceGroupRelateDao {

    List<DeviceGroupRelate> getDeviceGroupByGroupId(int groupId);
}
