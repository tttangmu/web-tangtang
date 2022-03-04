package org.wdl.hotelTest.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtils {

	public static void addCookie(String loginname, String password, int age, String cookieName, HttpServletResponse response, HttpServletRequest request) {
		
		StringBuffer  value = new StringBuffer();
		value.append(loginname);
		value.append("#");
		value.append(password);//admin#123456
		
		Cookie  cookie = getCookieByName(request,cookieName);
		if(cookie != null) {
			cookie.setValue(value.toString());
		}else {
			cookie = new Cookie(cookieName, value.toString());
		}
		
		//设置cookie的有效时间
		cookie.setMaxAge(age);
		//cookie的作用域 
		cookie.setPath(request.getContextPath());
		System.out.println("request.getContextPath():"+request.getContextPath());
		
		response.addCookie(cookie);
	}

	public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
		//从request中获取所有的cookie信息
		Cookie[]  cookies =request.getCookies();
		
		if(cookies != null && cookies.length >0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(ConstantUtil.COOKIE_NAME)) {
					return cookie;
				}
			}
		}
		return null;
		
	}

	//移除cookie
	public static void removeCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
		Cookie  cookie = getCookieByName(request, cookieName);
		
		if(cookie != null) {
			//设置cookie的生命周期
			cookie.setMaxAge(0);
			//设置cookie的作用域
			cookie.setPath(request.getContextPath());
			//将cookie响应出去
			response.addCookie(cookie);
		}
	}

}
