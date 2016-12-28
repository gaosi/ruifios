<%@ include file="/WEB-INF/template/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title><spring:message code="title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	
	<!-- Bootstrap core CSS -->
	<link href="${dist}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
	
	<!-- Font Awesome -->
	<link href="${dist}/css/font-awesome.min.css" rel="stylesheet">
	
	<!-- Endless http://minetheme.com/Endless1.5.1/-->
	<link href="${dist}/css/endless.min.css" rel="stylesheet">

	<!-- Pace -->
	<link href="${dist}/css/pace.css" rel="stylesheet">
	
	<!-- Color box -->
	<link href="${dist}/css/colorbox.css" rel="stylesheet">
	
	<!-- Morris -->
	<link href="${dist}/css/morris.css" rel="stylesheet"/>	
	
	<!-- Endless -->
	<link href="${dist}/css/endless.min.css" rel="stylesheet">
	<link href="${dist}/css/endless-skin.css" rel="stylesheet">

  </head>
  <body class="overflow-hidden">
  
	<div id="wrapper" class="preload">
		<!-- Overlay Div -->
		<div id="overlay" class="transparent"></div>
	  
		<div id="top-nav" class="skin-6 fixed">
			<div class="brand">
				<span>Endless</span>
				<span class="text-toggle"> Admin</span>
			</div><!-- /brand -->
			<button type="button" class="navbar-toggle pull-left" id="sidebarToggle">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<button type="button" class="navbar-toggle pull-left hide-menu" id="menuToggle">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<ul class="nav-notification clearfix">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
						<i class="fa fa-envelope fa-lg"></i>
						<span class="notification-label bounceIn animation-delay4">7</span>
					</a>
					<ul class="dropdown-menu message dropdown-1">
						<li><a>You have 4 new unread messages</a></li>					  
						<li>
							<a class="clearfix" href="javascript:void(0);">
								<img src="${dist}/img/user.jpg" alt="User Avatar">
								<div class="detail">
									<strong>John Doe</strong>
									<p class="no-margin">
										Lorem ipsum dolor sit amet...
									</p>
									<small class="text-muted"><i class="fa fa-check text-success"></i> 27m ago</small>
								</div>
							</a>	
						</li>
						<li>
							<a class="clearfix" href="javascript:void(0);">
								<img src="${dist}/img/user2.jpg" alt="User Avatar">
								<div class="detail">
									<strong>Jane Doe</strong>
									<p class="no-margin">
										Lorem ipsum dolor sit amet...
									</p>
									<small class="text-muted"><i class="fa fa-check text-success"></i> 5hr ago</small>
								</div>
							</a>	
						</li>
						<li>
							<a class="clearfix" href="javascript:void(0);">
								<img src="${dist}/img/user.jpg" alt="User Avatar">
								<div class="detail">
									<strong>Bill Doe</strong>
									<p class="no-margin">
										Lorem ipsum dolor sit amet...
									</p>
									<small class="text-muted"><i class="fa fa-reply"></i> Yesterday</small>
								</div>
							</a>	
						</li>
						<li>
							<a class="clearfix" href="javascript:void(0);">
								<img src="${dist}/img/user2.jpg" alt="User Avatar">
								<div class="detail">
									<strong>Baby Doe</strong>
									<p class="no-margin">
										Lorem ipsum dolor sit amet...
									</p>
									<small class="text-muted"><i class="fa fa-reply"></i> 9 Feb 2013</small>
								</div>
							</a>	
						</li>
						<li><a href="javascript:void(0);">View all messages</a></li>					  
					</ul>
				</li>
				<li class="dropdown hidden-xs">
					<a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-tasks fa-lg"></i>
						<span class="notification-label bounceIn animation-delay5">4</span>
					</a>
					<ul class="dropdown-menu task dropdown-2">
						<li><a href="javascript:void(0);">You have 4 tasks to complete</a></li>					  
						<li>
							<a href="javascript:void(0);">
								<div class="clearfix">
									<span class="pull-left">Bug Fixes</span>
									<small class="pull-right text-muted">78%</small>
								</div>
								<div class="progress">
									<div class="progress-bar" style="width:78%"></div>
								</div>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<div class="clearfix">
									<span class="pull-left">Software Updating</span>
									<small class="pull-right text-muted">54%</small>
								</div>
								<div class="progress progress-striped">
									<div class="progress-bar progress-bar-success" style="width:54%"></div>
								</div>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<div class="clearfix">
									<span class="pull-left">Database Migration</span>
									<small class="pull-right text-muted">23%</small>
								</div>
								<div class="progress">
									<div class="progress-bar progress-bar-warning" style="width:23%"></div>
								</div>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<div class="clearfix">
									<span class="pull-left">Unit Testing</span>
									<small class="pull-right text-muted">92%</small>
								</div>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-danger " style="width:92%"></div>
								</div>
							</a>
						</li>
						<li><a href="javascript:void(0);">View all tasks</a></li>					  
					</ul>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
						<i class="fa fa-bell fa-lg"></i>
						<span class="notification-label bounceIn animation-delay6">5</span>
					</a>
					<ul class="dropdown-menu notification dropdown-3">
						<li><a href="javascript:void(0);">You have 5 new notifications</a></li>					  
						<li>
							<a href="javascript:void(0);">
								<span class="notification-icon bg-warning">
									<i class="fa fa-warning"></i>
								</span>
								<span class="m-left-xs">Server #2 not responding.</span>
								<span class="time text-muted">Just now</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="notification-icon bg-success">
									<i class="fa fa-plus"></i>
								</span>
								<span class="m-left-xs">New user registration.</span>
								<span class="time text-muted">2m ago</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="notification-icon bg-danger">
									<i class="fa fa-bolt"></i>
								</span>
								<span class="m-left-xs">Application error.</span>
								<span class="time text-muted">5m ago</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="notification-icon bg-success">
									<i class="fa fa-usd"></i>
								</span>
								<span class="m-left-xs">2 items sold.</span>
								<span class="time text-muted">1hr ago</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="notification-icon bg-success">
									<i class="fa fa-plus"></i>
								</span>
								<span class="m-left-xs">New user registration.</span>
								<span class="time text-muted">1hr ago</span>
							</a>
						</li>
						<li><a href="javascript:void(0);">View all notifications</a></li>					  
					</ul>
				</li>
				<li class="profile dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
						<strong>John Doe</strong>
						<span><i class="fa fa-chevron-down"></i></span>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a class="clearfix" href="javascript:void(0);">
								<img src="${dist}/img/user.jpg" alt="User Avatar">
								<div class="detail">
									<strong>John Doe</strong>
									<p class="grey">John_Doe@email.com</p> 
								</div>
							</a>
						</li>
						<li><a tabindex="-1" href="javascript:void(0);" class="main-link"><i class="fa fa-edit fa-lg"></i> Edit profile</a></li>
						<li><a tabindex="-1" href="javascript:void(0);" class="main-link"><i class="fa fa-picture-o fa-lg"></i> Photo Gallery</a></li>
						<li><a tabindex="-1" href="javascript:void(0);" class="theme-setting"><i class="fa fa-cog fa-lg"></i> Setting</a></li>
						<li class="divider"></li>
						<li><a tabindex="-1" class="main-link logoutConfirm_open" href="#logoutConfirm" data-popup-ordinal="0" id="open_68053454"><i class="fa fa-lock fa-lg"></i> Log out</a></li>
					</ul>
				</li>
			</ul>
		</div><!-- /top-nav-->
		
		<!-- Logout confirmation -->
		<div class="custom-popup width-100" id="logoutConfirm" style="display: none;">
			<div class="padding-md">
				<h2 class="m-top-none" style="color:#DBC7C7;">您确定要退出<spring:message code="title"/>吗？</h2>
			</div>
	
			<div class="text-center">
				<a class="btn btn-success m-right-sm logoutConfirm_close" href="${base}/auth/logout">确定</a>
				<a class="btn btn-danger logoutConfirm_close">取消</a>
			</div>
		</div>
		
		<!-- 修改密码 -->
		<div id="editPassWord" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="topForm" name="topForm" class="" data-validate="parsley" novalidate="">
					<div class="modal-header">
						<button type="button" data-dismiss="modal" class="close" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h5 class="modal-title" id="editPassWord_title">修改密码</h5>
					</div>
					<div class="modal-body">
						<div class="form-group">
						  <label for="exampleInputEmail1">当前密码：</label>
						  <input type="password" name="oldPw" class="form-control parsley-validated" data-required="true" data-required-message="旧密码不能为空" placeholder="请输入用户当前密码">
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">新密码：</label>
						  <input type="password" name="newPw" class="form-control input-sm parsley-validated" id="newPw" data-required="true" data-required-message="新密码不能为空" placeholder="请输入新密码">
						</div>
						<div class="form-group">
						  <label for="exampleInputPassword1">确认密码：</label>
						  <input type="password" name="conPw" class="form-control input-sm parsley-validated" data-equalto="#newPw" data-equalto-message="输入的两次密码不相同" data-required="true" data-required-message="确认密码不能为空" placeholder="请输入确认密码">
						  <span id="errorPs"></span>
						</div>
					</div>
					<div class="modal-footer">
	       				<button type="submit" class="btn btn-info" onclick="savePassword()">确定</button>
						<button type="reset" class="btn btn-success" data-dismiss="modal">关闭</button>
					</div>
					</form>
				</div>			
			</div>
		</div>
		
		<!-- 修改个人信息 -->
		<div class="modal fade" id="editPersonInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		     <form name="personInfo" id="personInfo" class="form-horizontal no-margin" data-validate="parsley" novalidate="">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="addOrUpdate_modal_label">
		               	个人信息
		            </h4>
		         </div>
		         <div class="modal-body">
		         	<input type="hidden" name="id" />
		         	
		           	<div class="panel-body">
						<div class="form-group">
							<label class="control-label col-lg-3">用户名称：</label>
							<div class="col-lg-8 ">
								<input type="text" id="name" name="name" class="form-control input-sm parsley-validated" readonly="readonly">
								<!-- <span class="custom-checkbox"></span> -->
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">真实名称：</label>
							<div class="col-lg-8">
								<input type="text" id="realName" name="realName" class="form-control input-sm parsley-validated" data-required="true" data-required-message="真是名称不可为空">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">超时时间：</label>
							<div class="col-lg-8 input-group" style="padding-left:5px;">
								<input type="text" id="timeout" name="timeout" aria-describedby="addonh" class="form-control input-sm parsley-validated" data-type="digits" data-type-digits-message="请您输入整数" data-required="true" data-required-message="超时时间不可为空"  placeholder="请输入时间值">
								<span class="input-group-addon" id="addonh">分钟</span>
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3">邮件：</label>
							<div class="col-lg-8">
								<input type="text" id="email" name="email" class="form-control input-sm parsley-validated" data-type="email" data-type-email-message="Email格式不合法" >
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3">电话：</label>
							<div class="col-lg-8">
								<input type="text" id="phone" name="phone" class="form-control input-sm parsley-validated" data-type="phone" data-type-phone-message="电话格式不合法">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3">手机：</label>
							<div class="col-lg-8">
								<input type="text" id="mobile" name="mobile" class="form-control input-sm parsley-validated" data-type="phone" data-type-phone-message="电话格式不合法">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
		           		<div class="form-group">
							<label class="control-label col-lg-3">描述：</label>
							<div class="col-lg-8">
								<textarea name="description" id="comment" class="form-control parsley-validated" data-maxwords="200" data-maxwords-message="描述不可超过{200}位" placeholder="请输入描述..." rows="3"></textarea>
							</div>
						</div><!-- /form-group -->
						
		           	</div>
		           	
		         </div>
		         <div class="modal-footer">
		            <button type="submit" class="btn btn-success" onclick="updateUser()">
		              	确定
		            </button>
		            <button type="button" class="btn btn-info"  data-dismiss="modal">
		               	取消
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
		     </form>
			</div><!-- /.modal -->
		</div>
			
		<!-- 提醒框 -->
		<div class="modal fade " id="top_modal" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header ">
			            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                  &times;
			            </button>
			            <h4 class="modal-title">
			             	提醒
			            </h4>
			         </div>
			         <div class="modal-body" >
			         	<h5 id="top_info"></h5>
			         </div>
			         <div class="modal-footer">
			            <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
			         </div>
			      </div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		
		<!-- theme-setting -->
		<div id="theme-setting">
			<div class="title">
				<strong class="no-margin">Skin Color</strong>
			</div>
			<div class="theme-box">
				<a class="theme-color" style="background:#323447" id="default"></a>
				<a class="theme-color" style="background:#efefef" id="skin-1"></a>
				<a class="theme-color" style="background:#a93922" id="skin-2"></a>
				<a class="theme-color" style="background:#3e6b96" id="skin-3"></a>
				<a class="theme-color" style="background:#635247" id="skin-4"></a>
				<a class="theme-color" style="background:#3a3a3a" id="skin-5"></a>
				<a class="theme-color" style="background:#495B6C" id="skin-6"></a>
			</div>
		</div><!-- /theme-setting -->
		
		<!-- 左侧目录 -->
		<aside class="fixed skin-6">
			<div class="sidebar-inner scrollable-sidebar">
				<div class="size-toggle">
					<a class="btn btn-sm" id="sizeToggle">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					<a class="btn btn-sm pull-right logoutConfirm_open" href="#logoutConfirm" data-popup-ordinal="1" id="open_11715015">
						<i class="fa fa-power-off"></i>
					</a>
				</div><!-- /size-toggle -->	
				<div class="user-block clearfix">
					<img src="${dist}/img/user.jpg" alt="User Avatar">
					<div class="detail">
						<strong>admin</strong><span class="badge badge-danger bounceIn animation-delay4 m-left-xs">4</span>
						<ul class="list-inline">
							<li><a href="javascript:void(0);">管理员</a></li>
							<li><a href="javascript:void(0);" class="no-margin">Inbox</a></li>
						</ul>
					</div>
				</div><!-- /user-block -->
				<div class="search-block">
					<div class="input-group">
						<input type="text" class="form-control input-sm" placeholder="search here...">
						<span class="input-group-btn">
							<button class="btn btn-default btn-sm" type="button"><i class="fa fa-search"></i></button>
						</span>
					</div><!-- /input-group -->
				</div><!-- /search-block -->
				
				<!-- main-menu -->
				<div class="main-menu">
					<ul>
						<!-- 安全态势 -->
						<li class="admin_li" id="mune_home">
							<a href="${base}/home/home02?mune=mune_home">
								<span class="menu-icon">
									<i class="fa fa-home fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.state"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 内网透视 -->
						<li class="admin_li" id="menu_perspective">
							<a href="${base}/discover/asset?mune=menu_perspective">
								<span class="menu-icon">
									<i class="fa fa-desktop fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.perspective"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 内网威胁 -->
						<li class="admin_li" id="menu_threat">
							<a href="${base}/alert/index?mune=menu_threat">
								<span class="menu-icon">
									<i class="fa fa-bell fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.threat"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 安全日志 -->
						<li class="admin_li" id="menu_log">
							<a href="${base}/log/index?mune=menu_log">
								<span class="menu-icon">
									<i class="fa fa-magnet fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.log"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 安全回溯 -->
						<li class="admin_li" id="menu_history">
							<a href="${base}/history/index?mune=menu_history">
								<span class="menu-icon">
									<i class="fa fa-reply fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.retrospect"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 报表管理 -->
						<li class="admin_li" id="menu_report">
							<a href="${base}/report/index?mune=menu_report">
								<span class="menu-icon">
									<i class="fa fa-bar-chart-o fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.report"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						<!-- 日志管理 -->
						<li class="admin_li" id="menu_config">
							<a href="${base}/config/index?mune=menu_config">
								<span class="menu-icon">
									<i class="fa fa-cogs fa-lg"></i> 
								</span>
								<span class="text">
									<spring:message code="menu.config"/><!-- 空白 -->
								</span>
								<span class="menu-hover"></span>
							</a>
						</li>
						
					</ul>
					
					<!-- <div class="alert alert-info">
						
					</div> -->
				</div><!-- /main-menu -->
			</div><!-- /sidebar-inner -->
		</aside>

		<div id="main-container">
			<sitemesh:write property="div.main-container" />
		</div><!-- /main-container -->
	
	</div>
	
	<a href="" id="scroll-to-top" class="hidden-print" style="bottom:8px;"><i class="fa fa-chevron-up"></i></a>
		
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	
    <!-- Jquery 
	<script src="${dist}/js/jquery/jquery-3.0.0.js"></script>
 	-->
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
    
	<!-- ajax submit form -->
	<script src="${dist}/js/form/jquery.form.min.js"></script>
	<script src="${dist}/js/form/jquery.form.ext.js"></script>
	 
	<!-- Endless -->
	<script src="${dist}/js/endless.js"></script>
	
  	<sitemesh:write property="div.script-container"/>
  
<script type="text/javascript">
$(document).ready(function() {
	var mune_id = '${param.mune }';
	if(mune_id=="") {
		$("#mune_home").addClass("active");
	}
	else 
	{
		var mune_ids = new Array();
		mune_ids = mune_id.split("_");
		var parentId = mune_ids[0]+"_"+mune_ids[1];
		$("#"+parentId).addClass("active");
		$("#${param.mune }").addClass("active");
	}
	
	//修改密码
	$('#editPassWord').on('hidden.bs.modal', function(e){
		$('#topForm').parsley().reset();
		$('input[type=password]').val('');
	});
		
});
</script>

  </body>
</html>