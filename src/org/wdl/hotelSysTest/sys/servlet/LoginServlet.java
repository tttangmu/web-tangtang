package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelSysTest.sys.service.UserService;
import org.wdl.hotelSysTest.sys.service.UserServiceImpl;
import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConstantUtil;
import org.wdl.hotelTest.util.CookieUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sys/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======跟登录相关的请求=========");
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		
		String remenber = request.getParameter("remenber");
		System.out.println("method:"+method+"   loginName:"+loginname+"   password:"+password+"  remenber:"+remenber);
		
		UserService  userService = new UserServiceImpl();
		if(method != null && method.equals("submitTable")) {
			//提交登录表单  去数据库查询用户输入的登录名和密码是否存在
			if(loginname != null && !loginname.equals("")
					&&password != null && !password.equals("")) {
				
				User  user = userService.findByLoginNameAndPass(loginname,password);
				System.out.println("user:"+user);
				if(user != null) {
					
					if(remenber != null && remenber.equals("reme")) {
						//记住账户一周   需传递cookie的名字，要保存的用户名和密码，有效时间（毫秒）
						CookieUtils.addCookie(URLEncoder.encode(loginname, "utf-8"),URLEncoder.encode(password, "utf-8"),7*24*60*1000,ConstantUtil.COOKIE_NAME,response,request);
					}
					//session默认半个小时在整个项目中有效
					request.getSession().setAttribute(ConstantUtil.SESSION_NAME, user);
					//登录成功。跳转到首页
					response.sendRedirect(getServletContext().getContextPath()+"/sys/index.action");
				}else {
					request.setAttribute("message", "用户名或密码错误，请重新输入！");
					request.getRequestDispatcher("/WEB-INF/jsp/sys/login.jsp").forward(request, response);
				}
			}
			
		}else {
			//跳转到登录页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/login.jsp").forward(request, response);
		}
	}

}
