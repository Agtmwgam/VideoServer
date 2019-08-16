package com.tw.service;

import com.tw.dao.LogListDao;
import com.tw.entity.LogList;
import com.tw.form.LogListForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class LogListService {

    private static Logger log = Logger.getLogger(LogListService.class);

    @Autowired
    LogListDao logListDao;


    public Integer getTotalBySerial(LogListForm logListForm){

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (logListForm.getPageNo()-1) * logListForm.getPageSize());
        param.put("pageSize", logListForm.getPageSize());
        param.put("serial", logListForm.getSerial());
        param.put("startTime", logListForm.getStartTime());
        param.put("endTime", logListForm.getEndTime());

        return logListDao.getTotalBySerial(param);
    }

    public void addLogList(LogList logList){
        logListDao.addLogList(logList);
        log.info("【日志】日志数据入库成功");
    }


    public List<LogList> getListBySerialPage(LogListForm logListForm){

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", (logListForm.getPageNo()-1) * logListForm.getPageSize());
        param.put("pageSize", logListForm.getPageSize());
        param.put("serial", logListForm.getSerial());
        param.put("startTime", logListForm.getStartTime());
        param.put("endTime", logListForm.getEndTime());

        return logListDao.getLogListBySerialPage(param);
    }



}
