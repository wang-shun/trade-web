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
					<input type="hidden" id="lapPkid" name="lapPkid" value="${toApproveRecord.pkid }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="propertyPkid" name="propertyPkid" value="${editCaseDetailVO.propertyPkid }">
					<input type="hidden" id="initPayPkid" name="initPayPkid" value="${editCaseDetailVO.initPayPkid }">
					<input type="hidden" id="secPayPkid" name="secPayPkid" value="${editCaseDetailVO.secPayPkid }">
					<input type="hidden" id="lastPayPkid" name="lastPayPkid" value="${editCaseDetailVO.lastPayPkid }">
					<input type="hidden" id="compensatePayPkid" name="compensatePayPkid" value="${editCaseDetailVO.compensatePayPkid }">
					<input type="hidden" id="signPkid" name="signPkid" value="${editCaseDetailVO.signPkid }">
					<input type="hidden" id="lcid" name="lcid" value="${editCaseDetailVO.lcid }">
					<input type="hidden" id="ghid" name="ghid" value="${editCaseDetailVO.ghid }">
					<input type="hidden" id="taxid" name="taxid" value="${editCaseDetailVO.taxid }">
					<input type="hidden" id="mpkid" name="mpkid" value="${editCaseDetailVO.mpkid }">
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
																分单时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input class="input_type yuanwid datatime" id="resDate" name="resDate" disabled="disbled" readonly="readonly"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.resDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
															</div>
														</div>
													</div>
													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">
																首付付款 </label> <input class="input_type yuanwid"  
																value="<fmt:formatNumber value='${ editCaseDetailVO.initAmount}' type='number' pattern='#0.00' />" 
															    id="initAmount" name="initAmount" onkeyup="checkNum(this)"> <span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input  class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															       id="initPayTime" name="initPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 方式
															</label>
															<aist:dict clazz="select_control data_style" id="initPayType" name="initPayType" display="select" defaultvalue="${editCaseDetailVO.initPayType}" dictType="30015" />
														</div>
													</div>
													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">二期付款</label>
															<input class=" input_type yuanwid" 
															value="<fmt:formatNumber value='${editCaseDetailVO.secAmount }' type='number' pattern='#0.00' />" 
															id="secAmount" name="secAmount" onkeyup="checkNum(this)"> 
															<span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input  class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															       id="secPayTime" name="secPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 方式
															</label> 
														<aist:dict clazz="select_control data_style" id="secPayType" name="secPayType" display="select" defaultvalue="${editCaseDetailVO.secPayType}" dictType="30015" />
														</div>
													</div>
													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">尾款付款</label>
															<input class=" input_type yuanwid" value="<fmt:formatNumber value='${editCaseDetailVO.lastAmount}' type='number' pattern='#0.00' />" 
															       id="lastAmount" name="lastAmount" onkeyup="checkNum(this)"> <span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															        id="lastPayTime" name="lastPayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 方式
															</label>
														<aist:dict clazz="select_control data_style" id="lastPayType" name="lastPayType" display="select" defaultvalue="${editCaseDetailVO.lastPayType}" dictType="30015" />
														</div>
													</div>
													<div class="line">
														<div class="form_content">
															<label class="control-label sign_left_small">装修补偿款</label>
															<input class=" input_type yuanwid" value="<fmt:formatNumber value='${editCaseDetailVO.compensateAmount}' type='number' pattern='#0.00' />" 
															id="compensateAmount" name="compensateAmount" onkeyup="checkNum(this)"><span class="date_icon">万元</span>
														</div>
														<div class="form_content mt3">
															<label
																class="control-label sign_left_small select_style mend_select">
																时间 </label>
															<div
																class="input-group sign-right dataleft input-daterange pull-left"
																data-date-format="yyyy-mm-dd">
																<input  class="input_type yuanwid datatime"
																	value="<fmt:formatDate  value='${editCaseDetailVO.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />" 
															        id="compensatePayTime" name="compensatePayTime" onfocus="this.blur()">
															</div>
														</div>
														<div class="form_content">
															<label class="control-label sign_left_small"> 方式
															</label>
														<aist:dict clazz="select_control data_style" id="compensatePayType" name="compensatePayType" display="select" defaultvalue="${editCaseDetailVO.compensatePayType}" dictType="30015" />															
														</div>
													</div>
												</div>
												<div id="guestUpDiv" class="clear add-member mb20" onclick="addDateDivUp()">添加上家</div>
											  <div id="guestDownDiv" class="clear add-member mb20" onclick="addDateDivDown();">添加下家</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small"> 产证面积
														</label> <input class=" input_type yuanwid" id="square" name="square" onkeyup="checkNum(this)"
												                  value="<fmt:formatNumber value='${editCaseDetailVO.square}' type='number' pattern='#0.00' />">  <span class="date_icon">平方米</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 产证地址
														</label> <input class="input_type mendwidth" id="propertyAddr" name="propertyAddr" value="${editCaseDetailVO.propertyAddr}">
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small"> 竣工年份
														</label>
														<select class=" select_control yuanwid" name="finishYear" id="finishYear"></select>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 所在楼层
														</label> <input class=" input_type data_style" placeholder="所在楼层"  onkeyup="checkNum2(this)"
												      id="locateFloor" name="locateFloor" value="${editCaseDetailVO.locateFloor}">
														<span class="date_icon">层</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">总层高</label> <input
															class=" input_type data_style" placeholder="总层高" onkeyup="checkNum2(this)"
												            id="totalFloor" name="totalFloor" value="${editCaseDetailVO.totalFloor}"> <span
															class="date_icon">层</span>
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small"> 房屋类型
														</label>
														<aist:dict clazz="select_control yuanwid" id="propertyType" name="propertyType" display="select" defaultvalue="${editCaseDetailVO.propertyType}" dictType="30014" />
														
													</div>

												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															实际签约时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="realConTime" readonly="readonly" disabled="disabled"
												               name="realConTime" value="<fmt:formatDate  value='${editCaseDetailVO.realConTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 成交价
														</label> <input class=" input_type yuanwid" id="conPrice" name="conPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.conPrice}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">房屋户口</label>
														<aist:dict clazz="select_control data_style" id="isConCert" name="isConCert" display="select" defaultvalue="${editCaseDetailVO.isHukou}" dictType="hukou_remain" />
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small"> 合同价
														</label> <input class=" input_type yuanwid" id="realPrice" name="realPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.realPrice}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 合同公证
														</label>
													<aist:dict clazz="select_control data_style" id="isHukou" name="isHukou" display="select" defaultvalue="${editCaseDetailVO.isConCert}" dictType="gongzheng_need" />
													</div>

												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															还款时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input class="input_type yuanwid datatime"
																 id="loanCloseCode" name="loanCloseCode" readonly="readonly" disabled="disabled"
												                 value="<fmt:formatDate  value='${editCaseDetailVO.loanCloseCode}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 抵押金额
														</label> <input class=" input_type yuanwid" id="uncloseMoney" name="uncloseMoney" onkeyup="checkNum(this)" <c:if test="${empty editCaseDetailVO.lcid}">readOnly</c:if>
													value="<fmt:formatNumber value='${editCaseDetailVO.uncloseMoney}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 金额来源
														</label>
														<aist:dict clazz="select_control data_style" id="closeType" name="closeType" display="select" defaultvalue="${editCaseDetailVO.closeType}" dictType="LoanCloseMethod" />
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label
															class="control-label sign_left_small select_style mend_select">
															实际过户时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="realHtTime" readonly="readonly" disabled="disabled"
												                name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.realHtTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 房产税
														</label> <input class=" input_type yuanwid" id="houseHodingTax" name="houseHodingTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.houseHodingTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															个人所得税 </label> <input class=" input_type yuanwid" pid="personalIncomeTax" name="personalIncomeTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.personalIncomeTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">
															上家营业税 </label> <input class=" input_type yuanwid"  id="businessTax" name="businessTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.businessTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small"> 下家契税
														</label> <input class=" input_type yuanwid" id="contractTax" name="contractTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.contractTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">
															土地增值税 </label> <input class=" input_type yuanwid" id="landIncrementTax" name="landIncrementTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.landIncrementTax}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															实际审税时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="taxTime" readonly="readonly" disabled="disabled"
												                name="taxTime" value="<fmt:formatDate  value='${editCaseDetailVO.taxTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">是否唯一住房</label>
														<aist:dict clazz="select_control data_style" id="isUniqueHome" name="isUniqueHome" display="select" defaultvalue="${editCaseDetailVO.isUniqueHome}" dictType="is_unique_home" />
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">购房年数</label>
														<aist:dict clazz="select_control data_style" id="holdYear" name="holdYear" display="select" defaultvalue="${editCaseDetailVO.holdYear}" dictType="hold_year" />
													</div>
												</div>
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															查限购时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input class="input_type yuanwid datatime"
																id="realPlsTime" readonly="readonly" disabled="disabled"
												name="realPlsTime" value="<fmt:formatDate  value='${editCaseDetailVO.realPlsTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															领证时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input class="input_type yuanwid datatime" disabled="disabled"
																id="realPropertyGetTime" name="realPropertyGetTime" readonly="readonly"
												               value="<fmt:formatDate  value='${editCaseDetailVO.realPropertyGetTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															结案时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input class="input_type yuanwid datatime"
																id="approveTime" readonly="readonly" disabled="disabled"
												name="approveTime" value="<fmt:formatDate  value='${editCaseDetailVO.approveTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane">
										<div class="form_list">
											<div class="marinfo">
												<div class="line">
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															签约时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input class="input_type yuanwid datatime"
																id="signDate" name="signDate" readonly="readonly"  disabled="disabled"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.signDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															批贷时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="apprDate" name="apprDate" readonly="readonly"  disabled="disabled"
											                    value="<fmt:formatDate  value='${editCaseDetailVO.apprDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">贷款类型</label>
														<aist:dict clazz="select_control yuanwid" id="mortType" name="mortType" display="select" defaultvalue="${editCaseDetailVO.mortType}" dictType="30016" />
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">贷款金额</label>
														<input class=" input_type yuanwid" id="mortTotalAmount"  name="mortTotalAmount" onkeyup="checkNum(this)" 
												value="<fmt:formatNumber value='${ editCaseDetailVO.mortTotalAmount}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">公积金贷款金额</label>
														<input class=" input_type yuanwid" id="prfAmount" name="prfAmount" onkeyup="checkNum(this)" 
												value="<fmt:formatNumber value='${ editCaseDetailVO.prfAmount}' type='number' pattern='#0.00' />">  <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">公积金贷款年限</label>
														<input class=" input_type yuanwid" id="prfYear" name="prfYear" value="${ editCaseDetailVO.prfYear}" onkeyup="checkNum2(this)"> <span class="date_icon">年</span>
													</div>

												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">商贷金额</label>
														<input class=" input_type yuanwid" id="comAmount" name="comAmount" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.comAmount}' type='number' pattern='#0.00' />"> <span class="date_icon">万元</span>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">商贷年限</label>
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
															他证送达时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																readonly="readonly" disabled="disabled" id="tazhengArrDate" name="tazhengArrDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.tazhengArrDate}' type='both' pattern='yyyy-MM-dd'/>">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">是否自办</label>
														<select class="select_control data_style" id="isDelegateYucui" disabled="disabled" name="isDelegateYucui"> 
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
														<label class="control-label sign_left_small">主贷人</label> <select disabled="disabled"
														 class="select_control yuanwid"
															name="custCode" id="custCode" >
											        </select>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">主贷人单位</label>
														<input class=" input_type yuanwid" style="width: 491px;"
															id="custCompany" name="custCompany" value="${ editCaseDetailVO.custCompany}">
													</div>
													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															申请时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																id="prfApplyDate" name="prfApplyDate" readonly="readonly" disabled="disabled" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.prfApplyDate}' type='both' pattern='yyyy-MM-dd'/>">
														</div>
													</div>

													<div class="form_content mt3">
														<label
															class="control-label sign_left_small select_style mend_select">
															放款时间 </label>
														<div
															class="input-group sign-right dataleft input-daterange pull-left"
															data-date-format="yyyy-mm-dd">
															<input  class="input_type yuanwid datatime"
																value="<fmt:formatDate  value='${editCaseDetailVO.lendDate }'  type='both' pattern='yyyy-MM-dd' />" 
													          id="lendDate" name="lendDate" readonly="readonly" disabled="disabled" onfocus="this.blur()">
														</div>
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">房贷套数</label>
														<input class=" input_type yuanwid" id="houseNum" name="houseNum" value="${editCaseDetailVO.houseNum}" onkeyup="checkNum2(this)">
													</div>
												</div>

												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">信贷员</label> <input
															class=" input_type yuanwid" id="loanerName" readonly="readonly"  name="loanerName" value="${ editCaseDetailVO.loanerName}">
													</div>
													<div class="form_content">
														<label class="control-label sign_left_small">信贷员电话</label>
														<input class=" input_type yuanwid" readonly="readonly"  id="loanerPhone" name="loanerPhone" value="${ editCaseDetailVO.loanerPhone}">
													</div>
												</div>
												<div class="line">
													<div class="form_content">
														<label class="control-label sign_left_small">评估公司</label>
														<input class=" input_type yuanwid" readonly="readonly"  id="finOrgName" name="finOrgName" value="${editCaseDetailVO.finOrgName}">
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
				</form>
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
			deleteAndModify();
			
			var flag= true;
			var _comDiscount = $('#comDiscount').val();
			var _mortType = $('#mortType').find(':selected').val();
			
			if((_mortType=='30016001'&&_comDiscount=='')||(_mortType=='30016002'&&_comDiscount=='')){
				window.wxc.alert('纯商贷和组合贷款必须填写利率折扣, 不能为空');
				$('#comDiscount').focus();
				flag = false;
			}
			
			if((_mortType=='30016001'&&_comDiscount!='')||(_mortType=='30016002'&&_comDiscount!='')){
				if(isNaN(_comDiscount)){
					window.wxc.alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
		            $('#comDiscount').focus();
		            flag = false;
		        }else if(_comDiscount>1.5 || _comDiscount<0.5){
		        	window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		    		$('#comDiscount').focus();
		    		flag = false;
		    	}else if(_comDiscount<=1.5 || _comDiscount>=0.5){
	        		var reg =/^[01]{1}\.{1}\d{3,}$/;
	        		if(reg.test(_comDiscount)){
	        			window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
	        			$('#comDiscount').focus();
	        			flag = false;
	        		}		    		
		    	}
			} 
			
			if(flag){
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
			
		}
		
		var divIndexDown = 1;
		function addDateDivDown() {
			
			var txt = '<div id="dateDivD_' + divIndexDown + '" class="line">';
			txt += "<input type='hidden' name='pkidDown' value='0'/>";
			txt += "<div class='form_content'>";
			txt += "<label class='control-label sign_left_small'>下家姓名</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value=''></div>";
			txt += "<div class=\"form_content\">";
			txt += "<label class=\"control-label sign_left_small\">下家电话</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value=''></div>";
			txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivD_'
					+ divIndexDown + '\');"><font>删除</font></a></span>';
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
			txt += "<label class='control-label sign_left_small'>上家姓名</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value=''></div>";
			txt += "<div class=\"form_content\">";
			txt += "<label class=\"control-label sign_left_small\">上家电话</label>";
			txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value=''></div>";
			txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivU_'
					+ divIndexUp + '\');"><font>删除</font></a></span>';
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
							txt += "<label class='control-label sign_left_small'>下家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value='"+value.guestName+"'></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">下家电话</label>";
							txt += "<input type=\"text\"  style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value='"+value.guestPhone+"'></div>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'dateDivD_'+divIndexDown+'\','
									+ value.pkid + ');"><font>删除</font></a></span>';
							txt += '</div>';
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="line">';
							txt += "<input type='hidden' name='pkidUp' value='"+value.pkid+"'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>上家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value='"+value.guestName+"'></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">上家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value='"+value.guestPhone+"'></div>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'dateDivU_'+divIndexUp+'\','
									+ value.pkid + ');"><font>删除</font></a></span>';
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
							txt += "<label class='control-label sign_left_small'>下家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameDown\" value=''></div>";
							txt += "<div class='form_content'>";
							txt += "<label class=\"control-label sign_left_small\">下家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneDown\" value=''></div>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivD_'
									+ divIndexDown + '\');"><font>删除</font></a></span>';
							txt += '</div>';
							// alert(txt);
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="line">';
							txt += "<input type='hidden' name='pkidUp' value='0'/>";
							txt += "<div class='form_content'>";
							txt += "<label class='control-label sign_left_small'>上家姓名</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestNameUp\" value=''></div>";
							txt += "<div class=\"form_content\">";
							txt += "<label class=\"control-label sign_left_small\">上家电话</label>";
							txt += "<input type=\"text\" style='margin-left:4px;' class=\"input_type yuanwid\" name=\"guestPhoneUp\" value=''></div>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivU_'
									+ divIndexUp + '\');"><font>删除</font></a></span>';
							txt += '</div>';
							// alert(txt);
							$("#guestUpDiv").before(txt);
							divIndexUp++;
						}
					}
				},
				error : function(errors) {
					window.wxc.error("上下家加载失败！");
				}
			});
		}
		function show(fileId) {
			window.open(appCtx['shcl-image-web'] + "/image/"+fileId+"/_f.jpg");
		}
	</script> </content>
</body>


</html>