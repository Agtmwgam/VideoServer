package com.tw.entity;


import lombok.Data;

/**
 * @author liutianwen
 * @Description: 设备图片
 * @date 2019年8月3日
 */
@Data
public class DevicePicture extends BaseEntity{
    //    设备ID
    private String serial;
    //    密度分析图片名称
    private String densityPictureName;
    //    密度分析图片存放路径
    private String densityPicturePath;

    public DevicePicture() {
        super();
    }

    public DevicePicture(String serial, String densityPictureName, String densityPicturePath) {
        super();
        this.serial = serial;
        this.densityPictureName = densityPictureName;
        this.densityPicturePath = densityPicturePath;
    }
}
