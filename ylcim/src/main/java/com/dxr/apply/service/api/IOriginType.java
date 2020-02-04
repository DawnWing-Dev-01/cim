package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.system.entity.SystemConstant;

/**
 * @description: <投诉结果来源类型服务接口层>
 * @author: w.xL
 * @date: 2018-4-30
 */
public interface IOriginType {

    /**
     * 获取来源类型page数据, EasyUI格式
     * @param page
     * @param rows
     * @param systemConstant
     * @return
     * @throws ServiceException
     */
    public String getOriginTypePage(int page, int rows, SystemConstant constant)
            throws ServiceException;

    /**
     * 保存常量
     * @param constant
     * @return
     * @throws ServiceException
     */
    public String saveOriginType(SystemConstant constant)
            throws ServiceException;

    /**
     * 更新常量
     * @param constant
     * @return
     * @throws ServiceException
     */
    public String updateOriginType(SystemConstant constant)
            throws ServiceException;

    /**
     * 删除常量
     * @param constantId
     * @return
     * @throws ServiceException
     */
    public String deleteOriginType(String constantId) throws ServiceException;

    /**
     * 获取常量详细信息
     * @param constantId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String constantId) throws ServiceException;

    /**
     * 获取来源类型下拉框
     * @return
     * @throws ServiceException
     */
    public String getOriginTypeCommbox() throws ServiceException;
}
