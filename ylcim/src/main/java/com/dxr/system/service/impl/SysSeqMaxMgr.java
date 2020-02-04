package com.dxr.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dxr.system.dao.SysSeqMaxDao;
import com.dxr.system.entity.SysSeqMax;
import com.dxr.system.service.api.ISysSeqMax;

/**
 * @description: <流水序列服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class SysSeqMaxMgr extends BasalMgr<SysSeqMax> implements ISysSeqMax {

    @Autowired
    private SysSeqMaxDao sysSeqMaxDao;

    private String codeSeqId;

    @Override
    public SysSeqMax getDbObject(String id) throws ServiceException {
        return sysSeqMaxDao.get(id);
    }

    @Override
    public String generateComplaintCode() throws ServiceException {
        Integer maxNum = sysSeqMaxDao.updateAndGetCurrentSeq(codeSeqId);
        if (maxNum == null) {
            throw new ServiceException("generate Complaint Code error...");
        }
        //数字长度为5位，长度不够数字前面补0
        String newStrNum = String.format("%05d", maxNum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(dateStr);
        sb.append(newStrNum);
        return sb.toString();
    }

    public String getCodeSeqId() {
        return codeSeqId;
    }

    public void setCodeSeqId(String codeSeqId) {
        this.codeSeqId = codeSeqId;
    }
}
