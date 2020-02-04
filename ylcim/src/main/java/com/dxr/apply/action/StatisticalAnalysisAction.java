package com.dxr.apply.action;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dawnwing.framework.utils.DateUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.service.api.IStatisticalAnalysis;

/**
 * @description: <统计分析Action>
 * @author: w.xL
 * @date: 2018-4-22
 */
public class StatisticalAnalysisAction extends BasalAction {

    private static final long serialVersionUID = -441086673046004787L;

    @Autowired
    private IStatisticalAnalysis statisticalAnalysisMgr;

    @Override
    public String index() {
        // 以年为维度统计
        if ("foryear".equals(viewType)) {
            return "foryearView";
        }
        // 以行业为维度
        if ("forindustry".equals(viewType)) {
            return "forindustryView";
        }
        return super.index();
    }

    /**
     * 年度统计
     */
    public void statisticsByYear() {
        try {
            String result = statisticalAnalysisMgr.statisticsByYear();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "StatisticalAnalysisAction.statisticsByYear() is error...",
                    e);
            JSONObject result = new JSONObject();
            result.put("xAxis", new ArrayList<Integer>());
            result.put("series", new ArrayList<Long>());
            super.writeToView(result.toJSONString());
        }
    }

    /**
     * 行业统计
     */
    public void statisticsByIndustry() {
        if (StringUtils.isEmpty(object)) {
            object = String.valueOf(DateUtils.getNowYear());
        }
        String result = statisticalAnalysisMgr.statisticsByIndustry(object);
        super.writeToView(result);
    }

    /**
     * 年份下拉框数据
     */
    public void getYearCommbox() {
        String result = statisticalAnalysisMgr.getYearCommbox();
        super.writeToView(result);
    }
}
