<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
#page-wrapper {
    position: inherit;
     margin: 0 0 0 0px!important; 
</style>
</head>

<body>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">产调编号： ${propertyResearch.prCode }</span>
	</div>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">产调地址： ${propertyResearch.prAddress }</span>
	</div>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">产调项目： ${propertyResearch.prCat }</span>
	</div>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">执行人： ${prAppliantName}</span>
	</div>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">区蕫： ${prCostOrgMgr}</span>
	</div>
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">完成日期：<fmt:formatDate
				value="${propertyResearch.prCompleteTime }" pattern="yyyy-MM-dd HH:mm:ss" />
		</span>
	</div>
	<c:if test="${!(empty propertyResearch.isSuccess)}">
		<div class="row"
			style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
			<span class="col-lg-4">是否无效：
				${propertyResearch.isSuccess==0?"无效":"有效" }</span>
		</div>
	</c:if>
	<c:if test="${(propertyResearch.isSuccess==0)}">
	<div class="row"
		style="font-size: 14px; margin-bottom: 4px; margin-top: 4px;">
		<span class="col-lg-4">无效原因：${propertyResearch.unSuccessReason }
		</span>
	</div>
	</c:if>
	<c:forEach var="att" items="${attachments }">
		<div class="row">
			<img src="<aist:appCtx appName="img-centanet"/>/image/${att.preFileAdress}.jpg" width="100%">
		</div>
	</c:forEach>

</body>
</html>
