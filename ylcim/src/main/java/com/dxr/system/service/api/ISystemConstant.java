package com.dxr.system.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.SystemConstant;

/**
 * @description: <系统常量服务接口类>
 * @author: w.xL
 * @date: 2018-3-28
 */
public interface ISystemConstant extends IBasalMgr<SystemConstant> {

    /**
     * 获取系统常量page数据, EasyUI格式
     * @param page
     * @param rows
     * @param systemConstant
     * @return
     * @throws ServiceException
     */
    public String getSystemConstantPage(int page, int rows,
            SystemConstant constant) throws ServiceException;

    /**
     * 获取系统常量列表
     * @param page
     * @param rows
     * @param constant
     * @return
     * @throws ServiceException
     */
    public List<SystemConstant> findSystemConstantList(int page, int rows,
            SystemConstant constant) throws ServiceException;

    /**
     * 获取下拉框数据
     * @param group
     * @return
     * @throws ServiceException
     */
    public String getSystemConstantCommbox(String group)
            throws ServiceException;

    /**
     * 设置缓存
     * @throws ServiceException
     */
    public Integer setCache() throws ServiceException;

    /**
     * 保存常量
     * @param constant
     * @return
     * @throws ServiceException
     */
    public String saveSystemConstant(SystemConstant constant)
            throws ServiceException;

    /**
     * 更新常量
     * @param constant
     * @return
     * @throws ServiceException
     */
    public String updateSystemConstant(SystemConstant constant)
            throws ServiceException;

    /**
     * 删除常量
     * @param constantId
     * @return
     * @throws ServiceException
     */
    public String deleteSystemConstant(String constantId)
            throws ServiceException;

    /**
     * 获取常量详细信息
     * @param constantId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String constantId) throws ServiceException;
}
