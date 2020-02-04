package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <预警发布提醒>
 * @author: w.xL
 * @date: 2018-4-21
 */
public class WarningPublishRemind extends AbstractEntity{

    private static final long serialVersionUID = -6136091420095153452L;

    private String industryId;
    
    private String industryName;
    
    private Integer threshold;
    
    private Integer complaintTotal;
    
    private Integer yearNum;
    
    private Integer monthNum;
    
    /**
     * 0-(忽略、已发布)不显示; 1-显示;
     */
    private Integer showType = 1;

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getComplaintTotal() {
        return complaintTotal;
    }

    public void setComplaintTotal(Integer complaintTotal) {
        this.complaintTotal = complaintTotal;
    }

    public Integer getYearNum() {
        return yearNum;
    }

    public void setYearNum(Integer yearNum) {
        this.yearNum = yearNum;
    }

    public Integer getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(Integer monthNum) {
        this.monthNum = monthNum;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }
}
