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

<body>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
<input type="hidden" id="prStatus" value="2"/>
<div class="">
	<div class="col-lg-13">
	     <div class="">
	         <div class="ibox-title">
	            <h4>已完成产调</h4>
	            </div>
	        </div>
     </div>
</div>
<div class="ibox-content">
    <div class="jqGrid_wrapper">
        <div>物业地址 :<input type="text" id="addr"/>
			<button id="addrSearchButton" style="margin-left: 30px;">搜索</button>
		</div>
		<hr>
         <table id="table_property_list"></table>
         <div id="pager_property_list"></div>
     </div>                        
</div>
<content tag="local_script">
    <!-- Mainly scripts -->
    <script src="${ctx}/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/js/bootstrap.min.js"></script>
    <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>


    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>

	<script src="${ctx}/js/trunk/property/successList.js"></script>
	<script src="${ctx}/js/trunk/property/propertyByaddr.jqgridSearch.js"></script>
	<script>
	jQuery(document).ready(function() {
		 var addr = $("#addr").val();
		 JQGrid_propertyByaddSearch.init('table_property_list','addrSearchButton',addr);
	});
	</script>

</content>
</body>
</html>
