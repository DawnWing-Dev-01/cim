package com.dxr.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.cms.dao.ArticleDao;
import com.dxr.cms.entity.ArticleInfo;
import com.dxr.cms.service.api.IArticle;

/**
 * @description: <CMS文章服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ArticleMgr extends BasalMgr<ArticleInfo> implements IArticle {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public ArticleInfo getDbObject(String id) throws ServiceException {
        return articleDao.get(id);
    }

    @Override
    public String getArticlePage(int page, int rows, ArticleInfo articleInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(articleInfo)) {
            if (StringUtils.isNotEmpty(articleInfo.getColumnId())) {
                filterList.add(new FilterBean("article.columnId",
                        HqlSymbol.HQL_EQUAL, articleInfo.getColumnId()));
            }

            if (StringUtils.isNotEmpty(articleInfo.getName())) {
                filterList.add(new FilterBean("article.name",
                        HqlSymbol.HQL_LIKE, "%" + articleInfo.getName() + "%"));
            }

            if (StringUtils.isNotEmpty(articleInfo.getArticleFrom())) {
                filterList.add(new FilterBean("article.articleFrom",
                        HqlSymbol.HQL_LIKE, "%" + articleInfo.getArticleFrom()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(articleInfo.getArticleType())) {
                filterList.add(new FilterBean("article.articleType",
                        HqlSymbol.HQL_LIKE, "%" + articleInfo.getArticleType()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(articleInfo.getSearchIndex())) {
                filterList.add(new FilterBean("article.searchIndex",
                        HqlSymbol.HQL_LIKE, "%" + articleInfo.getSearchIndex()
                                + "%"));
            }
        }

        List<ArticleInfo> articleList = articleDao.getArticlePage((page - 1)
                * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", articleDao.getTotal(filterList));
        result.put("rows", articleList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public Map<String, Object> findArticleList(int page, int rows,
            ArticleInfo articleInfo) throws ServiceException {
        List<ArticleInfo> articleList = articleDao.findArticleList((page - 1)
                * rows, rows, articleInfo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", articleDao.getArticleTotal(articleInfo));
        result.put("rows", articleList);
        return result;
    }

    @Override
    public String saveArticle(ArticleInfo articleInfo) throws ServiceException {
        articleDao.save(articleInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateArticle(ArticleInfo articleInfo)
            throws ServiceException {
        String articleId = articleInfo.getId();
        ArticleInfo articleDb = articleDao.get(articleId);
        articleDb.setName(articleInfo.getName());
        articleDb.setArticleFrom(articleInfo.getArticleFrom());
        articleDb.setArticleType(articleInfo.getArticleType());
        articleDb.setAuthor(articleInfo.getAuthor());
        articleDb.setSummary(articleInfo.getSummary());
        articleDb.setContent(articleInfo.getContent());
        articleDb.setSearchIndex(articleInfo.getSearchIndex());
        articleDb.setUpdateDate(new Date());
        articleDao.update(articleDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteArticle(String articleId) throws ServiceException {
        articleDao.deleteById(articleId);
        return new Message("删除成功!").toString();
    }

    @Override
    public String getDetails(String articleId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        ArticleInfo articleInfo = articleDao.get(articleId);

        detailsMap.put("articleInfo.id", articleInfo.getId());
        detailsMap.put("articleInfo.name", articleInfo.getName());
        detailsMap.put("articleInfo.columnId", articleInfo.getColumnId());
        detailsMap.put("articleInfo.articleFrom", articleInfo.getArticleFrom());
        detailsMap.put("articleInfo.articleType", articleInfo.getArticleType());
        detailsMap.put("articleInfo.author", articleInfo.getAuthor());
        detailsMap.put("articleInfo.authorId", articleInfo.getAuthorId());
        detailsMap.put("articleInfo.deliveryDate",
                articleInfo.getDeliveryDate());
        detailsMap.put("articleInfo.searchIndex", articleInfo.getSearchIndex());
        detailsMap.put("articleInfo.summary", articleInfo.getSummary());
        detailsMap.put("articleInfo.content", articleInfo.getContent());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

}
