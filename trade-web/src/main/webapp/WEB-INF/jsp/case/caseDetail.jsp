<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">

<link href="${ctx}/css/plugins/jasny/jasny-bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />

<style type="text/css">
/* body {
	overflow: hidden;
	overflow-y: hiden;
}  */

.checkbox.checkbox-inline>div {
	margin-left: 28px;
}

.checkbox.checkbox-inline>input {
	margin-left: 23px;
}

.modal-body {
	padding: 10px !important
}

.wrapper-content {
	padding: 0 !important
}

.userHead {
	width: 80px;
	height: 80px;
	display: inline-block;
	border-radius: 50%;
	background-size: 80px 108px;
	vertical-align: middle;
	background-image: url(../img/a5.png);
}

[class^=mark] {
	position: absolute;
	top: 8px;
	left: 130px;
	width: 56px;
	height: 37px;
	z-index: 0;
	background-position: left center;
	background-repeat: no-repeat
}

.mark-baodan {
	background-image: url(../img/mark-baodan.png);
}

.mark-guaqi {
	background-image: url(../img/mark-guaqi.png);
}

.mark-jiean {
	background-image: url(../img/mark-jiean.png);
}

.mark-wuxiao {
	background-image: url(../img/mark-wuxiao.png);
}
/* .mark-zaitu{background-image:url(../img/mark-zaitu.png);} */
.row:nth-last-child(2) .wd-31, .row:nth-last-child(1) .wd-31 {
	width: 31%;
}

.row:nth-last-child(2) .wd-50, .row:nth-last-child(1) .wd-50 {
	width: 50%;
}

.row:nth-last-child(2) .mr0, .row:nth-last-child(1) .mr0 {
	margin-left: 0;
	margin-right: 0;
}

.row:nth-last-child(2) .wd87, .row:nth-last-child(1) .wd87 {
	width: 84px;
	margin-left: 8px;
}

.row:nth-last-child(2) .wd-72, .row:nth-last-child(1) .wd-72 {
	width: 72%;
	margin-left: -8px;
}

.row:nth-last-child(2) .wd-64, .row:nth-last-child(1) .wd-64 {
	width: 61%;
	padding-right: 0;
}

.row:nth-last-child(2) .form-control-static, .row:nth-last-child(1) .form-control-static
	{
	margin-left: -2px;
}

.pd0 {
	padding: 0;
}

.pr0 {
	padding-right: 0;
}

.wd445 {
	margin-bottom: 15px;
	padding-right: 30px;
}

.wd445 select, .kuaquselect select {
	float: right;
	height: 34px;
	border-radius: 2px;
	margin-left: 10px;
}

#hzxm {
	padding-bottom: 15px;
}

.kuaquselect {
	margin: -20px 0 0 0;
	padding-right: 0;
}

.modal-content {
	width: 820px;
}

.text-left {
	text-align: left !important;
	margin-left: -10px;
}

.row {
	margin-right: 0px !important;
}

.modal-content {
	width: 1000px !important;
}

.min-h215 {
	min-height: 215px;
}

/* add_css */
.infos {
	margin: 13px 0px 12px;
}

p.title {
	margin-left: 15px;
}

.tittle h4 {
	margin-left: 25px;
}

.white_bg, .item .panel {
	background: #fff;
}

.ibox-conn {
	padding: 8px 10px;
	border: 1px solid #eee;
	border-bottom-right-radius: 3px;
	border-bottom-left-radius: 3px;
}

.info_box span {
	padding: 3px 10px;
	font-size: 14px;
	font-weight: bold;
	color: #ffffff;
	background-color: #00CFEC;
	display: inline-block;
	border-top-right-radius: 3px;
	border-top-left-radius: 3px;
}

.else_conn {
	height: 40px;
	line-height: 40px;
	margin-bottom: 15px;
}

.ibox-conn .col-sm-6 {
	padding-left: 0;
}

.scroll_box dl {
	color: #666;
}

.else_conn_two {
	min-height: 132px;
}

.tab-content .tab-pane p {
	margin-top: 10px !important;
}

.tab-content .tab-pane {
	padding-top: 20px !important;
}

.ibox-text {
	height: 214px;
}


.nav-tabs>li.active>a, .nav-tabs>li.active>a:hover, .nav-tabs>li.active>a:focus
	{
	color: #555 !important;
	cursor: default !important;
	background-color: #fff !important;
	border: 1px solid #ddd !important;
	border-bottom-color: transparent !important;
}

.panel {
	border-radius: 0px !important;
	margin-bottom: 12px;
	min-height:300px;
}


 .wrapper .top12{
   margin-top:12px !important;
}
.scroll_box .navbar {
	margin-top: 20px;
}

.collapse {
	padding-left: 0px;
}

.scroll_nav {
	background: #4bccec;
}

.scroll_content li.active {
	border: none !important;
	background: #faab58 !important;
}

.scroll_content li a:hover {
	border: none !important;
	background: #faab58 !important;
}

.scroll_content li a {
	color: #fff !important;
}

.navbar-default .navbar-nav>.active>a, .navbar-default .navbar-nav>.active>a:focus,
	.navbar-default .navbar-nav>.active>a:hover {
	color: #fff;
	background-color: #faab58 !important;
}

@media ( min-width : 768px) {
	.ibox-conn .dl-horizontal dt {
		width: 60px;
		line-height: 22px;
	}
	.ibox-conn .dl-horizontal dd {
		margin-left: 70px;
		margin-bottom: 7px;
		line-height: 22px;
	}
}

.navbar {
	margin-bottom: 0px;
}

dt {
	color: #777;
	font-weight: normal;
}

.page-wrapper {
	margin-left: 12px;
	margin-right: 12px;
}

.ibox-content-head h5 {
	margin-left: 20px;
	float: left;
	margin-bottom: 15px;
}

.wrapper .row {
	margin-left: 0px !important;
}
.isStuck{
z-index:1500;width:100%
}

</style>
</head>

<body>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}" />
	<input type="hidden" id="caseCode" value="${toCase.caseCode}" />
	<input type="hidden" id="instCode" value="${toWorkFlow.instCode}" />
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}" />
	<input type="hidden" id="processDefinitionId"
		value="${toWorkFlow.processDefinitionId}" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<script>
				<%if (request.getAttribute("msg") == null || request.getAttribute("msg") == "") {%>
				<%} else {%>
					alert("<%=request.getAttribute("msg")%>");
				<%}%>
			</script>

	<%-- <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include> --%>
	<!-- 主要内容页面 -->
					<nav id="navbar-example" class="navbar navbar-default navbar-static"
					role="navigation" >
					<div id="isFixed" style="position:relative; top: 0px;"
						class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
						<ul class="nav navbar-nav scroll_content">
							<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
							<li class="menuItem"><a href="#serviceFlow"> 服务流程 </a></li>
							<li class="menuItem"><a href="#aboutInfo"> 相关信息 </a></li>
							
						</ul>
					</div>
				</nav>
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
					<div class="top12 panel" id="basicInfo"> 
						<div class="panel-body" >
							<div class="ibox-content-head">
								<h5>案件基本信息</h5>
								<small class="pull-right">誉萃编号：${toCase.caseCode}｜中原编号：${toCase.ctmCode}</small>
							</div>
							<div id="infoDiv infos" class="row">
								<div class="ibox white_bg">
									<div class="info_box info_box_one col-sm-4 ">
										<span>物业信息</span>
										<div class="ibox-conn ibox-text">
											<dl class="dl-horizontal">
												<dt>CTM地址</dt>
												<dd>${toPropertyInfo.ctmAddr}</dd>
												<dt>产证地址</dt>
												<dd>${toPropertyInfo.propertyAddr}</dd>
												<dt>层高</dt>
												<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
												<dt>产证面积</dt>
												<dd>${toPropertyInfo.square}平方</dd>
												<dt>房屋类型</dt>
												<dd>
													<aist:dict id="propertyType" name="propertyType"
														display="label" dictType="30014"
														dictCode="${toPropertyInfo.propertyType}" />
												</dd>
											</dl>
										</div>
									</div>
									<div class="info_box info_box_two col-sm-5">
										<span>买卖双方</span>
										<div class="ibox-conn else_conn">
											<dl class="dl-horizontal col-sm-6">
												<dt>上家姓名</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${caseDetailVO.sellerMobile}">${caseDetailVO.sellerName}
													</a>
												</dd>
											</dl>
											<dl class="dl-horizontal col-sm-6">
												<dt>下家姓名</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${caseDetailVO.buyerMobile}">${caseDetailVO.buyerName}
													</a>
												</dd>
											</dl>
										</div>
										<span>经纪人信息</span>
										<div class="ibox-conn else_conn_two ">
											<dl class="dl-horizontal">
												<dt>姓名</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${toCaseInfo.agentPhone}">
														${caseDetailVO.agentName}</a>
												</dd>
												<dt>所属分行</dt>
												<dd>${toCaseInfo.grpName }</dd>
												<dt>直管经理</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${caseDetailVO.mcMobile}">
														${caseDetailVO.mcName} </a>
												</dd>
											</dl>
										</div>
									</div>
									<div class="info_box info_box_three col-sm-3">
										<span>经办人信息</span>
										<div class="ibox-conn  ibox-text">
											<dl class="dl-horizontal">
												<dt>交易顾问</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${caseDetailVO.cpMobile}">
														${caseDetailVO.cpName} </a>
												</dd>
												<c:if test="${empty caseDetailVO.proList}">
													<dt>合作顾问</dt>
													<dd></dd>
												</c:if>
												<c:if test="${!empty caseDetailVO.proList}">
													<c:forEach items="${caseDetailVO.proList}" var="pro">
														<dt>合作顾问</dt>
														<dd>
															<a data-toggle="popover" data-placement="right"
																data-content="${pro.processorMobile}">
																${pro.processorName} </a>
														</dd>
													</c:forEach>
												</c:if>
												<dt>助理</dt>
												<dd>
													<a data-toggle="popover" data-placement="right"
														data-content="${caseDetailVO.asMobile}">
														${caseDetailVO.asName} </a>
												</dd>
											</dl>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
					<!-- 服务流程 -->
					<div class="panel " id="serviceFlow">
						<div class="panel-body" >
							<ul class="nav nav-tabs">
								<li class="active"><a href="#settings" data-toggle="tab">操作纪录</a>
								</li>
								<li class=""><a href="#profile" data-toggle="tab">案件基本操作</a>
								</li>
								<li class=""><a href="#messages" data-toggle="tab">案件进程总览</a>
								</li>
								<li class=""><a href="#home" data-toggle="tab">流程备注</a></li>
							</ul>

							<div class="tab-content">
								<div class="tab-pane fade active in" id="home">
									<div class="jqGrid_wrapper row">
										<table id="caseCommenTable"></table>
										<div id="caseCommenPager"></div>
									</div>
								</div>
								<div class="tab-pane fade" id="profile">
									<div class="row">
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUSPEND">
											<a role="button" id="casePause"
												class="btn btn-primary btn-xm" href="javascript:casePause()">案件挂起/恢复
											</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PRARISE">
											<a role="button" data-toggle="modal"
												class="btn btn-primary btn-xm btn-activity"
												href="#pr-modal-form">产调发起 </a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUPSTART">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="${ctx }/spv/toSaveSpv?caseCode=${toCase.caseCode}">房款监管</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUPEND">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:startSpvOutApplyProcess('${toCase.caseCode}')">监管解除</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.DAISHOU">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="http://shs-ctm01/centalineoa/portal/frmIndex.aspx"
												target="_blank">代收代付</a>
										</shiro:hasPermission>
										<c:if test="${ !isBackTeam}">
											<c:if test="${not empty toWorkFlow.processDefinitionId}">
												<c:if test="${not empty toWorkFlow.instCode}">
													<shiro:hasPermission
														name="TRADE.CASE.CASEDETAIL.LEADCHANGE">
														<a role="button"
															class="btn btn-primary btn-xm btn-activity"
															href="javascript:showOrgCp()">责任人变更</a>
													</shiro:hasPermission>
												</c:if>
											</c:if>
										</c:if>
										<shiro:hasPermission name="TRADE.CASE.ORGC.CHANGE">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:caseChangeTeam()">案件转组</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PLANCHANGE">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:showPlanModal()">交易计划变更</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SERVCHANGE">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:showSrvModal()">服务项变更</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="TRADE.CASE.COWORKCHANGE">
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:showChangeModal()">变更合作对象</a>
										</shiro:hasPermission>
										<c:if test="${toCase.caseProperty != 30003002}">
											<!-- 已经结案审批通过限制流程重启 -->
											<shiro:hasPermission name="TRADE.CASE.RESTART">
												<a role="button" id="processRestart"
													class="btn btn-primary btn-xm btn-activity"
													href="javascript:serviceRestart()">流程重启</a>
											</shiro:hasPermission>
										</c:if>
										<shiro:hasPermission name="TRADE.CASE.RESET">
											<a role="button" id="caseResetes"
												class="btn btn-primary btn-xm btn-activity"
												href="javascript:caseReset()">案件重置</a>
										</shiro:hasPermission>
										<c:if test="${isCaseOwner && isNewFlow}">
											<!-- 主办 &10:445004或者之后的流程-->
											<a role="button" class="btn btn-primary btn-xm btn-activity"
												href="javascript:showLoanReqmentChgModal()">贷款需求选择</a>
										</c:if>
									</div>
									<!-- 案件转组 -->
									<div id="team-modal-form" class="modal fade" role="dialog"
										aria-labelledby="team-modal-title" aria-hidden="true">
										<div class="modal-dialog" style="width: 700px">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h4 class="modal-title" id="team-modal-title">案件转组</h4>
												</div>
												<div class="modal-body">
													<div class="row">
														<form id="team-form" class="form-horizontal"></form>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary"
														onclick="javascript:changeCaseTeam()">提交</button>
												</div>
											</div>
										</div>
									</div>
									<!-- 案件转组 end -->

									<!-- 责任人变更 -->
									<div id="leading-modal-form" class="modal fade" role="dialog"
										aria-labelledby="leading-modal-title" aria-hidden="true">
										<div class="modal-dialog" style="width: 1200px">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h4 class="modal-title" id="leading-modal-title">
														请选择交易顾问</h4>
												</div>
												<div class="modal-body">
													<div class="row" style="height: 450px; overflow: auto;">
														<div class="col-lg-12 ">
															<h3 class="m-t-none m-b"></h3>
															<div class="wrapper wrapper-content animated fadeInRight">
																<div id="leading-modal-data-show" class="row"></div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 产调发起 -->
									<div id="pr-modal-form" class="modal fade" role="dialog"
										aria-labelledby="pr-modal-title" aria-hidden="true">
										<div class="modal-dialog" style="width: 1200px">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h4 class="modal-title" id="pr-modal-title">请选择产调项目</h4>
												</div>
												<div class="modal-body">
													<div class="row">
														<div class="col-lg-12 checkbox i-checks checkbox-inline">
															<h3 class="m-t-none m-b"></h3>
															<aist:dict id="pr_item" name="pr_item" display="checkbox"
																defaultvalue="" dictType="30009" />
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														onclick="javascript:checkAllPRItem()">全选</button>
													<button type="button" class="btn btn-default"
														onclick="javascript:cleanPRItem()">清空</button>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary"
														onclick="javascript:startCasePrairses()">提交</button>
												</div>
											</div>
										</div>
									</div>
									<!-- 交易计划变更 -->
									<div id="plan-modal-form" class="modal fade" role="dialog"
										aria-labelledby="plan-modal-title" aria-hidden="true">
										<div class="modal-dialog" style="width: 1000px">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h4 class="modal-title" id="plan-modal-title">交易计划变更</h4>
												</div>
												<div class="modal-body">
													<div class="row">
														<form id="plan-form" class="form-horizontal"></form>
													</div>
												</div>
												<div class="modal-footer">

													<button type="button" class="btn btn-primary"
														onclick="javascript:openTransHistory()">变更记录</button>
													<button type="button" class="btn btn-primary"
														onclick="javascript:resetPlanModal()">重置</button>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary"
														onclick="return savePlanItems();">提交</button>
												</div>
											</div>
										</div>
									</div>
									<!-- 服务项变更 -->
									<div id="srv-modal-form" class="modal fade" role="dialog"
										aria-labelledby="srv-modal-title" aria-hidden="true">
										<div class="modal-dialog" style="width: 700px">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h4 class="modal-title" id="srv-modal-title">添加删除服务项</h4>
												</div>
												<div class="modal-body">
													<div class="row">
														<form class="form-horizontal">
															<div class="form-group">
																<div class="col-lg-3 control-label">原始服务项：</div>
																<div class="col-lg-9 control-label"
																	style="text-align: left" id="oldSrvs"></div>
															</div>
															<div class="hr-line-dashed"></div>
															<div class="form-group">
																<div class="col-lg-3 control-label">已选择服务项：</div>
																<div class="col-lg-9 control-label"
																	style="text-align: left" id="selectedSrvs"></div>
															</div>
															<div class="hr-line-dashed"></div>
															<div class="form-group">
																<div class="col-lg-3 control-label">服务项：</div>
																<div class="col-lg-9 checkbox i-checks checkbox-inline">
																	<aist:dict id="srvCode" name="srvCode"
																		display="checkbox" defaultvalue=""
																		dictType="yu_serv_cat_code_tree" level='2' />
																</div>
															</div>
														</form>
													</div>
												</div>
												<div class="modal-footer">

													<button type="button" class="btn btn-primary"
														onclick="javascript:resetSrvModal()">重置</button>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">关闭</button>
													<button type="button" class="btn btn-primary"
														onclick="javascript:saveSrvItems()">提交</button>
												</div>
											</div>
										</div>
									</div>

									<!-- start 变更合作对象  -->
									<div id="change-modal-form" class="modal fade" role="dialog"
										aria-labelledby="leading-modal-title" aria-hidden="true">
										<form id="changeCooprations" action="${ctx}/case/updateCoope"
											method="post" class="form-horizontal">
											<input type="hidden" name="instCode"
												value="${toWorkFlow.instCode}" /> <input type="hidden"
												name="caseId" value="${toCase.pkid}" />
											<div class="modal-dialog" style="width: 800px">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">×</button>
														<h4 class="modal-title" id="leading-modal-title">
															请选择服务项目</h4>
													</div>
													<div class="modal-body">
														<div class="row"
															style="max-height: 400px; overflow-y: auto; overflow-x: hidden">
															<div class="col-lg-12 ">
																<div
																	class="wrapper wrapper-content animated fadeInRight">
																	<div id="change-modal-data-show" class="row"></div>
																</div>
															</div>
														</div>
													</div>

													<div class="modal-footer">
														<input type="button" class="btn btn-primary" value="提交"
															onclick="submit_change()" />
														<button type="button" class="btn btn-primary"
															data-dismiss="modal">关闭</button>
													</div>
												</div>

											</div>
										</form>
									</div>
									<!-- end 变更合作对象   -->
									<!-- 修改表单数据-->
									<div id="changeForm-modal-form" class="modal fade"
										role="dialog" aria-labelledby="plan-modal-title"
										aria-hidden="true">
										<div class="modal-dialog" style="width: 1000px">
											<form id="changeForm-form" class="form-horizontal"
												method="post" target="_blank">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">×</button>
														<h4 class="modal-title" id="plan-modal-title">
															选择要修改的表单项目</h4>
													</div>
													<div class="modal-body">
														<div class="row">
															<div class="col-lg-3"
																style="margin-top: 9px; margin-left: 15px;">
																请选择您要修改的环节</div>
															<div class="col-lg-3">
																<input name="caseCode" value="${toCase.caseCode}"
																	id="hid_case_code" type="hidden"> <input
																	name="source" value="caseDetails" type="hidden">
																<input name="instCode" value="${toWorkFlow.instCode}"
																	type="hidden"> <select id="sel_changeFrom"
																	name="changeFrom" class="form-control m-b"
																	style="padding-bottom: 3px; height: 45.003px;">
																	<c:forEach items="${myTasks}" var="item">
																		<option value="${item.taskDefinitionKey }">${item.name }</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-12"
																style="margin-top: 9px; margin-left: 10px;">
																<font color="red">*</font>注1：交易顾问只能修改归属自己的、已提交的任务，未完成的任务请在待办任务中填写。
															</div>
														</div>
														<div class="row">
															<div class="col-lg-12"
																style="margin-top: 9px; margin-left: 10px;">
																<font color="red">*</font>注2：在环节表单中，凡是涉及到交易时间或变更流程走向的信息，系统不允许用户修改。
															</div>
														</div>
													</div>
													<div class="modal-footer">


														<button type="button" class="btn btn-default"
															data-dismiss="modal">关闭</button>
														<button type="submit" class="btn btn-primary">
															去修改</button>
													</div>
												</div>
											</form>
										</div>
									</div>

									<!-- loanRequirementChange -->
									<div id="loanReqmentChg-modal-form" class="modal fade"
										role="dialog" aria-labelledby="plan-modal-title"
										aria-hidden="true">
										<div class="modal-dialog" style="width: 1000px">
											<form method="get" class="form-horizontal"
												id="loan_reqment_chg_form">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-hidden="true">×</button>
														<h4 class="modal-title" id="plan-modal-title">贷款需求选择
														</h4>
													</div>

													<!-- 交易单编号 -->
													<input type="hidden" name="caseCode"
														value="${toCase.caseCode}">
													<!-- 流程引擎需要字段 -->
													<input type="hidden" name="processInstanceId"
														value="${toWorkFlow.instCode}">
													<div class="modal-body">

														<div style="padding-left: 20px; padding-right: 20px;">
															<div class="row">
																<div class="col-md-7">
																	<div class="form-group" id="data_1" name="isYouXiao">
																		<label class="col-md-5 control-label"
																			style='padding-left: 0px; text-align: left;'><font
																			color="red">*</font>请选择客户贷款需求</label>
																		<div class="col-md-7">
																			<aist:dict clazz="form-control" id="mortageService"
																				name="mortageService" display="select"
																				defaultvalue="0" dictType="mortage_service" />
																		</div>
																	</div>
																</div>
															</div>
															<div class="row" id='div_releasePlan'>
																<div class="col-md-7">
																	<div class="form-group">
																		<label class="col-md-5 control-label"
																			style='padding-left: 0px; text-align: left;'><font
																			color="red">*</font>预计放款时间</label>
																		<div class="col-md-7">
																			<div class=" input-group date">
																				<span class="input-group-addon"><i
																					class="fa fa-calendar"></i></span> <input type="text"
																					class="form-control" name="estPartTime"
																					id="estPartTime" disabled="disabled" value="">
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row">
																*请注意：当您选择纯公积金贷款时，您需要选择一位合作人；当您选择其它贷款时，默认的服务执行人为您自己。</div>
															<div class="divider">
																<hr>
															</div>
															<div id="hzxm"></div>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">关闭</button>
														<button type="button" id="btn_loan_reqment_chg"
															class="btn btn-primary">变更</button>
													</div>
												</div>
											</form>
										</div>
									</div>

								</div>
								<div class="tab-pane fade" id="messages">
									<c:if test="${not empty toWorkFlow.processDefinitionId}">
										<c:if test="${not empty toWorkFlow.instCode}">
											<iframe frameborder="no" border="0" marginwidth="0"
												marginheight="0" scrolling="auto" allowtransparency="yes"
												overflow:auto;
												style="height: 1068px; width: 100%;"
												src="<aist:appCtx appName='aist-activiti-web'/>/diagram-viewer/index.html?processDefinitionId=${toWorkFlow.processDefinitionId}&processInstanceId=${toWorkFlow.instCode}"></iframe>
										</c:if>
									</c:if>
								</div>
								<div class="tab-pane fade" id="settings">
									<table id="operation_history_table"></table>
									<div id="operation_history_pager"></div>
								</div>
							</div>
						</div>
					</div>


					<!-- 相关信息 -->
					<div class="panel " id="aboutInfo" style="min-height:800px;">
					<a style="float: right;margin-right: 12px;margin-top:10px;" href="javascript:showChangeFormModal();">我要修改</a>
						<div class="panel-body">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#home_info" data-toggle="tab">交易相关信息</a>
								</li>
								<li class=""><a href="#profile_info" data-toggle="tab">贷款相关信息</a>
								</li>
								<li class=""><a href="#messages_info" data-toggle="tab">房款监管信息</a>
								</li>
								<li class=""><a href="#settings_info" data-toggle="tab">金融服务信息</a>
								</li>
								<li class=""><a href="#fujian_info" data-toggle="tab">附件信息</a>
								</li>
								<li class=""><a href="#ctm_info" data-toggle="tab">ctm附件</a>
								</li>
								<li class=""><a href="#caseComment-info" data-toggle="tab">备注</a>
								</li>
							</ul>

							<div class="tab-content">
								<div class="tab-pane fade active in" id="home_info">
									<div class="row ">
										<label class="col-sm-3 control-label">派单时间：${caseDetailVO.createTime}</label>
										<label class="col-sm-3 control-label">分单时间：${caseDetailVO.resDate}</label>
										<label class="col-sm-3 control-label">签约时间：${caseDetailVO.realConTime}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">核价时间：${caseDetailVO.pricingTime}</label>
										<label class="col-sm-3 control-label">审税时间：${caseDetailVO.taxTime}</label>
										<label class="col-sm-3 control-label">查限购时间：${caseDetailVO.realPlsTime}</label>
										<label class="col-sm-3 control-label">过户时间：${caseDetailVO.realHtTime}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">领证时间：${caseDetailVO.realPropertyGetTime}</label>
										<label class="col-sm-3 control-label">结案时间：${caseDetailVO.closeTime}</label>
										<label class="col-sm-3 control-label">户口情况：${caseDetailVO.isHukou}</label>
										<label class="col-sm-3 control-label">合同公证：${caseDetailVO.isConCert}</label>
									</div>
									<div class="row ">

										<label class="col-sm-3 control-label">合同价： <c:if
												test="${!empty caseInfo.conPrice}">
			                                                ${caseInfo.conPrice/10000}  &nbsp&nbsp万元
			                                            </c:if>
										</label>
										<shiro:hasPermission name="TRADE.CASE.DEALPRICE:SHOW">
											<label class="col-sm-3 control-label">成交价： <c:if
													test="${!empty caseInfo.realPrice}">
				                                                ${caseInfo.realPrice/10000}&nbsp&nbsp万元
				                                            </c:if>
											</label>
										</shiro:hasPermission>
										<label class="col-sm-3 control-label">核定价格： <c:if
												test="${!empty caseInfo.taxPricing}">
			                                                ${caseInfo.taxPricing/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">房屋性质：${caseDetailVO.houseProperty}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">付款金额(首付)： <c:if
												test="${!empty caseInfo.amount1}">
			                                                ${caseInfo.amount1/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">付款金额(二期)： <c:if
												test="${!empty caseInfo.amount2}">
			                                                ${caseInfo.amount2/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">付款金额(尾款)： <c:if
												test="${!empty caseInfo.amount3}">
			                                                ${caseInfo.amount3/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">付款金额(装修)： <c:if
												test="${!empty caseInfo.amount4}">
			                                                ${caseInfo.amount4/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">付款方式(首付)：${caseDetailVO.payType1}</label>
										<label class="col-sm-3 control-label">付款方式(二期)：${caseDetailVO.payType2}</label>
										<label class="col-sm-3 control-label">付款方式(尾款)：${caseDetailVO.payType3}</label>
										<label class="col-sm-3 control-label">付款方式(装修)：${caseDetailVO.payType4}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">付款时间(首付)：${caseDetailVO.payTime1}</label>
										<label class="col-sm-3 control-label">付款时间(二期)：${caseDetailVO.payTime2}</label>
										<label class="col-sm-3 control-label">付款时间(尾款)：${caseDetailVO.payTime3}</label>
										<label class="col-sm-3 control-label">付款时间(装修)：${caseDetailVO.payTime4}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">购房年数：${caseDetailVO.holdYear}</label>
										<label class="col-sm-3 control-label">唯一住房：
											${caseDetailVO.isUniqueHome}</label> <label
											class="col-sm-3 control-label">房产税： <c:if
												test="${!empty caseInfo.houseHodingTax}">
			                                                ${caseInfo.houseHodingTax/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">契税： <c:if
												test="${!empty caseInfo.contractTax}">
			                                                ${caseInfo.contractTax/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">个人所得税： <c:if
												test="${!empty caseInfo.personalIncomeTax}">
			                                                ${caseInfo.personalIncomeTax/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">营业税： <c:if
												test="${!empty caseInfo.businessTax}">
			                                                ${caseInfo.businessTax/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">土地增值税： <c:if
												test="${!empty caseInfo.landIncrementTax}">
			                                                ${caseInfo.landIncrementTax/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">上家剩余贷款： <c:if
												test="${!empty caseInfo.uncloseMoney}">
			                                                ${caseInfo.uncloseMoney/10000}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">还款方式：
											${caseDetailVO.closeType}</label> <label
											class="col-sm-3 control-label">还款时间：${caseDetailVO.loanCloseCode}</label>
									</div>
									<div class="row ">
										<label class="col-sm-6 control-label">上家贷款银行：${caseInfo.upBank}</label>
									</div>
								</div>
								<div class="tab-pane fade" id="profile_info">
									<div class="row ">
										<label class="col-sm-3 control-label">签约时间：${caseDetailVO.signDate}</label>
										<label class="col-sm-3 control-label">批贷时间：${caseDetailVO.apprDate}</label>
										<label class="col-sm-3 control-label">他证送达时间：${caseDetailVO.tazhengArrDate}</label>

										<label class="col-sm-3 control-label">放款时间：${caseDetailVO.lendDate}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">贷款类型：${caseDetailVO.mortTypeName}</label>
										<label class="col-sm-3 control-label">商贷金额： <c:if
												test="${!empty toMortgage.comAmount}">
			                                                ${toMortgage.comAmount}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">商贷年限：${toMortgage.comYear}</label>
										<label class="col-sm-3 control-label">商贷利率：${toMortgage.comDiscount}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">是否自办：<c:choose>
												<c:when test="${toMortgage.isDelegateYucui=='1'}">否</c:when>
												<c:when test="${toMortgage.isDelegateYucui=='0'}">是</c:when>
												<c:otherwise>
                                                     ${toMortgage.isDelegateYucui}
                                                     </c:otherwise>
											</c:choose></label> <label class="col-sm-3 control-label">公积金贷款金额： <c:if
												test="${!empty toMortgage.prfAmount}">
			                                                ${toMortgage.prfAmount}&nbsp&nbsp万元
			                                            </c:if>
										</label> <label class="col-sm-3 control-label">公积金贷款年限：${toMortgage.prfYear}</label>
										<label class="col-sm-3 control-label">放款方式：${caseDetailVO.lendWay}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">主贷人：${caseDetailVO.mortBuyer}</label>
										<label class="col-sm-3 control-label">主贷人单位：${caseDetailVO.buyerWork}</label>
										<label class="col-sm-3 control-label">房贷套数：${toMortgage.houseNum}</label>
										<label class="col-sm-3 control-label">申请时间：${caseDetailVO.prfApplyDate}</label>
									</div>

									<c:choose>
										<c:when
											test="${toMortgage.isDelegateYucui=='1' && (toMortgage.mortType=='30016001' or toMortgage.mortType=='30016002')}">
											<div class="row ">
												<label class="col-sm-3 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
												<label class="col-sm-3 control-label">支
													行：${caseDetailVO.bankName}</label> <label
													class="col-sm-3 control-label">是否为临时银行：<c:choose>
														<c:when test="${toMortgage.isTmpBank==1}">是</c:when>
														<c:otherwise>否</c:otherwise>
													</c:choose></label> <label class="col-sm-3 control-label">推荐函编号：${toMortgage.recLetterNo}</label>
											</div>
										</c:when>
										<c:otherwise>
											<div class="row ">
												<label class="col-sm-6 control-label">贷款银行：${caseDetailVO.parentBankName}</label>
												<label class="col-sm-6 control-label">支
													行：${caseDetailVO.bankName}</label>
											</div>
										</c:otherwise>
									</c:choose>

									<div class="row ">
										<label class="col-sm-3 control-label">信贷员：${toMortgage.loanerName}</label>
										<label class="col-sm-3 control-label">信贷员电话：${toMortgage.loanerPhone}</label>
										<label class="col-sm-3 control-label">评估公司：${caseDetailVO.evaName}</label>
										<label class="col-sm-3 control-label">评估费金额： <c:if
												test="${!empty caseDetailVO.evaFee}">
			                                                ${caseDetailVO.evaFee}&nbsp&nbsp元
			                                            </c:if>
										</label>
									</div>
									<div class="row ">
										<label class="col-sm-12 control-label">备注：${toMortgage.remark}</label>
									</div>
								</div>
								<div class="tab-pane fade" id="messages_info">
									<table id="gridTable1"></table>
									<div id="gridPager1"></div>
								</div>
								<div class="tab-pane fade" id="settings_info">
									<c:forEach var="toLoanAgent" items="${toLoanAgents}"
										varStatus="status">
										<c:forEach var="toLoanAgentVO" items="${toLoanAgentVOs}"
											begin="${status.index}" end="${status.index}">
											<div class="row ">
												<label class="col-sm-3 control-label">客户姓名：${toLoanAgent.custName}</label>
												<label class="col-sm-3 control-label">贷款服务：${toLoanAgentVO.loanSrvName}</label>
												<label class="col-sm-6 control-label">贷款机构：${toLoanAgentVO.finOrgName}</label>
											</div>
											<div class="row ">
												<label class="col-sm-3 control-label">客户电话：${toLoanAgent.custPhone}</label>
												<label class="col-sm-3 control-label">贷款金额： <c:if
														test="${!empty toLoanAgent.loanAmount}">
			                                                ${toLoanAgent.loanAmount/10000}&nbsp&nbsp万元
			                                            </c:if></label> <label
													class="col-sm-3 control-label">放款金额： <c:if
														test="${!empty toLoanAgent.actualAmount}">
			                                                ${toLoanAgent.actualAmount/10000}&nbsp&nbsp万元
			                                            </c:if></label>
											</div>
											<div class="row ">
												<label class="col-sm-3 control-label">申请状态：${toLoanAgentVO.applyStatusName}</label>
												<label class="col-sm-3 control-label">申请期数： <c:if
														test="${!empty toLoanAgent.month}">
			                                                ${toLoanAgent.month}&nbsp&nbsp月
			                                            </c:if></label> <label
													class="col-sm-3 control-label">转介人：${toLoanAgent.zjName}</label>
												<label class="col-sm-3 control-label">转介人编号：${toLoanAgent.zjCode}</label>
											</div>
											<div class="row ">
												<label class="col-sm-3 control-label">确认状态：${toLoanAgentVO.confirmStatusName}</label>
												<label class="col-sm-3 control-label">合作人：${toLoanAgent.coName}</label>
												<label class="col-sm-3 control-label">合作人编号：${toLoanAgent.coCode}</label>
												<label class="col-sm-3 control-label">分配比例： <c:if
														test="${!empty toLoanAgent.awardPer}">
			                                                ${toLoanAgent.awardPer}&nbsp&nbsp%
			                                            </c:if></label>
											</div>
											<div class="row ">
												<label class="col-sm-3 control-label">申请时间：${toLoanAgentVO.applyTime}</label>
												<label class="col-sm-3 control-label">确认时间：${toLoanAgentVO.confirmTime}</label>
												<label class="col-sm-3 control-label">面签时间：${toLoanAgentVO.signTime}</label>
												<label class="col-sm-3 control-label">放款时间：${toLoanAgentVO.releaseTime}</label>
											</div>
											<div class="row ">
												<label class="col-sm-3 control-label">对账时间：${toLoanAgentVO.incomeConfirmTime}</label>
												<label class="col-sm-3 control-label">结账时间：${toLoanAgentVO.incomeArriveTime}</label>
												<label class="col-sm-3 control-label">超期导出时间：${toLoanAgentVO.lastExceedExportTime}</label>
											</div>
											<c:if test="${!status.last}">
												<div class="hr-line-dashed"></div>
											</c:if>
										</c:forEach>
									</c:forEach>
								</div>
								<div class="tab-pane fade" id="fujian_info">
									<div class="panel-body">
										<div id="imgShow" class=""></div>
									</div>
								</div>
								<div class="tab-pane fade" id="ctm_info">
									<table id="gridTable"></table>
									<div id="gridPager"></div>
								</div>
								<div class="tab-pane fade" id="caseComment-info">
									<div id="caseCommentList" class="add_form"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <%-- <script src="${ctx}/transjs/task/follow.pic.list.js"></script> --%>
	<script src="${ctx}/js/trunk/case/caseDetail.js?v=1.0.6"></script> <script
		src="${ctx}/js/trunk/case/showCaseAttachment.js"></script> <!-- 校验 -->
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/js/plugins/validate/common/additional-methods.js"></script>
	<script src="${ctx}/js/plugins/validate/common/messages_zh.js"></script>
	  <script src="${ctx}/js/stickUp.js"></script>
	 <script src="${ctx}/toastr.min.js"></script>

	<!-- 放款监管信息  --> <script src="${ctx}/transjs/task/caseflowlist.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/trunk/comment/caseComment.js"></script> <!-- 各个环节的备注信息  -->
	<script src="${ctx}/js/trunk/case/caseRemark.js"></script> <jsp:include
		page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> <script>
		var caseCode = $("#caseCode").val();
		var ctmCode = $("#ctm").val();
		var url = "/quickGrid/findPage";
		var ctx = $("#ctx").val();
	    var r1 =false;
	    var changeTaskRole=false;
	    var serivceDepId='${serivceDefId}';
	    var loanReqType="${loanReqType}";
	    <shiro:hasPermission name="TRADE.CASE.DEALPRICE:SHOW">  
			r1 = true;
		</shiro:hasPermission>
		<shiro:hasPermission name="TRADE.CASE.TASK:ASSIGN">
			changeTaskRole=true;
		</shiro:hasPermission>
		var isNewFlow =${isNewFlow}; 
		var isCaseManager=${isCaseManager};
		//jqGrid 初始化
		$("#gridTable").jqGrid({
			url : ctx+url,
			mtype : 'GET',
			datatype : "json",
			height : 250,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 20,
			/*   rowList: [10, 20, 30], */
			colNames : [ '附件类型','附件名称','附件路径','上传时间','操作'],
			colModel : [ {
				name : 'ATT_TYPE',
				index : 'ATT_TYPE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'ATT_NAME',
				index : 'ATT_NAME',
				align : "center",
				width : 20,
				resizable : false
			}, {
				name : 'ATT_PATH',
				index : 'ATT_PATH',
				align : "center",
				width : 20,
				resizable : false
				//formatter : linkhouseInfo
			}, {
				name : 'UPLOAD_DATE',
				index : 'UPLOAD_DATE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'READ',
				index : 'READ',
				align : "center",
				width : 20,
				resizable : false
			}],
			multiselect: true,
			pager : "#gridPager",
			//sortname:'UPLOAD_DATE',
	        //sortorder:'desc',
			viewrecords : true,
			pagebuttions : true,
			multiselect:false,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
			pgtext : " {0} 共 {1} 页",
			gridComplete:function(){
				var ids = jQuery("#gridTable").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
    				var id = ids[i];
    				var rowDatas = jQuery("#gridTable").jqGrid('getRowData', ids[i]); // 获取当前行
    				
    				var link = "<button  class='btn red' onclick='addAttachmentReadLog(\""+ctx+"\",\""+ctmCode+"\",\""+caseCode+"\",\""+rowDatas['ATT_NAME']+"\",\""+rowDatas['ATT_PATH']+"\")'>查看附件</a>";
    				
    				//var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";
    				
    				jQuery("#gridTable").jqGrid('setRowData', ids[i], { READ: link});
    				
    				var attType = rowDatas["ATT_TYPE"];
    				if(!r1 && attType =='买卖居间协议') {
   					   $("#gridTable").jqGrid("delRowData", id);      
    				} 
				}
			},
			postData : {
				queryId : "followPicListQuery",
				argu_ctmCode : ctmCode
			}
			 
		});
		
		//附件连接
		function linkhouseInfo(cellvalue, options, rowObject){
			 var link = '<a href="" target="_black" onclick="addAttachmentReadLog('+cellvalue+')">'+cellvalue+'</a>';
			 return link;
		}

		function addAttachmentReadLog(ctx,ctmCode,caseCode,attachName,attachPath) {
			var tsAttachmentReadLog = {
				 	'caseCode':caseCode,
				 	'ctmCode':ctmCode,
				 	'attachmentName':attachName,
				 	'attachmentAddress':attachPath
			};
			//tsAttachmentReadLog=$.toJSON(tsAttachmentReadLog);
			
			$.ajax({
				type : 'post',
				cache : false,
				async : true,
				url : ctx+'/log/addAttachmentReadLog',
				data : tsAttachmentReadLog,
				dataType : "json",
				success : function(data) {
					//alert("记录日志成功");
				},
				error : function(errors) {
					//alert("记录日志失败");
					return false;
				}
			});
			
			window.open(attachPath);
			/*var url=ctx+"/api/imageshow/imgShow?img="+attachPath;
			window.open(encodeURI(encodeURI(url)));*/
		}

		//加载页面获取屏幕高度
 		$(function(){
 			
			var caseCode = $('#caseCode').val();
		
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
/* 	        var h= window.screen.availHeight;
			$("#scroll").css("height",h-h*0.32); */ 
			//点击浏览器任何位置隐藏提示信息
	        $("body").bind("click",function(evt){
              if($(evt.target).attr("data-toggle")!='popover') 
                {
               	$('a[data-toggle="popover"]').popover('hide');
               	}
            });
			//隐藏头部信息
			
 	        window.onscroll = function(){ 
	        	if(document.body.scrollTop>62){
	        		$("#isFixed").css("position","fixed");
	        		$("#isFixed").addClass("istauk");
	        	/* 	$(".wrapper").css("margin-top","px"); */
	        	}else{
	        		$("#isFixed").css("position","relative");
	        		$("#isFixed").removeClass("istauk");
	        		
	        	}
	        	
	        	
	        } 
		})
        jQuery(function($) {
            $(document).ready( function() {
               $('.stickup-nav-bar').stickUp({
                // $('.col-lg-9').stickUp({
                                    parts: {
                                      0:'basicInfo',
                                      1:'serviceFlow',
                                      2:'aboutInfo'
                                    },
                                    itemClass: 'menuItem',
                                    itemHover: 'active',
                                    marginTop: 'auto'
                                  });

           

            });
        });
	       
	</script> </content>
</body>
</html>

