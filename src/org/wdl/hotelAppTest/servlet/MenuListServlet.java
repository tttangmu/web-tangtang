package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelAppTest.service.FoodService;
import org.wdl.hotelAppTest.service.FoodServiceImpl;
import org.wdl.hotelAppTest.service.FoodTypeService;
import org.wdl.hotelAppTest.service.FoodTypeServiceImpl;
import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.FoodType;

/**
 * Servlet implementation class MenuListServlet
 */
@WebServlet("/app/menuList.do")
public class MenuListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String foodTypeId = request.getParameter("foodTypeId");
		//① 查询所有未删除的菜系名字
		FoodTypeService foodTypeService = new FoodTypeServiceImpl();
		List<FoodType> foodTypes= foodTypeService.findAll();
		System.out.println("foodTypes:"+foodTypes);
		

		if(foodTypeId == null || foodTypeId.equals("")) {
			//② 默认查询所有菜系中第一个未删除菜系的菜品
			Integer  foodTypeIdInt = foodTypes.get(0).getId();
			foodTypeId = Integer.toString(foodTypeIdInt);
		}
		
		FoodService foodService = new FoodServiceImpl();
		List<Food> foods = foodService.findByFoodTypeId(Integer.parseInt(foodTypeId));
		System.out.println("foods:"+foods);
		
		request.setAttribute("foodTypes", foodTypes);
		request.setAttribute("foods", foods);
		request.getRequestDispatcher("/WEB-INF/jsp/app/menuList.jsp").forward(request, response);
	}

}
