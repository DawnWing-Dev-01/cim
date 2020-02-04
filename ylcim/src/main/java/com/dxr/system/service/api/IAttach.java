package com.dxr.system.service.api;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.AttachInfo;

/**
 * @description: <附件业务服务接口层>
 * @author: w.xL
 * @date: 2018-3-23
 */
public interface IAttach extends IBasalMgr<AttachInfo> {

    /**
     * @param page
     * @param rows
     * @param attach
     * @return
     * @throws Exception
     * @remark 获取附件列表接口
     */
    public String getAttachPage(int page, int rows, AttachInfo attachInfo)
            throws Exception;

    /**
     * 保存附件
     * @param attachInfo
     * @return 附件Id
     * @throws ServiceException
     */
    public String saveAttach(AttachInfo attachInfo) throws ServiceException;

    /**
     * @remark 上传一个附件
     * @param file
     * @param fileFileName
     * @param fileContentType
     * @param attachType
     * @return
     * @throws Exception
     */
    public String uploadAttach(File file, String fileFileName,
            String fileContentType, String attachType) throws Exception;

    /**
     * 构建上传文件错误信息
     * @param message
     * @return
     */
    public String builderUploadError(String message);

    /**
     * @Title: downloadAttach
     * @Description 附件下载
     * @return String  
     * @throws
     */
    public void downloadAttach(String attachId, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    /**
     * 根据路径下载
     * @param downloadFile
     * @param downloadName
     * @param request
     * @param response
     * @throws Exception
     */
    public void downloadByPath(String downloadFile, String downloadName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    /**
     * @Title: delAttach
     * @Description 删除附件的方法
     * @return void  
     * @throws
     */
    public String deleteAttach(String attachId) throws ServiceException;

    /**
     * @param businId
     * @return
     * @throws Exception
     * @remark 根据业务主键获取附件列表
     */
    public String getAttachList(String id) throws Exception;

    /**
     * 根据attachId获取Attach的JSON字符串
     * @param attachId
     * @return
     * @throws Exception
     */
    public String getAttachByAttachIdStr(String attachId) throws Exception;

    /**
     * @Title: getShouImage
     * @Description 读取图片的方法
     * @return String  
     * @throws
     */
    public void showImage(String attachId, HttpServletResponse response)
            throws Exception;

    /**
     * 文件空间
     * @param attachType
     * @param order
     * @return
     * @throws Exception
     */
    public String showAttachList(String attachType, String order)
            throws Exception;
}
