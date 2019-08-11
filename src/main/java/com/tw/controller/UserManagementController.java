package com.tw.controller;

import com.tw.entity.VUser;
import com.tw.entity.common.ConstantParam;
import com.tw.service.VUserService;
import com.tw.util.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.tw.util.ResponseInfo.CODE_ERROR;
import static com.tw.util.ResponseInfo.CODE_SUCCESS;

/**
 * @Classname RegisterController
 * @Description 用户管理
 * @Date 2019/8/5 22:21
 * @Created by liutianwen
 */
@RestController
public class UserManagementController {

    @Autowired
    private VUserService userService;

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @param requestMap  phoneNumber
     * @Description 用户管理
     */
    @PostMapping(value = "/getUserInfo")
    public ResponseInfo findPassword(@RequestBody Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        VUser user = new VUser();

        String phoneNumber = requestMap.get("phoneNumber").toString();
        user.setPhoneNumber(phoneNumber);

        //模糊查询用户
        List<VUser> userList= userService.fuzzyQueryUser(user);
        response.setCode(CODE_SUCCESS);
        response.setData(userList);

        return response;
    }

    /**
     * @return
     * @Date 2019/8/5 22:21
     * @Created liutianwen
     * @Description 校验用户管理中输入的手机号是否正确
     */
    public ResponseInfo checkUserInfo(Map<String, Object> requestMap) {
        ResponseInfo response = new ResponseInfo();
        //用户注册手机号
        String phoneNumber = requestMap.get("phoneNumber").toString();
        //手机号码是否正规范
        Boolean isRightPhone = false;

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

        //校验该用户是否已注册
        VUser user2 = new VUser();
        user2.setPhoneNumber(phoneNumber);
        VUser returnUser = userService.queryUser(user2);
        if (StringUtils.isBlank(returnUser.getPhoneNumber())) {
            response.setCode(CODE_ERROR);
            response.setMessage("The user is not exist！");
            return response;
        }

        response.setCode(CODE_SUCCESS);
        return response;
    }


}
