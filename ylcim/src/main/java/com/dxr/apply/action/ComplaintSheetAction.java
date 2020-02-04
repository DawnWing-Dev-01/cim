package com.dxr.apply.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.ComplaintPhotoInfo;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.WorkFlowExample;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.entity.WorkFlowLog;
import com.dxr.apply.service.api.IComplaintPhoto;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IFlowExample;
import com.dxr.apply.service.api.IWorkFlow;
import com.dxr.comm.cache.SystemConstantCache;

/**
 * @description: <投诉登记表Action层>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintSheetAction extends BasalAction {

    private static final long serialVersionUID = 7606159144542718205L;

    @Autowired
    private IComplaintSheet complaintSheetMgr;
    @Autowired
    private IComplaintPhoto complaintPhotoMgr;
    @Autowired
    private IFlowExample flowExampleMgr;
    @Autowired
    private IWorkFlow workFlowMgr;
    @Autowired
    private SystemConstantCache scCache;

    private String flowNode;

    private ComplaintSheetInfo complaintInfo;

    private WorkFlowLog flowLogInfo;

    private List<WorkFlowInfo> workflowList;

    private WorkFlowExample workflowExample;

    private List<WorkFlowLog> workflowLogList;

    /**
     * 失信行为审核视图
     * 
     * @return
     */
    public String verify() {
        return "verify";
    }

    /**
     * 失信行为处理视图
     * 
     * @return
     */
    public String handle() {
        return "handle";
    }

    /**
     * 历史失信行为视图
     * 
     * @return
     */
    public String history() {
        return "history";
    }

    @Override
    public String formView() {
        super.putViewDeftData("genderMale", ConstGlobal.USER_GENDER_MALE);
        super.putViewDeftData("genderFemale", ConstGlobal.USER_GENDER_FEMALE);
        super.putViewDeftData("registerUnit",
                String.valueOf(scCache.get("ConsumerWarningDefaultAuthor")));
        super.putViewDeftData("complaintSource",
                ConstGlobal.COMPLAINT_SOURCE_SYSTEM_ENTRY);
        try {
            super.putViewDeftData("complaintPhotoList",
                    complaintPhotoMgr.getComplaintPhotoList(object));
        } catch (ServiceException e) {
            super.putViewDeftData("complaintPhotoList",
                    new ArrayList<ComplaintPhotoInfo>());
        }
        if ("verifyForm".equals(viewType) || "readOnly".equals(viewType)) {
            try {
                // 查询流程定义列表
                workflowList = workFlowMgr.getWorkFlowList();
                // 查询正在运行的流程
                workflowExample = flowExampleMgr.loadFlowExample(object);
                // 查询流程日志
                workflowLogList = complaintSheetMgr
                        .getFlowLogListForComplaintId(object);
            } catch (ServiceException e) {
                workflowList = new ArrayList<WorkFlowInfo>();
                workflowExample = new WorkFlowExample();
                workflowLogList = new ArrayList<WorkFlowLog>();
            }
            if ("verifyForm".equals(viewType)) {
                return "verifyFormView";
            }
        }
        return super.formView();
    }

    /**
     * 获取失信行为列表, DataGrid形式展示
     */
    public void getComplaintSheetPage() {
        try {
            String result = complaintSheetMgr.getComplaintSheetPage(page, rows,
                    complaintInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ComplaintSheetAction.getComplaintSheetPage() is error...",
                    e);
            super.emptyGrid();
        }
    }

    /**
     * 保存失信行为
     */
    public void saveComplaintSheet() {
        try {
            String result = complaintSheetMgr.saveComplaintSheet(complaintInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintSheetAction.saveComplaintSheet() is error...", e);
            super.operateException();
        }
    }

    /**
     * 更新失信行为
     */
    public void updateComplaintSheet() {
        try {
            String result = complaintSheetMgr
                    .updateComplaintSheet(complaintInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintSheetAction.updateComplaintSheet() is error...",
                    e);
            super.operateException();
        }
    }

    /**
     * 删除失信行为
     */
    public void deleteComplaintSheet() {
        try {
            String result = complaintSheetMgr.deleteComplaintSheet(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintSheetAction.deleteComplaintSheet() is error...",
                    e);
            super.operateException();
        }
    }

    /**
     * 获取投诉单/失信行为信息详细
     */
    public void getDetails() {
        try {
            String result = complaintSheetMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ComplaintSheetAction.getDetails() is error...", e);
            super.brace();
        }
    }

    /**
     * 提交流程/进度
     */
    public void submitWorkFlow() {
        try {
            complaintSheetMgr.updateWorkFlow(object, null);
            super.writeToView(new Message("提交成功!").toString());
        } catch (ServiceException e) {
            logger.error("ComplaintSheetAction.submitWorkFlow() is error...", e);
            super.error("提交失败!");
        }
    }

    /**
     * 获取待办列表, DataGrid形式展示
     */
    public void getTodoListPage() {
        try {
            String result = complaintSheetMgr.getTodoListPage(page, rows,
                    complaintInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ComplaintSheetAction.getTodoListPage() is error...",
                    e);
            super.emptyGrid();
        }
    }

    /**
     * 审核投诉单
     */
    public void verifyComplaint() {
        try {
            String result = complaintSheetMgr.verifyComplaint(object,
                    flowLogInfo);
            super.writeToView(result);
        } catch (RuntimeException e) {
            logger.error("ComplaintSheetAction.verifyComplaint() is error...",
                    e);
            super.error(e.getMessage());
        } catch (ServiceException e) {
            logger.error("ComplaintSheetAction.verifyComplaint() is error...",
                    e);
            super.error("操作失败!");
        }
    }

    /**
     * 更新投诉单公示状态
     */
    public void updateIsPublicity() {
        try {
            String result = complaintSheetMgr.updateIsPublicity(complaintInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintSheetAction.updateIsPublicity() is error...", e);
            super.error("操作失败!");
        }
    }

    /**
     * 更新是否公示
     */
    public void updateExampleType() {
        try {
            String result = flowExampleMgr.updateExampleType(workflowExample);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintSheetAction.updateExampleType() is error...", e);
            super.error("操作失败!");
        }
    }

    public ComplaintSheetInfo getComplaintInfo() {
        return complaintInfo;
    }

    public void setComplaintInfo(ComplaintSheetInfo complaintInfo) {
        this.complaintInfo = complaintInfo;
    }

    public WorkFlowLog getFlowLogInfo() {
        return flowLogInfo;
    }

    public void setFlowLogInfo(WorkFlowLog flowLogInfo) {
        this.flowLogInfo = flowLogInfo;
    }

    public String getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(String flowNode) {
        this.flowNode = flowNode;
    }

    public List<WorkFlowLog> getWorkflowLogList() {
        return workflowLogList;
    }

    public void setWorkflowLogList(List<WorkFlowLog> workflowLogList) {
        this.workflowLogList = workflowLogList;
    }

    public WorkFlowExample getWorkflowExample() {
        return workflowExample;
    }

    public void setWorkflowExample(WorkFlowExample workflowExample) {
        this.workflowExample = workflowExample;
    }

    public List<WorkFlowInfo> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(List<WorkFlowInfo> workflowList) {
        this.workflowList = workflowList;
    }
}
