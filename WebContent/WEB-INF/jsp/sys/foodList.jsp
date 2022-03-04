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
	//文档加载完后
	window.onload=function(){
		
		//获取后台保存的foodTypeId值
		var foodTypeId = "${foodTypeId}";
		//获取菜系select标签
		var foodTypeIdSelect = document.getElementById("foodTypeId");
		//获取下拉框中所有的option
		var  options = foodTypeIdSelect.options;
		
		//遍历菜系select标签中所有的option标签
		$.each( options, function(i, option){
		  $(option).attr("selected",option.value == foodTypeId);
		});
	}
	
	function foodTypeChange(obj){
		//获取用户输入的关键字
		var keyword = $("#keyword").val();
		
		//获取被选中的菜品类型值
		var foodTypeId = $("#foodTypeId option:selected").val();
		
		//发送请求
		window.location = "${ctx}/sys/food.action?keyword="+keyword+"&foodTypeId="+foodTypeId+"&method=list";
	}
</script>
</head>
<body>
<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="../images/sys/title_arrow.gif"/> 菜品列表
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>


	<!-- 过滤条件 -->
	<div id="QueryArea">
		<form action="${ctx }/sys/food.action" method="get"  target="right">
			<input type="hidden" name="method" value="list">
			<input type="text" id="keyword" name="keyword" value="${keyword}" placeholder="请输入菜品名称">
			<select name="foodTypeId" id="foodTypeId"  onchange="foodTypeChange()">
				<option value="">全部</option>
					<c:forEach items="${foodTypes}"  var="foodType">
						<option value="${foodType.id}">${foodType.typeName}</option>
					</c:forEach>
			</select>
			<input type="submit" value="搜索">
			<a href="${ctx}/sys/food.action?method=addPage" target="right"><input type="button" value="添加"></a>
		</form>
	</div>
<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
    <table class="MainArea_Content" style="text-align: center;" cellspacing="0" cellpadding="0">
        <!-- 表头-->
        <thead>
            <tr align="center" valign="middle" id="TableTitle">
				<td>菜编号</td>
				<td>菜名</td>
				<td>所属菜系</td>
				<td>价格</td>
                <td>折后价格</td>
                <td>创建时间</td>
                <td>修改时间</td>
                <td>是否删除</td>
				<td>操作</td>
			</tr>
		</thead>	
		<!--显示数据列表 -->
        <tbody id="TableData">
        	<c:choose>
        		<c:when test="${not empty foods }">
        			<c:forEach  items="${foods}"  var="food" varStatus="status">
        				<tr class="TableDetail1">
							<td>${status.index+1}&nbsp;</td>
							<td>${food.foodName}&nbsp;</td>
							<td>${food.foodType.typeName}&nbsp;</td>
							<td><fmt:formatNumber value="${food.price}"  pattern="0.00"></fmt:formatNumber> &nbsp;</td>
			                <td><fmt:formatNumber value="${food.price*food.discount}"  pattern="0.00"></fmt:formatNumber>&nbsp;</td>
			                <td><fmt:formatDate value="${food.createDate}" pattern="yyyy-MM-dd  HH:mm:ss"/> </td>
			                <td><fmt:formatDate value="${food.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
			                <td>
			                	<c:if test="${food.disabled == 1}">
									已删
								</c:if>
								<c:if test="${food.disabled == 0}">
									未删
								</c:if>
							</td>
							<td>
								<a href="${ctx}/sys/food.action?method=viewUpdate&id=${food.id}"  class="FunctionButton">更新</a>
								<c:if test="${food.disabled == 1}">
									<a href="${ctx}/sys/food.action?method=update&id=${food.id}&disabled=0" class="FunctionButton">激活</a>				
								</c:if>
								<c:if test="${food.disabled == 0}">
									<a href="${ctx}/sys/food.action?method=update&id=${food.id}&disabled=1" class="FunctionButton">删除</a>				
								</c:if>
							</td>
						</tr>
        			</c:forEach>
        		</c:when>
        	
        		<c:when test="${ empty foods }">
        			<tr class="TableDetail1" >
							<td colspan="9">满足当前条件的菜品没有找到！</td>
					</tr>
				</c:when>
        	</c:choose>
        </tbody>
    </table>
	
</div>
</body>
</html>
