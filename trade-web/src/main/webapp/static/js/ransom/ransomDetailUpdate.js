/**
 * 赎楼单修改js
 * @author wbcaiyx
 */

$(document).ready(function(){
	reloadWorkInfo();	
	$('#save').click(function() {
		//window.location.href = ctx + "/ransomList/ransom/ransomDetail";
		submitUpdateRansom();
	});
	$("#close").click(function(){
		if (confirm("您确定要关闭本页吗？")) { 
			window.open('', '_self').close(); 
		}
	});
});

/**
 * 加载完成环节信息
 * @returns
 */
function reloadWorkInfo(){
	var url = ctx + '/quickGrid/findPage';
	var ransomCode = $('#ransomCode').val();
	var data = {};
	data.page = 1;
	data.rows = 10;
	data.queryId = "queryRansomLink";
	data.argu_ransomCode = ransomCode;
//	debugger;
	debugger;
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
        	var html = template('template_workInfo',data);
      		$('#work-info').html(html);
        }
	});	
}

	/**
	 * 赎楼单修改
	 * @returns
	 */
	function submitUpdateRansom(){
		debugger;
		var ransomCode = $("#ransomCode").val();
		//主贷人姓名
		var borrowerName = $("#borrowerName option:selected").val();
		//电话
		var borrowerPhone = $("#borrowerPhone").val();
		//受理时间
		var signTime = $("#signTime").val();
		//尾款机构
		var finOrgCode = $("#finOrgCode").val();
		//尾款类型
		var mortgageType = $("#mortgageType").val();
		//抵押类型
		var diyaType = $("#diyaType").val();
		//贷款金额
		var loanMoney = $("#loanMoney").val();
		//剩余金额
		var restMoney = $("#restMoney").val();
		//借款总金额
		var borrowerMoney = $("#borrowerMoney").val();
		
		var ransomVo = {
				ransomCode:ransomCode,
				borrowerName:borrowerName,
				borrowerPhone:borrowerPhone,
				signTime:signTime,
				finOrgCode:finOrgCode,
				mortgageType:mortgageType,
				diyaType:diyaType,
				loanMoney:loanMoney,
				restMoney:restMoney,
				borrowerMoney:borrowerMoney
		};
		
		$.ajax({
			url: ctx + "/ransomList/updateRansom",
			dataType:"json",
//			contentType: "application/json; charset=utf-8",\
			data:ransomVo,
			type:"POST",
			success: function(data){
				window.wxc.href = "../../jsp/ransom/ransomDetail";
			},
			error: function(data){
				window.wxc.error(data.message);
			}
		});
	}


