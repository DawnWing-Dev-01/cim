package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.WarningPublishRemind;

/**
 * @description: <消费预警提示Dao层>
 * @author: w.xL
 * @date: 2018-4-21
 */
public class WarningPublishRemindDao extends BasalDao<WarningPublishRemind> {

    /**
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<WarningPublishRemind> getWarningPublishRemindList(
            List<FilterBean> filterList) {
        String hql = "select wpr from WarningPublishRemind wpr where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "wpr.createDate desc");
        Query query = super.createQuery(filterList, hql);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(wpr.id) from WarningPublishRemind wpr where 1 = 1 and wpr.showType != 0 ";
        hql = super.setFilterBean(filterList, hql);
        Query query = super.createQuery(filterList, hql);
        return (long) query.uniqueResult();
    }
}
