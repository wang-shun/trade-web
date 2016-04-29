<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


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
<!-- 弹出框插件 -->
<link href="${ctx}/css/plugins/layer/layer.css" rel="stylesheet">
<link href="${ctx}/css/plugins/layer/layer.ext.css" rel="stylesheet">
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
</style>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>基本奖金明细筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-lg-6">		
								<div class="form-group m-b">
									<label class="col-sm-2 control-label" id="case_date">归属月份</label>
									<div id="datepicker"
										class="input-group input-medium date-picker input-daterange"
										data-date-format="yyyy-mm-dd">
										<input id="dtBegin" name="dtBegin" class="form-control"
											style="font-size: 13px;" type="text" value=""
											placeholder="起始日期"> <span class="input-group-addon">到</span>
										<input id="dtEnd" name="dtEnd" class="form-control"
											style="font-size: 13px;" type="text" value=""
											placeholder="结束日期" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-6">						
								<div  class="form-group m-b">
									<label class="col-sm-2 control-label" id="case_date">所在组</label>
									<input id="orgName" type="text" class="form-control" style="width:200px">
								</div>
							</div>
							<div class="col-lg-6">
								<div  class="form-group m-b">
									<label class="col-sm-2 control-label" id="case_date">人员</label>
									<input id="userName" type="text" class="form-control" style="width:200px">
								</div>
							</div>
						</div>
						<div class="row">
						<div class="col-lg-6">
							<div  class="form-group m-b">
								<label class="col-sm-2 control-label" id="case_date">案件地址</label>
								<input id="caseAddr" type="text" class="form-control" style="width:200px">
							</div>
							</div>
							<div class="col-lg-6">
							<div  class="form-group m-b">
								<label class="col-sm-2 control-label" id="case_date">案件编号</label>
								<input id="caseCode" type="text" class="form-control" style="width:200px">
							</div>
							</div>
						</div>
						<span class="input-group-btn ">
							<button id="searchButton" type="button"
								class="btn btn-primary pull-left">查询</button>
						</span>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>基本奖金明细</h5>
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
		
        <div style="display:none;" id="test">
         <form id="excelInForm"  method="post" enctype="multipart/form-data" action="${ctx}/award/uploadBaseImport"> 
                <div class="modal-body">
                <input type="hidden" id="inType" value="" />
           		    <label class="col-sm-7 control-label">
           		    <input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
           		    </label>
          <div class="col-sm-3"></div>
                </div> 
              </form>
        </div>
		
       <!-- 失败数据 -->
     
       
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	
	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
		 <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  <!-- iCheck --> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
     <!-- 弹出框插件 -->
    <script src="${ctx}/js/plugins/layer/layer.js"></script>
    <script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
    
	 <script src="${ctx}/js/trunk/award/baseImport.js"></script> 	
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <script>
    <c:if test="${not empty fList}">
    	var hasError=true;
 	</c:if>
    <c:if test="${empty fList}">
		var hasError=false;
	</c:if>
 	
    </script>
 </content>
</body>
</html>
