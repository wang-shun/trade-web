/**
 * 案件详情
 *
 */
Array.prototype.contains = function(obj){
	 var i = this.length;
     while (i--) {
         if (this[i] === obj) {
         return true;
         }
     }
     return false;
};

$(document).ready(function() {

	$("#subscribe").subscribeToggle({
		moduleType:"1001",
		subscribeType:"2001"
	});
	$("#mortageService").change(function(){
		mortageService();
	});
	var caseCode = $("#caseCode").val();
	var evaCode = $("#evaCode").val();
	//评估操作记录
	getOperateLogList(evaCode);
	
	//流程备注
	caseremarkList.init(ctx,'/quickGrid/findPage','evalCommenTable','evalCommenPager',caseCode);  // 显示各个流程的备注信息列表
	
	//审批记录
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_invoice','gridPager_invoice',caseCode,'10');
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_rebate','gridPager_rebate',caseCode,'10');
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_baodao','gridPager_baodao',caseCode,'11');
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_settle','gridPager_settle',caseCode,'10');
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_message','gridPager_message',caseCode,'10');
	ApproveList.init(ctx,'/quickGrid/findPage', 'gridTable_refund','gridPager_refund',caseCode,'10');
	
	var width = $('.jqGrid_wrapper').width();
	$('#operation_history_table').setGridWidth(width);

	$('#gridTable_invoice').setGridWidth(width);
	$('#gridTable_rebate').setGridWidth(width);
	$('#gridTable_baodao').setGridWidth(width);
	$('#gridTable_settle').setGridWidth(width);
	$('#gridTable_message').setGridWidth(width);
	$('#gridTable_refund').setGridWidth(width);
	
	$(window).bind('resize', function() {
		var width1 = $('.jqGrid_wrapper').width();
		$('#operation_history_table').setGridWidth(width1);
		$('#gridTable_invoice').setGridWidth(width1);
		$('#gridTable_rebate').setGridWidth(width1);
		$('#gridTable_baodao').setGridWidth(width1);
		$('#gridTable_settle').setGridWidth(width1);
		$('#gridTable_message').setGridWidth(width1);
		$('#gridTable_refund').setGridWidth(width1);
	});
	
	//附件		
	getShowAttachment();
	//业绩记录/收费情况查询
	//queryPer();

});

function getOperateLogList(evaCode){
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	// Configuration for jqGrid Example 1
	var dispCols=[ 'TASKID', 'CASECODE', 'PARTCODE',
					'INSTCODE', '红绿灯', '红灯记录', '所属流程信息','任务名', '执行人', '预计执行时间',
					'执行时间','任务状态' ];
	var colModels=
	[ {
		name : 'ID',
		index : 'ID',
		align : "center",
		width : 0,
		key : true,
		resizable : false,
		hidden : true
	}, {
		name : 'CASE_CODE',
		index : 'CASE_CODE',
		align : "center",
		width : 0,
		key : true,
		resizable : false,
		hidden : true
	}, {
		name : 'PART_CODE',
		index : 'PART_CODE',
		align : "center",
		width : 0,
		key : true,
		resizable : false,
		hidden : true
	}, {
		name : 'INST_CODE',
		index : 'INST_CODE',
		align : "center",
		width : 0,
		key : true,
		resizable : false,
		hidden : true
	}, {
		name : 'DATELAMP',
		index : 'DATELAMP',
		width : 40,
		editable : true,
		formatter : dateLampFormatter
	}, {
		name : 'RED_LOCK',
		index : 'RED_LOCK',
		width : 30,
		editable : true,
		formatter : isRedFormatter
	}, {
		name : 'WFE_NAME',
		index : 'WFE_NAME',
		width : 30
	}, {
		name : 'NAME',
		index : 'NAME',
		width : 75
	}, {
		name : 'ASSIGNEE',
		index : 'ASSIGNEE',
		width : 75
	}, {
		name : 'EST_PART_TIME',
		index : 'EST_PART_TIME',
		width : 90
	}, {
		name : 'END_TIME',
		index : 'END_TIME',
		width : 90
	},{
		name : 'status',
		index : 'status',
		width : 90
	}
	];
	$("#operation_history_table").jqGrid(
			{
				url : url,
				datatype : "json",
				mtype : "POST",
				height : 300,
				autowidth : true,
				shrinkToFit : true,
				rowNum : 10,
				/* rowList: [10, 20, 30], */
				colNames : dispCols,
				colModel : colModels,
				pager : "#operation_history_pager",
				sortname:'A.create_time',
    	        sortorder:'desc',
				viewrecords : true,
				pagebuttions : true,
				hidegrid : false,
				recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
				pgtext : " {0} 共 {1} 页",

				// rowid为grid中的行顺序
				onSelectRow : function(rowid) {
				},
				postData : {
					queryId : "queryTaskHistoryItemList",
					argu_evaCode : evaCode
				}

	});
}

function showAttachment(url){
	window.open(url);
}

var ApproveList = (function() {
	return {
		init : function(ctx, url, gridTableId, gridPagerId,
				caseCode,approveType) {
			//jqGrid 初始化
			$("#" + gridTableId)
					.jqGrid(
							{
								url : ctx + url,
								mtype : 'GET',
								datatype : "json",
								height : 125,
								autowidth : true,
								shrinkToFit : true,
								rowNum : 3,
								/*   rowList: [10, 20, 30], */
								colNames : [ '审批人', '审批时间', '审批结果', '审批意见' ],
								colModel : [ {
									name : 'OPERATOR',
									index : 'OPERATOR',
									align : "center",
									width : 25	,
									resizable : false
								}, {
									name : 'OPERATOR_TIME',
									index : 'OPERATOR_TIME',
									align : "center",
									width : 25,
									resizable : false
								}, {
									name : 'NOT_APPROVE',
									index : 'NOT_APPROVE',
									align : "center",
									width : 25,
									resizable : false
								//formatter : linkhouseInfo
								}, {
									name : 'CONTENT',
									index : 'CONTENT',
									align : "center",
									width : 25,
									resizable : false
								} ],
								multiselect : true,
								pager : "#" + gridPagerId,
								//sortname:'UPLOAD_DATE',
								//sortorder:'desc',
								viewrecords : true,
								pagebuttions : true,
								multiselect : false,
								hidegrid : false,
								recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
								pgtext : " {0} 共 {1} 页",
								gridComplete : function() {
									var ids = jQuery("#" + gridTableId)
											.jqGrid('getDataIDs');
									for (var i = 0; i < ids.length; i++) {
										var id = ids[i];
										var rowDatas = jQuery(
												"#" + gridTableId).jqGrid(
												'getRowData', ids[i]); // 获取当前行

										var auditResult = rowDatas['NOT_APPROVE'];
										var auditResultDisplay = null;
										if (!auditResult) {
											auditResultDisplay = "审批通过"
										} else {
											auditResultDisplay = auditResult;
										}
										jQuery("#" + gridTableId)
												.jqGrid(
														'setRowData',
														ids[i],
														{
															NOT_APPROVE : auditResultDisplay
														});
									}
								},
								postData : {
									queryId : "queryLoanlostApproveList",
									caseCode : caseCode,
									approveType : approveType
								}

							});
		}
	};
})();

/**
 * 附件信息
 */
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
				if(value.partCode !='property_research'){//!产调
					//附件类型,T_TO_ACCESORY_LIST
					if(value.preFileName!=oldType){
						if(oldType!=""){
							trStr += '</div></div>';
						}
						oldType=value.preFileName;
						trStr += '<div class="row ibox-content">';
						trStr += '<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">'+value.preFileName+'</label>';
						trStr += '<div class="col-sm-10 lightBoxGallery" style="text-align:left">';
					}
					trStr += "<a href='#' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
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


/**
 * 评估流程重启
 */
function evalProcessRestart(){
	window.wxc.confirm("确定重启评估流程？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/eval/restart/init",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode,evaCode:$("#evaCode").val()},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , 
		   success:function(data){
				if(!data.success){
					$.unblockUI();   
					window.wxc.error(data.message);
				}else{
					window.location.href=ctx+"/eval/task/route/evalServiceRestartApply?taskId="+data.content.activeTaskId+"&instCode="+data.content.id+"&caseCode="+caseCode+"&evalCode="+data.content.businessKey;
				}
			}
		});
	}});
}

//爆单
function evalBaodan(){
	window.wxc.confirm("确定评估爆单？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/eval/stop/init",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode,evaCode:$("#evaCode").val()},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , 
		   success:function(data){
				if(!data.success){
					$.unblockUI();   
					window.wxc.error(data.message);
				
				}else{
					window.location.href=ctx+"/eval/task/route/evalServiceStopApply?taskId="+data.content.activeTaskId+"&instCode="+data.content.id+"&caseCode="+caseCode+"&evaCode="+data.content.businessKey;
				}
			}
		});
	}});
}

//驳回
function evalReject(caseCode){
	window.wxc.confirm("确定评估驳回？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/eval/stop/init",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode,evaCode:$("#evaCode").val()},
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){//超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , 
		   success:function(data){
				if(!data.success){
					$.unblockUI();   
					window.wxc.error(data.message);
				
				}else{
					window.wxc.alert("驳回成功");
					//window.location.href=ctx+"/eval/task/route/evalServiceStopApply?taskId="+data.content.activeTaskId+"&instCode="+data.content.id+"&caseCode="+caseCode+"&evaCode="+data.content.businessKey;
				}
			}
		});
	}});
}


function dateFormat(dateTime){
	if(dateTime ==null || dateTime == '' || dateTime == undefined){
		return '';
	}
	var date = new Date(dateTime);
	return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
}
var lamp1 = $("#Lamp1").val();
var lamp2 = $("#Lamp2").val();
var lamp3 = $("#Lamp3").val();
/**
 * 红绿灯Formate
 * @param cellvalue
 * @returns {String}
 */
function dateLampFormatter(cellvalue) {

	var color='';
	if (cellvalue < lamp1||cellvalue==null)
		return "";
	if (cellvalue < lamp2) {
		color='green';
	} else if (cellvalue < lamp3) {
		color='yellow';
	} else {
		color='red';
	}
	var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
	outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
	outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
	outDiv+='</div>';
	return outDiv;
}

function isRedFormatter(cellvalue) {

	var reStr='无';
	if (cellvalue =='1')
		reStr='有';
	return reStr;
}
