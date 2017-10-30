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
										<a role="button" id="evalComChange" class="btn btn-primary btn-xm" href="javascript:evalComChange('${evaCode}')">评估公司变更</a>
										<a role="button" id="evalBaodan" class="btn btn-primary btn-xm btn-activity" href="javascript:evalBaodan()">评估爆单 </a>
										<a role="button" id="evalReject" class="btn btn-primary btn-xm btn-activity" href="javascript:evalReject('${caseCode}')">驳回申请</a>
										<a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:transferCommission()" target="_blank">评估公司变更调佣</a>
									
								</div>
								
								<!-- 评估公司变更-->
								<div id="change-eval-company-modal-form" class="modal fade" role="dialog"
									aria-labelledby="plan-modal-title" aria-hidden="true">
									<div class="modal-dialog" style="width: 1000px">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">×</button>
												<h4 class="modal-title" id="plan-modal-title">评估公司变更</h4>
											</div>
											<div class="modal-body">
											<div style="margin-left: 150px">
												<div class="row">
													<form id="change-eval-company-form" class="form-horizontal">
														<table>
															<tr>
															<td>评估公司变更</td>
															<td>是<input type="radio" name="changeItem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;否
															<input type="radio" name="changeItem"></td>
															</tr>
															<tr>
															<td>评估公司变更原因</td>
															<td><input type="text" id="changeReason" name="changeReason"></td>
															</tr>
														</table>
													
													</form>
												</div>
											</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary"
													onclick="return saveEvaComChangeItems();">提交</button>
											</div>
										</div>
									</div>
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
															<input name="evaCode" value="${evaCode}" id="hid_case_code" type="hidden">
															<input name="source" value="evalDetails" type="hidden">
															<input name="instCode" value="${toWorkFlow.instCode}" type="hidden">
															<select id="sel_changeFrom"	name="changeFrom" class="form-control m-b"	style="padding-bottom: 3px; height: 45.003px;">
																<c:forEach items="${myTasks}" var="item">
																	<option value="${item.formKey }">${item.name }</option>
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
												style="height: 400px; width: 100%;"
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
										<label class="col-sm-3 control-label">询价类型：<aist:dict id="evalPriceType" name="evalPriceType" display="label" dictType="EVAPRICING_TYPE" dictCode="${toEvaPricingVo.evaType}" /></label>
										<label class="col-sm-3 control-label">询价值：${toEvaPricingVo.totalPrice / 10000.00}&nbsp;万元</label>
										<label class="col-sm-3 control-label">询价时间：<fmt:formatDate value="${toEvaPricingVo.evalTime}" type="date" pattern="yyyy-MM-dd"/></label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估公司：${toEvaPricingVo.evaCompany}</label>
										<label class="col-sm-3 control-label">房龄：${toEvaPricingVo.houseAge}&nbsp;年内</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估申请</h4>
									<div class="row">
									    <label class="col-sm-3 control-label">评估类型：<aist:dict id="evalType" name="evalType" display="label" dictType="EVAL_TYPE" dictCode="${toEvalReportProcessVo.reportType}" /></label>
										<label class="col-sm-3 control-label">评估公司：${toEvalReportProcessVo.finOrgName}</label>
										<label class="col-sm-3 control-label">评估公司联系人：${toEvalReportProcessVo.evaComContact}</label>
									</div>
									<div class="row">
									    <label class="col-sm-3 control-label">联系方式：${toEvalReportProcessVo.contactWay}</label>
										<label class="col-sm-3 control-label">房龄：${toEvalReportProcessVo.houseAgeApply}&nbsp;年内</label>
										<label class="col-sm-3 control-label">询价值：${toEvalReportProcessVo.inquiryResult / 10000.00}&nbsp;万元</label>
									</div>
									<div class="row">
									    <label class="col-sm-3 control-label">评估报告份数：${toEvalReportProcessVo.reportNum}&nbsp;份</label>
										<label class="col-sm-3 control-label">申请评估日期：<fmt:formatDate value="${toEvalReportProcessVo.applyDate}" type="date" pattern="yyyy-MM-dd"/></label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>评估上报</h4>
									 <div class="row">
									    <label class="col-sm-3 control-label">评估上报日期：<fmt:formatDate value="${toEvalReportProcessVo.forwardDate}" type="date" pattern="yyyy-MM-dd"/></label>
										<label class="col-sm-3 control-label">预计出评估报告日期：<fmt:formatDate value="${toEvalReportProcessVo.toIssueDate}" type="date" pattern="yyyy-MM-dd"/></label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>出具评估报告</h4>
									<div class="row">
										<label class="col-sm-3 control-label">实际出具评估报告日期：<fmt:formatDate value="${toEvalReportProcessVo.issueDate}" type="date" pattern="yyyy-MM-dd"/></label>
										<label class="col-sm-3 control-label">收取报告日期：<fmt:formatDate value="${toEvalReportProcessVo.reportGetDate}" type="date" pattern="yyyy-MM-dd"/></label>
										<label class="col-sm-3 control-label">评估价：${toEvalReportProcessVo.evaPrice / 10000.00}&nbsp;万元</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">房龄：${toEvalReportProcessVo.houseAgeIssue}&nbsp;年内</label>
										<label class="col-sm-3 control-label">评估报告份数：${toEvalReportProcessVo.reportNumIssue}&nbsp;份</label>
									</div>
									
									<h4><span style="font-size:12px;color:#b0b0b0;">● </span>使用评估报告</h4>
									<div class="row">
										<label class="col-sm-3 control-label">领取报告时间：${toEvalReportProcessVo.reportRevDate}</label>
										<label class="col-sm-3 control-label">领取人：${toEvalReportProcessVo.receiver}</label>
										<label class="col-sm-3 control-label">领取份数：${toEvalReportProcessVo.receiveNum}&nbsp;份</label>
									</div>
						       </div>
						     
						     <!-- 评估发票信息 -->
						    <div class="tab-pane fade" id="invoice_info">
									<div class="row">
										<label class="col-sm-3 control-label">申请日期：${toEvaInvoiceVo.applyDate}</label>
										<label class="col-sm-3 control-label">发票金额：${toEvaInvoiceVo.invoiceAmount}&nbsp;元</label>
										<label class="col-sm-3 control-label">发票种类：${toEvaInvoiceVo.invoiceType}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">开票抬头：${toEvaInvoiceVo.invoiceHeader}</label>
										<label class="col-sm-3 control-label">开票地址：${toEvaInvoiceVo.invoiceAddress}</label>
										<label class="col-sm-3 control-label">税号：${toEvaInvoiceVo.taxNum}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">预计开具发票日期：${toEvaInvoiceVo.toFinshDate}</label>
										<label class="col-sm-3 control-label">开具发票时间：<fmt:formatDate value="${toEvaInvoiceVo.billTime}" type="date" pattern="yyyy-MM-dd"/></label>
										<label class="col-sm-3 control-label">银行账户：${toEvaInvoiceVo.bankAccount}</label>
									</div>
									<div class="content">
										<table id="gridTable_invoice"></table>
								        <div id="gridPager_invoice"></div>
									 </div>
							</div>
							
							<!-- 评估返利报告审批 -->
						    <div class="tab-pane fade" id="rebate_info">
									<div class="row">
									    <label class="col-sm-3 control-label">评估费收据：${toEvalRebateVo.evalRecept}</label>
										<label class="col-sm-3 control-label">
										              <c:if test="${toEvalRebateVo.status == '9' }">
										                  财务审批评估费实收：
										              </c:if>
										              <c:if test="${toEvalRebateVo.status != '9' }">
										                  申请评估费实收:
										              </c:if>
										               ${toEvalRebateVo.evalRealCharges}&nbsp;元 </label>
										<label class="col-sm-3 control-label">评估费应收：${toEvalRebateVo.evalDueCharges}元</label>
									</div>
									<div class="row">
									    <label class="col-sm-3 control-label">中原分成金额：${toEvalRebateVo.centaComAmount}元</label>
										<label class="col-sm-3 control-label">评估公司分成金额：${toEvalRebateVo.evaComAmount}元</label>
										<label class="col-sm-3 control-label">录入时间：<fmt:formatDate value="${toEvalRebateVo.inputTime}" type="date" pattern="yyyy-MM-dd"/></label>
									</div>
									<div class="row">
									    <label class="col-sm-3 control-label">评估返利状态：<aist:dict id="evalRebate" name="evalRebate" display="label" dictType="eval_rebate_status" dictCode="${toEvalRebateVo.status}" /></label>
									</div>
									<div class="content">
										<table id="gridTable_rebate"></table>
								        <div id="gridPager_rebate"></div>
									 </div>
							</div>
							
							<!-- 评估爆单 -->
						    <div class="tab-pane fade" id="baodao_info">
									 <div class="content">
										<table id="gridTable_baodao"></table>
								        <div id="gridPager_baodao"></div>
								      </div>
							</div>
							
							<!-- 评估公司变更 -->
						    <div class="tab-pane fade" id="change_info">
									<div class="row">
									    <label class="col-sm-3 control-label">变更原因：${toEvalReportProcessVo.changeInfo}</label>
									</div>
							</div>
							
							<!-- 评估结算 -->
						    <div class="tab-pane fade" id="settle_info">
									<div class="row">
									    <label class="col-sm-3 control-label">结算费用：${toEvalSettleVo.settleFee}</label>
									</div>
									<div class="row">
										<table id="gridTable_settle"></table>
								        <div id="gridPager_settle"></div>
									</div>
							</div>
							
							<!-- 调佣审批 -->
						    <div class="tab-pane fade" id="message_info">
								<div class="row">
									     <label class="col-sm-3 control-label">调佣事项：${evalChangeCommVO.changeChargesType}</label>
									     <label class="col-sm-3 control-label">调佣类型：${evalChangeCommVO.changeChargesType}</label>
									     <label class="col-sm-3 control-label">调佣事由：${evalChangeCommVO.changeChargesCause}</label>
									</div>
									<div class="content">
										<table style="width:100%;" class="table-hover">
								            <thead>
								            <tr>
								                <td></td><td>合作费类型</td><td>分成金额</td><td>分成比例</td><td>合作人</td><td>合作部门</td><td>合作经理</td>
								            </tr>
								            </thead>
								            <tbody>
								            <c:forEach items="${evalChangeCommVO.coPersonList }" var="coPerson" varStatus="s">
								            <tr>
								                    <td>${coPerson.position }${s.count} : <input type="hidden" name="coPersonList[${s.index}].pkid" value="${coPerson.pkid }"></td>
								                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateType" value="${coPerson.cooperateType }" readonly="readonly"></td>
								                    <td><input class="shareAmount"  type="text" style="width: 120px" name="coPersonList[${s.index}].shareAmount" value="${coPerson.shareAmount }"  readonly="readonly"></td>
								                    <td><span class="aa"></span><span>%</span></td>
								                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].employeeName" value="${coPerson.employeeName }"  readonly="readonly"></td>
								                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateDept" value="${coPerson.cooperateDept }" readonly="readonly"></td>
								                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateManager" value="${coPerson.cooperateManager }" readonly="readonly"></td>
								                </tr>
								            </c:forEach>
								                <tr>
								                    <td></td><td>部门</td><td>员工</td><td>分成金额</td><td>分成比例</td><td>分成说明</td><td>成交单数</td>
								                </tr>
								                <c:forEach items="${evalChangeCommVO.sharePersonList }" var="sharePerson" varStatus="s">
								                <tr>
								                    <td>${sharePerson.position }${s.count}:<input type="hidden" name="sharePersonList[${s.index}].pkid" value="${sharePerson.pkid }"></td>
								                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].department" value="${sharePerson.department }" readonly="readonly"></td>
								                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].employeeName" value="${sharePerson.employeeName }" readonly="readonly"></td>
								                    <td><input class="shareAmount" type="text" style="width: 120px" name="sharePersonList[${s.index}].shareAmount" value="${sharePerson.shareAmount }" readonly="readonly"></td>
								                    <td><span class="aa"></span><span>%</span></td>
								                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].shareReason" value="${sharePerson.shareReason }" readonly="readonly"></td>
								                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].dealCount" value="${sharePerson.dealCount }" readonly="readonly"></td>
								                </tr>
								                </c:forEach>
								              
								                <tr></tr><tr></tr>
								                
								                <c:forEach items="${evalChangeCommVO.warrantPersonList }" var="warrantPersonList" varStatus="s">
								                <tr>
								                    <td>权证1:<input type="hidden" name="warrantPersonList[${s.index}].pkid" value="${warrantPersonList.pkid }" readonly="readonly"></td>
								                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].department" value="${warrantPersonList.department }" style="width: 120px" readonly="readonly"></td>
								                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].employeeName" value="${warrantPersonList.employeeName }" style="width: 120px" readonly="readonly"></td>
								                    <td></td>
								                    <td></td>
								                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].position" value="${warrantPersonList.position }" style="width: 120px" readonly="readonly"></td>
								                    <td></td>
								                </tr>
								                </c:forEach>
								                <tr>
								                    <td></td>
								                    <td></td>
								                    <td>合计:</td>
								                    <td><input id="ttlComm" class="shareAmount" type="text" value="${evalChangeCommVO.ttlComm }" name="ttlComm" style="width: 120px" readonly="readonly"></td>
								                    <td><span id="totalPacentage"></span><span>%</span></td>
								                    <td>单数合计:</td>
								                    <td><input type="text" value="${evalChangeCommVO.dealCount }" name="dealCount" style="width: 120px" readonly="readonly"></td>
								                </tr>
								            </tbody>
								        </table>
									 </div>
									 <div class="content">
										<table id="gridTable_message"></table>
								        <div id="gridPager_message"></div>
									 </div>
							</div>
								
							<!-- 评估退费 -->
							<div class="tab-pane fade" id="refund_info">
							       <div class="row">
										<label class="col-sm-3 control-label">退费类别：${toEvaRefundVo.refundKinds}</label>
										<label class="col-sm-3 control-label">申请人：${toEvaRefundVo.proposer}</label>
										<label class="col-sm-3 control-label">申请分行：${toEvaRefundVo.applyDepart}</label>
									</div>
									 <div class="row">
										<label class="col-sm-3 control-label">申请时间：${toEvaRefundVo.applyTime}</label>
										<label class="col-sm-3 control-label">退费金额：${toEvaRefundVo.refundAmount}</label>
										<label class="col-sm-3 control-label">退款对象：${toEvaRefundVo.refundTarget}</label>
									</div>
									 <div class="row">
										<label class="col-sm-3 control-label">退款原因：${toEvaRefundVo.refundCause}</label>
										<label class="col-sm-3 control-label">预计退款时间：${toEvaRefundVo.toRefundTime}</label>
										<label class="col-sm-3 control-label">评估报告回收份数：${toEvaRefundVo.reportBackNum}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估报告回收份数：${toEvaRefundVo.reportBackNum}</label>
										<label class="col-sm-3 control-label">回收时间：${toEvaRefundVo.backTime}</label>
										<label class="col-sm-3 control-label">未回收原因：${toEvaRefundVo.backCause}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估报告回收份数：${toEvaRefundVo.reportBackNum}</label>
										<label class="col-sm-3 control-label">回收时间：${toEvaRefundVo.backTime}</label>
										<label class="col-sm-3 control-label">未回收原因：${toEvaRefundVo.backCause}</label>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label">评估费实收金额：${toEvaRefundVo.evalRealCharges}</label>
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
	<%-- <script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script> --%>
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

