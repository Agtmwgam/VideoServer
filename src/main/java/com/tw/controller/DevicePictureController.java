package com.tw.controller;

import com.tw.entity.DevicePicture;
import com.tw.service.HeatDataService;
import com.tw.util.JsonMapperUtil;
import com.tw.service.DevicePictureService;
import com.tw.util.ResponseInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lushiqin
 * @Description:密度图片功能模块
 * @Date: 2019/8/6
 * @return:
 */
@RestController
@Controller
@RequestMapping("/picture/")
public class DevicePictureController {

    private static Logger log = Logger.getLogger(DevicePictureController.class);

    @Autowired
    private DevicePictureService  devicePictureService;

    @Autowired
    private HeatDataService heatDataService;

    /***
     * @description 密度分析图详情接口
     * @param serial
     * @return
     */
    @GetMapping("/getDensityPicture")
    public String getDensityPicture(@RequestParam(value = "serial") String serial
            , @RequestParam(value = "pageSize", required = false) Integer pageSize
            , @RequestParam(value = "pageNo", required = false) Integer pageNo) {
        log.info("=====/getDensityPicture从前端获取到的参数是=====serial:"+serial);
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> resultMap = new HashMap<>();
        List<DevicePicture> list = devicePictureService.getDensityPicture(serial);
        if (list.size()>=0) {
            int total = list.size();
            response.setPageSize(pageSize);
            response.setPageNo(pageNo);
            response.setTotal(total);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setMessage("getDensityPicture success!");
            response.setData(list);
        }
        else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getDensityPicture failed!");

        }
        return JsonMapperUtil.toJsonString(response);
    }



}
