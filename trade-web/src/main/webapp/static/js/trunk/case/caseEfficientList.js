$(function(){
	
	init();
	
	//加载案件时效管理列表
	reloadGrid();
	
	//条件查询时间
	$("#btnSearch").click(function(){
		reloadGrid();
	});
	
	//确认延期按钮事件
	$('#btnConfirm').click(function(){
		var comment = $("#subAchieve textarea[name='comment']").val();
		
		if(comment == ""){
			$("#subAchieve #tip").removeClass("hide");
			return false;
		}
		
		var caseCode = $("#subAchieve input[name='caseCode']").val();
		var inProgress = $("#subAchieve input[name='inProgress']").val();
		var delayDays = $("#subAchieve input[name='delayDays']").val();
		
		var partCode = "";
		var delayDays = "0";
		if(inProgress == "firstFollow"){
			partCode = "FirstFollow";
		}
		else if(inProgress == "sign"){
			partCode = "TransSign";
			delayDays = "7";
		}
		else if(inProgress == "guohu"){
			partCode = "Guohu";
			delayDays = "15";
		}
		else if(inProgress == "caseClose"){
			partCode = "CaseClose";
			delayDays = "15";
		}
		
		$.ajax({
    		cache:false,
    		async:true,
    		type:"POST",
    		dataType:"json",
    		url:ctx+"/caseEfficient/delay",
    		data:{
    			caseCode:caseCode,
    			partCode:partCode,
    			delayDays:delayDays,
    			comment:comment
    		},
    		beforeSend: function () {  
	        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
	        }, 
    		success:function(data){
    			$.unblockUI();  
    			console.log("data:" + data);
    			if(data){
    				window.wxc.success("延期成功！",{"wxcOk":function(){
						$("#closeBtn").click();
						reloadGrid();
					}});
    			}else{
    				window.wxc.error("延期失败！");
    			}
    		}
    	});
	});
	
	//日期控件初始化
	$('#datepicker_0').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	
});

//加载案件时效管理列表信息
	function reloadGrid(){
		var data = getParams();
		
		$("#caseEfficientList").reloadGrid({
	    	ctx : ctx,
			queryId : "queryCaseEfficientList",
		    templeteId : 'template_caseEfficientList',
		    data : data,
		    wrapperData : data
	    });
	}
	
	//导出列表
	function exportExcel(){
		var data = getParams();
		
		$.exportExcel({
			ctx : "..",
			queryId : "queryCaseEfficientList",
			colomns : ['caseCode','leadingProcessName','dispatchTime','firstFollowDateTime','signDateTime','guohuDateTime','caseCloseDateTime','firstFollowEffInfo','signEffInfo','guohuEffInfo','caseCloseEffInfo','totalEff'],
			data:data
		});
	}
	
	function getParams(){
		var data = {};
		
		var inProgress = $("#selInProgress option:selected").val();
		var beginDateTime = $("#dtBegin_0").val();
		var endDateTime = $("#dtEnd_0").val();
		var overdue = $("#selOverdue option:selected").val();
		var status = $("#selStatus option:selected").val();
		var delay = $("#selDelay option:selected").val();
		var jobCode = $("#jobCode").val();
		var userId = $("#userId").val();
		var orgId = $("#orgId").val();
		var caseCode = $.trim($("#caseCode").val());
		
		data.overdue = overdue;
		data.inProgress = inProgress;
		data.beginDateTime = beginDateTime;
		data.endDateTime = endDateTime;
		data.status = status;
		data.delay = delay;
		data.jobCode = jobCode
		data.uid = userId;
		data.oId = orgId;
		data.caseCode = caseCode;
		
		return data;
	}

//选业务组织的回调函数
function radioYuCuiOrgSelectCallBackInCaseEff(array) {
	if (array && array.length > 0) {
			$("#teamCode").val(array[0].name);
			$("#orgId").val(array[0].id);
	} else {
			$("#teamCode").val("");
			$("#orgId").val("");
	}
}

//弹出延期弹出框
function showDelayPop(caseCode,inProgress){
	$("#subAchieve input[name='caseCode']").val(caseCode);
	$("#subAchieve input[name='inProgress']").val(inProgress);
	
	$("#subAchieve textarea[name='comment']").val("");
	$("#subAchieve #tip").addClass("hide");
	
	$('#subAchieve').modal('show');
}

//初始化
function init(){
	//页面加载列表默认显示当前逾期列表
	$("#selOverdue").val("curOverdued");  
}

