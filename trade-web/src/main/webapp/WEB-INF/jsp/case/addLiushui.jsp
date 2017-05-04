<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增实收流水</title>
	<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
	<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
	<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<!-- stickUp fixed css -->
	<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
	<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
	<!-- index_css  -->
	<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
	<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
	<link rel="stylesheet" href="${ctx}/static_res/trans/css/common/report.css">
	<link rel="stylesheet" href="${ctx}/static/trans/css/addOutlist.css">
	<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
</head>

<body>
<input type="hidden" id="caseCode_" value="${caseCode_}" />
  <div class="wrapper wrapper-content animated fadeInUp">
      <div class="ibox-content" id="reportFive">
     	 <form  id="saveCaseInfo">
     	  <input type="hidden" name="distCode" id="distCode" value="${caseCode_}">
     	  <input type="hidden" id="caseCode" name="caseCode" value="${caseCode}" />
          <div method="get" class="form_list">
              <div class="title"> 新增实收流水 </div>
          </div>
          <div  class="form_list mt20" id="addVoucher">
              <div class="line">
                  <div class="form_content">
                      <label class="control-label sign_left_small">付款金额</label> 
                      <input class="select_control sign_right_one" placeholder="" name="paymentAmount" id="paymentAmount" value="">
                     <span class="date_icon">万元</span>
                  </div>
                  <div class="form_content">
                      <label class="control-label sign_left_small">
                          	付款人
                      </label>
                      <input type="text" class="select_control sign_right_one" id="payer" name="payer" value="">
                  </div>
                  <div class="form_content">
                      <label class="control-label sign_left_small">
                          	付款时间
                      </label>
                      <input id="paymentDate" name="paymentDate" class="select_control sign_right_one" type="text"  placeholder="付款时间">
                  </div>
              </div>
              <div class="line">
                <div>
                     <div class="ibox-title" style="height: auto;border:0;padding-left:0;" id="aboutInfo">
						<c:choose>
							<c:when test="${accesoryList!=null}">
								<label class="control-label sign_left_small">上传附件</label>
								<div class="table-box" id="addWdCaseContract" style="margin-left: 127px;margin-top: -27px;"></div>
							</c:when>
							<c:otherwise>
								<label class="control-label sign_left_small pull-left">上传附件</label>
								<p style=" float: left;  margin-left: 5px; font-weight: bold; ">无需上传备件</p>
							</c:otherwise>
						</c:choose>
					</div>
                </div>
            </div>
          </div>
          <div class="text-center mt40 mb30">
               <a onclick="sumbitRe()" class="btn btn-success">创建</a>
              <button type="reset" class="btn btn-grey" data-toggle="modal" >清空</button>
          </div>
          </form>
      </div>
      <div class="ibox-content" style="height:250px;background:#f3f3f4;border-top:none;">
      </div>
  </div>
 
<content tag="local_require">
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script>

/**
 * 日期控件
 */
$('#paymentDate').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

/**
* 添加附件
*/
var fileUpload;
 require(['main'], function() {
 	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
 		fileUpload = aistFileUpload;
 		
 		fileUpload.init({
  		caseCode : $('#caseCode_').val(),
  		partCode : "AddLiushui",
  		fileUploadContainer : "addWdCaseContract"
  	}); 
  });
 });
 
 /**
  * 页面提交
  */
 function sumbitRe(){	
	 
	if(!checkForm()){
		return false;		
	}
	
 	var data = [];
 	$("form").each(function(){
 		var obj = $(this).serializeArray();
 		for(var i in obj){
 			data.push(obj[i]);
 		}
 	}); 

 	var url = ctx+"/caseMerge/saveLiushui";
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
 				window.wxc.success("新增实收流水成功！",{"wxcOk":function(){
 					window.location.href=ctx+"/case/myCaseList";
 				}});
 			}else{
 				window.wxc.error("新增实收流水失败！"+data.message); 
 			}
 			
 		}
 	});
 	
 }
 
 function checkForm(){
		var formSubmitFlag = true;
		
		if ($('input[name=paymentAmount]').val() == '') {
			window.wxc.alert("付款金额不能为空!");
			$('input[name=paymentAmount]').focus();
			return false;
		}
		
		var payerAmountFlag = true;
		var payerAmountEle;
		$("input[name$='paymentAmount']").each(function(i,e){
			if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber($(e).val()))){
				payerAmountFlag = false;
				payerAmountEle = $(e);
				
				return false;
			}
		});
		if(!payerAmountFlag){
				window.wxc.alert("请填写有效的金额！");
			    changeClass(payerAmountEle);
				return false;
		}
		
		if ($('input[name=payer]').val() == '') {
			window.wxc.alert("付款人不能为空!");
			$('input[name=payer]').focus();
			return false;
		}
		
		var payerNameFlag = true;
		var payerNameEle;
		$("input[name='payer']").each(function(i,e){
			if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isName($(e).val()))){
				payerNameFlag = false;
				payerNameEle = $(e);
				 return false;
				 }
			});
		
		 if(!payerNameFlag){
			 window.wxc.alert("请填写有效的付款人姓名！");
		    changeClass(payerNameEle);
			return false;
		 }
		 
		 
		 var cashFlowCreateTimeFlag = true;
			var cashFlowCreateTimeEle;
			$("input[name='paymentDate']").each(function(i,e){
				if(($(e).val() == null || $(e).val() == '')){
					cashFlowCreateTimeFlag = false;
					cashFlowCreateTimeEle = $(e);
					return false;
				}
			});
			if(!cashFlowCreateTimeFlag){
				window.wxc.alert("请选择有效的付款时间！");
				changeClass(cashFlowCreateTimeEle);
				return false;
			}
		
		if ( ($("#addLiushui_contract_pic_list li").length == undefined || $("#addLiushui_contract_pic_list li").length == 0) ) {
			window.wxc.alert("请上传附件信息！");
			formSubmitFlag = false;
			return false;
		}
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
     
</script>
</content>
</body>
</html>