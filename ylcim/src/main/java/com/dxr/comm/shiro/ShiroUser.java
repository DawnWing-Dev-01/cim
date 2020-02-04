package com.dxr.comm.shiro;

import java.io.Serializable;
import java.util.Set;

import com.dxr.system.entity.UserInfo;

/**
 * @description: <自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息>
 * @author: w.xL
 * @date: 2018-2-21
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -7977344000786366713L;

    private String userId;
    private final String loginName;
    private String realName;
    private String name;
    private Set<String> urlSet;
    private Set<String> roles;
    private UserInfo userInfo;

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(String userId, String loginName, String name,
            Set<String> urlSet) {
        this.userId = userId;
        this.loginName = loginName;
        this.name = name;
        this.urlSet = urlSet;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getLoginName() {
        return loginName;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        setRealName(userInfo.getName());
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
