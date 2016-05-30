$(document).ready(function() {
	$("#searchButton").click(function(){
		pricingList.init('/quickGrid/findPage',ctx); // 列表风格	
	});
	pricingList.init('/quickGrid/findPage',ctx); // 列表风格
});

var pricingList = function () {	
	
	 return {
	        init: function (url, _ctx,page) {
	        	ctx=_ctx;
	        	$("#pricingListDiv").block({ 
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
		            	$("#pricingListDiv").unblock();
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
        var obj=$('#pricingListDiv').find('.house-cont'); //定义页面元素对象
        data.ctx=_ctx;
        var html= template('pricingList', data); 
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
	data.search_residenceName = $("#residenceName").val() ; 
	data.search_ariserId = ariserId;
	data.queryId="queryToEguPricingPage";
//	data.sortname='PB_TIME';
 //   data.sortorder='desc';
    data.rows=10;
    data.page=page?page:1;
	return data;
}
function openW(evaCode){
	window.open(ctx+"/task/mobile/pricingInfo?evaCode="+evaCode+"");
}

function getPricingList(tableId,pageId){
	   $("#"+tableId).jqGrid("GridUnload");
	   $("#"+tableId).jqGrid({
	    	url:ctx+"/quickGrid/findPage",
	        datatype: "json",
	        mtype:"GET",
	        height: 300,
	        autowidth: true,
	        shrinkToFit: true,
	        rowNum: 8,
	        colNames: ['询价ID', '评估编号', '物业地址','询价状态', '询价时间', '询价结果','单价（元）','总价（万元）','目标银行','申请编号','是否确认','确认序号','结果编码'],
	        colModel: [
	            {name: 'PKID',index: 'PKID',  width: 30},
	            {name: 'EVA_CODE', index: 'EVA_CODE', width: 70},
	            {name: 'RESIDENCE_NAME', index: 'RESIDENCE_NAME', width: 160},
	            {name: 'STATUS', index: 'STATUS', width: 30},
	            {name: 'ARISE_TIME', index: 'ARISE_TIME', width: 70},
	            {name: 'RESULT', index: 'RESULT', width: 50},
	            {name: 'UNIT_PRICE', index: 'UNIT_PRICE', width: 50},
	            {name: 'TOTAL_PRICE', index: 'TOTAL_PRICE', width: 50},
	            {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', hidden:true,width: 80},
	            {name: 'APPLY_CODE', index: 'APPLY_CODE',hidden:true, width: 60},
	            {name: 'IS_FINAL', index: 'IS_FINAL',hidden:true, width: 0},
	            {name: 'COMFIRM_SEQ', index: 'COMFIRM_SEQ',hidden:true, width: 0},
	            {name: 'RESULT_CODE', index: 'RESULT_CODE', hidden:true,width: 0}	            
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
	        	queryId:"queryToEguPricingPage",
	        	search_residenceName:$("#residenceName").val(),
	        	search_ariserId:ariserId
	        },
	        gridComplete : function() { 
	        	
	        },		
	        onSelectRow : function(rowid,status) {
				var rowData = $("#"+tableId).jqGrid('getRowData', rowid);
//				$("#eva_code").val(rowData['EVA_CODE']);
	        	window.open(ctx+"/task/mobile/pricingInfo?evaCode="+rowData['EVA_CODE']);
			}
	    });

}

function searchPricingList(){
	$("#table_list_1").setGridParam({
		"postData" : {
        	queryId:"queryToEguPricingPage",
        	search_residenceName:$("#residenceName").val(),
        	search_ariserId:ariserId
        },
		"page":1 
	}).trigger('reloadGrid');
}

//$(document).ready(function () {
//	getPricingList("table_list_1","pager_list_1");
//	
//	$("#searchButton").click(function(){
//		searchPricingList();
//	});
// });