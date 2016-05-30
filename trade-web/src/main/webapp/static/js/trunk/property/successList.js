/**
 * 产调处理结果
 * liaohail
 * 
 */
$(document).ready(function() {
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var prDistrictId = $("#prDistrictId").val();
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
		colNames : [ 'PKID','行政区域','物业地址', '产调项目','所属分行',
		             '产调申请人','申请人员工编号', '产调执行人', '产调申请时间',
		             '产调受理时间','产调完成时间','是否有效','无效原因','来源','区董' ,'操作'],
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
		},{
			name : 'PR_APPLIANT',
			index : 'PR_APPLIANT',
			width : 40
		},{
			name:"APPLIANT_EMPLOYEE_CODE",
			index:"APPLIANT_EMPLOYEE_CODE",
			width:40
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
			name : 'IS_SUCCESS',
			index : 'IS_SUCCESS',
			width : 30
		},{
			name : 'UNSUCCESS_REASON',
			index : 'UNSUCCESS_REASON',
			width : 30
		},
		{
			name : 'CHANNEL',
			index : 'CHANNEL',
			width : 30
		},
		{
			name : 'QUDS',
			index : 'QUDS',
			width : 30
		},{
			name : 'QUDS',
			index : 'QUDS',
			width : 30,
			formatter:function(cellvalue, options, rawObject){
				var a="<a href='../mobile/property/box/show?prCode="+rawObject.prCode+"' target='_blank'>查看附件</a>";
				return a;
			}
		}
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
			queryId : "querySuccessList",
			search_prDistrictId : prDistrictId,
			search_prStatus : prStatus
		}

	});

	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_property_list').setGridWidth(width);

	});
	 $('.contact-box').each(function() {
         animationHover(this, 'pulse');
     });
});

