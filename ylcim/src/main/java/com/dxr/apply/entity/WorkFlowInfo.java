package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <简易工作流定义信息>
 * @author: w.xL
 * @date: 2018-4-2
 */
public class WorkFlowInfo extends AbstractEntity {

    private static final long serialVersionUID = -4077356784639755247L;

    /**
     * 流节点
     */
    private String flowNodeCode;
    
    /**
     * 节点显示文字
     */
    private String flowNodeText;
    
    /**
     * 当前节点处理方式： 1-角色处理、2-具体用户处理、0-不用处理
     */
    private Integer handleMode;
    
    /**
     * 处理当前节点的对象/主体, 显示值
     */
    private String handleSubject;
    
    /**
     * 处理主体Id
     */
    private String subjectId;

    public String getFlowNodeCode() {
        return flowNodeCode;
    }

    public void setFlowNodeCode(String flowNodeCode) {
        this.flowNodeCode = flowNodeCode;
    }

    public String getFlowNodeText() {
        return flowNodeText;
    }

    public void setFlowNodeText(String flowNodeText) {
        this.flowNodeText = flowNodeText;
    }

    public Integer getHandleMode() {
        return handleMode;
    }

    public void setHandleMode(Integer handleMode) {
        this.handleMode = handleMode;
    }

    public String getHandleSubject() {
        return handleSubject;
    }

    public void setHandleSubject(String handleSubject) {
        this.handleSubject = handleSubject;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
