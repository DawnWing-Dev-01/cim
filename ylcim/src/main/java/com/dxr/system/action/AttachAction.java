package com.dxr.system.action;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.AttachInfo;
import com.dxr.system.service.api.IAttach;

/**
 * @description: <附件Action层>
 * @author: w.xL
 * @date: 2018-3-23
 */
public class AttachAction extends BasalAction {

    private static final Logger logger = LoggerFactory
            .getLogger(AttachAction.class);
    private static final long serialVersionUID = 2447386419776857376L;

    @Autowired
    private IAttach attachMgr;

    private AttachInfo attachInfo;

    private String attachType;

    private String downloadFile;
    private String downloadName;

    // KindEditor上传文件
    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;
    private String dir;
    private String order;

    /**
     * 获取附件列表
     */
    public void getAttachPage() {
        try {
            String result = attachMgr.getAttachPage(page, rows, attachInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("AttachAction.getAttachPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 上传附件
     */
    public void uploadAttach() {
        try {
            String result = attachMgr.uploadAttach(file, fileFileName,
                    fileContentType, attachType);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("AttachAction.uploadAttach() is error...", e);
            super.empty();
        }
    }

    /**
     * 富文本编辑器KindEditor上传附件
     */
    public void uploadAttach4Editor() {
        try {
            String result = attachMgr.uploadAttach(imgFile, imgFileFileName,
                    imgFileContentType, dir);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("AttachAction.uploadAttach() is error...", e);
            super.writeToView(attachMgr.builderUploadError("上传附件失败!"));
        }
    }

    /**
     * 文件空间
     */
    public void showAttachList() {
        try {
            String result = attachMgr.showAttachList(dir, order);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("AttachAction.showAttachList() is error...", e);
            super.brace();
        }
    }

    /**
     * 附件下载
     */
    public void downloadAttach() {
        try {
            attachMgr.downloadAttach(object, super.getRequest(),
                    super.getResponse());
        } catch (Exception e) {
            logger.error("AttachAction.downloadAttach() is error...", e);
            super.empty();
        }
    }

    /**
     * 根据路径下载文件
     */
    public void downloadByPath() {
        try {
            attachMgr.downloadByPath(downloadFile, downloadName,
                    super.getRequest(), super.getResponse());
        } catch (Exception e) {
            logger.error("AttachAction.downloadByPath() is error...", e);
            super.empty();
        }
    }

    /**
     * @Title: delAttach
     * @Description 删除附件
     * @return void  
     * @throws
     */
    public void deleteAttach() {
        try {
            String result = attachMgr.deleteAttach(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("AttachAction.delAttach() is error...", e);
            super.empty();
        }
    }

    /**
     * 获取附件列表
     */
    public void getAttachList() {
        try {
            String result = attachMgr.getAttachList(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getAttachList", e);
            super.bracket();
        }
    }

    /**
     * 获取附件
     */
    public void getAttachByAttachIdStr() {
        try {
            String result = attachMgr.getAttachByAttachIdStr(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getAttachByAttachIdStr", e);
            super.bracket();
        }
    }

    /**
     * 显示图片
     */
    public void showImage() {
        try {
            attachMgr.showImage(object, super.getResponse());
        } catch (Exception e) {
            logger.error("showImage", e);
            super.empty();
        }
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public AttachInfo getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(AttachInfo attachInfo) {
        this.attachInfo = attachInfo;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public File getImgFile() {
        return imgFile;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public String getImgFileFileName() {
        return imgFileFileName;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public String getImgFileContentType() {
        return imgFileContentType;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
