package com.tw.dao;

import com.tw.entity.VUser;
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
public interface VUserDao {

    //新建一个用户
    @Transactional
    void creatUser(VUser user);

    //查找用户
    VUser queryUser(VUser user);

}
