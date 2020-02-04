package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.WorkFlowLog;

/**
 * @description: <流程日志数据操作层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class FlowLogDao extends BasalDao<WorkFlowLog> {

    /**
     * 获取流程日志, 根据流程实例Id
     * @param flowExampleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<WorkFlowLog> getFlowLogList(String flowExampleId) {
        String hql = "select flowLog from WorkFlowLog flowLog where 1 = 1 "
                + "and flowLog.flowExampleId = :flowExampleId ";
        hql = super.orderBy(hql, "flowLog.handleDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("flowExampleId", flowExampleId);
        return query.list();
    }

    /**
     * 获取最近的流程日志
     * @param flowExampleId
     * @return
     */
    public WorkFlowLog getRecentlyFlowLog(String flowExampleId) {
        String hql = "select flowLog from WorkFlowLog flowLog where 1 = 1 "
                + "and flowLog.flowExampleId = :flowExampleId ";
        hql = super.orderBy(hql, "flowLog.createDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("flowExampleId", flowExampleId);
        query.setMaxResults(1);
        return (WorkFlowLog) query.uniqueResult();
    }
}
