package com.tw.dto;

import lombok.Data;

/**
 * Created by haizhi on 2019/8/5.
 */
@Data
public class UserRoleDTO {

    //    用户ID
    private Integer userID;

    //    用户昵称
    private String nickName;

    //    手机号
    private String phoneNumber;

    //    用户身份
    private String isRoot;

}
