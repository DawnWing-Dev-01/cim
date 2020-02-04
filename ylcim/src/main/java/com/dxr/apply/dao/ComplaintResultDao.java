package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.entity.ComplaintResultInfo;

/**
 * @description: <投诉结果导入导出DAO数据层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class ComplaintResultDao extends BasalDao<ComplaintResultInfo> {

    private String originPublicity;
    
    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintResultInfo> getComplaintResultPage(int start,
            int limit, List<FilterBean> filterList) {
        String hql = "select result from ComplaintResultInfo result where 1 = ? ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "result.enterDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setParameter(0, 1);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param dealerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintResultInfo> findComplaintResultList(String dealerId){
        String hql = "select result from ComplaintResultInfo result where 1 = 1 " +
        		"and result.originTypeId != :originTypeId " +
        		"and result.isPublicity = :isPublicity " +
        		"and result.dealerId = :dealerId ";
        hql = super.orderBy(hql, "result.enterDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("originTypeId", originPublicity);
        query.setParameter("isPublicity", ConstGlobal.COMPLAINT_IS_PUBLICITY_TRUE);
        query.setParameter("dealerId", dealerId);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(result.id) from ComplaintResultInfo result where 1 = 1 ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * @param enterCode
     * @return
     */
    public Long findTotalByCode(String enterCode) {
        String hql = "select count(result.id) from ComplaintResultInfo result where 1 = 1 "
                + "and result.enterCode = ? ";
        return super.countByHql(hql, enterCode);
    }

    /**
     * @param originTypeId
     * @return
     */
    public Long findTotalByOrigin(String originTypeId) {
        String hql = "select count(result.id) from ComplaintResultInfo result where 1 = 1 "
                + "and result.originTypeId = ? ";
        return super.countByHql(hql, originTypeId);
    }

    public void setOriginPublicity(String originPublicity) {
        this.originPublicity = originPublicity;
    }
    
    /**
     * 统计分析
     * @param industryId
     * @param year
     * @return
     */
    public long statisticsAnalysis(String industryId, String year) {
        StringBuilder hql = new StringBuilder("select count(result.id) ");
        hql.append("from ComplaintResultInfo result, DealerInfo dealer ");
        hql.append("where result.dealerId = dealer.id ");
        hql.append("and result.isPublicity = :isPublicity ");
        if (StringUtils.isNotEmpty(industryId)) {
            hql.append("and dealer.industryId = :industryId ");
        }
        if (StringUtils.isNotEmpty(year)) {
            hql.append("and date_format(result.enterDate, '%Y') = :year ");
        }

        //date_format(NOW(),'%Y')
        Query query = this.createQuery(hql.toString());
        query.setParameter("isPublicity",
                ConstGlobal.COMPLAINT_IS_PUBLICITY_TRUE);
        if (StringUtils.isNotEmpty(industryId)) {
            query.setParameter("industryId", industryId);
        }
        if (StringUtils.isNotEmpty(year)) {
            query.setParameter("year", year);
        }
        return (Long) query.uniqueResult();
    }
}
