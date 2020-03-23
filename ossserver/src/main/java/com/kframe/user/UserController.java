package com.kframe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kframe.auth.service.UserService;
import com.kframe.common.PageInfo;
import com.kframe.common.RetResult;
import com.kframe.entity.UserInfo;

@RestController("user")
public class UserController {

	@Autowired
	private UserService userService;
	/**
	 * 保存 修改
	 * @param userinfo
	 * @return
	 */
	@PostMapping(value = "/user/save")
	public RetResult<UserInfo> saveUserInfo(@RequestBody UserInfo userinfo) {
		return userService.saveUser(userinfo);
	}
	
	/**
	 * 删除
	 * @param userInfo
	 * @return
	 */
	@DeleteMapping(value = "/user/remove")
	public RetResult remove(@RequestBody UserInfo userInfo) {
		userService.remove(userInfo);
		return RetResult.success();	
	}
	
	
	@GetMapping("/user/page")
	public RetResult page() {
		UserInfo userinfo = new UserInfo();
		userinfo.setUsername("admin");
		return RetResult.success(userService.queryPage(new PageInfo(), userinfo));
	}
}
