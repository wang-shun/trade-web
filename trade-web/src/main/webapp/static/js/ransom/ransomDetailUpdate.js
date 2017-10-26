/**
 * 赎楼单修改js
 * @author wbcaiyx
 */

$(document).ready(function(){
	
	//reloadWorkInfo();	
	$('#save').click(function() {
		//window.location.href = ctx + "/ransomList/ransom/ransomDetail";
		if(checkForm()){
			submitUpdateRansom();
		}
	});
});

	/**
	 * 赎楼单修改
	 * @returns
	 */
	function submitUpdateRansom(){
		debugger;
		var caseCode = $("#caseCode").val();
		var ransomCode = $("#ransomCode").val();
		//主贷人姓名
		var borrowerName = $("#borrowerName option:selected").val();
		//电话
		var borrowerPhone = $("#borrowerPhone").val();
		//受理时间
		var signTime = $("#signTime").val();
		
		var ransomVo = new Array();
		for(var i = 0; i < $("#bank-org tr").length; i ++){
			//尾款机构
			var finOrgCode = $("#finOrgCode" + i + "").val();
			//尾款类型
			var mortgageType = $("#mortgageType" + i + "").val();
			//抵押类型
			var diyaType = $("#diyaType" + i + "").val();
			//贷款金额
			var loanMoney =   parseInt($("#loanMoney" + i + "").val() * 10000);
			//剩余金额
			var restMoney =  parseInt($("#restMoney" + i + "").val() * 10000);
			
			var resJson = {
					ransomCode:ransomCode,
					borrowerName:borrowerName,
					borrowerPhone:borrowerPhone,
					signTime:signTime,
					finOrgCode:finOrgCode,
					mortgageType:mortgageType,
					diyaType:diyaType,
					loanMoney:loanMoney,
					restMoney:restMoney,
//					borrowerMoney:borrowerMoney
			};
			
			ransomVo.push(resJson);
		}
		
		//借款总金额
		var borrowerMoney =  parseInt($("#borrowerMoney").val() * 10000);
		ransomVo.push(borrowerMoney);
//		var ransomVo = {
//				ransomCode:ransomCode,
//				borrowerName:borrowerName,
//				borrowerPhone:borrowerPhone,
//				signTime:signTime,
//				finOrgCode:finOrgCode,
//				mortgageType:mortgageType,
//				diyaType:diyaType,
//				loanMoney:loanMoney,
//				restMoney:restMoney,
//				borrowerMoney:borrowerMoney
//		};
		
		$.ajax({
			url: ctx + "/ransomList/updateRansom",
			dataType:"json",
			data:{
				ransomVo:JSON.stringify(ransomVo)
	   		},
			type:"POST",
			success: function(data){
				window.location.href = ctx + "/ransomList/ransomDetail?ransomCode=" + ransomCode;
			},
			error: function(data){
				window.wxc.error(data.message);
			}
		});
	}

	/**
	 * 判断信息不能为空
	 * @returns
	 */
	function checkForm(){
		//主贷人姓名
		var borrowerName = $("#borrowerName option:selected").val();
		//电话
		var borrowerPhone = $("#borrowerPhone").val();
		//受理时间
		var signTime = $("#signTime").val();
		
		for(var i = 0; i < $("#bank-org tr").length; i++){
			//尾款机构
			var finOrgCode = $("#finOrgCode" + i + "").val();
			//尾款类型
			var mortgageType = $("#mortgageType" + i + "").val();
			//抵押类型
			var diyaType = $("#diyaType" + i + "").val();
			//贷款金额
			var loanMoney = $("#loanMoney" + i + "").val();
			//剩余金额
			var restMoney = $("#restMoney" + i + "").val();
			
			if(finOrgCode == "" || finOrgCode == null){
				window.wxc.alert("尾款机构不能为空！");
				return false;
			}
			
			if(mortgageType == "" || mortgageType == null){
				window.wxc.alert("尾款类型不能为空！");
				return false;
			}
			
			if(diyaType == "" || diyaType == null){
				window.wxc.alert("抵押类型不能为空！");
				return false;
			}
			
			if(loanMoney == "" || loanMoney == null){
				window.wxc.alert("贷款金额不能为空！");
				return false;
			}
			
//			if(loanMoney != "" || loanMoney != null){
//				if(!reg.test(parseFloat(loanMoney))){
//					window.wxc.alert("贷款金额必须是数字！");
//					return false;
//				}
//			}
//			
			if(restMoney == "" || restMoney == null){
				window.wxc.alert("剩余金额不能为空！");
				return false;
			}
		}
		
		//借款总金额
		var borrowerMoney = $("#borrowerMoney").val();

		var reg = /^\d+(\.\d{2})?$/g; //判断金钱为数字,并且保留两位小数
		
		if(borrowerName == "" || borrowerName == null){
			window.wxc.alert("主贷人姓名不能为空！");
			return false;
		}
		
		if(borrowerPhone == "" || borrowerPhone == null){
			window.wxc.alert("电话不能为空！");
			return false;
		}
		
		if(signTime == "" || signTime == null){
			window.wxc.alert("受理时间不能为空！");
			return false;
		}
		
//		if(finOrgCode == "" || finOrgCode == null){
//			window.wxc.alert("尾款机构不能为空！");
//			return false;
//		}
//		
//		if(mortgageType == "" || mortgageType == null){
//			window.wxc.alert("尾款类型不能为空！");
//			return false;
//		}
		
//		if(diyaType == "" || diyaType == null){
//			window.wxc.alert("抵押类型不能为空！");
//			return false;
//		}
//		
//		if(loanMoney == "" || loanMoney == null){
//			window.wxc.alert("贷款金额不能为空！");
//			return false;
//		}
//		
//		if(loanMoney != "" || loanMoney != null){
//			if(!reg.test(parseFloat(loanMoney))){
//				window.wxc.alert("贷款金额必须是数字！");
//				return false;
//			}
//		}
//		
//		if(restMoney == "" || restMoney == null){
//			window.wxc.alert("剩余金额不能为空！");
//			return false;
//		}
		
//		if(restMoney != "" || restMoney != null){
//			if(!reg.test(parseFloat(restMoney))){
//				window.wxc.alert("剩余金额必须是数字！");
//				return false;
//			}
//		}
		
//		if(borrowerMoney == "" || borrowerMoney == null){
//			window.wxc.alert("借款总金额不能为空！");
//			return false;
//		}
		
		if(borrowerMoney != "" || borrowerMoney != null){
//			if(!reg.test(parseFloat(borrowerMoney))){
//				window.wxc.alert("借款总金额必须是数字！");
//				return false;
//			}
		}
		
		return true;
	}

