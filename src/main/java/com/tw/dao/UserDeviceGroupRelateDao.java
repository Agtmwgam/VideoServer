package com.tw.dao;

import com.tw.entity.UserDeviceGroupRelate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeviceGroupRelateDao {

    List<UserDeviceGroupRelate> getGroupListByUserId(int userId);

}
