package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.MessageService;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @Classname RegisterController
 * @Description 用户注册
 * @Date 2019/8/5 22:21
 * @Created by liutianwen
 */
@Controller
@RequestMapping("/forget/")
public class ForgetPasswordController {

    @Autowired
    private VUserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 忘记密码
     */
    @RequestMapping(value = "findPwd")
    public ResponseInfo findPassword(@RequestBody Map<String, Object> requestMap) {

//        String verifyCode = requestMap.get("verifyCode").toString();
        VUser user = (VUser) requestMap.get("VUser");
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();

        user.setPhoneNumber("18210081211");

//       检查用户注册信息(手机号，新密码，验证码)是否正常
        ResponseInfo response = checkUserInfo(requestMap);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }

        //创建用户
        Boolean flag = userService.creatUser(user);
        response.setCode(CODE_SUCCESS);
        response.setMessage(" Registered successfully!");

        return response;
    }

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 校验用户注册时，校验所有参数是否正常
     */
    public ResponseInfo checkUserInfo(Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        VUser user = (VUser) requestMap.get("VUser");
        //       用户注册手机号
        String phoneNumber = user.getPhoneNumber();
//       用户注册密码
        String password = user.getPassword();
        //手机号码是否正规范
        Boolean isRightPhone = false;
        //密码是否规范
        Boolean isRightPassword = false;

        //手机号码校验
//        手机号是否为空
        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber can not be empty!");
            return response;
        }
        //手机号码校验是否规范
        isRightPhone = phoneNumber.matches(ConstantParam.VERIFYPHONENUMBER);
        if (!isRightPhone) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber is not correct!");
            return response;
        }

//        密码校验
//        密码是否为空
        if (StringUtils.isBlank(password)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The password can not be empty!");
            return response;
        }
        //密码是否符合规范
        isRightPassword = password.matches(ConstantParam.VERIFYPASSWORD);
        //      密码是否符合规范
        if (!isRightPassword) {
            response.setCode(CODE_ERROR);
            response.setMessage("The password is not correct!");
            return response;
        }

        // 验证码校验
//        短信是否为空
        if (StringUtils.isBlank(requestMap.get("msgNum").toString())) {
            response.setCode("9999");
            response.setMessage("The msgNum can not be empty!");
            return response;
        }
        //  调用短信验证码验证接口
        response = messageService.validateNum(requestMap);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }


        //校验该用户是否已注册
        VUser user2 = new VUser();
        user2.setPhoneNumber(phoneNumber);
        VUser returnUser = userService.queryUser(user2);
        if (!StringUtils.isBlank(returnUser.getPhoneNumber())) {
            response.setCode(CODE_ERROR);
            response.setMessage("The user is not exist!");
            return response;
        }

        response.setCode(CODE_ERROR);
        return response;
    }


}
