package com.dxr.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.system.dao.WeChatViewDao;
import com.dxr.system.entity.WeChatViewInfo;
import com.dxr.system.service.api.IWeChatView;

/**
 * @description: <微信视图服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WeChatViewMgr extends BasalMgr<WeChatViewInfo> implements IWeChatView {

    @Autowired
    private WeChatViewDao wechatViewDao;

    @Override
    public WeChatViewInfo getWeChatView(String viewId) throws ServiceException {
        return wechatViewDao.get(viewId);
    }

    @Override
    public WeChatViewInfo getDbObject(String id) throws ServiceException {
        return wechatViewDao.get(id);
    }

}
