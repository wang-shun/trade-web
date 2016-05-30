/**
 * 案件统计详情
 */
$(document).ready(function() {
	
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var org = $("#org").val();
	if (org=="") {
		org = null;
	}	
	var month = $("#month").val();
	if (month=="") {
		month = null;
	}
	var orgs = $("#orgs").val();
	if (orgs=="") {
		orgs = null;
	}

	// 初始化列表
	var data = {};
	data.argu_org = org;
	data.argu_orgs = orgs;
	data.rows = 12;
	data.page = 1;
	data.queryId = "queryCaseCountOrg";
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

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

 //删除日期控件
function removeDateDiv(index) {
	$("#dateDiv_" + index).remove();
}

 //查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	reloadGrid(params);
};

function reloadGrid(data) {
	
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

function initpage(totalCount,pageSize,currentPage,records)
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

//日期
var createTimeStart;
var createTimeEnd;

/**
 * 查询参数取得
 */
function getParamsValue() {
	//yucu组织选择
	var orgs =  $('#yuCuiOriGrpId').val();
	//时间范围
	createTimeStart = $('#dtBegin_0').val();
	createTimeEnd = $('#dtEnd_0').val();
	//状态
	var status=$("#caseProperty option:selected").val();
	//queryId
	var queryIds = "";
	if(status=="signed"){
		queryIds = "queryCastDetailItemListSigned";
	}else if(status=="transfered"){
		queryIds = "queryCastDetailItemListTransfered";
	}else if(status=="closed"){
		queryIds = "queryCastDetailItemListClosed";
	}else{
		queryIds = "queryCastDetailItemListReceived";
	}
	
	//设置查询参数
	var params = {
		search_status : status,
		search_createTimeStart : createTimeStart,
		search_createTimeEnd : createTimeEnd,
		argu_orgs : orgs,
		queryId : queryIds
	};
	return params;
}

function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();
	if(inTextType=='2'||inTextType=='4'){
		initAutocomplete(ctx+"/labelVal/queryUserInfo");
	}else if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
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