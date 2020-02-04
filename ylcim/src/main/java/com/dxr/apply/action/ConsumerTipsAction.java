package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.service.api.IConsumerTips;
import com.dxr.cms.entity.ArticleInfo;

/**
 * @description: <12315消费提示Action层>
 * @author: w.xL
 * @date: 2018-3-24
 */
public class ConsumerTipsAction extends BasalAction {

    private static final long serialVersionUID = -9033298445107551607L;

    @Autowired
    private IConsumerTips consumerTipsMgr;

    private ArticleInfo articleInfo;

    /**
     * 获取12315消费提示列表
     */
    public void getConsumerTipsPage() {
        try {
            String result = consumerTipsMgr.getConsumerTipsPage();
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error(
                    "ConsumerTipsAction.getConsumerTipsPage() is error...", e);
            super.bracket();
        }
    }

    /**
     * 发布12315消费提示
     */
    public void addConsumerTips() {
        try {
            String result = consumerTipsMgr
                    .addConsumerTips(object, articleInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("ConsumerTipsAction.addConsumerTips() is error...", e);
            super.operateException();
        }
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }
}
