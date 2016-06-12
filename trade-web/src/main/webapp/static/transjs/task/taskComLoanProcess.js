var popInited=false;
function checkAssess(){
	
	if($("#building_no").val() == ""){
		$("#building_no").css("border-color","red");
		return false;
	}else if($("#room_no").val() == ""){
		$("#room_no").css("border-color","red");
		return false;
	}else if($("#area").val() == ""){
		$("#area").css("border-color","red");
		return false;
	}else if($("#deal_price").val() == ""){
		$("#deal_price").css("border-color","red");
		return false;
	}else if($("#floor").val() == ""){
		$("#floor").css("border-color","red");
		return false;
	}else if($("#total_floor").val() == ""){
		$("#total_floor").css("border-color","red");
		return false;
	}else if($("#prop_type").val() == ""){
		$("#prop_type").css("border-color","red");
		return false;
	}else if(($("#prop_type").val()=="5" || $("#prop_type").val()=="6" || $("#prop_type").val()=="7")&&$("#area_ground").val()==""){
		alert("别墅地上建筑面积不能为空！")
		return false;
	}

	return true;
}
function checkDisagree(){
	
	if($("#expected_price").val() == ""){
		$("#expected_price").css("border-color","red");
		return false;
	}else if($("#applyForm").find("input[name='remark']").val() == ""){
		$("#applyForm").find("input[name='remark']").css("border-color","red");
		return false;
	}

	return true;
}

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
		formId.find("input[name='comYear']").css("border-color","red");
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
	var prfAmoutStr=formId.find("input[name='prfAmount']").val();
	var prfAmount=prfAmoutStr==''?0:parseFloat(prfAmoutStr);
	var mortTotalAmount=parseFloat(formId.find("input[name='mortTotalAmount']").val());
	var comAmount=parseFloat(formId.find("input[name='comAmount']").val());
	if((mortTotalAmount-prfAmount).toFixed(5)!=comAmount){
		alert('贷款总额必须等于商贷和公积金之和');
    	return false;
	}
	return true;
}
//询价	
function assess(){
	var isMainLoanBank = $("#isMainLoanBank").val();

	if(checkAssess()){
		$("#toEguPricingSubmitBtn").attr("disabled",true);

	 	$.ajax({
		    url:ctx+"/remote/egu/assess",
	    	method:"post",
	    	dataType:"json",
	    	data:$("#addToEguPricingForm").serialize(),
	    	success:function(data){
	    		$("#toEguPricingSubmitBtn").attr("disabled",false);
	    		if(data.message !=null){
	        		//匹配多个小区
	        		if(data.code == "30009"){
		        		var msg = $.parseJSON(data.message);
		        		$("#modal-select").modal('show');
		        		var str = "";
		        		if(msg.length > 0){
			        		str +="<div style='height:30px'><div class='col-md-3'>小区编号</div><div class='col-md-7'>小区地址</div></div>";
		        			for(var i=0;i<msg.length;i++){
				        		str +="<div class='div' style='height:30px'><div class='col-md-3'>"+msg[i].id+"</div><div class='col-md-7'>"+msg[i].name+"</div></div>";
		        			}
		        		}
		        		$("#residenceList").html(str);
		        		$(".div").each(function(){
		        			$(this).mouseover(function(){
			        			$(this).addClass("mouseover-color");
			        		});
		        			$(this).mouseout(function(){
			        			$(this).removeClass("mouseover-color");
			        		});
		        			$(this).click(function(){
		        				$("#residence_id").val($.trim($(this).find("div:first").html()));
				        		$("#modal-select").modal('hide');
		        			});
		        		});
	        		}else{
	        			
		        		alert(data.message);
						if(!!data.success){
							$("#modal-form").modal("hide");
						}
	        		}
		
					if(isMainLoanBank == 1){
						searchPricingList("table_list_1");
		    		//	getPricingList("table_list_1","pager_list_1");
					}else{
						searchPricingList("table_list_3");
		    		//	getPricingList("table_list_3","pager_list_3");
					}
	    		}	    		
	    	}
	    });
	}
}
//弹出异议申请窗口
function showApplyModal(evaCode,applyCode,totalPrice){
	if(totalPrice==""||totalPrice == null){
		alert("询价结果未返回，不能发起异议！");
		return;
	}
	$("#modal-applyForm").modal("show");
	$("#eva_code").val(evaCode);
	$("#apply_code").val(applyCode);
}

//发起异议申请
function disagree(){

	var evaCode = $("#eva_code").val();
	var isMainLoanBank = $("#isMainLoanBank").val();
	if(checkDisagree()){
		if(evaCode!=""){
			$("#subApplyBtn").attr("disabled",true);

			$.ajax({
				url:ctx+"/remote/egu/"+evaCode+"/disagree",
				method:"post",
				dataType:"json",
				data:$("#applyForm").serialize(),
				success:function(data){
					$("#subApplyBtn").attr("disabled",false);

					alert(data.message);
					if(data.success){
		    			if(isMainLoanBank == 0){
							searchPricingList("table_list_3");
		        	//		getPricingList("table_list_3","pager_list_3");
		    			}else{
							searchPricingList("table_list_1");
		        	//		getPricingList("table_list_1","pager_list_1");
		    			}
						$("#modal-applyForm").modal("hide");
					}
				}
			});	
		}
	}
}

//确认询价结果
function confirmPricing(){
	var evaCode = $("#eva_code").val();
	$.ajax({
		url:ctx+"/remote/egu/"+evaCode+"/confirm",
		method:"post",
		dataType:"json",
		data:{evaCode:evaCode},
		success:function(data){
			if(!data.success){
				if(data.message != ""){
					alert(data.message);
				}	
			}
		}
	});
}
//接受/取消接受询价结果
function accOrCancelPricing(tableId,pkid,totalPrice){
	if(totalPrice==""||totalPrice == null){
		alert("询价结果未返回，不能接受询价！");
		return;
	}
	var btn = $("#"+tableId+"_btn_"+pkid).html();
	if(btn == "取消"){
		cancelAccept(tableId,pkid);
	}else if(btn == "接受"){
		acceptPricing(tableId,pkid);
	}
}
//接受询价结果
function acceptPricing(tableId,pkid){
	var isMainLoanBank = $("#isMainLoanBank").val();
	$.ajax({
		url:ctx+"/task/acceptPricingResult",
		method:"post",
		dataType:"json",
		data:{pkid:pkid},
		success:function(data){
			if(data.success){
				$("#"+tableId+"_btn_"+pkid).html("取消");
    			if(isMainLoanBank == 0){
					searchPricingList("table_list_3");
        	//		getPricingList("table_list_3","pager_list_3");
    			}else{
					searchPricingList("table_list_1");
        	//		getPricingList("table_list_1","pager_list_1");
    			}
			}else{
				alert(data.message);	
			}
		}
	});
}

//取消接受询价结果
function cancelAccept(tableId,pkid){
	var isMainLoanBank = $("#isMainLoanBank").val();

	$.ajax({
		url:ctx+"/task/cancelAccept",
		method:"post",
		dataType:"json",
		data:{pkid:pkid},
		success:function(data){
			if(data.success){
				$("#"+tableId+"_btn_"+pkid).html("接受");
    			if(isMainLoanBank == 0){
					searchPricingList("table_list_3");
        	//		getPricingList("table_list_3","pager_list_3");
    			}else{
					searchPricingList("table_list_1");
        	//		getPricingList("table_list_1","pager_list_1");
    			}
			}else{
				alert(data.message);	
			}
		}
	});
}

//保存贷款信息
function saveMortgage(form){


	form.find("input[name='custName']").val(form.find("select[name='custCode']").find("option:selected").text());
		$.ajax({
			url:ctx+"/task/saveMortgage",
			method:"post",
			dataType:"json",
			data:form.serialize(),
			success:function(data){
				if(!data.success){
					alert(data.message);
				}
			}
		});
	
}

//完成贷款审批
function completeMortgage(form){

	var pkid=form.find("input[name='pkid']").val();

	var lastBankSub = form.find("input[name='lastBankSub']:checked");
	if(pkid == null || pkid == "" ){
		alert("贷款信息不存在！");
		return;
	}
	if(lastBankSub.val() == undefined){
		alert("最终贷款银行未确认！");
		return;
	}
	
	var lastLoanBank = null;
	if(lastBankSub.val() != undefined){
		lastLoanBank = form.find("input[name='finOrgCode']").val();
	}
	
	$.ajax({
		url:ctx+"/task/completeMortgage",
		method:"post",
		dataType:"json",
		data:{pkid:pkid,caseCode:$("#caseCode").val(),apprDate:$("#apprDate").val(),lastLoanBank:lastLoanBank,partCode:$("#partCode").val()},
		success:function(data){
			if(data.success){
				if('caseDetails'==source){
					alert('保存成功');
				}else{
					submitMortgage();
				}
			}else{
				alert(data.message);
			}
		}
	});
}
//查询分行信息
function getParentBank(selector,selectorBranch,finOrgCode,flag){
	var bankHtml = "<option value=''>请选择</option>";
    $.ajax({
    	cache:true,
    	url:ctx+"/manage/queryParentBankList",
		method:"post",
		dataType:"json",
		async:false,
		data:{tag:'cl',nowCode:finOrgCode},
		success:function(data){
			if(data != null){
				for(var i = 0;i<data.length;i++){
					if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
					}
				}
			}
		}
     });
    selector.find('option').remove();
	 selector.append($(bankHtml));
	 $.ajax({
		    url:ctx+"/manage/queryParentBankInfo",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{finOrgCode:finOrgCode,flag:flag},
		    success:function(data){
	    		if(data != null){
	    			selector.val(data.content);
	    		}
	    	}
		});
	 //selector.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});
	 
	 getBranchBankList(selectorBranch,selector.val(),finOrgCode,flag);

	 return bankHtml;
}
//查询支行信息
function getBranchBankList(selectorBranch,pcode,finOrgCode,flag){
	selectorBranch.find('option').remove();
	selectorBranch[0];
	selectorBranch.append($("<option value=''>请选择</option>"));
	$.ajax({
		cache:true,
	    url:ctx+"/manage/queryBankListByParentCode",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:{faFinOrgCode:pcode,flag:flag,tag:'cl',nowCode:finOrgCode},
	    	success:function(data){
	    		if(data != null){
	    			for(var i = 0;i<data.length;i++){
						var coLevelStr='('+data[i].coLevelStr+')';
			
						var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
						if(data[i].finOrgCode==finOrgCode){
							option.attr("selected",true);
						}
						
						selectorBranch.append(option);
	    			}
	    		}
	    	}
	 });
	//selectorBranch[0].value=finOrgCode;
	// selector.append($(html));
	//selector.val(finOrgCode);
	 
	// selector.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});

	return true;
}

//给贷款银行赋值
function getParentBankInfo(finOrgCode,formId){
	 $.ajax({
	    url:ctx+"/manage/queryParentBankInfo",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:{finOrgCode:finOrgCode},
	    success:function(data){
    		if(data != null){
    			$("#"+formId).find("select[name='bank_type']").val(data.content);
    		}
    	}
	});
}
/**
 * 选择支行事件
 */
function subBankChange(){
	/*var secoLevel=$(this).find('option:selected').attr('coLevel');
	var levsls='01';
	if(levsls.indexOf(secoLevel)==-1){
		alert('您选择的当前银行为非入围银行，4月15日起将无法在系统内被选择！');
	}*/
}
var mCustCode='';
var custCode='';
//查询贷款信息
function getMortgageInfo(caseCode,isMainLoanBank,queryCustCodeOnly){
	/*var isMainLoanBank = $("#isMainLoanBank").val();*/
	if(isMainLoanBank=='0'&&!mCustCode){
		getMortgageInfo(caseCode,'1',true);
	}
	 $.ajax({
	    url:ctx+"/task/getMortgageInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
	    	success:function(data){	
	    		if(queryCustCodeOnly){
	    			if(data.content != null && data.content.custCode != null){
	    				mCustCode=data.content.custCode;
	    			}
	    			return true;
	    		}
	    		var finOrgCode = "";
	    		if(data.content != null && data.content.finOrgCode != null){
	    			finOrgCode = data.content.finOrgCode;
	    		}
	    		if(data.content != null && data.content.custCode != null){
	    			custCode=data.content.custCode;
	    		}
 				 //银行下拉列表
				if(isMainLoanBank == 1){
    				getGuestInfo("mortgageForm");

	  				getParentBank($("#mortgageForm").find("select[name='bank_type']"),$("#mortgageForm").find("select[name='finOrgCode']"),finOrgCode);
	  				
	  				$("#mortgageForm").find("select[name='bank_type']").change(function(){
	  					/*$("#mortgageForm").find("select[name='finOrgCode']").chosen("destroy");*/
				    	getBranchBankList($("#mortgageForm").find("select[name='finOrgCode']"),$("#mortgageForm").find("select[name='bank_type']").val(),"");
				    	/*$("#mortgageForm").find("select[name='finOrgCode']").unbind('change');
				    	$("#mortgageForm").find("select[name='finOrgCode']").bind('change',subBankChange);*/
				    }); 
				}else{
    				getGuestInfo("mortgageForm1");

					getParentBank($("#mortgageForm1").find("select[name='bank_type']"),$("#mortgageForm1").find("select[name='finOrgCode']"),finOrgCode);
	  				
	  				$("#mortgageForm1").find("select[name='bank_type']").change(function(){
	  					/*$("#mortgageForm1").find("select[name='finOrgCode']").chosen("destroy");*/
				    	getBranchBankList($("#mortgageForm1").find("select[name='finOrgCode']"),$("#mortgageForm1").find("select[name='bank_type']").val(),"");
				    	/*$("#mortgageForm1").find("select[name='finOrgCode']").unbind('change');
				    	$("#mortgageForm1").find("select[name='finOrgCode']").bind('change',subBankChange);*/
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
		    			$("#finOrgCode").val(data.content.finOrgCode).val();
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
		    			$("#finOrgCode").val(data.content.finOrgCode);
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

//加载已上传的附件信息
function getAttchInfo(){
	var caseCode = $("#caseCode").val();
	 $.ajax({
	    url:ctx+"/attachment/quereyAttachments",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,partCode:"ToEvaReport"},
	    	success:function(data){

				//将返回的数据进行包装
				for(var i=1;i<4;i++){
					$("#picContainer"+i).html("");
					var trStr = "";

					//实勘描述
					$.each(data.attList,function(index, value) {
						if(value.preFileCode==i){
							trStr+="<div id='picContainer"+value.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
							trStr+="<div class=\"preview span12\">";
							trStr+="<input type=\"hidden\" name=\"pic1\" id=\"pic1\" value=\""+i+"\" />";
							trStr+="<input type=\"hidden\" name=\"attachmentId\" value=\""+value.pkid+"\" />";

							trStr+="<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' alt=''>";
							trStr+="</div>";
							trStr+="<div class=\"delete span2\" style=\"margin-left: 85%; margin-top: -120px;\">";
							trStr+="<button onclick=\"romoveDiv('picContainers',"+value.pkid+");\" class=\"btn red\""; 
							trStr+="style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
							trStr+="<i class=\"icon-remove\"></i>";
							trStr+="</button>";
							trStr+="</div>";
							trStr+="</div>";
							
						}
					});
					$("#picContainer"+i).append(trStr);

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
					if(custCode == data[i].pkid){
						$("#"+formId).find("select[name='custCode']").append("<option cName='"+data[i].workUnit+"' value='"+data[i].pkid+"' selected >"+data[i].guestName+"</option>");
					}else{
						$("#"+formId).find("select[name='custCode']").append("<option cName='"+data[i].workUnit+"'value='"+data[i].pkid+"'>"+data[i].guestName+"</option>");
					}
				}
				guestCompanyReadOnly();
			}
		}
	});
}
function guestCompanyReadOnly(){
	var custJ=$("#mortgageForm1").find("select[name='custCode']");
	var custComJ=$("#mortgageForm1").find("input[name='custCompany']");
	if(custJ.val()==mCustCode){
		custComJ.val(custJ.find('option:selected').attr('cName')).attr('readonly','readonly');
	}else{
		custComJ.removeAttr('readonly');
	}
}

//确认报告为最终报告
function confirmReport(pkid){
	var isMainLoanBank = $("#isMainLoanBank").val();
	$.ajax({
		url:ctx+"/task/confirmReport",
		method:"post",
		dataType:"json",
		data:{pkid:pkid},
		success:function(data){
			if(data != null ){
				if(data.success){
					if(isMainLoanBank == 1){
						 searchReportList("table_list_4");
				//		getReportList("table_list_4","pager_list_4");
						$("#modal-form-report").modal("hide");
					}else{
						 searchReportList("table_list_6");
				//		getReportList("table_list_6","pager_list_6");
						$("#modal-form-report").modal("hide");
					}
					$("#report_btn_"+pkid).html("已确认");
					$("#report_btn_"+pkid).attr("disabled","disabled");
				}else{
					alert(data.message);
				}
			}
		}
	});
}

var list4 = null;
//获取报告单列表
function getReportList(tableId,pageId,isMainLoanBank){
	$("#"+tableId).jqGrid("GridUnload");
	$("#"+tableId).jqGrid({
    	url:ctx+"/quickGrid/findPage",
        datatype: "json",
        height: 300,
        autowidth: true,
        shrinkToFit: true,
        rowNum: 8,
        colNames: ['序号', '评估编号', '报告序列号', '报告类型', '当前状态','状态时间', '目标银行','是否最终报告', '是否接受报告结果'],
        colModel: [
            {name: 'PKID', index: 'PKID',  width: 60},
            {name: 'EVA_CODE', index: 'EVA_CODE', width: 140},
            {name: 'SERIAL_NUMBER', index: 'SERIAL_NUMBER', width: 140},
            {name: 'REPORT_TYPE', index: 'REPORT_TYPE', width: 140},
            {name: 'FEEDBACK', index: 'FEEDBACK', width: 140},
            {name: 'REPORT_RESPONSE_TIME', index: 'REPORT_RESPONSE_TIME', width: 140},
            {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME', width: 240},
            {name: 'IS_FINAL_REPORT', index: 'IS_FINAL_REPORT', width: 0,hidden:true},
            {name: 'accept', index: 'accept',align:'center',width: 150}
           
        ], 
        add: true,
        addtext: 'Add',
        pager: "#"+pageId,
        viewrecords: true,
        pagebuttions: true,
        hidegrid: false,
        recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
        pgtext : " {0} 共 {1} 页",
        search:false,
        postData:{
        	queryId:"queryToEvaReportPage",
        	search_caseCode:$("#caseCode").val(),
        	search_evaCode:$("#eva_code").val(),
        	search_isMainLoanBank:isMainLoanBank
        },
        gridComplete : function() { 
        	var ids = jQuery("#"+tableId).jqGrid('getDataIDs'); 
        						
        	for (var i=0; i<ids.length; i++) { 
        		var rowDatas = $("#"+tableId).jqGrid('getRowData', ids[i]); // 获取当前行
        							
        		var sub="<button  class='btn red' id='report_btn_'"+rowDatas['PKID']+"' onclick='confirmReport(\""+rowDatas['PKID']+"\")' style='width:90px;'>确认</button>";
        		if(rowDatas['IS_FINAL_REPORT'] == "1"){
            		 sub="<button  class='btn red' disabled='disabled' style='width:90px;'>已确认</button>";
        		}
        		jQuery("#"+tableId).jqGrid('setRowData', ids[i], {accept : sub}); 
        			
        	}
        }
        
    });
    $("#"+tableId).navGrid('#'+pageId,{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
    		"#"+pageId,
    		{
    			caption:"新增报告",	
    			onClickButton: function (){ 
    				getEvaCompany();
    				getAttchInfo();
    				$("#modal-form-report").modal('show');
    		}
    }); 
}
//加载报告单列表
function searchReportList(tableId){
	$("#"+tableId).setGridParam({
		"postData" : {
	       	queryId:"queryToEvaReportPage",
        	search_caseCode:$("#caseCode").val(),
        	search_evaCode:$("#eva_code").val(),
        	search_isMainLoanBank:$("#isMainLoanBank").val()
        },
		"page":1 
	}).trigger('reloadGrid');
}
//查询评估公司
function getEvaCompany(){
	$.ajax({
		url:ctx+"/manage/queryEvaCompany",
		method:"post",
		dataType:"json",
		data:{},
		success:function(data){
			if(data != null && data.length > 0){
				var foc = $("#reportForm").find("select[id='orgPricing']");
				foc.html("");
				for(var i=0;i<data.length;i++){
					foc.append("<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgName+"</option>");
				}
			}
		}
	});
}
//贷款结果界面信息
function getCompleteMortInfo(isMainLoanBank){
	
	var caseCode = $("#caseCode").val();
	 $.ajax({
	    url:ctx+"/task/getMortgageInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
	    	success:function(data){
				  
	    		if(data != null && data.content != null){
	    			if(isMainLoanBank == 1){
		    			$("#completeForm").find("input[name='pkid']").val(data.content.pkid);
		    			$("#completeForm").find("#comAmount").html(data.content.comAmount+"万元");
		    			$("#completeForm").find("#comDiscount").html(data.content.comDiscount+"折");
		    			$("#completeForm").find("input[name='finOrgCode']").val(data.content.finOrgCode);
		    			$("#completeForm").find("input[name='apprDate']").val(data.content.apprDate);
		    			if(data.content.lastLoanBank != null){
			    			$("#completeForm").find("input[name='lastBankSub']").attr("checked","checked");
		    			}

	    			}else{
	    				$("#completeForm1").find("input[name='pkid']").val(data.content.pkid);
		    			$("#completeForm1").find("#comAmount").html(data.content.comAmount+"万元");
		    			$("#completeForm1").find("#comDiscount").html(data.content.comDiscount+"折");
		    			$("#completeForm1").find("input[name='finOrgCode']").val(data.content.finOrgCode);
		    			$("#completeForm1").find("input[name='apprDate']").val(data.content.apprDate);
		    			if(data.content.lastLoanBank != null){
			    			$("#completeForm1").find("input[name='lastBankSub']").attr("checked","checked");

		    			}
	    			}
	    		}
	    	}
	  });
}
//提醒清单
function getReminderList(tableId,pageId){
	$("#"+tableId).jqGrid("GridUnload");
	$("#"+tableId).jqGrid({
    	url:ctx+"/quickGrid/findPage",
        datatype: "json",
        height: 300,
        autowidth: true,
        shrinkToFit: true,
		multiselect : true,
        rowNum: 8,
        colNames: ['提醒事项', '备注'],
        colModel: [
            {name: 'REMIND_ITEM', index: 'REMIND_ITEM', width: '40%'},
            {name: 'COMMENT', index: 'COMMENT', width: '55%'}
        ], 
        add: true,
        addtext: 'Add',
        pager: "#"+pageId,
        viewrecords: true,
        pagebuttions: true,
        hidegrid: false,
        recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
        pgtext : " {0} 共 {1} 页",
        search:false,
        postData:{
        	queryId:"queryToReminderList",
        	search_partCode:$("#partCode").val()
        }   
    });
}
var grid = null;
var accPricing = null;
var accPricingIds = null;
//查询询价列表
function getPricingList(tableId,pageId,isMainLoanBank){
	   $("#"+tableId).jqGrid("GridUnload");
	   accPricing == null;
	   $("#"+tableId).jqGrid({
	    	url:ctx+"/quickGrid/findPage",
	        datatype: "json",
	        mtype:"GET",
	        height: 300,
	        autowidth: true,
	        shrinkToFit: true,
	        rowNum: 8,
	        colNames: ['询价ID', '评估编号', '询价状态', '询价时间', '询价结果','单价（元）','总价（万元）','目标银行','申请编号','是否确认','确认序号','结果编码','操作'],
	        colModel: [
	       //     {name: 'PKID', formatter:'checkbox',formatoptions:{disabled:false},checked:false,index: 'PKID',  width: 30},
	            {name: 'PKID',index: 'PKID',hidden:true},
	            {name: 'EVA_CODE', index: 'EVA_CODE'},
	            {name: 'STATUS', index: 'STATUS'},
	            {name: 'ARISE_TIME', index: 'ARISE_TIME'},
	            {name: 'RESULT', index: 'RESULT'},
	            {name: 'UNIT_PRICE', index: 'UNIT_PRICE'},
	            {name: 'TOTAL_PRICE', index: 'TOTAL_PRICE'},
	            {name: 'FIN_ORG_NAME', index: 'FIN_ORG_NAME'},
	            {name: 'APPLY_CODE', index: 'APPLY_CODE',hidden:true, width: 0},
	            {name: 'IS_FINAL', index: 'IS_FINAL',hidden:true, width: 0},
	            {name: 'COMFIRM_SEQ', index: 'COMFIRM_SEQ',hidden:true, width: 0},
	            {name: 'RESULT_CODE', index: 'RESULT_CODE', hidden:true,width: 0},

	            {name: 'act', index: 'act'}
	            
	        ],
	        add: true,
	        addtext: 'Add',
	        pager: "#"+pageId,
	        viewrecords: true,
	        pagebuttions: true,
	        hidegrid: false,
	        recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
	        pgtext : " {0} 共 {1} 页",
	        search:false,
	        postData:{
	        	queryId:"queryToEguPricingPage",
	        	search_caseCode:$("#caseCode").val(),
	        	search_isMainLoanBank:isMainLoanBank
	        },
	        gridComplete : function() { 
	        	accPricingIds = jQuery("#"+tableId).jqGrid('getDataIDs'); 
	        						
	        	for (var i=0; i<accPricingIds.length; i++) { 
	        		var rowDatas = $("#"+tableId).jqGrid('getRowData', accPricingIds[i]); // 获取当前行
	        		if(rowDatas['IS_FINAL'] == "1"){
	        			accPricing = rowDatas;
	        		}
	        		var sub="<button  class='btn red' id='"+tableId+"_btn_"+rowDatas['PKID']+"' onclick='accOrCancelPricing(\""+tableId+"\",\""+rowDatas['PKID']+"\",\""+rowDatas['TOTAL_PRICE']+"\")' style='width:50px;'>";
	        		if(rowDatas['IS_FINAL'] == "1"){
	        			sub += "取消";
	        		}else{
	        			sub += "接受";
	        		}
	        		sub += "</button>&nbsp;";
	        		if(rowDatas['RESULT_CODE'] == "5"){
		        		sub +="<button  class='btn red' style='width:70px;' disabled >发起异议</button>";
	        		}else{
		        		sub +="<button onclick='showApplyModal(\""+rowDatas['EVA_CODE']+"\",\""+rowDatas['APPLY_CODE']+"\",\""+rowDatas['TOTAL_PRICE']+"\")' class='btn red' style='width:70px;'>发起异议</button>";
	        		}
	        		jQuery("#"+tableId).jqGrid('setRowData', accPricingIds[i], {act : sub}); 
	        			
	        	}
	        },		
	        onSelectRow : function(rowid,status) {
//				var rowData = $("#"+tableId).jqGrid('getRowData', rowid);
//				$("#eva_code").val(rowData['EVA_CODE']);
			}
	    });
	    $("#"+tableId).navGrid('#'+pageId,{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
	    		"#"+pageId,
    		{
    			caption:"新增询价",	
    			onClickButton: function (){ 
    				$("#modal-form").modal('show');
    				$.ajax({
 				    	url:ctx+"/task/findToPropertyInfo",
 						method:"post",
 						dataType:"json",
 						data:{caseCode:$("#caseCode").val()},
 						success:function(data){
 							if(data != null && data.content != null){
 								$("#residence_name").val(data.content.propertyAddr);
 								$("#total_floor").val(data.content.totalFloor);
 								$("#floor").val(data.content.locateFloor);
 							}
 						}
 				  });
    				if($("#input[name='optionsRadios']:checked").val()==0){
    					$("#direct_launch_div").hide();
    				}else{
    					$("#direct_launch_div").show();
    				}
    				if(popInited)return true;
    				popInited=true;
    				$("input[name='optionsRadios']").click(function(){
    					 if($(this).val()==0){
    						 $("#direct_launch_div").hide();
    						 $("#addToEguPricingForm").find("input").each(function(){
    							 if($(this).attr("id")!="code" && $(this).attr("name")!="optionsRadios"){
                					 $(this).attr("disabled","disabled");
    							 }
            				 });
    						 $("#addToEguPricingForm").find("select").each(function(){
            					 $(this).attr("disabled","disabled");
            				 });
    					 }else{
    						 $("#direct_launch_div").show();
    						 $("#addToEguPricingForm").find("input").each(function(){
                				 $(this).removeAttr("disabled");
            				 });
    						 $("#addToEguPricingForm").find("select").each(function(){
                				 $(this).removeAttr("disabled");
            				 });
    					 }
    				 });
  				   
  				   
  				   
  				 //银行下拉列表
  				getParentBank($("#addToEguPricingForm").find("select[name='bank_type']"),$("#bank_branch_id"),"","egu");
  				
  				$("#addToEguPricingForm").find("select[name='bank_type']").change(function(){
  					/*$("#bank_branch_id").chosen("destroy");*/
			    	getBranchBankList($("#bank_branch_id"),$("#addToEguPricingForm").find("select[name='bank_type']").val(),"","egu");
			    }); 
    		}
	    }); 
}
//加载询价列表
function searchPricingList(table_list_id){
	$("#"+table_list_id).setGridParam({
		"postData" : {
			queryId:"queryToEguPricingPage",
        	search_caseCode:$("#caseCode").val(),
        	search_isMainLoanBank:$("#isMainLoanBank").val()
        },
		"page":1 
	}).trigger('reloadGrid');
}
//绑定评估编号
function bindEvaCode(){
	$.ajax({
		url:ctx+"/task/bindEvaCode",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val(),evaCode:$("#code").val(),isMainLoanBank:$("#isMainLoanBank").val()},
		success:function(data){
			if(data.message != ""){
				alert(data.message);
				$("#modal-form").modal("hide");
				var isMainLoanBank = $("#isMainLoanBank").val();
				if(isMainLoanBank == 1){
					searchPricingList("table_list_1");
	    		//	getPricingList("table_list_1","pager_list_1");
				}else{
					searchPricingList("table_list_3");
	    		//	getPricingList("table_list_3","pager_list_3");
				}
			}
		}
	});
}
//提交完成按揭贷款流程
function submitMortgage(){
	$.ajax({
		url:ctx+"/task/submitMortgage",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val(),taskId:$("#taskId").val(),processInstanceId:$("#processInstanceId").val(),isMainLoanBank:$("#isMainLoanBank").val()},
		success:function(data){
			alert(data.message);
			if(data.success){
				//window.location.href=ctx+"/task/myTaskList?"+new Date().getTime();
				caseTaskCheck();
			}
		}
	});
}
//验证备件是否上传
function checkAttUp(attDiv){
	var flag = true;
	attDiv.each(function(){
		var pic = $(this).find("img");
		if(pic.length == 0){
			flag = false;
			return false;
		}else{
			flag = true;
		}
	});
	if(flag == false){
		alert("备件未上传完整！");
	}
	return flag;
}
//保存操作步骤
function saveStep(){
	$.ajax({
		url:ctx+"/task/saveMortStep",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val(),isMainLoanBank:$("#isMainLoanBank").val(),step:stepIndex},
		success:function(data){
			if(!data.success){
				alert(data.message);
			}
		}
	});
}

function checkReportAtt(){
	var flag = true;
	for(var i=1;i<4;i++){
		var length = $("#picContainer"+i).find("img").length;
		if(length == 0) {
			alert("备件未完全上传！");
			flag = false;
			break;
		} else {
			flag = true;
		}
	}
	return flag;
}
var stepIndex = 0;

$(document).ready(function () {
	/*$("#bank_branch_id").change(subBankChange);*/
	 $("input[name=optionsRadios]").each(function(){
		 $(this).click(function(){
				$("input[type='text']").each(function(){
					$(this).css("border-color","#e5e6e7");	
				});
					
				$("select").each(function(){
					$(this).css("border-color","#e5e6e7");
				});
		 });
	 });

	 $("#toEguPricingSubmitBtn").click(function(){
		 var rdo = $("input[name=optionsRadios]:checked");
		 if(rdo !=null && rdo.val() == 0){

			if($("#code").val() == ""){
				alert("请输入移动端收到的评估编号！");
				return ;
			}
			bindEvaCode();
		 }else{
			 assess();
		 }
		 
	 });
	 
	 //关闭询价输入窗口，刷新询价列表
	 $("#pricingClose").click(function(){
		 var isMainLoanBank = $("#isMainLoanBank").val();
		 if(isMainLoanBank == 1){
			 searchPricingList("table_list_1");
			// getPricingList("table_list_1","pager_list_1");
		 }else{
			 searchPricingList("table_list_3");
			// getPricingList("table_list_3","pager_list_3");
		 }
	 });
	 
	 //关闭报告单发起窗口，刷新报告单列表
	 $("#reportClose").click(function(){
		 var isMainLoanBank = $("#isMainLoanBank").val();
		 if(isMainLoanBank == 1){
			 searchReportList("table_list_4");
		//	 getReportList("table_list_4","pager_list_4");
		 }else{
			 searchReportList("table_list_6");
		//	 getReportList("table_list_6","pager_list_6");
		 }
	 });
	 
	 //异议发起
	 $("#subApplyBtn").click(function(){
		 disagree();
	 });
	 //提交流程
	 $("#sub").click(function(){
		 submitMortgage();
	});
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
	$("#wizard").steps({labels:{
			next:"下一步",
			previous:"上一步",
			finish:"提交"
	 	},
	 	onInit:function(event, currentIndex){
	 		$("#winzard").find("li").css("width","16%");
	 	},
		startIndex:step,
		showFinishButtonAlways:false,
	 	enableCancelButton:false,
	 	onStepChanging: function (event, currentIndex, newIndex){
	 		if(currentIndex == 0){
	 			/*if(accPricing == null){
	 				alert("请先接受询价结果！");
	 				return false;
	 			}*/
	 			if(accPricing){
	 				$("#eva_code").val(accPricing['EVA_CODE']);
	 			}
	
	 		}else if(currentIndex == 2){
	 			var flag = false;
	 			if(checkMortgageForm($("#mortgageForm"))){
		 			saveMortgage($("#mortgageForm"));
		 			flag = true;
	 			}
	 			return flag;
	 		}else if(currentIndex == 3 ){
		 		var rowId=$("#table_list_1").jqGrid("getGridParam","selrow");
				var rowData = $("#table_list_1").jqGrid('getRowData', rowId);
				if(rowData['TOTAL_PRICE'] == ""){
					alert("询价结果还未返回，不能发起报告！");
					return false;
				}
	 			deleteAndModify();
	 			return checkAttUp($(".att_first"));

	 		}else if (currentIndex==4){
	 			var rowId=$("#table_list_1").jqGrid("getGridParam","selrow");
				var rowData = $("#table_list_1").jqGrid('getRowData', rowId);
				if(rowData['TOTAL_PRICE'] == ""){
					alert("询价结果还未返回，不能发起报告！");
					return false;
				}
				confirmPricing();
				return true;
	 		}

	 		return true;
	 	},
	 	onStepChanged: function (event, currentIndex, priorIndex){
	 		stepIndex = currentIndex;
	 		if(currentIndex > step){
		 		saveStep();
	 		}
	 		if(currentIndex == 1){
	 			getReminderList("table_list_2","pager_list_2");
	 		}else if(currentIndex == 2){
		 		getMortgageInfo($("#caseCode").val(),1);
	 		}else if(currentIndex == 3){
	 		//	getExplPicByhouseCode() ;
	 		}else if(currentIndex == 4){

	 			getReportList("table_list_4","pager_list_4",1);
	 		}else if(currentIndex == 5){
	 			getCompleteMortInfo(1);
	 		}
	 	},
	 	onFinished: function (event, currentIndex)
		{
	 		completeMortgage($("#completeForm"));	
	    }
	});
	
	$("#wizard1").steps({labels:{
		next:"下一步",
		previous:"上一步",
		finish:"提交"
 	},
 	showFinishButtonAlways:false,
 	enableCancelButton:false,
 	startIndex:step1,
 	onInit:function(event, currentIndex){
 		
 	},
 	onStepChanging: function (event, currentIndex, newIndex){
 		if(currentIndex == 0){
 			/*if(accPricing == null){
 				alert("请先接受询价结果！");
 				return false;
 			}*/
 			if(accPricing){
 				$("#eva_code").val(accPricing['EVA_CODE']);
 			}
 			/*if(accPricingIds.length != 0 && accPricing == null){
 				alert("请先接受询价结果！");
 				return false;
 			}else if(accPricing != null){
				$("#eva_code").val(accPricing['EVA_CODE']);
 			}*/

 		}else if(currentIndex == 2 ){
 			var flag = false;
 			if(checkMortgageForm($("#mortgageForm1"))){
	 			saveMortgage($("#mortgageForm1"));
	 			flag = true;
 			}
 			return flag;
 		}else if(currentIndex == 3 ){
 			deleteAndModify();
 			return checkAttUp($(".att_second"));

 		}else if (currentIndex==4){
	 		var rowId=$("#table_list_3").jqGrid("getGridParam","selrow");
			var rowData = $("#table_list_3").jqGrid('getRowData', rowId);
			if(rowData['TOTAL_PRICE'] == ""){
				alert("询价结果还未返回，不能发起报告！");
				return false;
			}
			confirmPricing();
			return true;
 		}
 		
 		return true;
 	},
 	onStepChanged: function (event, currentIndex, priorIndex){
 		stepIndex = currentIndex;
 		if(currentIndex > step){
	 		saveStep();
 		}
 		if(currentIndex == 1){
 			getReminderList("table_list_5","pager_list_5");
 		}else if(currentIndex == 2){
	 		getMortgageInfo($("#caseCode").val(),0);
 		}else if(currentIndex == 3){
 		//	getExplPicByhouseCode() ;
 		}else if(currentIndex == 4){
 			getReportList("table_list_6","pager_list_6",0);
 		}else if(currentIndex == 5){
 			getCompleteMortInfo(0);
 		}
 	},
 	onFinished: function (event, currentIndex)
    {
 		completeMortgage($("#completeForm1"));
 		return true;
    }
});
//    $("#finOrgCode").change(function(){
//    	if($("#finOrgCode").val() == "P00021"){
//    		$("#priceInfo").hide();
//    	}
//    });
    $("#reportSubBtn").click(function(){
    	if($("#reportType").val()==""){
    		alert("请选择报告类型！");
    		return ;
    	}
    	if(getUploadPicInfo()){
    		return;
    	};
    	if(attachmentIds.length==0&&preFileAdress.length==0){
    		alert("图片数据不能为空！");
        	return;
    	}
    	var picDiv=$("div[name='allPicDiv2']");
	    var spans =$("input[name='preFileAdress1']");
	    if(spans.length < picDiv.length) {
	    	alert("你有未上传的完成的文件，请稍候再试！");
	    	return;
	    }
    	if(!checkReportAtt()){
    		return;
    	}
    	var reportType = $("#reportType").val();
    	var evaCode = $("#eva_code").val();
    	var caseCode = $("#caseCode").val();
    	var isMainLoanBank = $("#isMainLoanBank").val();
    	var url = "";
    	var finOrgCode = $("#orgPricing").val();
    	if(finOrgCode!=null && finOrgCode=="P00021"){
        	if(reportType == "1"){  //预估单
        		url = ctx+"/remote/egu/"+evaCode+"/prereport";
        	}else if(reportType == "2"){ //询价单
        		url = ctx+"/remote/egu/"+evaCode+"/inquiryreport";
        	}else if(reportType == "3"){   //评估报告
        		url = ctx+"/remote/egu/"+evaCode+"/report";
        	}
		//	$("#reportSubBtn").attr("disabled",true);

        	$.ajax({
        		url:url,
        		method:"post",
        		dataType:"json",
        		data:[
        		    {name:"caseCode",value:caseCode},
        		    {name:"isMainLoanBank",value:isMainLoanBank},
        			{name:"finOrgCode",value:finOrgCode},
        			{name:"reportType",value:reportType},
        			{name:"evaCode",value:evaCode},
        			{name:"preFileAdress",value:preFileAdress},
        			{name:"picTag",value:picTag},
        			{name:"picName",value:picName},
        			{name:"attachmentIds",value:attachmentIds}
        		], 
	   		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  

                     $.unblockUI(); 
  
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
        			//$("#reportSubBtn").attr("disabled",false);

        			if(data.success){
        				 if(isMainLoanBank == 1){
        					 searchReportList("table_list_4");
        				//	 getReportList("table_list_4","pager_list_4");
        				 }else{
        					 searchReportList("table_list_6");
        				//	 getReportList("table_list_6","pager_list_6");
        				 }
        				$("#modal-form-report").modal('hide');
        			}
            		alert(data.message);
        			
        		}
        	});
    	}else{
    		if(confirm("点击该按钮将会启动线下发起报告单流程，请确认输入正确！")){
	        	$.ajax({
	        		url:ctx+"/task/submitEvaReport",
	        		method:"post",
	        		dataType:"json",
	        		data:[
	        		    {name:"caseCode",value:caseCode},
	        		    {name:"isMainLoanBank",value:isMainLoanBank},
	        			{name:"finOrgCode",value:finOrgCode},
	        			{name:"reportType",value:reportType},
	        			{name:"evaCode",value:evaCode},
	        			{name:"preFileAdress",value:preFileAdress},
	        			{name:"picTag",value:picTag},
	        			{name:"picName",value:picName},
	        			{name:"attachmentIds",value:attachmentIds},
	        			{name:"taskId",value:$("#taskId").val()},
	        			{name:"expectRate",value:$("#expectRate").val()},
	        			{name:"comment",value:$("#comment").val()},
	        			{name:"processInstanceId",value:$("#processInstanceId").val()}
	        		],  
		   		    beforeSend:function(){  
	    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	    				$(".blockOverlay").css({'z-index':'9998'});
	                },
	                complete: function() {  
	
	                     $.unblockUI(); 
	  
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
	        		//	$("#reportSubBtn").attr("disabled",false);
						//caseTaskCheck();
	
	        			if(data.success){
	        				 if(isMainLoanBank == 1){
	        					 searchReportList("table_list_4");
	        				//	 getReportList("table_list_4","pager_list_4");
	        				 }else{
	        					 searchReportList("table_list_6");
	        				//	 getReportList("table_list_6","pager_list_6");
	        				 }
	        				$("#modal-form-report").modal('hide');
	        				//window.location.href=ctx+"/task/myTaskList?"+new Date().getTime();
	
	        			}
	            		alert(data.message);
	        			
	        		}
	        	});
    		}
    	}
    });
    
   // getPricingList("table_list_1","pager_list_1");
 
 });