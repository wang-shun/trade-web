<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<style type="text/css">
body {
	background-color: #f3f3f4;
}
</style>
</head>

<body>
	<h2>产调申请已提交誉萃人工处理，将于24小时内回复 。</h2>
	<c:forEach items="${userList}" var="item">
	${item.realName }-<a href="tel:${item.mobile }">${item.mobile }</a> </br>
	</c:forEach>
</body>
</html>
