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
<link href="${ctx}/css/plugins/switch/bootstrap-switch.min.css" rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<!-- 弹出框插件 -->
<%-- <link href="${ctx}/css/plugins/layer/layer.css" rel="stylesheet">
<link href="${ctx}/css/plugins/layer/layer.ext.css" rel="stylesheet"> --%>
<!-- 时间控件 -->
<link href="${ctx}/js/plugins/dateSelect/dateSelect.css?v=1.0.2" rel="stylesheet"></script>
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
.org-form-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width: 100%;
    font-size: 14px;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>

		<div class="row">
			<div class="ibox">
			<div class="ibox-title">
				<div class="bonus-m">
					<input type="button" class="btn btn-warning m-r-sm" value="&lt;" >
                    <h5 class="month">yyyy/MM月</h5>
                    <input type="button" class="btn btn-warning m-r-sm disable" disabled value="&gt;" style="margin-left:10px;">
				</div>
             </div>
			<div class="ibox-content">
					<form method="get" class="form-horizontal">
					 	<div class="row">
							<div class="col-lg-3 col-md-4">
                                   <div class="form-group">
                                      <label class="col-lg-3 col-md-3 control-label font_w">案件编号:</label>
                                       <div class="col-lg-9 col-md-9">
                                           <input type="text" class="form-control" id="caseCode" name="caseCode">
                                       </div>
                                   </div>
                               </div>
                               <div class="col-lg-3 col-md-4">
                                   <div class="form-group">
                                       <label class="col-lg-3 col-md-3 control-label">产证地址:</label>
                                       <div class="col-lg-9 col-md-9">
                                            <input type="text" class="form-control" id="propertyAddr" name="propertyAddr">
                                       </div>
                                   </div>
                               </div>
                               <div class="col-lg-3 col-md-4">                                   
                                  <button id="searchButton" type="button" class="btn btn-primary">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <button id="exportExcelButton" type="button" class="btn btn-primary" onclick="javascript:exportToExcel()">导出至Excel</button>
                               </div>
			             </div>
					</form>
				</div>
			</div>
		</div>
	 <div class="row">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>个人绩效奖金明细</h5>
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
	
       
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	
	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
		 <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  <!-- iCheck --> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
		<script	src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script>
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <!-- 组织控件 --> 
    <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <!-- 弹出框插件 -->
    <script src="${ctx}/js/plugins/layer/layer.js"></script>
    <script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
    <!-- 日期控件 -->
    <script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>
    <!-- 列表 -->
    <script src="${ctx}/transjs/kpi/personBonus.list.js"></script>
    <script src="${ctx}/js/plugins/jquery.custom.js"></script>
    <script>
    var ctx = "${ctx}";
    var belongM = "${belongM}";
    var belongLastM = "${belongLastM}";
 	// 是否显示错误信息
	<c:if test="${not empty errorList}">
    	var hasError=true;
 	</c:if>
    <c:if test="${empty errorList}">
		var hasError=false;
	</c:if>
	var sw;
	//初始化日期控件
	var monthSel=new DateSelect($('.bonus-m'),{max:new Date(),moveDone:reloadGrid});
    $(document).ready(function(){
    	// 初始化列表
    	PersonBonusList.init('${ctx}','/quickGrid/findPage','table_list_1','pager_list_1','${belongM}');
    	// 滑块
    	sw=$('#moSwitch').bootstrapSwitch({
    		'onText':"上月",
    		'offText':'当月'
    	}).on('switchChange.bootstrapSwitch', function(e, data) {
		});
    	// 查询
		$('#searchButton').click(function() {
			reloadGrid();
		});
		
    });
    
    function reloadGrid(bm){
			if(!bm){
				bm=monthSel.getDate().format('yyyy-MM-dd');	
			}else{
				bm=bm.format('yyyy-MM-dd');
			}
			
			var data = {};
    	data.search_caseCode =$.trim( $('#caseCode').val() ); 
    	data.search_propertyAddr =$.trim( $('#propertyAddr').val() ); 
    	data.argu_belongMonth = monthSel.getDate().format('yyyy-MM-dd');
    	data.queryId="personBonusList";
    	
	    $("#table_list_1").jqGrid('setGridParam',{
    		datatype:'json',
    		mtype:'post',
    		postData:data
    	}).trigger('reloadGrid'); 
	}
    function exportToExcel() {
    	$.exportExcel({
	    	ctx : "${ctx}",
	    	queryId : 'personBonusList',
	    	colomns : ['CASE_CODE','PARTICIPANT','PROPERTY_ADDR','GUOHU_TIME','CLOSE_TIME','SRV_CODE','BASE_AMOUNT','SRV_PART_IN','SATISFACTION','MKPI','KPI_RATE_SUM','SRV_PART','AWARD_KPI_MONEY'],
	    	data : {search_caseCode:$('#caseCode').val(),search_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd')}
	    }) 
     }
    </script>
 </content>
</body>
</html>
