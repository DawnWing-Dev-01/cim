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
import com.dxr.apply.dao.ConsumerWarningDao;
import com.dxr.apply.dao.DealerDao;
import com.dxr.apply.dao.IndustryDao;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.service.api.IIndustry;

/**
 * @description: <行业信息管理服务接口实现层>
 * @author: w.xL
 * @date: 2018-2-27
 */
public class IndustryMgr extends BasalMgr<IndustryInfo> implements IIndustry {

    @Autowired
    private IndustryDao industryDao;
    @Autowired
    private ConsumerWarningDao consumerWarningDao;
    @Autowired
    private DealerDao dealerDao;

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#getIndustryPage(int, int, com.dxr.apply.entity.IndustryInfo)
     */
    @Override
    public String getIndustryPage(int page, int rows, IndustryInfo industryInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(industryInfo)) {
            if (StringUtils.isNotEmpty(industryInfo.getName())) {
                filterList
                        .add(new FilterBean("industry.name",
                                HqlSymbol.HQL_LIKE, "%"
                                        + industryInfo.getName() + "%"));
            }
        }

        List<IndustryInfo> industryList = industryDao.getIndustryPage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", industryDao.getTotal(filterList));
        result.put("rows", industryList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public List<IndustryInfo> getThresholdNotNulltIndustryList()
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        filterList.add(new FilterBean("industry.warningThreshold",
                HqlSymbol.HQL_NOT_EQUAL, 0));
        List<IndustryInfo> industryList = industryDao.getIndustryPage(0,
                Integer.MAX_VALUE, filterList);
        return industryList;
    }

    @Override
    public List<IndustryInfo> getIndustryList() throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        List<IndustryInfo> industryList = industryDao.getIndustryPage(0,
                Integer.MAX_VALUE, filterList);
        return industryList;
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#saveIndustry(com.dxr.apply.entity.IndustryInfo)
     */
    @Override
    public String saveIndustry(IndustryInfo industryInfo)
            throws ServiceException {
        // 行业重名校验
        IndustryInfo industry = getIndustryByName(industryInfo.getName());
        if (industry != null) {
            return new Message(false, ConstGlobal.MESSAGE_TYPE_WARNING,
                    "不允许重名!").toString();
        }
        industryDao.save(industryInfo);
        return new Message("保存成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#updateIndustry(com.dxr.apply.entity.IndustryInfo)
     */
    @Override
    public String updateIndustry(IndustryInfo industryInfo)
            throws ServiceException {
        String industryId = industryInfo.getId();
        IndustryInfo industryDb = industryDao.get(industryId);
        if (ObjectUtils.isEmpty(industryDb)) {
            return new Message(false, Property.error, "行业信息不存在!").toString();
        }

        // 行业重名校验
        String industryName = industryInfo.getName();
        if (!industryName.equals(industryDb.getName())) {
            IndustryInfo industry = getIndustryByName(industryName);
            if (industry != null) {
                return new Message(false, ConstGlobal.MESSAGE_TYPE_WARNING,
                        "不允许重名!").toString();
            }
        }

        // 更新数据库对象字段
        industryDb.setName(industryInfo.getName());
        industryDb.setCode(industryInfo.getCode());
        industryDb.setWarningThreshold(industryInfo.getWarningThreshold());
        industryDb.setSort(industryInfo.getSort());
        industryDb.setRemark(industryInfo.getRemark());
        industryDb.setUpdateDate(new Date());
        industryDao.update(industryDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String updateThreshold(IndustryInfo industryInfo)
            throws ServiceException {
        String industryId = industryInfo.getId();
        IndustryInfo industryDb = industryDao.get(industryId);
        industryDb.setWarningThreshold(industryInfo.getWarningThreshold());
        industryDao.update(industryDb);
        return new Message("设置成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#deleteIndustry(java.lang.String)
     */
    @Override
    public String deleteIndustry(String industryId) throws ServiceException {
        IndustryInfo industryInfo = industryDao.get(industryId);
        if (ObjectUtils.isEmpty(industryInfo)) {
            return new Message(false, Property.error, "经营者信息不存在!").toString();
        }

        // 判断该行业下是否存在经营者信息
        long dealerTotal = dealerDao.getTotal(industryId);
        if (dealerTotal > 0) {
            return new Message(false, Property.error,
                    "该行业下存在经营者信息, 请移除到其他行业再删除该行业!").toString();
        }
        // 判断该行业下是否存在消费预警信息
        long cwTotal = consumerWarningDao.getTotal(industryId);
        if (cwTotal > 0) {
            return new Message(false, Property.error,
                    "该行业下存在消费预警信息, 请先清空后再删除该行业!").toString();
        }

        industryInfo.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        industryDao.update(industryInfo);
        return new Message("删除成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#getDetails(java.lang.String)
     */
    @Override
    public String getDetails(String industryId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        IndustryInfo industryInfo = industryDao.get(industryId);
        if (ObjectUtils.isEmpty(industryInfo)) {
            return JSON.toJSONString(detailsMap);
        }

        detailsMap.put("industryInfo.id", industryInfo.getId());
        detailsMap.put("industryInfo.name", industryInfo.getName());
        detailsMap.put("industryInfo.code", industryInfo.getCode());
        detailsMap.put("industryInfo.warningThreshold",
                industryInfo.getWarningThreshold());
        detailsMap.put("industryInfo.sort", industryInfo.getSort());
        detailsMap.put("industryInfo.remark", industryInfo.getRemark());
        return JSON.toJSONString(detailsMap);
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IIndustry#getIndustryCommBox()
     */
    @Override
    public String getIndustryCommBox() throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        List<IndustryInfo> industryList = industryDao.getIndustryPage(0,
                Integer.MAX_VALUE, filterList);

        return JSON.toJSONStringWithDateFormat(industryList, "yyyy-MM-dd");
    }

    @Override
    public IndustryInfo getDbObject(String id) throws ServiceException {
        return industryDao.get(id);
    }

    @Override
    public IndustryInfo getIndustryByName(String name) throws ServiceException {
        return industryDao.getIndustryByName(name);
    }
}
