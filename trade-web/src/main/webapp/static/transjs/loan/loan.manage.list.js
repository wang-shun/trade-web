var LoanManageList = (function(){    
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
    			colNames : [ 'pkId','产品名称','合作机构','产证地址','客户姓名', '案件负责人', '自填状态','案件已确认状态','申请金额','申请时间','放款金额','放款时间','业务员姓名','经理姓名','分行组别','所属区董','所属区域','所属服务部','所属组别','创建时间','操作' ],
    			colModel : [ {
    				name : 'pkId',
    				index : 'pkId',
    				align : "center",
    				width : 30,
    				key : true,
    				resizable : false,
    				hidden : true
    			}, {
    				name : 'loanSrvCode',
    				index : 'loanSrvCode',
    				align : "center",
    				width : 30,
    				key : true,
    				resizable : false
    			}, {
    				name : 'finOrgName',
    				index : 'finOrgName',
    				align : "center",
    				width : 30,
    				key : true,
    				resizable : false
    			},{
    				name : 'propertyAddr',
    				index : 'propertyAddr',
    				align:'center',
    				resizable : true,
    				width : 40
    			},{
    				name : 'custName',
    				index : 'custName',
    				align:'center',
    				resizable : true,
    				width : 10
    			}, 			
    			{
    				name : 'executorId',
    				index : 'executorId',
    				align:'center',
    				resizable : true,
    				width : 10
    			}, {
    				name : 'applyStatus',
    				index : 'applyStatus',
    				align:'center',
    				resizable : true,
    				width : 10
    			}, {
    				name : 'confirmStatus',
    				index : 'confirmStatus',
    				align:'center',
    				resizable : true,
    				width : 10
    			},{
    				name : 'loanAmount',
    				index : 'loanAmount',
    				align:'center',
    				resizable : true,
    				width : 10,
    				hidden : true
    			},{
    				name : 'applyTime',
    				index : 'applyTime',
    				align:'center',
    				resizable : true,
    				width : 40,
    				hidden : true
    			}, {
    				name : 'actualAmount',
    				index : 'actualAmount',
    				align:'center',
    				resizable : true,
    				width : 10,
    				hidden : true
    			}, {
    				name : 'releaseTime',
    				index : 'releaseTime',
    				align:'center',
    				resizable : true,
    				width : 40
    			},{
    				name : 'agentName',
    				index : 'agentName',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			}, {
    				name : 'jfhjlName',
    				index : 'jfhjlName',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false
    			},{
    				name : 'orgName',
    				index : 'orgName',
    				align:'center',
    				resizable : true,
    				width : 20
    			},{
    				name : 'jqydsName',
    				index : 'jqydsName',
    				align:'center',
    				resizable : true,
    				width : 20
    			}, {
    				name : 'belogDistrict',
    				index : 'belogDistrict',
    				align:'center',
    				resizable : true,
    				width : 20
    			}, {
    				name : 'PARENT_ORG_NAME',
    				index : 'PARENT_ORG_NAME',
    				align:'center',
    				resizable : true,
    				width : 20
    			}, {
    				name : 'yuOrgName',
    				index : 'yuOrgName',
    				align:'center',
    				resizable : true,
    				width : 20
    			}, 
    			
    			{
    				name : 'createTime',
    				index : 'createTime',
    				align:'center',
    				resizable : true,
    				width : 40
    			},{
    				name : 'CONFRIM',
    				index : 'CONFRIM',
    				align:'center',
    				resizable : true,
    				width : 50
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
    				var ids = jQuery("#"+gridTableId).jqGrid('getDataIDs');
    				for (var i = 0; i < ids.length; i++) {
	    				var id = ids[i];
	    				var rowDatas = jQuery("#"+gridTableId).jqGrid('getRowData', ids[i]); // 获取当前行
	    				
	    				var detailBtn = "<button  class='btn red'  onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";
	    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { CONFRIM: detailBtn});
    				}
    				
    			},
    			postData : {
    				queryId : "loanManageList",
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

function openLoan(ctx,pkid) {
	$('#alertOper').attr("href",
			ctx + "/loan/box/details?pkid=" + pkid+"&isOnlyRead=1");
	$("#alertOper").trigger('click');
}

function test() {
	 var prCodeArray = new Array();
	 var pkidList = jQuery("#gridTable").jqGrid('getGridParam', 'selarrrow');
	 for(var i = 0;i<pkidList.length;i++){
		 var item=$("#gridTable").jqGrid('getRowData',pkidList[i]);
		 prCodeArray.push(item.PKID);
	 }
	 alert(prCodeArray);
}
