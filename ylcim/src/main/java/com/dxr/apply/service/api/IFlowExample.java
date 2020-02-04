package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.WorkFlowExample;

/**
 * @description: <流程实例服务接口层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public interface IFlowExample extends IBasalMgr<WorkFlowExample> {

    /**
     * 根据业务Id(投诉单)获取流程实例
     * @param businessId
     * @return
     * @throws ServiceException
     */
    public WorkFlowExample loadFlowExample(String businessId)
            throws ServiceException;

    /**
     * 保存流程实例
     * @param flowExample
     * @param flowNodeText
     * @throws ServiceException
     */
    public void saveFlowExample(WorkFlowExample flowExample, String flowNodeText)
            throws ServiceException;

    /**
     * 更新流程实例
     * @param flowExample
     * @param flowNodeText
     * @throws ServiceException
     */
    public void updateFlowExample(WorkFlowExample flowExample,
            String flowNodeText) throws ServiceException;

    /**
     * 运行实例
     * @param businessId
     * @param handleResult 审核人的操作：同意: 1(继续执行); 驳回:0(返回上一级);结束: -1(over);
     * @param flowLogId流程日志Id
     * @param subjectId
     *            用户转交（指派）下一个处理人
     * @return 当前流程flowCode
     * @throws ServiceException
     */
    public String runExample(String businessId, Integer handleResult,
            String flowLogId, String subjectId) throws ServiceException;

    /**
     * 更新流程实例类型(正常受理、非正常受理)
     * @param flowExample
     * @return
     * @throws ServiceException
     */
    public String updateExampleType(WorkFlowExample flowExample)
            throws ServiceException;
    
    /**
     * 发送待办消息通知
     * @param flowExample
     * @throws ServiceException
     */
    public void afterSendMsg(WorkFlowExample flowExample, String flowNodeText)
            throws ServiceException;
}
