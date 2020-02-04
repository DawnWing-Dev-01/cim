package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <经营者信息>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerInfo extends AbstractEntity {

    private static final long serialVersionUID = 5594320300359434410L;

    /**
     * 简称, 可多个用分号隔开(,)
     */
    private String simpleName;

    /**
     * 主营项目
     */
    private String mainProject;

    /**
     * 经营地址
     */
    private String dealerAddress;

    /**
     * 注册地址
     */
    private String registerAddress;

    /**
     * 法人姓名
     */
    private String legalPerson;

    /**
     * 营业执照号
     */
    private String licenseNo;

    /**
     * 所属行业
     */
    private String industryId;

    /**
     * 所属行业名称, 只展示不存储
     */
    private String industryName;

    /**
     * 经营类型, 经营者性质（私企或其他）
     */
    private String dealerTypeId;

    /**
     * 经营类型名称, 只展示不存储
     */
    private String dealerTypeName;

    /**
     * 管辖单位(组织机构Id)
     */
    private String jurisdiction;

    /**
     * 管辖单位称呼
     */
    private String jurisdictionName;

    /**
     * 经营者二维码
     */
    private String qrcode;

    /**
     * 联系电话
     */
    private String linkTel;
    
    /**
     * 根据创建日期查询开始日期, 不用持久化到数据库
     */
    private Date startDate;
    
    /**
     * 根据创建日期查询结束日期, 不用持久化到数据库
     */
    private Date endDate;

    public DealerInfo() {
        super();
    }

    /**
     * @param dealerInfo
     * @param industryName
     */
    public DealerInfo(DealerInfo dealerInfo, String industryName,
            String dealerTypeName, String jurisdictionName) {
        this(dealerInfo.getId(), dealerInfo.getName(), dealerInfo
                .getLegalPerson(), dealerInfo.getMainProject(), dealerInfo
                .getDealerAddress(), dealerInfo.getLicenseNo(), dealerInfo
                .getCreateDate(), dealerInfo.getUpdateDate(), dealerInfo
                .getSort(), dealerInfo.getStatus(), dealerInfo.getRemark(),
                dealerInfo.getIndustryId(), dealerInfo.getSimpleName(),
                dealerInfo.getRegisterAddress(), dealerInfo.getQrcode(),
                dealerInfo.getJurisdiction(), dealerInfo.getLinkTel(),
                industryName, dealerInfo.getDealerTypeId(), dealerTypeName,
                jurisdictionName);
    }

    /**
     * @param id
     * @param name
     * @param legalPerson
     * @param mainProject
     * @param dealerAddress
     * @param createDate
     * @param updateDate
     * @param sort
     * @param status
     * @param remark
     * @param industryId
     * @param industryName
     */
    public DealerInfo(String id, String name, String legalPerson,
            String mainProject, String dealerAddress, String licenseNo,
            Date createDate, Date updateDate, Integer sort, Integer status,
            String remark, String industryId, String simpleName,
            String registerAddress, String qrcode, String jurisdiction,
            String linkTel, String industryName, String dealerTypeId,
            String dealerTypeName, String jurisdictionName) {
        this.setId(id);
        this.setName(name);
        this.setLegalPerson(legalPerson);
        this.setMainProject(mainProject);
        this.setDealerAddress(dealerAddress);
        this.setLicenseNo(licenseNo);
        this.setCreateDate(createDate);
        this.setUpdateDate(updateDate);
        this.setSort(sort);
        this.setStatus(status);
        this.setRemark(remark);
        this.setIndustryId(industryId);
        this.setIndustryName(industryName);
        this.setDealerTypeId(dealerTypeId);
        this.setDealerTypeName(dealerTypeName);
        this.setSimpleName(simpleName);
        this.setRegisterAddress(registerAddress);
        this.setQrcode(qrcode);
        this.setJurisdiction(jurisdiction);
        this.setLinkTel(linkTel);
        this.setJurisdictionName(jurisdictionName);
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getMainProject() {
        return mainProject;
    }

    public void setMainProject(String mainProject) {
        this.mainProject = mainProject;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

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

    public String getDealerTypeId() {
        return dealerTypeId;
    }

    public void setDealerTypeId(String dealerTypeId) {
        this.dealerTypeId = dealerTypeId;
    }

    public String getDealerTypeName() {
        return dealerTypeName;
    }

    public void setDealerTypeName(String dealerTypeName) {
        this.dealerTypeName = dealerTypeName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
