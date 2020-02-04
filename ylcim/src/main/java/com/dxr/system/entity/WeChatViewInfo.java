package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <微信视图实体类>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WeChatViewInfo extends AbstractEntity {

    private static final long serialVersionUID = -2422735911275358160L;

    /**
     * 重定向地址
     */
    private String redirect;

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
