package com.dxr.comm.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.webui.wechat.service.api.IWeChat;

/**
 * @description: <微信缓存(全局token&调用微信JS接口的临时票据)刷新任务, 2个小时刷新一次>
 * @author: w.xL
 * @date: 2018-4-22
 */
public class WechatCacheRefreshJob {

    private static final Logger logger = LoggerFactory
            .getLogger(WechatCacheRefreshJob.class);

    @Autowired
    private IWeChat weChatMgr;

    /**
     * 运行任务方法
     */
    public void refreshCacheTask() {
        logger.info("---------------------------- Start Refresh Wechat Cache -------------------------------");
        try {
            // 刷新access_token
            weChatMgr.refreshCacheToken();
            // 刷新jsapi_ticket
            weChatMgr.refreshCacheTicket();
        } catch (ServiceException e) {
            logger.error("Wechat Cache Refresh Task error...");
        }
        logger.info("---------------------------------------------------------------------------------------");
    }
}
