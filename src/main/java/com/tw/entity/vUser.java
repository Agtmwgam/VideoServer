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
    private String userID;
    //    用户昵称
    private String name;
    //    用户账号
    private String account;
    //    账户密码
    private String password;
    //    手机号
    private String phone;

    public vUser() {
        super();
    }

    public vUser(String userID, String name, String account, String password, String phone) {
        super();
        this.userID = userID;
        this.name = name;
        this.account = account;
        this.password = password;
        this.phone = phone;
    }
}

