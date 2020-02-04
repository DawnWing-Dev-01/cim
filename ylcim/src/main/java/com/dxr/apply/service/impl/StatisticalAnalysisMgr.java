package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.DateUtils;
import com.dxr.apply.dao.ComplaintResultDao;
import com.dxr.apply.dao.ComplaintSheetDao;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.service.api.IIndustry;
import com.dxr.apply.service.api.IStatisticalAnalysis;

public class StatisticalAnalysisMgr extends BasalMgr<Object> implements
        IStatisticalAnalysis {

    @Autowired
    private ComplaintSheetDao complaintSheetDao;
    @Autowired
    private IIndustry industryMgr;
    @Autowired
    private ComplaintResultDao complaintResultDao;

    @Override
    public String statisticsByYear() {
        List<Long> statistics = new ArrayList<Long>();
        List<Integer> fiveYear = DateUtils.beforeFiveYear();
        for (Integer year : fiveYear) {
            long yearTotal = complaintSheetDao.statisticsAnalysis(null,
                    String.valueOf(year));
            long resultTotal = complaintResultDao.statisticsAnalysis(null,
                    String.valueOf(year));
            statistics.add(yearTotal + resultTotal);
        }
        JSONObject result = new JSONObject();
        result.put("xAxis", fiveYear);
        result.put("series", statistics);
        return result.toJSONString();
    }

    @Override
    public String statisticsByIndustry(String year) {
        List<String> industryNameList = new ArrayList<String>();
        List<Long> statistics = new ArrayList<Long>();
        try {
            // 获取行业列表
            List<IndustryInfo> industryList = industryMgr.getIndustryList();
            for (IndustryInfo industryInfo : industryList) {
                String industryId = industryInfo.getId();
                long industryTotal = complaintSheetDao.statisticsAnalysis(
                        industryId, String.valueOf(year));
                long resultTotal = complaintResultDao.statisticsAnalysis(
                        industryId, String.valueOf(year));
                if (industryTotal > 0) {
                    industryNameList.add(industryInfo.getName());
                    statistics.add(industryTotal + resultTotal);
                }
            }

            if (industryNameList.isEmpty()) {
                industryNameList.add("暂无数据");
                statistics.add((long) 0);
            }

            JSONObject result = new JSONObject();
            result.put("xAxis", industryNameList);
            result.put("series", statistics);
            return result.toJSONString();
        } catch (ServiceException e) {
            JSONObject result = new JSONObject();
            industryNameList.add("暂无数据");
            result.put("xAxis", industryNameList);
            statistics.add((long) 0);
            result.put("series", statistics);
            return result.toJSONString();
        }
    }

    @Override
    public String getYearCommbox() {
        JSONArray jsonArray = new JSONArray();
        JSONObject option = null;
        Integer nowYear = DateUtils.getNowYear();
        List<Integer> fiveYear = DateUtils.beforeFiveYear();
        for (Integer year : fiveYear) {
            option = new JSONObject();
            option.put("year", year);
            option.put("text", year);
            if (year.intValue() == nowYear.intValue()) {
                option.put("selected", true);
            }
            jsonArray.add(option);
        }
        return jsonArray.toJSONString();
    }

    @Override
    public Object getDbObject(String id) throws ServiceException {
        return null;
    }

}
