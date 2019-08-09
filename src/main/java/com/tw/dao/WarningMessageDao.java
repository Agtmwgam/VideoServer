package com.tw.dao;

import com.tw.entity.WarningMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zhuoshouyi
 * @Description:
 * @Date: 2019/8/7
 * @Param:
 * @return:
 */
@Mapper
@Repository
public interface WarningMessageDao {

    // save
    @Transactional
    void saveWarningMessage(WarningMessage warningMessage);


}
