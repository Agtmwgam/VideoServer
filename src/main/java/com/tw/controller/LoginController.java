package com.tw.controller;

import com.tw.entity.RootInfo;
import com.tw.entity.VUser;
import com.tw.service.MessageService;
import com.tw.service.RootInfoService;
import com.tw.service.UserService;
import com.tw.service.VUserService;
import com.tw.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @Author: John
 * @Description:    用于校验登录的控制权
 * @Date:  2019/8/3 19:20
 * @param: null
 * @return:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MessageService messageService;

    @Autowired
    private VUserService vUserService;

    @Autowired
    private RootInfoService rootInfoService;

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @Author: John
     * @Description: 发送短信的方法
     * @Date:  2019/8/4 0:00
     * @param: requestMap
     * @return:
     */
    @RequestMapping(value = "/smsSendCode", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo sendMsg(@RequestBody Map<String,Object> requestMap) {

        logger.info("===========从前端接收的内容为："+requestMap.toString());

        ResponseInfo response = new ResponseInfo();
        String phoneNumber = (String) requestMap.get("phoneNumber");

        //校验前端传过来的手机号码是否是正确的，如果正确就继续，否则就返回格式错误
        Boolean isValidPhoneNumber = PhoneUtil.isNotValidChinesePhone(phoneNumber);
        if (isValidPhoneNumber) {
            logger.info("===============校验手机号码："+phoneNumber+" 成功！");
            Map<String, Object> resultMap = messageService.publicSendMessage(phoneNumber);
            response.setCode(CODE_SUCCESS);
            response.setMessage("send message success!");
            //将hash值和tamp时间返回给前端
            response.setData(resultMap);
        } else {
            logger.error("===============校验手机号码："+phoneNumber+" 失败！");
            response.setCode(CODE_ERROR);
            response.setMessage("it's not correct phoneNumber!");
        }
        return response;
    }


    /**
     * @Author: John
     * @Description: 校验短信验证是否正确
     * @Date:  2019/8/4 0:01
     * @param: requestMap
     * @return:
     */
    @RequestMapping(value = "/smsValidate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo validateNum(@RequestBody Map<String,Object> requestMap) {
        logger.info("==============从前端收到的校验信息为："+requestMap.toString());
        ResponseInfo response = messageService.validateNum(requestMap);
        return response;
    }


    /**
     * @Author: John
     * @Description: 用户通过密码登录
     * @Date:  2019/8/10 14:49
     * @param: requestMap 登录信息（phoneNumber、pwd）
     * @return:
     */
    @RequestMapping(value = "/logOnByPwd", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo logOnByPwd(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //短信校验是通过的情况下才可以登录
        String passWord = (String) requestMap.get("pwd");
        if (StringUtils.isBlank(passWord)) {
            response.setCode(CODE_ERROR);
            response.setMessage("PassWord couldn't be null!");
            return response;
        }
        //如果密码不为空，就校验
        String phoneNumber = (String)requestMap.get("phoneNumber");
        VUser vUser = new VUser();
        vUser.setPassword(phoneNumber);
        vUser.setPassword(passWord);
        VUser user = vUserService.queryUser(vUser);
        if (user != null) {
            System.out.println("============查询到的用户为："+user.toString());
            System.out.println("============登录成功");
            //TODO
            //这里后面要将这个登录状态放到jwt里面，维持这个登录状态
            //TODO
            response.setCode(CODE_SUCCESS);
            response.setMessage(phoneNumber + " login success!");
            return response;
        }
        response.setCode(CODE_ERROR);
        response.setMessage("couldn't find this user!");
        return response;

    }


    /**
     * @Author: John
     * @Description: 用户通过短信登录
     * @Date:  2019/8/10 16:02
     * @param: requestMap
     * @return:
     */
    @RequestMapping(value = "/logOnByMsg", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseInfo logOnByMsg(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //先检测是否在数据库中应有该手机号码了
        String phoneNumber = (String)requestMap.get("phoneNumber");
        Boolean isRootPhone = false;

        //如果管理员列表里面有这个号码的话，有限处理成管理员权限
        RootInfo rootInfo = new RootInfo();
        rootInfo.setRootPhone(phoneNumber);
        List<RootInfo> info = rootInfoService.getRootInfo(rootInfo);
        if (info != null && info.size() >0) {
            isRootPhone = true;
        }

        VUser vUser = new VUser();
        vUser.setPhoneNumber(phoneNumber);
        if ((vUserService.queryUser(vUser) == null) & !isRootPhone) {
            response.setCode(CODE_ERROR);
            response.setMessage("this phoneNumber:" + phoneNumber + "have not yet registered, please registed first!");
            return response;
        }

        ResponseInfo resResponse = messageService.validateNum(requestMap);
        if (resResponse.getCode() == CODE_SUCCESS) {
            logger.warn("======短信校验成功");
            response.setCode(CODE_SUCCESS);
            response.setMessage(phoneNumber + " registered success!");
            //如果是管理员登录成功，要把管理员的标记添加到data里面
            if (isRootPhone) {
                Map<String, Object> isRootMap = new HashMap<String, Object>();
                isRootMap.put("isRoot", CODE_SUCCESS);
                response.setData(isRootMap);
            }
        } else {
            logger.warn("======短信校验失败");
            response.setCode(CODE_ERROR);
            response.setMessage(phoneNumber + " registered failed!");
        }
        return response;
    }


    //校验管理员的二次登录密码

}