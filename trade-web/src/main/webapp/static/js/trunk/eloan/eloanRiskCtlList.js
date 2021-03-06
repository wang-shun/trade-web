/**
 * 风控措施清单
 * shilei
 *
 */

var ctx = $("#ctx").val();
var userId = $("#userId").val();

$(document).ready(function() {
	
    $('#searchBtn').click(function(){
    	var sPage = 1;
		reloadGrid(sPage);
	});
    
    $('#exportBtn').click(function(){
    	
    	var queryId = "queryEloanRiskControlList";
    	
    	var data = getParams();
    	
    	$.exportExcel({
    		ctx : ctx,
    		queryId : queryId,
    		colomns : ['ELOANCODE','RISKTYPE','SERVICECODE',
    		           'FINORGCODE', 'CUSTOMERNAME', 'EXECUTORID',
    		           'EXECUTORTEAMID', 'EXECUTORDISTID',
    		           'EXETIME', 'RCEXEID'],
    		data:data
    		});
    	
    });
	
	$('#riskCtlList table').addClass("table table_blue table-striped table-bordered table-hover");

	var sPage = 1;
	reloadGrid(sPage);

});

function reloadGrid(page){

	var data = getParams();
	data.page = page;
	data.rows = 12;
	
	$("#riskCtlList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryEloanRiskControlList',
	    templeteId : 'template_eloanRiskCtlList',
	    data : data,
	    wrapperData : data
    });
	
	// initpage(data.total, data.pagesize, data.page, data.records);
	
	//setStyle();
}

function getParams() {
	var eloanCode = $("#eloanCode").val();
	var eloanRiskType = $("#eloanRiskType").val();
	var eloanChooseRole = $("#eloanChooseRole").val();
	var eloanName = $("#eloanName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var exeTeam = $("#exeTeam").val();
	
	var data = {};
	
	data.sEloanCode = eloanCode;
	data.sEloanRiskType = eloanRiskType;
	data.sEloanChooseRole = eloanChooseRole;
	data.sEloanName = eloanName;
	data.sStartTime = startTime;
	data.sEndTime = endTime?(endTime+' 23:59:59'):endTime;
	data.sExeTeam = exeTeam;
	
	return data;
} 

//分页
function initpage(totalCount, pageSize, currentPage,records) {
	if (totalCount > 1500) {
		totalCount = 1500;
	}
	var currentTotalstrong = $('#currentTotalPage')
			.find('strong');
	if (totalCount < 1 || pageSize < 1
			|| currentPage < 1) {
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(
			currentPage + '/' + totalCount);
	$('#totalP').text(records);

	$("#pageBar").twbsPagination({
		totalPages : totalCount,
		visiblePages : 9,
		startPage : currentPage,
		first : '<i class="fa fa-step-backward"></i>',
		prev : '<i class="fa fa-chevron-left"></i>',
		next : '<i class="fa fa-chevron-right"></i>',
		last : '<i class="fa fa-step-forward"></i>',
		showGoto : true,
		onPageClick : function(event, page) {
			reloadGrid(page);
		}
	});
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
			$("#exeTeam").val(array[0].name);
			$("#exeTeam").attr('hVal', array[0].id);
		} else {
			$("#exeTeam").val("");
			$("#exeTeam").attr('hVal', "");
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