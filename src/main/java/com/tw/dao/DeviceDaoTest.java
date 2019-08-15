package com.tw.dao;

import com.tw.entity.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/12
 * @Param:
 * @return:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DeviceDaoTest {

    @Autowired
    DeviceDao deviceDao;

    @Test
    public void getDeviceBySerial() throws Exception {

        Device device = deviceDao.getDeviceBySerial("Q12345678");
        System.out.println("");
    }

}