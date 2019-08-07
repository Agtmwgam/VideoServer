package com.tw.dao;

import com.tw.entity.BeatMessage;
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
public interface BeatMessageDao {

    // save
    @Transactional
    void saveBeatMessage(BeatMessage beatMessage);

}
