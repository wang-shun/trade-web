<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}
.ui-state-hover{
	cursor:pointer;
}
.btn-xm{
margin-left: 10px;
margin-top: 10px;
width:160px;
}
.tbsporg {
 width: 80%;;
 height:30px;
}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="queryUserId" value="${queryUserId}"/>	
		<div class="row">
		<div class="col-lg-12">
			<div class="ibox">
				<div class="ibox-title">
					<h5>案件筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-md-5 col-sm-12">
								<div class="form-group ">
									<label class="col-md-2 control-label">物业地址：</label>
									<div class="col-md-8">
										<input type="text" id="txt_prd_addr" name="txt_prd_addr" placeholder=""class="form-control" >
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-12">
								<div class="form-group">
									<label class="col-md-3  col-sm-4 control-label">导入时间:</label>
									<div class="col-md-8">
									   	<div id="datepicker" class="input-group input-medium date-picker input-daterange pull-left" data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="起始日期"> 
											<span class="input-group-addon">到</span>
											<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="结束日期" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-5">
								<div class="form-group ">
									<label class="col-md-2 control-label">组织：</label>
									<div class="col-md-10">
											<input type="text" class="span12 tbsporg" id="radioOrgName" name="radioOrgName" readonly="readonly" 
												   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
												   startOrgId:'1D29BB468F504774ACE653B946A393EE', orgType:'',departmentType:'',departmentHeriarchy:'',
												   chkStyle:'radio',callBack:radioOrgSelectCallBack,
												   expandNodeId:''})" />
											<input class="m-wrap " type="hidden" id="oriGrpId" name="oriGrpId"> 
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-12">
							   <div class="form-group">
							     <label class="col-md-3  col-sm-4 control-label">
							        <button type="button" class="btn btn-primary" onclick="javascript:loadGrid()"> 查询 </button>	
							     </label>
							     <label class="col-md-1  col-sm-4 control-label">
							        <button type="button" class="btn btn-primary" onclick="javascript:clean()"> 清空 </button>	
							     </label>
							   </div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>我的案件列表</h5>
				</div>

				<div class="ibox-content">
					<div class="jqGrid_wrapper">
						<table id="table_list_1"></table>
						<div id="pager_list_1"></div>
						
			
				
					</div><div class="ibox-button text-center"><a class="btn btn-primary" href="javascript:caseChangeTeam()"
							disabled="true" id="caseChangeTeamButton">案件派单</a></div>
				</div>
			</div>
		</div>
	</div>
 <!-- 案件转组 -->
            <div id="team-modal-form" class="modal fade" role="dialog" aria-labelledby="team-modal-title" aria-hidden="true">
	             <div class="modal-dialog" style="width:700px">
	                <div class="modal-content">
	                    <div class="modal-header">
						   <button type="button" class="close" data-dismiss="modal"
						      aria-hidden="true">×
						   </button>
						   <h4 class="modal-title" id="team-modal-title">
						      案件转组
						   </h4>
					   </div>
                       <div class="modal-body">
                       <div class="row">
                       <form  id="team-form" class="form-horizontal">
                       
                       
			            </form>
			            </div>
                     </div> 
                     <div class="modal-footer">
			            <button type="button" class="btn btn-default"
			               data-dismiss="modal">关闭
			            </button>
			            <button type="button" class="btn btn-primary" onclick="javascript:changeCaseTeam()">
			                                提交
			            </button>
                     </div>
                     </div>
                 </div>
             </div>  
	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
		 <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  <!-- iCheck --> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>	
<%-- 	<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> --%>
    <script src="${ctx}/js/jquery.blockui.min.js"></script> 
    <script src="${ctx}/js/trunk/case/unlocatedCase.js"></script>
 </content>
</body>
</html>
