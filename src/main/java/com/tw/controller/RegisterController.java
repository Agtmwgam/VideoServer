package com.tw.controller;

import com.tw.entity.DeviceGroup;
import com.tw.entity.RootInfo;
import com.tw.entity.UserDeviceGroupRelate;
import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.*;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
public class RegisterController {

    @Autowired
    private VUserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private DevGroupService devGroupService;

    @Autowired
    private UserDeviceGroupRelateService userDeviceGroupRelateService;

    @Autowired
    private RootInfoService rootInfoService;

    //声明一个Logger，这个是static的方式
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/sendMessage")
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
     * @param  requestMap phoneNumber password msgNum hash tamp
     * @Description 用户注册
     */
    @PostMapping(value = "/toRegister", headers = "Accept=application/json")
    @Transactional
        public ResponseInfo createUser(@RequestBody Map<String,Object> requestMap) {
        ResponseInfo response=new ResponseInfo();
        VUser user=new VUser();
        user.setPhoneNumber(requestMap.get("phoneNumber").toString());
        user.setPassword(requestMap.get("password").toString());
        user.setNickName(requestMap.get("nickName").toString());

//       检查用户注册信息(手机号，密码，验证码)是否正常
        response = checkUserInfo(requestMap);
        if (response.getCode() == CODE_ERROR) {
            return response;
        }

        //创建用户
        int isAdd = userService.creatUser(user);
        //user=userService.queryUser(user);
//        给客户添加默认分组
        DeviceGroup deviceGroup =new DeviceGroup();
        deviceGroup.setDeviceGroupName(ConstantParam.MY_DEVICE_GROUP);
        int isAddUser = devGroupService.addDevGroup(deviceGroup);
        if (isAddUser > 0) {
            logger.warn("======添加用户" + user.getNickName() + "成功！");
        }
        //添加关联关系
        if (isAdd > 0) {
            //添加到自己的分组中
            UserDeviceGroupRelate userDeviceGroupRelate = new UserDeviceGroupRelate();
            userDeviceGroupRelate.setDeviceGroupId(deviceGroup.getDeviceGroupId());
            userDeviceGroupRelate.setUserId(user.getUserID());
            int isAddRelate = userDeviceGroupRelateService.addUserDeviceGroupRelate(userDeviceGroupRelate);
            if (isAddRelate ==1) {
                response.setCode(ResponseInfo.CODE_SUCCESS);
                response.setMessage("add default deviceGroup success!");
            } else {
                response.setCode(ResponseInfo.CODE_ERROR);
                response.setMessage("add default deviceGroup failed!");
            }
        } else {
            response.setCode(ResponseInfo.CODE_ERROR);
            response.setMessage("add default deviceGroup failed!");
        }
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
        //Boolean isValidPhoneNumber = PhoneUtil.isNotValidChinesePhone(phoneNumber);
        //只做长度判断
        Boolean isValidPhoneNumber = (phoneNumber.length() == 11);
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
        RootInfo rootInfo=new RootInfo();
        rootInfo.setRootPhone(phoneNumber);
        if(rootInfoService.getRootInfo(rootInfo).size()!=0){
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
            response.setCode(CODE_ERROR);
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
