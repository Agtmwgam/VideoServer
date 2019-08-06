package com.tw.dao;

import com.tw.entity.DeviceVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Component
@Mapper
public interface DeviceVideoDao {

    String getWarningInfoDesc(String serial,String eventId);

    List<DeviceVideo> getWarningInfoList(String serial);
}
