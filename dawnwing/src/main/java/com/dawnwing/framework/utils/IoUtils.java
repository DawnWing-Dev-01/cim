package com.dawnwing.framework.utils;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

/**
 * Io 工具类
 * 
 * @author wenxiaolong
 *
 */
public class IoUtils extends StreamUtils{

	private static final Logger logger = LoggerFactory.getLogger(IoUtils.class);

	/**
	 * 统一关闭Io流
	 * @param closeable
	 */
	public static void unifyClose(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					logger.error("close io arise exception, continue close next...", e);
					continue;
				}
			}
		}
	}
}
