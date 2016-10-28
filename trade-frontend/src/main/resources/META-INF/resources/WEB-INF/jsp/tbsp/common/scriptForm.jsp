<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="${ctx}/static/tbsp/js/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
<script src="${ctx}/static/tbsp/js/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="${ctx}/static/tbsp/js/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/static/tbsp/js/jquery-validation/additional-methods.js"></script>
<script type="text/javascript">
    var validatorType = {LABLE:"lable",MOUSEOVER:"mouseover",DEFINITION:"definition"};
    var DEFAULTS_VALIDATOR = validatorType.LABLE;
	$(function (){
		$.extend($.validator.messages, {
    		required: "必填字段",
    		remote: "请指定一个不重复的值",
    		email: "请输入正确格式的电子邮件",
    		url: "请输入合法的网址",
    		date: "请输入合法的日期",
    		dateISO: "请输入合法的日期 (ISO).",
    		number: "请输入合法的数字",
    		digits: "只能输入整数",
    		creditcard: "请输入合法的信用卡号",
    		equalTo: "请再次输入相同的值",
    		accept: "请输入拥有合法后缀名的字符串",
    		maxlength: jQuery.validator.format("允许的最大长度为 {0} 个字符"),
    		minlength: jQuery.validator.format("允许的最小长度为 {0} 个字符"),
    		rangelength: jQuery.validator.format("允许的长度为{0}和{1}之间"),
    		range: jQuery.validator.format("请输入介于 {0} 和 {1} 之间的值"),
    		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    		min: jQuery.validator.format("请输入一个最小为 {0} 的值")
    	});
		$.metadata.setType("attr", "validate");
		$.validator.setDefaults({
	        success: function (lable){
	            lable.ligerHideTip();
	            lable.remove();
	        },
	        submitHandler: function (form){
	        	$(form).find(".l-text,.l-textarea").ligerHideTip();
	           	if($(form).attr("submitType") === "ajaxSubmit"){
	           		var option = {
	           			 type: "POST",
 	               		 url: $(form).attr("action"),
 	                     cache:false, 
 	               		 data: $(form).serialize(),
 	               		 success: function(data){
 	               			if(data && data.success != null && data.success == false && data.msg){
	 	                   		$.notice(data.msg,"error");
	 	                   		return ;
	 	                   	}
 	               			var ajaxCallBack = $(form).attr("ajaxCallBack");
 	               			if(ajaxCallBack){
 	               				while(/\(\w*?\)/.test(ajaxCallBack)){
 	               					ajaxCallBack = ajaxCallBack.replace(/\(\w*?\)/g,"");
 	               				}
 	               				if(typeof data === 'string'){
	 	               				eval(ajaxCallBack+"(\'"+data+"\')");
 	               				}else{
 	               					eval(ajaxCallBack+"(\'"+JSON.stringify(data)+"\')");
 	               				}
 	               			}
 	               		 }	
	           		}
	           		$.blockAjax(option);
	           	}else{
    	           	form.submit();
	           	}
	        }
		});
		if(DEFAULTS_VALIDATOR == validatorType.DEFINITION){
			//需要调用者自定义实现validate验证
		}else if(DEFAULTS_VALIDATOR == validatorType.MOUSEOVER){
			$("form").each(function(i){$(this).validate({errorPlacement: function (lable, element){if (element.hasClass("l-textarea")){element.addClass("l-textarea-invalid");}else if (element.hasClass("l-text-field")){element.parent().addClass("l-text-invalid");}$(element).removeAttr("title").ligerHideTip();$(element).attr("title", lable.html()).ligerTip();}});});
		}else{
			$("form").each(function(i){$(this).validate({errorPlacement: function (lable, element){if (element.hasClass("l-textarea")){ element.ligerTip({ content: lable.html(), target: element[0] }); }else if (element.hasClass("l-text-field")){element.parent().ligerTip({ content: lable.html(), target: element[0] });}else{ lable.appendTo(element.parents("td:first").next("td"));}}});});
		}
	    removeNameAndId();
	    $("form").ligerForm();
	    restoreNameAndId();
	});
	function removeNameAndId(){$("input[name='id']").each(function(i){ $(this).removeAttr("name"); $(this).attr("tbspnametemp","id");});$("input[id='id']").each(function(i){ $(this).removeAttr("id"); $(this).attr("tbspidtemp","id");});}
	function restoreNameAndId(){$("input[tbspnametemp='id']").each(function(i){ $(this).removeAttr("tbspnametemp"); $(this).attr("name","id"); });$("input[tbspidtemp='id']").each(function(i){ $(this).removeAttr("tbspidtemp"); $(this).attr("id","id"); });}
</script>