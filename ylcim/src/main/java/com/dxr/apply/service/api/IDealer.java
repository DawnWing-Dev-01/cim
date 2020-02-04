package com.dxr.apply.service.api;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.DealerInfo;

/**
 * @description: <经营者信息管理服务接口层IService>
 * @author: w.xL
 * @date: 2018-2-26
 */
public interface IDealer extends IBasalMgr<DealerInfo> {

    /**
     * @param page
     * @param rows
     * @param dealerInfo
     * @return
     * @throws Exception
     */
    public String getDealerPage(int page, int rows, DealerInfo dealerInfo)
            throws ServiceException;

    /**
     * @param dealerInfo
     * @return
     * @throws ServiceException
     */
    public String saveDealer(DealerInfo dealerInfo) throws ServiceException;

    /**
     * @param dealerInfo
     * @return
     * @throws ServiceException
     */
    public String updateDealer(DealerInfo dealerInfo) throws ServiceException;

    /**
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public String deleteDealer(String dealerId) throws ServiceException;

    /**
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String dealerId) throws ServiceException;

    /**
     * 生成经营者二维码
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public String generateDealerQrCode(String dealerId) throws ServiceException;

    /**
     * 生成经营者二维码, 带背景
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public String createDealerQrCodeWithBgi(String dealerId) throws ServiceException;
    
    /**
     * 批量生成经营者二维码
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public String generateBatchDealerQrCode(String[] dealerIds)
            throws ServiceException;

    /**
     * 生成临时zip文件, 下载完成后会删除
     * @param dealerIds
     * @return
     * @throws ServiceException
     */
    public String addTempZipFile(String[] dealerIds) throws ServiceException;
    
    /**
     * 批量下载二维码
     * @param attachId
     * @return
     * @throws ServiceException
     */
    public void downloadBatch(String attachId, HttpServletRequest request,
            HttpServletResponse response) throws ServiceException;

    /**
     * 经营者信息Excel导入
     * @param file
     * @return
     * @throws ServiceException
     */
    public String addDealerForImport(File file, String fileName) throws ServiceException;
    
    /**
     * 查询经营者信用相关信息
     * @param dealerId
     * @return
     * @throws ServiceException
     */
    public Map<String, Object> queryDealerCredit(String dealerId)
            throws ServiceException;

    /**
     * 模糊查找经营者信息列表
     * @param keywords 用户输入的值
     * @return 经营者列表json字符串
     * @throws ServiceException
     */
    public String fuzzyQueryDealerList(String keywords) throws ServiceException;
    
    /**
     * @Description 读取图片的方法
     * @return String  
     * @throws
     */
    public void showImage(String dealerId, HttpServletResponse response)
            throws Exception;
}
