package com.dxr.apply.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <投诉/举报照片实体类>
 * @author: w.xL
 * @date: 2018-3-14
 */
public class ComplaintPhotoInfo extends AbstractEntity {

    private static final long serialVersionUID = -4670655270534894257L;

    private String complaintId;
    
    private String imgPath;

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
