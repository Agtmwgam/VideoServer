package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.LoginService;
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
@RequestMapping("/register/")
public class RegisterController {

    @Autowired
    private VUserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private LoginService loginService;

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户注册
     */
    @RequestMapping(value = "toRegister")
    public ResponseInfo createUser(@RequestBody Map<String, Object> requestMap) {

//        String verifyCode = requestMap.get("verifyCode").toString();
        VUser user = (VUser) requestMap.get("VUser");
        String requestHash = requestMap.get("hash").toString();
        String tamp = requestMap.get("tamp").toString();
        String msgNum = requestMap.get("msgNum").toString();

        user.setPhoneNumber("18210081211");
        user.setPassword("4gews@#$rg");

//       检查用户注册信息(手机号，密码，验证码)是否正常
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
//        String verifyCode=requestMap.get("verifyCode").toString();
        VUser user = (VUser) requestMap.get("VUser");
        //       用户注册手机号
        String phoneNumber = user.getPhoneNumber();
//       用户注册密码
        String password = user.getPassword();
        //手机号码是否正规范
        Boolean isRightPhone = false;
        //验证码是否正确
//        Boolean isRightCode = false;
        //密码是否规范
        Boolean isRightPassword = false;

        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(password)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The password can not be empty!");
            return response;
        }
//        if (StringUtils.isBlank(verifyCode)) {
//            response.setCode("9999");
//            response.setMessage("The verifyCode can not be empty!");
//            return response;
//        }


        //手机号码校验是否规范
        isRightPhone = phoneNumber.matches(ConstantParam.VERIFYPHONENUMBER);
        //校验该用户是否已注册
        VUser user2 = new VUser();
        user2.setPhoneNumber(phoneNumber);
        boolean isRegesiter = userService.queryUser(user2);
        if (!isRegesiter) {
            response.setCode(CODE_ERROR);
            response.setMessage("The user is exist!");
            return response;
        }


//       调用短信验证码验证接口
        response = loginService.validateNum(requestMap);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }
//       isRightCode=messageService.sendMessage(user.getPhoneNumber());
        //密码是否符合规范
        isRightPassword = password.matches(ConstantParam.VERIFYPASSWORD);
        //手机号码是否规范
        if (!isRightPhone) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber is not correct!");
            return response;
        }

//      短信验证码是否正确
//        if (!isRightCode) {
//            response.setCode("9999");
//            response.setMessage("The messageCode is not correct!");
//            return response;
//        }

        //      密码是否符合规范
        if (!isRightPassword) {
            response.setCode(CODE_ERROR);
            response.setMessage("The password is not correct!");
            return response;
        }
        response.setCode(CODE_ERROR);
        return response;
    }


}
