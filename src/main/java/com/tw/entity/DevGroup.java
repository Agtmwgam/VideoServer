package com.tw.entity;

/**
 * @Author: John
 * @Description:设备组类
 * @Date:  2019/8/10 21:20
 * @param: null
 * @return:
 */
public class DevGroup extends BaseEntity {
    private int groupId;             //自增主键
    private String groupName;       //组名


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "DevGroup{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}