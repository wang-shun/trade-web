/**
 * 案件统计详情
 */
$(document).ready(function() {
	reloadGrid(1);
});

/**
 * 查询参数取得
 */
function getParamsValue() {
	
	var org = $("#org").val();
	if (org=="" || org=="ff8080814f459a78014f45a73d820006"){
		org = null;
	}
	
	var startTime = $('#startTime').val();
	if(startTime==''){
		startTime=null;
	}
	if(startTime!=null){
		startTime=startTime+" 00:00:00";
	}
	
	var endTime = $('#endTime').val();
	if(endTime==''){
		endTime=null;
	}
	if(endTime!=null){
		endTime=endTime+" 23:59:59";
	}
	
	var transJob = $('#transJob').val();
	var queryIds = "";
	if(transJob=="誉萃总经理"){
		queryIds = "queryCaseCountDistrict";
	}else if(transJob=="誉萃总监"){
		queryIds = "queryCaseCountOrg";
	}else if(transJob=="交易主管"){
		queryIds = "queryCaseCountProcessor";
	}
	//设置查询参数
	var params = {
		argu_org : org,
		argu_startTime : startTime,
		argu_endTime : endTime,
		queryId : queryIds,
		rows : 12
	};
	return params;
}

function reloadGrid(page) {
	
	var ctx = $("#ctx").val();
	var params = getParamsValue();
	if(!page){
		page=1
	}
	params.page=page;
	
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: params,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
          $.unblockUI();   	 
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
	if (totalCount<1 || pageSize<1 || currentPage<1){
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

//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

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
