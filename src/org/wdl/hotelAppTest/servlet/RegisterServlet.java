package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelAppTest.service.UserService;
import org.wdl.hotelAppTest.service.UserServiceImpl;
import org.wdl.hotelTest.bean.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/app/register.do")
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
		System.out.println("=============register.do==========");
		request.setCharacterEncoding("utf-8");
		String  method = request.getParameter("method");
		String  loginName = request.getParameter("loginName");
		String  phone = request.getParameter("phone");
		String  email = request.getParameter("email");
		String  passWord = request.getParameter("passWord");
		String  okPassWord = request.getParameter("okPassWord");
		
		System.out.println("method:"+method+"   loginName:"+loginName+
				"   phone:"+phone+"  email:"+email+"  passWord:"+passWord+"  okPassWord:"+okPassWord);
		UserService userService = new UserServiceImpl();
		if(method != null && method.equals("ajaxLoginName")) {
			//校验用户输入的登录名是否已经存在
			//通过登录名查询用户
			User user = userService.findByLoginName(loginName);
			System.out.println("user:"+user);
			
			if(user != null) {
				//当前用户输入的登录名已经存在
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("您输入的登录名已存在，请重新输入！");
			}
			
		}else if(method != null && method.equals("submitTable")) {
			//提交注册表单
			if(passWord != null  && !passWord.equals(okPassWord)) {
				//两次输入的密码不一致
				request.setAttribute("message", "两次输入的密码不一致");
				request.getRequestDispatcher("/WEB-INF/jsp/app/register.jsp").forward(request, response);
			}else {
				User user = new User();
				user.setEmail(email);
				user.setLoginName(loginName);
				user.setPassword(passWord);
				user.setPhone(phone);
				
				//保存用户信息
				userService.save(user);
				System.out.println("=======注册成功============");
				response.sendRedirect(getServletContext().getContextPath()+"/app/login.do");
			}
		}else {
			request.getRequestDispatcher("/WEB-INF/jsp/app/register.jsp").forward(request, response);
		}
	}

}
