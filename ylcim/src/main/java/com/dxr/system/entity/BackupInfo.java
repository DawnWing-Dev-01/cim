package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <数据库备份>
 * @author: w.xL
 * @date: 2018-3-21
 */
public class BackupInfo extends AbstractEntity{

    private static final long serialVersionUID = 322550127210719107L;

    
    /**
     * 备份路径
     */
    private String backupPath;

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }
}
