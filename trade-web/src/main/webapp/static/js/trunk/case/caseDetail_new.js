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
	//CCAI附件
	getShowCCAIAttachment();
	//附件
	getShowAttachment();
	//业绩记录
//	queryPer();

});

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
			width : 20,
			resizable : false
		},{
			name : 'FILE_NAME',
			index : 'FILE_NAME',
			align : "center",
			width : 20,
			resizable : false
		}, {
			name : 'URL',
			index : 'URL',
			align : "center",
			width : 20,
			resizable : false
		}, {
			name : 'UPLOAD_TIME',
			index : 'UPLOAD_TIME',
			align : "center",
			width : 20,
			resizable : false
		},{
			name : 'READ',
			index : 'READ',
			align : "center",
			width : 20,
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
 * 业绩记录
 */
function queryPer(){
	var data={};
	var ccaiCode = $("#ctm").val();
	if(ccaiCode == null || ccaiCode == '' || ccaiCode == undefined){
		return;
	}
	data.ccaiCode = ccaiCode;
//	var url =  "/quickGrid/findPage";
	
	var templeteId1 = 'template_successList';
	var templeteId2 = 'template_successList2';
	var part1 = $("#table_content_pre");
	var part2 = $("#table_content_pre_partner");
	
	/*var data = {};
	var sharingInfo = new Array();
	var obj1 = {};
	obj1.type = 1;
	obj1.department="部门1";
	obj1.employee="员工1";
	obj1.sharingAmount=50000;
	obj1.sharingProportion=0.1;
	obj1.sharingInstruction="说明1";
	obj1.turnoverNum=2;
	var obj2 = {};
	obj2.type=2;
	obj2.department="部门1";
	obj2.employee="员工1";
	obj2.sharingAmount=50000;
	obj2.sharingProportion=0.1;
	obj2.sharingInstruction="说明1";
	obj2.turnoverNum=2;
	sharingInfo.push(obj1);
	sharingInfo.push(obj2);
	data.sharingInfo=sharingInfo;
	
	var cooperateFeeInfo = new Array();
	var obj3 = {};
	obj3.cooperateFeeType="合作费类型";
	obj3.sharingAmount=2000;
	obj3.sharingProportion=0.02;
	obj3.partner="part";
	obj3.cooperateDepartment="部门";
	obj3.cooperateManager="我是经理";
	var obj4 = {};
	obj4.cooperateFeeType="合作费类型";
	obj4.sharingAmount=2000;
	obj4.sharingProportion=0.02;
	obj4.partner="part";
	obj4.cooperateDepartment="部门";
	obj4.cooperateManager="我是经理";
	cooperateFeeInfo.push(obj3);
	cooperateFeeInfo.push(obj4);
	data.cooperateFeeInfo=cooperateFeeInfo;
	data.totalFee=1000000;
	data.totalPerformance=100000;
	data.totalTurnoverNum= 10;*/
	$.ajax({
		async: true,
		url:ctx+url ,
		method: "post",
		dataType: "json",
		data: data, 
		success: function(data){
      	  var execData = new Array();
      	  var partner = new Array();
      	  var exec,partn,fc=false;
      	  if(data.sharingInfo != null && data.sharingInfo.length > 0){
      		templateHtmlExec = template(templeteId1,data);
      	  }else {
      		  var temp1 = {};
      		  var temp2 = {};
      		  temp1.type=1;
      		  temp2.type=2;
      		  if(data.sharingInfo == null || data.sharingInfo == undefined){
      			data.sharingInfo = new Array();
      		  }
      		  data.sharingInfo.push(temp1);
      		  data.sharingInfo.push(temp2);
      		  templateHtmlExec = template(templeteId1,data);
      	  }
      	  if(data.cooperateFeeInfo !=null&&  data.cooperateFeeInfo.length >0){
      		 templateHtmlPart = template(templeteId2,data);
      	  }else{
      		  var temp3 = {};
      		if(data.cooperateFeeInfo == null || data.cooperateFeeInfo == undefined){
      			data.cooperateFeeInfo = new Array();
      		  }
      		  data.cooperateFeeInfo.push(temp3);
      		  templateHtmlPart = template(templeteId2,data);
      	  }
      	  
      	  part1.empty();
      	  part1.html(templateHtmlExec);
      	  part2.empty();
    	  part2.html(templateHtmlPart);
    	  $('#totalFee').html(data.totalFee);
    	  $('#totalPerformance').html(data.totalPerformance);
    	  $('#totalTurnoverNum').html(data.totalTurnoverNum);
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
 * 流程重启
 */
function serviceRestart(){
	window.wxc.confirm("确定重启流程？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/service/restart",
			method:"post",
			dataType:"json",
			data:{caseCode:caseCode},
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
			   console.log("===Result==="+JSON.stringify(data));
				if(!data.success){
					$.unblockUI();   
					window.wxc.error(data.message);
				
				}else{
					window.location.href=ctx+"/task/serviceRestartApply?taskId="+data.content.activeTaskId+"&instCode="+data.content.id+"&caseCode="+caseCode;
				}
			}
		});
	}});
}


/**
 * 权证变更
 */
function showOrgCp() {

	var url = "/case/getUserOrgCpUserList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var data={operation:""};
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : data,
		success : function(data) {
			console.info(data);
			showLeadingModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

/**
 * 选择权证modal
 * @param data
 */
function showLeadingModal(data) {
	var addHtml = '';
	var ctx = $("#ctx").val();
	$.each(data,function(i, n) {
						addHtml += '<div class="col-lg-4"><div class="contact-box">';
						addHtml += '<a href="javascript:changeLeadingUser(' + i
								+ ')">';
						addHtml += '<div class="col-sm-4"><div class="text-center">';
						addHtml+='<span class="userHead">';
						if(n.imgUrl!=null){
							addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'">';
						}
						addHtml+='</span>';
						addHtml += '<div class="m-t-xs font-bold">交易顾问</div></div></div>';
						addHtml += '<div class="col-sm-8">';
						addHtml += '<input id="user_' + i
								+ '" type="hidden" value="' + n.id + '">';
						addHtml += '<h3><strong>' + n.realName
								+ '</strong></h3>';
						addHtml += '<p>联系电话：' + n.mobile + '</p>';
						addHtml += '<p>当前单数：' + n.userCaseCount + '</p>';
						addHtml += '<p>本月接单：' + n.userCaseMonthCount + '</p>';
						addHtml += '<p>未过户单：' + n.userCaseUnTransCount + '</p>';
						addHtml += '</div><div class="clearfix"></div></a>';
						addHtml += '</div></div>';
					})
	$("#leading-modal-data-show").html(addHtml);

	$('.contact-box').each(function() {
		animationHover(this, 'pulse');
	});
	$('#leading-modal-form').modal("show");
}
/**
 * 变更权证
 * @param index
 */
function changeLeadingUser(index) {
	window.wxc.confirm("您是否确认进行责任人变更？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		var instCode = $("#instCode").val();
		var userId = $("#user_" + index).val();

		var url = "/case/changeLeadingUser";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var params = '&userId=' + userId + '&caseCode=' + caseCode+"&instCode="+instCode;

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
 * 询价申请
 */
function evaPricingApply(){
	var info = "系统已存在与此案件相关的询价记录，是否关联？";
	var caseCode = $('#caseCode').val();
	var url = ctx+'/case/checkEvaPricing?caseCode='+caseCode;
	$.ajax({
		cache : false,
		async : true,
		url:url,
		type:'POST',
		dataType:'json',
		success : function(data) {
			if(data.success){
				if(data.content){
					window.wxc.confirm(info,{'wxcOk':function(){
						
					},'wxcCancel':function(){
						//新增询价
						var ctx = $("#ctx").val();
						window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
					}});
				}else{
					var ctx = $("#ctx").val();
					window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
				}
			}else{
				window.wxc.error(data.message);
			}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}
