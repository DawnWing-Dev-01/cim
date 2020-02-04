package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <经营者类型信息>
 * @author: w.xL
 * @date: 2018-2-28
 */
public class DealerTypeInfo extends AbstractEntity{

    private static final long serialVersionUID = -2218263998230605760L;

    /**
     * 经营者类型编码
     */
    private String typeCode;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
