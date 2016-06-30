/**
 * 我的产调
 * liaohail
 * 
 */
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var prAppliant = $("#userId").val();
	var prStatus = $("#prStatus").val();
	//jqGrid 初始化
	$("#table_property_list").jqGrid({
		url : url,
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 8,
		/*   rowList: [10, 20, 30], */
		colNames : [ 'PKID','所在区域','产证地址', '产调项目','所属分行',
		             '产调申请人', '产调执行人', '产调申请时间',
		             '产调受理时间','产调完成时间','状态','是否有效','无效原因','产调结果' ],
		colModel : [ {
			name : 'PKID',
			index : 'PKID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		},{
			name : 'DIST_CODE',
			index : 'DIST_CODE',
			align : "center",
			width : 30
			
		}, {
			name : 'PROPERTY_ADDR',
			index : 'PROPERTY_ADDR',
			width : 60
		}, {
			name : 'PR_CAT',
			index : 'PR_CAT',
			width : 40
		}, {
			name : 'orgName',
			index : 'orgName',
			width : 40
		}, {
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		}, {
			name : 'PR_EXECUTOR',
			index : 'PR_EXECUTOR',
			width : 40
		}, {
			name : 'PR_APPLY_TIME',
			index : 'PR_APPLY_TIME',
			width : 50
		}, {
			name : 'PR_ACCPET_TIME',
			index : 'PR_ACCPET_TIME',
			width : 50
		}, {
			name : 'PR_COMPLETE_TIME',
			index : 'PR_COMPLETE_TIME',
			width : 50
		},{
			name : 'PR_STATUS',
			index : 'PR_STATUS',
			width : 20
		},{
			name : 'IS_SUCCESS',
			index : 'IS_SUCCESS',
			width : 30
		},{
			name : 'UNSUCCESS_REASON',
			index : 'UNSUCCESS_REASON',
			width : 30
		},{
			name : 'resultGet',
			index : 'resultGet',
			width : 30,
			formatter : resultGet
		},
		],
		multiselect: false,
		pager : "#pager_property_list",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		onSelectRow : function(rowid,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	    		var ids=$("#table_property_list").jqGrid("getGridParam","selarrrow");
	    		if(ids.length==0){
	        		$("#caseDistributeButton").attr("disabled", true);
	    		}
    		}
		},
		onSelectAll :function(aRowids,status) {
			if(status){
    		    $("#caseDistributeButton").attr("disabled", false);
    		}else{
	        	$("#caseDistributeButton").attr("disabled", true);
    		}
		},
		postData : {
			queryId : "queryResultGetList",
			search_prAppliant : prAppliant
		}

	});
	function resultGet(cellvalue, options, rawObject){
		if(rawObject.PR_STATUS!='已完成'){
			return '';
		}
		var resultGet = "<button type='button' onclick='resultGet(\""+rawObject.PR_CODE+"\","+rawObject.PKID+")' class='btn red' >查看</button>";
			return resultGet;
	}
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});
//查看结果
function resultGet(caseCode,pkid){
	$('#resultGet').attr("href",ctx+"/mobile/property/box/show?prCode="+caseCode);
	$("#resultGet").trigger('click');
}
