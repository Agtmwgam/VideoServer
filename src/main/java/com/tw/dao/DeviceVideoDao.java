package com.tw.dao;

import com.tw.entity.DeviceVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liutianwen
 * @Description: 告警视频
 * @date 2019年8月3日
 */
@Mapper
@Repository
public interface DeviceVideoDao {

    String getWarningInfoDesc(@Param("serial")String serial,@Param("eventId")String eventId);

    List<DeviceVideo> getWarningInfoList(@Param("serial")String serial,@Param("pageSize")Integer pageSize, @Param("pageNo")Integer pageNo);

     int AddVideo(DeviceVideo video);
}
