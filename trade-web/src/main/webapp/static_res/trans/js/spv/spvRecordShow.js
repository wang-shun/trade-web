var handle = $("#handle").val();
var trindex = 0;

//审批通过
function approvalby(){
	
	if(null ==$.trim($("#turndownContent_").val()) || "" == $.trim($("#turndownContent_").val())){
		window.wxc.alert("请填写审批意见！");
		 return false;
	}
	
	window.wxc.confirm("是否审批通过？",{"wxcOk":function(){
		//设置流程判断参数
		$('#chargeInAppr').val(true);
		$('#turndownContent').val($('#turndownContent_').val());//审批意见
		var data = $("#teacForm").serialize();
		var url = ctx+"/spv/task/cashflowIntApply/deal";
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			url : url,
			dataType : "json",
			data :  data,
			success : function(data) {
				window.wxc.success("审批通过！",{"wxcOk":function(){
					rescCallbocak();
				}});
			},
			error : function(errors) {
				window.wxc.error("审批失败！");
			}
			
		});
	}});
}
//得到页面数据 
function getFormData(){
	var data = $("#teacForm").serialize();
}

//审批驳回
function turndown(){
	
	if(null ==$.trim($("#turndownContent_").val()) || "" == $.trim($("#turndownContent_").val())){
		window.wxc.alert("请填写审批意见！");
		 return false;
	}
	window.wxc.confirm("是否审批驳回？",{"wxcOk":function(){
		$('#chargeInAppr').val(false);
		$('#turndownContent').val($('#turndownContent_').val());
		var data = $("#teacForm").serialize();
		var url = ctx+"/spv/task/cashflowIntApply/deal";
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			url : url,
			dataType : "json",
			data :  data,
			success : function(data) {
				window.wxc.success("驳回成功！",{"wxcOk":function(){
					rescCallbocak();
				}});
			},
			error : function(errors) {
				window.wxc.error("驳回失败！");
			}
			
		});
	}});
}
function rescCallbocak(){
	   window.opener.location.reload(); //刷新父窗口
	   window.close(); //关闭子窗口.
	}




