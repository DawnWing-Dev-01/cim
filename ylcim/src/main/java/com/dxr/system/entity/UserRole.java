package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class UserRole extends AbstractEntity {

    private static final long serialVersionUID = 2277590032412131390L;

    private String userId;

    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
