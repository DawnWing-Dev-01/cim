package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.DealerInfo;
import com.dxr.apply.service.api.IDealer;

/**
 * @description: <经营者信息Action层>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerAction extends BasalAction {

    private static final long serialVersionUID = -2263533099503122618L;

    @Autowired
    private IDealer dealerMgr;

    private DealerInfo dealerInfo;

    @Override
    public String formView() {
        if ("showQrCode".equals(viewType)) {
            try {
                String showQrCodeUrl = dealerMgr.createDealerQrCodeWithBgi(object);
                super.putViewDeftData("showQrCodeUrl", showQrCodeUrl);
            } catch (ServiceException e) {
                logger.error("DealerAction.showQrCodeUrl() is error...", e);
            }
            return "showQrCodeView";
        } else if ("choose".equals(viewType)) {
            return "chooseView";
        } else if ("import".equals(viewType)) {
            return "importView";
        } else if ("searchform".equals(viewType)) {
            return "searchform";
        }
        return super.formView();
    }

    /**
     * 经营者信息查询界面
     * @return
     */
    public String search() {
        return "searchView";
    }

    /**
     * 获取经营者信息列表, DataGrid形式展示
     */
    public void getDealerPage() {
        try {
            String result = dealerMgr.getDealerPage(page, rows, dealerInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.getDealerPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存经营者信息
     */
    public void saveDealer() {
        try {
            String result = dealerMgr.saveDealer(dealerInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.saveDealer() is error...", e);
            super.brace();
        }
    }

    /**
     * 更新经营者信息
     */
    public void updateDealer() {
        try {
            String result = dealerMgr.updateDealer(dealerInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.updateDealer() is error...", e);
            super.brace();
        }
    }

    /**
     * 删除经营者信息, 逻辑删除
     */
    public void deleteDealer() {
        try {
            String result = dealerMgr.deleteDealer(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.deleteDealer() is error...", e);
            super.brace();
        }
    }

    /**
     * 获取经营者信息详细
     */
    public void getDetails() {
        try {
            String result = dealerMgr.getDetails(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.getDetails() is error...", e);
            super.brace();
        }
    }

    /**
     * 生成二维码
     */
    public void generateQrCode() {
        try {
            String result = dealerMgr.generateDealerQrCode(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.generateQrCode() is error...", e);
            super.brace();
        }
    }

    /**
     * 批量生成二维码
     */
    public void generateBatchQrCode() {
        try {
            String result = dealerMgr.generateBatchDealerQrCode(strArray);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("DealerAction.generateBatchQrCode() is error...", e);
            super.brace();
        }
    }

    /**
     * 批量生成二维码
     */
    public void baleTempZip() {
        try {
            String zipAttachId = dealerMgr.addTempZipFile(strArray);
            super.writeToView(zipAttachId);
        } catch (ServiceException e) {
            logger.error("DealerAction.baleTempZip() is error...", e);
            super.empty();
        }
    }

    /**
     * 批量生成二维码
     */
    public void downloadBatch() {
        try {
            dealerMgr.downloadBatch(object, super.getRequest(),
                    super.getResponse());
        } catch (ServiceException e) {
            logger.error("DealerAction.downloadBatch() is error...", e);
        }
    }

    /**
     * 运营商导入
     */
    public void dealerImport() {
        try {
            String result = dealerMgr.addDealerForImport(file, fileFileName);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerAction.dealerImport() is error...", e);
            super.error("导入失败, 请联系管理员!<br>" + e.getMessage());
        }
    }
    
    /**
     * 展示二维码
     */
    public void showQrCode(){
        try {
            dealerMgr.showImage(object, super.getResponse());
        } catch (Exception e) {
            logger.error("DealerAction.showQrCode() is error...", e);
        }
    }

    public DealerInfo getDealerInfo() {
        return dealerInfo;
    }

    public void setDealerInfo(DealerInfo dealerInfo) {
        this.dealerInfo = dealerInfo;
    }
}
