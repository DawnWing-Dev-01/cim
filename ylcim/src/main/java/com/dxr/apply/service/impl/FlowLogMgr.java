package com.dxr.apply.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.apply.dao.FlowLogDao;
import com.dxr.apply.entity.WorkFlowLog;
import com.dxr.apply.service.api.IFlowLog;

/**
 * @description: <流程日志服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-3
 */
public class FlowLogMgr extends BasalMgr<WorkFlowLog> implements IFlowLog {

    @Autowired
    private FlowLogDao flowLogDao;

    @Override
    public WorkFlowLog getDbObject(String id) throws ServiceException {
        return flowLogDao.get(id);
    }

    @Override
    public void saveFlowLog(WorkFlowLog flowLog) throws ServiceException {
        flowLogDao.save(flowLog);
    }

    @Override
    public void updateFlowLog(WorkFlowLog flowLog) throws ServiceException {
        WorkFlowLog flowLogDb = getDbObject(flowLog.getId());
        flowLogDb.setFlowExampleId(flowLog.getFlowExampleId());
        flowLogDb.setFlowNodeId(flowLog.getFlowNodeId());
        flowLogDb.setFlowNodeText(flowLog.getFlowNodeText());
        flowLogDb.setUpdateDate(new Date());
        flowLogDao.update(flowLogDb);
    }

    @Override
    public List<WorkFlowLog> getFlowLogList(String flowExampleId)
            throws ServiceException {
        return flowLogDao.getFlowLogList(flowExampleId);
    }

    @Override
    public WorkFlowLog getRecentlyFlowLog(String flowExampleId)
            throws ServiceException {
        return flowLogDao.getRecentlyFlowLog(flowExampleId);
    }

}
