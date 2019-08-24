package com.tw.convert;


import com.tw.dto.UserDeviceDTO;
import com.tw.entity.VUser;

/**
 * @Date 2019/8/5 22:21
 * @Created liutianwen
 * @Description 用户中间转换类
 */
public class Vuser2UserDTOConvert {

    public static UserDeviceDTO convert(VUser user) {
        UserDeviceDTO userDeviceDTO = new UserDeviceDTO();
        userDeviceDTO.setUserID(user.getUserID());
        userDeviceDTO.setPhoneNumber(user.getPhoneNumber());
        userDeviceDTO.setNickName(user.getNickName());
        userDeviceDTO.setPassword(user.getPassword());
        return userDeviceDTO;
    }
}
