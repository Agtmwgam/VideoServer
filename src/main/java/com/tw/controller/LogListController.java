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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    LogListService logListService;

    /**
     * 日志列表接口
     * @return
     */
    @PostMapping("/logList")
    public ResponseInfo logList(HttpServletRequest httpServletRequest,
                                @RequestBody LogListForm logListForm){

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

        // 根据 serial 查询设备日志
        List<LogList> logLists = logListService.getListBySerialPage(logListForm);

        if (startTime.length()!=19 || endTime.length()!=19){
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("startTime or endTime input error!");
            response.setData(logLists);
        }

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
            response.setData(logLists);
        }

        return response;
    }
}
