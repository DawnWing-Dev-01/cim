package com.dxr.system.entity;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @author w.xL
 * 组织机构
 */
public class Organization extends AbstractEntity {

    private static final long serialVersionUID = 2902572429251799820L;
    
    private String fatherId;
    
    /**
     * 部门编码
     */
    private String code;
    
    /**
     * 索引号
     */
    private String indexNum;
    
    /**
     * 图标
     */
    private String icon = "czs-control-rank";
    
    /**
     * 是否叶子节点
     * [1]是叶子;[0]不是叶子
     */
    private Integer isLeaf = ConstGlobal.TREE_IS_LEAF;
    
    private Integer sort;

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
