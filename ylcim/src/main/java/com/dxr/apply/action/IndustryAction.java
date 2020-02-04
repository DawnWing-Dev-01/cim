package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.service.api.IIndustry;

/**
 * @description: <行业信息管理Action层>
 * @author: w.xL
 * @date: 2018-2-27
 */
public class IndustryAction extends BasalAction {

    private static final long serialVersionUID = -2986304895969352782L;

    @Autowired
    private IIndustry industryMgr;

    private IndustryInfo industryInfo;

    /**
     * 预警阀值页面
     * @return
     */
    public String threshold() {
        return "thresholdView";
    }

    /**
     * 获取行业信息列表, DataGrid形式展示
     */
    public void getIndustryPage() {
        try {
            String result = industryMgr.getIndustryPage(page, rows,
                    industryInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.getIndustryPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存行业信息
     */
    public void saveIndustry() {
        try {
            String result = industryMgr.saveIndustry(industryInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.saveIndustry() is error...", e);
            super.brace();
        }
    }

    /**
     * 更新行业信息
     */
    public void updateIndustry() {
        try {
            String result = industryMgr.updateIndustry(industryInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.updateIndustry() is error...", e);
            super.brace();
        }
    }

    /**
     * 设置预警阀值
     */
    public void updateThreshold() {
        try {
            String result = industryMgr.updateThreshold(industryInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.updateThreshold() is error...", e);
            super.brace();
        }
    }

    /**
     * 删除行业信息, 逻辑删除
     */
    public void deleteIndustry() {
        try {
            String result = industryMgr.deleteIndustry(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.deleteIndustry() is error...", e);
            super.brace();
        }
    }

    /**
     * 获取行业信息详细
     */
    public void getDetails() {
        try {
            String result = industryMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.getDetails() is error...", e);
            super.brace();
        }
    }

    /**
     * 查询行业信息下拉列表
     */
    public void getIndustryCommBox() {
        try {
            String result = industryMgr.getIndustryCommBox();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("IndustryAction.getIndustryCommBox() is error...", e);
            super.emptyGrid();
        }
    }

    public IndustryInfo getIndustryInfo() {
        return industryInfo;
    }

    public void setIndustryInfo(IndustryInfo industryInfo) {
        this.industryInfo = industryInfo;
    }
}
