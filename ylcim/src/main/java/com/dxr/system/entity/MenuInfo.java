package com.dxr.system.entity;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @author w.xL
 *
 */
public class MenuInfo extends AbstractEntity {

	private static final long serialVersionUID = -7919737473851891949L;

	private String fatherId;
	
	/**
	 * 操作
	 */
	private String action;
	
	private String type;
	
	private String icon = "czs-tag-l";
	
	/**
	 * 是否叶子节点
	 * [1]是叶子;[0]不是叶子
	 */
	private Integer isLeaf = ConstGlobal.TREE_IS_LEAF;
	
	private Integer sort;

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
