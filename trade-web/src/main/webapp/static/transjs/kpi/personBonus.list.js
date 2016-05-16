var PersonBonusList = (function(){    
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
    			colNames : [ '主键','案件编号','过户时间','结案时间','物业地址','人员','服务','基础奖金','环节占比','满意度','是否达标','考核结果','满意度占比','绩效奖金'],
    			colModel : [ {
    				name : 'PKID',
    				index : 'PKID',
    				align : "center",
    				width : 0,
    				key : true,
    				resizable : false,
    				hidden : true
    			}, {
    				name : 'CASE_CODE',
    				index : 'CASE_CODE',
    				align : "center",
    				width : 20,
    				resizable : false
    			}, {
    				name : 'GUOHU_TIME',
    				index : 'GUOHU_TIME',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'CLOSE_TIME',
    				index : 'CLOSE_TIME',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'PROPERTY_ADDR',
    				index : 'PROPERTY_ADDR',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'PARTICIPANT',
    				index : 'PARTICIPANT',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'SRV_CODE',
    				index : 'SRV_CODE',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'BASE_AMOUNT',
    				index : 'BASE_AMOUNT',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'SRV_PART_IN',
    				index : 'SRV_PART_IN',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'SATISFACTION',
    				index : 'SATISFACTION',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'MKPI',
    				index : 'MKPI',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'KPI_RATE_SUM',
    				index : 'KPI_RATE_SUM',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'SRV_PART',
    				index : 'SRV_PART',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'AWARD_KPI_MONEY',
    				index : 'AWARD_KPI_MONEY',
    				align : "center",
    				width : 20,
    				resizable : false
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
    				queryId : "personBonusList",
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
