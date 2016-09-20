/**
 * 风控措施清单
 * shilei
 *
 */

var ctx = $("#ctx").val();

$(document).ready(function() {
	
	var data = {};

    $("#riskCtlList").aistGrid({
		ctx : ctx,
		queryId : 'queryEloanRiskControlList',
	    templeteId : 'template_eloanRiskCtlList',
	    data : data,
	    wrapperData : data,
	    columns : [{
			           colName :"贷款合约编号"
			      },{
	    	           colName :"产品类型"
	    	      },{
	    	           colName :"风控措施类型"
	    	      },{
	    	           colName :"借款人"
    	          },{
	    	           colName :"贷款专员"
    	          },{
		    	       colName :"风控执行人"
	    	      },{
		    	       colName :"操作"
	    	      }]
	
	});
	
	//$('#sourceList table').addClass("apply-table");
	$('#riskCtlList table').addClass("table table_blue table-striped table-bordered table-hover");
	
	reloadGrid();

});

function reloadGrid(){
	
	var data = getParams();
	
	$("#riskCtlList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryEloanRiskControlList',
	    templeteId : 'template_eloanRiskCtlList',
	    data : data,
	    wrapperData : data
    });
	
	setStyle();
}

function getParams() {
	var data = {};
	return data;
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

/*function exportExcel(){
	
	var queryId = "querySourceList";
	
	var data = getParams();
	
	data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;
	
	$.exportExcel({
		ctx : "..",
		queryId : queryId,
		colomns : ['PR_CODE','IS_SUCCESS','UNSUCCESS_REASON','DIST_CODE',
				   'PR_ADDRESS','APP_RNAME','PR_APPLY_ORG_NAME','PR_APPLY_DEP_NAME','ORG_NAME',
				   'EXE_RNAME','PR_COST_ORG_MGR','PR_COST_ORG_NAME','PR_STATUS','PR_APPLY_TIME',
				   'PR_ACCPET_TIME','PR_COMPLETE_TIME'],
		data:data
	});
}*/