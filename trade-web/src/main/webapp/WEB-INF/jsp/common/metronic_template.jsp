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

<%-- <link href="${ctx}/media/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/media/css/bootstrap-responsive.min.css"
	rel="stylesheet" type="text/css" /> --%>
	
<link href="${ctx}/media/css/bootstrap.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/media/css/bootstrap-responsive.css"
	rel="stylesheet" type="text/css" />

<link href="${ctx}/media/css/font-awesome.min.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/media/css/style-metro.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/media/css/style.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/media/css/style-responsive.css" rel="stylesheet"
	type="text/css" />

<link href="${ctx}/media/css/default.css" rel="stylesheet"
	type="text/css" id="style_color" />

<link href="${ctx}/media/css/uniform.default.css" rel="stylesheet"
	type="text/css" />

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
<script src="${ctx}/media/js/jquery-1.10.1.min.js"
		type="text/javascript"></script>

<%@include file="/WEB-INF//jsp/tbsp/common/taglibs.jspf"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"%>
<!-- BEGIN BODY -->

<body class="page-header-fixed page-sidebar-closed">

	<!-- BEGIN HEADER -->

	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->
		<jsp:include page="/WEB-INF/jsp/common/metronic_head.jsp" flush="true" />
		<!-- END TOP NAVIGATION BAR -->

	</div>

	<!-- END HEADER -->

	<!-- BEGIN CONTAINER -->

	<div class="page-container">

		<!-- BEGIN SIDEBAR -->


			<jsp:include page="/WEB-INF/jsp/common/metronic_sidebar.jsp" flush="false"></jsp:include>

			

		<!-- END SIDEBAR -->

		<!-- BEGIN PAGE -->
		
<%-- 		<jsp:include page="<%=currentjsp%>" flush="true" /> --%>

		<sitemesh:body></sitemesh:body>
		

		<!-- END PAGE -->

	</div>

	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->

	<div class="footer">

		<div class="footer-inner">

			2015 &copy; AIST. 武汉爱思昕科技术咨询有限公司 <a
				href="http://www.aisthink.com/" title="AIST" target="_blank">AIST</a>

		</div>

		<div class="footer-tools">

			<span class="go-top"> <i class="icon-angle-up"></i>

			</span>

		</div>

	</div>

	<!-- END FOOTER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN GOLOBAL JS VAR -->
	<script type="text/javascript">
	</script>
	<!-- EN GLOBAL JS VAR -->

	<!-- BEGIN CORE PLUGINS -->

	<script src="${ctx}/media/js/jquery-1.10.1.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/media/js/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>

	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

	<script src="${ctx}/media/js/jquery-ui-1.10.1.custom.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/media/js/bootstrap.min.js" type="text/javascript"></script>

	<!--[if lt IE 9]>

	<script src="${ctx}/media/js/excanvas.min.js"></script>

	<script src="${ctx}/media/js/respond.min.js"></script>  

	<![endif]-->

	<script src="${ctx}/media/js/jquery.slimscroll.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/media/js/jquery.blockui.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/media/js/jquery.cookie.min.js"
		type="text/javascript"></script>

	<script src="${ctx}/media/js/jquery.uniform.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<jsp:include
	page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>	
	<sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
		
	<!-- END JAVASCRIPTS -->
</body>

<!-- END BODY -->

</HTML>