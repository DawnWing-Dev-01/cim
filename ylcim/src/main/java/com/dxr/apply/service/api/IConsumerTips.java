package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.apply.entity.ConsumerTipsInfo;
import com.dxr.cms.entity.ArticleInfo;

/**
 * @description: <12315消费提示服务接口类>
 * @author: w.xL
 * @date: 2018-3-24
 */
public interface IConsumerTips {

    /**
     * 获取12315消费提示列表
     * @return
     * @throws ServiceException
     */
    public List<ConsumerTipsInfo> getConsumerTipsList() throws ServiceException;

    /**
     * 获取12315消费提示列表
     * @return
     * @throws ServiceException
     */
    public String getConsumerTipsPage() throws ServiceException;

    /**
     * @param tipsId
     * @param articleInfo
     * @return
     * @throws ServiceException
     */
    public String addConsumerTips(String tipsId, ArticleInfo articleInfo)
            throws ServiceException;
}
