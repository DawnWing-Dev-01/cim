package com.dxr.apply.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.DateUtils;
import com.dawnwing.framework.utils.ExcelUtils;
import com.dawnwing.framework.utils.IoUtils;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.ComplaintResultDao;
import com.dxr.apply.dao.ComplaintSheetDao;
import com.dxr.apply.entity.ComplaintHandleInfo;
import com.dxr.apply.entity.ComplaintResultInfo;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.service.api.IComplaintHandle;
import com.dxr.apply.service.api.IComplaintResult;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;

/**
 * @description: <投诉结果导入导出服务接口实现层>
 * @author: w.xL
 * @date: 2018-5-1
 */
public class ComplaintResultMgr extends BasalMgr<ComplaintResultInfo> implements
        IComplaintResult {

    @Autowired
    private ComplaintResultDao complaintResultDao;
    @Autowired
    private ComplaintSheetDao complaintSheetDao;
    @Autowired
    private IComplaintHandle handleMgr;

    private String originPublicity;

    @Override
    public ComplaintResultInfo getDbObject(String id) throws ServiceException {
        return complaintResultDao.get(id);
    }

    @Override
    public String getComplaintResultPage(int page, int rows,
            ComplaintResultInfo complaintResultInfo) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (complaintResultInfo != null) {
            if (StringUtils.isNotEmpty(complaintResultInfo.getOriginTypeId())) {
                filterList.add(new FilterBean("result.originTypeId",
                        HqlSymbol.HQL_EQUAL, complaintResultInfo
                                .getOriginTypeId()));
            }

            if (StringUtils.isNotEmpty(complaintResultInfo.getDealerId())) {
                filterList
                        .add(new FilterBean("result.dealerId",
                                HqlSymbol.HQL_EQUAL, complaintResultInfo
                                        .getDealerId()));
            }

            if (StringUtils.isNotEmpty(complaintResultInfo.getEnterCode())) {
                filterList.add(new FilterBean("result.enterCode",
                        HqlSymbol.HQL_LIKE, "%"
                                + complaintResultInfo.getEnterCode() + "%"));
            }
        }

        List<ComplaintResultInfo> resultList = complaintResultDao
                .getComplaintResultPage((page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", complaintResultDao.getTotal(filterList));
        result.put("rows", resultList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    /**
     * 历史失信行为转换为投诉结果
     */
    public void saveHistoryToResult() throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        filterList.add(new FilterBean("complaint.flowStatus",
                HqlSymbol.HQL_EQUAL,
                ConstGlobal.COMPLAINT_FLOW_STATUS_CONFIRMED));
        filterList.add(new FilterBean("complaint.isPublicity",
                HqlSymbol.HQL_EQUAL, ConstGlobal.COMPLAINT_IS_PUBLICITY_TRUE));
        List<ComplaintSheetInfo> complaintList = complaintSheetDao
                .getComplaintSheetPage(0, Integer.MAX_VALUE, filterList);
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        ComplaintResultInfo cri = null;
        for (ComplaintSheetInfo complaintSheetInfo : complaintList) {
            String code = complaintSheetInfo.getComplaintCode();
            long total = complaintResultDao.findTotalByCode(code);
            if (total > 0) {
                continue;
            }

            cri = new ComplaintResultInfo();
            cri.setOriginTypeId(originPublicity);
            cri.setDealerId(complaintSheetInfo.getDealerId());
            cri.setIsPublicity(complaintSheetInfo.getIsPublicity());
            cri.setEnterCode(code);
            cri.setEnterDept(complaintSheetInfo.getRegisterUnit());
            String providerName = StringUtils.isNotEmpty(complaintSheetInfo
                    .getInformerName()) ? complaintSheetInfo.getInformerName()
                    : "匿名";
            cri.setProviderName(providerName);
            Integer classify = complaintSheetInfo.getReportClassify();
            String complaintType = classify == 1 ? "举报" : (classify == 2 ? "投诉"
                    : "咨询");
            cri.setComplaintType(complaintType);
            cri.setHandleStatus("办结已归档");
            cri.setHandleDept(complaintSheetInfo.getRegisterUnit());
            cri.setEnterDate(complaintSheetInfo.getCreateDate());
            cri.setComplaints(complaintSheetInfo.getComplaintReason());
            // 获取投诉处理结果
            ComplaintHandleInfo complaintHandle = handleMgr
                    .getFinallyHandle(complaintSheetInfo.getId());
            cri.setFinishKnotDate(complaintHandle.getHandleDate());
            cri.setHandlePeople(complaintHandle.getHandleUserName());
            cri.setFinishKnot(complaintHandle.getHandleSay());

            cri.setCreator(shiroUser.getRealName());
            complaintResultDao.save(cri);
        }
    }

    @Override
    public String addResultForImport(String originTypeId, File file,
            String fileName) throws ServiceException {
        try {
            ShiroUser shiroUser = SecurityUser.getLoginUser();
            ComplaintResultInfo complaintResult = null;
            ExcelUtils excelUtils = new ExcelUtils(file, fileName);
            List<Map<Integer, Object>> rows = excelUtils.readExcelContent();
            for (Map<Integer, Object> row : rows) {
                complaintResult = new ComplaintResultInfo();
                String enterCode = String.valueOf(row.get(0));
                long total = complaintResultDao.findTotalByCode(enterCode);
                if (total > 0) {
                    continue;
                }
                complaintResult.setOriginTypeId(originTypeId);
                complaintResult.setEnterCode(enterCode);
                complaintResult.setProviderName(String.valueOf(row.get(1)));
                complaintResult.setComplaints(String.valueOf(row.get(2)));
                Date enterDate = null;
                if (row.get(3) instanceof Date) {
                    enterDate = (Date) row.get(3);
                } else {
                    String enterDateStr = String.valueOf(row.get(3));
                    enterDate = DateUtils.strToDate(Property.yyyyMMdd,
                            enterDateStr);
                }
                complaintResult.setEnterDate(enterDate);
                complaintResult.setComplaintType(String.valueOf(row.get(4)));
                complaintResult.setHandleStatus(String.valueOf(row.get(5)));
                complaintResult.setEnterDept(String.valueOf(row.get(6)));
                complaintResult.setHandleDept(String.valueOf(row.get(7)));
                String handleDeadlineStr = String.valueOf(row.get(8));
                if (row.get(8) != null
                        && StringUtils.isNotEmpty(handleDeadlineStr)) {
                    Date handleDeadline = null;
                    if (row.get(8) instanceof Date) {
                        handleDeadline = (Date) row.get(8);
                    } else {
                        handleDeadline = DateUtils.strToDate(Property.yyyyMMdd,
                                handleDeadlineStr);
                    }
                    complaintResult.setHandleDeadline(handleDeadline);
                }
                String netCreatedateStr = String.valueOf(row.get(9));
                if (row.get(9) != null
                        && StringUtils.isNotEmpty(netCreatedateStr)) {
                    Date netCreatedate = null;
                    if (row.get(9) instanceof Date) {
                        netCreatedate = (Date) row.get(9);
                    } else {
                        netCreatedate = DateUtils.strToDate(Property.yyyyMMdd,
                                netCreatedateStr);
                    }
                    complaintResult.setNetCreatedate(netCreatedate);
                }
                complaintResult.setNetRemark(String.valueOf(row.get(10)));
                complaintResult.setNetResult(String.valueOf(row.get(11)));
                if (row.get(12) != null) {
                    complaintResult.setFinishKnot(String.valueOf(row.get(12)));
                }

                String finishKnotDateStr = String.valueOf(row.get(13));
                if (row.get(13) != null
                        && StringUtils.isNotEmpty(finishKnotDateStr)) {
                    Date finishKnotDate = null;
                    if (row.get(13) instanceof Date) {
                        finishKnotDate = (Date) row.get(13);
                    } else {
                        finishKnotDate = DateUtils.strToDate(Property.yyyyMMdd,
                                finishKnotDateStr);
                    }
                    complaintResult.setFinishKnotDate(finishKnotDate);
                }
                if (row.get(14) != null) {
                    complaintResult
                            .setHandlePeople(String.valueOf(row.get(14)));
                }
                if (row.get(15) != null) {
                    complaintResult.setExt01(String.valueOf(row.get(15)));
                }
                if (row.get(16) != null) {
                    complaintResult.setExt02(String.valueOf(row.get(16)));
                }
                if (row.get(17) != null) {
                    complaintResult.setExt03(String.valueOf(row.get(17)));
                }
                if (row.get(18) != null) {
                    complaintResult.setExt04(String.valueOf(row.get(18)));
                }
                if (row.get(19) != null) {
                    complaintResult.setExt05(String.valueOf(row.get(19)));
                }
                complaintResult.setCreator(shiroUser.getRealName());

                complaintResultDao.save(complaintResult);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return new Message("导入成功！").toString();
    }

    @Override
    public void exportComplaintResult(HttpServletResponse response,
            Date startDate, Date endDate, String originTypeId)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();
        if (originTypeId != null) {
            filterList.add(new FilterBean("result.originTypeId",
                    HqlSymbol.HQL_EQUAL, originTypeId));
        }
        if (startDate != null) {
            filterList.add(new FilterBean("result.enterDate",
                    HqlSymbol.HQL_GREATER_OR_EQUAL, startDate, false));
        }
        if (endDate != null) {
            filterList.add(new FilterBean("result.enterDate",
                    HqlSymbol.HQL_LESS_OR_EQUAL, endDate, false));
        }

        List<ComplaintResultInfo> resultList = complaintResultDao
                .getComplaintResultPage(0, Integer.MAX_VALUE, filterList);

        // 新文件写入数据，并下载*****************************************************  
        InputStream is = null;
        OutputStream out = null;
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            String prjRootPath = this.getClass().getResource("/../../")
                    .getPath();
            // 要对获取到的目录解码, 规避路径带空格导致出现找不到文件的错误
            prjRootPath = URLDecoder.decode(prjRootPath, "utf-8");
            File newFile = new File(prjRootPath
                    + "/static/other/template/template_export_result.xls");

            is = new FileInputStream(newFile);// 将excel文件转为输入流  
            workbook = new HSSFWorkbook(is);// 创建个workbook，  
            // 获取第一个sheet  
            sheet = workbook.getSheetAt(0);
            // 循环写数据  
            Row row = null;
            int index = 1;
            for (ComplaintResultInfo crInfo : resultList) {
                row = sheet.getRow(index);
                if (row == null) {
                    row = sheet.createRow(index);
                }
                Cell cell0 = row.getCell(0) != null ? row.getCell(0) : row
                        .createCell(0);
                cell0.setCellValue(crInfo.getEnterCode());
                Cell cell1 = row.getCell(1) != null ? row.getCell(1) : row
                        .createCell(1);
                cell1.setCellValue(crInfo.getProviderName());
                Cell cell2 = row.getCell(2) != null ? row.getCell(2) : row
                        .createCell(2);
                cell2.setCellValue(crInfo.getComplaints());
                if (crInfo.getEnterDate() != null) {
                    Cell cell3 = row.getCell(3) != null ? row.getCell(3) : row
                            .createCell(3);
                    cell3.setCellValue(DateUtils.dateFormat(Property.yyyyMMdd,
                            crInfo.getEnterDate()));
                }

                Cell cell4 = row.getCell(4) != null ? row.getCell(4) : row
                        .createCell(4);
                cell4.setCellValue(crInfo.getComplaintType());
                Cell cell5 = row.getCell(5) != null ? row.getCell(5) : row
                        .createCell(5);
                cell5.setCellValue(crInfo.getHandleStatus());
                Cell cell6 = row.getCell(6) != null ? row.getCell(6) : row
                        .createCell(6);
                cell6.setCellValue(crInfo.getEnterDept());
                Cell cell7 = row.getCell(7) != null ? row.getCell(7) : row
                        .createCell(7);
                cell7.setCellValue(crInfo.getHandleDept());
                if (crInfo.getHandleDeadline() != null) {
                    Cell cell8 = row.getCell(8) != null ? row.getCell(8) : row
                            .createCell(8);
                    cell8.setCellValue(DateUtils.dateFormat(Property.yyyyMMdd,
                            crInfo.getHandleDeadline()));
                }
                if (crInfo.getNetCreatedate() != null) {
                    Cell cell9 = row.getCell(9) != null ? row.getCell(9) : row
                            .createCell(9);
                    cell9.setCellValue(DateUtils.dateFormat(Property.yyyyMMdd,
                            crInfo.getNetCreatedate()));
                }
                if (StringUtils.isNotEmpty(crInfo.getNetRemark())) {
                    Cell cell10 = row.getCell(10) != null ? row.getCell(10)
                            : row.createCell(10);
                    cell10.setCellValue(crInfo.getNetRemark());
                }
                if (StringUtils.isNotEmpty(crInfo.getNetResult())) {
                    Cell cell11 = row.getCell(11) != null ? row.getCell(11)
                            : row.createCell(11);
                    cell11.setCellValue(crInfo.getNetResult());
                }
                if (StringUtils.isNotEmpty(crInfo.getFinishKnot())) {
                    Cell cell12 = row.getCell(12) != null ? row.getCell(12)
                            : row.createCell(12);
                    cell12.setCellValue(crInfo.getFinishKnot());
                }
                if (crInfo.getFinishKnotDate() != null) {
                    Cell cell13 = row.getCell(13) != null ? row.getCell(13)
                            : row.createCell(13);
                    cell13.setCellValue(DateUtils.dateFormat(Property.yyyyMMdd,
                            crInfo.getFinishKnotDate()));
                }
                if (StringUtils.isNotEmpty(crInfo.getHandlePeople())) {
                    Cell cell14 = row.getCell(14) != null ? row.getCell(14)
                            : row.createCell(14);
                    cell14.setCellValue(crInfo.getHandlePeople());
                }
                if (StringUtils.isNotEmpty(crInfo.getExt01())) {
                    Cell cell15 = row.getCell(15) != null ? row.getCell(15)
                            : row.createCell(15);
                    cell15.setCellValue(crInfo.getExt01());
                }
                if (StringUtils.isNotEmpty(crInfo.getExt02())) {
                    Cell cell16 = row.getCell(16) != null ? row.getCell(16)
                            : row.createCell(16);
                    cell16.setCellValue(crInfo.getExt02());
                }
                if (StringUtils.isNotEmpty(crInfo.getExt03())) {
                    Cell cell17 = row.getCell(17) != null ? row.getCell(17)
                            : row.createCell(17);
                    cell17.setCellValue(crInfo.getExt03());
                }
                if (StringUtils.isNotEmpty(crInfo.getExt04())) {
                    Cell cell18 = row.getCell(18) != null ? row.getCell(18)
                            : row.createCell(18);
                    cell18.setCellValue(crInfo.getExt04());
                }
                if (StringUtils.isNotEmpty(crInfo.getExt05())) {
                    Cell cell19 = row.getCell(19) != null ? row.getCell(19)
                            : row.createCell(19);
                    cell19.setCellValue(crInfo.getExt05());
                }
                index++;
            }
            out = response.getOutputStream();
            String fileName = "投诉结果导出" + System.currentTimeMillis() + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            throw new ServiceException("导出Excel异常", e);
        } finally {
            IoUtils.unifyClose(workbook, out, is);
        }
    }

    @Override
    public String getDetails(String compResultId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        ComplaintResultInfo complaintResult = getDbObject(compResultId);
        if (ObjectUtils.isEmpty(complaintResult)) {
            return JSON.toJSONString(detailsMap);
        }

        detailsMap.put("complaintResult.id", complaintResult.getId());
        detailsMap.put("complaintResult.enterCode",
                complaintResult.getEnterCode());
        detailsMap.put("complaintResult.providerName",
                complaintResult.getProviderName());
        detailsMap.put("complaintResult.complaints",
                complaintResult.getComplaints());
        detailsMap.put("complaintResult.enterDate",
                complaintResult.getEnterDate());
        detailsMap.put("complaintResult.complaintType",
                complaintResult.getComplaintType());
        detailsMap.put("complaintResult.handleStatus",
                complaintResult.getHandleStatus());
        detailsMap.put("complaintResult.enterDept",
                complaintResult.getEnterDept());
        detailsMap.put("complaintResult.handleDept",
                complaintResult.getHandleDept());
        detailsMap.put("complaintResult.handleDeadline",
                complaintResult.getHandleDeadline());
        detailsMap.put("complaintResult.netCreatedate",
                complaintResult.getNetCreatedate());
        detailsMap.put("complaintResult.netRemark",
                complaintResult.getNetRemark());
        detailsMap.put("complaintResult.netResult",
                complaintResult.getNetResult());
        detailsMap.put("complaintResult.finishKnot",
                complaintResult.getFinishKnot());
        detailsMap.put("complaintResult.finishKnotDate",
                complaintResult.getFinishKnotDate());
        detailsMap.put("complaintResult.handlePeople",
                complaintResult.getHandlePeople());
        detailsMap.put("complaintResult.creator", complaintResult.getCreator());
        detailsMap.put("complaintResult.ext01", complaintResult.getExt01());
        detailsMap.put("complaintResult.ext02", complaintResult.getExt02());
        detailsMap.put("complaintResult.ext03", complaintResult.getExt03());
        detailsMap.put("complaintResult.ext04", complaintResult.getExt04());
        detailsMap.put("complaintResult.ext05", complaintResult.getExt05());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

    @Override
    public String updateRelatedDealer(ComplaintResultInfo complaintResultInfo)
            throws ServiceException {
        ComplaintResultInfo complaintResult = getDbObject(complaintResultInfo
                .getId());
        complaintResult.setIsPublicity(complaintResultInfo.getIsPublicity());
        complaintResult.setDealerId(complaintResultInfo.getDealerId());
        complaintResultDao.update(complaintResult);
        return new Message("关联成功！").toString();
    }

    @Override
    public List<ComplaintResultInfo> findComplaintResultList(String dealerId)
            throws ServiceException {
        return complaintResultDao.findComplaintResultList(dealerId);
    }

    @Override
    public String saveComplaintResult(ComplaintResultInfo complaintResultInfo)
            throws ServiceException {
        String enterCode = complaintResultInfo.getEnterCode();
        long total = complaintResultDao.findTotalByCode(enterCode);
        if (total > 0) {
            throw new ServiceException("登记编号【" + enterCode + "】已存在，请重新录入");
        }
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        complaintResultInfo.setCreator(shiroUser.getRealName());

        complaintResultDao.save(complaintResultInfo);
        return new Message("保存成功！").toString();
    }

    @Override
    public String updateComplaintResult(ComplaintResultInfo complaintResultInfo)
            throws ServiceException {
        ComplaintResultInfo complaintResult = getDbObject(complaintResultInfo
                .getId());
        complaintResult.setEnterDept(complaintResultInfo.getEnterDept());
        complaintResult.setProviderName(complaintResultInfo.getProviderName());
        complaintResult
                .setComplaintType(complaintResultInfo.getComplaintType());
        complaintResult.setHandleStatus(complaintResultInfo.getHandleStatus());
        complaintResult.setHandleDept(complaintResultInfo.getHandleDept());
        complaintResult.setEnterDate(complaintResultInfo.getEnterDate());
        complaintResult.setHandleDeadline(complaintResultInfo
                .getHandleDeadline());
        complaintResult.setComplaints(complaintResultInfo.getComplaints());
        complaintResult
                .setNetCreatedate(complaintResultInfo.getNetCreatedate());
        complaintResult.setNetRemark(complaintResultInfo.getNetRemark());
        complaintResult.setNetResult(complaintResultInfo.getNetResult());
        complaintResult.setFinishKnotDate(complaintResultInfo
                .getFinishKnotDate());
        complaintResult.setHandlePeople(complaintResultInfo.getHandlePeople());
        complaintResult.setFinishKnot(complaintResultInfo.getFinishKnot());
        complaintResult.setExt01(complaintResultInfo.getExt01());
        complaintResult.setExt02(complaintResultInfo.getExt02());
        complaintResult.setExt03(complaintResultInfo.getExt03());
        complaintResult.setExt04(complaintResultInfo.getExt04());
        complaintResult.setExt05(complaintResultInfo.getExt05());
        complaintResultDao.update(complaintResult);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteComplaintResult(String resultId)
            throws ServiceException {
        complaintResultDao.deleteById(resultId);
        return new Message("删除成功!").toString();
    }

    public void setOriginPublicity(String originPublicity) {
        this.originPublicity = originPublicity;
    }

}
