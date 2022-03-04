package org.wdl.hotelSysTest.sys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wdl.hotelSysTest.sys.service.DinnerTableService;
import org.wdl.hotelSysTest.sys.service.DinnerTableServiceImpl;
import org.wdl.hotelTest.bean.DinnerTable;

/**
 * Servlet implementation class DinnerTableServlet
 */
@WebServlet("/sys/dinnerTable.action")
public class DinnerTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DinnerTableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("========/sys/dinnerTable.action=============");
		
		request.setCharacterEncoding("utf-8");
		//通过name属性值获取前端传递的参数
		String keyword = request.getParameter("keyword");
		String tableStatus = request.getParameter("tableStatus");
		String disabled = request.getParameter("disabled");
		String method = request.getParameter("method");
		System.out.println("method:"+method+"  keyword:"+keyword+"   tableStatus:"+tableStatus+"  disabled:"+disabled);
		
		//更新需要的参数
		String   id = request.getParameter("id");
		String  tableName = request.getParameter("tableName");
		System.out.println("id:"+id+"  tableName:"+tableName);
		
		DinnerTableService dinnerTableService = new DinnerTableServiceImpl();
		if(method != null && !method.equals("") && method.equals("list")) {
			//查询餐桌
			List<DinnerTable>  dinnerTables = dinnerTableService.find(keyword,tableStatus,disabled);
			System.out.println("dinnerTables:"+dinnerTables);
			//保存所有的餐桌
			request.setAttribute("dinnerTables", dinnerTables);
			request.setAttribute("keyword", keyword);
			request.setAttribute("tableStatus", tableStatus);
			request.setAttribute("disabled", disabled);
			//重定向不可在下一个JSP中获取保存中request中的数据
			//response.sendRedirect("/WEB-INF/jsp/sys/dinnerTableList.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/sys/dinnerTableList.jsp").forward(request, response);
		
		}else if(method != null && !method.equals("") && method.equals("update")) {
			//表示更新，即删除和激活
			//通过ID查询餐桌
			DinnerTable  dinnerTable = dinnerTableService.findById(Integer.parseInt(id));
			
			System.out.println("dinnerTable:"+dinnerTable);
			dinnerTable.setDisabled(Integer.parseInt(disabled));
			System.out.println("dinnerTable2:"+dinnerTable);
			
			//更新
			dinnerTableService.update(dinnerTable);
			System.out.println("===========servlet更新餐桌完成==========");
			
			
			System.out.println("getServletContext().getContextPath():"+getServletContext().getContextPath());
			response.sendRedirect(getServletContext().getContextPath()+"/sys/dinnerTable.action?method=list");
		
		}else if(method != null && !method.equals("") && method.equals("addPage")) {
			//跳转到餐桌的添加页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/dinnerTableAdd.jsp").forward(request, response);
		}else if(method != null && !method.equals("") && method.equals("addSubmit")) {
			//根据餐桌的名字查询餐桌
			DinnerTable  dinnerTable = dinnerTableService.findByTableName(tableName);
			System.out.println("根据餐桌的名字查询餐桌："+dinnerTable);
			
			String message = "";
			//判断餐桌的名字是否存在
			if(dinnerTable == null) {
				//当前用户输入的餐桌名字目前数据库没有，则保存用户输入的餐桌名
				DinnerTable table = new DinnerTable();
				table.setTableName(tableName);
				
				dinnerTableService.save(table);
				message = "success";
			}else {
				message = "fail";
			}
			
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(message);
		}
		
	}

}
