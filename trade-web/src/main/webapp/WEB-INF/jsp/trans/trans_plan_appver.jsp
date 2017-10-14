<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">


<style type="text/css">
</style>
</head>
<%--by wbzhouht--%>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>交易计划变更审核</h5>
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-btn">
		<div class="text-center">
			<!-- 流程引擎需要字段 -->
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
			<input type="hidden" id="processInstanceId"
				   name="processInstanceId" value="${processInstanceId}">
			<input type="hidden" id="ctx" value="${ctx}" />
			<input type="hidden" id="caseCode" value="${caseCode}" />
			<a href="#" class="btn btn-success btn-space" onclick="save(true)">同意</a>
			<a href="#" class="btn btn-success btn-space" onclick="save(false)">不同意</a>
		</div>
	</div>
<content tag="local_script">
	<!-- Peity -->
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- Custom and plugin javascript -->
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script	src="<c:url value='/js/trunk/trans/trans_plan_appver.js' />"></script>
	<!-- 必须JS -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
 </content>
</body>
</html>
