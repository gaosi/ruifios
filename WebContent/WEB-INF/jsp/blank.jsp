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
		
	</div>
	
    <a href="" id="scroll-to-top" class="hidden-print" style="bottom:8px;"><i class="fa fa-chevron-up"></i></a>
	
<div id="script-container">
	
</div>
  </body>
</html>