package com.dxr.apply.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.DealerInfo;

/**
 * @description: <经营者信息管理DAO层>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerDao extends BasalDao<DealerInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DealerInfo> getDealerPage(int start, int limit,
            List<FilterBean> filterList) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder
                .append("select new DealerInfo(dealer, industry.name, '', org.name) ");
        //from DealerInfo dealer, IndustryInfo industry, DealerTypeInfo dealerType 
        strBuilder
                .append("from DealerInfo dealer, IndustryInfo industry, Organization org ");
        strBuilder.append("where 1 = 1 ");
        strBuilder.append("and dealer.status = ? ");
        strBuilder.append("and dealer.industryId = industry.id ");
        strBuilder.append("and dealer.jurisdiction = org.id ");
        //strBuilder.append("and dealer.dealerTypeId = dealerType.id ");
        String hql = strBuilder.toString();
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "dealer.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setParameter(0, ConstGlobal.DATA_STATUS_OKAY);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(*) from DealerInfo dealer, IndustryInfo industry, Organization org"
                + " where 1 = 1 and dealer.status = ? "
                + "and dealer.industryId = industry.id "
                + "and dealer.jurisdiction = org.id ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        query.setParameter(0, ConstGlobal.DATA_STATUS_OKAY);
        return (Long) query.uniqueResult();
    }

    /**
     * 根据名字模糊查找经营者信息
     * @param dealerName
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DealerInfo> fuzzyQueryDealerList(String keywords) {
        String hql = "select dealer from DealerInfo dealer where 1 = 1 and dealer.status = :status "
                + "and ( dealer.name like :keywords or dealer.simpleName like :keywords ) ";
        hql = super.orderBy(hql, "dealer.createDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("keywords", "%" + keywords + "%");
        query.setFirstResult(0);
        query.setMaxResults(Integer.MAX_VALUE);
        return query.list();
    }

    /**
     * 获取行业下经营者信息数量
     * @param industryId
     * @return
     */
    public long getTotal(String industryId) {
        String hql = "select count(*) from DealerInfo dealer where 1 = 1 "
                + "and dealer.status = ? and dealer.industryId = ? ";
        return super.countByHql(hql, ConstGlobal.DATA_STATUS_OKAY, industryId);
    }

    /**
     * 获取管辖单位下经营者信息数量
     * @param industryId
     * @return
     */
    public long getTotalByOrg(String jurisdiction) {
        String hql = "select count(*) from DealerInfo dealer where 1 = 1 "
                + "and dealer.status = ? and dealer.jurisdiction = ? ";
        return super
                .countByHql(hql, ConstGlobal.DATA_STATUS_OKAY, jurisdiction);
    }

    /**
     * @param licenseNo
     * @return
     */
    public DealerInfo getDealerByLicenseNo(String licenseNo) {
        String hql = "select dealer from DealerInfo dealer where 1 = 1 "
                + "and dealer.status = :status and dealer.licenseNo = :licenseNo ";
        Query query = super.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_OKAY);
        query.setParameter("licenseNo", licenseNo);
        return (DealerInfo) query.uniqueResult();
    }
}
