package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.ComplaintPhotoInfo;

/**
 * @description: <投诉照片服务接口层>
 * @author: w.xL
 * @date: 2018-4-8
 */
public interface IComplaintPhoto extends IBasalMgr<ComplaintPhotoInfo> {

    /**
     * 获取投诉照片列表
     * @param complaintId
     * @return
     * @throws ServiceException
     */
    public List<ComplaintPhotoInfo> getComplaintPhotoList(String complaintId)
            throws ServiceException;

    /**
     * 保存投诉照片
     * @param complaintId
     * @param imageServerIds
     * @return
     * @throws ServiceException
     */
    public String saveComplaintPhoto(String complaintId, String imageServerIds)
            throws ServiceException;
}
