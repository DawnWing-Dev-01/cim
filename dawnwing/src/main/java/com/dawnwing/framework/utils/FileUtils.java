package com.dawnwing.framework.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(FileUtils.class);

    /**上传文件
     * @param file
     * @param fileFileName
     * @param fileType
     * @param dirStrPath
     * @return
     */
    public static String uploadFile(File file, String fileFileName,
            String fileType, String dirStrPath) {
        InputStream is = null;
        FileOutputStream os = null;
        File finalFile = null;
        try {
            // 创建目录
            File dirPath = new File(dirStrPath);
            // 判断目录
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            // 获取文件格式
            String fileSuffix = fileFileName.substring(fileFileName
                    .lastIndexOf(".") + 1);
            // 创建文件输入流
            is = new FileInputStream(file);
            // 生成的uuid全称文件名
            String fileFullName = UUID.randomUUID().toString().replace("-", "")
                    + "." + fileSuffix;
            // 最终上传后的文件
            finalFile = new File(dirPath.getPath(), fileFullName);
            // 文件输出流
            os = new FileOutputStream(finalFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            logger.error("uploadFile==>>到不到文件", e);
        } catch (IOException e) {
            logger.error("uploadFile==>>IO异常", e);
        } finally {
            IoUtils.unifyClose(os, is);
        }
        return finalFile.getPath();
    }

    /**
     * @param is
     * @param fileName
     * @param targetDir
     * @return
     */
    public static String uploadFile(InputStream is, String fileName,
            String targetDir) {
        FileOutputStream os = null;
        File finalFile = null;
        try {
            // 创建目录
            File dirPath = new File(targetDir);
            // 判断目录
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            // 最终上传后的文件
            finalFile = new File(dirPath.getPath(), fileName);
            // 文件输出流
            os = new FileOutputStream(finalFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            logger.error("uploadFile==>>到不到文件", e);
        } catch (IOException e) {
            logger.error("uploadFile==>>IO异常", e);
        } finally {
            IoUtils.unifyClose(os, is);
        }
        return finalFile.getPath();
    }

    // 文件下载
    public static InputStream getDownloadFile(String fileName, String filePath)
            throws Exception {
        // 如果下载文件名为中文，进行字符编码转换
        ServletActionContext.getResponse().setHeader(
                "Content-Disposition",
                "attachment;fileName="
                        + java.net.URLEncoder.encode(fileName, "UTF-8"));
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }

    /**
     * @Title: downloadFile
     * @Description 下载文件
     * @return void
     * @throws
     */
    public static void downloadFile(String downloadFile, String downloadName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        InputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        OutputStream output = null;
        try {
            File download = new File(downloadFile);
            inputStream = new FileInputStream(download);
            // 获得输出流
            output = response.getOutputStream();
            outputStream = new BufferedOutputStream(output);
            response.setContentType("application/octet-stream");
            downloadName = downloadName.replace(" ", "");
            //			response.setHeader("Content-Disposition", "attachment;filename="
            //					+ new String(downloadName.getBytes("UTF-8"), "ISO8859-1"));
            //		   - String fileName = URLEncoder.encode(downloadName, "UTF-8");  

            //          -  if (fileName.length() > 150) {  
            //                String guessCharset = "UTF-8"; /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/  
            //                fileName = new String(downloadName.getBytes(guessCharset), "ISO8859-1");   
            //            }   
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? downloadName.getBytes()
                    : downloadName.getBytes("UTF-8"); // name.getBytes("UTF-8")处理safari的乱码问题   
            downloadName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码   
            response.setHeader("Content-disposition",
                    String.format("attachment; filename=\"%s\"", downloadName));
            //           -   response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            IoUtils.unifyClose(inputStream, output);
        }
    }

    /**
     * @Title: showImage
     * @Description 下载图片
     * @return void
     * @throws
     */
    public static void showImage(String imgpath, String imgName,
            HttpServletResponse response) throws Exception {
        InputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        OutputStream output = null;
        try {
            File imgload = new File(imgpath);
            inputStream = new FileInputStream(imgload);
            // 获得输出流
            output = response.getOutputStream();
            outputStream = new BufferedOutputStream(output);
            response.setContentType("image/*");
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            IoUtils.unifyClose(inputStream, output);
        }
    }

    /**
     * @param filePath
     * @return
     * @remark 获取文件的大小
     */
    public static float getFileSize(String filePath) {
        File file = new File(filePath);
        return getFileSize(file);
    }

    /**
     * @param file
     * @return
     * @remark 获取文件的大小
     */
    public static float getFileSize(File file) {
        FileChannel fc = null;
        float fileSize = 0;
        try {
            if (file.exists() && file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                fileSize = (float) fc.size();
            } else {
                logger.error("file doesn't exist or is not a file");
            }
        } catch (Exception e) {
            logger.error("getDownloadFileSize", e);
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
        }
        return fileSize;
    }

    /**
     * @param file
     * @return
     * @remark 获取文件的大小
     */
    public static int getSize(File file) {
        FileChannel fc = null;
        int fileSize = 0;
        try {
            if (file.exists() && file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                fileSize = (int) fc.size() / 1024;
            } else {
                logger.error("file doesn't exist or is not a file");
            }
        } catch (Exception e) {
            logger.error("getDownloadFileSize", e);
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (Exception e) {
                    logger.error("Exception", e);
                }
            }
        }
        return fileSize;
    }

    /**
     * @Title: delFile
     * @Description 删除文件
     * @param filePath
     *            文件的路径字符串
     * @throws
     */
    public static void delFile(String filePath) {
        delFile(new File(filePath));
    }

    /**
     * @Title: delFile
     * @Description 删除文件
     * @param file
     *            具体的文件
     * @throws
     */
    public static void delFile(File file) {
        try {
            if (file != null && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("delFile", e);
        }
    }
}
