package com.tw.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhuoshouyi
 * @Description:
 * @Date: 2019/8/7
 * @Param:
 * @return:
 */
@Data
public class BeatMessage extends BaseEntity {

    // id
    private Integer beatId;

    // 帧标识
    private String frame;

    // 消息编号
    private String mesNo;

    // 时间
    private Date mesDate;

    // 组名
    private String groupName;

    // 设备名
    private String deviceName;

    // 型号
    private String deviceModel;

    // 序列号
    private String serial;

    // 设备状态
    private char exeStatus;

    // IP来源
    private String ip;


}
