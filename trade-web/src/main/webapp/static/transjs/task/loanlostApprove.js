$(document).ready(function() {
	$("#reminder_list").jqGrid({
		//data : reminderdata,
		url:ctx+"/quickGrid/findPage",
		datatype : "json",
		height:120,
		width:1059,
		shrinkToFit : true,
        rowNum:4,
        sortname : 'OPERATOR_TIME',
		viewrecords : true,
		sortorder : "desc",
        viewrecords:true,
		colNames : [ '操作时间', '操作人', '环节编码', '内容' ],
		colModel : [ {
			name : 'OPERATOR_TIME',
			index : 'OPERATOR_TIME',
			width : '15%'
		}, {
			name : 'OPERATOR',
			index : 'OPERATOR',
			width : '15%'
		}, {
			name : 'PART_CODE',
			index : 'PART_CODE',
			width : '20%'
		}, {
			name : 'CONTENT',
			index : 'CONTENT',
			width : '50%'
		}

		],
		pager : "#pager_list_1",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		postData:{
        	queryId:"queryLoanlostApproveList",
        	search_caseCode: caseCode,
        	search_approveType: approveType,
        	search_processInstanceId: processInstanceId
        },
	});
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#reminder_list').setGridWidth(width);

	});
}); 