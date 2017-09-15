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
	//附件
	getShowAttachment();
	//业绩记录
	queryPer();

});

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
		beforeSend: function () { 
			$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	  			$(".blockOverlay").css({'z-index':'9998'});
		},  
		success: function(data){
		  $.unblockUI();
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
      		  data.sharingInfo.push(temp1);
      		  data.sharingInfo.push(temp2);
      		  templateHtmlExec = template(templeteId1,data);
      	  }
      	  if(data.cooperateFeeInfo !=null&&  data.cooperateFeeInfo.length >0){
      		 templateHtmlPart = template(templeteId2,data);
      	  }else{
      		  var temp3 = {};
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
	var info='如果需要变更贷款需求,可以在过户之前使用\"贷款需求选择\"无需流程重启，是否继续重启流程？';
	if(!isNewFlow){
		info="点击该按钮将会启动流程重启审批流程，您确定要启动该流程吗？";
	}
	
	
	window.wxc.confirm(info,{"wxcOk":function(){
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
		   } , success:function(data){
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
 * 责任人变更初始化
 */
function showOrgCp() {
	//查询机构交易顾问
	var url = "/case/getUserOrgCpUserList";
	var ctx = $("#ctx").val();
	url = ctx + url;

	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data : "",
		success : function(data) {
			console.info(data);
			showLeadingModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
function imgLoad(img){
 	 img.parentNode.style.backgroundImage="url("+img.src+")";
}
/**
 * 选择交易顾问modal
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

/*贷款需求选择提交*/
function chgLoanReqment(){
	if(!chgLoanReqmentCheck()){
		return false;
	}
	
	var jsonData = $("#loan_reqment_chg_form").serializeArray();
	for(var i=0;i<jsonData.length;i++)
	{
		var item = jsonData[i];
		if(item["name"]=='partner' && (item["value"] == 0 || item["value"] == -1)){
			delete jsonData[parseInt(i)];
		}
	}
	
	
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/task/mortgageSelect/loanRequirementChange",
		dataType : "json",
		data : jsonData,
		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
         },
		success : function(data) {
			if(data.success){
				window.wxc.success("变更成功",{"wxcOk": function(){
					location.reload();
				}});
			}else{
				window.wxc.error(data.message);
			}
		},complete: function() { 
			 $.unblockUI(); 
		},
		error : function(errors) {
			window.wxc.error("数据保存出错");
			 $.unblockUI();
		}
	});
}

/*贷款需求选择校验*/
function chgLoanReqmentCheck() {
	var flag = false;
	$('select[id="cooperationUser0"] option:selected').each(function(i,item){
		if(item.value == "0"){
			window.wxc.alert("合作顾问为必选项!");
//				 item.focus();
			 flag = true;
			 return false;
		}else if(item.value=="-1"){
			if($('#partner0').find(':selected').val()=="0"){
				window.wxc.alert("请选择跨区合作顾问!");
			 flag = true;
			 return false;
			}
		}
	});
	if($('select[id="mortageService"] option:selected').val()=='2'&&$('select[id="cooperationUser0"]').size()==0){
		window.wxc.alert("正在加载合作项目!");
		 return false;
	}
	if($('#mortageService').val()!='0'&& $("#loan_reqment_chg_form").find('#estPartTime').val()==''){
		window.wxc.alert('请选择预计放款时间');
		return false;
	}
	if(flag)return false;
	return true;
}
/*贷款需求变更*/
function showLoanReqmentChgModal(){
	$("#mortageService").val("0");
	$("#hzxm").html('');
	$('#div_releasePlan').hide();
	$('#div_releasePlan .input-group.date').datepicker({
		todayBtn : "linked",
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true
	});
	$('#loanReqmentChg-modal-form').modal("show");
}
/*function fetchLoanReleasePlan(){
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : ctx+"/task/mortgageSelect/getLoanReleasePlan",
		dataType : "json",
		data : {"caseCode":$("#caseCode").val()},
		success : function(data) {
			if(data){
				$('#div_releasePlan .input-group.date').datepicker({
					todayBtn : "linked",
					keyboardNavigation : false,
					forceParse : false,
					autoclose : true,
					defaultDate:new Date(data.estPartTime)
				});
				//$("#estPartTime").val();
			}
		}
	});
	
}	*/


/*贷款需求选择*/
function mortageService() {
	var value = $("#mortageService").val();
	if(value!='0'){
		$("#loan_reqment_chg_form").find("#estPartTime").removeProp('disabled');
		$("#loan_reqment_chg_form").find("#estPartTime").removeAttr('disabled');
		$('#div_releasePlan').show();
	}else{
		//0 表示无贷款
		$("#loan_reqment_chg_form").find("#estPartTime").prop('disabled','disabled');//防止后台拿到数据
		$('#div_releasePlan').hide();
	}
	$("#hzxm").html("");
	if(value=='2'){
		var url = ctx+"/task/firstFollow/queryMortageByServiceCode";
		$.ajax({
			cache : false,
			async : true,//false同步，true异步
			type : "POST",
			url : url,
			dataType : "json",
			data : {"serviceCode":'3000400201'},
			success : function(data) {
				txt = "<div class='row'>";
			    txt += "<div class='col-xs-12 col-md-6'>";
			    txt += "<div class='form-group'  name='isYouXiao'>";
			    txt += "<label class='col-md-3 control-label text-left'>合作项目</label>";
			    txt += "<div class='col-md-7'>";
				txt += "<input type='hidden' name='coworkService' value='"+data.dic.dicCode+"'/>";
				txt += "<p id='' class='form-control-static'>"+data.dic.dictName+"</p>";
				txt += "</div>";
				txt += "</div>";
				txt += "</div>";
				txt += "<div class='col-xs-12 col-md-6'>";
				txt += "<div class='form-group' id='data_1' name='isYouXiao'>";
				txt += "<label class='col-md-5 control-label'><font color='red'>*</font>合作顾问</label>";
				txt += "<div class='col-md-7 pr0'>";
				txt += "<select class='form-control m-b' name='partner' id='cooperationUser0'>";
				txt += "<option value='0'>----未选择----</option>";
				$.each(data.users, function(j, user){
					txt += "<option value='"+user.id+"'>"+user.realName+"("+user.orgName+"):"+user.count+"件</option>";	
				});
				if($.trim(data.orgcode)!='033F045'){/*浦东合作顾问选中台 */
					txt += "<option value='-1'>---跨区选择---</option>";
				}
				txt += '</select></div></div>';
				txt += "</div>";
				txt += "</div>";
				$("#hzxm").append(txt);

				/*生成跨区合作下拉框*/
				var _partner = $('#cooperationUser0');
				_partner.bind('change', function(){
					var selectedVal = _partner.find(':selected').val();
					if(selectedVal=='-1'){
						if($('#mortage_corss_area').length==0){
							mortageCrossAreaCooperation();
						}
					}else{
						if($('#mortage_corss_area').length>0){
							mortageRemoveCrossAreaCooperation();
						}
					}					
				});
				},
			
			error : function(errors) {
				window.wxc.error("数据出错。");
			}
		});
	}
}

/*生成贷款需求跨区合作选项框*/
function mortageCrossAreaCooperation(){
	var corsstxt = "";
	corsstxt += "<div class='col-md-12 kuaquselect' id='mortage_corss_area'>";
	corsstxt += "<select name='partner' id='partner0'>";
	corsstxt += "<option value='0'>----人员----</option>";
	corsstxt += '</select>';
	corsstxt += "<select id='cross_org0'>";
	corsstxt += "<option value='0'>----组别----</option>";
	corsstxt += '</select>';				
	corsstxt += "<select id='cross_district0'>";
	corsstxt += "<option value='0'>----部门----</option>";
	corsstxt += '</select></div>';
	$("#hzxm").append(corsstxt);
	
	var ctx = $("#ctx").val();
	var url = ctx+"/task/firstFollow/getCrossAeraCooperationItems";
	$.ajax({
		cache : true,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		success : function(data) {
			
			/*三级联动*/
			var district = $('#cross_district0');
			var org = $('#cross_org0');
			var consult = $("#partner0");
			var districtStr="";
			
			$.each(data.cross,function(j,items){
				districtStr += "<option value='"+ items.districtId+"'>" + items.districtName+"</option>";
			});
			district.empty().append("<option value='0'>----部门----</option>"+districtStr);
			
			district.bind("change", function(){
				var orgStr="";
				var myIndex = district.find(":selected").index()-1;
				if(myIndex>=0){
					$.each(data.cross[myIndex].orgs, function(i, items){
						orgStr += "<option value='"+items.orgId+"'>"+items.orgName+"</option>";
					})
					org.empty().append("<option value='0'>----组别----</option>"+orgStr);
					var val1 = org.find(":selected").val();
					if(val1!='0'){
						changeConsult();
					}
				}else{
					org.empty().append("<option value='0'>----组别----</option>");
					consult.empty().append("<option value='0'>----人员----</option>");							
				}
			});
			
			org.bind("change", changeConsult);
			function changeConsult(){
				var consultStr="";
				var index1 = district.find(":selected").index()-1;
				var index2 = org.find(":selected").index()-1;
				if(index2>=0){
					$.each(data.cross[index1].orgs[index2].userItems, function(k,items) {
						consultStr += "<option value='"+items.id+"'>"+items.realName+"("+items.count+"件)</option>";
					});
					consult.empty().append("<option value='0'>----人员----</option>"+consultStr);
					if(consultStr == ""){
						consult.empty();
						consult.append("<option value='0'>----人员----</option>");
					}
				}else{
					consult.empty().append("<option value='0'>----人员----</option>");
				}
			}
		},
		error : function(errors) {
			window.wxc.error("数据出错。");
		}
	});
}

/*删除贷款需求跨区合作的DOM节点*/
function mortageRemoveCrossAreaCooperation(){
	$("#mortage_corss_area").remove();
}



/**
 * 变更合作对象
 */
function showChangeModal() {
	//查询机构交易顾问
	var url = "/case/changeCoopeRelation";
	var ctx = $("#ctx").val();
	var caseCode=$("#caseCode").val();
	url = ctx + url;

	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout : 10000,
		data :[{
			name:'caseCode',
			value:caseCode
		}],
		success : function(data) {
			ChangeModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

//变更合作对象
function ChangeModal(data) {
	var addHtml = '';
	var aa=0;
	$.each(data.servitemList, function(index, value){
		addHtml+='<div class="row">';
		addHtml += '<div class="col-md-6 wd-50">';
		addHtml += '<div class="form-group mr0">';
			if(value.users !=""&&value.users.length!=0){
				addHtml += "<input type='hidden' name='caseCode' value='"+$('#caseCode').val()+"' />";
				addHtml += "<input type='hidden' name='srvCode' value='"+value.srvCode+"'/>";
			}
			addHtml += "<label class='col-md-3 control-label wd87'>合作项目</label>";
			addHtml += "<div class='col-md-9 wd-64'><p class='form-control-static'>"+value.srvName+"</p></div>"
		addHtml += '</div></div>';
		
		addHtml += '<div class="col-md-6 wd-50">';
			if(value.users !=""&&value.users.length!=0){
				addHtml += "<label class='col-md-3 control-label'>合作顾问</label>";
			}
		addHtml += "<div class=\"col-md-9\">";
		if(value.users !=""&&value.users.length!=0){
			addHtml += "<select class='form-control m-b' id='userChange"+index+"' name='myProcessorId'>";
			aa=index;
			var oldOrgId='';
			var preProcessorId = '';
			$.each(value.users, function(j, value){
				// 让修改后的复选框默认被选中
				if(data.servitemList[aa].processorId==value.id){
					addHtml += "<option value='"+value.id+"' selected='selected'>"+value.realName+"("+value.orgName+")"+"</option>";
				}else{
					addHtml += "<option value='"+value.id+"'>"+value.realName+"("+value.orgName+")"+"</option>";
				}
				preProcessorId = value.id;
				oldOrgId=value.orgId;
			});
			//if(data.orgcode!='033F045'){/*浦东合作顾问选中台*/
				addHtml += "<option value='-1'>---跨区选择---</option>";
			//}
			addHtml += "</select>";
			addHtml += "<input type='hidden' name='orgId' id='org"+index+"' value='"+value.orgId+"'/>";
			addHtml += "<input type='hidden'  id='processorId"+index+"' name='processorId' value=''/>";
			addHtml += "<input type='hidden' name='oldOrgId' id='oldOrg"+index+"' value='"+oldOrgId+"'/>";
			addHtml += "<input type='hidden' name='otherProcessorId' id='otherProcessorId"+index+"' value='"+data.servitemList[index].processorId+"'/>";
			addHtml += "<input type='hidden' name='otherOrgId' id='otherOrgId"+index+"' value=''/>";
			addHtml += "<input type='hidden' name='preProcessorId' value='"+data.servitemList[index].processorId+"' />";
		}else{
			
		}
		addHtml += "</div></div>";
		addHtml += '</div>';
		addHtml += '</div>';
		
	});
	
	$("#change-modal-data-show").html(addHtml);

	$.each(data.servitemList, function(index, value){
		
		if(value.users !=""&&value.users.length!=0){
			$('#processorId'+index).val(data.servitemList[index].processorId); //赋值processorId的值
			
			var isNeedDefualt = true;
			$.each(value.users, function(j, value){
				if(data.servitemList[index].processorId==value.id){
					isNeedDefualt = false;
				}
			});
			
			if(isNeedDefualt) {
				var url = "/user/getUserInfo";
				var ctx = $("#ctx").val();
				var otherProcessorId=$("#otherProcessorId"+index).val();
				url = ctx + url;

				$.ajax({
					cache : false,
					async : true,
					type : "POST",
					url : url,
					dataType : "json",
					timeout : 10000,
					data :[{
						name:'userId',
						value:otherProcessorId
					}],
					success : function(data) {
						var newOption='<option value='+otherProcessorId+' selected="selected">'+data.realName+'('+data.orgName+')'+'</option>';
						$('#userChange'+index).append(newOption);
						$('#otherOrgId'+index).val(data.orgId);
						$('#org'+index).val(data.orgId);					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
			}
		}
    });

	$('.change-box').each(function() {
		animationHover(this, 'pulse');
	});
	$('#change-modal-form').modal("show");
}


/*点击生成或清除合作顾问下拉框*/
$(document).on("change",'select[name="myProcessorId"]',function(){
	var pros=$('select[name="myProcessorId"]');
	$.each(pros,function(i,items){
		var parent = $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-9').parent('.col-md-6').parent('.row');
		var p1= $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-9');
		var org = p1.children(':hidden:eq(0)');
		var oldOrg = p1.children(':hidden:eq(2)');
		var consult = p1.children(':hidden:eq(1)');
		var otherConsult = p1.children(':hidden:eq(3)');
		var otherOrg = p1.children(':hidden:eq(4)');
		if($('select[name="myProcessorId"]:eq('+i+')').find(":selected").val()=='-1'){
			if($("#corss_area"+i).length==0){
				var corsstxt="";
				corsstxt += "<div class='col-md-12 wd445' id='corss_area"+i+"'>";
				corsstxt += "<select name='crossProcessorId' id='crossConsult"+i+"'>";
				corsstxt += "<option value='0'>----人员----</option>";
				corsstxt += '</select>';
				corsstxt += "<select name='crossOrgId' id='crossOrg"+i+"'>";
				corsstxt += "<option value='0'>----组别----</option>";
				corsstxt += '</select>';				
				corsstxt += "<select id='crossDistrict"+i+"'>";
				corsstxt += "<option value='0'>----部门----</option>";
				corsstxt += '</select></div>';
				parent.append(corsstxt);
				crossAreaCooperation(i);
			}
		}else if($('select[name="myProcessorId"]:eq('+i+')').find(":selected").val()==otherConsult.val()){
			org.val(otherOrg.val());
			consult.val(otherConsult.val());
			if($("#corss_area"+i).length>0){
				removeCrossAreaCooperation(i);
			}				
		}else{
			org.val(oldOrg.val());
			consult.val($('select[name="myProcessorId"]:eq('+i+')').find(":selected").val());
			if($("#corss_area"+i).length>0){
				removeCrossAreaCooperation(i);
			}					
		}	
	});
});

/*生成跨区合作选项框*/
function crossAreaCooperation(i){
	
	var ctx = $("#ctx").val();
	var url = ctx +"/task/firstFollow/getCrossAeraCooperationItems";
	
	$.ajax({
		cache : true,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		success : function(data) {
			
			/*三级联动*/
			var district = $('#crossDistrict'+i);
			var org = $('#crossOrg'+i);
			var consult = $("#crossConsult"+i);
			var districtStr="";
			
			$.each(data.cross,function(j,items){
				districtStr += "<option value='"+ items.districtId+"'>" + items.districtName+"</option>";
			});
			district.empty().append("<option value='0'>----部门----</option>"+districtStr);
			
			district.bind("change", function(){
				var orgStr="";
				var myIndex = district.find(":selected").index()-1;
				if(myIndex>=0){
					$.each(data.cross[myIndex].orgs, function(l, items){
						orgStr += "<option value='"+items.orgId+"'>"+items.orgName+"</option>";
					})
					org.empty().append("<option value='0' selected='selected'>----组别----</option>"+orgStr);
					var val1 = org.find(":selected").val();
					if(val1!='0'){
						changeConsult();
					}
				}else{
					org.empty().append("<option value='0'>----组别----</option>");
					consult.empty().append("<option value='0'>----人员----</option>");					
				}
			});
			
			org.bind("change", changeConsult);
			function changeConsult(){
				var consultStr="";
				var index1 = district.find(":selected").index()-1;
				var index2 = org.find(":selected").index()-1;
				if(index2>=0){
					$.each(data.cross[index1].orgs[index2].userItems, function(k,items) {
						consultStr += "<option value='"+items.id+"'>"+items.realName+"("+items.count+"件)</option>";
					});
					consult.empty().append("<option value='0'>----人员----</option>"+consultStr);
					if(consultStr == ""){
						consult.empty();
						consult.append("<option value='0'>----人员----</option>");
					}
				getVals();
				}else{
					consult.empty().append("<option value='0'>----人员----</option>");
				}
			}
			
			consult.bind("change", getVals);
			/*改变隐藏框的值*/
			function getVals(){
				var guwen=consult.find(':selected').val();
				var zuzhi=org.find(':selected').val();
				
				if(guwen!='0'){
					 $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-9').children(':hidden:eq(0)').val(zuzhi);
					 $('select[name="myProcessorId"]:eq('+i+')').parent('.col-md-9').children(':hidden:eq(1)').val(guwen);
				}
			}
			
		},
		error : function(errors) {
			window.wxc.error("数据出错。");
		}
	});
}

/*删除跨区合作的DOM节点*/
function removeCrossAreaCooperation(i){
	$("#corss_area"+i).remove();
}

/*跨区合作表单校验*/
function check(){
	var crossAreas= $('.wd445');
	if(crossAreas.length==0){
		return true;
	}else if(crossAreas.length>0){
		$.each(crossAreas, function(i,items){
			 var crossProcessorId = $('.wd445:eq('+i+')').children('select[name="crossProcessorId"]').find(':selected').val();
			if(crossProcessorId=='0'){
				window.wxc.alert("跨区合作交易顾问不能为空!");
				return false;
			}
		});
	}
	return true;
}

/*提交跨区合作表单*/
function submit_change(){
	if(check()){
		$('#changeCooprations').submit();
	}
}



/**
 * 变更责任人
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
 * 产调发起
 * 
 */
function startCasePrairses() {
	window.wxc.confirm("您是否确认进行产调发起？",{"wxcOk":function(){
		var url = "/case/startCasePrairses";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var prItems = new Array;
		$("input[name='pr_item']:checked").each(function() {
			prItems.push(this.value);
		});
		if(prItems.length == 0){
			window.wxc.alert("请至少选择一项");
			return;
		}
		var params ='&caseCode=' + caseCode+ '&prItems=' + prItems;

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
						$('#pr-modal-form').modal("hide");
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}});
}
function managerShowSrvModal(){
	resetSrvModal();
	$('#srv-modal-form').modal("show");
}	
	
//初始化服务项
function showSrvModal(){
	resetSrvModal();
	$('#srv-modal-form').modal("show");
}

/*cyx
 * var srvs;
//初始化服务项
function resetSrvModal(){
	$("input:checkbox[name='srvCode']").removeAttr("checked");
	srvs = $("#srvCodes").val();
	var reHtml= "";
	var srvDicts = $("input:checkbox[name='srvCode']").val();
	if(srvs!=null){
		$.each(srvs.split(","), function(k, v){
			$("input:checkbox[name='srvCode']").each(function() {
				if(this.value==v){
					$(this).prop("checked", true);
					var s = $(this).parent().parent().parent().text();
					reHtml+=s;
					reHtml+=" ";
				}
			});
	  });
	}
	$("#oldSrvs").html(reHtml);
	changeSrvs();
}

//变更服务项
function changeSrvs(){
	var reHtml= "";
	$("input:checkbox[name='srvCode']:checked").each(function() {
		var s = $(this).parent().parent().parent().text();
		reHtml+=s;
		reHtml+=" ";
	});
	$("#selectedSrvs").html(reHtml);
}*/

var resSrvs = [ '30004001', '30004002'];  //商贷、公积金贷款  流程重启
var delSrv='30004010'; //交易过户后台组服务  直接爆单
//保存服务项
function saveSrvItems(){
	window.wxc.confirm("您是否确认进行服务项变更？",{"wxcOk":function(){
		var isDel = false;
		var delSrvCheck = $("input[name='srvCode'][value="+delSrv+"]").prop('checked');
		//srvs 根据caseCode查询的有哪些服务项
		if(srvs.indexOf(delSrv)>-1 && !delSrvCheck){  //查询返回下标，从0开始， 
			//原先有商贷、现在没有勾选
			isDel=true;
		}else if(delSrvCheck && srvs.indexOf(delSrv)==-1){
			//现在已勾选商贷服务、原先没有商贷
			isDel=true;
		}

		var isRes = false;
		var resSrvCheck1 = $("input[name='srvCode'][value="+resSrvs[0]+"]").prop('checked');
		var resSrvCheck2 = $("input[name='srvCode'][value="+resSrvs[1]+"]").prop('checked');
		if(resSrvCheck1 && resSrvCheck2){
			window.wxc.alert("商贷/组合贷和纯公积金贷只允许存在一种！");
			return;
		}else if(resSrvCheck1 && srvs.indexOf(resSrvs[0])==-1){
			isRes = true;
		}else if(!resSrvCheck1 && srvs.indexOf(resSrvs[0])>0){
			isRes = true;
		}else if(resSrvCheck2 && srvs.indexOf(resSrvs[1])==-1){
			isRes = true;
		}else if(!resSrvCheck2 && srvs.indexOf(resSrvs[1])>0){
			isRes = true;
		}
		var url = "/case/saveSrvItems";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var prItems = new Array;
	
		$("input[name='srvCode']:checked").each(function() {
			prItems.push(this.value);
		});
		var params ='&caseCode=' + caseCode+ '&prItems=' + prItems+ '&isDel=' + isDel+ '&isRes=' + isRes+"&srvs="+srvs;
		var confirmStr = "您的选择会进行流程重启，是否继续？";
		if(isDel)confirmStr = "您的选择会进行案件爆单操作，是否继续？";
		
		if(!isRes && !isDel){
			serviceChangeApply(params,url);
		}
		else {
			window.wxc.confirm(confirmStr,{"wxcOk":function(){
				serviceChangeApply(params,url);
			}});
		}
	}});
}

function serviceChangeApply(params,url){
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

				if(data.message!="变更成功！"){
					window.location.reload();
					window.location.href=ctx+"/task/ServiceChangeApply?&caseCode="+caseCode +"&taskId="+data.content;
				}else{
					window.wxc.success("提交成功！",{"wxcOk":function(){
						window.location.reload();
						changeSrvsHidden();
						$('#srv-modal-form').modal("hide");
					}});
				}
			}else{
				window.wxc.error(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function saveSrvItemsForManager(){
		var resSrvCheck1 = $("input[name='srvCode'][value="+resSrvs[0]+"]").prop('checked');
		var resSrvCheck2 = $("input[name='srvCode'][value="+resSrvs[1]+"]").prop('checked');
		if(resSrvCheck1 && resSrvCheck2) {
			window.wxc.alert("商贷和纯公积金贷只允许存在一种！");
			return;
		}

		var url = "/case/saveSrvItemsForManager";
		var ctx = $("#ctx").val();
		url = ctx + url;
		var caseCode = $("#caseCode").val();
		var prItems = new Array;
		$("input[name='srvCode']:checked").each(function() {
			prItems.push(this.value);
		});
		var params ='&caseCode=' + caseCode+ '&prItems=' + prItems+"&srvs="+srvs;
		var confirmStr = "您目前在更新服务状态,，是否继续？";
		
		window.wxc.confirm(confirmStr,{"wxcOk":function(){
			$.ajax({
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
						window.wxc.success(data.message);
						window.location.reload();
					}else{
						window.wxc.error(data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}});
	}




function changeSrvsHidden(){
	var url = "/case/getSrvsByCaseCode";
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
				$("#srvCodes").val(data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}











//清空表单
function checkAllPRItem() {
	$("input[name='pr_item']").prop("checked", true);
}
//清空表单
function cleanPRItem() {
	$("input[name='pr_item']").removeAttr("checked");
}

function startSpvOutApplyProcess(caseCode){
	window.wxc.confirm("点击该按钮将会启动资金监管解除审批流程，您确定要启动该流程吗？",{"wxcOk":function(){
		$.ajax({
			url:ctx+"/spv/startSpvOutApplyProcess",
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
		  		 $(".btn-primary").one("click",function(){
		  				parent.$.fancybox.close();
		  			});	 
		                }
		            } , 
			success:function(data){
                $.unblockUI();   
				if(!data.success){
					window.wxc.error(data.message);
				}else{
					window.location.href=ctx+"/task/spvOutApply?taskId="+data.content.taskId+"&instCode="+data.content.processInstanceId+"&caseCode="+data.content.caseCode;
				}
			}
		});
	}});
}
/**
 * 案件转组初始化
 
function caseChangeTeam(){
	var url = "/case/getOrgTeamList";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	$.ajax({
		cache : false,
		async:true,
		type : "POST",
		url : url,
		dataType : "json",
		timeout: 10000,
	    data : "", 
		success : function(data) {
			showTeamModal(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	}); 
}
/**
 * 选择组别modal
 * @param data
 */
function showTeamModal(data){
	var inHtml = '';
	inHtml+='<div class="form-group"><label class="col-lg-3 control-label">';
	inHtml+= '请选择组别：';
	inHtml+='</label><div class="col-lg-9" style="text-align:left; margin-top:-10px;" >';
	$.each(data,function(i, n){
		inHtml+='<div class="checkbox i-checks"><label>';
		inHtml+='<input type="radio" name="teamRadio" value="'+n.id+'"/>  '+n.orgName+' </label></div>';
	})
	inHtml+='</div></div>';
	$("#team-form").html(inHtml);
	$('#team-modal-form').modal("show");
}
//我要修改显示弹框
function showChangeFormModal(){
	$('#changeForm-modal-form').modal("show");
}
/**
 * 案件转组
 * @param index
 */
function changeCaseTeam(){
	var orgName =$('input[name="teamRadio"]:checked').parent().text();
	
	window.wxc.confirm("您是否确认分配给"+orgName+"?",{"wxcOk":function(){
    	var orgId =$('input[name="teamRadio"]:checked').val();
		var url = "/case/orgChange";
		var ctx = $("#ctx").val();
		url = ctx + url;
		
		var params='&orgId='+orgId+'&caseCode='+$("#caseCode").val();
		
		$.ajax({
			cache : false,
			async:true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout: 10000,
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
					window.wxc.success("转组成功");
					$('#team-modal-form').modal("hide");
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}); 
	}});
}
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


function caseReset(){
	window.wxc.confirm("您的操作将恢复案件至未分单状态，是否确定要重置案件？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		$.ajax({
			url:ctx+"/case/reset",
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
		   } , success:function(data){
				if(!data.success){
					$.unblockUI();   
					window.wxc.error(data.message);
				}else{
					window.wxc.success("案件重置成功.");
					window.location.reload();
				}
			}
		});
	}});
}

/**
 * 修改外单
 * hejf
 * 2017年4月24日16:10:46
 */
function editWdCase(){
	var caseCode = $("#caseCode").val();
	window.location.href = ctx+"/caseMerge/editWdCase"+'?caseCode=' + caseCode;
}
/**
 * 删除流水
 * hejf
 * 2017年6月9日17:56:12
 */
function delLiushui( pkid){
	
	window.wxc.confirm("您是否确认删除？",{"wxcOk":function(){

	var url = "/caseMerge/delLiushui";
	var ctx = $("#ctx").val();
	url = ctx + url;
	var params ='&pkid=' + pkid;
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
				window.wxc.alert(data.message);
				window.location.reload();
			}else{
				window.wxc.error("删除失败！"+data.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
	
	}});
}






