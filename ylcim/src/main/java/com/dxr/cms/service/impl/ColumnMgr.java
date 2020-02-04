package com.dxr.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.cms.dao.ColumnDao;
import com.dxr.cms.entity.ColumnInfo;
import com.dxr.cms.service.api.IColumn;

/**
 * @description: <CMS 栏目服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-22
 */
public class ColumnMgr extends BasalMgr<ColumnInfo> implements IColumn {

    @Autowired
    private ColumnDao columnDao;

    @Override
    public ColumnInfo getDbObject(String id) throws ServiceException {
        return columnDao.get(id);
    }

    @Override
    public String getColumnPage(int page, int rows, ColumnInfo columnInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(columnInfo)) {
            if (StringUtils.isNotEmpty(columnInfo.getName())) {
                filterList.add(new FilterBean("column.name",
                        HqlSymbol.HQL_LIKE, "%" + columnInfo.getName() + "%"));
            }
        }

        List<ColumnInfo> columnList = columnDao.getColumnPage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", columnDao.getTotal(filterList));
        result.put("rows", columnList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveColumn(ColumnInfo columnInfo) throws ServiceException {
        columnDao.save(columnInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateColumn(ColumnInfo columnInfo) throws ServiceException {
        String columnId = columnInfo.getId();
        ColumnInfo columnDb = columnDao.get(columnId);
        columnDb.setName(columnInfo.getName());
        columnDb.setSort(columnInfo.getSort());
        columnDb.setRemark(columnInfo.getRemark());
        columnDb.setUpdateDate(new Date());
        columnDao.update(columnDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteColumn(String columnId) throws ServiceException {
        columnDao.deleteById(columnId);
        return new Message("删除成功!").toString();
    }

    @Override
    public String getDetails(String columnId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        ColumnInfo columnDb = columnDao.get(columnId);

        detailsMap.put("columnInfo.id", columnDb.getId());
        detailsMap.put("columnInfo.name", columnDb.getName());
        detailsMap.put("columnInfo.sort", columnDb.getSort());
        detailsMap.put("columnInfo.remark", columnDb.getRemark());
        return JSON.toJSONString(detailsMap);
    }

}
