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

    // 根据 serial 找到 device 的信息
    BeatMessage findBySerial(String serial);

    // 更新心跳信息
    void modifyBeat(BeatMessage beatMessage);

}
