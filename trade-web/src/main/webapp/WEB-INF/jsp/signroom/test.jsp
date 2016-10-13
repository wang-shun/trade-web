<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	background-color: #f3f3f4;
}

.span_margin {
	margin-bottom: 12px;
}

.checker {
	float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;
}.checker1{
float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;}

.chkblock>div {
	display: block;
	float: none !important;
}

.row {
	margin-top: 10px;
}
</style>
</head>

<body>
	用户名 :  <input type="text" name="username" value="${username }">
	
	
	<content tag="local_script"> 
	    <script src="${ctx}/momedia/js/jquery.blockui.min.js"></script>  
	</content>
</body>
</html>
