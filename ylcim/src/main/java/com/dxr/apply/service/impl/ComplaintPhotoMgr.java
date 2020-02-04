package com.dxr.apply.service.impl;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.FileUtils;
import com.dxr.apply.dao.ComplaintPhotoDao;
import com.dxr.apply.entity.ComplaintPhotoInfo;
import com.dxr.apply.service.api.IComplaintPhoto;
import com.dxr.comm.utils.SimpleHttpClient;
import com.dxr.system.entity.AttachInfo;
import com.dxr.system.service.api.IAttach;
import com.dxr.webui.wechat.service.api.IWeChat;

/**
 * @description: <投诉照片服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-8
 */
public class ComplaintPhotoMgr extends BasalMgr<ComplaintPhotoInfo> implements
        IComplaintPhoto {

    @Autowired
    private ComplaintPhotoDao complaintPhotoDao;

    @Autowired
    private IWeChat weChatMgr;

    @Autowired
    private IAttach attachMgr;

    private String downloadMediaUrl;
    private String uploadRootPath;

    @Override
    public ComplaintPhotoInfo getDbObject(String id) throws ServiceException {
        return complaintPhotoDao.get(id);
    }

    @Override
    public List<ComplaintPhotoInfo> getComplaintPhotoList(String complaintId)
            throws ServiceException {
        return complaintPhotoDao.getComplaintPhotoList(complaintId);
    }

    @Override
    public String saveComplaintPhoto(String complaintId, String imageServerIds)
            throws ServiceException {
        String token = weChatMgr.getCacheToken();
        // 保存的根目录
        String targetDir = uploadRootPath + "wechat_official_account" + "\\"
                + complaintId;
        ComplaintPhotoInfo complaintPhoto = null;
        String[] imageServerIdArray = imageServerIds.split(";");
        int index = 1;
        for (String imageServerId : imageServerIdArray) {
            complaintPhoto = new ComplaintPhotoInfo();
            // 请求微信后台接口, 下载用户上传的相片
            Map<String, Object> param = new LinkedHashMap<String, Object>();
            param.put("access_token", token);
            param.put("media_id", imageServerId);
            HttpEntity httpEntity = SimpleHttpClient.getHttpEntity(
                    downloadMediaUrl, null, param);
            InputStream is = null;
            try {
                is = httpEntity.getContent();
                // 生成的uuid全称文件名
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String fileFullName = uuid + ".jpg";
                String imgPath = FileUtils.uploadFile(is, fileFullName,
                        targetDir);

                // 保存附件记录
                String attachPath = imgPath;
                AttachInfo attachInfo = new AttachInfo();
                attachInfo.setAttachType(ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE);
                attachInfo.setAttachName(fileFullName);
                attachInfo.setAttachSize(FileUtils.getFileSize(attachPath));
                attachInfo.setFileType("image/jpeg");
                attachInfo.setAttachPath(attachPath);
                attachMgr.saveAttach(attachInfo);

                // 数据库保存投诉/举报照片
                complaintPhoto.setComplaintId(complaintId);
                complaintPhoto.setImgPath(attachInfo.getId());
                complaintPhoto.setSort(index);
                complaintPhotoDao.save(complaintPhoto);
            } catch (Exception e) {
                throw new ServiceException(
                        "HttpEntity.getContent() is error...", e);
            } finally {
                index++;
            }
        }
        return new Message("保存成功!").toString();
    }

    public String getDownloadMediaUrl() {
        return downloadMediaUrl;
    }

    public void setDownloadMediaUrl(String downloadMediaUrl) {
        this.downloadMediaUrl = downloadMediaUrl;
    }

    public String getUploadRootPath() {
        return uploadRootPath;
    }

    public void setUploadRootPath(String uploadRootPath) {
        this.uploadRootPath = uploadRootPath;
    }

}
