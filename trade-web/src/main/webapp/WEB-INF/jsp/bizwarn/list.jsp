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
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    
    <!-- 上传相关 -->
    <link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
	
	<!-- 分页控件 -->
	<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
	
	<link href="${ctx}/css/processinglist/popmac.css" rel="stylesheet" />
	
	<style>
		.mr5{margin:0 5px 0 30px;}
		#div_f{
			display: none;
		}
	</style>
	
	<script>
		var optTransferRole=false;
		<shiro:hasPermission name="TRADE.PRSEARCH.TRANSFER">
			optTransferRole=true;
		</shiro:hasPermission>
	</script>
	
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
		.apply-table tr em{font-style:normal;font-size:12px;line-height:18px;}
		.apply-table tbody a{color:#1a5f8e;}
	</style>
	
	<script type="text/javascript">
		var idList = [ 1 ];
		var pkid ='';
		var taskitem = "";
		var caseCode = "";
		var prCode = '';
	</script>
	
</head>
<body>

	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins" style="margin-top: 10px;margin-bottom: 5px;">
				<div class="ibox-title">
					<h4>商贷流失预警案件列表</h4>
				</div>
				<div class="ibox-content" style="padding-bottom: 5px;">
					<form action="${ctx }/quickGrid/findPage?xlsx" class="form-horizontal" method="post" id ='myForm'>
					    <div class="jqGrid_wrapper">
					    	<input type="hidden" id="ctx" value="${ctx}"/>
					    	<div class="row form-group">
			    				<label class="col-md-1  control-label">预警类型</label>
			    				<div class="col-md-3">
			    					<select id="warnType" class="form-control pull-left">
			    						<option value="">请选择</option>
			    						<option value="LOANLOSS">贷款流失</option>
			    					</select>
			    				</div>
			    				<label class="col-md-1  control-label">状态</label>
			    				<div class="col-md-3">
			    					<select id="status" class="form-control pull-left">
			    						<option value="">请选择</option>
			    						<option value="0" <c:if test="${status == '0' }">selected="selected"</c:if>>生效</option>
			    						<option value="1" <c:if test="${status == '1' }">selected="selected"</c:if>>解除</option>
			    					</select> 
			    				</div>
				    		</div> 
					        <div class="row form-group">
			        			<label class="col-md-1  control-label">案件编号</label>
			    				<div class="col-md-3">
			    					<input type="text" id="caseCode" name="search_caseCode" class="form-control"/>
			    				</div>
			
			        			<label class="col-md-1  control-label">产证地址 </label>
			    				<div class="col-md-3">
			    					<input type="text" id="addr" name="search_propertyAddr" class="form-control"/>
			    				</div>
							</div>
							<div class="row form-group">
			        			<label class="col-md-1  control-label">预警时间</label>
		    					<div class="col-md-3" style='margin-left: 0px'>
			    					<div id="datepicker_0"
										class="input-group input-medium date-picker input-daterange pull-left"
										data-date-format="yyyy-mm-dd">
										<input id="warnTimeStart" name="search_warnTimeStart" class="form-control date-picker-input"
											style="font-size: 13px;" type="text" value=""
											placeholder="起始日期"> <span class="input-group-addon">到</span>
										<input id="warnTimeEnd"  class="form-control date-picker-input"
											style="font-size: 13px;" type="text" value=""
											placeholder="结束日期" />
											<input type="hidden" name='search_warnTimeEnd' id='search_completeTimeEnd' value=''>
									</div>
								</div>
								<div class="col-md-4">
									<a class='btn btn-primary mr5' id='addrSearchButton'>搜索</a>
									<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
								</div>
							</div>
					     </div>                         
					</form>
				</div>
			</div>
		</div>
	</div>
    
	<div class="main">
		<div class="apply-wrap">
			<div class="table">
				<div id="bizwarnList"></div>
			</div>
		</div>
	</div>
	
	<content tag="local_script">
	    <!-- Mainly scripts -->
		<script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	    <!-- Peity -->
	    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	
		<script src="${ctx}/js/trunk/bizwarn/list.js?v=1.0.3"></script>
		
		<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		
		<!-- 分页控件  -->
	    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script src="${ctx}/js/plugins/jquery.custom.js"></script>
		
		<script id="template_bizwarnList" type="text/html">
         	{{each rows as item index}}
                 {{if index%2 == 0}}
 				    <tr class="tr-1">
                 {{else}}
                    <tr class="tr-2">
                 {{/if}}
						<td><a href="../case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank" style="text-decoration:underline;">{{item.caseCode}}</a></td>
						<td>{{item.propertyAddress}}</td>
						<td>{{item.warnType1}}</td>
						<td>{{item.status1}}</td>
						<td>{{item.warnDatetime}}</td>
						<td>{{item.relieveDatetime}}</td>
						<td class="btn-g">
							<a href="javascript:relieve('{{item.caseCode}}','{{item.status}}')" class="btn-y" target='_blank'>解除</a>
						</td>
					</tr>
			{{/each}}
	 	</script> 
	</content>
</body>
</html>
