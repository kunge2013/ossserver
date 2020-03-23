package com.kframe.auth.service;

import com.kframe.common.IBaseService;
import com.kframe.common.RetResult;
import com.kframe.entity.UserInfo;

public interface IUserService extends IBaseService<UserInfo, Long> {
	/**
	 * 新增修改用户
	 * @param userInfo
	 * @return userInfo
	 */
	public RetResult<UserInfo> saveUser(UserInfo userInfo);
	
	
	/**
	 * 删除
	 * @param userInfo
	 * @return
	 */
	public void remove(UserInfo userInfo);
	
	
}
