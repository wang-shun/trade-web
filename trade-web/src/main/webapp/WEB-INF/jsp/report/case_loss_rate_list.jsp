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
<title>流失贷款审批驳回原因统计</title>
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
			<h2 class="title">流失案件统计报表</h2>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label mr10 select_style mend_select" >过户审批通过时间</label>
						<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" id="datepicker_0">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" value="${startTime}"
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text" value="${endTime}" placeholder="结束日期">
						</div>
					</div>
					<div class="add_btn">
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
		                                       <a href="#tabZb" id="AtabZb" data-toggle="tab">流失原因统计-组别</a>
		                                   </li>
									   </c:when> 
									   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
									      <li class="active">
		                                       <a href="#tabGb" id="AtabGb" data-toggle="tab">流失原因统计-贵宾服务部</a>
		                                   </li>
									   </c:when> 
									   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
									      <li class="active">
		                                       <a href="#tabZb" id="AtabZb" data-toggle="tab">流失原因统计-组别</a>
		                                   </li>
		                                   <li class="">
		                                       <a href="#tabGb" id="AtabGb" data-toggle="tab">流失原因统计-贵宾服务部</a>
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
								       <div class="tab-pane active" id="tabZb" style="display: block" >
								   </c:when> 
								   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
								      <div class="tab-pane active" id="tabZb" style="display: none" >
								   </c:when> 
								   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
								      <div class="tab-pane active" id="tabZb" style="display: block" >
								   </c:when> 
								</c:choose>	
                                   <table class="table table_blue table-striped table-bordered table-hover " >
                                       <thead>
                                       <tr>
											<th >组别</th>
											<th class="demo-top" title="收入、流水不足 ">收入</th>
											<th class="demo-top" title="客户资质差、征信有问题 ">客户</th>
											<th class="demo-top" title="客户亲戚、朋友在银行上班 ">客户</th>
											<th class="demo-top" title="客户是银行VIP ">客户</th>
											<th class="demo-top" title="客户自己找的银行优惠折扣大 ">客户</th>
											<th class="demo-top" title="客户不愿意支付评估费 ">客户</th>
											<th class="demo-top" title="客户坚持自己办理 ">客户</th>
											<th class="demo-top" title="房东坚持客户到他指定银行办理">房东</th>
											<th class="demo-top" title="房龄老、面积小等不予受理案件 ">房龄</th>
											<th class="demo-top" title="银行退单，客户自办 ">银行</th>
											<th class="demo-top" title="中原无法办理案件 ">中原</th>
											<th class="demo-top" title="分行原因导致案件流失 ">分行</th>
											<th class="demo-top" title="其他 ">其他</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostZbList"></tbody>
								</table>
							</div>
							<c:choose>   
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_team'}">
							       <div class="tab-pane active" id="tabGb" style="display: none" >
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_district'}">
							      <div class="tab-pane active" id="tabGb" style="display: block" >
							   </c:when> 
							   <c:when test="${sessionUser.serviceDepHierarchy=='yucui_headquarter'}">
							      <div class="tab-pane active" id="tabGb" style="display: none" >
							   </c:when> 
							</c:choose>	
                                   <table class="table table_blue table-striped table-bordered table-hover " >
                                       <thead>
                                       <tr>
											<th>贵宾服务部</th>
											<th class="demo-top" title="收入、流水不足 ">收入</th>
											<th class="demo-top" title="客户资质差、征信有问题 ">客户</th>
											<th class="demo-top" title="客户亲戚、朋友在银行上班 ">客户</th>
											<th class="demo-top" title="客户是银行VIP ">客户</th>
											<th class="demo-top" title="客户自己找的银行优惠折扣大 ">客户</th>
											<th class="demo-top" title="客户不愿意支付评估费 ">客户</th>
											<th class="demo-top" title="客户坚持自己办理 ">客户</th>
											<th class="demo-top" title="房东坚持客户到他指定银行办理">房东</th>
											<th class="demo-top" title="房龄老、面积小等不予受理案件 ">房龄</th>
											<th class="demo-top" title="银行退单，客户自办 ">银行</th>
											<th class="demo-top" title="中原无法办理案件 ">中原</th>
											<th class="demo-top" title="分行原因导致案件流失 ">分行</th>
											<th class="demo-top" title="其他 ">其他</th>
										</tr>
										</thead>
									<tbody id="myMortgageApproveLostGbList"></tbody>
								</table>
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
<script	id="template_myMortgageApproveLostZbList" type="text/html">
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
<script	id="template_myMortgageApproveLostGbList" type="text/html">
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
<script>
			var ctx = "${ctx}";
			jQuery(document).ready(function() {
				if(document.getElementById("tabZb").style.display == 'block'){
					putParams("exportcaseLossRateReasonZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false);
				}
				if(document.getElementById("tabGb").style.display == 'block'){
					putParams("exportcaseLossRateReasonGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",false);
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
				data.pagination = false;//不分页
				if(type) {data.queryId=qId};
				return data;
			}
			$('#searchButton').click(function() {// 查询
				if(document.getElementById("tabZb").style.display == 'block'){
					putParams("exportcaseLossRateReasonZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false);
				}
				if(document.getElementById("tabGb").style.display == 'block'){
					putParams("exportcaseLossRateReasonGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",false);
				}
			});
			function loanLostCaseExportToExcel(){//案件excel导出
				var qId ;
				var colom =['orgName','reason1','reason2','reason3','reason4','reason5','reason6',
					         'reason7','reason8','reason9','reason10','reason11','reason12','reason13'];
			
				if(document.getElementById("tabZb").style.display == 'block'){
					qId = 'exportcaseLossRateReasonZbList';
				}
				if(document.getElementById("tabGb").style.display == 'block'){
					qId = 'exportcaseLossRateReasonGbList';
				}
				var data = 	getParams(qId,false);
				aist.exportExcel({
					ctx : "${ctx}",
					queryId : qId,
					colomns : colom,
					data : data
				})
			} 
			$('#AtabZb').click(function() {
				document.getElementById("tabZb").style.display="block";
				document.getElementById("tabGb").style.display="none";
				putParams("exportcaseLossRateReasonZbList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false);
			});
			$('#AtabGb').click(function() {
				document.getElementById("tabGb").style.display="block";
				document.getElementById("tabZb").style.display="none";
				putParams("exportcaseLossRateReasonGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",false);
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
						},
						error : function(e, jqxhr, settings,exception) {$.unblockUI();}
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