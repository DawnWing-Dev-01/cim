package com.dxr.system.action;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.service.api.IRoleMenu;

/**
 * @description: <角色资源关系Action层>
 * @author: w.xL
 * @date: 2018-3-19
 */
public class RoleMenuAction extends BasalAction {

    private static final long serialVersionUID = -3116270659143815120L;

    @Autowired
    private IRoleMenu roleMenuMgr;

    private String[] menuIds;

    /**
     * 保存角色
     */
    public void saveRoleMenu() {
        try {
            String result = roleMenuMgr.saveRoleMenu(object,
                    Arrays.asList(menuIds));
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("RoleMenuAction.saveRoleMenu() is error...", e);
            super.writeToView(new Message(false,
                    ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                    .toString());
        }
    }
    
    /**
     * 根据角色获取资源menuId列表
     */
    public void findMenuIdByRoleId(){
        try {
            String result = roleMenuMgr.findMenuIdByRoleId(object);
            super.writeToView(result);
        } catch (Exception e) {
            super.bracket();
        }
    }

    public String[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String[] menuIds) {
        this.menuIds = menuIds;
    }
}
