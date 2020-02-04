package com.dxr.apply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.FlowExampleDao;
import com.dxr.apply.entity.ComplaintHandleInfo;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.WorkFlowExample;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.entity.WorkFlowLog;
import com.dxr.apply.service.api.IComplaintHandle;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IFlowExample;
import com.dxr.apply.service.api.IFlowLog;
import com.dxr.apply.service.api.IWorkFlow;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.service.api.IUser;
import com.dxr.webui.wechat.service.api.ITemplate;

/**
 * @description: <流程实例服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class FlowExampleMgr extends BasalMgr<WorkFlowExample> implements
        IFlowExample {

    @Autowired
    private FlowExampleDao flowExampleDao;
    @Autowired
    private IWorkFlow workFlowMgr;
    @Autowired
    private IFlowLog flowLogMgr;
    @Autowired
    private ITemplate templateMgr;
    @Autowired
    private IUser userMgr;
    @Autowired
    private IComplaintHandle handleMgr;
    @Autowired
    private IComplaintSheet complaintSheetMgr;

    /**
     * 微信待办提示模板Id
     */
    private String agencyTemplateId;

    @Override
    public WorkFlowExample getDbObject(String id) throws ServiceException {
        return flowExampleDao.get(id);
    }

    @Override
    public WorkFlowExample loadFlowExample(String businessId)
            throws ServiceException {
        return flowExampleDao.loadFlowExample(businessId);
    }

    @Override
    public void saveFlowExample(WorkFlowExample flowExample, String flowNodeText)
            throws ServiceException {
        // No1: 保存流程实例
        flowExampleDao.save(flowExample);

        // No2: 微信公众号通知
        afterSendMsg(flowExample, flowNodeText);
    }

    /**
     * 保存第一个流程实例&&记录流程日志
     * @param businessId
     * @param firstWorkFlow
     * @throws ServiceException
     */
    private void saveFlowExample(String businessId, WorkFlowInfo firstWorkFlow)
            throws ServiceException {
        // 新建一个流程实例
        WorkFlowExample flowExample = new WorkFlowExample();
        flowExample.setBusinessId(businessId);
        flowExample.setFlowNodeId(firstWorkFlow.getId());
        flowExample.setFlowNodeCode(firstWorkFlow.getFlowNodeCode());
        flowExample.setHandleMode(firstWorkFlow.getHandleMode());
        flowExample.setSubjectId(firstWorkFlow.getSubjectId());

        // 调用保存流程实例方法保存
        saveFlowExample(flowExample, firstWorkFlow.getFlowNodeText());

        // 记录流程日志
        WorkFlowLog flowLog = new WorkFlowLog();
        flowLog.setFlowExampleId(flowExample.getId());
        flowLog.setFlowNodeId(firstWorkFlow.getId());
        flowLog.setFlowNodeText(firstWorkFlow.getFlowNodeText());
        flowLog.setHandleResult(1);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        if (shiroUser != null) {
            flowLog.setHandleUserId(shiroUser.getUserId());
            flowLog.setHandleUserName(shiroUser.getRealName());
            flowLog.setHandleSay("【" + shiroUser.getRealName() + "】提交了失信举报单。");
        } else {
            flowLog.setHandleUserName("微信用户");
            flowLog.setHandleSay("微信用户默认提交。");
        }
        flowLogMgr.saveFlowLog(flowLog);
    }

    @Override
    public void updateFlowExample(WorkFlowExample flowExample,
            String flowNodeText) throws ServiceException {
        // No1: 更新流程实例
        WorkFlowExample flowExampleDb = getDbObject(flowExample.getId());
        flowExampleDb.setFlowNodeId(flowExample.getFlowNodeId());
        flowExampleDb.setFlowNodeCode(flowExample.getFlowNodeCode());
        flowExampleDb.setHandleMode(flowExample.getHandleMode());
        flowExampleDb.setSubjectId(flowExample.getSubjectId());
        flowExampleDao.update(flowExampleDb);

        // No2: 微信公众号通知
        afterSendMsg(flowExample, flowNodeText);
    }

    /**
     * 更新流程实例&&流程记录日志
     * @param flowExample
     * @param workflow
     * @param flowLogId
     * @param handleResult
     * @param subjectId
     * @throws ServiceException
     */
    private void updateFlowExample(WorkFlowExample flowExample,
            WorkFlowInfo workflow, String flowLogId, Integer handleResult,
            String subjectId) throws ServiceException {
        flowExample.setFlowNodeId(workflow.getId());
        flowExample.setFlowNodeCode(workflow.getFlowNodeCode());
        // 动态指定下一个环节处理人
        if (StringUtils.isNotEmpty(subjectId)) {
            flowExample
                    .setHandleMode(ConstGlobal.WORKFLOW_HANDLE_MODE_USER_HANDLE);
            flowExample.setSubjectId(subjectId);
        } else {
            flowExample.setHandleMode(workflow.getHandleMode());
            flowExample.setSubjectId(workflow.getSubjectId());
        }

        flowExample.setUpdateDate(new Date());
        if (handleResult != ConstGlobal.WORKFLOW_HANDLE_RESULT_REJECT) {
            if (handleResult != ConstGlobal.WORKFLOW_HANDLE_RESULT_END) {
                // 流程往下走需要判断是否到终点
                // 得到流程最终节点
                WorkFlowInfo lastWorkFlow = workFlowMgr.loadLast();
                // 判断Last节点和Next节点是否是同一个, 来确定流程是否已结束
                if (lastWorkFlow.getId().equals(workflow.getId())) {
                    flowExample.setStatus(ConstGlobal.FLOW_EXAMPLE_STATUS_OVER);
                }
            } else {
                flowExample.setStatus(ConstGlobal.FLOW_EXAMPLE_STATUS_OVER);
            }
        }

        updateFlowExample(flowExample, workflow.getFlowNodeText());

        // 记录流程日志
        WorkFlowLog flowLog = flowLogMgr.getDbObject(flowLogId);
        flowLog.setFlowExampleId(flowExample.getId());
        // 特殊处理：若流程是往上走, 记录日志的节点就得2次next才是当前节点
        if (handleResult == ConstGlobal.WORKFLOW_HANDLE_RESULT_REJECT) {
            WorkFlowInfo nextworkflow = workFlowMgr.loadNext(workflow
                    .getFlowNodeCode());
            nextworkflow = workFlowMgr.loadNext(nextworkflow.getFlowNodeCode());
            flowLog.setFlowNodeId(nextworkflow.getId());
            flowLog.setFlowNodeText(nextworkflow.getFlowNodeText());
        } else {
            flowLog.setFlowNodeId(workflow.getId());
            flowLog.setFlowNodeText(workflow.getFlowNodeText());
        }
        flowLogMgr.updateFlowLog(flowLog);
    }

    @Override
    public String runExample(String businessId, Integer handleResult,
            String flowLogId, String subjectId) throws ServiceException {
        // No1: 获取业务(投诉单)下的流程实例
        WorkFlowExample flowExample = loadFlowExample(businessId);
        if (flowExample == null) {
            // 流程启动, 获取流程定义的第一个节点
            WorkFlowInfo firstWorkFlow = workFlowMgr.loadFirst();
            // 校验权限
            //verifyAuthority(firstWorkFlow);
            // 保存流程实例
            saveFlowExample(businessId, firstWorkFlow);
            return firstWorkFlow.getFlowNodeCode();
        }

        // No2: 校验权限, 是否有权限操作
        verifyAuthority(flowExample.getFlowNodeId());

        // No3: 根据用户的审核结果来确定流程的走向, 同意-流程往下走; 驳回-返回上一级; 非投诉-直接结束;
        String nowFlowCode = flowExample.getFlowNodeCode();
        WorkFlowInfo workflow = null;
        if (handleResult == ConstGlobal.WORKFLOW_HANDLE_RESULT_AGREE) {
            // 获取流程定义的下一个节点, 更新正在执行中的流程
            workflow = workFlowMgr.loadNext(nowFlowCode);
        } else if (handleResult == ConstGlobal.WORKFLOW_HANDLE_RESULT_REJECT) {
            // 获取流程定义的上一个节点, 更新正在执行中的流程
            workflow = workFlowMgr.loadPrev(nowFlowCode);
            // 终审驳回时需要获取分流时动态指定的处理人
            if (nowFlowCode
                    .equals(ConstGlobal.COMPLAINT_FLOW_STATUS_ALREADYHANDLED)
                    || nowFlowCode
                            .equals(ConstGlobal.COMPLAINT_FLOW_STATUS_SUPERVISORAUDITED)) {
                // 获取最近的一个流程日志
                WorkFlowLog recentlyFlowLog = flowLogMgr
                        .getRecentlyFlowLog(flowExample.getId());
                // 从而获取上一个审核者
                subjectId = recentlyFlowLog.getHandleUserId();
            }
        } else if (handleResult == ConstGlobal.WORKFLOW_HANDLE_RESULT_END) {
            // 获取流程定义的最后一个节点, 更新正在执行中的流程
            workflow = workFlowMgr.loadLast();

            // 初审时直接结束的流程, 将审核意见设置成最终处理结果
            WorkFlowLog flowLog = flowLogMgr.getDbObject(flowLogId);
            String handleSay = flowLog.getHandleSay();
            saveComplaintHandleForEnd(businessId, handleSay);
        }

        // No4: 更新流程, 记录日志&发送通知(微信待办通知)
        updateFlowExample(flowExample, workflow, flowLogId, handleResult,
                subjectId);

        return workflow.getFlowNodeCode();
    }

    /**
     * 保存投诉处理, 在初审直接结束时
     * @param complaintId
     * @param handleSay
     * @throws ServiceException
     */
    private void saveComplaintHandleForEnd(String complaintId, String handleSay)
            throws ServiceException {
        ComplaintHandleInfo handleInfo = new ComplaintHandleInfo();
        handleInfo.setComplaintId(complaintId);
        handleInfo.setHandleSay(handleSay);
        handleInfo.setHandleType(ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        handleInfo.setHandleUserId(shiroUser.getUserId());
        handleInfo.setHandleUserName(shiroUser.getRealName());
        handleInfo.setHandleDate(new Date());
        handleMgr.saveComplaintHandle(handleInfo, true);
    }

    /**
     * 发送待办消息通知
     * @param flowExample
     * @throws ServiceException
     */
    public void afterSendMsg(WorkFlowExample flowExample, String flowNodeText)
            throws ServiceException {
        Integer handleMode = flowExample.getHandleMode();
        String complaintId = flowExample.getBusinessId();
        if (handleMode == ConstGlobal.WORKFLOW_HANDLE_MODE_USER_HANDLE) {
            // userId
            String subjectId = flowExample.getSubjectId();
            UserInfo userInfo = userMgr.getDbObject(subjectId);
            String wechatOpenId = userInfo.getWechatOpenId();
            if (StringUtils.isNotEmpty(wechatOpenId)) {
                String jsonBody = builderAgencyRemindJsonBody(complaintId,
                        wechatOpenId, flowNodeText);
                templateMgr.sendMessage(jsonBody);
            }
        }
        if (handleMode == ConstGlobal.WORKFLOW_HANDLE_MODE_ROLE_HANDLE) {
            // roleId
            String subjectId = flowExample.getSubjectId();
            // 根据角色查找用户
            List<UserInfo> userList = userMgr.findUserListForRoleId(subjectId);
            // 循环给拥有该角色的用户发送消息
            for (UserInfo userInfo : userList) {
                String wechatOpenId = userInfo.getWechatOpenId();
                if (StringUtils.isNotEmpty(wechatOpenId)) {
                    String jsonBody = builderAgencyRemindJsonBody(complaintId,
                            wechatOpenId, flowNodeText);
                    templateMgr.sendMessage(jsonBody);
                }
            }
        }
    }

    /**
     * 构建待办提醒消息jsonBody
     * @param onpenId
     * @return
     * @throws ServiceException 
     */
    private String builderAgencyRemindJsonBody(String complaintId,
            String onpenId, String flowNodeText) throws ServiceException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("touser", onpenId);
        jsonBody.put("template_id", agencyTemplateId);
        jsonBody.put("url", null);
        jsonBody.put("topcolor", "#FF0000");
        JSONObject data = new JSONObject();
        // {{first.DATA}}
        JSONObject firstJsonNode = new JSONObject();
        data.put("first", firstJsonNode);
        firstJsonNode.put("value", "您有新的待办，请尽快登录平台处理\r\n");
        firstJsonNode.put("color", "#173177");

        // 提醒内容：{{keyword1.DATA}}
        JSONObject keyword1JsonNode = new JSONObject();
        data.put("keyword1", keyword1JsonNode);
        keyword1JsonNode.put("value", "审核待办");

        // 时间：{{keyword2.DATA}}
        JSONObject keyword2JsonNode = new JSONObject();
        data.put("keyword2", keyword2JsonNode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        keyword2JsonNode.put("value", sdf.format(new Date()));

        // 当前状态：{{remark.DATA}}
        JSONObject remarkJsonNode = new JSONObject();
        data.put("remark", remarkJsonNode);
        StringBuilder remark = new StringBuilder();
        remark.append("当前状态：");
        remark.append("已" + flowNodeText);
        remark.append("\r\n");
        ComplaintSheetInfo complaintInfo = complaintSheetMgr
                .getDbObject(complaintId);
        remark.append("投诉对象：");
        remark.append(complaintInfo.getDealerName());
        remark.append("\r\n");
        remark.append("投诉内容：");
        remark.append(complaintInfo.getComplaintReason());
        remarkJsonNode.put("value", remark.toString());

        jsonBody.put("data", data);
        return jsonBody.toJSONString();
    }

    /**
     * 权限校验(在提交审核时), 若没有权限则直接抛出异常
     * @param workflow
     * @throws ServiceException
     */
    private void verifyAuthority(String flowNodeId) throws ServiceException {
        WorkFlowInfo workflow = workFlowMgr.getDbObject(flowNodeId);
        Integer handleMode = workflow.getHandleMode();
        if (handleMode == ConstGlobal.WORKFLOW_HANDLE_MODE_USER_HANDLE) {
            // userId, 当前节点处理方式为某一用户
            String subjectId = workflow.getSubjectId();
            ShiroUser shiroUser = SecurityUser.getLoginUser();
            // 判断当前登录人和处理该节点subjectId是否一致, 不一致则说明没有该节点处理权限
            if (!subjectId.equals(shiroUser.getUserId())) {
                throw new ServiceException("no Authority...",
                        ErrorCode.ERRCODE_VERIFY_FLOW_UN_AUTHORITY);
            }
        }
        if (handleMode == ConstGlobal.WORKFLOW_HANDLE_MODE_ROLE_HANDLE) {
            // roleId, 当前节点处理方式为某一角色
            String subjectId = workflow.getSubjectId();
            Subject subject = SecurityUser.getSubject();
            // 判断当前登录人是否有处理该节点subjectId的角色, 若没有则说明没有该节点处理权限
            if (!subject.hasRole(subjectId)) {
                throw new ServiceException("no Authority...",
                        ErrorCode.ERRCODE_VERIFY_FLOW_UN_AUTHORITY);
            }
        }
    }

    @Override
    public String updateExampleType(WorkFlowExample flowExample)
            throws ServiceException {
        String businessId = flowExample.getBusinessId();
        WorkFlowExample example = loadFlowExample(businessId);
        example.setExampleType(flowExample.getExampleType());
        flowExampleDao.update(example);
        return new Message("更新成功!").toString();
    }

    public String getAgencyTemplateId() {
        return agencyTemplateId;
    }

    public void setAgencyTemplateId(String agencyTemplateId) {
        this.agencyTemplateId = agencyTemplateId;
    }
}
