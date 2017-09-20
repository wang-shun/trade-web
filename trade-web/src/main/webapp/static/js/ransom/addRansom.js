 jQuery(function($) {
    $(document).ready( function() {

       
       $("#submitBtn").click(function(){
     	  if(!checkFormSubmit()){
     		  return false;
     	  }
     	  if($("#handle").val() == null || $("#handle").val() == ''){
     		 window.wxc.confirm("确定提交并开启流程吗？",{"wxcOk":function(){
     			submitNewRansom();
     		 }}); 	
     	  }else{
     		 window.wxc.confirm("确定提交任务吗？",{"wxcOk":function(){
     			submitNewRansom();
     		 }}); 
     	  }
     });
       function submitNewRansom(){
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
    }); 
});
 
 function checkFormSubmit(){
	 var ds = $('.case_content').css('display');
		if(ds=='none'){
			window.wxc.alert("请选择关联案件！");
			return false;	
		}
	 
	 
	 
 }

//添加机构
var index = 1;
var count = $('#loanLostFinOrgName option:last').index();
function addOrg() {
	var txt='<tr id="trId' + index + '"><td><select name="loanLostFinOrgName"';
	txt +='	id="loanLostFinOrgName" class="teamcode select_control ">';
	txt += $("#loanLostFinOrgName").html()
    txt += '</select></td>';
	//txt +='<td><aist:dict id="mortType" name="mortType" clazz=" select_control yuanwid " display="select" dictType="" defaultvalue="${toMortgage.mortType }" /></td>';
	//txt +='<td><aist:dict id="mortType" name="mortType" clazz=" select_control yuanwid " display="select" dictType="" defaultvalue="${toMortgage.mortType }" /></td>';
	txt +='<td><input type="text" name="" id=""  /></td>';
	txt +='<td><input type="text" name="" id=""  /></td>';
	txt +='<td><input type="text" name="" id=""  /></td>';
	txt +='<td><input type="text" name="" id=""  /></td>';
	txt +='<td><span><a href="javascript:removeTr('+ index + ');"><font>删除</font></a></span></td></tr>';

	
	$("#addInput").before(txt);
	index++;
}

function removeTr(index) {
	$("#trId" + index).remove();
}

