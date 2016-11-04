<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>金融贷款申请列表</title>

<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">

<!-- stickUp fixed css -->
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/trans/css/common/stickDash.css"
	rel="stylesheet">

<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<!-- index_css  -->

<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css"
	rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
</head>


<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<div class="row">
		<div class="wrapper wrapper-content animated fadeInUp">
			<div class="ibox-content border-bottom clearfix space_box">
				<h2 class="title">金融贷款申请列表</h2>
				<form method="get" class="form_list">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"> 归属组织 </label> 
							<input  id="orgName" name="orgName" class="teamcode input_type" placeholder="请输入"  value=""
							onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', 
							orgType:'',departmentType:'',departmentHeriarchy:'',expandNodeId:'${serviceDepId}',						
							chkStyle:'radio',callBack:eloanRadioYuCuiOrgSelectCallBack})">
							<input type="hidden" id="yuCuiOriGrpId" value="">
						
							<div class="input-group float_icon organize_icon" id="organizeOnclick">
								<i class="icon iconfont"></i>
							</div>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"> 申请结果 </label> <select
								class="select_control sign_right_one" id="eloanApplyResult">
								<option value="">请选择</option>
								<option value="1">已通过</option>
								<option value="2">未通过</option>
								<option value="3">等待中</option>
							</select>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"> 产品类型 </label> <select
								class="select_control sign_right_one"  id="eloanProductType">
								<option value="">请选择</option>
								<option value="30004001">商业贷</option>
								<option value="30004002">纯公积金贷款</option>
								<option value="30004003">资金监管/安心宝</option>
								<option value="30004004">代收代付</option>
								<option value="30004005">E+产品1号-sfk</option>
								<option value="30004006">E+产品2号-qkd</option>
								<option value="30004007">E+产品3号-sld</option>
								<option value="30004008">E+产品4号-xfd</option>
								<!-- <option value="30004009">首付贷</option> -->
								<option value="30004010">交易过户</option>
								<option value="30004011">E+产品5号-hfd</option>
								<option value="30004012">E+产品6号-dyd</option>
								<option value="30004013">E+产品7号-wtd</option>
							</select>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label
								class="control-label sign_left_small select_style mend_select">
								申请时间 </label>
							<div class="input-group sign-right dataleft input-daterange"
								data-date-format="yyyy-mm-dd" id="datepicker">
								<input id="dtBegin_0" name="dtBegin_0" class="form-control data_style" type="text"
									value="" placeholder="起始日期"> <span
									class="input-group-addon">到</span> <input id="dtEnd_0" name="dtEnd_0"
									class="form-control data_style" type="text" value=""
									placeholder="结束日期">
							</div>
						</div>

					</div>
					<div class="form_content space">
						<div class="add_btn" style="margin-left: 122px;">
							<button type="button" class="btn btn-success" id="eloanSearchButton">
								<i class="icon iconfont"></i> 查询
							</button>
							<button type="button"  id="eloanContractListToExcel" class="btn btn-success">导出列表</button>
							<button type="button"  id="eloanListCleanButton"  class="btn btn-grey">清空</button>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="ibox"> -->
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="table_content">
				<table	class="table table_blue table-striped table-bordered table-hover "	id="editable">
					<thead>
						<tr>
							<th class="text-center light_icon">	<i	class="iconfont icon_light"> &#xe604; </i></th>
							<th><span class="sort" sortColumn="ELOAN_CODE" sord="desc"	onclick="eloanCodeSort();">e+合约编号</span><i id="eloanCodeSorti"	class="fa fa-sort-desc fa_down"></i></th>
							<th>物业地址</th>
							<th>产品名称</th>
							<th>归属组</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
					</thead>
										
					<tbody id="eloanContractList"></tbody>
				</table>
				<!-- page -->
				<div class="text-center page_box">
					<span id="currentTotalPage"><strong></strong></span> <span
						class="ml15">共<strong id="totalP"></strong>条
					</span>&nbsp;
					<div id="pageBar" class="pagergoto"></div>
				</div>
				
			</div>
		</div>
	</div>
	
<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
<!-- Mainly scripts -->
<content tag="local_script"> 
<!-- stickup plugin --> 
<script	 src="${ctx}/static/js/plugins/stickup/stickUp.js"></script> 
<script	 src="${ctx}/static/trans/js/workbench/stickDash.js"></script> 
<!-- Toastr script -->
<script  src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
<script src="${ctx}/static/js/morris/morris.js"></script> 
<script src="${ctx}/static/js/morris/raphael-min.js"></script> 
<!-- index_js -->
<script src="${ctx}/static/trans/js/eloan/eloan.js"></script> 
<script	src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- aist --> 
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script	src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
<script	src="${ctx}/js/template.js" type="text/javascript"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script	src="${ctx}/js/jquery.editable-select.min.js"></script>
<script		src="${ctx}/js/plugins/jquery.custom.js"></script>  

<script	id="template_eloanContractList" type="text/html">
        {{each rows as item index}}
		<tr>
			<td class="center">
				<div class="sk-spinner sk-spinner-double-bounce sk-spinner-light mt3">
					<div class="sk-double-bounce1 red_light"></div>
					<div class="sk-double-bounce2 red_light"></div>
				</div>
		    </td>
   		   <td>
       		 <p class="big">
           	 <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
               <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
             </a>
        	 </p>
            <p>
				{{if  item.CONFIRM_STATUS=='0' || item.CONFIRM_STATUS==NULL || item.CONFIRM_STATUS==""}}
                      	 <span class="waiting_color">等待中</span>
				{{/if}}
                {{if  item.LOANTYPE=='1'}}
                      	 <span class="no_color">未通过</span> 
				{{/if}}
				{{if  item.LOANTYPE=='2'}}
                      	 <span class="yes_color">已通过</span> 
				{{/if}}      
        	</p>
       	 </td>
		 <td>
			<p class="big">
				<span class="yes_color">购</span>{{item.PROPERTY_ADDR}}
			</p>
			<p class="big">
				<span class="waiting_color">售</span>{{item.PROPERTY_ADDR}}
			</p>
		</td>
		<td>
				<p><i class="sign_blue"> {{item.LOAN_SRV_CODE}}</i></p>
				<p>{{item.FIN_ORG_NAME}}</p>
		</td>

		<td class="center">
			<span class="manager"><a href="#"><em>{{item.EXCUTORNAME}}</em></a></span>
			<span class="manager"><a href="#"><em>{{item.EXCUTOR_TEAM_NAME}}</em></span>
		</td>

		<td class="center">
			<p class="smll_sign"><i class="sign_normal">申</i>{{item.APPLY_TIME}} </p>
			<p>	<i class="icon iconfont icon_day">&#xe62c;</i>20天</p>
		</td>

		<td class="center">
			<button class="btn btn-success">查看</button>
		</td>
</tr>
{{/each}}
</script> 
<script>
var ctx = "${ctx}";
var serviceDepId = "${serviceDepId}";//引号亦能取值

$(document).ready(function() {
	// 初始化列表		
	var startDate = $("#dtBegin_0").val();
	var endDate = '';
	if (!$.isBlank($("#dtEnd_0").val())) {
		endDate = $("#dtEnd_0").val() + " 23:59:59";
	}
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	data.queryId = "eloanContractListQuery";
	data.rows = 10;
	data.page = 1;

	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : eloanSearchMethod
	});
	reloadGrid(data);

});

// 查询
$('#eloanSearchButton').click(function() {
	eloanSearchMethod();
});

//查询
function eloanSearchMethod(){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "eloanContractListQuery";
	reloadGrid(params);
}


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

	var sortcolumn = $('span.active')
			.attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

	var orgArray = queryOrgs == null ? null : queryOrgs
			.split(",");
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
			//console.log("===Result==="+JSON.stringify(data));		
			$.unblockUI();
			data.ctx = ctx;
			var myEloanContractList = template('template_eloanContractList',data);
			$("#eloanContractList").empty();
			$("#eloanContractList").html(myEloanContractList);
			// 显示分页
			initpage(data.total, data.pagesize,
					data.page, data.records);
		},
		error : function(e, jqxhr, settings,exception) {
			$.unblockUI();
		}
	});
}
//分页
function initpage(totalCount, pageSize, currentPage,records) {
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
			eloanSearchMethod(page);
		}
	});
}

function getParams(){
	
	//申请时间
	var startDate = $("#dtBegin_0").val();
	var endDate = '';
	if (!$.isBlank($("#dtEnd_0").val())) {
		endDate = $("#dtEnd_0").val() + " 23:59:59";
	}
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	//组织
	var organizeOrgId = $('#yuCuiOriGrpId').val().trim();
	data.organizeOrgId=organizeOrgId;
	
	//审核结果和商品类型数据
	var eloanApplyResult = $("#eloanApplyResult option:selected").val();
	var eloanProductType = $("#eloanProductType option:selected").val();
	data.eloanApplyResult=eloanApplyResult;
	data.eloanProductType=eloanProductType;
	
	
	return data;
}

//案件导出excel
$('#eloanContractListToExcel').click(function() {
	var url = "/quickGrid/findPage?xlsx&";
	// excel导出列
	var displayColomn = new Array;
	displayColomn.push('CASE_CODE');
	displayColomn.push('PROPERTY_ADDR');	
/* 	displayColomn.push('SIGN_DATE');
	displayColomn.push('LEND_DATE');
	displayColomn.push('APPR_DATE');	
	displayColomn.push('CUST_NAME');
	displayColomn.push('MORT_TOTAL_AMOUNT');
	displayColomn.push('COM_AMOUNT');	
	displayColomn.push('PRF_AMOUNT');
	displayColomn.push('SDSTATUS');	
	displayColomn.push('FIN_ORG_NAME');
	displayColomn.push('FIN_ORG_NAME_YC');	
	displayColomn.push('IS_TMP_BANK');
	displayColomn.push('REAL_NAME');
	displayColomn.push('ORG_NAME');	
	displayColomn.push('ORG_ORG_NAME'); */

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

	var orgArray = queryOrgs == null ? '' : queryOrgs.split(",");

	var argu_idflag = '&argu_idflag=' + arguUserId;

	if (arguUserId == null)
		argu_idflag = '&argu_idflag=';
	var argu_queryorgs = "&" + jQuery.param({
		argu_queryorgs : orgArray
	});
	if (argu_queryorgs == null)
		argu_queryorgs = '&argu_queryorgs=';
	var params = getParamsValue();	
	var queryId = '&queryId=eloanContractListQuery';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + argu_idflag
			+ argu_queryorgs + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();

})

//组织图标选择
$('#organizeOnclick').click(function() {	
	orgSelect({
		displayId : 'oriGrpId',
		displayName : 'radioOrgName',
		startOrgId : serviceDepId,
		expandNodeId : serviceDepId, //添加此属性的作用是展开 组织列表
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		callBack : eloanRadioYuCuiOrgSelectCallBack
	})
});	

//选业务组织的回调函数
function eloanRadioYuCuiOrgSelectCallBack(array) {	
	if (array && array.length > 0) {
		$("#orgName").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
	} else {
		$("#orgName").val("");
		$("#yuCuiOriGrpId").val("");
	}
}

//点击清除按钮的时候清空表单
$('#eloanListCleanButton').click(function() {
			$("input[name='dtBegin_0']").datepicker('update', '');
			$("input[name='dtEnd_0']").datepicker('update', '');			

			$("input[name='orgName']").val("");
			$("#yuCuiOriGrpId").val("");
			
			$("#eloanProductType").val("");
			$("#eloanApplyResult").val("");
});	
//eloan编号排序
function eloanCodeSort() {
	alert("eloancodesort");
	if ($("#eloanCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#eloanCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#eloanCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#eloanCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#eloanCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}

// 日期控件
$('#datepicker').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

</script> 
</content>
</body>
</html>