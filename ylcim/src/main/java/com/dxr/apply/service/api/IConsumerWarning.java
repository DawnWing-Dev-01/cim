package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.ConsumerWarningInfo;

/**
 * @description: <消费预警服务接口层>
 * @author: w.xL
 * @date: 2018-3-30
 */
public interface IConsumerWarning extends IBasalMgr<ConsumerWarningInfo> {

    /**
     * 获取消费预警列表Page, EasyUI格式
     * @param page
     * @param rows
     * @param consumerWarningInfo
     * @param dataType 数据类型：历史数据、正在展示（将来展示）
     * @return
     * @throws ServiceException
     */
    public String getConsumerWarningPage(int page, int rows,
            ConsumerWarningInfo consumerWarningInfo, String dataType)
            throws ServiceException;

    /**
     * 根据行业Id获取消费预警
     * @param industryId
     * @return
     * @throws ServiceException
     */
    public List<ConsumerWarningInfo> findIndustryConsumerWarning(
            String industryId) throws ServiceException;

    /**
     * 保存消费预警
     * @param consumerWarningInfo
     * @return
     * @throws ServiceException
     */
    public String saveConsumerWarning(ConsumerWarningInfo consumerWarningInfo)
            throws ServiceException;

    /**
     * 更新消费预警
     * @param consumerWarningInfo
     * @return
     * @throws ServiceException
     */
    public String updateConsumerWarning(ConsumerWarningInfo consumerWarningInfo)
            throws ServiceException;

    /**
     * 删除消费预警
     * @param warningId
     * @return
     * @throws ServiceException
     */
    public String deleteConsumerWarning(String warningId)
            throws ServiceException;

    /**
     * 详情展示
     * @param warningId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String warningId) throws ServiceException;

    /**
     * 预警发布提醒, 查询上个月有效预警和预警阀值比较
     * @return
     * @throws ServiceException
     */
    public String updateWarningPublishRemind() throws ServiceException;
}
