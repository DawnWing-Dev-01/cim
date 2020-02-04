package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.system.entity.UserInfo;

/**
 * @author w.xL
 *
 */
public class UserDao extends BasalDao<UserInfo> {

    /**
     * @param username
     * @param password
     * @return
     */
    public UserInfo findUser(String username) {
        StringBuilder hql = new StringBuilder();
        hql.append("select user from UserInfo user where 1 = 1 ");

        if (StringUtils.isNotEmpty(username)) {
            hql.append("and user.username = :username ");
        }
        Query query = super.createQuery(hql.toString());
        query.setParameter("username", username);
        return (UserInfo) query.uniqueResult();
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserInfo> getAllUserList() {
        String hql = "select user from UserInfo user";
        Query query = super.createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<UserInfo> getUserPage(int start, int limit,
            List<FilterBean> filterList) {
        /*String hql = "select user from UserInfo user, RoleInfo role, UserRole urr "
                + "where 1 = 1 and urr.roleId = role.id and user.id = urr.userId ";*/
        String hql = "select user from UserInfo user, Organization org where 1 = 1 "
                + "and user.orgId = org.id "
                + "and user.username != :loginName ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "user.sort");
        Query query = super.createQuery(filterList, hql);
        query.setParameter("loginName", "administrator");
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(*) from UserInfo user, Organization org where 1 = 1 "
                + "and user.orgId = org.id "
                + "and user.username != :loginName ";
        hql = super.setFilterBean(filterList, hql);
        Query query = super.createQuery(filterList, hql);
        query.setParameter("loginName", "administrator");
        return (Long) query.uniqueResult();
    }

    /**
     * 根据账号获取用户总数
     * @param username
     * @return
     */
    public Long findUserCountByCode(String username) {
        String hql = "select count(user.id) from UserInfo user where 1 = 1 "
                + "and user.username = ? ";
        return super.countByHql(hql, username);
    }
    
    /**
     * 获取组织机构下用户数量
     * @param industryId
     * @return
     */
    public long getTotalByOrg(String orgId) {
        String hql = "select count(user.id) from UserInfo user where 1 = 1 "
                + "and user.status = ? and user.orgId = ? ";
        return super
                .countByHql(hql, ConstGlobal.DATA_STATUS_OKAY, orgId);
    }

    /**
     * 根据角色获取拥有该角色的用户
     * @param roleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserInfo> findUserListForRoleId(String roleId) {
        String hql = "select user from UserInfo user, UserRole ur where 1 = 1 "
                + "and user.id = ur.userId and ur.roleId = :roleId "
                + "and user.status = :status ";
        hql = super.orderBy(hql, "user.sort");
        Query query = super.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("roleId", roleId);
        return query.list();
    }
}
