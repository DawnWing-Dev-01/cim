package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @author w.xL
 *
 */
public class RoleMenu extends AbstractEntity {

	private static final long serialVersionUID = -5247220901556727681L;

	private String roleId;
	
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
