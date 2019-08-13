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
public class UserDeviceRelate extends BaseEntity {
    //    用户ID
    private Integer userId;
    //    设备ID
    private Integer deviceId;



}

