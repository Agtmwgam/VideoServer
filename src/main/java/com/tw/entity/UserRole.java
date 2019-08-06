package com.tw.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liutianwen
 * @Description:
 * @date 2019年8月3日
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class UserRole extends BaseEntity{
    //角色名称
    private long roleName;
    //  角色ID
    private long roleId;
    //  用户ID
    private long userId;
    //  描述
    private String description;

    public UserRole() {
        super();
    }

    public UserRole(long roleName, long roleId, long userId, String description) {
        super();
        this.roleName = roleName;
        this.roleId = roleId;
        this.userId = userId;
        this.description = description;
    }
}
