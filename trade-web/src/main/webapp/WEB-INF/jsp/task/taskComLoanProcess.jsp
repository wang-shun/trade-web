<!DOCTYPE html>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/steps/jquery.steps.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet" />
<!-- datepikcer -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/css/common/step-mend.css' />" >
<style type="text/css">
/*
* @Author: anchen
* @Date:   2016-11-30 11:15:22
* @Last Modified by:   MIUu
* @Last Modified time: 2016-12-29 10:33:45
*/
.wizard > .steps .current a, .wizard > .steps .current a:hover, .wizard > .steps .current a:active {
    background: #52cdec;
    color: #fff;
    cursor: default;
}
.wizard > .content > .body input {
    border: 1px solid #e5e5e5;
    display:inline-block!important;
}
.wizard > .steps .done a, .wizard > .steps .done a:hover, .wizard > .steps .done a:active {
    background: #87ddf3;
    color: #fff;
}
.wizard > .actions a, .wizard > .actions a:hover, .wizard > .actions a:active {
    background: #52cdec;
    color: #fff;
    display: block;
    padding: 0.5em 1em;
    text-decoration: none;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .steps .disabled a, .wizard > .steps .disabled a:hover, .wizard > .steps .disabled a:active {
    background: #f4f4f4;
    color: #aaa;
    cursor: default;
}
.wizard > .content {
    background: #fff;
    display: block;
    margin: 8px 0 0;
    min-height: 700px;
    overflow: hidden;
    position: relative;
    width: auto;
    padding-top: 10px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .content > .body {
    float: left;
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 0;
}
.wizard > .content .jqGrid_wrapper {
    margin: 5px 0 0;
}
.wizard > .actions {
    position: relative;
    display: block;
    text-align: right;
    width: 100%;
    margin-top: 20px;
    text-align: center;
}
.wizard > .steps a, .wizard > .steps a:hover, .wizard > .steps a:active {
    display: block;
    width: auto;
    margin: 0 0.5em 0.5em 0;
    padding: 8px;
    text-decoration: none;
    /* -webkit-border-radius: 5px; */
    -moz-border-radius: 5px;
    border-radius: 5px;
}
.wizard > .steps > ul > li {
    width: 14.28%;
}
.wizard > .content > .body > h4 {
    margin-bottom: 16px;
}
 .grey_visited {
            background-color: #ccc;
        }
        .blue_visited {
            background-color: #4bccec;
        }
        .red_visited {
            background-color: #ed5565;
        }
        .color_visited {
            display: inline-block;
            color: #fff;
            font-size: 14px;
            font-style: normal;
            padding: 1px 10px;
            margin-top: 2px;
            margin-right: 5px;
            border-radius: 3px;
        }

        .show_info span {
           display: inline-block;
        }
        .show_info .form_content {
            margin: 5px 10px;
        }
        .wizard > .content {
            min-height: 820px;
        }
        .grey_visited {
            background-color: #ccc;
        }
        .blue_visited {
            background-color: #4bccec;
        }
        .red_visited {
            background-color: #ed5565;
        }
        .color_visited {
            display: inline-block;
            color: #fff;
            font-size: 14px;
            font-style: normal;
            padding: 1px 10px;
            margin-top: 2px;
            margin-right: 5px;
            border-radius: 3px;
        }

        .show_info span {
           display: inline-block;
        }
        .show_info .form_content {
            margin: 5px 10px;
        }
        .radio_disabled em {
            float: left;
        }
        .pay_list_c1 {
            width: 24px;
            height: 18px;
            float: left;
            padding-top: 3px;
            cursor: pointer;
            text-align: center;
            margin-right: 3px;
            background-image: url(../static/trans/img/inputradio.gif);
            background-repeat: no-repeat;
            background-position: -24px 0;
        }
        .on {
            background-position: 0 0;
        }
        .on_check {
            background-position: -26px 0px;
        }

        .piaochecked {
            width: 20px;
            height: 20px;
            float: left;
            cursor: pointer;
            margin-left: 10px;
            text-align: center;
            background-image: url(images/checkbox_01.gif);
            background-repeat: no-repeat;
            background-position: 0 0;
        }

        .ibox-content{
            border-top-width: 0px;
        
        }
       input[readonly], select[disabled] {
		  background-color: #eee!important;
	}
</style>
<%
	response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<script language="javascript">
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var mainLoanBank = "1";

	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}

</script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

<%--环节编码 --%>
<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">

<!-- 交易单编号 -->
<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
<!-- 流程引擎需要字段 -->
<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
<input type="hidden" id="isMainLoanBank" name="isMainLoanBank" value="1"/>
<!-- 临时银行审批 -->
<input type="hidden" id="tmpBankStatus" name="tmpBankStatus"/>
<input type="hidden" id="serviceJobCode" value="${sessionUser.serviceJobCode }">
<input type="hidden" id="adminLoanerProcess"  name="adminLoanerProcess">
<!-- 案件合同价 -->
<input type="hidden" id="conPrice" name="conPrice" value="${caseBaseVO.sign.conPrice }">
	<!-- 服务流程 -->
		<div class="panel " id="serviceFlow">
	        <div class="row wrapper white-bg new-heading ">
	             <div class="pl10">
	                 <h2 class="newtitle-big">商贷审批</h2>
	                    <div class="mt20">
	                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
	                            <i class="iconfont icon">&#xe600;</i> 在途单列表
	                        </button>
	                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
	                            <i class="iconfont icon">&#xe63f;</i> 案件视图
	                        </button>
	                    </div>
	             </div>
	        </div>
        
       <div id="modal-form" class="modal fade" aria-hidden="true" >
		<div class="modal-dialog" style="width: 1000px;height:98%;overflow-y:scroll">
			<div class="modal-content">
				<div class="modal-body" >
					<div class="col-lg-12">
						<div class="ibox " style="min-height:500px;">
							<div class="ibox-title">
							     <button type="button" class="close" data-dismiss="modal" id="pricingClose"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>询价发起</h5>
							</div>
							<form id="addToEguPricingForm">
								<input type="hidden" name="caseCode" value="${caseCode}">
								<input type="hidden" name="isMainLoanBank" value="1"/>
								
								<div class="ibox-content" >
									<div class="form-group row">
										<label class="col-sm-2 control-label">方式选择：</label>
										<div class="col-sm-10">
										<div class="row">
											<div class="col-md-10">
												<div class="radio">
													<input type="radio" checked="" value="1"
														id="optionsRadios1" name="optionsRadios">直接发起
												</div>
											</div>
											</div>
										</div>
									</div>

									<div class="form-group row">
											<label class="col-sm-2 control-label"></label>
											
											<div class="col-sm-10" style="height:30px">
											<div class="row">
											<div class="col-md-3">
												<div class="radio">
													<input type="radio" value="0" id="optionsRadios2"
														name="optionsRadios">评估编号绑定<span>
												</div>
											</div>
											<div class="col-md-5" style="margin-top:-15px">
												<input type="text" id="code" name="code" data-mask ="999999-999999-999" placeholder="填写手机端收到的评估编号"
													class="form-control"></span>
											</div>
											</div>
										</div>
									</div>
								<div id="direct_launch_div">
									<div class="form-group row">
										<label class="col-sm-2 control-label">小区：</label>
										<div class="col-sm-10">
											<div class="row">
												<div class="col-md-2">
													<input type="text" name="residence_id"
														id="residence_id" placeholder="小区编号"
														class="form-control" onkeyup="checkInt(this)">
												</div>
												<div class="col-md-7">
													<input type="text" name="residence_name"
														id="residence_name" placeholder="小区地址（名称）"
														class="form-control">
												</div>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">贷款银行：</label>
										<div class="col-sm-10">
										
											<select  name="bank_type" class="form-control"
												id="bank_type" >
											</select>
											
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">支行<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<select  name="bank_branch_id" class="form-control"
												id="bank_branch_id">

											</select>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">楼栋室号<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<div class="row">
												<div class="col-md-2">
													<input type="text" name="building_no" id="building_no"
														placeholder="楼栋号" class="form-control" >
												</div>
												<div class="col-md-3">
													<input type="text" name="room_no" id="room_no"
														placeholder="室号" class="form-control" >
												</div>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">建筑面积<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<div class="row">
												<div class="col-md-3">
													<input type="text" name="area" id="area"
														placeholder="建筑面积" class="form-control" onkeyup="checknum(this)">
												</div>
												<div class="col-md-3">
													<input type="text" name="area_ground" id="area_ground"
														placeholder="建筑地上面积" class="form-control" onkeyup="checknum(this)">
												</div>
												<div class="col-md-3">
													<input type="text" name="area_basement"
														id="area_basement" placeholder="建筑地下面积"
														class="form-control" onkeyup="checknum(this)">
												</div>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">期望价格<span class="star">*</span>：</label>
										<div class="col-sm-10">
										<div class="row">
										<div class="col-md-5">
											<input type="text" name="deal_price" id="deal_price"
												class="form-control" placeholder="万元" onkeyup="checknum(this)">
												
										</div>
										</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">房屋结构：</label>
										<div class="col-sm-10">
											<div class="row">
												<div class="col-md-2">
													<input type="text" name="room" id="room"
														placeholder="室" class="form-control" onkeyup="checkInt(this)">
												</div>
												<div class="col-md-3">
													<input type="text" name="hall" id="hall"
														placeholder="厅" class="form-control" onkeyup="checkInt(this)">
												</div>
												<div class="col-md-3">
													<input type="text" name="toilet" id="toilet"
														placeholder="卫" class="form-control" onkeyup="checkInt(this)">
												</div>

											</div>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">楼层<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<div class="row">
												<div class="col-md-2">
													<input type="text" name="floor" id="floor"
														placeholder="所在楼层" class="form-control" onkeyup="checkInt(this)">
												</div>
												<div class="col-md-3">
													<input type="text" name="total_floor" id="total_floor"
														placeholder="总楼层" class="form-control" onkeyup="checkInt(this)">
												</div>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label class="col-sm-2 control-label">房屋类型<span class="star">*</span>：</label>
										<div class="col-sm-4">
										<div class="row">
										<div class="col-md-10">
											<aist:dict id="prop_type" name="prop_type"
												clazz="form-control" display="select"
												dictType="prop_type" defaultvalue="" />
										</div>
										</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">平面类型：</label>
										<div class="col-sm-4">
										<div class="row">	
											<div class="col-md-10">														
											<aist:dict id="plane_type" name="plane_type"
												display="select" clazz="form-control"
												dictType="flat_type" defaultvalue="" />
											</div>
										</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">朝向：</label>
										<div class="col-sm-10">
										<div class="row">
											<div class="col-md-10">																																												
											<aist:dict id="towards" name="towards" display="select"
												clazz="form-control m-b" dictType="towards"
												defaultvalue="" />
											</div>
										</div>
										</div>
									</div>
									<div style="clear: both"></div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">景观类型：</label>
										<div class="col-sm-10">
										<div class="row">	
											<div class="col-md-5">																																											
											<aist:dict id="scape" name="scape" display="radio"
												dictType="scape" defaultvalue="" />
											</div>
										</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">临街类型：</label>
										<div class="col-sm-10">
										<div class="row">	
											<div class="col-md-5">																												
											<aist:dict id="near_street" name="near_street"
												display="radio" dictType="near_street" defaultvalue="" />
											</div>
										</div>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-sm-2 control-label">备注：</label>
										<div class="col-sm-10">
										<div class="row">	
											<div class="col-md-10">																												
											<input type="text" class="form-control" name="remark"
												id="remark">
											</div>
										</div>
										</div>
									</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<input type="button" class="btn btn-success" id="toEguPricingSubmitBtn" value="提交">
				</div>
			</div>
		</div>
	</div>

	<div id="modal-applyForm" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox ">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>询价价格异议申请</h5>
							</div>

							<form id="applyForm">
								<input type="hidden" name="eva_code" id="eva_code" />
								<input type="hidden" name="case_id" value="${caseCode}">
								
								<div class="ibox-content">

									<div class="form-group">
										<label class="col-sm-3 control-label">期望价格<span class="star">*</span>：</label>
										<div class="col-sm-8 input-group">
											<input type="text" name="expected_price"
												id="expected_price" placeholder="期望价格"
												class="form-control" onkeyup="checknum(this)">
											<span class="input-group-addon">万元</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">备注<span class="star">*</span>：</label>
										<div class="col-sm-8 input-group">
											<input type="text" name="remark" id="remark"
												placeholder="备注" class="form-control">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<input type="button" class="btn btn-success" id="subApplyBtn" value="提交">
				</div>
			</div>
		</div>
	</div>
	
	<div id="modal-select" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="width: 600px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="col-lg-12">
						<div class="ibox " style="min-height:150px;">
							<div class="ibox-title">
							    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
								<h5>该物业地址对应多个小区，请点击选择一个小区</h5>
							</div>
								
								<div class="ibox-content">

									<div class="form-group">
										<div class="col-sm-12" id="residenceList" style="line-height:26px">

										</div>
									</div>
									
								</div>
						</div>
					</div>
					<div>&nbsp;</div>
				</div>
			</div>
		</div>
	</div>

	<div id="modal-form-report" class="modal inmodal in" aria-hidden="false" tabindex="-1" role="dialog">
		<div class="modal-dialog" style="width: 1000px; overflow-y:scroll">
			<div class="modal-content" >
				<div class="modal-body" style="height: 800px">
				<div class="col-lg-12">
					<div class="ibox " style="min-height: 700px">
					<div class="ibox-title">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h5>评估报告发起</h5>
					</div>
					<form id="reportForm" >
						<div class="ibox-content">
						<div class="form-group">
							<label class="col-sm-2 control-label">选择评估公司<span class="star">*</span>：</label>
							<div class="col-sm-10">
								<select class="form-control m-b" name="orgPricing" id="orgPricing">
									
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">选择需要的报告<span class="star">*</span>：</label>
							<div class="col-sm-10">
								<aist:dict id="reportType" name="reportType"
								clazz="form-control m-b" display="select"
								dictType="report_type" defaultvalue="" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">备注：</label>
							<div class="col-sm-10">
								<input type="text" name="comment" id="comment"
												placeholder="备注" class="form-control" >
							</div>
						</div>
						</div>
						</form>
					
					<div class="form-group">
					  <div class="table-box" id="comLoanProcess2fileUploadContainer" style="text-align:center;"></div>
						<%-- <%for(int j=1;j<4;j++) {%>
						<div class="ibox-content">
							<%if(j==1){ %>
							<h5>身份证</h5>
							<%}else if(j==2) {%>
							<h5>房产证</h5>
							<%}else{ %>
							<h5>购房合同</h5>
							<%} %>
							<div class="" id="fileupload_div_pic<%=j%>">
								<form id="fileupload"
									action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
									method="POST" enctype="multipart/form-data">
									<noscript>
										<input type="hidden" name="redirect"
											value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
										<input type="hidden" id="preFileCode" name="preFileCode"
											value="<%=j%>">
									</noscript>
									<h5 align="left"></h5>
									<div class="row-fluid fileupload-buttonbar">
										<div class="" style="height: auto">
											<div role="presentation" class="table table-striped "
												style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
												<div id="picContainer<%=j %>" class="files"
													data-toggle="modal-gallery"
													data-target="#modal-gallery"></div>
												<span class=" fileinput-button "
													style="margin-left: 0 !important; width: 80px;">
													<div id="chandiaotuBtn" class=""
														style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
														<i class="fa fa-plus"></i>
													</div> <input id="picFileupload<%=j %>" type="file"
													name="files[]" multiple
													data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
													data-sequential-uploads="true">
												</span>
											</div>
										</div>
									</div>
								</form>
							</div>

							<div class="row-fluid">
								<div class="">
									<script id="templateUpload<%=j %>" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv2" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
								
							        <div class="preview"><span class="fade"></span></div>
								
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									
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
	<script id="templateDownload<%=j %>" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv2" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12" name="reportPic">
										<input type="hidden" name="preFileAdress1" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag1" value="<%=j%>"></input>
										<input type="hidden" name="picName1" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" style="height:80px;width:80px">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-130px;">
							           <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
								</div>
							</div>

							<div class="row-fluid" style="display: none;">
								<div class="span4">
									<div class="control-group">
										<a class="btn blue start" id="startUpload"
											style="height: 30px; width: 50px"> <i
											class="icon-upload icon-white"></i> <span>上传</span>
										</a>
									</div>
								</div>
							</div>
						</div> --%>
					<%-- 	<%} %> --%>
					</div>

					<input type="button" class="btn btn-success" id="reportSubBtn" value="提交">

				</div>
			</div>
		</div>
	</div>
	</div>
	</div>

        <div class="ibox-content border-bottom clearfix space_box noborder">
            <div class="">
                    <div class="panel blank-panel">
                        <div class="panel-heading" style="padding:0;">
                            <div class="panel-options">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a id="tab1" data-toggle="tab" href="#tab-1">主选银行</a></li>
                                    <li class=""><a id="tab2" data-toggle="tab" href="#tab-2">候选银行</a></li>
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body no-padding">
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                   <div class="ibox float-e-margins">
                                        <div>
                                        
                          <div id="wizard">
                          <!-- 派单Tab开始（主) -->
                          <h3>派单信息</h3>
                                                <section>
                                                	<form id="orderform" >
                                                	<input type="hidden" name="isMainLoanBank" value="1">
                                                	<input type="hidden" name="loanerStatus">
                                                	<input type="hidden" name="bankOrgId" id="bankOrgId" value=""/>
                                                	<input type="hidden" name="tmpBankStatus">
                                                    <div class="form_list" style="margin-top: 0;">
                                                        <div class="marinfo">
                                                            <h4>派单信息</h4>
                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">是否临时银行</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="isTmpBank" >是
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" checked="">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">银行</label>
                                                                    <select name="bank_type" class="select_control  ">
                                                                    </select>
                                                                    <select name="finOrgCode" class=" select_control  ">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                            	                                     
	                                         	 				<input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
												 				<input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
												 				<input type="hidden"  id="loanerId" name="loanerId" />
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">信贷员</label> <input class="input_type yuanwid" placeholder="" value="" name='loanerName'>
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" onclick="selectLoanerByOrgId()"></i>
                                                                    
                                                                </div>
                                                                <div class="form_content">
					                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
																 <input type="text" name="loanerPhone" id="loanerPhone" placeholder="联系方式" class="input_type data_style">
		                                         				</div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content tmpBankShow nonTmpBankHide" id="div_tmpBankStatus">
                                                                    <label class="control-label sign_left_small">临时银行状态</label>
                                                                    <i class="color_visited grey_visited"></i>
                                                                    
                                                                </div>
																<div class="form_content tmpBankHide nonTmpBankShow" id="div_nonTmpBankStatus">
                                                                    <label class="control-label sign_left_small">接单状态</label>
                                                                    <i class="color_visited grey_visited"></i>
                                                                </div>
                                                            </div>
                                                            <div class="line tmpBankShow nonTmpBankHide">
		                                         				<div class="form_content">
		                                           		        <label class="control-label sign_left_small">临时银行原因<span class="star">*</span></label>
													 			<input type="text" name="tmpBankReason" class="input_type optionwid">
		                                        			 	</div>
		                                    				 </div> 
                                                            <div class="line tmpBankHide nonTmpBankShow">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        预定签约时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="resSignTime" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">签约地点</label><select name="resSignAddr"  class=" select_control data_style ">
                                                                    <option value="">浦东贵宾服务部</option></select>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;" id='div_order'>
                                                        <div class="marinfo">
                                                            <h4>客户信息</h4>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small"> 主贷人姓名</label> 
                                                                    <select name="custCode" id="custCode" class="input_type yuanwid">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款总额</label> <input name="mortTotalAmount" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款类型</label>
                                                                         <aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="" />
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷部分金额</label> <input name="comAmount" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷期望年限</label> <input name="comYear" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金部分金额</label> <input name='prfAmount' class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金期望年限</label> <input name="prfYear" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
  
                                                        </div>
                                                    </div>
                                                           <div class="line">
                                                                <div class="form_content" style="margin-left: 122px;">
																	<button class="btn btn-success ml5 tmpBankHide nonTmpBankShow" type="button" name="btn_order">派单</button>
                                                                    <button class="btn btn-success ml5 tmpBankShow nonTmpBankHide" type="button" name="btn_reqTmpBank">申请</button>
                                                                    <button class="btn btn-grey ml5 tmpBankHide nonTmpBankShow" type="button" name="btn_order_cancel">撤销</button>
                                                                    
                                                                </div>
                                                            </div>
                                                    </form>
                                                </section>
                          <!-- 派单Tab结束（结束) -->
                          
							<h3>询价</h3>
							<section>
							<h4>询价结果查看</h4>
								 <div class="jqGrid_wrapper">
	                                <table id="table_list_1"></table>
	                                <div id="pager_list_1"></div>
	                             </div>
							</section>
							
							<h3>提醒清单</h3>
							<section>
						    <div style="width:100%">
								<div class="step-content" style="margin-top: -25px;">
									<div class="ibox">
									<div class="ibox-title">
										<h4>备件列表</h4>
										<a class="btn btn-primary pull-right" href="#" id="sendSMS" style="clear:both;margin-top:-31px">发送短信提醒</a>
									</div>
									<div class="ibox-content">
										 <div class="jqGrid_wrapper">
											<table id="table_list_2"></table>
											<div id="pager_list_2"></div>
											</div>
										</div>
									  </div>
								</div>
							</div>
							</section>
							
							
							<h3>贷款签约</h3>
							<!--  <fieldset>-->
							<section>
							<form id="mortgageForm" class="form_list" style="margin:0">
							<input type="hidden" name="processStart" id="processStart" value=""/>
								    <input type="hidden" name="pkid" id="pkid"/>
									<input type="hidden" name="caseCode" value="${caseCode}">
									<input type="hidden" name="isMainLoanBank" value="1"/>	
									<input type="hidden" name="stateInBank"  id="stateInBank"  value=""/>	
									<input type="hidden" name="bankOrgId" id="bankOrgId" value=""/>	
									<input type="hidden" name='custName' id="custName" value="">
									<!-- 记录评估费的时候需要记录合同价 -->
									<input type="hidden" id="conPrice" name="conPrice" value="${caseBaseVO.sign.conPrice }">
								<h2 class="newtitle title-mark">按揭派单信息</h2>
                                                    <div class="form_list show_info">
                                                        <div class="line">
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small ver-top">信贷员 </label> 
                                                                <span class="yuanwid" id="lab_loanerName">赵小明</span>
                                                            </div>
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">银行</label>
                                                                <span id="lab_bankName">中国邮政储蓄银行股份有限公司上海闸北区行知路分行</span>
                                                            </div>
                                                        </div>
                                                        <div class="line">
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small ver-top">贷款人 </label> 
                                                                <span class="yuanwid" id="lab_custName">张飞先</span>
                                                            </div>
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">贷款总额 </label> 
                                                                <span class="yuanwid" id="lab_mortTotalAmount">500万</span>
                                                            </div>
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">贷款类型 </label> 
                                                                <span class="yuanwid" id="lab_mortType">组合贷</span>
                                                            </div>
                                                        </div>
                                                        <div class="line">
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">商贷金额</label> 
                                                                <span class="yuanwid" id="lab_comAmount">200万</span>
                                                            </div>
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">商贷年限 </label> 
                                                                <span class="yuanwid" id="lab_comYear">20年</span>
                                                            </div>

                                                        </div>
                                                        <div class="line">
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">公积金贷款金额</label> 
                                                                <span class="yuanwid" id="lab_prfAmount">200万</span>
                                                            </div>
                                                            <div class="form_content">
                                                                <label class="control-label sign_left_small">公积金贷款年限</label> 
                                                                <span class="yuanwid" id="lab_prfYear">10年</span>
                                                            </div>
                                                        </div>
                                                    </div>
													
                                                        <h2 class="newtitle title-mark">贷款信息填写</h2>
                                                        <div class="marinfo">
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">主贷人 </label> 
                                                                    <select name="custCode" id="custCode" class="input_type yuanwid" style="border-color: rgb(204, 204, 204);"></select>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small"> 贷款总额 </label> <input name='mortTotalAmount' onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款类型</label>
                                                                         <aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="" />
                                                                </div> 
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">主贷人单位</label> <input name='custCompany' class="input_type mendwidth" placeholder="" value="" >
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">认定套数</label> <input name='houseNum' onkeyup="checkInt(this)" class=" input_type data_style" placeholder="" value="" >
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷金额</label> <input name="comAmount" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷年限</label> <input name='comYear' onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷利率折扣</label> <input name='comDiscount' onkeyup="autoCompleteComDiscount(this)" class=" input_type data_style" placeholder="0.50~1.5之间" value="" >
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金贷款金额</label> <input name='prfAmount' class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金贷款年限</label> <input name="prfYear" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">放款方式</label>
                                                                    <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
															display="select" dictType="30017" defaultvalue="" />
                                                                </div>
                                                            </div>

                                                            
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                     	   签约时间
                   	                                                 </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="signDate" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">推荐函编号</label> <input name="recLetterNo" class=" input_type yuanwid" placeholder="" value="" >
                                                                </div>
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">放款前报告</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="ifReportBeforeLend" checked="">需要
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="ifReportBeforeLend">不需要
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small" style="float: left;margin-top: 9px;" id="yinhang">是否临时银行</label>
                                                                    <p class="radio_disabled" style="width: 187px;">
                                                                        <label >
                                                                            <span class="pay_list_c1 on">
                                                                                <input type="radio" checked="checked" name="isTmpBank" value="1" class="radioclass">
                                                                            </span>是
                                                                        </label>
                                                                        <label>
                                                                            <span class="pay_list_c1 ">
                                                                                <input type="radio"  name="isTmpBank" value="0" class="radioclass">
                                                                            </span>否
                                                                        </label>
                                                                    </p>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款银行</label><select name="bank_type"  class=" select_control data_style ">
                                                                    <option value="">请选择</option></select>
                                                                    <select name="finOrgCode"  class=" select_control data_style ">
                                                                    <option value="">请选择</option></select>
                                                                </div>
                                                                
                                                            </div>
                                                             <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">信贷员</label> 
                                                                    <input class="input_type yuanwid" placeholder="" value="" name="loanerName" style="border-color: rgb(204, 204, 204);" readonly="">
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" onclick="selectLoanerByOrgId()"></i>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">电话</label> <input name= 'loanerPhone' class=" input_type yuanwid" placeholder="" value="" readonly>
                                                                </div>
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">是否到场</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="isLoanerArrive" checked="">是
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="isLoanerArrive">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <h2 class="newtitle title-mark">评估信息和补件信息填写</h2>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">评估费</label> <input name='evaFee' class=" input_type yuanwid" placeholder="" value="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">服务费</label> <input name='serviceFee' class=" input_type yuanwid" placeholder="" value="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">收费总计</label> <input name='feeTotal' class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">评估费支出</label> <input name='evaFeeCost' class=" input_type yuanwid" placeholder="" value="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">评估费业绩</label> <input name='evaFeePerf' class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">服务费业绩</label> <input name='serviceFeePerf' class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                   <span class="date_icon">元</span>
                                                                </div>
                                                            </div>
                                                            <div style="margin: 0 0 25px 127px;color:#a8a8a8;">注：业绩计算时优先满足标准评估费（1‰合同价）</div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">补件名称</label> <input name='supContent' class=" input_type yuanwid" placeholder="" value="" >
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left select_style mend_select" style="width: 102px;">
                                                                        补件时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="remindTime" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">备注</label>
                                                                    <input name='remark' class="input_type optionwid" placeholder="" value="">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
							
							
							</section>
							
							
							<h3>上传备件</h3>
							<section>
							<input type="hidden" name = "accesoryCount" id="accesoryCount" value="${fn:length(accesoryList)} "/>
							<c:choose>  
						    <c:when test="${accesoryList!=null}">  
							<h4>上传备件<br><div class="table-box" id="comLoanProcessfileUploadContainer"></div>
						
				    </c:when>  
				    <c:otherwise> 
					<h5>上传备件<br>无需上传备件</h5>
				    </c:otherwise>  
					</c:choose> 
					</section>
							
					<h3>收费评估报告发起</h3>
					<section>							
						<h4>报告查看</h4>
						<div class="jqGrid_wrapper">
							<table id="table_list_4"></table>
							<div id="pager_list_4"></div>
						</div>
					</section>

					<h3>完成</h3>
					<section>
					<form  class="form_list" name="completeForm" id="completeForm" style="margin:0">
					<input type="hidden" name="pkid" />
							<input type="hidden" name="finOrgCode" />
							<input type="hidden" id="fl_is_tmp_bank"/>
							<input type="hidden" name='isMainLoanBank' id='isMainLoanBank' value="1">
                                                        <h2 class="newtitle title-mark">贷款信息填写</h2>
                                                        <div class="marinfo">

                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">临时银行</label>
                                                                    <div class="controls" style="width: 162px;">
                                                                       <label class="radio inline"> <input type="radio" disabled value="1" name="isTmpBank" checked="checkd">是
                                                                        </label> 
                                                                        <label class="radio inline"> <input type="radio" disabled value="0" name="isTmpBank">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">银行</label>
                                                                    <select name="bank_type" class=" select_control  " new_disabled="">
                                                                    </select>
                                                                    <select name="finOrgCode" class=" select_control  " new_disabled="">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            
                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">最终贷款银行</label>
                                                                    <div class="controls" style="width: 162px;">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="lastBankSub" checked="">是
                                                                        </label> 
                                                                        <label class="radio inline"> <input type="radio" value="0" name="lastBankSub">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        审批时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                            <div class="form_content">
                                                                    <label class="control-label sign_left_small">信贷员 </label> <input name="loanerName" class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        处理时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="" class="input_type yuanwid datatime" type="text" value="" readonly="" placeholder="">
                                                                    </div>
                                                                </div>
                                                                
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">主贷人 </label> <input class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款总额</label> <input class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款类型</label>
                                                                         <select name="loanLostFinOrgName" class=" select_control data_style " new_disabled="" readonly="">
                                                                    <option value="">请选择</option></select>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">主贷人单位</label> <input class="input_type mendwidth" placeholder="" value="" readonly="">
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">认定套数</label> <input class=" input_type data_style" placeholder="" value="" readonly="">
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷金额</label> <input class=" input_type yuanwid" placeholder="" value="">
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷年限</label> <input class=" input_type yuanwid" placeholder="" value="">
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">利率折扣</label> <input class=" input_type data_style" placeholder="0.50~1.5之间" value="">
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金贷款金额</label> <input class=" input_type yuanwid" placeholder="" value="">
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款年限</label> <input class=" input_type yuanwid" placeholder="" value="">
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">放款方式</label><select name="loanLostFinOrgName" class=" select_control data_style ">
                                                                    <option value="">请选择</option></select>
                                                                </div>
                                                            </div>

                                                            
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        签约时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">推荐函编号</label> <input class=" input_type yuanwid" placeholder="" value="" readonly="">
                                                                </div>
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">放款前报告</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="raduio2" checked="">需要
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="raduio2">不需要
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                         <form id="completeForm" class="form_list">
                         	<input type="hidden" name="pkid" />
							<input type="hidden" name="finOrgCode" />
							<input type="hidden" id="fl_is_tmp_bank"/>
							<input type="hidden" name='isMainLoanBank' id='isMainLoanBank' value="1">
	                         <div class="marinfo">
	                             <div class="line">
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one ver-top">贷款银行</label>
	                                     <span id="sp_bank">&nbsp;</span>
	                                 </div>
	
	                             </div>
	                             <div class="line">
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one ver-top">支行</label>
	                                     <span id="sp_sub_bank">&nbsp;</span>
	                                 </div>
	                             </div>
	                             <div class="line" style="width:988px;">
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">商贷金额</label>
	                                     <span id="comAmount" class="info-text">&nbsp;</span>
	                                 </div>
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">商业贷款利率</label>
	                                     <span id="comDiscount" class="info-text">&nbsp;</span>
	                                 </div>
	                             </div>
	                             <div class="line" style="width:988px;">
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">是否临时银行</label>
	                                     <span id="sp_is_tmp_bank" class="info-text">&nbsp;</span>
	                                 </div>
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">临时银行审批结果</label>
	                                     <span id="tmpBankRejectReason" class="info-text"></span>
	                                 </div>
	                             </div>
	                             <div class="line" style="width:988px;">
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">临时银行处理人</label>
	                                     <span id="sp_tmp_bank_u" class="info-text">&nbsp;</span>
	                                 </div>
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one">临时银行处理时间</label>
	                                     <span id="sp_tmp_bank_t" class="info-text">&nbsp;</span>
	                                 </div>
	                             </div>
	                             <div class="line" style="width:988px;">
	                                 <div class="form_content mt5">
	                                     <label class="control-label sign_left_one">作为最终贷款银行</label>
	                                     <label ><input type="checkbox" value="1" id="lastBankSub" name="lastBankSub" /> 是</label>
	                                 </div>
	                                 <div class="form_content">
	                                     <label class="control-label sign_left_one" style="float: left;margin-top: 6px;margin-left:300px;">审批时间</label>
	                                     <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                         <input name="apprDate" id="apprDate" class="input_type yuanwid datatime" type="text" readonly >
	                                     </div>
	                                 </div>
	                             </div>
	                         </div>
                           </form>		
						</section>
						</div>
                                        </div>
                                    </div>
                                </div>

                                <div id="tab-2" class="tab-pane">
                                    <div class="ibox float-e-margins">
                                        <div>
                                        
                            <div id="wizard1">
                            <!-- 派单Tab开始（次) -->
                          <h3>派单信息</h3>
                                                <section>
                                                	<form id="orderform1" >
                                                	<input type="hidden" name="isMainLoanBank" value="0">
                                                	<input type="hidden" name="loanerStatus">
                                                	<input type="hidden" name="tmpBankStatus">
                                                    <input type="hidden" name="bankOrgId" id="bankOrgId" value=""/>
                                                    <div class="form_list" style="margin-top: 0;">
                                                        <div class="marinfo">
                                                            <h4>派单信息</h4>
                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">是否临时银行</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="isTmpBank" >是
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" checked="">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">银行</label>
                                                                    <select name="bank_type" class="select_control  ">

                                                                    </select>
                                                                    <select name="finOrgCode" class=" select_control  ">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="line">
	                                         	 				<input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" />
												 				<input type="hidden" id="loanerOrgId" name ="loanerOrgId" />
												 				<input type="hidden"  id="loanerId" name="loanerId" />
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">信贷员</label> <input class="input_type yuanwid" placeholder="" value="" name='loanerName'>
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" onclick="selectLoanerByOrgId()"></i>
                                                                </div>
                                                                 <div class="form_content">
					                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
																 <input type="text" name="loanerPhone" id="loanerPhone" placeholder="联系方式" class="input_type data_style">
		                                         				</div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content tmpBankShow nonTmpBankHide" id="div_tmpBankStatus">
                                                                    <label class="control-label sign_left_small">临时银行状态</label>
                                                                    <i class="color_visited grey_visited"></i>
                                                                    
                                                                </div>
																<div class="form_content tmpBankHide nonTmpBankShow" id="div_nonTmpBankStatus">
                                                                    <label class="control-label sign_left_small">接单状态</label>
                                                                    <i class="color_visited grey_visited"></i>
                                                                </div>
                                                            </div>
                                                            <div class="line tmpBankShow nonTmpBankHide">
		                                         				<div class="form_content">
		                                           		        <label class="control-label sign_left_small">临时银行原因<span class="star">*</span></label>
													 			<input type="text" name="tmpBankReason" class="input_type optionwid">
		                                        			 	</div>
		                                    				 </div> 
                                                            <div class="line tmpBankHide nonTmpBankShow">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        预定签约时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="resSignTime" class="input_type yuanwid datatime" type="text" value="" placeholder="">
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">签约地点</label><select name="resSignAddr"  class=" select_control data_style ">
                                                                    <option value="">浦东贵宾服务部</option></select>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;">
                                                        <div class="marinfo">
                                                            <h4>客户信息</h4>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small"> 主贷人姓名</label> <input name="custCode" class=" input_type yuanwid" placeholder="" value="" >
                                                                </div>
                                                            </div>
                                                             <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款总额</label> <input name="mortTotalAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款类型</label>
                                                                         <aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="" />
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷部分金额</label> <input name="comAmount" onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷期望年限</label> <input name="comYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金部分金额</label> <input name='prfAmount' onkeyup="checknum(this)" class=" input_type yuanwid" placeholder="" value="" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金期望年限</label> <input name="prfYear" onkeyup="checknum(this)" class=" input_type data_style" placeholder="" value="" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                            
                                                        </div>
                                                    </div>
                                                    
                                                     <div class="line">
                                                          <div class="form_content" style="margin-left: 122px;">
                                                                    <button class="btn btn-success ml5 tmpBankHide nonTmpBankShow" type="button" name="btn_order">派单</button>
                                                                    <!--侯选银行不能申请临时银行 <button class="btn btn-success ml5 tmpBankShow nonTmpBankHide" type="button" name="btn_reqTmpBank">申请</button> -->
                                                                    <button class="btn btn-grey ml5 tmpBankHide nonTmpBankShow" type="button" name="btn_order_cancel">撤销</button>
                                                                    
                                                                </div>
                                                            </div>
                                                    </form>
                                                </section>
                          <!-- 派单Tab结束（次) -->
							<h3>询价</h3>
							<section>
								<h4>询价结果查看</h4>
									<div class="jqGrid_wrapper">
										<table id="table_list_3"></table>
										<div id="pager_list_3"></div>
									</div>
							</section>

							<h3>提醒清单</h3>	
							<section>						
						    <div style="width:100%">
							<div class="step-content" style="margin-top: -25px;">
							<div class="ibox">
								<div class="ibox-title">
									<h4>备件列表</h4>
									<a class="btn btn-primary pull-right" href="#" id="sendSMS1" style="clear:both;margin-top:-31px">发送短信提醒</a>									
								</div>
								<div class="ibox-content">
									<div class="jqGrid_wrapper">
										<table id="table_list_5"></table>
										<div id="pager_list_5"></div>
										</div>
									</div>
							    </div>
							</div>
							</div>
							</section>

							<h3>贷款签约</h3>
							<!--  <fieldset>-->
							<section>
							<div class="step-content" style="margin-top: -30px;">
								<div class="ibox" style="width:988px;">
								<div class="ibox-content">
								<form id="mortgageForm1" class="form_list">
								    <input type="hidden" name="pkid" id="pkid"/>
								    <input type="hidden" name="processStart" id="processStart" value=""/>
									<input type="hidden" name="caseCode" value="${caseCode}">
									<input type="hidden" name="isMainLoanBank" value="0"/>	
									<input type="hidden" name="stateInBank"  id="stateInBank"  value=""/>	
									<input type="hidden" name="bankOrgId" id="bankOrgId" value=""/>	
																		<!-- 记录评估费的时候需要记录合同价 -->
									<input type="hidden" id="conPrice" name="conPrice" value="${caseBaseVO.sign.conPrice }">							
		                            <div class="marinfo">
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 主贷人<span class="star">*</span></label> 
													 <select name="custCode" id="custCode" class="input_type yuanwid">
													 </select>
													 <input type="hidden" name="custName" id="custName" />
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 贷款总额<span class="star">*</span></label>
		                                             <input type="text" name="mortTotalAmount" id="mortTotalAmount" class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">贷款类型<span class="star">*</span></label>
														<aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="" />
		                                         </div>
		                                     </div>    
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">主贷人单位</label>
													 <input type="text" name="custCompany" id="custCompany"
														class="input_type mendwidth">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">认定套数</label>
													 <input type="text" name="houseNum" id="houseNum"
															class="input_type data_style" onkeyup="checkInt(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷金额<span class="star">*</span></label>
													 <input type="text" name="comAmount" id="comAmount"
															class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷利率折扣<span class="star">*</span></label>
													 <input type="text" name="comDiscount" id="comDiscount" placeholder="0.50~1.50之间"
															class="input_type yuanwid"  onkeyup="autoCompleteComDiscount(this)">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷年限<span class="star">*</span></label>
													 <input type="text" name="comYear" id="comYear"
														    class="input_type data_style" onkeyup="checknum(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款金额</label>
													 <input type="text" name="prfAmount" id="prfAmount"
															class="input_type yuanwid" onkeyup="checknum(this)" placeholder="">
													 <span class="date_icon">万元</span>		
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款年限</label>
													 <input type="text" name="prfYear" id="prfYear"
															class="input_type data_style" onkeyup="checknum(this)">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left">放款方式<span class="star">*</span></label>
													 <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
															display="select" dictType="30017" defaultvalue="" />
		                                         </div>
		                                     </div>
	
				                             <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small select_style mend_select">签约时间<span class="star" >*</span>
		                                             </label>
		                                             <div class="input-group sign-right dataleft input-daterange pull-left" id="date_4">
                                                            <input class="input_type yuanwid" type="text" placeholder="" name="signDate" id="signDate" readonly>
                                                     </div>										
		                                         </div>
		                                		<div class="form_content">
		                                             <label class="control-label sign_left_small">推荐函编号<span class="star">*</span></label>
													 <input type="text" name="recLetterNo" id="recLetterNo" class="input_type data_style">
		                                         </div>
		                                         <div class="form_content radio-seat" style="margin-top:8px;">
		                                             <label class="control-label sign_left">需要放款前报告</label>							             
										             <div class="controls">
                                                         <label class="radio inline"> <input type="radio" value="1" name="ifReportBeforeLend">是</label>
                                                         <label class="radio inline"> <input type="radio" value="0" name="ifReportBeforeLend" checked="checked">否</label>
                                                     </div>
		                                         </div>
		                                     </div>
		                                     <div class="line">
		                                         <div class="form_content radio-seat" style="margin-top:5px;">
		                                             <label class="control-label sign_left_small">是否临时银行</label>
													 <div class="controls ">
                                                        <label class="radio inline"> <input type="radio" value="1" name="isTmpBank" disabled="true">是</label>
                                                        <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" disabled="true" checked="checked">否</label>
                                                     </div>
		                                         </div>
		                                         
		                                         <div class="form_content" id="dispachTimeShow0">
		                                          	<div class="form_content radio-seat" style="margin-left:26px;">
			                                             <label class="control-label sign_left_small">派单时间<span class="star" ></span></label>
	                                                     <input class="input_type yuanwid" type=text placeholder="" name="dispachTime" id="dispachTime0" readonly>	
                                                    </div>								
		                                         </div>
		                                     </div> 
		                                     		                                     
		         							 <div class="line">
		                                         <div class="form_content tmpBankReasonDiv">
		                                             <label class="control-label sign_left_small">临时银行原因<span class="star">*</span></label>
													 <input type="text" name="tmpBankReason" class="input_type optionwid">
		                                         </div>
		                                     </div> 
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">贷款银行</label>																										
													 <select  name="bank_type" class="select_control data_style" id="bank_type" ></select>	
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small" style="width: 147px;">贷款支行<span class="star">*</span></label>
													 <select  name="finOrgCode" class="select_control data_style" id="finOrgCode" ></select>
		                                         </div
		                                     </div>
		                                     
		                                     <div class="line">
		                                     
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 信贷员 <span class="star">*</span></label>
													 <input  type="text" readonly='readonly' name="loanerName" id="loanerName" placeholder="" class="input_type yuanwid" >
													 <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage"  id="loanerNameImage" name ="loanerNameImage"  onclick="selectLoanerByOrgId()" >&#xe627;</i>
													 </input>			
													 <input type="hidden"  id="loanerOrgCode"  name="loanerOrgCode" />
													 <input type="hidden"  id="loanerOrgId" name ="loanerOrgId" />
													 <input type="hidden"  id="loanerId" name="loanerId" />
													 <div class="input-group float_icon organize_icon" ></div>	
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
													 <input type="text" name="loanerPhone" id="loanerPhone"	placeholder="联系方式" class="input_type data_style">
		                                         </div>
		                                         
		                                         <div class="form_content" style="margin-top:8px;">
		                                             <label class="control-label sign_left">信贷员到场</label> 
		                                             <div class="controls" >
											              <label class="radio inline"> <input type="radio" value="1" name="isLoanerArrive">是</label> 
								                          <label class="radio inline"> <input type="radio" value="0" name="isLoanerArrive" checked="checked">否</label>
		                                             </div>
		                                         </div>
		                                     </div>                                 


		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">补件名称</label>
											         <input type="text" class="input_type yuanwid" name="supContent" id="supContent">										
		                                         </div>
		                                         <div class="form_content" >
		                                             <label class="control-label sign_left_small select_style mend_select">补件时间</label>										             
										             <div class="input-group sign-right dataleft input-daterange pull-left" id="date_1" data-date-format="yyyy-mm-dd">
                                                            <input class="input_type yuanwid" type="text" name="remindTime" id="remindTime" readonly>
                                                     </div>
		                                         </div>
		                                     </div>

		                                     
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">备注</label>
													 <input type="text" name="remark" id="remark" class="input_type optionwid">
		                                         </div>
		                                     </div>
		                                     
		                               </div>
									</form>
								  </div>
								</div>
							</div>
							</section>

		<h3>上传备件</h3>
		<section>
		<input type="hidden" name = "accesoryCount" id="accesoryCount" value="${fn:length(accesoryList)} "/>
		<c:choose>  
	    <c:when test="${accesoryList1!=null}">  
		<h4>上传备件<br><div class="table-box" id="comLoanProcess1fileUploadContainer"></div>
	    </c:when>  
	    <c:otherwise> 
		<h5>上传备件<br>无需上传备件</h5>
	    </c:otherwise>  
		</c:choose> 
</section>

							<h3>收费评估报告发起</h3>
							<section>
								<h4>报告查看</h4>
								<div class="jqGrid_wrapper">
									<table id="table_list_6"></table>
									<div id="pager_list_6"></div>
								</div>
							</section>

							<h3>完成</h3>
							<section>
									<form id="completeForm1" class="form_list">
				                         	<input type="hidden" name="pkid" />
											<input type="hidden" name="finOrgCode" />
											<input type="hidden" id="fl_is_tmp_bank"/>
											<input type="hidden" name='isMainLoanBank' id='isMainLoanBank' value="1">
                                                 <div class="marinfo">
                                                     <div class="line">
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one ver-top">贷款银行</label>
                                                             <span id="sp_bank">&nbsp;</span>
                                                         </div>

                                                     </div>
                                                     <div class="line">
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one ver-top">支行</label>
                                                             <span id="sp_sub_bank">&nbsp;</span>
                                                         </div>
                                                     </div>
                                                     <div class="line" style="width:988px;">
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">商贷金额</label>
                                                             <span id="comAmount" class="info-text">&nbsp;</span>
                                                         </div>
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">商业贷款利率</label>
                                                             <span id="comDiscount" class="info-text">&nbsp;</span>
                                                         </div>
                                                     </div>
                                                     <div class="line" style="width:988px;">
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">是否临时银行</label>
                                                             <span id="sp_is_tmp_bank" class="info-text">&nbsp;</span>
                                                         </div>
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">临时银行审批结果</label>
                                                             <span id="tmpBankRejectReason" class="info-text"></span>
                                                         </div>
                                                     </div>
                                                     <div class="line" style="width:988px;">
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">临时银行处理人</label>
                                                             <span id="sp_tmp_bank_u" class="info-text">&nbsp;</span>
                                                         </div>
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one">临时银行处理时间</label>
                                                             <span id="sp_tmp_bank_t" class="info-text">&nbsp;</span>
                                                         </div>
                                                     </div>
                                                     <div class="line" style="width:988px;">
                                                         <div class="form_content mt5">
                                                             <label class="control-label sign_left_one">作为最终贷款银行</label>
                                                             <label ><input type="checkbox" value="1" id="lastBankSub" name="lastBankSub" /> 是</label>
                                                         </div>
                                                         <div class="form_content">
                                                             <label class="control-label sign_left_one" style="float: left;margin-top: 6px;margin-left:300px;">审批时间</label>
                                                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                 <input name="apprDate" id="apprDate" class="input_type yuanwid datatime" type="text" readonly >
                                                             </div>
                                                         </div>
                                                     </div>
                                                 </div>
                                             </form>		
										</section>
									</div>
                                     
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="aboutInfo">   
               <div class="view-content" id="caseCommentList"> </div>
            </div>
          </div>  

    <div class="modal inmodal fade in file-popup" id="myFile" tabindex="-1" role="dialog" aria-hidden="false" >
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">附件库</h4>
                </div>
                <div class="modal-body">
                    <div class="tab">
                        <div class="tab_menu">
                            <ul>
                                <li class="selected">分类1</li>
                                <li>分类2</li>
                            </ul>
                        </div>
                        <div class="tab_box">
                            <div>
                                <table class="table table-file" >
                                    <thead>
                                        <tr>
                                            <th class="text-center">
                                                <input type="checkbox" id="CheckedAll" />
                                            </th>
                                            <th>
                                                文件名
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                类型
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                上传时间
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-center">
                                                <input type="checkbox" name="items" />
                                            </td>
                                            <td>
                                               20161103test1.jpg
                                            </td>
                                            <td>
                                                类型1
                                            </td>
                                            <td>
                                                2016-12-06 15:30:21
                                            </td>
                                            <td>
                                                <a href="javascript:void(0)" class="fa-blue mr10">查看</a>
                                                <a href="javascript:void(0)" class="fa-pink">删除</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="text-center">
                                                <input type="checkbox" name="items" />
                                            </td>
                                            <td>
                                               20161103test1.jpg
                                            </td>
                                            <td>
                                                类型1
                                            </td>
                                            <td>
                                                2016-12-06 15:30:21
                                            </td>
                                            <td>
                                                <a href="javascript:void(0)" class="fa-blue mr10">查看</a>
                                                <a href="javascript:void(0)" class="fa-pink">删除</a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="text-center page_box">
                                    <span id="currentTotalPage">
                                        <strong>
                                            1/8
                                        </strong>
                                    </span>
                                    <span class="ml15">
                                        共
                                        <strong id="totalP">
                                            144
                                        </strong>
                                        条
                                    </span>
                                    &nbsp;
                                    <div id="pageBar" class="pagination text-center">
                                        <ul class="pagination">
                                            <li class="first disabled">
                                                <a href="#"><i class="fa fa-step-backward"></i></a>
                                            </li>
                                            <li class="prev disabled">
                                                <a href="#"><i class="fa fa-chevron-left"></i></a>
                                            </li>
                                            <li class="page active">
                                                <a href="#">
                                                    1
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    2
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    3
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    4
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    5
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    6
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    7
                                                </a>
                                            </li>
                                            <li class="page">
                                                <a href="#">
                                                    8
                                                </a>
                                            </li>
                                            <li class="next">
                                                <a href="#"><i class="fa fa-chevron-right"></i></a>
                                            </li>
                                            <li class="last">
                                                <a href="#"><i class="fa fa-step-forward"></i></a>
                                            </li>
                                        </ul>
                                        <div class="pagergoto">
                                            <span class="go_text">
                                                跳转至
                                            </span>
                                            <span>
                                            <input type="text" class="go_input" value="">
                                            <input type="button" class="go_btn" value="GO"></span>
                                        </div>
                                    </div>
                                </div>
                             </div>
                             <div class="cover">
                                 <table class="table table-file" >
                                    <thead>
                                        <tr>
                                            <th class="text-center">
                                                <input type="checkbox" id="CheckedAll" />
                                            </th>
                                            <th>
                                                文件名
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                文件类型
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                上传时间
                                                <a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                            </th>
                                            <th>
                                                操作
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                        </tr>
                                    </tbody>
                                </table>
                             </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="file" class="btn btn-danger">上传新附件</button>
                    <button type="button" class="btn btn-success">提交</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

<content tag="local_script"> 
<script>
var source = "${source}";

//‘我要修改’时不可修改签约时间和审批时间
if('caseDetails'==source){
	readOnlyForm();
}

function readOnlyForm(){
	$("input[name='signDate']").attr('readOnly',true).css("background-color","#eee").parent().removeClass("input-daterange");
	$("input[name='apprDate']").attr('readOnly',true).css("background-color","#eee").parent().removeClass("input-daterange");
}
</script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/staps/jquery.steps.min.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<!-- 上传附件相关 --> 
<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script> 
<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> 
<!-- 上传附件结束 -->
<script src="<c:url value='/transjs/task/taskComLoanProcess.js' />?6"></script> 
<script	src="<c:url value='/js/trunk/task/attachment.js' />"></script> 
<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
<script src="<c:url value='/transjs/sms/sms.js' />"></script>	
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/stickUp.js' />"></script>
<!-- 改版引入的新的js文件 --> 
<script src="<c:url value='/js/common/textarea.js' />"></script>
<script src="<c:url value='/js/common/common.js' />"></script>
<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script>
var afterTimeFlag=${afterTimeFlag};
var popInited=false;
var operForm;//用于定位当前操作的是哪个Form
var MORT_ORDER_1 = new mortageRender("#orderform",'O');
var MORT_ORDER_0 = new mortageRender("#orderform1",'O');
var MORT_SIGN_1 = new mortageRender('#mortgageForm','S');
var MORT_SIGN_0 = new mortageRender('#mortgageForm1','S');
var MORT_COMPLETE_1 = new mortageRender('#completeForm','C');
var MORT_COMPLETE_0 = new mortageRender('#completeForm1','C');
var ORDER_1 = new orderRender("#orderform",'O');
var ORDER_0 = new orderRender("#orderform1",'O');
var ORDER_SIGN_1 = new orderRender('#mortgageForm','S');
var ORDER_SIGN_0 = new orderRender('#mortgageForm1','S');
var ORDER_COMPLETE_1 = new orderRender('#completeForm','C');
var ORDER_COMPLETE_0 = new orderRender('#completeForm1','C');
var tradeCenter;

/**
 * 加载签约地点 
 *val 选中的ID
 */
function loadTradeCenterList(f,val){
	if(tradeCenter){ //只加载一次
		return buildTradeCenter(f,tradeCenter,val);
	}
	$.ajax({
	    url:ctx+"/weixin/signroom/getTradeCenterList",
	    async:false,
    	method:"post",
    	dataType:"json",
    	success:function(data){
    		if(data){
    			tradeCenter=data;
    			return buildTradeCenter(f,tradeCenter,val);
    		}
    }});
}
/***
 * 生成签约地点下拉框
 */
function buildTradeCenter(f,tradeCenter,val){
	var saddr = f.find('[name=resSignAddr]');
	saddr.find('option').remove();
	saddr.append($("<option value=''>请选择</option>"));
	$(tradeCenter).each(function(i,e){
		saddr.append($("<option value='"+e.pkid+"'>"+e.centerName+"</option>"));	
	});
	if(val){
		saddr.val(val);
	}
}
//点击tab页面触发函数   -----From Bootstrap 标签页（Tab）插件
$(".nav-tabs").find("a").on('shown.bs.tab', function (e) {
	  var id = e.target.id;
	  if(id == 'tab1'){
		  //点击主选银行
		  $("#isMainLoanBank").val(1);
		  $("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(1);
	  }else if(id == 'tab2'){
		  //点击候选银行
		  $("#isMainLoanBank").val(0);
		  $("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(0);
	  }
	})

function checknum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

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

function checkInt(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");  
}

 	var ctx = "${ctx}";
	var step = ${step};
	var step1 = ${step1};

	var evaCode = "${evaCode}";
	if(evaCode != ""){
		$("#eva_code").val(evaCode);
	}
 	var index = 0;
 	/**评估费用修改后计算费用总计和业绩*/
	function evalFeeChanged(){
 		var f =$(this).closest('form');
 		var conPrice=parseFloat($('#conPrice').val());
		var evaFee = parseFloat(f.find('[name=evaFee]').val()||0);
		var serviceFee = parseFloat(f.find('[name=serviceFee]').val()||0);
		var evaFeeCost = parseFloat(f.find('[name=evaFeeCost]').val()||0);
		var feeTotal = evaFee + serviceFee ;
		
		
		var evaFeeReal = conPrice / 1000 ; 
		evaFeeReal = Math.min(evaFeeReal,feeTotal) ;
		evaFeeReal = Math.max(evaFeeReal,evaFee) ;
		var perfTotal = feeTotal - evaFeeCost ;
		var evaFeePerf = evaFeeReal-evaFeeCost;
		var serviceFeePerf = perfTotal - evaFeePerf ;
		if(serviceFeePerf <=0){
			serviceFeePerf ='';
		}
		if(evaFeePerf<=0){
			evaFeePerf='';
		}
		f.find('[name=feeTotal]').val(feeTotal);
		f.find('[name=evaFeePerf]').val(evaFeePerf);
		f.find('[name=serviceFeePerf]').val(serviceFeePerf);
	}
 	
 	
 	jQuery(document).ready(function() {
 		$('[name=btn_order]').click(loanerProcessStart);
 		$('[name=btn_order_cancel]').click(loanerProcessCancel);
 		$('[name=btn_reqTmpBank]').click(startTmpBankWorkFlow)
		$("#mortgageForm1").find("select[name='custCode']").change(guestCompanyReadOnly);
		$("#mortgageForm1,#mortgageForm").find('[name=evaFee],[name=serviceFee],[name=evaFeeCost]').change(evalFeeChanged);
		$("select[name='mortType']").change(function(){
			var f=$(this).closest('form');
			if($(this).val()=='30016001'){
				f.find("input[name='prfAmount']").val('').prop('disabled',true).css("background-color","#eee");
				f.find("input[name='prfYear']").val('').prop('disabled',true).css("background-color","#eee");
			}else{
				f.find("input[name='prfAmount']").prop('disabled',false).css("background-color","");
				f.find("input[name='prfYear']").prop('disabled',false).css("background-color","");
			}
		});
		
		$("#sendSMS").click(function(){
					var t='';
					var s='/';
					$("#table_list_2").find("input:checkbox:checked").closest('td').next().each(function(){
						t+=($(this).text()+s);
					});
					if(t!=''){
						t=t.substring(0,t.length-1);
					}
					$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
		});
		
		$("#sendSMS1").click(function(){
					var t='';
					var s='/';
					$("#table_list_5").find("input:checkbox:checked").closest('td').next().each(function(){
						t+=($(this).text()+s);
					});
					if(t!=''){
						t=t.substring(0,t.length-1);
					}
					$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
		});
		
		var updFun = function(e) {
			var that = $(this).data('blueimp-fileupload')
					|| $(this).data('fileupload');
			that._resetFinishedDeferreds();
			that._transition($(this).find('.fileupload-progress'))
					.done(function() {
						that._trigger('started', e);
					});
		};
		
		for(var i=1;i<4;i++){
			AistUpload.init('picFileupload'+i, 'picContainer'+i,
					'templateUpload'+i, 'templateDownload'+i,
					updFun);
			$("#picContainer"+i).bind('DOMNodeInserted', function(e) {
				var picDiv=$("div[name='allPicDiv2']");
				var input=$("input[name='picTag1']");
				if(picDiv.length > input.length) {
					index++;
					if(index % 2 == 0) {
						index = 0;
						$("#startUpload").click();
					}
				}
			});
		}
		
		$.each(idList, function(index, value){
			AistUpload.init('picFileupload'+value, 'picContainer'+value,
					'templateUpload'+value, 'templateDownload'+value, updFun);
			/**监听 div 执行自动上传*/
			$("#picContainer"+value).bind('DOMNodeInserted', function(e) {
				var picDiv=$("div[name='allPicDiv1']");
				var input=$("input[name='picTag']");
				if(picDiv.length > input.length) {
					index++;
					if(index % 2 == 0) {
						//alert("执行自动上传");
						index = 0;
						$("#startUpload1").click();
					}
				}
			});
		});
		
		$("#code").inputmask({"mask":"999999-999999-999"});
		
		$("select[name='mortType']").each(function(){
			$(this).find("option[value='30016003']").remove();
		});
		
		$("select[name='mortType']").blur(function(){
			if($(this).val()!=""){
				$(this).css("border-color","#e5e6e7");
			}
		});

		$('.input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : false,//显示年日历周
			autoclose : true
		});
		//设置初始操作步骤
		getPricingList("table_list_3","pager_list_3",0);
		getPricingList("table_list_1","pager_list_1",1);
		 //银行下拉列表
		getParentBank($("#addToEguPricingForm").find("select[name='bank_type']"),$("#bank_branch_id"),"",null,"egu");
		
		$("#addToEguPricingForm").find("select[name='bank_type']").change(function(){
			/*$("#bank_branch_id").chosen("destroy");*/
    	getBranchBankList($("#bank_branch_id"),$("#addToEguPricingForm").find("select[name='bank_type']").val(),"",null,"egu");
        });
		

		$("select[name='finOrgCode']").change(finOrgCodeChange);
		
		 document.getElementById('optionsRadios1').checked=true;
            if($("input[name='optionsRadios']:checked").val()==0){
                $("#direct_launch_div").hide();
            }else{
                $("#direct_launch_div").show();
                 $("#addToEguPricingForm").find("input").each(function(){
                     $(this).removeAttr("disabled");
                     if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios" &&$(this).attr("type")!="hidden"){
                         $(this).val("");
                     }
                 });
                 $("#addToEguPricingForm").find("select").each(function(){
                     $(this).removeAttr("disabled");
                     if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios" &&$(this).attr("type")!="hidden"){
                         $(this).val("");
                     }
                 });
            }
		if(popInited)return true;
		popInited=true;
		$("input[name='optionsRadios']").click(function(){
			 if($(this).val()==0){
				 $("#direct_launch_div").hide();
				 $("#addToEguPricingForm").find("input").each(function(){
					 if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios"){
       					 $(this).attr("disabled","disabled");
					 }
   				 });
				 $("#addToEguPricingForm").find("select").each(function(){
   					 $(this).attr("disabled","disabled");
   				 });
			 }else{
				 $("#direct_launch_div").show();
				 $("#addToEguPricingForm").find("input").each(function(){
       				 $(this).removeAttr("disabled");
   				 });
				 $("#addToEguPricingForm").find("select").each(function(){
       				 $(this).removeAttr("disabled");
   				 });
			 }
		 });
		if(step1==0){
			loadMortgageInfo(0,MORT_ORDER_0);
		}else if(step1 == 2){
 			getReminderList("table_list_5","pager_list_5");
		}else if(step1 == 3||step1==4){
	 		getMortgageInfo($("#caseCode").val(),0);
		}else if(step1 == 5){
			getReportList("table_list_6","pager_list_6",0);
		}else if(step1 == 6){
			getCompleteMortInfo(0);
		}

			$("#isMainLoanBank").val("1");
			$("#addToEguPricingForm").find("input[name='isMainLoanBank']").val(1);
			if(step==0){
				//loadMortgageInfo(1,MORT_ORDER_1);
			}else if(step == 2){
	 			getReminderList("table_list_2","pager_list_2");
			}else if(step == 3||step==4){
		 		getMortgageInfo($("#caseCode").val(),1);
			}else if(step == 5){
				getReportList("table_list_4","pager_list_4",1);
			}else if(step == 6){
				getCompleteMortInfo(1);
			}

	 	$(".myDataToggle").click(function(){
	 		$("#isMainLoanBank").val($(this).attr('data-m'));
	 		$("#addToEguPricingForm").find("input[name='isMainLoanBank']").val($(this).attr('data-m'));
	 		if($("#isMainLoanBank").val() == "0"){
	 			$("#first").removeClass("in");
	 		}else{
	 			$("#second").removeClass("in");
	 		}
		});
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
		
		 $(window).bind('resize', function () {
             var width = $('.jqGrid_wrapper').width();
             $('#table_list_1').setGridWidth(width);
             $('#table_list_2').setGridWidth(width);
         });
		
	});
	
	var attachmentList = null;
	var files = null;
	var attachmentIds = null;
	var preFileAdress = null;
	var picTag = null;
	var picName = null;
	function getUploadPicInfo() {
		attachmentIds =[];
		preFileAdress = [];
		picTag = [];
		picName = [];

		// 图片的ID
		$("div[name='reportPic']").each(function(){
			$(this).find("input[name='preFileAdress1']").each(function(){
				preFileAdress.push($(this).val());
			});
			$(this).find("input[name='picTag1']").each(function(){
				picTag.push($(this).val());
			});
			$(this).find("input[name='picName1']").each(function(){
				picName.push($(this).val());
			});
			
		});

		$("input[name='attachmentId']").each(function(){
			attachmentIds.push($(this).val());
		});
		
		return false;
	}
	
	function startTmpBankWorkFlow(){
		//'我要修改'页面不触发流程 	
		if(source != null && source !=''){
			return;
		}
		//未选择临时银行
		if(!$("#isTmpBank").is(':checked')){
			return;
		}

	 	$.ajax({
		    url:ctx+"/mortgage/tmpBankAudit/start",
		    async:false,
	    	method:"post",
	    	dataType:"json",
	    	data:{caseCode:$("#caseCode").val()},
	    	success:function(data){
	    		if(data.success){
	    			window.wxc.success(data.message);
	    		}else{
	    			window.wxc.error("流程开启失败！");
	    		}
	    		
	    	}
	 	});

	}
	function selectLoanerUser(array) {
		//operForm 在弹出选择用户已经确定
		selectLoanerUserCom(array,operForm);
	}
	
	function selectLoanerUserCom(array,$form){		
		var isMainLoanBank = $("#isMainLoanBank").val();
		if (array && array.length > 0) {
				if(isMainLoanBank == 1){
					$form.find("input[name='loanerName1']").val(array[0].username);
					$form.find("input[name='loanerName']").val(array[0].username);
				}else if(isMainLoanBank == 0){
					$form.find("input[name='loanerName']").val(array[0].username);
				}				
				$.ajax({
					url : ctx + "/eloan/LoanerCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {
						$form.find("#loanerNameImage").css("color","#52cdec");
						$form.find("#loanerPhone").val(data.user.mobile);
						$form.find("#loanerId").val(data.user.id);
						$form.find("#loanerOrgCode").val(data.user.orgName);
						$form.find("#loanerOrgId").val(data.user.orgId);
					}
				})
			} else {
				if(isMainLoanBank == 1){
					$form.find("input[name='loanerName1']").val("");
				}
				$form.find("input[name='loanerName']").val("");				
				$form.find("#loanerOrgCode").val("");
				$form.find("#loanerOrgId").val("");
			}
	}
	
	//渲染图片 
	function renderImg(){
		$('.wrapper-content').viewer('destroy');
		$('.wrapper-content').viewer();
	}
 	</script> 
 </content>
 <content tag="local_require">
    <script>
    	var fileUpload;
    
	    require(['main'], function() {
			requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
				fileUpload = aistFileUpload; 
				
				fileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "ComLoanProcess",
		    		preFileCode : "_letter_first",
		    		fileUploadContainer : "comLoanProcessfileUploadContainer"
		    	});
				
				fileUpload.init({
			    		caseCode : $('#caseCode').val(),
			    		partCode : "ComLoanProcess",
			    		preFileCode : "_letter_sec",
			    		fileUploadContainer : "comLoanProcess1fileUploadContainer"
			    	});
				
			    
		    });
	    });
	    
	    
	</script>
	</content>
</body>
</html>
