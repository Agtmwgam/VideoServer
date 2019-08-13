package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.MessageService;
import com.tw.service.VUserService;
import com.tw.util.PhoneUtil;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @Classname RegisterController
 * @Description 用户注册
 * @Date 2019/8/5 22:21
 * @Created by liutianwen
 */
@RestController
@RequestMapping("/shungkon/")
public class RegisterController {

    @Autowired
    private VUserService userService;

    @Autowired
    private MessageService messageService;

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "sendeMessage")
    public ResponseInfo sendeMessage(@RequestBody String phoneNumber) {
//        phoneNumber="18210081211";
        ResponseInfo  responseInfo=new ResponseInfo();
        Map<String, Object> resultMap = messageService.publicSendMessage(phoneNumber);
        responseInfo.setData(resultMap);
        System.out.println("hash:"+resultMap.get("hash"));
        System.out.println("tamp:"+resultMap.get("tamp"));
        return responseInfo;
    }

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 用户注册
     */
    @PostMapping(value = "toRegister", headers = "Accept=application/json")
        public ResponseInfo createUser(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response=new ResponseInfo();
        VUser user=new VUser();
        user.setPhoneNumber(requestMap.get("phoneNumber").toString());
        user.setPassword(requestMap.get("password").toString());

//       检查用户注册信息(手机号，密码，验证码)是否正常
        response = checkUserInfo(requestMap);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }

        //创建用户
        userService.creatUser(user);
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
//        VUser user = (VUser) requestMap.get("VUser");
        //用户注册手机号
        String phoneNumber = requestMap.get("phoneNumber").toString();
        //用户注册密码
        String password = requestMap.get("password").toString();
        //手机号码是否正规范
//        Boolean isRightPhone = false;
        //密码是否规范
        Boolean isRightPassword = false;

        //手机号码校验
//        手机号是否为空
        if (StringUtils.isBlank(phoneNumber)) {
            response.setCode(CODE_ERROR);
            response.setMessage("The phoneNumber can not be empty!");
            return response;
        }
        //校验前端传过来的手机号码是否是正确的，如果正确就继续，否则就返回格式错误
        Boolean isValidPhoneNumber = PhoneUtil.isNotValidChinesePhone(phoneNumber);
        if (!isValidPhoneNumber) {
            logger.error("===============校验手机号码："+phoneNumber+" 失败！");
            response.setCode(CODE_ERROR);
            response.setMessage("it's not correct phoneNumber!");
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

        //校验该用户是否已注册
        VUser user2 = new VUser();
        user2.setPhoneNumber(phoneNumber);
        user2.setPassword(password);

        if ( userService.queryUser(user2) != null) {
            response.setMessage("The user is exist!");
            response.setCode(CODE_ERROR);
            return response;
        }

        if ( userService.queryUser(user2) != null) {
            response.setMessage("The user is exist!");
            response.setCode(CODE_ERROR);
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

        response.setCode(CODE_SUCCESS);
        return response;
    }


}
