package org.wdl.hotelTest.util;

public class ConstantUtil {
	//表示订单未付款
	public  static final Integer ORDER_NO_PAY = 0;
	
	//表示订单已付款
	public  static final Integer ORDER_PAY =1;
	
	//餐桌未使用
	public  static final Integer TABLE_NO_USE = 0;
	
	//餐桌正在使用
	public  static final Integer TABLE_USEING =1;
	
	//删除
	public  static final Integer DSABLED = 1;
	//未删
	public  static final Integer NO_DISABLED =0;
		
		
	//保存用户名和密码的session的名字
	public static final String SESSION_NAME = "session_user";
	
	public static final String COOKIE_NAME = "loginname_pass";
}
