package com.kframe.oss.bean;

import org.springframework.beans.BeanUtils;

import com.kframe.entity.UserInfo;

public class LoginBean extends UserInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7769949837514805021L;
	
	
	private String verifycode;

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
	/**
	 * 拷贝对象
	 * @return
	 */
	public UserInfo copy() {
		UserInfo userinfo = new UserInfo();
		BeanUtils.copyProperties(this, userinfo);
		return userinfo;
	}
}
