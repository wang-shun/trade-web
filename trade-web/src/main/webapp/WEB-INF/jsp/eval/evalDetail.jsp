<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  taglib  uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- stickUp fixed css -->
<link href="<c:url value='/static/trans/css/common/hint.css' />" rel="stylesheet" />
<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">

<link href="<c:url value='/css/plugins/jasny/jasny-bootstrap.min.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<style>
.table thead tr th {
    background-color: #4bccec;
    font-size: 14px;
    font-weight: normal;
    color: #fff;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}" />
	<input type="hidden" id="caseCode" value="${caseCode}" />
	<input type="hidden" id="evaCode" value="${evaCode}" />
	<input type="hidden" id="instCode" value="${toWorkFlow.instCode}" />
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}" />
	<input type="hidden" id="processDefinitionId"
		value="${toWorkFlow.processDefinitionId}" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<script>
		var resourceDistributionBtn = false;
		<%if (request.getAttribute("msg") == null || request.getAttribute("msg") == "") {%>
		<%} else {%>
			window.wxc.alert("<%=request.getAttribute("msg")%>");
		<%}%>
		<shiro:hasPermission name="TRADE.CASE.DISTRIBUTION">
		 resourceDistributionBtn = true;
		</shiro:hasPermission>
	</script>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

				<!-- 服务流程 -->
				<div class="panel " id="serviceFlow">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#settings" data-toggle="tab">评估操作纪录</a>
							</li>
							<li class=""><a href="#profile" data-toggle="tab">评估基本操作</a>
							</li>
							<li class=""><a href="#messages" data-toggle="tab">评估进程总览</a>
							</li>
							<li class=""><a href="#home" data-toggle="tab">流程备注</a></li>
						</ul>

						<div class="tab-content">
							<div class="tab-pane active fade in" id="settings">
								<div class="jqGrid_wrapper row">
									<table id="operation_history_table"></table>
									<div id="operation_history_pager"></div>
								</div>
							</div>
							<div class="tab-pane fade" id="profile">
								<div class="row">
									<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUSPEND"> -->
										<a role="button" id="processRestart" class="btn btn-primary btn-xm" href="javascript:evalProcessRestart()">评估重启 </a>
									<!-- </shiro:hasPermission> -->
									<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.EDITWDCASE"> -->
										<a role="button" id="evalComChange" class="btn btn-primary btn-xm" href="javascript:evalComChange()">评估公司变更</a>
									<!-- </shiro:hasPermission> -->
									<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.PRARISE"> -->
										<a role="button" id="evalBaodan" class="btn btn-primary btn-xm btn-activity" href="javascript:evalBaodan()">评估爆单 </a>
									<!-- </shiro:hasPermission> -->
									<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.SUPEND"> -->
										<a role="button" id="evalReject" class="btn btn-primary btn-xm btn-activity" href="javascript:evalReject('${caseCode}')">驳回申请</a>
									<!-- </shiro:hasPermission> -->
									<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.DAISHOU"> -->
										<a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:transferCommission()" target="_blank">评估公司变更调佣</a>
									<!-- </shiro:hasPermission> -->
									
								</div>

								<!-- 修改表单数据-->
								<div id="changeForm-modal-form" class="modal fade" role="dialog"
									aria-labelledby="plan-modal-title" aria-hidden="true">
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
															<input name="caseCode" value="${toCase.caseCode}" id="hid_case_code" type="hidden">
															<input name="source" value="caseDetails" type="hidden">
															<input name="instCode" value="${toWorkFlow.instCode}" type="hidden">
															<select id="sel_changeFrom"	name="changeFrom" class="form-control m-b"	style="padding-bottom: 3px; height: 45.003px;">
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
													<button type="submit" class="btn btn-primary">去修改</button>
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
							<div class="tab-pane fade" id="home">
								<table id="evalCommenTable"></table>
								<div id="evalCommenPager"></div>
							</div>
						</div>
					</div>
				</div>


				<!-- 相关信息 -->
				<div class="panel " id="aboutInfo" style="min-height: 800px;">
					<a style="float: right; margin-right: 12px; margin-top: 10px;"
						href="javascript:showChangeFormModal();">修改</a>
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#eval_info" data-toggle="tab">评估信息</a></li>
							<li class=""><a href="#settings_info" data-toggle="tab">评估费发票</a></li>
							<li class=""><a href="#rebate_info" data-toggle="tab">评估返利报告审批</a></li>
							<li class=""><a href="#baodao_info" data-toggle="tab">评估爆单</a></li>
							<li class=""><a href="#caseComment-info" data-toggle="tab">评估公司变更</a></li>
							<li class=""><a href="#bizwarn-info" data-toggle="tab" style="padding:10px;">评估结算</a></li>
							<li class=""><a href="#messages_info1" data-toggle="tab" style="padding:10px;">调佣审批</a></li>
						    <li class=""><a href="#liushui-info" data-toggle="tab" style="padding:10px;">附件</a></li>
						     <li class=""><a href="#liushui-info" data-toggle="tab" style="padding:10px;">备注</a></li>
						</ul>
						
						<div class="tab-content">
						      <div class="tab-pane fade active in" id="eval_info">
							        <h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估询价</h4>
									<div class="row ">
										<label class="col-sm-3 control-label">询价类型：${toEvaPricingVo.evaType}</label>
										<label class="col-sm-3 control-label">询价值：${toEvaPricingVo.totalPrice}</label>
										<label class="col-sm-3 control-label">询价时间：${toEvaPricingVo.evalTime}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">评估公司：${ctoEvaPricingVo.evaCompany}</label>
										<label class="col-sm-3 control-label">房龄：${toEvaPricingVo.houseAge}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估申请</h4>
									<div class="row ">
										<label class="col-sm-3 control-label">评估公司：${toEvaPricingVo.evaType}</label>
										<label class="col-sm-3 control-label">评估公司联系人：${ToEvalReportProcess.evaComContact}</label>
										<label class="col-sm-3 control-label">联系方式：${ToEvalReportProcess.contactWay}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">房龄：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">询价值：${ToEvalReportProcess.inquiryResult}</label>
										<label class="col-sm-3 control-label">评估报告份数：${ToEvalReportProcess.reportNum}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">申请评估日期：${ToEvalReportProcess.applyDate}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>出具评估报告</h4>
									<div class="row ">
										<label class="col-sm-3 control-label">实际出具评估报告日期：${ToEvalReportProcess.issueDate}</label>
										<label class="col-sm-3 control-label">收取报告日期：${ToEvalReportProcess.reportGetDate}</label>
										<label class="col-sm-3 control-label">评估价：${ToEvalReportProcess.evaPrice}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">房龄：${ToEvalReportProcess.houseAgeIssue}</label>
										<label class="col-sm-3 control-label">评估报告份数：${ToEvalReportProcess.reportNumIssue}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>使用评估报告</h4>
									<div class="row ">
										<label class="col-sm-3 control-label">领取报告时间：${toEvaPricingVo.reportRevDate}</label>
										<label class="col-sm-3 control-label">领取人：${ToEvalReportProcess.receiver}</label>
										<label class="col-sm-3 control-label">领取份数：${ToEvalReportProcess.receiveNum}</label>
									</div>
						       </div>
						       
						    <div class="tab-pane fade" id="rebate_info">
									<div class="row ">
										<label class="col-sm-3 control-label">评估费实收：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">评估费应收：${ToEvalReportProcess.inquiryResult}</label>
										<label class="col-sm-3 control-label">中原分成金额：${ToEvalReportProcess.reportNum}</label>
									</div>
									<div class="row ">
										<label class="col-sm-3 control-label">录入时间：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
							</div>
							
							<div class="tab-pane fade" id="baodao_info">
								<table id="gridTable1"></table>
								<div id="gridPager1"></div>
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

	<content tag="local_script"> <!-- Peity -->
	<script	src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jasny/jasny-bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<%-- <script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script> --%>
	<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
	<script src="<c:url value='/js/trunk/eval/evalDetail.js' />"></script>
	<%-- <script src="<c:url value='js/trunk/case/showCaseAttachment.js' />"></script> --%>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<!-- 放款监管信息  -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script	src="<c:url value='/transjs/task/caseflowlist.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<!-- 各个环节的备注信息  -->
	<script src="<c:url value='/js/trunk/case/caseRemark.js' />"></script>
	<!-- 公共信息js -->	
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	
</content>

</body>
</html>

