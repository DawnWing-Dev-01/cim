package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.ComplaintHandleInfo;

/**
 * @description: <投诉处理数据操作层Dao>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintHandleDao extends BasalDao<ComplaintHandleInfo> {

    /**
     * 获取投诉处理列表page, EasyUI格式
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintHandleInfo> getComplaintHandlePage(int start,
            int limit, List<FilterBean> filterList) {
        String hql = "select handle from ComplaintHandleInfo handle where 1 = 1 ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "handle.handleDate desc");
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
        String hql = "select count(handle.id) from ComplaintHandleInfo handle where 1 = 1 ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * 是否有最终定论
     * @param complaintId
     * @return
     */
    public boolean isFinally(String complaintId) {
        String hql = "select count(handle.id) from ComplaintHandleInfo handle where 1 = 1 "
                + "and handle.complaintId = ? " + "and handle.handleType = ? ";
        Long count = super.countByHql(hql, complaintId,
                ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY);
        return count > 0;
    }

    /**
     * 获取最终处罚结果
     * @param complaintId
     * @return
     */
    public ComplaintHandleInfo getFinallyHandle(String complaintId) {
        String hql = "select handle from ComplaintHandleInfo handle where 1 = 1 "
                + "and handle.complaintId = :complaintId "
                + "and handle.handleType = :handleType ";
        Query query = super.createQuery(hql);
        query.setParameter("complaintId", complaintId);
        query.setParameter("handleType",
                ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY);
        return (ComplaintHandleInfo) query.uniqueResult();
    }
}
