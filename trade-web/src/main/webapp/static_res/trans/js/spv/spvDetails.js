
$(document).ready(function(){	    

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
				amountMort_.prop("readonly",true).val('').blur();
				amountOwn_.siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("readonly",true).val('').blur();
				amountMortPsf_.prop("readonly",true).val('').blur();
				break;
			case '2':
				//纯商贷
                amountMort_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortPsf_.prop("readonly",true).val('').blur();
				break;
			case '3':
				//组合贷
				amountMort_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortPsf_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				break;
			case '4':
				//公积金贷
				amountMort_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				amountMortCom_.prop("readonly",true).val('').blur();
				amountMortPsf_.prop("readonly",false).siblings("label").prepend("<i style='color:red;'>*</i> ");
				break;
			} 
		}).change();
		
		//流程开启后只读表单
		if($("#handle").val() != '' && $("#handle").val() != 'SpvApply'){
		    readOnlyRiskForm();
		}

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
    	   if(deleteAndModify()){
    	      saveBtnClick($("#handle").val(),null,'checkForSave',null,null);
    	   }
       });

       
       $("#submitBtn").click(function(){
      	  if(!deleteAndModify()){
     		  return false;
     	  }
     	  if(!checkFormSubmit()){
     		  return false;
     	  }
     	  if($("#handle").val() == null || $("#handle").val() == ''){
     		 window.wxc.confirm("确定提交并开启流程吗？",{"wxcOk":function(){
     			submitNewSpv();
     		 }}); 	
     	  }else{
     		 window.wxc.confirm("确定提交任务吗？",{"wxcOk":function(){
     			submitNewSpv();
     		 }}); 
     	  }
     });
       function submitNewSpv(){
    	   var totalArr = [];
       	  $("form").each(function(){
       		 var obj = $(this).serializeArray();
       		for(var i in obj){
       			if(obj[i].name.indexOf("idValiDate") != -1 && obj[i].value == '长期有效'){
       				obj[i].value = '3000-01-01';
       			}
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
 					if(data.success){
 						window.wxc.success("流程开启成功！",{"wxcOk":function(){
 							window.location.href = ctx+"/spv/spvList";
 						}});  	 
 					}else{
 						window.wxc.error("流程开启失败！",{"wxcOk":function(){
 							window.location.href = ctx+"/spv/spvList";
 						}});  	 
 					}     
 					 $.unblockUI();
 				},		
 			error : function(errors) {
 					$.unblockUI();   
 					window.wxc.error("数据保存出错！");
 				}  
        });
       }
       
       //风控专员提交申请
       $("#riskOfficerApply").click(function(){
           riskAjaxRequest(null,'SpvApply',ctx+'/spv/spvApply/deal');	
       });
       
       //风控总监审批通过
       $("#riskDirectorApproveY").click(function(){
    	   riskAjaxRequest(true,'SpvApprove',ctx+'/spv/spvApprove/deal');
       });
       
       //风控总监审批驳回
       $("#riskDirectorApproveN").click(function(){      	   
    	   riskAjaxRequest(false,'SpvApprove',ctx+'/spv/spvApprove/deal');
       });
       
       //风控专员签约
       $("#RiskOfficerSign").click(function(){
    	   riskAjaxRequest(null,'SpvSign',ctx+'/spv/spvSign/deal');
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
    
    function saveBtnClick(handle,SpvApplyApprove,type,url,data){
    	if(type == 'checkForSubmit' && handle == 'SpvApply'){
      	  if(!checkFormSubmit()){
        		  return false;
        	  }
        }else{
    	  if(!checkFormSave()){
      		  return false;
      	  }
      }	 

  	  if(type == 'checkForSubmit' && handle == 'SpvApply'){
	  		window.wxc.confirm("是否确定提交申请！",{"wxcOk":function(){
	  			saveNewSpv(url,data);
	  		}});
  	  }else if(handle == 'SpvApprove' && SpvApplyApprove){
	  		var passOrRefuseReason = $("#passOrRefuseReason").val();
	  	    if(passOrRefuseReason=='' || passOrRefuseReason==null){
	  	    	window.wxc.alert("请填写审批意见！");
	  		    changeClass($("#passOrRefuseReason"));
	  		    return false;
	  	    }
	  		window.wxc.confirm("是否确定通过！",{"wxcOk":function(){	  			
	  			saveNewSpv(url,data);
	  		}});
  	  }else if(handle == 'SpvApprove' && !SpvApplyApprove){
	  		var passOrRefuseReason = $("#passOrRefuseReason").val();
	  	    if(passOrRefuseReason=='' || passOrRefuseReason==null){
	  	    	window.wxc.alert("请填写审批意见！");
	  		    changeClass($("#passOrRefuseReason"));
	  		    return false;
	  	    }
	  		window.wxc.confirm("是否确定驳回！",{"wxcOk":function(){
	  			saveNewSpv(url,data);
	  		}});
  	  }else if(handle == 'SpvSign'){
	  		window.wxc.confirm("是否确定签约！",{"wxcOk":function(){
	  			saveNewSpv(url,data);
	  		}});
  	  }
    }
    
    function saveNewSpv(url,data1){
   	  	var totalArr = [];
	      	  $("form").each(function(){
	      		 var obj = $(this).serializeArray();
	      		for(var i in obj){
	      			if(obj[i].name.indexOf("idValiDate") != -1 && obj[i].value == '长期有效'){
	      				obj[i].value = '3000-01-01';
	      			}
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
				    	 if(data.success){
				    		 ajaxCall(url,data1);
				    		 
				    		 if($("#urlType").val() == 'myTask'){    	 
		 				    	 window.opener.location.reload(); //刷新父窗口
		 			        	 window.close(); //关闭子窗口.
		 				     }else{
		 				    	window.wxc.success(data.message);
		 				    	 //window.location.href = ctx+"/spv/saveHTML?pkid="+data.content;
						    	window.location.href = ctx+"/spv/spvList";
					     } 
				    	 }else{
				    		 window.wxc.error("保存资金监管签约失败！");
				    	 }
				    	 
	 					 $.unblockUI();
	 				},		
	 			error : function(errors) {
	 					$.unblockUI();   
	 					window.wxc.error("数据保存出错！");
	 				}	 
	      	  });
	      	  
    }
    
    //保存必填项
	function checkFormSave(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			window.wxc.alert("请选择关联案件！");
			return false;	
		}
		
		var signAmount = $("input[name='toSpvProperty.signAmount']").val();
		if(signAmount != null && signAmount != ''){
			if(!isNumber(signAmount)){
				window.wxc.alert("请填写有效的网签金额！(首位非0且小数位不超过2位的数字)");
				changeClass($("input[name='toSpvProperty.signAmount']"));
				return false;
			}
		}	
		var toSpvAmount = $("input[name='toSpv.amount']").val();
        if(toSpvAmount != null && toSpvAmount != ''){
        	if(!isNumber(toSpvAmount)){
        		window.wxc.alert("请填写有效的监管总金额！(首位非0且小数位不超过2位的数字)");
        		changeClass($("input[name='toSpvProperty.signAmount']"));
        		return false;
        	}
        }
        
        var amountOwn = $("input[name='toSpv.amountOwn']");
        var amountMort = $("input[name='toSpv.amountMort']");
        var amountMortCom = $("input[name='toSpv.amountMortCom']");
        var amountMortPsf = $("input[name='toSpv.amountMortPsf']");
        if(amountOwn.val() != null && amountOwn.val() != ''){
        	if(!isNumber(amountOwn.val())){
        		window.wxc.alert("请填写有效的自筹资金！(首位非0且小数位不超过2位的数字)");
        		changeClass($("input[name='toSpv.amountOwn']"));
        		return false;
        	}
        }
        if(amountMort.val() != null && amountMort.val() != ''){
        	if(!isNumber(amountMort.val())){
        		window.wxc.alert("请填写有效的贷款资金！(首位非0且小数位不超过2位的数字)");
        		changeClass($("input[name='toSpv.amountMort']"));
        		return false;
        	}
        }
        if(amountMortCom.val() != null && amountMortCom.val() != ''){
        	if(!isNumber(amountMortCom.val())){
        		window.wxc.alert("请填写有效的商业贷款！(首位非0且小数位不超过2位的数字)");
        		changeClass($("input[name='toSpv.amountMortCom']"));
        		return false;
        	}
        }
        if(amountMortPsf.val() != null && amountMortPsf.val() != ''){
        	if(!isNumber(amountMortPsf.val())){
        		window.wxc.alert("请填写有效的公积金贷款！(首位非0且小数位不超过2位的数字)");
        		changeClass($("input[name='toSpv.amountMortPsf']"));
        		return false;
        	}
        }
        
        var deAmountFlag = true;
        var deAmountEle;
        $("input[name$='deAmount']").each(function(i,e){
        	if($(e).val() != null && $(e).val() != ''){
        		if(!isNumber($(e).val())){
        		 deAmountFlag = false;
        		 deAmountEle = $(e);
   				 return false;
   			 }
        	} 
		});

        if(!deAmountFlag){
        	window.wxc.alert("请填写有效的监管资金金额！(首位非0且小数位不超过2位的数字)");
		    changeClass(deAmountEle);
			return false;
        }
		
		//签约时判断
		  if($("#handle").val() == 'SpvSign'){
			  var spvAccountName = $("input[name='toSpvAccountList[1].name']").val();
			  if(spvAccountName == null || spvAccountName == ''){
				  window.wxc.alert("请填写卖方收款账号名称！");
				  changeClass($("input[name='toSpvAccountList[1].name']"));
				  return false;
			  }
			  var spvAccountAcc = $("input[name='toSpvAccountList[1].account']").val();
			  if(spvAccountAcc == null || spvAccountAcc == ''){
				  window.wxc.alert("请填写卖方收款账号！");
				  changeClass($("input[name='toSpvAccountList[1].account']"));
				  return false;
			  }
			  var spvAccountTelephone = $("input[name='toSpvAccountList[1].telephone']").val();
			  if(spvAccountTelephone == null || spvAccountTelephone == ''){
				  window.wxc.alert("请填写卖方电话！");
				  changeClass($("input[name='toSpvAccountList[1].telephone']"));
				  return false;
			  }
			  if(spvAccountTelephone != null && spvAccountTelephone != ''){
				  if(!isMobile(spvAccountTelephone)){
					  window.wxc.alert("请填写有效的卖方电话！(1(3、4、5、7、8)+9位数字)");
					  changeClass($("input[name='toSpvAccountList[1].telephone']"));
					  return false;
				  }
			  }
			  var spvAccountBank = $("input[name='toSpvAccountList[1].bank']").val();
			  if(spvAccountBank == null || spvAccountBank == ''){
				  window.wxc.alert("请填写卖方收款账号开户行（银行）！");
				  changeClass($("input[name='toSpvAccountList[1].bank']"));
				  return false;
			  }
			  var spvAccountBranchBank = $("input[name='toSpvAccountList[1].branchBank']").val();
			  if(spvAccountBranchBank == null || spvAccountBranchBank == ''){
				  window.wxc.alert("请填写卖方收款账号开户行（支行）！");
				  changeClass($("input[name='toSpvAccountList[1].branchBank']"));
				  return false;
			  }
			  var spvConCode = $("input[name='toSpv.spvConCode']").val();
			  if(spvConCode == null || spvConCode == ''){
				  window.wxc.alert("请填写签约合同号！");
				  changeClass($("input[name='toSpv.spvConCode']"));
				  return false;
			  }
			  var signTime = $("input[name='toSpv.signTime']").val();
			  if(signTime == null || signTime == ''){
				  window.wxc.alert("请填写签约时间！");
				  changeClass($("input[name='toSpv.signTime']"));
				  return false;
			  }
		  }
		
		 return true;
	}
	
	function changeClass(object){
		$(object).focus().addClass("borderClass").blur(function(){
			$(this).removeClass("borderClass");
		});
	}
	
    //提交必填项
	function checkFormSubmit(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			window.wxc.alert("请选择关联案件！");
			return false;	
		}
		
		/** ------买方信息验证开始--------  **/
		
		var buyerName = $("input[name='spvCustList[0].name']").val();
		if(buyerName == null || buyerName == ''){
			window.wxc.alert("请填写买方姓名！");
			changeClass($("input[name='spvCustList[0].name']"));
			return false;
		}
		
		if(!isName(buyerName)){
			window.wxc.alert("请填写有效的买方姓名！(1-5个汉字或者多个英文)");
			changeClass($("input[name='spvCustList[0].name']"));
			return false;
		}
		
		var buyerGender = $("input[name='spvCustList[0].gender']:checked").val();
		if(buyerGender == null || buyerGender == ''){
			window.wxc.alert("请选择买方性别！");
			return false;
		}
		
		var buyerMobile = $("input[name='spvCustList[0].phone']").val();
		if(buyerMobile == ""){
			window.wxc.alert("请填写买方手机号码！");
			changeClass($("input[name='spvCustList[0].phone']"));
			return false;
		}
		
		if(!isMobile(buyerMobile)) 
		{ 
			window.wxc.alert('请输入有效的买方手机号码！');
		    changeClass($("input[name='spvCustList[0].phone']"));
		    return false; 
		} 
		
		var buyerIdValiDate = $("input[name='spvCustList[0].idValiDate']").val();
		if(buyerIdValiDate == ""){
			window.wxc.alert('请填写买方证件有效期！');
			changeClass($("input[name='spvCustList[0].idValiDate']"));
		    return false; 
		}
		
		var buyerIdCode = $("input[name='spvCustList[0].idCode']").val();
		if(buyerIdCode == ""){
			window.wxc.alert('请填写买方证件编号！');
			changeClass($("input[name='spvCustList[0].idCode']"));
		    return false; 
		}
		
		if($("select[name='spvCustList[0].idType']").val() == 'SFZ'){
			if(!isIdCard(buyerIdCode)){
				window.wxc.alert("请填写有效的买方证件编号！");
				changeClass($("input[name='spvCustList[0].idCode']"));
				return false;
			}
		}else{
			if(!/^[a-zA-Z0-9]+$/.test(buyerIdCode)){
				window.wxc.alert("请填写有效的买方证件编号！");
				changeClass($("input[name='spvCustList[0].idCode']"));
				return false;
			}
		}
		
		var buyAddress = $("input[name='spvCustList[0].homeAddr']").val();
		if(buyAddress == ''){
			window.wxc.alert("请填写买房家庭地址！");
			changeClass($("input[name='spvCustList[0].homeAddr']"));
			return false;
		}
		
		var buyerHasDele = $("input[name='spvCustList[0].hasDele'][value='1']").is(":checked");
		
		var buyerAgentName = $("input[name='spvCustList[0].agentName']").val();
		if(buyerHasDele && (buyerAgentName == null || buyerAgentName == '')){
			window.wxc.alert("请填写买方委托人姓名！");
			changeClass($("input[name='spvCustList[0].agentName']"));
			return false;
		}
		
		if(buyerHasDele && (buyerAgentName != null && buyerAgentName != '')){
			if(!isName(buyerAgentName)){
				window.wxc.alert("请填写有效的买方委托人姓名！(1-5个汉字或者多个英文)");
				changeClass($("input[name='spvCustList[0].agentName']"));
				return false;
			}
		}
		
		var buyerAgentIdCode = $("input[name='spvCustList[0].agentIdCode']").val();
		if(buyerHasDele && (buyerAgentIdCode == null || buyerAgentIdCode == '')){
			window.wxc.alert("请填写买方委托人证件编号！");
			changeClass($("input[name='spvCustList[0].agentIdCode']"));
			return false;
		}
		if(buyerHasDele && $("select[name='spvCustList[0].agentIdType']").val() == 'SFZ'){
			if(!isIdCard(buyerAgentIdCode)){
				window.wxc.alert("请填写有效的买方委托人证件编号！");
				changeClass($("input[name='spvCustList[0].agentIdCode']"));
				return false;
			}
		}else if(buyerHasDele &&  $("select[name='spvCustList[0].agentIdType']").val() != 'SFZ'){
			if(!/^[a-zA-Z0-9]+$/.test(buyerAgentIdCode)){
				window.wxc.alert("请填写有效的买方委托人证件编号！");
				changeClass($("input[name='spvCustList[0].agentIdCode']"));
				return false;
			}
		}
		
		/** ------买方信息验证结束--------  **/
		
		/** ------卖方信息验证开始--------  **/
		var sellerName = $("input[name='spvCustList[1].name']").val();
		if(sellerName == ""){
			window.wxc.alert("请填写卖方姓名！");
			changeClass($("input[name='spvCustList[1].name']"));
			return false;
		}
		
		if(!isName(sellerName)){
			window.wxc.alert("请填写有效的卖方姓名！(1-5个汉字或者多个英文)");
			changeClass($("input[name='spvCustList[1].name']"));
			return false;
		}
		
		var sellerGender = $("input[name='spvCustList[1].gender']:checked").val();
		if(sellerGender == null || sellerGender == ''){
			window.wxc.alert("请选择卖方性别！");
			return false;
		}
		
		var sellerMobile = $("input[name='spvCustList[1].phone']").val();
		if(sellerMobile == ""){
			window.wxc.alert("请填写卖方手机号码！");
			changeClass($("input[name='spvCustList[1].phone']"));
			return false;
		}
		 
		if(!isMobile(sellerMobile)) 
		{ 
			window.wxc.alert('请输入有效的卖方手机号码！');
		    changeClass($("input[name='spvCustList[1].phone']"));
		    return false; 
		} 
		
		var sellerIdValiDate = $("input[name='spvCustList[1].idValiDate']").val();
		if(sellerIdValiDate == ""){
			window.wxc.alert("请填写卖方证件有效期！");
			changeClass($("input[name='spvCustList[1].idValiDate']"));
			return false;
		}
		
		var sellerIdCode = $("input[name='spvCustList[1].idCode']").val();
		if(sellerIdCode == ""){
			window.wxc.alert("请填写卖方证件编号！");
			changeClass($("input[name='spvCustList[1].idCode']"));
			return false;
		}
		
		if($("select[name='spvCustList[1].idType']").val() == 'SFZ'){
			if(!isIdCard(sellerIdCode)){
				window.wxc.alert("请填写有效的卖方证件编号！");
				changeClass($("input[name='spvCustList[1].idCode']"));
				return false;
			}
		}else{
			if(!/^[a-zA-Z0-9]+$/.test(sellerIdCode)){
				window.wxc.alert("请填写有效的卖方证件编号！");
				changeClass($("input[name='spvCustList[1].idCode']"));
				return false;
			}
		}
		
		var sellerAddress = $("input[name='spvCustList[1].homeAddr']").val();
		if(sellerAddress == ""){
			window.wxc.alert("请填写卖方家庭地址！");
			changeClass($("input[name='spvCustList[1].homeAddr']"));
			return false;
		}
		
		var sellerHasDele = $("input[name='spvCustList[1].hasDele'][value='1']").is(":checked");
		
		var sellerAgentName = $("input[name='spvCustList[1].agentName']").val();
		if(sellerHasDele && (sellerAgentName == null || sellerAgentName == '')){
			window.wxc.alert("请填写卖方委托人姓名！");
			changeClass($("input[name='spvCustList[1].agentName']"));
			return false;
		}
		
		if(sellerHasDele && (sellerAgentName != null && sellerAgentName != '')){
			if(!isName(sellerAgentName)){
				window.wxc.alert("请填写有效的卖方委托人姓名！(1-5个汉字或者多个英文)");
				changeClass($("input[name='spvCustList[1].agentName']"));
				return false;
			}
		}
		
		var sellerAgentIdCode = $("input[name='spvCustList[1].agentIdCode']").val();
		if(sellerHasDele && (sellerAgentIdCode == null || sellerAgentIdCode == '')){
			window.wxc.alert("请填写卖方委托人证件编号！");
			changeClass($("input[name='spvCustList[1].agentIdCode']"));
			return false;
		}
		if(sellerHasDele && $("select[name='spvCustList[1].agentIdType']").val() == 'SFZ'){
			if(!isIdCard(sellerAgentIdCode)){
				window.wxc.alert("请填写有效的卖方委托人证件编号！");
				changeClass($("input[name='spvCustList[1].agentIdCode']"));
				return false;
			}
		}else if(sellerHasDele &&  $("select[name='spvCustList[1].agentIdType']").val() != 'SFZ'){
			if(!/^[a-zA-Z0-9]+$/.test(sellerAgentIdCode)){
				window.wxc.alert("请填写有效的卖方委托人证件编号！");
				changeClass($("input[name='spvCustList[1].agentIdCode']"));
				return false;
			}
		}
		
		/** ------卖方信息验证结束--------  **/
		
		/** ------房产及交易信息验证开始--------  **/
		
		var prOwnerName = $("input[name='toSpvProperty.prOwnerName']").val();
		if(prOwnerName == ""){
			window.wxc.alert("请填写房产权利人！");
			changeClass($("input[name='toSpvProperty.prOwnerName']"));
			return false;
		}
		
		if(!isName(prOwnerName)){
			window.wxc.alert("请填写有效的房产权利人！(1-5个汉字或者多个英文)");
			changeClass($("input[name='toSpvProperty.prOwnerName']"));
			return false;
		}
		
		var prNo = $("input[name='toSpvProperty.prNo']").val();
		if(prNo == ""){
			window.wxc.alert("请输入房产证号！");
			changeClass($("input[name='toSpvProperty.prNo']"));
			return false;
		}
		
		var prSize = $("input[name='toSpvProperty.prSize']").val();
		if(prSize == ""){
			window.wxc.alert("请填写房屋面积！");
			changeClass($("input[name='toSpvProperty.prSize']"));
			return false;
		}
		
		if(!isNumber(prSize)){
			window.wxc.alert("请填写有效的房屋面积！(首位非0且小数位不超过2位的数字)");
			changeClass($("input[name='toSpvProperty.prSize']"));
			return false;
		}
		
		var prAddr = $("input[name='toSpvProperty.prAddr']").val();
		if(prAddr == ""){
			window.wxc.alert("请填写产证地址！");
			changeClass($("input[name='toSpvProperty.prAddr']"));
			return false;
		}
		
		var mortgageeName = $("input[name='toSpvProperty.mortgageeName']").val();
		var isMortClear = $("input[name='toSpvProperty.isMortClear'][value='0']").is(":checked");
		if(isMortClear && (mortgageeName == null || mortgageeName == '')){
			window.wxc.alert("请填写抵押方！");
			changeClass($("input[name='toSpvProperty.mortgageeName']"));
			return false;
		}
		
		var mortgageeBank = $("input[name='toSpvProperty.mortgageeBank']").val();
		if(isMortClear && (mortgageeBank == null || mortgageeBank == '')){
			window.wxc.alert("请填写开户行！");
			changeClass($("input[name='toSpvProperty.mortgageeBank']"));
			return false;
		}
		
		var signNo = $("input[name='toSpvProperty.signNo']").val();
		if(signNo == ""){
			window.wxc.alert("请填写网签合同号！");
			changeClass($("input[name='toSpvProperty.signNo']"));
			return false;
		}
		
		if(!isNumber3(signNo)){
			window.wxc.alert("请填写有效的网签合同号！(纯数字)");
			changeClass($("input[name='toSpvProperty.signNo']"));
			return false;
		}
		
		var signAmount = $("input[name='toSpvProperty.signAmount']");
		if(signAmount.val() == ""){
			window.wxc.alert("请填写网签金额！");
			changeClass(signAmount);
			return false;
		}
		
		if(!isNumber(signAmount.val())){
			window.wxc.alert("请填写有效的网签金额！(首位非0且小数位不超过2位的数字)");
			changeClass(signAmount);
			return false;
		}
		
		
		/** ------房产及交易信息验证结束--------  **/
		
		
		/** ------监管资金及账户信息信息验证开始--------  **/
		
		var amount = $("input[name='toSpv.amount']");  //监管总金额
		if(amount.val() == ""){
			window.wxc.alert("请填写监管总金额！");
			changeClass(amount);
			return false;
		}
		
		if(!isNumber(amount.val())){
			window.wxc.alert("请填写有效的监管总金额！(首位非0且小数位不超过2位的数字)");
			changeClass(amount);
			return false;
		}
		
		amountV = Number(amount.val());
		signAmountV = Number(signAmount.val());
		
		if(amountV > signAmountV){
			window.wxc.alert("监管总金额应小于等于网签金额！");
			changeClass(amount);
			return false;
		}
        
		/** ------监管资金及账户信息信息验证结束--------  **/
		
		/** ------监管资金的支付信息验证开始--------  **/

		var amountOwn = $("input[name='toSpv.amountOwn']");
        var amountMort = $("input[name='toSpv.amountMort']");
        var amountMortCom = $("input[name='toSpv.amountMortCom']");
        var amountMortPsf = $("input[name='toSpv.amountMortPsf']"); 

        if(amountOwn.parent().find("i").length>0 && (amountOwn.val() == null || amountOwn.val() == '')){
        	window.wxc.alert("请填写自筹资金！");
        	changeClass(amountOwn);
        	return false;
        }  
        if(amountOwn.val() != null && amountOwn.val() != ''){
        	if(!isNumber(amountOwn.val())){
        		window.wxc.alert("请填写有效的自筹资金！(首位非0且小数位不超过2位的数字)");
        		changeClass(amountOwn);
        		return false;
        	}
        }
        if(amountMort.parent().find("i").length>0 && (amountMort.val() == null || amountMort.val() == '')){
        	window.wxc.alert("请填写贷款资金！");
        	changeClass(amountMort);
        	return false;
        }
        if(amountMort.val() != null && amountMort.val() != ''){
        	if(!isNumber(amountMort.val())){
        		window.wxc.alert("请填写有效的贷款资金！(首位非0且小数位不超过2位的数字)");
        		changeClass(amountMort);
        		return false;
        	}
        }
        if(amountMortCom.parent().find("i").length>0 && (amountMortCom.val() == null || amountMortCom.val() == '')){
        	window.wxc.alert("请填写商业贷款！");
        	changeClass(amountMortCom);
        	return false;
        }
        if(amountMortCom.val() != null && amountMortCom.val() != ''){
        	if(!isNumber(amountMortCom.val())){
        		window.wxc.alert("请填写有效的商业贷款！(首位非0且小数位不超过2位的数字)");
        		changeClass(amountMortCom);
        		return false;
        	}
        }
        if(amountMortPsf.parent().find("i").length>0 && (amountMortPsf.val() == null || amountMortPsf.val() == '')){
        	window.wxc.alert("请填写公积金贷款！");
        	changeClass(amountMortPsf);
        	return false;
        }
        if(amountMortPsf.val() != null && amountMortPsf.val() != ''){
        	if(!isNumber(amountMortPsf.val())){
        		window.wxc.alert("请填写有效的公积金贷款！(首位非0且小数位不超过2位的数字)");
        		changeClass(amountMortPsf);
        		return false;
        	}
        }
        
        var amountOwnV = Number(amountOwn.val());
        var amountMortV = Number(amountMort.val());
        var amountMortComV = Number(amountMortCom.val());
        var amountMortPsfV = Number(amountMortPsf.val()); 

        if(amountMortV != accAdd(amountMortComV,amountMortPsfV)){
        	window.wxc.alert("贷款资金需等于商业贷款与公积金贷款之和！");
        	changeClass(amountMort);
        	return false;
        }
        
        if(amountV != accAdd(amountOwnV,amountMortV)){
        	window.wxc.alert("监管总金额需等于自筹资金与贷款资金之和！");
        	changeClass(amount);
        	return false;
        }	
		
		/** ------监管资金的支付信息验证结束--------  **/
		
		/** ------资金监管账号信息验证开始--------  **/
        /**卖方*/
		var sellerAccount = $("input[name='toSpvAccountList[1].account']").val();
		if(sellerAccount != null && sellerAccount != ''){
			if(!isNumber2(sellerAccount)){
				window.wxc.alert("请填写有效的卖方收款账号！(纯数字(首位非0))");
				changeClass($("input[name='toSpvAccountList[1].account']"));
				return false;
			}
		}
		
		var sellerAccountTelephone = $("input[name='toSpvAccountList[1].telephone']").val();
		if(sellerAccountTelephone != null && sellerAccountTelephone != ''){
			if(!isMobile(sellerAccountTelephone)){
				window.wxc.alert("请填写有效的卖方电话！(1(3、4、5、7、8)+9位数字)");
				changeClass($("input[name='toSpvAccountList[1].telephone']"));
				return false;
			}
		}
		/**买方*/
		var buyerAccountName = $("input[name='toSpvAccountList[0].name']").val();
		if(buyerAccountName == null || buyerAccountName == ''){
			window.wxc.alert("请填写买方退款账户名称！");
			changeClass($("input[name='toSpvAccountList[0].name']"));
			return false;
		}
		
		var buyerAccount = $("input[name='toSpvAccountList[0].account']").val();
		if(buyerAccount == null || buyerAccount == ''){
			window.wxc.alert("请填写买方退款账号！");
			changeClass($("input[name='toSpvAccountList[0].account']"));
			return false;
		}
		
		if(buyerAccount != null && buyerAccount != ''){
		    if(!isNumber2(buyerAccount)){
		    	window.wxc.alert("请填写有效的买方退款账号！(纯数字(首位非0))");
		    	changeClass($("input[name='toSpvAccountList[0].account']"));
		    	return false;
		    }
		}
		
		var buyerAccountTelephone = $("input[name='toSpvAccountList[0].telephone']").val();
		if(buyerAccountTelephone == null || buyerAccountTelephone == ''){
			window.wxc.alert("请填写买方电话！");
			changeClass($("input[name='toSpvAccountList[0].telephone']"));
			return false;
		}
		
		if(buyerAccountTelephone != null && buyerAccountTelephone != ''){
			if(!isMobile(buyerAccountTelephone)){
				window.wxc.alert("请填写有效的买方电话！(1(3、4、5、7、8)+9位数字)");
				changeClass($("input[name='toSpvAccountList[0].telephone']"));
				return false;
			}
		}
		
		var buyerBank = $("input[name='toSpvAccountList[0].bank']").val();
		if(buyerBank == null || buyerBank == ''){
			window.wxc.alert("请填写买方开户行（银行）！");
			changeClass($("input[name='toSpvAccountList[0].bank']"));
			return false;
		}
		
		var buyerBranchBank = $("input[name='toSpvAccountList[0].branchBank']").val();
		if(buyerBranchBank == null || buyerBranchBank == ''){
			window.wxc.alert("请填写买方开户行（支行）！");
			changeClass($("input[name='toSpvAccountList[0].branchBank']"));
			return false;
		}
		
		/**托管方*/
		var spvAccountName = $("select[name='toSpvAccountList[2].name'] option:selected").val();
		if(spvAccountName == null || spvAccountName == ''){
			window.wxc.alert("请选择托管账户名称！");
			changeClass($("select[name='toSpvAccountList[2].name']"));
			return false;
		}
		
		var spvAccount = $("input[name='toSpvAccountList[2].account']").val();
		if(spvAccount == null || spvAccount == ''){
			window.wxc.alert("请填写托管账号！");
			changeClass($("input[name='toSpvAccountList[2].account']"));
			return false;
		}	
		
		if(spvAccount != null && spvAccount != ''){
		    if(!isNumber2(spvAccount)){
		    	window.wxc.alert("请填写有效的托管账号！(纯数字(首位非0))");
		    	changeClass($("input[name='toSpvAccountList[2].account']"));
		    	return false;
		    }
		}
		
		/**新增*/
		var customFlag = true;
   $("input[name^='toSpvAccountList'][name$='accountType']").each(function(i,e){
	   var index = $(e).attr("name").replace('toSpvAccountList[','').replace('].accountType','');
	   if(parseInt(index) > 3){
		var customAccountName = $("input[name='toSpvAccountList["+index+"].name']").val();
		if(customAccountName == null || customAccountName == ''){
			window.wxc.alert("请填写新增账户名称！");
			changeClass($("input[name='toSpvAccountList["+index+"].name']"));
			customFlag = false;
			return false;
		}
		
		var customAccount = $("input[name='toSpvAccountList["+index+"].account']").val();
		if(customAccount == null || customAccount == ''){
			window.wxc.alert("请填写新增账户账号！");
			changeClass($("input[name='toSpvAccountList["+index+"].account']"));
			customFlag = false;
			return false;
		}
		
		if(customAccount != null && customAccount != ''){
		    if(!isNumber2(customAccount)){
		    	window.wxc.alert("请填写有效的新增账户账号！(纯数字(首位非0))");
		    	changeClass($("input[name='toSpvAccountList["+index+"].account']"));
		    	customFlag = false;
		    	return false;
		    }
		}
		
		var customAccountTelephone = $("input[name='toSpvAccountList["+index+"].telephone']").val();
		if(customAccountTelephone == null || customAccountTelephone == ''){
			window.wxc.alert("请填写新增账户电话！");
			changeClass($("input[name='toSpvAccountList["+index+"].telephone']"));
			customFlag = false;
			return false;
		}
		
		if(customAccountTelephone != null && customAccountTelephone != ''){
			if(!isMobile(customAccountTelephone)){
				window.wxc.alert("请填写有效的新增账户电话！(1(3、4、5、7、8)+9位数字)");
				changeClass($("input[name='toSpvAccountList["+index+"].telephone']"));
				customFlag = false;
				return false;
			}
		}
		
		var customBank = $("input[name='toSpvAccountList["+index+"].bank']").val();
		if(customBank == null || customBank == ''){
			window.wxc.alert("请填写新增账户开户行（银行）！");
			changeClass($("input[name='toSpvAccountList["+index+"].bank']"));
			customFlag = false;
			return false;
		}
		
		var customBranchBank = $("input[name='toSpvAccountList["+index+"].branchBank']").val();
		if(customBranchBank == null || customBranchBank == ''){
			window.wxc.alert("请填写新增账户开户行（支行）！");
			changeClass($("input[name='toSpvAccountList["+index+"].branchBank']"));
			customFlag = false;
			return false;
		}
		}
		});
      
        if(!customFlag){
        	return false;
        }
        
        var applyUser = $("input[name='toSpv.applyUser']").val();
        if(applyUser == null || applyUser == ''){
        	window.wxc.alert("请选择申请人！");
        	changeClass($("input[id='realName']"));
			return false;
        }
		
		/** ------资金监管账号信息验证结束--------  **/
		
		/** ------资金出款约定验证开始--------  **/
		var length = $("#addTr tr").length;
		var isVerify = true;
		var isRepeat = false;
		var rowElement1,rowElement2;

		$("#addTr tr:visible").each(function(i,e){
			var deCondCode = $(e).find("select[name$='deCondCode'] option:selected").val();
    		var payeeAccountType = $(e).find("select[name$='payeeAccountType'] option:selected").val();
    		var deAmount = $(e).find("input[name$='deAmount']").val();
    		
    		if(deCondCode == "" || payeeAccountType == "" || deAmount == ""){
    			rowElement1 = $(e).find("select[name$='deCondCode']");
    			isVerify = false;
    			return false;
    		}
    		
    		$("#addTr tr:visible").each(function(i_,e_){
    			if(i != i_ && 
    					deCondCode == $(e_).find("select[name$='deCondCode'] option:selected").val() &&
    					payeeAccountType == $(e_).find("select[name$='payeeAccountType'] option:selected").val()){
    				rowElement2 = $(e_).find("select[name$='deCondCode']");
    				isRepeat = true;
    				return false;
    			}
    		});
    		if(isRepeat){
    			return false;
    		}
        });
		
		if(!isVerify){
			window.wxc.alert("划转条件、账户、金额这三项信息不能为空！");
			changeClass(rowElement1);
			return false;
		}
		
		if(isRepeat){
			window.wxc.alert("同一划转条件同一账户只允许一条监管合约！");
			changeClass(rowElement2);
			return false;
		}
	
		var rowsAmount = getRowsAmount();
		if(rowsAmount != amountV){
			window.wxc.alert("监管总金额应等于资金出款约定金额之和！");
			return false;
		}
		
		/** ------资金出款约定验证结束--------  **/
		
		/** ------附件验证开始--------------  **/
		var imgIsEmpty = false;
		$("ul[class='filelist']").each(function(i,e){
			var length = $(e).find("img").length;
			if(length == 0){
				imgIsEmpty = true;
				return false;
			}
		});
		
		if(imgIsEmpty){
			window.wxc.alert("每种类型请至少上传一张附件！");
			return false;
		}
		/** ------附件验证结束--------------  **/
		
		return true;
	}
	
	
	//汇总出款约定表格金额
	function getRowsAmount(){
		var total = 0;
	
		$("#addTr tr:visible").each(function(index,element){
    		var deAmount = $(element).find("input[name$='deAmount']").val();		
    		if(deAmount == ""){
    			return true;
    		}
    		total = accAdd(total,Number(deAmount));
        });
		
		return total;
	}
	
	function ajaxCall(url,data){
		$.ajax({
			url:url,
			method:"post",
			dataType:"json",
			async:false,
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
				     rescCallbocak();
					 $.unblockUI();
				},
				error : function(errors) {
					$.unblockUI();   
					window.wxc.error("数据保存出错:"+JSON.stringify(errors));
				}
		});
	}
	
	//风控总监审批公共方法   
    function riskAjaxRequest(SpvApplyApprove,handle,url){
	    var data = {spvCode:$("input[name='toSpv.spvCode']").val(),caseCode:$("#caseCode").val(),taskId:$("#taskId").val(),instCode:$("#instCode").val(),remark:$("#passOrRefuseReason").val(),source:$("#source").val()};
	    if(SpvApplyApprove != null){
	    	data.SpvApplyApprove = SpvApplyApprove;
	    }
	    if(handle == "SpvSign"){
	    	data.spvConCode = $("input[name='toSpv.spvConCode']").val();
	    	data.signTime = $("input[name='toSpv.signTime']").val();
	    }
    	//验证参数是否填写正确
    	saveBtnClick(handle,SpvApplyApprove,'checkForSubmit',url,data);
    }

    
    //风控申请审批时只读表单
    function readOnlyRiskForm(){
    	$("input").prop("readOnly",true);
    	$(":radio").prop("disabled",true);
    	$("input[name='spvCustList[0].idValiDate']").prop("disabled",true);
    	$("input[name='spvCustList[1].idValiDate']").prop("disabled",true);
    	$("select").prop("disabled",true);
    	$("#realName").prop("disabled",true);
    	$("input[id^='picFileupload']").prop("disabled",true);
    	$("button").prop("disabled",true);
    }
    
    function getParentBank(selector,selectorBranch,finOrgCode,bank){
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
		    		if(data != null && data.content){
		    			selector.val(data.content);
		    		}else if(bank){
		    			selector.append("<option value='"+bank+"' selected='selected'>"+bank+"</option>");
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
		    			var flag = false;
		    			for(var i = 0;i<data.length;i++){
							//var coLevelStr='('+data[i].coLevelStr+')';
		    				var coLevelStr='';
							var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
							if(data[i].finOrgCode==finOrgCode){
								flag = true;
								option.attr("selected",true);
							}
							selectorBranch.append(option);
		    			}

		    			if(!flag && finOrgCode){
		    				selectorBranch.append("<option value='"+finOrgCode+"' selected='selected'>"+finOrgCode+"</option>");
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
 
	 //手机号码校验
	 function isMobile(number){
	     reg = /^1[3|4|5|7|8]\d{9}$/;
	     if (!reg.test(number)) {
	         return false; 
	     }
	     return true;
	 }
	 
		
	 //证件编号校验
	function isIdCard(cardId){
		 //1.身份证
		 var reg1 = /^\d{15}$|^\d{17}([0-9]|X|x)$/;
		 //2.军官证
		 //var reg2 = /^\d{15}$/;
		 //3.外国护照
		 //var reg3 = /^\d{9}$|^\d{2}[a-zA-Z]{2}\d{5}$|^[a-zA-Z]{2}\d{6,7}$|^[a-zA-Z]{3}\d{6}$|^[a-zA-Z][a-zA-Z0-9]{6,8}[a-zA-Z]$/;
		 //4.香港身份证
		 //var reg4 = /^[a-zA-Z]{1}\d{6}\(\d{1}\)$/;
		 //5.台胞证
		 //var reg5 = /^\d{8}$/;
		 //6.中国护照
		 //var reg6 = /^1(4|5)\d{7}$|^(G|S|D)\d{8}$|^(S|P)\.\d{7}$/;
		 //7.港澳通行证
		 //var reg7 = /^W\d{8}$/;
		 //8.其他
		 //var reg8 = /^[a-zA-Z0-9]+$/;
		 var testExp = reg1.test(cardId)/* || reg2.test(cardId) || reg3.test(cardId) || reg4.test(cardId) 
		               || reg5.test(cardId) || reg6.test(cardId) || reg7.test(cardId) || reg8.test(cardId)*/;
	     if (testExp) {
	         return true; 
	     }
	     return false;
	}
	
	//姓名验证(汉字和英文大小写)
	function isName(name){
		name = name.replace(/\s/g,"");//去除中间空格
		reg = /((^[\u4E00-\u9FA5]{1,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
		if (!reg.test(name)) {
	         return false; 
	     }
	     return true;
	}
	
	//金额验证(两位小数)
	function isNumber(num){
		if(Number(num) == 0) return false;
		var reg=/^([1-9]{1}\d*|0)(\.\d{1,2})?$/;
		if(!reg.test(num)){
			return false;
		}
		return true;
	}
	
	//号码验证(首位非0纯数字)
	function isNumber2(num){
		var reg=/^[1-9]{1}\d*$/;
		if(!reg.test(num)){
			return false;
		}
		return true;
	}
	
	//号码验证(纯数字)
	function isNumber3(num){
		var reg=/^\d*$/;
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
//Number.prototype.div = function (arg){
//    return accDiv(this, arg);
//};
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
//Number.prototype.mul = function (arg){
//    return accMul(arg, this);
//};
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
//Number.prototype.add = function (arg){
//    return accAdd(arg,this);
//}
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
//Number.prototype.sub = function (arg){
//    return accSub(arg,this);
//}

function rescCallbocak(){
 	   if($("#urlType").val() == 'myTask'){    	 
 		   window.opener.location.reload(); //刷新父窗口
     	   window.close(); //关闭子窗口.
	     }else{
	    	 window.location.href = ctx+"/spv/spvList";
	     }
	}


function updateAccTypeOptions(){
	var accTypeOptions_ = getAccTypeOptions();
	$("select[name^='toSpvDeDetailList'][name$='payeeAccountType']").each(function(i,e){
		var index = $(e).attr("name").replace('toSpvDeDetailList[','').replace('].payeeAccountType','');
		var eVal = $(e).attr("value");
		$(e).empty().html(accTypeOptions_);
		$(e).find("option").each(function(i_,e_){
			if($(e_).attr("value") == eVal){
				$(e_).prop("selected",true);
				return false;
			}
		});
	}).change(function(){
		$(this).attr("value",this.value);
	});
}

function getAccTypeOptions(){
	accTypeOptions = '';
	accTypeOptions += '<option value="">请选择</option>';
	$("*[name^='toSpvAccountList'][name$='name']").each(function(i,e){
		if($(e).attr("name").indexOf("[0]") != -1){
			accTypeOptions += '<option value="BUYER">'+$(e).val()+'(买方账户)</option>';
		}else if($(e).attr("name").indexOf("[1]") != -1){
			accTypeOptions += '<option value="SELLER">'+$(e).val()+'(卖方账户)</option>';
		}else if($(e).attr("name").indexOf("[3]") != -1){
			accTypeOptions += '<option value="FUND">'+$(e).val()+'(资金方账户)</option>';
		}else if($(e).attr("name").indexOf("[2]") != -1){
			//*
		}else{
			var index = $(e).attr("name").replace('toSpvAccountList[','').replace('].name','');
			accTypeOptions += '<option value="CUSTOM_'+index+'">'+$(e).val()+'(新添加)</option>';
		}
	});
    
	return accTypeOptions;
}