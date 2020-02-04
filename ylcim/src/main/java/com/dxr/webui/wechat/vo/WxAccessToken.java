package com.dxr.webui.wechat.vo;

/**
 * @description: <微信全局唯一令牌AccessToken VO对象>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WxAccessToken {

    /**
     * 获取到的凭证
     */
    private String token;
    
    /**
     * 凭证有效时间，单位：秒
     */
    private Integer expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
