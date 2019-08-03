package com.tw.entity;

import lombok.Data;

/**
 * @author liutianwen
 * @Description: 设备视频
 * @date 2019年8月3日
 */
@Data
public class DeviceVideo extends BaseEntity{
    //  设备号
    private String deviceId;
    //  终端名称
    private String deviceName;
    //  视频名称
    private String warningVideoName;
    //  视频存放路径
    private String warningVideoPath;
    //  视频1  /视频缩略小图2 /视频缩略小图3
    private String fileType;
    //  告警视频小缩略图
    private String warningPictureLittle;
    //  告警视频大缩略图
    private String warningPictureBig;
    //  实时流(预留)
    private String liveStreaming;
    //  描述
    private String description;

    public DeviceVideo() {
        super();
    }

    public DeviceVideo(String deviceId, String deviceName, String warningVideoName, String warningVideoPath, String fileType, String warningPictureLittle, String warningPictureBig, String liveStreaming, String description) {
        super();
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.warningVideoName = warningVideoName;
        this.warningVideoPath = warningVideoPath;
        this.fileType = fileType;
        this.warningPictureLittle = warningPictureLittle;
        this.warningPictureBig = warningPictureBig;
        this.liveStreaming = liveStreaming;
        this.description = description;
    }
}
