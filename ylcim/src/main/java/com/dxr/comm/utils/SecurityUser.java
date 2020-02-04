package com.dxr.comm.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.dxr.comm.shiro.ShiroDbRealm;
import com.dxr.comm.shiro.ShiroUser;

/**
 * @description: <封装获取登录的用户>
 * @author: w.xL
 * @date: 2018-2-25
 */
public class SecurityUser {

    /**
     * 根据运行时环境，返回可用于调用代码的当前可访问的Subject。
     * @return
     */
    public static Subject getSubject() {
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    /**
     * 获取shiro的登录用户
     * @return
     */
    public static ShiroUser getLoginUser() {
        ShiroUser shiroUser = null;
        Subject subject = getSubject();
        if (subject != null) {
            shiroUser = (ShiroUser) subject.getPrincipal();
        }
        return shiroUser;
    }

    /**
     * 获取用户自定义Realm
     * @return
     */
    public static ShiroDbRealm getUserRealm() {
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils
                .getSecurityManager();
        ShiroDbRealm shiroDbRealm = (ShiroDbRealm) securityManager.getRealms()
                .iterator().next();
        return shiroDbRealm;
    }

    /**
     * 清除用户所有缓存 <br>
     * 身份认证/授权信息
     * @param loginName
     */
    public static void clearCache(String loginName) {
        ShiroDbRealm shiroDbRealm = getUserRealm();
        PrincipalCollection principals = new SimplePrincipalCollection(
                loginName, "ClearCache-ShiroDbRealm");
        shiroDbRealm.clearCache(principals);
    }

    /**
     * 清除用户身份认证缓存
     * @param loginName
     */
    public static void clearCachedAuthenticationInfo(String loginName) {
        ShiroDbRealm shiroDbRealm = getUserRealm();
        PrincipalCollection principals = new SimplePrincipalCollection(
                loginName, "ClearCache-ShiroDbRealm");
        shiroDbRealm.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存
     * @param loginName
     */
    public static void clearCachedAuthorizationInfo(String loginName) {
        ShiroDbRealm shiroDbRealm = getUserRealm();
        PrincipalCollection principals = new SimplePrincipalCollection(
                loginName, "ClearCache-ShiroDbRealm");
        shiroDbRealm.clearCachedAuthorizationInfo(principals);
    }
}
