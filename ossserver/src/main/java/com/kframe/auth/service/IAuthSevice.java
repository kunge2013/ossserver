package com.kframe.auth.service;

import java.io.Serializable;

import com.kframe.common.RetResult;
import com.kframe.entity.UserInfo;
/**
 * 用户认证
 * @author fk
 *
 */
import com.kframe.entity.VerifyCode;
import com.kframe.oss.bean.LoginBean;
public interface IAuthSevice {
	
	/**
	 * 用户信息
	 * @param userinfo
	 * @return 返回token
	 */
	public RetResult<String> login(UserInfo userinfo);
	
	/**
	 * @param userinfo 用户信息
	 * @param verifyCode 验证码
	 * @return
	 */
	public RetResult<String> login(LoginBean bean);
	
	/**
	 * 校验token
	 * @param token
	 * @param id
	 * @return
	 */
	public RetResult checkTokenExists(String token, Serializable id);
	
	/**
	 * 短信注册
	 * @param mobile
	 * @param verifycode
	 * @param nation
	 * @return
	 */
	public RetResult<UserInfo> register(int nation, String mobile, String verifycode);

	/**
	 * 生成验证码
	 * @return
	 */
	public RetResult<VerifyCode> generVerifyCode();
	
}
