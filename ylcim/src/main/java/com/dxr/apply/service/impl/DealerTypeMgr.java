package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.DealerTypeDao;
import com.dxr.apply.entity.DealerTypeInfo;
import com.dxr.apply.service.api.IDealerType;

/**
 * @description: <经营者类型管理服务接口实现层>
 * @author: w.xL
 * @date: 2018-2-27
 */
public class DealerTypeMgr extends BasalMgr<DealerTypeInfo> implements
        IDealerType {

    @Autowired
    private DealerTypeDao dealerTypeDao;

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealerType#getTypePage(int, int, com.dxr.apply.entity.DealerTypeInfo)
     */
    @Override
    public String getTypePage(int page, int rows, DealerTypeInfo dealerTypeInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(dealerTypeInfo)) {
            if (StringUtils.isNotEmpty(dealerTypeInfo.getName())) {
                filterList.add(new FilterBean("type.name", HqlSymbol.HQL_LIKE,
                        "%" + dealerTypeInfo.getName() + "%"));
            }
        }

        List<DealerTypeInfo> dealerTypeList = dealerTypeDao.getTypePage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", dealerTypeDao.getTotal(filterList));
        result.put("rows", dealerTypeList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealerType#saveType(com.dxr.apply.entity.DealerTypeInfo)
     */
    @Override
    public String saveType(DealerTypeInfo dealerTypeInfo)
            throws ServiceException {
        dealerTypeDao.save(dealerTypeInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String updateType(DealerTypeInfo dealerTypeInfo)
            throws ServiceException {
        String dealerTypeId = dealerTypeInfo.getId();
        DealerTypeInfo dealerTypeDb = dealerTypeDao.get(dealerTypeId);
        if (ObjectUtils.isEmpty(dealerTypeDb)) {
            return new Message(false, Property.error, "经营者类型不存在!").toString();
        }
        // 更新数据库对象字段
        dealerTypeDb.setName(dealerTypeInfo.getName());
        dealerTypeDb.setTypeCode(dealerTypeInfo.getTypeCode());
        dealerTypeDb.setSort(dealerTypeInfo.getSort());
        dealerTypeDb.setRemark(dealerTypeInfo.getRemark());
        dealerTypeDb.setUpdateDate(new Date());
        dealerTypeDao.update(dealerTypeDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteType(String dealerTypeId) throws ServiceException {
        DealerTypeInfo dealerTypeInfo = dealerTypeDao.get(dealerTypeId);
        if (ObjectUtils.isEmpty(dealerTypeInfo)) {
            return new Message(false, Property.error, "经营者类型不存在!").toString();
        }
        dealerTypeInfo.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        dealerTypeDao.update(dealerTypeInfo);
        return new Message("删除成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#getDetails(java.lang.String)
     */
    @Override
    public String getDetails(String dealerTypeId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        DealerTypeInfo dealerTypeInfo = dealerTypeDao.get(dealerTypeId);
        if (ObjectUtils.isEmpty(dealerTypeInfo)) {
            return JSON.toJSONString(detailsMap);
        }

        detailsMap.put("dealerTypeInfo.id", dealerTypeInfo.getId());
        detailsMap.put("dealerTypeInfo.name", dealerTypeInfo.getName());
        detailsMap.put("dealerTypeInfo.typeCode", dealerTypeInfo.getTypeCode());
        detailsMap.put("dealerTypeInfo.sort", dealerTypeInfo.getSort());
        detailsMap.put("dealerTypeInfo.remark", dealerTypeInfo.getRemark());
        return JSON.toJSONString(detailsMap);
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealerType#getTypeCommBox()
     */
    @Override
    public String getTypeCommBox() throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        List<DealerTypeInfo> dealerTypeList = dealerTypeDao.getTypePage(0,
                Integer.MAX_VALUE, filterList);

        return JSON.toJSONStringWithDateFormat(dealerTypeList, "yyyy-MM-dd");
    }

    @Override
    public DealerTypeInfo getDbObject(String id) throws ServiceException {
        return dealerTypeDao.get(id);
    }
}
