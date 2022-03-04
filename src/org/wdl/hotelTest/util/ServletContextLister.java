package org.wdl.hotelTest.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServletContextLister
 *
 */
@WebListener
public class ServletContextLister implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServletContextLister() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	  ServletContext  servletContext = event.getServletContext();
          //上传图片到项目目录，只保存上传后的图片地址到数据库
            String  filePath = servletContext.getRealPath("/images/app/food");
   		//E:\\teach_tool\\apache-tomcat-8.0.50\\webapp\\HotelSysTest\\upload\\food
   		System.out.println("============filePath:"+filePath);
   		
   		//根据路径创建file
   		File file = new File(filePath);
   		if(!file.exists()) {
   			//如果文件不存在，就创建
   			file.mkdirs();
   			System.out.println("========项目启动，创建文件夹====");
   		}else {
   			System.out.println("======项目启动 文件夹已存在");
   		}
    	
    }
	
}
