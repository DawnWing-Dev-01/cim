package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.entity.ConsumerWarningInfo;

/**
 * @description: <消费预警数据操作DAO层>
 * @author: w.xL
 * @date: 2018-3-30
 */
public class ConsumerWarningDao extends BasalDao<ConsumerWarningInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @param dataType
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ConsumerWarningInfo> getConsumerWarningPage(int start,
            int limit, List<FilterBean> filterList, String dataType) {
        String hql = "select cwi from ConsumerWarningInfo cwi where 1 = 1 and cwi.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        // 根据页面过滤数据
        if (StringUtils.isNotEmpty(dataType) && "history".equals(dataType)) {
            // 历史数据
            hql += "and date(cwi.endShowDate) < date(now()) ";
        } else {
            // 正在显示/将要显示
            hql += "and date(cwi.endShowDate) >= date(now()) ";
        }
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "cwi.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @param dataType
     * @return
     */
    public long getTotal(List<FilterBean> filterList, String dataType) {
        String hql = "select count(cwi.id) from ConsumerWarningInfo cwi where 1 = 1 and cwi.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        // 根据页面过滤数据
        if (StringUtils.isNotEmpty(dataType) && "history".equals(dataType)) {
            // 历史数据
            hql += "and date(cwi.endShowDate) < date(now()) ";
        } else {
            // 正在显示/将要显示
            hql += "and date(cwi.endShowDate) >= date(now()) ";
        }
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * 获取行业消费预警信息, 当前时间在显示和结束范围内的
     * starShowDate <= now() <= endShowDate
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ConsumerWarningInfo> findIndustryConsumerWarning(
            List<FilterBean> filterList) {
        String hql = "select cwi from ConsumerWarningInfo cwi where 1 = 1 and cwi.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql += "and date(cwi.starShowDate) <= date(now()) ";
        hql += "and date(now()) <= date(cwi.endShowDate) ";
        hql = super.orderBy(hql, "cwi.createDate desc");
        Query query = super.createQuery(filterList, hql);
        return query.list();
    }

    /**
     * 获取行业下的消费预警信息数量
     * @param industryId
     * @return
     */
    public long getTotal(String industryId) {
        String hql = "select count(cwi.id) from ConsumerWarningInfo cwi where 1 = 1 "
                + "and cwi.status = ? and cwi.industryId = ? ";
        return super.countByHql(hql, ConstGlobal.DATA_STATUS_OKAY, industryId);
    }
}
