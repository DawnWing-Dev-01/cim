package com.dxr.system.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.UserRole;

/**
 * @description: <用户角色关系服务接口层>
 * @author: w.xL
 * @date: 2018-3-19
 */
public interface IUserRole extends IBasalMgr<UserRole>{

    /**
     * 根据用户id查找角色Id集合
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String findRoleIds(String userId) throws ServiceException;

    /**
     * 保存用户角色关系
     * @param userId
     * @param roleIds
     * @return
     * @throws ServiceException
     */
    public String saveUserRole(String userId, List<String> roleIds)
            throws ServiceException;

    /**
     * 删除用户角色关系
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String deleteUserRole(String userId) throws ServiceException;
}
