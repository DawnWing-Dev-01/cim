package com.dxr.webui.wechat.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.comm.utils.SimpleHttpClient;
import com.dxr.webui.wechat.service.api.ITemplate;
import com.dxr.webui.wechat.service.api.IWeChat;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: <微信公众号模板消息服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-10
 */
public class TemplateMgr implements ITemplate {

    private static final Logger logger = LoggerFactory
            .getLogger(TemplateMgr.class);

    @Autowired
    private IWeChat weChatMgr;

    private String templateMsgSendUrl;

    @Override
    public void sendMessage(String jsonBody) throws ServiceException {
        threadRun(jsonBody);
    }

    /**
     * 多线程执行
     */
    private void threadRun(String jsonBody) {
        Runnable runnable = new Runnable() {
            private String jsonBody;

            @Override
            public void run() {
                try {
                    sendMessage4Runnable(jsonBody);
                } catch (ServiceException e) {
                    logger.error("TemplateMgr.sendMessage4Runnable is error...");
                }
            }

            public Runnable setJsonBody(String jsonBody) {
                this.jsonBody = jsonBody;
                return this;
            }
        }.setJsonBody(jsonBody);
        new Thread(runnable).start();
    }

    /**
     * 多线程发送消息, 即时发送不了通知也不会影响业务不能使用
     * @param jsonBody
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private void sendMessage4Runnable(String jsonBody) throws ServiceException {
        try {
            String token = weChatMgr.getCacheToken();

            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("access_token", token);
            String sendMsgUri = SimpleHttpClient.setQueryString(
                    templateMsgSendUrl, param);

            String sendRes = SimpleHttpClient.invokerPost(sendMsgUri, null,
                    null, jsonBody);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(sendRes,
                    LinkedHashMap.class);
            Integer errcode = Integer.valueOf(linkedmap.get("errcode")
                    .toString());
            if (errcode != null && errcode != 0) {
                if (errcode == 40001 || errcode == 42001) {
                    // 刷新统一调用凭证
                    weChatMgr.refreshCacheToken();
                    // 递归调用下自己
                    sendMessage4Runnable(jsonBody);
                    return;
                }
                throw new ServiceException(
                        "[WeChat send/message] error : json is : " + sendRes);
            }
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (Exception e) {
            throw new ServiceException("sendMessage() is error...", e);
        }
    }

    public String getTemplateMsgSendUrl() {
        return templateMsgSendUrl;
    }

    public void setTemplateMsgSendUrl(String templateMsgSendUrl) {
        this.templateMsgSendUrl = templateMsgSendUrl;
    }

}
