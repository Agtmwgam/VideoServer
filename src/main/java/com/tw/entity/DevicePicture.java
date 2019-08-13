package com.tw.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liutianwen
 * @Description: 设备图片
 * @date 2019年8月3日
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DevicePicture extends BaseEntity{
    //    设备ID
    private String serial;
    //    密度分析图片名称
    private String densityPictureName;
    //    密度分析图片存放路径
    private String densityPicturePath;
    //    图片类型 1：原始灰度图 2：热力图
    private String fileType;

    public DevicePicture() {
        super();
    }

    public DevicePicture(String serial,String fileType, String densityPictureName, String densityPicturePath) {
        super();
        this.serial = serial;
        this.densityPictureName = densityPictureName;
        this.densityPicturePath = densityPicturePath;
        this.fileType = fileType;
    }
}
