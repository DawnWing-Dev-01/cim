package com.dxr.apply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.apply.dao.ComplaintResultDao;
import com.dxr.apply.service.api.IOriginType;
import com.dxr.system.entity.SystemConstant;
import com.dxr.system.service.api.ISystemConstant;

/**
 * @description: <投诉结果来源类型服务接口实现层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class OriginTypeMgr extends BasalMgr<Object> implements IOriginType {

    @Autowired
    private ISystemConstant systemConstantMgr;
    @Autowired
    private ComplaintResultDao complaintResultDao;

    /**
     * 投诉来源在数据字典的group
     */
    private String originTypeScGroup;

    @Override
    public String getOriginTypePage(int page, int rows, SystemConstant constant)
            throws ServiceException {
        if (constant == null) {
            constant = new SystemConstant();
        }
        // 过滤投诉来源数据
        constant.setScGroup(originTypeScGroup);
        return systemConstantMgr.getSystemConstantPage(page, rows, constant);
    }

    @Override
    public String saveOriginType(SystemConstant constant)
            throws ServiceException {
        constant.setScGroup(originTypeScGroup);
        return systemConstantMgr.saveSystemConstant(constant);
    }

    @Override
    public String updateOriginType(SystemConstant constant)
            throws ServiceException {
        constant.setScGroup(originTypeScGroup);
        return systemConstantMgr.updateSystemConstant(constant);
    }

    @Override
    public String deleteOriginType(String constantId) throws ServiceException {
        // 判断是否有引用
        long total = complaintResultDao.findTotalByOrigin(constantId);
        if (total > 0) {
            throw new ServiceException("该来源类型下有绑定投诉结果，请移动至其他来源下再删除!",
                    ErrorCode.ERRCODE_DATA_REPEAT);
        }

        // 无引用直接删除
        return systemConstantMgr.deleteSystemConstant(constantId);
    }

    @Override
    public String getDetails(String constantId) throws ServiceException {
        return systemConstantMgr.getDetails(constantId);
    }

    @Override
    public String getOriginTypeCommbox() throws ServiceException {
        return systemConstantMgr.getSystemConstantCommbox(originTypeScGroup);
    }

    @Override
    public Object getDbObject(String id) throws ServiceException {
        return null;
    }

    public String getOriginTypeScGroup() {
        return originTypeScGroup;
    }

    public void setOriginTypeScGroup(String originTypeScGroup) {
        this.originTypeScGroup = originTypeScGroup;
    }

}
