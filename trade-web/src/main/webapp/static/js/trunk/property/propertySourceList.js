/**
 * 产调来源清单
 * shilei
 *
 */

var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prDep = $("#prDep").val();

$(document).ready(function() {
	
	var data = {};
    data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;

    $("#sourceList").aistGrid({
		ctx : ctx,
		queryId : 'querySourceList',
	    templeteId : 'template_sourceList',
	    data : data,
	    wrapperData : data,
	    columns : [{
			           colName :"产调编号"
			      },{
	    	           colName :"产调地址"
	    	      },{
	    	           colName :"产调来源"
	    	      },{
	    	           colName :"成本归属"
    	          },{
	    	           colName :"申请人"
    	          },{
		    	       colName :"时间"
	    	      }]
	
	});
    
    $('#searchBtn').click(function(){
		reloadGrid();
	});
	
	//$('#sourceList table').addClass("apply-table");
	$('#sourceList table').addClass("table table_blue table-striped table-bordered table-hover");
	
	reloadGrid();
});

function reloadGrid(){
	
	var data = getParams();
	
	data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;
	
	$("#sourceList").reloadGrid({
    	ctx : ctx,
		queryId : 'querySourceList',
	    templeteId : 'template_sourceList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	
	var prApp = $("#prApp").val();
	var prCostMgr = $("#prCostMgr").val();
	var prStatus = $("#prStatus").val();
	var prDistName = $("#prDistName").val();
	var prAppDep = $("#prAppDep").val();
	var timeType =  $("#timeType").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var prIsSuccess = $('input[name="successGroup"]:checked').val();
	
	var data = {};
	
	data.sPrApp = prApp;
	data.sPrCostMgr = prCostMgr;
	data.sPrStatus = prStatus;
	data.sPrDistName = prDistName;
	data.sPrAppDep = prAppDep;
	data.sTimeType = timeType;
	data.sStartTime = startTime;
	data.sEndTime = endTime?(endTime+' 23:59:59'):endTime;
	data.sPrIsSuccess = prIsSuccess;
	
	return data;
} 