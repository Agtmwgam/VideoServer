package com.tw.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: John
 * @Description:设备组类
 * @Date:  2019/8/10 21:20
 * @param: null
 * @return:
 */
@Data
public class DevGroup extends BaseEntity {
    private int groupId;             //自增主键
    private String groupName;       //组名


    @Override
    public String toString() {
        return "DevGroup{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}