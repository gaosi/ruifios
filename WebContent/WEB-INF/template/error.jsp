<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/template/taglibs.jsp" %>
<!DOCTYPE html>

<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="decorator" content=""/>
  	<link href="${dist}/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" />
  
  	<title>错误</title>
  <style type="text/css">
  #container { position:fixed; top:0; bottom:0; left:5%; margin:0 auto; width:90%; text-align:center; overflow:auto; }
  .contents {text-align:left;}
  .stacktrace {padding-left:2em;}
  p.errorcode {word-wrap:break-word;text-align:left;}
  .button-bar { display:none; }
  </style>
  </head>

<body>
<div>
  <div class="container">
    <div class="block-error">
      

      <div class="row button-bar">
        <div class="col-md-6 col-md-offset-3">
          <button class="btn btn-primary btn-block" onclick="history.go(-1)"><fmt:message key="button.back"/></button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>