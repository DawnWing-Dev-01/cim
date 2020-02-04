package com.dxr.system.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.Iconlibrary;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-3-20
 */
public class IconlibraryDao extends BasalDao<Iconlibrary> {

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Iconlibrary> queryIconlibrary(String from){
        String hql = "select icon from Iconlibrary icon where 1 = 1 and icon.from = :from ";
        Query query = super.createQuery(hql);
        query.setParameter("from", from);
        return query.list();
    }
    
    /**
     * @param from
     * @return
     */
    public Integer deleteIconlibrary(String from){
        String hql = "delete Iconlibrary icon where icon.from = :from ";
        Query query = super.createQuery(hql.toString());
        query.setParameter("from", from);
        return query.executeUpdate();
    }
}
