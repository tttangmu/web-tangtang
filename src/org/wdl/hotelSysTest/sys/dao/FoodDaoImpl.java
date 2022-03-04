package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.FoodType;
import org.wdl.hotelTest.util.ConnectionFactory;

public class FoodDaoImpl implements FoodDao {

	@Override
	public List<Food> find(String keyword, String foodTypeId) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		
		//② 准备SQL语句  SELECT * FROM tb_food WHERE food_name LIKE  '%干锅%' AND foodType_id = 2
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT food.*,foodType.type_name FROM tb_food food LEFT JOIN  tb_food_type foodType ON food.foodType_id = foodType.id  where 1=1 ");
		
		if(keyword != null && !keyword.equals("")) {
			sql.append(" and food_name LIKE '%"+keyword+"%'");
		}
		
		if(foodTypeId != null && !foodTypeId.equals("")) {
			sql.append(" and foodType_id = "+foodTypeId);
		}
		
		System.out.println("sql:"+sql);
		
		
		try {
			//③准备集装箱
			preparedStatement = connection.prepareStatement(sql.toString());
			
			//④执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			List<Food> foods = new ArrayList<>();
			while (resultSet.next()) {
				Food  food = new Food();
				food.setId(resultSet.getInt("id"));
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setImg(resultSet.getString("img"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				food.setCreateDate(resultSet.getTimestamp("create_date"));
				food.setUpdateDate(resultSet.getTimestamp("update_date"));
				food.setDisabled(resultSet.getInt("disabled"));
				
				FoodType foodType = new FoodType();
				foodType.setTypeName(resultSet.getString("type_name"));
				
				food.setFoodType(foodType);
				
				foods.add(food);
			}
			//遍历foods，根据food中foodTypeId查询菜系，就可以获取food对于的菜系名字 不采取这种方法
			return  foods;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
		return null;
	}

	@Override
	public Food findById(int id) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句  SELECT * FROM tb_food WHERE food_name LIKE  '%干锅%' AND foodType_id = 2
		String  sql = "SELECT * FROM tb_food where id = ? ";
		
		try {
			//③准备集装箱
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			//④执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Food  food = new Food();
				food.setId(resultSet.getInt("id"));
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setImg(resultSet.getString("img"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				food.setCreateDate(resultSet.getTimestamp("create_date"));
				food.setUpdateDate(resultSet.getTimestamp("update_date"));
				food.setDisabled(resultSet.getInt("disabled"));
				
				System.out.println("dao:"+food);
				return  food;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void update(Food food) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句  
		String  sql = "UPDATE tb_food SET food_name = ?,foodType_id = ?,price = ?,discount = ?,remark=?,img =?,update_date =NOW(),disabled = ? WHERE id = ?";
		
		try {
			//③准备集装箱
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, food.getFoodName());
			preparedStatement.setInt(2, food.getFoodTypeId());
			preparedStatement.setDouble(3, food.getPrice());
			preparedStatement.setDouble(4, food.getDiscount());
			preparedStatement.setString(5, food.getRemark());
			preparedStatement.setString(6, food.getImg());
			preparedStatement.setInt(7, food.getDisabled());
			preparedStatement.setInt(8, food.getId());
			
			//④执行SQL语句
			int i  = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Food findByFoodName(String foodName) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句  SELECT * FROM tb_food WHERE food_name LIKE  '%干锅%' AND foodType_id = 2
		String  sql = "SELECT * FROM tb_food where food_name = ? ";
		
		try {
			//③准备集装箱
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, foodName);
			
			//④执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Food  food = new Food();
				food.setId(resultSet.getInt("id"));
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setImg(resultSet.getString("img"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				food.setCreateDate(resultSet.getTimestamp("create_date"));
				food.setUpdateDate(resultSet.getTimestamp("update_date"));
				food.setDisabled(resultSet.getInt("disabled"));
				
				System.out.println("dao:"+food);
				return  food;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void save(Food food) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句  
		String  sql = "INSERT INTO  tb_food(food_name,foodType_id,price,discount,remark,img,create_date)  VALUES(?,?,?,?,?,?,NOW())";
		
		try {
			//③准备集装箱
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, food.getFoodName());
			preparedStatement.setInt(2, food.getFoodTypeId());
			preparedStatement.setDouble(3, food.getPrice());
			preparedStatement.setDouble(4, food.getDiscount());
			preparedStatement.setString(5, food.getRemark());
			preparedStatement.setString(6, food.getImg());
			
			//④执行SQL语句
			int i  = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

}
