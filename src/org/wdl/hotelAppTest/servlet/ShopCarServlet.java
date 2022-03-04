package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShopCarServlet
 */
@WebServlet("/app/shopCar.action")
public class ShopCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopCarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("=============shopCar.action================");
		String method = request.getParameter("method");
		String foodId = request.getParameter("foodId");
		String buyNum = request.getParameter("buyNum");
		String dinnerTableId = request.getParameter("dinnerTableId");
		Integer foodIdInt = Integer.parseInt(foodId);
		
		System.out.println("method:"+method+"dinnerTableId:"+dinnerTableId+"  foodId:"+foodId+" buyNum:"+buyNum);
		
		HttpSession  session = request.getSession();
		//根据餐桌的id获取其购物车
		Map<Integer, Integer>  shopCar = (Map<Integer, Integer>) session.getAttribute(dinnerTableId);
		
		//添加商品到购物车
		if(method != null && method.equals("add")) {
			if(shopCar != null) {
				//当前餐桌的购物车已经有商品
				//拿到当前餐桌购物车中所有的菜品id
				Set<Integer> foodIds= shopCar.keySet();
				System.out.println("foodIds:"+foodIds);
				//判断当前购物车中，是否已经有当前需要加入到购物车中的商品
				if(foodIds.contains(foodIdInt)) {
					//表示当前餐桌中已经有当前菜品，需在原购买数量的基础上+1
					Integer  buyNum2 = shopCar.get(foodIdInt);
					shopCar.put(foodIdInt, buyNum2+1);
				}else {
					//第一次加入该商品到当前餐桌的购物车
					shopCar.put(foodIdInt, 1);
				}
				
			}else {
				//新建购物车  key为商品id  value为购买数量
				shopCar = new HashMap<>();
				shopCar.put(foodIdInt, 1);
				session.setAttribute(dinnerTableId, shopCar);
			}
			System.out.println("getServletContext().getContextPath():"+getServletContext().getContextPath());
			
			//request.getRequestDispatcher("/app/menu.action?id="+dinnerTableId).forward(request, response);
			
		}else if(method != null && method.equals("update")) {
			//更新购物车
			shopCar.put(foodIdInt, Integer.parseInt(buyNum));
			System.out.println("修改后："+shopCar);
			
		}else if(method != null && method.equals("delete")) {
			//删除餐桌中的某个菜品
			//根据餐桌的id获取其购物车
			shopCar.remove(foodIdInt);
		}
		response.sendRedirect(getServletContext().getContextPath()+"/app/menu.action?id="+dinnerTableId);
	}

}
