package com.tw.dao;

import com.tw.entity.Device;
import com.tw.entity.RootDeviceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RootDeviceGroupDao {

    List<RootDeviceGroup> getAllRootDeviceGroup();

    List<Device> getRootDeviceByGroupId(int rootDeviceGroupId);

    int addRootGroup(String rootDeviceGroupName);

    int moveRootGroup(Map<String, Object> param);

    String getGroupNameByCondition(int newGroupId);

    int deleteRootGroup(RootDeviceGroup rootDeviceGroup);

    int modifyRootDeviceGroupName(Map<String, Object> param);

    Boolean checkExistGroup(Map<String, Object> param);

    Boolean checkIsDefaultGroup(Map<String, Object> param);

    RootDeviceGroup getRootDeviceGroupById(int id);

    List<RootDeviceGroup> getObjByDeviceGroupId(Map<String, Object> rootDeviceGroupId);

    int getDefaultGroupId(Map<String, Object> param);

    int moveToDefaultGroup(Map<String, Object> param);

    int delRootDevice(Map<String, Object> param);
}