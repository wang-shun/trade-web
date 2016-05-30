<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
</head>
<body><jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<form action="#" method="post" id="excelForm"></form>
<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
<input type="hidden" id="prStatus" value="0"/>
<div class="">
    <div class="col-lg-13">
        <div class="">
            <div class="ibox-title">
               <h4>待处理产调</h4>
            </div>
        </div>
    </div>
</div>
<div class="ibox-content">
    <div class="jqGrid_wrapper">
        <table id="table_property_list"></table>
        <div id="pager_property_list"></div>
         <a class="btn btn-primary" onclick="exportToExcel();" id="expToexcel">导出产调至Excel</a>
   </div>                        
</div>

<content tag="local_script">
    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
     <script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/trunk/property/processWaitList.js"></script>




</content>
</body>
</html>
