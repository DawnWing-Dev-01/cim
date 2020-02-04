package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <消费预警信息>
 * @author: w.xL
 * @date: 2018-3-30
 */
public class ConsumerWarningInfo extends AbstractEntity {

    private static final long serialVersionUID = 3165254892320893776L;

    /**
     * 预警标题
     */
    private String ewTitle;
    
    /**
     * 预警时间
     */
    private Date ewDate;
    
    /**
     * 预警内容
     */
    private String ewContent;
    
    /**
     * 预警行业
     */
    private String industryId;
    
    /**
     * 预警发布单位
     */
    private String ewAuthor;
    
    /**
     * 开始显示时间
     */
    private Date starShowDate;
    
    /**
     * 结束显示时间
     */
    private Date endShowDate;

    public String getEwTitle() {
        return ewTitle;
    }

    public void setEwTitle(String ewTitle) {
        this.ewTitle = ewTitle;
    }

    public Date getEwDate() {
        return ewDate;
    }

    public void setEwDate(Date ewDate) {
        this.ewDate = ewDate;
    }

    public String getEwContent() {
        return ewContent;
    }

    public void setEwContent(String ewContent) {
        this.ewContent = ewContent;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getEwAuthor() {
        return ewAuthor;
    }

    public void setEwAuthor(String ewAuthor) {
        this.ewAuthor = ewAuthor;
    }

    public Date getStarShowDate() {
        return starShowDate;
    }

    public void setStarShowDate(Date starShowDate) {
        this.starShowDate = starShowDate;
    }

    public Date getEndShowDate() {
        return endShowDate;
    }

    public void setEndShowDate(Date endShowDate) {
        this.endShowDate = endShowDate;
    }
}
