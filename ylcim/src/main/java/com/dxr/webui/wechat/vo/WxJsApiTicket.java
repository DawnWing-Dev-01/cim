package com.dxr.webui.wechat.vo;

/**
 * @description: <微信调用js接口的临时票据 VO对象>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WxJsApiTicket {

    /**
     * 获取到的临时票据
     */
    private String ticket;
    
    /**
     * 凭证有效时间，单位：秒
     */
    private Integer expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
