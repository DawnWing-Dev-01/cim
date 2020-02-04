package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.RoleInfo;
import com.dxr.system.service.api.IRole;

/**
 * @author w.xL
 */
public class RoleAction extends BasalAction {

    private static final long serialVersionUID = -3613399842152383654L;

    @Autowired
    private IRole roleMgr;

    private RoleInfo roleInfo;

    @Override
    public String formView() {
        if ("authorize".equals(viewType)) {
            return "authorize";
        }
        if ("choose".equals(viewType)) {
            return "chooseView";
        }
        return super.formView();
    }

    /**
     * 获取角色列表
     */
    public void getRolePage() {
        try {
            String result = roleMgr.getRolePage(page, rows, roleInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getRolePage", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存角色
     */
    public void saveRole() {
        try {
            String result = roleMgr.saveRole(roleInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("RoleAction.saveRole() is error...", e);
            if (e.getErrorCode() == ErrorCode.ERRCODE_DATA_REPEAT) {
                super.writeToView(new Message(false,
                        ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                        .toString());
                return;
            }
            super.operateException();
        }
    }

    /**
     * 更新角色
     */
    public void updateRole() {
        try {
            String result = roleMgr.updateRole(roleInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("RoleAction.updateRole() is error...", e);
            if (e.getErrorCode() == ErrorCode.ERRCODE_DATA_REPEAT) {
                super.writeToView(new Message(false,
                        ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                        .toString());
                return;
            }
            super.operateException();
        }
    }

    /**
     * 删除角色
     */
    public void deleteRole() {
        try {
            String result = roleMgr.deleteRole(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("RoleAction.deleteRole() is error...", e);
            super.operateException();
        }
    }

    /**
     * 查看详情
     */
    public void getDetails() {
        try {
            String result = roleMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("RoleAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }
}
