<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
	
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<meta content="" name="description" />

<meta content="" name="author" />

<!-- BEGIN GLOBAL MANDATORY STYLES -->

<%-- <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/css/bootstrap-responsive.min.css"
	rel="stylesheet" type="text/css" /> --%>
	
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/css/bootstrap-responsive.css"	rel="stylesheet" type="text/css" />

<link href="${ctx}/css/font-awesome.min.css" rel="stylesheet"	type="text/css" />

<link href="${ctx}/css/style-metro.css" rel="stylesheet"	type="text/css" />

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/css/style-responsive.css" rel="stylesheet"	type="text/css" />


<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE STYLES -->

<sitemesh:head></sitemesh:head>

<!-- END PAGE STYLES -->
</HEAD>

<%
	// session.invalidate();
	String currentjsp = null;
	if (request.getAttribute("currentjsp") != null)
		currentjsp = request.getAttribute("currentjsp").toString();
	else
		currentjsp = "/portlet/notfound.jsp";
%>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-header-fixed page-sidebar-closed">



	<div>
		<sitemesh:body></sitemesh:body>
		
	</div>

	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->

	<!-- <div class="footer">

		<div class="footer-inner">


		</div>

		<div class="footer-tools">

			<span class="go-top"> <i class="icon-angle-up"></i>

			</span>

		</div>

	</div> -->

	<!-- END FOOTER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	
	<!-- BEGIN GOLOBAL JS VAR -->
	
	<!-- EN GLOBAL JS VAR -->

	<!-- BEGIN CORE PLUGINS -->

	<script  src="${ctx}/js/jquery-1.8.3.min.js"		type="text/javascript"></script>

	
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

	<script src="${ctx}/js/jquery-ui-1.10.1.custom.min.js"		type="text/javascript"></script>

	<script src="${ctx}/js/bootstrap.min.js" type="text/javascript"></script>

	<!--[if lt IE 9]>

	<script src="${ctx}/js/excanvas.min.js"></script>

	<script src="${ctx}/js/respond.min.js"></script>  

	<![endif]-->

	<script src="${ctx}/js/jquery.slimscroll.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/js/jquery.blockui.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/js/jquery.cookie.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/js/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script type="text/javascript">
	try{
		var ctx="${ctx}";
		var productCtx = ctx;
		if(productCtx.value){
			productCtx=productCtx.value;
		}
        var arr = productCtx.match(/http:\/\/[a-zA-Z0-9]+\.([a-zA-Z0-9.]+)[:]*[^:^/]*/);
		var domain = arr[1];
		if(domain.indexOf('sh.')==0){
			domain = domain.substr(3);
		}
		document.domain = domain;
 		//document.domain = '';
 	}catch(e){}
	</script>
	<!-- END CORE PLUGINS -->
	<sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
		
	<!-- END JAVASCRIPTS -->

</body>

<!-- END BODY -->

</HTML>