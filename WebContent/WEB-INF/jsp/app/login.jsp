<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<!-- Custom Theme files -->
<script type="text/javascript">
	//在页面完成加载时调用
	window.onload = function(){
		 document.getElementById("loginname").focus();
		document.onkeydown = function(){
			// firefox没有window.event对象
			var event = arguments[0] ? arguments[0] : window.event;
			if (event.keyCode === 13){
				submitTable();
			}
		};
	};

	function submitTable(){
		//先判断用户是否输入用户名和密码，如其中一个未输入，则不需要提交表单中的内容到数据库
		var loginname = document.getElementById("loginname").value;
		var password = document.getElementById("password").value;
		
		if(loginname == null || loginname == ""){
			$("#message").val("请输入用户名");
			//用户名的输入框获取焦点
			document.getElementById("loginname").focus;
			//阻止默认行为
			return false;
		}
		
		if(password == null || password == ""){
			$("#message").val("请输入密码");
			//用户名的输入框获取焦点
			document.getElementById("password").focus;
			return false;
		}
		//提交表单
		document.getElementById("loginform").submit();
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
				<div class="container">
					<nav class="navbar navbar-default">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header w3l_logo">
							<h1><a href="${pageContext.request.contextPath}/app/index.do">点餐系统</a></h1>
						</div> 
						<div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
							<ul class="nav navbar-nav navbar-right">
								<li><a href="${pageContext.request.contextPath}/app/index.do" class="active">主页</a></li>	
								<!-- Mega Menu -->
								<li class="dropdown">
									<a href="${pageContext.request.contextPath}/app/menuList.do" class="dropdown-toggle" ">菜单 </a>
								</li>
								<li class="w3pages">
									<a href="${pageContext.request.contextPath}/app/order.action?method=list">我的订单</a>
								</li>  
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
	<div class="container">	
		<ol class="breadcrumb w3l-crumbs">
			<li><a href="app/index.do"><i class="fa fa-home"></i> 主页</a></li> 
			<li class="active">登录</li>
		</ol>
	</div>
	<!-- //breadcrumb -->
	<!-- login-page -->
	<div class="login-page about">
		<div class="container"> 
			<h3 class="w3ls-title w3ls-title1">登录您的账户</h3>  
			<div class="login-agileinfo"> 
				<form action="${pageContext.request.contextPath}/app/login.do" method="post" id="loginform"  onsubmit=" return submitTable()"> 
					<input type="hidden"  name="method" value="submitTable">
					<center> <font color="red" id="message">${message}</font></center>
					<input class="agile-ltext" type="text" id="loginname" name="loginname" placeholder="请输入用户名" value="" required="">
					<input class="agile-ltext" type="password" id="password" name="password" placeholder="请输入密码"  required="">
					<div class="wthreelogin-text"> 
						<ul> 
							<li>
								<label class="checkbox">
									<input type="checkbox" id="remenber" name="remenber" value="reme"><i></i> 
									记住我 ? 
								</label> 
							</li>
						</ul>
						<div class="clearfix"> </div>
					</div>   
					<input type="submit" onclick="submitTable()"  value="登录">
				</form>
				<p>没有帐号? <a href="${pageContext.request.contextPath}/app/register.do"> 现在注册!</a></p> 
			</div>	 
		</div>
	</div>
</body>
</html>