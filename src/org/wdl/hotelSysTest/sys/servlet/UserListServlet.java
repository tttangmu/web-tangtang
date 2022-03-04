package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wdl.hotelSysTest.sys.service.UserService;
import org.wdl.hotelSysTest.sys.service.UserServiceImpl;
import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConstantUtil;

/**
 * Servlet implementation class UserModelServlet
 */
@WebServlet("/sys/userList.action")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		String method = request.getParameter("method");
		String disabled = request.getParameter("disabled");
		String id = request.getParameter("id");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		System.out.println("  searchType:"+searchType+"  keyword:"+keyword+"   method:"+method+"   disabled："+disabled+"   id:"+id);
		System.out.println("loginName:"+loginName+"  password:"+password+"  phone:"+phone+" email:"+email);
		
		HttpSession  session = request.getSession();
		User userSession = (User) session.getAttribute(ConstantUtil.SESSION_NAME);
		UserService userService = new UserServiceImpl();
		if(method != null & method.equals("list")) {
			//查询宿舍管理员
			List<User>  users = userService.find(searchType,keyword,disabled);
			System.out.println("users:"+users);
			
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword", keyword);
			request.setAttribute("disabled", disabled);
			request.setAttribute("users", users);
			
			request.getRequestDispatcher("/WEB-INF/jsp/sys/userList.jsp").forward(request, response);
		}else if(method != null & method.equals("update")) {
			User  user = userService.findById(Integer.parseInt(id));
			System.out.println("user删除或激活查询:"+user);
			user.setDisabled(Integer.parseInt(disabled));
			userService.update(user);
			
			response.sendRedirect(getServletContext().getContextPath()+"/sys/userList.action?method=list");
		}else if(method != null & method.equals("addPage")) {
			request.getRequestDispatcher("/WEB-INF/jsp/sys/userAdd.jsp").forward(request, response);
		}else if(method != null & method.equals("ajaxloginName")) {
			User user = userService.findByLoginNameAndPass(loginName, null);
			System.out.println("user:"+user);
			if(user != null) {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("当前用户名已存在，请重新输入！");
			}
		}else if(method != null & method.equals("addSubmit")) {
			if(id != null && !id.equals("")) {
				System.out.println("id:"+id);
				//更新
				User  user = userService.findById(Integer.parseInt(id.trim()));
				user.setPassword(password);
				user.setEmail(email);
				user.setPhone(phone);
				
				userService.update(user);
			}else {
				User user = new User();
				user.setLoginName(loginName);
				user.setPassword(password);
				user.setEmail(email);
				user.setPhone(phone);
				user.setCreateUserId(userSession.getId());
				
				userService.save(user);
				
			}
			response.sendRedirect(getServletContext().getContextPath()+"/sys/userList.action?method=list");
		}else if(method != null & method.equals("viewUpdate")) {
			User  user = userService.findById(Integer.parseInt(id));
			System.out.println("user更新查询:"+user);
			
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/jsp/sys/userUpdate.jsp").forward(request, response);
		}
		
	}

}
