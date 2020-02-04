package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.WarningPublishRemind;

/**
 * @description: <消费预警提示服务接口层>
 * @author: w.xL
 * @date: 2018-4-21
 */
public interface IWarningPublishRemind extends IBasalMgr<WarningPublishRemind> {

    /**
     * @param wprInfo
     * @return
     * @throws ServiceException
     */
    public String getWarningPublishRemindPage(WarningPublishRemind wprInfo)
            throws ServiceException;

    /**
     * 保存
     * @param wprInfo
     * @throws ServiceException
     */
    public void saveWarningPublishRemind(WarningPublishRemind wprInfo)
            throws ServiceException;

    /**
     * 更新显示状态
     * @param wprInfo
     * @throws ServiceException
     */
    public void updateShowType(WarningPublishRemind wprInfo)
            throws ServiceException;
}
