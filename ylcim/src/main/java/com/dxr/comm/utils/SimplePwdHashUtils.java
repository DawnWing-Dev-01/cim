package com.dxr.comm.utils;

import com.dxr.comm.shiro.SimplePwdHash;

/**
 * @description: <密码加密工具>
 * @author: w.xL
 * @date: 2018-2-23
 */
public class SimplePwdHashUtils {

    public static void main(String[] args) {
        SimplePwdHash sph = new SimplePwdHash();
        sph.setAlgorithmName("md5");
        sph.setHashIterations(1);
        String password = "111111";
        String salt = "U01";
        String hexPwd = sph.hashByShiro(password, salt);
        System.out.println(hexPwd);
        
        String hexPwd1 = sph.hashByShiro("111111", "402880e9622d8f1501622dd6fcc20000");
        System.out.println(hexPwd1);
    }
}