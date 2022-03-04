package org.wdl.hotelAppTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;
import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConnectionFactory;

public class UserDaoImpl implements UserDao{

	@Override
	public User findByLoginNameAndPass(String loginname, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "SELECT * FROM tb_user WHERE LOGIN_NAME = ? AND PASSWORD = ? AND disabled = 0";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setString(1, loginname);
			 preparedStatement.setString(2, password);
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				User user = new User();
				Integer userId = resultSet.getInt("id");
				user.setId(userId);
				user.setCreateDate(resultSet.getTimestamp("create_date"));
				user.setEmail(resultSet.getString("email"));
				user.setLoginName(resultSet.getString("login_name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setDisabled(resultSet.getInt("disabled"));
				return  user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

	@Override
	public User findByLoginName(String loginName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "SELECT * FROM tb_user WHERE LOGIN_NAME = ? ";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setString(1, loginName);
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				User user = new User();
				Integer userId = resultSet.getInt("id");
				user.setId(userId);
				user.setCreateDate(resultSet.getTimestamp("create_date"));
				user.setEmail(resultSet.getString("email"));
				user.setLoginName(resultSet.getString("login_name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setDisabled(resultSet.getInt("disabled"));
				return  user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

	@Override
	public void save(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "INSERT INTO tb_user(LOGIN_NAME,PASSWORD,email,PHONE,CREATE_DATE)  VALUES(?,?,?,?,NOW())";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setString(1, user.getLoginName());
			 preparedStatement.setString(2, user.getPassword());
			 preparedStatement.setString(3, user.getEmail());
			 preparedStatement.setString(4, user.getPassword());
			
			//④ 执行SQL语句
			 int  result = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
	}

}
