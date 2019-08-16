package com.tw.dao;

import com.tw.entity.LogList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/16
 * @Param:
 * @return:
 */
@Mapper
@Repository
public interface LogListDao {

    @Transient
    void addLogList(LogList logList);

    List<LogList> getLogListBySerialPage(Map<String, Object> param);

    Integer getTotalBySerial(Map<String, Object> param);

}
