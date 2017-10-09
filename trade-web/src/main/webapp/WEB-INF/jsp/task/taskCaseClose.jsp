<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
	rel="stylesheet">
<!-- 备件相关结束 -->
<!-- jdGrid相关 -->
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- datepikcer -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css"
	rel="stylesheet">
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">


<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var approveType = "${approveType }";
	var processInstanceId = "${processInstanceId}";
	var finishYear = "${editCaseDetailVO.finishYear}";
	var finOrgCode = "${editCaseDetailVO.lastLoanBank}";
	var custCode = "${ editCaseDetailVO.custCode}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<!-- 主要内容页面 -->
	<div class="wrapper wrapper-content">
		<div class="row animated fadeInDown" id="serviceFlow">
			<div class="scroll_box fadeInDown animated marginbot">
				<div class="row wrapper white-bg new-heading ">
					<div class="pl10">
						<h2 class="newtitle-big">结案归档</h2>
						<div class="mt20">
							<button type="button" class="btn btn-icon btn-blue mr5">
								<i class="iconfont icon">&#xe600;</i> <a
									href="${ctx }/case/myCaseList">在途单列表</a>
							</button>
							<button type="button" class="btn btn-icon btn-blue mr5">
								<i class="iconfont icon">&#xe63f;</i><a
									href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a>
							</button>
						</div>
					</div>
				</div>

				<div class="ibox-content border-bottom clearfix space_box noborder">
					<div>
						<h2 class="newtitle title-mark">填写任务信息</h2>
						<div class="panel blank-panel">
							<div class="panel-heading">
								<div class="panel-options">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="#tab-1">交易信息</a></li>
										<li class=""><a data-toggle="tab" href="#tab-2">贷款信息</a></li>
										<li class=""><a data-toggle="tab" href="#tab-3">附件信息</a></li>
									</ul>
								</div>
							</div>

							<div class="panel-body">
								<div class="tab-content">
									<div id="tab-1" class="tab-pane active">
										<div>
											<div class="form_list">
												<form method="get" class="form-horizontal" id="caseCloseform">
													<%--环节编码 --%>
													<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
													<!-- 交易单编号 -->
													<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
													<%-- 原有数据对应id --%>
													<input type="hidden" id="taskId" name="taskId" value="${taskId }">
													<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
													<%-- 设置审批类型 --%>
													<input type="hidden" id="approveType" name="approveType" value="${approveType }">
													<input type="hidden" id="operator" name="operator" value="${operator }">
													<%-- 原有数据对应id --%>

													<input type="hidden" id="initPayPkid" name="initPayPkid" value="${editCaseDetailVO.initPayPkid }">
													<input type="hidden" id="secPayPkid" name="secPayPkid" value="${editCaseDetailVO.secPayPkid }">
													<input type="hidden" id="lastPayPkid" name="lastPayPkid" value="${editCaseDetailVO.lastPayPkid }">
													<input type="hidden" id="compensatePayPkid" name="compensatePayPkid" value="${editCaseDetailVO.compensatePayPkid }">
												<div class="marinfo">
													<div class="line">
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																业务单创建时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input class="input_type yuanwid datatime"id="createTime" name="realConTime" disabled="disabled" readonly="readonly"
											                        value="<fmt:formatDate  value='${editCaseDetailVO.createTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																接单时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input class="input_type yuanwid datatime" id="resDate" name="resDate" disabled="disbled" readonly="readonly"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.resDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
															</div>
														</div>
													</div>
													
													<div id="guestUpDiv" class="clear add-member mb20"></div>
													<div id="guestDownDiv" class="clear add-member mb20"></div>

													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">
																付款金额(一期) </label> <input class="input_type yuanwid"  
																value="<fmt:formatNumber value='${ editCaseDetailVO.initAmount}' type='number' pattern='#0.00' />" 
															    id="initAmount" name="initAmount" onkeyup="checkNum(this)"> <span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																付款时间(一期) </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input  class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															       id="initPayTime" name="initPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 付款方式(一期)
															</label>
															<aist:dict clazz="select_control data_style" id="initPayType" name="initPayType" display="select" defaultvalue="${editCaseDetailVO.initPayType}" dictType="30015" />
														</div>
													</div>

													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">付款金额(二期)</label>
															<input class=" input_type yuanwid" 
															value="<fmt:formatNumber value='${editCaseDetailVO.secAmount }' type='number' pattern='#0.00' />" 
															id="secAmount" name="secAmount" onkeyup="checkNum(this)"> 
															<span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																付款时间(二期) </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input  class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															       id="secPayTime" name="secPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 付款方式(二期)
															</label> 
														<aist:dict clazz="select_control data_style" id="secPayType" name="secPayType" display="select" defaultvalue="${editCaseDetailVO.secPayType}" dictType="30015" />
														</div>
													</div>
													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">付款金额(三期)</label>
															<input class=" input_type yuanwid" value="<fmt:formatNumber value='${editCaseDetailVO.lastAmount}' type='number' pattern='#0.00' />" 
															       id="lastAmount" name="lastAmount" onkeyup="checkNum(this)"> <span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																付款时间(三期) </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															        id="lastPayTime" name="lastPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 付款方式(三期)
															</label>
														<aist:dict clazz="select_control data_style" id="lastPayType" name="lastPayType" display="select" defaultvalue="${editCaseDetailVO.lastPayType}" dictType="30015" />
														</div>
													</div>
												</div>
												</form>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small"> 产证面积
														</label> <input class=" input_type yuanwid" id="square"  readonly="readonly" disabled="disabled" name="square" onkeyup="checkNum(this)"
												                  value="<fmt:formatNumber value='${editCaseDetailVO.square}' type='number' pattern='#0.00' />">  <span class="date_icon">平方米</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 产证地址
														</label> <input class="input_type mendwidth" readonly="readonly"  id="propertyAddr" name="propertyAddr" value="${editCaseDetailVO.propertyAddr}">
													</div>

												</div>
												<div class="line">
													<div class="form_content mt3">
														<label class="control-label sign_left_small"> 竣工年份
														</label>
														<input class=" select_control yuanwid" readonly="readonly" name="finishYear" id="finishYear" value="${editCaseDetailVO.finishYear}"></input>
													</div>
													<div class="form_content mt3">
														<label class="control-label sign_left_small"> 所在楼层
														</label> <input class=" input_type yuanwid" readonly="readonly"  placeholder="所在楼层"  onkeyup="checkNum2(this)"
												      id="locateFloor" name="locateFloor" value="${editCaseDetailVO.locateFloor}">
														<span class="date_icon">层</span>
													</div>
													<div class="form_content mt3">
														<label class="control-label sign_left_small">总层高</label> <input
															class=" input_type yuanwid" readonly="readonly"  placeholder="总层高" onkeyup="checkNum2(this)"
												            id="totalFloor" name="totalFloor" value="${editCaseDetailVO.totalFloor}"> <span
															class="date_icon">层</span>
													</div>

												</div>
												<div class="line">
												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															网签时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime"
																id="realConTime" readonly="readonly"
												               name="realConTime" value="<fmt:formatDate  value='${editCaseDetailVO.realConTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">  合同价
														</label> <input class=" input_type yuanwid" readonly="readonly"  id="conPrice" name="conPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.conPrice}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">成交价
														</label> <input class=" input_type yuanwid" id="realPrice" readonly="readonly"   name="realPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.realPrice}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															缴税时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime"
																id="realHtTime" readonly="readonly"
												                name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.paymentTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															过户时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime"
																id="realHtTime" readonly="readonly"
												                name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.realHtTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															领证时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime"
																id="realHtTime" readonly="readonly"
												                name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.realPropertyGetTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">
															个人所得税 </label> <input class=" input_type yuanwid" pid="personalIncomeTax"  readonly="readonly"  name="personalIncomeTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.personalIncomeTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															增值税及附加 </label> <input class=" input_type yuanwid"  readonly="readonly"   id="businessTax" name="businessTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.businessTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 契税
														</label> <input class=" input_type yuanwid" id="contractTax"  readonly="readonly"  name="contractTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.contractTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															土地增值税 </label> <input class=" input_type yuanwid"  readonly="readonly"  id="landIncrementTax" name="landIncrementTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.landIncrementTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
												</div>
												
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">
															卖方剩余贷款 </label> <input class=" input_type yuanwid" pid="personalIncomeTax"  readonly="readonly"  name="personalIncomeTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.restMoney}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															还款方式 </label> <input class=" input_type yuanwid"  readonly="readonly"   id="businessTax" name="businessTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.restPayType}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															还款时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime"
																id="realHtTime" readonly="readonly"
												                name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.restPayTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															还款银行 </label> <input class=" input_type yuanwid"  readonly="readonly"  id="landIncrementTax" name="landIncrementTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.restFinOrgCode}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															结案时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input class="input_type yuanwid datatime"
																id="approveTime" readonly="readonly"
												name="approveTime" value="<fmt:formatDate  value='${editCaseDetailVO.approveTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content mt3">
														<label class="control-label sign_left_small"> 房屋性质
														</label>
														<aist:dict clazz="select_control data_style" id="propertyType" name="propertyType" display="label" dictCode="${editCaseDetailVO.propertyType}" dictType="30014" />
													</div>
													<div class="form_content mt3">
														<label class="control-label sign_left_small">是否唯一住房</label>
														<aist:dict clazz="select_control data_style" id="isUniqueHome" name="isUniqueHome" display="label" dictCode="${editCaseDetailVO.isUniqueHome}" dictType="is_unique_home" />
													</div>
													<div class="form_content mt3">
														<label class="control-label sign_left_small">购房年数</label>
														<aist:dict clazz="select_control data_style" id="holdYear" name="holdYear" display="label" dictCode="${editCaseDetailVO.holdYear}" dictType="hold_year" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane">
										<div class="form_list">
											<div class="marinfo">
											<div class="line">
													<div class="form_content">
														<label
															class="control-label sign_left_small">
															付款方式</label>
														<aist:dict clazz="select_control yuanwid" id="payType" name="payType" display="select" defaultvalue="${editCaseDetailVO.payType}" dictType="30015" />
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small">
															贷款金额 </label>
														<input class=" input_type yuanwid" id="mortTotalAmount"  name="mortTotalAmount" onkeyup="checkNum(this)" 
												value="<fmt:formatNumber value='${ editCaseDetailVO.mortTotalAmount}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">是否自办</label>
														<select class="select_control yuanwid" id="isDelegateYucui"  name="isDelegateYucui">
														<c:choose>
															<c:when test="${editCaseDetailVO.isDelegateYucui == '1'}">
																<option value="0" >是</option>
																<option value="1" selected="selected">否</option>
															</c:when>
															<c:otherwise>
																<option value="0" selected="selected">是</option>
																<option value="1" >否</option>
															</c:otherwise>
														</c:choose>
														</select>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															面签时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input class="input_type yuanwid datatime"
																id="signDate" name="signDate" readonly="readonly"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.signDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															递件时间 </label>
														<div
															class="input-group sign-right dataleft pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="apprDate" name="apprDate" readonly="readonly"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.apprDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															批贷时间 </label>
														<div
															class="input-group sign-right dataleft pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="apprDate" name="apprDate" readonly="readonly"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.apprDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">公积金贷款金额</label>
														<input class=" input_type yuanwid" id="prfAmount" name="prfAmount" onkeyup="checkNum(this)" 
												value="<fmt:formatNumber value='${ editCaseDetailVO.prfAmount}' type='number' pattern='#0.00' />">  <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">公积金贷款年限</label>
														<input class=" input_type yuanwid" id="prfYear" name="prfYear" value="${ editCaseDetailVO.prfYear}" onkeyup="checkNum2(this)"> <span class="date_icon">年</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">主贷人</label> <select
														 class="select_control yuanwid"
															name="custCode" id="custCode" >
											        </select>
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">商贷贷款金额</label>
														<input class=" input_type yuanwid" id="comAmount" name="comAmount" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.comAmount}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">商贷贷款年限</label>
														<input class=" input_type yuanwid" id="comYear" name="comYear" value="${ editCaseDetailVO.comYear}"><span class="date_icon">年</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">商贷利率折扣</label>
														<input class=" input_type yuanwid" id="comDiscount" name="comDiscount" onkeyup="autoCompleteComDiscount(this)" placeholder="0.50~1.50之间"
											value="<fmt:formatNumber value='${editCaseDetailVO.comDiscount}' type='number' pattern='#0.00' />">
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">放款方式</label>
														<aist:dict clazz="select_control yuanwid" id="lendWay" name="lendWay" display="select" defaultvalue="${editCaseDetailVO.lendWay}" dictType="30017" />
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															领他项证时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime" disabled="disabled"
																readonly="readonly"  id="tazhengArrDate" name="tazhengArrDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.tazhengArrDate}' type='both' pattern='yyyy-MM-dd'/>">
														</div>
													</div>
												</div>
												<div class="line">
													<div class="form_content mt3">
														<label class="control-label sign_left_small">房贷套数</label>
														<input class=" input_type yuanwid" id="houseNum" name="houseNum" value="${editCaseDetailVO.houseNum}" onkeyup="checkNum2(this)">
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															放款时间 </label>
														<div
															class="input-group sign-right dataleft pull-left">
															<input  class="input_type yuanwid datatime" disabled="disabled"
																value="<fmt:formatDate  value='${editCaseDetailVO.lendDate }'  type='both' pattern='yyyy-MM-dd' />" 
													          id="lendDate" name="lendDate" readonly="readonly"  onfocus="this.blur()">
														</div>
													</div>
												</div>

												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">信贷员</label> <input
															class=" input_type yuanwid" id="loanerName" readonly="readonly"  name="loanerName" value="${ editCaseDetailVO.loanerName}">
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">信贷员联系电话</label>
														<input class=" input_type yuanwid" readonly="readonly"  id="loanerPhone" name="loanerPhone" value="${ editCaseDetailVO.loanerPhone}">
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">评估公司</label>
														<input class=" input_type yuanwid" readonly="readonly"  id="finOrgName" name="finOrgName" value="${editCaseDetailVO.finOrgName}">
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">评估价格</label>
														<input class=" input_type yuanwid" id="comAmount" name="comAmount" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.comAmount}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">承办银行</label>
															<select class="select_control data_style" style="width: 491px" name="bank" id="bank" >
											               </select>
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">支行名称</label>
															<select class="select_control data_style"  style="width: 491px" id="lastLoanBank" >
											                </select>
											               <input name="lastLoanBank" type="hidden" value="${editCaseDetailVO.lastLoanBank }"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-3" class="tab-pane">
									<div id="imgShow" >
											<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
												<div class="row">
													 <p>${accesory.preFileName}</p></br>
													<div class="mb20 mt20" style="text-align:left">
														<a href="#"  data-gallery="">
															<img src="<aist:appCtx appName='shcl-image-web'/>/image/${accesory.preFileAdress}/_f.jpg" style="padding-bottom: 5px;padding-top: 5px;width:100px;" class="viewer-toggle">
														</a>
													</div>
												</div>
											</c:forEach>
										</div>
									  </div>
									</div>
								</div>
							</div>

					<div class="clearfix" id="aboutInfo">
						<h2 class="newtitle title-mark">审批记录</h2>
						<div class="jqGrid_wrapper">
							<table id="reminder_list"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				</div>

				<!-- 案件备注信息 -->
				<div id="caseCommentList" class="view-content"></div>
				<div class="form-btn">
					<div class="text-center">
						<button class="btn btn-success btn-space" onclick="save(false)">保存</button>
						<button class="btn btn-success btn-space" onclick="submitFrom()">提交</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<content tag="local_script"> 
	<!-- Steps --> 
	<script	src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- 上传附件相关 -->
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
	<script src="${ctx}/js/stickUp.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>


	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script>
	<script	src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script	src="${ctx}/transjs/task/showAttachment.js"></script>
	<script	src="${ctx}/js/trunk/task/caseCloseAttachment.js"></script> 
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script	src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script	src="${ctx}/js/template.js" type="text/javascript"></script>
	<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script	src="${ctx}/js/viewer/viewer.min.js"></script> 
	<!-- 改版引入的新的js文件 -->
    <script src="${ctx}/js/common/textarea.js?v=1.0.1"></script> 
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	
	<!--公共信息-->
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
		<script>
		var isAccumulation=false;
		var loanReq ="${loanReq}";
		
		initView();
		
		function initView(){
			$('#basicInfo').viewer();
		}
		
		//select框 设置只读
		function readOnlyF(){
			$("#mortType").attr("disabled","disabled"); //贷款类型
			$("#isDelegateYucui").attr("disabled","disabled");
			$("#lastLoanBank").attr("disabled","disabled");
			$("#custCode").attr("disabled","disabled");
			$("#bank").attr("disabled","disabled");
		}
		$(document).ready(function() {
			isAccumulation=$('#mortType').val()=='30016003';
/* 			if(isAccumulation){
				$('#comAmount').val('').attr("disabled","disabled");
				$('#comYear').val('').attr("disabled","disabled");
			}else if($('#mortType').val()=='30016001'){
				$('#prfAmount').val('').attr("disabled","disabled");
				$('#prfYear').val('').attr("disabled","disabled");
			} */
			
			$('#comAmount').attr("readonly","readonly");
			$('#comYear').attr("readonly","readonly");
			$('#comDiscount').attr("readonly","readonly");
			$('#mortTotalAmount').attr("readonly","readonly");
			//$("#tab-2").find("select").attr("disabled","disabled");			
			$('#prfAmount').attr("readonly","readonly");
			$('#prfYear').attr("readonly","readonly");
			
			
			if(!~~loanReq){
				$("#tab-2").find("input").attr("disabled","disabled");
				$("#tab-2").find("select").attr("disabled","disabled");
			}
			 $("#wizard").steps();
				<c:if test="${empty editCaseDetailVO.lcid}">
				 $("#closeType").attr("disabled","disabled");
				</c:if>
			// showTask();
			/**年份选择初始化*/
			initSelectYear("finishYear", finishYear);
			
			$("#bank").change(function(){
				 var selectValue = $("#bank").val(); 
				 getBranchBankList(selectValue)
			 });
			if(finOrgCode!=''){
		 		getBankList(finOrgCode);	
			}
		 	/*主贷人*/
		 	initSelectCustCode(custCode);
		 	
			initGuestInfo("30006002");
			initGuestInfo("30006001")
			readOnlyF();
			/**日期组件*/
	        $('#data_1 .input-group.date').datepicker({
	            todayBtn: "linked",
	            keyboardNavigation: false,
	            forceParse: false,
	            calendarWeeks: false,
	            autoclose: true
	        });
		});
		
		/*贷款折扣自动补全*/
		function autoCompleteComDiscount(obj){
			
			obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
			obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
			obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			
			var inputVal = obj.value;
		 	if(inputVal>=0.5 && inputVal<=1.5){
				var reg =/^[01]{1}\.{1}\d{3,}$/;
				if(reg.test(inputVal)){
					obj.value = inputVal.substring(0,4);
				}
			}
		} 
		
		/**初始化select*/
		function initSelectYear(id, value) {
			var d = new Date();
			var endYear = d.getFullYear();
			var starYear = 1900;
			var friend = $("#" + id);
			friend.empty();
			for (var i = endYear; i >= starYear; i--) {
				if(value==null || value=='' || value==undefined || value > endYear || value < starYear) {
					value=endYear;
				}
				if(value==i) {
					friend.append("<option value='"+i+"'  selected='selected'>"+i+"</option>");
				} else {
					friend.append("<option value='"+i+"'>"+i+"</option>");
				}
				
			}
		}
		
		/*获取银行列表*/
		function getBankList(pcode){
			var friend = $("#bank");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryBankListByPcode",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":pcode},
		    	success:function(data){
		    		if(data.bankList != null){
		    			var selectBank=isAccumulation?pcode:data.bankCode;//公积金的话选的直接是分行
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(selectBank == data.bankList[i].finOrgCode) {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"+data.bankList[i].finOrgName+"</option>");
		    					$("#bank").val(data.bankList[i].finOrgName);
		    				} else {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
		    				}
		    			}
						friend.chosen({no_results_text:"未找到该选项",width:"491px",search_contains:true,disable_search_threshold:10});
						if(isAccumulation)return ;
	    				if(pcode == null || pcode == "" || pcode == undefined) {
	    					getBranchBankList(data.bankList[0].finOrgCode);
	    				} else {
	    					getBranchBankList(data.bankCode);
	    				}
		    		}
		    	}
			  });
		}
		
		/*获取支行列表*/
		function getBranchBankList(pcode){
			var friend = $("#lastLoanBank");
			if(document.getElementById("lastLoanBank")["options"].length > 0) {
				friend.chosen('destroy');
			}
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryBankListByParentCode",
			    method:"post",
			    dataType:"json",
			    data:{faFinOrgCode:pcode},
		    	success:function(data){
		    		if(data != null){
		    			var str = "";
		    			for(var i = 0;i<data.length;i++){
		    				if(finOrgCode == data[i].finOrgCode) {
		    					//friend.append("<option value='"+data[i].finOrgCode+"' selected='selected'>"+data[i].finOrgName+"</option>");
		    					 str += "<option value='"+data[i].finOrgCode+"' selected='selected'>"+data[i].finOrgName+"</option>";
		    				} else {
		    					//friend.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
		    					str += "<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>";
		    				}
		    			}
		    			friend.html(str);
						friend.chosen({no_results_text:"未找到该选项",width:"491px",search_contains:true,disable_search_threshold:10});
		    		}
		    	}
			  });
		}
		
		function showTask(){
			isShow('transInfo', '');
			isShow('mortgageInfo', 'none');
		}
		
		function showMortgage(){
			isShow('transInfo', 'none');
			isShow('mortgageInfo', '');
		}
		
		/*设置div显示或隐藏*/
		function isShow(divName, stats) {
		    var div_array = document.getElementsByName(divName);   
		    for(i=0;i<div_array.length;i++)  
		    {  
			    div_array[i].style.display = stats; 
		    }  
		}
		
		/*double 验证*/
	    function checkNum(obj) { 
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
	        //保证只有出现一个.而没有多个.
	        obj.value = obj.value.replace(/\.{2,}/g,".");
	        //保证.只出现一次，而不能出现两次以上
	        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     }  
		
		function checkNum2(obj) {
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
		}
		
		/**初始化主贷人select*/
		function initSelectCustCode(value) {
			var friend = $("#custCode");
			friend.empty();
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : "${ctx}/task/PSFSign/qureyGuestList",
				dataType : "json",
				data : [{name:"caseCode",value:caseCode}],
				success : function(data) {
					$.each(data, function(index, value){
						if((value.pkid+"")==custCode) {
							friend.append("<option value='"+value.pkid+"'  selected='selected'>"+value.guestName+"</option>");
						} else {
							friend.append("<option value='"+value.pkid+"'>"+value.guestName+"</option>");
						}
					});
				},
				error : function(errors) {
					window.wxc.error("获取主贷人失败");
				}
			});
		}
		
		/**提交数据*/
		function submitFrom() {
			/*if(checkAttachment()) {
				save(true);
			}*/
			save(true);
		}

		/**保存数据*/
		function save(b) {
			var jsonData = $("#caseCloseform").serializeArray();

			var conPrice = Number($('input[name=conPrice]').val());
			var initAmount = Number($('input[name=initAmount]').val());
			var secAmount = Number($('input[name=secAmount]').val());
			var lastAmount = Number($('input[name=lastAmount]').val());
//			var compensateAmount = Number($('input[name=compensateAmount]').val());

			if(initAmount>0){
				if(null == $('input[name=initPayTime]').val() || '' == $('input[name=initPayTime]').val()){
					window.wxc.alert("首付付款时间不能为空!");
					$('input[name=initPayTime]').focus();
					return false;
				}

				if ($('select[name=initPayType]').val() == '') {
					window.wxc.alert("首付付款方式不能为空!");
					$('select[name=initPayType]').focus();
					return false;
				}

			}
			if(secAmount>0){
				if(null == $('input[name=secPayTime]').val() || '' == $('input[name=secPayTime]').val()){
					window.wxc.alert("二期付款时间不能为空!");
					$('input[name=secPayTime]').focus();
					return false;
				}

				if ($('select[name=secPayType]').val() == '') {
					window.wxc.alert("二期付款方式不能为空!");
					$('select[name=secPayType]').focus();
					return false;
				}

			}
			if(lastAmount>0){
				if(null == $('input[name=lastPayTime]').val() || '' == $('input[name=lastPayTime]').val()){
					window.wxc.alert("尾款付款时间不能为空!");
					$('input[name=lastPayTime]').focus();
					return false;
				}

				if ($('select[name=lastPayType]').val() == '') {
					window.wxc.alert("尾款付款方式不能为空!");
					$('select[name=lastPayType]').focus();
					return false;
				}

			}
			/* if(compensateAmount>0){
				if(null == $('input[name=compensatePayTime]').val() || '' == $('input[name=compensatePayTime]').val()){
					window.wxc.alert("装修补偿款时间不能为空!");
					$('input[name=compensatePayTime]').focus();
					return false;
				}

				if ($('select[name=compensatePayType]').val() == '') {
					window.wxc.alert("装修补偿款方式不能为空!");
					$('select[name=compensatevPayType]').focus();
					return false;
				}

			} */

			if (conPrice!=initAmount+secAmount+lastAmount) {//天津版本无装修补偿款  compensateAmount
				window.wxc.alert("付款信息项之和必须等于合同价!");
				$('input[name=conPrice]').focus();
				return false;
			}

			if (Number($('input[name=conPrice]').val()) <= 0) {
				window.wxc.alert("合同价必须大于0!");
				$('input[name=conPrice]').focus();
				return false;
			}

			var url = ctx+"/task/CaseClose/saveCaseClose";
			if(b) {
				url = ctx+"/task/CaseClose/submitCaseClose";
			}
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
				beforeSend:function(){
					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
					$(".blockOverlay").css({'z-index':'9998'});
				},
				complete: function() {
					if(!b){
						$.unblockUI();
					}
					 if(status=='timeout'){//超时,status还有success,error等值的情况
					  Modal.alert(
					  {
						msg:"抱歉，系统处理超时。"
					  });
					 $(".btn-primary").one("click",function(){
							parent.$.fancybox.close();
						});
							}
						} ,

				success : function(data) {
							if(data.message){
								window.wxc.alert(data.message);
							}
							if(window.opener)
							{
								 window.close();
								 window.opener.callback();
							} else {
								 window.location.href = "${ctx }/task/myTaskList";
							}
							//window.location.href = "${ctx }/task/myTaskList";
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		
		var divIndexDown = 1;
		function addDateDivDown() {
			
			var txt = '<div id="dateDivD_' + divIndexDown + '" class="line">';
			txt += "<input type='hidden' name='pkidDown' value='0'/>";
			txt += "<div class='form_content'>";
			txt += "<label class='control-label sign_left_small'>买家姓名</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value=''></div>";
			txt += "<div class=\"form_content\">";
			txt += "<label class=\"control-label sign_left_small\">买家电话</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value=''></div>";
			txt += '<span class=""></span>';
			txt += '</div>';
			// alert(txt);
			$("#guestDownDiv").before(txt);
			divIndexDown++;
		}
		
		var divIndexUp = 1;
		function addDateDivUp() {
			var txt = '<div id="dateDivU_' + divIndexUp + '" class="line">';
			txt += "<input type='hidden' name='pkidUp' value='0'/>";
			txt += "<div class='form_content'>";
			txt += "<label class='control-label sign_left_small'>卖家姓名</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value=''></div>";
			txt += "<div class=\"form_content\">";
			txt += "<label class=\"control-label sign_left_small\">卖家电话</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value=''></div>";
			txt += '<span class=""></span>';
			txt += '</div>';
			// alert(txt);
			$("#guestUpDiv").before(txt);
			divIndexUp++;
		}
		
		function removeDateDiv(index) {
			$("#" + index).remove();
		}
		
		function removeDiv(id,pkid) {
			var txt = "<input type='hidden' name='guestPkid' value='"+pkid+"'/>";
			$("#guestDelDiv").before(txt);
			$("#"+id).remove();
		}
		
		function initGuestInfo(transPosition) {
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : "${ctx}/task/sign/queryGuestInfo",
				dataType : "json",
				data :  [{
					name:"transPosition",
					value:transPosition
				},{
					name:"caseCode",
					value:caseCode
				}],
				success : function(data) {
					var length = data.length;
					if(length > 0) {
					$.each(data, function(index, value){
						if(transPosition=="30006002") {
							var txt = '<div id="dateDivD_' + divIndexDown + '" class="line">';
							txt += "<input type='hidden' name='pkidDown' value='"+value.pkid+"'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>买家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value='"+value.guestName+"'></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">买家电话</label>";
							txt += "<input type=\"text\"  style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value='"+value.guestPhone+"'></div>";
							txt += '<span class=""></span>';
							txt += '</div>';
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="line">';
							txt += "<input type='hidden' name='pkidUp' value='"+value.pkid+"'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>卖家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value='"+value.guestName+"'></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">卖家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value='"+value.guestPhone+"'></div>";
							txt += '<span class=""></span>';
							txt += '</div>';		
							$("#guestUpDiv").before(txt);
							divIndexUp++;
						}
					});
					} else {
						if(transPosition=="30006002") {
							var txt = '<div id="dateDivD_' + divIndexDown + '" class="line">';
							txt += "<input type='hidden' name='pkidDown' value='0'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>买家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value=''></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">买家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value=''></div>";
							txt += '<span class=""><span>';
							txt += '</div>';
							// alert(txt);
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="line">';
							txt += "<input type='hidden' name='pkidUp' value='0'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>卖家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value=''></div>";
							txt += "<div class=\"form_content\">";
							txt += "<label class=\"control-label sign_left_small\">卖家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value=''></div>";
							txt += '<span class=""></span>';
							txt += '</div>';
							// alert(txt);
							$("#guestUpDiv").before(txt);
							divIndexUp++;
						}
					}
				},
				error : function(errors) {
					window.wxc.error("买卖双方信息加载失败！");
				}
			});
		}
		function show(fileId) {
			window.open(appCtx['shcl-image-web'] + "/image/"+fileId+"/_f.jpg");
		}
	</script> </content>
</body>


</html>