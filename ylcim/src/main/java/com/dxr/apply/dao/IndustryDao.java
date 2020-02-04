package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.IndustryInfo;

/**
 * @description: <行业信息管理DAO层>
 * @author: w.xL
 * @date: 2018-2-27
 */
public class IndustryDao extends BasalDao<IndustryInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<IndustryInfo> getIndustryPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select industry from IndustryInfo industry where 1 = 1 and industry.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "industry.sort");
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
        String hql = "select count(*) from IndustryInfo industry where 1 = 1 and industry.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * @param name
     * @return
     */
    public IndustryInfo getIndustryByName(String name) {
        String hql = "select industry from IndustryInfo industry where 1 = 1 "
                + "and industry.status = :status and industry.name = :name ";
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("name", name);
        return (IndustryInfo) query.uniqueResult();
    }
}
