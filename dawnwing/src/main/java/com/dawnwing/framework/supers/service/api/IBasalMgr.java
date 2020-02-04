package com.dawnwing.framework.supers.service.api;

import com.dawnwing.framework.core.ServiceException;

public interface IBasalMgr<T> {

    /**
     * 根据id获取数据库对象, 抽象方法, 子类必须覆盖
     * @param id
     * @return
     */
    public T getDbObject(String id) throws ServiceException;
}
