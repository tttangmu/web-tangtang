<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="common.jsp" %>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!-- 包含公共的JSP代码片段 -->
<title>无线点餐平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- regex.js是正则表达式的一系列判断 -->
<script type=text/javascript src="${pageContext.request.contextPath}/js/regex.js"></script>
<script type="text/javascript">
	
function checkEmailBlur(obj){
	document.getElementById("error").innerHTML="";
	if(!checkEmail(obj.value)){
		document.getElementById("error").innerHTML="邮箱格式不正确！";
	}
}

function checkPhoneOnblur(obj){
	document.getElementById("error").innerHTML="";
	if(!checkPhone(obj.value)){
		document.getElementById("error").innerHTML="手机号码格式不正确！";
	}
	
}
function checkForm(){
	var loginName=document.getElementById("loginName").value;
	var password=document.getElementById("password").value;
	var phone=document.getElementById("phone").value;
	var Okpassword=document.getElementById("Okpassword").value;
	var email=document.getElementById("email").value;
	
	if(loginName=="" ||password==""||phone=="" ||password==""||Okpassword==""||email==""){
		document.getElementById("error").innerHTML="信息填写不完整！";
		return false;
	} else if(password !=Okpassword){
		document.getElementById("error").innerHTML="密码填写不一致！";
		return false;
	}else if(!checkPhone(phone)){ 
		document.getElementById("error").innerHTML="手机号码格式错误！";
        return false; 
    }else if(!checkEmail(email)){
		return false; 
	}
	return true; 
}

	
	
</script>
</head>
<body>

<!-- 页面标题 -->
<div id="TitleArea">
	<div id="TitleArea_Head"></div>
	<div id="TitleArea_Title">
		<div id="TitleArea_Title_Content">
			<img border="0" width="13" height="13" src="../images/sys/title_arrow.gif"/> 添加新用户
		</div>
    </div>
	<div id="TitleArea_End"></div>
</div>

<!-- 主内容区域（数据列表或表单显示） -->
<div id="MainArea">
	 <!-- from标签必须设置  enctype="multipart/form-data" 默认post -->
	<form id="form" action="${ctx}/sys/userList.action" method="post" onsubmit="return checkForm()" >
		<input type="hidden" name="method" value="addSubmit">
		<input type="hidden" name="id" value="${user.id} ">
		<!-- 本段标题（分段标题） -->
		<div class="ItemBlock_Title">
        	<img width="4" height="7" border="0" src="../images/sys/item_point.gif">用户信息&nbsp;<font id="error" color="red"></font>
        </div>
		<!-- 本段表单字段 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
				<div class="ItemBlock2">
					<table cellpadding="0" cellspacing="0" class="mainForm">
						<tr>
							<td width="80px">用户名</td>
							<td><input type="text" disabled="disabled" id="loginName" name="loginName"  value="${user.loginName }" class="InputStyle"/> *</td>
						</tr>
						 <tr>
							<td>密码</td>
							<td><input type="password"  value="${user.password }" id="password" name="password" class="InputStyle" /></td>
						</tr>
						 <tr>
							<td>确认密码</td>
							<td><input type="password"   value="" id="Okpassword" name="Okpassword" class="InputStyle" /></td>
						</tr>
						<tr>
							<td>邮箱</td>
							<td><input type="text"  id="email" name="email"  onblur="checkEmailBlur(this)" value="${user.email }" class="InputStyle" /> *</td>
						</tr>
                        <tr>
							<td>电话号码</td>
							<td><input type="text"  onblur="checkPhoneOnblur(this)" value="${user.phone }" id="phone" name="phone" class="InputStyle" /></td>
						</tr>
					</table>
				</div>
            </div>
        </div>
		
		
		<!-- 表单操作 -->
		<div id="InputDetailBar">
			<input type="submit"  value="添加" class="FunctionButtonInput">
            <a href="javascript:history.go(-1);" class="FunctionButton">返回</a>
        </div>
	</form>
</div>
</body>
</html>
