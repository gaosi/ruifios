<%@ include file="/WEB-INF/template/taglibs.jsp" %>

	<!-- Overlay Div -->
	<div id="overlay" class="transparent"></div>
	  
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
          <a class="navbar-brand" href="${base }/sales/index">Bootstrap theme</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="${menu == 'sales' ? 'active' : ''}"><a href="${base }/sales/index">Home</a></li>
            <li class="${menu == 'record' ? 'active' : ''}"><a href="${base }/record/main">Search</a></li>
            <li class="${menu == 'statistical' ? 'active' : ''}"><a href="${base }/record/statistical">Statistical</a></li>
            <!-- <li class="dropdown" class="active">
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
            </li> -->
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
            	<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0);">
					<strong>${sessionScope.uname }</strong>
					<span><i class="fa fa-chevron-down"></i></span>
				</a>
            	<ul class="dropdown-menu" role="menu">
					<li><a tabindex="-1" href="#editProfileModal" data-toggle="modal">Edit profile</a></li>
            		<li><a tabindex="-1" class="main-link logoutConfirm_open" href="#logoutConfirm" data-popup-ordinal="0">Sign out</a></li>
            	</ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
	<!-- 修改个人信息 -->
	<div class="modal fade" id="editProfileModal" data-url="${base}/auth/getLoginUser" tabindex="-1" role="dialog" aria-labelledby="editProfileModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form id="editProfileForm" class="form-horizontal no-margin" data-validate="parsley" novalidate="" action="${base}/auth/addOrupdateUser">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title">
		               	修改个人信息
		            </h4>
		         </div>
		         <div class="modal-body">
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
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">超时时间：</label>
							<div class="col-lg-8">
								<div class="input-group">
									<input type="text" id="timeout" name="timeout" aria-describedby="addonh" class="form-control input-sm parsley-validated" data-type="digits" data-type-digits-message="请您输入整数" data-required="true" data-required-message="超时时间不可为空"  placeholder="请输入时间值">
									<span class="input-group-addon">分钟</span>
								</div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">邮件：</label>
							<div class="col-lg-8">
								<input type="text" id="email" name="email" class="form-control input-sm parsley-validated" data-type="email" data-type-email-message="Email格式不合法" >
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">电话：</label>
							<div class="col-lg-8">
								<input type="text" id="phone" name="phone" class="form-control input-sm parsley-validated" data-type="phone" data-type-phone-message="电话格式不合法">
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">手机：</label>
							<div class="col-lg-8">
								<input type="text" id="mobile" name="mobile" class="form-control input-sm parsley-validated" data-type="phone" data-type-phone-message="电话格式不合法">
							</div><!-- /.col -->
						</div><!-- /form-group -->
		           		<div class="form-group">
							<label class="control-label col-lg-3">描述：</label>
							<div class="col-lg-8">
								<textarea name="description" id="comment" class="form-control parsley-validated" data-maxwords="200" data-maxwords-message="描述不可超过{0}位" placeholder="请输入描述..." rows="3"></textarea>
							</div>
						</div><!-- /form-group -->
						
		         		<input type="hidden" name="id" />
		         	
		           	</div>
		           	
		         </div>
		         <div class="modal-footer">
		            <button type="submit" class="btn btn-success">
		              	<spring:message code="button.sure"/>
		            </button>
		            <button type="button" class="btn btn-info" data-dismiss="modal">
		               	<spring:message code="button.cancel"/>
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
		    </form>
		</div><!-- /.modal -->
	</div>
	
	<!-- Logout confirmation -->
	<div class="custom-popup width-100" id="logoutConfirm" style="display: none;">
		<div class="padding-md">
			<h2 class="m-top-none" style="color:#DBC7C7;">您确定要退出<spring:message code="title"/>吗？</h2>
		</div>

		<div class="text-center">
			<a class="btn btn-success m-right-sm logoutConfirm_close" href="${base}/auth/logout"><spring:message code="button.sure"/></a>
			<a class="btn btn-danger logoutConfirm_close"><spring:message code="button.cancel"/></a>
		</div>
	</div>
	
	<a href="" id="scroll-to-top" class="hidden-print" style="bottom:8px;"><i class="fa fa-chevron-up"></i></a>
		