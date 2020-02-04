package com.dawnwing.framework.supers.entity;

import java.io.Serializable;
import java.util.Date;

import com.dawnwing.framework.common.ConstGlobal;

/**
 * 基础的实体类
 * @author wenxiaolong
 */
public class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = -4526759899131981602L;

	protected String id;
	
	protected String name;
	
	protected Integer sort;
	
	/**
	 * 是否初始化数据
	 */
	private Integer isInit = ConstGlobal.DATA_NO_INIT;
	
	/**
	 * 记录状态：[0]被删除; [1]正常数据;
	 */
	protected Integer status = ConstGlobal.DATA_STATUS_OKAY;
	
	/**
	 * 创建时间
	 */
	protected Date createDate = new Date();
	
	/**
	 * 更新时间
	 */
	protected Date updateDate = new Date();
	
	/**
	 * 备注
	 */
	protected String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isEmpty(){
		return this == null;
	}
	
	public boolean isNotEmpty(){
		return !isEmpty();
	}
}
