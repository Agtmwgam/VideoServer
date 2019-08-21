package com.tw.dao;

import com.tw.entity.Device;
import com.tw.entity.VUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author liutianwen
 * @Description:用户DAO
 * @date 2019年8月3日
 */
@Mapper
@Repository
public interface VUserDao {

    //新建一个用户
    @Transactional
    int creatUser(VUser user);

    //查找用户
    VUser queryUser(VUser user);

    //模糊查找用户
    List<VUser> fuzzyQueryUser(VUser user);

    //模糊查找用户分页
    List<VUser> fuzzyQueryUserByPage(Map<String, Object> param);

    //更新客户
    @Transactional
    Integer modifyUser(VUser user);

    // 逻辑删除用户
    public Integer delUser(VUser user);

    //根据条件查总数
    int getTotleOfUserAndDevice(Map<String, Object> param);

    List<Device> getDeviceByUserId(Integer userID);
}
