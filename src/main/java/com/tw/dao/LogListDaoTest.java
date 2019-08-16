package com.tw.dao;

import com.tw.entity.LogList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/16
 * @Param:
 * @return:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LogListDaoTest {

    @Autowired
    LogListDao dao;

    @Test
    public void addLog() throws Exception {

        LogList logList = new LogList();
        logList.setSerial("QSZ001");
        logList.setLogNum("43321");
        logList.setLogTime("31232");
        logList.setIpAddress("123.123.123.123");
        logList.setLogName("321");
        logList.setLogPath("http://fsdafdsfsf");
        System.out.println(logList);
        dao.addLogList(logList);

    }

    @Test
    public void getLogListBySerialPage() throws Exception {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("serial", "QSZ001");
        param.put("start", "0");
        param.put("pageSize", "10");

        List<LogList> logListList = dao.getLogListBySerialPage(param);
        System.out.println("");
    }

}