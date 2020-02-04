package com.dxr.cms.service.api;

import java.util.Map;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.cms.entity.ArticleInfo;

/**
 * @description: <CMS文章服务接口层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public interface IArticle extends IBasalMgr<ArticleInfo> {

    /**
     * 获取文章列表, EasyUI格式
     * @param page
     * @param rows
     * @param articleInfo
     * @return
     * @throws ServiceException
     */
    public String getArticlePage(int page, int rows, ArticleInfo articleInfo)
            throws ServiceException;

    /**
     * 获取文章列表, wechat公众号列表
     * @param page
     * @param rows
     * @param articleInfo
     * @return
     * @throws ServiceException
     */
    public Map<String, Object> findArticleList(int page, int rows,
            ArticleInfo articleInfo) throws ServiceException;

    /**
     * 保存文章
     * @param articleInfo
     * @return
     * @throws ServiceException
     */
    public String saveArticle(ArticleInfo articleInfo) throws ServiceException;

    /**
     * 更新文章
     * @param articleInfo
     * @return
     * @throws ServiceException
     */
    public String updateArticle(ArticleInfo articleInfo)
            throws ServiceException;

    /**
     * 删除文章
     * @param articleId
     * @return
     * @throws ServiceException
     */
    public String deleteArticle(String articleId) throws ServiceException;

    /**
     * @param articleId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String articleId) throws ServiceException;
}
