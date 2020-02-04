package com.dxr.apply.service.api;

import java.util.List;
import java.util.Map;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.DealerCreditVo;
import com.dxr.apply.entity.WorkFlowLog;

/**
 * @description: <投诉登记表服务接口类>
 * @author: w.xL
 * @date: 2018-3-13
 */
public interface IComplaintSheet extends IBasalMgr<ComplaintSheetInfo> {

    /**
     * 获取投诉单列表page, EasyUI格式
     * 
     * @param page
     * @param rows
     * @param complaintSheetInfo
     * @return
     * @throws ServiceException
     */
    public String getComplaintSheetPage(int page, int rows,
            ComplaintSheetInfo complaintSheetInfo) throws ServiceException;

    /**
     * 保存投诉单
     * 
     * @param complaintSheet
     * @return
     * @throws ServiceException
     */
    public String saveComplaintSheet(ComplaintSheetInfo complaintSheet)
            throws ServiceException;

    /**
     * 更新投诉单
     * 
     * @param complaintSheet
     * @return
     * @throws ServiceException
     */
    public String updateComplaintSheet(ComplaintSheetInfo complaintSheet)
            throws ServiceException;

    /**
     * 删除投诉单
     * 
     * @param complaintId
     * @return
     * @throws ServiceException
     */
    public String deleteComplaintSheet(String complaintId)
            throws ServiceException;

    /**
     * 失信行为/投诉单详情
     * 
     * @param complaintId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String complaintId) throws ServiceException;

    /**
     * @param complaintId
     * @param flowLogId
     * @return
     * @throws ServiceException
     */
    public String updateWorkFlow(String complaintId, WorkFlowLog flowLog)
            throws ServiceException;

    /**
     * 获取待办列表, EasyUI格式
     * 
     * @param page
     * @param rows
     * @param complaintSheetInfo
     * @return
     * @throws ServiceException
     */
    public String getTodoListPage(int page, int rows,
            ComplaintSheetInfo complaintSheetInfo) throws ServiceException;

    /**
     * 审核校验
     * 
     * @param complaintId
     * @param flowLog
     * @return
     * @throws ServiceException
     */
    public String verifyComplaint(String complaintId, WorkFlowLog flowLog)
            throws ServiceException;

    /**
     * 获取流程日志, 根据投诉单ComplaintId
     * 
     * @param complaintId
     * @return
     * @throws ServiceException
     */
    public List<WorkFlowLog> getFlowLogListForComplaintId(String complaintId)
            throws ServiceException;

    /**
     * 更新是否需要公示
     * @param complaintSheetInfo (complaintId、isPublicity)
     * @return
     * @throws ServiceException
     */
    public String updateIsPublicity(ComplaintSheetInfo complaintSheetInfo)
            throws ServiceException;

    /**
     * 获取经营者失信行为列表
     * @param dealerId 经营者id
     * @return
     * @throws ServiceException
     */
    public List<DealerCreditVo> findDealerCreditList(String dealerId)
            throws ServiceException;

    /**
     * 统计投诉量
     * @param filterPar
     * @return
     * @throws ServiceException
     */
    public long statisticsComplaintSheet(Map<String, Object> filterPar)
            throws ServiceException;
}
