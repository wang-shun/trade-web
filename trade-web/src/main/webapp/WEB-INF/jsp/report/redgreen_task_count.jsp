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
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<link href="${ctx}/css/transcss/case/myCaseList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
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
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
#inTextVal{width:50%}
.chosen-container{float:left;margin-right:10px;margin-left:15px}
#addLine{line-height:35px;}
.product-type span{margin-right:5px}	
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.date-info .col-md-12 .form-group:not(first-child){margin-bottom:0}
[id^="dateDiv_"]:not(#dateDiv_0) .chosen-container{margin-left:33px}
.text-center{text-align:center;}
.slash{font-weight:bold !important;}
.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
#searchButton{margin-right:5px;}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 
	 <div class="row">
		

		<div class="wrapper wrapper-content  animated fadeInRight">
		 <div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>红绿灯任务统计</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<!-- <div class="row date-info">
							<div class="col-md-12">
								<div class="form-group">
							<label class="col-md-1 control-label m-l">产调申请时间</label>
							<div id="dateDiv_0">
							<div id="datepicker_0"
								class="input-group input-medium date-picker input-daterange pull-left"
								data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control"
									style="font-size: 13px;" type="text" value=""
									placeholder="起始日期">
							</div>
							<div id="addLine" class="pull-left m-l">
							
						</div>
						</div>
						</div>
							</div>
						</div> -->
						<div class="row m-t-sm">
							<!-- <div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label m-l-lg"></label>
									<div>
										<button id="searchButton" type="button" class="btn btn-warning">查询</button>
										<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
									</div>							
								</div>
							</div> -->
							<div class="col-md-6"  style="text-align:right;">
							 	<shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
									<a data-toggle="modal" class="btn btn-primary" href="javascript:void(0)" onclick="javascript:exportTExcel()">任务统计导出</a>
								</shiro:hasPermission>
							</div>
						</div>
					</form>
				</div>
				
			</div>
		</div>
			<div class="data-wrap">
		<div class="data-wrap-in">
			<table border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th class="t-left pd-l">贵宾服务部</th>
						<th class="t-left pd-l">总监</th>
						<th class="t-left pd-l">组别</th>
						<th class="t-left pd-l">主管</th>
						<th class="text-center">黄灯</th>
						<th class="text-center">红灯</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody id="redgreenTaskList">
					
				</tbody>
			</table>
		</div>
	</div>
	<div class="text-center">
		<span id="currentTotalPage"><strong class="bold"></strong></span>
		<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
    </div>

		</div>
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	<content tag="local_script"> 
    <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
    <script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	 <script src="${ctx}/js/trunk/report/redgreen_task_count.js?v=1.1"></script>
	 <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	 <script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
	  <!-- 分页控件  -->
     <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	 <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	 <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	 <script id="template_redgreenTaskList" type= "text/html">
      {{each rows as item index}}
  				   {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td class="t-left pd-l">{{item.orgName1}}</td>
						<td class="t-left pd-l">{{item.realName1}}</td>
						<td class="t-left pd-l">{{item.orgName2}}</td>
						<td class="t-left pd-l">{{item.realName2}}</td>
						<td class="t-left pd-l">{{item.yellow}}</td>
						<td class="t-left pd-l">{{item.red}}</td>
						<td class="t-left pd-l">
                            <a href="javascript:queryRedGreenTaskDetail('{{item.id}}');" target="_blank">查看详细</a>&nbsp;&nbsp;
                            <a href="javascript:exportToExcel('{{item.id}}');" target="_blank">导出</a>
                        
                        </td>
						
				  </tr>
       {{/each}}
     </script>
     <script></script>

	 </content>
</body>
</html>