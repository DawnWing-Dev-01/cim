package com.dxr.webui.wechat.service.api;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.webui.wechat.vo.WxAccessToken;
import com.dxr.webui.wechat.vo.WxJsApiTicket;
import com.dxr.webui.wechat.vo.WxUserInfo;

/**
 * @description: <微信公众号服务接口层>
 * @author: w.xL
 * @date: 2018-3-1
 */
public interface IWeChat {

    /**
     * 获取微信页面 JSSDK 的页面配置
     * @param url
     * @return
     * @throws ServiceException
     */
    public Map<String, String> getPageConfig(String url)
            throws ServiceException;

    /**
     * 从缓存中获取唯一接口调用凭据Access_Token<br>
     * 缓存保留2个小时, 若取不到则调用接口获取最新的凭据
     * @return
     * @throws ServiceException
     */
    public String getCacheToken() throws ServiceException;
    
    /**
     * 强制刷新唯一接口调用凭据Access_Token<br>
     * 缓存保留2个小时
     * @throws ServiceException
     */
    public void refreshCacheToken() throws ServiceException;

    /**
     * 获取最新的公众号的全局唯一接口调用凭据Access_Token<br>
     * @return
     * @throws ServiceException
     */
    public WxAccessToken getNewToken() throws ServiceException;

    /**
     * 从缓存中获取调用微信JS接口的临时票据JsApiTicket<br>
     * 缓存保留2个小时, 若取不到则调用接口获取最新的临时票据
     * @param accessToken 公众号全局唯一接口调用凭据
     * @return
     * @throws ServiceException
     */
    public String getCacheTicket(String accessToken) throws ServiceException;

    /**
     * 强制刷新调用微信JS接口的临时票据JsApiTicket<br>
     * 缓存保留2个小时
     * @throws ServiceException
     */
    public void refreshCacheTicket() throws ServiceException;
    
    /**
     * 获取最新的调用微信JS接口的临时票据JsApiTicket<br>
     * @param accessToken 公众号全局唯一接口调用凭据
     * @return
     * @throws ServiceException
     */
    public WxJsApiTicket getNewTicket(String accessToken)
            throws ServiceException;

    /**
     * 微信网页授权
     * @param req
     * @param url 当前url
     * @param code 获取网页授权access_token的code
     * @param state
     * @return
     * @throws ServiceException, UnsupportedEncodingException
     */
    public Map<String, Object> wechatAuthorize(HttpServletRequest req,
            String url, String code, String state) throws ServiceException,
            UnsupportedEncodingException;

    /**
     * 获取公众号关注用户列表
     * @param nextOpenid
     * @return
     * @throws ServiceException
     */
    public String getWeChatUserList(String nextOpenId) throws ServiceException;

    /**
     * 获取微信用户基本信息(UnionID机制)
     * @param accessToken
     * @param openId
     * @return
     * @throws Exception
     */
    public WxUserInfo getWeChatUserInfo(String accessToken, String openId)
            throws Exception;

    /**
     * 判断微信用户是否已关注公众号
     * @param req
     * @param code
     * @param state
     * @return WxUserInfo subscribe用户是否订阅该公众号标识  1-已订阅; 0-为订阅;
     * @throws ServiceException
     */
    public WxUserInfo wechatSubscribe(HttpServletRequest req, String code,
            String state) throws ServiceException;
}
