package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.HabitSayInfo;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;

/**
 * @description: <流程审核常用意见数据操作层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class HabitSayDao extends BasalDao<HabitSayInfo> {

    /**
     * 获取常用意见列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<HabitSayInfo> getHabitSayList() {
        String hql = "select habitSay from HabitSayInfo habitSay where 1 = 1 and habitSay.userId = :userId ";
        hql = super.orderBy(hql, "habitSay.createDate desc");
        Query query = super.createQuery(hql);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        query.setParameter("userId", shiroUser.getUserId());
        return query.list();
    }

    /**
     * 获取用户该常用意见的个数
     * @param sayDetail
     * @return
     */
    public long getTotal(String sayDetail) {
        String hql = "select count(habitSay.id) from HabitSayInfo habitSay where 1 = 1 "
                + "and habitSay.userId = :userId "
                + "and habitSay.sayDetail = :sayDetail ";
        Query query = super.createQuery(hql);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        query.setParameter("userId", shiroUser.getUserId());
        query.setParameter("sayDetail", sayDetail);
        return (long) query.uniqueResult();
    }
}
