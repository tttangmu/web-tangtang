package org.wdl.hotelTest.bean;

import java.util.Date;
import java.util.List;

public class Order {
	private Integer id;
	private String orderCode;//订单编号
	private Integer tableId;//订单是那一餐桌的
	private Double totalPrice;//订单总金额
	private Integer orderStatus;//订单的状态 0表示未付款  1表示已付款
	private Date orderDate;//下单，提交购物车的世界
	private Date payDate;//付款的时间
	private Date updateDate;//最后更新的时间，菜品信息更改的时间
	private Integer disabled;
	private Integer creatUserId;
	
	private DinnerTable dinnerTable;
	private List<OrderDetail> orderDetails;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public DinnerTable getDinnerTable() {
		return dinnerTable;
	}
	public void setDinnerTable(DinnerTable dinnerTable) {
		this.dinnerTable = dinnerTable;
	}
	
	public Integer getCreatUserId() {
		return creatUserId;
	}
	public void setCreatUserId(Integer creatUserId) {
		this.creatUserId = creatUserId;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", orderCode=" + orderCode + ", tableId=" + tableId + ", totalPrice=" + totalPrice
				+ ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", payDate=" + payDate + ", updateDate="
				+ updateDate + ", disabled=" + disabled + ", creatUserId=" + creatUserId + ", dinnerTable="
				+ dinnerTable + ", orderDetails=" + orderDetails + "]";
	}
	
	
}
