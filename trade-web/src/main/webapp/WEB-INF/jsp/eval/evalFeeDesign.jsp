<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">

<style type="text/css">
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>案件筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="input-group m-b">
							<div class="input-group-btn">
								<select id="inTextType" data-placeholder="搜索条件设定"
									class="btn btn-white chosen-select">
									<option value="0" selected>客户姓名</option>
									<option value="1">物业地址</option>
									<option value="2">经纪人姓名</option>
								</select>
							</div>
							<input id="inTextVal" type="text" class="form-control">

						</div>
						<span class="input-group-btn ">
							<button id="searchButton" type="button"
								class="btn btn-primary pull-right">查询</button>
						</span>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>我的案件列表</h5>
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
	 <!-- 评估费核实 -->
	<div id="modal-form" class="modal fade" role="dialog"
		aria-labelledby="modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 600px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="modal-title"></h4>
				</div>
				<form method="post" id="editForm" class="form-horizontal" action="saveEvalItem">
				<div id="modal-data-show" class="modal-body">
					
				</div>
				</form>
				<div class="modal-footer">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:saveEvalItem()">保存</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrg" value="${queryOrg}" />
<content tag="local_script"> 
	<script	src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script	src="${ctx}/js/trunk/eval/evalFeeDesign.js"></script> 
 </content>
</body>
</html>
