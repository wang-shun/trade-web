function checknum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function checkInt(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");  
}	

function checkAssess(){
	if($("#residence_name").val() == ""){
		$("#residence_name").css("border-color","red");
		return false;
	}else if($("#building_no").val() == ""){
		$("#building_no").css("border-color","red");
		return false;
	}else if($("#bank_branch_id").val() == ""){
		$("#bank_branch_id").css("border-color","red");
		return false;
	}else if($("#room_no").val() == ""){
		$("#room_no").css("border-color","red");
		return false;
	}else if($("#area").val() == ""){
		$("#area").css("border-color","red");
		return false;
	}else if($("#deal_price").val() == ""){
		$("#deal_price").css("border-color","red");
		return false;
	}else if($("#floor").val() == ""){
		$("#floor").css("border-color","red");
		return false;
	}else if($("#total_floor").val() == ""){
		$("#total_floor").css("border-color","red");
		return false;
	}else if($("#prop_type").val() == ""){
		$("#prop_type").css("border-color","red");
		return false;
	}else if(($("#prop_type").val()=="5" || $("#prop_type").val()=="6" || $("#prop_type").val()=="7")&&$("#prop_type").val("area_ground")){
		alert("别墅地上建筑面积不能为空！")
		return false;
	}

	return true;
}

function assess(){
	if(checkAssess()){
		$("#subApplyBtn").attr("disabled",true);
	 	$.ajax({
		    url:ctx+"/remote/egu/assess",
	    	method:"post",
	    	dataType:"json",
	    	data:$("#addToEguPricingForm").serialize(),
	    	success:function(data){
	    		$("#subApplyBtn").attr("disabled",false);
	    		if(data.message !=null){
	        		//匹配多个小区
	        		if(data.code == "30009"){
		        		var msg = $.parseJSON(data.message);
		        		$("#modal-select").modal('show');
		        		var str = "";
		        		if(msg.length > 0){
			        		str +="<div style='height:30px'><div class='col-md-3'>小区编号</div><div class='col-md-7'>小区地址</div></div>";
		        			for(var i=0;i<msg.length;i++){
				        		str +="<div class='div' style='height:30px'><div class='col-md-3'>"+msg[i].id+"</div><div class='col-md-7'>"+msg[i].name+"</div></div>";
		        			}
		        		}
		        		$("#residenceList").html(str);
		        		$(".div").each(function(){
		        			$(this).mouseover(function(){
			        			$(this).addClass("mouseover-color");
			        		});
		        			$(this).mouseout(function(){
			        			$(this).removeClass("mouseover-color");
			        		});
		        			$(this).click(function(){
		        				$("#residence_id").val($.trim($(this).find("div:first").html()));
				        		$("#modal-select").modal('hide');
		        			});
		        		});
	        		}else{
		        		alert(data.message);
	        		}
	    		}	    		
	    	}
	    });
	}
}
//查询分行信息
function getParentBank(selector,selectorBranch,finOrgCode){
	var bankHtml = "<option value=''>请选择</option>";
    $.ajax({
    	url:ctx+"/manage/queryParentBankList",
		method:"post",
		dataType:"json",
		async:false,
		data:{},
		success:function(data){
			if(data != null){
				for(var i = 0;i<data.length;i++){
					bankHtml+="<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</option>";
				}
			}
		}
     });
	 selector.html(bankHtml);
	 $.ajax({
		    url:ctx+"/manage/queryParentBankInfo",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{finOrgCode:finOrgCode},
		    success:function(data){
	    		if(data != null){
	    			selector.val(data.content);
	    		}
	    	}
		});
	 selector.chosen({no_results_text:"未找到该选项",width:"100%",search_contains:true,disable_search_threshold:10});

	 getBranchBankList(selectorBranch,selector.val(),"");

	 return bankHtml;
}
//查询支行信息
function getBranchBankList(selector,pcode,finOrgCode){

	var html = "<option value=''>请选择</option>";
	$.ajax({
	    url:ctx+"/manage/queryBankListByParentCode",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:{faFinOrgCode:pcode},
	    	success:function(data){
	    		if(data != null){
	    			for(var i = 0;i<data.length;i++){
	    				html +="<option value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</option>";
	    			}
	    		}
	    	}
	 });
	 selector.html(html);
	 selector.val(finOrgCode);

	 selector.chosen({no_results_text:"未找到该选项",width:"100%",search_contains:true,disable_search_threshold:10});

	return html;
}

function isShowAreaGround(obj) {
	var value = $(obj).val();	
	if(value == 5 || value == 6 || value == 7) {
		$("#areaGround").show();
	} else {
		$("#areaGround").hide();
	}
}


$(document).ready(function () {
	
	getParentBank($("#bank_type"),$("#bank_branch_id"),"");
	$("#bank_type").change(function(){
		$("#bank_branch_id").chosen("destroy");
    	getBranchBankList($("#bank_branch_id"),$("#bank_type").val(),"");
	});
			
	$("input[type='text']").each(function(){
		$(this).blur(function(){
			if($(this).val()!=""){
				$(this).css("border-color","#e5e6e7");
			}
		});
	});
	
	$("select").each(function(){
		$(this).blur(function(){
			if($(this).val() != ""){
				$(this).css("border-color","#e5e6e7");
			}
		});
	});
	
	$("#subApplyBtn").click(function(){
		assess();
	});
     
 });