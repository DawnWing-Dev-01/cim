package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.BackupInfo;
import com.dxr.system.service.api.IBackup;

public class BackupAction extends BasalAction {

    private static final long serialVersionUID = 2232001756566560187L;

    @Autowired
    private IBackup backupMgr;

    private BackupInfo backupInfo;

    /**
     * 获取角色列表
     */
    public void getBackupPage() {
        try {
            String result = backupMgr.getBackupPage(page, rows, backupInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("BackupAction.getBackupPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 备份数据库
     */
    public void backup() {
        try {
            String result = backupMgr.backup();
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("BackupAction.backup() is error...", e);
            super.writeToView(new Message(false,
                    ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                    .toString());
        }
    }

    /**
     * 还原数据库
     */
    public void restore() {
        try {
            String result = backupMgr.restore(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("BackupAction.restore() is error...", e);
            super.writeToView(new Message(false,
                    ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                    .toString());
        }
    }
    
    /**
     * 删除备份记录/文件
     */
    public void deleteBackup() {
        try {
            String result = backupMgr.deleteBackup(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("BackupAction.deleteBackup() is error...", e);
            super.operateException();
        }
    }

    public BackupInfo getBackupInfo() {
        return backupInfo;
    }

    public void setBackupInfo(BackupInfo backupInfo) {
        this.backupInfo = backupInfo;
    }
}
