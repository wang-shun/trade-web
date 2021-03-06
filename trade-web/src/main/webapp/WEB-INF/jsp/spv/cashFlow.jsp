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
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"
	rel="stylesheet">
<style type="text/css">
.form-group label {
	text-align: right;
}
.form-group div {
	height: 35px;
}
.form-control {
	margin-bottom: 5px;
	height:32px;
}
.col-sm-10{
	height:37px;
}
.col-md-2{
	width:12%
}

</style>
</head>

<body>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>进出账查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
						<div class="form-group ">
							<div class="form-group">
								<label class="col-md-2 control-label">案件编号：</label>
								<div class="col-md-2">
									<input type="text" name="caseCode"
												id="caseCode" placeholder=""
												class="form-control" >
								</div>
								<label class="col-md-2 control-label">类型：</label>
								<div class="col-md-2">
									<aist:dict id="cashFlowType" name="cashFlowType" clazz="form-control m-b"
										display="select" defaultvalue="" dictType="yu_serv_cat_code_tree" />
								</div>
								<label class="col-md-2 control-label">流水方向：</label>
								<div class="col-md-2">
									<aist:dict id="cashDirection" name="cashDirection" clazz="form-control m-b"
										display="select" defaultvalue="" dictType="yu_cash_direction" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">开始时间：</label>
								<div class="col-md-2">
									<input type="text" name="startTime"
												id="startTime" placeholder=""
												class="form-control" >
								</div>
								<label class="col-md-2 control-label">截止时间：</label>
								<div class="col-md-2">
									<input type="text" name="endTime"
												id="endTime" placeholder=""
												class="form-control" >
								</div>
								
								<div class="col-sm-1">
									<button id="searchButton" type="button" class="btn btn-primary pull-right">查询</button>
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight" style="padding:0px">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">

						<h5>资金进出账列表</h5>
						
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
	<form action="#" method="post" id="excelForm"></form>
	
	<content tag="local_script">	
	<script src="<c:url value='/js/bootstrap-typeahead.js' />"></script>
	 <script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> <script
		src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> <script
		src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 

	<script src="<c:url value='/transjs/spv/cashFlow.js' />"></script> 

    <script>
    var ctx = "${ctx}";
    
    </script>
    
    </content>
</body>
</html>
