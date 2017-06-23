<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/static/image/favicon.ico' />" type="image/x-icon">  
    <link rel="shortcut icon" href="<c:url value='/static/image/favicon.ico' />" type="image/x-icon">  

    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">

    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/boxtemplate.css' />" rel="stylesheet">
    
    <sitemesh:head></sitemesh:head>
    
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

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
    <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <!-- jQuery UI -->
    <script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
    
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
 
    <script>
    </script>
</body>
</html>
