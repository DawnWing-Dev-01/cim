package com.dawnwing.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dawnwing.framework.core.ServiceException;


/**
 * @ClassName: ImageUtils
 * @Description: 图片工具类
 * @author wxl 
 * @email 694231485@QQ.COM
 * @date 2015-12-22 下午4:43:13
 * @version V1.0
 */ 
public class ImageUtils {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	
	/**
	 * @Title: getImageByteArray
	 * @Description 将文件转换成二进制(字节数组)
	 * @return byte[]  
	 * @throws
	 */ 
	public static byte[] getImageByteArray(File imageFile) throws Exception{
		BufferedImage bi = null;
		byte[] imgBytes = null;
		try {
			bi = ImageIO.read(imageFile);      
			ByteArrayOutputStream baos = new ByteArrayOutputStream();      
			ImageIO.write(bi, "jpeg", baos);      
			imgBytes = baos.toByteArray();
		} catch (IOException e) {
			logger.error("ImageUtils====>>>getImageByteArray", e);
			throw new ServiceException("图片转换失败!");
		} finally {
			imageFile.delete();
		}
		return imgBytes;
	}
	
	/**
	 * @throws AlertException 
	 * @Title: getImageStr
	 * @Description 将图片转换为Base64加密后的字符串
	 * @return String  
	 * @throws
	 */ 
	public static String getImageStr(String imgFile) throws ServiceException {
		InputStream is = null;
		byte[] data = null;
		try {
			is = new FileInputStream(imgFile);
			data = new byte[is.available()];
			is.read(data);
			is.close();
		} catch (IOException e) {
			logger.error("ImageUtils====>>>getImageStr", e);
			throw new ServiceException("图片转换失败!");
		}
		return Base64Utils.encode(data);
	}
	
	/**
	 * @Title: showImage
	 * @Description 将图片以输出流的方式展现在网页上
	 * @return void  
	 * @throws
	 */ 
	public static void showImage(File imageFile , HttpServletResponse response) throws Exception{
		OutputStream outputStream = null;
		FileInputStream fileInputStream = null;
		try{
			response.setContentType("image/*");
			outputStream = response.getOutputStream();
			if(imageFile.isFile()){
				fileInputStream = new FileInputStream(imageFile);
				int length = fileInputStream.available();
				byte[] bytes = new byte[length];
				fileInputStream.read(bytes);
				outputStream.write(bytes);
			}
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e) {
				
			}
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (Exception e) {
				
			}
		}
	}
}
