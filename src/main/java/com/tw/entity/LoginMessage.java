package com.tw.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
@Data
public class LoginMessage extends BaseEntity {

    // id
    private Integer loginId;

    // 帧标识
    private String frame;

    // 序列号
    private String serial;

    // 验证码
    private String deviceVerifyCode;

    // 设备型号
    private String deviceType;

    // 软件版本
    private String softVersion;

    // 生产日期
    private String productDate;

    // 随机数
    private String rand;

    // 更新时间
    private Date updateTime = new Date();
}
