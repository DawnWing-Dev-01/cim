package com.dxr.system.action;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.service.api.IUserRole;

/**
 * @description: <用户角色关系Action>
 * @author: w.xL
 * @date: 2018-3-19
 */
public class UserRoleAction extends BasalAction {

    private static final long serialVersionUID = -7039777855665558273L;

    @Autowired
    private IUserRole userRoleMgr;

    private String[] roleIds;

    /**
     * 根据用户获取角色RoleId列表
     */
    public void findRoleIdByUserId() {
        try {
            String result = userRoleMgr.findRoleIds(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserRoleAction.saveRoleMenu() is error...", e);
            super.bracket();
        }
    }

    /**
     * 保存角色用户关系
     */
    public void saveUserRole() {
        try {
            String result = userRoleMgr.saveUserRole(object,
                    Arrays.asList(roleIds));
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserRoleAction.saveRoleMenu() is error...", e);
            super.writeToView(new Message(false,
                    ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                    .toString());
        }
    }
    
    /**
     * 解除用户角色关系
     */
    public void deleteUserRole() {
        try {
            String result = userRoleMgr.deleteUserRole(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserRoleAction.deleteRoleMenu() is error...", e);
            super.operateException();
        }
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
}
