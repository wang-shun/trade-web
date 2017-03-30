$(document).ready(function() {
	$("#searchButton").click(function(){
		reportList.init('/quickGrid/findPage',ctx); // 列表风格	
	});
	reportList.init('/quickGrid/findPage',ctx); // 列表风格
});

var reportList = function () {	
	
	 return {
	        init: function (url, _ctx,page) {
	        	ctx=_ctx;
	        //	initTemplate();
	        	$("#reportListDiv").block({ 
	                 message: "<span style='margin-top:100px'>数据加载中...</span>", 
	                 showOverlay: false, 
	                 css: { 
	                     width: '100%', 
	                     height:'100%',
	                     top: '100px', 
	                     border: 'none', 
	                     backgroundColor:'#fff',
	                     opacity: .8
	                     } 
	             }); 
	        	$.ajax({    
	        	    type: "POST",
	           	    url : ctx+url, 
		            dataType:"json",    
		            global:false,
		            data : packData(page),
		            async: true,
		            success: function(data){
		            	$("#reportListDiv").unblock();
		            	createDivList(data,_ctx,_ctx);	
		            },
		            error: function (data) {
	                    alert("data:" + data.responseText);
	                }
		       });
	      } 
	 };
	 
	 //创建列表
	 function createDivList(data,ctx,_ctx){
		 // 清空列表
        var obj=$('#reportListDiv').find('.house-cont'); //定义页面元素对象
        data.ctx=_ctx;
        var html= template('reportList', data); 
        obj.empty();
        obj.html(html);
        // 显示分页
        
        initpage(data.total,data.pagesize,data.page);
	 }
}();


//分页
function initpage(totalCount,pageSize,currentPage)
{
		var currentTotalstrong=$('#currentTotalPage').find('strong');
		cpage=currentPage<=0?1:currentPage;
		$(currentTotalstrong).empty();
		$(currentTotalstrong).text(cpage+'/'+totalCount);
		
		$("#pageBar").twbsPagination({
			totalPages:totalCount,
			visiblePages:9,
			startPage:cpage,
			first:'<i class="icon-step-backward"></i>',
			prev:'<i class="icon-chevron-left"></i>',
			next:'<i class="icon-chevron-right"></i>',
			last:'<i class="icon-step-forward"></i>',
			onPageClick: function (event, page) {
				 pricingList.init('/quickGrid/findPage',ctx,page); // 列表风格
		    }
		});
}
/**
 * packData
 */
function packData(page){
	var data = {};	
	data.searchResidenceName = $("#residenceName").val() ; 
	data.search_ariserId = ariserId;
	data.queryId="queryToEvaReportPage";
//	data.sortname='PB_TIME';
 //   data.sortorder='desc';
    data.rows=10;
    data.page=page?page:1;
	return data;
}


function getReportList(tableId,pageId){
	$("#"+tableId).jqGrid("GridUnload");
	$("#"+tableId).jqGrid({
    	url:ctx+"/quickGrid/findPage",
        datatype: "json",
        height: 300,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 8,
        colNames: ['序号', '评估编号', '报告序列号', '报告类型', '当前状态','状态时间', '目标银行','是否最终报告'],
        colModel: [
            {name: 'PKID', index: 'PKID',  width: 60},
            {name: 'EVA_CODE', index: 'EVA_CODE', width: 140},
            {name: 'SERIAL_NUMBER', index: 'SERIAL_NUMBER',hidden:true, width: 140},
            {name: 'REPORT_TYPE', index: 'REPORT_TYPE', width: 140},
            {name: 'FEEDBACK', index: 'FEEDBACK', width: 140},
            {name: 'REPORT_RESPONSE_TIME', index: 'REPORT_RESPONSE_TIME', width: 140},
            {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', width: 240},
            {name: 'IS_FINAL_REPORT', index: 'IS_FINAL_REPORT', width: 0,hidden:true}           
        ], 
        add: true,
        addtext: 'Add',
        pager: "#"+pageId,
        viewrecords: true,
        pagebuttions: true,
        hidegrid: false,
        recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
        pgtext : " {0} 共 {1} 页",
        search:false,
        postData:{
        	queryId:"queryToEvaReportPage",
        	search_evaCode:$("#evaCode").val(),
        	search_ariserId:ariserId
        },
        gridComplete : function() { 
        	
        }
        
    });

}
function searchReportList(){
	$("#table_list_1").setGridParam({
		"postData" : {
			queryId:"queryToEvaReportPage",
        	search_evaCode:$("#evaCode").val(),
        	search_ariserId:ariserId
        },
		"page":1 
	}).trigger('reloadGrid');
}
//$(document).ready(function () {
//	getReportList("table_list_1","pager_list_1");
//	
//	$("#searchButton").click(function(){
//		searchReportList();
//	});
// });