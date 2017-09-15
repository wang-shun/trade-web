$(document).ready(function() {
	//产品类型toggle
	$("#productType").hide();
	$("#more").click(function() {
		$("#productType").toggle();
	});


	cleanForm();
	
	aist.sortWrapper({
		reloadGrid : searchMethod
	});

	//基本信息等高
	var cpDivWidth = $("#case_date_0").next().width();
	$('#inTextType').next().css("width", cpDivWidth);
	// 初始化列表
	var data = {};
	data.queryId = "queryCastListItemList";
	data.rows = 10;
	data.page = 1;
	data.argu_isNotResearchCloseCase = "true";//默认非结案案件
	data.argu_sessionUserId = $("#userId").val();
	aist.wrap(data);//格式化，添加排序字段及排序正反序
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

//新增案件
$('#addNewCase').click(function() {
	window.location.href = ctx+"/caseMerge/addCase/case";
});

//新建外单案件
$('#addNewWdCase').click(function() {
	window.location.href = ctx+"/caseMerge/addWdCase";
});

// 添加日期查询条件
var divIndex = 1;
//获取最大索引值
var count = $('#case_date_0 option:last').index();

//添加日期条件
function addDateDiv() {

	var txt = '<div class="row clearfix add_group"><div id="dateDiv_' + divIndex + '" class="input-group">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
		+ divIndex
		+ '" class="btn btn-white chosen-select chosen_space" name="case_date" type="select" >';
	txt += $("#case_date_0").html()
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

// 查看未被选择的日期类型，选项排除已选的下标
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
	if(!page) {
		page = 1;
	}
	//查询日期是否都已OK
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 10;
		params.queryId = "queryCastListItemList";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}

};

function reloadGrid(data) {
	//添加排序-----
	aist.wrap(data);

	/*var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;*/
	//添加排序----------

	//交易顾问：arguUserId;非则判断部门层次,组别:组织ID;区域:所有部门ID;非无条件
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
			var myCaseList2 = template('template_myCaseList1' , data);
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

//分页
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

	// 案件类型
	var caseProperty = $('#caseProperty option:selected').val().trim();
	//案件是否关注
	var isSubscribeFilter = $('#isSubscribeFilter option:selected').val();
	if(isSubscribeFilter==null || isSubscribeFilter=='') {
		isSubscribeFilter = -1;
	}
	// 付款方式
	var mortageType = $('#mortageService option:selected').val();
	if(mortageType==null || mortageType=='') {
		mortageType = -1;
	}
	//案件状态
	var status = $('#status option:selected').val();
	//案件组织条件
	var yuCuiOriGrpId =  $('#yuCuiOriGrpId').val();
	// 选择全部案件，条件无
	if (caseProperty == '30003006'){
		caseProperty = null;
	}
	// 如果不是结案结案
	var isNotResearchCloseCase;
	if(caseProperty != '30003002') {
		isNotResearchCloseCase = "true";
	}
	//状态为未指定，条件无
	if (status == '30001006'){
		status = null;
	}
	// 产品类型
	getCheckBoxValues("srvCode");
	// 客户姓名 物业地址 经纪人
	var inTextVal = $('#inTextVal').val();
	//条件为权证专员及所属分行时，AutoComplete自动查询补全数据,intextTypeChange()
	var hVal = $('#inTextVal').attr('hVal');

	var guestName = "";
	var agentName = "";
	var proName = "";
	var propertyAddr = "";
	var agentOrgName = "";
	// caseCode与ctmCode
	var caseCode =  "";
	var ctmCode = "";
	if (inTextVal != null && inTextVal.trim() != "") {
		var inTextType = $('#inTextType').val();
		if (inTextType == '0') {
			guestName = inTextVal.trim();//客户名称
		} else if (inTextType == '1') {
			propertyAddr = inTextVal.trim();//产权地址
		} else if (inTextType == '2') {
			agentName = inTextVal.trim();//经纪人名称
		}else if (inTextType == '3') {
			agentOrgName = hVal.trim();//自动补全数据，所属分行
		}else if (inTextType == '4') {
			proName = hVal.trim();//自动补全数据，权证专员
		}else if (inTextType == '5') {
			caseCode = inTextVal.trim();//案件编号
		}else if (inTextType == '6') {
			ctmCode = inTextVal.trim();//成交报告编号
		}
	}

	//设置查询参数
	//argu_  where条件参数；   seach_ searchCondition参数
	var params = {
		argu_sessionUserId : $("#userId").val(),
		argu_isSubscribeFilter : isSubscribeFilter,
		/*argu_caseOriginType : caseOriginType,*/
		argu_mortageType : mortageType,

		argu_caseCode : caseCode,
		argu_ctmCode : ctmCode,
		argu_caseProperty : caseProperty,
		argu_status : status,

		argu_guestname : guestName, //客户姓名
		argu_agentName :agentName, //经纪人姓名  直接姓名，不是id

		argu_proName : proName,  //交易顾问的userid
		argu_agentOrgName : agentOrgName, //所属分行  orgid

		argu_propertyAddr : propertyAddr,
		argu_srvCode : srvCode,
		argu_srvCode1 : srvCode1,
		argu_srvCode2 : srvCode2,
		argu_srvCode3 : srvCode3,
		argu_srvCode4 : srvCode4,
		argu_srvCode5 : srvCode5,
		argu_srvCode6 : srvCode6,
		argu_srvCode7 : srvCode7,
		argu_srvCode8 : srvCode8,
		argu_srvCode9 : srvCode9,
		argu_srvCode11 : srvCode11,
		argu_srvCode12 : srvCode12,
		argu_srvCode13 : srvCode13,
		argu_srvCode14 : srvCode14,
		argu_srvCode15 : srvCode15,

		argu_createTimeStart : createTimeStart,
		argu_resDateStart : resDateStart,
		argu_approveTimeStart : approveTimeStart,
		argu_createTimeEnd : createTimeEnd,
		argu_resDateEnd : resDateEnd,
		argu_approveTimeEnd : approveTimeEnd,

		argu_realConTimeStart : realConTimeStart,
		argu_realConTimeEnd : realConTimeEnd,
		argu_realConTime : (realConTimeStart == null && realConTimeEnd == null ? null : true),

		argu_realHtTimeStart : realHtTimeStart,
		argu_realHtTimeEnd : realHtTimeEnd,
		argu_realHtTime : (realHtTimeStart == null && realHtTimeEnd == null ? null : true),

		argu_realPropertyGetTimeStart : realPropertyGetTimeStart,
		argu_realPropertyGetTimeEnd : realPropertyGetTimeEnd,
		argu_realPropertyGetTime : (realPropertyGetTimeStart == null && realPropertyGetTimeEnd == null ? null : true),

		argu_signDateStart : signDateStart,
		argu_signDateEnd : signDateEnd,
		argu_lendDateStart : lendDateStart,
		argu_lendDateEnd : lendDateEnd,
		argu_signlendDate : (signDateStart == null && signDateEnd == null && lendDateStart == null && lendDateEnd == null ? null : true),

		argu_guohuApproveTimeStart : guohuApproveTimeStart,
		argu_guohuApproveTimeEnd : guohuApproveTimeEnd,
		argu_guohuApproveTime : (guohuApproveTimeStart == null && guohuApproveTimeEnd == null ? null : true),


		argu_yuCuiOriGrpId : yuCuiOriGrpId,
		argu_isNotResearchCloseCase : isNotResearchCloseCase

	};
	return params;
}

//待dict确认再算
// 日期类型
var createTimeStart, resDateStart, realHtTimeStart, realPropertyGetTimeStart, realConTimeStart, signDateStart, lendDateStart, approveTimeStart,guohuApproveTimeStart;
var createTimeEnd, resDateEnd, realHtTimeEnd, realPropertyGetTimeEnd, realConTimeEnd, signDateEnd, lendDateEnd, approveTimeEnd,guohuApproveTimeEnd;

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
			if (val == '30005001') {
				//派单日期  CASE_CREATE_TIME
				createTimeStart = start;
			} else if (val == '30005002') {
				//分单日期  CASE_DISPATCH_TIME
				resDateStart = start;
			} else if (val == '30005003') {
				//签约日期  CASE_REAL_CON_TIME
				realConTimeStart = start;
			} else if (val == '30005004') {
				//过户日期  TRANSFER_REAL_HT_TIME
				realHtTimeStart = start;
			} else if (val == '30005005') {
				//领证日期  CASE_REAL_PROPERTY_GET_TIME
				realPropertyGetTimeStart = start;
			} else if (val == '30005006') {
				//签约日期（贷款）  MORT_SIGN_DATE
				signDateStart = start;
			} else if (val == '30005007') {
				//放款日期（贷款）  MORT_LEND_DATE
				lendDateStart = start;
			} else if (val == '30005008') {
				//结案日期  CASE_CLOSE_TIME
				approveTimeStart = start;
			} else if (val == '30005009') {
				//过户审批日期 TRANSFER_APP_TIME
				guohuApproveTimeStart = start;
			}
		}
		if (end != "") {
			if (val == '30005001') {
				createTimeEnd = end;
			} else if (val == '30005002') {
				resDateEnd = end;
			} else if (val == '30005003') {
				realConTimeEnd = end;
			} else if (val == '30005004') {
				realHtTimeEnd = end;
			} else if (val == '30005005') {
				realPropertyGetTimeEnd = end;
			} else if (val == '30005006') {
				signDateEnd = end;
			} else if (val == '30005007') {
				lendDateEnd = end;
			} else if (val == '30005008') {
				approveTimeEnd = end;
			} else if (val == '30005009') {
				guohuApproveTimeEnd = end;
			}
		}
	}
	return true;
}

// 产品类型
var srvCode;
var srvCode1;
var srvCode2;
var srvCode3;
var srvCode4;
var srvCode5;
var srvCode6;
var srvCode7;
var srvCode8;
var srvCode9;
var srvCode11;
var srvCode12;
var srvCode13;
var srvCode14;
var srvCode15;
function getCheckBoxValues(name) {
	srvCode = "";
	srvCode1 = "";
	srvCode2 = "";
	srvCode3 = "";
	srvCode4 = "";
	srvCode5 = "";
	srvCode6 = "";
	srvCode7 = "";
	srvCode8 = "";
	srvCode9 = "";
	srvCode11 = "";
	srvCode12 = "";
	srvCode13 = "";
	srvCode14 = "";
	srvCode15 = "";
	//$("input[name=" + name + "].selected").each(function() {
	$("span[name='srvCode'].selected").each(function() {
		var val = $(this).attr('value');
		srvCode += val + ",";

		if (val == '30004001') {
			srvCode1 = val;
		} else if (val == '30004002') {
			srvCode2 = val;
		} else if (val == '30004003') {
			srvCode3 = val;
		} else if (val == '30004004') {
			srvCode4 = val;
		} else if (val == '30004005') {
			srvCode5 = val;
		} else if (val == '30004006') {
			srvCode6 = val;
		} else if (val == '30004007') {
			srvCode7 = val;
		} else if (val == '30004008') {
			srvCode8 = val;
		} else if (val == '30004009') {
			srvCode9 = val;
		} else if (val == '30004011') {
			srvCode11 = val;
		} else if (val == '30004012') {
			srvCode12 = val;
		} else if (val == '30004013') {
			srvCode13 = val;
		} else if (val == '30004014') {
			srvCode14 = val;
		} else if (val == '30004015') {
			srvCode15 = val;
		}
	});
}

// 清空表单
function cleanForm() {
	//案件类型初始：全部案件
	$("input[name='case_property'][value=30003006]").prop("checked", true);
	//案件状态初始：未指定
	$("input[name='case_status'][value=30001006]").prop("checked", true);
	$("input[name='srvCode']").removeAttr("checked");
	$("input[name='dtBegin']").val("");
	$("input[name='dtEnd']").val("");
	$("#inTextVal").val("");
}

//搜索条件中，权证专员及所属分行数据补全
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
	$("input[name='dtBegin']").val('');
	$("input[name='dtEnd']").val('');
	$("span[name='srvCode']").removeClass("selected");
	$("#caseProperty").val("");
	$("#status").val("");
	$("#mortageService").val("");
	$("#inTextVal").val("");
	$("#isSubscribeFilter").val('');
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
