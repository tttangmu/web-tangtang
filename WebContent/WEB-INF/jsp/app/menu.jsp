<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加商品到购物车</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> 
	//scrollTo() 方法可把内容滚动到指定的坐标
	addEventListener("load", 
		function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); }
		
	//添加商品到购物车
	function addShopCar(foodId){
		var dinnerTableId = $("#dinnerTableId").val();
		//加入商品到购物车需要调节，加入哪一个商品foodID，到哪一个餐桌的购物车dinnerTableId
		
		window.location.href = "${pageContext.request.contextPath}/app/shopCar.action?dinnerTableId="+dinnerTableId+"&foodId="+foodId+"&method=add";
	}
	
	//购买数量输入框失去焦点事件
	<!--第一个参数：当前标签dom对象         第二个参数：商品的id   第三参数：购买数量     第四个参数：餐桌id -->
	function blurFn(obj,foodId,buyNum,dinnerTableId){
		//获取用户输入的购买数量
		var  num = obj.value;
		
		//如果用户输入的数量<1或者不是一个数字，就赋值为原来的购买数量buyNum
		if(num <1 || isNaN(num)){
			
			obj.value = buyNum;
			
		}else if(num != buyNum){
			//反之，不符合上面两种，而且与原来的购买数量buyNum不一致，就去通过餐桌的id找到对应购买车去修改购买数量 
			window.location.href = "${pageContext.request.contextPath}/app/shopCar.action?method=update&foodId="+foodId+"&buyNum="+Math.ceil(num)+"&dinnerTableId="+dinnerTableId;
		}
	}
	
	<!-- 第一个参数：餐桌的id  第二个参数：菜品的id -->
	//删除餐桌中的某个菜品
	function deleteFn(dinnerTableId,foodId){
		window.location.href = "${pageContext.request.contextPath}/app/shopCar.action?method=delete&foodId="+foodId+"&dinnerTableId="+dinnerTableId;
	}
	
	//占位和取消占位
	<!-- 第一个参数：餐桌的id   第二个参数：更改对应table_status字段的值 -->
	function orderDinner(dinnerTableId,tableStatus){
		window.location.href = "${pageContext.request.contextPath}/app/dinnerTable.action?tableStatus="+tableStatus+"&dinnerTableId="+dinnerTableId;
	}
	
	//下单
	function order(dinnerTableId){
		window.location.href = "${pageContext.request.contextPath}/app/order.action?method=order&dinnerTableId="+dinnerTableId+"&total=${total}";
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
				<div class="container" style="margin-right: 5px;margin-left: 5px;">
					<nav class="navbar navbar-default">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header w3l_logo">
							<h1><a href="index.do">点餐系统</a></h1>
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
	<!-- //breadcrumb -->
	<!-- products -->
	<div class="products">	 
		<div class="container">
			<div class="col-md-9 product-w3ls-right" style="width: 70%;"> 
				<div class="product-top">
					<h4>主页/菜单:
						<!-- 遍历所有未删除的菜系 -->
						<c:if test="${not empty foodTypes}">
							<c:forEach items="${foodTypes}"  var="foodType">
								<a href="${pageContext.request.contextPath}/app/menu.action?foodTypeId=${foodType.id}&id=${dinnerTable.id}" style="color:white;">
									${foodType.typeName}
								</a>&nbsp;
							</c:forEach>
						</c:if>
						
						<c:if test="${ empty foodTypes}">
							当前没有未删除的菜系
						</c:if>
					</h4>
					<div class="clearfix"> </div>
				</div>
				
				<!-- 右侧展示菜品开始 -->
				<div class="products-row">
					<c:if test="${not empty foods }">
						<c:forEach items="${foods}" var="food">
							<div class="col-xs-6 col-sm-4 product-grids">
									<div class="flip-container">
										<div class="flipper agile-products">
												<div class="front"> 
													<img  src="${pageContext.request.contextPath}/images/app/food/${food.img}" style="width: 250px;height: 150px" class="img-responsive" alt="img">
													<div class="agile-product-text">              
														<h5>${food.foodName}</h5>  
													</div> 
												</div>
												<div class="back">
													<h4>${food.foodName}</h4>
													<p>${food.remark}</p>
													<h6>
														<fmt:formatNumber value="${food.price}" pattern="0.00"></fmt:formatNumber> <sup>￥</sup>
													</h6>
													<form action="#" method="post">
														<input type="hidden" id="dinnerTableId"  name="dinnerTableId" value="${dinnerTable.id}"> 
														<button type="button"  onclick="addShopCar(${food.id})" class="w3ls-cart pw3ls-cart">
															<i class="fa fa-cart-plus" aria-hidden="true"></i>加入购物车
														</button>
														<span class="w3-agile-line"> </span>
														<a href="#" >详情</a>
													</form>
												</div>
										</div>
									</div> 
								</div> 
						</c:forEach>
					</c:if>	
				<!-- 右侧展示菜品结束 -->
				</div>
			</div>
			
			<!--左侧购物车  -->
			<div class="col-md-3 rsidebar" style="width: 28%;">
				<div class="rsidebar-top">
					<div class="sidebar-row">
						<ol class="breadcrumb w3l-crumbs">
							<li class="active">${dinnerTable.tableName}的购物车</li> 
						</ol>
						<form method="post" action="" style="margin-top: 10px;">    
							<ul style="margin: 5cpx 0 20px;padding: 1em;list-style-type: none;border: 1px solid #ccc;border-radius: 4px;box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);">
								<center><font style="color: red"></font> </center>
									<!--目前没有商品-->

									<!--目前有商品-->
									<c:if test="${not empty foods2 }">
										<c:forEach items="${foods2}" var="food">
											<li style="margin: 10px;" name="food">    
												<div>
													<!-- 可到菜品详情页 -->
													<a  href="#">${food.foodName}</a>
													<input name="id" value="${food.id}" type="hidden"> 
													<!--第一个参数：当前标签dom对象         第二个参数：商品的id   第三参数：购买数量     第四个参数：餐桌id -->
									    			<input  name=buyNum  value="${food.buyNum}"  onblur="blurFn(this, ${food.id}, ${food.buyNum},${dinnerTable.id});"
									    			style="width: 30px;border-radius: 3px;border: 1px solid #a3a3a3;text-align: right;padding: 2px 4px;" >
													<!-- 第一个参数：餐桌的id  第二个参数：菜品的id -->
													<input type="button"  value="×" onclick="deleteFn(${dinnerTable.id},${food.id});" style="border-radius: 3px;border: 1px solid #a3a3a3;background: b7b7b7;"></input>
												</div>
												<div style="float: right;">
													<a><s><fmt:formatNumber value="${food.price}" pattern="0.00"></fmt:formatNumber> </s></a>
													<a><strong>¥<fmt:formatNumber value="${food.price * food.discount}" pattern="0.00"></fmt:formatNumber></strong></a></div>
											</li><hr>  
										</c:forEach>
									</c:if>
							</ul>
							<div style="float:right;margin-top: 10px;">                    
								总金额:<fmt:formatNumber value="${total}"  pattern="0.00"></fmt:formatNumber>
									
									<!-- 第一个参数：餐桌的id   第二个参数：更改对应table_status字段的值 -->
									<c:if test="${dinnerTable.tableStatus == 1}">
										<input type="button"  onclick="order(${dinnerTable.id})" value="下单"></input>
										<input type="button"  onclick="orderDinner(${dinnerTable.id},0)" value="取消占位"></input>  
									</c:if>
									<c:if test="${dinnerTable.tableStatus == 0}">
										<input type="button"  onclick="orderDinner(${dinnerTable.id},1)" value="占位"></input>  
									</c:if>
							</div>    
						</form>
						<div class="clearfix"> </div> 
					</div>
				</div>
			</div>
			<div class="clearfix"> </div> 
			<!--左侧购物车或订单展示结束  -->
		</div>
	</div> 
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</body>
</html>