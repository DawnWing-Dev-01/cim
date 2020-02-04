package com.dxr.apply.dao;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.WorkFlowExample;

/**
 * @description: <流程实例数据操作DAO层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class FlowExampleDao extends BasalDao<WorkFlowExample> {

    /**
     * @param businessId
     * @return
     */
    public WorkFlowExample loadFlowExample(String businessId) {
        String hql = "select example from WorkFlowExample example where 1 = 1 "
                + "and example.businessId = :businessId";
        Query query = super.createQuery(hql);
        query.setParameter("businessId", businessId);
        return (WorkFlowExample) query.uniqueResult();
    }
}