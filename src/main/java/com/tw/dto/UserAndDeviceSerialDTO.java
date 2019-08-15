package com.tw.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Date 2019/8/12 22:21
 * @Created liutianwen
 * @Description DTO
 * @return
 */
@Data
@EqualsAndHashCode()
public class UserAndDeviceSerialDTO {

    //    用户ID
    private Integer userId;
    //    用户昵称
    private String nickName;
    //    账户密码
    private String password;
    //    手机号
    private String phoneNumber;
    //    设备号数组
    private List<String> serialList;

    public UserAndDeviceSerialDTO() {
        super();
    }

    public UserAndDeviceSerialDTO(Integer userID, String nickName, String account, String password, String phone,List<String> serialList) {
        super();
        this.userId = userID;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phone;
        this.serialList = serialList;
    }

    public void setUserID(Integer userID) {
        this.userId=userID;
    }
}
