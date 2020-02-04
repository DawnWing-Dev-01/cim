package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.system.entity.AttachInfo;

/**
 * @description: <附件数据库操作Dao>
 * @author: w.xL
 * @date: 2018-3-23
 */
public class AttachDao extends BasalDao<AttachInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @remark 获取过滤后的附件列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AttachInfo> getAttachPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select attach From AttachInfo attach where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "attach.createDate desc");
        Query query = this.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param start
     * @param limit
     * @param filterList
     * @remark 获取过滤后的附件列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<AttachInfo> getAttachPage4Order(int start, int limit,
            List<FilterBean> filterList, String order) {
        String hql = "select attach From AttachInfo attach where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        if (StringUtils.isNotEmpty(order)) {
            hql = super.orderBy(hql, order);
        }else{
            hql = super.orderBy(hql, "attach.createDate desc");
        }
        Query query = this.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @remark 获取过滤后的附件总数
     * @return
     */
    public Long getAttachTotal(List<FilterBean> filterList) {
        String hql = "select count(attach.id) From AttachInfo attach where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }
}
