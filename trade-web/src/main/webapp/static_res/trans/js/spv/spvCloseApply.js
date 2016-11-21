$(document).ready(function(){
	readOnlyForm();
})

/**只读表单*/
function readOnlyForm(){
	$("input").prop("readOnly",true);
	$(":radio").prop("disabled",true);
	$("select").addClass("selectro").find("option").addClass("optionro");
	$("#realName").prop("disabled",true);
	$("input[id^='picFileupload']").prop("disabled",true);
	$("button").prop("disabled",true);
}

function rescCallbocak(){
	   if($("#urlType").val() == 'myTask'){    	 
		   window.opener.location.reload(); //刷新父窗口
  	       window.close(); //关闭子窗口.
	     }else{
	    	 window.location.href = ctx+"/spv/spvList";
	     }
	}