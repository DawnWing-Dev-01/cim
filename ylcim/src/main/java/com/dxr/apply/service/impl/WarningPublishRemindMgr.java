package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.WarningPublishRemindDao;
import com.dxr.apply.entity.WarningPublishRemind;
import com.dxr.apply.service.api.IWarningPublishRemind;

/**
 * @description: <消费预警提示服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-21
 */
public class WarningPublishRemindMgr extends BasalMgr<WarningPublishRemind>
        implements IWarningPublishRemind {

    @Autowired
    private WarningPublishRemindDao wprDao;

    @Override
    public WarningPublishRemind getDbObject(String id) throws ServiceException {
        return wprDao.get(id);
    }

    @Override
    public String getWarningPublishRemindPage(WarningPublishRemind wprInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        filterList.add(new FilterBean("wpr.showType", HqlSymbol.HQL_NOT_EQUAL,
                0));
        if (wprInfo != null) {
            if (StringUtils.isNotEmpty(wprInfo.getIndustryId())) {
                filterList.add(new FilterBean("wpr.industryId",
                        HqlSymbol.HQL_EQUAL, wprInfo.getIndustryId()));
            }
            if (wprInfo.getYearNum() != null) {
                filterList.add(new FilterBean("wpr.yearNum",
                        HqlSymbol.HQL_EQUAL, wprInfo.getYearNum()));
            }
            if (wprInfo.getMonthNum() != null) {
                filterList.add(new FilterBean("wpr.monthNum",
                        HqlSymbol.HQL_EQUAL, wprInfo.getMonthNum()));
            }
        }

        List<WarningPublishRemind> wprList = wprDao
                .getWarningPublishRemindList(filterList);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", wprDao.getTotal(filterList));
        result.put("rows", wprList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    private List<WarningPublishRemind> getWprList(WarningPublishRemind wprInfo) {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (wprInfo != null) {
            if (StringUtils.isNotEmpty(wprInfo.getIndustryId())) {
                filterList.add(new FilterBean("wpr.industryId",
                        HqlSymbol.HQL_EQUAL, wprInfo.getIndustryId()));
            }
            if (wprInfo.getYearNum() != null) {
                filterList.add(new FilterBean("wpr.yearNum",
                        HqlSymbol.HQL_EQUAL, wprInfo.getYearNum()));
            }
            if (wprInfo.getMonthNum() != null) {
                filterList.add(new FilterBean("wpr.monthNum",
                        HqlSymbol.HQL_EQUAL, wprInfo.getMonthNum()));
            }
        }

        List<WarningPublishRemind> wprList = wprDao
                .getWarningPublishRemindList(filterList);
        return wprList;
    }

    @Override
    public void saveWarningPublishRemind(WarningPublishRemind wprInfo)
            throws ServiceException {
        List<WarningPublishRemind> wprList = getWprList(wprInfo);
        if (wprList != null && !wprList.isEmpty()) {
            return;
        }
        wprDao.save(wprInfo);
        wprDao.getSession().flush();
    }

    @Override
    public void updateShowType(WarningPublishRemind wprInfo)
            throws ServiceException {
        WarningPublishRemind wprDb = wprDao.get(wprInfo.getId());
        wprDb.setShowType(wprInfo.getShowType());
        wprDao.update(wprDb);
    }
}
