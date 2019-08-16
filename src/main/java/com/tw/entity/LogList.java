package com.tw.entity;

import lombok.Data;

/**
 * @Author: haizhi
 * @Description: 日志文件实体类
 * @Date: 2019/8/16
 * @Param:
 * @return:
 */
@Data
public class LogList extends BaseEntity {

    // id
    private Integer logId;

    // 序列号
    private String serial;

    // 日志编号
    private String logNum;

    // ip地址
    private String ipAddress;

    // 日志文件名称
    private String logName;

    // 日志生成时间
    private String logTime;

    // 日志路径
    private String logPath;

}
