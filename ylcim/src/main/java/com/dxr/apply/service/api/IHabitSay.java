package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.HabitSayInfo;

/**
 * @description: <流程审核常用意见服务接口层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public interface IHabitSay extends IBasalMgr<HabitSayInfo> {

    /**
     * 获取常用意见列表
     * @return
     * @throws ServiceException
     */
    public String getHabitSayList() throws ServiceException;

    /**
     * 保存常用意见
     * @param sayDetail
     * @return
     * @throws ServiceException
     */
    public void saveHabitSay(String sayDetail) throws ServiceException;

    /**
     * 删除常用意见
     * @param habitSayId
     * @throws ServiceException
     */
    public void deleteHabitSay(String habitSayId) throws ServiceException;
}