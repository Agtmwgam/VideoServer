package com.tw.dao;

import com.tw.entity.User;
import com.tw.entity.UserDeviceRelate;
import com.tw.entity.VUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * @author liutianwen
 * @Description:用户DAO
 * @date 2019年8月3日
 */
@Mapper
@Repository
public interface UserDeviceRelateDao {

    //新建一个用户
    @Transactional
    void creatUserDevice(UserDeviceRelate udr);


    // 逻辑删除用户下的设备
    int delUserDevice(Map<String, Object> param);


    /**
     * @author liutianwen
     * @desc  增加用户设备
     * @param userDeviceRelate
     * @return
     */
    void addUserDevice(UserDeviceRelate userDeviceRelate);

    //	根据deviceId找到对应的user
    VUser getUserByDeviceID(@Param("deviceId") Integer deviceId);

}
