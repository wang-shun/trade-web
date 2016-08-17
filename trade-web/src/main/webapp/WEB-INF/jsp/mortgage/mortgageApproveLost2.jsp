<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>流失案件</title>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/style.css" rel="stylesheet" />
<!-- Data Tables -->
<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet" />
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
</head>
<body class="pace-done">
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox-content border-bottom clearfix space_box">
		<h2 class="title">贷款流失筛选</h2>
		<form method="get" class="form_list">
			<div class="line">
				<div class="form_content">
					<label class="control-label sign_left_small"> 案件编号 </label> <input
						class="teamcode input_type" style="width: 152px;"
						placeholder="请输入" value="" id="caseCode" name="caseCode" />
				</div>
				<div class="form_content">
					<label class="control-label sign_left_small"> 主办 </label> <input
						class="teamcode input_type" style="width: 152px;"
						placeholder="请输入" value="" id="caseoperator" name="caseoperator" />
				</div>
				<div class="form_content">
					<label class="control-label sign_left_small"> 案件组织 </label> <input
						class="teamcode input_type" placeholder="请输入" value=""
						id="orgName"
						onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})"
						value=''> <input type="hidden" id="yuCuiOriGrpId" value="">
					<div class="input-group float_icon organize_icon">
						<i class="icon iconfont">&#xe61b;</i>
					</div>
				</div>
			</div>
			<div class="line">
				<div class="form_content">
					<label class="control-label sign_left_small"> 产证地址 </label> <input
						class="teamcode input_type" style="width: 435px;"
						placeholder="请输入" value="" id="propertyAddr" name="propertyAddr" />
				</div>
			</div>

			<div class="line">
				<div class="form_content">
					<label
						class="control-label sign_left_small select_style mend_select">
						审批日期 </label>
					<div class="input-group sign-right dataleft input-daterange"
						data-date-format="yyyy-mm-dd" id="datepicker_0">
						<input id="dtBegin_0" name="dtBegin"
							class="form-control data_style" type="text" value="${startTime}"
							placeholder="起始日期"> <span class="input-group-addon">到</span>
						<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
							type="text" value="${endTime}" placeholder="结束日期">
					</div>
				</div>

				<div class="form_content space">
					<div class="add_btn">
						<button id="searchButton" type="button" class="btn btn-success">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="button" id="exportExcelButton"
							class="btn btn-success" onclick="javascript:loanLostCaseExportToExcel()">导出列表</button>
					</div>
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
							<th><span class="sort" sortColumn="CASE_CODE" sord="desc"
								onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti"
								class="fa fa-sort-desc fa_down"></i></th>
							<th>案件地址</th>
							<th><span class="sort" sortColumn="START_TIME_" sord="desc"
								onclick="createTimeSort();">创建时间</span><i id="createTimeSorti"
								class="fa fa-sort-asc fa_up"></i></th>
							<th>经办人</th>
							<th>贷款类型</th>
						</tr>
				</thead>
				<tbody id="myMortgageApproveLostList"></tbody>
			</table>
		</div>

	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
</div>
	<!-- End page wrapper-->
	<!-- Mainly scripts -->
	<content tag="local_script"> <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Custom and plugin javascript --> <script
		src="${ctx}/js/inspinia.js"></script> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <!-- 选择组织控件 --> <jsp:include
		page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> <!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/plugins/jquery.custom.js"></script> <script
		id="template_myMortgageApproveLostList" type="text/html">
          {{each rows as item index}}
 					{{if index%2 == 0}}
 				     	<tr class="tr-1">
                    {{else}}
                        <tr class="tr-2">
                    {{/if}}

			<td class="t-left">
				 <p class="big">
                       <a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">
							{{item.CASE_CODE}}
						</a>
				 </p>
				 <p>
						<i class="tag_sign">c</i>{{item.CTM_CODE}}
				 </p>
			</td>
			<td class="t-left">
					<p class="big">{{item.PROPERTY_ADDR}}</p>
					<span >
						<i class="salesman-icon"></i>
 						<a class="hint  hint-top2"  data-hint="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}} " data-toggle="tooltip" data-placement="top" >{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_PHONE}}<span class="slash">/</span>{{item.GRP_NAME}}</a>					 
					</span>
			</td>                
             <td>						
					<p>  
                        <i class="sign_normal">创</i>{{item.START_TIME_}}          
                    </p>
			</td> 
			
			<td class="center">
              	 <span class="manager"><a href="#"><em>主办人：</em>{{item.CASEOPERATOR}}</a></span>
                 <span class="manager"><a href="#"><em>经纪人：</em>{{item.AGENT_NAME}}</a></span>
                 <span class="manager"><a href="#"><em>审批人：</em>{{item.real_name}}</a></span>
            </td> 
			<td>
				<p>
                     <i class="sign_blue">{{item.MORT_TYPE}}</i>
                </p>       
			</td>
				
                   
              </tr>
		{{/each}}
	    </script> <script>
var ctx = "${ctx}";
var serviceDepId = "${serviceDepId}";
						
jQuery(document).ready(function() {
		// 初始化列表
		var data = {};
		data.queryId = "queryMortgageApproveLost";
		data.rows = 10;
		data.page = 1;
							
		/* 加载排序查询组件 */
		aist.sortWrapper({
			reloadGrid : searchMethod
		});
							
		reloadGrid(data);									
});

// 查询
$('#searchButton').click(function() {
	searchMethod();
});
						
// 查询
function searchMethod(page) {
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "queryMortgageApproveLost";
	reloadGrid(params);
};
						
function reloadGrid(data) {
							
		var queryOrgFlag = $("#queryOrgFlag").val();
		var isAdminFlag = $("#isAdminFlag").val();
		var queryOrgs = $("#queryOrgs").val();
		var arguUserId = null;
		if (queryOrgFlag == 'true') {
			arguUserId = null;
			if (isAdminFlag == 'true') {
				queryOrgs = null;
			}
		} else {
				queryOrgs = null;
				arguUserId = "yes";
		}
							
		var sortcolumn = $('span.active').attr("sortColumn");
		var sortgz = $('span.active').attr("sord");
		data.sidx = sortcolumn;
		data.sord = sortgz;

		var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
		data.argu_idflag = arguUserId;
		//data.argu_queryorgs = orgArray;
		data.argu_queryorgs = serviceDepId;
		aist.wrap(data);

		$.ajax({
					async : true,
					url : ctx + "/quickGrid/findPage",
					method : "post",
					dataType : "json",
					data : data,
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
									'border' : 'none',
									'z-index' : '9999'
							}
						});
						$(".blockOverlay").css({
									'z-index' : '9998'
							});
						},
					success : function(data) {
							$.unblockUI();
							data.ctx = ctx;
							console.log("====Result===="+JSON.stringify(data));

							var myMortgageApproveLostList = template('template_myMortgageApproveLostList', data);
							$("#myMortgageApproveLostList").empty();
							$("#myMortgageApproveLostList").html(myMortgageApproveLostList);
							// 显示分页
							initpage(data.total, data.pagesize, data.page, data.records);
						},
					error : function(e, jqxhr, settings, exception) {
								$.unblockUI();
							}
					});
}
//分页
function initpage(totalCount, pageSize, currentPage, records) {
		if (totalCount > 1500) {
				totalCount = 1500;
		}
		var currentTotalstrong = $('#currentTotalPage').find('strong');
		if (totalCount < 1 || pageSize < 1 || currentPage < 1) {
			$(currentTotalstrong).empty();
			$('#totalP').text(0);
			$("#pageBar").empty();
			return;
		}
		$(currentTotalstrong).empty();
		$(currentTotalstrong).text(currentPage + '/' + totalCount);
		$('#totalP').text(records);

		$("#pageBar").twbsPagination({
				totalPages : totalCount,
				visiblePages : 9,
				startPage : currentPage,
				first : '<i class="icon-step-backward"></i>',
				prev : '<i class="icon-chevron-left"></i>',
				next : '<i class="icon-chevron-right"></i>',
				last : '<i class="icon-step-forward"></i>',
				showGoto : true,
				onPageClick : function(event, page) {				
				searchMethod(page);
				}
		});
}
//获取参数					
function getParams() {
		//;yuCuiOriGrpId;;
		var startDate = $("#dtBegin_0").val();
		var endDate = '';
		if (!$.isBlank($("#dtEnd_0").val())) {
				endDate = $("#dtEnd_0").val() + " 23:59:59";
		}
							
		var data = {};
			//todo  查询条件需要补充完善
			data.startDate = startDate;
			data.endDate = endDate;
			data.caseCode = $("#caseCode").val();
			data.caseCode = $("#caseCode").val();
			data.propertyAddr = $("#propertyAddr").val();
			data.caseoperator = $("#caseoperator").val();
			// 获取组织或者人员
			var queryOrgFlag = $("#queryOrgFlag").val();
			var isAdminFlag = $("#isAdminFlag").val();
			var queryOrgs = $("#queryOrgs").val();
			var arguUserId = null;
			if (queryOrgFlag == 'true') {
					arguUserId = null;
					if (isAdminFlag == 'true') {
						queryOrgs = null;
					}
			} else {
				queryOrgs = null;
				arguUserId = "yes";
			}
			var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
			data.argu_idflag = arguUserId;
			//data.search_realName = $("#realName").val();
			var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();//案件组织id

			if ($.isBlank(yuCuiOriGrpId)) {
					data.argu_queryorgs = serviceDepId;
			} else {
					data.argu_queryorgs = yuCuiOriGrpId;
			}
			return data;
}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
		if (array && array.length > 0) {
				$("#orgName").val(array[0].name);
				$("#yuCuiOriGrpId").val(array[0].id);
		} else {
				$("#orgName").val("");
				$("#yuCuiOriGrpId").val("");
		}
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
				queryId : 'queryMortgageApproveLost',
				colomns : [ 'CASE_CODE', 'PROPERTY_ADDR','LEADING_PROCESS_ID', 'ORG_ID','AGENT_NAME', 'MORT_TYPE', 'real_name','END_TIME_' ],
				data : data
			})
}

//案件case_code排序图标变化函数
function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

//案件创建时间排序图标变化函数
function createTimeSort(){
	if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else if($("#createTimeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#createTimeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#createTimeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}
					</script> </content>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
</body>
</html>