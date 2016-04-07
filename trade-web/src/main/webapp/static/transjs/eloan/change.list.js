var ChangeList = (function(){    
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
    			colNames : [ 'PKID','产品名称','','执行人','所属服务部','','变更前状态', '变更后状态','','','变更时间', '产证地址','客户姓名','申请金额(万元)','操作' ],
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
    				name : 'CONFIRM_STATUS',
    				index : 'CONFIRM_STATUS',
    				align : "center",
    				width : 20,
    				key : true,
    				resizable : false,
    				hidden : true
    			},{
    				name : 'EXECUTOR_ID',
    				index : 'EXECUTOR_ID',
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
    			},{
    				name : 'CHANGE_PKID',
    				index : 'CHANGE_PKID',
    				align:'center',
    				resizable : true,
    				hidden : true,
    				width : 20
    			},{
    				name : 'ST_FROM',
    				index : 'ST_FROM',
    				align:'center',
    				resizable : true,
    				width : 20
    			},{
    				name : 'ST_TO',
    				index : 'ST_TO',
    				align:'center',
    				resizable : true,
    				width : 20
    			}, {
    				name : 'ST_TO_ID',
    				index : 'ST_TO_ID',
    				align:'center',
    				resizable : true,
    				hidden : true,
    				width : 20
    			},{
    				name : 'ST_FROM_ID',
    				index : 'ST_FROM_ID',
    				align:'center',
    				resizable : true,
    				hidden : true,
    				width : 20
    			},{
    				name : 'CHANGE_DATE',
    				index : 'CHANGE_DATE',
    				sortable : false,
    				resizable : true,
    				width : 20
    			}, {
    				name : 'PROPERTY_ADDR',
    				index : 'PROPERTY_ADDR',
    				align:'center',
    				resizable : true,
    				width : 20
    			},{
    				name : 'CUST_NAME',
    				index : 'CUST_NAME',
    				align:'center',
    				resizable : true,
    				width : 20
    			},{
    				name : 'LOAN_AMOUNT',
    				index : 'LOAN_AMOUNT',
    				align:'center',
    				resizable : true,
    				width : 20
    			},{
    				name : 'CONFRIM',
    				index : 'CONFRIM',
    				align:'center',
    				resizable : true,
    				width : 70
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
	    				
	    				var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['PKID']+"\")' style='width:90px;'>详细</button>";
	    				var confirmBtn="<button  class='btn red' id='report_btn_'"+rowDatas['PKID']+"' onclick='confrimStatus(\""+ctx+"\",\""+rowDatas['PKID']+"\",\""+rowDatas['ST_TO_ID']+"\",\""+rowDatas['CHANGE_PKID']+"\")' style='width:90px;'>确认</button>";
	    				var cancelBtn="<button  class='btn red' id='report_btn_'"+rowDatas['PKID']+"' onclick='cancelStatus(\""+ctx+"\",\""+rowDatas['PKID']+"\",\""+rowDatas['ST_FROM_ID']+"\",\""+rowDatas['CHANGE_PKID']+"\")' style='width:90px;'>取消</button>";
		        		/*if(rowDatas['CONFIRM_STATUS'] == "1"){
		        			confirmBtn="<button  class='btn red' disabled='disabled' style='width:90px;'>已确认</button>";
		        		}	*/
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { CONFRIM: confirmBtn+"&nbsp;&nbsp;&nbsp;"+cancelBtn+"&nbsp;&nbsp;&nbsp;"+detailBtn});
    				}
    			},
    			postData : {
    				queryId : "changeListQuery",
    				search_districtOrgId : adminOrg
    			}
    		});
       }   
    };    
})();    

function openLoan(ctx,pkid) {
	$('#alertOper').attr("href",
			ctx + "/loan/box/details?pkid=" + pkid+"&isOnlyRead=1");
	$("#alertOper").trigger('click');
}

function confrimStatus(ctx,pkid,stTo,changePkId){
	$.ajax({
		url:ctx+"/loan/confirmStatus",
		method:"post",
		dataType:"json",
		data:{pkId:pkid,changePkId:changePkId,stTo:stTo},
		success:function(data){
			if(data != null ){
				if(data.success){
					var data = {};
					data.queryId="changeListQuery";
			    	$("#changeGridTable").jqGrid('setGridParam',{
			    		datatype:'json',
			    		mtype:'post',
			    		postData:data,
			    	}).trigger('reloadGrid');
			    	
			    	var data1 = {};
			    	data1.queryId="warnListQuery";
			    	$("#gridTable").jqGrid('setGridParam',{
			    		datatype:'json',
			    		mtype:'post',
			    		postData:data1,
			    	}).trigger('reloadGrid');
				}else{
					alert(data.message);
				}
			}
		}
	});
}

function cancelStatus(ctx,pkid,stFrom,changePkId) {
	$.ajax({
		url:ctx+"/loan/cancelStatus",
		method:"post",
		dataType:"json",
		data:{pkId:pkid,changePkId:changePkId,stFrom:stFrom},
		success:function(data){
			if(data != null ){
				if(data.success){
					var data = {};
					data.queryId="changeListQuery";
			    	$("#changeGridTable").jqGrid('setGridParam',{
			    		datatype:'json',
			    		mtype:'post',
			    		postData:data,
			    	}).trigger('reloadGrid');
			    	
			    	var data1 = {};
			    	data1.queryId="warnListQuery";
			    	$("#gridTable").jqGrid('setGridParam',{
			    		datatype:'json',
			    		mtype:'post',
			    		postData:data1,
			    	}).trigger('reloadGrid');
				}else{
					alert(data.message);
				}
			}
		}
	});
}