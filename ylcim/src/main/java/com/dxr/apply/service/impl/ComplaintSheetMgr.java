package com.dxr.apply.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.ComplaintSheetDao;
import com.dxr.apply.entity.ComplaintHandleInfo;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.DealerCreditVo;
import com.dxr.apply.entity.WorkFlowExample;
import com.dxr.apply.entity.WorkFlowLog;
import com.dxr.apply.service.api.IComplaintHandle;
import com.dxr.apply.service.api.IComplaintPhoto;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IFlowExample;
import com.dxr.apply.service.api.IFlowLog;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.service.api.ISysSeqMax;
import com.dxr.webui.wechat.service.api.ITemplate;

/**
 * @description: <投诉登记表服务接口实现类>
 * @author: w.xL
 * @date: 2018-3-13
 */
public class ComplaintSheetMgr extends BasalMgr<ComplaintSheetInfo> implements
        IComplaintSheet {

    @Autowired
    private ComplaintSheetDao complaintSheetDao;
    @Autowired
    private IComplaintPhoto complaintPhotoMgr;
    @Autowired
    private IFlowExample flowExampleMgr;
    @Autowired
    private IFlowLog flowLogMgr;
    @Autowired
    private ITemplate templateMgr;
    @Autowired
    private ISysSeqMax sysSeqMaxMgr;
    @Autowired
    private IComplaintHandle handleMgr;
    @Autowired
    private SystemConstantCache scCache;

    private String scheduleTemplateId;
    private String resultTemplateId;

    @Override
    public ComplaintSheetInfo getDbObject(String id) throws ServiceException {
        return complaintSheetDao.get(id);
    }

    @Override
    public String getComplaintSheetPage(int page, int rows,
            ComplaintSheetInfo complaintSheetInfo) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (ObjectUtils.isNotEmpty(complaintSheetInfo)) {
            if (StringUtils.isNotEmpty(complaintSheetInfo.getDealerId())) {
                filterList.add(new FilterBean("complaint.dealerId",
                        HqlSymbol.HQL_EQUAL, complaintSheetInfo.getDealerId()));
            }

            if (StringUtils.isNotEmpty(complaintSheetInfo.getDealerName())) {
                filterList.add(new FilterBean("complaint.dealerName",
                        HqlSymbol.HQL_LIKE, "%"
                                + complaintSheetInfo.getDealerName() + "%"));
            }

            if (StringUtils.isNotEmpty(complaintSheetInfo.getFlowStatus())) {
                filterList
                        .add(new FilterBean("complaint.flowStatus",
                                HqlSymbol.HQL_EQUAL, complaintSheetInfo
                                        .getFlowStatus()));
            }

            // 录入员Id过滤
            if (StringUtils.isNotEmpty(complaintSheetInfo.getReporterId())) {
                // 特殊处理, 超级管理员可以看到正在执行的投诉单
                ShiroUser shiroUser = SecurityUser.getLoginUser();
                String account = shiroUser.getLoginName();
                if (!"administrator".equals(account)) {
                    filterList.add(new FilterBean("complaint.reporterId",
                            HqlSymbol.HQL_EQUAL, complaintSheetInfo
                                    .getReporterId()));
                }
            }
        }

        List<ComplaintSheetInfo> complaintList = complaintSheetDao
                .getComplaintSheetPage((page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", complaintSheetDao.getTotal(filterList));
        result.put("rows", complaintList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveComplaintSheet(ComplaintSheetInfo complaintSheet)
            throws ServiceException {
        // WeChatOpenId不为空则说明为实名举报
        if (StringUtils.isNotEmpty(complaintSheet.getInformerWeChatOpenId())) {
            complaintSheet
                    .setComplaintType(ConstGlobal.COMPLAINT_TYPE_REALNAME);
        }

        // 生成编号流水号
        String code = sysSeqMaxMgr.generateComplaintCode();
        complaintSheet.setComplaintCode(code);

        // 持久化到数据库
        complaintSheetDao.save(complaintSheet);

        // 投诉/举报单Id
        String complaintId = complaintSheet.getId();

        if (ConstGlobal.COMPLAINT_SOURCE_WECHAT_ENTRY.equals(complaintSheet
                .getComplaintSource())) {
            // 微信举报需要省略提交步骤
            updateWorkFlow(complaintId, null);
        }

        // 下载用户上传到微信服务器上的投诉照片, 保存到自己服务器上
        String imageServerIds = complaintSheet.getImageServerIds();
        if (StringUtils.isNotEmpty(imageServerIds)) {
            complaintPhotoMgr.saveComplaintPhoto(complaintId, imageServerIds);
        }

        return new Message("保存成功!").toString();
    }

    @Override
    public String updateComplaintSheet(ComplaintSheetInfo complaintSheet)
            throws ServiceException {
        String complaintId = complaintSheet.getId();
        ComplaintSheetInfo complaintSheetDb = complaintSheetDao
                .get(complaintId);
        complaintSheetDb.setRegisterUnit(complaintSheet.getRegisterUnit());
        complaintSheetDb.setComplaintCode(complaintSheet.getComplaintCode());
        complaintSheetDb.setInformerName(complaintSheet.getInformerName());
        complaintSheetDb.setInformerGender(complaintSheet.getInformerGender());
        complaintSheetDb.setInformerAge(complaintSheet.getInformerAge());
        complaintSheetDb.setInformeriPhone(complaintSheet.getInformeriPhone());
        complaintSheetDb
                .setInformerAddress(complaintSheet.getInformerAddress());
        complaintSheetDb.setDealerId(complaintSheet.getDealerId());
        complaintSheetDb.setDealerName(complaintSheet.getDealerName());
        complaintSheetDb.setDealerLinkman(complaintSheet.getDealerLinkman());
        complaintSheetDb.setDealeriPhone(complaintSheet.getDealeriPhone());
        complaintSheetDb.setDealerAddress(complaintSheet.getDealerAddress());
        complaintSheetDb
                .setComplaintReason(complaintSheet.getComplaintReason());
        complaintSheetDb.setReportClassify(complaintSheet.getReportClassify());
        complaintSheetDb.setUpdateDate(new Date());
        complaintSheetDao.update(complaintSheetDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String getDetails(String complaintId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        ComplaintSheetInfo complaintSheetInfo = complaintSheetDao
                .get(complaintId);

        detailsMap.put("complaintInfo.id", complaintSheetInfo.getId());
        detailsMap.put("complaintInfo.dealerId",
                complaintSheetInfo.getDealerId());
        detailsMap.put("complaintInfo.complaintSource",
                complaintSheetInfo.getComplaintSource());
        detailsMap.put("complaintInfo.registerUnit",
                complaintSheetInfo.getRegisterUnit());
        detailsMap.put("complaintInfo.complaintCode",
                complaintSheetInfo.getComplaintCode());
        detailsMap.put("complaintInfo.informerName",
                complaintSheetInfo.getInformerName());
        detailsMap.put("complaintInfo.informerGender",
                complaintSheetInfo.getInformerGender());
        detailsMap.put("complaintInfo.informerAge",
                complaintSheetInfo.getInformerAge());
        detailsMap.put("complaintInfo.informeriPhone",
                complaintSheetInfo.getInformeriPhone());
        detailsMap.put("complaintInfo.informerAddress",
                complaintSheetInfo.getInformerAddress());
        detailsMap.put("complaintInfo.dealerName",
                complaintSheetInfo.getDealerName());
        detailsMap.put("complaintInfo.dealerLinkman",
                complaintSheetInfo.getDealerLinkman());
        detailsMap.put("complaintInfo.dealeriPhone",
                complaintSheetInfo.getDealeriPhone());
        detailsMap.put("complaintInfo.dealerAddress",
                complaintSheetInfo.getDealerAddress());
        detailsMap.put("complaintInfo.complaintReason",
                complaintSheetInfo.getComplaintReason());
        detailsMap.put("complaintInfo.dealerJurisdiction",
                complaintSheetInfo.getDealerJurisdiction());
        return JSON.toJSONString(detailsMap);
    }

    @Override
    public String deleteComplaintSheet(String complaintId)
            throws ServiceException {
        ComplaintSheetInfo complaintSheetDb = complaintSheetDao
                .get(complaintId);
        complaintSheetDb.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        complaintSheetDao.update(complaintSheetDb);
        return new Message("删除成功!").toString();
    }

    @Override
    public String updateWorkFlow(String complaintId, WorkFlowLog flowLog)
            throws ServiceException {
        // 执行流程往下走
        String flowLogId = null;
        String subjectId = null;
        Integer handleResult = null;
        if (flowLog != null) {
            flowLogId = flowLog.getId();
            subjectId = flowLog.getSubjectId();
            handleResult = flowLog.getHandleResult();
        }
        String flowCode = flowExampleMgr.runExample(complaintId, handleResult,
                flowLogId, subjectId);

        ComplaintSheetInfo complaintSheetDb = complaintSheetDao
                .get(complaintId);
        complaintSheetDb.setFlowStatus(flowCode);
        complaintSheetDao.update(complaintSheetDb);

        // 公众号通知举报者, 流程进度
        afterSendMsg(complaintSheetDb);

        return new Message("更新成功!").toString();
    }

    @Override
    public String getTodoListPage(int page, int rows,
            ComplaintSheetInfo complaintSheetInfo) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (complaintSheetInfo != null) {
            if (StringUtils.isNotEmpty(complaintSheetInfo.getDealerName())) {
                filterList.add(new FilterBean("complaint.dealerName",
                        HqlSymbol.HQL_LIKE, "%"
                                + complaintSheetInfo.getDealerName() + "%"));
            }
        }

        List<ComplaintSheetInfo> complaintList = complaintSheetDao
                .getTodoListPage((page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", complaintSheetDao.getTodoTotal(filterList));
        result.put("rows", complaintList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String verifyComplaint(String complaintId, WorkFlowLog flowLog)
            throws ServiceException {
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        if (shiroUser != null) {
            flowLog.setHandleUserId(shiroUser.getUserId());
            flowLog.setHandleUserName(shiroUser.getRealName());
        }

        // 先保存流程日志
        flowLogMgr.saveFlowLog(flowLog);

        // 更新流程进度&举报单状态
        try {
            updateWorkFlow(complaintId, flowLog);
        } catch (ServiceException se) {
            if (se.getErrorCode() == ErrorCode.ERRCODE_VERIFY_FLOW_UN_AUTHORITY) {
                throw new RuntimeException("流程实例不是最新状态, 请刷新待办列表", se);
            }
        }
        return new Message("审核成功!").toString();
    }

    @Override
    public List<WorkFlowLog> getFlowLogListForComplaintId(String complaintId)
            throws ServiceException {
        List<WorkFlowLog> workflowLogList = null;
        WorkFlowExample workflowExample = flowExampleMgr
                .loadFlowExample(complaintId);
        if (workflowExample != null) {
            workflowLogList = flowLogMgr
                    .getFlowLogList(workflowExample.getId());
        } else {
            workflowLogList = new ArrayList<WorkFlowLog>();
        }
        return workflowLogList;
    }

    @Override
    public String updateIsPublicity(ComplaintSheetInfo complaintSheetInfo)
            throws ServiceException {
        ComplaintSheetInfo complaintSheetDb = getDbObject(complaintSheetInfo
                .getId());
        complaintSheetDb.setIsPublicity(complaintSheetInfo.getIsPublicity());
        complaintSheetDao.update(complaintSheetDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public List<DealerCreditVo> findDealerCreditList(String dealerId)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        filterList.add(new FilterBean("complaint.status", HqlSymbol.HQL_EQUAL,
                ConstGlobal.DATA_STATUS_OKAY));
        filterList.add(new FilterBean("complaint.flowStatus",
                HqlSymbol.HQL_EQUAL,
                ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED));
        filterList.add(new FilterBean("complaint.isPublicity",
                HqlSymbol.HQL_EQUAL, ConstGlobal.COMPLAINT_IS_PUBLICITY_TRUE));
        filterList.add(new FilterBean("complaint.dealerId",
                HqlSymbol.HQL_EQUAL, dealerId));
        filterList.add(new FilterBean("handle.handleType", HqlSymbol.HQL_EQUAL,
                ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY));

        List<DealerCreditVo> dealerCreditList = complaintSheetDao
                .findDealerCreditList(0, Integer.MAX_VALUE, filterList);
        return dealerCreditList;
    }

    @Override
    public long statisticsComplaintSheet(Map<String, Object> filterPar)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        filterList.add(new FilterBean("complaint.flowStatus",
                HqlSymbol.HQL_EQUAL,
                ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED));
        filterList.add(new FilterBean("complaint.isPublicity",
                HqlSymbol.HQL_EQUAL, ConstGlobal.COMPLAINT_IS_PUBLICITY_TRUE));

        if (filterPar != null) {
            Object industryId = filterPar.get("industryId");
            if (industryId != null) {
                filterList.add(new FilterBean("dealer.industryId",
                        HqlSymbol.HQL_EQUAL, industryId));
            }
            Object startDate = filterPar.get("startDate");
            if (startDate != null) {
                filterList.add(new FilterBean("complaint.createDate",
                        HqlSymbol.HQL_GREATER_OR_EQUAL, startDate, false));
            }
            Object endDate = filterPar.get("endDate");
            if (endDate != null) {
                filterList.add(new FilterBean("complaint.createDate",
                        HqlSymbol.HQL_LESS_OR_EQUAL, endDate, false));
            }
        }
        return complaintSheetDao.statisticsComplaintSheet(filterList);
    }

    /**
     * 发送消息, 提醒实名举报的微信用户流程进度
     * @param flowExample
     * @throws ServiceException
     */
    private void afterSendMsg(ComplaintSheetInfo complaintSheet)
            throws ServiceException {
        // 举报类型
        String complaintType = complaintSheet.getComplaintType();
        if (ConstGlobal.COMPLAINT_TYPE_REALNAME.equals(complaintType)) {
            // 实名举报
            String wechatOpenId = complaintSheet.getInformerWeChatOpenId();
            // 并且wechatOpenId不为空
            if (StringUtils.isNotEmpty(wechatOpenId)) {
                String jsonBody = null;
                if (ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED
                        .equals(complaintSheet.getFlowStatus())) {
                    // 流程走完时, 通知举报者投诉结果
                    jsonBody = builderResultRemindJsonBody(wechatOpenId,
                            complaintSheet);
                } else {
                    // 通知举报者进度有变化
                    jsonBody = builderScheduleRemindJsonBody(wechatOpenId,
                            complaintSheet);
                }
                templateMgr.sendMessage(jsonBody);
            }
        }
    }

    /**
     * 构建处理进度提醒消息jsonBody
     * @param onpenId
     * @return
     */
    private String builderScheduleRemindJsonBody(String onpenId,
            ComplaintSheetInfo complaintSheet) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("touser", onpenId);
        jsonBody.put("template_id", scheduleTemplateId);
        /*String absoluteWebContext = String.valueOf(scCache
                .get("AbsoluteWebContext"));
        jsonBody.put("url", absoluteWebContext
                + "wechat/wechatAction!showSchedule?object=" + complaintId);*/
        jsonBody.put("topcolor", "#FF0000");
        JSONObject data = new JSONObject();
        // {{first.DATA}}
        JSONObject firstJsonNode = new JSONObject();
        firstJsonNode.put(
                "value",
                String.format("您好，您举报的【%1$s】有新的进展。具体信息如下：\r\n",
                        complaintSheet.getDealerName()));
        firstJsonNode.put("color", "#173177");
        data.put("first", firstJsonNode);
        // 投诉编号：{{keyword1.DATA}}
        JSONObject keyword1JsonNode = new JSONObject();
        keyword1JsonNode.put("value", complaintSheet.getComplaintCode());
        data.put("keyword1", keyword1JsonNode);
        // 处理状态：{{keyword2.DATA}}
        JSONObject keyword2JsonNode = new JSONObject();
        keyword2JsonNode.put("value",
                showFlowNode(complaintSheet.getFlowStatus()));
        data.put("keyword2", keyword2JsonNode);
        // 处理单位：{{keyword3.DATA}}
        JSONObject keyword3JsonNode = new JSONObject();
        String defaultCorporation = String.valueOf(scCache
                .get("ConsumerWarningDefaultAuthor"));
        keyword3JsonNode.put("value", defaultCorporation);
        data.put("keyword3", keyword3JsonNode);
        // {{remark.DATA}}
        JSONObject remarkJsonNode = new JSONObject();
        remarkJsonNode.put("value", "感谢您对经营者信用公示平台的关注。");
        data.put("remark", remarkJsonNode);

        jsonBody.put("data", data);
        return jsonBody.toJSONString();
    }

    /**
     * 构建投诉结果通知提醒消息jsonBody
     * @param onpenId
     * @param dealerName
     * @param complaintId
     * @return
     */
    private String builderResultRemindJsonBody(String onpenId,
            ComplaintSheetInfo complaintSheet) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("touser", onpenId);
        jsonBody.put("template_id", resultTemplateId);
        jsonBody.put("topcolor", "#FF0000");
        JSONObject data = new JSONObject();
        // {{first.DATA}}
        JSONObject firstJsonNode = new JSONObject();
        firstJsonNode.put("value", "您好，您的投诉已处理完毕。具体信息如下：\r\n");
        firstJsonNode.put("color", "#173177");
        data.put("first", firstJsonNode);
        // 投诉对象：{{keyword1.DATA}}
        JSONObject keyword1JsonNode = new JSONObject();
        keyword1JsonNode.put("value", complaintSheet.getDealerName());
        data.put("keyword1", keyword1JsonNode);
        // 投诉时间：{{keyword2.DATA}}
        JSONObject keyword2JsonNode = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        keyword2JsonNode.put("value",
                sdf.format(complaintSheet.getCreateDate()));
        data.put("keyword2", keyword2JsonNode);
        // 处理情况：{{keyword3.DATA}}
        JSONObject keyword3JsonNode = new JSONObject();
        // 获取投诉处理结果
        ComplaintHandleInfo complaintHandle = handleMgr
                .getFinallyHandle(complaintSheet.getId());
        keyword3JsonNode.put("value", complaintHandle.getHandleSay());
        data.put("keyword3", keyword3JsonNode);
        // 咨询电话：{{keyword4.DATA}}
        JSONObject keyword4JsonNode = new JSONObject();
        String officialTelephone = String.valueOf(scCache
                .get("OfficialTelephone"));
        keyword4JsonNode.put("value", officialTelephone);
        data.put("keyword4", keyword4JsonNode);
        // {{remark.DATA}}
        JSONObject remarkJsonNode = new JSONObject();
        remarkJsonNode.put("value", "感谢您的反馈。");
        data.put("remark", remarkJsonNode);

        jsonBody.put("data", data);
        return jsonBody.toJSONString();
    }

    /**
     * 展示进度节点
     * @param flowStatus
     * @return
     */
    private String showFlowNode(String flowStatus) {
        String text = null;
        switch (flowStatus) {
        case ConstGlobal.COMPLAINT_FLOW_STATUS_SUBMITTED:
            text = "已提交";
            break;
        case ConstGlobal.COMPLAINT_FLOW_STATUS_ACCEPTED:
            text = "已受理";
            break;
        case ConstGlobal.COMPLAINT_FLOW_STATUS_AUDITED:
            text = "已审核";
            break;
        case ConstGlobal.COMPLAINT_FLOW_STATUS_SHUNTED:
            text = "已分流";
            break;
        case ConstGlobal.COMPLAINT_FLOW_STATUS_ALREADYHANDLED:
            text = "已办理";
            break;
        case ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED:
            text = "已结束";
            break;
        default:
            text = "已起草";
            break;
        }
        return text;
    }

    public String getScheduleTemplateId() {
        return scheduleTemplateId;
    }

    public void setScheduleTemplateId(String scheduleTemplateId) {
        this.scheduleTemplateId = scheduleTemplateId;
    }

    public String getResultTemplateId() {
        return resultTemplateId;
    }

    public void setResultTemplateId(String resultTemplateId) {
        this.resultTemplateId = resultTemplateId;
    }
}
