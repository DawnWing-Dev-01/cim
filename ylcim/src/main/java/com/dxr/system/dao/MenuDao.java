package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.MenuInfo;

/**
 * @author w.xL
 *
 */
public class MenuDao extends BasalDao<MenuInfo> {

    /**
     * @param fatherId
     * @param roleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MenuInfo> getChildren(String fatherId, List<String> roleIds) {
        StringBuilder hql = new StringBuilder();
        hql.append("select menu from MenuInfo menu, RoleMenu rrm, RoleInfo role where 1 = 1 ");
        hql.append("and menu.id = rrm.menuId ");
        hql.append("and menu.status = :status ");
        hql.append("and menu.fatherId = :fatherId ");
        hql.append("and rrm.roleId in ( :roleIds ) ");
        hql.append("group by menu.id, menu.fatherId, menu.name ");
        hql.append("order by menu.sort");
        Query query = super.createQuery(hql.toString());
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("fatherId", fatherId);
        // 若roleIds为空, 需要补个''字符, 否则执行SQL会报错
        if (roleIds != null && roleIds.isEmpty()) {
            roleIds.add("''");
        }
        query.setParameterList("roleIds", roleIds);

        return query.list();
    }

    /*public List<MenuInfo> getRootMenuByRole(List<String> roleIds) {
        StringBuilder hql = new StringBuilder();
        hql.append("select menu from MenuInfo menu, RoleMenuRelation rrm, RoleInfo role where 1 = 1 ");
        hql.append("and menu.id = rrm.menuId ");
        hql.append("and menu.fatherId = ? ");
        if (roleIds != null && !roleIds.isEmpty()) {
            hql.append("and rrm.roleId in ( ");
            int index = 0;
            for (String roleId : roleIds) {
                hql.append("'" + roleId + "'");
                if (index < (roleIds.size() - 1)) {
                    hql.append(", ");
                }
                index++;
            }
            hql.append(") ");
        }
        hql.append("group by menu.id, menu.fatherId, menu.name ");
        hql.append("order by menu.sort");
        Query query = super.createQuery(hql.toString());
        query.setString(0, ConstGlobal.MENU_NAV_ROOT);
        return query.list();
    }*/
}
