package com.dxr.system.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.WeChatViewInfo;

/**
 * @description: <微信视图服务接口层>
 * @author: w.xL
 * @date: 2018-3-2
 */
public interface IWeChatView extends IBasalMgr<WeChatViewInfo> {

    /**
     * 根据viewId获取微信视图对象
     * @param viewId
     * @return
     * @throws ServiceException
     */
    public WeChatViewInfo getWeChatView(String viewId) throws ServiceException;
}
