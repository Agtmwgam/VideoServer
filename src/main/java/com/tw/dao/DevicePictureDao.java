package com.tw.dao;

import com.tw.entity.DevicePicture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liutianwen
 * @Description:密度图片
 * @date 2019年8月3日
 */
@Mapper
@Repository
public interface DevicePictureDao {

    List<DevicePicture> getDensityPicture(String serail);

    List<String> getDensityPictureData(String serail);


    int AddPicture(DevicePicture devicePicture);

}
