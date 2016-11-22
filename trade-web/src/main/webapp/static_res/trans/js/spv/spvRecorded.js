$(document).ready(function(){
	var handle = $("#handle").val();
	
	$("#cashFlowRecord").find("select").prop("disabled",true);
	
	//流程开启后只读表单
	if(handle == 'directorAudit' || handle == 'financeAudit' 
		|| handle == 'financeSecondAudit'){
	    readOnlyRiskForm();
	}
	
	$("#none_save_btn").click(function(){saveBtnClick()});
	$("#none_submit_btn").click(function(){submitBtnClick(handle,null)});
	$("#apply_save_btn").click(function(){saveBtnClick()});
	$("#apply_submit_btn").click(function(){submitBtnClick(handle,null)});
	$("#directorAudit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#directorAudit_reject_btn").click(function(){submitBtnClick(handle,false)});
	$("#financeAudit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#financeAudit_reject_btn").click(function(){submitBtnClick(handle,false)});
	$("#financeSecondAudit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#financeSecondAudit_reject_btn").click(function(){submitBtnClick(handle,false)});
});

function checkFormSubmit(){
	
    if($("#toSpvCashFlowApplyAttachType").val() == null || $("#toSpvCashFlowApplyAttachType").val() == ''){
    	alert("请选择出账条件！");
    	return false;
    }
    
    var receiverFlag = true;
    var receiverEle;
    $("input[name$='toSpvCashFlow.receiver']").each(function(i,e){
    	if($(e).val() == null || $(e).val() == ''){
    		receiverFlag = false;
    		receiverEle = $(e);
    		return false;
    	}
    });
    
    if(!receiverFlag){
    	alert("请填写收款人姓名！");
    	changeClass(receiverEle);
    	return false;
    }
    
	var receiverAccFlag = true;	
	var receiverAccEle;
	$("input[name$='toSpvCashFlow.receiverAcc']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber2($(e).val()))){
			 receiverAccFlag = false;
			 receiverAccEle = $(e);
			 return false;
			 }
		});
	
    if(!receiverAccFlag){
    	alert("请填写有效的银行卡号！");
	    changeClass(receiverAccEle);
		return false;
    }
    
	var receiverBankFlag = true;
	var receiverBankEle;
	$("input[name$='toSpvCashFlow.receiverBank']").each(function(i,e){
		if($(e).val() == null || $(e).val() == ''){
			 receiverBankFlag = false;
			 receiverBankEle = $(e);
			 return false;
			 }
		});
	
    if(!receiverBankFlag){
    	alert("请填写银行名称！");
	    changeClass(receiverBankEle);
		return false;
    }

	var amountFlag = true;	
	var amountEle;
	var sumAmount = 0;
	$("input[name$='toSpvCashFlow.amount']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '') && !isNumber($(e).val())){
    		 amountFlag = false;
    		 amountEle = $(e);
			 return false;
    	} 
	});
	
    if(!amountFlag){
    	alert("请填写有效的出账金额！");
	    changeClass(amountEle);
		return false;
    }  
    
	$("input[name$='toSpvCashFlow.amount']").each(function(i,e){
		if(($(e).val() != null && $(e).val() != '' && isNumber($(e).val()))){
			sumAmount = accAdd(sumAmount,Number($(e).val()));
		}
	});
    
    var totalCashFlowInAmount = Number($("#totalCashFlowInAmount").val());
    var totalCashFlowOutAmount = Number($("#totalCashFlowOutAmount").val());
    var totalProcessCashFlowOutAmout = Number($("#totalProcessCashFlowOutAmout").val());

    if(!handle || handle == 'apply'){
    	if(accAdd(Number(sumAmount),accAdd(totalCashFlowOutAmount,totalProcessCashFlowOutAmout)) > totalCashFlowInAmount){
        	alert("已经超过可出账的金额！");
        	return false;
        }
    }
    
	var voucherNoFlag = true;	
	var voucherNoEle;
	$("input[name$='toSpvCashFlow.voucherNo']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber2($(e).val()))){
			 voucherNoFlag = false;
			 voucherNoEle = $(e);
			 return false;
			 }
		});
	
    if(!voucherNoFlag){
    	alert("请填写有效的贷记凭证编号！");
	    changeClass(voucherNoEle);
		return false;
    }
    
	var vouNoRepeatFlag = false;
	$("input[name$='toSpvCashFlow.voucherNo']").each(function(i,e){
		var voucherNo = $(e).val();
		$("input[name$='toSpvCashFlow.voucherNo']").each(function(i_,e_){
			var voucherNo_ = $(e_).val();
			if(i != i_ && voucherNo == voucherNo_){
				vouNoRepeatFlag = true;
				return false;
			}
		});
		});
	
	if(vouNoRepeatFlag){
		alert("贷记凭证编号不能相同！");
		return false;
	}
    
	var directionFlag = true;	
	var directionEle;
	$("select[name$='toSpvCashFlow.direction']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '')){
			 directionFlag = false;
			 directionEle = $(e);
			 return false;
			 }
		});
	
    if(!directionFlag){
    	alert("请选择付款方式！");
	    changeClass(directionEle);
		return false;
    }
    
    var imgFlag = true;
    $("td[id^='td_file']").each(function(i,e){
    	var length = $(e).find("img").length;
    	if(length == 0){
    		imgFlag = false;
    		return false;
    	}
    });
    
    if(!imgFlag){
    	alert("需要上传至少一张附件！");
    	return false;
    }
    
    return true;
}

function changeClass(object){
	$(object).focus();
	$(object).addClass("borderClass").blur(function(){
		$(this).removeClass("borderClass");
	});	;
}

//保存按钮方法
function saveBtnClick(){
	if($("#addSum").val() != $("#doneSum").val()){
		alert("请先上传图片成功后再提交");
		return false;
	}

	  if(!checkFormSave()){
  		  return false;
  	  }

	  	var totalArr = [];
      	  $("form").each(function(){
      		 var obj = $(this).serializeArray();
      		for(var i in obj){
           		totalArr.push(obj[i]);
      		}
      	  }); 

      	  $.ajax({
        		url:ctx+"/spv/cashFlowOutAppr/save",
        		method:"post",
        		dataType:"json",
        		data:totalArr,
        		async:false,
        		beforeSend:function(){  
 				//$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
 				$(".blockOverlay").css({'z-index':'9998'});
             },
 	        complete: function() {
 	                // $.unblockUI(); 
 	                 if(status=='timeout'){ //超时,status还有success,error等值的情况
 		          	  Modal.alert(
 					  {
 					    msg:"抱歉，系统处理超时。"
 					  }); 
 			                } 
 			            } ,   
 			success : function(data) {   
	 				if(data.ajaxResponse.success){
	 						alert("数据保存成功！");
	 				}else{
	 					    alert("数据保存出错:"+data.ajaxResponse.message);
	 				}
			    	 if($("#urlType").val() == 'myTask'){    	 
 				    	 window.location.reload(); //刷新父窗口
 				     }else{
			    	     window.location.href=ctx+"/spv/task/cashFlowOutAppr/process?businessKey="+data.ajaxResponse.code;
			     }
 					// $.unblockUI();
 				}	 
      	  });
}

//提交、同意、驳回按钮方法
function submitBtnClick(handle,chargeOutAppr){
	  if(!checkFormSubmit()){
		  return false;
	  }
	  if(!handle){
		  if(!confirm("确定提交并开启流程吗！")){
			  return false;
		  } 
	  }else if(handle == 'apply'){
	  		if(!confirm("是否确定提交申请！")){
	  		  return false;
	  	    }
	  }else if(handle == 'directorAudit'){
		  var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
	   	   if(refuseReason=='' || refuseReason==null){
	   		   alert("请在备注栏填写驳回原因！");
	   		   return false;
	   	   }
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
		  	   if(!confirm("是否确定驳回！")){
		     		  return false;
		     	  } 
		  }
	  }else if(handle == 'financeAudit'){
		   var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
	   	   if(refuseReason=='' || refuseReason==null){
	   		   alert("请填写审批意见！");
	   		   return false;
	   	   }
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
		  		if(!confirm("是否确定驳回！")){
		     		  return false;
		     	  } 
		  }
	  }else if(handle == 'financeSecondAudit'){
		   var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
	   	   if(refuseReason=='' || refuseReason==null){
	   		   alert("请填写审批意见！");
	   		   return false;
	   	   }
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
		  	   if(!confirm("是否确定驳回！")){
		     		  return false;
		     	  } 
		  }
	  }else if(handle == 'cashFlowOut'){
		  if(!confirm("是否确定提交申请！")){
	  		  return false;
	  	  }
	  }
	  
	  var totalArr = [];
	  totalArr.push({"name":"chargeOutAppr","value":chargeOutAppr});
	  $("form").each(function(){
		 var obj = $(this).serializeArray();
		for(var i in obj){
     		totalArr.push(obj[i]);
		}
	  });

	  $.ajax({
		url:ctx+"/spv/cashFlowOutAppr/deal",
		method:"post",
		dataType:"json",
		data:totalArr,   		        				        		    
 		beforeSend:function(){  
			//$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
      },
      complete: function() {
               //$.unblockUI(); 
               if(status=='timeout'){ //超时,status还有success,error等值的情况
	          	  Modal.alert(
				  {
				    msg:"抱歉，系统处理超时。"
				  }); 
		                } 
		            } ,   
		success : function(data) {   
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
					window.location.href = ctx+"/spv/spvList";
				}else{
					alert("任务提交成功！");
					window.opener.location.reload(); //刷新父窗口
		        	window.close(); //关闭子窗口.
				}
			}else{
				alert("数据保存出错:"+data.ajaxResponse.message);
			}
			    // if($("#urlType").val() == 'myTask'){    	 
			    	// window.opener.location.reload(); //刷新父窗口
		        	// window.close(); //关闭子窗口.
			    // }else{
			    //      window.location.href=ctx+"/spv/spvFlowApplyList";
			    // }
				 //$.unblockUI();
			}
 });
};

function readOnlyRiskForm(){
	$("input").prop("readOnly",true);
	$(":radio").prop("disabled",true);
	$("select").prop("disabled",true);
	$("a").prop("disabled",true);
}

/**************************************验证************************************************/
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
//金额验证(整数)
function isNumber2(num){
	var reg=/^\d*$/;
	if(!reg.test(num)){
		return false;
	}
	return true;
}
/*****************************************************************************************/

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
//  return accAdd(arg,this);
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