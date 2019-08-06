package com.tw.convert;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.vUser;

/**
 * 功能: 将 vUser 类转换为 UserRoleDTO 类.
 *
 * Created by zhuoshouyi on 2019/8/5.
 */
public class vUser2UserRoleDTOConvert {

    public static UserRoleDTO convert(vUser user){

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserID(user.getUserID());
        userRoleDTO.setNickName(user.getNickName());
        userRoleDTO.setPhoneNumber(user.getPhoneNumber());

        return userRoleDTO;
    }


}
