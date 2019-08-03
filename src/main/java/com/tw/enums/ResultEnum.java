package com.tw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回给前端提示的一个消息
 * 定义异常
 *
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {

    PARAM_ERROR(1, "参数不正确"),
    RESULT_NOT_FOUND(2, "结果不存在"),
    PARAM_NOT_AUTHENTICATION(10, "请求无 Authorization"),
    USER_NOT_FOUND(401, "登陆失效或用户不存在"),
    USER_HAVE_NOT_PRIVILEGE(403, "用户无查看或修改权限"),
    ;

    private Integer code;

    private String desc;
}
