package com.dxr.apply.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.ConsumerTipsInfo;

/**
 * @description: <12315消费提示数据库操作DAO层>
 * @author: w.xL
 * @date: 2018-3-24
 */
public class ConsumerTipsDao extends BasalDao<ConsumerTipsInfo> {

    @SuppressWarnings("unchecked")
    public List<ConsumerTipsInfo> getConsumerTipsPage() {
        String hql = "select tips from ConsumerTipsInfo tips where 1 = 1 and tips.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.orderBy(hql, "tips.sort");
        Query query = super.createQuery(new ArrayList<FilterBean>(), hql);
        return query.list();
    }
}
