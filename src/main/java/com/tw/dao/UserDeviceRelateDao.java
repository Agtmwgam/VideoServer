package com.tw.dao;

import com.tw.entity.UserDeviceRelate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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


    // 逻辑删除用户
    public Integer delUserDevice(UserDeviceRelate udr);

}
