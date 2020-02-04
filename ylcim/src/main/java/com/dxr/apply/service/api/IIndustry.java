package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.IndustryInfo;

/**
 * @description: <行业信息管理服务接口层IService>
 * @author: w.xL
 * @date: 2018-2-27
 */
public interface IIndustry extends IBasalMgr<IndustryInfo> {

    /**
     * @param page
     * @param rows
     * @param industryInfo
     * @return
     * @throws ServiceException
     */
    public String getIndustryPage(int page, int rows, IndustryInfo industryInfo)
            throws ServiceException;

    /**
     * @param industryInfo
     * @return
     * @throws ServiceException
     */
    public String saveIndustry(IndustryInfo industryInfo)
            throws ServiceException;

    /**
     * @param industryInfo
     * @return
     * @throws ServiceException
     */
    public String updateIndustry(IndustryInfo industryInfo)
            throws ServiceException;

    /**
     * @param industryInfo
     * @return
     * @throws ServiceException
     */
    public String updateThreshold(IndustryInfo industryInfo)
            throws ServiceException;

    /**
     * @param industryIId
     * @return
     * @throws ServiceException
     */
    public String deleteIndustry(String industryId) throws ServiceException;

    /**
     * @param industryIId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String industryId) throws ServiceException;

    /**
     * 获取行业信息下拉列表
     * @return
     * @throws ServiceException
     */
    public String getIndustryCommBox() throws ServiceException;

    /**
     * 根据行业名称获取行业信息
     * @param name
     * @return
     * @throws ServiceException
     */
    public IndustryInfo getIndustryByName(String name) throws ServiceException;

    /**
     * 获取预警阀值不为空的行业列表
     * @return
     * @throws ServiceException
     */
    public List<IndustryInfo> getThresholdNotNulltIndustryList()
            throws ServiceException;

    /**
     * 获取行业列表
     * @return
     * @throws ServiceException
     */
    public List<IndustryInfo> getIndustryList() throws ServiceException;
}
