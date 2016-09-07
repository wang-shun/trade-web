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

<script src="${ctx}/js/plugins/chartJs/echarts.js"
	type="text/javascript"></script>

</head>

<body>
	<div class="row">
		<div class="col-md-12">
			<!--*********************** HTML_main*********************** -->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content border-bottom clearfix space_box">
					<!-- 					<h4>已完成产调</h4> -->
					<h2 class="title">产调来源报表</h2>
					<div class="row">
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right">0</span>
									<h5>全部产调</h5>
								</div>
								<div class="ibox-content">
									
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right">0</span>
									<h5>有效产调</h5>
								</div>
								<div class="ibox-content">
									
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="ibox float-e-margins no-records msGrid">
								<div class="ibox-title">
									<span class="label label-success pull-right">0</span>
									<h5>无效产调</h5>
								</div>
								<div class="ibox-content">
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>