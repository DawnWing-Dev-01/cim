package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.WorkFlowInfo;

/**
 * @description: <简易工作流数据操作层>
 * @author: w.xL
 * @date: 2018-4-2
 */
public class WorkFlowDao extends BasalDao<WorkFlowInfo> {

    /**
     * 获取简易工作流列表page, EasyUI格式
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<WorkFlowInfo> getWorkFlowPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 and workFlow.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "workFlow.sort");
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
        String hql = "select count(workFlow.id) from WorkFlowInfo workFlow where 1 = 1 and workFlow.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * 根据流节点code获取流程总数
     * @param username
     * @return
     */
    public Long findWorkFlowByCode(String flowNodeCode) {
        String hql = "select count(workFlow.id) from WorkFlowInfo workFlow where 1 = 1 "
                + "and workFlow.flowNodeCode = ? ";
        return super.countByHql(hql, flowNodeCode);
    }

    /**
     * 获取当前的流程节点
     * @param flowCode
     * @return
     */
    public WorkFlowInfo loadNow(String flowCode) {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 and workFlow.status = :status "
                + "and workFlow.flowNodeCode = :flowNodeCode";
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("flowNodeCode", flowCode);
        query.setMaxResults(1);
        return (WorkFlowInfo) query.uniqueResult();
    }

    /**
     * 获取第一条记录, 根据sort排序
     * @return
     */
    public WorkFlowInfo loadFirst() {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 "
                + "and workFlow.sort = ("
                + "select min(workFlow.sort) from WorkFlowInfo workFlow where workFlow.status = :status "
                + ") ";
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        return (WorkFlowInfo) query.uniqueResult();
    }

    /**
     * 获取最后一条记录, 根据sort排序
     * @return
     */
    public WorkFlowInfo loadLast() {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 "
                + "and workFlow.sort = ("
                + "select max(workFlow.sort) from WorkFlowInfo workFlow where workFlow.status = :status "
                + ") ";
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        return (WorkFlowInfo) query.uniqueResult();
    }

    /**
     * 根据flowCode获取下一个节点
     * @param flowCode
     * @return
     */
    public WorkFlowInfo loadNext(String flowCode) {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 "
                + "and workFlow.status = :status "
                + "and workFlow.sort > ( "
                + "select workFlow.sort from WorkFlowInfo workFlow where workFlow.flowNodeCode = :flowNodeCode "
                + ") ";
        hql = super.orderBy(hql, "workFlow.sort");
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("flowNodeCode", flowCode);
        query.setMaxResults(1);
        return (WorkFlowInfo) query.uniqueResult();
    }

    /**
     * 根据flowCode获取上一个节点
     * @param flowCode
     * @return
     */
    public WorkFlowInfo loadPrev(String flowCode) {
        String hql = "select workFlow from WorkFlowInfo workFlow where 1 = 1 "
                + "and workFlow.status = :status "
                + "and workFlow.sort < ( "
                + "select workFlow.sort from WorkFlowInfo workFlow where workFlow.flowNodeCode = :flowNodeCode "
                + ") ";
        hql = super.orderBy(hql, "workFlow.sort desc");
        Query query = this.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("flowNodeCode", flowCode);
        query.setMaxResults(1);
        return (WorkFlowInfo) query.uniqueResult();
    }
}
