package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.SystemConstant;

/**
 * @description: <系统常量数据操作Dao层>
 * @author: w.xL
 * @date: 2018-3-28
 */
public class SystemConstantDao extends BasalDao<SystemConstant> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SystemConstant> getSystemConstantPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select constant from SystemConstant constant where 1 = 1 and constant.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "constant.sort");
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
        String hql = "select count(constant.id) from SystemConstant constant where 1 = 1 and constant.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * 根据键值获取系统常量（数据字典）数量
     * @param scKey
     * @return
     */
    public Long findConstantByKey(String scKey) {
        String hql = "select count(constant.id) from SystemConstant constant where 1 = 1 and constant.status = ? "
                + "and constant.scKey = ? ";
        return super.countByHql(hql, ConstGlobal.DATA_STATUS_OKAY, scKey);
    }
}
