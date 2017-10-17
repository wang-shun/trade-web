$(document).ready(function() {
	$('.input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true
	});
	var ctx = "${ctx}";
	//loadTimeInfo();
	$('#save').click(function() {
		//window.location.href = ctx + "/ransomList/ransom/ransomDetail";
		submitUpdateRansom();
	});
});

/**
 * 加载赎楼变更时间信息
 * @returns
 */
function loadTimeInfo(){
	debugger;
	//申请时间
	var applyTime = $("#applyTime").val();
	//申请时间变更理由
	var applyRemake = $("#applyRemake").val();
	//面签时间
	var interviewTime = $("#interviewTime").val();
	//面签时间变更理由
	var interviewRemake = $("#interviewRemake").val();
	//陪同还贷时间
	var repayTime = $("#repayTime").val();
	//陪同还贷时间变更理由
	var repayRemake = $("#repayRemake").val();
	//注销抵押时间
	var cancelTime = $("#cancelTime").val();
	//注销抵押时间变更理由
	var cancelRemake = $("#cancelRemake").val();
	//领取产证时间
	var redeemTime = $("#redeemTime").val();
	//领取产证时间变更理由
	var redeemRemake = $("#redeemRemake").val();
	//回款结清时间
	var paymentTime = $("#paymentTime").val();
	//回款结清时间变更理由
	var paymentRemake = $("#paymentRemake").val();
	
	if(applyTime != "" && applyTime != null){
		$("#applyTime").attr("disabled",true); 
		$("#applyRemake").attr("disabled",true); 
	}
	
	if(interviewTime != "" && interviewTime != null){
		$("#interviewTime").attr("disabled",true); 
		$("#interviewRemake").attr("disabled",true); 
	}
	
	if(repayTime != "" && repayTime != null){
		$("#repayTime").attr("disabled",true); 
		$("#repayRemake").attr("disabled",true); 
	}
	
	if(cancelTime != "" && cancelTime != null){
		$("#cancelTime").attr("disabled",true); 
		$("#cancelRemake").attr("disabled",true); 
	}
	
	if(redeemTime != "" && redeemTime != null){
		$("#redeemTime").attr("disabled",true); 
		$("#redeemRemake").attr("disabled",true); 
	}
	
	if(paymentTime != "" && paymentTime != null){
		$("#paymentTime").attr("disabled",true); 
		$("#paymentRemake").attr("disabled",true); 
	}
}

/**
 * 提交赎楼计划时间变更
 * @returns
 */
function submitUpdateRansom(){
	debugger;
	var ransomVo = getParams();
	
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:ransomVo,
		type:"POST",
		success: function(data){
			window.wxc.alert("赎楼计划时间修改保存成功！");
			window.location.href = ctx + "/ransomList/ransomDetail?caseCode=" + caseCode;
		},
		error: function(data){
			window.wxc.error(data.message);
		}
	});
}
/**
 * 变更记录明细
 * @returns
 */
function reloadTimeRecord(){
	debugger;
	saveUpdateRansom();
	
	var url = ctx + '/quickGrid/findPage';
	var ransomCode = $('#ransomCode').val();
	var data = {};
	data.page = 1;
	data.rows = 10;
	data.queryId = "queryRansomTimeRecord1";
	data.argu_ransomCode = ransomCode;
	$.ajax({
		async: true,
		type:'POST',
		url:url,
		dataType:'json',
		data:data,
		beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
        },  
        success: function(data){
        	$.unblockUI();
        	var html = template('template_timeRecord',data);
      		$('#record-detail').html(html);
        }
	});	
}

function saveUpdateRansom(){
	debugger;
	var ransomVo = getParams();
	
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:ransomVo,
		type:"POST",
		success: function(data){
//			window.wxc.alert("赎楼计划时间修改保存成功！");
//			window.location.href = ctx + "/ransomList/ransomDetail?caseCode=" + caseCode;
		},
		error: function(data){
			window.wxc.error(data.message);
		}
	});
}

function getParams(){
	var caseCode = $("#caseCode").val();
	var ransomCode = $("#ransomCode").val();
	//申请时间
	var applyTime = strToDate($("#applyTime").val());
	//申请时间变更理由
	var applyRemake = $("#applyRemake").val().trim();
	//面签时间
	var interviewTime = strToDate($("#interviewTime").val());
	//面签时间变更理由
	var interviewRemake = $("#interviewRemake").val().trim();
	//陪同还贷时间
	var repayTime = strToDate($("#repayTime").val());
	//陪同还贷时间变更理由
	var repayRemake = $("#repayRemake").val().trim();
	//注销抵押时间
	var cancelTime = strToDate($("#cancelTime").val());
	//注销抵押时间变更理由
	var cancelRemake = $("#cancelRemake").val().trim();
	//领取产证时间
	var redeemTime = strToDate($("#redeemTime").val());
	//领取产证时间变更理由
	var redeemRemake = $("#redeemRemake").val().trim();
	//回款结清时间
	var paymentTime = strToDate($("#paymentTime").val());
	//回款结清时间变更理由
	var paymentRemake = $("#paymentRemake").val().trim();
	
	var ransomVo = {
			ransomCode:ransomCode,
			applyTime:applyTime,
			applyRemake:applyRemake,
			interviewTime:interviewTime,
			interviewRemake:interviewRemake,
			repayTime:repayTime,
			repayRemake:repayRemake,
			cancelTime:cancelTime,
			cancelRemake:cancelRemake,
			redeemTime:redeemTime,
			redeemRemake:redeemRemake,
			paymentTime:paymentTime,
			paymentRemake:paymentRemake
	};
	
	return ransomVo;
}
	/**
	 * 根据日期字符串转换成日期
	 * 控件字符串转换成日期格式 "2017-10-11"
	 * @returns
	 */
	function strToDate(str){
		
		var regEx = new RegExp("\\-","gi");
		str=str.replace(regEx,"-");
		
		return str;
	}