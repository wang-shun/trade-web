
$(document).ready(function(){
		//流程开启后只读表单
		if($("#role").val() != ''){
		    readOnlyRiskForm();
		}

       	$('#loading-example-btn').click(function () {
            btn = $(this);
            simpleLoad(btn, true)
            simpleLoad(btn, false)
        });
    });

	$(".buyinfo, .sellinfo, .pledgeinfo").hide();
	
	if($("#BuyRadio1").is(":checked")){
		$(".buyinfo").show();
	}
	if($("#SellRadio1").is(":checked")){
		$(".sellinfo").show();
	}
	if($("#Pledge2").is(":checked")){
		$(".pledgeinfo").show();
	}
	
	$("#BuyRadio1").on("click", function() {
		$(".buyinfo").show();
	});
	$("#BuyRadio2").on("click", function() {
		$(".buyinfo").hide();
	});
	$("#SellRadio1").on("click", function() {
		$(".sellinfo").show();
	});
	$("#SellRadio2").on("click", function() {
		$(".sellinfo").hide();
	});
	$("#Pledge2").on("click", function() {
		$(".pledgeinfo").show();
	});
	$("#Pledge1").on("click", function() {
		$(".pledgeinfo").hide();
	});

	var sum = parseInt($("#toSpvDeDetailListSize").val()); //定义sum为全局变量
		function getAtr(i) {
		$str = '';
		$str += "<tr align='center'>";
		$str += "<td class='text-left'><select class='table-select'><option name='toSpvDeDetailList["+sum+"].deCondCode' value=''>买方贷款审批完成</option></select></td>";
		$str += "<td class='text-left'><select class='table-select'><option name='toSpvDeDetailList["+sum+"].payeeAccountId' value='1'>资金方</option><option name='toSpvDeDetailList["+sum+"].payeeAccountId' value='2'>卖方</option></select></td>";
		
		$str += "<td><input name='toSpvDeDetailList["+sum+"].deAmount' class='table-input-one' type='text' placeholder='请输入金额'>元</td>";
		$str += "<td class='text-left' ><input name='toSpvDeDetailList["+sum+"].deAddition' class='table-input' type='text' placeholder='' /></td>";
		$str += "<td class='btn-height'><a href='javascript:void(0)'  onClick='getAtr(this)'>添加</a><a onClick='getDel(this)' class='grey' href='javascript:void(0)'>删除</a></td>";
		$str += "</tr>";
		$("#addTr").append($str);
		sum++;
		if(sum > 0){
			$('#example').hide();
		}
		$("#sum").html(sum);
		}
		function getDel(k) {
		$(k).parents('tr').remove();
		sum--;
		if(sum == 0){
			$('#example').show();
		}
		$("#sum").html(sum);
	}

    function simpleLoad(btn, state) {
        if (state) {
            btn.children().addClass('fa-spin');
            btn.contents().last().replaceWith(" Loading");
        } else {
            setTimeout(function () {
                btn.children().removeClass('fa-spin');
                btn.contents().last().replaceWith(" Refresh");
            }, 2000);
        }
    }

    jQuery(function($) {
    $(document).ready( function() {
       $('.stickup-nav-bar').stickUp({
        // $('.col-lg-9').stickUp({
                            parts: {
                              0:'base_info',
                              1:'spvone_info',
                              2:'spvtwo_info',
                              3:'spvthree_info',
                              4:'spvfour_info',
                            },
                            itemClass: 'menuItem',
                            itemHover: 'active',
                            marginTop: 'auto'
                          });

       $('#phone').click(function(){
            toastr.info('Are you the 6 fingered man?');

       });
       
       $("#saveBtn").click(function(){
    	  //保存时必须选择关联案件，监管总金额，监管机构
    	  if(!checkFormSave()){
    		  return;
    	  }
     	  var totalArr = [];
     	  $("form").each(function(){
     		 var obj = $(this).serializeArray();
     		for(var i in obj){
     			if(obj[i].name.indexOf('idValiDate') != '-1'){
      				//匹配yyyy-MM-DD格式
     				obj[i].value += '-01';
      				totalArr.push(obj[i]);
      			}else{
          			totalArr.push(obj[i]);
      			}
     		}
     	  }); 

     	  $.ajax({
       		url:ctx+"/spv/saveNewSpv",
       		method:"post",
       		dataType:"json",
       		data:totalArr,	        				        		    
       		success:function(data){
       			alert(data.message);
       		},  	 
    	    error : function(errors) {
				alert("数据保存出错:"+JSON.stringify(errors));
			}
     	 
        });
     });
       
       $("#submitBtn").click(function(){
    	 //保存时必须选择关联案件，监管总金额，监管机构
     	  if(!checkFormSubmit()){
     		  return;
     	  }
      	  var totalArr = [];
      	  $("form").each(function(){
      		 var obj = $(this).serializeArray();
      		for(var i in obj){
      			if(obj[i].name.indexOf('idValiDate') != '-1'){
       				//匹配yyyy-MM-DD格式
      				obj[i].value += '-01';
       				totalArr.push(obj[i]);
       			}else{
           			totalArr.push(obj[i]);
       			}
      		}
      	  });
    	  
    	  $.ajax({
      		url:ctx+"/spv/submitNewSpv",
      		method:"post",
      		dataType:"json",
      		data:totalArr,   		        				        		    
      		success:function(data){
      			alert(data.message);
				 if(window.opener)
			     {
					 window.close();
					 window.opener.callback();
			     } else {
			    	 window.location.href = "spvList";
			     }	 
      		}  	 
       });
     });
       
       
       //风控专员提交申请
       $("#riskOfficerApply").click(function(){
    	   riskAjaxRequest(null,'${ctx}/spv/spvApply/deal');	
       });
       
       //风控总监审批通过
       $("#riskDirectorApproveY").click(function(){
    	   riskAjaxRequest(true,'${ctx}/spv/spvApprove/deal');
       });
       
       //风控总监审批驳回
       $("#riskDirectorApproveY").click(function(){
    	   riskAjaxRequest(false,'${ctx}/spv/spvApprove/deal');
       });

       $('#chat-discussion').hide();

       $('#label7').click(function(){
            // alert(1);
            $('#base_info').hide();
            // alert(2);
            $('#chat-discussion').show();
       });

       $('#label4').click(function(){
            $('#base_info').show();
            $('#chat-discussion').hide();
       });

    }); 
});
    
    //保存必填项
	function checkFormSave(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			alert("请选择关联案件");
			return false;	
		}
        var toSpvAmount = $("#toSpvAmount").val();
        if(toSpvAmount == null || toSpvAmount == ''){
        	alert("请填写监管总金额");
        	return false;
        }
        var toSpvSpvInsti = $("#toSpvSpvInsti").val();
        if(toSpvSpvInsti == null || toSpvSpvInsti == ''){
        	alert("请填写监管机构");
        	return false;
        }
		 return true;
	}
	
    //提交必填项
	function checkFormSubmit(){
		var ds = $('.case_content').css('display');
		if(ds=='none'){
			alert("请选择关联案件！");
			return false;	
		}
		
		var buyerName = $("input[name='spvCustList[0].name']").val();
		var sellerName = $("input[name='spvCustList[1].name']").val();
		if((buyerName == null || buyerName == '')||(sellerName == null || sellerName == '')){
			alert("请填写买/卖方姓名！");
			return false;
		}
		
		var buyerGender = $("input[name='spvCustList[0].gender']").val();
		var sellerGender = $("input[name='spvCustList[1].gender']").val();
		if((buyerGender == null || buyerGender == '')||(sellerGender == null || sellerGender == '')){
			alert("请勾选买/卖方性别！");
			return false;
		}
		
		var buyerPhone = $("input[name='spvCustList[0].phone']").val();
		var sellerPhone = $("input[name='spvCustList[1].phone']").val();
		if((buyerPhone == null || buyerPhone == '')||(sellerPhone == null || sellerPhone == '')){
			alert("请填写买/卖方手机号！");
			return false;
		}
		
		var idValiDate_0 = $("input[name='spvCustList[0].idValiDate']").val();
        var idValiDate_1 = $("input[name='spvCustList[1].idValiDate']").val();
    	if((!new RegExp("^[1-2]\\d{3}-(0?[1-9]||1[0-2])$").test(idValiDate_0)&&(idValiDate_0 != null && idValiDate_0 != '')) || 
        		(!new RegExp("^[1-2]\\d{3}-(0?[1-9]||1[0-2])$").test(idValiDate_1)&&(idValiDate_1 != null && idValiDate_1 != ''))){
        	alert("证件有效期需输入正确的‘yyyy-MM’格式！");
        	return false;
        }  
        	
        var amountMort = $("input[name='toSpv.amountMort']").val();
        var amountMortCom = $("input[name='toSpv.amountMortCom']").val();
        var amountMortPsf = $("input[name='toSpv.amountMortPsf']").val();
        if(amountMort != null && amountMort != ''){
        	amountMort = parseInt(amountMort);
        }else{
        	amountMort = 0;
        }
        if(amountMortCom != null && amountMortCom != ''){
        	amountMortCom = parseInt(amountMortCom);
        }else{
        	amountMortCom = 0;
        }
        if(amountMortPsf != null && amountMortPsf != ''){
        	amountMortPsf = parseInt(amountMortPsf);
        }else{
        	amountMortPsf = 0;
        }
        
        if(amountMort != (amountMortCom+amountMortPsf)){
        	alert("贷款资金需等于商业贷款与公积金贷款之和！");
        	return false;
        }
        
        var toSpvAmount = $("#toSpvAmount").val();
        if(toSpvAmount == null || toSpvAmount == ''){
        	alert("请填写监管总金额！");
        	return false;
        }
        
        var toSpvSpvInsti = $("#toSpvSpvInsti").val();
        if(toSpvSpvInsti == null || toSpvSpvInsti == ''){
        	alert("请填写监管机构！");
        	return false;
        }
        
        
		 return true;
	}
	
	//风控总监审批公共方法   
    function riskAjaxRequest(SpvApplyApprove,url){
    	    var data = {caseCode:$("#caseCode").val(),taskId:$("#taskId").val(),instId:$("#instId").val(),source:$("#source").val()};
    	    if(SpvApplyApprove != null){
    	    	data.SpvApplyApprove = SpvApplyApprove;
    	    }
			$.ajax({
				url:url,
				method:"post",
				dataType:"json",
				data:data,
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
						/*if(data.message){
							alert(data.message);
						}*/
						 if(window.opener)
					     {
							 window.close();
							 window.opener.callback();
					     } else {
					    	 window.location.href = "${ctx }/task/myTaskList";
					     }
						 $.unblockUI();
					},
					
					error : function(errors) {
						$.unblockUI();   
						alert("数据保存出错:"+JSON.stringify(errors));
					}
			});
    }
    
    //风控申请审批时只读表单
    function readOnlyRiskForm(){
    	$("input").attr("readOnly",true);
    	$("input:radio").attr("disabled",true);
    	$("select").attr("disabled",true);
    }
	