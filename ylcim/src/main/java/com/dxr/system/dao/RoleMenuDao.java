package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.MenuInfo;
import com.dxr.system.entity.RoleMenu;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class RoleMenuDao extends BasalDao<RoleMenu> {

    /**
     * @param roleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MenuInfo> findMenuListByRoleId(String roleId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select mi from RoleInfo ri, RoleMenu rm, MenuInfo mi ");
        hql.append("where 1 = 1");
        hql.append("and ri.id = rm.roleId ");
        hql.append("and rm.menuId = mi.id ");
        hql.append("and ri.id = :roleId");
        Query query = super.createQuery(hql.toString());
        query.setParameter("roleId", roleId);
        return query.list();
    }

    /**
     * 根据roleId删除角色资源关系
     * @param roleId
     */
    public Integer deleteRoleMenuByRoleId(String roleId) {
        String hql = "delete RoleMenu rm where rm.roleId = :roleId ";
        Query query = super.createQuery(hql.toString());
        query.setParameter("roleId", roleId);
        return query.executeUpdate();
    }
}
