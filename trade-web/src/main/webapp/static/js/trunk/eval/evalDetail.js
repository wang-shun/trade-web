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
	//评估操作记录
	getOperateLogList(caseCode);
	//流程备注
	caseremarkList.init(ctx,'/quickGrid/findPage','evalCommenTable','evalCommenPager',caseCode);  // 显示各个流程的备注信息列表
	
	//CCAI附件
	getShowCCAIAttachment();
	//附件
	getShowAttachment();
	//业绩记录/收费情况查询
	queryPer();

});

function getOperateLogList(caseCode){
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
					argu_casecode : caseCode
				}

	});
}

/**
 * CCAI附件
 */
function getShowCCAIAttachment(){
	var caseCode = $("#caseCode").val();
	$("#gridTable").jqGrid({
		url : ctx+'/quickGrid/findPage',
		mtype : 'GET',
		datatype : "json",
		height : 250,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 5,
		colNames : [ '附件类型','附件名称','附件路径','上传时间','操作'],
		colModel : [ {
			name : 'FILE_CAT',
			index : 'FILE_CAT',
			align : "center",
			width : 120,
			resizable : false
		},{
			name : 'FILE_NAME',
			index : 'FILE_NAME',
			align : "center",
			width : 240,
			resizable : false
		}, {
			name : 'URL',
			index : 'URL',
			align : "center",
			width : 300,
			resizable : false
		}, {
			name : 'UPLOAD_TIME',
			index : 'UPLOAD_TIME',
			align : "center",
			width : 180,
			resizable : false
		},{
			name : 'READ',
			index : 'READ',
			align : "center",
			width : 180,
			resizable : false
		}],
		multiselect: true,
		pager : "#gridPager",
		viewrecords : true,
		pagebuttions : true,
		multiselect:false,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",
		gridComplete:function(){
			var ids = jQuery("#gridTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var rowData = jQuery("#gridTable").jqGrid('getRowData', ids[i]); // 获取当前行	    				
				var link = "<button  class='btn red' onclick='showAttachment(\""+rowData['URL']+"\")'>查看附件</a>";
				
				jQuery("#gridTable").jqGrid('setRowData', ids[i], { READ: link});
			}
		},
		postData : {
			queryId : "getCcaiAttachmentListByCaseCode",
			caseCode : caseCode
		}
		 
	});
}
function showAttachment(url){
	window.open(url);
}
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
 * 业绩记录/收费情况
 */
function queryPer(){
	var ccaiCode = $("#ctm").val();
	if(ccaiCode == null || ccaiCode == '' || ccaiCode == undefined){
		return;
	}
	var url =  "/case/getPricingAndFee?ccaiCode=" + ccaiCode;
	
	var templeteId1 = 'template_successList';
	var templeteId2 = 'template_successList2';
	var part1 = $("#table_content_pre");
	var part2 = $("#table_content_pre_partner");
	
	$.ajax({
		async: true,
		url:ctx+url ,
		method: "post",
		dataType: "json",
		success: function(da){
			var data = da.content;
			//业绩记录
			if(data.fees != null){
				var templateHtmlExec = "";
				var templateHtmlPart = "";
	      	  	if(data.fees.sharingInfo != null && data.fees.sharingInfo.length > 0){
	      	  		templateHtmlExec = template(templeteId1,data.fees);
	      	  	}else {
	      	  		var temp1 = {};
	      	  		var temp2 = {};
	      	  		temp1.type=1;
					temp2.type=2;
					if(data.fees.sharingInfo == null || data.fees.sharingInfo == undefined){
						data.fees.sharingInfo = new Array();
					}
	      		  	data.fees.sharingInfo.push(temp1);
	      		  	data.fees.sharingInfo.push(temp2);
	      		  	templateHtmlExec = template(templeteId1,data.fees);
	      	  	}
  	  			if(data.fees.cooperateFeeInfo !=null&&  data.fees.cooperateFeeInfo.length >0){
  	  				templateHtmlPart = template(templeteId2,data.fees);
  	  			}else{
  	  				var temp3 = {};
		      		if(data.fees.cooperateFeeInfo == null || data.fees.cooperateFeeInfo == undefined){
		      			data.fees.cooperateFeeInfo = new Array();
		      		}
		      		data.fees.cooperateFeeInfo.push(temp3);
		      		templateHtmlPart = template(templeteId2,data.fees);
  				}
	      	  
  	  			part1.empty();
  	  			part1.html(templateHtmlExec);
				part2.empty();
				part2.html(templateHtmlPart);
				$('#totalFee').html(data.fees.totalFee);
				$('#totalPerformance').html(data.fees.totalPerformance);
				$('#totalTurnoverNum').html(data.fees.totalTurnoverNum);
			}
			if(data.prices !=null){
				var prices = data.prices;
				$('#ownerReceivableCommission').html(prices.ownerReceivableCommission);
				$('#guestReceivableCommission').html(prices.guestReceivableCommission);
				$('#ownerCommissionTime').html(dateFormat(prices.ownerCommissionTime));
				$('#guestCommissionTime').html(dateFormat(prices.guestCommissionTime));
				$('#ownerCommissionDis').html(prices.ownerCommissionDis);
				$('#guestCommissionDis').html(prices.guestCommissionDis);
				$('#ownerCommissionMature').html(dateFormat(prices.ownerCommissionMature));
				$('#guestCommissionMature').html(dateFormat(prices.guestCommissionMature));
				$('#assessmentFee').html(prices.assessmentFee);
				$('#receivableAssessmentFee').html(prices.receivableAssessmentFee);
				$('#receiptsAssessmentFee').html(prices.receiptsAssessmentFee);
			}
		}		
      	 
	});
}

/**
 * 案件挂起
 */
function casePause(){
	var activityFlag = $("#activityFlag").val();
	var showStr = "";
	if(activityFlag == "30003003"){
		showStr = "您是否确认进行案件挂起操作？";
	}else if(activityFlag == "30003004"){
		showStr = "您是否确认进行案件恢复操作？";
	}
	
	window.wxc.confirm(showStr,{"wxcOk":function(){
		var url = "/case/casePause";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var params ='&caseCode=' + caseCode;
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data : params,
			success : function(data) {
				if(data.success){
					window.wxc.success("操作成功");
					$("#activityFlag").val(data.content);
					buttonActivity();
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}});
}

//挂起案件按钮toggle
function buttonActivity(){
	var activityFlag = $("#activityFlag").val();
	if(activityFlag == "30003003" || activityFlag == "30003007" || activityFlag == "30003008"){
		$('.btn-activity').show();
		$('#casePause').text("案件挂起");
		$('#casePause').show();
	}else{
		if(activityFlag=="30003004"){
			$('#casePause').text("案件恢复");
			$('#casePause').show();
			$('.btn-activity').hide();
		}else{
			$('.btn-activity').hide();
			$('#casePause').hide();
		}
	}
}

/**
 * 交易计划变更
 */
function showPlanModal(){
	resetPlanModal();
	$('#plan-modal-form').modal("show");
}
//重置交易计划
function resetPlanModal(){
	$("input[name='estPartTime']").val("");
	var url = "/case/getTransPlanByCaseCode";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : params,
		success : function(data) {

			var inHtml = "";
			$.each(data, function(k, v){
				inHtml+='<div class="form-group"><div class="col-lg-2 control-label">';
				inHtml+= '预计'+v.partName+'时间';
				inHtml+='</div><div class="col-lg-4 control-label" style="text-align:left; margin-top:-10px;" >';
				inHtml+='<input type="hidden" id="pkId_'+k+'" name="estId" value="'+v.pkid+'" >';
				inHtml+='<input type="hidden" id="isChange_'+k+'" name="estFlag" value="false" >';
				inHtml+='<span style="position: relative; z-index: 9999;">';
				inHtml+='<div class="input-group date"><span class="input-group-addon">';
				inHtml+='<i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>';
				inHtml+='<input class="form-control" type="text" id="estPartTime_'+k+'" name="estPartTime" value="'+v.estPartTimeStr+'" lang="' + v.estPartTimeStr + '" onchange="javascript:changeEstTime('+k+')">';
				inHtml+='</div>	</span></div>';
				inHtml+='<div class="col-lg-1 control-label">';
				inHtml+= '变更理由';
				inHtml+='</div><div class="col-lg-3 control-label" style="text-align:left; margin-top:-10px;" >';
				inHtml+='<input class="form-control" type="text" id="whyChange_'+k+'" name="whyChange" value="" onfocus="javascript:initBorderColor(this);">';
				inHtml+='</div>';
				inHtml+='</div>';

			});
			$("#plan-form").html(inHtml);
			 $('.input-group.date').datepicker({
				  todayBtn: "linked",
	                keyboardNavigation: false,
	                forceParse: false,
	                calendarWeeks: true,
	                autoclose: true
	            });
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});	
}
function changeEstTime(index){
	$("#isChange_"+index).val("true");
}
function initBorderColor(obj){
	$(obj).css("border-color","#e5e6e7");
}
//交易计划变更记录页面跳转
function openTransHistory(){
	var url = "/task/transPlan/showTransPlanHistory?";
	var ctx = $("#ctx").val();
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	url = ctx + url + params;
	window.location.href= url;
}
//交易计划变更 - 保存
function savePlanItems(){
	var url = "/case/savePlanItems";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var caseCode = $("#caseCode").val();
	var params ='&caseCode=' + caseCode;
	var isChanges = new Array;
	var estIds = new Array;
	var estTimes = new Array;
	var whyChanges = new Array;
	var msg = "";

	$("input:hidden[name='estFlag']").each(function(k) {
		if($(this).val() == 'true'){
			var whyChange = $("#whyChange_"+k).val();
			if(whyChange==""||whyChange.trim==""){
				msg = "请输入变更理由";
				return false;
			}
		}
		isChanges.push($(this).val());
	});
	
	var isChange = false;
	$("#plan-form input[name='estPartTime']").each(function(index){
		var newEstPartTime = this.value;
		var oldEstPartTime = $(this).attr("lang");
		
		if(newEstPartTime != oldEstPartTime){
			var reason = $("#whyChange_" + index).val();
			
			if(reason == ""){
				$("#whyChange_" + index).css("border-color","red");
				isChange = true;
				return false;
			}
		}
		
	});
	
	
	if(isChange){
		window.wxc.alert("变更理由为必填项！");
		return false;
	}
	
	$("#plan-form").find("input:text[name='estPartTime']").each(function(k) {
		if($(this).val()==""||$(this).val().trim==""){
			msg = "交易计划不允许为空";
			return false;
		}
		estTimes.push($(this).val());
	});
	$("input:hidden[name='estId']").each(function() {
		estIds.push($(this).val());
	});
	$("input:text[name='whyChange']").each(function() {
		whyChanges.push($(this).val());
	});
	if(msg!=""){
		window.wxc.alert(msg);
		return false;
	}
	params+="&isChanges="+isChanges+"&estIds="+estIds+"&estDates="+estTimes+"&whyChanges="+whyChanges;
	
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : params,
		beforeSend:function(){  
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        complete: function() {  
            $.unblockUI();   
            if(status=='timeout'){//超时,status还有success,error等值的情况
          	  Modal.alert(
			  {
			    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
			  });
	  		 $(".btn-primary").one("click",function(){
	  				parent.$.fancybox.close();
	  			});	 
	                }
	            } , 
		success : function(data) {
			if(data.success){
				window.wxc.success("提交成功",{"wxcOk":function(){
					window.location.reload();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

/**
 * 爆单
 */
function caseBaodan(){
	var ctx = $("#ctx").val();
	var url = ctx + "/case/caseBaodan";
	var caseCode = $('#caseCode').val();
	var data = "&caseCode="+caseCode; 
	window.wxc.confirm("确认对案件进行爆单？",{"wxcOk":function(){
		$.ajax({
			cache : false,
			async : true,
			type:"POST",
			URL:URL,
			dataType:"json",
			timeout : 10000,
			data : data,
			success : function(data) {
				if(data.success){
					window.wxc.success("爆单成功!");
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}});
	
}

/**
 * 变更交易助理
 */
function showChangeAssistantModal() {
	//查询该组别下所有交易助理
	var url = "/case/getAssistantInfo";
	var ctx = $("#ctx").val();
	url = ctx + url;
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		success : function(data) {
			changeAssistant(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

//变更交易助理
function changeAssistant(data) {
	var assistantName = $("#assistantUserName").val();
	var addHtml = '';
	addHtml+='<div class="row">';
	addHtml += '<div class="col-md-6 wd-50" style="width:100%">';
	addHtml += "<label class='col-md-3 control-label'>交易助理</label>";
	addHtml += "<div class=\"col-md-6\">";
	addHtml += "<select class='form-control m-b' id='assistantChange' name='assistantId'>";
	if(data.users!=null && data.users!=''){
		$.each(data.users, function(index, value){
			// 让修改后的复选框默认被选中
			if(assistantName==value.userRealName){
				addHtml += "<option value='"+value.userId+"' selected='selected'>"+value.userRealName+"</option>";
			}else{
				addHtml += "<option value='"+value.userId+"'>"+value.userRealName+"</option>";
			}
		});
	}
	addHtml += "</select>";
	addHtml += "</div></div>";
	addHtml += '</div>';
	addHtml += '</div>';
	$("#change-assistant-data-show").html(addHtml);
	$('#change-assistant-form').modal("show");
}
/**
 * 变更助理
 * 
 */
function changeAssistantInfo() {
	window.wxc.confirm("您是否确认进行助理变更？",{"wxcOk":function(){
		var assistantId = $("#assistantChange").val();
		var pkid = $("#pkidAssistant").val();
		var url = "/case/changeAssistant";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params = '&assistantId=' + assistantId + '&pkid=' + pkid;
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data : params,
			success : function(data) {
				if(data.success){
					window.wxc.success("变更成功");
					location.reload();
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}});
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
