package com.dawnwing.framework.supers.service.impl;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;

public abstract class BasalMgr<T> implements IBasalMgr<T> {

    /**
     * 成功
     */
    protected static String SUCCESS = "{\"success\": true}";

    /**
     * 失败
     */
    protected static String FAILURE = "{\"success\": false}";

    /**
     * 空表格
     */
    protected String EMPTY_GRID = "{\"total\": 0, \"rows\": []}";
    
    /**
     * 空数组
     */
    protected String EMPTY_ARRAY = "[]";

    /**
     * 根据id获取数据库对象, 抽象方法, 子类必须覆盖
     * @param id
     * @return
     */
    public abstract T getDbObject(String id) throws ServiceException;
}
