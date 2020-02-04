package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <流程日志表实体类>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class WorkFlowLog extends AbstractEntity {

    private static final long serialVersionUID = 8614501957340758746L;

    /**
     * 流程实例Id
     */
    private String flowExampleId;
    
    /**
     * 流程节点Id
     */
    private String flowNodeId;
    
    /**
     * 流程节点显示值
     */
    private String flowNodeText;
    
    /**
     * 当前处理用户id
     */
    private String handleUserId;
    
    /**
     * 处理用户名称
     */
    private String handleUserName;
    
    /**
     * 处理结果：1 - 同意; 0 - 驳回; -1 - 结束(初审);
     */
    private Integer handleResult;
    
    /**
     * 处理意见
     */
    private String handleSay;
    
    /**
     * 处理时间
     */
    private Date handleDate = new Date();

    //********************************************************************
    // 以下属性为显示属性, 不在数据库中存储
    private String subjectId;
    
    public String getFlowExampleId() {
        return flowExampleId;
    }

    public void setFlowExampleId(String flowExampleId) {
        this.flowExampleId = flowExampleId;
    }

    public String getFlowNodeId() {
        return flowNodeId;
    }

    public void setFlowNodeId(String flowNodeId) {
        this.flowNodeId = flowNodeId;
    }

    public String getFlowNodeText() {
        return flowNodeText;
    }

    public void setFlowNodeText(String flowNodeText) {
        this.flowNodeText = flowNodeText;
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

    public Integer getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(Integer handleResult) {
        this.handleResult = handleResult;
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

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
    
}
