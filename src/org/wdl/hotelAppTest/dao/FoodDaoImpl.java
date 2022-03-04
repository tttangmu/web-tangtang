package org.wdl.hotelAppTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.util.ConnectionFactory;

public class FoodDaoImpl implements FoodDao {

	//根据菜系ID查询未删除的菜品
	@Override
	public List<Food> findByFoodTypeId(Integer foodTypeId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "SELECT * FROM tb_food WHERE disabled = 0 AND foodType_id = ?";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始  是什么类型的就set什么类型
			 preparedStatement.setInt(1, foodTypeId);
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			List<Food> foods = new ArrayList<>();
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				Food food = new Food();
				food.setId(resultSet.getInt("id"));
				food.setCreateDate(resultSet.getTimestamp("create_date"));
				food.setDisabled(resultSet.getInt("disabled"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setImg(resultSet.getString("img"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				food.setUpdateDate(resultSet.getTimestamp("update_date"));
				
				foods.add(food);
			}
			
			return foods;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		
		return null;
	}

	@Override
	public Food findById(Integer foodId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "SELECT * FROM tb_food WHERE id = ?";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始  是什么类型的就set什么类型
			 preparedStatement.setInt(1, foodId);
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				Food food = new Food();
				food.setId(resultSet.getInt("id"));
				food.setCreateDate(resultSet.getTimestamp("create_date"));
				food.setDisabled(resultSet.getInt("disabled"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setImg(resultSet.getString("img"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				food.setUpdateDate(resultSet.getTimestamp("update_date"));
				
				return food;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

}
