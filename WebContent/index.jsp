<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<% request.setAttribute("base", request.getContextPath()); %>

<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
    <title><spring:message code="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=0">
    <meta name="description" content="">
    <meta name="decorator" content="">
    <meta name="author" content="">
    <link rel="icon" href="${base}/dist/img/user.jpg" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/dist/img/user.jpg" type="image/x-icon" />

    <!-- Bootstrap core CSS -->
    <link href="${base}/dist/css/bootstrap/bootstrap.min.css" rel="stylesheet">

	<!-- Jquery -->
	<script type="text/javascript" src="${base}/dist/js/jquery/jquery-1.10.2.min.js"></script>
	<!-- form validate -->
    <script type="text/javascript" src="${base}/dist/js/parsley.min.js"></script>
    <!-- form submit -->
    <script type="text/javascript" src="${base}/dist/js/form/jquery.form.min.js"></script>
	
<style type="text/css">
	body {
	  padding-top: 40px;
	  padding-bottom: 40px;
	  background-color: #eee;
	}
	
	.form-signin {
	  max-width: 330px;
	  padding: 15px;
	  margin: 0 auto;
	}
	.form-signin .form-signin-heading, .form-signin .checkbox {
	  margin-bottom: 10px;
	}
	.form-signin .form-signin-heading {
	  text-align: center;
	}
	.form-signin .sr-only {
	  position: static;
	}
	.form-signin .checkbox {
	  font-weight: normal;
	}
	.form-signin .form-control {
	  position: relative;
	  height: auto;
	  -webkit-box-sizing: border-box;
	     -moz-box-sizing: border-box;
	          box-sizing: border-box;
	  padding: 10px;
	  font-size: 16px;
	}
	.form-signin .form-control:focus {
	  z-index: 2;
	}
	.form-signin input[type="email"] {
	  margin-bottom: -1px;
	  border-bottom-right-radius: 0;
	  border-bottom-left-radius: 0;
	}
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}
</style>
<script type="text/javascript">
//处理session超时或者服务器重启后导致 使用iframe页面时，无法再父窗口打开login.jsp
function _checkparent(){
	if(window.parent.length>0){
		window.parent.location="login.jsp";
	}
}
$(document).ready(function() {
	$("#login-form").submit(function() {debugger;
		if($(this).parsley().isValid()) {
			$(this).ajaxSubmit({
	            type: 'POST',
	            dataType: 'json',
	            resetForm: true,
	            success: function(response, status, xhr, form) {
	              	console.log(response);
	            },
	            error: function(xhr, status, error, form) {
	            	if(xhr && xhr == 'success'){
	            		window.location="${base}/auth/loginsuccess";
	            	}else{
	            		window.location="${base}/auth/index";
	            	}
	            }
	        });
        }
        
        return false;
	}).find("input").get(0).focus();
});
</script>
  </head>
  
  <body>
   
	<div class="container">
	
		<form class="form-signin" id="login-form" method="post" action="${base}/auth/login">
        	<h2 class="form-signin-heading"><spring:message code="title"/></h2>
        	<label for="inputUsername" class="sr-only"><spring:message code="user.name"/></label>
        	<input type="text" name="username" id="inputUsername" class="form-control" placeholder="用户名" data-required="true" autofocus/>
        	<label for="inputPassword" class="sr-only"><spring:message code="user.pswd"/>密码</label>
        	<input type="password" name="pswd" id="inputPassword" class="form-control" placeholder="密码" data-required="true" />
        	<div class="checkbox">
	          	<label id="error_message" style="color: fc4343; font-size:14px;">
	            	<!-- <input type="checkbox" value="remember-me"> Remember me -->
	          	</label>
        	</div>
        	<button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="plan.login"/></button>
      	</form>
			
	</div>

  </body>
</html>

