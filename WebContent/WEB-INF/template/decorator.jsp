<%@ include file="/WEB-INF/template/taglibs.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <title><sitemesh:write property="title"/></title>
	
	<sitemesh:write property="meta" />
    <sitemesh:write property="head" />

  </head>

  <body>
  
	<!-- Overlay Div -->
	<div id="overlay" class="transparent"></div>
  
	<!-- Logout confirmation -->
	<div class="custom-popup width-100" id="logoutConfirm" style="display: none;">
		<div class="padding-md">
			<h2 class="m-top-none" style="color:#DBC7C7;"><spring:message code="plan.logout.message"/></h2>
		</div>

		<div class="text-center">
			<a class="btn btn-success m-right-sm" href="${base}/auth/loginOut">确定</a>
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
					  <input type="password" name="oldPassWord" class="form-control parsley-validated" data-required="true" data-required-message="旧密码不能为空" placeholder="请输入用户当前密码">
					</div>
					<div class="form-group">
					  <label for="exampleInputPassword1">新密码：</label>
					  <input type="password" name="newPassWord" class="form-control input-sm parsley-validated" id="newPw" data-required="true" data-required-message="新密码不能为空" placeholder="请输入新密码">
					</div>
					<div class="form-group">
					  <label for="exampleInputPassword1">确认密码：</label>
					  <input type="password" name="conPw" class="form-control input-sm parsley-validated" data-equalto="#newPassWord" data-equalto-message="输入的两次密码不相同" data-required="true" data-required-message="确认密码不能为空" placeholder="请输入确认密码">
					  <span id="errorPs"></span>
					</div>
				</div>
				<div class="modal-footer">
       				<button type="submit" class="btn btn-info">确定</button>
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
	<div class="modal fade " id="top_modal" tabindex="100" role="dialog" aria-hidden="true" style="z-index: 9999;">
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
	
	<!-- /theme-setting -->
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
	
	
    <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Bootstrap theme</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    			
   	<div id="main-container" class="container theme-showcase">
   		<sitemesh:write property="div.main-container" />	
   	</div><!-- /main-container -->
    	
	<a href="" id="scroll-to-top" class="hidden-print" style="bottom:8px;"><i class="fa fa-chevron-up"></i></a>
		 
    <!-- Le javascript ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	
    <sitemesh:write property="div.script-container"/>

<script type="text/javascript">
$(document).ready(function(){
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
	$('#topForm').submit(function(){
		if($(this).parsley().isValid()){
			$.ajaxFormSubmit({
		        url : '${base}/auth/changePsw',
		        secureuri : false,
		        formElementId : 'topForm',//form表单id
		        dataType : 'text',
		        success : function (data, status){
			        if(data){
						$('#editPassWord').modal('hide');
						$('#top_info').text("操作成功");
						$('#top_modal').modal('show');
					}else{
						$('#top_info').text("操作失败,用户当前密码输入错误");
						$('#top_modal').modal('show');
					}
		        },
		        error: function(data, status, e){
		        	console.log(error);
		        }
			});
		}
	});
	$('#editPassWord').on('hidden.bs.modal', function(e){
		$('#topForm').parsley().reset();
		$('input[type=password]').val('');
	});
	
	//修改个人信息
	$('#editPersonInfo').on('shown.bs.modal', function () {
		$.ajax({
			url: '${base}/auth/getLoginUser',
			type: 'post',
			dataType: 'json',
			success: function(data){
				$("#personInfo input, textarea").each(function () {
                    var input = $(this);
                    if (data[input.attr('name')])
                        input.val(data[input.attr('name')]);
                });
			},
			error:function(error){
				console.log(error);
			}
		});
	});
	$("#personInfo").submit( function () {
		$.ajaxFormSubmit({
	        url : '${base}/auth/addOrupdateUser',
	        secureuri : false,
	        formElementId : 'personInfo',//form表单id
	        dataType : 'text',
	        success : function (data, status){
		        if(data=="success"){
					$("#editPersonInfo").modal('hide');
				}else{
					$('#top_info').text("操作失败");
					$('#top_modal').modal("show");
				}
	        },
	        error: function(data, status, e){
	        	console.log(error);
	        }
		});
	});
});
</script>

  </body>
</html>