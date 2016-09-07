
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
		if(buyerName != null && buyerName != ''){
			if(!isName(buyerName)){
				alert("请填写合法的买方姓名！");
				return false;
			}
		}
		if(sellerName != null && sellerName != ''){
			if(!isName(sellerName)){
				alert("请填写合法的卖方姓名！");
				return false;
			}
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
		if(buyerPhone != null && buyerPhone != ''){
			if(!isMobile(buyerPhone)){
				alert("请填写合法的买方手机号");
				return false;
			}
		}
		if(sellerPhone != null && sellerPhone != ''){
			if(!isMobile(sellerPhone)){
				alert("请填写合法的卖方手机号");
				return false;
			}
		}
		
		var buyerIdType = $("select[name='spvCustList[0].idType'] option:selected").val();
		var sellerIdType = $("select[name='spvCustList[1].idType'] option:selected").val();
		if((buyerIdType == null || buyerIdType == '')||(sellerIdType == null || sellerIdType == '')){
			alert("请选择买/卖方证件类型！");
			return false;
		}
		
		var buyerIdValiDate = $("input[name='spvCustList[0].idValiDate']").val();
		var sellerIdValiDate = $("input[name='spvCustList[1].idValiDate']").val();
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
		if(buyerIdCode != null && buyerIdCode != ''){
			if(!isIdCardSimple(buyerIdCode) && !new RegExp("/^\d{15}$/").test(buyerIdCode)){
				alert("请填写合法的买方证件编号！");
				return false;
			}
		}	
		if(sellerIdCode != null && sellerIdCode != ''){
			if(!isIdCardSimple(sellerIdCode)  && !new RegExp("/^\d{15}$/").test(sellerIdCode)){
				alert("请填写合法的卖方证件编号！");
				return false;
			}
		}
		
		var buyerHomeAddr = $("input[name='spvCustList[0].homeAddr']").val();
		var sellerHomeAddr = $("input[name='spvCustList[1].homeAddr']").val();
		if((buyerHomeAddr == null || buyerHomeAddr == '')||(sellerHomeAddr == null || sellerHomeAddr == '')){
			alert("请填写买/卖方家庭住址！");
			return false;
		}
		
		var buyerHasDele = $("input[name='spvCustList[0].hasDele'][value='1']").is(":checked");
		var sellerHasDele = $("input[name='spvCustList[1].hasDele'][value='1']").is(":checked");
		var buyerAgentName = $("input[name='spvCustList[0].agentName']").val();
		var sellerAgentName = $("input[name='spvCustList[1].agentName']").val();
		if(buyerHasDele && (buyerAgentName == null || buyerAgentName == '')){
			alert("请选择买方委托人姓名！");
			return false;
		}
		if(sellerHasDele && (sellerAgentName == null || sellerAgentName == '')){
			alert("请选择卖方委托人姓名！");
			return false;
		}
		if(buyerHasDele && (buyerAgentName != null && buyerAgentName != '')){
			if(!isName(buyerAgentName)){
				alert("请填写合法的买方委托人姓名！");
				return false;
			}
		}
		if(sellerHasDele && (sellerAgentName != null && sellerAgentName != '')){
			if(!isName(sellerAgentName)){
				alert("请填写合法的卖方委托人姓名！");
				return false;
			}
		}

		var buyerAgentIdType = $("select[name='spvCustList[0].agentIdType'] option:selected").val();
		var sellerAgentIdType = $("select[name='spvCustList[1].agentIdType'] option:selected").val();
		if(buyerHasDele && (buyerAgentIdType == null || buyerAgentIdType == '')){
			alert("请选择买方委托人证件类型！");
			return false;
		}
		if(sellerHasDele && (sellerAgentIdType == null || sellerAgentIdType == '')){
			alert("请选择卖方委托人证件类型！");
			return false;
		}
		
		var buyerAgentIdCode = $("input[name='spvCustList[0].agentIdCode']").val();
		var sellerAgentIdCode = $("input[name='spvCustList[1].agentIdCode']").val();
		if(buyerHasDele && (buyerAgentIdCode == null || buyerAgentIdCode == '')){
			alert("请填写买方委托人证件编号！");
			return false;
		}
		if(buyerHasDele && buyerAgentIdCode != null && buyerAgentIdCode != ''){
			if(!isIdCardSimple(buyerAgentIdCode) && !new RegExp("/^\d{15}$/").test(buyerAgentIdCode)){
				alert("请填写合法的买方委托人证件编号！");
				return false;
			}
		}	
		if(sellerHasDele && (sellerAgentIdCode == null || sellerAgentIdCode == '')){
			alert("请填写卖方委托人证件编号！");
			return false;
		}
		if(sellerHasDele && sellerAgentIdCode != null && sellerAgentIdCode != ''){
			if(!isIdCardSimple(sellerAgentIdCode) && !new RegExp("/^\d{15}$/").test(sellerAgentIdCode)){
				alert("请填写合法的卖方委托人证件编号！");
				return false;
			}
		}
		
		var prOwnerName = $("input[name='toSpvProperty.prOwnerName']").val();
		if(prOwnerName == null || prOwnerName == ''){
			alert("请填写房产权利人！");
			return false;
		}
		if(prOwnerName != null && prOwnerName != ''){
			if(!isName(prOwnerName)){
				alert("请填写合法的房产权利人姓名！");
				return false;
			}
		}
		
		var prNo = $("input[name='toSpvProperty.prNo']").val();
		if(prNo == null || prNo == ''){
			alert("请填写房产证号！");
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
		var isMortClear = $("input[name='toSpvProperty.isMortClear'][value='0']").is(":checked");
		if(isMortClear && (mortgageeName == null || mortgageeName == '')){
			alert("请填写抵押方！");
			return false;
		}
		
		var mortgageeBank = $("input[name='toSpvProperty.mortgageeBank']").val();
		if(isMortClear && (mortgageeBank == null || mortgageeBank == '')){
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
		if(buyerAccountTelephone != null && buyerAccountTelephone != ''){
			if(!isMobile(buyerAccountTelephone)){
				alert("请填写合法的买方电话");
				return false;
			}
		}
		if(sellerAccountTelephone != null && sellerAccountTelephone != ''){
			if(!isMobile(sellerAccountTelephone)){
				alert("请填写合法的卖方电话");
				return false;
			}
		}
		
		var buyerBank = $("select[name='toSpvAccountList[0].bank'] option:selected").val();
		if(buyerBank == null || buyerBank == ''){
			alert("请选择买方开户行！");
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
    	$(":radio").attr("disabled",true);
    	$("select").attr("disabled",true);
    }
    
    function getParentBank(selector,selectorBranch,finOrgCode){
		var bankHtml = "<option value=''>请选择</option>";
	    $.ajax({
	    	cache:true,
	    	url:ctx+"/manage/queryParentBankList",
			method:"post",
			dataType:"json",
			async:false,
			data:{nowCode:finOrgCode},
			success:function(data){
				if(data != null){
					for(var i = 0;i<data.length;i++){
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
					}
				}
			}
	     });
	    selector.find('option').remove();
		 selector.append($(bankHtml));
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
		 getBranchBankList(selectorBranch,selector.val(),finOrgCode);
		 return bankHtml;
	}
	function getBranchBankList(selectorBranch,pcode,finOrgCode){
		selectorBranch.find('option').remove();
		selectorBranch[0];
		selectorBranch.append($("<option value=''>请选择</option>"));
		$.ajax({
			cache:true,
		    url:ctx+"/manage/queryBankListByParentCode",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{faFinOrgCode:pcode,nowCode:finOrgCode},
		    	success:function(data){
		    		if(data != null){
		    			for(var i = 0;i<data.length;i++){
							var coLevelStr='('+data[i].coLevelStr+')';
				
							var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
							if(data[i].finOrgCode==finOrgCode){
								option.attr("selected",true);
							}
							selectorBranch.append(option);
		    			}
		    		}
		    	}
		 });
		return true;
	}
	
	 // 身份证号验证 (日期)
	 function isIdCard(cardid) {
	     //身份证正则表达式(18位) 
	     var isIdCard2 = /^[1-9]\d{5}(19\d{2}|[2-9]\d{3})((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}|\d{3}X)$/i;
	     var stard = "10X98765432"; //最后一位身份证的号码
	     var first = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //1-17系数
	     var sum = 0;
	     if (!isIdCard2.test(cardid)) {
	         return false;
	     }
	     var year = cardid.substr(6, 4);
	     var month = cardid.substr(10, 2);
	     var day = cardid.substr(12, 2);
	     var birthday = cardid.substr(6, 8);
	     if (birthday != dateToString(new Date(year + '/' + month + '/' + day))) { //校验日期是否合法
	         return false;
	     }
	     for (var i = 0; i < cardid.length - 1; i++) {
	         sum += cardid[i] * first[i];
	     }
	     var result = sum % 11;
	     var last = stard[result]; //计算出来的最后一位身份证号码
	     if (cardid[cardid.length - 1].toUpperCase() == last) {
	         return true;
	     } else {
	         return false;
	     }
	 }
	 //日期转字符串 返回日期格式yyyyMMdd
	 function dateToString(date) {
	     if (date instanceof Date) {
	         var year = date.getFullYear();
	         var month = date.getMonth() + 1;
	         month = month < 10 ? '0' + month: month;
	         var day = date.getDate();
	         day = day < 10 ? '0' + day: day;
	         return year + month + day;
	     }
	     return '';
	 }
	 
	 //手机号码校验
	 function isMobile(number){
	     reg = /^1[3|4|5|7|8]\d{9}$/;
	     if (!reg.test(number)) {
	         return false; 
	     }
	     return true;
	 }
	 
		
	 //身份证验证(简单)
	function isIdCardSimple(cardId){
		 reg = /^\d{15}$)|(^\d{17}([0-9]|X)$/i;
	     if (!reg.test(cardId)) {
	         return false; 
	     }
	     return true;
	}
	
	//姓名验证(汉字和英文大小写)
	function isName(name){
		name = name.replace(/\s/g,"");//去除中间空格
		reg = /((^[\u4E00-\u9FA5]{2,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
		if (!reg.test(name)) {
	         return false; 
	     }
	     return true;
	}
