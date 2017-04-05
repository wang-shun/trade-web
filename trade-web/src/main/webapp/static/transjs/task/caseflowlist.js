var caseflowListitem = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId, caseCode){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 250,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 10,
    			/*   rowList: [10, 20, 30], */
    			colNames : ['发起人','款项名','流水类型','流水方向','流水金额(万元)','时间'],
    			colModel : [{
    				name : 'INITIATOR',
    				index : 'INITIATOR',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'CASH_ITEM',
    				index : 'CASH_ITEM',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'CASH_FLOW_TYPE',
    				index : 'CASH_FLOW_TYPE',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'FLOW_DIRECTION',
    				index : 'FLOW_DIRECTION',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			}, {
    				name : 'FLOW_AMOUNT',
    				index : 'FLOW_AMOUNT',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false,
    				formatter : amountTwo
    			}, {
    				name : 'FLOW_TIME',
    				index : 'FLOW_TIME',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			// sortname:'UPLOAD_DATE',
    	        // sortorder:'desc',
    			viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",
    			gridComplete:function(){
    				
    			},
    			postData : {
    				queryId : "queryCaseFlowList",
    				search_caseCode : caseCode
    			}
    		});
    		
    		/*$(window).bind('resize', function() {
				$('#gridTable1').setGridWidth($(window).width()*0.7);
			});*/
       }
    };    
})();    


// 流水编号保留两位小数
function amountTwo(cellvalue, options, rowObject){
	 var result=(cellvalue/10000).toFixed(2);  // 保留2位小数
	 return result;
}

