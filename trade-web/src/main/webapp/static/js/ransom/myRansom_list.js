$(document).ready(function() {
	$("#productType").hide();
	$("#more").click(function() {
		$("#productType").toggle();
	});


	cleanForm();
	aist.sortWrapper({
		reloadGrid : searchMethod
	});

	//基本信息等高
	var cpDivWidth = $("#ransomSearchTime").next().width();
	$('#inTextType').next().css("width", cpDivWidth);
	// 初始化列表
	var data = {};
	//data.queryId = "queryCastListItemList";
	data.queryId = "ransomListItemList";
	data.rows = 10;
	data.page = 1;
	data.argu_isNotResearchCloseCase = "true";
	data.argu_sessionUserId = $("#userId").val();
	aist.wrap(data);
	reloadGrid(data);

	//$("input:checkbox[name='srvCode'][value='30004010']").parent().parent().parent().hide();
	$("span[name='srvCode']").click(function(){
		var id = $(this).attr("id");
		$("span[id='"+id+"']").changeSelect();
	});
});

jQuery.fn.changeSelect = function(params){
	if(this.hasClass("selected")) {
		this.removeClass("selected");
	} else {
		this.addClass("selected");
	}
};

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
	language : 'zh-CN'
});

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

//新增赎楼单
$('#addNewRansomCase').click(function() {
	//window.location.href = ctx+"/ransomCaseMerge/addRansomCase/ransomCase";
	window.location.href = ctx+"/ransomList/ransomCase/addRansom";
	
});
//赎楼单详情
//$('#ransomDetail').click(function() {
//	window.location.href = ctx+"ransomList/ransomBaseInfo";
//});
/**
 * 新建外单案件
 */
$('#addNewWdCase').click(function() {
	window.location.href = ctx+"/caseMerge/addWdCase";
});

// 添加日期查询条件
var divIndex = 1;
var count = $('#ransomSearchTime option:last').index();
function addDateDivTwo() {

	var txt = '<div class="row clearfix add_group"><div id="dateDiv_' + divIndex + '" class="input-group">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
		+ divIndex
		+ '" class="btn btn-white chosen-select chosen_space" name="case_date" type="select" >';
	txt += $("#ransomSearchTime").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
		+ divIndex
		+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
		+ divIndex
		+ '" name="startTime" class="form-control" style="font-size: 13px;border-radius: 0px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
		+ divIndex
		+ '" name="dtEnd" class="form-control" style="font-size: 13px;border-radius: 0px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
		+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$(".date-info").after(txt);
	// 日期控件
	$('#datepicker_' + divIndex).datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	// 设置初始选中
	var selIndex = findFirstNoCheckVal();
	$("#case_date_" + divIndex).get(0).selectedIndex = selIndex;
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	;

	divIndex++;
}

function addDateDiv() {

	var txt = '<div class="row clearfix add_group"><div id="dateDiv_' + divIndex + '" class="input-group">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
		+ divIndex
		+ '" class="btn btn-white chosen-select chosen_space" name="case_date" type="select" >';
	txt += $("#ransomSearchTime").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
		+ divIndex
		+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
		+ divIndex
		+ '" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
		+ divIndex
		+ '" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
		+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$(".date-info").after(txt);
	// 日期控件
	$('#datepicker_' + divIndex).datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	// 设置初始选中
	var selIndex = findFirstNoCheckVal();
	$("#case_date_" + divIndex).get(0).selectedIndex = selIndex;
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
	;

	divIndex++;
}

// 查看未被选择的日期类型
function findFirstNoCheckVal() {

	var onUseArray = new Array();
	if (divIndex > count)
		return 0;
	for (var r = 0; r < divIndex; r++) {
		var onUse = $('#case_date_' + r + ' option:selected').index();
		onUseArray.push(onUse);

	}
	for (var s = 0; s < count; s++) {
		var onUseFlag = false;
		for (var m = 0; m < onUseArray.length; m++) {
			if (s == onUseArray[m]) {
				onUseFlag = true;
				break;
			}
		}
		if (!onUseFlag)
			return s;
	}
	return 0;
}
//删除日期控件
function removeDateDiv(index) {
	$("#dateDiv_" + index).parent().remove();
}

//查询
function searchMethod(page) {
	debugger;
	if(!page) {
		page = 1;
	}
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 10;
		params.queryId = "ransomListItemList";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}

};

function reloadGrid(data) {
	//添加排序-----
	aist.wrap(data);

	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;
	//添加排序----------

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var serviceDepId = $("#serviceDepId").val();
	var arguUserId=null;
	if(queryOrgFlag == 'true'){
		arguUserId=null;
		if(isAdminFlag == 'true'){
			queryOrgs=null;
		}
	}else{
		queryOrgs= null;
		arguUserId="yes";
	}

	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
	data.argu_queryorgs = orgArray;
	data.rows = 10;

	$.ajax({
		async: true,
		url:ctx+ "/quickGrid/findPage" ,
		method: "post",
		dataType: "json",
		data: data,
		beforeSend: function () {
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
			$(".blockOverlay").css({'z-index':'9998'});
		},
		success: function(data){
			$.unblockUI();
			data.ctx = ctx;
			var myCaseList = template('template_myCaseList' , data);
			$("#myCaseList").empty();
			$("#myCaseList").html(myCaseList);
			// 显示分页
			mycase_initpage(data.total,data.pagesize,data.page, data.records);
			demoPoshytip();
			$("#myCaseList").subscribeToggle({
				moduleType:"1001",
				subscribeType:"2001"
			});

		},
		error: function (e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}
/**
 * 页面title显示
 */
function demoPoshytip(){
	$('.demo-left').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'left',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
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
}

function mycase_initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);

	$("#pageBar").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			searchMethod(page);
		}
	});
}


//获取查询参数
function getParamsValue() {
	debugger;
	//赎楼状态
	var ransomStatus = $('#ransomStatus').val();
	//当前赎楼环节
	var ransomProperty = $('#ransomProperty').val();
	//案件归属
	var agentName = $('#agentName').val().trim();
	//信息搜索
	var searchInfo = $('#searchInfo option:selected').val();
	// 客户姓名 归属人姓名 房屋地址
	var inTextVal = "";
	if(searchInfo != ""){
		inTextVal = $('#inTextVal').val();
	}

	//设置查询参数
	//argu_  where条件参数；   seach_ searchCondition参数
	var params = {
		argu_ransomStatus : ransomStatus,
		argu_ransomProperty : ransomProperty,
		argu_agentName : agentName,

		argu_searchInfo : searchInfo,
		argu_inTextVal : inTextVal,

	};
	return params;
}




// 日期类型
var createTimeStart, resDateStart, realHtTimeStart, realPropertyGetTimeStart, realConTimeStart, signDateStart, lendDateStart, approveTimeStart,guohuApproveTimeStart;
var createTimeEnd, resDateEnd, realHtTimeEnd, realPropertyGetTimeEnd, realConTimeEnd, signDateEnd, lendDateEnd, approveTimeEnd,guohuApproveTimeEnd;


function oldGetSearchDateValues() {
	createTimeStart = null;
	resDateStart = null;
	realHtTimeStart = null;
	realPropertyGetTimeStart = null;
	realConTimeStart = null;
	signDateStart = null;
	lendDateStart = null;
	approveTimeStart = null;
	createTimeEnd = null;
	resDateEnd = null;
	realHtTimeEnd = null;
	realPropertyGetTimeEnd = null;
	realConTimeEnd = null;
	signDateEnd = null;
	lendDateEnd = null;
	approveTimeEnd = null;
	guohuApproveTimeStart = null;
	guohuApproveTimeEnd = null;
	var codeStr = "";
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if (codeStr.indexOf(val) != -1)
			return false;
		codeStr += val;
		if (start != "") {
			if (val == 'RANSOMAPPLY') {
				//申请  
				createTimeStart = start;
			} else if (val == 'RANSOMINTERVIEW') {
				//面签
				resDateStart = start;
			} else if (val == 'RANSOMREPAYMENT') {
				//陪同还贷
				realConTimeStart = start;
			} else if (val == 'RANSOMDIYA') {
				//注销抵押
				realHtTimeStart = start;
			} else if (val == 'RANSOMPERMIT') {
				//领取产证
				realPropertyGetTimeStart = start;
			} else if (val == 'RANSOMPAYMENT') {
				//回款结清
				signDateStart = start;
			} 
		}
		if (end != "") {
			if (val == 'RANSOMAPPLY') {
				//申请  
				createTimeStart = start;
			} else if (val == 'RANSOMINTERVIEW') {
				//面签
				resDateStart = start;
			} else if (val == 'RANSOMREPAYMENT') {
				//陪同还贷
				realConTimeStart = start;
			} else if (val == 'RANSOMDIYA') {
				//注销抵押
				realHtTimeStart = start;
			} else if (val == 'RANSOMPERMIT') {
				//领取产证
				realPropertyGetTimeStart = start;
			} else if (val == 'RANSOMPAYMENT') {
				//回款结清
				signDateStart = start;
			} 
		}
	}
	return true;
}


// 日期控件取值
function getSearchDateValues() {

	createTimeStart = null;
	createTimeEnd = null;

	resDateStart = null;
	resDateEnd = null;

	realHtTimeStart = null;
	realHtTimeEnd = null;

	realPropertyGetTimeStart = null;
	realPropertyGetTimeEnd = null;

	realConTimeStart = null;
	realConTimeEnd = null;

	lendDateStart = null;
	lendDateEnd = null;

	signDateStart = null;
	signDateEnd = null;

	approveTimeStart = null;
	approveTimeEnd = null;

	guohuApproveTimeStart = null;
	guohuApproveTimeEnd = null;

	var codeStr = "";
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if(end&&end!=''){
			end=end+' 23:59:59';
		}
		if (codeStr.indexOf(val) != -1)
			return false;
		codeStr += val;
		if (start != "") {
			if (val == 'RANSOMAPPLY') {
				//申请  
				createTimeStart = start;
			} else if (val == 'RANSOMINTERVIEW') {
				//面签
				resDateStart = start;
			} else if (val == 'RANSOMREPAYMENT') {
				//陪同还贷
				realConTimeStart = start;
			} else if (val == 'RANSOMDIYA') {
				//注销抵押
				realHtTimeStart = start;
			} else if (val == 'RANSOMPERMIT') {
				//领取产证
				realPropertyGetTimeStart = start;
			} else if (val == 'RANSOMPAYMENT') {
				//回款结清
				signDateStart = start;
			} 
		}
		if (end != "") {
			if (val == 'RANSOMAPPLY') {
				createTimeEnd = end;
			} else if (val == 'RANSOMINTERVIEW') {
				resDateEnd = end;
			} else if (val == 'RANSOMREPAYMENT') {
				realConTimeEnd = end;
			} else if (val == 'RANSOMDIYA') {
				realHtTimeEnd = end;
			} else if (val == 'RANSOMPERMIT') {
				realPropertyGetTimeEnd = end;
			} else if (val == 'RANSOMPAYMENT') {
				signDateEnd = end;
			} 
		}
	}
	return true;
}

// 清空表单
function cleanForm() {
	$("input[name='case_property'][value=30003006]").prop("checked", true);
	$("input[name='case_status'][value=30001006]").prop("checked", true);
	$("input[name='srvCode']").removeAttr("checked");
	$("input[name='startTime']").val("");
	$("input[name='dtEnd']").val("");
	$("#inTextVal").val("");
	checkAllItem();
}
//全选
function checkAllItem() {
	$(".excel_in").prop("checked", true);
	$("#checkAll").hide();
	$("#unCheckAll").show();
}
//反选
function unCheckAllItem() {
	$(".excel_in").removeAttr("checked");
	$("#unCheckAll").hide();
	$("#checkAll").show();

}
//show modal
function showExcelIn() {
	checkAllItem();
	$('#modal-form').modal("show");
}

//show modal
function realShowExcelIn() {
	checkAllItem();
	$('#realModal-form').modal("show");
}

//Excel显示列
var oldColNames = {
	30010001 : [ 'AGENT_NAME','AGENT_PHONE','AGENT_EMP_CODE','AGENT_ORG', 'AGENT_FHJL', 'JFHJL_MGR_CODE','AGENT_QYJL','JQYJL_MGR_CODE',
		'AGENT_QYZJ','JQYZJ_MGR_CODE','AGENT_QYDS','JQYDS_MGR_CODE','AGENT_QDORG' ],
	30010002 : [ 'OP_NAME', 'OP_CODE', 'OP_ORG', 'OP_MANAGER','OP_AS',
		'OP_PARENT_ORG','SRV_STR','PROCESSOR_ID','PROCESSOR_ORG_NAME'],
	30010003 : [ 'PROPERTY_ADDR', 'SELLER', 'SELLER_PHONE', 'BUYER',
		'BUYER_PHONE' ],
	30010004 : 'CREATE_TIME',
	30010005 : 'RES_DATE',
	30010006 : 'CASE_PROPERTY',
	30010007 : 'STATUS',
	30010008 : 'AGENT_QDORG',
	30011001 : ['LOAN_REQ','MORT_TYPE2','LOAN_LOST_TYPE'],
	//30011009 : ,
	30011002 : [ 'MORT_TOTAL_AMOUNT', 'COM_AMOUNT', 'PRF_AMOUNT' ],
	30011003 : [ 'BANK_NAME', 'PARENT_BANK_NAME' ],
	30011004 : [ 'LOANER_NAME', 'IS_LOANER_ARRIVE' ],
	30011005 : 'EVA_NAME',
	30011006 : [ 'CUST_NAME', 'CUST_PHONE' ],
	30011007 : [ 'SIGN_DATE', 'APPR_DATE', 'LEND_DATE' ],
	30011008 : 'COM_DISCOUNT',
	30011010 : 'REC_LETTER_NO',
	30012001 : [ 'REAL_PRICE', 'CON_PRICE','CHECK_PRICE','UNIT_PRICE' ],
	30012002 : [ 'REAL_CON_TIME', 'TAX_TIME', 'PRICING_TIME', 'REAL_PLS_TIME',
		'REAL_HT_TIME', 'GUOHU_CREATE_TIME','GUOHU_COMMENT','REAL_PROPERTY_GET_TIME','CLOSE_TIME','HOUSE_QUANTITY','TRANSFER_HOUSE_HODING_TAX','TRANSFER_PERSONAL_INCOME_TAX','TRANSFER_BUSINESS_TAX','TRANSFER_CONTRACT_TAX','TRANSFER_LAND_INCREMENT_TAX','TAX_HOUSE_HODING_TAX','TAX_PERSONAL_INCOME_TAX','TAX_BUSINESS_TAX','TAX_CONTRACT_TAX','TAX_LAND_INCREMENT_TAX'],
	30013001 : 'SPV_TYPE',
	30013002 : 'AMOUNT',
	30013003 : 'SIGN_TIME',
	30012003:['GUOHU_APPROVER','GUOHU_APPROVER_TIME']
};


function exportToExcel() {
	if (oldGetSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('CTM_CODE');
		displayColomn.push('AGENT_FHJL_CODE');
		$("input[name='basic_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		$("input[name='mortage_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		/*新增确认函编号*/
		displayColomn.push('REC_LETTER_NO');
		displayColomn.push('LOANLOST_CONFIRM_CODE');
		displayColomn.push('SELF_DEL_REASON');
		displayColomn.push('loanlost_apply_reason');
		$("input[name='trade_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		$("input[name='fund_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(oldColNames[val]);
		});
		displayColomn.push('EVAL_FEE');
		displayColomn.push('RECORD_TIME');

		var queryOrgFlag = $("#queryOrgFlag").val();
		var isAdminFlag = $("#isAdminFlag").val();
		var queryOrgs = $("#queryOrgs").val();
		var arguUserId=null;
		if(queryOrgFlag == 'true'){
			arguUserId=null;
			if(isAdminFlag == 'true'){
				queryOrgs=null;
			}
		}else{
			queryOrgs= null;
			arguUserId="yes";
		}

		var orgArray = queryOrgs==null?'':queryOrgs.split(",");

		var argu_idflag = '&argu_idflag='+arguUserId;

		if(arguUserId==null)argu_idflag='&argu_idflag=';
		var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
		if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
		var queryId = '&queryId=queryCaseExcelItemList';
		var colomns = '&colomns=' + displayColomn;


		url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
		//url+= "&_s(earch=true";
		//url= decodeURI(url);
//		alert(url);
		$('#excelForm').attr('action', url);

		$('#excelForm').method="post" ;
		$('#excelForm').submit();
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
}

/*
 * 2017-04-11
 newExcel显示列
 */
var newColNames = {
	30010001 : [ 'AGENT_NAME', 'AGENT_PHONE','AGENT_EMPLOYEE_CODE','GRP_NAME', 'JFHJL_NAME', 'JFHJL_EMPLOYEE_CODE','JQYJL_NAME','JQYJL_EMPLOYEE_CODE',
		'JQYZJ_NAME','JQYZJ_EMPLOYEE_CODE','JQYDS_NAME','JQYDS_EMPLOYEE_CODE','BA_NAME' ],
	30010002 : [ 'CASE_REAL_NAME_F', 'CASE_EMPLOYEE_CODE_F', 'CASE_ORG_NAME_F', 'CASE_MANAGER_REAL_NAME','CASE_ASSISTANT_REAL_NAME','CASE_DISTRICT_NAME','CASE_SRV_NAME','CASE_SRV_REAL_NAME','CASE_SRV_ORG_NAME'],

	30010003 : [ 'HOUSR_PROPERTY_ADDR', 'GUEST_NAME_UP', 'GUEST_PHONE_UP', 'GUEST_NAME_DOWN','GUEST_PHONE_DOWN' ],

	30010004 : 'CASE_CREATE_TIME',
	30010005 : 'CASE_DISPATCH_TIME',
	30010006 : 'CASE_PROPERTY_CN', //英文
	30010007 : 'CASE_STATUS_CN',
	30010008 : 'AGENT_QDORG',

	30011001 : ['CASE_LOAN_REQ_CN','MORT_TYPE_CN','IS_DELEGATE_YUCUI_CN'],
	//30011009 : ,
	30011002 : [ 'MORT_TOTAL_AMOUNT', 'MORT_COM_AMOUNT', 'MORT_PRF_AMOUNT' ],
	30011003 : [ 'MORT_FIN_BRANCH_NAME', 'MORT_FIN_SUB_BRANCH_NAME' ],
	30011004 : [ 'MORT_LOANER_NAME', 'IS_LOANER_ARRIVE_CN' ],
	30011005 : 'CASE_EVA_COMPANY',
	30011006 : [ 'MORT_CUST_NAME', 'MORT_CUST_PHONE' ],
	30011007 : [ 'MORT_SIGN_DATE', 'MORT_APPR_DATE', 'MORT_LEND_DATE' ],
	30011008 : 'MORT_COM_DISCOUNT',
	30011010 : 'LOAN_REC_LETTER_NO',
	30012001 : [ 'CASE_REAL_PRICE', 'CASE_CON_PRICE','CASE_TAX_PRICING','CASE_HOUSE_UNIT_PRICE' ],
	30012002 : [ 'CASE_REAL_CON_TIME', 'CASE_TAX_TIME', 'CASE_PRICING_TIME', 'CASE_REAL_PLS_TIME','TRANSFER_REAL_HT_TIME','TRANSFER_CREATE_TIME','TRANSFER_COMMENT','CASE_REAL_PROPERTY_GET_TIME','CASE_CLOSE_TIME','SIGN_HOUSE_QUANTITY_CN','TRANSFER_HOUSE_HODING_TAX','TRANSFER_PERSONAL_INCOME_TAX','TRANSFER_BUSINESS_TAX','TRANSFER_CONTRACT_TAX','TRANSFER_LAND_INCREMENT_TAX','TAX_HOUSE_HODING_TAX','TAX_PERSONAL_INCOME_TAX','TAX_BUSINESS_TAX','TAX_CONTRACT_TAX','TAX_LAND_INCREMENT_TAX'],
	30013001 : 'SPV_TYPE_CN',
	30013002 : 'SPV_AMOUNT',
	30013003 : 'SPV_SIGN_TIME',
	30012003:['TRANSFER_APP_OPERATOR_NAME','TRANSFER_APP_PASS_TIME']
};


function newExportToExcel() {
	if (getSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('CTM_CODE');
		$("input[name='basic_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		$("input[name='mortage_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});

		/*新增确认函编号*/
		displayColomn.push('LOAN_LOST_CONFIRM_CODE');
		displayColomn.push('LOAN_SELF_DEL_REASON');
		displayColomn.push('LOAN_LOST_APPLY_REASON');

		$("input[name='trade_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		$("input[name='fund_info_item']:checked").each(function() {
			var val = this.value;
			displayColomn.push(newColNames[val]);
		});
		displayColomn.push('EVA_EVAL_FEE');
		displayColomn.push('EVA_RECORD_TIME');

		var queryOrgFlag = $("#queryOrgFlag").val();
		var isAdminFlag = $("#isAdminFlag").val();
		var queryOrgs = $("#queryOrgs").val();
		var arguUserId=null;
		if(queryOrgFlag == 'true'){
			arguUserId=null;
			if(isAdminFlag == 'true'){
				queryOrgs=null;
			}
		}else{
			queryOrgs= null;
			arguUserId="yes";
		}

		var orgArray = queryOrgs==null?'':queryOrgs.split(",");

		var argu_idflag = '&argu_idflag='+arguUserId;

		if(arguUserId==null)argu_idflag='&argu_idflag=';
		var argu_queryorgs = "&"+jQuery.param({argu_queryorgs:orgArray});
		if(argu_queryorgs==null)argu_queryorgs='&argu_queryorgs=';
		var params = getParamsValue();
		var queryId = '&queryId=newQueryCaseExcelItemList';//queryCaseExcelItemList
		var colomns = '&colomns=' + displayColomn;


		url = ctx + url + jQuery.param(params) + queryId +argu_idflag+argu_queryorgs + colomns;
		$('#excelForm').attr('action', url);

		$('#excelForm').method="post" ;
		$('#excelForm').submit();
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
}


function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
	if(inTextType=='4'){
		initAutocomplete(ctx+"/labelVal/queryUserInfo");
	}else if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
}

//自动补全插件
function initAutocomplete(url){
	$("#inTextVal").AutoComplete({
		data:url,
		'itemHeight': 20,
		'width': 280,
		maxItems:10,
		ajaxType:'POST',
		beforeLoadDataHandler:function(){
			$("#inTextVal").attr('hVal','');
			return true;
		},
		afterSelectedHandler:function(data){
			if(data&&data.value){
				$("#inTextVal").attr('hVal',data.value);
			}else{
				$("#inTextVal").attr('hVal','');
			}
		}
	}).AutoComplete('show');
}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
		$("#teamCode").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
	} else {
		$("#teamCode").val("");
		$("#yuCuiOriGrpId").val("");
	}
}
//清空
$('#myCaseListCleanButton').click(function() {
	//$("input[id='inTextVal']").val('');
	//$("input[name='teamCode']").val('');
	$("input[name='startTime']").val('');
	$("input[name='dtEnd']").val('');
	$("span[name='srvCode']").removeClass("selected");
	//$("select").val("");
	$("#caseProperty").val("");
	$("#status").val("");
	$("#mortageService").val("");
	$("#inTextVal").val("");

});


function caseCodeSort(){
	if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
	}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
		$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
	}else{
		$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
	}
}

function ts(status){
	window.wxc.alert(status+"案件不能拆分案件！");
}

function hlts(){
	window.wxc.alert("没有找到可以合流的案件！");
}