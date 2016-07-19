/*任务详情列表*/
$(document).ready(function(){
	/*加载排序查询组件*/
	aist.sortWrapper({
		reloadGrid : reloadGrid
	});
	reloadGrid(1);
});

/*获取param*/
function getParam(page){
	if(!page){
		page=1;
	}
	
	//查询参数
	var param = {
		queryId : 'queryHistoryTaskListDetail',
		page:page,
		rows : 12
	};
	
	var taskName = null;
	var handleTimeStart = null;
	var handleTimeEnd = null;
	var org = null;
	var consultantId = null;
	
	handleTimeStart = $('#dtBegin_0').val();
	if(handleTimeStart==''){
		handleTimeStart=null;
	}else{
		handleTimeStart += ' 00:00:00';
	}
	param.argu_handleTimeStart=handleTimeStart;
	
	handleTimeEnd = $('#dtEnd_0').val();
	if(handleTimeEnd==''){
		handleTimeEnd=null;
	}else{
		handleTimeEnd += ' 23:59:59';
	}
	param.argu_handleTimeEnd=handleTimeEnd;
	
	org = $('#yuCuiOriGrpId').val();
	if(org=='ff8080814f459a78014f45a73d820006'){
		org=null;
	}else if(org==''){
		if($('#org').val()!='ff8080814f459a78014f45a73d820006'){
			org=$('#org').val();
		}else{
			org=null;
		}
	}
	param.argu_org=org;
	
	consultantId = $('#inTextVal').attr('hVal');
	if(consultantId==''){
		consultantId=null;
	}
	param.argu_consultantId=consultantId;
	
	taskName = $('#queryTaskName').find(':selected').val();
	if(taskName==''){
		taskName=null;
	}
	param.argu_taskName = taskName;
	
	var num2 = $('#queryItems').find(':selected').val();
	var content = $('#queryContent').val();
	if(content==''){
		content=null;
	}
	if(num2=='1'){
		param.search_caseAddress = content;
	}else if(num2=='2'){
		param.search_agentName = content;
	}else if(num2=='3'){
		param.search_grpName = content;
	}else if(num2=='4'){
		param.search_caseCode = content;
	}else if(num2=='5'){
		param.search_ctmCode = content;
	}
	return param;
}

/*reloadGrid*/
function reloadGrid(page){
	var ctx = $('#ctx').val();
	var param = getParam(page);
	
	aist.wrap(param);
	var sortColumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	param.sidx=sortColumn;
	param.sord=sortgz;
	
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: param,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
        	$.unblockUI();   	 
      	  	data.ctx = ctx;
      	  	var myTaskList = template('template_myTaskList', data);
      	  	$("#myTaskList").empty();
			$("#myTaskList").html(myTaskList);
			// 显示分页 
            initpage(data.total, data.pagesize, data.page, data.records);
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
	});	
}

/*查询按钮*/
$('#searchButton').click(function(){
	reloadGrid(1);
});

/*导出Excel报表*/
$('#exportExcel').click(function(){
	if(!confirm('是否导出Excel报表?')){
		return false;
	}
	
	var ctx = $('#ctx').val();
	var url = "/quickGrid/findPage?xlsx&";
	var queryIdArg = 'queryHistoryTaskListDetail';
	var displayColomn =[
	    'CASE_CODE', 'CTM_CODE', 'TASK_NAME', 'SELLER', 'BUYER', 'PROPERTY_ADDR',
	    'AGENT_NAME', 'AGENT_PHONE', 'GRP_NAME', 'START_TIME', 'END_TIME',
	    'CONSULTANT_NAME', 'YUCUI_ORG_ID', 'CONSULTANT_TEL'
	];
	var param = {};
	var handleTimeStart = $('#dtBegin_0').val();
	if(handleTimeStart==''){
		handleTimeStart=null;
	}else{
		handleTimeStart += ' 00:00:00';
	}
	param.argu_handleTimeStart=handleTimeStart;
	
	var handleTimeEnd = $('#dtEnd_0').val();
	if(handleTimeEnd==''){
		handleTimeEnd=null;
	}else{
		handleTimeEnd += ' 23:59:59';
	}
	param.argu_handleTimeEnd=handleTimeEnd;
	
	var org = $('#yuCuiOriGrpId').val();
	if(org=='ff8080814f459a78014f45a73d820006'){
		org=null;
	}else if(org==''){
		if($('#org').val()!='ff8080814f459a78014f45a73d820006'){
			org=$('#org').val();
		}else{
			org=null;
		}
	}
	param.argu_org=org;
	
	var consultantId = $('#inTextVal').attr('hVal');
	if(consultantId==''){
		consultantId=null;
	}
	param.argu_consultantId=consultantId;
	
	var taskName = $('#queryTaskName').find(':selected').val();
	if(taskName==''){
		taskName=null;
	}
	param.argu_taskName = taskName;
	
	var num2 = $('#queryItems').find(':selected').val();
	var content = $('#queryContent').val();
	if(content==''){
		content=null;
	}
	if(num2=='1'){
		param.search_caseAddress = content;
	}else if(num2=='2'){
		param.search_agentName = content;
	}else if(num2=='3'){
		param.search_grpName = content;
	}else if(num2=='4'){
		param.search_caseCode = content;
	}else if(num2=='5'){
		param.search_ctmCode = content;
	}
	
	url = ctx + url + $.param(param) + '&queryId='+ queryIdArg + '&colomns='+ displayColomn;
	$('#excelForm').attr('action', url);
	$('#excelForm').submit();
	alert('导出Excel成功');
	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	$(".blockOverlay").css({'z-index':'9998'});
	//2s后刷新页面
	setTimeout(function(){
		reloadGrid(1);
	},2000);
});

/*分页插件*/
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
			reloadGrid(page);
	    }
	});
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

//日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});