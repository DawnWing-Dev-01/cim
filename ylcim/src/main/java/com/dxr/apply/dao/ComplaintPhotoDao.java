package com.dxr.apply.dao;

import java.util.List;

import org.hibernate.Query;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.apply.entity.ComplaintPhotoInfo;

/**
 * @description: <投诉/举报照片数据操作层Dao>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintPhotoDao extends BasalDao<ComplaintPhotoInfo> {

    /**
     * 获取投诉照片
     * @param complaintId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ComplaintPhotoInfo> getComplaintPhotoList(String complaintId) {
        String hql = "select photo from ComplaintPhotoInfo photo where 1 = 1 "
                + "and photo.complaintId = :complaintId ";
        hql = super.orderBy(hql, "photo.sort");
        Query query = super.createQuery(hql);
        query.setParameter("complaintId", complaintId);
        return query.list();
    }
}
