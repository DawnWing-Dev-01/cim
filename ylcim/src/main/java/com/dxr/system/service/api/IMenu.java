package com.dxr.system.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.MenuInfo;

/**
 * @author w.xL
 */
public interface IMenu extends IBasalMgr<MenuInfo> {

    /**
     * 获取导航
     * @return
     * @throws ServiceException
     */
    public String getNav() throws ServiceException;

    /**
     * 获取菜单树
     * @return
     * @throws ServiceException
     */
    public String getTree() throws ServiceException;

    /**
     * 获取菜单树表格
     * @return
     * @throws ServiceException
     */
    public String getTreeGrid() throws ServiceException;

    /**
     * @param menuInfo
     * @return
     * @throws ServiceException
     */
    public String saveMenu(MenuInfo menuInfo) throws ServiceException;

    /**
     * @param menuInfo
     * @return
     * @throws ServiceException
     */
    public String updateMenu(MenuInfo menuInfo) throws ServiceException;

    /**
     * @param menuId
     * @param isLeaf
     * @return
     * @throws ServiceException
     */
    public String updateIsLeaf(String menuId, Integer isLeaf)
            throws ServiceException;

    /**
     * @param menuId
     * @return
     * @throws ServiceException
     */
    public String deleteMenu(String menuId) throws ServiceException;

    /**
     * @param menuId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String menuId) throws ServiceException;
}
