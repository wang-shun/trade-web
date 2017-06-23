<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

    <!-- Gritter -->
	<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">
    <style type="text/css">
    	body{background-color: #fff !important;}
    	.pic-css{margin-left: 15px;}
    </style>
</head>

<body>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="userId" value="${userId}"/>
<input type="hidden" id="prStatus" value="2"/>
<div class="">
	<div class="col-lg-13">
	         <div class="ibox-title">
	            <h3>产调结果</h3>
	        </div>
     </div>
     <hr>
</div>
<div>
	<c:if test="${toAttachmentListLength > 0}">
		<c:forEach items="${toAttachmentList}" var="toAttachment">
			<a class="pic-css" href='${imgHost}/filesvr/downLoad?id=${toAttachment.preFileAdress}' title='点击下载' download="${toAttachment.fileName}">${toAttachment.fileName}</a>
			<hr>
		</c:forEach>
	</c:if>
</div>
<content tag="local_script">
    <!-- Mainly scripts -->

    <!-- Peity -->
    <script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>

    <!-- jqGrid -->
    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>

	<script src="<c:url value='/js/trunk/property/resultGetList.js' />"></script>
	<%-- <script src="<c:url value='/js/trunk/property/propertyByaddr.jqgridSearch.js' />"></script> --%>
	<script src="<c:url value='/js/trunk/property/resultGetListByaddr.jqgridSearch.js' />"></script>
	<script>
	jQuery(document).ready(function() {
		 var addr = $("#addr").val();
		 JQGrid_resultGetByaddSearch.init('table_property_list','addrSearchButton',addr);
	});
	</script>

</content>
</body>
</html>
