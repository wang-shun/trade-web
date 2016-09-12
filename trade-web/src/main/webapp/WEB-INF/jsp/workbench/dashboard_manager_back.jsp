<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%
	response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- stickUp fixed css -->
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">

<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- ION-RANGESLIDER -->
<link
	href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">

<!-- fullcalendar -->
<link href="${ctx}/static/css/plugins/fullcalendar/fullcalendar.css"
	rel="stylesheet">
<link
	href="${ctx}/static/css/plugins/fullcalendar/fullcalendar.print.css"
	rel='stylesheet' media='print'>

<!-- morris -->
<link href="${ctx}/static/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">

<!-- fancybox -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/jquery.fancybox-buttons.css?v=1.0.5" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/jquery.fancybox-thumbs.css?v=1.0.7" />

<link href="${ctx}/static/trans/css/common/stickDash.css"
	rel="stylesheet">

<!-- iCheck -->
<link href="${ctx}/static/css/plugins/iCheck/custom.css"
	rel="stylesheet">

<!-- index_css  -->
<link href="${ctx}/static/trans/css/workbench/dashboard/dashboard.css"
	rel="stylesheet">
</head>

<body>
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">
	<input type="hidden" id="startDate" value="${startDate}">


	<!-- main Start -->
	<div
		class="row wrapper border-bottom white-bg page-heading stickup-nav-bar"
		style="z-index: 1 !important;">
		<ul class="nav navbar-nav">
			<li class="menuItem active"><a href="#base_info">基本信息</a></li>
			<li class="menuItem"><a href="#zj_info">资金流水</a></li>
		</ul>
	</div>


	<div class="row">
		<div class="wrapper wrapper-content animated fadeInUp">

			<!-- <div class="ibox"> -->
			<div class="ibox-content" id="base_info">
				<div class="row" style="position: relative;">
					<h5 class="main_titile"
						style="position: absolute; top: 0; left: 25px; font-size: 14px;">案件分布统计</h5>
					<div class="col-dash-8">
						<div id="mainwe" style="width: 100%; height: 250px;"></div>
					</div>
					<div class="col-md-3">
						<div class="task_light">
							<p class="fa_red">
								<i class="fa fa-bell "></i> 红灯任务 <small><a
									href="${ctx }/workspace/ryLightList?color=0" target="_blank">
										${redLight }</a></small>
							</p>
							<p class="fa_orange">
								<i class="fa fa-bell "></i> 黄灯任务 <small><a
									href="${ctx }/workspace/ryLightList?color=1" target="_blank">${yeLight }</a></small>
							</p>
							<p class="fa_orange">
								<i class="fa fa-bell "></i> 流失预警 <small><a
									href="${ctx }/bizwarn/list?status=0" target="_blank">${bizwarnCaseCount }</a></small>
							</p>
						</div>
					</div>
				</div>
			</div>



			<div class="ibox-content" id="zj_info">
				<div class="row m-t-sm" id="">
					<div class="col-lg-12">
						<div class="panel blank-panel">
							<div class="panel-heading">
								<div class="panel-options">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab-1" data-toggle="tab">工作数据显示</a>
										</li>
										<li class=""><a href="#tab-2" data-toggle="tab">业务提醒</a>
										</li>
										<li class=""><a href="#tab-3" data-toggle="tab">龙虎榜</a></li>
									</ul>
								</div>
							</div>

							<div class="panel-body">
								<div class="tab-content no_border">
									<div class="tab-pane active" id="tab-1">
										<div class="row">
											<div class="col-md-12">
												<div class="ibox float-e-margins">
													<div class="ibox-content" style="border-width: 0px;">
														<div class="row">
															<div class="col-md-4">
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>任务</th>
																			<th>今天</th>
																			<th>明天</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${managerWorkLoad.listByTask}"
																			var="item">
																			<tr>
																				<td>${item.name }</td>
																				<td>${item.tCount }</td>
																				<td>${item.yCount }</td>
																			</tr>
																		</c:forEach>
																		<c:if test="${empty managerWorkLoad.listByTask}">
																			<tr>
																				<td>无</td>
																				<td></td>
																				<td></td>
																			</tr>
																		</c:if>
																	</tbody>
																</table>
															</div>
															<div class="col-md-8">
																<table class="table table-bordered">
																	<thead>
																		<tr>
																			<th>人员</th>
																			<th>今天</th>
																			<th>明天</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${managerWorkLoad.listByUser}"
																			var="item">
																			<tr>
																				<td>${item.userName }</td>
																				<td>${item.tCountStr }</td>
																				<td>${item.yCountStr }</td>
																			</tr>
																		</c:forEach>
																		<c:if test="${empty managerWorkLoad.listByUser}">
																			<tr>
																				<td>无</td>
																				<td></td>
																				<td></td>
																			</tr>
																		</c:if>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane" id="tab-2">
										<div class="row">
											<div class="col-md-4">
												<div class="widget-box">
													<div class="widget-header widget-header-flat">
														<h4 class="smaller">反馈提醒</h4>
														<div class="widget-toolbar">
															<label> <span class="label label-blue">0</span>
															</label>
														</div>
													</div>

													<div class="widget-body">
														<div
															style="height: 320px; overflow: hidden; overflow-y: scroll; width: 100%;">
															<div id="div_messagelist1" style="min-height: 320px;"
																class="widget-main"></div>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="widget-box">
													<div class="widget-header widget-header-flat">
														<h4 class="smaller">作业提醒</h4>
														<div class="widget-toolbar">
															<label> <span class="label label-blue">0</span>
															</label>
														</div>
													</div>

													<div class="widget-body">
														<div
															style="height: 320px; overflow: hidden; overflow-y: scroll; width: 100%;">
															<div id="div_messagelist2" style="min-height: 320px;"
																class="widget-main"></div>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="widget-box">
													<div class="widget-header widget-header-flat">
														<h4 class="smaller">止损提醒</h4>
														<div class="widget-toolbar">
															<label> <span class="label label-blue">0</span>
															</label>
														</div>
													</div>

													<div class="widget-body">
														<div
															style="height: 320px; overflow: hidden; overflow-y: scroll; width: 100%;">
															<div id="div_messagelist3" style="min-height: 320px;"
																class="widget-main"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- 龙虎榜 -->
									<div class="tab-pane" id="tab-3">
										<script type="text/javascript">
											function imgLoad(img) {
												img.parentNode.style.backgroundImage = "url("
														+ img.src + ")";
											}
										</script>
										<div class="row dragon">
											<div class="col-md-6">
												<div class="panel panel-danger">
													<div class="panel-heading">
														E+金融申请榜 <span class="btn btn-xs btn-white pull-right">
															<strong id="loanAmountRank" class="text-danger">
														</strong>
														</span> 
													</div>
													<div class="panel-body">
														<div class="feed-activity-list" id="loanAmountRankList"></div>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="panel panel-warning">
													<div class="panel-heading">
														E+金融签约榜 <span class="btn btn-xs btn-white pull-right">
															<strong id="signAmountRank" class="text-danger">
														</strong>
														</span> 
													</div>
													<div class="panel-body">
														<div class="feed-activity-list" id="signAmountRankList"></div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<div class="panel panel-info">
													<div class="panel-heading">
														E+金融放款榜 <span class="btn btn-xs btn-white pull-right">
															<strong id="actualAmountRank" class="text-danger"></strong>
														</span> 
													</div>
													<div class="panel-body">
														<div class="feed-activity-list" id="actualAmountRankList"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="portlet-body" style="display: block;">
				<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
			</div>
			<!-- 待办事项 -->
			<div class="ibox-content">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins col-heigth">
							<div class="ibox-title"
								style="border: none !important; padding-top: 5px;">
								<h5>待办事项</h5>
							</div>
							<div class="ibox-content"
								style="margin-top: 0px !important; border: none !important;">
								<div id="calendar"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- main End -->

	</div>
	</div>
	<content tag="local_script"> <!-- stickup plugin --> 
	<script	src="${ctx}/static/js/plugins/stickup/stickUp.js"></script> 
	<!-- owner -->
	<script src="${ctx}/static/trans/js/workbench/stickDash.js"></script> 
	<script	src="${ctx}/static/trans/js/workbench/caseCount.js"></script> 
	<script	src="${ctx}/static/trans/js/workbench/dashboard.js"></script> 
	<!-- Toastr script -->
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> 
	<script	src="${ctx}/static/js/morris/morris.js"></script> 
	<script	src="${ctx}/static/js/morris/raphael-min.js"></script> 
	<!-- messageGrid -->
	<script src="${ctx}/static/js/messageGrid.js"></script> 
	<!-- jquery.formatMoney -->
	<script src="${ctx}/static/js/jquery.formatMoney.js"></script> 
	<!-- fullcalendar -->
	<script src="${ctx}/static/js/plugins/fullcalendar/moment.min.js"></script>
	<script src="${ctx}/static/js/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="${ctx}/static/js/plugins/fullcalendar/zh-cn.js"></script>
	<!-- IonRangeSlider --> 
	<script	src="${ctx}/static/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<!-- Add fancyBox main JS and CSS files --> 
	<script	src="${ctx}/static/js/jquery.fancybox.js?v=2.1.5"></script> 
	<script	src="${ctx}/static/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<script src="${ctx}/static/js/jquery.fancybox-thumbs.js?v=1.0.7"></script> 
	<script	src="${ctx}/static/js/jquery.fancybox-media.js?v=1.0.6"></script> 
	<!-- iCheck --> 
	<script	src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script> 
	<!-- ECharts.js -->
	<script src="${ctx}/static/js/echarts.min.js"></script> 
	<script	src="${ctx}/static/trans/js/workbench/dashboard_echart.js"></script> 
	<script	src="${ctx}/static/trans/js/workbench/caseCount.js"></script> 
	<script	src="${ctx}/static/trans/js/workbench/dashboard.js"></script> 
	<script	type="text/javascript">
	$(document).ready(function() {
			//加载echarts
			reloadStatus();

			reloadMonth();	
			
			//龙虎榜数据查询
			queryGetRankBank();
			$('#sp_evalFee').on('click', evalFeeClick);
		});
	</script> 
</content>
</body>

</html>