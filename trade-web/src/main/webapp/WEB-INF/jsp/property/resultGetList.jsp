<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
		
    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    
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
		.apply-table tr em{font-style:normal;font-size:12px;line-height:18px;}
		.apply-table tbody a{color:#1a5f8e;}
	</style>
    
</head>

<body>

	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="userId" value="${userId}"/>
	<input type="hidden" id="prStatus" value="2"/>
	
	<div class="row">
		<div class="col-md-12">
			<div class="ibox float-e-margins" style="margin-top: 10px;margin-bottom: 5px;">
				<div class="ibox-title">
					<h5>我的产调结果</h5>
				</div>
				<div class="ibox-content" style="padding-bottom: 5px;">
					<div class="row form-group">
	    				<label class="col-md-2  control-label" style="text-align: center;padding-top: 7px;">产证地址 </label>
	    				<div class="col-md-3">
	    					<input type="text" id="addr" class='form-control'/>
	    				</div>
	    				<div class="col-md-4">
	    					<a class='btn btn-primary' id='addrSearchButton'>搜索</a>
	    				</div>
		    		</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="main">
		<div class="apply-wrap">
			<div class="table">
				<div id="resultGetList"></div>
			</div>
		</div>
	</div>
		
	<content tag="local_script">
	    <!-- Mainly scripts -->
	
	    <!-- Peity -->
	    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>
	
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
			
		<!-- Add Button helper (this is optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>
	
	    <!-- jqGrid -->
	    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	
		<script src="${ctx}/js/trunk/property/resultGetList.js?v=1.0.1"></script>
		<script src="${ctx}/js/trunk/property/resultGetListByaddr.jqgridSearch.js?v=1.0.2"></script>
		
		<!-- 分页控件  -->
	    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script src="${ctx}/js/plugins/jquery.custom.js"></script>
		
		<script id="template_resultGetList" type= "text/html">
         	{{each rows as item index}}
                 {{if index%2 == 0}}
 				    <tr class="tr-1">
                 {{else}}
                    <tr class="tr-2">
                 {{/if}}
						<td>{{item.DIST_CODE}}</td>
						<td><a href='../mobile/property/box/show?prCode={{item.PR_CODE}}' target='_blank'>{{item.PROPERTY_ADDR}}</a></td>
						<td>{{item.applyOrgName}}</td>
						<td rowspan="2" class="fs12 sq-state">
							<span>
								{{if item.PR_APPLY_TIME == null}}
									<i class="invalid-label">申</i>
								{{else}}
									<i class="sq-label">申</i>{{item.PR_APPLY_TIME}}
								{{/if}}	
							</span>
							<span>
								{{if item.PR_ACCPET_TIME == null}}
									<i class="invalid-label">受</i>
								{{else}}
									<i class="sl-label">受</i>{{item.PR_ACCPET_TIME}}
								{{/if}}	
							</span>
							<span>
								{{if item.PR_COMPLETE_TIME == null}}
									<i class="invalid-label">完</i>
								{{else}}
									<i class="sl-label">完</i>{{item.PR_COMPLETE_TIME}}
								{{/if}}
							</span>
						</td>
						{{if item.IS_SUCCESS == '是'}}
							<td class="fs12 sq-state"><i class="valid-label">是</i></td>
						{{else if item.IS_SUCCESS == '否'}}
							<td rowspan="2" class="fs12 sq-state invalid"><i class="invalid-label">否</i><em style="word-break:break-all">{{item.UNSUCCESS_REASON}}</em></td>
						{{else}}
							<td></td>
                 		{{/if}}
						<td class="sqr">申请人：{{item.PR_APPLIANT}}
							{{if item.CHANNEL == '经纪人'}}
								<i class="jl-label">经</i>
							{{else}}
                 				<i class="yc-label">誉</i>
							{{/if}}
						</td>
						<td class="btn-g">
							{{if item.PR_STATUS == '已完成'}}
								<a href='../mobile/property/box/show?prCode={{item.PR_CODE}}' class="btn-y" target='_blank'>查看</a>
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
						{{if item.IS_SUCCESS == '是'}}
							<td></td>
						{{else if item.IS_SUCCESS == '否'}}、
						{{else}}
							<td></td>
                 		{{/if}}
						<td>执行人：{{item.PR_EXECUTOR}}</td>
						<td></td>
					</tr>
			{{/each}}
	 	</script> 
	</content>
</body>
</html>
