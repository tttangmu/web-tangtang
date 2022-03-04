package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelSysTest.sys.service.OrderDetailService;
import org.wdl.hotelSysTest.sys.service.OrderDetailServiceImpl;
import org.wdl.hotelSysTest.sys.service.OrderService;
import org.wdl.hotelSysTest.sys.service.OrderServiceImpl;
import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.OrderDetail;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/sys/orderDetail.action")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String buyNum = request.getParameter("buyNum");
		String id = request.getParameter("id");
		String foodIds = request.getParameter("foodIds");
		String orderId = request.getParameter("orderId");
		System.out.println("method:"+method+"  buyNum:"+buyNum+"  id:"+id+"  foodIds:"+foodIds+"  orderId:"+orderId);
		
		OrderDetailService orderDetailService= new OrderDetailServiceImpl();
		OrderService orderService= new OrderServiceImpl();
		if(method != null && method.equals("update")) {
			OrderDetail orderDetail = orderDetailService.findById(Integer.parseInt(id));
			orderDetail.setBuyNum(Integer.parseInt(buyNum));
			System.out.println("orderDetail:"+orderDetail);
			
			orderDetailService.update(orderDetail,2);
			request.getRequestDispatcher("/sys/order.action?method=list").forward(request, response);
			
		}else if(method != null && method.equals("delete")) {
			OrderDetail orderDetail = orderDetailService.findById(Integer.parseInt(id));
			orderDetail.setDisabled(1);
			System.out.println("orderDetail:"+orderDetail);
			
			orderDetailService.update(orderDetail,1);
			request.getRequestDispatcher("/sys/order.action?method=list").forward(request, response);
		}else if(method != null && method.equals("addFoodSubmit")) {
			String[] arrfoodIds = foodIds.split(",");
			System.out.println("arrfoodIds:"+arrfoodIds);
			
			
			orderDetailService.addFoods(arrfoodIds,Integer.parseInt(orderId));
			request.getRequestDispatcher("/sys/order.action?method=list").forward(request, response);
		}else if(method != null && method.equals("print")) {
			Order order = orderService.findById(Integer.parseInt(orderId));
			
			System.out.println("order:"+order);
			request.setAttribute("order", order);
			request.getRequestDispatcher("/WEB-INF/jsp/sys/printOrder.jsp").forward(request, response);
		}
		
		
	}

}
