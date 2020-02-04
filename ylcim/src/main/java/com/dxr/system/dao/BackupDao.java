package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.BackupInfo;

/**
 * @description: <数据库备份dao层>
 * @author: w.xL
 * @date: 2018-3-21
 */
public class BackupDao extends BasalDao<BackupInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<BackupInfo> getBackupPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select backup from BackupInfo backup where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "backup.createDate desc");
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
        String hql = "select count(backup.id) from BackupInfo backup where 1 = 1 ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }
}
