
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

//验证按揭贷款补件页签

function checkPatch(formId){
		var patchTimeBuy = formId.find("input[name='patchTimeBuy']").val();
		if(patchTimeBuy==""){
			window.wxc.alert("补件时间为必填项！");
			formId.find("input[name='patchTimeBuy']").css("border-color","red");
			return false;
		}
		var patchTimeSell = formId.find("input[name='patchTimeSell']").val();
		if(patchTimeSell==""){
			window.wxc.alert("补件时间为必填项！");
			formId.find("input[name='patchTimeSell']").css("border-color","red");
			return false;
		}
	return true;
}

function checkyuyueForm(formId){
	if(formId.find("select[name=bookResult]").val() == "" || formId.find("select[name=bookResult]").val() == null){
		window.wxc.alert("预约结果为必选项！");
		formId.find("select[name='bookResult']").css("border-color","red");
		return false;
	}else if(formId.find("input[name=bookPerson]").val() == "" || formId.find("input[name=bookPerson]").val() == null){
		window.wxc.alert("预约人为必填项！");
		formId.find("input[name='bookPerson']").css("border-color","red");
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
	}else if(formId.find("input[name=coLender]").val() =="" || formId.find("input[name=coLender]").val() == null){
		window.wxc.alert("共贷人为必填项！");
		formId.find("input[name='coLender']").css("border-color","red");
		return false;
	}else if(formId.find("input[name=prfAmount]").val() == "" || formId.find("input[name=prfAmount]").val() == null){
		window.wxc.alert("公积金贷款金额必填项！");
		formId.find("input[name='prfAmount']").css("border-color","red");
		return false;
	}else if (formId.find("input[name=prfYear]").val() == "" || formId.find("input[name=prfYear]").val() == null){
		window.wxc.alert("公积金贷款年限必填项！");
		formId.find("input[name='prfYear']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='lendWay']").val() == "" || formId.find("select[name='lendWay']").val() == null){
		window.wxc.alert("放款方式必填项！");
		formId.find("select[name='lendWay']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='bank_type']").val() =="" || formId.find("select[name='bank_type']").val() == null){
		window.wxc.alert("请选择贷款银行！");
		formId.find($("select[name='bank_type']")).css("border-color","red");
		return false;
	}else if(formId.find("select[name='finOrgCode']").val() =="" || formId.find("select[name='finOrgCode']").val() == null){
		window.wxc.alert("贷款支行为必填项！");
		formId.find("select[name='finOrgCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerName']").val() == "" || formId.find("input[name='loanerName']").val() == null){
		window.wxc.alert("信贷员为必填项！");
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;		
	}/*else if(formId.find("input[name='loanerId']").val() == ""){
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;		
	}*/else if(formId.find("input[name='loanerPhone']").val() == "" || formId.find("input[name='loanerPhone']").val() == null){
		window.wxc.alert("信贷员电话为必填项！");
		formId.find("input[name='loanerPhone']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerPhone']").val() != "" && !(/^0?1[3|4|5|7|8][0-9]\d{8}$/.test(formId.find("input[name='loanerPhone']").val()))){
		formId.find("input[name='loanerPhone']").css("border-color","red");
		window.wxc.alert("信贷员手机号码输入错误！");
		return false;
	}else if(formId.find("input[name='signDate']").val() == "" || formId.find("input[name='signDate']").val() == null){
		window.wxc.alert("实际面签时间为必填项！");
		formId.find("input[name='signDate']").css("border-color","red");
		return false;
	}
	return true;
}
function checkCompleteMortgage(formId){	
	$("input,select").css("border-color","#ccc");	
	if(formId.find("select[name='custCode']").val() == "" || formId.find("select[name='custCode']").val() == null){
		window.wxc.alert("主贷人为必填项！");
		formId.find("select[name='custCode']").css("border-color","red");
		return false;
	}else if(formId.find("input[name=coLender]").val() =="" || formId.find("input[name=coLender]").val() == null){
		window.wxc.alert("共贷人为必填项！");
		formId.find("input[name='coLender']").css("border-color","red");
		return false;
	}
	else if(formId.find("select[name='mortType']").val() == ""){
		window.wxc.alert("贷款类型为必填项！");
		formId.find("select[name='mortType']").css("border-color","red");
		return false;
	}else if(formId.find("select[name='lendWay']").val() == ""){
		window.wxc.alert("放款方式必填项！");
		formId.find("select[name='lendWay']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='prfAmount']").val() == ""){
		window.wxc.alert('公积金贷款金额为必填项');
		formId.find("input[name='prfAmount']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='prfYear']").val() == ""){
		window.wxc.alert("公积金贷款年限为必填项！");
		formId.find("input[name='prfYear']").css("border-color","red");
		return false;
	
	}else if(formId.find("select[name='bank_type']").val() ==""){
		window.wxc.alert("请选择贷款银行！");
		formId.find($("select[name='bank_type']")).css("border-color","red");
		return false;
	}else if(formId.find("select[name='finOrgCode']").val() ==""){
		window.wxc.alert("贷款支行为必填项！");
		formId.find("select[name='finOrgCode']").css("border-color","red");
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
	
	}else if(formId.find("input[name='apprCompleTime']").val() == ""){
		window.wxc.alert("实际审批完成时间！");
		formId.find("input[name='apprCompleTime']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanContraTime']").val() == ""){
		window.wxc.alert("出具借款合同时间！");
		formId.find("input[name='loanContraTime']").css("border-color","red");
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
	}else if(formId.find("input[name='comYear']").val() == ""){
		window.wxc.alert("商贷年限为必填项！");
		formId.find("input[name='comYear']").css("border-color","red");
		return false;
	}else if(formId.find("input[name='loanerName']").val() == ""){
		window.wxc.alert("信贷员为必填项！");
		formId.find("input[name='loanerName']").css("border-color","red");
		return false;
		
	}else if(formId.find("input[name='loanerId']").val() == ""){
		formId.find("input[name='loanerName']").css("border-color","red");
		window.wxc.alert("请选择信贷员！");
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
	}
	
	if(formId.find("select[name='finOrgCode']").val() == ""){
		window.wxc.alert("贷款支行为必填项！");
		formId.find("select[name='finOrgCode']").css("border-color","red");
		return false;
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

/*//询价	
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
		    			getPricingList("table_list_1","pager_list_1",1);
					}else{

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
		        			getPricingList("table_list_3","pager_list_3",0);
		    			}else{
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
        			getPricingList("table_list_3","pager_list_3",0);
    			}else{
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
        			getPricingList("table_list_3","pager_list_3",0);
    			}else{
        			getPricingList("table_list_1","pager_list_1",1);
    			}
			}else{
				window.wxc.error(data.message);	
			}
		}
	});
}
*/
//保存贷款信息
function saveMortgage(form){
	console.info(form);
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

/***
 * 完成贷款审批
 */
        
function completeMortgage(form){	
	if(!checkCompleteMortgage(form)){
		return false;
	}
	var pkid=form.find("input[name='pkid']").val();
	if(pkid == null || pkid == "" ){
		window.wxc.alert("贷款信息不存在！");
		return;
	}
	var finOrgCode = form.find("[name='finOrgCode']").val();
	if(finOrgCode == null || finOrgCode == ""){
		alert("请先选择贷款银行和支行！");
		return;
	}	
	$.ajax({
		url:ctx+"/task/completeMortgage",
		method:"post",
		dataType:"json",
		data:form.serialize(),
		success:function(data){
			if(data.success){
						//TODO 如果作为最终银行提交，自动结束候选银行或者主选银行的的派单流程
						//window.close();
						//window.opener.callback();
						submitMortgage();
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
					//if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
					//}
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
	f=selectorBranch.closest('form');
	
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
	    		finOrgCodeChange(f);
	    	}
	 });
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
function selectLoanerByOrgId(){	
	var f=$(this).closest('form');
	operForm=f;
	var finOrgId = f.find('input[name=bankOrgId]').val();
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




//设置  可输入  信贷员
function onkeyuploanerName(){
	var f=$(this.event.target).closest('form');
	f.find("#loanerNameImage").css("color","#676A6C");
	f.find("#loanerId").val("");
	f.find("#loanerOrgCode").val("");
	f.find("#loanerOrgId").val("");	
}
/**
 * 加载派单信息(最后一条)
 */
function loadMortLoaner(caseCode,isMainLoanBank,render){
	$.ajax({
	    url:ctx+"/task/loadOrderInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,isMainLoanBank:isMainLoanBank},
	    success:function(data){	
	    	if(!data.success){
	    		window.wxc.error(data.message);
	    	}else{
	    		render.render(data);
	    	}
	 }});
}



/**
 * 加载贷款信息
 * @param caseCode
 * @param isMainLoanBank
 * @param render
 */
function loadMortgageInfo(isMainLoanBank,render){
	$.ajax({
	    url:ctx+"/task/getMortgageInfo",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:$("#caseCode").val(),isMainLoanBank:isMainLoanBank},
	    success:function(data){	
	     	if(!data.success){
	    		window.wxc.error(data.message);
	    	}else{
	    		render.render(data);
	    	}
	    }
	   });
}



/**
 * 加载评估信息
 * @param f form表单
 * @param mid 贷款表id
 */
function loadEval(f,mid){
	 $.ajax({
		    url:ctx+"/task/getEval",
		    method:"post",
		    dataType:"json",
		    data:{mid:mid},
		    success:function(data){	
		    	if(!data.success){
		    		window.wxc.error(data.message);
		    	}else{
		    		var $content=data.content;
		    		if($content){
		    			f.find('[name=evaFee]').val($content.evaFee||'').prop('readonly',true);
		    			f.find('[name=serviceFee]').val($content.serviceFee||'').prop('readonly',true);
		    			f.find('[name=evaFeeCost]').val($content.evaFeeCost||'').prop('readonly',true);
		    			
		    			f.find('[name=feeTotal]').val($content.feeTotal||'');
		    			f.find('[name=evaFeePerf]').val($content.evaFeePerf||'');
		    			f.find('[name=serviceFeePerf]').val($content.serviceFeePerf||'');
		    		}
		    		
		    	}
	    	}
		  });
}
//临时银行状态及对应的显示样式
var tempBankStatusList ={};
tempBankStatusList['0']={str:'拒绝',clasz:'red_visited'};
tempBankStatusList['1']={str:'同意',clasz:'blue_visited'};
tempBankStatusList['2']={str:'审批中',clasz:'grey_visited'};
//派单状态显示样式
var orderStatusList ={};
orderStatusList['ACCEPTING']='grey_visited';
orderStatusList['AUDITING']='grey_visited';
orderStatusList['COMPLETED']='blue_visited';
orderStatusList['ACC_REJECTED']='red_visited';
orderStatusList['AUD_REJECTED']='red_visited';
orderStatusList['CANCELED']='red_visited';
orderStatusList['CLOSED']='blue_visited';

var mCustCode='';
var custCode='';
/**
 * 派单信息是否临时银行Change
 * @param v 是否临时银行
 */
function orderTmpBankChange(v,f,finOrgCode){
	if(!$(f).is('form')){
		//v=$(this).val();
		v='1'
		f=$(this).closest('form');
	}
	var fid = f.attr('id');
	var fName = '';
	if (fid != null && fid !='' && fid != undefined) {
		 fName=f.attr('id').match(/[a-zA-Z]*/)[0];
	}
	var isMain = f.find("[name='isMainLoanBank']").val();
	var tab= fName=='orderform'?'':fName=='mortgageForm'?'SIGN_':'COMPLETE_';
	var cl;
	/*if(v=='1'){//临时银行处理
		f.find('.tmpBankShow').show();
		f.find('.tmpBankHide').hide();
		f.find('[name=loanerName]').unbind('click');
	}else{//非临时银行处理
		cl='cl';
		f.find('.nonTmpBankHide').hide();
		f.find('.nonTmpBankShow').show();
		f.find('[name=loanerName]').bind('click',selectLoanerByOrgId);
		loadMortLoaner($('#caseCode').val(),isMain,eval("ORDER_"+tab+isMain));
	}*/
	//加载分支
	getParentBank(f.find("select[name='bank_type']"),f.find("select[name='finOrgCode']"),finOrgCode,cl);
	f.find("select[name='bank_type']").unbind('change').bind('change',function(){
		//var isTmpBank= $(this).closest('form').find('[name=isTmpBank]:checked').val();
			//if(isTmpBank =='1'){
				
			//	getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),finOrgCode,'');
			//}else{
				
				getBranchBankList(f.find("select[name='finOrgCode']"),f.find("select[name='bank_type']").val(),finOrgCode,'cl');
			//}
    }); 
}
/**
 * 选择银行时带出银行的orgId
 * @param val
 */
function finOrgCodeChange(val){
	var f;
	if($(val).is("form")){
		 f= val;   
	}else{
		f=$(this).closest('form');
	}
	var finOrgCode="";
	//var isTmpBank = f.find("input[name='isTmpBank']:checked").val();
	finOrgCode =f.find("select[name='finOrgCode']").val(); 
	var finOrgId='';	
	/*if(!$(val).is("form")){//如果不是临时银行的话应该将信贷员清空(如果有传form的话说明是代码直接调用的不是选择银行触发的)
		f.find('[name=loanerId]').val('');
		f.find('[name=loanerOrgCode]').val('');
		f.find('[name=loanerOrgId]').val('');
		f.find("#loanerNameImage").css("color","#676A6C");
	}else{
		f.find("#loanerNameImage").css("color","#52cdec");
	}*/
	if(null == finOrgCode || finOrgCode == '' || finOrgCode == undefined){
		return;
	}
	$.ajax({
		    url:ctx+"/manage/queryBankOrgIdByOrgCode",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{finOrgCode:finOrgCode},
		    success:function(data){
	    		if(data != null){
	    			finOrgId = data.content;
	    			f.find('input[name=bankOrgId]').val(finOrgId);
	    		}else{
	    			f.find('input[name=bankOrgId').val('');
	    		}
	    	}
	 });
}


/**
 * 补件信息渲染
 * @returns
 */
function renderMortgagePatch(f,data){
	var $content = data.content;
	if($content){
		
		var isPatch = $content.isPatch;
		var buyPatchStr = $content.buyPatchStr;
		var sellPatchStr = $content.sellPatchStr;
		var patchTimeBuy = $content.patchTimeBuy;
		var patchTimeSell = $content.patchTimeSell;
		if(isPatch && isPatch == "1"){
			f.find("input[name=isPatch]").eq(0).prop('checked',true);
			//alert('buyPatchStr'+buyPatchStr)
			if(buyPatchStr){
				//alert($("#form_check").text());
				var buyPatchArr =  buyPatchStr.split(",");
				for( var i = 0; i<buyPatchArr.length; i++){
					f.find("input:checkbox[value='"+buyPatchArr[i]+"']").prop('checked',true);
				}
			}
			if(sellPatchStr){
				var sellPatchArr =  sellPatchStr.split(",");
				for( var i = 0; i<sellPatchArr.length; i++){
					f.find("input:checkbox[value='"+sellPatchArr[i]+"']").prop('checked',true);
				}
			}
			if(patchTimeBuy){
				f.find("input[name=patchTimeBuy]").val(patchTimeBuy);
			}else{
				f.find("input[name=patchTimeBuy]").val("");
			}
			if(patchTimeSell){
				f.find("input[name=patchTimeSell]").val(patchTimeSell);
			}else{
				f.find("input[name=patchTimeSell]").val("");
			}
		}else if(isPatch && isPatch == "0"){
			f.find("input[name=isPatch]").eq(1).prop('checked',true);
			$("#form_check").hide();
			$("#form_check").next("div.marinfo").hide();
		}
		
	}
}


/**
 * 预约结果信息渲染
 */

function renderMortgageYuyue(f,data){
	var $content=data.content;
	if($content){
		f.find('input[name=pkid]').val($content.pkid);
		f.find('input[name=caseCode]').val($content.caseCode);
		if($content.isMainLoanBank){
			f.find('input[name=isMainLoanBank]').val($content.isMainLoanBank);
		}
		f.find('select[name=bookResult]').val($content.bookResult);
		f.find('input[name=bookPerson]').val($content.bookPerson);
		
	}

}
/**
 * 评估结果信息渲染
 */
function renderMortgagePinGu(f,data){
	var $content = data.content;
	if($content){
		f.find('input[name=pkid]').val($content.pkid);
		f.find('input[name=caseCode]').val($content.caseCode);
		f.find('input[name=isMainLoanBank]').val($content.isMainLoanBank);
	}

}
/**
 * 
 * 签约Table贷款信息渲染
 * @param f
 * @param data 返回的数据
 */
function renderMortgageSign(f,data){
	var $content=data.content;
	
	if($content){
		getGuestInfo(f.attr('id'),$content.custCode);
		orderTmpBankChange($content.isTmpBank,f,$content.finOrgCode);
		f.find('input[name=pkid]').val($content.pkid);
		f.find('input[name=caseCode]').val($content.caseCode);
		f.find('input[name=isMainLoanBank]').val($content.isMainLoanBank);
		f.find('input[name=custName]').val($content.custName);
		f.find('input[name=coLender]').val($content.coLender);
		f.find('select[name=custCode]').val($content.caseCode);
		f.find('select[name=mortType]').val($content.mortType);
		f.find('select[name=lendWay]').val($content.lendWay);
		f.find('input[name=houseNum]').val($content.houseNum);
		f.find('input[name=prfAmount]').val($content.prfAmount);
		f.find('input[name=prfYear]').val($content.prfYear);
		//f.find('input[name=comDiscount]').val($content.comDiscount);
		//f.find('input[name=priCreditUnit]').val($content.priCreditUnit);
		f.find('select[name=bank_type]').val($content.bank_type);
		f.find('select[name=finOrgCode]').val($content.finOrgCode);
		f.find('input[name=signDate]').val($content.signDate);
		f.find('input[name=loanerOrgCode]').val($content.loanerOrgCode);
		f.find('input[name=loanerOrgId]').val($content.loanerOrgId);
		f.find('input[name=loanerId]').val($content.loanerId);
		f.find('input[name=loanerName]').val($content.loanerName);
		f.find('input[name=loanerPhone]').val($content.loanerPhone);
		
		if(undefined != $content.loanerId && ""!= $content.loanerId){
			$(".loanerNameImage").css("color","#52cdec");
		}else{$(".loanerNameImage").css("color","#676A6C");}
		
		
		//loadEval(f,$content.pkid);*/
	}else{
		getGuestInfo(f.attr('id'),'');
		$(".loanerNameImage").css("color","#676A6C");
		orderTmpBankChange(f.find('[name=isTmpBank]:checked').val(),f,null);
	}

	
} 

/**
 * 完成Table贷款信息渲染
 * @param form jqueryForm对象
 * @param data 返回的数据
 */
function renderMortgageComplete(f,data){
	var $content=data.content;
		if($content){
			getGuestInfo(f.attr('id'),$content.custCode);
			orderTmpBankChange($content.isTmpBank,f,$content.finOrgCode);
			f.find('input[name=pkid]').val($content.pkid);
			f.find('input[name=caseCode]').val($content.caseCode);
			f.find('input[name=isMainLoanBank]').val($content.isMainLoanBank);
			f.find('input[name=custName]').val($content.custName);
			f.find('input[name=coLender]').val($content.coLender).attr("readonly",true);
			f.find('select[name=custCode]').val($content.caseCode).attr("disabled",true);
			f.find('select[name=mortType]').val($content.mortType).attr("disabled",true);
			f.find('select[name=lendWay]').val($content.lendWay).attr("disabled",true);
			f.find('input[name=houseNum]').val($content.houseNum).attr("readonly",true);
			f.find('input[name=prfAmount]').val($content.prfAmount).attr("readonly",true);
			f.find('input[name=prfYear]').val($content.prfYear).attr("readonly",true);
			//f.find('input[name=comDiscount]').val($content.comDiscount);
			//f.find('input[name=priCreditUnit]').val($content.priCreditUnit);
			f.find('select[name=bank_type]').val($content.bank_type).attr("disabled",true);
			f.find('select[name=finOrgCode]').val($content.finOrgCode).attr("disabled",true);
			f.find('input[name=signDate]').val($content.signDate).attr("disabled",true);
			f.find('input[name=loanerOrgCode]').val($content.loanerOrgCode);
			f.find('input[name=loanerOrgId]').val($content.loanerOrgId);
			f.find('input[name=loanerId]').val($content.loanerId);
			f.find('input[name=loanerName]').val($content.loanerName).attr("readonly",true);
			f.find('input[name=loanerPhone]').val($content.loanerPhone).attr("readonly",true);
			f.find('input[name=apprCompleTime]').val($content.apprCompleTime);
			f.find('input[name=loanContraTime]').val($content.loanContraTime);
			if(undefined != $content.loanerId && ""!= $content.loanerId){
				$(".loanerNameImage").css("color","#52cdec");
			}else{$(".loanerNameImage").css("color","#676A6C");}
		
			
			getGuestInfo(f.attr('id'),$content.custCode);
			orderTmpBankChange($content.isTmpBank,f,$content.finOrgCode);
		
	}else{
		getGuestInfo(f.attr('id'),'');
		orderTmpBankChange(f.find('[name=isTmpBank]:checked').val(),f,null);
	}

} 
/**
 * 派单Table派单信息渲染
 * @param f
 * @param data
 */
function renderOrder(f,data){
	var $content=data.content;
	if($content){
		if($content.loanerStatus=='ACCEPTING'||$content.loanerStatus=='AUDITING'||$content.loanerStatus=='COMPLETED'){
			//除了取消派单外其它全部只读
			f.find('input').prop('readOnly',true);
			f.find('select,[name=isTmpBank]').prop('disabled',true);
		}else{
			f.find('input').removeProp('readOnly');
			f.find('select,[name=isTmpBank]').removeProp('disabled');
		}
		f.find("[name=resSignTime]").val($content.resSignTime);
		f.find("[name=resSignAddr]").val($content.resSignAddr);
		//后台金额没有转换这里转成万元
		f.find("[name=mortTotalAmount]").val(($content.mortTotalAmount/10000)||'');
		f.find("[name=mortType]").val($content.mortType);
		f.find("[name=comAmount]").val(($content.comAmount/10000)||'');
		f.find("[name=comYear]").val($content.comYear);
		f.find("[name=prfAmount]").val(($content.prfAmount/10000)||'' );
		f.find("[name=prfYear]").val($content.prfYear);
		f.find("[name=loanerStatus]").val($content.loanerStatus);
		f.find('#div_nonTmpBankStatus i').text($content.loanerStatusStr).removeClass().addClass("color_visited "+orderStatusList[$content.loanerStatus]);
		
		//loadTradeCenterList(f,$content.resSignAddr);
	}else{
		//loadTradeCenterList(f);
	}
}
/**
 * 签约Table派单信息渲染
 * @param f
 * @param data
 */
function renderOrderSign(f,data){
	var $content=data.content;
	if($content){
		f.find("#lab_mortTotalAmount").text((($content.mortTotalAmount/10000)||'')+'万元');
		f.find("#lab_mortType").text($content.mortType=='30016001'?'按揭贷款':$content.mortType=='30016002'?'组合贷款':'');
		f.find("#lab_comAmount").text((($content.comAmount/10000)||'')+'万元');
		f.find("#lab_comYear").text($content.comYear + '年');
		f.find("#lab_prfAmount").text((($content.prfAmount/10000)||'')+'万元' );
		f.find("#lab_prfYear").text(($content.prfYear||'')+'年');
		f.find("#lab_loanerStatus").text($content.loanerStatus);
		f.find("#lab_bankName").text($content.loanerOrgCodeStr);
		f.find("#lab_loanerName").text($content.loanerName);
		f.find("#lab_custName").text($content.custName);
		if($content.loanerStatus=='ACCEPTING'||$content.loanerStatus=='AUDITING'||$content.loanerStatus=='COMPLETED'){
			//除了取消派单外其它全部只读
			f.find('[name=bank_type],[name=finOrgCode],[name=isTmpBank]').prop('disabled',true).unbind('click').click(function(){return false});
		}else{
			f.find('[name=bank_type],[name=finOrgCode],[name=isTmpBank]').removeProp('disabled').unbind('click');
		}
	}
}
/**
 * 完成Table派单 信息渲染
 * @param f
 * @param data
 */
function renderOrderComplete(f,data){
	var $content= data.content;
	if($content){
		f.find("[name=loanerStatus]").val($content.loanerStatus);
	}
}
!function($, window) {
	var _render=function(sel,renderType){
		this._selector=sel;this.renderType=renderType;
	}
	var r=function(data){
		//alert('this.renderType'+this.renderType)
		if(this.renderType=='Y'){
			renderMortgageYuyue($(this._selector),data);
		}else if(this.renderType=='V'){
			renderMortgagePinGu($(this._selector),data);
		}
		else if (this.renderType=='S'){
			renderMortgageSign($(this._selector),data);
		//回写补件信息
		}else if(this.renderType=='T'){
			renderMortgagePatch($(this._selector),data);
		}else if(this.renderType=='C'){
			renderMortgageComplete($(this._selector),data);
		}/*else if(this.renderType=='QC'){
			if(data.content != null && data.content.custCode != null){
				window.mCustCode=data.content.custCode;
			}
			return true;
		}*/
	}
	_render.prototype = {
		constructor : _render,
		render:r
	};
	window.mortageRender=_render;
}(jQuery,window);
!function($, window) {
	var _render=function(sel,renderType){
		this._selector=sel;this.renderType=renderType;
	}
	var r=function(data){
		//渲染类型 O:派单(order) S:签约 C:完成,QC:仅查询客户信息
		if(this.renderType=='O'){
			renderOrder($(this._selector),data);
		}else if (this.renderType=='S'){
			renderOrderSign($(this._selector),data);
		}else if(this.renderType=='C'){
			renderOrderComplete($(this._selector),data);
		}
	}
	_render.prototype = {
		constructor : _render,
		render:r
	};
	window.orderRender=_render;
}(jQuery,window);


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

/**
 * formId 要显示的formID
 * val :要选中的值
 * 查询客户信息
 */
function getGuestInfo(formId,val){
	$.ajax({
		url:ctx+"/task/getGuestInfo",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val()},
		success:function(data){
			if(data != null && data.length > 0){
				$("#"+formId).find("select[name='custCode']").html("");
				
				for(var i=0;i<data.length;i++){
					if(val == data[i].pkid){
						$("#"+formId).find("select[name='custCode']").append("<option cName='"+data[i].workUnit+"' value='"+data[i].pkid+"' selected >"+data[i].guestName+"</option>");
						$("#"+formId).find("select[name='custName']").val(data[i].guestName);
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
	//$("#"+tableId).jqGrid("GridUnload");
	/*$("#"+tableId).jqGrid({
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
        
    });*/
   /* $("#"+tableId).navGrid('#'+pageId,{add: false, del:false,edit:false,search:false,refresh:false}).navButtonAdd(
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
    }); */
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

//提醒清单
function getReminderList(tableId,pageId){
	/*$("#"+tableId).jqGrid("GridUnload");
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
    });*/
}
var grid = null;
var accPricing = null;
var accPricingIds = null;
//查询询价列表
function getPricingList(tableId,pageId,isMainLoanBank){
	 /*  $("#"+tableId).jqGrid("GridUnload");
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
	    }); */
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
	    			getPricingList("table_list_1","pager_list_1",1);
				}else{

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
					window.location.href=ctx+"/task/myTaskList?"+new Date().getTime();
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
/**
 * 派单Tab是否可以进行
**/
function checkOrderCanNextStep(isMain){
	var f ;
	if(isMain=='1'){
		f=$('#orderform');
	}else{
		f=$('#orderform1');
	}
	var isTmpBank= f.find('[name=isTmpBank]:checked').val();
	var loanerStatus = f.find('[name=loanerStatus]').val();
	var tmpStatus= f.find('[name=tmpBankStatus]').val();
	/*if(!!~~isTmpBank && tmpStatus !='1'){//选择的临时银行 却还没有通过
		window.wxc.alert('临时银行审批未通过');
		return false;
	}else if(!~~isTmpBank && loanerStatus!='AUDITING'&&loanerStatus!='COMPLETED' &&loanerStatus!='CLOSED'){
		window.wxc.alert('信贷员未接单或被打回');
		return false
	}*/
	return true;
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
	 /*$("#pricingClose").click(function(){
		 var isMainLoanBank = $("#isMainLoanBank").val();
		 if(isMainLoanBank == 1){			 
			 getPricingList("table_list_1","pager_list_1",1);
		 }else{			
			 getPricingList("table_list_3","pager_list_3",0);
		 }
	 });*/
	 
	 //关闭报告单发起窗口，刷新报告单列表
	/* $("#reportClose").click(function(){
		 var isMainLoanBank = $("#isMainLoanBank").val();
		 if(isMainLoanBank == 1){
			 searchReportList("table_list_4");		
		 }else{
			 searchReportList("table_list_6");		
		 }
	 });*/
	 
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
	//showFinishButtonAlways:false,
 	//enableCancelButton:false,
 	onStepChanging: function (event, currentIndex, newIndex){
 		if(currentIndex == 0){
 			var flag = false; 			
 			if(checkyuyueForm($("#mortgageForm0"))){
	 			saveMortgage($("#mortgageForm0"));
	 			flag = true;
 			}
 			return flag;
 		}else if(currentIndex == 2){
 			var flag = false; 			
 			/*点第二步点击下一步的时候判断 信贷员是否变更？ 判断是否启流程
 			 * 变更为：选择信贷员派单之后，设置银行、信贷员为disabled
 			 * */
 			if(checkMortgageForm($("#mortgageForm"))){
	 			saveMortgage($("#mortgageForm"));
	 			flag = true;
 			}
 			return flag;
 		}else if(currentIndex == 3){
 			var flag = false;
 			var is_patch = $("#mortgageForm_b").find("input[name=isPatch]:checked").val();
 			if(is_patch=="1"){
	 			if(checkPatch($("#mortgageForm_b"))){
 					$.ajax({
 					    url:ctx+"/task/getMortgageInfo",
 					    method:"post",
 					    dataType:"json",
 					    async: false,
 					    data:{caseCode:$("#caseCode").val(),isMainLoanBank:"1"},
 					    success:function(data){	
 					     	if(!data.success){
 					    		window.wxc.error(data.message);
 					    	}else{
 					    		var pkid = data.content.pkid;
 					    		$("#pkid_bujian").val(pkid);
 					    		saveMortgage($("#mortgageForm_b"));
 					 			flag = true;
 					    	}
 					    }
 					   });
	 				flag = true;
	 			}
 			}else{
 				$.ajax({
				    url:ctx+"/task/getMortgageInfo",
				    method:"post",
				    dataType:"json",
				    async: false,
				    data:{caseCode:$("#caseCode").val(),isMainLoanBank:"1"},
				    success:function(data){	
				     	if(!data.success){
				    		window.wxc.error(data.message);
				    	}else{
				    		var pkid = data.content.pkid;
				    		$("#pkid_bujian").val(pkid);
				    		var _isPatch = $("#mortgageForm_b").find("input[name=isPatch]").val();
				    		if(_isPatch == "0"){
				    			$("#bujian_b")[0].checked = false;
				    			var $arr = $("#form_check").find("input[name=buyPatch]");
				    			for(var i = 0 ; i < $arr.length; i++){
				    				$arr[i].checked = false;
				    			}
				    			$("#bujian_s")[0].checked = false;
				    			var $arr1 = $("#form_check").find("input[name=sellPatch]");
				    			for(var i = 0 ; i < $arr1.length; i++){
				    				$arr1[i].checked = false;
				    			}
				    		}
				    		saveMortgage($("#mortgageForm_b"));
				 			flag = true;
				    	}
				    }
				   });
 			
 			}
 			return flag;
 		}else if(currentIndex == 4){

 			if ($("#loan_doc_confirm_letter_first_pic_list li").length == undefined
					|| $("#loan_doc_confirm_letter_first_pic_list li").length == 0 ) {
				window.wxc.alert("贷款材料未上传!");
				return false;
			}
 			/*if($("#mortgageForm").find("input[name='isTmpBank']:checked").val() == '0'){
 				//非临时银行需要判断上传推荐函
	 			if ($("#rec_letter_first_pic_list li").length == undefined || $("#rec_letter_first_pic_list li").length == 0 ) {
					window.wxc.alert("推荐函未上传!");
					return false;
				}
 			}*/
 			var option = [];
 			option.container = "PSFLoanProcessfileUploadContainer";
 			
 			//验证上传文件是否全部上传
			var isCompletedUpload = fileUpload.isCompletedUploadById(option);
			
			if(!isCompletedUpload){
				window.wxc.alert("附件还未全部上传!");
				return false;
			}
 			return true;
 		}else if(currentIndex == 5 ){
 			if(!checkCompleteMortgage($("#completeForm"))){
	 			return false;
 			}
 			saveMortgage($("#completeForm"));
 		}

 		return true;
 	},
 	onStepChanged: function (event, currentIndex, priorIndex){
 		stepIndex = currentIndex;
 		//alert("currentIndex"+currentIndex)
 		if(currentIndex > step){
	 		saveStep(1,stepIndex);
 		}
 		if(currentIndex == 0){
 			loadMortgageInfo(1,MORT_SIGN_3);
 		}else if(currentIndex == 1){
 			loadMortgageInfo(1,MORT_VIEW);
 		}
 		else if(currentIndex == 2){
 			loadMortgageInfo(1,MORT_SIGN_1);
 		}else if(currentIndex == 3){
 			//获取页面信息  1 表示主选银行
	 		loadMortgageInfo(1,MORT_SIGN_2);
 		}else if(currentIndex == 4){
 			loadMortgageInfo(1,MORT_SIGN_1);
 		}else if(currentIndex == 5 ){
 			loadMortgageInfo(1,MORT_COMPLETE_1);
 			
 		}
 	},
 	onFinished: function (event, currentIndex)
	{
 		completeMortgage($("#completeForm"));	
    }
});



    $("#reportSubBtn").click(function(){
    	if($("#reportType").val()==""){
    		window.wxc.alert("请选择报告类型！");
    		return ;
    	}
    	if(getUploadPicInfo()){
    		return;
    	};
    	
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
     
 });
/**
 * 取消派单
 */
function loanerProcessCancel(){
	var form = $(this).closest('form');
	var loanerStatus = form.find('[name=loanerStatus]').val();
	var isMainLoanBank = form.find('[name=isMainLoanBank]').val();
	var data = 
	{
	   "caseCode":$("#caseCode").val(),
	   "isMainLoanBankProcess":isMainLoanBank 
	};
	if(loanerStatus =='ACCEPTING' || loanerStatus =='AUDITING'|| loanerStatus=='COMPLETED'){
		window.wxc.confirm("确定要撤消派单吗！",{"wxcOk":function(){
		 	$.ajax({
			    url:ctx+"/task/loanerProcessCancle",
			    async:false,
		    	method:"post",
		    	dataType:"json",
		    	data:data,
		    	success:function(data){    		
		    		if(data.success){	
		    			window.wxc.alert('撤单成功');
		    			loadMortLoaner($('#caseCode').val(),isMainLoanBank,eval("ORDER_"+isMainLoanBank));
		    		}else{
		    			window.wxc.alert(data.message);
		    		}
		    	}
		 	});
		}});
		
	}else{
		window.wxc.alert('当前派单不允许进行撤单');
	}
} 
/*
 * @author: zhuody
 * @des:启动派单流程
 * 
 * */

function loanerProcessStart(){	
	 	
	var form = $(this).closest('form');
	var bankLevel = form.find("#finOrgCode").find('option:selected').attr('coLevel');//所选银行分行级别
	var isMainLoanBank = form.find('[name=isMainLoanBank]').val();
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
    		if(data.success){	
    			if(!data.content){
    				if(beforeSendLoanerProcess(form)){
	    				window.wxc.confirm("派单前，请再次确认您选择的信贷员是否正确！",{"wxcOk":function(){
	    					startLoanerOrderWorkFlow(bankLevel,isMainLoanBank);  
		    			 }
	   	    		 });  
	    			} 			 
    			}else{
    				window.wxc.alert('派单信息已存在，请先撤销原派单后再操作!');
    			}
	    	}else{
				window.wxc.alert(data.message);
				return;
			} 
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
		formId = $("#orderform");
	}else if (isMainLoanBank == 0){
		formId = $("#orderform1");
	}
	
	loanerUserId = formId.find("input[name='loanerId']").val();
	loanerOrgId = formId.find("input[name='loanerOrgId']").val();
	bankOrgCode = formId.find("select[name='finOrgCode']").val();  
	loanerOrgCode = formId.find("input[name='loanerOrgCode']").val();
	loanerName = formId.find("input[name='loanerName']").val();
	
	var mor = getFormParams(formId);
	mor.loanerName = loanerName;
	mor.caseCode = $("#caseCode").val();
	mor.loanerId = loanerUserId;
	mor.loanerOrgId = loanerOrgId;
	mor.finOrgCode = bankOrgCode;
	mor.bankLevel = bankLevel;
	mor.isMainLoanBankProcess = isMainLoanBank;
	mor.loanerOrgCode = loanerOrgCode;
	
 	$.ajax({
	    url:ctx+"/task/sendOrderStart",
	    async:false,
    	method:"post",
    	dataType:"json",
    	data:mor,
    	success:function(data){
    		if(data.success){
    			window.wxc.success('派单成功');
    			loadMortLoaner($('#caseCode').val(),isMainLoanBank,eval("ORDER_"+isMainLoanBank));
    		}else{
    			window.wxc.success(data.message);
    		}
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
	
	var comYear =  formId.find("input[name='comYear']").val();	
	var loanerPhone =  formId.find("input[name='loanerPhone']").val();
	
	var prfYear = formId.find("input[name='prfYear']").val();
	var custCompany = formId.find("input[name='custCompany']").val();
	var isTmpBank =  formId.find("input[name='isTmpBank']:checked").val();

	
	var mor = {};
	mor.custCode = custCode;
	mor.custName = custName;
	mor.mortTotalAmount = mortTotalAmount;
	mor.mortType = mortType;
	mor.comAmount = comAmount;	
	mor.comYear = comYear;
	mor.loanerPhone = loanerPhone;
	mor.prfAmount = prfAmount;
	mor.prfYear = prfYear;	
	mor.custCompany = custCompany;
	mor.isTmpBank = isTmpBank;
	mor.resSignTime= formId.find('[name=resSignTime]').val();
	mor.resSignAddr= formId.find('[name=resSignAddr]').val();
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

