var MonthKpiImportList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,belongMonth){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 550,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 8,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '主键','人员','所在组别','所属贵宾服务部','类型','当月金融产品数','上月滚存数','金融产品产出率'],
    			colModel : [ {
    				name : 'PKID',
    				index : 'PKID',
    				align : "center",
    				width : 0,
    				key : true,
    				resizable : true,
    				hidden : true
    			}, {
    				name : 'PARTICIPANT',
    				index : 'PARTICIPANT',
    				align : "center",
    				width : 20,
    				resizable : true
    			}, {
    				name : 'TEAM_ID',
    				index : 'TEAM_ID',
    				align : "center",
    				width : 20,
    				resizable : true
    			},{
    				name : 'DISTRICT_ID',
    				index : 'DISTRICT_ID',
    				align : "center",
    				width : 20,
    				resizable : true
    				//hidden : true
    			},{
    				name : 'TYPE',
    				index : 'TYPE',
    				align : "center",
    				width : 20,
    				resizable : true,
    				formatter : function(cellvalue,
							options, rawObject) {
    					if(cellvalue=='IMP'){
    						return '导入';
    					}else if(cellvalue=='GEN'){
    						return '生成';
    					}
    					return cellvalue;
    				}
    			},{
    				name : 'FIN_ORDER',
    				index : 'FIN_ORDER',
    				align : "center",
    				width : 20,
    				resizable : true
    			},{
    				name : 'FIN_ORDER_ROLL',
    				index : 'FIN_ORDER_ROLL',
    				align : "center",
    				width : 20,
    				resizable : true
    			}, {
    				name : 'FIN_ORDER_RATE',
    				index : 'FIN_ORDER_RATE',
    				align:'center',
    				resizable : true,
    				width : 10
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			sortname:'PKID',
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
    				queryId : "monthKpiList",
                    argu_belongMonth : belongMonth
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
