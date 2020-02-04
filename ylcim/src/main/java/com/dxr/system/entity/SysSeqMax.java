package com.dxr.system.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <流水序列实体类>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class SysSeqMax extends AbstractEntity {

    private static final long serialVersionUID = 8852674107257741737L;

    /**
     * 序列Id
     */
    private String seqId;
    
    /**
     * 最大序列
     */
    private Integer maxNo;
    
    /**
     * 最后更新日期
     */
    private Date lastDate;

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public Integer getMaxNo() {
        return maxNo;
    }

    public void setMaxNo(Integer maxNo) {
        this.maxNo = maxNo;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
