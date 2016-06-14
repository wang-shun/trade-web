<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
    <link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
	<style>
		.mr5{margin:0 5px 0 30px;}
	</style>
</head>

<body>


<div class="">
	<div class="col-lg-13">
	     <div class="">
	         <div class="ibox-title">
	            <h4>已完成产调</h4>
	            </div>
	        </div>
     </div>
</div>
  <form action="${ctx }/quickGrid/findPage?xlsx" class="form-horizontal" method="post" id ='myForm'>
<div class="ibox-content">
    <div class="jqGrid_wrapper">
     <input type="hidden" id="xlsx" name="xlsx" value="xlsx"/>
   <input type="hidden" id="queryId" name="queryId" value="querySuccessList"/>
    <input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="prDistrictId" name="search_prDistrictId" value="${prDistrictId}"/>
<input type="hidden" id="prStatus"name="search_prStatus" value="2"/>
    <input type="hidden" name="colomns" value="PROPERTY_ADDR,orgName,PR_CAT,PR_APPLIANT,APPLIANT_EMPLOYEE_CODE,PR_EXECUTOR,PR_APPLY_TIME,PR_ACCPET_TIME,PR_COMPLETE_TIME,IS_SUCCESS,UNSUCCESS_REASON,DIST_CODE,QUDS,CHANNEL">
    	<div class="row form-group">
    				<label class="col-md-1  control-label">行政区域</label>
    				<div class="col-md-3">
    					<aist:dict id="distCode" clazz="form-control pull-left" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
    				</div>
    				<label class="col-md-1  control-label">产调项目</label>
    				<div class="col-md-3">
    					<input type="text" id="prCat" name="search_prCat"class="form-control"/>  
    				</div>
    		</div>
        <div class="row form-group">
        			<label class="col-md-1  control-label">所属分行</label>
    				<div class="col-md-3">
    					<input type="text" id="grpOrgName" name="search_grpOrgName" class="form-control"/>
    				</div>

        			<label class="col-md-1  control-label">物业地址 </label>
    				<div class="col-md-3">
    					<input type="text" id="addr" name="search_propertyAddr" class="form-control"/>
    				</div>
			
		</div>

		<div class="row form-group">
 
        			<label class="col-md-1  control-label">产调执行人</label>
    				<div class="col-md-3">
    					<input type="text" id="acuUser" name="search_acuUser" class="form-control"/>
    				</div>

        			<label class="col-md-1  control-label">产调申请人 </label>
    				<div class="col-md-3">
    					<input type="text" id="auUser" name="search_auUser" class="form-control"/>
    				</div>
		</div>
		<div class="row form-group">       		
        			<label class="col-md-1  control-label">是否有效</label>
    				<div class="col-md-3">
    					<aist:dict id="isSuccess" clazz="form-control pull-left" name="search_isSuccess" display="select"  dictType = "nullityTag" />
    				</div>
        			<label class="col-md-1  control-label">来源 </label>
    				<div class="col-md-3">
    					<select ltype="select" id="prChannel" name="search_prChannel" class="form-control pull-left" validate="" onchange="" ligerui="">
    					<option value="" selected="selected">请选择</option>
    					<option value="0">内部</option>
    					<option value="1">经纪人</option>
    					</select>
    				</div>
		</div>
		<div class="row form-group">
        			<label class="col-md-1  control-label">所属区董</label>
    				<div class="col-md-3">
    					<input type="text" id="quds" name="search_quds" class="form-control"/>
    				</div>
		</div>
		<div class="row form-group">
        			<label class="col-md-1  control-label">完成时间</label>
    					<div class="col-md-11" style='margin-left: 0px'>
    					<div id="datepicker_0"
								class="input-group input-medium date-picker input-daterange pull-left"
								data-date-format="yyyy-mm-dd">
								<input id="completeTimeStart" name="search_completeTimeStart" class="form-control date-picker-input"
									style="font-size: 13px;" type="text" value=""
									placeholder="起始日期"> <span class="input-group-addon">到</span>
								<input id="completeTimeEnd"  class="form-control date-picker-input"
									style="font-size: 13px;" type="text" value=""
									placeholder="结束日期" />
									<input type="hidden" name='search_completeTimeEnd' id='search_completeTimeEnd' value=''>
							</div>
					</div>
		</div>
		
		<div class="row form-group">
			<a class='btn btn-primary mr5' id='addrSearchButton'>搜索</a>
			<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
		</div>
		     
		<hr>
        <div class='row'>
        	<div class='col-md-12'>
			<table id="table_property_list"></table>
         	<div id="pager_property_list"></div>
         </div>
         </div>
         <div class='row form-group'>
         	<div class='col-md-12'>
    		<a class='btn btn-primary pull-left'  onclick="submitForm();return false" >导出产调至Excel</a>
    		</div>
    	</div>
     </div>                         
</div>
</form>    
<content tag="local_script">
    <!-- Mainly scripts -->
	<script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

	<script src="${ctx}/js/trunk/property/successList.js?v=1.0.3"></script>
	<script src="${ctx}/js/trunk/property/propertyByaddr.jqgridSearch.js"></script>
	<script>
	function report(){
		var data=packData();
	}
	function submitForm(){
		$('#search_completeTimeEnd').val($('#completeTimeEnd').val()==''?'':$('#completeTimeEnd').val()+' 23:59:59');
		 $('#myForm').submit();
	}
	jQuery(document).ready(function() {
		$('.date-picker-input').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		 var addr = $("#addr").val();
		 JQGrid_propertyByaddSearch.init('table_property_list','addrSearchButton',addr);
	});
	//清空
	$('#cleanButton').click(function() {
		$("input[name='search_prCat']").val('');
		$("input[name='search_propertyAddr']").val('');
		$("input[name='search_grpOrgName']").val('');
		$("input[name='search_acuUser']").val('');
		$("input[name='search_auUser']").val('');
		$("input[name='search_quds']").val('');
		$("input[name='search_completeTimeStart']").val('');
		$("input[id='completeTimeEnd']").val('');
	});
	</script>

</content>
</body>
</html>
