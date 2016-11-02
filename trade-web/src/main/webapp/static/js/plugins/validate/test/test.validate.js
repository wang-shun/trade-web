/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

/*var TestValidate = function () {
	 //校验规则 数组
	 rules = {
			  zipCode : {
				 required : true,
		    	 isZipCode : true
		      },
		      firstname: "required",
		      lastname: "required",
		      username: {
		        required: true,
		        minlength: 2
		        
		      },
		      password: {
		        required: true,
		        minlength: 5
		      },
		      confirm_password: {
		        required: true,
		        minlength: 5,
		        equalTo: "#password"
		      },
		      email: {
		        required: true,
		        email: true
		      },
		      "topic[]": {
		        required: "#newsletter:checked",
		        minlength: 2
		      },
		      agree: "required"
	 };
	 
	 messages = {
			  firstname: "请输入您的名字",
		      lastname: "请输入您的姓氏",
		      username: {
		        required: "请输入用户名",
		        minlength: "用户名必需由两个字母组成"
		      },
		      password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母"
		      },
		      confirm_password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母",
		        equalTo: "两次密码输入不一致"
		      },
		      email: "请输入一个正确的邮箱",
		      agree: "请接受我们的声明",
		      "topic[]": {
		    	  required: "你接受声明，至少勾选两个主题",
		    	  minlength : "至少勾选两个主题"
		      }
	};
	 
   jQuery.validator.addMethod("isZipCode", function(value, element) {   
		    var tel = /^[0-9]{6}$/;
		    return this.optional(element) || (tel.test(value));
   }, "请正确填写您的邮政编码ddd");
   
   jQuery.validator.addMethod("isExist", function(value, element) {   
	    var regx = /,/;
	    return this.optional(element) || (regx.test(value));
   }, "请正确填写您的邮政编码ddd");
   
    //重写错误显示消息方法,以alert方式弹出错误消息   
   showErrors = function(errorMap, errorList) {  
    var msg = "";  
    $.each(errorList, function(i, v) {  
     msg += (v.message + "\r\n");  
    });  
    if (msg != "")  
    	layer.alert(msg,{
    		icon: 2,
    		time : 2000
    	});
   }
	
	return {
		init: function (formid,errorid) {
			 //验证
		    $("#"+formid).validate(
		    	{
		    		//需验证的  name
		    		rules: rules, 
		    		messages: messages,
		    		showErrors : showErrors,
		    		onfocusout	: false,
		    		onkeyup	: false,
		    		focusInvalid	: true,
		    		onclick : false
		    		//focusCleanup : false
		    		//错误提示信息位置
					//errorLabelContainer: $("#"+errorid),
					//wrapper: "li",
					//ignore : ":hidden:not([name*='isExistPartiInfo'])"
		    	}		
		    );
		}
	};
}();*/

function check(){ 
	   jQuery.validator.addMethod("isZipCode", function(value, element) {   
		    var tel = /^[0-9]{6}$/;
		    return this.optional(element) || (tel.test(value));
  }, "请正确填写您的邮政编码ddd");
  
  jQuery.validator.addMethod("isExist", function(value, element) {   
	    var regx = /,/;
	    return this.optional(element) || (regx.test(value));
  }, "请正确填写您的邮政编码ddd");
  
   //重写错误显示消息方法,以alert方式弹出错误消息   
  showErrors = function(errorMap, errorList) {  
   var msg = "";  
   $.each(errorList, function(i, v) {  
    msg += (v.message + "\r\n");  
   });  
   if (msg != "")  
   	layer.alert(msg,{
   		icon: 2,
   		time : 20000
   	});
  }
return $("#signupForm").validate({ 
	rules:  {
		  zipCode : {
				 required : true,
		    	 isZipCode : true
		      },
		      firstname: "required",
		      lastname: "required",
		      username: {
		        required: true,
		        minlength: 2
		        
		      },
		      password: {
		        required: true,
		        minlength: 5
		      },
		      confirm_password: {
		        required: true,
		        minlength: 5,
		        equalTo: "#password"
		      },
		      email: {
		        required: true,
		        email: true
		      },
		      "topic[]": {
		        required: "#newsletter:checked",
		        minlength: 2
		      },
		      agree: "required"
	 }, 
	messages: {
		  firstname: "请输入您的名字",
	      lastname: "请输入您的姓氏",
	      username: {
	        required: "请输入用户名",
	        minlength: "用户名必需由两个字母组成"
	      },
	      password: {
	        required: "请输入密码",
	        minlength: "密码长度不能小于 5 个字母"
	      },
	      confirm_password: {
	        required: "请输入密码",
	        minlength: "密码长度不能小于 5 个字母",
	        equalTo: "两次密码输入不一致"
	      },
	      email: "请输入一个正确的邮箱",
	      agree: "请接受我们的声明",
	      "topic[]": {
	    	  required: "你接受声明，至少勾选两个主题",
	    	  minlength : "至少勾选两个主题"
	      }
},
	showErrors : showErrors,
	onfocusout	: false,
	onkeyup	: false,
	focusInvalid	: true,
	onclick : false,
	focusCleanup : false

}); 
} 


   