	function getSpvDeCond(){
		$.ajax({
			url:ctx+"/spv/querySpvDeCond",
			method:"post",
			dataType:"json",
			data:{spvCode:$("#spvCode").val()},
			success:function(data){
				if(data != null && data.length > 0){
					var foc = $("#condId");
					foc.html("<option value=''>请选择</option>");
					for(var i=0;i<data.length;i++){
						foc.append("<option value='"+data[i].pkid+"'>"+data[i].deCondition+"</option>");
					}
					$("#condId").val($("#cond").val());
				//	getSpvDeRecInfo();
				}
			}
		});
	}
	function checkform(){
		if($("#condId").val()==null || $("#condId").val() == ""){
			alert("请选择解除条件！");
			return false;
		}else if($("#deAmount").val() == ""){
			alert("解除金额不能为空！");
			$("#deAmount").focus();
			return false;
		}
		return true;
	}
	function getSpvDeRecInfo(){
		$.ajax({
			url:ctx+"/spv/getSpvDeRec",
			method:"post",
			dataType:"json",
			data:{spvCode:$("#spvCode").val(),condId:$("#condId").val()},
			success:function(data){
				if(data != null){
					if(data.content != null){
						$("#deAmount").val(data.content.deAmount);
						$("#remark").val(data.content.remark);
					}else{
						$("#deAmount").val("");
						$("#remark").val("");
					}
				}
			}
		});
	}
	function saveSpvDeRec(f){
		if(!checkform()){
			return;
		}
		
		deleteAndModify();
			
		var url = ctx+"/spv/saveSpvDeRec";
		if(f){
			url = ctx+"/spv/submitSpvDeRec";
		}
		
    	$.ajax({
    		url:url,
    		method:"post",
    		dataType:"json",
    		data:{
    			spvCode:$("#spvCode").val(),
    			deAmount:$("#deAmount").val(),
    			condId:$("#condId").val(),
    			deCond:$("#condId :selected").text(),
    			remark:$("#remark").val(),
    			caseCode:caseCode,
    			partCode:taskitem,
    			processInstanceId:$("#processInstanceId").val(),
    			taskId:$("#taskId").val()
    		},	 
		    beforeSend:function(){  
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
            },
            complete: function() {  
                    $.unblockUI();   
                    if(data.success && f){
                    	  $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
          				$(".blockOverlay").css({'z-index':'1900'});
                    }
                  
  
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
    			
    			if(data.success && f){
    				//window.location.href=ctx+"/task/myTaskList";
    				caseTaskCheck();
    			}else{
    				alert(data.message);
    			}
    		}
    	});
	
	}
	$(document).ready(
		function() {
			getSpvDeCond();
			
//			$("#condId").change(function(){
//				getSpvDeRecInfo();
//			});

			$("#saveBtn").click(function(){
				saveSpvDeRec(false);
			});
			
			$("#subBtn").click(function(){
				if(checkAttachment()) {
					saveSpvDeRec(true);
				}
			});
	});