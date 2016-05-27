<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
	rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
	rel="stylesheet" />
<style type="text/css">

.form-control {
	margin-bottom: 5px;
	height:32px;
}

.form-group label {
	text-align: right;
}

.radio {
	margin-left: 20px;margin-top:-10px
}

.mouseover-color{
	background-color:#B4D5F5;
}

</style>
<script language="javascript">
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
 	var ctx = "${ctx}";

	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>

	<%--环节编码 --%>
	<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
	<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">
	
	<!-- 交易单编号 -->
	<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
	<!-- 流程引擎需要字段 -->
	<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
	<input type="hidden" id="isMainLoanBank" name="isMainLoanBank" value="1"/>
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<h2>商贷签约</h2>
			<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
			</ol>
         <!--    <button class="btn btn-outline btn-primary " id="sub" type="button"><i class="fa fa-check"></i>&nbsp;提交</button>
            <button class="btn btn-outline btn-success " id="save" type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">保存</span></button>
           -->              
		</div>
	</div>
	
	<div class="panel-body">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox">
					<div class="ibox-title" id="firBank">
						<h5 id="firstBank">主选银行</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> 
						</div>
					</div>
					<div class="ibox-content" id="first" >							
								<div class="row">
								<form id="mortgageForm">
									<input type="hidden" name="pkid" id="pkid"/>
									<input type="hidden" name="caseCode" value="${caseCode}">
									<input type="hidden" name="isMainLoanBank" value="1"/>
									
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款类型<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<aist:dict id="mortType" name="mortType"
												clazz="form-control m-b" display="select" dictType="30016"
												defaultvalue="" />

										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款总额<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="mortTotalAmount"
												id="mortTotalAmount" class="form-control" onkeyup="checknum(this)" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" >商贷部分金额<span class="star">*</span>：</label>
										<div class="col-md-10" >
											<input type="text" name="comAmount" id="comAmount"
												class="form-control" onkeyup="checknum(this)" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷部分年限<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="comYear" id="comYear"
												class="form-control" onkeyup="checknum(this)">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">商贷部分利率折扣<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="comDiscount" id="comDiscount"
												class="form-control" onkeyup="checknum(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" >公积金贷款金额：</label>
										<div class="col-md-10">
											<input type="text" name="prfAmount" id="prfAmount"
												class="form-control" onkeyup="checknum(this)" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">公积金贷款年限：</label>
										<div class="col-md-10">
											<input type="text" name="prfYear" id="prfYear"
												class="form-control" onkeyup="checknum(this)">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人<span class="star">*</span>：</label>
										<div class="col-md-10">
											<select name="custCode" id="custCode" class="form-control">
											</select>
											<input type="hidden" name="custName" id="custName" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" >主贷人单位：</label>
										<div class="col-md-10" >
											<input type="text" name="custCompany" id="custCompany"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">放款方式<span class="star">*</span>：</label>
										<div class="col-md-10">
											<aist:dict id="lendWay" name="lendWay" clazz="form-control"
												display="select" dictType="30017" defaultvalue="" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员姓名<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="loanerName" id="loanerName"
												placeholder="姓名" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" >信贷员电话<span class="star">*</span>：</label>
										<div class="col-md-10" >
											<input type="text" name="loanerPhone" id="loanerPhone"
												placeholder="联系方式" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员到场：</label>
										<div class="col-md-10">
											<label class="checkbox-inline"> <input
												type="checkbox" value="1" name="isLoanerArrive"
												id="isLoanerArrive"> 是
											</label>
										</div>

									</div>
									<div style="clear: both"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label">认定套数：</label>
										<div class="col-md-10">
											<input type="text" name="houseNum" id="houseNum"
												class="form-control" onkeyup="checkInt(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label" >签约时间<span class="star" >*</span>：</label>
										<div class="col-md-10" >
											<input type="date" class="form-control" name="signDate" id="signDate" >					
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">需要放款前报告：</label>
										<div class="col-md-10">
										<label class="checkbox-inline">
											<input type="checkbox" value="1" name="ifReportBeforeLend"
												id="ifReportBeforeLend"> 是
										</label>
										</div>
									</div>
									
									<div style="clear: both"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label">贷款银行：</label>
										<div class="col-md-10" style="height:38px">																										
											<select class="chosen-select" name="bank_type"
												id="bank_type" >
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款支行<span class="star">*</span>：</label>
										<div class="col-md-10" style="height:38px">
											<select class="chosen-select" name="finOrgCode"
																	id="finOrgCode"  >
											</select>
										</div>
									</div>

									<div style="clear: both"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">补件名称：</label>
										<div class="col-md-10">
											<input type="text" class="form-control" name="supContent" id="supContent">										
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">补件时间：</label>
										<div class="col-md-10" >
											
												<input type="date" class="form-control" name="remindTime" id="remindTime" >
											
										</div>
									</div>
									<div style="clear: both"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">备注：</label>
										<div class="col-sm-10">
											<input type="text" name="remark" id="remark" class="form-control">
										</div>
									</div>
									</form>
								</div>
								
							<h3>上传备件</h3>
							
						    <div style="width:100%">
							<div class="row">

							<div class="ibox-title" style="height:310px;">
							<input type="hidden" name = "accesoryCount" id="accesoryCount" value="${fn:length(accesoryList)} "/>
							<c:choose>  
						    <c:when test="${accesoryList!=null}">  
							<h5>${accesoryList[0].accessoryName }</h5>
							<c:forEach var="accesory" items="${accesoryList}" varStatus="status">
					            <div class="att_first" id="fileupload_div_pic"> 
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
									<div class="row-fluid fileupload-buttonbar" >
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
						
				    </c:when>  
				    <c:otherwise> 
					<h5>上传备件<br>无需上传备件</h5>
				    </c:otherwise>  
					</c:choose> 
					</div>
		<div class="ibox-title">
		<div class="form-group">
			<a href="#" class="btn btn-primary" style="width:98%;" id="saveBtn1">保存</a>
		</div>

		</div>
								  </div>
							</div>
														
						</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel-body" style="margin-top:-30px">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox">
					<div class="ibox-title" id="secBank">
						<h5 id="secondBank">候选银行</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-down"></i>
							</a> 
						</div>
					</div>
					
					<div class="ibox-content" id="second" >
								<div class="row">
								<form id="mortgageForm1">
									<input type="hidden" name="pkid" id="pkid"/>
									<input type="hidden" name="caseCode" value="${caseCode}">
									<input type="hidden" name="isMainLoanBank" value="0"/>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款类型<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<aist:dict id="mortType" name="mortType"
												clazz="form-control m-b" display="select" dictType="30016"
												defaultvalue="" />

										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款总额<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="mortTotalAmount"
												id="mortTotalAmount" class="form-control" onkeyup="checknum(this)" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷部分金额<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="comAmount" id="comAmount" onkeyup="checknum(this)"
												class="form-control" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">商贷部分年限<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="comYear" id="comYear" onkeyup="checknum(this)"
												class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">商贷部分利率折扣<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="comDiscount" id="comDiscount" onkeyup="checknum(this)"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">公积金贷款金额：</label>
										<div class="col-md-10">
											<input type="text" name="prfAmount" id="prfAmount" onkeyup="checknum(this)"
												class="form-control" placeholder="万元">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">公积金贷款年限：</label>
										<div class="col-md-10">
											<input type="text" name="prfYear" id="prfYear" onkeyup="checknum(this)"
												class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人<span class="star">*</span>：</label>
										<div class="col-md-10">
											<select name="custCode" id="custCode" class="form-control">
											</select>
											<input type="hidden" name="custName" id="custName" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">主贷人单位：</label>
										<div class="col-md-10">
											<input type="text" name="custCompany" id="custCompany"
												class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">放款方式<span class="star">*</span>：</label>
										<div class="col-md-10">
											<aist:dict id="lendWay" name="lendWay" clazz="form-control"
												display="select" dictType="30017" defaultvalue="" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员姓名<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="loanerName" id="loanerName"
												placeholder="姓名" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员电话<span class="star">*</span>：</label>
										<div class="col-md-10">
											<input type="text" name="loanerPhone" id="loanerPhone"
												placeholder="联系方式" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">信贷员到场：</label>
										<div class="col-md-10">
											<label class="checkbox-inline"> <input
												type="checkbox" value="1" name="isLoanerArrive"
												id="isLoanerArrive"> 是
											</label>
										</div>
									</div>
									<div style="clear: both"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label">认定套数：</label>
										<div class="col-md-10">
											<input type="text" name="houseNum" id="houseNum"
												class="form-control" onkeyup="checkInt(this)">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">签约时间<span class="star" >*</span>：</label>
										<div class="col-md-10">
											<input type="date" class="form-control" name="signDate" id="signDate" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">需要放款前报告：</label>
										<div class="col-md-10">
											<input type="checkbox" value="1" name="ifReportBeforeLend"
												id="ifReportBeforeLend"> 是
										</div>
									</div>
																			
									<div style="clear: both"></div>

									<div class="form-group">
										<label class="col-sm-2 control-label">贷款银行：</label>
										<div class="col-md-10" style="height:38px">																										
											<select class="chosen-select" name="bank_type"
												id="bank_type" >
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">贷款支行<span class="star">*</span>：</label>
										<div class="col-md-10" style="height:38px">
											<select class="chosen-select" name="finOrgCode"
																	id="finOrgCode"  >
											</select>
										</div>
									</div>

									<div style="clear: both"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">补件名称：</label>
										<div class="col-md-10">
											<input type="text" class="form-control" name="supContent" id="supContent">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">补件时间：</label>
										<div class="col-md-10" >
											<input type="date" class="form-control" name="remindTime" id="remindTime" >
										</div>
									</div>
									<div style="clear:both"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">备注：</label>
										<div class="col-sm-10">
											<input type="text" name="remark" id="remark" class="form-control">
										</div>
									</div>
									</form>
								</div>
								
								
								<h3>上传备件</h3>
							
						    <div style="width:100%">
							<div class="step-content">

		<div class="ibox-title" style="height:310px;">
		<c:choose>  
	    <c:when test="${accesoryList1!=null}">  
		<h5>${accesoryList1[0].accessoryName }</h5>
		<c:forEach var="accesory" items="${accesoryList1}" varStatus="status">
               <div class="att_second" id="fileupload_div_pic"> 
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
		</c:choose> 
		</div>
		<div class="ibox-title">
		<div class="form-group">
			<a href="#" class="btn btn-primary" style="width:98%;" id="saveBtn2">保存</a>
		</div>

		</div>
								  </div>
							</div>
					</div>
				</div>
			</div>
		</div>
		<div id="smsPlatFrom"></div>
	</div>

<content tag="local_script"> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
   	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/mobilejs/task/taskComLoanProcess.js"></script> 
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	<script src="${ctx}/transjs/sms/sms.js"></script>	
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script> 
	
	 </content>
</body>

</html>