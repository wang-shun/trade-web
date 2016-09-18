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
	
	
	
	$(this).hide();
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
	
	setStyle();
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

function chooseApplicant(id) {
	userSelect({
		startOrgId : id,
		expandNodeId : id,
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : 'consultant',
		callBack : applicantSelectUserBack
	});
}

function applicantSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#prApp").val(array[0].username);
		$("#prApp").attr('hVal', array[0].userId);

	} else {
		$("#prApp").val("");
		$("#prApp").attr('hVal', "");
	}
}

function chooseDist(id) {
	
	orgSelect({
		displayId: 'oriGrpId',
		displayName: 'radioOrgName', 
		startOrgId: id, 
		expandNodeId: id,
		orgType:'', 
		departmentType:'', 
		departmentHeriarchy:'',
		chkStyle:'radio',
		callBack: distSelectOrgBack
	});
}

function distSelectOrgBack(array) {
		if (array && array.length > 0) {
			$("#prDistName").val(array[0].name);
			$("#prDistName").attr('hVal', array[0].id);
		} else {
			$("#prDistName").val("");
			$("#prDistName").attr('hVal', "");
		}
}

function setStyle(){
	//left
	$('.demo-left').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'left',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
	});

	//right
	$('.demo-right').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
	});

	//top
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});

	//bottom
	$('.demo-bottom').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'bottom',
		offsetX: 8,
		offsetY: 5,
	});	
}