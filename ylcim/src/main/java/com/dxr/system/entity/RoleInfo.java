package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @author w.xL
 *
 */
public class RoleInfo extends AbstractEntity {

	private static final long serialVersionUID = 1845481306630704295L;

	/**
	 * 角色编码
	 */
	private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
