package org.wdl.hotelTest.bean;

import java.util.Date;

public class DinnerTable {

	private Integer id;// PRIMARY KEY AUTO_INCREMENT, 
	private String tableName;// VARCHAR(20), 表名
	private Integer tableStatus;// INT DEFAULT 0, 餐桌状态：0表示未使用  1表示正在使用
	private Date beginUseDate;// DATETIME 客户开始使用餐桌时间
	private Date createDate;
	private Date updateDate;
	private Integer disabled;//餐桌是否被删除 0没有删除   1已删除
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Integer getTableStatus() {
		return tableStatus;
	}
	public void setTableStatus(Integer tableStatus) {
		this.tableStatus = tableStatus;
	}
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getBeginUseDate() {
		return beginUseDate;
	}
	public void setBeginUseDate(Date beginUseDate) {
		this.beginUseDate = beginUseDate;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	@Override
	public String toString() {
		return "DinnerTable [id=" + id + ", tableName=" + tableName + ", tableStatus=" + tableStatus + ", beginUseDate="
				+ beginUseDate + ", createDate=" + createDate + ", updateDate=" + updateDate +  ", disabled=" + disabled + "]";
	}
}
