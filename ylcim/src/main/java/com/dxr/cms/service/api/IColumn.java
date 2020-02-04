package com.dxr.cms.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.cms.entity.ColumnInfo;

/**
 * @description: <CMS 栏目服务接口层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public interface IColumn extends IBasalMgr<ColumnInfo> {

    /**
     * 获取栏目列表, EasyUI格式
     * @param page
     * @param rows
     * @param columnInfo
     * @return
     * @throws ServiceException
     */
    public String getColumnPage(int page, int rows, ColumnInfo columnInfo)
            throws ServiceException;

    /**
     * 保存栏目
     * @param columnInfo
     * @return
     * @throws ServiceException
     */
    public String saveColumn(ColumnInfo columnInfo) throws ServiceException;

    /**
     * 更新栏目
     * @param columnInfo
     * @return
     * @throws ServiceException
     */
    public String updateColumn(ColumnInfo columnInfo) throws ServiceException;

    /**
     * 删除栏目
     * @param columnId
     * @return
     * @throws ServiceException
     */
    public String deleteColumn(String columnId) throws ServiceException;

    /**
     * @param columnId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String columnId) throws ServiceException;
}
