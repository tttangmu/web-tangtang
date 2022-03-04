package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelAppTest.service.UserService;
import org.wdl.hotelAppTest.service.UserServiceImpl;
import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConstantUtil;
import org.wdl.hotelTest.util.CookieUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/app/login.do")
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
		//解决post请求，中文乱码问题
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		String remenber = request.getParameter("remenber");
		System.out.println("method:"+method+"   loginname:"+loginname+"   password:"+password+  "  remenber:"+remenber);
		
		
		if(method != null && method.equals("submitTable")) {
			//提交登录表单
			if(loginname != null && !loginname.equals("")
					&& password != null && !password.equals("")) {
				//去数据库查询，用户输入的用户名和密码是否存在，同时是未删除的状态
				UserService userService = new UserServiceImpl();
				User user = userService.findByLoginNameAndPass(loginname,password);
				System.out.println("user:"+user);
				if(user != null) {
					//保存在session中的数据是在整个浏览器中有效，默认30分钟有效，前提是保存数据到session后浏览器没有关闭
					//浏览器一关闭，保存在session中的数据马上失效
					request.getSession().setAttribute(ConstantUtil.SESSION_NAME, user);
					
					if(remenber != null && remenber.equals("reme")) {
						//记住密码一周   需要保存的信息：用户名、密码、保存的时间，cookie的的名字
						CookieUtils.addCookie(URLEncoder.encode(loginname, "utf-8"),URLEncoder.encode(password,"utf-8"),7*24*60*60*1000,ConstantUtil.COOKIE_NAME,response,request);
					}
					
					//登录成功，跳转到首页
					response.sendRedirect(getServletContext().getContextPath()+"/app/index.do");
				}else {
					//登录不成功
					request.setAttribute("message", "登录名或密码错误");
					//跳转到登录页面
					request.getRequestDispatcher("/WEB-INF/jsp/app/login.jsp").forward(request, response);
				}
			}
		}else {
			//跳转到登录页面
			request.getRequestDispatcher("/WEB-INF/jsp/app/login.jsp").forward(request, response);
		}
		
	}

}
