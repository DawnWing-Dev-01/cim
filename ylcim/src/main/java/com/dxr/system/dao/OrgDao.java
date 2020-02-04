package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.system.entity.Organization;

/**
 * @author w.xL
 */
public class OrgDao extends BasalDao<Organization> {

    /**
     * @param fatherId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Organization> getChildren(String fatherId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select org from Organization org where 1 = 1 ");
        if (StringUtils.isNotEmpty(fatherId)) {
            hql.append("and org.fatherId = :fatherId ");
        } else {
            hql.append("and org.fatherId is null ");
        }
        hql.append("order by org.sort");
        Query query = super.createQuery(hql.toString());

        if (StringUtils.isNotEmpty(fatherId)) {
            query.setParameter("fatherId", fatherId);
        }
        return query.list();
    }

    /**
     * @param name
     * @return
     */
    public Organization getOrganizationByName(String name) {
        String hql = "select org from Organization org where 1 = 1 and org.name = :name";
        Query query = super.createQuery(hql);
        query.setParameter("name", name);
        return (Organization) query.uniqueResult();
    }
}
