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
<title>过户驳回率统计</title>
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

<style type="text/css">

</style>
</head>
<body class="pace-done">
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox-content border-bottom clearfix space_box">
		<h2 class="title">过户驳回率统计</h2>
		<form method="get" class="form_list">
			<div class="line">
				<div class="form_content">
					<label class="control-label mr10 select_style mend_select">过户审批通过时间</label>
					<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" id="datepicker_0">
						<input id="dtBegin_0" name="dtBegin" class="form-control data_style" type="text" value="${startTime}" placeholder="起始日期"> <span class="input-group-addon">到</span>
						<input id="dtEnd_0" name="dtEnd" class="form-control data_style" type="text" value="${endTime}" placeholder="结束日期">
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
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0"
			class="table table_blue table-striped table-bordered table-hover "
			id="editable">
			<thead>
				<tr>
					<th>贵宾服务部</th>
					<th>贷款..</th>
					<th>商贷..</th>
					 <th>公积..</th>
					<th>合同..</th>
					<th>核定..</th>
					<th>商贷..</th>
					<th>主贷..</th>
					<th>贷款..</th>
					<th>贷款..</th>
					<th>附件..</th>
					<th>贷款..</th>
					<th>贷款..</th>
					<th>其他..</th>
				</tr>
			</thead>
			<tbody id="myMortgageApproveLostList"></tbody>
		</table>
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
<script	id="template_myMortgageApproveLostList" type="text/html">
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
			// 初始化列表		
			var startDate = $("#dtBegin_0").val();
			var endDate = '';
			if (!$.isBlank($("#dtEnd_0").val())) {
				endDate = $("#dtEnd_0").val() + " 23:59:59";
			}
			var data = {};
			data.startDate = startDate;
			data.endDate = endDate;
			data.queryId = "exportCaseRejectionRateReasonZbList";
			data.rows = 10;
			data.page = 1;
			//data.sortname = "j.ORG_NAME",
			///data.sortorder = "DESC";
			data.sortActive = false;
			/* 加载排序查询组件 */
			/* aist.sortWrapper({
				reloadGrid : loanLostApproveSearchMethod
			}); */
			reloadGrid(data);
		});
		//获取参数					
		function getParams() {
			var startDate = $("#dtBegin_0").val();
			var endDate = '';
			if (!$.isBlank($("#dtEnd_0").val())) {
				endDate = $("#dtEnd_0").val() + " 23:59:59";
			}

			var data = {};//getCheckBoxValues2("srvCode");	
			//todo  查询条件需要补充完善
			data.startDate = startDate;
			data.endDate = endDate;
			//data.serviceDepId = "${sessionUser.serviceDepId}";
			data.queryId = "exportCaseRejectionRateReasonZbList";
			data.rows = 10;
			data.page = 1;
			data.sortname = "orgName";
			data.sortorder = "ASC";
			return data;
		}

		// 查询
		$('#searchButton').click(function() {
			loanLostApproveSearchMethod();
		});

		// 查询
		function loanLostApproveSearchMethod(page) {
			if (!page) {
				page = 1;
			}
			var params = getParams();
			reloadGrid(params);
		};

		function reloadGrid(data) {
			//aist.wrap(data);
			 $.ajax({
					async : true,
					url : ctx + "/quickGrid/findPage",
					method : "post",
					dataType : "json",
					data : data,
					beforeSend : function() {
						$.blockUI({message : $("#salesLoading"),css : {'border' : 'none','z-index' : '9999'}});
						$(".blockOverlay").css({'z-index' : '9998'});
					},
					success : function(data) {
						$.unblockUI();
						data.ctx = ctx;
						var myMortgageApproveLostList = template( 'template_myMortgageApproveLostList', data);
						$("#myMortgageApproveLostList").empty();
						$("#myMortgageApproveLostList").html(
					    myMortgageApproveLostList);
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

		//案件excel导出
		function loanLostCaseExportToExcel() {
		var data = getParams();
		aist.exportExcel({
			ctx : "${ctx}",
			queryId : 'exportCaseRejectionRateReasonZbList',
			colomns : [ 'orgName', 'reason1','reason2','reason3','reason4','reason5','reason6','reason7','reason8','reason9','reason10','reason11','reason12',
			            'reason13'],
			data : data
		})
		} 
</script>
</content>
<input type="hidden" id="ctx" value="${ctx}" />
</body>
</html>