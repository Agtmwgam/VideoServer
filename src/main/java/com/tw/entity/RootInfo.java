package com.tw.entity;

/**
 * @Author: John
 * @Description: 管理员信息
 * @Date:  2019/8/11 14:23
 * @param: null
 * @return:
 */
public class RootInfo extends BaseEntity {

    private String rootPhone;     //手机号码
    private String loginPassword;   //登录密码（备用）
    private String secondPassword;  //二级密码
    private String authority;       //权限
    private String description;     //备注


    public String getRootPhone() {
        return rootPhone;
    }

    public void setRootPhone(String rootPhone) {
        this.rootPhone = rootPhone;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RootInfo{" +
                "rootPhone='" + rootPhone + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", secondPassword='" + secondPassword + '\'' +
                ", authority='" + authority + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
