/**
 * 案件统计详情
 */
$(document).ready(function() {
	
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	//用户组织
	var org = $("#org").val();
	if (org==""||org=="ff8080814f459a78014f45a73d820006") {
		org = null;
	}
	//起始时间 结束时间
	var startTime = $("#startTime").val();
	if(startTime==""||startTime==null){
		startTime=null;
	}
	var endTime = $("#endTime").val();
	if(endTime==""||endTime==null){
		endTime=null;
	}
	
	// 初始化列表
	var data = {};	
	var transJob =$("#transJob").val();
	if(transJob=="誉萃总经理"){
		data.argu_dis=org;
		data.queryId = "queryCaseCountDistrict";
	}else if(transJob=="誉萃总监"){
		data.argu_org=org;
		data.queryId = "queryCaseCountOrg";
	}else if(transJob=="交易主管"){
		data.argu_org=org;
		data.argu_uId=uId;
		data.queryId = "queryCaseCountProcessor";
	}
	
	data.rows = 12;
	data.page = 1;
	data.startTime = startTime;
	data.endTime = endTime;
	
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

var flag=1;
// 查询组织
$('#queryOrgs').click(function() {
	flag=0;
	searchMethod();
});

// 查询贵宾服务部
$('#queryDistrict').click(function() {
	flag=1;
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
	
	var org = $("#org").val();
	if (org=="") {
		org = null;
	}	
	var orgs = $("#orgs").val();
	if (orgs=="") {
		orgs = null;
	}	
	var month = $("#month").val();
	if (month=="") {
		month = null;
	}
	//queryId
	var queryIds = "";
	if(flag==0){
		queryIds = "queryCaseCountOrg";
	}else if(flag==1){
		queryIds = "queryCastCountDistrict";
	}
	//设置查询参数
	var params = {
		argu_org : org,
		argu_orgs : orgs,
		argu_month : month,
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
