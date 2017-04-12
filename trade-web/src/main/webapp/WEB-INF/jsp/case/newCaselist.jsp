<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>自录单案件详细列表</title>
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
			<h2 class="title">自录单案件详细列表</h2>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label mr10 select_style mend_select" >创建时间</label>
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
						<!-- <button type="button" id="loanLostExportExcelButton"
							class="btn btn-success"
							onclick="javascript:loanLostCaseExportToExcel()" >导出列表</button> -->
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
                               
                           </div>
                       </div>
                       <div class="panel-body">
                           <div class="tab-content">
                                   <table class="table table-small table-striped table-bordered table-hover " >
                                       <thead>
	                                       <tr>
												<th >案件编号</th>
												<th >案件状态</th>
												<th >产证地址</th>
												<th >上家</th>
												<th >下家</th>
												<th >创建人</th>
												<th >创建时间</th>
											</tr>
										</thead>
										<tbody id="myMortgageApproveLostZbList"></tbody>
								</table>
								<div class="text-center page_box">
									<span id="currentTotalPagef"><strong ></strong></span>
									<span class="ml15">共<strong  id="totalPf"></strong>条</span>&nbsp;
								<div id="pageBarf" class="pagination text-center"></div>  
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
					{{item.caseCode}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.status}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.caseProperty}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.realName}}
				 </p>
			</td>
			<td class="t-left">
				 <p class="big">
					{{item.createTime}}
				 </p>
			</td>
			
 			</tr>
		{{/each}}
</script> 
<script>
var ctx = "${ctx}";
/**
 * 进入页面查询设置
 */
jQuery(document).ready(function() {
	putParams("newCaseInputList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false,1);
});
function putParams(qId,temp,tempValue,type,page){
	var data = getParams(qId,true,page); 
	reloadGrid(data,temp,tempValue,type);
}
function getParams(qId,type,page) {
	var startDate = $("#dtBegin_0").val();
	var endDate = '';
	if (!$.isBlank($("#dtEnd_0").val())) {
		endDate = $("#dtEnd_0").val() + " 23:59:59";
	}
	var data = {};
	if(!page) { page = 1; }
    data.rows = 10;
    data.page = page;
	data.startDate = startDate;
	data.endDate = endDate;
	
	if("false" == "${sessionUser.serviceDepHierarchy == 'yucui_headquarter'}")
	{data.serviceDepId = "${sessionUser.serviceDepId}";}
	/**分页设置**/
	data.pagination = true;
	if(type) {data.queryId=qId};
	return data;
}
/***
 * 点击查询时调用的查询方法
 */
$('#searchButton').click(function() {
	putParams("newCaseInputList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false,1);
});
/**
 *案件详细导出
 */
function loanLostCaseExportToExcel(){
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
});
$('#AtabGb').click(function() {
	document.getElementById("tabGb").style.display="block";
	document.getElementById("tabZb").style.display="none";
	if(readyType == 1 ){
		putParams("exportcaseLossRateReasonGbList","template_myMortgageApproveLostGbList","myMortgageApproveLostGbList",false);
		readyType = 0;
	}
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
				initpagef(data.total,data.pagesize,data.page, data.records);
			},
			error : function(e, jqxhr, settings,exception) {$.unblockUI();}
		});
}

/* 分页   **/
function initpagef(totalCount,pageSize,currentPage,records) {
	var currentTotalstrong=$('#currentTotalPagef').find('strong');
	if(totalCount>1500){ totalCount = 1500; }
	if (totalCount<1 || pageSize<1 || currentPage<1) {
		$(currentTotalstrong).empty();
		$('#totalPf').text(0);
		$("#pageBarf").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalPf').text(records);
	$("#pageBarf").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			putParams("newCaseInputList","template_myMortgageApproveLostZbList","myMortgageApproveLostZbList",false,page);
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