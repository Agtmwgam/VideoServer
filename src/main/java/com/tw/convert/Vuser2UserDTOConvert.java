package com.tw.convert;


import com.tw.dto.UserDeviceDTO;
import com.tw.entity.VUser;

//过来用户的敏感信息
public class Vuser2UserDTOConvert {

    public static UserDeviceDTO convert(VUser user) {
        UserDeviceDTO userDeviceDTO = new UserDeviceDTO();
        userDeviceDTO.setUserID(user.getUserID());
        userDeviceDTO.setPhoneNumber(user.getPhoneNumber());
        userDeviceDTO.setNickName(user.getNickName());
        return userDeviceDTO;
    }
}
