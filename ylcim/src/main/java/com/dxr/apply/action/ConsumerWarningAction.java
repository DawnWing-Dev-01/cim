package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.entity.ConsumerWarningInfo;
import com.dxr.apply.entity.WarningPublishRemind;
import com.dxr.apply.service.api.IConsumerWarning;
import com.dxr.apply.service.api.IWarningPublishRemind;
import com.dxr.comm.cache.SystemConstantCache;

/**
 * @description: <消费预警Action层>
 * @author: w.xL
 * @date: 2018-3-30
 */
public class ConsumerWarningAction extends BasalAction {

    private static final long serialVersionUID = 6599148048904658884L;

    @Autowired
    private IConsumerWarning consumerWarningMgr;
    @Autowired
    private SystemConstantCache scCache;
    @Autowired
    private IWarningPublishRemind wprMgr;

    private ConsumerWarningInfo warningInfo;
    private WarningPublishRemind wprInfo;

    @Override
    public String formView() {
        // 消费预警默认发布单位
        super.putViewDeftData("ewAuthor",
                String.valueOf(scCache.get("ConsumerWarningDefaultAuthor")));
        return super.formView();
    }

    /**
     * 历史预警页面
     * @return
     */
    public String history() {
        return "historyView";
    }

    /**
     * 获取行业信息列表, DataGrid形式展示
     */
    public void getConsumerWarningPage() {
        try {
            String result = consumerWarningMgr.getConsumerWarningPage(page,
                    rows, warningInfo, object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ConsumerWarningAction.getConsumerWarningPage() is error...",
                    e);
            super.emptyGrid();
        }
    }

    /**
     * 保存消费预警
     */
    public void saveConsumerWarning() {
        try {
            String result = consumerWarningMgr.saveConsumerWarning(warningInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ConsumerWarningAction.saveConsumerWarning() is error...",
                    e);
            super.brace();
        }
    }

    /**
     * 更新消费预警
     */
    public void updateConsumerWarning() {
        try {
            String result = consumerWarningMgr
                    .updateConsumerWarning(warningInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ConsumerWarningAction.updateConsumerWarning() is error...",
                    e);
            super.brace();
        }
    }

    /**
     * 删除消费预警
     */
    public void deleteConsumerWarning() {
        try {
            String result = consumerWarningMgr.deleteConsumerWarning(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error(
                    "ConsumerWarningAction.deleteConsumerWarning() is error...",
                    e);
            super.brace();
        }
    }

    /**
     * 获取行业信息详细
     */
    public void getDetails() {
        try {
            String result = consumerWarningMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ConsumerWarningAction.getDetails() is error...", e);
            super.brace();
        }
    }

    /**
     * 预警发布提醒, 查询上个月有效预警和预警阀值比较
     */
    public void warningPublishRemind() {
        try {
            String result = consumerWarningMgr.updateWarningPublishRemind();
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("ConsumerWarningAction.getDetails() is error...", e);
            super.emptyGrid();
        }
    }
    
    /**
     * 更新预警发布提示对象
     */
    public void updateWprShowType(){
        try {
            wprMgr.updateShowType(wprInfo);
        } catch (ServiceException e) {
            logger.error("ConsumerWarningAction.updateWprShowType() is error...", e);
        }
    }

    public ConsumerWarningInfo getWarningInfo() {
        return warningInfo;
    }

    public void setWarningInfo(ConsumerWarningInfo warningInfo) {
        this.warningInfo = warningInfo;
    }

    public WarningPublishRemind getWprInfo() {
        return wprInfo;
    }

    public void setWprInfo(WarningPublishRemind wprInfo) {
        this.wprInfo = wprInfo;
    }
}
