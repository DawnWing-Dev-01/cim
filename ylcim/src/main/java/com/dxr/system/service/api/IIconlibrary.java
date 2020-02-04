package com.dxr.system.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.Iconlibrary;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-3-20
 */
public interface IIconlibrary extends IBasalMgr<Iconlibrary> {

    /**
     * @param from
     * @return
     * @throws ServiceException
     */
    public String queryIconlibrary(String from) throws ServiceException;

    /**
     * @param from
     * @return
     * @throws ServiceException
     */
    public List<Iconlibrary> queryIconlibraryList(String from)
            throws ServiceException;

    /**
     * @param iconlibrary
     * @return
     * @throws ServiceException
     */
    public String saveIconlibrary(Iconlibrary iconlibrary)
            throws ServiceException;

    /**
     * @param from
     * @return
     * @throws ServiceException
     */
    public String deleteIconlibrary(String from) throws ServiceException;
}
