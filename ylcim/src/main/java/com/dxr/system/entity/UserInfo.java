package com.dxr.system.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * 用户实体
 * 
 * @author w.xL
 */
public class UserInfo extends AbstractEntity{

	private static final long serialVersionUID = 539785183002075693L;

	/**
	 * 登录系统的账号
	 */
	private String username;
	
	/**
	 * 登录系统的密码
	 */
	private String password;
	
	/**
	 * 生日
	 */
	private Date birthday;
	
	/**
	 * 性别：[1]男; [2]女;
	 */
	private Integer gender;
	
	/**
	 * 组织机构Id
	 */
	private String orgId;
	
	/**
	 * 电话
	 */
	private String iphone;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 住址
	 */
	private String address;
	
	/**
	 * 微信加密账号
	 */
	private String wechatOpenId;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }
}
