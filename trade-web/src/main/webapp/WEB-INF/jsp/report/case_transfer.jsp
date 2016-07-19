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
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"
	rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<style type="text/css">
.radio label {
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

.org-label-control {
	background-color: #FFFFFF;
	background-image: none;
	border: 1px solid #e5e6e7;
	border-radius: 1px;
	color: inherit;
	display: block;
	padding: 6px 12px;
	transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s
		ease-in-out 0s;
	width: 45%;
	font-size: 14px;
}

.aline {
	text-decoration: underline;
}

.aline:HOVER {
	text-decoration: underline !important;
}

#inTextVal {
	width: 50%
}

.chosen-container {
	float: left;
	margin-right: 10px;
	margin-left: 15px
}

#addLine {
	line-height: 35px;
}

.product-type span {
	margin-right: 5px
}

.product-type .selected, .product-type span:hover {
	border-color: #f8ac59
}

.date-info .col-md-12 .form-group:not(first-child) {
	margin-bottom: 0
}

[id^="dateDiv_"]:not (#dateDiv_0 ) .chosen-container {
	margin-left: 33px
}

.text-center {
	text-align: center;
}

.slash {
	font-weight: bold !important;
}

.case-num {
	text-decoration: underline !important;
}

.case-num:HOVER {
	text-decoration: underline !important;
}

.case-num:visited {
	text-decoration: underline !important;
}

#searchButton {
	margin-right: 5px;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-md-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>过户案件筛选</h5>
					</div>
					<div class="ibox-content">
						<form method="get" class="form-horizontal">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">案件编号</label>
										<div class="col-md-8">
											<input type="text" id="caseCode" name="caseCode"
												class="form-control" value="">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">区董</label>
										<div class="col-md-8">
											<input type="text" id="realName" name="realName"
												class="form-control" value="">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">组别</label>
										<div class="col-md-8">
											<input type="text" class="form-control tbsporg" id="orgName"
												name="orgName"
												onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:'',chkLast:'true'})"
												value=''> <input type="hidden" id="yuCuiOriGrpId"
												name="yuCuiOriGrpId" value="">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">产证地址</label>
										<div class="col-md-8">
											<input type="text" id="propertyAddr" name="propertyAddr"
												class="form-control" value="">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-md-3 control-label m-l">主管</label>
										<div class="col-md-8">
											<input type="text" id="managerName"
												style="background-color: #FFFFFF" name="managerName"
												class="form-control tbspuser" readonly="readonly"
												onclick="chooseManager('${serviceDepId}')" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">过户审批状态</label>
										<div class="col-md-8">
											<select name="TransferStatus" id="TransferStatus"
												class="form-control">
												<option value="" selected="selected">-- 请选择 --</option>
												<option value="0">审批不通过</option>
												<option value="1">审批已通过</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">过户提交日期</label>
										<div class="col-md-8">
											<div id="datepicker_0"
												class="input-group input-medium date-picker input-daterange "
												data-date-format="yyyy-mm-dd">
												<input id="dtBegin_0" name="transferDateBegin"
													class="form-control" style="font-size: 13px;" type="text"
													value="${start}" placeholder="起始日期"> <span
													class="input-group-addon">到</span> <input id="dtEnd_0"
													name="transferDateEnd" class="form-control"
													style="font-size: 13px;" type="text" value="${end}"
													placeholder="结束日期" />
											</div>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group ">
										<label class="col-md-3 control-label m-l">过户审批提交日期</label>
										<div class="col-md-8">
											<div id="datepicker_1"
												class="input-group input-medium date-picker input-daterange "
												data-date-format="yyyy-mm-dd">
												<input id="dtBegin_1" name="caseTransferDateBegin"
													class="form-control" style="font-size: 13px;" type="text"
													placeholder="起始日期"> <span class="input-group-addon">到</span>
												<input id="dtEnd_1" name="caseTransferDateEnd"
													class="form-control" style="font-size: 13px;" type="text"
													placeholder="结束日期" />
											</div>
										</div>
									</div>
								</div>
							</div>


							<div class="row m-t-sm">
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-md-2 control-label m-l-lg"></label>
										<div>
											<button id="searchButton" type="button"
												class="btn btn-warning">查询</button>
											<button id="caseTransferCleanButton" type="button"
												class="btn btn-primary">清空</button>
										</div>
									</div>
								</div>
								<div class="col-md-6" style="text-align: right;">
									<a data-toggle="modal" class="btn btn-primary"
										href="javascript:void(0)"
										onclick="javascript:caseTransferExportToExcel()">案件导出Excel</a>
								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="data-wrap">
				<div class="data-wrap-in">
					<table border="1" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="t-left pd-l"><span class="sort"
									sortColumn="CASE_CODE" sord="desc">案件编号</span></th>
								<th class="t-left pd-l">产证地址</th>
								<th class="t-left pd-l">主管</th>
								<th class="t-left pd-l">组别</th>
								<th class="text-center"><span class="sort"
									sortColumn="transferDate" sord="desc">过户提交时间</span></th>
								<th class="text-center"><span class="sort"
									sortColumn="caseTransferDate" sord="desc">过户审批时间</span></th>
								<th class="text-center">是否审批通过</th>
								<th class="text-center">店组</th>
								<th class="text-center">片区</th>
								<th class="text-center">区域</th>
								<th class="text-center">区董</th>
							</tr>
						</thead>
						<tbody id="caseTransferList">
						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center">
				<span id="currentTotalPage"><strong class="bold"></strong></span> <span
					class="ml15">共<strong class="bold" id="totalP"></strong>条
				</span>&nbsp;
				<div id="pageBar" class="pagination my-pagination text-center m0"></div>
			</div>
		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/js/plugins/jquery.custom.js"></script> <script
		src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<script src="${ctx}/js/trunk/report/case_transfer.js?v=1.1"></script> <jsp:include
		page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		id="template_caseTransferList" type="text/html">
      {{each rows as item index}}
  				   {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}						
						<td class="t-left"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
						<td class="t-left">{{item.PROPERTY_ADDR}}</td>
						<td class="t-left">{{item.REAL_NAME}}</td>
						<td class="t-left">{{item.ORG_NAME}}</td>
						<td class="t-left">{{item.transferDate}}</td>
						<td class="t-left">{{item.caseTransferDate}}</td>
						<td class="text-center">{{item.status1}}</td>
						<td class="t-left">{{item.GRP_NAME}}</td>
						<td class="t-left">{{item.AR_NAME}}</td>
						<td class="t-left">{{item.VORG_NAME}}</td>
						<td class="t-left">{{item.VREAL_NAME}}</td>
				  </tr>
       {{/each}}
     </script> <script></script> </content>
</body>
</html>