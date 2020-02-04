package com.dxr.comm.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @description: <shiro密码加密配置>
 * @author: w.xL
 * @date: 2018-2-23
 */
public class SimplePwdHash implements InitializingBean {
	
    /**
     * 算法名称
     */
    private String algorithmName;
	
    /**
     * hash次数
     */
    private int hashIterations;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
	}
	
	/**
     * 使用shiro的hash方式
     * @param algorithmName 算法
     * @param source 源对象
     * @param salt 加密盐
     * @param hashIterations hash次数
     * @return 加密后的字符
     */
	public String hashByShiro(Object source, Object salt) {
	    return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
	}
	
	public String getAlgorithmName() {
        return algorithmName;
    }
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    public int getHashIterations() {
        return hashIterations;
    }
    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }
}
