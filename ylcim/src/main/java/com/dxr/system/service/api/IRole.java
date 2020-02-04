package com.dxr.system.service.api;

import java.util.Map;
import java.util.Set;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.RoleInfo;

/**
 * @author w.xL
 */
public interface IRole extends IBasalMgr<RoleInfo> {

    /**
     * 获取角色列表, EasyUI格式
     * @param page
     * @param rows
     * @param roleInfo
     * @return
     * @throws Exception
     */
    public String getRolePage(int page, int rows, RoleInfo roleInfo)
            throws ServiceException;

    /**
     * 保存角色
     * @param roleInfo
     * @return
     * @throws ServiceException
     */
    public String saveRole(RoleInfo roleInfo) throws ServiceException;
    
    /**
     * 更新角色
     * @param roleInfo
     * @return
     * @throws ServiceException
     */
    public String updateRole(RoleInfo roleInfo) throws ServiceException;
    
    /**
     * 删除角色信息
     * @param roleId
     * @return
     * @throws ServiceException
     */
    public String deleteRole(String roleId) throws ServiceException;
    
    /**
     * 获取详细信息
     * @param roleId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String roleId) throws ServiceException;

    /**
     * 根据userId获取角色以及资源信息
     * @param userId
     * @return
     * @throws ServiceException
     */
    public Map<String, Set<String>> findResourceMapByUserId(String userId)
            throws ServiceException;
}
