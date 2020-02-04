package com.dxr.apply.service.api;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-4-22
 */
public interface IStatisticalAnalysis {

    /**
     * 年度统计
     * @return
     */
    public String statisticsByYear();

    /**
     * 行业统计
     * @return
     */
    public String statisticsByIndustry(String year);
    
    /**
     * 获取年份下拉框数据
     * @return
     */
    public String getYearCommbox();
}
