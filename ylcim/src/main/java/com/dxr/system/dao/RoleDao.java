package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.RoleInfo;

/**
 * @author w.xL
 */
public class RoleDao extends BasalDao<RoleInfo> {

    /**
     * 分页获取角色列表
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<RoleInfo> getRolePage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select role from RoleInfo role where 1 = 1 and role.status = 1 " +
        		"and role.id != :roleId ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "role.sort");
        Query query = super.createQuery(filterList, hql);
        query.setParameter("roleId", "R-01");
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * 获取角色总数
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(role.id) from RoleInfo role where 1 = 1 and role.status = 1 " +
        		"and role.id != :roleId ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        query.setParameter("roleId", "R-01");
        return (Long) query.uniqueResult();
    }

    /**
     * 根据编码获取角色总数
     * @param code
     * @return
     */
    public Long findRoleCountByCode(String code) {
        String hql = "select count(role.id) from RoleInfo role where 1 = 1 and role.status = 1 "
                + "and role.code = ? ";
        return super.countByHql(hql, code);
    }
}
