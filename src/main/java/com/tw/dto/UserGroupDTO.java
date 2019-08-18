package com.tw.dto;

import com.tw.entity.VUser;
import lombok.Data;

import java.util.List;

/**
 * @Author: John
 * @Description: 用户关联组中间表 (根据用户id返回名下的所有设备会到这个类)
 * @Date:  2019/8/14 12:53
 * @param: null
 * @return:
 */
@Data
public class UserGroupDTO {

    private VUser user;                         //原用户的基本信息
    private List<DevGroupDTO> devGroupList;    //设备组集合
}