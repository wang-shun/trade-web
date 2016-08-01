<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<!-- 备件相关结束 -->
<!-- jdGrid相关 -->
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- datepikcer -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />

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
	<div class="">
		<div class=" wrapper border-bottom white-bg page-heading">
			<div class="row">
			<div class="col-lg-10">
				<h2>结案归档</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
			</div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
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
					
					<div class="panel blank-panel">

                        <div class="panel-heading">
                            <div class="panel-options">

                                <ul class="nav nav-tabs">
                                    <li class="active">
                                    	<a data-toggle="tab" href="#tab-1" style="height:25px;padding-top: 0px;">
                                    	<h4>交易信息</h4></a>
                                    </li>
                                    <li class="">
                                    	<a data-toggle="tab" href="#tab-2" style="height:25px;padding-top: 0px;">
                                    	<h4>贷款信息</h4></a>
                                    </li>
                                    <li class="">
                                    	<a data-toggle="tab" href="#tab-3" style="height:25px;padding-top: 0px;">
                                    	<h4>附件信息</h4></a>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body">

                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="form-group">
										<label class="col-sm-2 control-label">业务单创建时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="createTime" name="realConTime" readonly="readonly"
											value="<fmt:formatDate  value='${editCaseDetailVO.createTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
										<label class="col-sm-2 control-label">分单时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="resDate" name="resDate" readonly="readonly"
											value="<fmt:formatDate  value='${editCaseDetailVO.resDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">首付付款</label>
										<div class="col-sm-20 input-group" style="margin-left: 197px;">
											<div class="row">
												<div class="col-md-3">
													<div class="input-group ">
														<input type="text" value="<fmt:formatNumber value='${ editCaseDetailVO.initAmount}' type='number' pattern='#0.00' />" 
															class="form-control" id="initAmount" name="initAmount" onkeyup="checkNum(this)">
															<span class="input-group-addon">万</span>
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-group" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
															<i class="fa fa-calendar"></i>
														</span>
														<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															class="form-control" id="initPayTime" name="initPayTime" onfocus="this.blur()">
													</div>
													<%-- <span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control m-b" id="initPayTime" name="initPayTime" onfocus="this.blur()"> --%>
													</div>
												</div>
												<div class="col-md-3">
													<div class="input-group ">
														<span class="input-group-addon">方式</span>
														<aist:dict clazz="form-control m-b" id="initPayType" name="initPayType" display="select" defaultvalue="${editCaseDetailVO.initPayType}" dictType="30015" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">二期付款</label>
										<div class="col-sm-20 input-group" style="margin-left: 197px;">
											<div class="row">
												<div class="col-md-3">
													<div class="input-group ">
														<input type="text" value="<fmt:formatNumber value='${editCaseDetailVO.secAmount }' type='number' pattern='#0.00' />" 
															class="form-control" id="secAmount" name="secAmount" onkeyup="checkNum(this)">
														<span class="input-group-addon">万</span>
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-group" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
															<i class="fa fa-calendar"></i>
														</span>
														<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															class="form-control" id="secPayTime" name="secPayTime" onfocus="this.blur()">
													</div>
													<%-- <span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="secPayTime" name="secPayTime" onfocus="this.blur()"> --%>
													</div>
												</div>
												<div class="col-md-3">
													<div class="input-group ">
														<span class="input-group-addon">方式</span> 
														<aist:dict clazz="form-control m-b" id="secPayType" name="secPayType" display="select" defaultvalue="${editCaseDetailVO.secPayType}" dictType="30015" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">尾款付款</label>
										<div class="col-sm-20 input-group" style="margin-left: 197px;">
											<div class="row">
												<div class="col-md-3">
													<div class="input-group ">
														<input type="text" value="<fmt:formatNumber value='${editCaseDetailVO.lastAmount}' type='number' pattern='#0.00' />" 
															class="form-control" id="lastAmount" name="lastAmount" onkeyup="checkNum(this)">
														<span class="input-group-addon">万</span>
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-group" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
															<i class="fa fa-calendar"></i>
														</span><input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
															class="form-control" id="lastPayTime" name="lastPayTime" onfocus="this.blur()">
													</div>
													<%-- <span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="lastPayTime" name="lastPayTime" onfocus="this.blur()"> --%>
													</div>
												</div>
												<div class="col-md-3">
													<div class="input-group ">
														<span class="input-group-addon">方式</span> 
														<aist:dict clazz="form-control m-b" id="lastPayType" name="lastPayType" display="select" defaultvalue="${editCaseDetailVO.lastPayType}" dictType="30015" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">装修补偿款</label>
				
										<div class="col-sm-20 input-group" style="margin-left: 197px;">
											<div class="row">
												<div class="col-md-3">
													<div class="input-group ">
														<input type="text" value="<fmt:formatNumber value='${editCaseDetailVO.compensateAmount}' type='number' pattern='#0.00' />" 
															class="form-control" id="compensateAmount" name="compensateAmount" onkeyup="checkNum(this)">
														<span class="input-group-addon">万</span>
													</div>
												</div>
												<div class="col-md-4">
													<div class="input-group" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
															<i class="fa fa-calendar"></i>
														</span>
														<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />" 
															class="form-control" id="compensatePayTime" name="compensatePayTime" onfocus="this.blur()">
													</div>
													<%-- <span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="compensatePayTime" name="compensatePayTime" onfocus="this.blur()"> --%>
													</div>
												</div>
												<div class="col-md-3">
													<div class="input-group ">
														<span class="input-group-addon">方式</span> 
														<aist:dict clazz="form-control m-b" id="compensatePayType" name="compensatePayType" display="select" defaultvalue="${editCaseDetailVO.compensatePayType}" dictType="30015" />
													</div>
												</div>
											</div>
										</div>
									</div>
				
									<div id="guestDownDiv">
									</div>
									<div id="addLine" class="form-group" style="margin-left: 100px;">
										<a href="javascript:addDateDivDown();" class="btn"><font>添加下家</font></a>
									</div>
									<div id="guestUpDiv">
									</div>
									<div id="addLine" class="form-group" style="margin-left: 100px;">
										<a href="javascript:addDateDivUp();" class="btn"><font>添加上家</font></a>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">产证地址</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="propertyAddr" name="propertyAddr" value="${editCaseDetailVO.propertyAddr}">
										</div>
										<label class="col-sm-2 control-label">产证面积</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="square" name="square" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${editCaseDetailVO.square}' type='number' pattern='#0.00' />">
										</div>
									</div>
				
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label">层高</label>
				
										<div class="col-sm-20">
											<div class="row">
												<div class="col-md-3">
													<input type="text" placeholder="所在楼层" class="form-control" onkeyup="checkNum2(this)"
														id="locateFloor" name="locateFloor" value="${editCaseDetailVO.locateFloor}">
												</div>
												<div class="col-md-3">
													<input type="text" placeholder="总层高" class="form-control" onkeyup="checkNum2(this)"
														id="totalFloor" name="totalFloor" value="${editCaseDetailVO.totalFloor}">
												</div>
											</div>
										</div>
									</div> --%>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">层高</label>
										<div class="col-sm-4">
											<input type="text" placeholder="所在楼层" class="form-control" onkeyup="checkNum2(this)"
												id="locateFloor" name="locateFloor" value="${editCaseDetailVO.locateFloor}">
										</div>
										<label class="col-sm-2 control-label">总层高</label>
										<div class="col-sm-4">
											<input type="text" placeholder="总层高" class="form-control" onkeyup="checkNum2(this)"
												id="totalFloor" name="totalFloor" value="${editCaseDetailVO.totalFloor}">
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">竣工年份</label>
										<div class="col-sm-4">
											<select class="form-control m-b" name="finishYear" id="finishYear">
											</select>
										</div>
										<label class="col-sm-2 control-label">房屋类型</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="propertyType" name="propertyType" display="select" defaultvalue="${editCaseDetailVO.propertyType}" dictType="30014" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">实际签约时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="realConTime" readonly="readonly"
												name="realConTime" value="<fmt:formatDate  value='${editCaseDetailVO.realConTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">合同公证</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="isHukou" name="isHukou" display="select" defaultvalue="${editCaseDetailVO.isConCert}" dictType="gongzheng_need" />
										</div>
										<label class="col-sm-2 control-label">房屋户口</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="isConCert" name="isConCert" display="select" defaultvalue="${editCaseDetailVO.isHukou}" dictType="hukou_remain" />
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">成交价</label>
										<div class="col-sm-4">
											<div class="input-group ">
												<input type="text" class="form-control" id="conPrice" name="conPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.conPrice}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">合同价</label>
										<div class="col-sm-4">
											<div class="input-group ">
												<input type="text" class="form-control" id="realPrice" name="realPrice" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${editCaseDetailVO.realPrice}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">还款时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="loanCloseCode" name="loanCloseCode" readonly="readonly"
												value="<fmt:formatDate  value='${editCaseDetailVO.loanCloseCode}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">抵押金额</label>
										<div class="col-sm-4">
											<div class="input-group ">
												<input type="text" class="form-control" id="uncloseMoney" name="uncloseMoney" onkeyup="checkNum(this)" <c:if test="${empty editCaseDetailVO.lcid}">readOnly</c:if>
													value="<fmt:formatNumber value='${editCaseDetailVO.uncloseMoney}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">资金来源</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="closeType" name="closeType" display="select" defaultvalue="${editCaseDetailVO.closeType}" dictType="LoanCloseMethod" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">实际过户时间</label>
										<div class="col-sm-10">
											<input type="text"  class="form-control" id="realHtTime" readonly="readonly"
												name="realHtTime" value="<fmt:formatDate  value='${editCaseDetailVO.realHtTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">房产税</label>
										<div class="col-sm-4">
											<div class="input-group">
												<input type="text" class="form-control" id="houseHodingTax" name="houseHodingTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.houseHodingTax}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">个人所得税</label>
										<div class="col-sm-4">
											<div class="input-group">
												<input type="text" class="form-control" id="personalIncomeTax" name="personalIncomeTax" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.personalIncomeTax}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">上家营业税</label>
										<div class="col-sm-4">
											<div class="input-group">
											<input type="text" class="form-control" id="businessTax" name="businessTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.businessTax}' type='number' pattern='#0.00' />">
											<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">下家契税</label>
										<div class="col-sm-4">
											<div class="input-group">
											<input type="text" class="form-control" id="contractTax" name="contractTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.contractTax}' type='number' pattern='#0.00' />">
											<span class="input-group-addon">万</span>
											</div>
										</div> 
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">土地增值税</label>
										<div class="col-sm-4">
											<div class="input-group">
											<input type="text" class="form-control" id="landIncrementTax" name="landIncrementTax" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.landIncrementTax}' type='number' pattern='#0.00' />">
											<span class="input-group-addon">万</span>
										</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">实际审税时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="taxTime" readonly="readonly"
												name="taxTime" value="<fmt:formatDate  value='${editCaseDetailVO.taxTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
				
				
									<div class="form-group">
										<label class="col-sm-2 control-label">是否唯一住房</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="isUniqueHome" name="isUniqueHome" display="select" defaultvalue="${editCaseDetailVO.isUniqueHome}" dictType="is_unique_home" />
										</div>
										<label class="col-sm-2 control-label">购房年数</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="holdYear" name="holdYear" display="select" defaultvalue="${editCaseDetailVO.holdYear}" dictType="hold_year" />
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">查限购时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="realPlsTime" readonly="readonly"
												name="realPlsTime" value="<fmt:formatDate  value='${editCaseDetailVO.realPlsTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">领证时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="realPropertyGetTime" name="realPropertyGetTime" readonly="readonly"
												value="<fmt:formatDate  value='${editCaseDetailVO.realPropertyGetTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">结案时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="approveTime" readonly="readonly"
												name="approveTime" value="<fmt:formatDate  value='${editCaseDetailVO.approveTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
                                </div>

                                <div id="tab-2" class="tab-pane">
                                    <div class="form-group">
										<label class="col-sm-2 control-label">签约时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="signDate" name="signDate" readonly="readonly"
											value="<fmt:formatDate  value='${editCaseDetailVO.signDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
										<label class="col-sm-2 control-label">批贷时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="apprDate" name="apprDate" readonly="readonly"
											value="<fmt:formatDate  value='${editCaseDetailVO.apprDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款类型</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="mortType" name="mortType" display="select" defaultvalue="${editCaseDetailVO.mortType}" dictType="30016" />
										</div>
										<label class="col-sm-2 control-label">贷款金额</label>
										<div class="col-sm-4">
											<div class="input-group">
											<input type="text" class="form-control" id="mortTotalAmount" name="mortTotalAmount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.mortTotalAmount}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
												</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷金额</label>
										<div class="col-sm-4">
											<div class="input-group">
												<input type="text" class="form-control" id="comAmount" name="comAmount" onkeyup="checkNum(this)"
													value="<fmt:formatNumber value='${ editCaseDetailVO.comAmount}' type='number' pattern='#0.00' />">
													<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">商贷年限</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="comYear" name="comYear" value="${ editCaseDetailVO.comYear}">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">公积金贷款金额</label>
										<div class="col-sm-4">
											<div class="input-group">
											<input type="text" class="form-control" id="prfAmount" name="prfAmount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.prfAmount}' type='number' pattern='#0.00' />">
												<span class="input-group-addon">万</span>
											</div>
										</div>
										<label class="col-sm-2 control-label">公积金贷款年限</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="prfYear" name="prfYear" value="${ editCaseDetailVO.prfYear}" onkeyup="checkNum2(this)">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷利率折扣</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="comDiscount" name="comDiscount" onkeyup="autoCompleteComDiscount(this)" placeholder="0.50~1.50之间"
											value="<fmt:formatNumber value='${editCaseDetailVO.comDiscount}' type='number' pattern='#0.00' />">
										</div>
										<label class="col-sm-2 control-label">是否自办</label>
										<div class="col-sm-4">
											<select class="form-control m-b" id="isDelegateYucui" name="isDelegateYucui"> 
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
									
									<div class="form-group">
										<label class="col-sm-2 control-label">放款方式</label>
										<div class="col-sm-4">
											<aist:dict clazz="form-control m-b" id="lendWay" name="lendWay" display="select" defaultvalue="${editCaseDetailVO.lendWay}" dictType="30017" />
										</div>
										<label class="col-sm-2 control-label">他证送达时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" readonly="readonly" id="tazhengArrDate" name="tazhengArrDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.tazhengArrDate}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人</label>
										<div class="col-sm-4">
											<select class="form-control m-b" name="custCode" id="custCode" >
											</select>
											<%-- <input type="text" class="form-control" id="custName" name="custName" value="${ editCaseDetailVO.custName}"> --%>
										</div>
										<label class="col-sm-2 control-label">申请时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="prfApplyDate" name="prfApplyDate" readonly="readonly" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.prfApplyDate}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="loanerName" readonly="readonly" name="loanerName" value="${ editCaseDetailVO.loanerName}">
										</div>
										<label class="col-sm-2 control-label">放款时间</label>
										<div class="col-sm-4" id="data_1">
											<%-- <input type="text" class="form-control" id="lendDate" name="lendDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.lendDate}' type='both' pattern='yyyy-MM-dd'/>">
											 --%>
											<div class="">
											<!--input-group date 	<span class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</span> -->
												<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.lendDate }' type='both' pattern='yyyy-MM-dd' />" 
													class="form-control" id="lendDate" name="lendDate" readonly="readonly" onfocus="this.blur()">
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人单位</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="custCompany" name="custCompany" value="${ editCaseDetailVO.custCompany}">
										</div>
										<label class="col-sm-2 control-label">房贷套数</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="houseNum" name="houseNum" value="${editCaseDetailVO.houseNum}" onkeyup="checkNum2(this)">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员电话</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" readonly="readonly" id="loanerPhone" name="loanerPhone" value="${ editCaseDetailVO.loanerPhone}">
										</div>
										<label class="col-sm-2 control-label">评估公司</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" readonly="readonly" id="finOrgName" name="finOrgName" value="${editCaseDetailVO.finOrgName}">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">承办银行</label>
										<div class="col-sm-4">
											<select class="form-control m-b chosen-select" name="bank" id="bank" >
											</select>
										</div>
										
										<label class="col-sm-2 control-label">支行名称</label>
										<div class="col-sm-4">
											<select class="form-control m-b chosen-select" id="lastLoanBank" >
											</select>
											<input name="lastLoanBank" type="hidden" value="${editCaseDetailVO.lastLoanBank }"/>							</div>
									</div>

                                </div>
                                <div id="tab-3" class="tab-pane">
                                    <div class="ibox-title">
                                   <%--  <input type="hidden" name = "accesoryCount" id="accesoryCount" value="${fn:length(accesoryList)} "/>
										<c:choose>  
									    <c:when test="${accesoryList!=null}">  
										<h5>上传备件</h5>
										<div class="ibox-content" style="height:300px; overflow-y:scroll;">
										<h5>${accesoryList[0].accessoryName }</h5>
										<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
								               <div class="" id="fileupload_div_pic"> 
								               <form id="fileupload"
												action="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
												method="POST" enctype="multipart/form-data">
												<noscript>
													<input type="hidden" name="redirect" value="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload">
													<input type="hidden" id="preFileCode" name="preFileCode" value="${accesory.accessoryCode }">
												</noscript>
												<c:if test="${status.index != 0}">
													<h5 align="left"><br>${accesory.accessoryName }</h5>
												</c:if>
												<div class="row-fluid fileupload-buttonbar">
													<div class="" style="height: auto">
														<div role="presentation" class="table table-striped "
															style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
															<div id="picContainer${accesory.pkid }" class="files" data-toggle="modal-gallery"
																data-target="#modal-gallery"></div>
																<span class=" fileinput-button " style="margin-left:10px!important;width:80px;">
																<div id="chandiaotuBtn" class=""
																	style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
																	<i class="fa fa-plus"></i>
																</div> 
																<input id="picFileupload${accesory.pkid }" type="file" name="files[]" multiple
																data-url="<aist:appCtx appName='aist-filesvr-web'/>/servlet/jqueryFileUpload"
																data-sequential-uploads="true">
															</span>
														</div>
													</div>
												</div>  
												</form>
											</div>
											
										<div class="row-fluid">
										<div class="">
											<script id="templateUpload${accesory.pkid}" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
									<!--图片缩图  -->
							        <div class="preview"><span class="fade"></span></div>
									<!--  错误信息 -->
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									<!-- 单个对应的按钮  -->
							            <div class="start span1" style="display: none">
										{% if (!o.options.autoUpload) { %}
							                <button class="btn">
							                    <i class="icon-upload icon-white"></i>
							                    <span>上传</span>
							                </button>
							            {% } %}
										</div>
							        {% } else { %}
							            <div class="span1" colspan="2"></div>
							        {% } %}
							        <div class="cancel" style="margin-top:-172px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
						<script id="templateDownload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" style="width:80px;height:80px;margin-left:10px;">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-130px;">
							           <button data-url="<aist:appCtx appName='aist-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
												</div> 
											</div>
											</c:forEach>
											
											<div class="row-fluid" style="display:none;">
												<div class="span4">
													<div class="control-group">
														<a class="btn blue start" id="startUpload" style="height: 30px; width: 50px"> 
															<i class="icon-upload icon-white"></i> <span>上传</span>
														</a>
													</div>
												</div>
											</div>
									    </c:when>  
									    <c:otherwise> 
										<h5>上传备件<br>无需上传备件</h5>
									    </c:otherwise>  
										</c:choose>  --%>
										
										<div id="imgShow" class="lightBoxGallery">
											<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
												<div class="row ibox-content">
													<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">${accesory.preFileName}</label>
													<div class="col-sm-10 lightBoxGallery" style="text-align:left">
														<a href="#" title="Hydrangeas.jpg" data-gallery="">
															<img src="<aist:appCtx appName='shcl-image-web'/>/image/${accesory.preFileAdress}/__f.jpg" style="padding-bottom: 5px;padding-top: 5px;width:100px;" class="viewer-toggle">
														</a>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
                                </div>
                            </div>

                        </div>

                    </div>
				</form>

			</div>
		</div>
		
		<!-- 案件备注信息 -->
		<div id="caseCommentList" class="add_form">
		</div>
		
		<div class="ibox-title">
			<h5>审批记录</h5>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
		</div>
		<div class="ibox-title">
			<a href="#" class="btn" onclick="save(false)">保存</a>
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
	</div>
	<content tag="local_script"> 
    <!-- Steps -->
    <script src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script>
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

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>


	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<script	src="${ctx}/js/trunk/task/caseCloseAttachment.js"></script> 
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/viewer/viewer.min.js"></script>
	<script>
		var isAccumulation=false;
		var loanReq ="${loanReq}";
		
		initView();
		
		function initView(){
			$('#imgShow').viewer();
		}
		
		function readOnlyF(){
			$("#mortType").attr("disabled","disabled");
			$("#isDelegateYucui").attr("disabled","disabled");
			$("#lastLoanBank").attr("disabled","disabled");
			$("#custCode").attr("disabled","disabled");
			$("#bank").attr("disabled","disabled");
		}
		$(document).ready(function() {
			isAccumulation=$('#mortType').val()=='30016003';
			if(isAccumulation){
				$('#comAmount').val('').attr("disabled","disabled");
				$('#comYear').val('').attr("disabled","disabled");
			}else if($('#mortType').val()=='30016001'){
				$('#prfAmount').val('').attr("disabled","disabled");
				$('#prfYear').val('').attr("disabled","disabled");
			}
			
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
		    				} else {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
		    				}
		    			}
						friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
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
						friend.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
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
					alert("获取主贷人失败");
				}
			});
		}
		
		/**提交数据*/
		function submit() {
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
				alert('纯商贷和组合贷款必须填写利率折扣, 不能为空');
				$('#comDiscount').focus();
				flag = false;
			}
			
			if((_mortType=='30016001'&&_comDiscount!='')||(_mortType=='30016002'&&_comDiscount!='')){
				if(isNaN(_comDiscount)){
		            alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
		            $('#comDiscount').focus();
		            flag = false;
		        }else if(_comDiscount>1.5 || _comDiscount<0.5){
		    		alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		    		$('#comDiscount').focus();
		    		flag = false;
		    	}else if(_comDiscount<=1.5 || _comDiscount>=0.5){
	        		var reg =/^[01]{1}\.{1}\d{3,}$/;
	        		if(reg.test(_comDiscount)){
	        			alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
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
								alert(data.message);
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
						alert("数据保存出错");
					}
				});
			}
			
		}
		
		var divIndexDown = 1;
		function addDateDivDown() {
			
			var txt = '<div id="dateDivD_' + divIndexDown + '" class="form-group">';
			txt += "<input type='hidden' name='pkidDown' value='0'/>";
			txt += "<label class='col-sm-2 control-label'>下家姓名</label>";
			txt += "<div class='col-sm-4'>";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value=''></div>";
			txt += "<label class=\"col-sm-1 control-label\">下家电话</label>";
			txt += "<div class=\"col-sm-4\">";
			txt += "<div class=\"input-group \">";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneDown\" value=''>";
			txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivD_'
					+ divIndexDown + '\');"><font>删除</font></a></span>';
			txt += '</div></div></div>';
			// alert(txt);
			$("#guestDownDiv").before(txt);
			divIndexDown++;
		}
		
		var divIndexUp = 1;
		function addDateDivUp() {
			
			var txt = '<div id="dateDivU_' + divIndexUp + '" class="form-group">';
			txt += "<input type='hidden' name='pkidUp' value='0'/>";
			txt += "<label class='col-sm-2 control-label'>上家姓名</label>";
			txt += "<div class='col-sm-4'>";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value=''></div>";
			txt += "<label class=\"col-sm-1 control-label\">上家电话</label>";
			txt += "<div class=\"col-sm-4\">";
			txt += "<div class=\"input-group \">";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneUp\" value=''>";
			txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivU_'
					+ divIndexUp + '\');"><font>删除</font></a></span>';
			txt += '</div></div></div>';
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
							var txt = '<div id="dateDivD_' + divIndexDown + '" class="form-group">';
							txt += "<input type='hidden' name='pkidDown' value='"+value.pkid+"'/>";
							txt += "<label class='col-sm-2 control-label'>下家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value='"+value.guestName+"'></div>";
							txt += "<label class=\"col-sm-1 control-label\">下家电话</label>";
							txt += "<div class=\"col-sm-4\">";
							txt += "<div class=\"input-group \">";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneDown\" value='"+value.guestPhone+"'>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'dateDivD_'+divIndexDown+'\','
									+ value.pkid + ');"><font>删除</font></a></span>';
							txt += '</div></div></div>';
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="form-group">';
							txt += "<input type='hidden' name='pkidUp' value='"+value.pkid+"'/>";
							txt += "<label class='col-sm-2 control-label'>上家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value='"+value.guestName+"'></div>";
							txt += "<label class=\"col-sm-1 control-label\">上家电话</label>";
							txt += "<div class=\"col-sm-4\">";
							txt += "<div class=\"input-group \">";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneUp\" value='"+value.guestPhone+"'>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'dateDivU_'+divIndexUp+'\','
									+ value.pkid + ');"><font>删除</font></a></span>';
							txt += '</div></div></div>';
							$("#guestUpDiv").before(txt);
							divIndexUp++;
						}
					});
					} else {
						if(transPosition=="30006002") {
							var txt = '<div id="dateDivD_' + divIndexDown + '" class="form-group">';
							txt += "<input type='hidden' name='pkidDown' value='0'/>";
							txt += "<label class='col-sm-2 control-label'>下家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value=''></div>";
							txt += "<label class=\"col-sm-1 control-label\">下家电话</label>";
							txt += "<div class=\"col-sm-4\">";
							txt += "<div class=\"input-group \">";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneDown\" value=''>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
									+ divIndexDown + ');"><font>删除</font></a></span>';
							txt += '</div></div></div>';
							// alert(txt);
							$("#guestDownDiv").before(txt);
							divIndexDown++;
						} else if(transPosition=="30006001") {
							var txt = '<div id="dateDivU_' + divIndexUp + '" class="form-group">';
							txt += "<input type='hidden' name='pkidUp' value='0'/>";
							txt += "<label class='col-sm-2 control-label'>上家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value=''></div>";
							txt += "<label class=\"col-sm-1 control-label\">上家电话</label>";
							txt += "<div class=\"col-sm-4\">";
							txt += "<div class=\"input-group \">";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestPhoneUp\" value=''>";
							txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
									+ divIndexUp + ');"><font>删除</font></a></span>';
							txt += '</div></div></div>';
							// alert(txt);
							$("#guestUpDiv").before(txt);
							divIndexUp++;
						}
					}
				},
				error : function(errors) {
					alert("上下家加载失败！");
				}
			});
		}
		function show(fileId) {
			window.open(appCtx['shcl-image-web'] + "/image/"+fileId+"/_f.jpg");
		}
	</script> 
	</content>
</body>


</html>