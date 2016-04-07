var EloanWarnList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,adminOrg){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 250,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 8,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ 'PKID','产品名称','执行人','执行人电话','经理姓名','经理电话','所属服务部','状态', '超期天数', '最后导出时间','产证地址' ],
    			colModel : [ {
    				name : 'PKID',
    				index : 'PKID',
    				align : "center",
    				width : 0,
    				key : true,
    				resizable : false,
    				hidden : true
    			}, {
    				name : 'LOAN_SRV_CODE',
    				index : 'LOAN_SRV_CODE',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			}, {
    				name : 'EXECUTOR_ID',
    				index : 'EXECUTOR_ID',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'EXECUTOR_PHONE',
    				index : 'EXECUTOR_PHONE',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'MANAGER_NAME',
    				index : 'MANAGER_NAME',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'MANAGER_PHONE',
    				index : 'MANAGER_PHONE',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'PARENT_ORG_NAME',
    				index : 'PARENT_ORG_NAME',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			}, {
    				name : 'CONFIRM_STATUS',
    				index : 'CONFIRM_STATUS',
    				align:'center',
    				resizable : true,
    				width : 10
    			},{
    				name : 'EXCEED_DAY',
    				index : 'EXCEED_DAY',
    				align:'center',
    				resizable : true,
    				width : 10
    			}, {
    				name : 'LAST_EXCEED_EXPORT_TIME',
    				index : 'LAST_EXCEED_EXPORT_TIME',
    				sortable : false,
    				resizable : true,
    				width : 20
    			}, {
    				name : 'PROPERTY_ADDR',
    				index : 'PROPERTY_ADDR',
    				align:'center',
    				resizable : true,
    				width : 30
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			sortname:'CONFIRM_TIME',
    	        sortorder:'asc',
    	        viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",

    			gridComplete:function(){
    				
    			},
    			postData : {
    				queryId : "warnListQuery",
                    search_districtOrgId : adminOrg
    			}

    		});
    		
    		// Add responsive to jqGrid
    		$(window).bind('resize', function() {
    			var width = $('.jqGrid_wrapper').width();
    			$("#"+gridTableId).setGridWidth(width);
    		});
       }
    };    
})();  

function test() {
	 var prCodeArray = new Array();
	 var pkidList = jQuery("#gridTable").jqGrid('getGridParam', 'selarrrow');
	 for(var i = 0;i<pkidList.length;i++){
		 var item=$("#gridTable").jqGrid('getRowData',pkidList[i]);
		 prCodeArray.push(item.PKID);
	 }
	 alert(prCodeArray);
}
