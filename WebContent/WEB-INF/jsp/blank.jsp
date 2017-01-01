<%@ include file="/WEB-INF/template/taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title><spring:message code="title"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	
    <!-- Bootstrap core CSS 
    <link href="${dist }/css/bootstrap.min.css" rel="stylesheet">-->
	
	<!-- Font Awesome 
	<link href="${dist }/css/font-awesome.min.css" rel="stylesheet">-->
	
	<!-- Pace 
	<link href="${dist }/css/pace.css" rel="stylesheet">-->
	
	<!-- Color box 
	<link href="${dist }/css/colorbox.css" rel="stylesheet">-->
	
	<!-- Morris 
	<link href="${dist }/css/morris.css" rel="stylesheet"/>	-->
	
	<!-- Endless 
	<link href="${dist }/css/endless.min.css" rel="stylesheet">
	<link href="${dist }/css/endless-skin.css" rel="stylesheet">-->
	
  </head>
  <body class="overflow-hidden">
  
	<div id="wrapper" class="preload">
		
		<!-- 头部 -->
		<%--@ include file="./plugin/top.jsp" --%>
		<!-- 左侧目录 -->
		<%--@ include file="./plugin/left.jsp" --%>
	
		<div id="main-container">
			<div id="breadcrumb">
				<ul class="breadcrumb">
					 <li><i class="fa fa-home"></i><a href="javascript:void(0);"> Home</a></li>
					 <li class="active">Blank page</li>	 
				</ul>
			</div><!-- breadcrumb -->
		</div><!-- /main-container -->
		
		
		<!-- 添加视图模态框 -->
		<div class="modal fade" id="addSalesRecordModal" tabindex="-1" role="dialog" aria-labelledby="SalesRecordModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		   <form id="addSalesRecordForm" class="form-horizontal no-margin" data-validate="parsley" action="${base}/sales/add" method="post">
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
										<label class="control-label control-required col-lg-4">客户姓名：</label>
										<div class="col-lg-8">
											<input type="text" name="base.consumername" class="form-control input-sm parsley-validated" data-required="true" data-required-message="客户姓名不可为空" placeholder="请输入客户姓名" />
										</div><!-- /.col -->
									</div><!-- /form-group -->
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label control-required col-lg-4">身份证号：</label>
										<div class="col-lg-8">
											<input type="text" name="base.consumercard" class="form-control input-sm parsley-validated" data-required="true" data-required-message="身份证号不可为空" placeholder="请输入身份证号" />
										</div><!-- /.col -->
									</div><!-- /form-group -->
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label control-required col-lg-4">商家名称：</label>
										<div class="col-lg-8">
											<input type="text" name="base.merchantname" id="merchantname" list="shop_list" class="form-control input-sm parsley-validated" data-required="true" data-required-message="商家名称不可为空" placeholder="请输入商家名称" />
											<datalist id="shop_list"></datalist>
										</div><!-- /.col -->
									</div><!-- /form-group -->
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-lg-4">手机号：</label>
										<div class="col-lg-8">
											<input type="text" name="base.consumerphone" class="form-control input-sm parsley-validated" data-type="phone" data-type-phone-message="手机号码格式错误" placeholder="请输入手机号" />
										</div><!-- /.col -->
									</div><!-- /form-group -->
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>商品信息</legend>
							<table id="goodsTable" data-toggle="table" class="table table-hover table-striped table-bordered table-condensed">
								<thead>
									<tr>		
										<th>商品名称</th>			
										<th>原价</th>			
										<th>售价</th>
										<th>数量</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<input type="text" name="record[0].commodityname" list="good_list" class="form-control input-sm parsley-validated" data-required="true" data-required-message="商品名称不可为空" placeholder="请输入商品名称" />
											<datalist id="good_list"></datalist>
										</td>
										<td>
											<input type="text" name="record[0].originalcost" class="form-control input-sm parsley-validated" data-required="true" data-required-message="商品原价不可为空" data-type="alphanum" data-type-alphanum-message="请输入金额" placeholder="请输入商品原价（￥）" />
										</td>
										<td>
											<input type="text" name="record[0].sellingprice" class="form-control input-sm parsley-validated" data-required="true" data-required-message="商品售价不可为空" data-type="alphanum" data-type-alphanum-message="请输入金额" placeholder="请输入商品售价（￥）" />
										</td>
										<td>
											<input type="number" name="record[0].number" value="1" min="1" class="form-control input-sm parsley-validated" data-required="true" data-required-message="商品数量不可为空" data-type="integer" data-type-integer-message="请输入大于0数组" placeholder="请输入商品数量" />
										</td>
										<td>
											<!-- <button class="btn btn-xs btn-danger"><i class="fa fa-trash-o"></i></button> -->
											<a class="btn btn-sm btn-default add-record"><i class="fa fa-plus-circle"></i></a>
										</td>
									</tr>
								</tbody>
							</table>
						</fieldset>
		         	</div>
		         </div>
		         <div class="modal-footer">
		            <button type="submit" class="btn btn-success">
		              	<spring:message code="button.sure"/>
		            </button>
		            <button type="button" class="btn btn-info" data-dismiss="modal">
		               	<spring:message code="button.cancel"/>
		            </button>
		            <!-- input type="hidden" name="id" /> -->
		         </div>
		      </div><!-- /.modal-content -->
		    </form>
			</div><!-- /.modal -->
		</div>
		
	</div>
	
    <a href="" id="scroll-to-top" class="hidden-print" style="bottom:8px;"><i class="fa fa-chevron-up"></i></a>
	
<div id="script-container">
	
</div>
  </body>
</html>