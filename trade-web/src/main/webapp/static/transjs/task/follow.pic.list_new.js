var FollowPicList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 250,
    			autowidth : true,
    			shrinkToFit : true,
    			rownumWidth:true,
    			rowNum : 18,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '附件类型','附件名称','上传时间','操作'],
    			colModel : [ {
    				name : 'ATT_TYPE',
    				index : 'ATT_TYPE',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'ATT_NAME',
    				index : 'ATT_NAME',
    				align : "center",
    				width : 20,
    				resizable : false
    			}, {
    				name : 'UPLOAD_DATE',
    				index : 'UPLOAD_DATE',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'READ',
    				index : 'READ',
    				align : "center",
    				width : 30,
    				resizable : false
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			//sortname:'UPLOAD_DATE',
    	        //sortorder:'desc',
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
	    				
	    				
	    				var link = "<button  class='btn red' onclick='addAttachmentReadLog(\""+ctx+"\",\""+ctmCode+"\",\""+caseCode+"\",\""+rowDatas['ATT_NAME']+"\",\""+rowDatas['ATT_PATH']+"\")'>查看附件</a>";
	    				
	    				//var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";
	    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { READ: link});
    				}
    			},
    			postData : {
    				queryId : "followPicListQuery",
    				argu_ctmCode : ctmCode
    			}
    			 
    		});
       }   
    };    
})();    


// 附件连接
function linkhouseInfo(cellvalue, options, rowObject){
	 var link = '<a href="" target="_black" onclick="addAttachmentReadLog('+cellvalue+')">'+cellvalue+'</a>';
	 return link;
}

function addAttachmentReadLog(ctx,ctmCode,caseCode,attachName,attachPath) {
	var tsAttachmentReadLog = {
		 	'caseCode':caseCode,
		 	'ctmCode':ctmCode,
		 	'attachmentName':attachName,
		 	'attachmentAddress':attachPath
	};
	//tsAttachmentReadLog=$.toJSON(tsAttachmentReadLog);
	
	$.ajax({
		type : 'post',
		cache : false,
		async : true,
		url : ctx+'/log/addAttachmentReadLog',
		data : tsAttachmentReadLog,
		dataType : "json",
		success : function(data) {
			//alert("记录日志成功");
		},
		error : function(errors) {
			//alert("记录日志失败");
			return false;
		}
	});
	
	window.open(attachPath);
	/*var url=ctx+"/api/imageshow/imgShow?img="+attachPath;
	window.open(encodeURI(encodeURI(url)));*/
}

