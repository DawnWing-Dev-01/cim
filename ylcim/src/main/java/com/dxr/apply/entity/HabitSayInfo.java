package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <流程审核常用意见实体类>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class HabitSayInfo extends AbstractEntity {

    private static final long serialVersionUID = -1501680449498449457L;

    /**
     * 用户Id
     */
    private String userId;
    
    /**
     * 意见内容
     */
    private String sayDetail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSayDetail() {
        return sayDetail;
    }

    public void setSayDetail(String sayDetail) {
        this.sayDetail = sayDetail;
    }
}
