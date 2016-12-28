<%@ page language="java" contentType="text/html; charset=UTF-8"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<% 
request.setAttribute("base", request.getContextPath());
request.setAttribute("dist", request.getContextPath()+"/dist");
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="title"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="description" content="">
		<meta name="author" content="">
		
		<!-- Bootstrap core CSS -->
		<link href="${dist}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
		
		<!-- Font Awesome -->
		<link href="${dist}/css/font-awesome.min.css" rel="stylesheet">
		
		<!-- Endless http://minetheme.com/Endless1.5.1/-->
		<link href="${dist}/css/endless.min.css" rel="stylesheet">
	
	</head>
<style>
	body{
		background-image:url(./img/background.png);
		background-repeat: no-repeat;
		background-position: 50% 0;
		background-color: #2360bc;
		min-width:1000px;
		min-height:600px;
	}
	.login-wrapper {
		top: 10%;
		min-height: 500px;
		min-width: 1000px;
	}
	.login-header{
		position: relative;
		width:100%;
		/* margin: 20px auto; */
	}
	.login-widget{
		margin: 70px auto 0 !important;
		width:420px !important;
		height: 300px;
	}
	.login-footer{
		position: static;
		margin: 10% auto 100px;
		bottom: 100px;
		width: 100%;
		z-index: -1;
	}
	#loginForm{
		display: table;
		max-width: 300px;
		min-height: 230px;
		margin: 0 auto;
	}
	input[type=text]:focus, input[type=password]:focus{
		border-color: #2360bc;
		-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 10px #2360bc;
		-moz-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 10px #2360bc;
		box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 10px #2360bc;
		font-size: 16px;
		font-family: '微软雅黑';
	}
	input[type=text], input[type=password]{
		font-size: 16px !important;
		font-family: '微软雅黑';
	}
	
	.panel{
		box-shadow: 6px 6px 10px 0.4px rgba(16, 16, 16, 0.45);
		width: 420px;
		height:300px;
	}
	.panel-body{
		margin-top: 24px;
	}
	input, button{
		height:40px !important;
	}
	.form-group{
		margin-bottom:25px;
	}
</style>

  <body onload="_checkparent('${sessionScope.uid}');">
	
	<div class="login-wrapper">
			
		<div class="login-header">
			<div class="text-center">
				<h1 class="fadeInUp animation-delay8">
					<img src="./img/logo.png"></img>
				</h1>
			</div>
			<div class="text-center">
				<h3 class="fadeInUp animation-delay10" style="font-family:'黑体';font-size: 30px;">
					<span class="text-success" style="color:#fff;"><spring:message code="title"/></span>
				</h3>
			</div>
		</div>
		
		<div class="login-widget animation-delay1">	
			<div class="panel panel-default">
				<div class="panel-body">
					<form id="loginForm" name="loginForm" class="form-login" data-validate="parsley" action="${base}/auth/login" method="post">
						<label id="error_message" style="width: 100%;color: #fc4343;font-size:14px;text-align: center;"></label>
						<div class="col-lg-12 form-group">
							<!-- <label>Username</label> -->
							<input type="text" id="ruifios_name" name="username" onfocus="$('#error_message').text('');" placeholder="请输入用户名" class="form-control input-sm bounceIn animation-delay2 parsley-validated" data-required="true" data-required-message="用户名不可为空" >
						</div>
						<div class="col-lg-12 form-group">
							<!-- <label>Password</label> -->
							<input type="password" id="ruifios_ps" name="psw" onfocus="$('#error_message').text('');" placeholder="请输入密码" class="form-control input-sm bounceIn animation-delay4 parsley-validated" data-required="true" data-required-message="密码不可为空" >
						</div>
						<div class="col-lg-12 form-group">
							<button type="submit" class="btn bounceIn animation-delay6" onclick="login()" style="margin:0px;width:100%;background:#207bd8; color:white;">登录</button>
						</div>
					</form>
				</div>
			</div><!-- /panel -->
		</div><!-- /login-widget -->
		
		<div class="login-footer">
			<div class="text-center">
				<div class="fadeInUp animation-delay12" style="font-size:14px;font-family:'微软雅黑';">
					<span class="text-success" style="color:#fff;">Copyright © 2016 </span>
				</div>
			</div>
		</div>
		
	</div><!-- /login-wrapper -->
	
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    
    <!-- Jquery -->
	<script src="${dist}/js/jquery/jquery-1.10.2.min.js"></script>
 
    <!-- Bootstrap -->
    <script src="${dist}/js/bootstrap/bootstrap.js"></script>
   
	<!-- Modernizr -->
	<script src="${dist}/js/modernizr.min.js"></script>
   
    <!-- Pace -->
	<script src="${dist}/js/pace.js"></script>
   
	<!-- Popup Overlay -->
	<script src="${dist}/js/jquery/jquery.popupoverlay.min.js"></script>
   
    <!-- Slimscroll -->
	<script src="${dist}/js/jquery/jquery.slimscroll.min.js"></script>
   
	<!-- Cookie -->
	<script src="${dist}/js/jquery/jquery.cookie.min.js"></script>

	<!-- form validate -->
    <script src="${dist}/js/validate/parsley.min.js"></script>
    
	<!-- ajax submit form 
	<script src="${dist}/js/form/jquery.form.min.js"></script>-->
	<script src="${dist}/js/form/jquery.form.ext.js"></script>
	   
	<!-- Endless -->
	<script src="${dist}/js/endless.js"></script>
	   
<script type="text/javascript">
	//处理session超时或者服务器重启后导致 使用iframe页面时，无法再父窗口打开login.jsp
	function _checkparent(uid){debugger;
		if(uid){
			window.parent.location="${base}/auth/loginsuccess";
		}
		if(window.parent.length>0){
			window.parent.location="${base}/auth/index";
		}
	}
	
	/*
	* 监听密码框回车登陆
	*/
	$(document).ready(function(){
		$("#ruifios_ps").on("keyup", function(event){
			if(event.keyCode == "13")
			{
				login();
			}
		});
	});
	
	/**
	* 提交表单验证用户名密码
	*/
	function login()
	{
		if($('#loginForm').parsley().isValid()){
			$.ajaxFormSubmit({
		        url : '${base}/auth/login',
		        secureuri : false,
		        formElementId : 'loginForm',//form表单id
		        dataType : 'text',
		        success : function (data, status){debugger;
		        	if(data=="success")
		        	{
						window.location.reload(true) //浏览器重新从服务器请求资源,在http请求头中不会包含缓存标记。
					} 
		        	else
		        	{
						$('#error_message').text(data);
					}
		        },
		        error : function ( xml, status, e ){
		        	console.log(e);
		        }
			});
		}
		return false;
	}
	
</script>
	
  </body>
</html>