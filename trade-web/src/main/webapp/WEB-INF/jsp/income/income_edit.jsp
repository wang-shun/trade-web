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
					<h5>案件筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						
						<div id="dateDiv" class="form-group m-b">
							<label class="col-sm-2 control-label" id="case_date">结案日期</label>
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
						
						<div class="input-group m-b">
							<div id="select_div_1" class="input-group-btn ">
								<select id="inTextType" data-placeholder="搜索条件设定"
									class="btn btn-white chosen-select">
									<option value="0" selected>客户姓名</option>
									<option value="1">物业地址</option>
									<option value="2">经纪人姓名</option>
									<option value="4">经办人姓名</option>
									<option value="3">所属分行</option>
								</select>
							</div>
							<input id="inTextVal" type="text" class="form-control" style="width:400px">

						</div>
						<span class="input-group-btn ">
							<button id="searchButton" type="button"
								class="btn btn-primary pull-right">查询</button>
						</span>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>我的案件列表</h5>
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
		
        <div class="ibox-content">
            <div  class="row">
               <a role="button" id="trueCountExcel" class="btn btn-primary btn-xm" href="javascript:caseTrueCountExcel()">计件案件导出 </a>
   			   <a role="button"data-toggle="modal" class="btn btn-primary btn-xm btn-activity" href="#pr-modal-form">有效计件案件导出</a> 
               <a role="button" class="btn btn-primary btn-xm btn-activity" href="#">成交报告导出</a>
               <a role="button" id="mortageSTExcel" class="btn btn-primary btn-xm" href="javascript:mortageSTExcel()">贷款对账导出</a>
            </div>
            
            <div  class="row">
               <a role="button"class="btn btn-primary btn-xm" href="javascript:showExcelModal(1)">计件分配导入 </a>
   			   <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showExcelModal(2)">评分导入</a> 
               <a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showExcelModal(3)">收益导入</a>
               <a role="button" id="monthCountExcel" class="btn btn-primary btn-xm" href="javascript:showExcelModal(4)">月完成单导入</a>
               
            </div>
        </div>
		<!-- 收益导入 -->
        <div id="excel-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
          <div class="modal-dialog" style="width:1200px">
             <div class="modal-content">
                 <div class="modal-header">
				   <button type="button" class="close" data-dismiss="modal"
				      aria-hidden="true">×
				   </button>
				   <h4 class="modal-title" id="excel-modal-title">
				      	请上传附件
				   </h4>
				</div>
				
                <form id="excelInForm"  method="post" enctype="multipart/form-data"> 
                <div class="modal-body">
                <input type="hidden" id="inType" value="" />
           		    <label class="col-sm-7 control-label">
           		    <input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
           		    </label>
          <div class="col-sm-3"></div>
                </div> 
                
              </form>
                <div class="modal-footer">
		            <button type="button" class="btn btn-default"
		               data-dismiss="modal">关闭
		            </button>
		            <button type="button" class="btn btn-primary" onclick="javascript:excelIn()">
		                                提交
		            </button>
                </div>
                
             </div>
          </div>
       </div>  
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
		
    <script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/trunk/income/income_edit.js"></script>
 </content>
</body>
</html>
