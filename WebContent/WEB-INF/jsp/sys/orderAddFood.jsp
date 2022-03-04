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
	
	function blurFn(obj,orderDetailId,buyNum,vat){
		$("#"+vat).html("");
		//获取用户输入的购买数量
		var  num = obj.value;
		
		//如果用户输入的数量<1或者不是一个数字，就赋值为原来的购买数量buyNum
		if(num <1 || isNaN(num)){
			
			obj.value = buyNum;
			$("#"+vat).html("请输入正确格式的数量");
		}else if(num != buyNum){
			//反之，不符合上面两种，而且与原来的购买数量buyNum不一致，就去通过餐桌的id找到对应购买车去修改购买数量 
			window.location.href = "${pageContext.request.contextPath}/sys/orderDetail.action?method=update&buyNum="+Math.ceil(num)+"&id="+orderDetailId;
		}
	}
	
	function deleteFn(orderDetailId){
		window.location.href = "${pageContext.request.contextPath}/sys/orderDetail.action?method=delete&id="+orderDetailId;
	}
	
	function submit(){
       var foodIdEles=document.getElementsByName("foodId"); 
       
       var foodIds = new Array(); 
       var index = 0;
       for(var i=0;i<foodIdEles.length;i++){
           if(foodIdEles[i].checked==true){
        	   foodIds[index]=foodIdEles[i].value;
        	   index++;
        	}
	   }
       window.location.href = "${pageContext.request.contextPath}/sys/orderDetail.action?method=addFoodSubmit&foodIds="+foodIds+"&orderId=${order.id}";
	}
</script>
</head>
<body>
<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="../images/sys/title_arrow.gif"/>订单加菜
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>
<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
    <div class="products">	 
		<div class="container">
			<div class="col-md-12 ">
				<!-- 查询 所有订单遍历  未付款的排在上面     开始-->
				<div class="rsidebar-top col-md-12">
					<div class="sidebar-row">
						<h4>
							<span class="col-md-10">
								${order.dinnerTable.tableName} &nbsp;
								订单编号：${order.orderCode}&nbsp;
								下单时间：<fmt:formatDate value="${order.orderDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
							</span>
						</h4>
						<table  style="text-align: center;" cellspacing="0" cellpadding="0" width="500px">
						<!-- 表头-->
						<thead>
							<tr align="center" valign="middle" >
								<td>操作</td>
								<td>菜名</td>
								<td>价格</td>
								<td>折扣</td>
								<td>折后</td>
							</tr>
						</thead>
						<!--显示数据列表 -->
						<tbody id="TableData">
							<c:choose>
								<c:when test="${not empty foods  }">
									<c:forEach  items="${foods }"  var="food">
										<c:if test="${food.disabled ==0 }">
										<tr height="30">
											<td><input type="checkbox" name="foodId" value="${food.id}"> </td>
									 		<td>${food.foodName}</td>
									 		<td><fmt:formatNumber value="${food.price}" pattern="0.00"></fmt:formatNumber> </td>
									 		<td>${food.discount}</td>
									 		<td><fmt:formatNumber value="${food.price*food.discount}" pattern="0.00"></fmt:formatNumber> </td>
								 		</tr>
								 		</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									当前没有菜品，请先添加菜品！
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div style="padding-left: 200px;"><input onclick='submit()' type="button" name="method" value="确定"></div>
					<hr>
					</div>
				</div>	 
				</div>
				<div class="clearfix">&nbsp;</div>
			</div>
		</div>
</div>
</body>
</html>
