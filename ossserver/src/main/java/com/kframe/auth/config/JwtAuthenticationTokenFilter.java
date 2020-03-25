package com.kframe.auth.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kframe.auth.JwtService;
import com.kframe.entity.UserInfo;
import com.kframe.repositorys.UserRepository;

/**
 * token过滤器来验证token有效性
 * 
 * @author fk
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.header}")
	private String tokenheader;
	
	@Resource
	private JwtService jwtService;
	@Resource
	private UserRepository userRepository;
	Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		LOGGER.info("url = {}", url);
		String auth = request.getHeader(this.tokenheader) == null ? fetchCookieVal(tokenheader, request) : request.getHeader(this.tokenheader);
		String username = "";
		UserInfo userinfo = null;
		
	 
	        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
	        String origin = request.getHeader("Origin");
	        if(origin != null && !origin.isEmpty()) {
	            response.setHeader("Access-Control-Allow-Origin", origin);
	        }
	 
	        // 自适应所有自定义头
	        String headers = request.getHeader("Access-Control-Request-Headers");
	        if(headers != null) {
	            response.setHeader("Access-Control-Allow-Headers", headers);
	            response.setHeader("Access-Control-Expose-Headers", headers);
	        }
	 
	        // 允许跨域的请求方法类型
	        response.setHeader("Access-Control-Allow-Methods", "*");
	        // 预检命令（OPTIONS）缓存时间，单位：秒
	        response.setHeader("Access-Control-Max-Age", "3600");
	        // 明确许可客户端发送Cookie，不允许删除字段即可
	        response.setHeader("Access-Control-Allow-Credentials", "true");

		LOGGER.info("headers  {}  , auth = {} ", request.getHeaderNames(), auth);
		if ("/oss/api/verifyCode".equalsIgnoreCase(url)) {
			chain.doFilter(request, response);//放行
			return;
		}
		if (!"/oss/api/login".equalsIgnoreCase(url) &&!auth.isEmpty()) {
			userinfo = jwtService.parseUserInfo(auth);
			username = userinfo.getUsername();
			LOGGER.info("Checking authentication for user {}.", username);
			if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				// 校验通过后放行
				if (jwtService.validateToken(auth, userinfo)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userinfo, null, userinfo.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					LOGGER.info("Authenticated user {}, setting security context", userinfo);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);//放行
	}


	/**
	 * 获取cookie 值
	 * @param key
	 * @param request
	 * @return
	 */
	private String fetchCookieVal(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if(key.equalsIgnoreCase(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}
}
