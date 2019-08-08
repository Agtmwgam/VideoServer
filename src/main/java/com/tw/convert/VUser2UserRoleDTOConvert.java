package com.tw.convert;

import com.tw.dto.UserRoleDTO;
import com.tw.entity.VUser;

/**
 * 功能: 将 vUser 类转换为 UserRoleDTO 类.
 *
 * Created by zhuoshouyi on 2019/8/5.
 */
public class VUser2UserRoleDTOConvert {

    public static UserRoleDTO convert(VUser vUser){

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserID(vUser.getUserID());
        userRoleDTO.setNickName(vUser.getNickName());
        userRoleDTO.setPhoneNumber(vUser.getPhoneNumber());

        return userRoleDTO;
    }


}
