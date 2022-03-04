package org.wdl.hotelAppTest.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelAppTest.service.DinnerTableService;
import org.wdl.hotelAppTest.service.DinnerTableServiceImpl;
import org.wdl.hotelTest.bean.DinnerTable;


/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/app/index.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("==========index.do===============");
		
		request.setCharacterEncoding("utf-8");
		//通过标签的name属性值获取前端传递过来的参数
		String  method = request.getParameter("method");
		String  tableName = request.getParameter("tableName");
		String  tableStatus = request.getParameter("tableStatus");
		System.out.println("method:"+method+"　　tableName："+tableName+"    tableStatus:"+tableStatus);
		
		//查询未使用的餐桌
		DinnerTableService dinnerTableService = new DinnerTableServiceImpl();
		List<DinnerTable> dinnerTables= null;
		if(method != null  && !method.equals("") && method.equals("submitTable")) {
			//表明是通过点击查看餐桌按钮来查询
			 dinnerTables= dinnerTableService.findDinnerTables(tableStatus,tableName);
		}else {
			//项目一运行就查看未使用的餐桌
			//未使用的餐桌
			tableStatus = "0";
			dinnerTables= dinnerTableService.findDinnerTables(tableStatus,null);
		}
		System.out.println("dinnerTables:"+dinnerTables);
		request.setAttribute("tableStatus", tableStatus);
		request.setAttribute("dinnerTables", dinnerTables);
		//请求链没有断开的跳转，可在下一个servlet或者jsp中获取保存在request中的数据
		request.getRequestDispatcher("/WEB-INF/jsp/app/index.jsp").forward(request, response);
	}

}
