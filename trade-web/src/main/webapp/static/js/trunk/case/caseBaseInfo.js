
$(document).ready( function() {
 
       /** 获取公共信息 **/
       var caseCode = $('#caseCode').val();
       $.ajax({
       	type : 'post',
		cache : false,
		url : ctx+'/case/getCaseBaseInfo',
		data : "&caseCode="+caseCode,
		dataType : "json",
		success : function(data) {
			$('#agent').attr('data-content',data.agentMobile);
			$('#manager').attr('data-content',data.mcMobile);
			$('#ms').attr('data-content',data.msMobile);
			$('#loan').attr('data-content',data.loanMobile);
			$('#warr').attr('data-content',data.warMobile);
			$('#as').attr('data-content',data.asMobile);
			$('#buyer').attr('data-content',data.buyerMobile);
			$('#seller').attr('data-content',data.sellerMobile);
			new Vue({
				el:'#basicInfo',
				data:{
					caseProperty:data.toCase.caseProperty,
					status:data.toCase.status,
					isSubscribe:data.isSubscribe,
					caseCode:data.toCase.caseCode,
					ccaiCode:data.toCase.ccaiCode,
					toPropertyInfo:data.toPropertyInfo,
					agentName:data.agentName,
					agentGrpName:data.agentGrpName,
					mcName:data.mcName,
					msName:data.msName,
					loanName:data.loanName,
					warName:data.warName,
					asName:data.asName,
					buyerName:data.buyerName,
					sellerName:data.sellerName
				}
			})
			$("[data-toggle='popover']").popover();
		},
		error : function(errors) { 					
			return false;
		}               	               	
       });
   });
//竣工年限处理
Vue.filter('year', function (value) {
	if(value != null &&value != ''){
		if(value.length>0){
	        return value.substr(0,4);
		}else{
			return "";
		}
	}

})