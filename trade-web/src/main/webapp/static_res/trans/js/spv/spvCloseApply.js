$(document).ready(function(){
	readOnlyForm();

	$("#page_submit_btn").click(function(){submitBtnClick(handle)});
	$("#apply_submit_btn").click(function(){submitBtnClick(handle,true)});
	$("#apply_cancel_btn").click(function(){submitBtnClick(handle,false)});
	$("#managerAudit_pass_btn").click(function(){submitBtnClick(handle,null,true)});
	$("#managerAudit_reject_btn").click(function(){submitBtnClick(handle,null,false)});
	$("#directorAudit_pass_btn").click(function(){submitBtnClick(handle,null,true)});
	$("#directorAudit_reject_btn").click(function(){submitBtnClick(handle,null,false)});
})

function submitBtnClick(handle,continueApply,result){
	  if(!handle){
		  if(!validateForm()){
			  return false;
		  }
		  if(!confirm("确定提交并开启流程吗！")){
			  return false;
		  } 
	  }else if(handle == 'apply'){
			if(!validateForm()){
			  return false;
			}
	  		if(!confirm("是否确定提交申请！")){
	  		  return false;
	  	    }
	  }else if(handle == 'managerAudit'){
		  var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
	   	   if(refuseReason=='' || refuseReason==null){
	   		   alert("请在备注栏填写驳回原因！");
	   		   return false;
	   	   }
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
		   var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
	   	   if(refuseReason=='' || refuseReason==null){
	   		   alert("请填写审批意见！");
	   		   return false;
	   	   }
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
	  totalArr.push({"name":"continueApply","value":continueApply});
	  totalArr.push({"name":"result","value":result});
	  $("#spvfive_info,#auditContent").each(function(){
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
			}
});
};

//
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
	
	var $content = $("textarea[name='toSpvCloseApplyAuditList[0].content']");
	if($.trim($content.val()) == ''){
		alert("请填写审核意见！");
		changeClass($cotent);
		return false;
	}
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
	$("#realName").prop("disabled",true);
	$("input[id^='picFileupload']").prop("disabled",true);
	$("button").prop("disabled",true);
}
