$(document).ready(function() {
	$('.input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true
	});
	var ctx = "${ctx}";
	loadTimeInfo();
});

/**
 * 加载赎楼变更时间信息
 * @returns
 */
function loadTimeInfo(){
	debugger;
	//赎楼环节
	var property = $("input[name='partCode']").val();
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
	
	switch(property){
		case "APPLY" :
			$("#applyTime").attr("disabled",true); 
			$("#applyRemake").attr("disabled",true);
		case "SIGN" :
			$("#interviewTime").attr("disabled",true); 
			$("#interviewRemake").attr("disabled",true); 
		case "PAYLOAN_ONE" :
			$("#repayTime").attr("disabled",true); 
			$("#repayRemake").attr("disabled",true);
		case "PAYLOAN_TWO" :
			$("#repayTime").attr("disabled",true); 
			$("#repayRemake").attr("disabled",true);
		case "CANCELDIYA_ONE" :
			$("#cancelTime").attr("disabled",true); 
			$("#cancelRemake").attr("disabled",true); 
		case "CANCELDIYA_TWO" :
			$("#cancelTime").attr("disabled",true); 
			$("#cancelRemake").attr("disabled",true); 
		case "RECEIVE_ONE" :
			$("#redeemTime").attr("disabled",true); 
			$("#redeemRemake").attr("disabled",true); 
		case "RECEIVE_TWO" :
			$("#redeemTime").attr("disabled",true); 
			$("#redeemRemake").attr("disabled",true); 
		case "PAYCLEAR" :
			$("#paymentTime").attr("disabled",true); 
			$("#paymentRemake").attr("disabled",true); 
			break;
		default:break;
		
	}
	
}

/**
 * 保存 i=1 ，查看变更记录 i = 2
 * @param i
 * @returns
 */
function submitChangeRecord(i){
	debugger;
	var caseCode = $("#caseCode").val();
	var ransomVo = getParams();
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:{ransomVo:JSON.stringify(ransomVo),flag:i},
		type:"POST",
		success: function(data){
			
			if(data.code == "1"){
				window.wxc.alert("赎楼计划时间修改保存成功！");
				window.location.href = ctx + "/ransomList/ransomDetail?caseCode=" + caseCode;
			}else if(data.code == "2"){
				window.location.href = ctx + "/ransomList/ransomChangeRecord?caseCode=" + caseCode;
			}
		},
		error: function(data){
			window.wxc.error(data.message);
		}
	});
}

function getParams(){
	
	var caseCode = $("#caseCode").val();
	var ransomCode = $("#ransomCode").val();
	debugger;
	var array = new Array();
	for(var i = 0;i < $(".line input").length / 2;i++){
		var estPartTime = $("input[name='estPartTime" + i + "']").val();
		var remark = $("input[name='remark" + i + "']").val();
		var partCode = $("#partCode"+ i + "").text();
		
		if(partCode == "申请时间"){
			partCode = "APPLY";
		}
		
		if(partCode == "面签时间"){
			partCode = "SIGN";		
		}
		
		if(partCode == "陪同还贷时间"){
			partCode = "PAYLOAN_ONE";
		}
		
		if(partCode == "注销抵押时间"){
			partCode = "CANCELDIYA_ONE";
		}
		
		if(partCode == "领取产证时间"){
			partCode = "RECEIVE_ONE";
		}
		
		if(partCode == "回款结清时间"){
			partCode = "PAYCLEAR";
		}
		
		var resJson = {
			ransomCode:ransomCode,	
			partCode:partCode,
			estPartTime:estPartTime,
			remark:remark
		}
		
		array.push(resJson);
	}
	
	return array;
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