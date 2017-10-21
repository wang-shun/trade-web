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
	var ransomVo = getParams();
	$.ajax({
		url: ctx + "/ransomList/updateRansomPlanTime",
		dataType:"json",
		data:{ransomVo:JSON.stringify(ransomVo),flag:i},
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
	
	var ransomCode = $("#ransomCode").val();
	debugger;
	var array = new Array();
	for(var i = 0;i < $(".line input").length / 2;i++){
		var estPartTime = $("input[name='estPartTime" + i + "']").val();
		var remark = $("input[name='remark" + i + "']").val();
		
		var resJson = {
			ransomCode:ransomCode,	
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