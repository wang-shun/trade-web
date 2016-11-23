$(document).ready(function(){
	readOnlyForm();
	
	if(!handle || handle == 'apply'){
		$("input[name='toSpvCloseApply.closeType']").prop("disabled",false);
		$("input[name='toSpvCloseApply.comment']").prop("readonly",false);
	}

	$("#page_submit_btn").click(function(){submitBtnClick(handle)});
	$("#apply_submit_btn").click(function(){submitBtnClick(handle,true)});
	$("#apply_cancel_btn").click(function(){submitBtnClick(handle,false)});
	$("#managerAudit_pass_btn").click(function(){submitBtnClick(handle,null,true)});
	$("#managerAudit_reject_btn").click(function(){submitBtnClick(handle,null,false)});
	$("#directorAudit_pass_btn").click(function(){submitBtnClick(handle,null,true)});
	$("#directorAudit_reject_btn").click(function(){submitBtnClick(handle,null,false)});
})

function submitBtnClick(handle,continueApply,result){
	  if(!validateForm()){
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
	  }else if(handle == 'managerAudit'){
		  if(result){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
		  	   if(!confirm("是否确定驳回！")){
		     		  return false;
		     	  } 
		  }
	  }else if(handle == 'directorAudit'){
		  if(result){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
		  		if(!confirm("是否确定驳回！")){
		     		  return false;
		     	  } 
		  }
	  }
	  
	  var totalArr = [];
	  if(continueApply != null){
		  totalArr.push({"name":"continueApply","value":continueApply}); 
	  }
	  if(result != null){
		  totalArr.push({"name":"result","value":result}); 
	  }
	  console.log(result);
	  console.log(JSON.stringify(totalArr));
	  $("#spvfive,#auditContent,#instForm").each(function(){
		var obj = $(this).serializeArray();
		for(var i in obj){
   		totalArr.push(obj[i]);
		}
	  });

	  $.ajax({
		url:ctx+"/spv/spvCloseApply/deal",
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
				if(!handle){
					alert("流程开启成功！");
					window.location.href = ctx+"/spv/spvList";
				}else{
					alert("任务提交成功！");
					window.opener.location.reload(); //刷新父窗口
		        	window.close(); //关闭子窗口.
				}
			}else{
				alert("数据保存出错:\n"+data.message);
				rescCallback();
			}
			}
});
};

//验证
function validateForm(){
	var $closeType = $("input[name='toSpvCloseApply.closeType']:checked");
	if($.trim($closeType.val()) == ''){
		alert("请选择申请状态！");
		return false;
	}
	
	var $comment = $("input[name='toSpvCloseApply.comment']");
	if($.trim($comment.val()) == ''){
		alert("请填写原因！");
		changeClass($comment);
		return false;
	}
	
	if(handle == 'managerAudit' || handle == 'directorAudit'){
		var $content = $("textarea[name='toSpvCloseApplyAuditList[0].content']");
		if($.trim($content.val()) == ''){
			alert("请填写审核意见！");
			changeClass($cotent);
			return false;
		}
	}
	
	return true;
}

function changeClass(object){
	$(object).focus();
	$(object).addClass("borderClass").blur(function(){
		$(this).removeClass("borderClass");
	});	;
}

/**只读表单*/
function readOnlyForm(){
	$("input").prop("readOnly",true);
	$(":radio").prop("disabled",true);
	$("select").prop("disabled",true);
	$("input[id^='picFileupload']").prop("disabled",true);
	$("button").prop("disabled",true);
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
	})
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
