package com.dxr.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.ConsumerTipsDao;
import com.dxr.apply.entity.ConsumerTipsInfo;
import com.dxr.apply.service.api.IConsumerTips;
import com.dxr.cms.entity.ArticleInfo;
import com.dxr.cms.service.api.IArticle;

/**
 * @description: <12315消费提示服务接口实现类>
 * @author: w.xL
 * @date: 2018-3-24
 */
public class ConsumerTipsMgr extends BasalMgr<ConsumerTipsInfo> implements
        IConsumerTips {

    @Autowired
    private ConsumerTipsDao consumerTipsDao;

    @Autowired
    private IArticle articleMgr;

    @Override
    public String getConsumerTipsPage() throws ServiceException {
        List<ConsumerTipsInfo> tipsList = consumerTipsDao.getConsumerTipsPage();
        return JSONArray.toJSONString(tipsList);
    }

    @Override
    public ConsumerTipsInfo getDbObject(String id) throws ServiceException {
        return consumerTipsDao.get(id);
    }

    @Override
    public List<ConsumerTipsInfo> getConsumerTipsList() throws ServiceException {
        return consumerTipsDao.getConsumerTipsPage();
    }

    @Override
    public String addConsumerTips(String tipsId, ArticleInfo articleInfo)
            throws ServiceException {
        if (StringUtils.isNotEmpty(articleInfo.getId())) {
            articleMgr.updateArticle(articleInfo);
        } else {
            // 先保存消费提示
            articleMgr.saveArticle(articleInfo);
        }

        ConsumerTipsInfo tipsInfo = consumerTipsDao.get(tipsId);
        String articleId = tipsInfo.getArticleId();
        // 如果没有消费提示, 保存文章到消费提示下
        if (StringUtils.isEmpty(articleId)) {
            // 绑定文章
            tipsInfo.setArticleId(articleInfo.getId());
            consumerTipsDao.update(tipsInfo);
        }
        return new Message("发布成功!").toString();
    }

}
