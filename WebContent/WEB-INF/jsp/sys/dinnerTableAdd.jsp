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
	function  addSubmit(){
		//获取餐桌名
		var tableName = $("#tableName").val();
		
		//判断用户是否已输入餐桌名
		if(tableName != null && tableName != ""){
			//已输入，则判断餐桌名字是否在数据库存在，如已存在，则提示   餐桌名已存在，请重新输入
			//如餐桌名在数据库中没有，则保存新增的餐桌名
			$.ajax({
				   type: "POST",
				   url: "${ctx}/sys/dinnerTable.action?method=addSubmit",
				   data: "tableName="+tableName,
				   success: function(msg){
					   //alert("msg:"+msg.trim()+"typeof:"+typeof msg);
				     if(msg.trim() == "success"){
				    	 //用户输入的餐桌名在数据库中不存在，则新增
				    	 $("#message").html("保存成功！");
				    	 $("#tableName").val("");
				     }else{
				    	//用户输入的餐桌名在数据库中已经存在
				    	 $("#message").html("当前餐桌名字已经存在，请重新输入！");
				    	 $("#tableName").val("");
				     }
				   }
				});
			
			
		}else{
			//判断用户是否已输入餐桌名，未输入，则提示 请输入餐桌名
			$("#message").html("请输入菜系名称！");
		}
		
		
		
		
	}

</script>
</head>
<body>


<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="${ctx}/images/sys/title_arrow.gif"/>  添加餐桌
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>


<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
	<!-- 表单内容 -->
	<form action="" method="post">
		<!-- 本段标题（分段标题） -->
		<div class="ItemBlock_Title">
        	<img width="4" height="7" border="0" src="${ctx}/images/sys/item_point.gif"> 新桌信息&nbsp;
        </div>
		<!-- 本段表单字段 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
				<div class="ItemBlock2">
					<table cellpadding="0" cellspacing="0" class="mainForm">
						<tr>
							<td width="80px">新桌名字</td>
							<td>
								<input type="text" id="tableName"  name="tableName" class="InputStyle"/>*
								<label color="red" id="message"></label>
							</td>
						</tr>
					</table>
				</div>
            </div>
        </div>
		
		<!-- 表单操作 -->
		<div id="InputDetailBar">
            <input type="button" onclick="addSubmit()" value="添加" class="FunctionButtonInput">
            <a href="javascript:history.go(-1);" class="FunctionButton">返回</a>
        </div>
	</form>
	
</div>

</body>
</html>
