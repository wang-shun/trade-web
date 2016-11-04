var ctx = $("#ctx").val();

function relieve(caseCode,status){
	if(status == "1"){
		alert("该案件状态已经处于解除状态！");
		return false;
	}
	
	if(confirm("是否确定解除？")){
		
			$.ajax({
				cache:false,
				async:true,
				type:"POST",
				dataType:"json",
				url:ctx+"/bizwarn/relieve",
				data:{caseCode:caseCode},
				success:function(data){
					if(data.success){
						alert('解除成功');
						reloadGrid();
					}else{
						alert('解除失败');
					}
					$.unblockUI();
				}
			});
		}
}

function reloadGrid(){
	
	var data = getParams();
	
	$("#bizwarnList").reloadGrid({
    	ctx : ctx,
		queryId : data.queryId,
	    templeteId : 'template_bizwarnList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	var currentOrgId = $("#currentOrgId").val();
	var currentDepHierarchy = $("#currentDepHierarchy").val();
	var warnType = $("#warnType option:selected").val();
	var status = $("#status option:selected").val();
	var caseCode = $.trim($("#caseCode").val());
	var propertyAddr = $.trim($("#addr").val());
	var warnTimeStart = $("#warnTimeStart").val();
	var warnTimeEnd = $("#warnTimeEnd").val();
	
	var data = {};
	data.currentOrgId = currentOrgId;
	data.warnType = warnType;
	data.status = status;
	data.caseCode = caseCode;
	data.propertyAddr = propertyAddr;
	data.warnTimeStart = warnTimeStart;
	data.warnTimeEnd = warnTimeEnd?(warnTimeEnd+' 23:59:59'):warnTimeEnd;
	
	var queryId = "";
	if(currentDepHierarchy == "yucui_team"){
		queryId = "bizwarnListQueryByTeam";
	}
	else {
		queryId = "bizwarnListQueryByDistinct";
	}
	
	data.queryId = queryId;
	
	return data;
}

function init(){
	var data = getParams();
	
    $("#bizwarnList").aistGrid({
		ctx : ctx,
		queryId : data.queryId,
	    templeteId : 'template_bizwarnList',
	    data : data,
	    wrapperData : data,
	    columns : [{
			           colName :"案件编号"
			      },{
	    	           colName :"产证地址"
	    	      },{
	    	           colName :"预警类型"
	    	      },{
	    	           colName :"状态"
    	          },{
		    	       colName :"预警时间"
	    	      },{
		    	       colName :"解除时间"
	    	      },{
	    	           colName :"操作"
	    	      }]
	
	});
    
    $('#bizwarnList table').addClass("apply-table");
}

$(document).ready(function() {
	
	init();
	
	$('#addrSearchButton').click(function(){
		reloadGrid();
	});
	
	$("#cleanButton").click(function(){
		$("#warnType").val("");
		$("#status").val("");
		$("#caseCode").val("");
		$("#addr").val("");
		$("#warnTimeStart").val("");
		$("#warnTimeEnd").val("");
	});
	
	$('.date-picker-input').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	
});



















