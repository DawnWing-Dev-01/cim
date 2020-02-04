package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.service.api.IWorkFlow;

/**
 * @description: <简易工作流Action层>
 * @author: w.xL
 * @date: 2018-4-2
 */
public class WorkFlowAction extends BasalAction {

    private static final long serialVersionUID = 878722373504794868L;

    @Autowired
    private IWorkFlow workFlowMgr;

    private WorkFlowInfo workFlowInfo;

    @Override
    public String formView() {
        super.putViewDeftData("notHandle",
                ConstGlobal.WORKFLOW_HANDLE_MODE_NOT_HANDLE);
        super.putViewDeftData("roleHandle",
                ConstGlobal.WORKFLOW_HANDLE_MODE_ROLE_HANDLE);
        super.putViewDeftData("userHandle",
                ConstGlobal.WORKFLOW_HANDLE_MODE_USER_HANDLE);
        return super.formView();
    }

    /**
     * 获取简易工作流列表, DataGrid形式展示
     */
    public void getWorkFlowPage() {
        try {
            String result = workFlowMgr.getWorkFlowPage(page, rows,
                    workFlowInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WorkFlowAction.getWorkFlowPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存简易工作流
     */
    public void saveWorkFlow() {
        try {
            String result = workFlowMgr.saveWorkFlow(workFlowInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WorkFlowAction.saveWorkFlow() is error...", e);
            super.brace();
        }
    }

    /**
     * 更新简易工作流
     */
    public void updateWorkFlow() {
        try {
            String result = workFlowMgr.updateWorkFlow(workFlowInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WorkFlowAction.updateWorkFlow() is error...", e);
            super.brace();
        }
    }

    /**
     * 删除简易工作流, 逻辑删除
     */
    public void deleteWorkFlow() {
        try {
            String result = workFlowMgr.deleteWorkFlow(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WorkFlowAction.deleteWorkFlow() is error...", e);
            super.brace();
        }
    }

    /**
     * 获取行业信息详细
     */
    public void getDetails() {
        try {
            String result = workFlowMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WorkFlowAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public WorkFlowInfo getWorkFlowInfo() {
        return workFlowInfo;
    }

    public void setWorkFlowInfo(WorkFlowInfo workFlowInfo) {
        this.workFlowInfo = workFlowInfo;
    }
}
