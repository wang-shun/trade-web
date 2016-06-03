/**
 * 案件统计详情
 */
$(document).ready(function() {
	
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var createTimeStart = $("#createTimeStart").val();
	createTimeStart = createTimeStart + " 00:00:00";
	var createTimeEnd = $("#createTimeEnd").val();
	createTimeEnd = createTimeEnd +" 23:59:59";
	var org = $("#org").val();
	if (org=="ff8080814f459a78014f45a73d820006") {
		org = null;
	}
	var userId =$("#userId").val();
	if(userId==""||userId==null){
		userId=null;
	}
	var tempUser = $("#tempUser").val();
	if(tempUser==""||tempUser==null){
		tempUser=null;
	}
	var status = $("#status").val();
	
	// 初始化列表
	var data = {};
	data.argu_org = org;
	data.argu_processorId = userId;
	data.argu_tempUser = tempUser;
	data.queryId = "queryCaseDetailItemList";
	data.rows = 12;
	data.page = 1;
	if(status=="signed"){
		data.argu_signTimeStart=createTimeStart;
		data.argu_signTimeEnd=createTimeEnd;
	}else if(status=="transfered"){
		data.argu_houseTranferTimeStart=createTimeStart;
		data.argu_houseTranferTimeEnd=createTimeEnd;
	}else if(status=="closed"){
		data.argu_closeTimeStart=createTimeStart;
		data.argu_closeTimeEnd=createTimeEnd;
	}else{
		data.argu_receivedTimeStart=createTimeStart;
		data.argu_receivedTimeEnd=createTimeEnd;
	}
	
	/*加载排序查询组件*/
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
	language : 'zh-CN'
});

/*查询按钮查询*/
$('#searchButton').click(function() {
	searchMethod();
});

 //查询方法
function searchMethod(page) {

	if (!page) {
		page = 1;
	}
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 12;
		params.queryId = "queryCaseDetailItemList";
		reloadGrid(params);
	} else {
		alert("请不要选择同样的日期类型！");
	}
};

function reloadGrid(data) {
	
	aist.wrap(data);
	
	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data.sidx=sortcolumn;
	data.sord=sortgz;

	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        beforeSend: function () {  
        	//$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			//$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
          $.unblockUI();   	 
      	  data.ctx = ctx;
      	  var myCaseList = template('template_myCaseList' , data);
			  $("#myCaseList").empty();
			  $("#myCaseList").html(myCaseList);
			  // 显示分页 
            initpage(data.total,data.pagesize,data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}

function initpage(totalCount,pageSize,currentPage,records) {
	
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
		first:'<i class="icon-step-backward"></i>',
		prev:'<i class="icon-chevron-left"></i>',
		next:'<i class="icon-chevron-right"></i>',
		last:'<i class="icon-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			searchMethod(page);
	    }
	});
}

// 日期类型
var receivedTimeStart, signTimeStart, houseTranferTimeStart, closeTimeStart;
var receivedTimeEnd, signTimeEnd, houseTranferTimeEnd, closeTimeEnd;

// 日期控件取值
function getSearchDateValues() {

	receivedTimeStart = null;
	receivedTimeEnd = null;
	signTimeStart = null;
	signTimeEnd = null;
	houseTranferTimeStart = null;
	houseTranferTimeEnd = null;
	closeTimeStart = null;
	closeTimeEnd = null;

	var codeStr = "";
	for (var r = 0; r < divIndex; r++) {
		var val = $('#case_date_' + r + ' option:selected').val();
		if (val == undefined)
			continue;
		var start = $('#dtBegin_' + r).val();
		var end = $('#dtEnd_' + r).val();
		if (end && end != '') {
			end = end + ' 23:59:59';
		}
		if (codeStr.indexOf(val) != -1)
			return false;
		codeStr += val;
		if (start != "") {
			if (val == '1') {
				receivedTimeStart = start;
			} else if (val == '2') {
				signTimeStart = start;
			} else if (val == '3') {
				houseTranferTimeStart = start;
			} else if (val == '4') {
				closeTimeStart = start;
			}
		}
		if (start == "") {
			if (val == '1') {
				receivedTimeStart = "";
			} else if (val == '2') {
				signTimeStart = "";
			} else if (val == '3') {
				houseTranferTimeStart = "";
			} else if (val == '4') {
				closeTimeStart = "";
			}
		}
		if (end != "") {
			if (val == '1') {
				receivedTimeEnd = end;
			} else if (val == '2') {
				signTimeEnd = end;
			} else if (val == '3') {
				houseTranferTimeEnd = end;
			} else if (val == '4') {
				closeTimeEnd = end;
			}
		}
		if (end == "") {
			if (val == '1') {
				receivedTimeEnd = "";
			} else if (val == '2') {
				signTimeEnd = "";
			} else if (val == '3') {
				houseTranferTimeEnd = "";
			} else if (val == '4') {
				closeTimeEnd = "";
			}
		}
	}
	return true;
}

/**
 * 查询参数取得
 */
function getParamsValue() {
	
	//获取誉萃组织
	var org =  $('#yuCuiOriGrpId').val();
	if(org=="ff8080814f459a78014f45a73d820006"){
		org=null;
	}else if(org==""||org==null){
		org = $("#org").val();
	}
	
	//案件编号
	var caseNo = $("#caseNo").val();
	if(""==caseNo || null==caseNo){
		caseNo=null;
	}
	
	//案件地址
	var caseAddr = $("#caseAddr").val();
	if(""==caseAddr || null==caseAddr){
		caseAddr=null;
	}
	
	//交易顾问ID
	var userId =$("#userId").val();
	if(userId==""||userId==null){
		userId=null;
	}
	
	//人员ID
	var queryPersonId= $("#inTextVal").attr("hVal");
	if(queryPersonId==$("#personalId")||queryPersonId==""){
		queryPersonId=null;
	}
	
	//设置查询参数
	var params = {
		argu_receivedTimeStart : receivedTimeStart,
		argu_receivedTimeEnd : receivedTimeEnd,
		argu_receivedTime : ((receivedTimeStart==""&&receivedTimeEnd=="")?true:null),
		argu_signTimeStart : signTimeStart,
		argu_signTimeEnd : signTimeEnd,
		argu_signTime : ((signTimeStart==""&&signTimeEnd=="")?true:null),
		argu_houseTranferTimeStart : houseTranferTimeStart,
		argu_houseTranferTimeEnd : houseTranferTimeEnd,
		argu_houseTranferTime : ((houseTranferTimeStart==""&&houseTranferTimeEnd=="")?true:null),
		argu_closeTimeStart : closeTimeStart,
		argu_closeTimeEnd : closeTimeEnd,
		argu_closeTime : ((closeTimeStart==""&&closeTimeEnd=="")?true:null),
		argu_org : org,
		argu_processorId : userId,
		argu_queryPersonId : queryPersonId,
		search_caseNo : caseNo,
		search_caseAddr : caseAddr,
	};
	return params;
}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#teamCode").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
		
		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);
	}else{
		$("#teamCode").val("");
		$("#yuCuiOriGrpId").val("");
	}
}

//选取人员的回调函数
function selectUserBack(array){
	if(array && array.length >0){
        $("#inTextVal").val(array[0].username);
		$("#inTextVal").attr('hVal',array[0].userId);

	}else{
		$("#inTextVal").val("");
		$("#inTextVal").attr('hVal',"");
	}
}

//添加日期查询条件
var divIndex = 1;
var count = $('#case_date_0 option:last').index();
function addDateDiv() {

	var txt = '<div class="col-md-12 form-group"><label class="col-md-1  col-sm-2 control-label"></label><div id="dateDiv_' + divIndex + '" class="input-group m-b-xs m-t-xs">';
	txt += '<div class="input-group-btn">';
	txt += '    <select id="case_date_'
			+ divIndex
			+ '" class="btn btn-white chosen-select" name="case_date" ltype="select" >';
	txt += $("#case_date_0").html()
	txt += '</select></div>';
	txt += '<div id="datepicker_'
			+ divIndex
			+ '" class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">';
	txt += '    <input id="dtBegin_'
			+ divIndex
			+ '" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="起始日期"> ';
	txt += '    <span class="input-group-addon">到</span>';
	txt += '    <input id="dtEnd_'
			+ divIndex
			+ '" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="结束日期"/>';
	txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv('
			+ divIndex + ');"><font>删除</font></a></span>';
	txt += '</div></div></div>';
	// alert(txt);
	$("#addLine").before(txt);
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

//查看未被选择的日期类型
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
	$("#dateDiv_" + index).remove();
}