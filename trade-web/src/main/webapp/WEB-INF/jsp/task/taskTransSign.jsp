<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- aist列表样式 -->
<link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
<!-- 备注信息 -->
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<script type="text/javascript">
var ctx = "${ctx}";
var taskitem = "${taskitem}";
var caseCode = "${caseCode}";
var source = "${source}";
var finishYear = "${transSign.finishYear}";
if("${idList}" != "") {
	var idList = eval("("+"${idList}"+")");
} else {
	var idList = [];
}
</script>
<style type='text/css'>
.divider{position:relative}
.divider label{position:absolute;left:0;top:-17px}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<div>
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-md-10">
				<h2>签约</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
		</div>
		<div class="ibox-title">
			<h5>完成提醒</h5>
			<a class="btn btn-primary pull-right" href="#" id="sendSMS">发送短信提醒</a>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
			
		</div>
		
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="post" class="form-horizontal"
					action="" id="transSignForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="propertyPkid" name="propertyPkid" value="${transSign.propertyPkid}">
					<input type="hidden" id="initPayPkid" name="initPayPkid" value="${transSign.initPayPkid}">
					<input type="hidden" id="secPayPkid" name="secPayPkid" value="${transSign.secPayPkid}">
					<input type="hidden" id="lastPayPkid" name="lastPayPkid" value="${transSign.lastPayPkid}">
					<input type="hidden" id="compensatePayPkid" name="compensatePayPkid" value="${transSign.compensatePayPkid}">
					<input type="hidden" id="signPkid" name="signPkid" value="${transSign.signPkid}">
					<input type="hidden" id="housePkid" name="housePkid" value="${houseTransfer.pkid}">
					
					
					<div class="row">
						<div class="col-xs-12 col-md-5">
							<div class="form-group"  name="isYouXiao">
								<label class="col-md-4 control-label"><font color="red">*</font>抵押情况</label>
								<div class="col-md-8">
									<select class="form-control" readOnlydata='1' name="isLoanClose" id="diya">
										<option value="">请选择</option>
										<option value="true" ${transSign.isLoanClose=="1"?'selected':''}>有抵押</option>
										<option value="false" ${transSign.isLoanClose=="0"?'selected':''}>无抵押</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-7">
							<div class="form-group" id="data_1" name="isYouXiao">
								<label class="col-md-2 control-label"><font color="red">*</font>是否需要查限购</label>
								<div class="col-md-4">
									<select class="form-control" readOnlydata='1' name="isPerchaseReserachNeed" id="chaxiangou">
										<option value="">请选择</option>
										<option value="true" ${transSign.isPerchaseReserachNeed=="1"?'selected':''}>是</option>
										<option value="false" ${transSign.isPerchaseReserachNeed=="0"?'selected':''}>否</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-md-5">
							<div class="form-group" id="data_1">
								<label class="col-md-4 control-label"><font color="red">*</font>实际签约时间</label>
								<div class="col-md-8">
									<div class="input-group date readOnly_date">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										<input type="text" class="form-control" id="realConTime"
											name="realConTime" value="<fmt:formatDate  value='${transSign.realConTime}' type='both' pattern='yyyy-MM-dd'/>" onfocus="this.blur()">
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-md-7">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label"><font color="red">*</font>价格</label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-5">
								<div class="input-group">
									<span class="input-group-addon">合同价</span>
									<input type="text" placeholder="合同价" value="<fmt:formatNumber value='${ transSign.conPrice/10000}' type='number' pattern='#0.00' />" 
									class="form-control" id="conPrice" name="conPrice" onkeyup="checkNum(this)">
									<span class="input-group-addon">万元</span>
								</div>
								</div>
								<div class="col-md-5">
								<div class="input-group">
									<span class="input-group-addon">成交价</span>
									<input type="text" placeholder="成交价" value="<fmt:formatNumber value='${ transSign.realPrice/10000}' type='number' pattern='#0.00' />" 
									class="form-control" id="realPrice" name="realPrice" onkeyup="checkNum(this)">
									<span class="input-group-addon">万元</span>
								</div>
								</div>
							</div>
						</div>
					</div>
						</div>
					</div>
					<div class="divider"><hr><label class="btn btn-warning">付款信息</label></div>
					<div class="form-group">
						<label class="col-sm-2 control-label" >首付付款</label>
						<div class="col-sm-10 input-group" style="margin-left: 197px;margin-top:-2px;">

							<div class="row"> 
								<input type="hidden" value="首付付款" id="initPayName"
									name="initPayName" >
								<div class="col-md-3">
									<div class="input-group " >
										<input type="text" value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />" 
											class="form-control" id="initAmount" name="initAmount" onkeyup="checkNum(this)">
											<span class="input-group-addon">万</span>
									</div>
								</div>
								<div class="col-md-4" id="data_1">
									<div class="input-group date">
										<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
											<i class="fa fa-calendar"></i>
										</span>
										<input type="text" value="<fmt:formatDate  value='${transSign.initPayTime }' type='both' pattern='yyyy-MM-dd' />" 
											class="form-control" id="initPayTime" name="initPayTime" onfocus="this.blur()">
									</div>
								</div>
								<div class="col-md-3">
									<div class="input-group ">
										<span class="input-group-addon">方式</span>
										<aist:dict clazz="form-control m-b" id="initPayType" name="initPayType" display="select" defaultvalue="${transSign.initPayType}" dictType="30015" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">二期付款</label>

						<div class="col-sm-10 input-group" style="margin-left: 197px;margin-top:-2px;">
							<div class="row">
							<input type="hidden" value="二期付款" id="secPayName" name="secPayName">
							<div class="col-md-3">
								<div class="input-group ">
									<input type="text" value="<fmt:formatNumber value='${transSign.secAmount }' type='number' pattern='#0.00' />" 
										class="form-control" id="secAmount" name="secAmount" onkeyup="checkNum(this)">
									<span class="input-group-addon">万</span>
								</div>
							</div>
							<div class="col-md-4" id="data_1">
								<div class="input-group date">
									<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
										<i class="fa fa-calendar"></i>
									</span>
									<input type="text" value="<fmt:formatDate  value='${transSign.secPayTime }' type='both' pattern='yyyy-MM-dd' />" 
										class="form-control" id="secPayTime" name="secPayTime" onfocus="this.blur()">
								</div>
							</div>
							<div class="col-md-3">
								<div class="input-group ">
									<span class="input-group-addon">方式</span> 
									<aist:dict clazz="form-control m-b" id="secPayType" name="secPayType" display="select" defaultvalue="${transSign.secPayType}" dictType="30015" />
								</div>
							</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">尾款付款</label>

						<div class="col-sm-10 input-group" style="margin-left: 197px;margin-top:-2px;">
							<div class="row">
							<input type="hidden" value="尾款付款" id="lastPayName" name="lastPayName">
							<div class="col-md-3">
								<div class="input-group ">
									<input type="text" value="<fmt:formatNumber value='${transSign.lastAmount}' type='number' pattern='#0.00' />" 
										class="form-control" id="lastAmount" name="lastAmount" onkeyup="checkNum(this)">
									<span class="input-group-addon">万</span>
								</div>
							</div>
							<div class="col-md-4" id="data_1">
								<div class="input-group date">
									<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
										<i class="fa fa-calendar"></i>
									</span><input type="text" value="<fmt:formatDate  value='${transSign.lastPayTime }' type='both' pattern='yyyy-MM-dd' />" 
										class="form-control" id="lastPayTime" name="lastPayTime" onfocus="this.blur()">
								</div>
							</div>
							<div class="col-md-3">
								<div class="input-group ">
									<span class="input-group-addon">方式</span> 
									<aist:dict clazz="form-control m-b" id="lastPayType" name="lastPayType" display="select" defaultvalue="${transSign.lastPayType}" dictType="30015" />
								</div>
							</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">装修补偿款</label>

						<div class="col-sm-10 input-group" style="margin-left: 197px;margin-top:-2px;">
							<div class="row">
							<input type="hidden" value="装修补偿款" id="compensatePayName" name="compensatePayName">
							<div class="col-md-3">
								<div class="input-group ">
									<input type="text" value="<fmt:formatNumber value='${transSign.compensateAmount}' type='number' pattern='#0.00' />" 
										class="form-control" id="compensateAmount" name="compensateAmount" onkeyup="checkNum(this)">
									<span class="input-group-addon">万</span>
								</div>
							</div>
							<div class="col-md-4" id="data_1">
								<div class="input-group date">
									<span class="input-group-addon">时间 &nbsp;&nbsp;&nbsp;
										<i class="fa fa-calendar"></i>
									</span>
									<input type="text" value="<fmt:formatDate  value='${transSign.compensatePayTime }' type='both' pattern='yyyy-MM-dd' />" 
										class="form-control" id="compensatePayTime" name="compensatePayTime" onfocus="this.blur()">
								</div>
							</div>
							<div class="col-md-3">
								<div class="input-group ">
									<span class="input-group-addon">方式</span> 
									<aist:dict clazz="form-control m-b" id="compensatePayType" name="compensatePayType" display="select" defaultvalue="${transSign.compensatePayType}" dictType="30015" />
								</div>
							</div>
							</div>
						</div>
					</div>
					<div class="divider"><hr><label class="btn btn-warning">上家信息</label></div>
					<div id="guestUpDiv">
					</div>
					<div id="addLine" class="col-md-offset-2">
						<a href="javascript:addDateDivUp();" class="btn"><font>添加上家</font></a>
					</div>
					<div class="divider"><hr><label class="btn btn-warning">下家信息</label></div>
					<div id="guestDownDiv">
					</div>
					<div id="addLine" class="col-md-offset-2">
						<a href="javascript:addDateDivDown();" class="btn"><font>添加下家</font></a>
					</div>
					<div id="guestDelDiv">
					</div>
					<div class="divider"><hr><label class="btn btn-warning">产证信息</label></div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>产证地址</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="propertyAddr" name="propertyAddr" value="${transSign.propertyAddr}">
						</div>
						<label class="col-sm-2 control-label"><font color="red">*</font>产证面积</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="square" name="square" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${transSign.square}' type='number' pattern='#0.00' />">
						</div>
					</div>

					<!-- <div class="form-group">
					</div> -->
		
					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>所在楼层</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="locateFloor" name="locateFloor"  onkeyup="checkNum2(this)"
								 value="${transSign.locateFloor}">
						</div>
						<label class="col-sm-2 control-label"><font color="red">*</font>总层高</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="totalFloor" name="totalFloor" onkeyup="checkNum2(this)"
								 value="${transSign.totalFloor}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>竣工年份</label>
						<div class="col-sm-4">
							<select class="form-control" name="finishYear" id="finishYear">
							</select>
						</div>
						<label class="col-sm-2 control-label"><font color="red">*</font>房屋类型</label>
						<div class="col-sm-4">
							<aist:dict clazz="form-control" id="propertyType" name="propertyType" display="select" defaultvalue="${transSign.propertyType}" dictType="30014" />
						</div>
					</div>

					<!-- <div class="form-group">
					</div> -->

					<div class="form-group">
						<label class="col-sm-2 control-label"><font color="red">*</font>合同公证</label>
						<div class="col-sm-4">
							<aist:dict clazz="form-control" id="isHukou" name="isHukou" display="select" defaultvalue="${transSign.isConCert}" dictType="gongzheng_need" />
						</div>
						<label class="col-sm-2 control-label"><font color="red">*</font>房屋有户口</label>
						<div class="col-sm-4">
							<aist:dict clazz="form-control" id="isConCert" name="isConCert" display="select" defaultvalue="${transSign.isHukou}" dictType="hukou_remain" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="comment" name="comment" value="${transSign.comment}">
						</div>
					</div>
					<div class="divider"><hr><label class="btn btn-warning">预估税费</label></div>
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label"><font color="red">*</font>房产税</label>
						<div class="input-group">
							<input type="text" class="form-control" id="houseHodingTax" name="houseHodingTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.houseHodingTax}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div>
					</div>
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label"><font color="red">*</font>个人所得税</label>
						<div class="input-group">
							<input type="text" class="form-control" id="personalIncomeTax" name="personalIncomeTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.personalIncomeTax}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div>
					</div>

					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label"><font color="red">*</font>上家营业税</label>
						<div class="input-group">
							<input type="text" class="form-control" id="businessTax" name="businessTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.businessTax}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div>
					</div>

					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label"><font color="red">*</font>下家契税</label>
						<div class="input-group">
							<input type="text" class="form-control" id="contractTax" name="contractTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.contractTax}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div> 
					</div>

					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label"><font color="red">*</font>土地增值税</label>
						<div class="input-group">
							<input type="text" class="form-control" id="landIncrementTax" name="landIncrementTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ houseTransfer.landIncrementTax}' type='number' pattern='#0.00' />">
							<span class="input-group-addon">万</span>
						</div>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
		<div id="caseCommentList" class="add_form">
		</div>
		<div class="ibox-title" style="height:auto;">
		<c:choose>  
	    <c:when test="${accesoryList!=null}">  
		<h5>上传备件<br><br><br>${accesoryList[0].accessoryName }</h5>
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
			<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-left:10px;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
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
							        <div class="cancel" style="margin-top:-125px;margin-left:85%;">
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
							            {% if (file.id) { %}
                                              {% if (((file.name).substring((file.name).lastIndexOf(".")+1))=='tif') { %}
							               		<img src="${ctx }/img/tif.png" alt="" width="80px" height="80px">
                                              {% } else { %}
 												 <img src="${imgweb}/filesvr/downLoad?id={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
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
		</c:choose> 
		</div>
		<div class="ibox-title">
		<a href="#" class="btn btn-primary" onclick="save(false)">保存</a>&nbsp;&nbsp;
		<a href="#" class="btn btn-primary" readOnlydata='1' onclick="submit()">提交</a>
		</div>
<div id="smsPlatFrom"></div>
	</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

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
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

    <!-- 校验 -->
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/js/plugins/validate/common/additional-methods.js"></script>
	<script src="${ctx}/js/plugins/validate/common/messages_zh.js"></script>
	<script src="${ctx}/js/trunk/task/taskTransSign.validate.js?v=1.1.0"></script>
	<!-- 弹出框插件 -->
	<script src="${ctx}/js/plugins/layer/layer.js"></script>
	<script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
	
	<script src="${ctx}/transjs/sms/sms.js"></script>

	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
	
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script>
	
	//验证手机和电话号码
	function checkContactNumber(ContactNumber){
		var mobile = $.trim(ContactNumber); 
		
		var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;  
        var isPhone = /^(?:(?:0\d{2,3})-)?(?:\d{7,8})(-(?:\d{3,}))?$/;; 
        
        var isValid = true;
        //如果为1开头则验证手机号码  
        if (mobile.substring(0, 1) == 1) {  
            if (!isMobile.exec(mobile) && mobile.length != 11) { 
            	isValid = false;
                return isValid;  
            }  
        }  
        //如果为0开头则验证固定电话号码  
        else if (mobile.substring(0, 1) == 0) {  
            if (!isPhone.test(mobile)) {  
            	isValid = false;
                return isValid;
            }  
        }
        else {
        	isValid = false;
            return isValid;
        } 
        
        return isValid;
	}
	
	$("#sendSMS").click(function(){
		var t='';
		var s='/';
		$("#reminder_list").find("input:checkbox:checked").closest('td').next().each(function(){
			t+=($(this).text()+s);
		});
		if(t!=''){
			t=t.substring(0,t.length-1);
		}
		$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
	});
	
		function readOnlyForm(){
			$(".readOnly_date").removeClass('date');
			$(".readOnly_date input").attr('readOnly',true);
			$("select[readOnlydata=1]").closest('.row').hide();
			$("[readOnlydata=1]").attr('readonly',true);
			$("[readOnlydata=1]").each(function(){
				if($(this).is('a')){
					$(this).hide();
				}
			});
		}

		$(function() { 
			var caseCode = $('#caseCode').val();
			var srvCode = 'TransSign';
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : srvCode
			});
			TaskTransSignValidate.init("transSignForm","");
			// Examle data for jqGrid
			//AistUpload.initHouAddPicUpload();
			//AistUpload.initHouTapeInfoUpload();
			if('caseDetails'==source){
				readOnlyForm();
			}
			
			$("#reminder_list").jqGrid({
				//data : reminderdata,
				url:"${ctx}/quickGrid/findPage",
				datatype : "json",
				height:210,
				multiselect : true,
				autowidth : true,
				shrinkToFit : true,
		        // rowNum:8,
		        viewrecords:true,
				colNames : [ '提醒事项', '备注' ],
				colModel : [ {
					name : 'REMIND_ITEM',
					index : 'REMIND_ITEM',
					width : 500
				}, {
					name : 'COMMENT',
					index : 'COMMENT',
					width : 500
				}

				],
				// pager : "#pager_list_1",
				viewrecords : false,
				pagebuttions : false,
				hidegrid : false,
				// recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
				// pgtext : " {0} 共 {1} 页",
				postData:{
		        	queryId:"queryToReminderList",
		        	search_partCode: taskitem
		        },
			});
			// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#reminder_list').setGridWidth(width);

			});


			/**日期组件*/
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : false,//显示年日历周
				autoclose : true
			});

			/**年份选择初始化*/
			initSelectYear("finishYear", finishYear);

			// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#table_list_1').setGridWidth(width);
				$('#table_list_2').setGridWidth(width);

			});
			
			initGuestInfo("30006002");
			initGuestInfo("30006001")
			
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
			/* if(checkAttachment()) {
				save(true);
			} */
			save(true);
		}

		/**保存数据*/
		function save(b) {
		    if(!checkForm()) {
				return;
			} 
			if(!$("#transSignForm").valid()){
				return;
			}
			 var jsonData = $("#transSignForm").serializeArray();
			
			deleteAndModify();
			
			var url = "${ctx}/task/sign/saveSign";
			if(b){
				url = "${ctx}/task/sign/submitSign";
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
                	 $.unblockUI();  
                 	if(b){ 
                         $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
     				    $(".blockOverlay").css({'z-index':'1900'});
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
					if(b) {
						caseTaskCheck();
						if(null!=data.message){
							alert(data.message);
						}
						//window.location.href = "${ctx }/task/myTaskList";
					} else {
						alert("保存成功。");
						 window.close();
						 window.opener.callback();
					}
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			}); 
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

		//验证控件checkUI();
		function checkForm() {
			var checkGuest = true;
			if ($('select[name=isPerchaseReserachNeed]').val() == '' ) {
				alert("限购查询为必选项!");
				$('select[name=isPerchaseReserachNeed]').focus();
				return false;
			}
			if ($('select[name=isLoanClose]').val() == '') {
				alert("抵押情况为必选项!");
				$('select[name=isLoanClose]').focus();
				return false;
			}
			if($('input[name=realConTime]').val()=='') {
                alert("实际签约时间为必填项!");
                $('input[name=realConTime]').focus();
                return false;

           }			
			var selects = $("input[name='guestNameUp']");
			$.each(selects, function(j, item) {
				if(item.value == '') {
					alert("上家姓名为必填项!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else if(item.value.trim().indexOf(" ")> -1){
					alert("上家姓名中不能包含空格!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else if(item.value.indexOf("先生") > 0 || item.value.indexOf("小姐") > 0 
						|| item.value.indexOf("叔叔") > 0 || item.value.indexOf("阿姨") > 0
						|| item.value.indexOf("女士") > 0){
					alert("上家姓名中不能包含先生、小姐、叔叔、阿姨、女士!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else {
					$(selects[j]).val(item.value.trim());
					checkGuest = true;
				}
			});
			if(!checkGuest || selects==null) {
				return false;
			}
			selects = null;
			selects = $("input[name='guestPhoneUp']");
			$.each(selects, function(j, item) {
				if(item.value == '') {
					alert("上家电话为必填项!");
					selects[j].focus();
					checkGuest = false;
				}else {
					checkGuest = checkContactNumber(item.value);
					
					if(!checkGuest){
						alert("上家电话不符合手机号码或电话号码格式!");
						selects[j].focus();
						
						return false;
					}
					//checkGuest = true;
	                //return false;
				}
			});
			
			
			if(!checkGuest || selects==null) {
				return false;
			}
			
			selects = null;
			selects = $("input[name='guestNameDown']");
			$.each(selects, function(j, item) {
				if(item.value == '') {
					alert("下家姓名为必填项!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else if(item.value.trim().indexOf(" ")> -1){
					alert("下家姓名中不能包含空格!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else if(item.value.indexOf("先生") > 0 || item.value.indexOf("小姐") > 0 
						|| item.value.indexOf("叔叔") > 0 || item.value.indexOf("阿姨") > 0
						|| item.value.indexOf("女士") > 0){
					alert("下家姓名中不能包含先生、小姐、叔叔、阿姨、女士!");
					selects[j].focus();
					checkGuest = false;
	                return false;
				}else {
					$(selects[j]).val(item.value.trim());
					checkGuest = true;
				}
			});
			if(!checkGuest || selects==null) {
				return false;
			}
			selects = null;
			selects = $("input[name='guestPhoneDown']");
			$.each(selects, function(j, item) {
				if(item.value == '') {
					alert("下家电话为必填项!");
					selects[j].focus();
					checkGuest = false;
				} else {
					checkGuest = checkContactNumber(item.value);
					
					if(!checkGuest){
						alert("下家电话不符合手机号码或电话号码格式!");
						selects[j].focus();
						
						return false;
					}
					//checkGuest = true;
	                //return false;
				}
			});
			if(!checkGuest || selects==null) {
				return false;
			}
			
			if($('input[name=conPrice]').val()=='') {
                alert("合同价为必填项!");
                $('input[name=conPrice]').focus();
                return false;
            }
			if(Number($('input[name=conPrice]').val()) <= 0){
				alert("合同价必须大于0!");
                $('input[name=conPrice]').focus();
                return false;
			}
			
			if($('input[name=realPrice]').val()=='') {
                alert("成交价为必填项!");
                $('input[name=realPrice]').focus();
                return false;
            }
			if(Number($('input[name=realPrice]').val()) <= 0){
				alert("成交价必须大于0!");
                $('input[name=realPrice]').focus();
                return false;
			}
			
			
			if($('input[name=propertyAddr]').val()=='') {
                alert("产证地址为必填项!");
                $('input[name=propertyAddr]').focus();
                return false;
            }
			if($('input[name=square]').val()=='') {
                alert("产证面积为必填项!");
                $('input[name=square]').focus();
                return false;
            }
			if($('input[name=locateFloor]').val()=='') {
                alert("所在楼层为必填项!");
                $('input[name=locateFloor]').focus();
                return false;
            }
			if($('input[name=totalFloor]').val()=='') {
                alert("总层高为必填项!");
                $('input[name=totalFloor]').focus();
                return false;
            }
			if($('select[name=propertyType]').val()=='') {
                alert("房屋类型为必选项!");
                $('input[name=propertyType]').focus();
                return false;
            }
			if($('select[name=isHukou]').val()=='') {
                alert("合同公证为必选项!");
                $('input[name=isHukou]').focus();
                return false;
            }
			if($('select[name=isConCert]').val()=='') {
                alert("房屋户口为必选项!");
                $('input[name=isConCert]').focus();
                return false;
            }
			if($('input[name=houseHodingTax]').val()=='') {
                alert("房产税为必填项!");
                $('input[name=houseHodingTax]').focus();
                return false;
            }
		    if($('input[name=personalIncomeTax]').val()=='') {
                alert("个人所得税为必填项!");
                $('input[name=personalIncomeTax]').focus();
                return false;
            }
			if($('input[name=businessTax]').val()=='') {
                alert("上家营业税为必填项!");
                $('input[name=businessTax]').focus();
                return false;
            }
			if($('input[name=contractTax]').val()=='') {
                alert("下家契税为必填项!");
                $('input[name=contractTax]').focus();
                return false;
            }
			if($('input[name=conPrice]').val()=='') {
                alert("合同价为必填项!");
                $('input[name=conPrice]').focus();
                return false;
            }
			if($('input[name=landIncrementTax]').val()=='') {
                alert("土地增值税为必填项!");
                $('input[name=landIncrementTax]').focus();
                return false;
            }
			if($('input[name=picName]').val()==undefined&&$('input[name=pic]').val()==undefined) {
                alert("产调为必填项!");
                return false;
            }
			return true;
		}
		
		var divIndexDown = 1;
		function addDateDivDown() {
			
			var txt = '<div id="dateDivD_' + divIndexDown + '" class="form-group">';
			txt += "<input type='hidden' name='pkidDown' value='0'/>";
			txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>下家姓名</label>";
			txt += "<div class='col-sm-4'>";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value=''></div>";
			txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>下家电话</label>";
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
			txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>上家姓名</label>";
			txt += "<div class='col-sm-4'>";
			txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value=''></div>";
			txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>上家电话</label>";
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
							txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>下家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value='"+value.guestName+"'></div>";
							txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>下家电话</label>";
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
							txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>上家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value='"+value.guestName+"'></div>";
							txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>上家电话</label>";
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
							txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>下家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameDown\" value=''></div>";
							txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>下家电话</label>";
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
							txt += "<label class='col-sm-2 control-label'><font color='red'>*</font>上家姓名</label>";
							txt += "<div class='col-sm-4'>";
							txt += "<input type=\"text\" class=\"form-control\" name=\"guestNameUp\" value=''></div>";
							txt += "<label class=\"col-sm-2 control-label\"><font color='red'>*</font>上家电话</label>";
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
	</script> 
	</content>
</body>

</html>