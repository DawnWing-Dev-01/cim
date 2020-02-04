package com.dxr.webui.tools;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * Druid生成加密的数据库密码
 * 
 * @author wxl
 *
 */
public class DuridConfigTools {

	private static final String DEFAULT_MYSQL_PASSWORD = "123456";
	
	public static void main(String[] args) {
		try {
			String cipher = ConfigTools.encrypt(DEFAULT_MYSQL_PASSWORD);
			System.out.println(cipher);
			/*String decrypt = ConfigTools.decrypt(cipher);
			System.out.println(decrypt);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
