package com.dxr.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.dao.UserDao;
import com.dxr.system.dao.UserRoleDao;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.entity.UserRole;
import com.dxr.system.service.api.IUserRole;

/**
 * @description: <用户角色关系服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-19
 */
public class UserRoleMgr extends BasalMgr<UserRole> implements IUserRole {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public UserRole getDbObject(String id) throws ServiceException {
        return userRoleDao.get(id);
    }

    @Override
    public String findRoleIds(String userId) throws ServiceException {
        List<String> roleIds = userRoleDao.selectRoleIds(userId);
        return JSONArray.toJSONString(roleIds);
    }

    @Override
    public String saveUserRole(String userId, List<String> roleIds)
            throws ServiceException {
        // 先删除原来的关系
        userRoleDao.deleteUserRoleByUserId(userId);

        // 再构建立role和menu的关系
        if (!(roleIds != null && !roleIds.isEmpty())) {
            throw new ServiceException("roleIds is null...");
        }
        UserRole ur = null;
        for (String roleId : roleIds) {
            ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            userRoleDao.save(ur);
        }

        // 清除授权缓存
        UserInfo userInfo = userDao.get(userId);
        SecurityUser.clearCachedAuthorizationInfo(userInfo.getUsername());
        return new Message("授权成功!").toString();
    }

    @Override
    public String deleteUserRole(String userId) throws ServiceException {
        userRoleDao.deleteUserRoleByUserId(userId);

        // 清除授权缓存
        UserInfo userInfo = userDao.get(userId);
        SecurityUser.clearCachedAuthorizationInfo(userInfo.getUsername());
        return new Message("解除成功!").toString();
    }

}
