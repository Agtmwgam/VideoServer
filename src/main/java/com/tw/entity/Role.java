package com.tw.entity;

import lombok.Data;

/**
 * @author liutianwen
 * @Description: 用户角色
 * @date 2019年8月3日
 */
@Data
public class Role extends BaseEntity{

    //  角色ID
    private String roleId;
    //  角色名
    private String roleName;
    //  描述
    private String description;

    public Role() {
        super();
    }

    public Role(String roleId, String roleName, String description) {
        super();
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
    }
}
