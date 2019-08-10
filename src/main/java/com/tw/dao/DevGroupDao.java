package com.tw.dao;

import com.tw.entity.DevGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DevGroupDao {

    int addDevGroup(DevGroup devGroup);

    int deleteDevGroupById(int devGroupId);

    int updateDevGroup(DevGroup devGroup);

    DevGroup getDevGroupById(int devGroupId);

    List<DevGroup> getDevGroupByGroupName(String devGroupName);
}
