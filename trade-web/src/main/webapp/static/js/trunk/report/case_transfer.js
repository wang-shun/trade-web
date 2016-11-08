$(document).ready(function() {
	cleanForm();
	// 基本信息等高
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
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

	// 过户日期
	var transferDateBegin = $('#dtBegin_0').val();
	var transferDateOver = $('#dtEnd_0').val();

	if (transferDateOver && transferDateOver != '') {
		transferDateOver = transferDateOver + ' 23:59:59';
	}
	// 设置查询参数
	var data = {
		transferDateStart : transferDateBegin,
		transferDateEnd : transferDateOver,
	};
	data.queryId = "queryCastTransferItemList";
	data.rows = 12;
	data.page = 1;

	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	reloadGrid(data);
});

// select控件
var config = {
	'.chosen-select' : {},
	'.chosen-select-deselect' : {
		allow_single_deselect : true
	},
	'.chosen-select-no-single' : {
		disable_search_threshold : 10
	},
	'.chosen-select-no-results' : {
		no_results_text : 'Oops, nothing found!'
	},
	'.chosen-select-width' : {
		width : "95%"
	}
};

for ( var selector in config) {
	$(selector).chosen(config[selector]);
};

// 日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN',
});
$('#datepicker_1').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
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

	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	params.queryId = "queryCastTransferItemList";
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

	aist.wrap(data);

	var sortcolumn = $('span.active').attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

	var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
	data.argu_idflag = arguUserId;
	data.argu_queryorgs = orgArray;

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
			var caseTransferList = template('template_caseTransferList', data);
			$("#caseTransferList").empty();
			$("#caseTransferList").html(caseTransferList);
			// 显示分页
			initpage(data.total, data.pagesize, data.page, data.records);
		},
		error : function(e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}

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
			// console.log(page);
			searchMethod(page);
		}
	});
}

function getParamsValue() {

	// 过户日期
	var transferDateBegin = $('#dtBegin_0').val();
	var transferDateOver = $('#dtEnd_0').val();
	// 过户审核日期
	var caseTransferDateBegin = $('#dtBegin_1').val();
	var caseTransferDateOver = $('#dtEnd_1').val();

	var vrealName = $('#realName').val().trim();
	var orgName = $('#orgName').val();
	var managerName = $('#managerName').val();
	var caseCode = $('#caseCode').val().trim();
	var propertyAddr = $('#propertyAddr').val();

	// 审核状态
	var TransferStatus = $('#TransferStatus').val();
	if (transferDateOver && transferDateOver != '') {
		transferDateOver = transferDateOver + ' 23:59:59';
	}
	if (caseTransferDateOver && caseTransferDateOver != '') {
		caseTransferDateOver = caseTransferDateOver + ' 23:59:59';
	}
	// 设置查询参数
	var params = {
		caseTransferDateStart : caseTransferDateBegin,
		caseTransferDateEnd : caseTransferDateOver,
		transferDateStart : transferDateBegin,
		transferDateEnd : transferDateOver,
		vrealName : vrealName,
		orgName : orgName,
		TransferStatus : TransferStatus,
		managerName : managerName,
		caseCode : caseCode,
		propertyAddr : propertyAddr
	};
	return params;
}

// 清空表单
function cleanForm() {
	// $("input[name='transferDateBegin']").val("");
	// $("input[name='transferDateEnd']").val("");
	$("input[name='caseTransferDateBegin']").val("");
	$("input[name='caseTransferDateEnd']").val("");
	$("select").val("");
	$("input[name='realName']").val("");
	$("input[name='orgName']").val("");
	$("input[name='managerName']").val("");	
	$("input[name='caseCode']").val("");
	$("input[name='propertyAddr']").val("");
}

function caseTransferExportToExcel() {
	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	// excel导出列
	var displayColomn = new Array;
	displayColomn.push('BA_NAME');
	displayColomn.push('VREAL_NAME');	
	displayColomn.push('AGENT_ORG_NAME');
	displayColomn.push('AGENT_NAME');
	displayColomn.push('AGENT_MOBILE');	
	displayColomn.push('GUOHU_ORG_NAME');
	displayColomn.push('GUOHU_REAL_NAME');
	displayColomn.push('GUOHUDJ');
	displayColomn.push('dist_name');
	displayColomn.push('REAL_HT_TIME');
	displayColomn.push('START_TIME');
	displayColomn.push('CTM_CODE');
	displayColomn.push('CUST_NAME');
	displayColomn.push('GUEST_PHONE');
	displayColomn.push('PROPERTY_ADDR');	
	displayColomn.push('CON_PRICE');
	displayColomn.push('FIN_ORG_NAME');
	displayColomn.push('FIN_ORG_NAME_YC');
	displayColomn.push('COM_AMOUNT');
	displayColomn.push('PRF_AMOUNT');
	displayColomn.push('ACCOUNT');
	displayColomn.push('COM_DISCOUNT');
	displayColomn.push('CASE_CODE');	
	displayColomn.push('MORT_TYPE_NAME');
	displayColomn.push('SPONSOR');
	displayColomn.push('SDSTATUS');
	displayColomn.push('loanlost_apply_reason');
	displayColomn.push('SELF_DEL_REASON');
	displayColomn.push('EVAL_FEE');
	
	displayColomn.push('SELLER');
	displayColomn.push('SELLER_MOBILE');	
	displayColomn.push('BUYER');
	displayColomn.push('BUYER_MOBILE');
	displayColomn.push('transferDate');
	displayColomn.push('caseTransferDate');	
	displayColomn.push('status1');
	displayColomn.push('ASSESSOR');
	displayColomn.push('LAST_CONTENT');
	displayColomn.push('NOT_APPROVE');
	displayColomn.push('REAL_NAME');
	displayColomn.push('ORG_NAME');
	displayColomn.push('AR_NAME');
	displayColomn.push('VORG_NAME');


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
	var queryId = '&queryId=newQueryCastTransferExcelItemList';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + argu_idflag
			+ argu_queryorgs + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();

}

// 清空
$('#caseTransferCleanButton').click(function() {
	$("input[name='transferDateBegin']").datepicker('update', '');
	$("input[name='transferDateEnd']").datepicker('update', '');
	$("input[name='caseTransferDateBegin']").datepicker('update', '');
	$("input[name='caseTransferDateEnd']").datepicker('update', '');
	$("select").val("");
	$("input[name='realName']").val("");
	$("input[name='orgName']").val("");
	$("input[name='managerName']").val("");
	$("input[name='caseCode']").val("");
	$("input[name='propertyAddr']").val("");
	$("#yuCuiOriGrpId").val("");
});

// 选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
		$("#orgName").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
		$("#managerName").val("");
	} else {
		$("#orgName").val("");
		$("#yuCuiOriGrpId").val("");

	}
}
// 根据组织 选择主管
function chooseManager(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();

	if (yuCuiOriGrpId != "") {
		userSelect({
			startOrgId : yuCuiOriGrpId,
			expandNodeId : yuCuiOriGrpId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'Manager,Senior_Manager',
			callBack : caseTranseferSelectUserBack
		});
		//$("#yuCuiOriGrpId").val("");
	} else {
		userSelect({
			startOrgId : serviceDepId,
			expandNodeId : serviceDepId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'Manager,Senior_Manager',
			callBack : caseTranseferSelectUserBack
		});
	}
}

// 选取人员的回调函数
function caseTranseferSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#managerName").val(array[0].username);
		$("#managerName").attr('hVal', array[0].userId);

	} else {
		$("#managerName").val("");
		$("#managerName").attr('hVal', "");
	}
}
