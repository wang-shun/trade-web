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

<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />">


<!-- 新调整页面样式 -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- 必须CSS -->
<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
<link href="<c:url value='/css/common/myCaseList.css' />" rel="stylesheet">
<style type="text/css">
.f-n {
	float: none !important;
	line-height: 30px;
}
#wrapper .ui-widget-content .ui-state-default.ui-th-ltr{
	background-color: #4bccec;
	font-size: 14px;
	font-weight: normal;
	color: #fff;
}

</style>
</head>

<body>
<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">
				案件筛选
			</h2>
			<form method="get" class="form-horizontal form_box">
				<div class="row clearfix">
					<div id="select_div_1" class="form_content">
						<div class="sign_left">
							<select class="form-control" id="inTextType" data-placeholder="搜索条件设定">
								<option value="0" selected>客户姓名</option>
								<option value="1">产证地址</option>
								<option value="2">经纪人姓名</option>
							</select>
						</div>
						<div class="sign_right teamcode">
							<input  id="inTextVal" type="text" class="form-control pull-left">
						</div>
					</div>
					<div class="form_content">
						<div id="dateDiv_0">
							<label class="control-label sign_left">
								收取时间范围
							</label>
							<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left"
								 data-date-format="yyyy-mm-dd">

								<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;"
									   type="text" value="" placeholder="起始日期">
                                            <span class="input-group-addon">
                                                到
                                            </span>
								<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;"
									   type="text" value="" placeholder="结束日期">
							</div>
						</div>
					</div>


				</div>
				<div class="row date-info clearfix">
					<div class="form_content">
						<label class="sign_left control-label">
							案件编号
						</label>
						<div class="sign_right teamcode">
							<input id="caseId"  type="text" class="teamcode form-control" />
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left">
							是否足额收取
						</label>
						<div class="checkbox i-checks radio-inline sign sign_right" >
							<label class="mr7">
								<input type="radio" value="" id="owner1" checked="checked" name="ownerRadios">
								全部
							</label>
							<label class="mr7">
								<input type="radio" value="1" id="owner1" name="ownerRadios">
								是
							</label>
							<label class="mr7">
								<input type="radio" value="0" id="owner2"
									   name="ownerRadios">
								否
							</label>
						</div>
					</div>


				</div>

				<div class="row product-type" id="productType" style="">
					<div class="form_content">
						<label class="sign_left control-label">
							产品类型
						</label>
						<div class="sign_right goods_type">
                                        <span id="srvCode_0_0" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            交易过户
                                        </span>
                                        <span id="srvCode_0_1" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            首付贷（抵押类）
                                        </span>
                                        <span id="srvCode_0_2" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            佣金卡
                                        </span>
                                        <span id="srvCode_0_3" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            商业贷款
                                        </span>
                                        <span id="srvCode_0_4" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            税费卡
                                        </span>
                                        <span id="srvCode_0_5" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            代收代付
                                        </span>
                                        <span id="srvCode_0_6" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            资金监管/安心宝
                                        </span>
                                        <span id="srvCode_0_7" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            纯公积金贷款
                                        </span>
                                        <span id="srvCode_0_8" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            首付贷
                                        </span>
                                        <span id="srvCode_0_9" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            消费贷
                                        </span>
                                        <span id="srvCode_0_10" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            换房贷
                                        </span>
                                        <span id="srvCode_0_11" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            抵押贷
                                        </span>
                                        <span id="srvCode_0_12" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            委托贷
                                        </span>
                                        <span id="srvCode_0_13" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            赎楼贷
                                        </span>
                                        <span id="srvCode_0_14" name="srvCode" class="btn btn-white" onclick=""
											  value="">
                                            全款贷
                                        </span>
						</div>
					</div>
				</div>
				<div class="row m-t-sm">
					<div class="form_content">
						<div class="more_btn">
							<button id="searchButton" type="button" class="btn btn-success mr5 btn-icon"><i class="icon iconfont">&#xe635;</i>
								查询
							</button>
							<button id="cleanButton" type="button" class="btn btn-grey">
								清空
							</button>
						</div>
					</div>
				</div>
				<div id="exportExcel">
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table id="table_list_1"></table>
					<div id="pager_list_1"></div>
				</div>
			</div>
		</div>
	</div>
<div id="modal-form" class="modal fade" role="dialog"
	 aria-labelledby="modal-title" aria-hidden="true">
	<div class="modal-dialog" style="width: 600px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
				<h4 class="modal-title" id="modal-title"></h4>
			</div>
			<form method="post" id="editForm" class="form-horizontal"
				  action="saveEvalItem">
				<div id="modal-data-show" class="modal-body"></div>
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
<div id="commit-form" class="modal fade" role="dialog"
	 aria-labelledby="modal-title" aria-hidden="false">
	<div class="modal-dialog" style="width: 200px; margin-top: 5%;">
		<div class="modal-content">
			<h4 class="modal-body">确定保存吗？</h4>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消
				</button>
				<button type="button" class="btn btn-primary"
						onclick="javascript:commitItem()">确定</button>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="queryOrg" value="${queryOrg}" />
<content tag="local_script">
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script><script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>

	<script src="<c:url value='/js/common/myCaseList.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/js/trunk/eval/evalFeeDesign.js' />"></script>

</content>

</body>
</html>
