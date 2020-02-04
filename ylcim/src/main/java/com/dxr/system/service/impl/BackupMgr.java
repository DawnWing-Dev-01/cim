package com.dxr.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.FileUtils;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.utils.SimpleDataBaseUtils;
import com.dxr.system.dao.BackupDao;
import com.dxr.system.entity.BackupInfo;
import com.dxr.system.service.api.IBackup;

/**
 * @description: <数据库服务接口实现层>
 * @author: w.xL
 * @date: 2018-3-21
 */
public class BackupMgr extends BasalMgr<BackupInfo> implements IBackup {

    @Autowired
    private BackupDao backupDao;

    @Autowired
    private SimpleDataBaseUtils simpleDataBaseUtils;

    /**
     * 备份目录
     */
    private String backupDir;

    @Override
    public BackupInfo getDbObject(String id) throws ServiceException {
        return backupDao.get(id);
    }

    @Override
    public String getBackupPage(int page, int rows, BackupInfo backupInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (ObjectUtils.isNotEmpty(backupInfo)) {
            if (StringUtils.isNotEmpty(backupInfo.getName())) {
                filterList.add(new FilterBean("backup.name",
                        HqlSymbol.HQL_LIKE, "%" + backupInfo.getName() + "%"));
            }
        }

        List<BackupInfo> backupList = backupDao.getBackupPage(
                (page - 1) * rows, rows, filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", backupDao.getTotal(filterList));
        result.put("rows", backupList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String backup() throws ServiceException {
        // No1、备份保存的地址
        String fileName = new Date().getTime() + ".sql";
        String backupPath = backupDir + fileName;

        // No2、将备份记录保存进数据库
        BackupInfo backup = new BackupInfo();
        backup.setBackupPath(backupPath);
        SimpleDateFormat sdf = new SimpleDateFormat(Property.yyyyMMdd);
        String name = "【数据备份】" + sdf.format(new Date());
        backup.setName(name);
        backup.setRemark("【手动备份】");
        backupDao.save(backup);
        // 强制此会话刷新, 持久状态的对象存储在数据库中
        backupDao.getSession().flush();

        // No3、执行备份操作
        try {
            simpleDataBaseUtils.backup(backupDir, fileName);
        } catch (ServiceException e) {
            // 备份数据库出现异常, 删除备份记录
            backupDao.deleteById(backup.getId());
            throw e;
        }
        return new Message("备份成功!").toString();
    }

    @Override
    public String restore(String backupId) throws ServiceException {
        // 获取备份在磁盘的位置
        BackupInfo backup = backupDao.get(backupId);
        String backupPath = backup.getBackupPath();
        if (StringUtils.isEmpty(backupPath)) {
            throw new ServiceException("数据库备份文件不存在！");
        }
        // 执行还原操作
        simpleDataBaseUtils.restore(backupPath);
        return new Message("还原成功!").toString();
    }

    @Override
    public String deleteBackup(String backupId) throws ServiceException {
        // 获取备份在磁盘的位置
        BackupInfo backup = backupDao.get(backupId);
        String backupPath = backup.getBackupPath();
        if (StringUtils.isEmpty(backupPath)) {
            throw new ServiceException("数据库备份文件不存在！");
        }
        // 删除备份文件
        FileUtils.delFile(backupPath);

        backupDao.delete(backup);
        return new Message("删除成功!").toString();
    }

    public String getBackupDir() {
        return backupDir;
    }

    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

}
