
$(document).ready(function(){
		//流程开启后只读表单
		if($("#role").val() != null && $("#role").val() != ''){
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
		
		$str += "<td><input name='toSpvDeDetailList["+sum+"].deAmount' class='table-input-one' type='text' placeholder='请输入金额'>万</td>";
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
          		totalArr.push(obj[i]);
     		}
     	  }); 

     	  $.ajax({
       		url:ctx+"/spv/saveNewSpv",
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
           		totalArr.push(obj[i]);
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
       $("#riskDirectorApproveN").click(function(){
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
/*        var toSpvAmount = $("#toSpvAmount").val();
        if(toSpvAmount == null || toSpvAmount == ''){
        	alert("请填写监管总金额");
        	return false;
        }
        var toSpvSpvInsti = $("#toSpvSpvInsti").val();
        if(toSpvSpvInsti == null || toSpvSpvInsti == ''){
        	alert("请填写监管产品");
        	return false;
        }*/
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
		
		var buyerGender = $("input[name='spvCustList[0].gender']:checked").val();
		var sellerGender = $("input[name='spvCustList[1].gender']:checked").val();
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
		
		var buyerIdType = $("select[name='spvCustList[0].idType'] option:selected").val();
		var sellerIdType = $("select[name='spvCustList[1].idType'] option:selected").val();
		if((buyerIdType == null || buyerIdType == '')||(sellerIdType == null || sellerIdType == '')){
			alert("请填写买/卖方证件类型！");
			return false;
		}
		
		var buyerIdValiDate = $("input[name='spvCustList[0].idValiDate']").val();
		var sellerIdValiDate = $("input[name='spvCustList[0].idValiDate']").val();
		if((buyerIdValiDate == null || buyerIdValiDate == '')||(sellerIdValiDate == null || sellerIdValiDate == '')){
			alert("请选择买/卖方证件有效期！");
			return false;
		}
		
		var buyerIdCode = $("input[name='spvCustList[0].idCode']").val();
		var sellerIdCode = $("input[name='spvCustList[1].idCode']").val();
		if((buyerIdCode == null || buyerIdCode == '')||(sellerIdCode == null || sellerIdCode == '')){
			alert("请填写买/卖方证件编号！");
			return false;
		}
		
		var buyerHomeAddr = $("input[name='spvCustList[0].homeAddr']").val();
		var sellerHomeAddr = $("input[name='spvCustList[1].homeAddr']").val();
		if((buyerHomeAddr == null || buyerHomeAddr == '')||(sellerHomeAddr == null || sellerHomeAddr == '')){
			alert("请填写买/卖方家庭住址！");
			return false;
		}
		
		var prOwnerName = $("input[name='toSpvProperty.prOwnerName']").val();
		if(prOwnerName == null || prOwnerName == ''){
			alert("请填写房产权利人！");
			return false;
		}
		
		var prSize = $("input[name='toSpvProperty.prSize']").val();
		if(prSize == null || prSize == ''){
			alert("请填写房屋面积！");
			return false;
		}
		
		var prAddr = $("input[name='toSpvProperty.prAddr']").val();
		if(prAddr == null || prAddr == ''){
			alert("请填写房屋地址！");
			return false;
		}
		
		var mortgageeName = $("input[name='toSpvProperty.mortgageeName']").val();
		if(mortgageeName == null || mortgageeName == ''){
			alert("请填写抵押方！");
			return false;
		}
		
		var mortgageeBank = $("input[name='toSpvProperty.mortgageeBank']").val();
		if(mortgageeBank == null || mortgageeBank == ''){
			alert("请填写开户行！");
			return false;
		}
		
		var signNo = $("input[name='toSpvProperty.signNo']").val();
		if(signNo == null || signNo == ''){
			alert("请填写网签合同号！");
			return false;
		}
		
		var signAmount = $("input[name='toSpvProperty.signAmount']").val();
		if(signAmount == null || signAmount == ''){
			alert("请填写网签金额！");
			return false;
		}
		
		var buyerAccountName = $("input[name='toSpvAccountList[0].name']").val();
		var sellerAccountName = $("input[name='toSpvAccountList[1].name']").val();
		if((buyerAccountName == null || buyerAccountName == '') || (sellerAccountName == null || sellerAccountName == '')){
			alert("请填写买/卖方账户名称！");
			return false;
		}
		
		var buyerAccountTelephone = $("input[name='toSpvAccountList[0].telephone']").val();
		var sellerAccountTelephone = $("input[name='toSpvAccountList[1].telephone']").val();
		if((buyerAccountTelephone == null || buyerAccountTelephone == '') || (sellerAccountTelephone == null || sellerAccountTelephone == '')){
			alert("请填写买/卖方电话！");
			return false;
		}
		
/*		var buyerBank = $("input[name='toSpvAccountList[0].bank']:checked").val();
		if(buyerBank == null || buyerBank == ''){
			alert("请勾选买方开户行！");
			return false;
		}*/
        	
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
        
/*        var toSpvAmount = $("#toSpvAmount").val();
        if(toSpvAmount == null || toSpvAmount == ''){
        	alert("请填写监管总金额！");
        	return false;
        }
        
        var toSpvSpvInsti = $("#toSpvSpvInsti").val();
        if(toSpvSpvInsti == null || toSpvSpvInsti == ''){
        	alert("请填写监管产品！");
        	return false;
        }*/
        
        
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
	