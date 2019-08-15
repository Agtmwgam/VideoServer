package com.tw.convert;

import com.tw.dto.UserAndDeviceSerialDTO;
import com.tw.entity.VUser;
import java.util.List;

/**
 * @return
 * @Date 2019/8/12 22:21
 * @Created liutianwen
 * @Description 转换成DTO
 */
public class VUserToVUserAndDeviceDTOConvert {

    public static UserAndDeviceSerialDTO convert(VUser vUser,List<String> serialList){
        UserAndDeviceSerialDTO uadDTO = new UserAndDeviceSerialDTO();
        uadDTO.setUserID(vUser.getUserID());
        uadDTO.setNickName(vUser.getNickName());
        uadDTO.setPhoneNumber(vUser.getPhoneNumber());
        uadDTO.setPassword(vUser.getPassword());
        uadDTO.setSerialList(serialList);
        return uadDTO;
    }

}
