package com.tw.dao;

import com.tw.entity.LoginMessage;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: haizhi
 * @Description:
 * @Date: 2019/8/11
 * @Param:
 * @return:
 */
public interface LoginMessageDao {

    // save
    @Transactional
    void saveLoginMessage(LoginMessage loginMessage);

    // 根据 serial 找到 device 的信息
    LoginMessage findBySerial(String serial);

    // 根据 serial 修改此设备的 isValid
    void updateIsValidBySerial(LoginMessage loginMessage);

    // 更新心跳信息
    void modifyLogin(LoginMessage loginMessage);
}
