$(document).ready(function(){
	var handle = $("#handle").val();
	
	$("#cashFlowRecord").find("select").prop("disabled",true);
	
	//流程开启后只读表单
	if(handle == 'directorAduit' || handle == 'financeAduit' 
		&& handle == 'financeSecondAduit' || handle == 'cashFlowOut'){
	    readOnlyRiskForm();
	}
	
	$("#none_save_btn").click(function(){saveBtnClick()});
	$("#none_submit_btn").click(function(){submitBtnClick(handle,null)});
	$("#apply_save_btn").click(function(){saveBtnClick()});
	$("#apply_submit_btn").click(function(){submitBtnClick(handle,null)});
	$("#directorAduit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#directorAduit_reject_btn").click(function(){submitBtnClick(handle,false)});
	$("#financeAduit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#financeAduit_reject_btn").click(function(){submitBtnClick(handle,false)});
	$("#financeSecondAduit_pass_btn").click(function(){submitBtnClick(handle,true)});
	$("#financeSecondAduit_reject_btn").click(function(){submitBtnClick(handle,false)});
	$("#cashFlowOut_submit_btn").click(function(){submitBtnClick(handle)});
});

//保存按钮方法
function saveBtnClick(){
/*	  if(!deleteAndModify()){
		  return false;
	  }
	  if(!checkFormSave()){
  		  return false;
  	  }*/

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
	 				if(data.ajaxResponse.success){
	 						alert("数据保存成功！");
	 				}else{
	 					    alert("数据保存出错:"+data.ajaxResponse.message);
	 				}
			    	 if($("#urlType").val() == 'myTask'){    	 
 				    	 window.opener.location.reload(); //刷新父窗口
 			        	 window.close(); //关闭子窗口.
 				     }else{
 				    	 window.location.reload();
			    	     //window.location.href="${ctx}/spv/task/cashFlowOutAppr/process?businessKey="+data.ajaxResponse.code;
			     }
 					 $.unblockUI();
 				}	 
      	  });
}

//提交、同意、驳回按钮方法
function submitBtnClick(handle,chargeOutAppr){
	  var data = {};
/*	  if(!deleteAndModify()){
		  return false;
	  }
	  if(!checkFormSubmit()){
		  return false;
	  }*/
	  if(!handle){
		  if(!confirm("确定提交并开启流程吗！")){
			  return false;
		  } 
	  }else{
		  if(!confirm("确定提交吗！")){
			  return false;
		  } 
	  }
	  
	  if(handle == 'apply'){
	  		if(!confirm("是否确定提交申请！")){
	  		  return false;
	  	    }
	  }else if(handle == 'directorAduit'){
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
			  var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
		   	   if(refuseReason=='' || refuseReason==null){
		   		   alert("请在备注栏填写驳回原因！");
		   		   return false;
		   	   }
			  		if(!confirm("是否确定驳回！")){
			     		  return false;
			     	  } 
		  }
	  }else if(handle == 'financeAduit'){
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
			  var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
		   	   if(refuseReason=='' || refuseReason==null){
		   		   alert("请在备注栏填写驳回原因！");
		   		   return false;
		   	   }
			  		if(!confirm("是否确定驳回！")){
			     		  return false;
			     	  } 
		  }
	  }else if(handle == 'financeSecondAduit'){
		  if(chargeOutAppr){
		   	   if(!confirm("是否确定通过！")){
			 		  return false;
			 	  } 
		  }else{
			  var refuseReason = $("textarea[name='toSpvAduitList[0].content']").val();
		   	   if(refuseReason=='' || refuseReason==null){
		   		   alert("请在备注栏填写驳回原因！");
		   		   return false;
		   	   }
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
console.log(JSON.stringify(totalArr));
	  $.ajax({
		url:ctx+"/spv/cashFlowOutAppr/deal",
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
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错:"+data.ajaxResponse.message);
			}
			     if($("#urlType").val() == 'myTask'){    	 
			    	 window.opener.location.reload(); //刷新父窗口
		        	 window.close(); //关闭子窗口.
			     }else{
			          window.location.reload();
			     }
				 $.unblockUI();
			}
 });
};

function readOnlyRiskForm(){
	$("input").prop("readOnly",true);
	$(":radio").prop("disabled",true);
	$("select").prop("disabled",true);
}