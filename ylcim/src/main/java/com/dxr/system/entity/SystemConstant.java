package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * 系统常量, 数据库存储
 * 
 * @author w.xL
 */
public class SystemConstant extends AbstractEntity {

	private static final long serialVersionUID = -8556618373051564657L;
	
	private String scKey;
	
	private String scVal;
	
	/**
	 * 分组
	 */
	private String scGroup;

	public String getScKey() {
		return scKey;
	}

	public void setScKey(String scKey) {
		this.scKey = scKey;
	}

	public String getScVal() {
		return scVal;
	}

	public void setScVal(String scVal) {
		this.scVal = scVal;
	}

    public String getScGroup() {
        return scGroup;
    }

    public void setScGroup(String scGroup) {
        this.scGroup = scGroup;
    }
}
