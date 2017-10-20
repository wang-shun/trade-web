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
							<!-- <li class=""><a href="#home" data-toggle="tab">流程备注</a></li> -->
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
										<a role="button" id="processRestart" class="btn btn-primary btn-xm" href="javascript:evalProcessRestart()">评估重启 </a>
										<a role="button" id="evalComChange" class="btn btn-primary btn-xm" href="javascript:evalComChange()">评估公司变更</a>
										<a role="button" id="evalBaodan" class="btn btn-primary btn-xm btn-activity" href="javascript:evalBaodan()">评估爆单 </a>
										<a role="button" id="evalReject" class="btn btn-primary btn-xm btn-activity" href="javascript:evalReject('${caseCode}')">驳回申请</a>
										<a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:transferCommission()" target="_blank">评估公司变更调佣</a>
									
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
															<input name="caseCode" value="${caseCode}" id="hid_case_code" type="hidden">
															<input name="caseCode" value="${evaCode}" id="hid_case_code" type="hidden">
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
															<font color="red">*</font>注1：内勤助理问只能修改归属自己的、已提交的评估任务，未完成的任务请在待办任务中填写。
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
					<!-- <a style="float: right; margin-right: 12px; margin-top: 10px;"
						href="javascript:showChangeFormModal();">修改</a> -->
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#eval_info" data-toggle="tab">评估信息</a></li>
							<li class=""><a href="#invoice_info" data-toggle="tab">评估费发票</a></li>
							<li class=""><a href="#rebate_info" data-toggle="tab">评估返利报告审批</a></li>
							<li class=""><a href="#baodao_info" data-toggle="tab">评估爆单</a></li>
							<li class=""><a href="#change_info" data-toggle="tab">评估公司变更</a></li>
							<li class=""><a href="#settle_info" data-toggle="tab" style="padding:10px;">评估结算</a></li>
							<li class=""><a href="#message_info" data-toggle="tab" style="padding:10px;">调佣审批</a></li>
							<li class=""><a href="#refund_info" data-toggle="tab" style="padding:10px;">评估退费</a></li>
						    <li class=""><a href="#attanchmet_info" data-toggle="tab" style="padding:10px;">附件</a></li>
						     <li class=""><a href="#comment_info" data-toggle="tab" style="padding:10px;">备注</a></li>
						</ul>
						
						<div class="tab-content">
						      <div class="tab-pane fade active in" id="eval_info">
							        <h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估询价</h4>
									<div class="row">
										<label class="col-sm-3 control-label">询价类型：${toEvaPricingVo.evaType}</label>
										<label class="col-sm-3 control-label">询价值：${toEvaPricingVo.totalPrice}</label>
										<label class="col-sm-3 control-label">询价时间：${toEvaPricingVo.evalTime}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估公司：${ctoEvaPricingVo.evaCompany}</label>
										<label class="col-sm-3 control-label">房龄：${toEvaPricingVo.houseAge}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估申请</h4>
									<div class="row">
										<label class="col-sm-3 control-label">评估公司：${toEvalReportProcess.finOrgId}</label>
										<label class="col-sm-3 control-label">评估公司联系人：${toEvalReportProcess.evaComContact}</label>
										<label class="col-sm-3 control-label">联系方式：${toEvalReportProcess.contactWay}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">房龄：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">询价值：${ToEvalReportProcess.inquiryResult}</label>
										<label class="col-sm-3 control-label">评估报告份数：${ToEvalReportProcess.reportNum}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">申请评估日期：${ToEvalReportProcess.applyDate}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>出具评估报告</h4>
									<div class="row">
										<label class="col-sm-3 control-label">实际出具评估报告日期：${ToEvalReportProcess.issueDate}</label>
										<label class="col-sm-3 control-label">收取报告日期：${ToEvalReportProcess.reportGetDate}</label>
										<label class="col-sm-3 control-label">评估价：${ToEvalReportProcess.evaPrice}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">房龄：${ToEvalReportProcess.houseAgeIssue}</label>
										<label class="col-sm-3 control-label">评估报告份数：${ToEvalReportProcess.reportNumIssue}</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>使用评估报告</h4>
									<div class="row">
										<label class="col-sm-3 control-label">领取报告时间：${ToEvalReportProcess.reportRevDate}</label>
										<label class="col-sm-3 control-label">领取人：${ToEvalReportProcess.receiver}</label>
										<label class="col-sm-3 control-label">领取份数：${ToEvalReportProcess.receiveNum}</label>
									</div>
						       </div>
						     
						     <!-- 评估发票信息 -->
						    <div class="tab-pane fade" id="invoice_info">
									<div class="row">
										<label class="col-sm-3 control-label">申请日期：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">发票金额：${ToEvalReportProcess.inquiryResult}</label>
										<label class="col-sm-3 control-label">发票种类：${ToEvalReportProcess.reportNum}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">开票抬头：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">开票地址：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">税号：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">预计开具发票日期：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">开具发票时间：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="content">
										<table id="gridTable_invoice"></table>
								        <div id="gridPager_invoice"></div>
									 </div>
							</div>
							
							<!-- 评估返利报告审批 -->
						    <div class="tab-pane fade" id="rebate_info">
									<div class="row">
									    <label class="col-sm-3 control-label">评估费收据：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">评估费实收：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">评估费应收：${ToEvalReportProcess.inquiryResult}</label>
									</div>
									<div class="row">
									    <label class="col-sm-3 control-label">中原分成金额：${ToEvalReportProcess.reportNum}</label>
										<label class="col-sm-3 control-label">评估公司分成金额：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">录入时间：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="content">
										<table id="gridTable_rebate"></table>
								        <div id="gridPager_rebate"></div>
									 </div>
							</div>
							
							<!-- 评估爆单 -->
						    <div class="tab-pane fade" id="baodao_info">
									<div class="row">
										<label class="col-sm-3 control-label">爆单原因：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									 <div class="content">
										<table id="gridTable_baodao"></table>
								        <div id="gridPager_baodao"></div>
								      </div>
							</div>
							
							<!-- 评估公司变更 -->
						    <div class="tab-pane fade" id="change_info">
									<div class="row">
									    <label class="col-sm-3 control-label">变更原因：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
							</div>
							
							<!-- 评估结算 -->
						    <div class="tab-pane fade" id="settle_info">
									<div class="row">
									    <label class="col-sm-3 control-label">结算费用：${ToEvalSettle.settleFee}</label>
									</div>
									<div class="row">
										<table id="gridTable_settle"></table>
								        <div id="gridPager_settle"></div>
									</div>
							</div>
							
							<!-- 调佣审批 -->
						    <div class="tab-pane fade" id="message_info">
									<div class="row">
									     <label class="col-sm-3 control-label">调佣事项：${ToEvalReportProcess.houseAgeApply}</label>
									     <label class="col-sm-3 control-label">调佣类型：${ToEvalReportProcess.houseAgeApply}</label>
									     <label class="col-sm-3 control-label">调佣对象：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="row">
									     <label class="col-sm-3 control-label">调佣金额：${ToEvalReportProcess.houseAgeApply}</label>
									     <label class="col-sm-3 control-label">调佣事由：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="content">
										<table id="gridTable_message"></table>
								        <div id="gridPager_message"></div>
									 </div>
							</div>
							
							<!-- 评估退费 -->
							<div class="tab-pane fade" id="refund_info">
							       <div class="row">
										<label class="col-sm-3 control-label">退费类别：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">申请人：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">申请分行：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									 <div class="row">
										<label class="col-sm-3 control-label">申请时间：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">退费金额：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">退款对象：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									 <div class="row">
										<label class="col-sm-3 control-label">退款原因：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">预计退款时间：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">报告领取时间：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">报告领取人：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">评估报告份数：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">报告领取时间：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估报告回收份数：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">回收时间：${ToEvalReportProcess.houseAgeApply}</label>
										<label class="col-sm-3 control-label">未回收原因：${ToEvalReportProcess.houseAgeApply}</label>
									</div>
									<div class="row">
										<table id="gridTable_refund"></table>
								        <div id="gridPager_refund"></div>
									 </div>
									
							</div>
							
							<div class="tab-pane fade" id="attanchmet_info">
								<div class="panel-body">
									<div id="imgShow" class=""></div>
								</div>
							</div>
							
							<div class="tab-pane fade" id="comment_info">
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

