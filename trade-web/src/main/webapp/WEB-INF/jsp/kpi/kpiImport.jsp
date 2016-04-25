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
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/switch/bootstrap-switch.min.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}

.ui-state-hover {
	cursor: pointer;
}

.btn-xm {
	margin-left: 10px;
	margin-top: 10px;
	width: 160px;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>个人案件KPI导入</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row form-group">
							<div class="switch " data-on-label="上月" data-off-label="当月">
								<input id="moSwitch" type="checkbox" checked />
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<label class="col-sm-5 control-label" id="case_date">案件编号</label>
								<input id="caseCode" type="text" class="form-control"
									style="width: 140px">
							</div>
							<div class="col-md-3">
								<label class="col-sm-5 control-label" id="case_date">环节</label>
								<input id="srvCode" type="text" class="form-control"
									style="width: 140px">
							</div>
							<div class="col-md-3">
								<label class="col-sm-5 control-label" id="case_date">所在级别</label>
								<input id="orgCode" type="text" class="form-control"
									style="width: 140px">
							</div>
							<span class="col-md-3 ">
								<button id="searchButton" type="button"
									class="btn btn-primary pull-right">导入</button>
							</span>
						</div>

					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>个人案件KPI明细</h5>
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

		<div class="ibox-content">
			<div class="row">
				<a role="" class="btn btn-primary btn-xm" id="importButton">个人案件KPI导入 </a>
			</div>
		</div>
		
		<!-- 失败数据 -->
		<div id="error-modal-form" class="modal fade" role="dialog"
			aria-labelledby="excel-modal-title" aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="excel-modal-title">导入失败数据</h4>
					</div>

					<div class="modal-footer">
						<div class="ibox float-e-margins">

							<div class="ibox-content">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>案件编号</th>
											<th>案件地址</th>
											<th>人员</th>
											<th>员工编号</th>
											<th>所有组织</th>
											<th>岗位</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${fList}" var="item">
											<tr>
												<td>${item.caseCode }</td>
												<td>${item.caseAddress }</td>
												<td>${item.userName }</td>
												<td>${item.employeeCode }</td>
												<td>${item.orgName }</td>
												<td>${item.jobName }</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/layer/layer.js"></script> <script
		src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script> <script>
			sw = $("#moSwitch").bootstrapSwitch({
				'onText' : "上月",
				'offText' : '当月',
				state : false
			}).on('switchChange.bootstrapSwitch', function(event, state) {
			});
			$("#importButton").click(
					function() {
						//iframe层
						layer.open({
							type : 1,
							title : '个人案件KPI导入 ',
							shadeClose : true,
							shade : 0.8,
							area : [ '50%', '40%' ],
							content : $('#excelImport'), //捕获的元素
							btn : [ '提交', '关闭' ],
							yes : function(index) {
								if (checkFileTypeExcel()) {
									$.blockUI({
										message : $("#salesLoading"),
										css : {
											'border' : 'none',
											'z-index' : '9999'
										}
									});
									$(".blockOverlay").css({
										'z-index' : '9998'
									});
									$("#excelInForm").attr("action",
											ctx + "/kpi/doImport");
									$("#excelInForm").find("input[name='currentMonth']").remove();
									$("#excelInForm").append($("<input>").attr({type:'hidden',name:'currentMonth'}).val($("#moSwitch").attr('checked')=='checked'));
									$("#excelInForm").attr("method", "POST");
									$('#excelInForm').submit();

									layer.close(index);
								}
							},
							cancel : function(index) {
								layer.close(index);
								//layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
							}
						});
					})
		</script> </content>
</body>
</html>
