package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.wdl.hotelAppTest.dao.FoodDao;
import org.wdl.hotelAppTest.dao.FoodDaoImpl;
import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.OrderDetail;
import org.wdl.hotelTest.util.ConnectionFactory;

public class OrderDetailDaoImpl implements OrderDetailDao {

	@Override
	public OrderDetail findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			//②准备SQL
				String sql = "SELECT tb_order_detail.`id`  detailId,tb_order_detail.*,tb_food.*   "
						+ " FROM tb_order_detail " + 
						" LEFT JOIN tb_food ON tb_food.`id`=tb_order_detail.`food_id`" + 
						" WHERE tb_order_detail.id = ? ";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句,返回影响的行数
			 resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				OrderDetail OrderDetail = new OrderDetail();
				OrderDetail.setId(resultSet.getInt("id"));
				OrderDetail.setBuyNum(resultSet.getInt("buyNum"));
				OrderDetail.setOrderId(resultSet.getInt("orderId"));
				OrderDetail.setFoodId(resultSet.getInt("food_id"));
				OrderDetail.setDiscount(resultSet.getDouble("discount"));
				OrderDetail.setDisabled(resultSet.getInt("disabled"));
				
				Food food = new Food();
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setImg(resultSet.getString("img"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				
				OrderDetail.setFood(food);
				return OrderDetail;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

	public void update(OrderDetail orderDetail, Connection connection) {
		//① 获取连接
		PreparedStatement  preparedStatement = null;
		
		//② 准备SQL语句  
		StringBuffer  sql = new StringBuffer();
		sql.append("UPDATE tb_order_detail SET orderId=?,food_id=?,buyNum=?,disabled=?,discount=? WHERE id =?;");
		
		try {
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			 preparedStatement.setInt(1, orderDetail.getOrderId());
			 preparedStatement.setInt(2, orderDetail.getFoodId());
			 preparedStatement.setInt(3, orderDetail.getBuyNum());
			 preparedStatement.setInt(4, orderDetail.getDisabled());
			 preparedStatement.setDouble(5, orderDetail.getDiscount());
			 preparedStatement.setInt(6, orderDetail.getId());
			
			//④ 执行SQL语句
			int resultSet = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(null, preparedStatement, null);
	}
	}

	@Override
	public void addFoods(String[] arrfoodIds,Integer orderId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatementOrder = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			//②准备SQL
			String sql = "INSERT INTO tb_order_detail(orderId,food_id,buyNum,discount) VALUES(?,?,?,?);";
			connection.setAutoCommit(false);
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql);	
			 
			 OrderDao orderDao = new OrderDaoImpl();
			 Double totalprice = orderDao.findById(orderId).getTotalPrice();
			 
			 for (String foodId : arrfoodIds) {
				 preparedStatement.setInt(1, orderId);
				preparedStatement.setInt(2, Integer.parseInt(foodId));
				preparedStatement.setInt(3, 1);
				
				FoodDao foodDao = new FoodDaoImpl();
				Food food = foodDao.findById(Integer.parseInt(foodId));
				totalprice = totalprice+food.getPrice()*food.getDiscount();
				preparedStatement.setDouble(4, food.getDiscount());
				
				preparedStatement.addBatch();
			}
			//执行批处理
			preparedStatement.executeBatch();
			
			
			 String sqlOrder ="update tb_order set total_Price = ? where id = ?"; 
			 preparedStatementOrder = connection.prepareStatement(sqlOrder);	
			 preparedStatementOrder.setDouble(1, totalprice);
			 preparedStatementOrder.setInt(2, orderId);
			 preparedStatementOrder.executeUpdate();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
	}

}
