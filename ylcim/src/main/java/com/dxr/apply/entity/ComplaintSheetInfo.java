package com.dxr.apply.entity;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <投诉登记表实体类>
 * @author: w.xL
 * @date: 2018-3-13
 */
public class ComplaintSheetInfo extends AbstractEntity {

    private static final long serialVersionUID = -4932451098743705007L;

    /**
     * 投诉编号
     */
    private String complaintCode;

    /**
     * 投诉人姓名/举报人, 微信端不体现
     */
    private String informerName;

    /**
     * 投诉人性别, 微信端不体现
     */
    private Integer informerGender;

    /**
     * 投诉人年龄, 微信端不体现
     */
    private Integer informerAge;

    /**
     * 投诉人住址, 微信端不体现
     */
    private String informerAddress;

    /**
     * 投诉人联系电话
     */
    private String informeriPhone;
    
    /**
     * 投诉类型, 实名投诉/匿名投诉
     */
    private String complaintType = ConstGlobal.COMPLAINT_TYPE_ANONYMOU;

    /**
     * 投诉人微信号(openId), 微信端实名举报会存储微信openId
     * 
     */
    private String informerWeChatOpenId;

    /**
     * 被投诉的经营者, 微信端不体现, 后端关联经营者
     */
    private String dealerId;

    /**
     * 被投诉经营者名称, 微信端为用户填写的名称, 后台录入为经营者名称
     */
    private String dealerName;

    /**
     * 被投诉经营者联系人, 微信端不体现
     */
    private String dealerLinkman;

    /**
     * 被投诉经营者经营地址, 微信端为用户填写的经营地址, 后台录入为经营者经营地址
     */
    private String dealerAddress;

    /**
     * 被投诉经营者联系电话, 微信端不体现
     */
    private String dealeriPhone;
    
    /**
     * 被投诉经营者管辖单位, 微信端不体现
     */
    private String dealerJurisdiction;

    /**
     * 投诉理由、事实以及请求
     */
    private String complaintReason;

    /**
     * 登记单位
     */
    private String registerUnit;

    /**
     * 录入员Id
     */
    private String reporterId;
    
    /**
     * 投诉来源
     */
    private String complaintSource = ConstGlobal.COMPLAINT_SOURCE_WECHAT_ENTRY;
    
    /**
     * 投诉单流程状态
     */
    private String flowStatus = ConstGlobal.COMPLAINT_FLOW_STATUS_DRAFT;
    
    /**
     * 投诉是否公示
     */
    private Integer isPublicity = ConstGlobal.COMPLAINT_IS_PUBLICITY_FALSE;
    
    /**
     * 举报分类: 1-举报, 2-投诉, 3-咨询;
     */
    private Integer reportClassify;
    
    /**
     * 微信服务器上传投诉取证照片Ids
     */
    private String imageServerIds;

    public String getComplaintCode() {
        return complaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        this.complaintCode = complaintCode;
    }

    public String getInformerName() {
        return informerName;
    }

    public void setInformerName(String informerName) {
        this.informerName = informerName;
    }

    public Integer getInformerGender() {
        return informerGender;
    }

    public void setInformerGender(Integer informerGender) {
        this.informerGender = informerGender;
    }

    public Integer getInformerAge() {
        return informerAge;
    }

    public void setInformerAge(Integer informerAge) {
        this.informerAge = informerAge;
    }

    public String getInformerAddress() {
        return informerAddress;
    }

    public void setInformerAddress(String informerAddress) {
        this.informerAddress = informerAddress;
    }

    public String getInformeriPhone() {
        return informeriPhone;
    }

    public void setInformeriPhone(String informeriPhone) {
        this.informeriPhone = informeriPhone;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getInformerWeChatOpenId() {
        return informerWeChatOpenId;
    }

    public void setInformerWeChatOpenId(String informerWeChatOpenId) {
        this.informerWeChatOpenId = informerWeChatOpenId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerLinkman() {
        return dealerLinkman;
    }

    public void setDealerLinkman(String dealerLinkman) {
        this.dealerLinkman = dealerLinkman;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealeriPhone() {
        return dealeriPhone;
    }

    public void setDealeriPhone(String dealeriPhone) {
        this.dealeriPhone = dealeriPhone;
    }

    public String getDealerJurisdiction() {
        return dealerJurisdiction;
    }

    public void setDealerJurisdiction(String dealerJurisdiction) {
        this.dealerJurisdiction = dealerJurisdiction;
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason;
    }

    public String getRegisterUnit() {
        return registerUnit;
    }

    public void setRegisterUnit(String registerUnit) {
        this.registerUnit = registerUnit;
    }

	public String getReporterId() {
		return reporterId;
	}

	public void setReporterId(String reporterId) {
		this.reporterId = reporterId;
	}

	public String getComplaintSource() {
        return complaintSource;
    }

    public void setComplaintSource(String complaintSource) {
        this.complaintSource = complaintSource;
    }

    public String getImageServerIds() {
        return imageServerIds;
    }

    public void setImageServerIds(String imageServerIds) {
        this.imageServerIds = imageServerIds;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Integer getIsPublicity() {
        return isPublicity;
    }

    public void setIsPublicity(Integer isPublicity) {
        this.isPublicity = isPublicity;
    }

    public Integer getReportClassify() {
        return reportClassify;
    }

    public void setReportClassify(Integer reportClassify) {
        this.reportClassify = reportClassify;
    }
}
