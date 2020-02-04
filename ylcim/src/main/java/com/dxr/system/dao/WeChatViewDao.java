package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.WeChatViewInfo;

/**
 * @description: <微信视图数据访问Dao层>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WeChatViewDao extends BasalDao<WeChatViewInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<WeChatViewInfo> getWeChatViewPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select weChatView from WeChatViewInfo weChatView where 1 = 1 and weChatView.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "weChatView.sort");
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
        String hql = "select count(*) from WeChatViewInfo weChatView where 1 = 1 and weChatView.status = "
                + ConstGlobal.DATA_STATUS_OKAY + " ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }
}
