package com.dxr.cms.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.cms.entity.ColumnInfo;

/**
 * @description: <CMS 栏目数据操作层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ColumnDao extends BasalDao<ColumnInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ColumnInfo> getColumnPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select column from ColumnInfo column where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "column.sort");
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
        String hql = "select count(column.id) from ColumnInfo column where 1 = 1 ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }
}
