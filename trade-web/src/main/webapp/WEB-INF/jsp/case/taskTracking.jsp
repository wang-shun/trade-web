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
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">

<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link
	href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"
	rel="stylesheet">

<style type="text/css">
.radio label {
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
.org-label-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width: 45%;
    font-size: 14px;
}
#inTextVal{width:50%}
.chosen-container{float:left;margin-right:10px;margin-left:15px}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
[id^="dateDiv_"]:not(#dateDiv_0) .chosen-container{margin-left:33px}
</style>
</head>

<body>

	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>案件筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						
						<div class="row form-group">
							<label class="col-md-2 control-label">产证地址</label>
							<div class="col-md-10">
								<input id="caseAddress" type="text" class="form-control">
							</div>
						</div>
						<div class="row form-group">
							<label class="col-md-2 control-label">案件编号</label>
							<div class="col-md-4">
								<input id="caseCode" type="text" class="form-control">
							</div>
							<label class="col-md-2 control-label">CTM编号</label>
							<div class="col-md-4">
								<input id="ctmCode" type="text" class="form-control">
							</div>
						</div>
						
						<div class="row form-group">
							<div class="col-md-2"><button id="searchButton" type="button" class="btn btn-warning pull-right">查询</button></div>		
						</div>
					</form>
				</div>
			</div>
		</div>
			<div class="col-lg-12 col-md-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>案件列表</h5>
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
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	<content tag="local_script">
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script>
	 $("#table_list_1").jqGrid(
				{
					url : "../quickGrid/findPage",
					mtype : 'POST',
				
					datatype:'local',
					height : 550,
					autowidth : true,
					shrinkToFit : true,
					forceFit : true,
					rowNum : 20,
					/* rowList: [10, 20, 30], */
					colNames : [ 'id', '案件编号', 'CTM编号','产证地址',
							'经纪人','所属分行', '上家', '下家','案件状态','经办人','主管'],
					colModel : [ {
						name : 'PKID',
						index : 'PKID',
						align : "center",
						width : 0,
						key : true,
						resizable : false,
						hidden : true
					}, {
						name : 'CASE_CODE',
						index : 'CASE_CODE',
						width : 75
					}, {
						name : 'CTM_CODE',
						index : 'CTM_CODE',
						width : 75
					},{
						name : 'PROPERTY_ADDR',
						index : 'PROPERTY_ADDR',
						width : 150
					}, {
						name : 'AGENT_NAME',
						index : 'AGENT_NAME',
						width : 35
					}, {
						name : 'AGENT_ORG_NAME',
						index : 'AGENT_ORG_NAME',
						width : 60
					}, {
						name : 'SELLER',
						index : 'SELLER',
						width : 30
					}, {
						name : 'BUYER',
						index : 'BUYER',
						width : 30
					},{
						name : 'STATUS',
						index : 'STATUS',
						width : 30
					}, {
						name : 'PROCESSOR_ID',
						index : 'PROCESSOR_ID',
						width : 70
					},  {
						name : 'MANAGER',
						index : 'MANAGER',
						width : 120
					}

					],
					pager : "#pager_list_1",
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",

					onSelectRow : function(rowid, status) {
						/* var rowData = $("#table_list_1")
								.jqGrid('getRowData', rowid);
						window.location.href = ctx+"/case/caseDetail?&caseId="
								+ rowData.PKID; */
					},
					postData : {
						queryId : "queryCastTrackingListItemList",
						search_propertyAddr: $('#caseAddress').val(),
						search_propertyAddr: $('#caseAddress').val(),
						search_caseCode:$("#caseCode").val(),
						search_ctmCode:$("#ctmCode").val()
					}

				});
	 $("#searchButton").click(function (){
		 $("#table_list_1").setGridParam({
				"postData" : {
					queryId : "queryCastTrackingListItemList",
					search_propertyAddr: $('#caseAddress').val(),
					search_caseCode:$("#caseCode").val(),
					search_ctmCode:$("#ctmCode").val()
				},
				"page":1 ,
				datatype : "json"
			}).trigger('reloadGrid');
	 });
    </script> </content>
</body>
</html>
