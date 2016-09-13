
$(document).ready(function(){
		//流程开启后只读表单
		if($("#handle").val() != null && $("#handle").val() != '' && $("#handle").val() != 'SpvApply'){
		    readOnlyRiskForm();
		}

		$("select[name='toSpv.buyerPayment']").change(function(){
			var val = $(this).val();
			var amountOwn_ = $("input[name='toSpv.amountOwn']");
			var amountMort_ = $("input[name='toSpv.amountMort']");
			var amountMortCom_ = $("input[name='toSpv.amountMortCom']");
			var amountMortPsf_ = $("input[name='toSpv.amountMortPsf']");
			amountOwn_.parent().find("i").remove();
			amountMort_.parent().find("i").remove();
			amountMortCom_.parent().find("i").remove();
			amountMortPsf_.parent().find("i").remove();
			switch(val){
			case '1':
				//全款
				amountMort_.prop("disabled",true);
				amountMort_.val('');
				amountMort_.blur();
				amountOwn_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("disabled",true);
				amountMortCom_.val('');
				amountMortCom_.blur();
				amountMortPsf_.prop("disabled",true);
				amountMortPsf_.val('');
				amountMortPsf_.blur();
				break;
			case '2':
				//纯商贷
				amountMort_.prop("disabled",false);
                amountMort_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("disabled",false);
				amountMortCom_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortPsf_.prop("disabled",true);
				amountMortPsf_.val('');
				amountMortPsf_.blur();
				break;
			case '3':
				//组合贷
				amountMort_.prop("disabled",false);
				amountMort_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("disabled",false);
				amountMortCom_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortPsf_.prop("disabled",false);
				amountMortPsf_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				break;
			case '4':
				//公积金贷
				amountMort_.prop("disabled",false);
				amountMort_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("disabled",true);
				amountMortCom_.val('');
				amountMortCom_.blur();
				amountMortPsf_.prop("disabled",false);
				amountMortPsf_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				break;
			} 
		});
		
		$("select[name='toSpv.buyerPayment']").change();

       	$('#loading-example-btn').click(function () {
            btn = $(this);
            simpleLoad(btn, true)
            simpleLoad(btn, false)
        });
    });

	$(".buyinfo, .sellinfo, .pledgeinfo").hide();
	
	if($("#BuyRadio1").is(":checked")){
		$(".buyinfo").show();
	}
	if($("#SellRadio1").is(":checked")){
		$(".sellinfo").show();
	}
	if($("#Pledge2").is(":checked")){
		$(".pledgeinfo").show();
	}
	
	$("#BuyRadio1").on("click", function() {
		$(".buyinfo").show();
	});
	$("#BuyRadio2").on("click", function() {
		$(".buyinfo").hide();
	});
	$("#SellRadio1").on("click", function() {
		$(".sellinfo").show();
	});
	$("#SellRadio2").on("click", function() {
		$(".sellinfo").hide();
	});
	$("#Pledge2").on("click", function() {
		$(".pledgeinfo").show();
	});
	$("#Pledge1").on("click", function() {
		$(".pledgeinfo").hide();
	});


    function simpleLoad(btn, state) {
        if (state) {
            btn.children().addClass('fa-spin');
            btn.contents().last().replaceWith(" Loading");
        } else {
            setTimeout(function () {
                btn.children().removeClass('fa-spin');
                btn.contents().last().replaceWith(" Refresh");
            }, 2000);
        }
    }

    jQuery(function($) {
    $(document).ready( function() {
       $('.stickup-nav-bar').stickUp({
        // $('.col-lg-9').stickUp({
                            parts: {
                              0:'base_info',
                              1:'spvone_info',
                              2:'spvtwo_info',
                              3:'spvthree_info',
                              4:'spvfour_info',
                            },
                            itemClass: 'menuItem',
                            itemHover: 'active',
                            marginTop: 'auto'
                          });

       $('#phone').click(function(){
            toastr.info('Are you the 6 fingered man?');

       });
       
       
       $("#saveBtn").click(function(){
    	   saveBtnClick()
       });

       
       $("#submitBtn").click(function(){
    	 //保存时必须选择关联案件，监管总金额，监管机构
     	  if(!checkFormSubmit()){
     		  return;
     	  }
     	  if(!confirm("确定提交并开启流程吗！")){
     		  return;
     	  }
      	  var totalArr = [];
      	  $("form").each(function(){
      		 var obj = $(this).serializeArray();
      		for(var i in obj){
           		totalArr.push(obj[i]);
      		}
      	  });
    	  
    	  $.ajax({
      		url:ctx+"/spv/submitNewSpv",
      		method:"post",
      		dataType:"json",
      		data:totalArr,   		        				        		    
       		beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
	        complete: function() {
	                 $.unblockUI(); 
	                 if(status=='timeout'){ //超时,status还有success,error等值的情况
		          	  Modal.alert(
					  {
					    msg:"抱歉，系统处理超时。"
					  }); 
			                } 
			            } ,   
			success : function(data) {   
					/*if(data.message){
						alert(data.message);
					}*/
				     if($("#urlType").val() == 'myTask'){    	 
				    	 window.opener.location.reload(); //刷新父窗口
			        	 window.close(); //关闭子窗口.
				     }else{
				    	 alert("流程开启成功！");
				    	 window.location.href = ctx+"/spv/spvList";
				     }
					 $.unblockUI();
				},		
			error : function(errors) {
					$.unblockUI();   
					alert("数据保存出错:"+JSON.stringify(errors));
				}  
       });
     });
       
       
       //风控专员提交申请
       $("#riskOfficerApply").click(function(){
    	   if(!confirm("是否确定提交申请！")){
       		  return;
       	  }
    	   riskAjaxRequest(null,'spvApply',ctx+'/spv/spvApply/deal');	
       });
       
       //风控总监审批通过
       $("#riskDirectorApproveY").click(function(){
    	   if(!confirm("是否确定通过！")){
        		  return;
        	  }
    	   riskAjaxRequest(true,'spvApprove',ctx+'/spv/spvApprove/deal');
       });
       
       //风控总监审批驳回
       $("#riskDirectorApproveN").click(function(){  
    	   
    	   if(!confirm("是否确定驳回！")){
      		  return;
      	  }
    	   var passOrRefuseReason = $("#passOrRefuseReason").val();
    	   if(passOrRefuseReason=='' || passOrRefuseReason==null){
    		   alert("请在备注栏填写驳回原因！");
    		   return;
    	   }    	   
    	   riskAjaxRequest(false,'spvApprove',ctx+'/spv/spvApprove/deal');
       });
       
       $("#RiskOfficerSign").click(function(){
    	   if(!confirm("是否确定签约！")){
        		  return;
        	  }
    	   riskAjaxRequest(null,'spvSign',ctx+'/spv/spvSign/deal');
       });

       $('#chat-discussion').hide();

       $('#label7').click(function(){
            // alert(1);
            $('#base_info').hide();
            // alert(2);
            $('#chat-discussion').show();
       });

       $('#label4').click(function(){
            $('#base_info').show();
            $('#chat-discussion').hide();
       });

    }); 
});
    function saveBtnClick(){
 	   //保存时必须选择关联案件，监管总金额，监管机构
  	  if(!checkFormSave()){
  		  return false;
  	  }
  	  
  	  	var isSuccess = false;
   	  	var totalArr = [];
	      	  $("form").each(function(){
	      		 var obj = $(this).serializeArray();
	      		for(var i in obj){
	           		totalArr.push(obj[i]);
	      		}
	      	  }); 

	      	  $.ajax({
	        		url:ctx+"/spv/saveNewSpv",
	        		method:"post",
	        		dataType:"json",
	        		data:totalArr,
	        		async:false,
	        		beforeSend:function(){  
	 				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	 				$(".blockOverlay").css({'z-index':'9998'});
	             },
	 	        complete: function() {
	 	                 $.unblockUI(); 
	 	                 if(status=='timeout'){ //超时,status还有success,error等值的情况
	 		          	  Modal.alert(
	 					  {
	 					    msg:"抱歉，系统处理超时。"
	 					  }); 
	 			                } 
	 			            } ,   
	 			success : function(data) {   
				    	 isSuccess = true;
	 				     if($("#urlType").val() == 'myTask'){    	 
	 				    	 window.opener.location.reload(); //刷新父窗口
	 			        	 window.close(); //关闭子窗口.
	 				     }else{
	 				    	 alert("数据保存成功！");
	 				    	 window.location.href = ctx+"/spv/spvList";
	 				     }
	 					 $.unblockUI();
	 				},		
	 			error : function(errors) {
	 					$.unblockUI();   
	 					alert("数据保存出错:"+JSON.stringify(errors));
	 				}	 
	      	  });
	      	  
 	   return isSuccess;
    }
    
    
    //保存必填项
	function checkFormSave(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			alert("请选择关联案件！");
			return false;	
		}
		
		var signAmount = $("input[name='toSpvProperty.signAmount']").val();
		if(signAmount != null && signAmount != ''){
			if(!isNumber(signAmount)){
				alert("请填写有效的网签金额！");
				return false;
			}
		}	
		var toSpvAmount = $("input[name='toSpv.amount']").val();
        if(toSpvAmount != null && toSpvAmount != ''){
        	if(!isNumber(toSpvAmount)){
        		alert("请填写有效的监管总金额！");
        		return false;
        	}
        }
        
        var amountOwn = $("input[name='toSpv.amountOwn']");
        var amountMort = $("input[name='toSpv.amountMort']");
        var amountMortCom = $("input[name='toSpv.amountMortCom']");
        var amountMortPsf = $("input[name='toSpv.amountMortPsf']");
        if(amountOwn.val() != null && amountOwn.val() != ''){
        	if(!isNumber(amountOwn.val())){
        		alert("请填写有效的自筹资金！");
        		return false;
        	}
        }
        if(amountMort.val() != null && amountMort.val() != ''){
        	if(!isNumber(amountMort.val())){
        		alert("请填写有效的贷款资金！");
        		return false;
        	}
        }
        if(amountMortCom.val() != null && amountMortCom.val() != ''){
        	if(!isNumber(amountMortCom.val())){
        		alert("请填写有效的商业贷款！");
        		return false;
        	}
        }
        if(amountMortPsf.val() != null && amountMortPsf.val() != ''){
        	if(!isNumber(amountMortPsf.val())){
        		alert("请填写有效的公积金贷款！");
        		return false;
        	}
        }
        $("input[name$='deAmount']").each(function(i,e){
        	if($(e).val() != null && $(e).val() != ''){
        		if(!isNumber($(e).val())){
   				 alert("请填写有效的监管资金金额！");
   				 return fasle;
   			 }
        	} 
		});
		
/*        var toSpvAmount = $("#toSpvAmount").val();
        if(toSpvAmount == null || toSpvAmount == ''){
        	alert("请填写监管总金额");
        	return false;
        }
        var toSpvSpvInsti = $("#toSpvSpvInsti").val();
        if(toSpvSpvInsti == null || toSpvSpvInsti == ''){
        	alert("请填写监管产品");
        	return false;
        }*/
        
		
		/*var length = $("#addTr tr").length;
		
		var isVerify = true;
		
		if(length > 1){
			$("#addTr tr:visible").each(function(index){
				var deCondCode = $("select[name='toSpvDeDetailList[" + index  + "].deCondCode'] option:selected").val();
	    		var payeeAccountType = $("select[name='toSpvDeDetailList[" + index + "].payeeAccountType'] option:selected").val();
	    		var deAmount = $("input[name='toSpvDeDetailList[" + index + "].deAmount'").val();
	    		
	    		if(deCondCode == "" || payeeAccountType == "" || deAmount == ""){
	    			isVerify = false;
	    		}
	        });
		}
		else {
			alert("请至少添加一条资金出款约定！");
			return false;
		}
		
		
		if(!isVerify){
			alert("划转条件、账户、金额这三项信息不能为空！");
			return false;
		}*/
		
		//签约时判断
		  if($("#handle").val() == 'SpvSign'){
			  var spvAccountName = $("input[name='toSpvAccountList[1].name']").val();
			  if(spvAccountName == null || spvAccountName == ''){
				  alert("请填写卖方收款账号名称！");
				  return false;
			  }
			  var spvAccountAcc = $("input[name='toSpvAccountList[1].account']").val();
			  if(spvAccountAcc == null || spvAccountAcc == ''){
				  alert("请填写卖方收款账号！");
				  return false;
			  }
			  var spvAccountTelephone = $("input[name='toSpvAccountList[1].telephone']").val();
			  if(spvAccountTelephone == null || spvAccountTelephone == ''){
				  alert("请填写卖方电话！");
				  return false;
			  }
			  if(spvAccountTelephone != null && spvAccountTelephone != ''){
				  if(!isMobile(spvAccountTelephone)){
					  alert("请填写有效的卖方电话!");
					  return false;
				  }
			  }
			  var spvAccountBank = $("select[name='toSpvAccountList[1].bank'] option:selected").val();
			  if(spvAccountBank == null || spvAccountBank == ''){
				  alert("请填写卖方收款账号开户行！");
				  return false;
			  }
			  var spvConCode = $("input[name='toSpv.spvConCode']").val();
			  if(spvConCode == null || spvConCode == ''){
				  alert("请填写签约合同号！");
				  return false;
			  }
			  var signTime = $("input[name='toSpv.signTime']").val();
			  if(signTime == null || signTime == ''){
				  alert("请填写签约时间！");
				  return false;
			  }
		  }
		
		 return true;
	}
	
	function changeClass(object){
		$(object).focus();
		$(object).addClass("borderClass");
	}
	
	$("input[type='text'],select[name='toSpvAccountList[0].bank']").blur(function(){
		$(this).removeClass("borderClass");
	});
	
	
    //提交必填项
	function checkFormSubmit(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			alert("请选择关联案件！");
			return false;	
		}
		
		/** ------买方信息验证开始--------  **/
		
		var buyerName = $("input[name='spvCustList[0].name']").val();
		if(buyerName == null || buyerName == ''){
			alert("请填写买方姓名！");
			changeClass($("input[name='spvCustList[0].name']"));
			return false;
		}
		
		if(!isName(buyerName)){
			alert("请填写有效的买方姓名！");
			changeClass($("input[name='spvCustList[0].name']"));
			return false;
		}
		
		var buyerGender = $("input[name='spvCustList[0].gender']:checked").val();
		if(buyerGender == null || buyerGender == ''){
			alert("请选择买方性别！");
			return false;
		}
		
		var buyerMobile = $("input[name='spvCustList[0].phone']").val();
		if(buyerMobile == ""){
			alert("请填写买方手机号码！");
			changeClass($("input[name='spvCustList[0].phone']"));
			return false;
		}
		
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(buyerMobile)) 
		{ 
		    alert('请输入有效的买方手机号码！');
		    changeClass($("input[name='spvCustList[0].phone']"));
		    return false; 
		} 
		
		var buyerIdValiDate = $("input[name='spvCustList[0].idValiDate']").val();
		if(buyerIdValiDate == ""){
			alert('请填写买方证件有效期！');
			changeClass($("input[name='spvCustList[0].idValiDate']"));
		    return false; 
		}
		
		var buyerIdCode = $("input[name='spvCustList[0].idCode']").val();
		if(buyerIdCode == ""){
			alert('请填写买方证件编号！');
			changeClass($("input[name='spvCustList[0].idCode']"));
		    return false; 
		}
		
		if(!isIdCardSimple(buyerIdCode)){
			alert("请填写有效的买方证件编号！");
			changeClass($("input[name='spvCustList[0].idCode']"));
			return false;
		}
		
		var buyAddress = $("input[name='spvCustList[0].homeAddr']").val();
		if(buyAddress == ''){
			alert("请填写买房家庭地址！");
			changeClass($("input[name='spvCustList[0].homeAddr']"));
			return false;
		}
		
		/** ------买方信息验证结束--------  **/
		
		/** ------卖方信息验证开始--------  **/
		var sellerName = $("input[name='spvCustList[1].name']").val();
		if(sellerName == ""){
			alert("请填写卖方姓名！");
			changeClass($("input[name='spvCustList[1].name']"));
			return false;
		}
		
		if(!isName(sellerName)){
			alert("请填写有效的卖方姓名！");
			changeClass($("input[name='spvCustList[1].name']"));
			return false;
		}
		
		var sellerGender = $("input[name='spvCustList[1].gender']:checked").val();
		if(sellerGender == null || sellerGender == ''){
			alert("请选择卖方性别！");
			return false;
		}
		
		var sellerMobile = $("input[name='spvCustList[1].phone']").val();
		if(sellerMobile == ""){
			alert("请填写卖方手机号码！");
			changeClass($("input[name='spvCustList[1].phone']"));
			return false;
		}
		
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(sellerMobile)) 
		{ 
		    alert('请输入有效的卖方手机号码！');
		    changeClass($("input[name='spvCustList[1].phone']"));
		    return false; 
		} 
		
		var sellerIdValiDate = $("input[name='spvCustList[1].idValiDate']").val();
		if(sellerIdValiDate == ""){
			alert("请填写卖方证件有效期！");
			changeClass($("input[name='spvCustList[1].idValiDate']"));
			return false;
		}
		
		var sellerIdCode = $("input[name='spvCustList[1].idCode']").val();
		if(sellerIdCode == ""){
			alert("请填写卖方证件编号！");
			changeClass($("input[name='spvCustList[1].idCode']"));
			return false;
		}
		
		if(!isIdCardSimple(sellerIdCode)){
			alert("请填写有效的卖方证件编号！");
			changeClass($("input[name='spvCustList[1].idCode']"));
			return false;
		}
		
		var sellerAddress = $("input[name='spvCustList[1].homeAddr']").val();
		if(sellerAddress == ""){
			alert("请填写卖方家庭地址！");
			changeClass($("input[name='spvCustList[1].homeAddr']"));
			return false;
		}
		
		var sellerHasDele = $("input[name='spvCustList[1].hasDele'][value='1']").is(":checked");
		
		var sellerAgentName = $("input[name='spvCustList[1].agentName']").val();
		if(sellerHasDele && (sellerAgentName == null || sellerAgentName == '')){
			alert("请填写卖方委托人姓名！");
			changeClass($("input[name='spvCustList[1].agentName']"));
			return false;
		}
		
		if(sellerHasDele && (sellerAgentName != null && sellerAgentName != '')){
			if(!isName(sellerAgentName)){
				alert("请填写有效的卖方委托人姓名！");
				changeClass($("input[name='spvCustList[1].agentName']"));
				return false;
			}
		}
		
		var sellerAgentIdCode = $("input[name='spvCustList[1].agentIdCode']").val();
		if(sellerHasDele && (sellerAgentIdCode == null || sellerAgentIdCode == '')){
			alert("请填写卖方委托人证件编号！");
			changeClass($("input[name='spvCustList[1].agentIdCode']"));
			return false;
		}
		if(sellerHasDele && sellerAgentIdCode != null && sellerAgentIdCode != ''){
			if(!isIdCardSimple(sellerAgentIdCode) && !new RegExp("/^\d{15}$/").test(sellerAgentIdCode)){
				alert("请填写有效的卖方委托人证件编号！");
				changeClass($("input[name='spvCustList[1].agentIdCode']"));
				return false;
			}
		}
		
		/** ------卖方信息验证结束--------  **/
		
		/** ------房产及交易信息验证开始--------  **/
		
		var prOwnerName = $("input[name='toSpvProperty.prOwnerName']").val();
		if(prOwnerName == ""){
			alert("请填写房产权利人！");
			changeClass($("input[name='toSpvProperty.prOwnerName']"));
			return false;
		}
		
		if(!isName(prOwnerName)){
			alert("请填写有效的房产权利人！");
			changeClass($("input[name='toSpvProperty.prOwnerName']"));
			return false;
		}
		
		var prNo = $("input[name='toSpvProperty.prNo']").val();
		if(prNo == ""){
			alert("请输入房产证号！");
			changeClass($("input[name='toSpvProperty.prNo']"));
			return false;
		}
		
		var prSize = $("input[name='toSpvProperty.prSize']").val();
		if(prSize == ""){
			alert("请填写房屋面积！");
			changeClass($("input[name='toSpvProperty.prSize']"));
			return false;
		}
		
		if(!isNumber(prSize)){
			alert("请填写有效的房屋面积！");
			changeClass($("input[name='toSpvProperty.prSize']"));
			return false;
		}
		
		var prAddr = $("input[name='toSpvProperty.prAddr']").val();
		if(prAddr == ""){
			alert("请填写产证地址！");
			changeClass($("input[name='toSpvProperty.prAddr']"));
			return false;
		}
		
		var mortgageeName = $("input[name='toSpvProperty.mortgageeName']").val();
		var isMortClear = $("input[name='toSpvProperty.isMortClear'][value='0']").is(":checked");
		if(isMortClear && (mortgageeName == null || mortgageeName == '')){
			alert("请填写抵押方！");
			changeClass($("input[name='toSpvProperty.mortgageeName']"));
			return false;
		}
		
		var mortgageeBank = $("input[name='toSpvProperty.mortgageeBank']").val();
		if(isMortClear && (mortgageeBank == null || mortgageeBank == '')){
			alert("请填写开户行！");
			changeClass($("input[name='toSpvProperty.mortgageeBank']"));
			return false;
		}
		
		var signNo = $("input[name='toSpvProperty.signNo']").val();
		if(signNo == ""){
			alert("请填写网签合同号！");
			changeClass($("input[name='toSpvProperty.signNo']"));
			return false;
		}
		
		if(!isNumber2(signNo)){
			alert("请填写有效的网签合同号！");
			changeClass($("input[name='toSpvProperty.signNo']"));
			return false;
		}
		
		var signAmount = $("input[name='toSpvProperty.signAmount']").val();
		if(signAmount == ""){
			alert("请填写网签金额！");
			changeClass($("input[name='toSpvProperty.signAmount']"));
			return false;
		}
		
		if(!isNumber(signAmount)){
			alert("请填写有效的网签金额！");
			changeClass($("input[name='toSpvProperty.signAmount']"));
			return false;
		}
		
		
		/** ------房产及交易信息验证结束--------  **/
		
		
		/** ------监管资金及账户信息信息验证开始--------  **/
		
		var amount = $("input[name='toSpv.amount']").val();  //监管总金额
		if(amount == ""){
			alert("请填写监管总金额！");
			changeClass($("input[name='toSpv.amount']"));
			return false;
		}
		
		if(!isNumber(amount)){
			alert("请填写有效的监管总金额！");
			changeClass($("input[name='toSpv.amount']"));
			return false;
		}
		
		amount = Number(amount);
		signAmount = Number(signAmount);
		
		if(amount > signAmount){
			alert("监管总金额应小于等于网签金额！");
			changeClass($("input[name='toSpv.amount']"));
			return false;
		}
		
		/** ------监管资金及账户信息信息验证结束--------  **/
		
		/** ------监管资金的支付信息验证开始--------  **/
		
			var amountOwn = $("input[name='toSpv.amountOwn']").val();  //自筹金额
		
		var buyerPayment = $("select[name='toSpv.buyerPayment'] option:selected").val();
		if(buyerPayment == "1"){
			if(amountOwn == ""){
				alert("请填写自筹资金金额！");
				changeClass($("input[name='toSpv.amountOwn']"));
				return false;
			}
			
			if(!isNumber(amountOwn)){
				alert("请填写有效的自筹资金金额！");
				changeClass($("input[name='toSpv.amountOwn']"));
				return false;
			}
			
			amountOwn = Number(amountOwn);
			
			if(amountOwn != amount){
				alert("监管总金额应等于自筹资金+贷款资金之和");
				changeClass($("input[name='toSpv.amountOwn']"));
				return false;
			}
		}
		else if(buyerPayment == "2"){
			amountOwn = Number(amountOwn);
			
			var amountMort = $("input[name='toSpv.amountMort']").val();   //贷款资金
			
			if(amountMort == ""){
				alert("请填写贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(!isNumber(amountMort)){
				alert("请填写有效的贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			var amountMortCom = $("input[name='toSpv.amountMortCom']").val();   //商业贷款金额
			if(amountMortCom == ""){
				alert("请填写商业贷款金额！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
			
			if(!isNumber(amountMortCom)){
				alert("请填写有效的商业贷款金额！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
			
			amountMort = Number(amountMort);
			amountMortCom = Number(amountMortCom);
			
			if(accAdd(amountMort,amountOwn) != amount){
				alert("监管总金额等于自筹资金+贷款资金之和");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(amountMortCom != amountMort){
				alert("贷款资金应等于商业贷款金额！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
			
		}
		else if(buyerPayment == "3"){
			amountOwn = Number(amountOwn);
			
			var amountMort = $("input[name='toSpv.amountMort']").val();   //贷款资金
			
			if(amountMort == ""){
				alert("请填写贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(!isNumber(amountMort)){
				alert("请填写有效的贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			var amountMortCom = $("input[name='toSpv.amountMortCom']").val();   //商业贷款金额
			if(amountMortCom == ""){
				alert("请填写商业贷款金额！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
			
			if(!isNumber(amountMortCom)){
				alert("请填写有效的商业贷款金额！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
			
			var amountMortPsf = $("input[name='toSpv.amountMortPsf']").val();   //公积金金额
			if(amountMortPsf == ''){
				alert("请填写公积金贷款金额！");
				changeClass($("input[name='toSpv.amountMortPsf']"));
				return false;
			}
			
			if(!isNumber(amountMortPsf)){
				alert("请填写有效的公积金贷款金额！");
				changeClass($("input[name='toSpv.amountMortPsf']"));
				return false;
			}
			
			
			amountMort = Number(amountMort);
			amountMortCom = Number(amountMortCom);
			amountMortPsf = Number(amountMortPsf);
			
			if(accAdd(amountMort,amountOwn) != amount){
				alert("监管总金额等于自筹资金+贷款资金之和");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(accAdd(amountMortCom,amountMortPsf) != amountMort){
				alert("贷款资金应等于商业贷款金额+公积金贷款金额之和！");
				changeClass($("input[name='toSpv.amountMortCom']"));
				return false;
			}
		}
		else if(buyerPayment == "4"){
			amountOwn = Number(amountOwn);
			
			var amountMort = $("input[name='toSpv.amountMort']").val();   //贷款资金
			
			if(amountMort == ""){
				alert("请填写贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(!isNumber(amountMort)){
				alert("请填写有效的贷款资金！");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			var amountMortPsf = $("input[name='toSpv.amountMortPsf']").val();   //公积金金额
			if(amountMortPsf == ''){
				alert("请填写公积金贷款金额！");
				changeClass($("input[name='toSpv.amountMortPsf']"));
				return false;
			}
			
			if(!isNumber(amountMortPsf)){
				alert("请填写有效的公积金贷款金额！");
				changeClass($("input[name='toSpv.amountMortPsf']"));
				return false;
			}
			
			amountMort = Number(amountMort);
			amountMortPsf = Number(amountMortPsf);
			
			if(accAdd(amountMort,amountOwn) != amount){
				alert("监管总金额等于自筹资金+贷款资金之和");
				changeClass($("input[name='toSpv.amountMort']"));
				return false;
			}
			
			if(amountMort != amountMortPsf){
				alert("贷款资金应等于公积金贷款金额！");
				changeClass($("input[name='toSpv.amountMortPsf']"));
				return false;
			}
		}
		
		
		/** ------监管资金的支付信息验证结束--------  **/
		
		/** ------资金监管账号信息验证开始--------  **/
		
		var buyerAccountName = $("input[name='toSpvAccountList[0].name']").val();
		if(buyerAccountName == null || buyerAccountName == ''){
			alert("请填写买方退款账户名称！");
			changeClass($("input[name='toSpvAccountList[0].name']"));
			return false;
		}
		
		var buyerAccount = $("input[name='toSpvAccountList[0].account']").val();
		if(buyerAccount == null || buyerAccount == ''){
			alert("请填写买方退款账号！");
			changeClass($("input[name='toSpvAccountList[0].account']"));
			return false;
		}
		
		if(buyerAccount != null && buyerAccount != ''){
		    if(!isNumber2(buyerAccount)){
		    	alert("请填写有效的买方退款账号！");
		    	changeClass($("input[name='toSpvAccountList[0].account']"));
		    	return false;
		    }
		}
		
		var buyerAccountTelephone = $("input[name='toSpvAccountList[0].telephone']").val();
		if(buyerAccountTelephone == null || buyerAccountTelephone == ''){
			alert("请填写买方电话！");
			changeClass($("input[name='toSpvAccountList[0].telephone']"));
			return false;
		}
		
		if(buyerAccountTelephone != null && buyerAccountTelephone != ''){
			if(!isMobile(buyerAccountTelephone)){
				alert("请填写有效的买方电话");
				changeClass($("input[name='toSpvAccountList[0].telephone']"));
				return false;
			}
		}
		
		var buyerBank = $("select[name='toSpvAccountList[0].bank'] option:selected").val();
		if(buyerBank == null || buyerBank == ''){
			alert("请选择买方开户行！");
			changeClass($("select[name='toSpvAccountList[0].bank']"));
			return false;
		}
		
		var spvAccountName = $("select[name='toSpvAccountList[2].name'] option:selected").val();
		if(spvAccountName == null || spvAccountName == ''){
			alert("请选择托管账户名称！");
			changeClass($("select[name='toSpvAccountList[2].name']"));
			return false;
		}
		
		var spvAccount = $("input[name='toSpvAccountList[2].account']").val();
		if(spvAccount == null || spvAccount == ''){
			alert("请填写托管账号！");
			changeClass($("input[name='toSpvAccountList[2].account']"));
			return false;
		}	
		
		if(spvAccount != null && spvAccount != ''){
		    if(!isNumber2(spvAccount)){
		    	alert("请填写有效的托管账号！");
		    	changeClass($("input[name='toSpvAccountList[2].account']"));
		    	return false;
		    }
		}
		
		/** ------资金监管账号信息验证结束--------  **/
		
		/** ------资金出款约定验证开始--------  **/
		var length = $("#addTr tr").length;
		var isVerify = true;
		var isRepeat = false;
		var totalAmount = 0;
		var pkid = $("input[name='spvCustList[0].pkid']").val();
		
		if(pkid == ""){
			if(length <= 1){
				alert("请至少添加一条资金出款约定！");
				return false;
			}
		}
		else {
			if(length < 1){
				alert("请至少添加一条资金出款约定！");
				return false;
			}
		}
		
		$("#addTr tr:visible").each(function(index){
			var deCondCode = $("select[name='toSpvDeDetailList[" + index  + "].deCondCode'] option:selected").val();
    		var payeeAccountType = $("select[name='toSpvDeDetailList[" + index + "].payeeAccountType'] option:selected").val();
    		var deAmount = $("input[name='toSpvDeDetailList[" + index + "].deAmount'").val();
    		
    		if(deCondCode == "" || payeeAccountType == "" || deAmount == ""){
    			isVerify = false;
    			return false;
    		}
    		
    		$("#addTr tr:visible").each(function(index1){
    			if(index != index1 && deCondCode == $("select[name='toSpvDeDetailList[" + index1  + "].deCondCode'] option:selected").val()){
    				isRepeat = true;
    				return false;
    			}
    		});
    		
    		deAmount = Number(deAmount);

    		totalAmount = accAdd(totalAmount,deAmount);
        });
		
		if(isRepeat){
			alert("划转条件不能重复！");
			return false;
		}
		
		if(!isVerify){
			alert("划转条件、账户、金额这三项信息不能为空！");
			return false;
		}
		
		if(totalAmount != amount){
			alert("监管总金额应等于资金出款约定金额之和！");
			return false;
		}
		
		/** ------资金出款约定验证结束--------  **/
		
		return true;
	}
	
	function ajaxCall(url,data){
		$.ajax({
			url:url,
			method:"post",
			dataType:"json",
			data:data,
			beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
            complete: function() {
	                 $.unblockUI(); 
                     if(status=='timeout'){ //超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  }); 
    		                } 
    		            } ,   
			success : function(data) {
				if($("#urlType").val() == 'myTask'){    	 
			    	 window.opener.location.reload(); //刷新父窗口
		        	 window.close(); //关闭子窗口.
				     }else{
				    	 window.location.href = ctx+"/spv/spvList";
				     }
					 $.unblockUI();
				},
				error : function(errors) {
					$.unblockUI();   
					alert("数据保存出错:"+JSON.stringify(errors));
				}
		});
	}
	
	//风控总监审批公共方法   
    function riskAjaxRequest(SpvApplyApprove,handle,url){
	    var data = {caseCode:$("#caseCode").val(),taskId:$("#taskId").val(),instCode:$("#instCode").val(),remark:$("#passOrRefuseReason").val(),source:$("#source").val()};
	    if(SpvApplyApprove != null){
	    	data.SpvApplyApprove = SpvApplyApprove;
	    }
	    //验证参数是否填写正确
    	var startWorkfollow = saveBtnClick();

	    //是否启动流程标记，false不启动，true启动
	    if(!startWorkfollow){
	    	return false;
    	}

	    //调用ajax方法
	    ajaxCall(url,data);
    }
    
    //风控申请审批时只读表单
    function readOnlyRiskForm(){
    	$("input").attr("readOnly",true);
    	$(":radio").attr("disabled",true);
    	$("input[name='spvCustList[0].idValiDate']").attr("disabled",true);
    	$("input[name='spvCustList[1].idValiDate']").attr("disabled",true);
    	$("select").attr("disabled",true);
    }
    
    function getParentBank(selector,selectorBranch,finOrgCode){
		var bankHtml = "<option value=''>请选择</option>";
	    $.ajax({
	    	cache:true,
	    	url:ctx+"/manage/queryParentBankList",
			method:"post",
			dataType:"json",
			async:false,
			data:{nowCode:finOrgCode},
			success:function(data){
				if(data != null){
					for(var i = 0;i<data.length;i++){
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
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
			    data:{finOrgCode:finOrgCode},
			    success:function(data){
		    		if(data != null){
		    			selector.val(data.content);
		    		}
		    	}
			});
		 getBranchBankList(selectorBranch,selector.val(),finOrgCode);
		 return bankHtml;
	}
	function getBranchBankList(selectorBranch,pcode,finOrgCode){
		selectorBranch.find('option').remove();
		selectorBranch[0];
		selectorBranch.append($("<option value=''>请选择</option>"));
		$.ajax({
			cache:true,
		    url:ctx+"/manage/queryBankListByParentCode",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{faFinOrgCode:pcode,nowCode:finOrgCode},
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
		return true;
	}
	
	function getPrdCategory(selector,selectorBranch,prodCode){	
		var prodHtml = "<option value=''>请选择</option>";
		var prdcCode = '';
		$.ajax({
		    url:ctx+"/spv/queryPrdcCodeByProdCode",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{prodCode:prodCode},
		    success:function(data){
		    	console.log(data);
	    		if(data != null){
	    			selector.val(data.prdcCode);
	    			prdcCode = data.prdcCode;
	    		}
	    	}
		});
		
	    $.ajax({
	    	cache:true,
	    	url:ctx+"/spv/queryPrdCategorys",
			method:"post",
			dataType:"json",
			async:false,
			data:{},
			success:function(data){
				if(data != null){
					for(var i = 0;i<data.length;i++){
						if(data[i].prdcCode == prdcCode){
							prodHtml+="<option value='"+data[i].prdcCode+"' selected='selected'>"+data[i].prdcName+"</option>";
						}else{
							prodHtml+="<option value='"+data[i].prdcCode+"' >"+data[i].prdcName+"</option>";		
						}		
					}
				}
			}
	     });
	     selector.find('option').remove();
		 selector.append($(prodHtml));
		 
		 getPrdDetail(selectorBranch,selector.val(),prodCode);
		 return prodHtml;
	}
	
	function getPrdDetail(selectorBranch,selector,prodCode){
		selectorBranch.find('option').remove();
		selectorBranch[0];
		selectorBranch.append($("<option value=''>请选择</option>"));
		$.ajax({
			cache:true,
		    url:ctx+"/spv/queryProdByPrdcCode",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{prdcCode:selector},
	    	success:function(data){
	    		if(data != null){
	    			for(var i = 0;i<data.length;i++){	
						var option = $("<option value='"+data[i].prodCode+"'>"+data[i].prodName+"</option>");
						if(data[i].prodCode==prodCode){
							option.attr("selected",true);
						}
						selectorBranch.append(option);
	    			}
	    		}
	    	}
		 });
		return true;
	}
	
/*********************************************************************************************************************************************/
	 // 身份证号验证 (日期)
	 function isIdCard(cardid) {
	     //身份证正则表达式(18位) 
	     var isIdCard2 = /^[1-9]\d{5}(19\d{2}|[2-9]\d{3})((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}|\d{3}X)$/i;
	     var stard = "10X98765432"; //最后一位身份证的号码
	     var first = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //1-17系数
	     var sum = 0;
	     if (!isIdCard2.test(cardid)) {
	         return false;
	     }
	     var year = cardid.substr(6, 4);
	     var month = cardid.substr(10, 2);
	     var day = cardid.substr(12, 2);
	     var birthday = cardid.substr(6, 8);
	     if (birthday != dateToString(new Date(year + '/' + month + '/' + day))) { //校验日期是否有效
	         return false;
	     }
	     for (var i = 0; i < cardid.length - 1; i++) {
	         sum += cardid[i] * first[i];
	     }
	     var result = sum % 11;
	     var last = stard[result]; //计算出来的最后一位身份证号码
	     if (cardid[cardid.length - 1].toUpperCase() == last) {
	         return true;
	     } else {
	         return false;
	     }
	 }
	 //日期转字符串 返回日期格式yyyyMMdd
	 function dateToString(date) {
	     if (date instanceof Date) {
	         var year = date.getFullYear();
	         var month = date.getMonth() + 1;
	         month = month < 10 ? '0' + month: month;
	         var day = date.getDate();
	         day = day < 10 ? '0' + day: day;
	         return year + month + day;
	     }
	     return '';
	 }
	 
	 //手机号码校验
	 function isMobile(number){
	     reg = /^1[3|4|5|7|8]\d{9}$/;
	     if (!reg.test(number)) {
	         return false; 
	     }
	     return true;
	 }
	 
		
	 //身份证验证(简单)
	function isIdCardSimple(cardId){
		 reg = /^\d{15}$|^\d{17}([0-9]|X)$/i;
	     if (!reg.test(cardId)) {
	         return false; 
	     }
	     return true;
	}
	
	//姓名验证(汉字和英文大小写)
	function isName(name){
		name = name.replace(/\s/g,"");//去除中间空格
		reg = /((^[\u4E00-\u9FA5]{2,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
		if (!reg.test(name)) {
	         return false; 
	     }
	     return true;
	}
	
	//金额验证(两位小数)
	function isNumber(num){
		var reg=/^[1-9]{1}\d*|0(\.\d{1,2})?$/;
		if(!reg.test(num)){
			return false;
		}
		return true;
	}
	
	//金额验证(两位小数)
	function isNumber2(num){
		var reg=/^[1-9]{1}\d*$/;
		if(!reg.test(num)){
			return false;
		}
		return true;
	}

/***************************************************************************************************************************/
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return ((r1/r2)*pow(10,t2-t1)).toFixed(2);
    }
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
};
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(2);
}
//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
};
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    return ((arg1*m+arg2*m)/m).toFixed(2);
}
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
    return accAdd(arg,this);
}
//减法函数
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg2*m-arg1*m)/m).toFixed(2);
}
///给number类增加一个sub方法，调用起来更加方便
Number.prototype.sub = function (arg){
    return accSub(arg,this);
}

function rescCallbocak(){
 	   if($("#urlType").val() == 'myTask'){    	 
 		   window.opener.location.reload(); //刷新父窗口
     	   window.close(); //关闭子窗口.
	     }else{
	    	 window.location.href = ctx+"/spv/spvList";
	     }
	}