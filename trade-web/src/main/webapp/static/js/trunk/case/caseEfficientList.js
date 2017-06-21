$(function(){
	
	//加载案件时效管理列表
	reloadGrid();
	
	//条件查询时间
	$("#btnSearch").click(function(){
		reloadGrid();
	});
	
	//加载案件时效管理列表信息
	function reloadGrid(){
		var data = getParams();
		
		console.log(data);
		
		$("#caseEfficientList").reloadGrid({
	    	ctx : ctx,
			queryId : "queryCaseEfficientList",
		    templeteId : 'template_caseEfficientList',
		    data : data,
		    wrapperData : data
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
		
		data.overdue = overdue;
		data.inProgress = inProgress;
		data.beginDateTime = beginDateTime;
		data.endDateTime = endDateTime;
		data.status = status;
		data.delay = delay;
		data.jobCode = jobCode
		data.uid = userId;
		data.oId = orgId;
		
		return data;
	}
	
	//日期控件初始化
	$('#datepicker_0').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	
});

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
