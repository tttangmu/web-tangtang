package org.wdl.hotelSysTest.sys.dao;

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
	public List<DinnerTable> find(String keyword, String tableStatus, String disabled) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		
		//② 准备SQL语句  SELECT  * FROM tb_dinner_table WHERE table_Name LIKE '%2%' AND table_status = ""  AND disabled = ""
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT  * FROM tb_dinner_table WHERE 1=1");
		
		if(keyword != null && !keyword.equals("")) {
			sql.append(" and  table_Name LIKE '%"+keyword+"%'");
		}
		
		if(tableStatus != null && !tableStatus.equals("")) {
			sql.append(" AND table_status ="+tableStatus);
		}
		
		if(disabled != null && !disabled.equals("")) {
			sql.append(" AND disabled ="+disabled);
		}
		
		System.out.println("sql:"+sql);
		
		try {
			//③ 获取集装箱
			 preparedStatement = connection.prepareStatement(sql.toString());
			
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			
			//⑤获取查询的结果
			List<DinnerTable>  dinnerTables = new ArrayList<>();
			//因为查询出来的结果包含表头信息，所以指针下移一行，查看下一行是否有数据
			//如有数据，就进入循环体，封装该行数据到实体bean中
			while (resultSet.next()) {
				DinnerTable  dinnerTable = new DinnerTable();
				//根据该行在数据库中的列名获取列值，注意列是什么类型就get什么类型
				dinnerTable.setId(resultSet.getInt("id"));
				dinnerTable.setTableName(resultSet.getString("table_Name"));
				dinnerTable.setTableStatus(resultSet.getInt("table_status"));
				dinnerTable.setUpdateDate(resultSet.getTimestamp("update_date"));
				dinnerTable.setBeginUseDate(resultSet.getTimestamp("begin_use_date"));
				dinnerTable.setCreateDate(resultSet.getTimestamp("create_date"));
				dinnerTable.setDisabled(resultSet.getInt("disabled"));
				
				dinnerTables.add(dinnerTable);
			}
			
			return dinnerTables;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	//操作数据库：① 获取连接
	//② 准备SQL语句 		
	//③ 获取集装箱		
	//④ 执行SQL语句
	//⑤获取查询的结果
	@Override
	public DinnerTable findById(int id) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
				
				
		try {
			//② 准备SQL语句 
			String sql = "SELECT  * FROM tb_dinner_table WHERE id= ?";
			//③ 获取集装箱
			preparedStatement = connection.prepareStatement(sql);
			//是什么类型的就set什么类型，索引从1开始
			preparedStatement.setInt(1, id);
			
			//④ 执行SQL语句
			resultSet = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				DinnerTable  dinnerTable = new DinnerTable();
				//根据该行在数据库中的列名获取列值，注意列是什么类型就get什么类型
				dinnerTable.setId(resultSet.getInt("id"));
				dinnerTable.setTableName(resultSet.getString("table_Name"));
				dinnerTable.setTableStatus(resultSet.getInt("table_status"));
				dinnerTable.setUpdateDate(resultSet.getTimestamp("update_date"));
				dinnerTable.setBeginUseDate(resultSet.getTimestamp("begin_use_date"));
				dinnerTable.setCreateDate(resultSet.getTimestamp("create_date"));
				dinnerTable.setDisabled(resultSet.getInt("disabled"));
				
				return dinnerTable;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
		return null;
	}

	@Override
	public void update(DinnerTable dinnerTable) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
				
		
		try {
			//② 准备SQL语句 	
			String sql = "UPDATE tb_dinner_table SET table_name = ?,table_status = ?,begin_use_date = ?,create_date = ?,update_date = ?,disabled = ? WHERE id = ?";
			
			Date beginUseDate = dinnerTable.getBeginUseDate() != null ? new Date(dinnerTable.getBeginUseDate().getTime()) : null;
			Date createDate = dinnerTable.getCreateDate() != null ? new Date(dinnerTable.getCreateDate().getTime()) : null;
			Date updateDate = dinnerTable.getUpdateDate() != null ? new Date(dinnerTable.getUpdateDate().getTime()) : null;
			//③ 获取集装箱	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dinnerTable.getTableName());
			preparedStatement.setInt(2, dinnerTable.getTableStatus());
			preparedStatement.setDate(3, beginUseDate);
			preparedStatement.setDate(4, createDate);
			preparedStatement.setDate(5, updateDate);
			preparedStatement.setInt(6, dinnerTable.getDisabled());
			preparedStatement.setInt(7, dinnerTable.getId());
			
			//④ 执行SQL语句
			int i = preparedStatement.executeUpdate();
			System.out.println("更新操作："+i);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
		
	}

	
	//操作数据库：① 获取连接
		//② 准备SQL语句 		
		//③ 获取集装箱		
		//④ 执行SQL语句
		//⑤获取查询的结果
	@Override
	public DinnerTable findByTableName(String tableName) {
		
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		String  sql = "SELECT  * FROM tb_dinner_table WHERE table_Name = ?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, tableName);
			
			resultSet = preparedStatement.executeQuery();
			
			//⑤获取查询的结果
			while (resultSet.next()) {
				DinnerTable  dinnerTable = new DinnerTable();
				//根据该行在数据库中的列名获取列值，注意列是什么类型就get什么类型
				dinnerTable.setId(resultSet.getInt("id"));
				dinnerTable.setTableName(resultSet.getString("table_Name"));
				dinnerTable.setTableStatus(resultSet.getInt("table_status"));
				dinnerTable.setUpdateDate(resultSet.getTimestamp("update_date"));
				dinnerTable.setBeginUseDate(resultSet.getTimestamp("begin_use_date"));
				dinnerTable.setCreateDate(resultSet.getTimestamp("create_date"));
				dinnerTable.setDisabled(resultSet.getInt("disabled"));
				
				return dinnerTable;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	
	//操作数据库：① 获取连接
			//② 准备SQL语句 		
			//③ 获取集装箱		
			//④ 执行SQL语句
			//⑤获取查询的结果
	@Override
	public void save(DinnerTable table) {
		//① 获取连接
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement  preparedStatement = null;
		ResultSet  resultSet= null;
		
		//② 准备SQL语句 
		String  sql = "INSERT INTO  tb_dinner_table(table_Name,create_date) VALUES(?,NOW());";
		
		
		try {
			//③ 获取集装箱		
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, table.getTableName());
			
			//④ 执行SQL语句
			int i = preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		
	}
}
