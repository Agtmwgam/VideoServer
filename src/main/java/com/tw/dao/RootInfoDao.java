package com.tw.dao;

import com.tw.entity.RootInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RootInfoDao {

    List<RootInfo> getRootInfo(RootInfo rootInfo);
}
