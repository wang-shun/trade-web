function checknum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function checkInt(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");  
}

var attachmentList = null;
var files = null;
var attachmentIds = null;
var preFileAdress = null;
var picTag = null;
var picName = null;
var index = 0;
function getUploadPicInfo() {
	attachmentIds =[];
	preFileAdress = [];
	picTag = [];
	picName = [];

	// 图片的ID
	$("div[name='reportPic']").each(function(){
		$(this).find("input[name='preFileAdress']").each(function(){
			preFileAdress.push($(this).val());
		});
		$(this).find("input[name='picTag']").each(function(){
			picTag.push($(this).val());
		});
		$(this).find("input[name='picName']").each(function(){
			picName.push($(this).val());
		});
		
	});

	$("input[name='attachmentId']").each(function(){
		attachmentIds.push($(this).val());
	});
	
	return false;
}
function checkDisagree(){
	
	if($("#expected_price").val() == ""){
		$("#expected_price").css("border-color","red");
		return false;
	}else if($("#disagreeForm").find("input[name='remark']").val() == ""){
		$("#disagreeForm").find("input[name='remark']").css("border-color","red");
		return false;
	}

	return true;
}
//发起异议申请
function disagree(){

	var evaCode = $("#evaCode").val();
	if(checkDisagree()){
		if(evaCode!=""){
			$("#subDisagreeBtn").attr("disabled",true);

			$.ajax({
				url:ctx+"/remote/egu/"+evaCode+"/disagree",
				method:"post",
				dataType:"json",
				data:{code:evaCode,expected_price:$("#expected_price").val(),remark:$("#disagreeForm").find("input[name='remark']").val()},
				success:function(data){
					$("#subDisagreeBtn").attr("disabled",false);

					alert(data.message);
					if(data.success){
						$("#disagreeModal").modal("hide");
					}
				}
			});	
		}
	}
}

//确认询价结果
function confirmPricing(){
	var evaCode = $("#evaCode").val();
	$.ajax({
		url:ctx+"/remote/egu/"+evaCode+"/confirm",
		method:"post",
		dataType:"json",
		data:{evaCode:evaCode},
		success:function(data){
			if(data.message != ""){
				alert(data.message);
			}
		}
	});
}

function bindEvaCode(){
	$.ajax({
		url:ctx+"/task/bindEvaCode",
		method:"post",
		dataType:"json",
		data:{caseCode:$("#caseCode").val(),evaCode:$("#evaCode").val(),isMainLoanBank:$("input[name='mainBank']:checked").val(),isFinal:$("input[name='isFinal']:checked").val()},
		success:function(data){
			alert(data.message);
			if(data.success){
				caseCode = $("#caseCode").val();
				$("#bindCaseCodeModal").modal("hide");
			}
		}
	});
}	
//加载已上传的附件信息
function getAttchInfo(){
	 $.ajax({
	    url:ctx+"/attachment/quereyAttachments",
	    method:"post",
	    dataType:"json",
	    data:{caseCode:caseCode,partCode:"ToEvaReport"},
	    	success:function(data){
			//	dataLength=data.attList.length;

				//将返回的数据进行包装
				for(var i=1;i<4;i++){
					var trStr = "";
					$("#picContainer"+i).html("");
					//实勘描述
					$.each(data.attList,function(index, value) {
						if(value.preFileCode==i){
							trStr+="<div id='picContainer"+value.pkid+"' name=\"allPicDiv\" class=\"template-download fade row-fluid span2 in\" style=\"height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;text-align:center;border-radius:4px;float:left;\">";
							trStr+="<div class=\"preview span12\">";
							trStr+="<input type=\"hidden\" name=\"pic\" id=\"pic\" value=\""+i+"\" />";
							trStr+="<input type=\"hidden\" name=\"attachmentId\" value=\""+value.pkid+"\" />";

							trStr+="<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/80_80_f.jpg' alt=''>";
							trStr+="</div>";
							trStr+="<div class=\"delete span2\" style=\"margin-left: 85%; margin-top: -120px;\">";
							trStr+="<button onclick=\"romoveDiv('picContainer',"+value.pkid+");\" class=\"btn red\""; 
							trStr+="style=\"line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;\">";
							trStr+="<i class=\"icon-remove\"></i>";
							trStr+="</button>";
							trStr+="</div>";
							trStr+="</div>";
							
						}
					});
					$("#picContainer"+i).html(trStr);

				}
	    	}
	  });
}
function checkReportAtt(){
	var flag = true;
	for(var i=1;i<4;i++){
		var length = $("#picContainer"+i).find("img").length;
		if(length == 0) {
			alert("备件未完全上传！");
			flag = false;
			break;
		} else {
			flag = true;
		}
	}
	return flag;
}
function reportApply(){
	if(caseCode == ""){
		alert("请先绑定案件编号！");
		return;
	}
	if($("#reportType").val()==""){
		alert("请选择报告类型！");
		return ;
	}
	getUploadPicInfo();

   	var picDiv=$("div[name='allPicDiv1']");
    var spans =$("input[name='preFileAdress']");
    if(spans.length < picDiv.length) {
    	alert("你有未上传的完成的文件，请稍候再试！");
    	return;
    }
	if(!checkReportAtt()){
		return;
	}
	
	var reportType = $("#reportType").val();
	var evaCode = $("#evaCode").val();
	var url = "";
	var finOrgCode = $("#orgPricing").val();
	if(reportType == "1"){  //预估单
		url = ctx+"/remote/egu/"+evaCode+"/prereport";
	}else if(reportType == "2"){ //询价单
		url = ctx+"/remote/egu/"+evaCode+"/inquiryreport";
	}else if(reportType == "3"){   //评估报告
		url = ctx+"/remote/egu/"+evaCode+"/report";
	}
	$("#reportSubBtn").attr("disabled",true);

	$.ajax({
		url:url,
		method:"post",
		dataType:"json",
		data:[
		    {name:"caseCode",value:caseCode},
			{name:"finOrgCode",value:finOrgCode},
			{name:"reportType",value:reportType},
			{name:"evaCode",value:evaCode},
			{name:"preFileAdress",value:preFileAdress},
			{name:"picTag",value:picTag},
			{name:"picName",value:picName},
			{name:"attachmentIds",value:attachmentIds}
		], 
		success:function(data){
			$("#reportSubBtn").attr("disabled",false);

			if(data.success){
				$("#modal-form-report").modal('hide');
			}
    		alert(data.message);
			
		}
	});
}
$(document).ready(function () {
	var updFun = function(e) {
		var that = $(this).data('blueimp-fileupload')
				|| $(this).data('fileupload');
		that._resetFinishedDeferreds();
		that._transition($(this).find('.fileupload-progress'))
				.done(function() {
					that._trigger('started', e);
				});
	};
	
	for(var i=1;i<4;i++){
		AistUpload.init('picFileupload'+i, 'picContainer'+i,
				'templateUpload'+i, 'templateDownload'+i,
				updFun);
		$("#picContainer"+i).bind('DOMNodeInserted', function(e) {
			var picDiv=$("div[name='allPicDiv1']");
			var input=$("input[name='picTag']");
			if(picDiv.length > input.length) {
				index++;
				if(index % 2 == 0) {
					index = 0;
					$("#startUpload").click();
				}
			}
		});
	}
	$("#bindBtn").click(function(){
		$("#bindCaseCodeModal").modal("show");
	});
	$("#subBindBtn").click(function(){
		bindEvaCode();
	});
	
	$("#disagreeApplyBtn").click(function(){

		$("#disagreeModal").modal("show");
	});
	$("#subDisagreeBtn").click(function(){
		
		disagree();
	});
	
	$("#subPricingBtn").click(function(){
		if(caseCode == null || caseCode == ""){
			alert("请先绑定案件编号！")
			return;
		}
		confirmPricing();
	});
	
	$("#reportApplyBtn").click(function(){
		if(caseCode == null || caseCode == ""){
			alert("请先绑定案件编号！")
			return;
		}
		getAttchInfo();
		$("#reportModal").modal("show");
	});
	
	$("#reportSubBtn").click(function(){
		reportApply();
	});
 });