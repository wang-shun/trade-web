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
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div id="modal-addOrModifyForm" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox ">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>添加/修改行政区域信息</h5>
							</div>

							<form id="addOrModifyForm">
								<input type="hidden" name="pkid" id="pkid" value="">
								
								<div class="ibox-content">
		
									<div class="form-group">
										<label class="col-sm-3 control-label">行政区域名称<span class="star">*</span>：</label>
										<div class="col-sm-8" >
										<input type="text" name="distName"
												id="distName" placeholder=""
												class="form-control" >
										</div>
									</div>
								  	<div class="form-group">
										<label class="col-sm-3 control-label">行政区域编码<span class="star">*</span>：</label>
										<div class="col-sm-8">
											<input type="text" name="distCode"
												id="distCode" placeholder=""
												class="form-control" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">誉萃部门名称：</label>
										<div class="col-sm-8">
											<input type="text" id="yuDistName" name="yuDistName" class="form-control" />										
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">誉萃部门编码：</label>
										<div class="col-sm-8">
											<input type="text" id="yuDistCode" name="yuDistCode" class="form-control" />
										
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<input type="button" class="btn btn-success" id="saveOrModifyBtn"
						value="提交">
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>行政区域查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;height:80px">
					<form method="get" class="form-horizontal">
						<div class="form-group ">
							<div class="form-group">
								<label class="col-md-2 control-label">行政区域编号：</label>
								<div class="col-md-2">
									<input type="text" name="orgCode"
												id="orgCode" placeholder=""
												class="form-control" >
								</div>
								<label class="col-md-2 control-label">行政区域名称：</label>
								<div class="col-md-2">
									<input type="text" name="orgName"
												id="orgName" placeholder=""
												class="form-control" >
								</div>
								<label class="col-md-2 control-label">誉萃部门名称：</label>
								<div class="col-md-2">
									<input type="text" name="yuOrgName"
												id="yuOrgName" placeholder=""
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
						<a href="#" id="delBtn" class="btn btn-primary" style="float:right;margin-right:5px" >删除</a>
						<a href="#" id="modifyBtn" class="btn btn-primary" style="float:right;margin-right:5px" >修改</a>
						<a href="#" id="addBtn" class="btn btn-primary" style="float:right;margin-right:5px" >添加</a>

						<h5>行政区域列表</h5>
						
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

	<content tag="local_script">	
	 <script src="<c:url value='/js/bootstrap-typeahead.js' />"></script>
	
	 <script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> <script
		src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> <script
		src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>

	<script src="<c:url value='/transjs/task/prResearchMapSet.js' />"></script> 

    <script>
    var ctx = "${ctx}";
    
    </script>
    
    </content>
</body>
</html>
