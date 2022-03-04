<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Frame top</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="../js/regex.js"></script>
<link href="../css/sys/login.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
if(window.location != parent.window.location){
	   //将当前页面作为最顶级页面
	   parent.window.location = window.location;
	   
}


//获取焦点，展示输入框的提示信息
function formOnfocus(id){
	$("#"+id+"_error").html("");
	switch (id) {
	case "loginname":
		$("#loginname_message").html("请输入您的登录名！");
		break;
	case "phone":
		$("#phone_message").html("请输入正确的手机号码！");
		break;
	case "email":
		$("#email_message").html("请输入您的常用邮箱！");
		break;	
	case "passWord":
		$("#passWord_message").html("可输入6-16位由英文或数字组成的字符");
		break;
	case "okPassWord":
		$("#okPassWord_message").html("请再次输入密码，两次密码需一致！");
		break;
	}
}

//失去焦点，判断用户输入的信息是否符合要求
function formOnblur(id){
	var  flag = false;
	//对一个输入框进行失去焦点判断时，先清除其提示信息
	$("#"+id+"_message").html("");
	//拿到当前输入框的值
	var fieldValue =document.getElementById(id).value;
	if(id == "loginname"){
		if(fieldValue == null || fieldValue ==""){
			$("#loginname_error").html("用户名不能为空");
		}else{
			//判断用户输入的用户名是否已经在数据库存在
			jQuery.ajax({
				   type: "POST",
				   url: "${ctx}/sys/register.do?method=ajaxLoginName",
				   data: "loginname="+fieldValue, 
				   dataType:"text",
				   async:false,
				   success: function(msg){
					   if(msg){
						   $("#loginname_error").html(msg);
                    	  //$("#loginname").val("");
                      }else{
                    	  flag = true;
                      }
				   },error:function(){
					   alert("数据加载异常");
				   }
			})
		}
	}else if(id == "phone"){
		if(fieldValue == null || fieldValue ==""){
			$("#phone_error").html("电话号码不能为空");
		}else{
			//检查电话号码格式是否正确
			if(!checkPhone(fieldValue)){
				//请输入正确的手机号码！
				$("#phone_error").html("您输入的电话号码格式不正确，请重新输入！");
			}else{
				flag = true;
			}
		}
	}else if(id == "email"){
		if(fieldValue == null || fieldValue ==""){
			$("#email_error").html("邮箱地址不能为空");
		}else{
			//用户已经输入邮箱，校验格式是否正确
			if(!checkEmail(fieldValue)){
				$("#email_error").html("您输入的邮箱格式不正确，请重新输入！");
			}else{
				flag = true;
			}
		}
	}else if(id == "passWord"){
		if(fieldValue == null || fieldValue ==""){
			$("#passWord_error").html("密码不能为空！");
		}else{
			if(!checkPassword(fieldValue)){
				$("#passWord_error").html("格式不正确，需6-16位由英文或数字组成的字符！");
			}else{
				flag = true;
			}
		}
	}else if(id == "okPassWord"){
		if(fieldValue == null || fieldValue ==""){
			$("#okPassWord_error").html("请再次输入密码！");
		}else if(fieldValue != $("#passWord").val()){
			$("#okPassWord_error").html("两次输入的密码不一致！");
		}else{
			flag = true;
		}
	}
	
	return flag;
}

function onRegister(){
	//获取所有输入框的ID值
	var ids = ["loginname","phone","email","passWord","okPassWord"];
	
	//遍历数组
	for (var i = 0; i < ids.length; i++) {
		if(!formOnblur(ids[i])){
			return false;
		}
	}
	
	//提交注册表单
	document.getElementById("registerform").submit();
}
</script>
</head>

<body>
 	
	<!-- 上部 -->
	<div id="Head_1">
		<!-- 标题 -->
		<div id="Head_1_Logo">
			<b style="font-family: '黑体'">点餐系统后台注册</b>
		</div>
		<div id="Head_1_FunctionButton"> 
			<a  href="${ctx }/sys/login.do"  style="color: white;">
				登录
			</a>
      		</div>
	</div>
	<div class="logo_box">
		<form action="${ctx }/sys/register.do" method="post" id="registerform" class="forms"> 
		<input type="hidden"  name="method" value="submitTable">
		<center> <font color="red"></font></center>
		<div class="input_outer">
			<span class="u_user"></span>
			<input name="loginname" id="loginname" autocomplete="off"  onfocus="formOnfocus(this.id);" onblur="formOnblur(this.id);" class="text"   placeholder="请输入登录名" style="color: black !important;ba" type="text">
			<div id="loginname_message" style="clear:both;color: black;"></div>
			<label id="loginname_error" style="color: black;"></label>
		</div>
		
		<div class="input_outer">
			<span class="u_phone"></span>
			<input name="phone" placeholder="手机号码" autocomplete="off" id="phone" onfocus="formOnfocus(this.id);" onblur="formOnblur(this.id);" class="text"   placeholder="请输入手机号码" style="color: black !important" type="text">
			<div id="phone_message" style="clear:both;color: black;"></div>
			<label id="phone_error" style="color: black;"></label>
		</div>
		
		<div class="input_outer">
			<span class="u_email"></span>
			<input name="email" placeholder="email"  id="email" autocomplete="off" onfocus="formOnfocus(this.id);" onblur="formOnblur(this.id);" class="text"   placeholder="请输入用户名" style="color: black !important" type="text">
			<div id="email_message" style="clear:both;color: black;"></div>
			<label id="email_error" style="color: black;"></label>
		</div>
		
		<div class="input_outer">
			<span class="u_pass"></span>
			<input name="passWord" placeholder="密码" autocomplete="off"  id="passWord" onfocus="formOnfocus(this.id);" onblur="formOnblur(this.id);" class="text"   placeholder="请输入用户名" style="color: black !important" type="text">
			 <div id="passWord_message" style="clear:both;color: black;"></div>
			<label id="passWord_error" style="color: black;"></label>
		</div>
		
		<div class="input_outer">
			<span class="u_pass"></span>
			<input name="okPassWord" placeholder="确认密码" autocomplete="off"  id="okPassWord" onfocus="formOnfocus(this.id);" onblur="formOnblur(this.id);" class="text"   placeholder="请输入用户名" style="color: black !important" type="text">
			 <div id="okPassWord_message" style="clear:both;color: black;"></div>
			 <label id="okPassWord_error" style="color: black;"></label>
		</div>
            
		<div class="mb2">
			<a class="act-but submit" href="javascript:;" onclick="onRegister()"  style="color: black">注册</a>
		</div>
		
	</form>
	</div>
</body>
</html>