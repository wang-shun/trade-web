var handle = $("#handle").val();
var trindex = 0;

//审批通过
function approvalby(){
	
	if(!confirm("是否审批通过！")){
		  return false;
	    }
	if(null ==$.trim($("#turndownContent_").val()) || "" == $.trim($("#turndownContent_").val())){
		alert("请填写审批意见！");
		 return false;
	}
	//设置流程判断参数
	$('#chargeInAppr').val(true);
	$('#turndownContent').val($('#turndownContent_').val());//审批意见
	
	//提交页面的参数
	var data = $("#teacForm").serialize();
	var url = ctx+"/spv/task/cashflowIntApply/deal";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data :  data,
		beforeSend:function(){  
         },
		success : function(data) {
			window.location.href = ctx+"/task/myTaskList";
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}
//得到页面数据 
function getFormData(){
	var data = $("#teacForm").serialize();
}

//审批驳回
function turndown(){
	if(!confirm("是否审批驳回！")){
		  return false;
	    }
	if(null ==$.trim($("#turndownContent_").val()) || "" == $.trim($("#turndownContent_").val())){
		alert("请填写审批意见！");
		 return false;
	}
	$('#chargeInAppr').val(false);
	$('#turndownContent').val($('#turndownContent_').val());
	//提交页面的参数
	var data = $("#teacForm").serialize();
	var url = ctx+"/spv/task/cashflowIntApply/deal";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data :  data,
		beforeSend:function(){  
         },
		success : function(data) {
			window.location.href = ctx+"/task/myTaskList";
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}
function rescCallbocak(){
	   window.opener.location.reload(); //刷新父窗口
	   window.close(); //关闭子窗口.
	}




