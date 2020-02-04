package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.DateUtils;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.ConsumerWarningDao;
import com.dxr.apply.entity.ConsumerWarningInfo;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.entity.WarningPublishRemind;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IConsumerWarning;
import com.dxr.apply.service.api.IIndustry;
import com.dxr.apply.service.api.IWarningPublishRemind;

/**
 * @description: <消费预警服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-30
 */
public class ConsumerWarningMgr extends BasalMgr<ConsumerWarningInfo> implements
        IConsumerWarning {

    @Autowired
    private ConsumerWarningDao consumerWarningDao;
    @Autowired
    private IIndustry industryMgr;
    @Autowired
    private IComplaintSheet complaintSheetMgr;
    @Autowired
    private IWarningPublishRemind wprMgr;;

    @Override
    public ConsumerWarningInfo getDbObject(String id) throws ServiceException {
        return consumerWarningDao.get(id);
    }

    @Override
    public String getConsumerWarningPage(int page, int rows,
            ConsumerWarningInfo consumerWarningInfo, String dataType)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(consumerWarningInfo)) {
            if (StringUtils.isNotEmpty(consumerWarningInfo.getEwTitle())) {
                filterList.add(new FilterBean("cwi.ewTitle",
                        HqlSymbol.HQL_LIKE, "%"
                                + consumerWarningInfo.getEwTitle() + "%"));
            }
            if (StringUtils.isNotEmpty(consumerWarningInfo.getIndustryId())) {
                filterList.add(new FilterBean("cwi.industryId",
                        HqlSymbol.HQL_EQUAL, consumerWarningInfo
                                .getIndustryId()));
            }
        }

        List<ConsumerWarningInfo> warningList = consumerWarningDao
                .getConsumerWarningPage((page - 1) * rows, rows, filterList,
                        dataType);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", consumerWarningDao.getTotal(filterList, dataType));
        result.put("rows", warningList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public List<ConsumerWarningInfo> findIndustryConsumerWarning(
            String industryId) throws ServiceException {
        List<ConsumerWarningInfo> result = new ArrayList<ConsumerWarningInfo>();
        if (StringUtils.isEmpty(industryId)) {
            return result;
        }

        List<FilterBean> filterList = new ArrayList<FilterBean>();
        // 过滤行业
        filterList.add(new FilterBean("cwi.industryId", HqlSymbol.HQL_EQUAL,
                industryId));
        result = consumerWarningDao.findIndustryConsumerWarning(filterList);
        return result;
    }

    @Override
    public String saveConsumerWarning(ConsumerWarningInfo consumerWarningInfo)
            throws ServiceException {
        consumerWarningDao.save(consumerWarningInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateConsumerWarning(ConsumerWarningInfo consumerWarningInfo)
            throws ServiceException {
        String warningId = consumerWarningInfo.getId();
        ConsumerWarningInfo warningInfo = getDbObject(warningId);
        warningInfo.setEwTitle(consumerWarningInfo.getEwTitle());
        warningInfo.setEwDate(consumerWarningInfo.getEwDate());
        warningInfo.setEwAuthor(consumerWarningInfo.getEwAuthor());
        warningInfo.setEwContent(consumerWarningInfo.getEwContent());
        warningInfo.setStarShowDate(consumerWarningInfo.getStarShowDate());
        warningInfo.setEndShowDate(consumerWarningInfo.getEndShowDate());
        warningInfo.setRemark(consumerWarningInfo.getRemark());
        warningInfo.setUpdateDate(new Date());
        consumerWarningDao.update(warningInfo);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteConsumerWarning(String warningId)
            throws ServiceException {
        consumerWarningDao.deleteById(warningId);
        return new Message("删除成功!").toString();
    }

    @Override
    public String getDetails(String warningId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        ConsumerWarningInfo warningInfo = getDbObject(warningId);
        detailsMap.put("warningInfo.id", warningInfo.getId());
        detailsMap.put("warningInfo.ewTitle", warningInfo.getEwTitle());
        detailsMap.put("warningInfo.ewDate", warningInfo.getEwDate());
        detailsMap.put("warningInfo.ewContent", warningInfo.getEwContent());
        detailsMap.put("warningInfo.industryId", warningInfo.getIndustryId());
        detailsMap.put("warningInfo.ewAuthor", warningInfo.getEwAuthor());
        detailsMap.put("warningInfo.starShowDate",
                warningInfo.getStarShowDate());
        detailsMap.put("warningInfo.endShowDate", warningInfo.getEndShowDate());
        detailsMap.put("warningInfo.remark", warningInfo.getRemark());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

    @Override
    public String updateWarningPublishRemind() throws ServiceException {
        Map<String, Object> filterPar = new HashMap<String, Object>();
        // 得到上一个月的月初&月末
        Map<String, Object> firstLast = DateUtils
                .getPrevMonthFirstLast(new Date());
        filterPar.put("startDate", firstLast.get("monthfirst"));
        filterPar.put("endDate", firstLast.get("monthlast"));
        WarningPublishRemind wprInfo = null;
        List<IndustryInfo> industryList = industryMgr
                .getThresholdNotNulltIndustryList();
        for (IndustryInfo industryInfo : industryList) {
            String industryId = industryInfo.getId();
            filterPar.put("industryId", industryId);
            // 获取该行业指定时间内的有效投诉量
            long total = complaintSheetMgr.statisticsComplaintSheet(filterPar);
            // 预警阀值
            Integer threshold = industryInfo.getWarningThreshold();
            if (total >= threshold) {
                wprInfo = new WarningPublishRemind();
                wprInfo.setName("该行业有效投诉已超预警阀值，需发布消费预警");
                wprInfo.setIndustryId(industryId);
                wprInfo.setIndustryName(industryInfo.getName());
                wprInfo.setThreshold(threshold);
                wprInfo.setComplaintTotal((int) total);
                wprInfo.setYearNum(Integer.valueOf(firstLast.get("year")
                        .toString()));
                wprInfo.setMonthNum(Integer.valueOf(firstLast.get("month")
                        .toString()) + 1);
                // 保存预警发布提醒
                wprMgr.saveWarningPublishRemind(wprInfo);
            }
        }

        return wprMgr.getWarningPublishRemindPage(null);
    }
}
