package com.tw.controller;

import com.tw.entity.DevicePicture;
import com.tw.service.DevicePictureService;
import com.tw.util.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/6
 * @param: null
 * @return:
 */
@Controller
@RequestMapping(value = "/picture")
public class DevicePictureController {

    @Autowired
    private DevicePictureService  devicePictureService;

    /***
     * @description 密度分析图详情接口
     * @param serial
     * @return
     */
    @RequestMapping("/getDensityPicture")
    public ResponseInfo getWarningInfoDesc(@RequestParam(value = "serial") String serial) {
        ResponseInfo response = new ResponseInfo();
        Map<String, Object> resultMap = new HashMap<>();
        List<DevicePicture> list = devicePictureService.getDensityPicture(serial);
        for (DevicePicture device1 : list) {
            System.out.println("===:"+device1.toString());
        }
        if (list != null) {
            int total = list.size();
            resultMap.put("total", total);
            resultMap.put("list", list);
            response.setCode(ResponseInfo.CODE_SUCCESS);
            response.setData(resultMap);
            response.setMessage("getDensityPicture success!");
        } else {
            resultMap.put("total", 0);
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("getDensityPicture failed!");

        }
        return response;
    }

}
