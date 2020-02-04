package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <行业信息>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class IndustryInfo extends AbstractEntity {

    private static final long serialVersionUID = 2406515262774611454L;

    /**
     * 行业编码
     */
    private String code;
    
    /**
     * 预警阀值
     */
    private Integer warningThreshold;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(Integer warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

}
