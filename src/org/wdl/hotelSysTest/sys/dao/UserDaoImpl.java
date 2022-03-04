package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.User;
import org.wdl.hotelTest.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByLoginNameAndPass(String loginname, String password) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
				
		try {
			//② 准备SQL语句 
			StringBuffer sql = new StringBuffer("SELECT  * FROM tb_user WHERE login_name= ? ");
			
			if(password != null && !password.equals("")) {
				sql.append("and password ="+password);
			}
			
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql.toString());
			//是什么类型的就set什么类型，索引从1开始
			preparedStatement.setString(1, loginname);
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setLoginName(resultSet.getString("login_name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setEmail(resultSet.getString("email"));
				user.setDisabled(resultSet.getInt("disabled"));
				user.setCreateDate(resultSet.getTimestamp("create_date"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void save(User user) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
				
		try {
			//② 准备SQL语句 
			String sql = "INSERT INTO tb_user(LOGIN_NAME,PASSWORD,email,phone,create_date,create_userId)  VALUES(?,?,?,?,NOW(),?);";
			
			
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql.toString());
			//是什么类型的就set什么类型，索引从1开始
			preparedStatement.setString(1, user.getLoginName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setInt(5, user.getCreateUserId());
			
			//④ 执行SQL语句
		int	 resultSet = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public List<User> find(String sql) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		try {
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql);
			
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			List<User> users = new ArrayList<>();
			//⑤获取查询的结果
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setLoginName(resultSet.getString("login_name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setEmail(resultSet.getString("email"));
				user.setDisabled(resultSet.getInt("disabled"));
				user.setCreateDate(resultSet.getTimestamp("create_date"));
				
				User createUser = new User();
				createUser.setLoginName(resultSet.getString("user2Name"));
				user.setCreateUser(createUser);
				
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public User findById(int id) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
				
		try {
			//② 准备SQL语句 
			StringBuffer sql = new StringBuffer("SELECT  * FROM tb_user WHERE id= ? ");
			
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql.toString());
			//是什么类型的就set什么类型，索引从1开始
			preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setLoginName(resultSet.getString("login_name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setEmail(resultSet.getString("email"));
				user.setDisabled(resultSet.getInt("disabled"));
				user.setCreateDate(resultSet.getTimestamp("create_date"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void update(User user) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
				
		try {
			//② 准备SQL语句 
			String sql = "update  tb_user  set LOGIN_NAME = ?,PASSWORD=?,email=?,phone=?,disabled=?  where id=?";
			
			
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql.toString());
			//是什么类型的就set什么类型，索引从1开始
			preparedStatement.setString(1, user.getLoginName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setInt(5, user.getDisabled());
			preparedStatement.setInt(6, user.getId());
			
			//④ 执行SQL语句
		int	 resultSet = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

}
