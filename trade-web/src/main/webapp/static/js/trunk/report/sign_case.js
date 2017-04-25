/**
 * 案件统计详情
 */
$(document).ready(function() {
	
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var signTimeStart = $("#signTimeStart").val();
	var signTimeEnd = $("#signTimeEnd").val();
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
	data.queryId = "querySignCaseList";
	data.rows = 12;
	data.page = 1;
	data.signTimeStart=signTimeStart;
	data.signTimeEnd=signTimeEnd;
	
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

//日期类型
var signTimeStart;
var signTimeEnd;

 //查询方法
function searchMethod(page) {
	if (!page) {
		page = 1;
	}

	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 12;
		params.queryId = "querySignCaseList";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
};

//清空
$('#cleanButton').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');
	$("input[name='orgManName']").val('');
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[id='inTextVal']").val('');
	$("input[name='orgName']").val('');
	
	$("select").val("");
});

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
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
          $.unblockUI();   	 
      	  data.ctx = ctx;
      	  var signCaseList = template('template_signCaseList' , data);
			  $("#signCaseList").empty();
			  $("#signCaseList").html(signCaseList);
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

// 日期控件取值
function getSearchDateValues() {

	signTimeStart = null;
	signTimeEnd = null;

	var start = $('#dtBegin').val();
	var end = $('#dtEnd').val();
	if (end && end != '') {
		end = end + ' 23:59:59';
	}

	if (start != "") {
		signTimeStart = start;
	}
	if (start == "") {
		signTimeStart = "";
	}
	if (end != "") {
		signTimeEnd = end;
	}
	if (end == "") {
		signTimeEnd = "";
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
	var caseCode = $("#caseCode").val().trim();
	if(""==caseCode || null==caseCode){
		caseCode=null;
	}
	
	//产证地址
	var propertyAddr = $("#propertyAddr").val().trim();
	if(""==propertyAddr || null==propertyAddr){
		propertyAddr=null;
	}
	
	//组别
	var orgName =$("#orgName").val().trim();
	if(orgName==""||orgName==null){
		orgName=null;
	}
	
	//区董
	var orgManName =$("#orgManName").val().trim();
	if(orgManName==""||orgManName==null){
		orgManName=null;
	}	
	
	//人员ID
	var queryPersonId= $("#inTextVal").attr("hVal");
	if(queryPersonId==$("#personalId")||queryPersonId==""){
		queryPersonId=null;
	}
	
	//设置查询参数
	var params = {};
	
	params.signTimeStart = signTimeStart;
	params.signTimeEnd = signTimeEnd;
	params.org = org;
	//params.argu_processorId = userId;
	params.queryPersonId = queryPersonId;
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.orgName = orgName;
	params.orgManName = orgManName;

	return params;
}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array){
    if(array && array.length >0){
        $("#orgName").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
		
		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
		$("#oldactiveName").attr("onclick",userSelect);
	}else{
		$("#orgName").val("");
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

//导出excel方法
function exportToExcel() {
	if (getSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('PROPERTY_ADDR');
		displayColomn.push('REAL_NAME');
		displayColomn.push('ORG_NAME');
		displayColomn.push('GRP_NAME');
		displayColomn.push('AR_NAME');
		displayColomn.push('ZONE');
		displayColomn.push('ORG_MAN_NAME');
		displayColomn.push('SIGN_TIME');

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
		var queryId = '&queryId=querySignCaseList';
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