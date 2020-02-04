package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.ComplaintHandleInfo;
import com.dxr.apply.service.api.IComplaintHandle;

/**
 * @description: <投诉处理Action层>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintHandleAction extends BasalAction {

    private static final long serialVersionUID = -3281194445472856259L;

    @Autowired
    private IComplaintHandle handleMgr;

    private ComplaintHandleInfo complaintHandleInfo;

    @Override
    public String formView() {
        super.putViewDeftData("handleProcess",
                ConstGlobal.COMPLAINT_HANDLE_TYPE_PROCESS);
        super.putViewDeftData("handleFinally",
                ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY);
        if ("handleGrid".equals(viewType)) {
            return "handleGridView";
        }
        return super.formView();
    }

    /**
     * 获取投诉处理列表, DataGrid形式展示
     */
    public void getComplaintHandlePage() {
        try {
            String result = handleMgr.getComplaintHandlePage(page, rows,
                    complaintHandleInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ComplaintHandleAction.getComplaintHandlePage() is error...",
                    e);
            super.emptyGrid();
        }
    }

    /**
     * 保存投诉处理
     */
    public void saveComplaintHandle() {
        try {
            String result = handleMgr.saveComplaintHandle(complaintHandleInfo,
                    false);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintHandleAction.saveComplaintHandle() is error...",
                    e);
            super.operateException();
        }
    }

    /**
     * 刪除投诉处理
     */
    public void deleteComplaintHandle() {
        try {
            String result = handleMgr.deleteComplaintHandle(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ComplaintHandleAction.deleteComplaintHandle() is error...",
                    e);
            super.operateException();
        }
    }

    /**
     * 是否最终结论
     */
    public void isFinally() {
        try {
            String result = handleMgr.isFinally(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("ComplaintHandleAction.isFinally() is error...", e);
            super.operateException();
        }
    }

    public ComplaintHandleInfo getComplaintHandleInfo() {
        return complaintHandleInfo;
    }

    public void setComplaintHandleInfo(ComplaintHandleInfo complaintHandleInfo) {
        this.complaintHandleInfo = complaintHandleInfo;
    }
}
