<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>过户驳回率统计报表</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/style.css" rel="stylesheet" />
<!-- Data Tables -->
<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
</head>
<body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">过户驳回率统计报表</h2>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label mr10 select_style mend_select">过户审批通过时间</label>
						<div class="input-group sign-right dataleft input-daterange" 
							data-date-format="yyyy-mm-dd" id="datepicker_0">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" 
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text"  placeholder="结束日期">
						</div>
				</div>
				<div class="add_btn" style="margin-left:440px;">
					<button id="searchButton" type="button" class="btn btn-success">
						<i class="icon iconfont">&#xe635;</i> 查询
					</button>
					<button type="button" id="loanLostExportExcelButton"
						class="btn btn-success"
						onclick="javascript:loanLostCaseExportToExcel()" >导出列表</button>
					<button type="reset" id="loanLostCleanButton"
						class="btn btn-grey">清&nbsp;&nbsp;空</button>
				</div>
				</div>
			</form>			
	</div>
	<div class="ibox-content" id="zj_info">
           <div class="row m-t-sm" id="">
               <div class="col-lg-12">
                   <div class="panel blank-panel">
                       <div class="panel-heading">
                           <div class="panel-options">
                               <ul class="nav nav-tabs">
                              		<c:choose>   
									   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_team'}">
									       <li class="active">
                                               <a href="#tab-1" data-toggle="tab" id="atab-1" >驳回原因统计-组别</a>
                                           </li>
                                           <li class="">
                                               <a href="#tab-3" data-toggle="tab" id="atab-3" >过户驳回率统计-组别</a>
                                           </li>
									   </c:when> 
									   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
									       <li class="active">
                                               <a href="#tab-2" data-toggle="tab" id="atab-2" >驳回原因统计-贵宾服务部</a>
                                           </li>
                                           <li class="">
                                               <a href="#tab-4" data-toggle="tab" id="atab-4" >过户驳回率统计-贵宾服务部</a>
                                           </li>
									   </c:when> 
									   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
									      <li class="active">
                                               <a href="#tab-1" data-toggle="tab" id="atab-1" >驳回原因统计-组别</a>
                                           </li>
                                           <li class="">
                                               <a href="#tab-2" data-toggle="tab" id="atab-2" >驳回原因统计-贵宾服务部</a>
                                           </li>
                                           <li class="">
                                               <a href="#tab-3" data-toggle="tab" id="atab-3" >过户驳回率统计-组别</a>
                                           </li>
                                           <li class="">
                                               <a href="#tab-4" data-toggle="tab" id="atab-4" >过户驳回率统计-贵宾服务部</a>
                                           </li>
									   </c:when> 
									</c:choose>	
                               </ul>
                           </div>
                       </div>

                       <div class="panel-body">
                           <div class="tab-content">
                           <c:choose>   
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_team'}">
							       <div class="tab-pane active" id="tab-1" style="display: block">
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
							      <div class="tab-pane active" id="tab-1" style="display: none">
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
							      <div class="tab-pane active" id="tab-1" style="display: block">
							   </c:when> 
							</c:choose>
                                   <table class="table table_blue table-striped table-bordered table-hover "  >
                                       <thead>
                                       <tr>
										<th>组别</th>
										<th class="demo-top" title="贷款银行有误">贷款</th>
										<th	class="demo-top" title="商贷金额有误">商贷</th>
										<th class="demo-top" title="公积金金额有误">公积</th>
										<th class="demo-top" title="合同价有误">合同</th>
										<th class="demo-top" title="核定价格有误">核定</th>
										<th class="demo-top" title="商贷利率有误">商贷</th>
										<th class="demo-top" title="主贷人未填/有误">主贷</th>
										<th class="demo-top" title="贷款情况有误（有贷款、无贷款）">贷款</th>
										<th class="demo-top" title="贷款类型未选择/有误（纯商贷、组合贷、纯公积金）">贷款</th>
										<th class="demo-top" title="附件照片缺少/有误（物业地址不符、模糊不清、缺少重要附件）">附件</th>
										<th class="demo-top" title="贷款推荐函未上传">贷款</th>
										<th class="demo-top" title="贷款确认书未上传">贷款</th>
										<th class="demo-top" title="其他">其他</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostReasonZbList"></tbody>
								</table>
							</div>
							<c:choose>   
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_team'}">
							       <div class="tab-pane active" id="tab-2" style="display: none" >
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
							      <div class="tab-pane active" id="tab-2" style="display: block" >
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
							      <div class="tab-pane active" id="tab-2" style="display: none" >
							   </c:when> 
							</c:choose>
                                   <table class="table table_blue table-striped table-bordered table-hover "  >
                                       <thead>
                                       <tr>
										<th>贵宾服务部</th>
										<th class="demo-top" title="贷款银行有误">贷款</th>
										<th	class="demo-top" title="商贷金额有误">商贷</th>
										<th class="demo-top" title="公积金金额有误">公积</th>
										<th class="demo-top" title="合同价有误">合同</th>
										<th class="demo-top" title="核定价格有误">核定</th>
										<th class="demo-top" title="商贷利率有误">商贷</th>
										<th class="demo-top" title="主贷人未填/有误">主贷</th>
										<th class="demo-top" title="贷款情况有误（有贷款、无贷款）">贷款</th>
										<th class="demo-top" title="贷款类型未选择/有误（纯商贷、组合贷、纯公积金）">贷款</th>
										<th class="demo-top" title="附件照片缺少/有误（物业地址不符、模糊不清、缺少重要附件）">附件</th>
										<th class="demo-top" title="贷款推荐函未上传">贷款</th>
										<th class="demo-top" title="贷款确认书未上传">贷款</th>
										<th class="demo-top" title="其他">其他</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostReasonGbList"></tbody>
								</table>
							</div>
                             <div class="tab-pane active" id="tab-3" style="display: none" >
                                   <table class="table table_blue table-striped table-bordered table-hover " >
                                       <thead>
                                       <tr>
										<th>组别</th>
										<th>总计</th>
										<th>不通过</th>
										<th>通过</th>
										<th>通过率</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostZbList"></tbody>
								</table>
							</div>
                             <div class="tab-pane active" id="tab-4" style="display: none" >
                                   <table class="table table_blue table-striped table-bordered table-hover "  >
                                       <thead>
                                       <tr>
										<th>贵宾服务部</th>
										<th>总计</th>
										<th>不通过</th>
										<th>通过</th>
										<th>通过率</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostGbList"></tbody>
								</table>
							</div>
							<div class="text-center page_box"  style="display: none" >
								<span id="currentTotalPage3"><strong></strong></span> <span
									class="ml15">共<strong id="totalP3"></strong>条
								</span>&nbsp;
								<div id="pageBar3" class="pagergoto"></div>
							</div>
							<div class="text-center page_box"  style="display: none" >
								<span id="currentTotalPage4"><strong></strong></span> <span
									class="ml15">共<strong id="totalP4"></strong>条
								</span>&nbsp;
								<div id="pageBar4" class="pagergoto"></div>
							</div>
						  </div>	
						</div>	
					</div>	
				</div>	
			</div>	
		</div>	
	</div>
	<content tag="local_script"> 
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script	src="${ctx}/js/inspinia.js"></script> 
	<script	src="${ctx}/js/plugins/pace/pace.min.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> 
	<script	src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
	<script	src="${ctx}/js/plugins/jquery.custom.js"></script> 
	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	<script	id="template_myMortgageApproveLostReasonZbList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
					{{item.orgName}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason1}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason2}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason3}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason4}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason5}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason6}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason7}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason8}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason9}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason10}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason11}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason12}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason13}}
				 </p>
			</td>
 			</tr>
		{{/each}}
	    </script> 
	<script	id="template_myMortgageApproveLostReasonGbList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
					{{item.orgName}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason1}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason2}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason3}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason4}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason5}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason6}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason7}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason8}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason9}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason10}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason11}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason12}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.reason13}}
				 </p>
			</td>
 			</tr>
		{{/each}}
	    </script>
<script	id="template_myMortgageApproveLostZbList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
					{{item.ORGNAME}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.AMOUNT}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.NOT_APPROVE}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.APPROVE}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.RATE}}
				 </p>
			</td>
 			</tr>
		{{/each}}
</script> 
<script	id="template_myMortgageApproveLostGbList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
					{{item.ORGNAME}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.AMOUNT}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.NOT_APPROVE}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.APPROVE}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.RATE}}
				 </p>
			</td>
 			</tr>
		{{/each}}
</script>  
	    <script>
						var ctx = "${ctx}";
						jQuery(document).ready(function() {
							if(document.getElementById("tab-1").style.display == 'block'){
								putParams("exportCaseRejectionRateReasonZbList","template_myMortgageApproveLostReasonZbList","myMortgageApproveLostReasonZbList",false);
 							}
 							if(document.getElementById("tab-2").style.display == 'block'){
 								putParams("exportCaseRejectionRateReasonGbList","template_myMortgageApproveLostReasonGbList","myMortgageApproveLostReasonGbList",false);
 							}
 							if(document.getElementById("tab-3").style.display == 'block'){
 								putParams("exportCaseRejectionRateZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",true);
 							}
 							if(document.getElementById("tab-4").style.display == 'block'){
 								putParams("exportCaseRejectionRateGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",true);
 							}
						});
						function putParams(qId,temp,tempValue,type){
							var data = getParams(qId,true); 
							reloadGrid(data,temp,tempValue,type);
						}
						function getParams(qId,type) {
							var startDate = $("#dtBegin_0").val();
							var endDate = '';
							if (!$.isBlank($("#dtEnd_0").val())) {
								endDate = $("#dtEnd_0").val() + " 23:59:59";
							}
							var data = {};
							data.startDate = startDate;
							data.endDate = endDate;
							if("false" == "${sessionUser.serviceDepHierarchy == 'yucui_headquarter'}")
							{data.serviceDepId = "${sessionUser.serviceDepId}";}
							data.rows = 200;
							data.page = 1;
							if(type) data.queryId=qId;
							return data;
						}
						$('#searchButton').click(function() {// 查询
							if(document.getElementById("tab-1").style.display == 'block'){
								putParams("exportCaseRejectionRateReasonZbList","template_myMortgageApproveLostReasonZbList","myMortgageApproveLostReasonZbList",false);
 							}
 							if(document.getElementById("tab-2").style.display == 'block'){
 								putParams("exportCaseRejectionRateReasonGbList","template_myMortgageApproveLostReasonGbList","myMortgageApproveLostReasonGbList",false);
 							}
 							if(document.getElementById("tab-3").style.display == 'block'){
 								putParams("exportCaseRejectionRateZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",true);
 							}
 							if(document.getElementById("tab-4").style.display == 'block'){
 								putParams("exportCaseRejectionRateGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",true);
 							}
						});
 						function loanLostCaseExportToExcel(){//案件excel导出
							var qId ;
							var colom;
							var colom1 =['orgName','reason1','reason2','reason3','reason4','reason5','reason6',
								         'reason7','reason8','reason9','reason10','reason11','reason12','reason13'];
							var colom2 =[ 'ORGNAME', 'AMOUNT','NOT_APPROVE','APPROVE','RATE' ];
							debugger;
 							if(document.getElementById("tab-1").style.display == 'block'){
 								qId = 'exportCaseRejectionRateReasonZbList';
 								colom = colom1;
 							}
 							if(document.getElementById("tab-2").style.display == 'block'){
 								qId = 'exportCaseRejectionRateReasonGbList';
 								colom = colom1;
 							}
 							if(document.getElementById("tab-3").style.display == 'block'){
 								qId = 'exportCaseRejectionRateZbList';
 								colom = colom2;
 							}
 							if(document.getElementById("tab-4").style.display == 'block'){
 								qId = 'exportCaseRejectionRateGbList';
 								colom = colom2;
 							}
 							var data =getParams(qId,false);
							aist.exportExcel({
								ctx : "${ctx}",
								queryId : qId,
								colomns : colom,
								data : data
							})
						} 
						
						$('#atab-1').click(function() {
							document.getElementById("tab-1").style.display="block";
							document.getElementById("tab-2").style.display="none";
							document.getElementById("tab-3").style.display="none";
							document.getElementById("tab-4").style.display="none";
							putParams("exportCaseRejectionRateReasonZbList","template_myMortgageApproveLostReasonZbList","myMortgageApproveLostReasonZbList",false);
						});
						$('#atab-2').click(function() {
							document.getElementById("tab-1").style.display="none";
							document.getElementById("tab-2").style.display="block";
							document.getElementById("tab-3").style.display="none";
							document.getElementById("tab-4").style.display="none";
							putParams("exportCaseRejectionRateReasonGbList","template_myMortgageApproveLostReasonGbList","myMortgageApproveLostReasonGbList",false);
						});
						$('#atab-3').click(function() {
							document.getElementById("tab-1").style.display="none";
							document.getElementById("tab-2").style.display="none";
							document.getElementById("tab-3").style.display="block";
							document.getElementById("tab-4").style.display="none";
							putParams("exportCaseRejectionRateZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",true);
						});
						$('#atab-4').click(function() {
							document.getElementById("tab-1").style.display="none";
							document.getElementById("tab-2").style.display="none";
							document.getElementById("tab-3").style.display="none";
							document.getElementById("tab-4").style.display="block";
							putParams("exportCaseRejectionRateGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",true);
						});
						

						function reloadGrid(data,temp,templateValue,type) {
							aist.wrap(data);
							$.ajax({
									async : true,
									url : ctx + "/quickGrid/findPage",
									method : "post",
									dataType : "json",
									data : data,
									beforeSend : function() {
										$.blockUI({ message : $("#salesLoading"), css : { 'border' : 'none', 'z-index' : '9999' } });
										$(".blockOverlay").css({ 'z-index' : '9998' });
									},
									success : function(data) {
										$.unblockUI();
										data.ctx = ctx;
										var myMortgageApproveLostList = template( temp, data);
										$("#"+templateValue).empty();
										$("#"+templateValue).html( myMortgageApproveLostList);
										//if(type) initpage(data.total, data.pagesize,data.page, data.records);
									},
									error : function(e, jqxhr, settings,exception) {$.unblockUI();}
								});
						}
						//分页
						function initpage(totalCount, pageSize, currentPage,
								records) {
							if (totalCount > 1500) {
								totalCount = 1500;
							}
							var currentTotalstrong = $('#currentTotalPage')
									.find('strong');
							if (totalCount < 1 || pageSize < 1
									|| currentPage < 1) {
								$(currentTotalstrong).empty();
								$('#totalP').text(0);
								$("#pageBar").empty();
								return;
							}
							$(currentTotalstrong).empty();
							$(currentTotalstrong).text(
									currentPage + '/' + totalCount);
							$('#totalP').text(records);

							$("#pageBar").twbsPagination({
								totalPages : totalCount,
								visiblePages : 9,
								startPage : currentPage,
								first : '<i class="fa fa-step-backward"></i>',
								prev : '<i class="fa fa-chevron-left"></i>',
								next : '<i class="fa fa-chevron-right"></i>',
								last : '<i class="fa fa-step-forward"></i>',
								showGoto : true,
								onPageClick : function(event, page) {
									loanLostApproveSearchMethod(page);
								}
							});
						}
					

						//日期控件
						$('#datepicker_0').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked',
							language : 'zh-CN'
						});
						$('.demo-top').poshytip({
							className: 'tip-twitter',
							showTimeout: 1,
							alignTo: 'target',
							alignX: 'center',
							alignY: 'top',
							offsetX: 8,
							offsetY: 5,
						});
						
					</script>
					</content>
	<input type="hidden" id="ctx" value="${ctx}" />
</body>
</html>