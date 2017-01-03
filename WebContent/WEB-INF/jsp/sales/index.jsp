<%@ include file="/WEB-INF/template/taglibs.jsp" %>

<!-- 为菜单是否选中识别用 -->
<c:set var="menu" value="sales" scope="request" />
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><spring:message code="title"/></title>

	<link rel="icon" href="${dist}/img/user.jpg" type="image/x-icon" />
	<link rel="shortcut icon" href="${dist}/img/user.jpg" type="image/x-icon" />

    <!-- Bootstrap core CSS -->
    <link href="${dist}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="${dist}/css/bootstrap/bootstrap-theme.min.css" rel="stylesheet">
    <!-- Bootstrap table CSS -->
    <link href="${dist}/css/bootstrap/bootstrap-table.css" rel="stylesheet">
	<!-- Font Awesome -->
	<link href="${dist}/css/font-awesome.min.css" rel="stylesheet">
	<!-- Pace -->
	<link href="${dist}/css/pace.css" rel="stylesheet">
	<!-- stylesheet 自定义样式 -->
	<link href="${dist}/css/stylesheet.css" rel="stylesheet">
  </head>

  <body role="document" class="overflow-hidden">
  
  	<!-- 头部 -->
	<c:import url="/WEB-INF/template/top.jsp"></c:import>

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
				<div class="pull-right col-lg-6 input-group">
	            	<input class="form-control" id="fuzzy" placeholder="请输入身份证号进行查询" type="text">
	            	<div class="input-group-btn" role="group">
	            		<button id="search-btn" type="button" class="btn btn-primary">&nbsp;<i class="fa  fa-search fa-lg"></i>&nbsp;</button>
	            	</div>
	            </div>
			</div>
		</div>
      
		<div class="row">
			<div class="col-lg-12">
				<div class="panel bg fadeInDown animation-delay1">
					<table id="UserTable" data-toggle="table" class="table table-hover table-striped table-bordered table-condensed">
						
					</table>
				</div>
			</div>
		</div>  
      
      	<!-- 销售记录视图模态框 -->
		<div class="modal fade" id="SalesRecordModal" tabindex="-1" role="dialog" aria-labelledby="SalesRecordModalLabel" aria-hidden="true">
			<div class="modal-dialog">
		    	<div class="modal-content">
		         	<div class="modal-header">
		            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            	</button>
		            	<h4 class="modal-title" id="SalesRecordModalLabel">
		               		销售记录
		            	</h4>
		         	</div>
		         	<div class="modal-body">
		         		<div class="row">
		         			<div class="col-lg-12">
								<div class="panel bg fadeInDown animation-delay1">
									<table id="SalesRecordTable" data-toggle="table" class="table table-hover table-striped table-bordered table-condensed">
										
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.modal -->
		</div>

		<!-- 添加视图模态框 -->
		<div class="modal fade" id="addSalesRecordModal" tabindex="-1" role="dialog" aria-labelledby="addSalesRecordModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		   <form id="addSalesRecordForm" class="form-horizontal no-margin" data-validate="parsley" action="${base}/sales/add" method="post">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="addSalesRecordModalLabel">
		               	添加销售记录
		            </h4>
		         </div>
		         <div class="modal-body">
		         	<div class="row">
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
		
    </div> <!-- /container -->

<div id="script-container">
	<!-- Jquery -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery-1.10.2.min.js"></script>
   	<!-- Slimscroll -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.slimscroll.min.js"></script> 
	<!-- Cookie -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.cookie.min.js"></script>
	<!-- Popup Overlay -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.popupoverlay.min.js"></script>
	<!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="${dist}/js/bootstrap/bootstrap.js"></script>
	<!-- Bootstrap table -->
    <script type="text/javascript" src="${dist}/js/bootstrap/bootstrap-table.js"></script>
    <!-- Modernizr -->
	<script type="text/javascript" src="${dist}/js/modernizr.min.js"></script>
	<!-- Pace -->
	<script type="text/javascript" src="${dist}/js/pace.js"></script>
	<!-- form validate -->
    <script type="text/javascript" src="${dist}/js/parsley.min.js"></script>
    <!-- form submit -->
    <script type="text/javascript" src="${dist}/js/form/jquery.form.min.js"></script>
    <!-- Endless -->
    <script type="text/javascript" src="${dist}/js/endless.js"></script>

<script type="text/javascript">
	// 初始化加载数据
	var ruifiosMap = {
		salesrecord: {},
		user: {},
		userselections: []
	};
	
	var SalesRecordHandler = {
		init: function(){
			// 复制销售记录行
			ruifiosMap['recordtr'] = $("#goodsTable tbody>tr:first-child").clone();
			// 加载用户信息
			SalesRecordHandler.loadUserTable(1);
			
			$.getJSON("${base}/tree/getshopsinfo", function(data, status, xhr) {
				var shopsTree = [], shops = [];
				$.each(data.nodes, function(index, node) {
					node.open= true,//对资产组节点设置状态 
					node.chkDisabled=true;
					// 添加树节点
					shopsTree.push(node);
					// 添加商家信息
					var name = node.name;
					shops.push(name);
					// 添加商品信息
					var goods = [];
					$.each(node.children, function(index, good) {
						goods.push(good.name);
					});
					ruifiosMap[name] = goods;
		        });
				ruifiosMap['shop'] = shops;
				ruifiosMap['shopsTree'] = shopsTree;
			});
			
			SalesRecordHandler.refreshShops($("#merchantname"));
		},
		refreshShops: function(shop){// 刷新化商家列表
			$(shop).empty();
			var shops = [];
			if(ruifiosMap['shop'])
				$.each(ruifiosMap['shop'], function(index, shop){
					shops.push($('option', {label: shop, value: shop}));
				});
			
			$(shop).append(shops);
		},
		refreshGoods: function(good, shop){// 刷新化商品列表
			$(good).empty();
			var goods = [];
			if(ruifiosMap[shop])
				$.each(ruifiosMap[shop], function(index, shop){
					goods.push($('option', {label: shop, value: shop}));
				});
			
			$(good).append(goods);
		},	
		getIdSelections: function($table) {
	        return $.map($table.bootstrapTable('getSelections'), function (row) {
	            return row.id
	        });
	    },
		loadSalesRecordTable: function(currentPage){
			$.loadBootstrapTable("#SalesRecordTable", {
				url: "${base}/sales/getsalesrecord",
				queryParams: function(params){ //设置传入参数
					params['currentPage'] = params.pageNumber;
					$.extend(params, ruifiosMap['salesrecord']);
					return params;
				},
				data: { page: currentPage },
				columns: [
					//{title:"全部", field: "id", checkbox:true, visible:false},
					{title:"用户名称", field: "consumername", width: "10%" },
					{title:"身份证号", field: "consumercard", width: "10%" },
					{title:"商家名称", field: "merchantname", width: "10%" },
					{title:"商品名称", field: "commodityname", width: "10%"},
					{title:"购买数量", field: "number", width: "5%" },
					{title:"商品原价", field: "originalcost", width: "5%" },
					{title:"商品售价", field: "sellingprice", width: "5%" },
					{title:"让利额度", field: "transferprice", width: "5%" },
					{title:"政府补贴", field: "subsidyprice", width: "5%" },
					{title:"实际支付", field: "actualpayment", width: "5%" }
					//{title:"", field: "timeLast", formatter: startTimeFormatter}
				]
			});
		},
		loadUserTable: function(currentPage){ //加载用户信息
			$.loadBootstrapTable("#UserTable", {
				url: "${base}/auth/getuserlist",
				pageSize: 20,
				queryParams: function(params){ //设置传入参数
					params['currentPage'] = params.pageNumber;
					$.extend(params, ruifiosMap['user']);
					return params;
				},
				data: { page: currentPage },
				columns: [
					{title:"全部", field: "id", checkbox:true, width: "16px" },
					{title:"用户名称", field: "realName", width: "25%" },
					{title:"身份证号", field: "userName", width: "25%" },
					{title:"电话号码", field: "userPhone", width: "25%" },
					{title:"消费金额（￥）", field: "consum", width: "25%" }
				]
			}).on('check.bs.table uncheck.bs.table ' +
	                'check-all.bs.table uncheck-all.bs.table', function () {
				var flag = false, selections = SalesRecordHandler.getIdSelections($("#UserTable"));
	            $("#delSalesRecord").prop('disabled', !selections.length);
	            
				if(selections.length < 2){
					flag = true;
				}
				$("#addSalesRecord").prop('disabled', !flag);
				
	            // save your data, here just save the current page
	            ruifiosMap.selections = SalesRecordHandler.getIdSelections($table);
	            // push or splice the selections if you want to save all data selections
	        });
		}
	};
	
	$(document).ready(function(){
		SalesRecordHandler.init();
		
		$(document).on("change",  "#merchantname", function(e){// 刷新商品信息
			var shop = $(this).val();
			$("#goodsTable datalist").each(function () {
				SalesRecordHandler.refreshGoods($(this), shop);
			});
		}).on("click",  "#goodsTable .add-record", function(e){// 添加一条销售记录
    		var trs = $("#goodsTable tbody tr");
    		if(trs.length>1)// 去掉最后一个td的删除按钮
    			$("#goodsTable tbody>tr").last().find("td:last-child").empty();
    		// 添加新的商品信息
    		var newtr = ruifiosMap['recordtr'].clone();
    		
    		var firsttd = newtr.find('td:first-child');
    		var inputlist = firsttd.find('input');
    		inputlist.attr("list", inputlist.attr("list")+trs.length);
    		var datalist = firsttd.find('datalist');
    		datalist.attr("id", inputlist.attr("id")+trs.length);
    		
    		var lasttd = newtr.find('td:last-child');
    		lasttd.find('a').addClass("del-record").removeClass("add-record");
    		lasttd.find('i').addClass("fa-minus-circle").removeClass("fa-plus-circle");
    		
    		lasttd.siblings().each(function (index, td) {
    			var input = $(td).find('input');
    			var name = input.attr('name').replace(/\d+/g, trs.length);
    			input.attr('name', name);
    		});
    		
    		$("#goodsTable tbody").append(newtr);
    	}).on("click",  "#goodsTable .del-record", function(e){// 删除一条销售记录
    		var tr = $(this).closest('tr');
    		if($("#goodsTable tbody>tr").length>2)
    			tr.prev().find('td:last-child').append(tr.find('td:last-child').html());
    		tr.remove();
    	}).on("show.modal.bs", "#addSalesRecordModal", function(e){// 摸态框关闭清理表单
    		$("#goodsTable tr:gt(0)").remove();
    		var form = $(this).find('form');
    		ruifiosMap['formdata']['base'] = {consumername: '', consumercard: '', consumerphone: ''};
    		var row = $("#UserTable").bootstrapTable('getSelections'); 
    		if(row) {
    			ruifiosMap['formdata']['base'] = {consumername: row['realName'], consumercard: row['userName'], consumerphone: row['userPhone']};
    		}
    	}).on("show.modal.bs", "#SalesRecordModal", function(e){// 摸态框关闭清理表单
    		ruifiosMap['salesrecord'] = {consumercard: $(this).data("id")};
    		
    		SalesRecordHandler.loadSalesRecordTable(1);
    	});
		
		$("#addSalesRecordForm").submit(function() {debugger;
			if($(this).parsley().isValid()) {
				$(this).ajaxSubmit({
		            type: 'POST',
		            dataType: 'json',
		            resetForm: true,
		            success: function(response, status, xhr, form) {
		              	$("#addSalesRecordModal").modal('hide');
		              	$("#SalesRecordModal").data('id', response);
		              	$("#SalesRecordModal").modal('show');
		            },
		            error: function(xhr, status, error, form) {
		            	console.log(xhr);
		            }
		        });
	        }
	        
	        return false;
		});
	});
</script>
</div>
  </body>
</html>
