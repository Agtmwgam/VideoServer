package com.tw.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liutianwen
 * @Description: 用户详情
 * @date 2019年8月3日
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class VUser extends BaseEntity {

    private Integer userID;     //用户ID
    private String nickName;    //用户昵称
    private String password;    //账户密码
    private String phoneNumber; //手机号


    public VUser() {
        super();
    }

    public VUser(Integer userID, String nickName, String account, String password, String phone) {
        super();
        this.userID = userID;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phone;
    }
}

