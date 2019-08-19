package com.tw.controller;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.RootInfo;
import com.tw.service.RootInfoService;
import com.tw.util.ResponseInfo;
import com.tw.util.UserAuthentication;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;


/**
 * @Author: John
 * @Description: 管理员登录相关
 * @Date:  2019/8/11 14:22
 * @param: null
 * @return:
 */
@RestController
@RequestMapping("/rootInfo")
public class RootInfoController {

    @Autowired
    private RootInfoService rootInfoService;


    //日志
    private static Logger logger = Logger.getLogger(DeviceController.class);


    @PostMapping("/loginBySecPwd")
    public ResponseInfo  loginBySecPwd(@RequestParam("secondPsd") String secondePwd, HttpServletRequest httpServletRequest) {

        ResponseInfo response = new ResponseInfo();
        // 1.校验用户身份
        UserRoleDTO userRoleDTO = UserAuthentication.authentication(httpServletRequest);

        if (StringUtils.isBlank(secondePwd)) {
            response.setCode(CODE_ERROR);
            response.setMessage("secondPwd couldn't not be null!");
            return response;
        } else {
            RootInfo rootInfo = new RootInfo();
            rootInfo.setRootPhone(userRoleDTO.getPhoneNumber());
            rootInfo.setSecondPassword(secondePwd);
            List<RootInfo> rootInfos = rootInfoService.getRootInfo(rootInfo);
            if (rootInfos != null && rootInfos.size() > 0) {
                response.setCode(CODE_SUCCESS);
                response.setMessage("login success!");
                return response;
            } else {
                response.setCode(CODE_ERROR);
                response.setMessage("account is not exist or secondPassword is incorrect!");
                return  response;
            }
        }
    }
}
