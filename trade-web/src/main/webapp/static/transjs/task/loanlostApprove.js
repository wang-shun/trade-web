$(document).ready(function() {
	$("#caseCommentList").caseCommentGrid({
		caseCode : caseCode,
		srvCode : taskitem
	});
	
	$("#reminder_list").jqGrid({
		//data : reminderdata,
		url:ctx+"/quickGrid/findPage",
		datatype : "json",
		height:120,
		width:1059,
		shrinkToFit : true,
        rowNum:4,
        sortname : 'OPERATOR_TIME',
		viewrecords : true,
		sortorder : "desc",
        viewrecords:true,
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
		}

		],
		pager : "#pager_list_1",
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		postData:{
        	queryId:"queryLoanlostApproveList",
        	search_caseCode: caseCode,
        	search_approveType: approveType,
        	search_processInstanceId: processInstanceId
        },
	});
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#reminder_list').setGridWidth(width);

	});
});

function getShowAttachment() {
	var caseCode = $("#caseCode").val();
	$.ajax({
		type : 'post',
		cache : false,
		async : true,//false同步，true异步
		dataType : 'json',
		url : ctx+'/attachment/quereyAttachment',
		data : [{
			name : 'caseCode',
			value : caseCode
		},
			{
				name : 'partCode',
				value : 'LoanlostApply'
			},
			{
				name : 'available',
				value : 'Y'
			}
		],
		dataType : "json",
		success : function(data) {
			dataLength=data.length;
			//将返回的数据进行包装
			var trStr = "";

			var ssss="";

			var oldType = "";
			$.each(data, function(indexAcc, value){
				if(value.partCode !='property_research'){
					if(value.preFileName!=oldType){
						if(oldType!=""){
							trStr += '</div></div>';
						}
						oldType=value.preFileName;
						trStr += '<div class="row ibox-content">';
						trStr += '<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">'+value.preFileName+'</label>';
						trStr += '<div class="col-sm-10 lightBoxGallery" style="text-align:left">';
					}
//					trStr += "<a href='#' onClick='show(\""+value.preFileAdress+"/_f.jpg\")' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
					trStr += "<a href='#' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
//					trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' style='padding-bottom: 5px;padding-top: 5px;'>";
					trStr += "<img src='"+appCtx['shcl-filesvr-web'] +"/JQeryUpload/getfile?fileId="+value.preFileAdress+"' style='padding-bottom: 5px;padding-top: 5px;width:100px;'>";
					trStr += "</a>";

				}
			});
			$("#imgShow").append(trStr);
			$('.wrapper-content').viewer();
		},
		error : function(errors) {
		}
	});
}