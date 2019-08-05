package com.tw.entity;

import lombok.Data;

/**
 * @author liutianwen
 * @Description: 用户详情
 * @date 2019年8月3日
 */
@Data
public class vUser extends BaseEntity {
    //    用户ID
    private Integer userID;
    //    用户昵称
    private String nickName;
    //    账户密码
    private String password;
    //    手机号
    private String phoneNumber;

    public vUser() {
        super();
    }

    public vUser(Integer userID, String nickName, String account, String password, String phone) {
        super();
        this.userID = userID;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phone;
    }
}

