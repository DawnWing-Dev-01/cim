package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.service.api.IOriginType;
import com.dxr.system.entity.SystemConstant;

/**
 * @description: <投诉结果来源类型Action层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class OriginTypeAction extends BasalAction {

    private static final long serialVersionUID = -1760336433297965543L;

    @Autowired
    private IOriginType originTypeMgr;
    
    private SystemConstant constant;
    
    /**
     * 获取投诉结果来源类型列表, DataGrid形式展示
     */
    public void getOriginTypePage() {
        try {
            String result = originTypeMgr.getOriginTypePage(page, rows, constant);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("OriginTypeAction.getOriginTypePage() is error...", e);
            super.emptyGrid();
        }
    }
    
    /**
     * 保存来源类型
     */
    public void saveOriginType() {
        try {
            String result = originTypeMgr.saveOriginType(constant);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("OriginTypeAction.saveOriginType() is error...", e);
            super.error(e.getMessage());
        }
    }
    
    /**
     * 修改来源类型
     */
    public void updateOriginType() {
        try {
            String result = originTypeMgr.updateOriginType(constant);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("OriginTypeAction.updateOriginType() is error...", e);
            super.error(e.getMessage());
        }
    }
    
    /**
     * 删除来源类型
     */
    public void deleteOriginType() {
        try {
            String result = originTypeMgr.deleteOriginType(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("OriginTypeAction.deleteOriginType() is error...", e);
            super.error(e.getMessage());
        }
    }
    
    /**
     * 获取来源类型详细
     */
    public void getDetails() {
        try {
            String result = originTypeMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("originTypeMgr.getDetails() is error...", e);
            super.brace();
        }
    }
    
    /**
     * 获取下拉框
     */
    public void getOriginTypeCommbox() {
        try {
            String result = originTypeMgr.getOriginTypeCommbox();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("OriginTypeAction.getOriginTypeCommbox() is error...", e);
            super.bracket();
        }
    }

    public SystemConstant getConstant() {
        return constant;
    }

    public void setConstant(SystemConstant constant) {
        this.constant = constant;
    }
}
