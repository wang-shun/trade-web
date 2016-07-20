$(document).ready(function() {
					cleanForm();
					//基本信息等高
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
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

					var orgArray = queryOrgs==null?null:queryOrgs.split(",");
					// 初始化列表
					var data = {};
		    	    data.queryId = "queryChandiaoTransferCountList";
		    	    data.rows = 12;
		    	    data.page = 1;
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
$('#datepicker_1').datepicker({
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

// 查询
$('#searchButton').click(function() {
	searchMethod();
});

 //查询
function searchMethod(page) {
	if(!page) {
		page = 1;
	}
	
	var params = getParamsValue();
	params.page = page;
	params.rows = 12;
	params.queryId = "queryChandiaoTransferCountList";
	reloadGrid(params);
	

};

function reloadGrid(data) {
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

	var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	data.argu_idflag = arguUserId;
    data.argu_queryorgs = orgArray;
    var end = $('#dtBegin_1').val();
	var prAccpetTimeEnd = $('#prAccpetTimeEnd').val();
    var prCompleteTimeStart = $('#prCompleteTimeStart').val();
	var prCompleteTimeEnd = $('#prCompleteTimeEnd').val();
	
	
	
	
	if(end&&end!=''){
		end = end +' 23:59:59';
	}
	if(prAccpetTimeEnd&&prAccpetTimeEnd!=''){
		prAccpetTimeEnd = prAccpetTimeEnd +' 23:59:59';
	}
	if(prCompleteTimeEnd&&prCompleteTimeEnd!=''){
		prCompleteTimeEnd = prCompleteTimeEnd +' 23:59:59';
	}
	
	data.prApplyTimeEnd = end;
	data.prAccpetTimeEnd = prAccpetTimeEnd;
	data.prCompleteTimeStart = prCompleteTimeStart;
	data.prCompleteTimeEnd = prCompleteTimeEnd;
    
	
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
      	  var chandiaoTransferList = template('template_chandiaoTransferList' , data);
			  $("#chandiaoTransferList").empty();
			  $("#chandiaoTransferList").html(chandiaoTransferList);
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
			 //console.log(page);
			searchMethod(page);
	    }
	});
}


function getParamsValue() {
	
	var start = $('#dtBegin_0').val();
	var end = $('#dtBegin_1').val();
	var prAccpetTimeStart = $('#prAccpetTimeStart').val();
	var prAccpetTimeEnd = $('#prAccpetTimeEnd').val();
	var prCompleteTimeStart = $('#prCompleteTimeStart').val();
	var prCompleteTimeEnd = $('#prCompleteTimeEnd').val();
	var yuCuiOriGrpId = $('#yuCuiOriGrpId').val();
	
	//设置查询参数
	var params = {
			prApplyTime : start,
			prApplyTimeEnd : end,
			prAccpetTimeStart : prAccpetTimeStart,
			prAccpetTimeEnd : prAccpetTimeEnd,
			prCompleteTimeStart : prCompleteTimeStart,
			prCompleteTimeEnd : prCompleteTimeEnd,
			yuCuiOriGrpId : yuCuiOriGrpId
	};
	return params;
}


function queryChandiaoDetail(id){
	var url = "/report/chandiaoDetail?organId="+id;
	var ctx = $("#ctx").val();
	var params = getParamsValue();
	
	//params.organId = id; + jQuery.param(params)
	
	url = ctx + url;
	$('#chandiaoDetail').attr('action', url);
	
	$('#chandiaoDetail').method="post" ;
	$('#chandiaoDetail').submit();
	
}

// 清空表单
function cleanForm() {
	$("input[name='dtBegin']").val("");
}

//清空
$('#cleanButton').click(function() {
	$("input[name='teamCode']").val('');
	$("input[name='yuCuiOriGrpId']").val('');
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[name='prAccpetTimeStart']").datepicker('update', '');
	$("input[name='prAccpetTimeEnd']").datepicker('update', '');
	$("input[name='prCompleteTimeStart']").datepicker('update', '');
	$("input[name='prCompleteTimeEnd']").datepicker('update', '');
	
	$("select").val("");
});