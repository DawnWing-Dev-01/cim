package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.WorkFlowLog;

/**
 * @description: <流程日志服务接口层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public interface IFlowLog extends IBasalMgr<WorkFlowLog> {

    /**
     * 保存流程日志
     * 
     * @param flowLog
     * @throws ServiceException
     */
    public void saveFlowLog(WorkFlowLog flowLog) throws ServiceException;

    /**
     * 更新流程日志
     * 
     * @param flowLog
     * @throws ServiceException
     */
    public void updateFlowLog(WorkFlowLog flowLog) throws ServiceException;

    /**
     * 获取流程日志, 根据流程实例Id
     * 
     * @param flowExampleId
     * @return
     * @throws ServiceException
     */
    public List<WorkFlowLog> getFlowLogList(String flowExampleId)
            throws ServiceException;

    /**
     * 获取最近的一个流程日志
     * @param flowExampleId
     * @return
     * @throws ServiceException
     */
    public WorkFlowLog getRecentlyFlowLog(String flowExampleId)
            throws ServiceException;
}
