package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.MessageService;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户注册
     */
    @RequestMapping(value = "toRegister")
    public ResponseInfo createUser(VUser user, String verifyCode) {
        user.setPhoneNumber("18210081211");

//       检查用户注册信息(手机号，密码，验证码)是否正常
        ResponseInfo response = checkUserInfo(user, verifyCode);
        if (response.getCode() == "9999") {
            return response;
        }

        //创建用户
        Boolean flag = userService.creatUser(user);
        response.setCode("0000");
        response.setMessage(" Registered successfully!");

        return response;
    }

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 校验用户注册时，所有参数是否正常
     */
    public ResponseInfo checkUserInfo(VUser user, String verifyCode) {
        ResponseInfo response = new ResponseInfo();
        //       用户注册手机号
        String phoneNumber = user.getPhoneNumber();
//       用户注册密码
        String password = user.getPassword();
        //手机号码是否正规范
        Boolean isRightPhone = false;
        //验证码是否正确
        Boolean isRightCode = false;
        //密码是否规范
        Boolean isRightPassword = false;

        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode("9999");
            response.setMessage("The phoneNumber can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(password)) {
            response.setCode("9999");
            response.setMessage("The password can not be empty!");
            return response;
        }
        if (StringUtils.isBlank(verifyCode)) {
            response.setCode("9999");
            response.setMessage("The verifyCode can not be empty!");
            return response;
        }


        //手机号码校验是否规范
        isRightPhone = phoneNumber.matches(ConstantParam.VERIFYPHONENUMBER);
//        TODO 调用短信验证码验证接口
//       isRightCode=messageService.sendMessage(user.getPhoneNumber());
        //密码是否符合规范
        isRightPassword = password.matches(ConstantParam.VERIFYPASSWORD);
        //手机号码是否规范
        if (!isRightPhone) {
            response.setCode("9999");
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
            response.setCode("9999");
            response.setMessage("The password is not correct!");
            return response;
        }
        response.setCode("9999");
        return response;
    }

    public static void main(String[] args) {
        RegisterController rc = new RegisterController();
        rc.createUser(new VUser(), null);
    }


}
