package com.tw.controller;

import com.tw.common.JsonMapper;
import com.tw.entity.DeviceVideo;
import com.tw.service.DeviceVideoService;
import com.tw.util.ResponseInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lushiqin
 * @Description: 短视频功能模块
 * @Date: 2019/8/6
 * @param: null
 * @return:
 */
@Controller
public class DeviceVideoController {

    private static Logger log = Logger.getLogger(DeviceVideoController.class);


    @Autowired
    private DeviceVideoService  deviceVideoService;

    /***
     * @description 告警视频详情接口
     * @param serial
     * @return
     */
    @GetMapping("/getWarningInfoDesc")
    public String getWarningInfoDesc(@RequestParam(value = "serial") String serial
                                            ,@RequestParam(value = "eventId") String eventId) {
        log.info("=====/getWarningInfoDesc获取到的参数是=====serial:"+serial+"  eventId:"+eventId);
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> resultMap = new HashMap<>();
        String warningVideoPath = deviceVideoService.getWarningInfoDesc(serial,eventId);
        if (warningVideoPath != null) {
            resultMap.put("warningVideoPath",warningVideoPath);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getDeviceByserial success!");
            response.setData(resultMap);
            System.out.println("=====warningVideoPath====="+warningVideoPath);
            log.info("=====getWarningInfoDesc:warningVideoPath====="+warningVideoPath);
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("warningVideoPath error!");
        }
        return JsonMapper.toJsonString(response);
    }


    /***
     *告警视频列表接口
     * @param serial
     * @return
     */
    @GetMapping("/getWarningInfoList")
    public String getWarningInfoList(@RequestParam(value = "serial") String serial) {
        log.info("=====/getWarningInfoList获取到的参数是=====serial:"+serial);
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> resultMap = new HashMap<>();
        List<DeviceVideo> list = deviceVideoService.getWarningInfoList(serial);
        for (DeviceVideo device1 : list) {
            System.out.println("===:"+device1.toString());
        }
        if (list != null) {
            int total = list.size();
            resultMap.put("total", total);
            resultMap.put("list", list);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getWarningInfoList success!");
            response.setData(resultMap);
        } else {
            resultMap.put("total", 0);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getWarningInfoList failed!");

        }
        return JsonMapper.toJsonString(response);
    }




}
