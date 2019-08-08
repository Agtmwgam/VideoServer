package com.tw.dao;

import com.tw.entity.BeatMessage;
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
public interface BeatMessageDao {

    // save
    @Transactional
    void saveBeatMessage(BeatMessage beatMessage);

}
