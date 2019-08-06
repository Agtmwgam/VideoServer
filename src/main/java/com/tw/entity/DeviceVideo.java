package com.tw.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liutianwen
 * @Description: 设备视频
 * @date 2019年8月3日
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DeviceVideo extends BaseEntity{
    private int id;
    //  事件Id(由deviceSerivalNum和事件戳组成)
    private String eventId;
    //  设备号
    private String serial;

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
    //  告警信息
    private String warningMessage;
    //  告警事件
    private String warningTime;

    //  实时流(预留)
    private String liveStreaming;
    //  描述
    private String description;



    public DeviceVideo() {
        super();
    }

    public DeviceVideo(String serial,String eventId,String warningVideoName,
                       String warningVideoPath, String fileType, String warningPictureLittle, String warningPictureBig,
                       String warningMessage, String warningTime,
                       String liveStreaming, String description) {
        super();
        this.serial = serial;
        this.eventId = eventId;
        this.warningVideoName = warningVideoName;
        this.warningVideoPath = warningVideoPath;
        this.fileType = fileType;
        this.warningPictureLittle = warningPictureLittle;
        this.warningPictureBig = warningPictureBig;
        this.warningMessage = warningMessage;
        this.warningTime = warningTime;
        this.liveStreaming = liveStreaming;
        this.description = description;
    }
}
