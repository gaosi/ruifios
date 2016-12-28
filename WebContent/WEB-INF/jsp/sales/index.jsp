<%@ include file="/WEB-INF/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><spring:message code="title"/></title>

	<link rel="icon" href="${base}/img/user.jpg" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/img/user.jpg" type="image/x-icon" />

    <!-- Bootstrap core CSS -->
    <link href="${base}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="${base}/css/bootstrap-theme.min.css" rel="stylesheet">

  </head>

  <body role="document">

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

    <div id="main-container" class="container theme-showcase" role="main">
		<div class="row">
			<div class="fixed col-lg-12">
				<div class="btn-group">
					<a id="addSalesRecord" class="btn btn-primary btn-sm" href="#addSalesRecordModal" data-toggle="modal">
						<i class="fa fa-plus-circle fa-lg"></i><spring:message code="button.add"/>
					</a>
					<a id="delSalesRecord" class="btn btn-primary btn-sm">
						<i class="fa fa-minus-circle fa-lg"></i><spring:message code="button.del"/>
					</a>
				</div>
			</div>
		</div>
      

		<!-- 添加视图模态框 -->
		<div class="modal fade" id="addSalesRecordModal" tabindex="-1" role="dialog" aria-labelledby="SalesRecordModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		     <form name="addProbeForm" class="form-horizontal no-margin" id="addProbeForm" data-validate="parsley" method="post">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="SalesRecordModalLabel">
		               	添加销售记录
		            </h4>
		         </div>
		         <div class="modal-body">
		         	<div class="panel-body">
		           		<fieldset>
							<legend>基本信息</legend>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-lg-3">是否启用：</label>
										<div class="col-lg-4 col-md-offset-3">
											<input type="checkbox" name="isrun" class="parsley-validated" value="1">
											<!-- <span class="custom-checkbox"></span> -->
										</div><!-- /.col -->
									</div><!-- /form-group -->
								</div>
								<div class="col-md-6">
								
								</div>
							</div>
						</fieldset>
						
						<div class="form-group">
							<label class="control-label col-lg-3">探针名称：</label>
							<div class="col-lg-8">
								<input type="text" name="name" class="form-control input-sm parsley-validated" data-required="true" data-required-message="探针名称不可为空" data-type="username" data-type-username-message="探针名称不合法"  placeholder="请输入探针名称">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">访问地址：</label>
							<div class="col-lg-8">
								<input type="text" name="address" class="form-control input-sm parsley-validated validated-if" data-type="ip" data-type-ip-message="IP地址不合法" data-required="true" data-required-message="访问地址不可为空" placeholder="请输入访问地址">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">访问端口：</label>
							<div class="col-lg-8">
								<input type="text" name="port" class="form-control input-sm parsley-validated" data-range="[0, 65535]" data-range-message="访问端口{0-65535}"  data-required="true" data-required-message="访问端口不可为空" placeholder="请输入访问端口">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">访问账号：</label>
							<div class="col-lg-8">
								<input type="text" name="account" class="form-control input-sm parsley-validated" data-required="true" data-required-message="访问账号不可为空" placeholder="请输入访问账号">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						<div class="form-group">
							<label class="control-label col-lg-3">访问密码：</label>
							<div class="col-lg-8">
								<input type="password" name="password" class="form-control input-sm parsley-validated" data-required="true" data-required-message="访问密码不可为空" placeholder="请输入访问密码">
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3">数据接口：</label>
							<div class="col-lg-8 input-group">
								<select id="probeiflist" name="mirrorIf" class="form-control input-sm parsley-validated" aria-describedby="sync_probeif" data-required="true" data-required-message="探针数据接口不可为空" placeholder="请先配置探针访问信息再选择网络接口">
									<option value="">请先配置探针访问信息再选择网络接口 </option> <!-- 点击此处时根据配置自动获取探针镜像口 -->
								</select>
								<span class="input-group-addon" id="sync_probeif" title="获取探针接口" onclick="updateInterface()"><i class="fa fa-refresh"></i></span>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3"><!-- 过滤策略 -->IP范围：</label>
							<div class="col-lg-8">
								<input type="text" name="ipRange" class="form-control input-sm " title="“192.168.1.0/24” ,“192.168.1.12/32”,“192.168.1.0 mask 255.255.255.0”,多网段以“；”分割" placeholder="“192.168.1.0/24” ,“192.168.1.12/32”,“192.168.1.0 mask 255.255.255.0”,多网段以“；”分割"><!-- 请输入IP范围采用“-”分割或多个IP地址采用“,”分割 -->
								<!-- <span class="custom-checkbox"></span> -->
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
						<div class="form-group">
							<label class="control-label col-lg-3">接收地址：</label>
							<div class="col-lg-8">
								<select id="routeIp" name="routeIp" class="form-control input-sm parsley-validated">
									<c:forEach var="name" items="${iplist}">
										<option value="${name }">${name }</option>
	                  				</c:forEach>
								</select>
								<div class="seperator"></div>
							</div><!-- /.col -->
						</div><!-- /form-group -->
						
		           		<div class="form-group">
							<label class="control-label col-lg-3">描述：</label>
							<div class="col-lg-8">
								<textarea name="description" id="content" class="form-control parsley-validated" data-maxcheck="200" data-maxcheck-message="描述不可超过200个字符" placeholder="请输入描述..." rows="3"></textarea>
							</div>
						</div><!-- /form-group -->	
		           	</div>
		           	
		         </div>
		         <div class="modal-footer">
		            <button type="submit" class="btn btn-success" onclick="addOrupdateProbe()">
		              	确定
		            </button>
		            <button type="button" class="btn btn-info" data-dismiss="modal">
		               	取消
		            </button>
		            <input type="hidden" name="id" />
		         </div>
		      </div><!-- /.modal-content -->
		     </form>
			</div><!-- /.modal -->
		</div>
		
    </div> <!-- /container -->

<div id="script-container">
	<!-- Jquery -->
	<script type="text/javascript" src="${base}/js/jquery/jquery-1.10.2.min.js"></script>
   	<!-- Slimscroll -->
	<script type="text/javascript" src="${base}/js/jquery/jquery.slimscroll.min.js"></script> 
	<!-- Cookie -->
	<script type="text/javascript" src="${base}/js/jquery/jquery.cookie.min.js"></script>
	<!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="${base}/js/bootstrap/bootstrap.min.js"></script>
	<!-- Pace -->
	<script type="text/javascript" src="${base}/js/pace.js"></script>
    <!-- Endless -->
    <script type="text/javascript" src="${base}/js/endless.js"></script>
</div>
  </body>
</html>
