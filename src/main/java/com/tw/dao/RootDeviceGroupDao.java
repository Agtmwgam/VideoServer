package com.tw.dao;

import com.tw.entity.Device;
import com.tw.entity.RootDeviceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RootDeviceGroupDao {

    List<RootDeviceGroup> getAllRootDeviceGroup();

    List<Device> getRootDeviceByGroupId(int rootDeviceGroupId);
}