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
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
	
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    
    <!-- 分页控件 -->
	<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
    
    <style>
		*{padding:0;margin:0;box-sizing:border-box;}
		.main{background-color:#f3f3f3;}
		.apply-wrap{background-color:#fff;font-family:'微软雅黑','Arial';}
		.apply-wrap .table{font-size:14px;}
		.apply-wrap .table{width:100%;background-color:#fff;}
		.apply-wrap .apply-table{width:100%;border:1px solid #eaeaea;border-right:0;}
		.apply-wrap .apply-table th{font-weight:normal;color:#fff;height:35px;background-color:#52cdec;}
		.apply-table th,.apply-table td{text-align:left;padding-left:10px;border-right:1px solid #eaeaea;}
		.apply-table tr td{color:#333;}
		.apply-table .tr-1{background-color:#fff;}
		.apply-table .tr-2{background-color:#f4f4f4;}
		.apply-table tr {height:32px!important;}
		.apply-table tr:nth-child(odd) td{padding:8px 0 3px 10px;}
		.apply-table tr:nth-child(even) td{padding:3px 0 12px 10px;}
		.apply-table tr .color-666{color:#666;}
		.apply-table tr .fs12{font-size:12px;}
		.apply-table tr .btn-y{color:#fff;padding:2px 10px;border-radius:3px;background-color:#f9ad58;text-decoration:none;}
		.triangle-up,.triangle-down{width:0;height:0;border-left:7px solid transparent;border-right:7px solid transparent;position:relative;left:8px;top:16px;}
		.triangle-up{border-bottom:10px solid #fff;top:-13px;}
		.triangle-down {border-top:10px solid #fff;}
		.fs12 i,.sqr i{color:#fff;font-size:12px;font-style:normal;padding:0px 2px;margin-right:5px;border-radius:2px;background-color:#52cdec;}
		.fs12 i.valid-label{background-color:#00cb1d;}
		.fs12 i.invalid-label{background-color:#ccc;}
		.sqr i.jl-label{background-color:#ee6384;margin-left:5px;}
		.sqr i.yc-label{background-color:#52cdec;margin-left:5px;}
		.fs12 span{display:block;padding:1px 0;}
		.apply-table tr .sq-state{padding:1px 0 1px 10px !important;}
		.apply-table tr:nth-child(odd) td.btn-g{padding:8px 0 4px 8px;}
		.apply-table tr:nth-child(even) td.btn-g{padding:5px 0 10px 8px;}
		.apply-table tr td.invalid{width:150px;padding-right:10px !important;}
		.apply-table tr em{font-style:normal;font-size:13px;line-height:18px;}
		.apply-table tbody a{color:#1a5f8e;}
	</style>
    
    <script>
		var optTransferRole=false;
		<shiro:hasPermission name="TRADE.PRSEARCH.TRANSFER">
			optTransferRole=true;
		 </shiro:hasPermission>
	</script>
	
</head>

<body>
	
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<form action="#" method="post" id="excelForm"></form>
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
	<input type="hidden" id="prStatus" value="0"/>
	
	
	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins" style="margin-top: 10px;margin-bottom: 5px;">
				<div class="ibox-title">
					<h5>待处理产调</h5>
				</div>
				<div class="ibox-content" style="padding-bottom: 5px;">
					<div class="row form-group">
	    				<label class="col-md-1  control-label" style="text-align: center;padding-top: 7px;">行政区域</label>
	    				<div class="col-md-3">
	    					<aist:dict id="distCode" clazz="form-control pull-left" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
	    				</div>
	    				<div class="col-md-4">
	    					<a class='btn btn-primary' id='searchButton'>搜索</a>
						    <a class="btn btn-primary" onclick="exportToExcel();" id="expToexcel">导出产调至Excel</a>
	    				</div>
		    		</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="main">
		<div class="apply-wrap">
			<div class="table">
				<div id="processWaitList"></div>
			</div>
		</div>
	</div>
	
	
	<content tag="local_script">
	    
	    <!-- Peity -->
	    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	
	    <!-- jqGrid -->
	    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	
	    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
	    <script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script src="${ctx}/js/trunk/property/processWaitList.js?v=1.0.1"></script>
	 	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	 	
	 	<!-- 分页控件  -->
	    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	 	
<!-- 	 	<span><i class="invalid-label">受</i></span> -->
<!-- 		<span><i class="invalid-label">完</i></span> -->
	 	<script id="template_processWaitList" type= "text/html">
         	{{each rows as item index}}
                 {{if index%2 == 0}}
 				    <tr class="tr-1">
                 {{else}}
                    <tr class="tr-2">
                 {{/if}}
						<td>{{item.DIST_CODE}}</td>
						<td>{{item.PROPERTY_ADDR}}</td>
						<td>{{item.applyOrgName}}</td>
						<td rowspan="2" class="fs12 sq-state">
							<span><i class="sq-label">申</i>{{item.PR_APPLY_TIME}}</span>
						</td>
						<td class="sqr">申请人：{{item.PR_APPLIANT}}
							{{if item.CHANNEL == '经纪人'}}
								<i class="jl-label">经</i>
							{{else}}
                 				<i class="yc-label">誉</i>
							{{/if}}
						</td>
						<td>
							{{if wrapperData.optTransferRole}}
								<button type="button" class="btn btn-warning btn-xs" id="teamCode" name="teamCode" readonly="readonly" onclick="showOrgSelect({{item.PKID}})" value="{{item.prDistrictId}}" >转组</button>
                 			{{else}}

                 			{{/if}}
						</td>
					</tr>
				{{if index%2 == 0}}
 				    <tr class="tr-1">
                {{else}}
                    <tr class="tr-2">
                {{/if}}
						<td></td>
						<td></td>
						<td>区董：{{item.orgMgr}}</td>
						<td>执行人：</td>
						<td></td>
					</tr>
			{{/each}}
	 </script> 
	</content>
</body>
</html>
