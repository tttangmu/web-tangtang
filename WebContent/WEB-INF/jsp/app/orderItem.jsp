<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>订单详情页</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="text/javascript">
	//付款
	function pay(orderId){
		window.location.href="${pageContext.request.contextPath}/app/order.action?method=pay&orderId="+orderId;
	}
	
	//取消订单
	function deleteOrder(orderId){
		window.location.href="${pageContext.request.contextPath}/app/order.action?method=delete&orderId="+orderId;
	}
</script>
</head>
<body> 
	<!-- banner -->
	<div class="banner about-w3bnr">
		<!-- header -->
		<div class="header">
			<!-- //header-one -->    
			<!-- navigation -->
			<div class="navigation agiletop-nav">
				<div class="container">
					<nav class="navbar navbar-default">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header w3l_logo">
							
							<h1><a href="${pageContext.request.contextPath}/app/index.do">点餐系统</a></h1>
						</div> 
						<div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
							<ul class="nav navbar-nav navbar-right">
								<li><a href="${pageContext.request.contextPath}/app/index.do" class="active">主页</a></li>	
								<!-- Mega Menu -->
								<li>
									<a href="${pageContext.request.contextPath}/app/menuList.do" class="dropdown-toggle" >菜单 </a>
								</li>
								<li class="w3pages">
									<a href="${pageContext.request.contextPath}/app/order.action?method=list" class="dropdown-toggle">我的订单</a>
								</li>  
								<c:if test="${empty session_user }">
									<li class="head-dpdn">
										<a href="${pageContext.request.contextPath}/app/login.do">登录</a>
									</li>
									<li class="head-dpdn">
										<a href="${pageContext.request.contextPath}/app/register.do">免费注册</a>
									</li>
								</c:if>
								<c:if test="${not empty session_user }">
									<li class="head-dpdn">
										<a>${session_user.loginName} 您好！</a>
									</li>
									<li class="head-dpdn">
										<a href="${pageContext.request.contextPath}/app/loginout.action">退出</a>
									</li>
								</c:if>
								<li class="head-dpdn">
									<a href="${pageContext.request.contextPath}/sys/index.action">后台入口</a>
								</li>
							</ul>
						</div>
					</nav>
				</div>
			</div>
			<!-- //navigation --> 
		</div>
		<!-- //header-end --> 
	</div>
	<!-- //banner -->    
	<!-- breadcrumb -->  
	<div class="container">	
		<ol class="breadcrumb w3l-crumbs">
			<li><a href="index.do"><i class="fa fa-home"></i> 主页</a></li> 
			<li class="active">${dinnerTable.tableName}订单详情</li>
		</ol>
	</div>
	<!-- //breadcrumb -->
	<!-- products -->
	<div class="products">	 
		<div class="container">
			<div class="col-md-12 ">
				<!-- 查询 所有订单遍历  未付款的排在上面     开始-->
				<div class="rsidebar-top col-md-12">
					<div class="sidebar-row">

						<!--遍历为付款未删除的订单开始-->
						<c:if test="${not empty orders}">
							<c:forEach  items="${orders}"  var="order">
								<h4>
								<span class="col-md-12">
									订单编号：${order.orderCode}
									下单时间：<fmt:formatDate value="${order.orderDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
								</span>
								</h4>
								<ul class="faq">
									<!-- 订单明细遍历开始 -->
									<c:if test="${not empty  order.orderDetails}">
										<c:forEach items="${order.orderDetails}"  var="orderDetail">
											<li>
												<a href="#">
													<span class="col-md-8">${orderDetail.food.foodName}</span>
													<span class="col-md-2">${orderDetail.buyNum}份</span>
													<span class="col-md-2">
														￥<fmt:formatNumber value="${orderDetail.food.price*orderDetail.discount}" pattern="0.00"></fmt:formatNumber>
													</span>
												</a>
											</li>
										</c:forEach>
									</c:if>
									<!-- 订单明细遍历结束 -->
								</ul>
								<br/>
								<span class="col-md-8"></span>
								<span class="col-md-4">
									<font color="#0096e6">
									总计:￥ <fmt:formatNumber value="${order.totalPrice}" pattern="0.00"></fmt:formatNumber></font>
										<!-- 订单id -->
										<c:if test="${order.orderStatus == 0 && (session_user.id == order.creatUserId)}">
											<!-- 参数为订单id -->
											<input type="button" onclick="pay(${order.id})" value="付款">
											<input type="button" onclick="deleteOrder(${order.id})" value="取消订单">
										</c:if>
								</span>
							</c:forEach>	
						</c:if>
						<!--遍历订单结束-->

						<!-- <center><font color="red">暂时没有订单！</font></center> -->
					</div>	 
				</div>
				<div class="clearfix">&nbsp;</div>
			</div>
		</div>
	</div>
</body>
</html>