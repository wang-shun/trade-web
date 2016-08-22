/**
 * 案件统计详情
 */
$(document).ready(function() {
	
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var signTimeStart = $("#signTimeStart").val();
	var signTimeEnd = $("#signTimeEnd").val();
	var lendTimeStart = $("#lendTimeStart").val();
	var lendTimeEnd = $("#lendTimeEnd").val();
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
	data.queryId = "findToMortgageIsActive";
	data.rows = 12;
	data.page = 1;
	data.signTimeStart=signTimeStart;
	data.signTimeEnd=signTimeEnd;
	data.lendTimeStart=lendTimeStart;
	data.lendTimeEnd=lendTimeEnd;
	
	/*加载排序查询组件*/
	aist.sortWrapper({
		reloadGrid : searchMethod
	});
	
	reloadGrid(data);
	
	$("span[name='finCode']").click(function(){
		var id = $(this).attr("id");
		$("span[id='"+id+"']").changeSelect();
	});
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

$('#datepicker_2').datepicker({
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
var lendTimeStart;
var lendTimeEnd;

//查询方法
function searchMethod(page) {

	if (!page) {
		page = 1;
	}
	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 12;
		params.queryId = "findToMortgageIsActive";
		reloadGrid(params);
	} else {
		alert("请不要选择同样的日期类型！");
	}
};

//清空
$('#cleanButton').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[name='dtBegin2']").datepicker('update', '');
	$("input[name='dtEnd2']").datepicker('update', '');
	$("input[id='inTextVal']").val('');
	$("input[name='custName']").val('');
	$("select").val("");
	//清空产品名称
	$("span[name='finCode'].selected").each(function(){
		$(this).click();
	});
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
      	  var mortgageInfoList = template('template_mortgageInfoList' , data);
			  $("#mortgageInfoList").empty();
			  $("#mortgageInfoList").html(mortgageInfoList);
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

//日期控件取值
function getSearchDateValues() {

	signTimeStart = null;
	signTimeEnd = null;
	lendTimeStart = null;
	lendTimeEnd = null;

	var start = $('#dtBegin').val();
	var end = $('#dtEnd').val();
	var start2 = $('#dtBegin2').val();
	var end2 = $('#dtEnd2').val();
	
	//签约时间
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
	//放款时间
	if (end2 && end2 != '') {
		end2 = end2 + ' 23:59:59';
	}

	if (start2 != "") {
		lendTimeStart = start2;
	}
	if (start2 == "") {
		lendTimeStart = "";
	}
	if (end2 != "") {
		lendTimeEnd = end2;
	}
	if (end2 == "") {
		lendTimeEnd = "";
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

	//物业地址
	var propertyAddr =$("#propertyAddr").val().trim();
	if(propertyAddr==""||propertyAddr==null){
		propertyAddr=null;
	}

	//客户姓名
	var custName =$("#custName").val().trim();
	if(custName==""||custName==null){
		custName=null;
	}
	
	// 产品类型
	var finCode = getCheckBoxValues("finCode");
	
	//设置查询参数
	var params = {};
	params.signTimeStart = signTimeStart;
	params.signTimeEnd = signTimeEnd;
	params.lendTimeStart = lendTimeStart;
	params.lendTimeEnd = lendTimeEnd;
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.custName = custName;
	params.finCode = finCode;

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

//产品类型
function getCheckBoxValues(name) {
	var finCode = [];
	$("span[name='finCode'].selected").each(function() {
		var val = $(this).attr('value');
		finCode.push(val);
	});

	return finCode;
}