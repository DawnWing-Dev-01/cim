package com.dxr.system.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.SysSeqMax;

/**
 * @description: <流水序列服务接口层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public interface ISysSeqMax extends IBasalMgr<SysSeqMax> {

    /**
     * 生成消费者投诉登记编号流水号
     * @return
     * @throws ServiceException
     */
    public String generateComplaintCode() throws ServiceException;
}