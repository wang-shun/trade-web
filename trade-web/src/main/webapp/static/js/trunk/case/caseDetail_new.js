/**
 * 案件详情
 * @author wbcaiyx
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
   /* $("#mortageService").change(function(){
        //mortageService();
    });*/
    //CCAI附件
    getShowCCAIAttachment();
    //附件
    getShowAttachment();
    //业绩记录/收费情况查询
    queryPer();
    
});

$("#btn_loan_reqment_chg").click(chgLoanReqment);
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
    if(activityFlag == "30003003"){
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
//重置交易计划 by wbzhouht
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
            if (data != null && data != "" && data != undefined) {
                var inHtml = "";
                $("#plan-form").html(inHtml);
                console.log(data);
                $.each(data, function (k, v) {
                    inHtml += '<div class="form-group"><div class="col-lg-2 control-label">';
                    inHtml += '预计' + v.partName + '时间';
                    inHtml += '</div><div class="col-lg-4 control-label" style="text-align:left; margin-top:-10px;" >';
                    inHtml += '<input type="hidden" id="pkId_' + k + '" name="estId" value="' + v.pkid + '" >';
                    inHtml += '<input type="hidden" id="isChange_' + k + '" name="estFlag" value="false" >';
                    inHtml += '<span style="position: relative; z-index: 9999;">';
                    inHtml += '<div class="input-group date" ><span class="input-group-addon" >';
                    inHtml += '<i class="fa fa-calendar" style="z-index:2100;position:relative;" ></i></span>';
                    inHtml += '<input class="form-control" type="text" id="estPartTime_' + k + '" name="estPartTime" value="' + v.estPartTimeStr + '" lang="' + v.estPartTimeStr + '" onchange="javascript:changeEstTime(' + k + ')">';
                    inHtml += '</div>	</span></div>';
                    inHtml += '<div class="col-lg-1 control-label">';
                    inHtml += '变更理由';
                    inHtml += '</div><div class="col-lg-3 control-label" style="text-align:left; margin-top:-10px;" >';
                    inHtml += '<input class="form-control" type="text" id="whyChange_' + k + '" name="whyChange" value="" onfocus="javascript:initBorderColor(this);">';
                    inHtml += '</div>';
                    inHtml += '</div>';

                });
                $("#plan-form").html(inHtml);
                $.each(data, function (k1, v1) {
                    if (!v1.edit) {
                        $("#estPartTime_" + k1).prop("disabled", "disabled");
                        $("#whyChange_" + k1).attr("disabled", "disabled");
                    }
                })
                $('.input-group.date input').datepicker({
                    todayBtn: "linked",
                    keyboardNavigation: false,
                    forceParse: false,
                    calendarWeeks: false,
                    autoclose: true
                });
            }else {
                window.wxc.error("请先填写交易计划变更！",{"wxcOk":function () {
                    $('#plan-modal-form').modal("hide");
                }})
            }
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
//交易计划变更 - 保存 by wbzhouht
function savePlanItems(){
    var isAudit=auditResult;
    if(isAudit){
        window.wxc.error("你已提交过变更，请等待审核！",function () {
            window.location.reload();
        })
        return;
    }
    var url = "/case/startTransPlan";
    var ctx = $("#ctx").val();
    url = ctx + url;
    var caseCode = $("#caseCode").val();
    var params ='&caseCode=' + caseCode+"&partCode="+partCode;
    var isChanges = new Array;
    var estIds = new Array;
    var estTimes = new Array;
    var whyChanges = new Array;
    var msg = "";
    $("input:hidden[name='estFlag']").each(function(k) {
        var pkid=$("#pkId_"+k).val();
        var whyChange=$("#whyChange_"+k).val();
        var newEstPartTime=$("#estPartTime_"+k).val();
        if($(this).val() == 'true'){
            var whyChange = $("#whyChange_"+k).val();
            if(whyChange==""||whyChange.trim==""){
                msg = "请输入变更理由";
                return false;
            }
            isChanges.push($(this).val());
            estTimes.push(newEstPartTime);
            estIds.push(pkid);
            whyChanges.push(whyChange);
        }
    });

    var isChange = false;
    $("#plan-form input[name='estPartTime']").each(function(index){
        var newEstPartTime = this.value;
        var oldEstPartTime = $(this).attr("lang");
        console.log(oldEstPartTime)
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

	/*$("#plan-form").find("input:text[name='estPartTime']").each(function(k) {
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
	 });*/
    if(msg!=""){
        window.wxc.alert(msg);
        return false;
    }
    params+="&isChanges="+isChanges+"&estIds="+estIds+"&estDates="+estTimes+"&whyChanges="+whyChanges;
    console.log(params)
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
                    window.location.href = ctx+"/task/myTaskList";
                    //window.location.reload();
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
	
	window.wxc.confirm("确定案件爆单？",{"wxcOk":function(){
		var caseCode = $("#caseCode").val();
		var data = "&caseCode="+caseCode; 
		$.ajax({
			url:ctx+"/caseStop/initCheck",
			method:"post",
			dataType:"json",
			data:data,
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                if(status=='timeout'){
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  });
		        }
		   } , 
		   success:function(data){
			   if(data.success){
				   	window.location.href=ctx+"/caseStop/apply/process?taskId="+data.content.activeTaskId
				   						+"&instCode="+data.content.id+"&caseCode="+caseCode+"&type=1";
			   }else{
				   $.unblockUI();   
					window.wxc.error(data.message);
			   }
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

//选择的权证类型
var chooseType;
//选择过户or贷款权证modal
function showChoose(){
	//案件挂起，无法操作工作流参数
	var activityFlag = $("#activityFlag").val();
	if(activityFlag == "30003004"){
        window.wxc.alert("挂起案件无法变更经办人");
        return;
    }
	
	chooseType = null;
	var warrant = $('#warr').html();
	var loan = $('#loan').html();

	var addHtml = '<div class="col-lg-12">';
	if((warrant && warrant.trim().length > 0) && (loan && loan.trim().length > 0)){	
		addHtml += '<a href="javascript:showOrgCp(1)"><div class="contact-box change_width_left">贷款权证</div></a>';
		addHtml += '<a href="javascript:showOrgCp(2)"><div class="contact-box change_width_left">过户权证</div></a>';

	}else if(loan && loan.trim().length > 0){
		addHtml += '<a href="javascript:showOrgCp(1)"><div class="contact-box change_width_left">贷款权证</div></a>';
	}else if(warrant && warrant.trim().length > 0){
		addHtml += '<a href="javascript:showOrgCp(2)"><div class="contact-box change_width_left">过户权证</div></a>';
	}else{
		window.wxc.alert("案件无过户或贷款权证");
		return;
	}
	addHtml += '</div>';
	
	$("#leading-choose").empty();
	$("#leading-choose").html(addHtml);

    $('.contact-box').each(function() {
        animationHover(this, 'pulse');
    });
    $('#lead-modal-choose').modal("show");
}



/**
 * 权证变更
 */
function showOrgCp(param) {
	
	$('#lead-modal-choose').modal("hide");
	if(param == 1){
		chooseType = "loan";
	}else if(param == 2){
		chooseType = "warrant";
	}
	
	
	var caseCode = $('#caseCode').val();
	//获取贷款/过户权证
	var url = "/case/getLoanOrWarrantList?caseCode="+caseCode +"&type="+chooseType;
	var ctx = $("#ctx").val();
	
	$.ajax({
		cache : false,
		async : true,
		type : "POST",
		url : ctx + url,
		dataType : "json",
		timeout : 10000,
		success : function(data) {
			if(data == null || data.length ==0){
				window.wxc.alert("查无同组织下的其他权证人员!");
			}else{
				showLeadingModal(data);
			}
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
						addHtml += '<div class="m-t-xs font-bold">'+n.type+'</div></div></div>';
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
    window.wxc.confirm("您是否确认进行变更？",{"wxcOk":function(){
        var caseCode = $("#caseCode").val();
        var instCode = $("#instCode").val();
        var userId = $("#user_" + index).val();

        var url = "/case/changeLeadingUser";
        var ctx = $("#ctx").val();
        url = ctx + url;
        var params = '&userId=' + userId + '&caseCode=' + caseCode+"&instCode="+instCode +"&chooseType="+chooseType;

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
                	window.wxc.alert("系统已存在与此案件相关的询价记录!");
                    /*window.wxc.confirm(info,{'wxcOk':function(){

                    },'wxcCancel':function(){
                        //新增询价
                        var ctx = $("#ctx").val();
                        window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
                    }});*/
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

/**
 * 评估申请
 */
function evalApply(){
	
	var ctx = $("#ctx").val();
	var caseCode = $('#caseCode').val();
	
	window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
	
	/**
	 * modify wbcaiyx 2017/10/27
	 * 一个案件可以有多条评估，不检查了
	 */
	/*//判断是否已有评估申请流程	
	var url = ctx+'/case/checkEvalProcess?caseCode='+caseCode;
	$.ajax({
		url:url,
		type:'POST',
		dataType:'json',
		success:function(data){
			if(data.success){
				*//**
				 * modify wbcaiyx 2017/10/26
				 * 无关询价，注释
				 *//*				
				if(data.content == 1){//询价已完成,可以评估申请
					window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
				}else if(data.content == 2){//无询价,进入询价申请
					window.wxc.confirm("无完成询价记录,是否申请询价？",{"wxcOk":function(){
						window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
					}});
					*//**
					 * modify 无询价直接评估 
					 * @author wbcaiyx
					 * date 2017/10/24
					 *//*
					window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
//				}
			}else{
				window.wxc.alert(data.message);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});*/
	
}

function dateFormat(dateTime){
    if(dateTime ==null || dateTime == '' || dateTime == undefined){
        return '';
    }
    var date = new Date(dateTime);
    return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
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
function chgLoanReqmentCheck(){
	var selectVal =  $("#mortageService").val();
	if(selectVal == "" || selectVal == null){
		window.wxc.alert("贷款需求选择必须选!");
		return false;
	}
	return true;
}

/*贷款需求选择提交*/
function chgLoanReqment(){
	if(!chgLoanReqmentCheck()){
		return false;
	}
	window.wxc.confirm("您是否确认进行贷款选择变更？",{"wxcOk":function(){
	var jsonData = $("#loan_reqment_chg_form").serializeArray();
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
	})}});
}

