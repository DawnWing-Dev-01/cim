package com.dawnwing.framework.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {

	private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	/**
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			return str;
		}
	}

	/**利用MD5进行加密
	 * @param str  待加密的字符串
	 * @return  加密后的字符串
	 * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException  
	 */
	public static String encoderByMd5(String str) {
		String newstr = null;
		try {
			//确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			Base64 base64 = new Base64();
			//加密后的字符串
			byte[] ecByte = base64.encode(md5.digest(str.getBytes("utf-8")));
			newstr = new String(ecByte);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		}
		return newstr;
	}

	/**
	 * 使用方法
	 */
	public static void useingMethod(){
		String md5 = getMD5("123456");
	    System.out.println(md5);
	    String ecMd5 = encoderByMd5("123456");
	    System.out.println(ecMd5);
	}
	
	public static void main(String[] args) {
	    useingMethod();
    }
}
