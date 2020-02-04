package com.dxr.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.system.dao.SystemConstantDao;
import com.dxr.system.entity.SystemConstant;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.service.api.ISystemConstant;

/**
 * @description: <系统常量服务接口实现类>
 * @author: w.xL
 * @date: 2018-3-28
 */
public class SystemConstantMgr extends BasalMgr<SystemConstant> implements
        ISystemConstant {

    @Autowired
    private SystemConstantDao systemConstantDao;

    @Autowired
    private SystemConstantCache scCache;

    @Override
    public SystemConstant getDbObject(String id) throws ServiceException {
        return systemConstantDao.get(id);
    }

    @Override
    public String getSystemConstantPage(int page, int rows,
            SystemConstant constant) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (constant != null) {
            if (StringUtils.isNotEmpty(constant.getScKey())) {
                filterList.add(new FilterBean("constant.scKey",
                        HqlSymbol.HQL_LIKE, "%" + constant.getScKey().trim()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(constant.getScGroup())) {
                filterList.add(new FilterBean("constant.scGroup",
                        HqlSymbol.HQL_EQUAL, constant.getScGroup().trim()));
            }
        }
        List<SystemConstant> constantList = systemConstantDao
                .getSystemConstantPage((page - 1) * rows, rows, filterList);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", systemConstantDao.getTotal(filterList));
        result.put("rows", constantList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public List<SystemConstant> findSystemConstantList(int page, int rows,
            SystemConstant constant) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (constant != null) {
            if (StringUtils.isNotEmpty(constant.getScKey())) {
                filterList.add(new FilterBean("constant.scKey",
                        HqlSymbol.HQL_LIKE, "%" + constant.getScKey().trim()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(constant.getScGroup())) {
                filterList.add(new FilterBean("constant.scGroup",
                        HqlSymbol.HQL_EQUAL, constant.getScKey().trim()));
            }
        }
        List<SystemConstant> constantList = systemConstantDao
                .getSystemConstantPage((page - 1) * rows, rows, filterList);
        return constantList;
    }

    @Override
    public String getSystemConstantCommbox(String group)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (StringUtils.isNotEmpty(group)) {
            filterList.add(new FilterBean("constant.scGroup",
                    HqlSymbol.HQL_EQUAL, group.trim()));
        }
        List<SystemConstant> constantList = systemConstantDao
                .getSystemConstantPage(0, Integer.MAX_VALUE, filterList);
        return JSONArray.toJSONString(constantList);
    }

    @Override
    public Integer setCache() throws ServiceException {
        // No1: 先清理
        scCache.clear();

        // No2: 再查询数据, 设置缓存
        List<SystemConstant> constantList = findSystemConstantList(0,
                Integer.MAX_VALUE, null);
        for (SystemConstant systemConstant : constantList) {
            scCache.put(systemConstant.getScKey(), systemConstant.getScVal());
        }
        return constantList.size();
    }

    @Override
    public String saveSystemConstant(SystemConstant constant)
            throws ServiceException {
        String scKey = constant.getScKey();
        long total = systemConstantDao.findConstantByKey(scKey);
        if (total > 0) {
            throw new ServiceException("数据字典Code【" + scKey + "】已存在，请重新输入!",
                    ErrorCode.ERRCODE_DATA_REPEAT);
        }
        systemConstantDao.save(constant);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateSystemConstant(SystemConstant constant)
            throws ServiceException {
        String scKey = constant.getScKey();
        SystemConstant constantDb = getDbObject(constant.getId());
        if (!scKey.equals(constantDb.getScKey())) {
            long total = systemConstantDao.findConstantByKey(scKey);
            if (total > 0) {
                throw new ServiceException("数据字典Code【" + scKey + "】已存在，请重新输入!",
                        ErrorCode.ERRCODE_DATA_REPEAT);
            }
            constantDb.setScKey(scKey);
        }
        constantDb.setScVal(constant.getScVal());
        constantDb.setScGroup(constant.getScGroup());
        constantDb.setRemark(constant.getRemark());
        constantDb.setUpdateDate(new Date());
        systemConstantDao.update(constantDb);
        return new Message("更新成功！").toString();
    }

    @Override
    public String deleteSystemConstant(String constantId)
            throws ServiceException {
        systemConstantDao.deleteById(constantId);
        return new Message("删除成功！").toString();
    }

    @Override
    public String getDetails(String constantId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        SystemConstant constant = getDbObject(constantId);
        detailsMap.put("constant.id", constant.getId());
        detailsMap.put("constant.scKey", constant.getScKey());
        detailsMap.put("constant.scVal", constant.getScVal());
        detailsMap.put("constant.scGroup", constant.getScGroup());
        detailsMap.put("constant.sort", constant.getSort());
        detailsMap.put("constant.remark", constant.getRemark());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

}
