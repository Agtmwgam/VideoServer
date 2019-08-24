package com.tw.convert;


import com.tw.dto.DeviceAndUserNameDTO;
import com.tw.dto.UserDeviceDTO;
import com.tw.entity.Device;
import com.tw.entity.VUser;

import java.util.Date;

/**
 * @Date 2019/8/5 22:21
 * @Created liutianwen
 * @Description   Device设备实体类 转换成 DeviceAndUserNameDTO设备和所属用户名实体类
 */
public class Device2DeviceAndUserNameDTOConvert {

    public static DeviceAndUserNameDTO convert(Device device) {
        DeviceAndUserNameDTO deviceAndUserNameDTO=new DeviceAndUserNameDTO();
        deviceAndUserNameDTO.setDeviceId(device.getDeviceId());
        deviceAndUserNameDTO.setDeviceName(device.getDeviceName());
        deviceAndUserNameDTO.setSerial(device.getSerial());
        deviceAndUserNameDTO.setDeviceVerifyCode(device.getDeviceVerifyCode());
        deviceAndUserNameDTO.setDeviceType(device.getDeviceType());
        deviceAndUserNameDTO.setSoftVersion(device.getSoftVersion());
        deviceAndUserNameDTO.setProductDate(device.getProductDate());
        deviceAndUserNameDTO.setDeviceStatus(device.getDeviceStatus());
        deviceAndUserNameDTO.setIsOnline(device.getIsOnline());
        deviceAndUserNameDTO.setIpAddress(device.getIpAddress());
        deviceAndUserNameDTO.setNewBeatTime(device.getNewBeatTime());
        deviceAndUserNameDTO.setOldBeatTime(device.getOldBeatTime());
        deviceAndUserNameDTO.setIsValid(device.getIsValid());

        return deviceAndUserNameDTO;
    }
}
