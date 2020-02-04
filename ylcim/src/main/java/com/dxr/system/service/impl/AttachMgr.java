package com.dxr.system.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.FileUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.dao.AttachDao;
import com.dxr.system.entity.AttachInfo;
import com.dxr.system.service.api.IAttach;

/**
 * @description: <附件业务服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-23
 */
public class AttachMgr extends BasalMgr<AttachInfo> implements IAttach {

    @Autowired
    private AttachDao attachDao;

    @Autowired
    private SystemConstantCache scCache;

    private String uploadAttachDir;

    private String uploadImageAllowSuffix;
    private String uploadFlashAllowSuffix;
    private String uploadMediaAllowSuffix;
    private String uploadFilesAllowSuffix;
    private String uploadOtherAllowSuffix;

    // 上传文件允许的后缀名
    private HashMap<String, String> allowSuffixMap = new HashMap<String, String>();

    /**
     * 获取附件列表接口实现
     */
    public String getAttachPage(int page, int rows, AttachInfo attachInfo)
            throws Exception {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (attachInfo != null) {
            if (attachInfo.getAttachName() != null
                    && !"".equals(attachInfo.getAttachName().trim())) {
                filterList.add(new FilterBean("attach.attachName",
                        HqlSymbol.HQL_LIKE, "%"
                                + attachInfo.getAttachName().trim() + "%"));
            }
        }
        List<AttachInfo> attachList = attachDao.getAttachPage(
                (page - 1) * rows, rows, filterList);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", attachDao.getAttachTotal(filterList));
        result.put("rows", attachList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveAttach(AttachInfo attachInfo) throws ServiceException {
        attachDao.save(attachInfo);
        return attachInfo.getId();
    }

    /**
     * 保存附件
     * @param file
     * @param fileFileName
     * @param fileContentType
     * @param attachType
     * @return
     * @throws Exception
     */
    public String uploadAttach(File file, String fileFileName,
            String fileContentType, String attachType) throws Exception {
        // No1: 判断文件格式检查扩展名
        String allowSuffixStr = allowSuffixMap.get(attachType);
        String fileExt = fileFileName.substring(
                fileFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.<String> asList(allowSuffixStr.split(","))
                .contains(fileExt)) {
            return builderUploadError("上传文件格式非法。\n 允许的格式：" + allowSuffixStr);
        }

        // No2: 构建目标地址
        String dirPath = builderTargetPath(attachType);

        // No3: 将上传的文件拷贝到目标地址上
        String uploadFilePath = FileUtils.uploadFile(file, fileFileName,
                fileContentType, dirPath);
        // No4: 保存附件记录, uploadFilePath不等于null则证明上传成功
        AttachInfo attach = null;
        if (StringUtils.isNotEmpty(uploadFilePath)) {
            attach = new AttachInfo();
            attach.setAttachType(attachType);
            attach.setAttachName(fileFileName);
            attach.setAttachSize(FileUtils.getFileSize(uploadFilePath));
            attach.setFileType(fileContentType);
            attach.setAttachPath(uploadFilePath);
            attach.setCreatorId(SecurityUser.getLoginUser().getUserId());
            attachDao.save(attach);
        }
        return builderUpload(attachType, attach.getId());
    }

    private String builderTargetPath(String attachType) {
        StringBuilder dirPath = new StringBuilder(uploadAttachDir);
        switch (attachType) {
        case ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE:
            dirPath.append(ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE);
            break;
        case ConstGlobal.UPLOAD_ATTACH_TYPE_FLASH:
            dirPath.append(ConstGlobal.UPLOAD_ATTACH_TYPE_FLASH);
            break;
        case ConstGlobal.UPLOAD_ATTACH_TYPE_MEDIA:
            dirPath.append(ConstGlobal.UPLOAD_ATTACH_TYPE_MEDIA);
            break;
        case ConstGlobal.UPLOAD_ATTACH_TYPE_FILES:
            dirPath.append(ConstGlobal.UPLOAD_ATTACH_TYPE_FILES);
            break;
        default:
            dirPath.append(ConstGlobal.UPLOAD_ATTACH_TYPE_OTHER);
            break;
        }
        Calendar cal = Calendar.getInstance();
        dirPath.append("\\" + cal.get(Calendar.YEAR));
        dirPath.append("\\" + (cal.get(Calendar.MONTH) + 1));
        dirPath.append("\\");
        return dirPath.toString();
    }

    private String builderUpload(String attachType, String attachId) {
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        String absoluteWebContext = String.valueOf(scCache
                .get("AbsoluteWebContext"));
        String showUrl = absoluteWebContext
                + "wechat/attachAction!showImage?object=" + attachId;
        if (!attachType.equals(ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE)) {
            showUrl = absoluteWebContext
                    + "wechat/attachAction!downloadAttach?object=" + attachId;
        }
        obj.put("url", showUrl);
        return obj.toJSONString();
    }

    public String builderUploadError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }

    /**
     * 附件下载
     */
    public void downloadAttach(String attachId, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        AttachInfo attach = attachDao.get(attachId);
        String downloadName = attach.getAttachName(); // 文件名
        String downloadFile = attach.getAttachPath();// 这里是存放图片的文件夹地址
        FileUtils.downloadFile(downloadFile, downloadName, request, response);
    }

    /**
     * 附件删除
     */
    public String deleteAttach(String attachId) throws ServiceException {
        AttachInfo attach = attachDao.get(attachId);
        FileUtils.delFile(attach.getAttachPath());
        attachDao.delete(attach);
        return new Message("删除成功").toString();
    }

    /**
     * 根据attachId获取Attach的JSON字符串
     */
    public String getAttachByAttachIdStr(String attachId) throws Exception {
        AttachInfo attach = attachDao.get(attachId);
        return JSONArray.toJSONStringWithDateFormat(attach, Property.yyyyMMdd);
    }

    /**
     * 显示图片的方法
     */
    public void showImage(String attachId, HttpServletResponse response)
            throws Exception {
        if (StringUtils.isNotEmpty(attachId)) {
            AttachInfo attach = attachDao.get(attachId);
            String imgName = attach.getAttachName(); // 文件名
            String imgpath = attach.getAttachPath();// 这里是存放图片的文件夹地址
            FileUtils.showImage(imgpath, imgName, response);
        }
    }

    @Override
    public AttachInfo getDbObject(String id) throws ServiceException {
        return attachDao.get(id);
    }

    @Override
    public String getAttachList(String id) throws Exception {
        return null;
    }

    @Override
    public void downloadByPath(String downloadFile, String downloadName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FileUtils.downloadFile(downloadFile, downloadName, request, response);
    }

    @Override
    public String showAttachList(String attachType, String order)
            throws Exception {
        //图片扩展名
        String[] fileTypes = allowSuffixMap.get("image").split(",");

        if (attachType != null) {
            if (!Arrays.<String> asList(
                    new String[] { "image", "flash", "media", "file" })
                    .contains(attachType)) {
                return "Invalid Directory name.";
            }
        }

        //遍历目录取的文件信息
        List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        filterList.add(new FilterBean("attach.attachType", HqlSymbol.HQL_EQUAL,
                attachType.trim()));
        // 排序处理
        if (order.equals("NAME")) {
            order = "attach.attachName";
        } else if (order.equals("SIZE")) {
            order = "attach.attachSize";
        } else if (order.equals("TYPE")) {
            order = "attach.fileType";
        }
        List<AttachInfo> attachList = attachDao.getAttachPage4Order(0,
                Integer.MAX_VALUE, filterList, order);
        long total = attachDao.getAttachTotal(filterList);
        for (AttachInfo attachInfo : attachList) {
            String fileName = attachInfo.getAttachName();
            Hashtable<String, Object> hash = new Hashtable<String, Object>();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
                    .toLowerCase();
            hash.put("is_dir", false);
            hash.put("has_file", false);
            hash.put("filesize", attachInfo.getAttachSize());
            boolean isPhoto = Arrays.<String> asList(fileTypes).contains(
                    fileExt);
            hash.put("is_photo", isPhoto);
            hash.put("filetype", fileExt);
            hash.put("filename", fileName);
            hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(attachInfo.getCreateDate()));
            String showsrc = (isPhoto ? "wechat/attachAction!showImage?object="
                    : "wechat/attachAction!downloadAttach?object=")
                    + attachInfo.getId();
            hash.put("filesrc", showsrc);
            fileList.add(hash);
        }

        JSONObject result = new JSONObject();
        result.put("current_url", "./");
        result.put("total_count", total);
        result.put("file_list", fileList);
        return result.toJSONString();
    }

    /**
     * 定义允许上传的文件扩展名
     */
    private void setAllowSuffixMap() {
        if (StringUtils.isNotEmpty(uploadImageAllowSuffix)) {
            allowSuffixMap.put(ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE,
                    uploadImageAllowSuffix);
        }
        if (StringUtils.isNotEmpty(uploadFlashAllowSuffix)) {
            allowSuffixMap.put(ConstGlobal.UPLOAD_ATTACH_TYPE_FLASH,
                    uploadFlashAllowSuffix);
        }
        if (StringUtils.isNotEmpty(uploadMediaAllowSuffix)) {
            allowSuffixMap.put(ConstGlobal.UPLOAD_ATTACH_TYPE_MEDIA,
                    uploadMediaAllowSuffix);
        }
        if (StringUtils.isNotEmpty(uploadFilesAllowSuffix)) {
            allowSuffixMap.put(ConstGlobal.UPLOAD_ATTACH_TYPE_FILES,
                    uploadFilesAllowSuffix);
        }
        if (StringUtils.isNotEmpty(uploadOtherAllowSuffix)) {
            allowSuffixMap.put(ConstGlobal.UPLOAD_ATTACH_TYPE_OTHER,
                    uploadOtherAllowSuffix);
        }
    }

    public String getUploadAttachDir() {
        return uploadAttachDir;
    }

    public void setUploadAttachDir(String uploadAttachDir) {
        this.uploadAttachDir = uploadAttachDir;
    }

    public String getUploadImageAllowSuffix() {
        return uploadImageAllowSuffix;
    }

    public void setUploadImageAllowSuffix(String uploadImageAllowSuffix) {
        this.uploadImageAllowSuffix = uploadImageAllowSuffix;
        setAllowSuffixMap();
    }

    public String getUploadFlashAllowSuffix() {
        return uploadFlashAllowSuffix;
    }

    public void setUploadFlashAllowSuffix(String uploadFlashAllowSuffix) {
        this.uploadFlashAllowSuffix = uploadFlashAllowSuffix;
        setAllowSuffixMap();
    }

    public String getUploadMediaAllowSuffix() {
        return uploadMediaAllowSuffix;
    }

    public void setUploadMediaAllowSuffix(String uploadMediaAllowSuffix) {
        this.uploadMediaAllowSuffix = uploadMediaAllowSuffix;
        setAllowSuffixMap();
    }

    public String getUploadFilesAllowSuffix() {
        return uploadFilesAllowSuffix;
    }

    public void setUploadFilesAllowSuffix(String uploadFilesAllowSuffix) {
        this.uploadFilesAllowSuffix = uploadFilesAllowSuffix;
        setAllowSuffixMap();
    }

    public String getUploadOtherAllowSuffix() {
        return uploadOtherAllowSuffix;
    }

    public void setUploadOtherAllowSuffix(String uploadOtherAllowSuffix) {
        this.uploadOtherAllowSuffix = uploadOtherAllowSuffix;
        setAllowSuffixMap();
    }
}
