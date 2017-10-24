$(document).ready(function() {
	$('.input-daterange').datepicker({
		keyboardNavigation : false,
		forceParse : false,
		autoclose : true
	});
	var ctx = "${ctx}";
	//loadTimeInfo();
	
	loadGridTable();
});

//function loadGridTable(){
//	debugger;
//	//var inHtml = "";
//	 //$("#time-info").html(inHtml);
//	var index = $("#order1").val();
//	var index = $("#order2").val();
//	$("#time-info").html('');
//	for(var i = 0; i < 6; i++){
//		var inHtml  = "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>申请时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' data-date-format='yyyy-mm-dd' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//			
//			inHtml += "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>面签时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//			
//			inHtml += "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>陪同还贷时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//		
//			inHtml += "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>注销抵押时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//			
//			inHtml += "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>领取产证时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//			
//			inHtml += "<div class='line'><div class='form_content'><label class='control-label sign_left_small select_style mend_select' id='partCode0'>回款结清时间</label>";
//			inHtml += "<div class='input-group sign-right dataleft input-daterange' >";
//			inHtml += "<input name='estPartTime" + i + "' class='form-control data_style' type='text' value='' placeholder=''></div></div>";
//			inHtml += "<div class='form_content'><label class='control-label sign_left_small'>变更理由</label>";
//			inHtml += "<input name='remark1" + i + "' class='teamcode input_type' placeholder='' value=''' /></div></div>";
//	
//	}
//	$("#time-info").html(inHtml);
//	$('.input-daterange').datepicker({
//		keyboardNavigation : false,
//		forceParse : false,
//		autoclose : true
//	});
//}
/**
 * 加载赎楼变更时间信息,已完成环节控件不可更改
 * @returns
 */
function loadTimeInfo(){
	debugger;
	//赎楼环节
	var property = $("#taskCode").val();
	
	for(var i = 0; i < $(".form_content").length/2; i++){
		
		switch(property){
			case "APPLY":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "SIGN":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "PAYLOAN_ONE":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "PAYLOAN_TWO":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "CANCELDIYA_ONE":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "CANCELDIYA_TWO":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "RECEIVE_ONE":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "RECEIVE_TWO":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			case "PAYCLEAR":
				$("input[name='estPartTime'" + i + "]").attr("disabled",true); 
				$("input[name='remark'" + i + "]").attr("disabled",true);
				break;
			default:
				break;
		}
		
	}
}

/**
 * 保存 i=1 ，查看变更记录 i = 2
 * @param i
 * @returns
 */
function submitChangeRecord(i){
	debugger;
	var ransomCode = $("#ransomCode").val();
	var count = $("#count").val();
	var ransomVo = getParams();
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:{ransomVo:JSON.stringify(ransomVo),flag:i,ransomCode:ransomCode,count:count},
		type:"POST",
		success: function(data){
			
			if(data.code == "1"){
				window.wxc.alert("赎楼计划时间修改保存成功！");
				window.location.href = ctx + "/ransomList/ransomDetail?ransomCode=" + ransomCode;
			}else if(data.code == "2"){
				window.location.href = ctx + "/ransomList/ransomChangeRecord?ransomCode=" + ransomCode;
			}
		},
		error: function(data){
			window.wxc.error(data.message);
		}
	});
}

/**
 * 获取参数
 * @returns
 */
function getParams(){
	
	var count = $("#count").val();
	debugger;
	var array = new Array();
	for(var i = 0;i < $(".line input").length / 2;i++){
		var estPartTime = $("input[name='estPartTime" + i + "']").val();
		var remark = $("input[name='remark" + i + "']").val();
		var partCode = $("#partCode" + i + "").text();
		
		if(partCode == '申请时间'){
			partCode = 'APPLY';
		}else if(partCode == '面签时间'){
			partCode = 'SIGN';
		}else if(partCode == '陪同还贷时间' && count == 0){
			partCode = 'PAYLOAN_ONE';
		}else if(partCode == '陪同还贷时间' && count == 1){
			partCode = 'PAYLOAN_TWO';
		}else if(partCode == '注销抵押时间' && count == 0){
			partCode = 'CANCELDIYA_ONE';
		}else if(partCode == '注销抵押时间' && count == 1){
			partCode = 'CANCELDIYA_TWO';
		}else if(partCode == '领取产证时间' && count == 0){
			partCode = 'RECEIVE_ONE';
		}else if(partCode == '领取产证时间' && count == 1){
			partCode = 'RECEIVE_TWO';
		}else if(partCode == '回款结清时间'){
			partCode = 'PAYCLEAR';
		}
		
		var resJson = {
//			ransomCode:ransomCode,
			partCode : partCode,	
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