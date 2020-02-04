package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <图标>
 * @author: w.xL
 * @date: 2018-3-20
 */
public class Iconlibrary extends AbstractEntity {

    private static final long serialVersionUID = 839254011306205080L;

    private String iconCls;
    
    private String from;
    
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
