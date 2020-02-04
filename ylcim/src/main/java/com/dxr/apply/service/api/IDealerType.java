package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.DealerTypeInfo;

/**
 * @description: <经营者类型管理服务接口层IService>
 * @author: w.xL
 * @date: 2018-2-27
 */
public interface IDealerType extends IBasalMgr<DealerTypeInfo> {

    /**
     * @param page
     * @param rows
     * @param dealerTypeInfo
     * @return
     * @throws ServiceException
     */
    public String getTypePage(int page, int rows, DealerTypeInfo dealerTypeInfo)
            throws ServiceException;

    /**
     * @param dealerTypeInfo
     * @return
     * @throws ServiceException
     */
    public String saveType(DealerTypeInfo dealerTypeInfo)
            throws ServiceException;

    /**
     * @param dealerTypeInfo
     * @return
     * @throws ServiceException
     */
    public String updateType(DealerTypeInfo dealerTypeInfo)
            throws ServiceException;

    /**
     * @param dealerTypeId
     * @return
     * @throws ServiceException
     */
    public String deleteType(String dealerTypeId) throws ServiceException;

    /**
     * @param dealerTypeId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String dealerTypeId) throws ServiceException;

    /**
     * 获取行业信息下拉列表
     * @return
     * @throws ServiceException
     */
    public String getTypeCommBox() throws ServiceException;
}
