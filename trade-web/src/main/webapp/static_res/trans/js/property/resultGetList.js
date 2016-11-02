
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
			           colName :"来源归属"
			      },{
	    	           colName :"产证地址"
	    	      },{
	    	           colName :"产调申请时间"
    	          },{
		    	       colName :"负责人"
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
	
	setStyle();
}

// 查看结果
function resultGet(prcode){
	location.href = ctx + '/mobile/property/box/show?prCode=' + prcode;
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