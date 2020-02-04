package com.dxr.webui.wechat.service.api;

import com.dawnwing.framework.core.ServiceException;

/**
 * @description: <微信公众号模板消息服务接口层>
 * @author: w.xL
 * @date: 2018-4-10
 */
public interface ITemplate {

    /**
     * 公众号向用户推送消息
     * @param onpenId
     * @throws ServiceException
     */
    public void sendMessage(String jsonBody) throws ServiceException;
}
