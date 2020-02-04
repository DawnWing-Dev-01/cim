package com.dxr.webui.wechat.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.utils.SimpleHttpClient;
import com.dxr.webui.wechat.service.api.IWeChat;
import com.dxr.webui.wechat.tools.WeChatSignAlgor;
import com.dxr.webui.wechat.vo.WxAccessToken;
import com.dxr.webui.wechat.vo.WxJsApiTicket;
import com.dxr.webui.wechat.vo.WxOauthAccessToken;
import com.dxr.webui.wechat.vo.WxUserInfo;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: <微信公众号服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-1
 */
public class WeChatMgr implements IWeChat {

    @Autowired
    private Cache<String, Object> wechatTokenCache;

    private String wechatAppId;
    private String wechatAppSecret;

    private String accessTokenApiUrl;
    private String jsApiTicketApiUrl;
    private String authorizeCodeApiUrl;
    private String authorizeAccessTokenApiUrl;
    private String authorizeRefreshTokenApiUrl;
    private String authorizeUserInfoApiUrl;
    private String authorizeCheckTokenApiUrl;
    private String userListUrl;
    private String userInfoUrl;

    private String wechatCfgDebug;
    private String wechatCfgJsApiList;
    private String hideMenuArray;

    private String baiduMapKey;

    @Override
    public Map<String, String> getPageConfig(String url)
            throws ServiceException {
        // 获取微信全局唯一token
        String token = getCacheToken();
        // 获取调用微信JS接口的临时票据
        String ticket = getCacheTicket(token);
        Map<String, String> wxConfig = WeChatSignAlgor.sign(ticket, url);
        // 设置需要的额外配置
        wxConfig.put("debug", wechatCfgDebug);
        // 开发者ID
        wxConfig.put("appId", wechatAppId);
        // 需要调用的接口API列表
        wxConfig.put("jsApiList", wechatCfgJsApiList);
        // 微信公众号需要隐藏的按钮
        wxConfig.put("hideMenuArray", hideMenuArray);
        // 百度地图开发者密钥AK
        wxConfig.put("baiduMapKey", baiduMapKey);
        return wxConfig;
    }

    @Override
    public String getCacheToken() throws ServiceException {
        String dynamicAccessToken = null;
        try {
            // No1: 从缓存中获取微信全局token
            WxAccessToken tokenCache = (WxAccessToken) wechatTokenCache
                    .get("access_token");
            if (ObjectUtils.isNotEmpty(tokenCache)) {
                return tokenCache.getToken();
            }

            // No2: 若缓存中不存在access_token, 需要动态获取最新的
            WxAccessToken newtoken = getNewToken();
            // 将获取到的access_token放入缓存
            wechatTokenCache.put("access_token", newtoken);
            dynamicAccessToken = newtoken.getToken();
        } catch (Exception e) {
            throw new ServiceException("getAccessToken() is error...", e);
        }
        return dynamicAccessToken;
    }

    @Override
    public void refreshCacheToken() throws ServiceException {
        // 直接动态获取最新的, 应用于access_token无效场景
        WxAccessToken newtoken = getNewToken();
        // 将获取到的access_token放入缓存
        wechatTokenCache.put("access_token", newtoken);
    }

    @SuppressWarnings("unchecked")
    @Override
    public WxAccessToken getNewToken() throws ServiceException {
        WxAccessToken wxAccessToken = null;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("grant_type", "client_credential");
            param.put("appid", wechatAppId);
            param.put("secret", wechatAppSecret);

            String res = SimpleHttpClient.invokerGet(accessTokenApiUrl, null,
                    param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[access_token] error : res exception...");
            }
            boolean hasKey = linkedmap.containsKey("access_token");
            if (!hasKey) {
                throw new ServiceException("[invoker error] json is : " + res);
            }

            // No3: 获取到动态获取的access_token
            String dynamicAccessToken = String.valueOf(linkedmap
                    .get("access_token"));
            Integer expiresIn = Integer.valueOf(String.valueOf(linkedmap
                    .get("expires_in")));
            wxAccessToken = new WxAccessToken();
            wxAccessToken.setToken(dynamicAccessToken);
            wxAccessToken.setExpiresIn(expiresIn);
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("getNewToken() is error...", e);
        }
        return wxAccessToken;
    }

    @Override
    public String getCacheTicket(String accessToken) throws ServiceException {
        String dynamicJsApiTicket = null;
        try {
            // No1: 从缓存中获取微信全局token
            WxJsApiTicket ticketCache = (WxJsApiTicket) wechatTokenCache
                    .get("jsapi_ticket");
            if (ObjectUtils.isNotEmpty(ticketCache)) {
                return ticketCache.getTicket();
            }

            // No2: 若缓存中不存在access_token, 需要动态获取
            WxJsApiTicket newTicket = getNewTicket(accessToken);
            // 将获取到的access_token放入缓存
            wechatTokenCache.put("jsapi_ticket", newTicket);
            dynamicJsApiTicket = newTicket.getTicket();
        } catch (Exception e) {
            throw new ServiceException("getJsApiTicket() is error...", e);
        }
        return dynamicJsApiTicket;
    }
    
    @Override
    public void refreshCacheTicket() throws ServiceException {
        String token = getCacheToken();
        // 直接动态获取最新的
        WxJsApiTicket newTicket = getNewTicket(token);
        // 将获取到的access_token放入缓存
        wechatTokenCache.put("jsapi_ticket", newTicket);
    }

    @SuppressWarnings("unchecked")
    @Override
    public WxJsApiTicket getNewTicket(String accessToken)
            throws ServiceException {
        WxJsApiTicket wxJsApiTicket = null;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("type", "jsapi");
            param.put("access_token", accessToken);

            String res = SimpleHttpClient.invokerGet(jsApiTicketApiUrl, null,
                    param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[jsapi_ticket] error : res exception...");
            }
            Integer errcode = Integer.valueOf(linkedmap.get("errcode")
                    .toString());
            String errmsg = String.valueOf(linkedmap.get("errmsg"));

            if (!(errcode == 0 && "ok".equals(errmsg))) {
                throw new ServiceException("[invoker error] json is : " + res);
            }

            // No3: 获取到动态获取的access_token
            String dynamicJsApiTicket = String.valueOf(linkedmap.get("ticket"));
            Integer expiresIn = Integer.valueOf(String.valueOf(linkedmap
                    .get("expires_in")));
            wxJsApiTicket = new WxJsApiTicket();
            wxJsApiTicket.setTicket(dynamicJsApiTicket);
            wxJsApiTicket.setExpiresIn(expiresIn);
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("getNewTicket() is error...", e);
        }
        return wxJsApiTicket;
    }

    @Override
    public Map<String, Object> wechatAuthorize(HttpServletRequest req,
            String url, String code, String state) throws ServiceException,
            UnsupportedEncodingException {
        Map<String, Object> res = new HashMap<String, Object>();
        HttpSession session = req.getSession();
        // 第一步：用户同意授权，获取code, 判断state是否有做过授权操作
        WxUserInfo wxUserInfo = (WxUserInfo) session.getAttribute("wxUserInfo");
        if (wxUserInfo != null) {
            // 不需要用户确认授权
            res.put("needToAuthorize", 0);
            res.put("wxUserInfo", wxUserInfo);
            return res;
        }
        if (state == null) {
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("appid", wechatAppId);
            // 授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
            param.put("redirect_uri", URLEncoder.encode(url, "UTF-8"));
            param.put("response_type", "code");
            param.put("scope", "snsapi_userinfo");
            param.put("state", "wxAuthorized#wechat_redirect");
            String authorizeCodeUrl = SimpleHttpClient.setQueryString(
                    authorizeCodeApiUrl, param);
            res.put("authorizeCodeUrl", authorizeCodeUrl);
            // 需要用户确认授权
            res.put("needToAuthorize", 1);
            return res;
        }

        // 不需要用户确认授权
        res.put("needToAuthorize", 0);

        // 第二步：通过code换取网页授权access_token（与基础支持中的access_token不同）
        // 判断session是否保存过wxOauthAccessToken
        WxOauthAccessToken wxOauthAccessToken = (WxOauthAccessToken) session
                .getAttribute("wxOauthAccessToken");
        if (wxOauthAccessToken != null) {
            // session中保存, 则校验下授权凭证是否有效
            boolean isInvalid = checkAuthToken(
                    wxOauthAccessToken.getAccessToken(),
                    wxOauthAccessToken.getOpenId());
            if (!isInvalid) {
                // 无效需要刷新下授权凭证
                wxOauthAccessToken = refreshOauthAccessToken(wxOauthAccessToken
                        .getRefreshToken());
            }
        } else {
            // 若没有保存, 需要重新获取, 然后再次放到session中
            wxOauthAccessToken = getOauthAccessToken(code);
            session.setAttribute("wxOauthAccessToken", wxOauthAccessToken);
        }

        // 第三步：拉取用户信息(需scope为 snsapi_userinfo)
        wxUserInfo = getWeCahtUserInfo(wxOauthAccessToken.getAccessToken(),
                wxOauthAccessToken.getOpenId());
        // 微信授权用户存在session中
        session.setAttribute("wxUserInfo", wxUserInfo);
        res.put("wxUserInfo", wxUserInfo);
        return res;
    }

    /**
     * 通过code换取网页授权access_token
     * @param code
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private WxOauthAccessToken getOauthAccessToken(String code)
            throws ServiceException {
        WxOauthAccessToken wxOauthAccessToken = null;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("appid", wechatAppId);
            param.put("secret", wechatAppSecret);
            param.put("code", code);
            param.put("grant_type", "authorization_code");
            String res = SimpleHttpClient.invokerGet(
                    authorizeAccessTokenApiUrl, null, param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[WeChat Oauth2] error : res exception...");
            }
            Object errcode = linkedmap.get("errcode");
            if (errcode != null) {
                throw new ServiceException("[WeChat Oauth2] error : json is : "
                        + res);
            }

            wxOauthAccessToken = new WxOauthAccessToken();
            String dynamicToken = String.valueOf(linkedmap.get("access_token"));
            wxOauthAccessToken.setAccessToken(dynamicToken);
            Integer expiresIn = Integer.valueOf(String.valueOf(linkedmap
                    .get("expires_in")));
            wxOauthAccessToken.setExpiresIn(expiresIn);
            String openId = String.valueOf(linkedmap.get("openid"));
            wxOauthAccessToken.setOpenId(openId);
            String refreshToken = String
                    .valueOf(linkedmap.get("refresh_token"));
            wxOauthAccessToken.setRefreshToken(refreshToken);
            String scope = String.valueOf(linkedmap.get("scope"));
            wxOauthAccessToken.setScope(scope);
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("getOauthAccessToken() is error...", e);
        }
        return wxOauthAccessToken;
    }

    /**
     * 刷新access_token（如果需要）
     * @param refreshToken
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private WxOauthAccessToken refreshOauthAccessToken(String refreshToken)
            throws ServiceException {
        WxOauthAccessToken wxOauthAccessToken = null;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("appid", wechatAppId);
            param.put("grant_type", "refresh_token");
            param.put("refresh_token", refreshToken);
            String res = SimpleHttpClient.invokerGet(
                    authorizeRefreshTokenApiUrl, null, param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[WeChat Oauth2] error : res exception...");
            }
            Object errcode = linkedmap.get("errcode");
            if (errcode != null) {
                throw new ServiceException("[WeChat Oauth2] error : json is : "
                        + res);
            }
            wxOauthAccessToken = new WxOauthAccessToken();
            String dynamicToken = String.valueOf(linkedmap.get("access_token"));
            wxOauthAccessToken.setAccessToken(dynamicToken);
            Integer expiresIn = Integer.valueOf(String.valueOf(linkedmap
                    .get("expires_in")));
            wxOauthAccessToken.setExpiresIn(expiresIn);
            String openId = String.valueOf(linkedmap.get("openid"));
            wxOauthAccessToken.setOpenId(openId);
            String newRefreshToken = String.valueOf(linkedmap
                    .get("refresh_token"));
            wxOauthAccessToken.setRefreshToken(newRefreshToken);
            String scope = String.valueOf(linkedmap.get("scope"));
            wxOauthAccessToken.setScope(scope);
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("refreshOauthAccessToken() is error...",
                    e);
        }
        return wxOauthAccessToken;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @param refreshToken
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private WxUserInfo getWeCahtUserInfo(String accessToken, String openId)
            throws ServiceException {
        WxUserInfo wxUserInfo = null;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("access_token", accessToken);
            param.put("openid", "openId");
            param.put("lang", "zh_CN");
            String res = SimpleHttpClient.invokerGet(authorizeUserInfoApiUrl,
                    null, param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[WeChat Oauth2] error : res exception...");
            }
            Object errcode = linkedmap.get("errcode");
            if (errcode != null) {
                throw new ServiceException("[WeChat Oauth2] error : json is : "
                        + res);
            }
            wxUserInfo = new WxUserInfo();
            String newOpenId = String.valueOf(linkedmap.get("openid"));
            wxUserInfo.setOpenId(newOpenId);
            String nickname = String.valueOf(linkedmap.get("nickname"));
            wxUserInfo.setNickName(new String(nickname.getBytes("iso-8859-1"),
                    "utf-8"));
            String sex = String.valueOf(linkedmap.get("sex"));
            wxUserInfo.setSex(Integer.valueOf(sex));
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("getWeCahtUserInfo() is error...", e);
        }
        return wxUserInfo;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param accessToken
     * @param openId
     * @return
     * @throws ServiceException 
     */
    @SuppressWarnings("unchecked")
    private boolean checkAuthToken(String accessToken, String openId)
            throws ServiceException {
        boolean isInvalid = false;
        try {
            // No1: 拼装接口需要的参数
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("access_token", accessToken);
            param.put("openid", openId);
            String res = SimpleHttpClient.invokerGet(authorizeCheckTokenApiUrl,
                    null, param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            // No2: 判断是否请求成功
            if (ObjectUtils.isEmpty(linkedmap)) {
                throw new ServiceException(
                        "[WeChat Oauth2] error : res exception...");
            }
            Object errcode = linkedmap.get("errcode");
            if (errcode != null) {
                if (Integer.valueOf(errcode.toString()) == 0) {
                    isInvalid = true;
                }
            }
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("checkAuthToken() is error...", e);
        }
        return isInvalid;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getWeChatUserList(String nextOpenId) throws ServiceException {
        List<WxUserInfo> wxUserInfoList = new ArrayList<WxUserInfo>();
        try {
            String token = getCacheToken();
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("access_token", token);
            if (StringUtils.isNotEmpty(nextOpenId)) {
                param.put("next_openid", nextOpenId);
            }
            String openIdRes = SimpleHttpClient.invokerGet(userListUrl, null,
                    param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(
                    openIdRes, LinkedHashMap.class);
            Object errcodeObj = linkedmap.get("errcode");
            if (errcodeObj != null) {
                Integer errcode = Integer.valueOf(errcodeObj.toString());
                if (errcode == 40001 || errcode == 42001) {
                    // 刷新统一调用凭证
                    refreshCacheToken();
                    // 递归调用下自己
                    return getWeChatUserList(nextOpenId);
                }
                throw new ServiceException(
                        "[WeChat user/get] error : json is : " + openIdRes);
            }

            LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) linkedmap
                    .get("data");
            List<String> openIds = (List<String>) data.get("openid");
            WxUserInfo wxUserInfo = null;
            for (String openId : openIds) {
                wxUserInfo = getWeChatUserInfo(token, openId);
                wxUserInfoList.add(wxUserInfo);
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("total", linkedmap.get("total"));
            result.put("rows", wxUserInfoList);
            result.put("nextOpenId", linkedmap.get("next_openid"));
            return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
        } catch (JsonMappingException e) {
            throw new ServiceException("format json is error...", e);
        } catch (Exception e) {
            throw new ServiceException("getWeChatUserList() is error...", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public WxUserInfo getWeChatUserInfo(String accessToken, String openId)
            throws Exception {
        Map<String, Object> infoParam = new LinkedHashMap<String, Object>();
        infoParam.put("access_token", accessToken);
        infoParam.put("openid", openId);
        infoParam.put("lang", "zh_CN");
        String infoRes = SimpleHttpClient.invokerGet(userInfoUrl, null,
                infoParam);
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> infoLinkedMap = mapper.readValue(infoRes,
                LinkedHashMap.class);
        Object infoErr = infoLinkedMap.get("errcode");
        if (infoErr != null) {
            throw new ServiceException("[WeChat user/info] error : json is : "
                    + infoRes);
        }
        WxUserInfo wxUserInfo = new WxUserInfo();
        Integer subscribe = Integer.valueOf(infoLinkedMap.get("subscribe")
                .toString());
        wxUserInfo.setSubscribe(subscribe);
        wxUserInfo.setOpenId(openId);
        if (subscribe != 0) {
            String nickname = String.valueOf(infoLinkedMap.get("nickname"));
            wxUserInfo.setNickName(new String(nickname.getBytes("ISO-8859-1"),
                    "UTF-8"));
            wxUserInfo.setSex(Integer.valueOf(infoLinkedMap.get("sex")
                    .toString()));
            wxUserInfo.setHeadImgUrl(String.valueOf(infoLinkedMap
                    .get("headimgurl")));
            String country = String.valueOf(infoLinkedMap.get("country"));
            wxUserInfo.setCountry(new String(country.getBytes("ISO-8859-1"),
                    "UTF-8"));
            String province = String.valueOf(infoLinkedMap.get("province"));
            wxUserInfo.setProvince(new String(province.getBytes("ISO-8859-1"),
                    "UTF-8"));
            String city = String.valueOf(infoLinkedMap.get("city"));
            wxUserInfo
                    .setCity(new String(city.getBytes("ISO-8859-1"), "UTF-8"));
            wxUserInfo.setSubscribeTime(Long.valueOf(infoLinkedMap.get(
                    "subscribe_time").toString()));
        }
        return wxUserInfo;
    }

    @Override
    public WxUserInfo wechatSubscribe(HttpServletRequest req, String code,
            String state) throws ServiceException {
        HttpSession session = req.getSession();
        // 判断session中是否存在wxUserInfo
        WxUserInfo wxUserInfo = (WxUserInfo) session
                .getAttribute("wechatUserInfo");
        if (wxUserInfo != null) {
            return wxUserInfo;
        }
        try {
            // 通过code换取网页授权access_token
            WxOauthAccessToken wxOauthAccessToken = getOauthAccessToken(code);
            // 获取openId
            String openId = wxOauthAccessToken.getOpenId();
            String token = getCacheToken();
            // 根据openId获取用户详细信息
            wxUserInfo = getWeChatUserInfo(token, openId);
            // 获取到wxUserInfo放到session中
            if (wxUserInfo != null && wxUserInfo.getSubscribe() != 0) {
                session.setAttribute("wechatUserInfo", wxUserInfo);
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return wxUserInfo;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getWechatAppSecret() {
        return wechatAppSecret;
    }

    public void setWechatAppSecret(String wechatAppSecret) {
        this.wechatAppSecret = wechatAppSecret;
    }

    public String getAccessTokenApiUrl() {
        return accessTokenApiUrl;
    }

    public void setAccessTokenApiUrl(String accessTokenApiUrl) {
        this.accessTokenApiUrl = accessTokenApiUrl;
    }

    public String getJsApiTicketApiUrl() {
        return jsApiTicketApiUrl;
    }

    public void setJsApiTicketApiUrl(String jsApiTicketApiUrl) {
        this.jsApiTicketApiUrl = jsApiTicketApiUrl;
    }

    public String getWechatCfgDebug() {
        return wechatCfgDebug;
    }

    public void setWechatCfgDebug(String wechatCfgDebug) {
        this.wechatCfgDebug = wechatCfgDebug;
    }

    public String getWechatCfgJsApiList() {
        return wechatCfgJsApiList;
    }

    public void setWechatCfgJsApiList(String wechatCfgJsApiList) {
        this.wechatCfgJsApiList = wechatCfgJsApiList;
    }

    public String getHideMenuArray() {
        return hideMenuArray;
    }

    public void setHideMenuArray(String hideMenuArray) {
        this.hideMenuArray = hideMenuArray;
    }

    public String getBaiduMapKey() {
        return baiduMapKey;
    }

    public void setBaiduMapKey(String baiduMapKey) {
        this.baiduMapKey = baiduMapKey;
    }

    public String getAuthorizeCodeApiUrl() {
        return authorizeCodeApiUrl;
    }

    public void setAuthorizeCodeApiUrl(String authorizeCodeApiUrl) {
        this.authorizeCodeApiUrl = authorizeCodeApiUrl;
    }

    public String getAuthorizeAccessTokenApiUrl() {
        return authorizeAccessTokenApiUrl;
    }

    public void setAuthorizeAccessTokenApiUrl(String authorizeAccessTokenApiUrl) {
        this.authorizeAccessTokenApiUrl = authorizeAccessTokenApiUrl;
    }

    public String getAuthorizeRefreshTokenApiUrl() {
        return authorizeRefreshTokenApiUrl;
    }

    public void setAuthorizeRefreshTokenApiUrl(
            String authorizeRefreshTokenApiUrl) {
        this.authorizeRefreshTokenApiUrl = authorizeRefreshTokenApiUrl;
    }

    public String getAuthorizeUserInfoApiUrl() {
        return authorizeUserInfoApiUrl;
    }

    public void setAuthorizeUserInfoApiUrl(String authorizeUserInfoApiUrl) {
        this.authorizeUserInfoApiUrl = authorizeUserInfoApiUrl;
    }

    public String getAuthorizeCheckTokenApiUrl() {
        return authorizeCheckTokenApiUrl;
    }

    public void setAuthorizeCheckTokenApiUrl(String authorizeCheckTokenApiUrl) {
        this.authorizeCheckTokenApiUrl = authorizeCheckTokenApiUrl;
    }

    public String getUserListUrl() {
        return userListUrl;
    }

    public void setUserListUrl(String userListUrl) {
        this.userListUrl = userListUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }
}
