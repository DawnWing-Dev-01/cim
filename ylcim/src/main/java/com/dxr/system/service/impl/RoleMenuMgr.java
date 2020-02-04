package com.dxr.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.system.dao.RoleMenuDao;
import com.dxr.system.entity.MenuInfo;
import com.dxr.system.entity.RoleMenu;
import com.dxr.system.service.api.IRoleMenu;

/**
 * @author w.xL
 *
 */
public class RoleMenuMgr extends BasalMgr<RoleMenu> implements IRoleMenu {

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public String saveRoleMenu(String roleId, List<String> menuIds)
            throws ServiceException {
        // 先删除原来的关系
        deleteRoleMenu(roleId);

        // 再构建立role和menu的关系
        if (!(menuIds != null && !menuIds.isEmpty())) {
            throw new ServiceException("menuIds is null...");
        }
        RoleMenu rm = null;
        for (String menuId : menuIds) {
            rm = new RoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            roleMenuDao.save(rm);
        }
        return new Message("授权成功!").toString();
    }

    @Override
    public String findMenuIdByRoleId(String roleId) throws ServiceException {
        List<MenuInfo> menuIds = roleMenuDao.findMenuListByRoleId(roleId);
        return JSONArray.toJSONString(menuIds);
    }

    @Override
    public void saveRoleMenu(RoleMenu roleMenu) throws ServiceException {
        roleMenuDao.save(roleMenu);
    }

    @Override
    public void deleteRoleMenu(String roleId) throws ServiceException {
        roleMenuDao.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public RoleMenu getDbObject(String id) throws ServiceException {
        return roleMenuDao.get(id);
    }
}
