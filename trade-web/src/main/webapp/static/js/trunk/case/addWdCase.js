/**
 * 外单案件添加页面js
 * 编写：hjf
 * 时间：2017年4月21日13:51:21
 * 版本：1.0
 */


function getCheckBoxValues(name) {
	var commSubject = [];
	$("span[name='commSubject'].out-btn-select").each(function() {
		var val = $(this).attr('value');
		commSubject.push(val);
	});							
	return commSubject;
}

/**
 * 页面提交
 */
function sumbitRe(){
	
	
	
	if(!checkForm()){
		return false;		
	}
	if(!phoneUpAndphoneDownCheck()){
		return false;
	}
	var commSubject = getCheckBoxValues("commSubject");
	var data = [];
	$("form").each(function(){
		var obj = $(this).serializeArray();
		for(var i in obj){
			if(obj[i].name=="commSubject"){
				obj[i].value=commSubject.toString();
			}
			data.push(obj[i]);
		}
	}); 
	var url = ctx+"/caseMerge/saveWdCaseInfo";
	$.ajax({
		cache : false,
		async : false,
		type : "post",
		url : url,
		dataType : "json",
		data : data,
		beforeSend:function(){  
         },
		success : function(data){
			if(data.success){
				window.wxc.success("新建外单成功！",{"wxcOk":function(){
					window.location.href=ctx+"/case/myCaseList";
				}});
			}else{
				window.wxc.error("新建外单失败！"+data.message); 
			}
			
		}
	});
	
}
/**
 * 编辑提交
 */
function saveRe(){	
	
	if(!checkForm()){
		return false;		
	}
	if(!phoneUpAndphoneDownCheck()){
		return false;
	}
	
	var commSubject = getCheckBoxValues("commSubject");
	var data = [];
	$("form").each(function(){
		var obj = $(this).serializeArray();
		for(var i in obj){
			if(obj[i].name=="commSubject"){
				obj[i].value=commSubject.toString();
			}
			data.push(obj[i]);
		}
	}); 
	
	var url = ctx+"/caseMerge/editWdCaseInfo";
	$.ajax({
		cache : false,
		async : false,
		type : "post",
		url : url,
		dataType : "json",
		data : data,
		beforeSend:function(){  
		},
		success : function(data){
			if(data.success){
				window.wxc.success("保存外单成功！",{"wxcOk":function(){
					window.location.href=ctx+"/case/myCaseList";
				}});
			}else{
				window.wxc.error("保存外单失败！"+data.message); 
			}
			
		}
	});
	
}
/**
 * 页面校验
 * @returns {Boolean}
 */
function checkForm(){
	var formSubmitFlag = true;
	
    var voucherNoFlag = true;
	var voucherNoEle;
	$("select[name$='sourceOfCooperation']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '')){
			voucherNoFlag = false;
			voucherNoEle = $(e);
			return false;
		}
	});
	
	if ($('input[name=propertyAddr]').val() == '') {
		window.wxc.alert("房屋地址不能为空!");
		$('input[name=propertyAddr]').focus();
		return false;
	}
	if ($('input[name=agentName]').val() == '') {
		window.wxc.alert("推荐人姓名不能为空!");
		$('input[name=agentName]').focus();
		return false;
	}
	
	var payerNameFlag = true;
	var payerNameEle;
	$("input[name='agentName']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isName($(e).val()))){
			payerNameFlag = false;
			payerNameEle = $(e);
			 return false;
			 }
		});
	
	 if(!payerNameFlag){
		 window.wxc.alert("请填写有效的推荐人姓名！");
	    changeClass(payerNameEle);
		return false;
	 }
	 
	
	if ($('input[name=agentPhone]').val() == '') {
		window.wxc.alert("推荐人电话不能为空!");
		$('input[name=agentPhone]').focus();
		return false;
	}
	
	var agentPhoneFlag = true;
	var agentPhoneEle;
	$("input[name='agentPhone']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !checkContactNumber($(e).val()))){
			agentPhoneFlag = false;
			agentPhoneEle = $(e);
			 return false;
			 }
		});
	
	 if(!agentPhoneFlag){
		 window.wxc.alert("请填写有效的推荐人电话！");
	    changeClass(payerNameEle);
		return false;
	 }
	
	if(!voucherNoFlag){
		window.wxc.alert("请选择有效的合作来源！");
	    changeClass(voucherNoEle);
		return false;
	 }
   
	
	if ($('input[name$=guestName]').val() == '') {
		window.wxc.alert("上下家姓名不能为空!");
		$('input[name$=guestName]').focus();
		return false;
	}
	if ($('input[name$=guestPhone]').val() == '') {
		window.wxc.alert("上下家电话不能为空!");
		$('input[name$=guestPhone]').focus();
		return false;
	}
	if ($('input[name=commCost]').val() == '') {
		window.wxc.alert("服务价格不能为空!");
		$('input[name=commCost]').focus();
		return false;
	}
	
	if ( ($("#addWdCase_contract_pic_list li").length == undefined || $("#addWdCase_contract_pic_list li").length == 0) ) {
		window.wxc.alert("请上传附件信息！");
		return false;
	}
/*	
	 $('span[.out-btn-select]').each(function(i,e){
	    	var length = $(e).find("span").length;
	    	if(length == 0){
	    		window.wxc.alert("服务项目不能为空!");
	    		return false;
	    	}
	    });*/
	
	return formSubmitFlag;
}
function changeClass(object){
	$(object).focus();
	$(object).addClass("borderClass").blur(function(){
		$(this).removeClass("borderClass");
	});	;
}

/**
 * 金额验证(两位小数)
 */
function isNumber(num){
	var reg=/^([1-9]{1}\d*)(\.\d{1,2})?$/;
	if(!reg.test(num)){
		return false;
	}
	return true;
}

/**
 * 姓名验证(汉字和英文大小写)
 */
function isName(name){
	name = name.replace(/\s/g,"");
	reg = /((^[\u4E00-\u9FA5]{1,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
	if (!reg.test(name)) {
       return false; 
   }
   return true;
}

//上下家电话相同验证
function phoneUpAndphoneDownCheck() {
	var checkGuestPhone= true;
	var selectsPhoneDown = $("input[name='guestPhoneDown']");
	var selectsPhoneUp = $("input[name='guestPhoneUp']");				
	
	
	$.each(selectsPhoneUp, function(j, item) {
		if (item.value == '') {
			window.wxc.alert("上家电话为必填项!");
			selectsPhoneUp[j].focus();
			checkGuestPhone = false;						
		} else {						
			checkGuestPhone = checkContactNumber(item.value);						
			if (!checkGuestPhone) {								
				//alert("上家电话不符合手机号码或电话号码格式!");
				selectsPhoneUp[j].focus();
				return false;
			}
		}
	});
	if (!checkGuestPhone || selectsPhoneUp == null) {
		return false;
	}
	//验证下家电话号码
	$.each(selectsPhoneDown, function(j, item) {
		if (item.value == '') {
			window.wxc.alert("下家电话为必填项!");
			selectsPhoneDown[j].focus();
			checkGuestPhone = false;
		} else {
			checkGuestPhone = checkContactNumber(item.value);
			if (!checkGuestPhone) {
				//alert("下家电话不符合手机号码或电话号码格式!");
				selectsPhoneDown[j].focus();
				return false;
			}
		}
	});
	if (!checkGuestPhone || selectsPhoneDown == null) {
		return false;
	}				
	$.each(selectsPhoneUp,function(i, itemPhoneUp) {
			if (itemPhoneUp.value != '') {
				$.each(selectsPhoneDown,function(j,	itemPhoneDown) {
					if (itemPhoneDown.value != '') {
						if (itemPhoneUp.value.trim() == itemPhoneDown.value.trim()) {
							window.wxc.alert("上下家电话不能填写一样!");
									checkGuestPhone=false;
									return checkGuestPhone;
						}
					}
				})							
		    }
	if(checkGuestPhone==false) {return  false;}
	})				
	return checkGuestPhone;
}


//判断是否有重复字符
function isUniqueChar(value){
	if(!value){
		return false;
	}
	var uniqueMap   = {};
	for(i=0;i<value.length;i++){
		var val = value.charAt(i);
		uniqueMap[val]=val;
	}
	var result = ""
	for(var key in uniqueMap){
		result +=key;
	}
	return (result.length==1);
}
//验证手机和电话号码
function checkContactNumber(ContactNumber) {
	
	var mobile = $.trim(ContactNumber);	
	var number=/^[0-9]*$/;	//数字
	var isValid = true;
	
	if(!number.exec(mobile)){					
		window.wxc.alert("电话号码只能由数字组成！");
		isValid = false;
		return isValid;
	}
	if(!(mobile.length ==8 || mobile.length ==11 || mobile.length ==13 || mobile.length ==14)){				
		window.wxc.alert("电话号码只能由是8位、11位、13位或者14位的数字组成！");
		isValid = false;
		return isValid;
	}
	
	if(isUniqueChar(mobile)){
		window.wxc.alert("电话号码不能为全部相同的数字！");
		isValid = false;
		return isValid;
	}
	return isValid;
}

