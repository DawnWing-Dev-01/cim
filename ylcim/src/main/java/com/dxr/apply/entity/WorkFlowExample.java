package com.dxr.apply.entity;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <流程实例>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class WorkFlowExample extends AbstractEntity {

    private static final long serialVersionUID = -1794037656015338986L;

    /**
     * 业务(举报单)Id
     */
    private String businessId;

    /**
     * 当前节点Id
     */
    private String flowNodeId;
    
    /**
     * 当前节点Id
     */
    private String flowNodeCode;

    /**
     * 当前节点由谁处理:1-某一角色;2-某一用户;
     */
    private Integer handleMode;

    /**
     * 操作执行者, 可以是角色、具体用户Id , 一般情况是从流程定义表拿过来, 特殊情况可以特殊指定（如分流）
     */
    private String subjectId;
    
    /**
     * 流程实例类型：
     *  <ul>
     *      <li>非投诉举报流程(unComplain)</li>
     *      <li>正常受理流程(normalAccept)</li>
     *      <li>非正常受理流程(unusualAccept)</li>
     *  </ul>
     */
    private String exampleType;

    /**
     * 流程实例状态
     */
    private Integer status = ConstGlobal.FLOW_EXAMPLE_STATUS_RUN;

    /**
     * 乐观锁
     */
    private Integer version;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getFlowNodeId() {
        return flowNodeId;
    }

    public void setFlowNodeId(String flowNodeId) {
        this.flowNodeId = flowNodeId;
    }

    public String getFlowNodeCode() {
        return flowNodeCode;
    }

    public void setFlowNodeCode(String flowNodeCode) {
        this.flowNodeCode = flowNodeCode;
    }

    public Integer getHandleMode() {
        return handleMode;
    }

    public void setHandleMode(Integer handleMode) {
        this.handleMode = handleMode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getExampleType() {
        return exampleType;
    }

    public void setExampleType(String exampleType) {
        this.exampleType = exampleType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
