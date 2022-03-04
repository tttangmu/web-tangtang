package org.wdl.hotelSysTest.sys.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;
import org.wdl.hotelTest.bean.Food;
import org.wdl.hotelTest.bean.Order;
import org.wdl.hotelTest.bean.OrderDetail;
import org.wdl.hotelTest.util.ConnectionFactory;

public class OrderDaoImpl implements OrderDao {

	@Override
	public List<Order> find(Integer userId, String orderCode, String orderDate, String dinnerTableId) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		
		//② 准备SQL语句  
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT  * FROM tb_order torder  LEFT JOIN tb_dinner_table dtable ON torder.`table_id`=dtable.`id`"
				+ " where 1=1");
		 if(userId != null && userId != 1) {
				sql.append(" and  torder.create_userId = "+userId);
			}
		 if(orderCode != null && !orderCode.equals("")) {
				sql.append(" and  torder.order_code like '%"+orderCode.trim()+"%'");
			}
		 if(orderDate != null && !orderDate.equals("")) {
				sql.append(" and  DATE_FORMAT(torder.order_date,'%Y-%m-%d') = '"+orderDate+"'");
			}
		 if(dinnerTableId != null && !dinnerTableId.equals("")) {
				sql.append(" and  torder.table_id = '"+dinnerTableId+"'");
			}
		 
		 System.out.println("搜索订单订单："+sql.toString());
		try {
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			
			//⑤获取查询的结果
			List<Order>  orders = new ArrayList<>();
			//因为查询出来的结果包含表头信息，所以指针下移一行，查看下一行是否有数据
			//如有数据，就进入循环体，封装该行数据到实体bean中
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setDisabled(resultSet.getInt("disabled"));
				order.setOrderCode(resultSet.getString("order_code"));
				order.setOrderDate(resultSet.getTimestamp("order_Date"));
				order.setOrderStatus(resultSet.getInt("order_Status"));
				order.setPayDate(resultSet.getTimestamp("pay_date"));
				order.setTableId(resultSet.getInt("table_id"));
				order.setTotalPrice(resultSet.getDouble("total_Price"));
				
				DinnerTable  dinnerTable = new DinnerTable();
				dinnerTable.setTableName(resultSet.getString("table_name"));
				order.setDinnerTable(dinnerTable);
				
				orders.add(order);
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
		return null;
	}

	@Override
	public Order findById(int id) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句  
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT  * FROM tb_order torder  "
				+ "LEFT JOIN tb_dinner_table dtable ON torder.`table_id`=dtable.`id`  "
				+ "where torder.id = ?");
		
		try {
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			 preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			//因为查询出来的结果包含表头信息，所以指针下移一行，查看下一行是否有数据
			//如有数据，就进入循环体，封装该行数据到实体bean中
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setDisabled(resultSet.getInt("disabled"));
				order.setOrderCode(resultSet.getString("order_code"));
				order.setOrderDate(resultSet.getTimestamp("order_Date"));
				order.setOrderStatus(resultSet.getInt("order_Status"));
				order.setPayDate(resultSet.getTimestamp("pay_date"));
				order.setTableId(resultSet.getInt("table_id"));
				order.setTotalPrice(resultSet.getDouble("total_Price"));
				
				DinnerTable  dinnerTable = new DinnerTable();
				dinnerTable.setTableName(resultSet.getString("table_name"));
				order.setDinnerTable(dinnerTable);
				return order;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public void update(Order order) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		
		//② 准备SQL语句  
		StringBuffer  sql = new StringBuffer();
		sql.append("UPDATE tb_order SET order_code=?,table_id=?,total_Price=?,"
				+ "order_Status=?,"
				+ "order_Date=?,pay_date=?,disabled=?  WHERE id =?");
		
		System.out.println("更新订单："+order.getOrderDate());
		Date payDate = order.getPayDate() != null ? new Date(order.getPayDate().getTime()) : null;
		try {
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			 preparedStatement.setString(1, order.getOrderCode());
			 preparedStatement.setInt(2, order.getTableId());
			 preparedStatement.setDouble(3, order.getTotalPrice());
			 preparedStatement.setInt(4, order.getOrderStatus());
			 preparedStatement.setTimestamp(5, new Timestamp(order.getOrderDate().getTime()));
			 preparedStatement.setDate(6, payDate);
			 preparedStatement.setInt(7, order.getDisabled());
			 preparedStatement.setInt(8, order.getId());
			
			//④ 执行SQL语句
			int resultSet = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				ConnectionFactory.close(null, preparedStatement, null);
		}
	}

	@Override
	public List<OrderDetail> findByOrderId(Integer id) {
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
						" WHERE tb_order_detail.orderId = ? ";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句,返回影响的行数
			 resultSet = preparedStatement.executeQuery();
			 List<OrderDetail> orderDetails = new ArrayList<>();
			while (resultSet.next()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setId(resultSet.getInt("detailId"));
				orderDetail.setBuyNum(resultSet.getInt("buyNum"));
				orderDetail.setOrderId(resultSet.getInt("orderId"));
				orderDetail.setFoodId(resultSet.getInt("food_id"));
				orderDetail.setDiscount(resultSet.getDouble("discount"));
				orderDetail.setDisabled(resultSet.getInt("disabled"));
				
				Food food = new Food();
				food.setFoodTypeId(resultSet.getInt("foodType_id"));
				food.setFoodName(resultSet.getString("food_name"));
				food.setImg(resultSet.getString("img"));
				food.setDiscount(resultSet.getDouble("discount"));
				food.setPrice(resultSet.getDouble("price"));
				food.setRemark(resultSet.getString("remark"));
				
				orderDetail.setFood(food);
				orderDetails.add(orderDetail);		
			}
			return orderDetails;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

}
