package com.dxr.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.system.dao.IconlibraryDao;
import com.dxr.system.entity.Iconlibrary;
import com.dxr.system.service.api.IIconlibrary;

public class IconlibraryMgr extends BasalMgr<Iconlibrary> implements
        IIconlibrary {

    @Autowired
    private IconlibraryDao iconlibraryDao;

    @Override
    public String queryIconlibrary(String from) throws ServiceException {
        List<Iconlibrary> iconlibraryList = iconlibraryDao
                .queryIconlibrary(from);
        return JSONArray.toJSONString(iconlibraryList);
    }

    @Override
    public String saveIconlibrary(Iconlibrary iconlibrary)
            throws ServiceException {
        iconlibraryDao.save(iconlibrary);
        return new Message("保存成功!").toString();
    }

    @Override
    public Iconlibrary getDbObject(String id) throws ServiceException {
        return iconlibraryDao.get(id);
    }

    @Override
    public String deleteIconlibrary(String from) throws ServiceException {
        iconlibraryDao.deleteIconlibrary(from);
        return new Message("删除成功!").toString();
    }

    @Override
    public List<Iconlibrary> queryIconlibraryList(String from)
            throws ServiceException {
        List<Iconlibrary> iconlibraryList = iconlibraryDao
                .queryIconlibrary(from);
        return iconlibraryList;
    }

}
