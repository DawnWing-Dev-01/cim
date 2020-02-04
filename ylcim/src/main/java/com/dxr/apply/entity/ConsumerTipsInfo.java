package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <12315消费提示实体类>
 * @author: w.xL
 * @date: 2018-3-24
 */
public class ConsumerTipsInfo extends AbstractEntity {

    private static final long serialVersionUID = 5533835853318176586L;

    private String monthTxt;
    
    private String monthNum;
    
    private String articleId;

    public String getMonthTxt() {
        return monthTxt;
    }

    public void setMonthTxt(String monthTxt) {
        this.monthTxt = monthTxt;
    }

    public String getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(String monthNum) {
        this.monthNum = monthNum;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
