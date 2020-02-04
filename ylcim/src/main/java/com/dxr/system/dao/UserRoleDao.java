package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.UserRole;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class UserRoleDao extends BasalDao<UserRole> {

    /**
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> selectRoleIds(String userId) {
        String hql = "select ur.roleId from UserRole ur where ur.userId = :userId ";
        Query query = super.createQuery(hql);
        query.setParameter("userId", userId);
        return query.list();
    }

    /**
     * 根据userId删除角色用户关系
     * @param roleId
     */
    public Integer deleteUserRoleByUserId(String userId) {
        String hql = "delete UserRole ur where ur.userId = :userId ";
        Query query = super.createQuery(hql.toString());
        query.setParameter("userId", userId);
        return query.executeUpdate();
    }
}
