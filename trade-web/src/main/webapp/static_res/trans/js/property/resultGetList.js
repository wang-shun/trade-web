
/**
 * 我的产调
 * liaohail
 * 
 */

var ctx = $("#ctx").val();
var prAppliant = $("#userId").val();

$(document).ready(function() {
	
	var data = {};
	data.search_prAppliant = prAppliant;
	
	$("#resultGetList").aistGrid({
		ctx : ctx,
		queryId : 'queryResultGetList',
	    templeteId : 'template_resultGetList',
	    data : data,
	    columns : [{
			           colName :"行政区域"
			      },{
	    	           colName :"产证地址"
	    	      },{
	    	           colName :"产调申请时间"
    	          },{
	    	           colName :"是否有效"
    	          },{
		    	       colName :"产调申请"
	    	      },{
	    	           colName :"操作"
	    	      }]
	});
	
	// 查询
	$('#addrSearchButton').click(function() {
		reloadGrid();
	});
	
	$('#resultGetList table').addClass("table table_blue table-striped table-bordered table-hover");
	
	reloadGrid();
	
});

function reloadGrid(){
	
	var data = {};
	data.search_prAppliant = prAppliant;
	
	var propertyAddr =  $("#addr").val();
	data.search_propertyAddr = propertyAddr;
	
	$("#resultGetList").reloadGrid({
    	ctx : ctx,
    	queryId : 'queryResultGetList',
	    templeteId : 'template_resultGetList',
	    data : data
    });
}

// 查看结果
function resultGet(prcode){
	location.href = ctx + '/mobile/property/box/show?prCode=' + prcode;
}
