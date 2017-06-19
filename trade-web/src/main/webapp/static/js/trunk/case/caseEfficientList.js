$(function(){
	
	//加载案件时效管理列表
	reloadGrid();
	
	//加载案件时效管理列表信息
	function reloadGrid(){
		var data = [];
		
		$("#caseEfficientList").reloadGrid({
	    	ctx : ctx,
			queryId : "queryCaseEfficientList",
		    templeteId : 'template_caseEfficientList',
		    data : data,
		    wrapperData : data
	    });
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