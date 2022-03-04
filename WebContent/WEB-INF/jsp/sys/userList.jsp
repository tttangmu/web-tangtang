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
	var disabledSelect = document.getElementById("disabled");
	//获取下拉框中所有的option
	var  options = disabledSelect.options;
	
	$.each( options, function(i, option){
	  $(option).attr("selected",option.value == disabled);
	});
	
	
	var searchType = "${searchType}";
	var searchTypeSelect = document.getElementById("searchType");
	//获取下拉框中所有的option
	var  options = searchTypeSelect.options;
	
	$.each( options, function(i, option){
	  $(option).attr("selected",option.value == searchType);
	});
}

function disabledChange(obj){
	//获取用户输入的关键字
	var keyword = $("#keyword").val();
	
	//获取被选中的餐桌是否删除的状态
	var disabled = obj.value;
	
	//发送请求
	window.location = "${ctx}/sys/userList.action?keyword="+keyword+"&disabled="+disabled+"&method=list&searchType="+$("#searchType").val();
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
					src="../images/sys/title_arrow.gif" /> 用户列表
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>
	<!-- 过滤条件 -->
	<div id="QueryArea">
		<form action="${ctx }/sys/userList.action" method="get" target="right">
			<input type="hidden" name="method" value="list">
			<input type="text" id="keyword" name="keyword" value="${keyword}"  >
			<!-- 餐桌使用状态的select标签 -->
			<select name="searchType" id="searchType" onchange="typeChange(this)" >
				<option value="name">姓名</option>
				<option value="email">邮箱</option>
				<option value="phone">电话号码</option>
			</select>
			<select name="disabled" id="disabled" onchange="disabledChange(this)">
				<option value="">全部</option>
				<option value="0">未删</option>
				<option value="1">已删</option>
			</select>
			<input type="submit" value="搜索">
			<a href="${ctx}/sys/userList.action?method=addPage" target="right"><input type="button" value="添加"></a>
		</form>
	</div>

	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" style="text-align: center;" cellspacing="0" cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>序号</td>
					<td>姓名</td>
					<td>邮箱</td>
					<td>电话号码</td>
					<td>创建人</td>
					<td>创建时间</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
				<c:choose>
					<c:when test="${not empty users}">
						<c:forEach items="${users}"  var="user"  varStatus="status">
							<tr>
								<td>${status.index+1}</td>
								<td>${user.loginName }</td>
								<td>${user.email }</td>
								<td>${user.phone }</td>
								<td>${user.createUser.loginName }</td>
								<td><fmt:formatDate value="${user.createDate }"  pattern="yyyy-MM-dd  HH:ss:mm"/> </td>
								<td>
									<c:if test="${user.disabled == 0}">
										未删
									</c:if>
									<c:if test="${user.disabled == 1}">
										已删
									</c:if>
								</td>
								<td>
									<a href="${ctx }/sys/userList.action?id=${user.id}&method=viewUpdate" class="FunctionButton">更新</a>
									<c:if test="${user.disabled == 0}">
										<a href="${ctx }/sys/userList.action?id=${user.id}&method=update&disabled=1" class="FunctionButton">删除</a>
									</c:if>
									<c:if test="${user.disabled == 1}">
										<a href="${ctx }/sys/userList.action?id=${user.id}&method=update&disabled=0" class="FunctionButton">激活</a>
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
