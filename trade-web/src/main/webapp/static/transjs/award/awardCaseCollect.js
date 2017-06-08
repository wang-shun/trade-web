var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

$(document).ready(function() {
	
	// 初始化列表
	var data = {};	
	data.queryId = "findAwardCaseCollectList";
	data.rows = 10;
	data.page = 1;
	var belongMonth = $.trim($("#belongMonth").val());	
	data.belongMonth = belongMonth;
	reloadGrid(data);
	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : awardCaseCollectSearchMethod
	});
	
	//top
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});	
});


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
	data.argu_queryorgs = orgArray;
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
			var awardCaseCollectList = template('template_awardCaseCollectList', data);
			$("#awardCaseCollectList").empty();
			$("#awardCaseCollectList").html(awardCaseCollectList);
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
	$(function() {
		// top
		$('.demo-top').poshytip({
			className : 'tip-twitter',
			showTimeout : 1,
			alignTo : 'target',
			alignX : 'center',
			alignY : 'top',
			offsetX : 8,
			offsetY : 5,
		});
	});

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
			awardCaseCollectSearchMethod(page);
		}
	});
}


/* 查询按钮查询 */
$('#awardCaseCollectSearch').click(function() {
	awardCaseCollectSearchMethod();
});

/* 清空查询条件 */
$('#awardCaseCollectClean').click(function() {
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[name='awardStatus']").val('');		
	$("input[name='propertyAddr']").val('');	
	$("input[name='caseCode']").val('');	
	$("input[name='belongMonth']").val('');

});

function awardCaseCollectSearchMethod(page){
	if (!page) {
		page = 1;
	}
	var params = getParams();
	params.page = page;
	params.rows = 10;
	params.queryId = "findAwardCaseCollectList";
	
	reloadGrid(params);
}

/**
 * 查询参数取得
 */

function getParams() {	
	// 设置查询参数
	var params = {};
	
	// 员工姓名
	var caseCode = $("#caseCode").val().trim();
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}
	// 员工姓名
	var propertyAddr = $("#propertyAddr").val().trim();
	if ("" == propertyAddr || null == propertyAddr) {
		propertyAddr = null;
	}

	// 员工姓名组织Id
	var awardStatus = $.trim($("#awardStatus").val());
	if (awardStatus == "" || awardStatus == null || awardStatus =="all") {
		awardStatus = null;
	}
	
	//签约
	var guohuApproveTimeStart =  $('#dtBegin_0').val();
	var guohuApproveTimeEnd = $('#dtEnd_0').val();
	if (guohuApproveTimeEnd && guohuApproveTimeEnd != '') {
		guohuApproveTimeEnd = guohuApproveTimeEnd + ' 23:59:59';
	}
	// 计件年月
	var belongMonth = $.trim($("#belongMonth").val());	
	if("" != belongMonth && null != belongMonth ){
		belongMonth = belongMonth + "-01";
	}
	
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;	
	params.belongMonth = belongMonth;
	params.awardStatus = awardStatus;
	params.guohuApproveTimeStart = guohuApproveTimeStart;
	params.guohuApproveTimeEnd = guohuApproveTimeEnd;
	
	return params;
}


function exportAwardBaseToExcel(){

	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();	
	// excel导出列
	var displayColomn = new Array;
	
	displayColomn.push('AWARD_MONTH');
	displayColomn.push('CASE_CODE');
	displayColomn.push('PROPERTY_ADDR');	
	displayColomn.push('FRONT_LEADING_PROCESS_CN');
	displayColomn.push('FRONT_ORG_ID_CN');
	displayColomn.push('BACK_LEADING_PROCESS_CN');
	displayColomn.push('BACK_ORG_ID_CN');	
	displayColomn.push('GUOHU_APPROVE_TIME');
	displayColomn.push('AWARD_STATUS_CN');	
	displayColomn.push('BASE_CASE_AMOUNT');
	displayColomn.push('AWARD_DESC');
	
	var params = getParams();	
	var queryId = '&queryId=findAwardCaseCollectList';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + ""
			+ "" + colomns;	
	
	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();
	
}

//案件case_code排序图标变化函数
function caseCodeSort() {
	if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}


//日期控件,只能选择月份
$('#datepicker_0').datepicker({
	format: 'yyyy-mm',  
    weekStart: 1,  
    autoclose: true,  
    startView: 'year',
    maxViewMode: 1,
    minViewMode:1,
	todayBtn : 'linked',
	language : 'zh-CN',
});

//日期控件
$('#datepicker_1').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});