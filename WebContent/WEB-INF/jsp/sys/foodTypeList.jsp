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
	
	//获取后台保存的disabled值
	var disabled = "${disabled}";
	//遍历菜系是否删除状态select标签中所有的option标签
	var disabledSelect = document.getElementById("disabled");
	//获取下拉框中所有的option
	var  options = disabledSelect.options;
	
	$.each( options, function(i, option){
	  $(option).attr("selected",option.value == disabled);
	});
}

function disabledChange(obj){
	//获取用户输入的关键字
	var keyword = $("#keyword").val();
	
	//获取被选中的餐桌是否删除的状态
	var disabled = obj.value;
	
	//发送请求
	window.location = "${ctx}/sys/foodType.action?keyword="+keyword+"&disabled="+disabled+"&method=list";
}
</script>
</head>
<body>
	<!-- 页面标题 -->
	<div id="TitleArea">
		<div id="TitleArea_Head"></div>
		<div id="TitleArea_Title">
			<div id="TitleArea_Title_Content">
				<img border="0" width="13" height="13"
					src="../images/sys/title_arrow.gif" /> 菜系列表
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>
	<!-- 过滤条件 -->
	<div id="QueryArea">
		<form action="${ctx }/sys/foodType.action" method="get" target="right">
			<input type="hidden" name="method" value="list">
			<input type="text" id="keyword" name="keyword" value="${keyword}" placeholder="请输入菜系名称">
			<select name="disabled" id="disabled" onchange="disabledChange(this)">
				<option value="">全部</option>
				<option value="0">未删</option>
				<option value="1">已删</option>
			</select>
			<input type="submit" value="搜索">
			<a href="${ctx}/sys/foodType.action?method=addPage" target="right"><input type="button" value="添加"></a>
		</form>
	</div>

	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" style="text-align: center;" cellspacing="0" cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>编号</td>
					<td>菜系名称</td>
					<td>创建时间</td>
					<td>是否已删除</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
				<c:choose>
					<c:when test="${not empty foodTypes}">
						<c:forEach items="${foodTypes}"  var="foodType"  varStatus="status">
							<tr>
								<td>${status.index+1}</td>
								<td>${foodType.typeName }</td>
								<td><fmt:formatDate value="${foodType.createDate }"  pattern="yyyy-MM-dd  HH:ss:mm"/> </td>
								<td>
									<c:if test="${foodType.disabled == 0}">
										未删
									</c:if>
									<c:if test="${foodType.disabled == 1}">
										已删
									</c:if>
								</td>
								<td>
									<a href="${ctx }/sys/foodType.action?id=${foodType.id}&method=viewUpdate" class="FunctionButton">更新</a>
									<c:if test="${foodType.disabled == 0}">
										<a href="${ctx }/sys/foodType.action?id=${foodType.id}&method=update&disabled=1" class="FunctionButton">删除</a>
									</c:if>
									<c:if test="${foodType.disabled == 1}">
										<a href="${ctx }/sys/foodType.action?id=${foodType.id}&method=update&disabled=0" class="FunctionButton">激活</a>
									</c:if>
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:when test="${empty foodTypes}">
						<tr>
							<td colspan="3" style="text-align: center;">没有你要找的数据！</td>
						</tr>
					</c:when>
				</c:choose>
					

			</tbody>
		</table>
	</div>
</body>
</html>
