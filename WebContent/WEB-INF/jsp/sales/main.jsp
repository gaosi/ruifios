<%@ include file="/WEB-INF/template/taglibs.jsp" %>

<!-- 为菜单是否选中识别用 -->
<c:set var="menu" value="record" scope="request" />
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
	<link href="${dist}/css/ztree/zTreeStyle.css" rel="stylesheet">
	<!-- stylesheet 自定义样式 -->
	<link href="${dist}/css/stylesheet.css" rel="stylesheet">
  </head>

  <body role="document" class="overflow-hidden">
  
  	<!-- 头部 -->
	<c:import url="/WEB-INF/template/top.jsp"></c:import>

    <div id="main-container" class="container theme-showcase" role="main">
      
		<div class="row">
			<div class="col-lg-2">
				<ul id="AllShopsInfo" class="ztree"></ul>
				<ul id="ShopsInfoTree" class="ztree"></ul>
			</div>
			<div class="col-lg-10">
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
			            	<input class="form-control" id="fuzzy" placeholder="请输入身份证号进行查询" type="text">
			            	<div class="input-group-btn" role="group">
			            		<button type="button" id="search-btn" class="btn btn-primary">&nbsp;<i class="fa  fa-search fa-lg"></i>&nbsp;</button>
			            		<div class="btn-group">
				            		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
										<i class="glyphicon glyphicon-export icon-share"></i> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu export" role="menu" data-target="#SalesRecordTable">
										<li data-type="json"><a href="javascript:void(0)">JSON</a></li>
										<li data-type="xml"><a href="javascript:void(0)">XML</a></li>
										<li data-type="csv"><a href="javascript:void(0)">CSV</a></li>
										<li data-type="txt"><a href="javascript:void(0)">TXT</a></li>
										<li data-type="sql"><a href="javascript:void(0)">SQL</a></li>
										<li data-type="excel"><a href="javascript:void(0)">MS-Excel</a></li>
									</ul>
								</div>
				            </div>
			            </div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div id="toolbar">
						
						</div>	
						<table id="SalesRecordTable" data-toggle="table" class="table table-hover table-striped table-bordered table-condensed">
							
						</table>
					</div>
				</div>
			</div>
		</div>  
		
    </div> <!-- /container -->

<div id="script-container">
	<!-- Jquery -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery-1.10.2.min.js"></script>
   	<!-- Slimscroll -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.slimscroll.min.js"></script> 
	<!-- Cookie -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.cookie.min.js"></script>
    <!-- jquery.ztree.all-3.5.js -->
    <script type="text/javascript" src="${dist}/js/ztree/jquery.ztree.all-3.5.js" ></script>
	<!-- Popup Overlay -->
	<script type="text/javascript" src="${dist}/js/jquery/jquery.popupoverlay.min.js"></script>
	<!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="${dist}/js/bootstrap/bootstrap.js"></script>
	<!-- Bootstrap table -->
    <script type="text/javascript" src="${dist}/js/bootstrap/bootstrap-table.js"></script>
	<!-- Bootstrap table -->
    <script type="text/javascript" src="${dist}/js/bootstrap/bootstrap-table-export.js"></script>
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
		salesrecord: {}
	};
	
	var SalesRecordHandler = {
		loadSalesRecordTable: function(currentPage){
			$.loadBootstrapTable("#SalesRecordTable", {
				url: "${base}/record/getsalesrecord",
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
					{title:"购买数量", field: "number", width: "10%" },
					{title:"商品原价", field: "originalcost", width: "10%" },
					{title:"商品售价", field: "sellingprice", width: "10%" },
					{title:"让利额度", field: "transferprice", width: "10%" },
					{title:"政府补贴", field: "subsidyprice", width: "10%" },
					{title:"实际支付", field: "actualpayment", width: "10%" }
					//{title:"", field: "timeLast", formatter: startTimeFormatter}
				]
			});
		},
		clickTreeNode: function(event, treeId, treeNode) {
			var parent = treeNode.getParentNode();
			ruifiosMap['salesrecord'] = {commodityname: '', merchantname: ''};
			if(parent && parent.merchantname){
				ruifiosMap['salesrecord'] = {merchantname: parent.name, commodityname: treeNode.name};
			}else if(treeNode.merchantname){
				ruifiosMap['salesrecord'] = {merchantname: treeNode.name};
			}
			
			SalesRecordHandler.loadSalesRecordTable(1);
		},
		init: function(){
			// 加载商家商品信息, status, xhr
			$.getJSON("${base}/tree/getshopsinfo", function(data) {
				var shopsTree = [];
				$.each(data.nodes, function(index, node) {
					node.open= true,//对资产组节点设置状态 
					node.chkDisabled=true;
					node.merchantname=true;
					// 添加树节点
					shopsTree.push(node);
		        });
				
				ruifiosMap['shopsTree'] = [{ name: '全部商品', open: true, children: shopsTree}];
				
				ruifiosMap['ztree'] = $.fn.zTree.init($("#AllShopsInfo"), {
					data: {simpleData: {enable: true}},
		            view: { selectedMulti: false },
		            callback: {
                        beforeClick: function(treeId, treeNode) { 
                        	if(treeNode.passing)
                        		return false;//过路节点不选择
                        	else
                        		return treeNode; 
                        },
                        onClick: SalesRecordHandler.clickTreeNode
		            }
			     }, ruifiosMap['shopsTree']);
			});
			
			// 加载销售信息
			SalesRecordHandler.loadSalesRecordTable(1);
		}
	};
	
	$(document).ready(function(){
		// 初始化销售记录
		SalesRecordHandler.init();
		
		$(document).on("click", "#search-btn", function(){
			var fuzzy = $("#fuzzy").val();
			$.extend(ruifiosMap['salesrecord'], {consumercard: fuzzy});
			
			SalesRecordHandler.loadSalesRecordTable(1);
		}).on("click", ".export.dropdown-menu >li", function(){
			var type = $(this).data('type');
			var table = $(this).parent("ul").data("target");
			
			$(table).tableExport({type: type, escape: false});
		});
	});
</script>
</div>
  </body>
</html>
