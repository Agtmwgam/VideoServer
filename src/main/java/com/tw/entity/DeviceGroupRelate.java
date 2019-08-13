package com.tw.entity;

import lombok.Data;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
@Data
public class DeviceGroupRelate extends BaseEntity {

    // id
    private Integer id;

    // 设备自增id
    private Integer deviceId;

    // 设备组自增id
    private Integer groupId;

}
