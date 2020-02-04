package com.dxr.apply.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
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
import com.dawnwing.framework.qrcode.QRCodeFactory;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ExcelUtils;
import com.dawnwing.framework.utils.FileUtils;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dawnwing.framework.utils.ZipUtils;
import com.dxr.apply.dao.DealerDao;
import com.dxr.apply.entity.ComplaintResultInfo;
import com.dxr.apply.entity.ConsumerWarningInfo;
import com.dxr.apply.entity.DealerCreditVo;
import com.dxr.apply.entity.DealerInfo;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.service.api.IComplaintResult;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IConsumerWarning;
import com.dxr.apply.service.api.IDealer;
import com.dxr.apply.service.api.IDealerType;
import com.dxr.apply.service.api.IIndustry;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.utils.DealerQrCodeRunnable;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.comm.utils.SimpleHttpClient;
import com.dxr.system.entity.AttachInfo;
import com.dxr.system.entity.Organization;
import com.dxr.system.service.api.IAttach;
import com.dxr.system.service.api.IOrg;
import com.google.zxing.WriterException;

/**
 * @description: <经营者信息管理服务接口实现层>
 * @author: w.xL
 * @date: 2018-2-26
 */
public class DealerMgr extends BasalMgr<DealerInfo> implements IDealer {

    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private IIndustry industryMgr;
    @Autowired
    private IDealerType dealerTypeMgr;
    @Autowired
    private QRCodeFactory qrCodeFactory;
    @Autowired
    private IAttach attachMgr;
    @Autowired
    private SystemConstantCache scCache;
    @Autowired
    private IOrg orgMgr;
    @Autowired
    private IComplaintSheet complaintSheetMgr;
    @Autowired
    private IConsumerWarning consumerWarningMgr;
    @Autowired
    private IComplaintResult complaintResultMgr;

    private String uploadRootPath;
    private String wechatAppId;
    private String authorizeCodeApiUrl;
    private String uploadTempPath;
    private Integer a4width;
    private Integer a4height;

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealer#getDealerPage(int, int, com.dxr.apply.entity.DealerInfo)
     */
    @Override
    public String getDealerPage(int page, int rows, DealerInfo dealerInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (ObjectUtils.isNotEmpty(dealerInfo)) {
            if (StringUtils.isNotEmpty(dealerInfo.getIndustryId())) {
                filterList.add(new FilterBean("dealer.industryId",
                        HqlSymbol.HQL_EQUAL, dealerInfo.getIndustryId()));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getName())) {
                filterList.add(new FilterBean("dealer.name",
                        HqlSymbol.HQL_LIKE, "%" + dealerInfo.getName() + "%"));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getSimpleName())) {
                filterList.add(new FilterBean("dealer.simpleName",
                        HqlSymbol.HQL_LIKE, "%" + dealerInfo.getSimpleName()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getLegalPerson())) {
                filterList.add(new FilterBean("dealer.legalPerson",
                        HqlSymbol.HQL_EQUAL, dealerInfo.getLegalPerson()));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getLicenseNo())) {
                filterList.add(new FilterBean("dealer.licenseNo",
                        HqlSymbol.HQL_EQUAL, dealerInfo.getLicenseNo()));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getDealerAddress())) {
                filterList.add(new FilterBean("dealer.dealerAddress",
                        HqlSymbol.HQL_LIKE, "%" + dealerInfo.getDealerAddress()
                                + "%"));
            }

            if (StringUtils.isNotEmpty(dealerInfo.getQrcode())) {
                // dealerInfo.getQrcode()只能是null / not null
                String factor = "not null".equals(dealerInfo.getQrcode()) ? HqlSymbol.HQL_NOT_EQUAL
                        : HqlSymbol.HQL_EQUAL;
                filterList.add(new FilterBean("dealer.qrcode", factor, ""));
            }

            if (dealerInfo.getStartDate() != null) {
                filterList.add(new FilterBean("dealer.createDate",
                        HqlSymbol.HQL_GREATER_OR_EQUAL, dealerInfo
                                .getStartDate(), false));
            }
            if (dealerInfo.getEndDate() != null) {
                filterList.add(new FilterBean("dealer.createDate",
                        HqlSymbol.HQL_LESS_OR_EQUAL, dealerInfo.getEndDate(),
                        false));
            }
        }

        List<DealerInfo> dealerList = dealerDao.getDealerPage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", dealerDao.getTotal(filterList));
        result.put("rows", dealerList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealer#saveDealer(com.dxr.apply.entity.DealerInfo)
     */
    @Override
    public String saveDealer(DealerInfo dealerInfo) throws ServiceException {
        // 若经营地址不填, 则默认和注册地址一致
        if (StringUtils.isEmpty(dealerInfo.getDealerAddress())) {
            dealerInfo.setDealerAddress(dealerInfo.getRegisterAddress());
        }

        DealerInfo dealerCheck = dealerDao.getDealerByLicenseNo(dealerInfo
                .getLicenseNo());
        if (dealerCheck != null) {
            return new Message(false, ConstGlobal.MESSAGE_TYPE_WARNING,
                    "统一社会信用代码不允许相同!").toString();
        }

        dealerDao.save(dealerInfo);
        return new Message("保存成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealer#updateDealer(com.dxr.apply.entity.DealerInfo)
     */
    @Override
    public String updateDealer(DealerInfo dealerInfo) throws ServiceException {
        String dealerId = dealerInfo.getId();
        DealerInfo dealerDb = dealerDao.get(dealerId);
        if (ObjectUtils.isEmpty(dealerDb)) {
            return new Message(false, Property.error, "经营者信息不存在!").toString();
        }
        String licenseNo = dealerInfo.getLicenseNo();
        if (!licenseNo.equals(dealerDb.getLicenseNo())) {
            DealerInfo dealerCheck = dealerDao.getDealerByLicenseNo(licenseNo);
            if (dealerCheck != null) {
                return new Message(false, ConstGlobal.MESSAGE_TYPE_WARNING,
                        "统一社会信用代码不允许相同!").toString();
            }
        }

        // 更新数据库对象字段
        dealerDb.setName(dealerInfo.getName());
        dealerDb.setLegalPerson(dealerInfo.getLegalPerson());
        dealerDb.setMainProject(dealerInfo.getMainProject());
        dealerDb.setDealerAddress(dealerInfo.getDealerAddress());
        dealerDb.setIndustryId(dealerInfo.getIndustryId());
        dealerDb.setSort(dealerInfo.getSort());
        dealerDb.setRemark(dealerInfo.getRemark());
        dealerDb.setLicenseNo(dealerInfo.getLicenseNo());
        dealerDb.setDealerTypeId(dealerInfo.getDealerTypeId());
        dealerDb.setSimpleName(dealerInfo.getSimpleName());
        dealerDb.setRegisterAddress(dealerInfo.getRegisterAddress());
        dealerDb.setJurisdiction(dealerInfo.getJurisdiction());
        dealerDb.setLinkTel(dealerInfo.getLinkTel());
        dealerDb.setUpdateDate(new Date());
        dealerDao.update(dealerDb);
        return new Message("更新成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealer#deleteDetails(java.lang.String)
     */
    @Override
    public String deleteDealer(String dealerId) throws ServiceException {
        DealerInfo dealerInfo = dealerDao.get(dealerId);
        if (ObjectUtils.isEmpty(dealerInfo)) {
            return new Message(false, Property.error, "经营者信息不存在!").toString();
        }
        dealerInfo.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        dealerDao.update(dealerInfo);
        return new Message("删除成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.apply.service.api.IDealer#getDetails(java.lang.String)
     */
    @Override
    public String getDetails(String dealerId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        DealerInfo dealerInfo = dealerDao.get(dealerId);
        if (ObjectUtils.isEmpty(dealerInfo)) {
            return JSON.toJSONString(detailsMap);
        }

        detailsMap.put("dealerInfo.id", dealerInfo.getId());
        detailsMap.put("dealerInfo.name", dealerInfo.getName());
        detailsMap.put("dealerInfo.mainProject", dealerInfo.getMainProject());
        detailsMap.put("dealerInfo.dealerAddress",
                dealerInfo.getDealerAddress());
        detailsMap.put("dealerInfo.legalPerson", dealerInfo.getLegalPerson());
        detailsMap.put("dealerInfo.industryId", dealerInfo.getIndustryId());
        IndustryInfo industryInfo = industryMgr.getDbObject(dealerInfo
                .getIndustryId());
        if (ObjectUtils.isNotEmpty(industryInfo)) {
            detailsMap.put("dealerInfo.industryName", industryInfo.getName());
        }
        detailsMap.put("dealerInfo.sort", dealerInfo.getSort());
        detailsMap.put("dealerInfo.remark", dealerInfo.getRemark());
        detailsMap.put("dealerInfo.licenseNo", dealerInfo.getLicenseNo());
        detailsMap.put("dealerInfo.dealerTypeId", dealerInfo.getDealerTypeId());
        /*DealerTypeInfo dealerTypeInfo = dealerTypeMgr.getDbObject(dealerInfo
                .getDealerTypeId());
        if (ObjectUtils.isNotEmpty(industryInfo)) {
            detailsMap.put("dealerInfo.dealerTypeName",
                    dealerTypeInfo.getName());
        }*/
        detailsMap.put("dealerInfo.simpleName", dealerInfo.getSimpleName());
        detailsMap.put("dealerInfo.registerAddress",
                dealerInfo.getRegisterAddress());
        detailsMap.put("dealerInfo.jurisdiction", dealerInfo.getJurisdiction());
        Organization orgInfo = orgMgr.getDbObject(dealerInfo.getJurisdiction());
        if (ObjectUtils.isNotEmpty(orgInfo)) {
            detailsMap.put("dealerInfo.jurisdictionName", orgInfo.getName());
        }
        detailsMap.put("dealerInfo.qrcode", dealerInfo.getQrcode());
        detailsMap.put("dealerInfo.linkTel", dealerInfo.getLinkTel());
        return JSON.toJSONString(detailsMap);
    }

    @Override
    public DealerInfo getDbObject(String id) throws ServiceException {
        return dealerDao.get(id);
    }

    @Override
    public String generateDealerQrCode(String dealerId) throws ServiceException {
        DealerInfo dealerInfo = dealerDao.get(dealerId);
        // No1: 先判断是否有二维码, 若有需要先删除
        String qrcodeAttachId = dealerInfo.getQrcode();
        if (StringUtils.isNotEmpty(qrcodeAttachId)) {
            attachMgr.deleteAttach(qrcodeAttachId);
        }

        // No2: 生成二维码, 并保存到目标目录中
        String format = "jpg";
        String fileName = dealerInfo.getId() + ".jpg";
        String targetDir = uploadRootPath + "dealer_QrCode\\";
        try {
            String absoluteWebContext = String.valueOf(scCache
                    .get("AbsoluteWebContext"));
            // 组装查询经营者信用的url
            String showDealerQrcode = absoluteWebContext
                    + "wechat/wechatAction!dealerCreditQuery.action?dealerId="
                    + dealerId;
            // 将查询经营者信用的url作为授权后重定向的回调链接地址
            String qrCodeContent = builderAuthorizeCodeUrl(showDealerQrcode);
            qrCodeFactory.creatQrImage(qrCodeContent, format, fileName,
                    targetDir, null);
        } catch (IOException | WriterException e) {
            throw new ServiceException("生成二维码失败！", e);
        }

        // No3: 保存附件记录
        String attachPath = targetDir + fileName;
        AttachInfo attachInfo = new AttachInfo();
        attachInfo.setAttachType(ConstGlobal.UPLOAD_ATTACH_TYPE_IMAGE);
        attachInfo.setAttachName(fileName);
        attachInfo.setAttachSize(FileUtils.getFileSize(attachPath));
        attachInfo.setFileType("image/jpeg");
        attachInfo.setAttachPath(attachPath);
        attachInfo.setCreatorId(SecurityUser.getLoginUser().getUserId());
        attachMgr.saveAttach(attachInfo);

        // No4: 更新经营者二维码字段
        dealerInfo.setQrcode(attachInfo.getId());
        attachInfo.setUpdateDate(new Date());
        dealerDao.update(dealerInfo);
        return new Message("二维码已生成!").toString();
    }

    @Override
    public String generateBatchDealerQrCode(String[] dealerIds)
            throws ServiceException {
        for (String dealerId : dealerIds) {
            generateDealerQrCode(dealerId);
        }
        return new Message("批量二维码已生成!").toString();
    }

    @Override
    public String createDealerQrCodeWithBgi(String dealerId)
            throws ServiceException {
        try {
            DealerInfo dealerInfo = dealerDao.get(dealerId);
            String qrcodeAttachId = dealerInfo.getQrcode();

            // 生成的包装后的二维码存放地址
            String targetDir = uploadRootPath + "dealer_QrCode_BGI\\";
            // 目录不存在时创建目录
            File dirFile = new File(targetDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            // 判断文件是否存在
            File qrcodeFile = new File(targetDir + "/" + dealerId + ".jpg");
            if (qrcodeFile.exists()) {
                return "wechat/dealerAction!showQrCode?object=" + dealerId;
            }

            // 项目根路径
            String rootPath = this.getClass().getResource("/../../").getPath();
            // 要对获取到的目录解码, 规避路径带空格导致出现找不到文件的错误
            rootPath = URLDecoder.decode(rootPath, "utf-8");
            // 二维码A4背景图
            String backgroundPath = String
                    .format("%1$s/static/other/QrCodeBGI/QrCode_Template.jpg",
                            rootPath);
            // 读取二维码背景图片, 返回 BufferedImage对象
            BufferedImage bgImgBi = ImageIO.read(new File(backgroundPath));

            // 创建一个线程
            ExecutorService es = Executors.newFixedThreadPool(50);
            List<Future<?>> futureList = new ArrayList<Future<?>>();
            AttachInfo attachInfo = attachMgr.getDbObject(qrcodeAttachId);
            String attachPath = attachInfo.getAttachPath();

            String targetstr = targetDir + "/" + dealerInfo.getId() + ".jpg";
            // 合成经营者打印二维码线程
            DealerQrCodeRunnable dqr = new DealerQrCodeRunnable(a4width,
                    a4height);
            dqr.setDealerName(dealerInfo.getName());
            dqr.setDealerQrCodePath(attachPath);
            dqr.setTargetPath(targetstr);
            dqr.setBgImgBi(bgImgBi);
            Future<?> futureTask = es.submit(dqr);
            futureList.add(futureTask);

            // 等待所有线程执行完毕
            for (Future<?> future : futureList) {
                future.get();
            }
            // 执行完毕关闭线程池
            es.shutdown();
        } catch (Exception e) {
            throw new ServiceException("createDealerQrCodeWithBgi is error...",
                    e);
        }
        return "wechat/dealerAction!showQrCode?object=" + dealerId;
    }

    @Override
    public String addTempZipFile(String[] dealerIds) throws ServiceException {
        try {
            // No1: 创建临时文件夹
            String uuid = UUID.randomUUID().toString();
            File temDir = new File(uploadTempPath + "\\" + uuid);
            if (!temDir.exists()) {
                temDir.mkdirs();
            }

            // 项目根路径
            String rootPath = this.getClass().getResource("/../../").getPath();
            // 要对获取到的目录解码, 规避路径带空格导致出现找不到文件的错误
            rootPath = URLDecoder.decode(rootPath, "utf-8");
            // 二维码A4背景图
            String backgroundPath = String
                    .format("%1$s/static/other/QrCodeBGI/QrCode_Template.jpg",
                            rootPath);
            // 读取二维码背景图片, 返回 BufferedImage对象
            BufferedImage bgImgBi = ImageIO.read(new File(backgroundPath));

            // 创建一个线程
            ExecutorService es = Executors.newFixedThreadPool(50);
            List<Future<?>> futureList = new ArrayList<Future<?>>();
            // No2: 生成需要下载的文件，存放在临时文件夹内
            for (String dealerId : dealerIds) {
                DealerInfo dealerInfo = getDbObject(dealerId);
                String qrcode = dealerInfo.getQrcode();
                AttachInfo attachInfo = attachMgr.getDbObject(qrcode);
                String attachPath = attachInfo.getAttachPath();

                //目标文件
                String attachName = attachInfo.getAttachName();
                // 获取文件格式
                String fileSuffix = attachName.substring(attachName
                        .lastIndexOf(".") + 1);
                String targetstr = temDir + "/" + dealerInfo.getName() + "."
                        + fileSuffix;
                // 合成经营者打印二维码线程
                DealerQrCodeRunnable dqr = new DealerQrCodeRunnable(a4width,
                        a4height);
                dqr.setDealerName(dealerInfo.getName());
                dqr.setDealerQrCodePath(attachPath);
                dqr.setTargetPath(targetstr);
                dqr.setBgImgBi(bgImgBi);
                Future<?> futureTask = es.submit(dqr);
                futureList.add(futureTask);
            }

            // 等待所有线程执行完毕
            for (Future<?> future : futureList) {
                future.get();
            }
            // 执行完毕关闭线程池
            es.shutdown();

            // No3: 调用工具类，生成zip压缩包
            ZipUtils.toZip(temDir.getPath());

            // No4: 保存附件记录
            String attachPath = temDir.getPath() + ".zip";
            AttachInfo attachInfo = new AttachInfo();
            attachInfo.setAttachType(ConstGlobal.UPLOAD_ATTACH_TYPE_OTHER);
            attachInfo.setAttachName(uuid + ".zip");
            attachInfo.setAttachSize(FileUtils.getFileSize(attachPath));
            attachInfo.setFileType("application/zip");
            attachInfo.setAttachPath(attachPath);
            attachInfo.setCreatorId(SecurityUser.getLoginUser().getUserId());
            attachMgr.saveAttach(attachInfo);
            return attachInfo.getId();
        } catch (Exception e) {
            throw new ServiceException("downloadBatch is error...", e);
        }
    }

    @Override
    public void downloadBatch(String attachId, HttpServletRequest request,
            HttpServletResponse response) throws ServiceException {
        try {
            // 先下载临时的zip文件
            attachMgr.downloadAttach(attachId, request, response);

            // 下载完成后删除
            attachMgr.deleteAttach(attachId);
        } catch (Exception e) {
            throw new ServiceException("downloadBatch is error...", e);
        }
    }

    @Override
    public String addDealerForImport(File file, String fileName)
            throws ServiceException {
        try {
            ExcelUtils excelUtils = new ExcelUtils(file, fileName);
            List<String> importIds = new ArrayList<String>();
            DealerInfo dealerInfo = null;
            List<Map<Integer, Object>> rows = excelUtils.readExcelContent();
            for (Map<Integer, Object> row : rows) {
                dealerInfo = new DealerInfo();
                // 企业名称
                dealerInfo.setName(String.valueOf(row.get(0)));
                // 统一社会信用代码
                String licenseNo = String.valueOf(row.get(1));
                DealerInfo dealerCheck = dealerDao
                        .getDealerByLicenseNo(licenseNo);
                if (dealerCheck != null) {
                    continue;
                }
                dealerInfo.setLicenseNo(licenseNo);
                // 法定代表人
                dealerInfo.setLegalPerson(String.valueOf(row.get(2)));
                // TODO管辖单位&行业处理
                // 管辖单位
                String jurisdiction = String.valueOf(row.get(3));
                Organization orgInfo = orgMgr
                        .getOrganizationByName(jurisdiction);
                if (orgInfo == null) {
                    throw new RuntimeException("基础信息不完善!管辖单位：" + row.get(3));
                }
                dealerInfo.setJurisdiction(orgInfo.getId());
                String industryName = String.valueOf(row.get(4));
                IndustryInfo industryInfo = industryMgr
                        .getIndustryByName(industryName);
                if (industryInfo == null) {
                    throw new RuntimeException("基础信息不完善!行业：" + row.get(4));
                }
                // 所属行业
                dealerInfo.setIndustryId(industryInfo.getId());
                // 经营范围
                dealerInfo.setMainProject(String.valueOf(row.get(5)));
                // 经营地址
                dealerInfo.setRegisterAddress(String.valueOf(row.get(6)));
                dealerInfo.setDealerAddress(String.valueOf(row.get(6)));
                // 联系电话
                dealerInfo.setLinkTel(String.valueOf(row.get(7)));

                // 持久化, 刷新到数据库中
                dealerDao.save(dealerInfo);
                dealerDao.getSession().flush();

                // 生成二维码
                generateDealerQrCode(dealerInfo.getId());

                // 将刚生成的id存起来便于后面的打包下载
                importIds.add(dealerInfo.getId());
            }

            // 打包下载二维码&生成需要的格式的图片
            String attachId = addTempZipFile(importIds
                    .toArray(new String[importIds.size()]));
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("attachId", attachId);
            return result.toJSONString();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> queryDealerCredit(String dealerId)
            throws ServiceException {
        Map<String, Object> dealerCredit = new LinkedHashMap<String, Object>();
        // 拼装经营者基本信息
        DealerInfo dealerInfo = getDbObject(dealerId);
        if (ObjectUtils.isNotEmpty(dealerInfo)) {
            dealerCredit.put("dealerInfo", dealerInfo);
        }
        // 获取行业信息
        IndustryInfo industry = industryMgr.getDbObject(dealerInfo
                .getIndustryId());
        if (ObjectUtils.isNotEmpty(industry)
                && industry.getStatus() != ConstGlobal.DATA_STATUS_DELETED) {
            dealerCredit.put("industry", industry);
        }
        // 拼装诚信记录数据
        List<DealerCreditVo> dealerCreditList = complaintSheetMgr
                .findDealerCreditList(dealerId);
        dealerCredit.put("creditArray", dealerCreditList);
        // 导入的投诉结果
        List<ComplaintResultInfo> complaintResultList = complaintResultMgr
                .findComplaintResultList(dealerId);
        dealerCredit.put("complaintResultArray", complaintResultList);
        // 获取行业预警列表
        List<ConsumerWarningInfo> warningList = consumerWarningMgr
                .findIndustryConsumerWarning(industry.getId());
        dealerCredit.put("warningArray", warningList);
        return dealerCredit;
    }

    @Override
    public String fuzzyQueryDealerList(String keywords) throws ServiceException {
        if (StringUtils.isEmpty(keywords)) {
            return super.EMPTY_ARRAY;
        }

        List<DealerInfo> dealerList = dealerDao.fuzzyQueryDealerList(keywords);
        if (dealerList == null || dealerList.isEmpty()) {
            DealerInfo nullObj = new DealerInfo();
            nullObj.setId("-1");
            nullObj.setName("没有找到【" + keywords + "】相关的信息");
            dealerList.add(nullObj);
        }
        return JSONArray.toJSONString(dealerList);
    }

    /**
     * 构建引导关注者打开url以获取用户的openId
     * @param url
     * @return
     * @throws ServiceException
     * @throws UnsupportedEncodingException 
     */
    private String builderAuthorizeCodeUrl(String url) throws ServiceException,
            UnsupportedEncodingException {
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        param.put("appid", wechatAppId);
        // 授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
        param.put("redirect_uri", URLEncoder.encode(url, "UTF-8"));
        param.put("response_type", "code");
        param.put("scope", "snsapi_userinfo");
        param.put("state", "ScanQRcode#wechat_redirect");
        return SimpleHttpClient.setQueryString(authorizeCodeApiUrl, param);
    }

    @Override
    public void showImage(String dealerId, HttpServletResponse response)
            throws Exception {
        String imgpath = uploadRootPath + "dealer_QrCode_BGI\\" + dealerId
                + ".jpg";
        FileUtils.showImage(imgpath, null, response);
    }

    public String getUploadRootPath() {
        return uploadRootPath;
    }

    public void setUploadRootPath(String uploadRootPath) {
        this.uploadRootPath = uploadRootPath;
    }

    public String getWechatAppId() {
        return wechatAppId;
    }

    public void setWechatAppId(String wechatAppId) {
        this.wechatAppId = wechatAppId;
    }

    public String getAuthorizeCodeApiUrl() {
        return authorizeCodeApiUrl;
    }

    public void setAuthorizeCodeApiUrl(String authorizeCodeApiUrl) {
        this.authorizeCodeApiUrl = authorizeCodeApiUrl;
    }

    public String getUploadTempPath() {
        return uploadTempPath;
    }

    public void setUploadTempPath(String uploadTempPath) {
        this.uploadTempPath = uploadTempPath;
    }

    public void setA4width(Integer a4width) {
        this.a4width = a4width;
    }

    public void setA4height(Integer a4height) {
        this.a4height = a4height;
    }
}
