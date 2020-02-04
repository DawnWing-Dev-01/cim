package com.dxr.cms.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <CMS文章信息>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ArticleInfo extends AbstractEntity {

    private static final long serialVersionUID = 6824477171567117098L;

    public ArticleInfo() {
        super();
    }

    public ArticleInfo(ArticleInfo articleInfo, String columnName) {
        this.id = articleInfo.getId();
        this.name = articleInfo.getName();
        this.columnId = articleInfo.getColumnId();
        this.articleFrom = articleInfo.getArticleFrom();
        this.articleType = articleInfo.getArticleType();
        this.author = articleInfo.getAuthor();
        this.authorId = articleInfo.getAuthorId();
        this.deliveryDate = articleInfo.getDeliveryDate();
        this.searchIndex = articleInfo.getSearchIndex();
        this.summary = articleInfo.getSummary();
        this.content = articleInfo.getContent();
        this.createDate = articleInfo.getCreateDate();
        this.updateDate = articleInfo.getUpdateDate();
        this.columnName = columnName;
    }

    public ArticleInfo(String id, String name, String columnId,
            String articleFrom, String articleType, Date deliveryDate,
            String searchIndex, String summary, String columnName) {
        this.id = id;
        this.name = name;
        this.columnId = columnId;
        this.articleFrom = articleFrom;
        this.articleType = articleType;
        this.deliveryDate = deliveryDate;
        this.summary = summary;
        this.columnName = columnName;
        this.searchIndex = searchIndex;
    }

    /**
     * 栏目Id
     */
    private String columnId;

    /**
     * 文章来源
     */
    private String articleFrom;

    /**
     * 文章类型, 原创、转载
     */
    private String articleType;

    /**
     * 发布者
     */
    private String author;

    /**
     * 发布者用户Id
     */
    private String authorId;

    /**
     * 发布时间
     */
    private Date deliveryDate;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章栏目
     */
    private String columnName;

    /**
     * 检索索引(关键字)
     */
    private String searchIndex;

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getArticleFrom() {
        return articleFrom;
    }

    public void setArticleFrom(String articleFrom) {
        this.articleFrom = articleFrom;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSearchIndex() {
        return searchIndex;
    }

    public void setSearchIndex(String searchIndex) {
        this.searchIndex = searchIndex;
    }
}
