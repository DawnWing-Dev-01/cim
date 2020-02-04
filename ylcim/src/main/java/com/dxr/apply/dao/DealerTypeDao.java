package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.DealerTypeInfo;

/**
 * @description: <经营者类型管理DAO层>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerTypeDao extends BasalDao<DealerTypeInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DealerTypeInfo> getTypePage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select type from DealerTypeInfo type where 1 = 1 and type.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "type.sort");
        Query query = super.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(*) from DealerTypeInfo type where 1 = 1 and type.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }
}
