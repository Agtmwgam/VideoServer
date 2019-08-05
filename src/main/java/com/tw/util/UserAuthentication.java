package com.tw.util;

import com.auth0.jwt.interfaces.Claim;
import com.tw.dto.UserRoleDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 功能: 解析 httpRequest 请求中的头文件,获取里面携带的用户信息
 *
 */
@Slf4j
public class UserAuthentication {

    public static UserRoleDTO authentication(HttpServletRequest httpServletRequest){

        log.info("【认证】识别用户身份,判断权限");
        Map<String, Claim> claimMap = JwtUtil.getToken(httpServletRequest);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserID(claimMap.get("userId") == null? null : claimMap.get("userId").asInt());
        log.info("【认证】userId:" + (claimMap.get("userId") == null? null : claimMap.get("userId").asInt()));

        userRoleDTO.setNickName(claimMap.get("nickName") == null? null : claimMap.get("nickName").asString());
        log.info("【认证】nickName:" + (claimMap.get("nickName") == null? null : claimMap.get("nickName").asString()));

        userRoleDTO.setPhoneNumber(claimMap.get("phoneNumber") == null? null : claimMap.get("phoneNumber").asString());
        log.info("【认证】phoneNumber:" + (claimMap.get("phoneNumber") == null? null : claimMap.get("phoneNumber").asString()));

        userRoleDTO.setIsRoot(claimMap.get("isRoot") == null? null : claimMap.get("isRoot").asString());
        log.info("【认证】isRoot:" + (claimMap.get("isRoot") == null? null : claimMap.get("isRoot").asString()));

        return userRoleDTO;
    }
}
