var GuoHuApproveList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height:120,
    			width:1059,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum:4,
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
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			sortname : 'OPERATOR_TIME',
    			sortorder : "desc",
    	        viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",

    			gridComplete:function(){
    				
    			},
    			postData:{
    	        	queryId:"queryLoanlostApproveList",
    	        	search_caseCode: caseCode,
    	        	search_approveType: approveType,
    	        	search_processInstanceId: processInstanceId
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