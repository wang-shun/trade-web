$(document).ready(function() {
	cleanForm();
	// 基本信息等高
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	// 过户日期
	var transferDateBegin = $('#dtBegin_0').val();
	var transferDateOver = $('#dtEnd_0').val();

	if (transferDateOver && transferDateOver != '') {
		transferDateOver = transferDateOver + ' 23:59:59';
	}
	// 设置查询参数	alert(transferDateStart);
	var data = {
		transferDateStart : transferDateBegin,
		transferDateEnd : transferDateOver,
	};
	data.queryId = "queryCastTransferItemList";
	data.rows = 10;
	data.page = 1;
	data.serviceDepId =$("#serviceDepId").val();

	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	reloadGrid(data);

});

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
	params.rows = 10;
	params.queryId = "queryCastTransferItemList";
	reloadGrid(params);

};

function reloadGrid(data) {

	aist.wrap(data);

	var sortcolumn = $('span.active').attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

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
	//var orgName = $('#orgName').val();
	var orgName = $('#yuCuiOriGrpId').val();
	//var managerName = $('#managerName').val();
	var managerName = $('#managerId').val();
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
		propertyAddr : propertyAddr,
		serviceDepId :$("#serviceDepId").val(),
		jobCode : $("#jobCode").val()
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

/*
 * 过户优化后的导出字段信息
 * */
function newCaseTransferExportToExcel() {

	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	// excel导出列
	var displayColomn = new Array;
	displayColomn.push('BA_NAME');
	displayColomn.push('JQYDS_NAME');
	displayColomn.push('GRP_NAME');
	displayColomn.push('AGENT_NAME');
	displayColomn.push('AGENT_PHONE');

	displayColomn.push('CASE_DISTRICT_NAME');
	displayColomn.push('TRANSFER_TRADE_NAME');
	displayColomn.push('CASE_HOUSE_UNIT_PRICE');
	displayColomn.push('HOUSR_DIST_CODE_CN');
	displayColomn.push('SIGN_HOUSE_QUANTITY_CN');
	//displayColomn.push('dist_name');
	displayColomn.push('TRANSFER_REAL_HT_TIME');
	displayColomn.push('TRANSFER_CREATE_TIME');
	displayColomn.push('CTM_CODE');
	displayColomn.push('MORT_CUST_NAME');
	displayColomn.push('MORT_CUST_PHONE');
	displayColomn.push('HOUSR_PROPERTY_ADDR');
	displayColomn.push('CASE_CON_PRICE');
	displayColomn.push('MORT_FIN_BRANCH_NAME');
	displayColomn.push('MORT_FIN_SUB_BRANCH_NAME');

	displayColomn.push('MORT_COM_AMOUNT');
	displayColomn.push('MORT_PRF_AMOUNT');
	displayColomn.push('MORT_TOTAL_AMOUNT');
	displayColomn.push('MORT_COM_DISCOUNT');
	displayColomn.push('CASE_CODE');

	displayColomn.push('MORT_TYPE_CN');
	displayColomn.push('CASE_REAL_NAME_F');
	displayColomn.push('CASE_REC_STATUS_CN');
	displayColomn.push('LOAN_LOST_APPLY_REASON');
	displayColomn.push('LOAN_SELF_DEL_REASON');
	displayColomn.push('EVA_EVAL_FEE');

	displayColomn.push('GUEST_NAME_UP');
	displayColomn.push('GUEST_PHONE_UP');
	displayColomn.push('GUEST_NAME_DOWN');
	displayColomn.push('GUEST_PHONE_DOWN');

	displayColomn.push('TRANSFER_SUB_TIME');
	displayColomn.push('TRANSFER_APP_TIME');
	displayColomn.push('TRANSFER_ISPASS_CN');
	displayColomn.push('TRANSFER_APP_OPERATOR_NAME');
	displayColomn.push('TRANSFER_LAST_CONTENT');
	displayColomn.push('TRANSFER_NOT_APPROVE');
	displayColomn.push('CASE_MANAGER_REAL_NAME');
	displayColomn.push('CASE_MANAGER_ORG_NAME');
	displayColomn.push('AR_NAME');

	displayColomn.push('ELOAN_PRO');
	displayColomn.push('ELOAN_PRO_AMOUNT');
	displayColomn.push('ELOAN_KA');
	displayColomn.push('ELOAN_KA_AMOUNT_STR');
	displayColomn.push('CASE_USE_CARD_PAY_CN');
	displayColomn.push('CASE_CARD_PAY_AMOUNT');

	var params = getParamsValue();
	var queryId = '&queryId=optimizeQueryCastTransferExcelItemList';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + ""
		+ "" + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();

}

/*
 * 过户优化前的导出
 * */
function oldCaseTransferExportToExcel() {

	var url = "/quickGrid/findPage?xlsx&";
	var ctx = $("#ctx").val();
	// excel导出列
	var displayColomn = new Array;
	displayColomn.push('BA_NAME');
	displayColomn.push('QJDS_NAME');
	displayColomn.push('GRP_NAME');
	displayColomn.push('AGENT_NAME');
	displayColomn.push('AGENT_PHONE');

	displayColomn.push('GUOHU_ORG_NAME');
	displayColomn.push('GUOHU_REAL_NAME');
	displayColomn.push('GUOHUDJ');
	displayColomn.push('DISTNAME');
	displayColomn.push('HOUSE_QUANTITY');

	//displayColomn.push('dist_name');
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
	displayColomn.push('TRANSFERDATE');
	displayColomn.push('CASETRANSFERDATE');
	displayColomn.push('N_STATUS');
	displayColomn.push('ASSESSOR');
	displayColomn.push('LAST_CONTENT');
	displayColomn.push('NOT_APPROVE');
	displayColomn.push('REAL_NAME');
	displayColomn.push('ORG_NAME');
	displayColomn.push('AR_NAME');
	displayColomn.push('VORG_NAME');

	displayColomn.push('ELOAN_PRO_TYPE');
	displayColomn.push('ELOAN_APPLYAMOUNT_COUNT');
	displayColomn.push('ELOAN_PRO_TYPE_KA');
	displayColomn.push('ELOAN_APPLYAMOUNT_COUNT_STRING');
	displayColomn.push('USE_CARD_PAY');
	displayColomn.push('CARD_PAY_AMOUNT');

	displayColomn.push('ACCOMPANY');
	displayColomn.push('ACCOMPANY_REASON');

	var params = getParamsValue();
	var queryId = '&queryId=queryCastTransferExcelItemList';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + ""
		+ "" + colomns;

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
	$("#managerId").val("");
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
		$("#managerId").val(array[0].userId);
	} else {
		$("#managerName").val("");
		$("#managerName").attr('hVal', "");
	}
}
