package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.MessageService;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
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

        ResponseInfo response = new ResponseInfo();
        //手机号码是否正规范
        Boolean isRightPhone = false;
        //验证码是否正确
        Boolean isRightCode = false;
        //密码是否规范
        Boolean isRightPassword = false;


        //手机号码校验是否规范
        isRightPhone = user.getPhoneNumber().matches(ConstantParam.VERIFYPHONENUMBER);

//        TODO 调用短信验证码验证接口
//        Boolean isRightCode=messageService.sendMessage(user.getPhoneNumber());

        //密码是否符合规范
        isRightPassword = user.getPassword().matches(ConstantParam.VERIFYPASSWORD);

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


        //创建用户
        Boolean flag = userService.creatUser(user);
        response.setCode("0000");
        response.setMessage(" Registered successfully!");

        return response;
    }


    public static void main(String[] args) {
        RegisterController rc = new RegisterController();
        rc.createUser(new VUser(), null);
    }


}
