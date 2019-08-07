package com.tw.dao;

import com.tw.entity.WarningMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zhuoshouyi
 * @Description:
 * @Date: 2019/8/7
 * @Param:
 * @return:
 */
@Mapper
public interface WarningMessageDao {

    // save
    @Transactional
    void saveWarningMessage(WarningMessage warningMessage);


}
