package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelSysTest.sys.service.UserService;
import org.wdl.hotelSysTest.sys.service.UserServiceImpl;
import org.wdl.hotelTest.bean.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/sys/register.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		String loginname = request.getParameter("loginname");
		
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String passWord = request.getParameter("passWord");
		
		System.out.println("method:"+method+"   loginName:"+loginname+"  phone:"+phone
				+"   email:"+email+"   passWword:"+passWord);
		
		UserService  userService = new UserServiceImpl();
		if(method != null && !method.equals("") && method.equals("ajaxLoginName")) {
			//校验登录名是否已经存在
			//根据用户名查询用户
			User user = userService.findByLoginNameAndPass(loginname, null);
			System.out.println("user:"+user);
			
			if(user != null) {
				response.setCharacterEncoding("utf-8");
				//当前用户输入的登录名已经存在
				PrintWriter  printWriter = response.getWriter();
				printWriter.print("您输入的登录名已存在，请重新输入！");
				
				//刷新
				printWriter.flush();
				//关闭
				printWriter.close();
			}
		}else if(method != null && !method.equals("") && method.equals("submitTable")) {
			//提交注册表单
			System.out.println("====提交注册表单==========");
			
			//保存用户输入的信息到数据库
			User user = new User();
			user.setEmail(email);
			user.setLoginName(loginname);
			user.setPassword(passWord);
			user.setPhone(phone);
			
			userService.save(user);
			request.getRequestDispatcher("/WEB-INF/jsp/sys/login.jsp").forward(request, response);
		}else {
			System.out.println("===================注册===========");
			request.getRequestDispatcher("/WEB-INF/jsp/sys/register.jsp").forward(request, response);
		}
		
	}

}
