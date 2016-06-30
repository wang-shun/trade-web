/*任务详情列表*/
$(document).ready(function(){
	reloadGrid(true,1);
});

/*reloadGrid*/
function reloadGrid(isFirstQuery,page){
	var ctx = $('#ctx').val();
	
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
	
	handleTimeStart = $('#handleTimeStart').val();
	if(handleTimeStart==''){
		handleTimeStart=null;
	}else{
		handleTimeStart += ' 00:00:00';
	}
	param.argu_handleTimeStart=handleTimeStart;
	
	handleTimeEnd = $('#handleTimeEnd').val();
	if(handleTimeEnd==''){
		handleTimeEnd=null;
	}else{
		handleTimeEnd += ' 23:59:59';
	}
	param.argu_handleTimeEnd=handleTimeEnd;
	
	org = $('#org').val();
	if(org==''){
		org=null;
	}
	param.argu_org=org;
	
	consultantId = $('#consultantId').val();
	if(consultantId==''){
		consultantId=null;
	}
	param.argu_consultantId=consultantId;
	
	if(isFirstQuery==true){
		taskName = $('#taskName').val();
		if(taskName==''){
			taskName=null;
		}else if(taskName=='1'){
			taskName='TransSign';
		}else if(taskName=='2'){
			taskName='ComLoanProcess';
		}else if(taskName=='3'){
			taskName='CaseClose';
		}
		param.argu_taskName = taskName;
		
	}else{
		var num1 = $('#queryTaskName').find(':selected').val();
		if(num1=='0'){
			taskName=null;
		}else if(num1=='1'){
			taskName='TransSign';
		}else if(num1=='2'){
			taskName='ComLoanProcess';
		}else if(num1=='3'){
			taskName='CaseClose';
		}
		param.argu_taskName = taskName;
		
		var num2 = $('#queryItems').find(':selected').val();
		var content = $('#queryContent').val();
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
	}
	
	$.ajax({
		async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: param,
        beforeSend: function () {  
        	//$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			//$(".blockOverlay").css({'z-index':'9998'});
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
	reloadGrid(false, 1);
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
			reloadGrid(false,page);
	    }
	});
}