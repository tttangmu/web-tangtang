<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="common.jsp" %>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<!-- 包含公共的JSP代码片段 -->
<title>无线点餐平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	//文档加载完后
	window.onload=function(){
		//获取后台保存的tableStatus值
		var tableStatus = "${tableStatus}";
		
		//遍历餐桌使用状态select标签中所有的option标签
		var tableStatusSelect = document.getElementById("tableStatus");
		//获取下拉框中所有的option
		var  options = tableStatusSelect.options;
		
		//如option标签的value值域后台保存的tableStatus值一致
		//则该option的状态为被选中
		$.each( options, function(i, option){
		  $(option).attr("selected",option.value == tableStatus);
		});
		
		
		//获取后台保存的disabled值
		var disabled = "${disabled}";
		//遍历餐桌是否删除状态select标签中所有的option标签
		var disabledSelect = document.getElementById("disabled");
		//获取下拉框中所有的option
		var  options = disabledSelect.options;
		
		$.each( options, function(i, option){
		  $(option).attr("selected",option.value == disabled);
		});
	}
	
	function tableStatusChange(obj){
		//获取用户输入的关键字
		var keyword = $("#keyword").val();
		
		//获取被选择的餐桌使用状态值
		var  tableStatus = obj.value;
		
		//获取被选中的餐桌是否删除的状态
		var disabled = $("#disabled option:selected").val();
		//发送请求
		window.location = "${ctx}/sys/dinnerTable.action?keyword="+keyword+"&tableStatus="+tableStatus+"&disabled="+disabled+"&method=list";
	}
	
	function disabledChange(obj){
		//获取用户输入的关键字
		var keyword = $("#keyword").val();
		
		//获取被选择的餐桌使用状态值
		var tableStatus =  $("#tableStatus option:selected").val();
		
		//获取被选中的餐桌是否删除的状态
		var disabled = obj.value;
		
		//发送请求
		window.location = "${ctx}/sys/dinnerTable.action?keyword="+keyword+"&tableStatus="+tableStatus+"&disabled="+disabled+"&method=list";
	}
	
</script>
</head>
<body>
<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="${ctx}/images/sys/title_arrow.gif"/> 餐桌列表
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>


<!-- 过滤条件 -->
<div id="QueryArea">
	<form action="${ctx}/sys/dinnerTable.action" method="get" target="right">
		<input type="hidden" name="method" value="list">
		<input type="text" id="keyword" name="keyword" value="${keyword}" placeholder="请输入餐桌名称" >
		<!-- 餐桌使用状态的select标签 -->
		<select name="tableStatus" id="tableStatus" onchange="tableStatusChange(this)" >
			<option value="">全部</option>
			<option value="0">未使用</option>
			<option value="1">正在使用</option>
		</select>
		<!-- 餐桌是否删除的select标签 -->
		<select name="disabled" id="disabled" onchange="disabledChange(this)">
			<option value="">全部</option>
			<option value="0">未删</option>
			<option value="1">已删</option>
		</select>
		<input type="submit" value="搜索">
		<!-- 其他功能超链接 -->
		<a href="${ctx}/sys/dinnerTable.action?method=addPage" target="right"><input type="button" value="添加"></a>
	</form>
</div>


<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
    <table class="MainArea_Content" cellspacing="0" cellpadding="0">
        <!-- 表头-->
        <thead>
            <tr align="center" valign="middle" id="TableTitle">
				<td>编号</td>
				<td>桌名</td>
				<td>餐桌使用状态</td>
				<td>客人启用时间</td>
				<td>餐桌是否删除</td>
				<td>操作</td>
			</tr>
		</thead>	
		<!--显示数据列表 -->
        <tbody id="TableData">
        	<c:forEach  items="${dinnerTables}"  var="dinnerTable" varStatus="status">
				<tr class="TableDetail1">
					<td align="center">${status.index+1}&nbsp;</td>
					<td align="center">${dinnerTable.tableName}&nbsp;</td>
					<td align="center">
						<c:if test="${dinnerTable.tableStatus == 1}">
							正在使用
						</c:if>
						<c:if test="${dinnerTable.tableStatus == 0}">
							未使用
						</c:if>
					</td>
					<td align="center"><fmt:formatDate value="${dinnerTable.beginUseDate}"  pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td align="center">
						<c:if test="${dinnerTable.disabled == 1}">
							已删除
						</c:if>
						<c:if test="${dinnerTable.disabled == 0}">
							未删除
						</c:if>
					</td>
					<td>
						<c:if test="${dinnerTable.disabled == 1}">
							<a href="${ctx }/sys/dinnerTable.action?method=update&id=${dinnerTable.id}&disabled=0" 
							class="FunctionButton"	target="right">激活</a>
						</c:if>
						
						<c:if test="${dinnerTable.disabled == 0}">
							<a href="${ctx }/sys/dinnerTable.action?method=update&id=${dinnerTable.id}&disabled=1" 
								class="FunctionButton"  target="right">删除</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
        </tbody>
    </table>
	
</div>
</body>
</html>
