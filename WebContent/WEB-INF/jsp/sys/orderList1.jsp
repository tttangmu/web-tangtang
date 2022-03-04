<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!-- 包含公共的JSP代码片段 -->
<title>无线点餐平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">

</script>
</head>
<body>
	<!-- 页面标题 -->
	<div id="TitleArea">
		<div id="TitleArea_Head"></div>
		<div id="TitleArea_Title">
			<div id="TitleArea_Title_Content">
				<img border="0" width="13" height="13"
					src="${ctx}/images/sys/title_arrow.gif" /> 餐厅订单列表
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>

	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" style="text-align: center;" cellspacing="0" cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>订单编号</td>
					<td>餐桌名</td>
					<td>下单日期</td>
					<td>付款日期</td>
					<td>总金额</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
				<c:choose>
					<c:when test="${not empty orders }">
						<c:forEach  items="${orders}"  var="order">
							<tr height="60">
						 		<td>${order.orderCode}</td>
						 		<td>${order.dinnerTable.tableName}</td>
						 		<td><fmt:formatDate value="${order.orderDate}"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						 		<td><fmt:formatDate value="${order.payDate}"  pattern="yyyy-MM-dd HH:mm:ss"/></td>
						 		<td><fmt:formatNumber value="${order.totalPrice}"  pattern="0.00"></fmt:formatNumber> </td>
						 		<td>
						 			<c:if test="${order.orderStatus ==1}">
						 				已付款
						 			</c:if>
						 			<c:if test="${order.orderStatus ==0}">
						 				未付款
						 			</c:if>
						 		</td>
						 		<td>
						 			<c:if test="${order.disabled ==1}">
										<a href="${ctx}/sys/order.action?method=update&id=${order.id}&disabled=0" class="FunctionButton" target="right">激活</a>				
									</c:if>
									<c:if test="${order.disabled ==0}">
										<a href="${ctx}/sys/order.action?method=update&id=${order.id}&disabled=1" class="FunctionButton" target="right">删除</a>				
									</c:if>
						 		</td>
					 		</tr>
					 		<%-- <tr>
					 			<c:forEach items="${order.orderDetails }" var="orderDetail">
									<a href="#">
										<span class="col-md-8">${orderDetail.food.foodName }</span>
										<span class="col-md-2">${orderDetail.buyNum}份</span>
										<span class="col-md-2">
											￥<fmt:formatNumber value="${orderDetail.food.price*orderDetail.discount}" pattern="0.00"></fmt:formatNumber>
										</span>
									</a>
								</c:forEach>
							</tr> --%>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
		<!-- 其他功能超链接 -->
		<div id="TableTail" align="center">
		</div>
	</div>
</body>
</html>
