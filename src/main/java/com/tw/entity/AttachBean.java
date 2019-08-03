package com.tw.entity;


/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/3
 * @param: null
 * @return:
 */

public class AttachBean {
    private String aId;//附件ID
    private String aName;//附件名称
    private String aType;//附件类型
    public String getaType() {
        return aType;
    }
    public void setaType(String aType) {
        this.aType = aType;
    }



    public String getaId() {
        return aId;
    }
    public void setaId(String aId) {
        this.aId = aId;
    }
    public String getaName() {
        return aName;
    }
    public void setaName(String aName) {
        this.aName = aName;
    }
    @Override
    public String toString() {
        return "AttachBean [aID=" + aId + ", aName=" + aName + ", aType=" + aType + "]";
    }
}
