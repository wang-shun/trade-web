<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>

<head>

    <meta charset="utf-8">
    
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <link href="<c:url value='/momedia/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/momedia/font-awesome/css/font-awesome.css' />" rel="stylesheet">

    <link href="<c:url value='/momedia/css/animate.css' />" rel="stylesheet">
    
    <sitemesh:head></sitemesh:head>
    
    <link href="<c:url value='/momedia/css/style.css' />" rel="stylesheet">
	<style type="text/css">
		.row{
	margin-left: 0px;
}
	</style>
</head>

<body>
    <div id="wrapper">
    

		<!-- 右侧内容 -->
        <div id="page-wrapper" class="gray-bg dashbard-1">
        
        	<!-- 右侧页面抬头 -->
	        
	        <!-- 右侧页面主体内容 -->
	        <sitemesh:body></sitemesh:body>
                
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="<c:url value='/momedia/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/momedia/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/momedia/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/momedia/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <!-- jQuery UI -->
    <script src="<c:url value='/momedia/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
    
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
 
    <script>
    </script>
</body>
</html>
