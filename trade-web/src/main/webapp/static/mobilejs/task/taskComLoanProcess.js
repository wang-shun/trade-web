function checknum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function checkInt(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");  
}

 	var index = 0;
	

function checkMortgageForm(formId){
	if(formId.find("select[name='mortType']").val() == ""){
		formId.find("select[name='mortType']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='mortTotalAmount']").val() == ""){
		formId.find("input[name='mortTotalAmount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comAmount']").val() == ""){
		formId.find("input[name='comAmount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comYear']").val() == ""){
		formId.find("input[name='comYear']").val().css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val() == ""){
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='custCode']").val() == "" || formId.find("select[name='custCode']").val() == null){
		formId.find("select[name='custCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerName']").val() == ""){
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerPhone']").val() == ""){
		formId.find("input[name='loanerPhone']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerPhone']").val() != "" && !(/^0?1[3|4|5|7|8][0-9]\d{8}$/.test(formId.find("input[name='loanerPhone']").val()))){
		formId.find("input[name='loanerPhone']").css("border-color","red");
		alert("信贷员手机号码输入错误！");
		return false;
	}else if(formId.find("select[name='finOrgCode']").val() == ""){
		formId.find("select[name='finOrgCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='supContent']").val() != "" && formId.find("input[name='remindTime']").val()==""){
		alert("请输入补件时间！");
		return false;
	}else if(formId.find("input[name='remindTime']").val() != "" && formId.find("input[name='supContent']").val()==""){
		alert("请输入补件名称！");
		return false;
	}else if(formId.find("input[name='signDate']").val() == "" ){
		formId.find("input[name='signDate']").css("border-color","red");
		return false;
	}
	return true;
}

//保存贷款信息
function saveMortgage(){
	var isMainLoanBank = $("#isMainLoanBank").val();
	
	var form = $("#mortgageForm");
	
	if(isMainLoanBank == 0){
		form = $("#mortgageForm1");
	}
	form.find("input[name='custName']").val(form.find("select[name='custCode']").text());
	if(checkMortgageForm(form) && checkAttUp()){ 
		deleteAndModify();
		$.ajax({
			url:ctx+"/task/saveMortgage",
			method:"post",
			dataType:"json",
			data:form.serialize(),
			success:function(data){
				alert(data.message);
			}
		});
	}
}

//查询分行信息
function getParentBank(selector,selectorBranch,finOrgCode){
	var bankHtml = "<option value=''>请选择</option>";
    $.ajax({
    	url:ctx+"/manage/queryParentBankList",
		method:"post",
		dataType:"json",
		async:false,
		data:{},
		success:function(data){
			if(data != null){
				for(var i = 0;i<data.length;i++){
					bankHtml+="<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</option>";
				}
			}
		}
     });
	 selector.html(bankHtml);
	 $.ajax({
		    url:ctx+"/manage/queryParentBankInfo",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{finOrgCode:finOrgCode},
		    success:function(data){
	    		if(data != null){
	    			selector.val(data.content);
	    		}
	    	}
		});
	 selector.chosen({no_results_text:"未找到该选项",width:"100%",search_contains:true,disable_search_threshold:10});

	 getBranchBankList(selectorBranch,selector.val(),finOrgCode);

	 return bankHtml;
}
//查询支行信息
function getBranchBankList(selector,pcode,finOrgCode){

	var html = "<option value=''>请选择</option>";
	$.ajax({
	    url:ctx+"/manage/queryBankListByParentCode",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:{faFinOrgCode:pcode},
	    	success:function(data){
	    		if(data != null){
	    			for(var i = 0;i<data.length;i++){
	    				html +="<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</option>";
	    			}
	    		}
	    	}
	 });
	 selector.html(html);
	 selector.val(finOrgCode);

	 selector.chosen({no_results_text:"未找到该选项",width:"100%",search_contains:true,disable_search_threshold:10});

	return html;
}

//查询贷款信息
function getMortgageInfo(caseCode,isMainLoanBank){
	 $.ajax({
	    url:ctx+"/task/getMortgageInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
	    	success:function(data){	
	    		var finOrgCode = "";
	    		if(data.content != null && data.content.finOrgCode != null){
	    			finOrgCode = data.content.finOrgCode;
	    		}
 				 //银行下拉列表
				if(isMainLoanBank == 1){
    				getGuestInfo("mortgageForm");

	  				getParentBank($("#mortgageForm").find("select[name='bank_type']"),$("#mortgageForm").find("select[name='finOrgCode']"),finOrgCode);
	  				
	  				$("#mortgageForm").find("select[name='bank_type']").change(function(){
	  					$("#mortgageForm").find("select[name='finOrgCode']").chosen("destroy");
				    	getBranchBankList($("#mortgageForm").find("select[name='finOrgCode']"),$("#mortgageForm").find("select[name='bank_type']").val(),"");
				    }); 
				}else{
    				getGuestInfo("mortgageForm1");

					getParentBank($("#mortgageForm1").find("select[name='bank_type']"),$("#mortgageForm1").find("select[name='finOrgCode']"),finOrgCode);
	  				
	  				$("#mortgageForm1").find("select[name='bank_type']").change(function(){
	  					$("#mortgageForm1").find("select[name='finOrgCode']").chosen("destroy");
				    	getBranchBankList($("#mortgageForm1").find("select[name='finOrgCode']"),$("#mortgageForm1").find("select[name='bank_type']").val(),"");
				    }); 
				}
				  
	    		if(data != null && data.content != null){
	    			
	    			if(isMainLoanBank == 1){

		    			$("#mortgageForm").find("input[name='pkid']").val(data.content.pkid);
		    			$("#mortgageForm").find("select[name='mortType']").val(data.content.mortType);
		    			$("#mortgageForm").find("input[name='mortTotalAmount']").val(data.content.mortTotalAmount);
		    			$("#mortgageForm").find("input[name='comAmount']").val(data.content.comAmount);
		    			$("#mortgageForm").find("input[name='comYear']").val(data.content.comYear);
		    			$("#mortgageForm").find("input[name='comDiscount']").val(data.content.comDiscount);
		    			$("#mortgageForm").find("input[name='prfAmount']").val(data.content.prfAmount);
		    			$("#mortgageForm").find("input[name='prfYear']").val(data.content.prfYear);
		    			$("#mortgageForm").find("select[name='custCode']").val(data.content.custCode);
		    			$("#mortgageForm").find("select[name='custName']").val(data.content.custName);
		    			$("#mortgageForm").find("input[name='custCompany']").val(data.content.custCompany);
		    			$("#mortgageForm").find("select[name='lendWay']").val(data.content.lendWay);
		    			$("#mortgageForm").find("input[name='loanerName']").val(data.content.loanerName);
		    			$("#mortgageForm").find("input[name='loanerPhone']").val(data.content.loanerPhone);
		    			if(data.content.isLoanerArrive == 1){
		    				$("#mortgageForm").find("input[name='isLoanerArrive']").prop("checked",true);
		    			}
		    			$("#mortgageForm").find("input[name='houseNum']").val(data.content.houseNum);
		    			$("#mortgageForm").find("input[name='signDate']").val(data.content.signDate);
		    			if(data.content.ifReportBeforeLend == 1){
		    				$("#mortgageForm").find("input[name='ifReportBeforeLend']").prop("checked",true);
		    			}
		    			$("#mortgageForm").find("select[name='finOrgCode']").val(data.content.finOrgCode);
		    			$("#mortgageForm").find("input[name='tazhengArrDate']").val(data.content.tazhengArrDate);
		    			$("#mortgageForm").find("input[name='remark']").val(data.content.remark);
		    			if(data.content.toSupDocu != null){
		    				$("#mortgageForm").find("input[name='supContent']").val(data.content.toSupDocu.supContent);
			    			$("#mortgageForm").find("input[name='remindTime']").val(data.content.toSupDocu.remindTime);
		    			}
		    			
	    			}else{
	    				
		    			$("#mortgageForm1").find("input[name='pkid']").val(data.content.pkid);
		    			$("#mortgageForm1").find("select[name='mortType']").val(data.content.mortType);
		    			$("#mortgageForm1").find("input[name='mortTotalAmount']").val(data.content.mortTotalAmount);
		    			$("#mortgageForm1").find("input[name='comAmount']").val(data.content.comAmount);
		    			$("#mortgageForm1").find("input[name='comYear']").val(data.content.comYear);
		    			$("#mortgageForm1").find("input[name='comDiscount']").val(data.content.comDiscount);
		    			$("#mortgageForm1").find("input[name='prfAmount']").val(data.content.prfAmount);
		    			$("#mortgageForm1").find("input[name='prfYear']").val(data.content.prfYear);
		    			$("#mortgageForm1").find("select[name='custCode']").val(data.content.custCode);
		    			$("#mortgageForm1").find("select[name='custName']").val(data.content.custName);
		    			$("#mortgageForm1").find("input[name='custCompany']").val(data.content.custCompany);
		    			$("#mortgageForm1").find("select[name='lendWay']").val(data.content.lendWay);
		    			$("#mortgageForm1").find("input[name='loanerName']").val(data.content.loanerName);
		    			$("#mortgageForm1").find("input[name='loanerPhone']").val(data.content.loanerPhone);
		    			if(data.content.isLoanerArrive == 1){
		    				$("#mortgageForm1").find("input[name='isLoanerArrive']").prop("checked",true);
		    			}
		    			$("#mortgageForm1").find("input[name='houseNum']").val(data.content.houseNum);
		    			$("#mortgageForm1").find("input[name='signDate']").val(data.content.signDate);

		    			if(data.content.ifReportBeforeLend == 1){
		    				$("#mortgageForm1").find("input[name='ifReportBeforeLend']").prop("checked",true);
		    			}
		    			$("#mortgageForm1").find("select[name='finOrgCode']").val(data.content.finOrgCode);
		    			$("#mortgageForm1").find("input[name='tazhengArrDate']").val(data.content.tazhengArrDate);
		    			$("#mortgageForm1").find("input[name='remark']").val(data.content.remark);
		    			if(data.content.toSupDocu != null){
		    				$("#mortgageForm1").find("input[name='supContent']").val(data.content.toSupDocu.supContent);
			    			$("#mortgageForm1").find("input[name='remindTime']").val(data.content.toSupDocu.remindTime);
		    			}
	    			}
	    		}
	    	}
	  });
}

//查询客户信息
function getGuestInfo(formId){
	$.ajax({
		url:ctx+"/task/getGuestInfo",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val()},
		success:function(data){
			if(data != null && data.length > 0){
				$("#"+formId).find("select[name='custCode']").html("");
				for(var i=0;i<data.length;i++){
					$("#"+formId).find("select[name='custCode']").append("<option value='"+data[i].pkid+"'>"+data[i].guestName+"</option>");
				}
			}
		}
	});
}

//验证备件是否上传
function checkAttUp(){
	var attDiv = null;
	var isMainLoanBank = $("#isMainLoanBank").val();
	if(isMainLoanBank == "1"){
		attDiv = $(".att_first");
	}else{
		attDiv = $(".att_second");
	}
	var flag = true;
	attDiv.each(function(){
		var pic = $(this).find("img");
		if(pic.length == 0){
			flag = false;
		}
	});
	if(flag == false){
		alert("备件未上传完整！");
	}
	return flag;
}
var stepIndex = 0;
$(document).ready(function () {
	$("#sendSMS").click(function(){
		var t='';
		var s='/';
		$("#table_list_2").find("input:checkbox:checked").closest('td').next().each(function(){
			t+=($(this).text()+s);
		});
		if(t!=''){
			t=t.substring(0,t.length-1);
		}
		$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
	});
	$("#sendSMS1").click(function(){
			var t='';
			var s='/';
			$("#table_list_5").find("input:checkbox:checked").closest('td').next().each(function(){
				t+=($(this).text()+s);
			});
			if(t!=''){
				t=t.substring(0,t.length-1);
			}
			$("#smsPlatFrom").smsPlatFrom({ctx:'${ctx}',caseCode:$('#caseCode').val(),serviceItem:t});
	});
	
	$("select[name='mortType']").each(function(){
		$(this).find("option[value='30016003']").remove();
	});
	$("select[name='mortType']").blur(function(){
		if($(this).val()!=""){
			$(this).css("border-color","#e5e6e7");
		}
	});
		
	$("#second").hide();
	$("#firBank").click(function(){
		if($("#first").css("display") == "none"){
			$("#second").hide();
			$("#first").show();
			$("#isMainLoanBank").val("1");

			getMortgageInfo($("#caseCode").val(),"1");
			
			$("#firBank i").removeClass("fa fa-chevron-down");
		
			$("#firBank i").addClass("fa fa-chevron-up");
			
			$("#secBank i").removeClass("fa fa-chevron-up");
			
			$("#secBank i").addClass("fa fa-chevron-down");	
			

	
		}else{
			$("#first").hide();
			
			$("#second").show();
			$("#isMainLoanBank").val("0");
			getMortgageInfo($("#caseCode").val(),"0");

			$("#firBank i").removeClass("fa fa-chevron-up");
		
			$("#firBank i").addClass("fa fa-chevron-down");

			$("#secBank i").removeClass("fa fa-chevron-down");
		
			$("#secBank i").addClass("fa fa-chevron-up");
	
		}
	});
	$("#secBank").click(function(){
		
		if($("#second").css("display") == "none"){
			
			$("#firBank i").removeClass("fa fa-chevron-up");
		
			$("#firBank i").addClass("fa fa-chevron-down");
			
			$("#secBank i").removeClass("fa fa-chevron-down");
		
			$("#secBank i").addClass("fa fa-chevron-up");	
		
			$("#first").hide();
			$("#second").show();
			$("#isMainLoanBank").val("0");
			getMortgageInfo($("#caseCode").val(),"0");
	
		}else{
			$("#firBank i").removeClass("fa fa-chevron-down");
		
			$("#firBank i").addClass("fa fa-chevron-up");
			
			$("#secBank i").removeClass("fa fa-chevron-up");
		
			$("#secBank i").addClass("fa fa-chevron-down");	
			
			$("#second").hide();
			$("#first").show();
			$("#isMainLoanBank").val("1");
			getMortgageInfo($("#caseCode").val(),"1");
	
		}
	});
	getMortgageInfo($("#caseCode").val(),"1");
	$("input[type='text']").each(function(){
		$(this).blur(function(){
			if($(this).val()!=""){
				$(this).css("border-color","#e5e6e7");
			}
		});
	});
	
	$("select").each(function(){
		$(this).blur(function(){
			if($(this).val() != ""){
				$(this).css("border-color","#e5e6e7");
			}
		});
	});
	
	$("#saveBtn1").click(function(){
		saveMortgage();
	});
	
	$("#saveBtn2").click(function(){
		saveMortgage();
	})
     
 });