package com.tw.controller;

import com.tw.entity.LogList;
import com.tw.form.LogListForm;
import com.tw.service.LogListService;
import com.tw.util.ResponseInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhuoshouyi
 * @Description: 管理员的日志列表
 * @Date: 2019/8/15
 * @Param:
 * @return:
 */
@RestController
public class LogListController {

    private static Logger log = Logger.getLogger(LogListController.class);

    // 全局统一时间格式化格式
    SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 日期的正则表达式
    final String DATE_REGULAR = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-2][0-9]:[0-6][0-9]:[0-6][0-9]";
    Pattern IS_DATE_FORMAT = Pattern.compile(DATE_REGULAR);

    @Autowired
    LogListService logListService;

    /**
     * 日志列表接口
     * @return
     */
    @PostMapping("/logList")
    public ResponseInfo logList(@RequestBody LogListForm logListForm){

        // 获取用户信息
//        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);

        final String serial = logListForm.getSerial();
        final String startTime = logListForm.getStartTime();
        final String endTime = logListForm.getEndTime();
        final int pageNo = logListForm.getPageNo();
        final int pageSize = logListForm.getPageSize();

        log.info("【日志】从前端获取到的参数是=====serial:"+serial+
                "  startTime:"+ startTime   +
                "  endTime:" + endTime +
                "  pageNo:" + pageNo +
                "  pageSize:" + pageSize);

        ResponseInfo response = new ResponseInfo();



        // 先对开始日期进行空值判断
        if (startTime!=null && !startTime.equals("")){
            // 如果有日期传入,需要按照 2019-01-01 00:00:00 的19位规范传入
            Matcher isStartValid = IS_DATE_FORMAT.matcher(startTime);
            if (!isStartValid.matches()){
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("startTime input error!");
                return response;
            }
        }

        // 先对结束日期进行空值判断
        if (endTime!=null && !endTime.equals("")){

            // 如果有日期传入,需要按照 2019-01-01 00:00:00 的19位规范传入
            Matcher isEndValid = IS_DATE_FORMAT.matcher(endTime);
            if (!isEndValid.matches()){
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("endTime input error!");
                return response;
            }
        }


        // 根据 serial 查询设备日志
        List<LogList> logLists = logListService.getListBySerialPage(logListForm);

        // 查询总数 total
        Integer total = logListService.getTotalBySerial(logListForm);

        if (logLists != null) {
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getListBySerialPage success!");
            response.setData(logLists);
            response.setPageNo(pageNo);
            response.setPageSize(pageSize);
            response.setTotal(total);
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getListBySerialPage failed!");
        }

        return response;
    }
}
