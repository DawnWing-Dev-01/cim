package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <投诉处理实体类>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintHandleInfo extends AbstractEntity {

	private static final long serialVersionUID = 4824991563120606775L;

	/**
	 * 投诉单Id
	 */
	private String complaintId;
	
	/**
	 * 当前处理人
	 */
	private String handleUserId;
	
	/**
	 * 处理人名称
	 */
	private String handleUserName;
	
	/**
	 * 处理类型: 1-最终结论(处罚结果); 0-处理过程(过程记录);
	 */
	private String handleType = ConstGlobal.COMPLAINT_HANDLE_TYPE_PROCESS;
	
	/**
	 * 处理意见
	 */
	private String handleSay;
	
	/**
	 * 处理时间
	 */
	private Date handleDate;

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}

	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	
	public String getHandleSay() {
		return handleSay;
	}

	public void setHandleSay(String handleSay) {
		this.handleSay = handleSay;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
}
