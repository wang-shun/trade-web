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
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">


<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<style type="text/css">
#selectDiv {
	width: 480px;
}

#inTextType_chosen {
	margin-left: 40px;
}
.ui-state-hover{
	cursor:pointer;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
#inTextVal{width:42%;}
#inTextType_chosen{margin-left:0}
.chosen-container{float:left;margin-right:10px}
</style>
</head>

<body>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />

	<div class="row">
		
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-lg-12 col-md-12">
			<div class="ibox float-e-margins">	
				<div class="ibox-title">
					<h5>任务筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
							<label class="col-md-2 control-label">红绿灯</label>
							<div class="checkbox i-checks radio-inline">
								<label> <input type="radio" value="0" id="lamp0"
									name="lampRadios"> 全部
								</label> <label> <input type="radio" value="1" id="lamp1"
									name="lampRadios"> <span class="label label-danger">红灯${Lamp[2]}</span>
								</label> <label> <input type="radio" value="2" id="lamp2"
									name="lampRadios"> <span class="label label-warning">黄灯${Lamp[1]}</span>
								</label> <label> <input type="radio" value="3" id="lamp3"
									name="lampRadios"> <span class="label label-info">绿灯${Lamp[0]}</span>
								</label>

							</div>
						</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
							<label class="col-md-3 control-label">授权代办</label>
							<div class="radio i-checks radio-inline">
								<label> <input type="radio" value="0" id="owner0"
									name="ownerRadios"> 全部
								</label> <label> <input type="radio" value="1" id="owner1"
									name="ownerRadios"> 本身
								</label> <label> <input type="radio" value="2" id="owner2"
									name="ownerRadios"> 代办
								</label>
							</div>
						</div>
							</div>
							
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label">请选择</label>
									<div class="control-div">
									       <select id="inTextType" data-placeholder= "搜索条件设定"
		                                        class= "btn btn-white chosen-select" style="float :left;" onchange="intextTypeChange()">
											<option value="1" selected>物业地址</option>
											<option value="0" >客户姓名</option>
											<option value="2">经纪人姓名</option>
											<option value="3">所属分行</option>
											<option value="4">案件编号</option>
											<option value="5">CTM编号</option>
										</select>
								<input id="inTextVal" type="text" class="form-control pull-left">
									</div>
							</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label">请选择</label>
									<div class="col-md-4">
										<aist:dict id="taskDfKey" name="taskDfKey"
										clazz="form-control m-b" display="select"
										dictType="part_code" defaultvalue="" />
								
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
										<button id="searchButton" type="button"
														class="btn btn-warning pull-left">查询</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
			<form method="get" class="form-horizontal"></form>

			<div class="col-lg-12 col-md-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>我的任务列表</h5>
						<shiro:hasPermission name="TRADE.TASK.RANK">  
							<button id="orderByButton" type="button" class="btn btn-warning pull-right">排序</button>
						</shiro:hasPermission>
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
	</div>

	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	<script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js">
		<!-- iCheck -->
		<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/js/trunk/task/mytask_list.js"></script> </content>
</body>
</html>
