var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

$(document).ready(function() {	
	getInitPage();
});


function getInitPage(){			
	
	var tsAwardKpiPay = {};
	var status ="0";
	var belongMonth = getBlongMonth();

	tsAwardKpiPay.status = status;
	tsAwardKpiPay.belongMonth = belongMonth;
	belongMonth = $("#belongMonth").val();
 	$.ajax({
	    url:ctx+"/newAward/getInitPage",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:tsAwardKpiPay,
    	
    	success:function(data){     		
    		var page = 0;
    		if(data.success == true){    			 
    			 if(data.content != "" && data.content != null){
    				 page = data.content;
    			 }    			
    		}
            var new_src = ctx+"/newAward/managerPiecework?belongMonth="+belongMonth+'&t='+(new Date().getTime());
            $("#UpdateUserItem1",parent.document.body).attr("src",new_src);
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

$('#datepicker_0').datepicker().on('hide', function(e){
	$('#datepicker_0').datepicker('update');
	getInitPage();
});


//获取计件年月信息
function getBlongMonth(){
	var bm = "";	
	//方式一
	var belongMonth =  $.trim($("#belongMonth",window.parent.document).val());
    //方式二
	//var belongMonth1 = parent.document.getElementById("belongMonth").value;
    if(belongMonth =="" || belongMonth == null || belongMonth == undefined){
    	bm == null;
    }else{
    	bm = belongMonth + "-01";
    }
    return bm;
}