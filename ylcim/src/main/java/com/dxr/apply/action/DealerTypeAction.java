package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.DealerTypeInfo;
import com.dxr.apply.service.api.IDealerType;

/**
 * @description: <经营者类型Action层>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerTypeAction extends BasalAction {

    private static final long serialVersionUID = -2263533099503122618L;

    @Autowired
    private IDealerType dealerTypeMgr;
    
    private DealerTypeInfo dealerTypeInfo;
    
    /**
     * 获取经营者类型列表, DataGrid形式展示
     */
    public void getTypePage() {
        try {
            String result = dealerTypeMgr.getTypePage(page, rows, dealerTypeInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerTypeAction.getTypePage() is error...", e);
            super.emptyGrid();
        }
    }
    
    /**
     * 保存经营者类型
     */
    public void saveType(){
        try {
            String result = dealerTypeMgr.saveType(dealerTypeInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerTypeAction.saveType() is error...", e);
            super.brace();
        }
    }
    
    /**
     * 更新经营者类型
     */
    public void updateType(){
        try {
            String result = dealerTypeMgr.updateType(dealerTypeInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerTypeAction.updateType() is error...", e);
            super.brace();
        }
    }
    
    /**
     * 删除经营者类型, 逻辑删除
     */
    public void deleteType(){
        try {
            String result = dealerTypeMgr.deleteType(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerTypeAction.deleteType() is error...", e);
            super.brace();
        }
    }
    
    /**
     * 获取经营者信息详细
     */
    public void getDetails(){
        try {
            String result = dealerTypeMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("DealerTypeAction.getDetails() is error...", e);
            super.brace();
        }
    }
    
    /**
     * 查询经营者类型下拉列表
     */
    public void getTypeCommBox(){
        try{
            String result = dealerTypeMgr.getTypeCommBox();
            super.writeToView(result);
        }catch(Exception e){
            logger.error("DealerTypeAction.getTypeCommBox() is error...", e);
            super.emptyGrid();
        }
    }

    public DealerTypeInfo getDealerTypeInfo() {
        return dealerTypeInfo;
    }

    public void setDealerTypeInfo(DealerTypeInfo dealerTypeInfo) {
        this.dealerTypeInfo = dealerTypeInfo;
    }
}
