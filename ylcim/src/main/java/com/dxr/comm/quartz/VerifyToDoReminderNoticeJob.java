package com.dxr.comm.quartz;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.utils.DateUtils;
import com.dxr.apply.dao.ComplaintSheetDao;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.WorkFlowExample;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.entity.WorkFlowLog;
import com.dxr.apply.service.api.IFlowExample;
import com.dxr.apply.service.api.IFlowLog;
import com.dxr.apply.service.api.IWorkFlow;

/**
 * @description: <流程步骤每到6天后如果还没有处理, 需要一个提醒消息推送>
 * <br>每天10点跑任务, 满足条件（6天没有处理）即发送待办消息
 * @author: w.xL
 * @date: 2018-4-28
 */
public class VerifyToDoReminderNoticeJob {

    private static final Logger logger = LoggerFactory
            .getLogger(VerifyToDoReminderNoticeJob.class);

    @Autowired
    private ComplaintSheetDao complaintSheetDao;
    @Autowired
    private IFlowExample flowExampleMgr;
    @Autowired
    private IFlowLog flowLogMgr;
    @Autowired
    private IWorkFlow workFlowMgr;

    @Transactional(readOnly = true)
    public void reminderNotice() {
        logger.info("-----------------------------------TODO Reminder Notice Star -----------------------------------");
        try {
            // No1: 获取所有的待办列表, 正在运行的实例
            List<ComplaintSheetInfo> complaintList = complaintSheetDao
                    .getAllTodoListPage();
            // No2: 循环待办列表, 一一对比, 判断是否满足提醒条件(超过6天未处理)
            for (ComplaintSheetInfo complaintSheetInfo : complaintList) {
                String complaintId = complaintSheetInfo.getId();
                // 获取流程实例
                WorkFlowExample workflowExample = flowExampleMgr
                        .loadFlowExample(complaintId);
                //根据流程实例Id获取最近的处理日志
                WorkFlowLog workflowLog = flowLogMgr
                        .getRecentlyFlowLog(workflowExample.getId());
                Date lastDate = workflowLog.getCreateDate();
                // 根据最近处理时间和当前时间求差值
                int differ = DateUtils.differDays(lastDate, new Date());
                // 判断2个日期相差是否大于6天
                if (differ > 6) {
                    // No3： 发送提醒通知
                    WorkFlowInfo workFlow = workFlowMgr.loadNow(workflowExample
                            .getFlowNodeCode());
                    flowExampleMgr.afterSendMsg(workflowExample,
                            workFlow.getFlowNodeText());
                    logger.info("-----------More than 6 days untreated Send TODO Reminder: 【 投诉对象："
                            + complaintSheetInfo.getDealerName() + "】");
                }
            }
        } catch (ServiceException e) {
            logger.error("TODO Reminder Notice is error...", e);
        }

        logger.info("-----------------------------------TODO Reminder Notice End  -----------------------------------");
    }
}
