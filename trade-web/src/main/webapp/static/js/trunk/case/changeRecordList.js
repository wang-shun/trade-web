/**
 * 案件统计详情
 */
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var operateTimeStart = $("#operateTimeStart").val();
	var operateTimeEnd = $("#operateTimeEnd").val();
	var tempUser = $("#tempUser").val();
	if(tempUser==""||tempUser==null){
		tempUser=null;
	}
	var status = $("#status").val();

	// 初始化列表
	var data = {};
	data.queryId = "queryChangeRecordList";
	data.rows = 12;
	data.page = 1;
	data.operateTimeStart=operateTimeStart;
	data.operateTimeEnd=operateTimeEnd;
	
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
var operateTimeStart;
var operateTimeEnd;

 //查询方法
function searchMethod(page) {
	if (!page) {
		page = 1;
	}

	if (getSearchDateValues()) {
		var params = getParamsValue();
		params.page = page;
		params.rows = 12;
		params.queryId = "queryChangeRecordList";
		reloadGrid(params);
	} else {
		window.wxc.alert("请不要选择同样的日期类型！");
	}
};

//清空
$('#cleanButton').click(function() {
	$("#caseCode").val('');
	$("#partName").val('');
	$("#operator").val('');
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("select").val('');
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
      	  var changeRecordList = template('template_changeRecordList' , data);
		  $("#changeRecordList").empty();
		  $("#changeRecordList").html(changeRecordList);
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
	operateTimeStart = null;
	operateTimeEnd = null;

	var start = $('#dtBegin').val();
	var end = $('#dtEnd').val();
	if (end && end != '') {
		end = end + ' 23:59:59';
	}
	if (start != "") {
		operateTimeStart = start;
	}
	if (start == "") {
		operateTimeStart = "";
	}
	if (end != "") {
		operateTimeEnd = end;
	}
	if (end == "") {
		operateTimeEnd = "";
	}		
	return true;
}

/**
 * 查询参数取得
 */
function getParamsValue() {
	//案件编号
	var caseCode = $("#caseCode").val().trim();
	if(caseCode == ''){
		caseCode = null;
	}
	//设置查询参数
	var params = {};
	
	params.caseCode = caseCode;
	params.operateTimeStart = operateTimeStart;
	params.operateTimeEnd = operateTimeEnd;

	return params;
}

//导出excel方法
function exportToExcel() {
	if (getSearchDateValues()) {
		var url = "/quickGrid/findPage?xlsx&";
		var ctx = $("#ctx").val();
		//excel导出列
		var displayColomn = new Array;
		displayColomn.push('CASE_CODE');
		displayColomn.push('SRV_NAME');
		displayColomn.push('PRE_PROCESSOR_NAME');
		displayColomn.push('PRE_ORG_NAME');
		displayColomn.push('PROCESSOR_NAME');
		displayColomn.push('ORG_NAME');
		displayColomn.push('CREATE_TIME');

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
		var queryId = '&queryId=queryChangeRecordList';
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