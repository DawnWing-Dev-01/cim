package com.dxr.cms.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.cms.entity.ColumnInfo;
import com.dxr.cms.service.api.IColumn;

/**
 * @description: <CMS文章管理 Action层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ColumnAction extends BasalAction {

    private static final long serialVersionUID = 9181456300355401256L;

    @Autowired
    private IColumn columnMgr;

    private ColumnInfo columnInfo;

    /**
     * 获取栏目信息列表, DataGrid形式展示
     */
    public void getColumnPage() {
        try {
            String result = columnMgr.getColumnPage(page, rows, columnInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ColumnAction.getIndustryPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 保存栏目信息
     */
    public void saveColumn() {
        try {
            String result = columnMgr.saveColumn(columnInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ColumnAction.saveColumn() is error...", e);
            super.operateException();
        }
    }

    /**
     * 更新栏目信息
     */
    public void updateColumn() {
        try {
            String result = columnMgr.updateColumn(columnInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ColumnAction.updateColumn() is error...", e);
            super.operateException();
        }
    }

    /**
     * 删除栏目信息
     */
    public void deleteColumn() {
        try {
            String result = columnMgr.deleteColumn(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ColumnAction.deleteColumn() is error...", e);
            super.operateException();
        }
    }

    /**
     * 获取栏目信息详细
     */
    public void getDetails() {
        try {
            String result = columnMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("ColumnAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }
}
