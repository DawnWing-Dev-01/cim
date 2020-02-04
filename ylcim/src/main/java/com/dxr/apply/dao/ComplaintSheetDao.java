package com.dxr.apply.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.DealerCreditVo;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;

/**
 * @description: <投诉登记表数据操作DAO层>
 * @author: w.xL
 * @date: 2018-3-13
 */
public class ComplaintSheetDao extends BasalDao<ComplaintSheetInfo> {

    @SuppressWarnings("unchecked")
    public List<ComplaintSheetInfo> getComplaintSheetPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select complaint from ComplaintSheetInfo complaint where complaint.status != :status ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "complaint.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(complaint.id) from ComplaintSheetInfo complaint where complaint.status != :status ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        return (Long) query.uniqueResult();
    }

    /**
     * 获取待办列表, 根据当前登录用户
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintSheetInfo> getTodoListPage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select complaint from WorkFlowExample example, ComplaintSheetInfo complaint where 1 = 1 "
                + "and example.businessId = complaint.id "
                + "and complaint.status != :status "
                + "and ( example.subjectId in ( :roleIds ) or example.subjectId = :userId ) ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "complaint.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        query.setParameter("userId", shiroUser.getUserId());
        List<String> roleIds = new ArrayList<String>(shiroUser.getRoles());
        query.setParameterList("roleIds", roleIds);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTodoTotal(List<FilterBean> filterList) {
        String hql = "select count(complaint.id) from WorkFlowExample example, ComplaintSheetInfo complaint where 1 = 1 "
                + "and example.businessId = complaint.id "
                + "and complaint.status != :status "
                + "and ( example.subjectId in ( :roleIds ) or example.subjectId = :userId ) ";
        hql = super.setFilterBean(filterList, hql);
        Query query = super.createQuery(filterList, hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        query.setParameter("userId", shiroUser.getUserId());
        List<String> roleIds = new ArrayList<String>(shiroUser.getRoles());
        query.setParameterList("roleIds", roleIds);
        return (Long) query.uniqueResult();
    }

    /**
     * 获取经营者经营记录列表
     * @param dealerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DealerCreditVo> findDealerCreditList(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select new com.dxr.apply.entity.DealerCreditVo(complaint.complaintReason, "
                + "complaint.createDate, handle.handleSay) "
                + "from ComplaintSheetInfo complaint, ComplaintHandleInfo handle "
                + "where 1 = 1 " + "and complaint.id = handle.complaintId";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "complaint.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * 按条件统计失信行为数量
     * @param filterList
     * @return
     */
    public long statisticsComplaintSheet(List<FilterBean> filterList) {
        String hql = "select count(complaint.id) "
                + "from ComplaintSheetInfo complaint, DealerInfo dealer "
                + "where complaint.dealerId = dealer.id "
                + "and complaint.status != ? ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        query.setParameter(0, ConstGlobal.DATA_STATUS_DELETED);
        return (Long) query.uniqueResult();
    }

    /**
     * 统计分析
     * @param industryId
     * @param year
     * @return
     */
    public long statisticsAnalysis(String industryId, String year) {
        StringBuilder hql = new StringBuilder("select count(complaint.id) ");
        hql.append("from ComplaintSheetInfo complaint, DealerInfo dealer ");
        hql.append("where complaint.dealerId = dealer.id ");
        hql.append("and complaint.status != :status ");
        hql.append("and complaint.flowStatus = :flowStatus ");
        hql.append("and complaint.isPublicity = :isPublicity ");
        if (StringUtils.isNotEmpty(industryId)) {
            hql.append("and dealer.industryId = :industryId ");
        }
        if (StringUtils.isNotEmpty(year)) {
            hql.append("and date_format(complaint.createDate, '%Y') = :year ");
        }

        //date_format(NOW(),'%Y')
        Query query = this.createQuery(hql.toString());
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        query.setParameter("flowStatus",
                ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED);
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

    /**
     * 获取所有待办列表, 判断若超过6天没处理则需要微信公众号发生通知消息
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintSheetInfo> getAllTodoListPage() {
        String hql = "select complaint from WorkFlowExample example, ComplaintSheetInfo complaint where 1 = 1 "
                + "and example.businessId = complaint.id "
                + "and complaint.status != :status "
                + "and example.status = :isRun ";
        hql = super.orderBy(hql, "complaint.createDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("status", ConstGlobal.DATA_STATUS_DELETED);
        query.setParameter("isRun", ConstGlobal.FLOW_EXAMPLE_STATUS_RUN);
        return query.list();
    }
}
