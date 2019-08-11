package com.tw.dao;

import com.tw.entity.BeatMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BeatMessageDaoTest {

    @Autowired
    BeatMessageDao beatMessageDao;

    @Test
    public void saveBeatMessage() throws Exception {

    }

    @Test
    public void findBySerial() throws Exception {
        BeatMessage beatMessage = beatMessageDao.findBySerial("T42683512");
        System.out.println("");

    }

}