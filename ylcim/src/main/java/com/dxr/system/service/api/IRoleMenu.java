package com.dxr.system.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.RoleMenu;

/**
 * @author w.xL
 */
public interface IRoleMenu extends IBasalMgr<RoleMenu> {

    /**
     * @param roleId
     * @param menuIds
     * @return
     * @throws ServiceException
     */
    public String saveRoleMenu(String roleId, List<String> menuIds)
            throws ServiceException;

    /**
     * @param roleMenu
     * @throws ServiceException
     */
    public void saveRoleMenu(RoleMenu roleMenu) throws ServiceException;

    /**
     * @param roleId
     * @throws ServiceException
     */
    public void deleteRoleMenu(String roleId) throws ServiceException;

    /**
     * @param roleId
     * @return
     * @throws ServiceException
     */
    public String findMenuIdByRoleId(String roleId) throws ServiceException;
}
