package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <投诉结果导入导出实体类>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class ComplaintResultInfo extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 来源Id
     */
    private String originTypeId;
    
    /**
     * 经营者/企业Id
     */
    private String dealerId;
    
    /**
     * 投诉是否公示
     */
    private Integer isPublicity = ConstGlobal.COMPLAINT_IS_PUBLICITY_FALSE;
    
    /**
     * 登记编号
     */
    private String enterCode;
    
    /**
     * 提供方姓名
     */
    private String providerName;
    
    /**
     * 投诉内容
     */
    private String complaints;
    
    /**
     * 登记日期
     */
    private Date enterDate;
    
    /**
     * 投诉类型
     */
    private String complaintType;
    
    /**
     * 办理情况状态
     */
    private String handleStatus;
    
    /**
     * 登记部门
     */
    private String enterDept;
    
    /**
     * 处理部门
     */
    private String handleDept;
    
    /**
     * 办理期限
     */
    private Date handleDeadline;
    
    /**
     * 初查反馈时间
     */
    private Date netCreatedate;
    
    /**
     * 详细情况说明
     */
    private String netRemark;
    
    /**
     * 不受理原因
     */
    private String netResult;
    
    /**
     * 办结
     */
    private String finishKnot;
    
    /**
     * 办结日期
     */
    private Date finishKnotDate;
    
    /**
     * 处理人
     */
    private String handlePeople;
    
    /**
     * 扩展字段1
     */
    private String ext01;
    
    /**
     * 扩展字段2
     */
    private String ext02;
    
    /**
     * 扩展字段3
     */
    private String ext03;
    
    /**
     * 扩展字段4
     */
    private String ext04;
    
    /**
     * 扩展字段5
     */
    private String ext05;
    
    /**
     * 创建人
     */
    private String creator;

    public String getOriginTypeId() {
        return originTypeId;
    }

    public void setOriginTypeId(String originTypeId) {
        this.originTypeId = originTypeId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getIsPublicity() {
        return isPublicity;
    }

    public void setIsPublicity(Integer isPublicity) {
        this.isPublicity = isPublicity;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getEnterDept() {
        return enterDept;
    }

    public void setEnterDept(String enterDept) {
        this.enterDept = enterDept;
    }

    public String getHandleDept() {
        return handleDept;
    }

    public void setHandleDept(String handleDept) {
        this.handleDept = handleDept;
    }

    public Date getHandleDeadline() {
        return handleDeadline;
    }

    public void setHandleDeadline(Date handleDeadline) {
        this.handleDeadline = handleDeadline;
    }

    public Date getNetCreatedate() {
        return netCreatedate;
    }

    public void setNetCreatedate(Date netCreatedate) {
        this.netCreatedate = netCreatedate;
    }

    public String getNetRemark() {
        return netRemark;
    }

    public void setNetRemark(String netRemark) {
        this.netRemark = netRemark;
    }

    public String getNetResult() {
        return netResult;
    }

    public void setNetResult(String netResult) {
        this.netResult = netResult;
    }

    public String getFinishKnot() {
        return finishKnot;
    }

    public void setFinishKnot(String finishKnot) {
        this.finishKnot = finishKnot;
    }

    public Date getFinishKnotDate() {
        return finishKnotDate;
    }

    public void setFinishKnotDate(Date finishKnotDate) {
        this.finishKnotDate = finishKnotDate;
    }

    public String getHandlePeople() {
        return handlePeople;
    }

    public void setHandlePeople(String handlePeople) {
        this.handlePeople = handlePeople;
    }

    public String getExt01() {
        return ext01;
    }

    public void setExt01(String ext01) {
        this.ext01 = ext01;
    }

    public String getExt02() {
        return ext02;
    }

    public void setExt02(String ext02) {
        this.ext02 = ext02;
    }

    public String getExt03() {
        return ext03;
    }

    public void setExt03(String ext03) {
        this.ext03 = ext03;
    }

    public String getExt04() {
        return ext04;
    }

    public void setExt04(String ext04) {
        this.ext04 = ext04;
    }

    public String getExt05() {
        return ext05;
    }

    public void setExt05(String ext05) {
        this.ext05 = ext05;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
