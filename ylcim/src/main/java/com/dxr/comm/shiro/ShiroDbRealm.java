package com.dxr.comm.shiro;

import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.service.api.IRole;
import com.dxr.system.service.api.IUser;

/**
 * @description: <自己实现shiro权限认证>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory
            .getLogger(ShiroDbRealm.class);

    @Autowired
    private IUser userMgr;

    @Autowired
    private IRole roleMgr;

    public ShiroDbRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroUser.getRoles());
        info.addStringPermissions(shiroUser.getUrlSet());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authToken) throws AuthenticationException {
        logger.info("------------------- shiro login authentication begin -------------------");
        SimpleAuthenticationInfo simpleAuth = null;
        UserInfo userInfo = null;
        ShiroUser shiroUser = null;
        try {
            UsernamePasswordToken token = (UsernamePasswordToken) authToken;
            UserInfo loginUser = new UserInfo();
            loginUser.setUsername(token.getUsername());
            userInfo = userMgr.findUser(loginUser);
            // 账号不存在
            if (userInfo == null || userInfo.isEmpty()) {
                return null;
            }
            // 账号已注销
            if (userInfo.getStatus() == 0) {
                throw new DisabledAccountException("账号未启用或账号已注销！");
            }

            // 读取用户的url和角色
            Map<String, Set<String>> resourceMap = roleMgr
                    .findResourceMapByUserId(userInfo.getId());
            Set<String> urls = resourceMap.get("urls");
            Set<String> roles = resourceMap.get("roles");
            shiroUser = new ShiroUser(userInfo.getId(), userInfo.getUsername(),
                    userInfo.getName(), urls);
            shiroUser.setRoles(roles);
            shiroUser.setUserInfo(userInfo);

            // 认证缓存信息
            simpleAuth = new SimpleAuthenticationInfo(shiroUser, userInfo
                    .getPassword().toCharArray(), ShiroByteSource.of(userInfo
                    .getId()), getName());
        } catch (ServiceException e) {
            logger.error("ShiroDbRealm.doGetAuthenticationInfo() is error", e);
        } finally {
            logger.info("-------------------  shiro login authentication end  -------------------");
        }
        return simpleAuth;
    }

    /**
     * 清除用户所有缓存
     * 身份认证/授权信息
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清除用户身份认证缓存
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}
