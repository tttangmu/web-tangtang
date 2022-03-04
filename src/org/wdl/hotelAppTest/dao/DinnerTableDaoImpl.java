package org.wdl.hotelAppTest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.hotelTest.bean.DinnerTable;
import org.wdl.hotelTest.util.ConnectionFactory;

public class DinnerTableDaoImpl implements DinnerTableDao {

	@Override
	public List<DinnerTable> findDinnerTables(String tableStatus, String tableName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			StringBuffer sql = new StringBuffer("SELECT * FROM tb_dinner_table WHERE disabled = 0");
			
			if(tableName != null && !tableName.equals("")) {
				sql.append(" and table_name like '%"+tableName+"%' ");
			}
			
			if(tableStatus != null && !tableStatus.equals("")) {
				sql.append(" and table_status = "+tableStatus);
			}
			System.out.println("sql:"+sql);
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			List<DinnerTable> dinnerTables = new ArrayList<>();
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				DinnerTable dinnerTable = new DinnerTable();
				dinnerTable.setId(resultSet.getInt("id"));
				dinnerTable.setTableName(resultSet.getString("table_name"));
				dinnerTable.setTableStatus(resultSet.getInt("table_status"));
				dinnerTable.setDisabled(resultSet.getInt("disabled"));
				dinnerTable.setBeginUseDate(resultSet.getTimestamp("begin_use_date"));
				dinnerTable.setCreateDate(resultSet.getTimestamp("create_date"));
				dinnerTable.setUpdateDate(resultSet.getTimestamp("update_date"));
				System.out.println(" dao :"+dinnerTable);
				dinnerTables.add(dinnerTable);
			}
			
			return dinnerTables;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		
		return null;
	}

	@Override
	public DinnerTable findById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "SELECT * FROM tb_dinner_table WHERE id = ?";
			
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			//索引从1开始
			 preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句
			 resultSet = preparedStatement.executeQuery();
			
			//指针下移一位，因为将表头信息也查询出来了，而表头信息不需要封装
			while (resultSet.next()) {
				DinnerTable dinnerTable = new DinnerTable();
				dinnerTable.setId(resultSet.getInt("id"));
				dinnerTable.setTableName(resultSet.getString("table_name"));
				dinnerTable.setTableStatus(resultSet.getInt("table_status"));
				dinnerTable.setDisabled(resultSet.getInt("disabled"));
				dinnerTable.setBeginUseDate(resultSet.getTimestamp("begin_use_date"));
				dinnerTable.setCreateDate(resultSet.getTimestamp("create_date"));
				dinnerTable.setUpdateDate(resultSet.getTimestamp("update_date"));
				
				return dinnerTable;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
		return null;
	}

	@Override
	public void update(DinnerTable dinnerTable) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			//①获取连接
			 connection = ConnectionFactory.getConnection();
			
			//②准备SQL
			String sql = "UPDATE tb_dinner_table SET table_status = ?,table_name= ?,"
					+ "begin_use_date = ?,CREATE_DATE = ?,update_date=now() ,"
					+ "disabled = ? WHERE id = ?";
			
			Date beginUserDate = dinnerTable.getBeginUseDate() != null ? new Date(dinnerTable.getBeginUseDate().getTime()) : null;
			Date createDate = dinnerTable.getCreateDate() != null ?new Date(dinnerTable.getCreateDate().getTime()) : null;
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			 preparedStatement.setInt(1, dinnerTable.getTableStatus());
			 preparedStatement.setString(2, dinnerTable.getTableName());
			 preparedStatement.setDate(3, beginUserDate);
			 preparedStatement.setDate(4, createDate);
			 preparedStatement.setInt(5, dinnerTable.getDisabled());
			 preparedStatement.setInt(6, dinnerTable.getId());
			 
			 
			//④ 执行SQL语句
			int result  =preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection,preparedStatement,resultSet );
		}
	}
}
