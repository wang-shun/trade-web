
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
		window.wxc.alert("别墅地上建筑面积不能为空！")
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
	$("input,select").css("border-color","#ccc");	
	if(formId.find("select[name='custCode']").val() == "" || formId.find("select[name='custCode']").val() == null){
		window.wxc.alert("主贷人为必填项！");
		formId.find("select[name='custCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='processStart']").val() == "" || formId.find("input[name='processStart']").val() == null){
		window.wxc.alert("请先完成派单至信贷员任务！");
		formId.find("input[name='processStart']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='mortTotalAmount']").val() == ""){
		window.wxc.alert("贷款总额为必填项！");
		formId.find("input[name='mortTotalAmount']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='mortType']").val() == ""){
		window.wxc.alert("贷款类型为必填项！");
		formId.find("select[name='mortType']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comAmount']").val() == ""){
		window.wxc.alert("商贷金额为必填项！");
		formId.find("input[name='comAmount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val() == ""){
		window.wxc.alert('商贷利率折扣为必填项');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val()<0.5||formId.find("input[name='comDiscount']").val()>1.5){
		window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val()>=0.5&&formId.find("input[name='comDiscount']").val()<=1.5
			&&new RegExp("/^[01]{1}\.{1}\d{3,}$/").test(formId.find("input[name='comDiscount']").val())){
		window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(isNaN(formId.find("input[name='comDiscount']").val())){
		window.wxc.alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comYear']").val() == ""){
		window.wxc.alert("商贷年限为必填项！");
		formId.find("input[name='comYear']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='lendWay']").val() == "" || formId.find("select[name='lendWay']").val() == null){
		window.wxc.alert("放款方式为必填项！");
		formId.find("select[name='lendWay']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerName']").val() == ""){
		window.wxc.alert("信贷员为必填项！");
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;		
	}/*else if(formId.find("input[name='loanerId']").val() == ""){
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;		
	}*/else if(formId.find("input[name='loanerPhone']").val() == ""){
		window.wxc.alert("信贷员电话为必填项！");
		formId.find("input[name='loanerPhone']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerPhone']").val() != "" && !(/^0?1[3|4|5|7|8][0-9]\d{8}$/.test(formId.find("input[name='loanerPhone']").val()))){
		formId.find("input[name='loanerPhone']").css("border-color","red");
		window.wxc.alert("信贷员手机号码输入错误！");
		return false;
	}else if(formId.find("input[name='signDate']").val() == "" ){
		window.wxc.alert("签约时间为必填项！");
		formId.find("input[name='signDate']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='isTmpBank'][value='0']").is(":checked") && formId.find("input[name='recLetterNo']").val() == ""){
		window.wxc.alert("推荐函编号为必填项！");
		formId.find("input[name='recLetterNo']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='remindTime']").val() != "" && formId.find("input[name='supContent']").val()==""){
		window.wxc.alert("请输入补件名称！");
		return false;
	}else if(formId.find("input[name='supContent']").val() != "" && formId.find("input[name='remindTime']").val()==""){
		window.wxc.alert("请输入补件时间！");
		return false;
	}
	
	if(formId.find("select[name='finOrgCode']").val() == ""){
		window.wxc.alert("贷款支行为必填项！");
		formId.find("select[name='finOrgCode']").css("border-color","red");
		return false;
    }
	if($("#finOrgCode").find('option:selected').attr('coLevel') == "0"){
		/*if(formId.find("input[name='loanerId']").val() == ""){
			alert("入围银行的信贷员请从合作银行中选择！");
			formId.find("input[name='loanerName']").css("border-color","red");
			return false;
		}*/
	}else{
		//formId.find("input[name='loanerId']").val("");
		//formId.find("input[name='loanerOrgCode']").val("");
		//formId.find("input[name='loanerOrgId']").val("");
	}
	if(!formId.find("input[name='isTmpBank'][value='1']").prop('checked')){
		if(afterTimeFlag){
			if (formId.find("input[name='recLetterNo']").val()==""){
				formId.find("input[name='recLetterNo']").css("border-color","red");
				return false;
			}
		}		
	}else{
		if(formId.find("input[name='tmpBankReason']").val() == ""){
			formId.find("input[name='tmpBankReason']").css("border-color","red");
			return false;
		}
	}
	var prfAmoutStr=formId.find("input[name='prfAmount']").val();
	var prfAmount=prfAmoutStr==''?0:parseFloat(prfAmoutStr);
	var mortTotalAmount=parseFloat(formId.find("input[name='mortTotalAmount']").val());
	var comAmount=parseFloat(formId.find("input[name='comAmount']").val());
	
	if((mortTotalAmount-prfAmount).toFixed(5)!=comAmount){
		window.wxc.alert('贷款总额必须等于商贷和公积金之和');
    	return false;
	}
	return true;
}


//派单流程提交前  校验
function beforeSendLoanerProcess(formId){	
	$("input,select").css("border-color","#ccc");
	
	if(formId.find("select[name='custCode']").val() == "" || formId.find("select[name='custCode']").val() == null){
		window.wxc.alert("主贷人为必填项！");
		formId.find("select[name='custCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='mortTotalAmount']").val() == ""){
		window.wxc.alert("贷款总额为必填项！");
		formId.find("input[name='mortTotalAmount']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='mortType']").val() == ""){
		window.wxc.alert("贷款类型为必填项！");
		formId.find("select[name='mortType']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comAmount']").val() == ""){
		window.wxc.alert("商贷金额为必填项！");
		formId.find("input[name='comAmount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val() == ""){
		window.wxc.alert('商贷利率折扣为必填项');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val()<0.5||formId.find("input[name='comDiscount']").val()>1.5){
		window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comDiscount']").val()>=0.5&&formId.find("input[name='comDiscount']").val()<=1.5
			&&new RegExp("/^[01]{1}\.{1}\d{3,}$/").test(formId.find("input[name='comDiscount']").val())){
		window.wxc.alert('商贷利率折扣应该不大于1.50,不小于0.50,小数位不超过两位');
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(isNaN(formId.find("input[name='comDiscount']").val())){
		window.wxc.alert("请输入0.50~1.50之间的合法数字,小数位不超过两位");
		formId.find("input[name='comDiscount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='comYear']").val() == ""){
		window.wxc.alert("商贷年限为必填项！");
		formId.find("input[name='comYear']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='lendWay']").val() == "" || formId.find("select[name='lendWay']").val() == null){
		window.wxc.alert("放款方式为必填项！");
		formId.find("select[name='lendWay']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerName']").val() == ""){
		window.wxc.alert("信贷员为必填项！");
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;
		
	}else if(formId.find("input[name='loanerId']").val() == ""){
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;
		
	}else if(formId.find("input[name='loanerPhone']").val() == ""){
		window.wxc.alert("信贷员电话为必填项！");
		formId.find("input[name='loanerPhone']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerPhone']").val() != "" && !(/^0?1[3|4|5|7|8][0-9]\d{8}$/.test(formId.find("input[name='loanerPhone']").val()))){
		formId.find("input[name='loanerPhone']").css("border-color","red");
		window.wxc.alert("信贷员手机号码输入错误！");
		return false;
	}else if(formId.find("input[name='signDate']").val() == "" ){
		window.wxc.alert("签约时间为必填项！");
		formId.find("input[name='signDate']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='isTmpBank'][value='0']").is(":checked") && formId.find("input[name='recLetterNo']").val() == ""){
		window.wxc.alert("推荐函编号为必填项！");
		formId.find("input[name='recLetterNo']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='remindTime']").val() != "" && formId.find("input[name='supContent']").val()==""){
		window.wxc.alert("请输入补件名称！");
		return false;
	}else if(formId.find("input[name='supContent']").val() != "" && formId.find("input[name='remindTime']").val()==""){
		window.wxc.alert("请输入补件时间！");
		return false;
	}
	
	if(formId.find("select[name='finOrgCode']").val() == ""){
		window.wxc.alert("贷款支行为必填项！");
		formId.find("select[name='finOrgCode']").css("border-color","red");
		return false;
    }
	if($("#finOrgCode").find('option:selected').attr('coLevel') == "0"){
		/*if(formId.find("input[name='loanerId']").val() == ""){
			alert("入围银行的信贷员请从合作银行中选择！");
			formId.find("input[name='loanerName']").css("border-color","red");
			return false;
		}*/
	}else{
		//formId.find("input[name='loanerId']").val("");
		//formId.find("input[name='loanerOrgCode']").val("");
		//formId.find("input[name='loanerOrgId']").val("");
	}
	if(!formId.find("input[name='isTmpBank'][value='1']").prop('checked')){
		if(afterTimeFlag){
			if (formId.find("input[name='recLetterNo']").val()==""){
				formId.find("input[name='recLetterNo']").css("border-color","red");
				return false;
			}
		}		
	}else{
		if(formId.find("input[name='tmpBankReason']").val() == ""){
			formId.find("input[name='tmpBankReason']").css("border-color","red");
			return false;
		}
	}
	var prfAmoutStr=formId.find("input[name='prfAmount']").val();
	var prfAmount=prfAmoutStr==''?0:parseFloat(prfAmoutStr);
	var mortTotalAmount=parseFloat(formId.find("input[name='mortTotalAmount']").val());
	var comAmount=parseFloat(formId.find("input[name='comAmount']").val());
	
	if((mortTotalAmount-prfAmount).toFixed(5)!=comAmount){
		window.wxc.alert('贷款总额必须等于商贷和公积金之和');
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
	        			
	        			window.wxc.alert(data.message);
						if(!!data.success){
							$("#modal-form").modal("hide");
						}
	        		}
		
					if(isMainLoanBank == 1){
						//searchPricingList("table_list_1");
		    			getPricingList("table_list_1","pager_list_1",1);
					}else{
						//searchPricingList("table_list_3");
		    			getPricingList("table_list_3","pager_list_3",0);
					}
	    		}	    		
	    	}
	    });
	}
}
//弹出异议申请窗口
function showApplyModal(evaCode,applyCode,totalPrice){
	if(totalPrice==""||totalPrice == null){
		window.wxc.alert("询价结果未返回，不能发起异议！");
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

					window.wxc.alert(data.message);
					if(data.success){
		    			if(isMainLoanBank == 0){
		    				//searchPricingList("table_list_3");
		        			getPricingList("table_list_3","pager_list_3",0);
		    			}else{
		    				//searchPricingList("table_list_1");
		        			getPricingList("table_list_1","pager_list_1",1);
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
					window.wxc.error(data.message);
				}	
			}
		}
	});
}
//接受/取消接受询价结果
function accOrCancelPricing(tableId,pkid,totalPrice){
	if(totalPrice==""||totalPrice == null){
		window.wxc.alert("询价结果未返回，不能接受询价！");
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
    				//searchPricingList("table_list_3");
        			getPricingList("table_list_3","pager_list_3",0);
    			}else{
    				//searchPricingList("table_list_1");
        			getPricingList("table_list_1","pager_list_1",1);
    			}
			}else{
				window.wxc.error(data.message);	
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
    				//searchPricingList("table_list_3");
        			getPricingList("table_list_3","pager_list_3",0);
    			}else{
    				//searchPricingList("table_list_1");
        			getPricingList("table_list_1","pager_list_1",1);
    			}
			}else{
				window.wxc.error(data.message);	
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
			async:false,
			dataType:"json",
			data:form.serialize(),
			success:function(data){
				if(!data.success){
					window.wxc.error(data.message);
				}
			}
		});
	
}

//完成贷款审批
function completeMortgage(form){	
	

	 var tmpBankCheckflag = $('#fl_is_tmp_bank').val() == '1';
/*		if(tmpBankCheckflag && $("#tmpBankStatus").val() != '3'){ 	
		window.wxc.alert("信贷员接单银行审批未完成或不通过！");
		return;
	}*/
	
	/*
	 * @author：zhuody
	 * @Date：2017年3月24日
	 * @Des：以前临时银行审批未结束之前，不许提交商贷流程，现在取消  即下面的判断取消
	 * $("#tmpBankStatus").val() != '1'：   = 1 表示临时银行审批通过   3表示分级银行审批通过
	 * 并且不是信息管理员的情况下需要判断审核状态
	 */	
	if($("#adminLoanerProcess").val() != "adminLoanerProcess"){ 
		if(tmpBankCheckflag){
			if($("#tmpBankStatus").val() != '1'){
				window.wxc.alert("临时银行审批未完成或不通过！");
				return;
			}
		}else{
			//非临时银行
			if(!($("#tmpBankStatus").val() == '3'  ||  $("#stateInBank").val() == 'MORT_APPROVED')){ 	
				window.wxc.alert("信贷员接单银行审批未完成或不通过！");
				return;
			}
		}
	}
	
	var pkid=form.find("input[name='pkid']").val();

	var lastBankSub = form.find("input[name='lastBankSub']:checked");
	if(pkid == null || pkid == "" ){
		window.wxc.alert("贷款信息不存在！");
		return;
	}
	
	var finOrgCode = form.find("input[name='finOrgCode']").val();
	if(finOrgCode == null || finOrgCode == ""){
		alert("请先选择贷款银行和支行！");
		return;
	}
	
	if(lastBankSub.val() == undefined){
		window.wxc.alert("最终贷款银行未确认！");
		return;
	}
	
	// 审批时间校验
	var apprDate=form.find("input[name='apprDate']").val();
	if($.isBlank(apprDate)) {
		window.wxc.alert("审批时间为必选项！");
		return;
	}

	var lastLoanBank = null;
	if(lastBankSub.val() != undefined){
		lastLoanBank = form.find("input[name='finOrgCode']").val();
	}
	//isMainLoanBank
	$.ajax({
		url:ctx+"/task/completeMortgage",
		method:"post",
		dataType:"json",
		data:{pkid:pkid,caseCode:$("#caseCode").val(),apprDate:$("#apprDate").val(),lastLoanBank:lastLoanBank,partCode:$("#partCode").val(),check:tmpBankCheckflag},
		success:function(data){
			if(data.success){
				if('caseDetails'==source){
					if(data.message){
						//TODO 如果作为最终银行提交，自动结束候选银行或者主选银行的的派单流程
						window.close();
						window.opener.callback();
					}else{
						window.wxc.success('保存成功');
					}
				}else{
					submitMortgage();
				}
			}else{
				window.wxc.error(data.message);
			}
		}
	});
}


//查询分行信息
function getParentBank(selector,selectorBranch,finOrgCode,tag,flag){
	var bankHtml = "<option value=''>请选择</option>";
	var param = {nowCode:finOrgCode};
	if(tag == 'cl'){
		param.tag = 'cl';
	}
    $.ajax({
    	cache:true,
    	url:ctx+"/manage/queryParentBankList",
		method:"post",
		dataType:"json",
		async:false,
		data:param,
		success:function(data){
			if(data != null){
				for(var i = 0;i<data.length;i++){
					if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
					}
				}
			}
		},
       error:function(e){
    	   window.wxc.error(e);
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
	 
	 getBranchBankList(selectorBranch,selector.val(),finOrgCode,tag,flag);

	 return bankHtml;
}
//查询支行信息
function getBranchBankList(selectorBranch,pcode,finOrgCode,tag,flag){
	selectorBranch.find('option').remove();
	selectorBranch[0];
	selectorBranch.append($("<option value=''>请选择</option>"));
	var param = {faFinOrgCode:pcode,flag:flag,nowCode:finOrgCode};
	if(tag == 'cl'){
		param.tag = 'cl';
	}
	$.ajax({
		cache:true,
	    url:ctx+"/manage/queryBankListByParentCode",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:param,
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

//信贷员选择
function selectLoanerByOrgId1(){	
	var finOrgId = $("#bankOrgId1").val();	
	if(finOrgId != null  && finOrgId !=""  && finOrgId != undefined){
		userSelect({
			startOrgId : finOrgId,//非营业部
			expandNodeId : finOrgId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : '',		
			callBack : selectLoanerUser
		});	
	}else{	
		window.wxc.alert('您选择的银行暂时未添加信贷员信息，请联系管理员！');
	}	
}

function selectLoanerByOrgId0(){
	var finOrgId = $("#bankOrgId0").val();
	if(finOrgId != null  && finOrgId !=""  && finOrgId != undefined){
		userSelect({
			startOrgId : finOrgId,//非营业部
			expandNodeId : finOrgId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : '',		
			callBack : selectLoanerUser_
		});	
	}else{	
		window.wxc.alert('您选择的银行暂时未添加信贷员信息，请联系管理员！');
		/*
		 * 默认非营业部所有的信贷员
		 * userSelect({
			startOrgId : '10B1F16BDC5E7F33E0532429030A8872',//非营业部
			expandNodeId : '10B1F16BDC5E7F33E0532429030A8872',
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : '',		
			callBack : selectLoanerUser_
		});	*/	
	}	
}


//设置  可输入  信贷员
function onkeyuploanerName(){

	$("#loanerNameImage").css("color","#676A6C");
	$("#loanerId").val("");
	$("#loanerOrgCode").val("");
	$("#loanerOrgId").val("");
	
	//设置信贷员的值
	var formId = $("#mortgageForm");	
	$("input[name='loanerName']",formId).val($("#loanerName1").val());	
}





var mCustCode='';
var custCode='';
//查询贷款信息
function getMortgageInfo(caseCode,isMainLoanBank,queryCustCodeOnly){
	 if(isMainLoanBank=='0' && !mCustCode){
		getMortgageInfo(caseCode,'1',true);
	 }
	 $.ajax({
	    url:ctx+"/task/getMortgageInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
	    	success:function(data){	
	    		//获取临时银行审批状态和拒绝原因
	    		//$("#tmpBankStatus").val(data.content.tmpBankStatus);--放到提交步骤    		 
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
	    		var fStr='mortgageForm';
	    		var f=$("#mortgageForm");
	    		if(isMainLoanBank != 1){
	    			fStr='mortgageForm1';
	    			f=$('#mortgageForm1');
	    		}
	    		
 				 //银行下拉列表
			
				getGuestInfo(fStr);
				if(data.content && data.content.isTmpBank=='1'){					
					 
					 $("#toLoanerCase").hide();
					 $("#toLoanerCaseTemp").hide();
					 $("#processStart").val("processIsStart");
					//临时银行
					getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),finOrgCode);
					//alert(f.find("select[name='bank_type']"));
				}else{
					 $("#toLoanerCase").show();
					 $("#toLoanerCaseTemp").show();
					//非临时银行
					getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),finOrgCode,'cl');
				}
	  				
	  				
  				f.find("select[name='bank_type']").change(function(){
  					/*$("#mortgageForm").find("select[name='finOrgCode']").chosen("destroy");*/
  					if(data.content && data.content.isTmpBank=='1'){
  						getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),"");
  					}else{
  						getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),"",'cl');
  					}
			    	
			    	/*$("#mortgageForm").find("select[name='finOrgCode']").unbind('change');
			    	$("#mortgageForm").find("select[name='finOrgCode']").bind('change',subBankChange);*/
			    }); 
  			
	  				
	  			//console.log("===Result==="+JSON.stringify(data.content));
	    		if(data != null && data.content != null){
	    				
		    			f.find("input[name='pkid']").val(data.content.pkid);
		    			f.find("select[name='mortType']").val(data.content.mortType);
		    			
		    			f.find("input[name='prfAmount']").val(data.content.prfAmount);
		    			f.find("input[name='prfYear']").val(data.content.prfYear);
		    			if(data.content.mortType=='30016001'){
		    				f.find("input[name='prfAmount']").val('').prop('disabled',true).css("background-color","#eee");
			    			f.find("input[name='prfYear']").val('').prop('disabled',true).css("background-color","#eee");
		    			}else{
		    				f.find("input[name='prfAmount']").prop('disabled',false).css("background-color","");
			    			f.find("input[name='prfYear']").prop('disabled',false).css("background-color","");
		    			}
		    			f.find("input[name='mortTotalAmount']").val(data.content.mortTotalAmount);
		    			f.find("input[name='comAmount']").val(data.content.comAmount);
		    			f.find("input[name='comYear']").val(data.content.comYear);
		    			f.find("input[name='comDiscount']").val(data.content.comDiscount);
		    			f.find("select[name='custCode']").val(data.content.custCode);
		    			f.find("select[name='custName']").val(data.content.custName);
		    			f.find("input[name='custCompany']").val(data.content.custCompany);
		    			f.find("select[name='lendWay']").val(data.content.lendWay);
		    			f.find("input[name='loanerName']").val(data.content.loanerName);
		    			if(data.content.isMainLoanBank == 1){
		    				f.find("input[name='loanerName1']").val(data.content.loanerName);
		    			}
		    			
		    			if(data.content.isTmpBank == 1){
		    				//临时银行 信贷员可以 输入  可以选择
		    				$("#forLoanerProcessShuru").show();
		    				$("#forLoanerProcessNoShuru").hide();
		    				
		    			}else if(data.content.isTmpBank == 0){		    				
		    				//临时银行 信贷员只能选择
		    				$("#forLoanerProcessShuru").hide();
		    				$("#forLoanerProcessNoShuru").show();		    				
		    			}		    			
		    			f.find("input[name='loanerId']").val(data.content.loanerId);
		    			f.find("input[name='loanerOrgId']").val(data.content.loanerOrgId);
		    			f.find("input[name='loanerOrgCode']").val(data.content.loanerOrgCode);
		    			f.find("input[name='loanerPhone']").val(data.content.loanerPhone);
		    			
		    			if(undefined != data.content.loanerId && ""!= data.content.loanerId){
		    				$(".loanerNameImage").css("color","#52cdec");
		    			}else{$(".loanerNameImage").css("color","#676A6C");}
		    			
		    			//设置信贷员审批的值
		    			f.find("input[name='stateInBank']").val(data.content.stateInBank);		    			
		    			
		    			if(data.content.isLoanerArrive == 1){
		    				f.find("input[name='isLoanerArrive'][value='1']").prop("checked",true);
		    			}else if(data.content.isLoanerArrive == 0){
		    				f.find("input[name='isLoanerArrive'][value='0']").prop("checked",true);
		    			}
		    			f.find("input[name='houseNum']").val(data.content.houseNum);
		    			f.find("input[name='signDate']").val(data.content.signDate);
		    			if(data.content.ifReportBeforeLend == 1){
		    				f.find("input[name='ifReportBeforeLend'][value='1']").prop("checked",true);
		    			}else if(data.content.ifReportBeforeLend ==0){
		    				f.find("input[name='ifReportBeforeLend'][value='0']").prop("checked",true);
		    			}

		    			f.find("select[name='finOrgCode']").val(data.content.finOrgCode);
		    			f.find("input[name='tazhengArrDate']").val(data.content.tazhengArrDate);
		    			f.find("input[name='remark']").val(data.content.remark);
		    			if(data.content.toSupDocu != null){
		    				f.find("input[name='supContent']").val(data.content.toSupDocu.supContent);
			    			f.find("input[name='remindTime']").val(data.content.toSupDocu.remindTime);
		    			}
		    			f.find("input[name='recLetterNo']").val(data.content.recLetterNo);
		    			f.find("input[name='isTmpBank']").prop("checked",false);
		    			f.find("input[name='isTmpBank'][value='"+data.content.isTmpBank+"']").prop("checked",true);
		    			f.find("input[name='tmpBankReason']").val(data.content.tmpBankReason);
		    			if(data.content.isTmpBank=='1'){
		    				f.find("input[name='recLetterNo']").prop('disabled',true).css("background-color","#eee");
		    				f.find(".tmpBankReasonDiv").show();
		    			}else{
		    				f.find("input[name='recLetterNo']").prop('disabled',false).css("background-color","");
		    				f.find(".tmpBankReasonDiv").hide();
		    			}
		    			
		    			//新增派单时间   TODO
		    			if(data.content.dispachTime !=null && data.content.dispachTime !="" && data.content.dispachTime != undefined){	
		    				f.find("input[name='processStart']").val("processIsStart");
		    				if(data.content.isMainLoanBank == 1){
		    					$("#dispachTimeShow1").show();
		    					f.find("input[name='dispachTime']").val(data.content.dispachTime);
		    				}else if(data.content.isMainLoanBank == 0){
		    					$("#dispachTimeShow0").show();
		    					f.find("input[name='dispachTime']").val(data.content.dispachTime);			    					
		    				}
		    				//派单时间不为空，显示 并且设置可以进行下一步提交
		    				
		    				
		    			}
		    			
		    			//两种情况下可选：1.流程还未开启 2.申请被驳回 
		    			if((data.code == '0' && !data.content.tmpBankUpdateBy) 
		    					|| data.content.tmpBankStatus == '0'){
		    				f.find("input[name='isTmpBank']").attr('readOnly',false);
		    				f.find("select[name='bank_type']").attr('disabled',false);
		    				f.find("select[name='finOrgCode']").attr('disabled',false);
		    			}else{
		    				f.find("input[name='isTmpBank']").attr('readOnly',true);
		    				f.find("select[name='bank_type']").attr('disabled',true);
		    				f.find("select[name='finOrgCode']").attr('disabled',true);
		    			}
		
	    		}
	    	}
	  });
}


//
/**
 * @date:2017-04-17
 * @desc:临时银行变更时，隐藏派单按钮、信贷员选择问题设置
 * */
function isTmpBankChange(){
	if(!!$(this).attr('readOnly')){
		return false;
	}
	var f=$(this).closest('form');
	var checkBtnVal = $("input[name='isTmpBank']:checked").val(); 
	
	//=1代表选择临时银行
	if(checkBtnVal == '1'){
		getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),'');
		f.find("select[name='bank_type']").change(function(){
		getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),"");
    }); 
		f.find("input[name='recLetterNo']").prop('disabled',true).val("").css("background-color","#DDDDDD");
		f.find(".tmpBankReasonDiv").show();
		
		//派单按钮 隐藏
		$("#toLoanerCase").hide();
		$("#toLoanerCaseTemp").hide();
		$("#processStart").val("processIsStart");
		
		//临时银行 信贷员可以 输入  可以选择
		$("#forLoanerProcessShuru").show();
		$("#forLoanerProcessNoShuru").hide();

	}else{
		getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),'','cl');
		f.find("select[name='bank_type']").change(function(){
		getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),"",'cl');
    }); 
		f.find("input[name='recLetterNo']").prop('disabled',false).css("background-color","");
		f.find(".tmpBankReasonDiv").hide();
		// 派单按钮 显示
		$("#toLoanerCaseTemp").show();
		$("#toLoanerCase").show();
		
		//临时银行 信贷员只能选择
		$("#forLoanerProcessShuru").hide();
		$("#forLoanerProcessNoShuru").show();
	}
	
	
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
					window.wxc.error(data.message);
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
    				var evaCode=$("#eva_code").val();
    				if(evaCode==''){
    					window.wxc.alert('请先接受询价结果！');
    					return false;
    				}
    				confirmPricing();
    				getEvaCompany();
    				//getAttchInfo();
    				fileUpload.init({
    		    		caseCode : $('#caseCode').val(),
    		    		partCode : "ComLoanProcess",
    		    		preFileCode : "_eg",
    		    		maskId : "modal-form-report",
    		    		fileUploadContainer : "comLoanProcess2fileUploadContainer"
    		    	});
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
	    async:false,
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
    	success:function(data){	
    	if(!data.success){
    		window.wxc.error(data.message);
    	}else{
    		if(data.content != null){    			
    			if(data.content.tmpBankStatus != null){
	    			$("#tmpBankStatus").val(data.content.tmpBankStatus);
    			}

    			if(data.content.tmpBankStatus == '0'){
	    			var reason = data.content.tmpBankRejectReason == null?"":data.content.tmpBankRejectReason;
		    		$("#tmpBankRejectReason").text("已拒绝:("+reason+")");
	    		}else if(data.content.tmpBankStatus == '1'){
	    			$("#tmpBankRejectReason").text("已通过！");
	    		}else if(data.content.tmpBankStatus == '2'){
	    			$("#tmpBankRejectReason").text("审批中！");
	    		}
    		}
    		
    		var f=$("#completeForm1");
    		if(isMainLoanBank == 1)
            f=$("#completeForm");
    		if(data != null && data.content != null){
				f.find("[id='sp_bank']").text(data.content.parentBankName);
    			f.find("[id='sp_sub_bank']").text(data.content.bankName);

    			if(!!~~data.content.isTmpBank){
    				f.find('#sp_tmp_bank_u').text(data.content.tmpBankUpdateByStr);
    				f.find('#sp_tmp_bank_t').text(data.content.tmpBankUpdateTime);
    				f.find('#sp_is_tmp_bank').text("是");
    				f.find('#fl_is_tmp_bank').val("1");
    				f.find(".tmpBankDiv").show();
    			}else{
    				f.find(".tmpBankDiv").hide();
    				f.find('#sp_is_tmp_bank').text("否");
    				f.find('#fl_is_tmp_bank').val("0");
    			}
    			
    			if(isMainLoanBank == 1){
	    			$("#completeForm").find("input[name='pkid']").val(data.content.pkid);
	    			$("#completeForm").find("#comAmount").html(data.content.comAmount+"万元");
	    			$("#completeForm").find("#comDiscount").html(data.content.comDiscount);
	    			$("#completeForm").find("input[name='finOrgCode']").val(data.content.finOrgCode);
	    			
	    			//派单流程银行审批通过有时间即设置，其他保持不变	
	    			if(data.content.apprDate){
	    				$("#completeForm").find("input[name='apprDate']").val(data.content.apprDate);
	    			}else{
	    				$("#completeForm").find("input[name='apprDate']").val(data.content.bankApproveTime);
	    			}
	    			
	    			if(data.content.lastLoanBank != null && data.content.lastLoanBank != ''){
		    			$("#completeForm").find("input[name='lastBankSub']").attr("checked","checked");
	    			}

    			}else{
    				$("#completeForm1").find("input[name='pkid']").val(data.content.pkid);
	    			$("#completeForm1").find("#comAmount").html(data.content.comAmount+"万元");
	    			$("#completeForm1").find("#comDiscount").html(data.content.comDiscount+"折");
	    			$("#completeForm1").find("input[name='finOrgCode']").val(data.content.finOrgCode);	    			
	    			//派单流程银行审批通过有时间即设置，其他保持不变
	    			if(data.content.apprDate){
	    				$("#completeForm1").find("input[name='apprDate']").val(data.content.apprDate);
	    			}else{
	    				$("#completeForm1").find("input[name='apprDate']").val(data.content.bankApproveTime);
	    			}
	    			
	    			if(data.content.lastLoanBank != null && data.content.lastLoanBank != ''){
		    			$("#completeForm1").find("input[name='lastBankSub']").attr("checked","checked");
	    			}

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
	        width: 860,
	        height: 400,
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
    		}
	    }); 
}
//加载询价列表
function searchPricingList(table_list_id){
	$("#"+table_list_id).setGridParam({
		"postData" : {
			queryId:"queryToEguPricingPage",
        	search_caseCode:$("#caseCode").val(),
        	search_isMainLoanBank:isMainLoanBank
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
				window.wxc.alert(data.message);
				$("#modal-form").modal("hide");
				var isMainLoanBank = $("#isMainLoanBank").val();
				if(isMainLoanBank == 1){
					//searchPricingList("table_list_1");
	    			getPricingList("table_list_1","pager_list_1",1);
				}else{
					//searchPricingList("table_list_3");
	    			getPricingList("table_list_3","pager_list_3",0);
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
			window.wxc.success(data.message,{"wxcOk" : function(){
				if(data.success){
					//window.location.href=ctx+"/task/myTaskList?"+new Date().getTime();
					caseTaskCheck();
				}
			}});
		}
	});
}
//验证备件是否上传
function checkAttUp(attDiv,f){
	var flag = true;
	attDiv.each(function(){
		var pic = $(this).find("img");
		var preFCode=$(this).find("input[name='preFileCode']").val();
		preFCode=preFCode||'';
		//临时银行或者2016年7月1日之前的案件可以不用上传推荐函
		if(preFCode.indexOf('rec_letter_')!=-1 && (!!f.find("input[name='isTmpBank'][value='1']").prop('checked') ||!afterTimeFlag)){
			flag=true;
			return true;
		}
		if(pic.length == 0){
			flag = false;
			return false;
		}else{
			flag = true;
		}
	});
	if(flag == false){
		window.wxc.alert("备件未上传完整！");
	}
	return flag;
}
//保存操作步骤
function saveStep(f,stepIndex){
	$.ajax({
		url:ctx+"/task/saveMortStep",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val(),isMainLoanBank:f,step:stepIndex},
		success:function(data){
			if(!data.success){
				window.wxc.error(data.message);
			}
		}
	});
}

function checkReportAtt(){
	var flag = true;
	for(var i=1;i<4;i++){
		var length = $("#picContainer"+i).find("img").length;
		if(length == 0) {
			window.wxc.alert("备件未完全上传！");
			flag = false;
			break;
		} else {
			flag = true;
		}
	}
	return flag;
}


var stepIndex = 0;


$(document).ready(function (){
	
	  $("#dispachTimeShow1").hide();
	  $("#dispachTimeShow0").hide();
	  $("#processStart").val("");//页面初始化的时候清空
	  var serviceJobCode = $("#serviceJobCode").val();
	  if(serviceJobCode == "COXXGLY"){
		  $("#processStart").val("processIsStart");
		  $("#adminLoanerProcess").val("adminLoanerProcess");		  
	  }
	  
	  //判断 信贷员流程  修改信息时不需要提交流程
	  var processFlag = getUrlParams('comFlag');				
	  if(processFlag == "processButtonHidden"){			
			 $("#toLoanerCase").hide();
			 $("#toLoanerCaseTemp").hide();
			 $("#processStart").val("processIsStart");
	  }else{			 
			 $("#toLoanerCaseTemp").show();
			 $("#toLoanerCase").show();
	  }  
	  
	  var form = $("#mortgageForm");
	  var form1 = $("#mortgageForm1");
	 //派单按钮 隐藏
	  var checkBtnValForm = form.find("input[name='isTmpBank']:checked").val(); 
	  var checkBtnValForm1 = form1.find("input[name='isTmpBank']:checked").val(); 
	
	  //=1代表选择临时银行
	  if(checkBtnValForm == '1' || checkBtnValForm1 == '1'){
		  $("#toLoanerCase").hide();
		  $("#toLoanerCaseTemp").hide();
	  }
	  
	  getProcessStart();

	/*$("#bank_branch_id").change(subBankChange);*/
	 $(".tmpBankReasonDiv").hide();
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
				window.wxc.alert("请输入移动端收到的评估编号！");
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
			 getPricingList("table_list_1","pager_list_1",1);
		 }else{			
			 getPricingList("table_list_3","pager_list_3",0);
		 }
	 });
	 
	 //关闭报告单发起窗口，刷新报告单列表
	 $("#reportClose").click(function(){
		 var isMainLoanBank = $("#isMainLoanBank").val();
		 if(isMainLoanBank == 1){
			 searchReportList("table_list_4");		
		 }else{
			 searchReportList("table_list_6");		
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
		finish:"提交",		
 	},
 	onInit:function(event, currentIndex){
 		$("#winzard").find("li").css("width","16%");
 	},
    headerTag: "h3",
    bodyTag: "section",
    transitionEffect: "slide",
	startIndex:step,
	showFinishButtonAlways:false,
 	enableCancelButton:false,
 	onStepChanging: function (event, currentIndex, newIndex){
 		if(currentIndex == 0){
 			if(accPricing){
 				$("#eva_code").val(accPricing['EVA_CODE']);
 			}

 		}else if(currentIndex == 2){ 			
 			var flag = false; 			
 			//TODO  
 			/*第三步点击下一步的时候判断 信贷员是否变更？ 判断是否启流程
 			 * 变更为：选择信贷员派单之后，设置银行、信贷员为disabled
 			 * 
 			 * */
 			if(checkMortgageForm($("#mortgageForm"))){
	 			saveMortgage($("#mortgageForm"));
	 			flag = true;
 			}
 			return flag;
 		}else if(currentIndex == 3 ){

 			if ($("#loan_doc_confirm_letter_first_pic_list li").length == undefined
					|| $("#loan_doc_confirm_letter_first_pic_list li").length == 0 ) {
				window.wxc.alert("贷款材料确认书未上传!");
				return false;
			}
 			
 			if($("#mortgageForm").find("input[name='isTmpBank']:checked").val() == '0'){
 				//非临时银行需要判断上传推荐函
	 			if ($("#rec_letter_first_pic_list li").length == undefined || $("#rec_letter_first_pic_list li").length == 0 ) {
					window.wxc.alert("推荐函未上传!");
					return false;
				}
 			}
 			var option = [];
 			option.container = "comLoanProcessfileUploadContainer";
 			
 			//验证上传文件是否全部上传
			var isCompletedUpload = fileUpload.isCompletedUploadById(option);
			
			if(!isCompletedUpload){
				window.wxc.alert("附件还未全部上传!");
				return false;
			}
 			
 			return true;
 		}

 		return true;
 	},
 	onStepChanged: function (event, currentIndex, priorIndex){
 		stepIndex = currentIndex;		
 		if(currentIndex > step){
	 		saveStep(1,stepIndex);
 		}
 		if(currentIndex == 1){
 			getReminderList("table_list_2","pager_list_2");
 		}else if(currentIndex == 2){
 			//获取页面信息  1 表示主选银行
	 		getMortgageInfo($("#caseCode").val(),1);
	 		
 		}else if(currentIndex == 3 && priorIndex !=2){
 			
 			getMortgageInfo($("#caseCode").val(),1);
 			
 		}else if(currentIndex == 4){
 			getMortgageInfo($("#caseCode").val(),1);
 			getReportList("table_list_4","pager_list_4",1);
 		}else if(currentIndex == 5 && priorIndex == 4){
 			//离开报告步骤执行临时银行审批流程
 			startTmpBankWorkFlow();
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
	headerTag: "h3",
bodyTag: "section",
transitionEffect: "slide",
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
			/*if(checkAttUp($(".att_second"),$("#mortgageForm1"))){
				return deleteAndModify();
			}*/
			if ($("#loan_doc_confirm_letter_sec_pic_list li").length == undefined
				|| $("#loan_doc_confirm_letter_sec_pic_list li").length == 0 ) {
			window.wxc.alert("贷款材料确认书未上传!");
			return false;
			}
			if($("#mortgageForm1").find("input[name='isTmpBank']:checked").val() == '0'){
				if ($("#rec_letter_sec_pic_list li").length == undefined || $("#rec_letter_sec_pic_list li").length == 0 ) {
				window.wxc.alert("推荐函未上传!");
				return false;
				}
			}
			var option = [];
 			option.container = "comLoanProcess1fileUploadContainer";
 			
			//验证上传文件是否全部上传
			var isCompletedUpload = fileUpload.isCompletedUploadById(option);
			
			if(!isCompletedUpload){
				window.wxc.alert("附件还未全部上传!");
				return false;
			}
			return true;
		}
		
		return true;
	},
	onStepChanged: function (event, currentIndex, priorIndex){
		stepIndex = currentIndex;
		if(currentIndex > step1){
 		saveStep(0,stepIndex);
		}
		if(currentIndex == 1){
			getReminderList("table_list_5","pager_list_5");
		}else if(currentIndex == 2){
 		getMortgageInfo($("#caseCode").val(),0);
		}else if(currentIndex == 3 && priorIndex != 2){
			getMortgageInfo($("#caseCode").val(),0);
		}else if(currentIndex == 4){
			getMortgageInfo($("#caseCode").val(),0);
			getReportList("table_list_6","pager_list_6",0);
		}else if(currentIndex == 5 && priorIndex == 4){
			//startTmpBankWorkFlow(); 候选银行禁止选临时银行
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
    		window.wxc.alert("请选择报告类型！");
    		return ;
    	}
    	if(getUploadPicInfo()){
    		return;
    	};
    	/*if(attachmentIds.length==0&&preFileAdress.length==0){
    		window.wxc.alert("图片数据不能为空！");
        	return;
    	}
    	var picDiv=$("div[name='allPicDiv2']");
	    var spans =$("input[name='preFileAdress1']");
	    if(spans.length < picDiv.length) {
	    	window.wxc.alert("你有未上传的完成的文件，请稍候再试！");
	    	return;
	    }
    	if(!checkReportAtt()){
    		return;
    	}*/
    	if ($("#identification_card_eg_pic_list li").length == undefined
				|| $("#identification_card_eg_pic_list li").length == 0 ) {
			window.wxc.alert("身份证未上传!");
			return false;
		}
    	if ($("#housing_contract_eg_pic_list li").length == undefined
				|| $("#housing_contract_eg_pic_list li").length == 0 ) {
			window.wxc.alert("购房合同未上传!");
			return false;
		}
    	if ($("#property_certificate_eg_pic_list li").length == undefined
				|| $("#property_certificate_eg_pic_list li").length == 0 ) {
			window.wxc.alert("房产证未上传!");
			return false;
		}
    	var option = [];
			option.container = "comLoanProcess2fileUploadContainer";
			//验证上传文件是否全部上传
		var isCompletedUpload = fileUpload.isCompletedUploadById(option);
		
		if(!isCompletedUpload){
			window.wxc.alert("附件还未全部上传!");
			return false;
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
    		    }, 
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
        			window.wxc.alert(data.message);
        			
        		}
        	});
    	}else{
    		window.wxc.confirm("点击该按钮将会启动线下发起报告单流程，请确认输入正确？",{"wxcOk":function(){
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
	        			window.wxc.alert(data.message);
	        			
	        		}
	        	});
    		}});
    	}
    });
    $("input[name='isTmpBank']").on('click',isTmpBankChange);
   // getPricingList("table_list_1","pager_list_1");
 
 });

/*
 * @author: zhuody
 * @des:启动派单流程
 * 
 * */

function loanerProcessStart(isMainLoanBank){	
	var bankLevel = $("#finOrgCode").find('option:selected').attr('coLevel');//所选银行分行级别 	
	//$("#processStart").val("processIsStart"); //点击派单流程设置 标志，作为"下一步"继续的依据
	
	var form = '';
	if(isMainLoanBank == "1"){
		form = $("#mortgageForm");		
	}else if(isMainLoanBank == "0"){
		form = $("#mortgageForm1");
	}	

	form.find("input[name='processStart']").val("processIsStart");

	var data = 
	{
	   "caseCode":$("#caseCode").val(),
	   "isMainLoanBank":isMainLoanBank 
	};
 	$.ajax({
	    url:ctx+"/task/isLoanerProcessStart",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:data,
    	
    	success:function(data){    		
    		if(data.success == false){	  //未启动派单流程  			
	    			//if(null != bankLevel &&  bankLevel != undefined  && null != loanerUserId && "" != loanerUserId){
    				if(beforeSendLoanerProcess(form)){
	    				window.wxc.confirm("派单前，请再次确认您选择的信贷员是否正确！",{"wxcOk":function(){
	    					startLoanerOrderWorkFlow(bankLevel,isMainLoanBank);  
		    			 }
	   	    		 });  
	    			} 			    		
	    	}else{
				window.wxc.alert(data.message);
				return;
			} 
    	}
 	}); 
}
//启动临时银行审批
function startTmpBankWorkFlow(){
	
	//'我要修改'页面不触发流程 	
	if(source != null && source !=''){
		return;
	}
 	$.ajax({
	    url:ctx+"/mortgage/tmpBankAudit/start",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:{caseCode:$("#caseCode").val()},
    	
    	success:function(data){
    		window.wxc.success(data.message);
    	}
 	});
}

//启动 信贷员审核流程
function  startLoanerOrderWorkFlow(bankLevel,isMainLoanBank){
	var loanerUserId =  "";		//所选信贷员的userId
	var loanerOrgId  =  "";		//所选信贷员的OrgId
	var bankOrgCode  =  "";		//所选银行分行的OrgCode	
	var loanerOrgCode = "";	
	var loanerName = "";
	var  formId = "";
	if(isMainLoanBank == 1){
		formId = $("#mortgageForm");
		loanerUserId = formId.find("input[name='loanerId']").val();
		loanerOrgId = formId.find("input[name='loanerOrgId']").val();
		bankOrgCode = formId.find("select[name='finOrgCode']").val();  
		loanerOrgCode = formId.find("input[name='loanerOrgCode']").val();
		loanerName = formId.find("input[name='loanerName']").val();
	}else if (isMainLoanBank == 0){
		formId = $("#mortgageForm1");
		loanerUserId = formId.find("input[name='loanerId']").val();
		loanerOrgId = formId.find("input[name='loanerOrgId']").val();
		bankOrgCode = formId.find("select[name='finOrgCode']").val();  
		loanerOrgCode = formId.find("input[name='loanerOrgCode']").val();
		loanerName = formId.find("input[name='loanerName']").val();
	}
	
	var mor = getFormParams(formId);
	mor.loanerName = loanerName;
	mor.caseCode = $("#caseCode").val();
	mor.loanerId = loanerUserId;
	mor.loanerOrgId = loanerOrgId;
	mor.finOrgCode = bankOrgCode;
	mor.bankLevel = bankLevel;
	mor.isMainLoanBank = isMainLoanBank;
	mor.loanerOrgCode = loanerOrgCode;
	
 	$.ajax({
	    url:ctx+"/task/sendOrderStart",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:mor,
    	
    	success:function(data){      		
    		//alert(data.content);
    		//回显 派单成功时间
    		if(isMainLoanBank == 1){
    			$("#dispachTime1").val(data.content);
    			$("#dispachTimeShow1").show();
    			
    			//派单成功之后 银行信息、信贷员信息不给修改    		
    			formId.find("select[name='bank_type']").attr("disabled",true);
    			formId.find("select[name='finOrgCode']").attr("disabled",true);
    			formId.find("input[name='loanerName1']").attr("disabled",true);
    			formId.find("#loanerNameImage").attr("disabled",true);
    			formId.find("#loanerNameImage1").attr("disabled",true);    			
    			
    		}else if(isMainLoanBank == 0){
    			$("#dispachTime0").val(data.content);
    			$("#dispachTimeShow0").show();    			
    		
    			formId.find("select[name='bank_type']").attr("disabled",true);
    			formId.find("select[name='finOrgCode']").attr("disabled",true);
    			formId.find("input[name='loanerName']").attr("disabled",true);
    			formId.find("#loanerNameImage").attr("disabled",true);
    		}
    		window.wxc.success(data.message);
    	}
 	});
}

//获取地址栏参数的值
function getUrlParams(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//封装页面填写值
function getFormParams(formId){	
	var custCode = formId.find("select[name='custCode']").val();	
	var custName = formId.find("select[name='custCode']").find("option:selected").text();	
	var mortType = formId.find("select[name='mortType']").val();	
	var mortTotalAmount = 0;
	if(null != formId.find("input[name='mortTotalAmount']").val()  && "" != formId.find("input[name='mortTotalAmount']").val()){
		mortTotalAmount = formId.find("input[name='mortTotalAmount']").val() * 10000;		
	}	
	var comAmount =0;
	if(null != formId.find("input[name='comAmount']").val()  && "" != formId.find("input[name='comAmount']").val()){
		 comAmount = formId.find("input[name='comAmount']").val() * 10000;
	}	
	var prfAmount =0;
	if(null != formId.find("input[name='prfAmount']").val()  && "" != formId.find("input[name='prfAmount']").val()){
		prfAmount =  formId.find("input[name='prfAmount']").val() * 10000;
	}	
	
	var comDiscount =  formId.find("input[name='comDiscount']").val();
	var comYear =  formId.find("input[name='comYear']").val();	
	var lendWay =  formId.find("select[name='lendWay']").val();
	var loanerPhone =  formId.find("input[name='loanerPhone']").val();
	
	var signDate =  formId.find("input[name='signDate']").val();
	var recLetterNo =  formId.find("input[name='recLetterNo']").val();
	
	var prfYear = formId.find("input[name='prfYear']").val();
	var custCompany = formId.find("input[name='custCompany']").val();
	var isTmpBank =  formId.find("input[name='isTmpBank']:checked").val();
	var ifReportBeforeLend =  formId.find("input[name='ifReportBeforeLend']:checked").val();
	var isLoanerArrive =  formId.find("input[name='isLoanerArrive']:checked").val();
	
	var mor = {};
	mor.custCode = custCode;
	mor.custName = custName;
	mor.mortTotalAmount = mortTotalAmount;
	mor.mortType = mortType;
	mor.comAmount = comAmount;
	mor.comDiscount = comDiscount;	
	mor.comYear = comYear;
	mor.lendWay = lendWay;
	mor.loanerPhone = loanerPhone;
	mor.recLetterNo = recLetterNo;
	mor.prfAmount = prfAmount;
	mor.prfYear = prfYear;	
	mor.signDate = signDate;
	mor.custCompany = custCompany;
	mor.isTmpBank = isTmpBank;
	mor.ifReportBeforeLend = ifReportBeforeLend;
	mor.isLoanerArrive = isLoanerArrive;
	
	return mor;
}


function getProcessStart(){
	var data = 
	{
	   "caseCode":$("#caseCode").val(),
	   "isMainLoanBank":$("#isMainLoanBank").val()
	};	
	
 	$.ajax({
	    url:ctx+"/task/isLoanerProcessStart",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:data,
    	
    	success:function(data){    		
    		if(data.success == true){	 //已经启动    		
    			 $("#processStart").val("processIsStart");		    		
	    	}
    	}
 	});  	
}

