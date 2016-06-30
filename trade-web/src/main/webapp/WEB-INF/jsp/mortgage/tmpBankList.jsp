<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Add fancyBox main JS and CSS files -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
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
							<label class="col-md-1 control-label">产证地址</label>
							<div class="col-md-11">
								<input id="caseAddress" type="text" class="form-control">
							</div>
						</div>
						<div class="row form-group">
							<label class="col-md-1 control-label">案件编号</label>
							<div class="col-md-5">
								<input id="caseCode" type="text" class="form-control">
							</div>
							<label class="col-md-1 control-label">CTM编号</label>
							<div class="col-md-5">
								<input id="ctmCode" type="text" class="form-control">
							</div>
						</div>
						<div class="row form-group">
							<label class="col-md-1 control-label">处理状态</label>
							<div class="col-md-5">
								<select name="proStatus" id="proStatus" class="form-control">									
									<option value="0" selected="selected">未处理</option>
									<option value="1">已处理</option>
								</select>
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
	<div class="portlet-body" style="display: block;">
		<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
	</div>
	<content tag="local_script">
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script> <!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js"></script> <!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js"></script> <!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js"></script>
	<script>
	var tmpBankList;
	$(document).ready(function(){
		$(".fancybox").fancybox({

			fitToView : false,
			width : '90%',
			height : '100%',
			autoSize : false,
			closeClick : false,
			openEffect : 'none',
			closeEffect : 'none'
		});

		fBox=$('#alertOper').fancybox({
			type : 'iframe',
			padding : 0,
			margin : 0,
			autoScale : false,
			fitToView : false,
			width : '90%',
			height : '100%',
			autoDimensions : true,
			showCloseButton : true,
			close : true,
			helpers : {
				overlay : {
					closeClick : false
				}
			},
			iframe : {
				preload : false
			},
			openEffect : 'none',
			closeEffect : 'none',
			afterClose : function() {
				tmpBankList.trigger("reloadGrid");//刷新列表
			}
		});
		$("#searchButton").click(function (){
			var postData={
					queryId : "queryTmpBankQuery",
					search_propertyAddr: $('#caseAddress').val(),
					search_caseCode:$("#caseCode").val(),
					search_ctmCode:$("#ctmCode").val(),
					proStatus:$("#proStatus").val()
				};
			 tmpBankList.setGridParam({
					"postData":postData ,
					"page":1 ,
					datatype : "json"
				}).trigger('reloadGrid');
		 });
	});
	
	tmpBankList= $("#table_list_1").jqGrid(
				{
					url : "../quickGrid/findPage",
					mtype : 'POST',
					height : 550,
					datatype:"json",
					autowidth : true,
					shrinkToFit : true,
					forceFit : true,
					rowNum : 20,
					/* rowList: [10, 20, 30], */
					colNames : [ 'id', '案件编号', 'CTM编号','案件地址',
							'贵宾服务部','组别', '总监', '交易顾问','处理状态','处理人','处理时间','主备选银行','操作'],
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
						width : 140
					}, {
						name : 'CTM_CODE',
						index : 'CTM_CODE',
						width : 140
					},{
						name : 'PROPERTY_ADDR',
						index : 'PROPERTY_ADDR',
						width : 150
					}, {
						name : 'DISTRICT_NAME',
						index : 'DISTRICT_NAME',
						width : 100
					}, {
						name : 'TEAM_NAME',
						index : 'TEAM_NAME',
						width : 100
					}, {
						name : 'DIRECTOR',
						index : 'DIRECTOR',
						width : 60
					}, {
						name : 'CONSULTANT',
						index : 'CONSULTANT',
						width : 60
					},{
						name : 'PRO_STATUS',
						index : 'PRO_STATUS',
						width : 60
					}, {
						name : 'PUSER',
						index : 'PUSER',
						width : 50
					},  {
						name : 'PTIME',
						index : 'PTIME',
						width : 70
					},{
						name : 'IS_MAIN_LOAN_BANK',
						index : 'IS_MAIN_LOAN_BANK',
						width : 70
					},
					{	name : 'PKID',
						index : 'PKID',
						width : 60,
						formatter : function(cellvalue,options,rawObject) {
							return "<button type=\"button\" class=\"btn btn-warning pull-left\" onclick=\"showTmpBankProcessBox('"+rawObject.PKID+"')\">处理</button>";
						}
					}
					],
					pager : "#pager_list_1",
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",
					postData : {
						queryId : "queryTmpBankQuery",
						search_propertyAddr: $('#caseAddress').val(),
						search_caseCode:$("#caseCode").val(),
						search_ctmCode:$("#ctmCode").val(),
						proStatus:$("#proStatus").val()
					}

				});
	
		function showTmpBankProcessBox(pkid) {
			$('#alertOper').attr("href",
					ctx + "/task/box/tmpBank?pkid=" + pkid);
			$("#alertOper").trigger('click');
		}

    </script> </content>
</body>
</html>
