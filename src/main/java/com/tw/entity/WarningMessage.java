package com.tw.entity;

import lombok.Data;

/**
 * @Author: zhuoshouyi
 * @Description:
 * @Date: 2019/8/7
 * @Param:
 * @return:
 */
@Data
public class WarningMessage extends BaseEntity {

    // id
    private Integer warnId;

    // 帧标识
    private String frame;

    // 消息编号
    private String mesNo;

    // 时间
    private String mesDate;

    // 组名
    private String groupName;

    // 设备名
    private String deviceName;

    // 型号
    private String deviceModel;

    // 序列号
    private String serial;

    // 视频分辨率
    private String videoResolution;

    // 目标位置
    private String targetLocation;

    // 设备状态
    private String exeStatus;

    // IP来源
    private String ip;

    // 是否生效
    private char isValid;

    // 创建时间
    private String createTime;
}
