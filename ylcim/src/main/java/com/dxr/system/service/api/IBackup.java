package com.dxr.system.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.BackupInfo;

/**
 * @description: <数据库备份服务接口层>
 * @author: w.xL
 * @date: 2018-3-21
 */
public interface IBackup extends IBasalMgr<BackupInfo> {

    /**
     * 获取备份列表, 按日期倒序排列
     * @param page
     * @param rows
     * @param backupInfo
     * @return
     * @throws ServiceException
     */
    public String getBackupPage(int page, int rows, BackupInfo backupInfo)
            throws ServiceException;

    /**
     * 备份数据库
     * @return
     * @throws ServiceException
     */
    public String backup() throws ServiceException;

    /**
     * 还原数据库
     * @param backupId
     * @return
     * @throws ServiceException
     */
    public String restore(String backupId) throws ServiceException;

    /**
     * 删除备份
     * @param backupId
     * @return
     * @throws ServiceException
     */
    public String deleteBackup(String backupId) throws ServiceException;
}
