/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
var TaskTransSignValidate  = function () {
	 //校验规则 数组
	 rules = {
			 guestNameUp : {
				 isillegal : true
		      },
		      guestNameDown: {
		    	  isillegalDown : true
		      }
	 };
	 
	 messages = {
	 };

    jQuery.validator.addMethod("isillegal", function(value, element) {   
	    var regx = /,/;
	    return this.optional(element) || (!regx.test(value));
    }, "上家姓名不能包含特殊字符");
    
    jQuery.validator.addMethod("isillegalDown", function(value, element) {   
	    var regx = /,/;
	    return this.optional(element) || (!regx.test(value));
    }, "下家姓名不能包含特殊字符");
    
    //重写错误显示消息方法,以alert方式弹出错误消息   
    showErrors = function(errorMap, errorList) {  
     var msg = "";  
     $.each(errorList, function(i, v) {  
      msg += (v.message + "\r\n</br>");  
     });  
     if (msg != "")  
     	layer.alert(msg,{
     		icon: 2
     		//time : 2000
     	});
    };
    

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
	}
}();


   