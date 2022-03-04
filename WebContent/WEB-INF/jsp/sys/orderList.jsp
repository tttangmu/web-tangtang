<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="common.jsp" %>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!-- 包含公共的JSP代码片段 -->
<title>无线点餐平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"><!--（含有bootstrap 所有css）  -->
<!-- 日期控件所需的样式表 -->
<link href="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<!-- 日期控件所需的js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<!-- 如需支持简体中文的显示，就需要加载中文的资源文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
$(document).ready(function(){
	$('.form_date').datetimepicker({
		language:'zh-CN',/*语言  默认值：’en’ */
	    weekStart: 1,/* 一周从哪一天开始。0（星期日）到6（星期六） */
	    todayBtn:  1,/*当天日期将会被选中。  */
		autoclose: 1,//选择后自动关闭当前时间控件
		todayHighlight: 1,/*高亮当天日期。  */
		startView: 2,/* 从月视图开始，选中那一天  3为选月份*/
		minView: 2,/* 从月视图开始，选天   选完天后，不在出现下级时分秒时间选择 */
		forceParse: 0,/*就是你输入的可能不正规，但是它胡强制尽量解析成你规定的格式（format）  */
		/* format: "yyyy-mm-dd hh:ii:ss", //时间格式  yyyy-mm-dd hh:ii:ss  */
	});
	
});

	function blurFn(obj,orderDetailId,buyNum,vat){
		$("#"+vat).html("");
		//获取用户输入的购买数量
		var  num = obj.value;
		
		//如果用户输入的数量<1或者不是一个数字，就赋值为原来的购买数量buyNum
		if(num <1 || isNaN(num)){
			
			obj.value = buyNum;
			$("#"+vat).html("请输入正确格式的数量");
		}else if(num != buyNum){
			//反之，不符合上面两种，而且与原来的购买数量buyNum不一致，就去通过订单详情的id去修改购买数量 
			window.location.href = "${pageContext.request.contextPath}/sys/orderDetail.action?method=update&buyNum="+Math.ceil(num)+"&id="+orderDetailId;
		}
	}
	
	function deleteFn(orderDetailId){
		window.location.href = "${pageContext.request.contextPath}/sys/orderDetail.action?method=delete&id="+orderDetailId;
	}
	
	//文档加载完成后
	window.onload = function(){
		//获取用户选中查询的搜索类型
		var dinnerTableId = "${dinnerTableId}";
		
		var dinnerTableIdSelect = document.getElementById("dinnerTableId");
		var options = dinnerTableIdSelect.options;
		
		//遍历所有的option，如果option中的值=用户选中查询的搜索类型，则该option被选中
		$.each(options,function(i,option){
			$(option).attr("selected",option.value == dinnerTableId);
		});
	}
</script>
</head>
<body>
<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="../images/sys/title_arrow.gif"/> 订单管理
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>

<form name="myForm"  action="${pageContext.request.contextPath}/sys/order.action?method=list"  class="form-search" method="post"  style="padding-bottom: 0px">
		<span class="data_search">
			<span class="controls input-append date form_date" style="margin-right: 10px" data-date-format="yyyy-mm-dd">
                <input id="orderDate" name="orderDate" style="width:120px;" placeholder="下单日期" type="text" 
                		value="${orderDate}" readonly>
                <span class="add-on"><i class="icon-remove"></i></span>
				<span class="add-on"><i class="icon-th"></i></span>
           	</span>
            <input id="orderCode" name="orderCode" style="width:320px;" placeholder="订单编号" type="text" 
                		value="${orderCode}" >
			<select id="dinnerTableId" name="dinnerTableId" style="width: 100px;">
				<option value="">全部</option>
				<c:forEach items="${dinnerTables}"  var="dinnerTable">
					<option value="${dinnerTable.id }">${dinnerTable.tableName }</option>
				</c:forEach>
			</select>
			&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
		</span>
</form>
<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
    <div class="products">	 
		<div class="container">
			<div class="col-md-12 ">
				<!-- 查询 所有订单遍历  未付款的排在上面     开始-->
				<div class="rsidebar-top col-md-12">
					<!-- 遍历所有的订单 -->
					<c:if test="${not empty orders }">
						<c:forEach items="${orders}" var="order" varStatus="vat">
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
										<td>菜名</td>
										<td>价格</td>
										<td>折扣</td>
										<td>总计</td>
										<td>操作</td>
									</tr>
								</thead>
								<!--显示数据列表 -->
								<tbody id="TableData">
									<c:choose>
										<c:when test="${not empty order.orderDetails  }">
											<c:forEach  items="${order.orderDetails }"  var="orderDetail">
												<tr height="60">
											 		<td>${orderDetail.food.foodName}</td>
											 		<td>${orderDetail.food.price}</td>
											 		<td>${orderDetail.discount}</td>
											 		<td><fmt:formatNumber value="${orderDetail.food.price*orderDetail.discount}" pattern="0.00"></fmt:formatNumber> </td>
											 		<td>
											 			<c:if test="${orderDetail.disabled ==1}">
															<font  style="color: red">已删</font>
														</c:if>
														<c:if test="${orderDetail.disabled ==0 and order.orderStatus !=1}">
															<input  name=buyNum  value="${orderDetail.buyNum}"  onblur="blurFn(this,${orderDetail.id},${orderDetail.buyNum},${vat.index});"
											    					style="width: 30px;border-radius: 3px;border: 1px solid #a3a3a3;text-align: right;padding: 2px 4px;" >
															<!-- 第一个参数：餐桌的id  第二个参数：菜品的id -->
															<input type="button"  value="×" onclick="deleteFn(${orderDetail.id});" style="border-radius: 3px;border: 1px solid #a3a3a3;background: b7b7b7;"></input>
														</c:if>
											 		</td>
										 		</tr>
											</c:forEach>
										</c:when>
									</c:choose>
									
								</tbody>
							</table>
								<font color="#005791" style="font-weight: bolder;">
									<c:if test="${order.orderStatus ==1 }">
										已付
									</c:if>
									<c:if test="${order.orderStatus ==0 }">
										<c:if test="${order.disabled ==1 }">
											已取消
										</c:if>
										<c:if test="${order.disabled ==0 }">
										未付
										</c:if>
									</c:if>&nbsp;
									￥<fmt:formatNumber value="${order.totalPrice }" pattern="0.00"></fmt:formatNumber>&nbsp;
								</font>
								<c:if test="${order.disabled ==1}">
									<a style="font-weight: bolder;" href="${ctx}/sys/order.action?method=update&id=${order.id}&disabled=0" target="right">激活订单</a>				
								</c:if>
								<c:if test="${order.disabled ==0}">
									<a style="font-weight: bolder;" href="${ctx}/sys/order.action?method=update&id=${order.id}&disabled=1" target="right">删除订单</a>				
								</c:if>&nbsp;
								<c:if test="${(order.orderStatus ==0)  && (order.disabled ==0)}">
									<a    href="${ctx}/sys/order.action?method=orderAddFood&id=${order.id}" style="font-weight: bolder;">加菜</a>&nbsp;
								</c:if>
								<a  target="sypost"  href="${ctx}/sys/orderDetail.action?method=print&orderId=${order.id}" style="font-weight: bolder;">打印</a>
								<font id="${vat.index}" style="color: red"></font>
							<hr>
							</div>
						</c:forEach>
					</c:if>

					<!-- <div class="sidebar-row"><center><font color="red">暂时没有订单！</font></center></div> -->
				</div>	 
				</div>
				<div class="clearfix">&nbsp;</div>
			</div>
		</div>
</div>
</body>
</html>
