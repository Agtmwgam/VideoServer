package com.tw.dto;

import com.tw.entity.DeviceGroup;
import com.tw.entity.Device;
import lombok.Data;

import java.util.List;

/**
 * @Author: John
 * @Description: 根据用户id返回名下的所有设备会到这个类
 * @Date:  2019/8/14 22:44
 * @param: null
 * @return:
 */
@Data
public class DevGroupDTO {

    private DeviceGroup deviceGroup;     //原本的的组的信息
    private List<Device> deviceList;    //设备组集合
}
