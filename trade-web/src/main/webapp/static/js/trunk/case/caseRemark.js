
var caseremarkList= (function(){    
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
    			colNames : ['环节','备注'],
    			colModel : [{
    				name : 'PART_CODE',
    				index : 'PART_CODE',
    				align : "center",
    				width : 50,
    				key : true,
    				resizable : false
    			},{
    				name : 'COMMENT',
    				index : 'COMMENT',
    				align : "center",
    				width : 50,
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
    				queryId : "queryCaseCommentList",
    				search_caseCode : caseCode
    			}
    		});
    		
    		$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#caseCommenTable').setGridWidth(width);
			});
    		
       }   
    };    
})();    


// 流水编号保留两位小数
function amountTwo(cellvalue, options, rowObject){
	 var result=(cellvalue/10000).toFixed(2);  // 保留2位小数
	 return result;
}


