package com.dxr.cms.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.cms.entity.ArticleInfo;
import com.dxr.cms.service.api.IArticle;

/**
 * @description: <CMS文章  Action层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ArticleAction extends BasalAction {

    private static final long serialVersionUID = -5127558246735284391L;

    @Autowired
    private IArticle articleMgr;

    private ArticleInfo articleInfo;

    @Override
    public String formView() {
        // 手机预览模式
        if ("iphone".equals(viewType)) {
            return "showIphone";
        }
        return super.formView();
    }

    /**
     * 获取文章信息列表, DataGrid形式展示
     */
    public void getArticlePage() {
        try {
            String result = articleMgr.getArticlePage(page, rows, articleInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ArticleAction.getArticlePage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存文章信息
     */
    public void saveArticle() {
        try {
            String result = articleMgr.saveArticle(articleInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ArticleAction.saveArticle() is error...", e);
            super.operateException();
        }
    }

    /**
     * 更新文章信息
     */
    public void updateArticle() {
        try {
            String result = articleMgr.updateArticle(articleInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ArticleAction.updateArticle() is error...", e);
            super.operateException();
        }
    }

    /**
     * 删除文章信息
     */
    public void deleteArticle() {
        try {
            String result = articleMgr.deleteArticle(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ArticleAction.deleteArticle() is error...", e);
            super.operateException();
        }
    }

    /**
     * 获取文章详细信息
     */
    public void getDetails() {
        try {
            String result = articleMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ArticleAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }
}
