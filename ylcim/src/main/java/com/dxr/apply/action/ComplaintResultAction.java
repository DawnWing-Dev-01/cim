package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.ComplaintResultInfo;
import com.dxr.apply.service.api.IComplaintResult;

/**
 * @description: <投诉结果导入导出Action层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class ComplaintResultAction extends BasalAction {

    private static final long serialVersionUID = 2943278730435764264L;

    @Autowired
    private IComplaintResult complaintResultMgr;

    private ComplaintResultInfo complaintResult;

    private String originPublicity;

    @Override
    public String formView() {
        if ("import".equals(viewType)) {
            return "importView";
        } else if ("linkedPublicity".equals(viewType)) {
            return "linkedPublicityView";
        } else if ("export".equals(viewType)) {
            return "exportView";
        }
        return super.formView();
    }

    /**
     * 获取投诉结果来源类型列表, DataGrid形式展示
     */
    public void getComplaintResultPage() {
        try {
            if (complaintResult != null
                    && originPublicity
                            .equals(complaintResult.getOriginTypeId())) {
                complaintResultMgr.saveHistoryToResult();
            }
            String result = complaintResultMgr.getComplaintResultPage(page,
                    rows, complaintResult);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ComplaintResultAction.getOriginTypePage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 投诉结果导入
     */
    public void importResult() {
        try {
            String result = complaintResultMgr.addResultForImport(object, file,
                    fileFileName);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ComplaintResultAction.compResultImport() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 投诉结果导出
     */
    public void exportResult() {
        try {
            // 判断是否是公示平台的数据
            if (originPublicity.equals(object)) {
                complaintResultMgr.saveHistoryToResult();
            }
            complaintResultMgr.exportComplaintResult(super.getResponse(),
                    startDate, endDate, object);
        } catch (Exception e) {
            logger.error("ComplaintResultAction.exportResult() is error...", e);
        }
    }

    /**
     * 获取信息详细
     */
    public void getDetails() {
        try {
            String result = complaintResultMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ComplaintResultAction.getDetails() is error...", e);
            super.brace();
        }
    }

    /**
     * 关联&&公示
     */
    public void linkedPublicity() {
        try {
            String result = complaintResultMgr
                    .updateRelatedDealer(complaintResult);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ComplaintResultAction.linkedPublicity() is error...",
                    e);
            super.error("操作异常");
        }
    }

    /**
     * 添加投诉结果 
     */
    public void saveComplaintResult() {
        try {
            String result = complaintResultMgr
                    .saveComplaintResult(complaintResult);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintResultAction.addComplaintResult() is error...", e);
            super.error(e.getMessage());
        }
    }

    /**
     * 修改投诉结果 
     */
    public void updateComplaintResult() {
        try {
            String result = complaintResultMgr
                    .updateComplaintResult(complaintResult);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintResultAction.updateComplaintResult() is error...",
                    e);
            super.error("操作异常");
        }
    }

    /**
     * 删除投诉结果 
     */
    public void deleteComplaintResult() {
        try {
            String result = complaintResultMgr.deleteComplaintResult(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintResultAction.deleteComplaintResult() is error...",
                    e);
            super.error("操作异常");
        }
    }

    public ComplaintResultInfo getComplaintResult() {
        return complaintResult;
    }

    public void setComplaintResult(ComplaintResultInfo complaintResult) {
        this.complaintResult = complaintResult;
    }

    public void setOriginPublicity(String originPublicity) {
        this.originPublicity = originPublicity;
    }
}
