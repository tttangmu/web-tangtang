package org.wdl.hotelTest.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	//自动读取c3p0-config.xml
	private static DataSource  dataSource =new ComboPooledDataSource();
	
	//获取连接
	public static Connection getConnection() {
		try {
			return  dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	public static void close(Connection  conn,PreparedStatement  preparedStatement,ResultSet resultSet) {
		try {
			//释放资源，根据先开后放的规则
			if(resultSet != null)  resultSet.close();
			if(preparedStatement != null) preparedStatement.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
