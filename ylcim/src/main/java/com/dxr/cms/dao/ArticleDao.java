package com.dxr.cms.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.cms.entity.ArticleInfo;

/**
 * @description: <CMS 文章信息数据操作层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ArticleDao extends BasalDao<ArticleInfo> {

    /**
     * @param start
     * @param limit
     * @param filterList
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ArticleInfo> getArticlePage(int start, int limit,
            List<FilterBean> filterList) {
        String hql = "select new ArticleInfo(article, column.name) "
                + "from ArticleInfo article, ColumnInfo column where 1 = 1 "
                + "and article.columnId = column.id ";
        hql = super.setFilterBean(filterList, hql);
        hql = super.orderBy(hql, "article.createDate desc");
        Query query = super.createQuery(filterList, hql);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * @param filterList
     * @return
     */
    public long getTotal(List<FilterBean> filterList) {
        String hql = "select count(article.id) from ArticleInfo article where 1 = 1 ";
        hql = this.setFilterBean(filterList, hql);
        Query query = this.createQuery(filterList, hql);
        return (Long) query.uniqueResult();
    }

    /**
     * 查找文章列表
     * @param start
     * @param limit
     * @param articleInfo id、searchIndex
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ArticleInfo> findArticleList(int start, int limit,
            ArticleInfo articleInfo) {
        StringBuilder hqlBuilder = new StringBuilder();
        hqlBuilder
                .append("select new ArticleInfo(article.id, article.name, article.columnId, article.articleFrom, ");
        hqlBuilder
                .append("article.articleType, article.deliveryDate, article.searchIndex, article.summary, column.name) ");
        hqlBuilder
                .append("from ArticleInfo article, ColumnInfo column where 1 = 1 ");
        hqlBuilder.append("and article.columnId = column.id ");
        hqlBuilder.append("and article.columnId = :columnId ");
        if (StringUtils.isNotEmpty(articleInfo.getSearchIndex())) {
            hqlBuilder
                    .append("and (article.name like :searchkey or article.searchIndex like :searchkey) ");
        }
        String hql = super.orderBy(hqlBuilder.toString(),
                "article.createDate desc");
        Query query = super.createQuery(hql);
        query.setParameter("columnId", articleInfo.getColumnId());
        if (StringUtils.isNotEmpty(articleInfo.getSearchIndex())) {
            query.setParameter("searchkey", "%" + articleInfo.getSearchIndex()
                    + "%");
        }
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }

    /**
     * 获取文章总数
     * @param articleInfo
     * @return
     */
    public long getArticleTotal(ArticleInfo articleInfo) {
        StringBuilder hqlBuilder = new StringBuilder(
                "select count(article.id) ");
        hqlBuilder
                .append("from ArticleInfo article, ColumnInfo column where 1 = 1 ");
        hqlBuilder.append("and article.columnId = column.id ");
        hqlBuilder.append("and article.columnId = :columnId ");
        if (StringUtils.isNotEmpty(articleInfo.getSearchIndex())) {
            hqlBuilder
                    .append("and (article.name like :searchkey or article.searchIndex like :searchkey) ");
        }
        Query query = super.createQuery(hqlBuilder.toString());
        query.setParameter("columnId", articleInfo.getColumnId());
        if (StringUtils.isNotEmpty(articleInfo.getSearchIndex())) {
            query.setParameter("searchkey", "%" + articleInfo.getSearchIndex()
                    + "%");
        }
        return (Long) query.uniqueResult();
    }
}
