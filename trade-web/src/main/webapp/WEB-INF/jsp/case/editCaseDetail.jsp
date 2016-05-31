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
<script type="text/javascript">
	var ctx = "${ctx}";
	var finishYear = "${editCaseDetailVO.finishYear}";
	var caseCode = "${caseCode}";
	var finOrgCode = "${editCaseDetailVO.lastLoanBank}";
</script>

</head>
<body>

	<div class="row">
		<div class="ibox-title">
			<h5>业务单信息确认修改</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="editCaseDetailForm">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="guestPkidUp" name="guestPkidUp" value="${editCaseDetailVO.guestPkidUp }">
					<input type="hidden" id="guestPkidDown" name="guestPkidDown" value="${editCaseDetailVO.guestPkidDown }">
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
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body">

                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="form-group">
										<label class="col-sm-2 control-label">业务单创建时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="createTime" name="realConTime" 
											value="<fmt:formatDate  value='${editCaseDetailVO.createTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
										<label class="col-sm-2 control-label">分单日期</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="resDate" name="resDate" 
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
													<div class="input-group">
													<span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control m-b" id="initPayTime" name="initPayTime" onfocus="this.blur()">
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
													<div class="input-group">
													<span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="secPayTime" name="secPayTime" onfocus="this.blur()">
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
												<div class="col-md-4" id="data_1">
													<div class="input-group">
													<span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="lastPayTime" name="lastPayTime" onfocus="this.blur()">
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
												<div class="col-md-4" id="data_1">
													<div class="input-group">
													<span class="input-group-addon">日期</span>
													<input type="text" value="<fmt:formatDate  value='${editCaseDetailVO.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />" 
														class="form-control" id="compensatePayTime" name="compensatePayTime" onfocus="this.blur()">
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
				
									<div class="form-group">
										<label class="col-sm-2 control-label">下家姓名</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="guestNameUp" name="guestNameUp" value="${editCaseDetailVO.guestNameUp}">
										</div>
										<label class="col-sm-2 control-label">下家电话</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="guestPhoneUp" name="guestPhoneUp" value="${editCaseDetailVO.guestPhoneUp}">
										</div>
									</div>
									
									<div id="guestDelDiv">
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">上家姓名</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="guestNameDown" name="guestNameDown" value="${editCaseDetailVO.guestNameDown}">
										</div>
										<label class="col-sm-2 control-label">上家电话</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="guestPhoneDown" name="guestPhoneDown" value="${editCaseDetailVO.guestPhoneDown}">
										</div>
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
											<input type="text" class="form-control" id="realConTime"
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
											<input type="text" class="form-control" id="loanCloseCode" name="loanCloseCode" 
												value="<fmt:formatDate  value='${editCaseDetailVO.loanCloseCode}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
				
									<div class="form-group">
										<label class="col-sm-2 control-label">抵押金额</label>
										<div class="col-sm-4">
											<div class="input-group ">
												<input type="text" class="form-control" id="uncloseMoney" name="uncloseMoney" onkeyup="checkNum(this)"
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
											<input type="text" class="form-control" id="realHtTime"
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
											<input type="text" class="form-control" id="taxTime"
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
											<input type="text" class="form-control" id="realPlsTime"
												name="realPlsTime" value="<fmt:formatDate  value='${editCaseDetailVO.realPlsTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">领证时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="realPropertyGetTime" name="realPropertyGetTime" 
												value="<fmt:formatDate  value='${editCaseDetailVO.realPropertyGetTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">结案时间</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="approveTime"
												name="approveTime" value="<fmt:formatDate  value='${editCaseDetailVO.approveTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
									</div>
                                </div>

                                <div id="tab-2" class="tab-pane">
                                    <div class="form-group">
										<label class="col-sm-2 control-label">签约时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="signDate" name="signDate" 
											value="<fmt:formatDate  value='${editCaseDetailVO.signDate}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
										</div>
										<label class="col-sm-2 control-label">批贷时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="apprDate" name="apprDate" 
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
											<input type="text" class="form-control" id="mortTotalAmount" name="mortTotalAmount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.mortTotalAmount}' type='number' pattern='#0.00' />">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷金额</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="comAmount" name="comAmount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.comAmount}' type='number' pattern='#0.00' />">
										</div>
										<label class="col-sm-2 control-label">商贷年限</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="comYear" name="comYear" value="${ editCaseDetailVO.comYear}">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">公积金贷款金额</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="prfAmount" name="prfAmount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${ editCaseDetailVO.prfAmount}' type='number' pattern='#0.00' />">
										</div>
										<label class="col-sm-2 control-label">公积金贷款年限</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="prfYear" name="prfYear" value="${ editCaseDetailVO.prfYear}" onkeyup="checkNum2(this)">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">利率折扣</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="comDiscount" name="comDiscount" onkeyup="checkNum(this)"
												value="<fmt:formatNumber value='${editCaseDetailVO.comDiscount}' type='number' pattern='#0.00' />">
										</div>
										<label class="col-sm-2 control-label">是否自办</label>
										<div class="col-sm-4">
											<select class="form-control m-b" id="isDelegateYucui" name="isDelegateYucui"> 
											<c:choose>
												<c:when test="${editCaseDetailVO.isDelegateYucui == '1'}">
													<option value="1" selected="selected">是</option>
													<option value="0">否</option>
												</c:when>
												<c:otherwise>
													<option value="1">是</option>
													<option value="0" selected="selected">否</option>
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
											<input type="text" class="form-control" id="tazhengArrDate" name="tazhengArrDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.tazhengArrDate}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="custName" name="custName" value="${ editCaseDetailVO.custName}">
										</div>
										<label class="col-sm-2 control-label">申请时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="prfApplyDate" name="prfApplyDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.prfApplyDate}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="loanerName" name="loanerName" value="${ editCaseDetailVO.loanerName}">
										</div>
										<label class="col-sm-2 control-label">放款时间</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="lendDate" name="lendDate" onfocus="this.blur()"
												value="<fmt:formatDate  value='${editCaseDetailVO.lendDate}' type='both' pattern='yyyy-MM-dd'/>">
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
											<input type="text" class="form-control" id="loanerPhone" name="loanerPhone" value="${ editCaseDetailVO.loanerPhone}">
										</div>
										<label class="col-sm-2 control-label">评估公司</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="finOrgName" name="finOrgName" value="${editCaseDetailVO.finOrgName}">
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">承办银行</label>
										<div class="col-sm-4">
											<select class="form-control m-b" name="bank" id="bank">
											</select>
										</div>
										
										<label class="col-sm-2 control-label">支行名称</label>
										<div class="col-sm-4">
											<select class="form-control m-b" name="lastLoanBank" id="lastLoanBank">
											</select>
										</div>
									</div>

                                </div>
                            </div>

                        </div>

                    </div>
					
					
					
					
					
				</form>

			</div>
		</div>

		<div class="ibox-title">
			<a href="#" class="btn" onclick="save()">保存</a>
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
	</div>
	<content tag="local_script"> 
    <!-- Steps -->
    <script src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script>

	<script>
		$(document).ready(function() {
			 $("#wizard").steps();
			// showTask();
			/**年份选择初始化*/
			initSelectYear("finishYear", finishYear);
			
			$("#bank").change(function(){
				 var selectValue = $("#bank").val(); 
				 getBranchBankList(selectValue)
			 });
			
		 	getBankList(finOrgCode);
		});
		
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
		
		/**提交数据*/
		function submit() {
			save();
		}

		/**保存数据*/
		function save() {
			var jsonData = $("#editCaseDetailForm").serializeArray();
			
			var url = "${ctx}/case/edit/saveCaseDetai";
			
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
				success : function(data) {
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
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
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(data.bankCode == data.bankList[i].finOrgCode) {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"+data.bankList[i].finOrgName+"</option>");
		    					getBranchBankList(data.bankList[i].finOrgCode);
		    				} else {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
		    				}
		    				if(pcode == null || pcode == "" || pcode == undefined) {
		    					getBranchBankList(data.bankList[0].finOrgCode);
		    				}
		    			}
		    		}
		    	}
			  });
		}
		
		/*获取支行列表*/
		function getBranchBankList(pcode){
			var friend = $("#lastLoanBank");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryBankListByParentCode",
			    method:"post",
			    dataType:"json",
			    data:{faFinOrgCode:pcode},
		    	success:function(data){
		    		if(data != null){
		    			for(var i = 0;i<data.length;i++){
		    				if(finOrgCode == data[i].finOrgCode) {
		    					friend.append("<option value='"+data[i].finOrgCode+"' selected='selected'>"+data[i].finOrgName+"</option>");
		    				} else {
		    					friend.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
		    				}
		    			}
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
	</script> 
	</content>
</body>


</html>