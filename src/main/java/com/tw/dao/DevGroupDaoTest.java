package com.tw.dao;

import com.tw.entity.DevGroup;
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
public class DevGroupDaoTest {

    @Autowired
    DevGroupDao devGroupDao;

    @Test
    public void getDevGroupBySerial() throws Exception {


        DevGroup devGroup = devGroupDao.getDevGroupBySerial("QSZN004");
        System.out.println("");

    }

}