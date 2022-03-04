package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelSysTest.sys.service.FoodTypeService;
import org.wdl.hotelSysTest.sys.service.FoodTypeServiceImpl;
import org.wdl.hotelTest.bean.FoodType;

/**
 * Servlet implementation class FoodTypeServlet
 */
@WebServlet("/sys/foodType.action")
public class FoodTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=========菜系================");
		String disabled = request.getParameter("disabled");
		String keyword = request.getParameter("keyword");
		String method = request.getParameter("method");
		//菜系名字
		String foodTypeName = request.getParameter("foodTypeName");
		//菜系的ID
		String id = request.getParameter("id");
		System.out.println(" method:"+method+"   keyword:"+keyword+"   disabled:"+disabled +"  id:"+id+"  foodTypeName:"+foodTypeName);
		
		
		FoodTypeService foodTypeService = new FoodTypeServiceImpl();
		if(method != null && !method.equals("") && method.equals("list")) {
			//根据用户的输入和选择去查询菜系
			List<FoodType>  foodTypes=  foodTypeService.find(keyword,disabled);
			System.out.println("  数量："+foodTypes.size()+"  foodTypes:"+foodTypes);
			
			request.setAttribute("foodTypes", foodTypes);
			request.setAttribute("keyword", keyword);
			request.setAttribute("disabled", disabled);
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodTypeList.jsp").forward(request, response);
		}else if(method != null && !method.equals("") && method.equals("addPage")) {
			//跳转到菜系添加页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodTypeAdd.jsp").forward(request, response);
		}else if(method != null && !method.equals("") && method.equals("addSubmit")) {
			//根据菜系名字查找菜系
			FoodType  foodType = foodTypeService.findByFoodName(foodTypeName);
			
			String message = "";
			//如果菜系为null，表现用户输入的菜系名字目前数据库还没有，可保存到数据库
			if(foodType == null) {
				FoodType foodType2 = new FoodType();
				foodType2.setTypeName(foodTypeName);
				//保存菜系名到数据库
				foodTypeService.save(foodType2);
				message ="success";
			}else {
				//如果菜系不为null，在前端提示，当前菜系名称已存在，请重新输入
				message = "fail";
			}
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(message);
		}else if(method != null && !method.equals("") && method.equals("viewUpdate")) {
			//根据菜系的ID查找菜系，在前端展示
			FoodType  foodType = foodTypeService.findById(Integer.parseInt(id));
			System.out.println("更新 foodType："+foodType);
			
			request.setAttribute("foodType", foodType);
			//跳转到更新页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodTypeUpdate.jsp").forward(request, response);
		}else if(method != null && !method.equals("") && method.equals("updateSubmit")) {
			//根据菜系名字查找菜系
			FoodType  foodType = foodTypeService.findByFoodName(foodTypeName);
			
			String message = "";
			//如果菜系为null，表现用户输入的菜系名字目前数据库还没有，可更新到数据库
			if(foodType == null) {
				//根据ID查找菜系
				FoodType  foodType2 = foodTypeService.findById(Integer.parseInt(id));
				System.out.println("菜系更改前："+foodType2);
				foodType2.setTypeName(foodTypeName);
				System.out.println("菜系更改后："+foodType2);
				
				foodTypeService.update(foodType2);
				message ="success";
			}else {
				//如果菜系不为null，在前端提示，当前菜系名称已存在，请重新输入
				message = "fail";
			}
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(message);
		}else if(method != null && !method.equals("") && method.equals("update")) {
			//删除或者激活
			
			//根据ID查找菜系
			FoodType  foodType2 = foodTypeService.findById(Integer.parseInt(id));
			System.out.println("菜系更改前："+foodType2);
			foodType2.setDisabled(Integer.parseInt(disabled));
			System.out.println("菜系更改后："+foodType2);
			
			foodTypeService.update(foodType2);
			
			response.sendRedirect(getServletContext().getContextPath()+"/sys/foodType.action?method=list");
		}
	}

}
