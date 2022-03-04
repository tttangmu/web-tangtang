package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.FoodType;
import org.wdl.hotelTest.util.ConnectionFactory;

public class FoodTypeDaoImpl implements FoodTypeDao {

	@Override
	public List<FoodType> find(String keyword, String disabled) {
		
		//操作数据库：① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句   SELECT * FROM tb_food_type WHERE type_name LIKE '%甜品%'  AND disabled = ?	
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_food_type WHERE  1=1");
		
		if(keyword != null && !keyword.equals("")) {
			sql.append(" and type_name LIKE '%"+keyword+"%'");
		}
		
		if (disabled != null && !disabled.equals("")) {
			sql.append(" AND disabled = "+disabled);
		}
			
		try {
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql.toString());
			//④ 执行SQL语句
			resultSet  = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			List<FoodType>  foodTypes = new ArrayList<>();
			while (resultSet.next()) {
				FoodType  foodType = new FoodType();
				foodType.setId(resultSet.getInt("id"));
				foodType.setTypeName(resultSet.getString("type_name"));
				foodType.setUpdateDate(resultSet.getTimestamp("update_date"));
				foodType.setCreateDate(resultSet.getTimestamp("create_date"));
				foodType.setDisabled(resultSet.getInt("disabled"));
				
				foodTypes.add(foodType);
			}
			return  foodTypes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
		
		return null;
	}

	@Override
	public FoodType findByFoodName(String foodTypeName) {
		//操作数据库：① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句   SELECT * FROM tb_food_type WHERE type_name LIKE '%甜品%'  AND disabled = ?	
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_food_type WHERE type_name=?");
		
		try {
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, foodTypeName);
			//④ 执行SQL语句
			resultSet  = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				FoodType  foodType = new FoodType();
				foodType.setId(resultSet.getInt("id"));
				foodType.setTypeName(resultSet.getString("type_name"));
				foodType.setUpdateDate(resultSet.getTimestamp("update_date"));
				foodType.setCreateDate(resultSet.getTimestamp("create_date"));
				foodType.setDisabled(resultSet.getInt("disabled"));
				
				return  foodType;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
				
		return null;
	}

	@Override
	public void save(FoodType foodType2) {
		//操作数据库：① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		Integer  resultSet= null;
		
		//② 准备SQL语句   
		StringBuffer sql = new StringBuffer("INSERT INTO tb_food_type(type_name,create_date) VALUES(?,NOW())");
		
		try {
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, foodType2.getTypeName());
			//④ 执行SQL语句
			resultSet  = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public FoodType findById(int id) {
		//操作数据库：① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句   SELECT * FROM tb_food_type WHERE type_name LIKE '%甜品%'  AND disabled = ?	
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_food_type WHERE  id= ?");
		
		try {
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, id);
			//④ 执行SQL语句
			resultSet  = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				FoodType  foodType = new FoodType();
				foodType.setId(resultSet.getInt("id"));
				foodType.setTypeName(resultSet.getString("type_name"));
				foodType.setUpdateDate(resultSet.getTimestamp("update_date"));
				foodType.setCreateDate(resultSet.getTimestamp("create_date"));
				foodType.setDisabled(resultSet.getInt("disabled"));
				return  foodType;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void update(FoodType foodType2) {
		//操作数据库：① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		Integer  resultSet= null;
		
		//② 准备SQL语句   
		StringBuffer sql = new StringBuffer("UPDATE tb_food_type SET type_name = ?,update_date = NOW(),disabled = ? WHERE id = ?");
		
		try {
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, foodType2.getTypeName());
			preparedStatement.setInt(2, foodType2.getDisabled());
			preparedStatement.setInt(3, foodType2.getId());
			//④ 执行SQL语句
			resultSet  = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

}
