<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>网络雷达预警系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="icon" href="../favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon" />
	
    <!-- Bootstrap core CSS -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Font Awesome -->
	<link href="<%=basePath %>css/font-awesome.min.css" rel="stylesheet">
	
	<!-- Pace -->
	<link href="<%=basePath %>css/pace.css" rel="stylesheet">
	
	<!-- Color box -->
	<link href="<%=basePath %>css/colorbox.css" rel="stylesheet">
	
	<!-- Morris -->
	<link href="<%=basePath %>css/morris.css" rel="stylesheet"/>	
	
	<!-- Endless -->
	<link href="<%=basePath %>css/endless.min.css" rel="stylesheet">
	<link href="<%=basePath %>css/endless-skin.css" rel="stylesheet">

	<!-- Jquery -->
	<script src="<%=basePath %>js/jquery-1.10.2.min.js"></script>
<style type="text/css">

	.mycharts {
		height:300px; width:100%;
	}
	
</style>
  </head>

  <body class="overflow-hidden">
	
	<div id="wrapper" class="preload">
		
		<div id="main-container" style="margin-left:200px;">
			<div id="breadcrumb">
				<ul class="breadcrumb">
					 <li><i class="fa fa-info-circle"></i>&nbsp;&nbsp;echarts图表</li> 
				</ul>
			</div><!-- /breadcrumb-->
			<div class="padding-md">
				<div class="row">
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="tab-content">
									<div id="bar"  class="mycharts"></div>
								</div>
							</div>
						</div><!-- /panel -->
					</div><!-- /.col -->
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="tab-content">
									<div id="line"  class="mycharts"></div>
								</div>
							</div>
						</div><!-- /panel -->
					</div><!-- /.col -->
				</div><!-- /.row -->
				<div class="row">
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="tab-content">
									<div id="area"  class="mycharts"></div>
								</div>
							</div>
						</div><!-- /panel -->
					</div><!-- /.col -->
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="tab-content">
									<div id="pie"  class="mycharts"></div>
								</div>
							</div>
						</div><!-- panel -->
					</div><!-- /.col -->
				</div><!-- /.row-->
				
				
				<div class="row">
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="tab-content">
									<div id="map"  class="mycharts"></div>
								</div>
							</div>
						</div><!-- /panel -->
					</div><!-- /.col -->
					<div class="col-md-6" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div  class="mycharts"></div>
							</div>
						</div><!-- panel -->
					</div><!-- /.col -->
				</div><!-- /.row-->
				
			</div><!-- /.padding20 -->
		</div>
		
		
	</div><!-- /wrapper -->

	<a href="" id="scroll-to-top" class="hidden-print"><i class="fa fa-chevron-up"></i></a>

	
   <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	
	<!-- Bootstrap -->
    <script src="<%=basePath %>js/bootstrap.js"></script>
   
	<!-- Flot -->
	<script src='<%=basePath %>js/jquery.flot.min.js'></script>
   
	<!-- Morris -->
	<script src='<%=basePath %>js/rapheal.min.js'></script>	
	<script src='<%=basePath %>js/morris.min.js'></script>	
	
	<!-- Colorbox -->
	<script src='<%=basePath %>js/jquery.colorbox.min.js'></script>	

	<!-- Sparkline -->
	<script src='<%=basePath %>js/jquery.sparkline.min.js'></script>
	
	<!-- Pace -->
	<script src='<%=basePath %>js/pace.js'></script>
	
	<!-- Popup Overlay -->
	<script src='<%=basePath %>js/jquery.popupoverlay.min.js'></script>
	
	<!-- Slimscroll -->
	<script src='<%=basePath %>js/jquery.slimscroll.min.js'></script>
	
	<!-- Modernizr -->
	<script src='<%=basePath %>js/modernizr.min.js'></script>
	
	<!-- Cookie -->
	<script src='<%=basePath %>js/jquery.cookie.min.js'></script>
	
	<!-- Endless 
	<script src="<%=basePath %>js/endless_dashboard.js"></script>
	-->
	<script src="<%=basePath %>js/endless.js"></script>
	
	<script src="<%=basePath %>echarts/echarts-all.js"></script>
	<script src="<%=basePath %>echarts/style.js"></script>
	
	<script type="text/javascript">
	
	var myChart1 = $.echarts('bar',"<%=basePath %>/demo/getBarChart");
	var myChart2 = $.echarts('line',"<%=basePath %>/demo/getLineChart");
	var myChart3 = $.echarts('area',"<%=basePath %>/demo/getAreaChart");
	var myChart4 = $.echarts('pie',"<%=basePath %>/demo/getPieChart");
	var myChart5 = $.echarts('map',"<%=basePath %>/demo/getMap");
	
	window.onresize = function(){
		myChart1.resize();
		myChart2.resize();
		myChart3.resize();
		myChart4.resize();
		myChart5.resize();
	};
	</script>
  </body>
</html>
    