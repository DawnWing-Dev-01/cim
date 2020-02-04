package com.dxr.apply.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.ComplaintHandleInfo;

/**
 * @description: <投诉处理服务接口层>
 * @author: w.xL
 * @date: 2018-3-13
 */
public interface IComplaintHandle extends IBasalMgr<ComplaintHandleInfo> {

    /**
     * 获取投诉处理列表page, EasyUI格式
     * 
     * @param page
     * @param rows
     * @param complaintSheetInfo
     * @return
     * @throws ServiceException
     */
    public String getComplaintHandlePage(int page, int rows,
            ComplaintHandleInfo complaintHandleInfo) throws ServiceException;

    /**
     * 保存投诉处理
     * 
     * @param complaintHandleInfo
     * @param isFlush 是否需要强制刷新提交数据, 调用Session().flush();方法
     *          true 刷新; false 不刷新;
     * @return
     * @throws ServiceException
     */
    public String saveComplaintHandle(ComplaintHandleInfo complaintHandleInfo,
            boolean isFlush) throws ServiceException;

    /**
     * 删除投诉处理
     * 
     * @param handleId
     * @return
     * @throws ServiceException
     */
    public String deleteComplaintHandle(String handleId)
            throws ServiceException;

    /**
     * 是否最终结论
     * @param complaintId
     * @return {"isFinally":"true/false"}
     * @throws ServiceException
     */
    public String isFinally(String complaintId) throws ServiceException;

    /**
     * 获取投诉单最终处罚结果
     * @param complaintId
     * @return
     */
    public ComplaintHandleInfo getFinallyHandle(String complaintId);
}
