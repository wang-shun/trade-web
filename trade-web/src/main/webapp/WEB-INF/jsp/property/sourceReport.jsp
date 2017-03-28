<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>产调来源报表</title>

<!-- General Style -->
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" />
<link rel="stylesheet" href="${ctx}/static/css/style.css" />

<!-- Data range select -->
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- datapicker -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/datapicker/datepicker3.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />

<!-- jQuery UI -->
<link rel="stylesheet"
	href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" />

<!-- 分页控件 -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/pager/centaline.pager.css" />
<link rel="stylesheet"
	href="${ctx}/static/trans/css/property/popmac.css" />

<link rel="stylesheet"
	href="${ctx}/css/trans/css/propertyresearch/successList.css" />

</head>

<body>
	<div class="row">
		<div class="col-md-12">
			<!--*********************** HTML_main*********************** -->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content border-bottom clearfix space_box" id="reportblock">
					<input type="hidden" id="xlsx" name="xlsx" value="xlsx" /> 
					<input type="hidden" id="queryId" name="queryId" value="querySourceList" /> 
					<input type="hidden" id="ctx" value="${ctx}" /> 
					<input type="hidden" id="prDistrictId" name="search_prDistrictId" value="${prDistrictId}" /> 
					<input type="hidden" id="prDep" name="search_prDep" value="${prDep}" />
					<h2 class="title" id="reportTitle">产调来源报表</h2>
					<div class="row">
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right" id="labelAll">0</span>
									<h5>全部产调</h5>
								</div>
								<div class="ibox-content" style="width: 380px; height: 400px;" id="pieChartAll">
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right" id="labelS">0</span>
									<h5>有效产调</h5>
								</div>
								<div class="ibox-content" style="width: 380px; height: 400px;" id="pieChartOne">
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right" id="labelUS">0</span>
									<h5>无效产调</h5>
								</div>
								<div class="ibox-content" style="width: 380px; height: 400px;"
									id="pieChartZero"></div>
							</div>
						</div>
						<div class="col-md-2">
							<div class="line">
								<div class="form_content">
									<select name="searchTimeType" id="timeType"
										class="form-control mend_select sign_left_small"
										style="width: 105px; margin-right: 0px;">
										<option value="0" selected="selected">申请时间</option>
										<option value="1">受理时间</option>
										<option value="2">完成时间</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<div class="line">
								<div class="add_btn" style="margin-left: 0px;">
									<button id="searchBtn" type="button" class="btn btn-success">
										<i class="icon iconfont"></i> 查询
									</button>
								</div>
							</div>
						</div>
						<div class="col-md-7">
							<div id="datarange" class="ionr"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div id="sourceReport" class="table_content"></div>
		</div>
	</div>

	<content tag="local_script"> 
	
	<script src="${ctx}/js/plugins/chartJs/echarts.js" type="text/javascript"></script>

	<!-- js模板引擎 -->
	<script src="${ctx}/static/js/template.js"></script>
	<!-- 分页控件  -->
	<script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<!-- 自定义扩展jQuery库 -->
	<script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
	<script src="${ctx}/static/trans/js/property/aist.jquery.custom.ps.js"></script>

	<!-- datapicker -->
	<script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	
	<!-- 日期拖拽 -->
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.js"></script>
	<!-- <script src="${ctx}/js/plugins/moment/moment-with-locales.js"></script> -->
	<script src="${ctx}/js/plugins/moment/moment-with-locales.js"></script>

	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	<!-- owner -->
	<script src="${ctx}/js/trunk/property/propertySourceReport.js"></script>
	
	<script id="template_sourceReport" type="text/html">
	</script>
	    
    </content>
	
</body>