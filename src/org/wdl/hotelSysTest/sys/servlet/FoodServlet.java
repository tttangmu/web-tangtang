package org.wdl.hotelSysTest.sys.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.wdl.hotelSysTest.sys.service.FoodService;
import org.wdl.hotelSysTest.sys.service.FoodServiceImpl;
import org.wdl.hotelSysTest.sys.service.FoodTypeService;
import org.wdl.hotelSysTest.sys.service.FoodTypeServiceImpl;
import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.FoodType;

/**
 * Servlet implementation class FoodServlet
 */
@WebServlet("/sys/food.action")
@MultipartConfig  //标识一个servlet支持文件的上传
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("==============菜品=========");
		
		request.setCharacterEncoding("utf-8");
		String keyword = request.getParameter("keyword");
		String foodTypeId = request.getParameter("foodTypeId");
		String method = request.getParameter("method");
		
		String disabled = request.getParameter("disabled");
		//更新需要的参数
		String   id = request.getParameter("id");
		String foodName = request.getParameter("foodName");
		String  price = request.getParameter("price");
		String  discount = request.getParameter("discount");
		String remark = request.getParameter("remark");
		System.out.println("method:"+method+"  foodTypeId:"+foodTypeId+"   keyword:"+keyword+"   disabled:"+disabled+"   id:"+id+" foodName:"+foodName);
		System.out.println("price:"+price+"  discount:"+discount+"   remark:"+remark);
		FoodService  foodService = new FoodServiceImpl();
		
		//查询所有的菜系名
		//根据用户的输入和选择去查询菜系
		FoodTypeService foodTypeService = new FoodTypeServiceImpl();
		List<FoodType>  foodTypes=  foodTypeService.find(null,null);
		System.out.println("foodTypes:"+foodTypes);
		request.setAttribute("foodTypes", foodTypes);
		
		if(method != null && method.equals("list")) {
			//根据关键字和菜品类型查询菜品
			List<Food>  foods = foodService.find(keyword,foodTypeId);
			System.out.println("foods.size():"+foods.size()+"   foods:"+foods);
			
			request.setAttribute("foodTypeId", foodTypeId);
			request.setAttribute("keyword", keyword);
			request.setAttribute("foods", foods);
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodList.jsp").forward(request, response);
		
		}else if(method != null && method.equals("update")) {
			//激活或删除
			//通过ID查询餐桌
			Food food = foodService.findById(Integer.parseInt(id));
			System.out.println("修改前："+food);
			food.setDisabled(Integer.parseInt(disabled));
			System.out.println("修改后："+food);
			
			foodService.update(food);
			
			response.sendRedirect(getServletContext().getContextPath() +"/sys/food.action?method=list");
		}else if(method != null && method.equals("addPage")) {
			//跳转到添加页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodAdd.jsp").forward(request, response);
		}else if(method != null && method.equals("ajaxFoodName")) {
			//查询用户输入的菜品名是否已经存在
			Food  food = foodService.findByFoodName(foodName);
			System.out.println("food:"+food);
			if(food != null) {
				//菜系名字已存在
				response.getWriter().print("fail");
			}
		}else if(method != null && method.equals("addSubmit")) {
			System.out.println("=========添加===========");
			
			//使用@MultipartConfig注解后，可通过 getPart获取上传的文件，返回的是Part类
			Part part = request.getPart("img");
			
			//上传图片到项目目录，只保存上传后的图片地址到数据库
			String  filePath = getServletContext().getRealPath("/images/app/food");
			//E:\\teach_tool\\apache-tomcat-8.0.50\\webapp\\HotelSysTest\\upload\\food
			System.out.println("filePath:"+filePath);
			
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			//牛蛙.jpg
			String fileName = part.getSubmittedFileName();
			System.out.println("fileName:"+fileName);
			
			//为了防止用户上传的图片名重复，一般上传图片是会给图片重新取一个不会重复的名字     不会重复的名字.jpg
			//截取文件的扩展名 .jpg
			String extName = fileName.substring(fileName.lastIndexOf("."));
			System.out.println("extName:"+extName);
			//不会重复的名字
			String name = UUID.randomUUID().toString();
			//拼装新的不会重复的名字
			StringBuffer newName = new StringBuffer();
			newName.append(name).append(extName);
			
			//将用户上传的图片写到指定的目录
			part.write(filePath+File.separator+newName.toString());
			
			Double  dis = discount != null && !discount.equals("")? Double.parseDouble(discount):1;
			//保存菜品相关信息到数据库
			Food food = new Food(foodName, Integer.parseInt(foodTypeId), Double.parseDouble(price), dis, remark, newName.toString(), 0);
			foodService.save(food);
			
			response.sendRedirect(getServletContext().getContextPath()+"/sys/food.action?method=list");
		}else if(method != null && method.equals("viewUpdate")) {
			//根据ID查询要修改的菜品
			Food  food = foodService.findById(Integer.parseInt(id));
			
			request.setAttribute("food", food);
			//跳转到更新页面
			request.getRequestDispatcher("/WEB-INF/jsp/sys/foodUpdate.jsp").forward(request, response);
		}else if(method != null && method.equals("updateSubmit")) {
			//更新菜品
			//使用@MultipartConfig注解后，可通过 getPart获取上传的文件，返回的是Part类
			Part part = request.getPart("img");
			
			//上传图片到项目目录，只保存上传后的图片地址到数据库
			String  filePath = getServletContext().getRealPath("/images/app/food");
			//E:\\teach_tool\\apache-tomcat-8.0.50\\webapp\\HotelSysTest\\upload\\food
			System.out.println("filePath:"+filePath);
			
			File file = new File(filePath);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			//牛蛙.jpg  Tomcat7的环境下没有part.getSubmittedFileName()方法，不能获取文件名
			String fileName = part.getSubmittedFileName();
			System.out.println("fileName:"+fileName);
			
			
			//表明用户没有修改菜品图片
			Double  dis = discount != null && !discount.equals("")? Double.parseDouble(discount):1;
			//更新菜品，先根据ID找到要修改的菜品
			Food  food = foodService.findById(Integer.parseInt(id));
			System.out.println("原始菜品："+food);
			
			food.setFoodName(foodName);
			food.setFoodTypeId(Integer.parseInt(foodTypeId));
			food.setDiscount(dis);
			food.setPrice(Double.parseDouble(price));
			food.setRemark(remark);
			
			if(fileName != null && !fileName.equals("")) {
				//用户更改了原来的菜品图片
				
				//为了防止用户上传的图片名重复，一般上传图片是会给图片重新取一个不会重复的名字     不会重复的名字.jpg
				//截取文件的扩展名 .jpg
				String extName = fileName.substring(fileName.lastIndexOf("."));
				System.out.println("extName:"+extName);
				//不会重复的名字
				String name = UUID.randomUUID().toString();
				//拼装新的不会重复的名字
				StringBuffer newName = new StringBuffer();
				newName.append(name).append(extName);
				
				//将用户上传的图片写到指定的目录
				part.write(filePath+File.separator+newName.toString());
				
				food.setImg(newName.toString());
			}
			
			System.out.println("修改后："+food);
			foodService.update(food);
			
			response.sendRedirect(getServletContext().getContextPath()+"/sys/food.action?method=list");
		
		}
	}

}
