package com.dxr.apply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.ComplaintHandleDao;
import com.dxr.apply.entity.ComplaintHandleInfo;
import com.dxr.apply.service.api.IComplaintHandle;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;

/**
 * @description: <投诉处理服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-13
 */
public class ComplaintHandleMgr extends BasalMgr<ComplaintHandleInfo> implements
        IComplaintHandle {

    @Autowired
    private ComplaintHandleDao handleDao;

    @Override
    public ComplaintHandleInfo getDbObject(String id) throws ServiceException {
        return handleDao.get(id);
    }

    @Override
    public String getComplaintHandlePage(int page, int rows,
            ComplaintHandleInfo complaintHandleInfo) throws ServiceException {
        if (complaintHandleInfo == null
                || StringUtils.isEmpty(complaintHandleInfo.getComplaintId())) {
            return super.EMPTY_GRID;
        }
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (ObjectUtils.isNotEmpty(complaintHandleInfo)) {
            if (StringUtils.isNotEmpty(complaintHandleInfo.getComplaintId())) {
                filterList.add(new FilterBean("handle.complaintId",
                        HqlSymbol.HQL_EQUAL, complaintHandleInfo
                                .getComplaintId()));
            }
        }

        List<ComplaintHandleInfo> handleList = handleDao
                .getComplaintHandlePage((page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", handleDao.getTotal(filterList));
        result.put("rows", handleList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveComplaintHandle(ComplaintHandleInfo complaintHandleInfo,
            boolean isFlush) throws ServiceException {
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        if (shiroUser != null) {
            complaintHandleInfo.setHandleUserId(shiroUser.getUserId());
            complaintHandleInfo.setHandleUserName(shiroUser.getRealName());
        }

        if (ConstGlobal.COMPLAINT_HANDLE_TYPE_FINALLY
                .equals(complaintHandleInfo.getHandleType())) {
            boolean isFinally = handleDao.isFinally(complaintHandleInfo
                    .getComplaintId());
            if (isFinally) {
                throw new ServiceException("已经有最终结论，请不要重复定论！");
            }
        }
        handleDao.save(complaintHandleInfo);
        // 强制提交
        if (isFlush) {
            handleDao.getSession().flush();
        }
        return new Message("保存成功!").toString();
    }

    @Override
    public String deleteComplaintHandle(String handleId)
            throws ServiceException {
        handleDao.deleteById(handleId);
        return new Message("刪除成功!").toString();
    }

    @Override
    public String isFinally(String complaintId) throws ServiceException {
        boolean isFinally = handleDao.isFinally(complaintId);
        JSONObject result = new JSONObject();
        result.put("isFinally", isFinally);
        return result.toJSONString();
    }

    @Override
    public ComplaintHandleInfo getFinallyHandle(String complaintId) {
        return handleDao.getFinallyHandle(complaintId);
    }

}
