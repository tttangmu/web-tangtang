package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wdl.hotelAppTest.service.DinnerTableService;
import org.wdl.hotelAppTest.service.DinnerTableServiceImpl;
import org.wdl.hotelAppTest.service.OrderService;
import org.wdl.hotelAppTest.service.OrderServiceImpl;
import org.wdl.hotelAppTest.service.UserService;
import org.wdl.hotelTest.bean.DinnerTable;
import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConstantUtil;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/app/order.action")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dinnerTableId = request.getParameter("dinnerTableId");
		String method = request.getParameter("method");
		//获取总金额
		String total = request.getParameter("total");
		//订单id
		String  orderId = request.getParameter("orderId");
		System.out.println("method:"+method+"   dinnerTableId:"+dinnerTableId+"  total:"+total+"  orderId:"+orderId);
		
		OrderService orderService = new OrderServiceImpl();
		if(method != null && method.equals("order")) {
			//下单  将购物车中的信息保存到订单表和订单明细表
			HttpSession  session = request.getSession();
			User user = (User) session.getAttribute(ConstantUtil.SESSION_NAME);
			//根据餐桌的id获取其购物车
			Map<Integer, Integer>  shopCar = (Map<Integer, Integer>) session.getAttribute(dinnerTableId);
			
			if(shopCar != null && !shopCar.isEmpty() ) {
				System.out.println("===购物车不为null ======");
				//保存数据到数据库
				orderService.order(Integer.parseInt(dinnerTableId),shopCar,total,user.getId());
				
				//删除对应餐桌购物车中的商品
				session.removeAttribute(dinnerTableId);
				
				//跳转到订单详情页面  根据餐桌id查询该餐桌所有未删除的订单
				//订单表：订单id  订单编号   下单时间  总金额
				//菜品表：菜品名  菜品价格
				//订单明细表：购买数量  折后价（菜品价格*购买时折扣）
				//默认菜品价格不会变，只改折扣
				List<Order>  orders = orderService.findByTableId(Integer.parseInt(dinnerTableId),user.getId());
				System.out.println("orders:"+orders);
				
				//当前订单是哪一个餐桌的
				DinnerTableService dinnerTableService = new DinnerTableServiceImpl();
				DinnerTable dinnerTable = dinnerTableService.findById(Integer.parseInt(dinnerTableId));
				
				request.setAttribute("orders", orders);
				request.setAttribute("dinnerTable", dinnerTable);
				request.getRequestDispatcher("/WEB-INF/jsp/app/orderItem.jsp").forward(request, response);
			}else {
				System.out.println("===购物车为null ======");
				//购物车中没有商品，回到点餐页面
				response.sendRedirect(getServletContext().getContextPath()+"/app/menu.action?id="+dinnerTableId);
			}
			
		}else if(method != null && method.equals("pay")) {
			//付款  订单的状态orderStatus=1   付款时间
			//餐桌的状态-没有使用
			Order order = orderService.findById(Integer.parseInt(orderId));
			System.out.println("order改前："+order);
			
			order.setOrderStatus(ConstantUtil.ORDER_PAY);
			
			System.out.println("order改后："+order);
			
			orderService.pay(order);
			response.sendRedirect(getServletContext().getContextPath()+"/app/index.do");
		}else if(method != null && method.equals("delete")) {
			//取消订单需做一下两个点
			//①将订单表中的disabled=1删除      ②将对于餐桌tableStaus=0未使用
			Order order = orderService.findById(Integer.parseInt(orderId));
			System.out.println("order改前："+order);
			
			order.setDisabled(ConstantUtil.DSABLED);
			
			System.out.println("order改后："+order);
			orderService.deleteOrder(order);
			
			response.sendRedirect(getServletContext().getContextPath()+"/app/index.do");
		}else if(method != null && method.equals("list")) {
			HttpSession  session = request.getSession();
			User user = (User) session.getAttribute(ConstantUtil.SESSION_NAME);
			//查询所有的订单
			List<Order>  orders = orderService.findAll(user.getId());
			System.out.println("查询所有订单："+orders);
			
			request.setAttribute("orders", orders);
			
			request.getRequestDispatcher("/WEB-INF/jsp/app/orderList.jsp").forward(request, response);
		}
	}

}




