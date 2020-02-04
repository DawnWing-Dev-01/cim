package com.dxr.apply.service.api;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.ComplaintResultInfo;

/**
 * @description: <投诉结果导入导出服务接口层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public interface IComplaintResult extends IBasalMgr<ComplaintResultInfo> {

    /**
     * @param page
     * @param rows
     * @param complaintResultInfo
     * @return
     * @throws ServiceException
     */
    public String getComplaintResultPage(int page, int rows,
            ComplaintResultInfo complaintResultInfo) throws ServiceException;

    /**
     * 历史失信行为转换为投诉结果
     * @throws ServiceException
     */
    public void saveHistoryToResult() throws ServiceException;

    /**
     * @param originTypeId
     * @param file
     * @param fileName
     * @return
     * @throws ServiceException
     */
    public String addResultForImport(String originTypeId, File file,
            String fileName) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     */
    public void exportComplaintResult(HttpServletResponse response,
            Date startDate, Date endDate, String originTypeId)
            throws ServiceException;

    /**
     * 更新关联经营者&&是否公示
     * @param complaintResultInfo
     * @return
     * @throws ServiceException
     */
    public String updateRelatedDealer(ComplaintResultInfo complaintResultInfo)
            throws ServiceException;

    /**
     * @param compResultId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String compResultId) throws ServiceException;

    /**
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public List<ComplaintResultInfo> findComplaintResultList(String dealerId)
            throws ServiceException;

    /**
     * 添加
     * @param complaintResultInfo
     * @return
     * @throws ServiceException
     */
    public String saveComplaintResult(ComplaintResultInfo complaintResultInfo)
            throws ServiceException;

    /**
     * 修改
     * @param complaintResultInfo
     * @return
     * @throws ServiceException
     */
    public String updateComplaintResult(ComplaintResultInfo complaintResultInfo)
            throws ServiceException;

    /**
     * 删除
     * @param resultId
     * @return
     * @throws ServiceException
     */
    public String deleteComplaintResult(String resultId)
            throws ServiceException;
}
