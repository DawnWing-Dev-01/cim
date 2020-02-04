package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
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
import com.dxr.apply.dao.WorkFlowDao;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.service.api.IWorkFlow;

/**
 * @description: <简易工作流服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-2
 */
public class WorkFlowMgr extends BasalMgr<WorkFlowInfo> implements IWorkFlow {

    @Autowired
    private WorkFlowDao workFlowDao;

    @Override
    public WorkFlowInfo getDbObject(String id) throws ServiceException {
        return workFlowDao.get(id);
    }

    @Override
    public String getWorkFlowPage(int page, int rows, WorkFlowInfo workFlowInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(workFlowInfo)) {
            if (StringUtils.isNotEmpty(workFlowInfo.getFlowNodeCode())) {
                filterList.add(new FilterBean("workFlow.flowNodeCode",
                        HqlSymbol.HQL_EQUAL, workFlowInfo.getFlowNodeCode()));
            }
            if (StringUtils.isNotEmpty(workFlowInfo.getFlowNodeText())) {
                filterList.add(new FilterBean("workFlow.flowNodeText",
                        HqlSymbol.HQL_EQUAL, workFlowInfo.getFlowNodeText()));
            }
        }

        List<WorkFlowInfo> workFlowList = workFlowDao.getWorkFlowPage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", workFlowDao.getTotal(filterList));
        result.put("rows", workFlowList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveWorkFlow(WorkFlowInfo workFlowInfo)
            throws ServiceException {
        String flowNodeCode = workFlowInfo.getFlowNodeCode();
        long count = workFlowDao.findWorkFlowByCode(flowNodeCode);
        if (count > 0) {
            throw new ServiceException(
                    "流节点Code【" + workFlowDao + "】已存在，请重新输入!",
                    ErrorCode.ERRCODE_DATA_REPEAT);
        }
        workFlowDao.save(workFlowInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateWorkFlow(WorkFlowInfo workFlowInfo)
            throws ServiceException {
        String flowNodeCode = workFlowInfo.getFlowNodeCode();
        WorkFlowInfo workFlowDb = getDbObject(workFlowInfo.getId());
        if (!flowNodeCode.equals(workFlowDb.getFlowNodeCode())) {
            long count = workFlowDao.findWorkFlowByCode(flowNodeCode);
            if (count > 0) {
                throw new ServiceException("流节点Code【" + workFlowDao
                        + "】已存在，请重新输入!", ErrorCode.ERRCODE_DATA_REPEAT);
            }
            workFlowDb.setFlowNodeCode(flowNodeCode);
        }
        workFlowDb.setFlowNodeText(workFlowInfo.getFlowNodeText());
        workFlowDb.setHandleMode(workFlowInfo.getHandleMode());
        workFlowDb.setHandleSubject(workFlowInfo.getHandleSubject());
        workFlowDb.setSubjectId(workFlowInfo.getSubjectId());
        workFlowDb.setSort(workFlowInfo.getSort());
        workFlowDb.setRemark(workFlowInfo.getRemark());
        workFlowDb.setUpdateDate(new Date());
        workFlowDao.update(workFlowDb);
        return new Message("更新成功！").toString();
    }

    @Override
    public String deleteWorkFlow(String workFlowId) throws ServiceException {
        WorkFlowInfo workFlowDb = getDbObject(workFlowId);
        workFlowDb.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        workFlowDao.update(workFlowDb);
        return new Message("删除成功！").toString();
    }

    @Override
    public String getDetails(String workFlowId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        WorkFlowInfo workFlowDb = getDbObject(workFlowId);
        detailsMap.put("workFlowInfo.id", workFlowDb.getId());
        detailsMap.put("workFlowInfo.flowNodeCode",
                workFlowDb.getFlowNodeCode());
        detailsMap.put("workFlowInfo.flowNodeText",
                workFlowDb.getFlowNodeText());
        detailsMap.put("workFlowInfo.handleMode", workFlowDb.getHandleMode());
        detailsMap.put("workFlowInfo.handleSubject",
                workFlowDb.getHandleSubject());
        detailsMap.put("workFlowInfo.subjectId", workFlowDb.getSubjectId());
        detailsMap.put("workFlowInfo.sort", workFlowDb.getSort());
        detailsMap.put("workFlowInfo.remark", workFlowDb.getRemark());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

    @Override
    public List<WorkFlowInfo> getWorkFlowList() throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        List<WorkFlowInfo> workflowList = workFlowDao.getWorkFlowPage(0,
                Integer.MAX_VALUE, filterList);
        return workflowList;
    }

    @Override
    public WorkFlowInfo loadNow(String flowCode) throws ServiceException {
        return workFlowDao.loadNow(flowCode);
    }

    @Override
    public WorkFlowInfo loadFirst() throws ServiceException {
        return workFlowDao.loadFirst();
    }

    @Override
    public WorkFlowInfo loadLast() throws ServiceException {
        return workFlowDao.loadLast();
    }

    @Override
    public WorkFlowInfo loadNext(String flowCode) throws ServiceException {
        return workFlowDao.loadNext(flowCode);
    }

    @Override
    public WorkFlowInfo loadPrev(String flowCode) throws ServiceException {
        return workFlowDao.loadPrev(flowCode);
    }
}
