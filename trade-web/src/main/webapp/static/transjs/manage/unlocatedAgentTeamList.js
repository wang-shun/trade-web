var UnlocatedAgentTeamScope = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 600,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 20,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '区域名称','片区名称','组织CODE','组织名称','组织ID','片区ID','大战区名称','大区名称' ],
    			colModel : [ {
    				name : 'SWZ_ORG_NAME',
    				index : 'SWZ_ORG_NAME',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'AR_ORG_NAME',
    				index : 'AR_ORG_NAME',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'ORG_CODE',
    				index : 'ORG_CODE',
    				align : "center",
    				width : 20,
    				resizable : false
    			}, {
    				name : 'ORG_NAME',
    				index : 'ORG_NAME',
    				align : "center",
    				width : 20,
    				resizable : false
    			}, {
    				name : 'ID',
    				index : 'ID',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false,
    				hidden:true
    			},{
    				name : 'BUSIAR_ID',
    				index : 'BUSIAR_ID',
    				align : "center",
    				width : 20,
    				resizable : false,
    				hidden:true
    			},{
    				name : 'WZ_ORG_NAME',
    				index : 'WZ_ORG_NAME',
    				align : "center",
    				width : 20,
    				resizable : false,
    				hidden : true
    			}, {
    				name : 'BA_ORG_NAME',
    				index : 'BA_ORG_NAME',
    				align:'center',
    				resizable : true,
    				width : 10,
    				hidden : true
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			sortname:'ORG_CODE',
    	        sortorder:'desc',
    	        viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",

    			gridComplete:function(){
    				
    			},
    			postData : {
    				queryId : "queryUnlocatedAgentTeamScope"
    			},
    			onSelectRow : function(rowid,status) {
					var rowData = $("#"+gridTableId).jqGrid('getRowData', rowid);
					$("#agenTeamCode").val(rowData['ORG_CODE']);
					$("#agenTeamName").val(rowData['ORG_NAME']);
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

/*function test() {
	 var prCodeArray = new Array();
	 var pkidList = jQuery("#gridTable").jqGrid('getGridParam', 'selarrrow');
	 for(var i = 0;i<pkidList.length;i++){
		 var item=$("#gridTable").jqGrid('getRowData',pkidList[i]);
		 prCodeArray.push(item.PKID);
	 }
	 alert(prCodeArray);
}*/
