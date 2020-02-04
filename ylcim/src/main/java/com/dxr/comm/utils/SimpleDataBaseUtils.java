package com.dxr.comm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.config.ConfigTools;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.utils.IoUtils;
import com.dawnwing.framework.utils.StringUtils;

/**
 * @description: <数据库工具类>
 * @author: w.xL
 * @date: 2018-3-21
 */
public class SimpleDataBaseUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(SimpleDataBaseUtils.class);

    /**
     * 数据库安装目录
     */
    private String installDir;

    private String serviceAddress;

    private String dbName;

    private String username;

    private String password;

    /**
     * 数据库备份
     * // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。
       // 注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行   
     * @param backupDir
     * @param fileName
     * @param targetDir
     * @throws ServiceException
     */
    public void backup(String targetDir, String fileName)
            throws ServiceException {
        InputStream in = null;
        InputStreamReader inReader = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        OutputStreamWriter writer = null;
        try {
            logger.info("------------------------- 开始备份   -------------------------");
            String cmd = builderCmd("mysqldump");
            logger.info("Runtime run cmd: " + cmd);
            // cmd命令在后台执行，没有命令窗口出现或者一闪而过的情况
            Process process = Runtime.getRuntime().exec(cmd);
            // 控制台的输出信息作为输入流
            in = process.getInputStream();
            // 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码   
            inReader = new InputStreamReader(in, "utf-8");

            StringBuffer sb = new StringBuffer();
            // 组合控制台输出信息字符串   
            br = new BufferedReader(inReader);
            String inStr = null;
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            String outStr = sb.toString();
            if (StringUtils.isEmpty(outStr)) {
                throw new ServiceException("备份失败!");
            }

            // 要用来做导入用的sql目标文件：
            File dirPath = new File(targetDir);
            if (!dirPath.exists()) {
                // 目录不存在, 创建目录文件夹
                dirPath.mkdirs();
            }
            fos = new FileOutputStream(new File(dirPath.getPath(), fileName));
            writer = new OutputStreamWriter(fos, "utf-8");
            writer.write(outStr);
            writer.flush();
        } catch (Exception e) {
            logger.error("备份失败!", e);
            throw new ServiceException("备份失败!", e);
        } finally {
            IoUtils.unifyClose(writer, fos, br, inReader, in);
        }
        logger.info("------------------------- 备份成功   -------------------------");
    }

    private String builderCmd(String opt) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(installDir + "bin\\");
        cmd.append(opt);
        cmd.append(" -h " + serviceAddress);
        cmd.append(" -u " + username);
        cmd.append(" -p" + password);
        cmd.append(" " + dbName);
        return cmd.toString();
    }

    public void restore(String restoreDir) throws ServiceException {
        OutputStream out = null;
        BufferedReader br = null;
        OutputStreamWriter writer = null;

        try {
            logger.info("------------------------- 开始还原   -------------------------");
            String cmd = builderCmd("mysql");
            // cmd命令在后台执行，没有命令窗口出现或者一闪而过的情况
            Process process = Runtime.getRuntime().exec(cmd);
            //控制台的输入信息作为输出流   
            out = process.getOutputStream();
            StringBuffer sb = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    restoreDir), "utf-8"));
            String inStr = null;
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            String outStr = sb.toString();

            writer = new OutputStreamWriter(out, "utf-8");
            writer.write(outStr);
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免   
            writer.flush();
        } catch (Exception e) {
            logger.error(dbName + "还原失败!", e);
            throw new ServiceException("还原失败!", e);
        } finally {
            // 别忘记关闭输入输出流
            IoUtils.unifyClose(writer, br, out);
        }
        logger.info("------------------------- 还原成功   -------------------------");
    }

    public String getInstallDir() {
        return installDir;
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = ConfigTools.decrypt(password);
        } catch (Exception e) {
            logger.error("ConfigTools.decrypt() is error...", e);
        }
    }
}