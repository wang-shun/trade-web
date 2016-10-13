/**
 * 入账申请
 */
$(function(){
	$("#addTr").html(getTR(0));
});
var handle = $("#handle").val();
var trindex = 0;

//添加入账申请信息tr
function getTR(thisIndex){
	var nextIndex = thisIndex+1;
	var  $str='';
	$str+='<tr>                                                                                                                                                                                           ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<input class="table-input-one boderbbt" type="text" placeholder="请输入姓名" name="items['+thisIndex+'].payerName">                                                                                         ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<p><input class="table_input boderbbt forBankNo" type="text"placeholder="请输入银行卡号"  onKeypress="if (!(event.keyCode > 47 && event.keyCode < 58)) event.returnValue = false;" name="items['+thisIndex+'].payerAcc"></p>                                                                                   ';
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="items['+thisIndex+'].payerBank"></p>                                                                                  ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td class="text-left">                                                                                                                                                                     ';
	$str+='		<input class="boderbbt forPayerAmount" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if (!(event.keyCode > 45 && event.keyCode < 58 &&event.keyCode !=47 ) ) event.returnValue = false;" name="items['+thisIndex+'].payerAmount">万元                                                                        ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<input class="table_input boderbbt forvalue" type="text" placeholder="请输入编号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="items['+thisIndex+'].receiptNo">                                                                                             ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<select name="items['+thisIndex+'].voucherNo" class="table-select boderbbt" onChange="this.value">                                                                                                                                ';
	$str+='			<option value="">请选择</option>                                                                                                                                                   ';
	$str+='			<option value="转账">转账</option>                                                                                                                                                 ';
	$str+='			<option value="刷卡">刷卡</option>                                                                                                                                                 ';
	$str+='			<option value="现金">现金</option>                                                                                                                                                 ';
	$str+='		</select>                                                                                                                                                                              ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td id="td_file'+thisIndex+'">                                                                                                                                                                                         ';
	$str+='		<span class="btn_file'+thisIndex+'">                                                                                                                                                                ';
	$str+='			<input id="fileupload_'+thisIndex+'" style="display:none" type="file" name="files[]" multiple="" data-url="http://a.sh.centanet.com/aist-filesvr-web/servlet/jqueryFileUpload" data-sequential-uploads="true">                                                                                                                                                 ';
	$str+='			<img class="bnt-flie" src="http://trade.centaline.com:8083/trade-web/static/trans/img/bnt-flie.png" alt="点击上传" style="cursor:pointer;" onClick="$(\'#fileupload_'+thisIndex+'\').trigger(\'click\');">                                                                        ';
	$str+='		</span>                                                                                                                                                                                ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+nextIndex+')">添加</a>';
	if(thisIndex > 0){
		$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>                                                                                                           ';
	}
	$str+='</tr>                                                                                                                                                                                          ';
	
	$("#addTr").append($str);

	$('#fileupload_'+thisIndex).fileupload({
		acceptFileTypes:'/(gif|jpg|jpeg|bmp|png|tif|tiff)/i',
		autoUpload: true,
        dataType: 'json',
        add:function(e,data){
        	var fileName = data.files[0].name;
        	if($("input[fileName='"+fileName+"']").size()==0){
        		data.submit();
        	}
        },
        done: function (e, data) {
        	if(data.result){
            	var fileId =  data.result.files[0].id;
            	var fileUrl = data.result.files[0].url;
            	var fileName = data.result.files[0].name;
            	var image = getUploadImage(thisIndex,fileUrl,fileId,fileName);
            	$('#td_file'+thisIndex).prepend(image);	
        	}
        }
    });

	cleanPkid();
}
function getUploadImage(thisIndex,fileUrl,fileId,fileName){
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = '<a class="response" target="_blank" href="'+fileUrl+'" title="'+fileName+'" alt="'+fileName+'">';
	image += '<input type="hidden" name ="items['+thisIndex+'].fileId" value = "'+fileId+'" fileName="'+fileName+'"/>';
	image += '<button type="button" class="btn btn-sm btn-default" >'+shortName+'<i class="icon iconfont icon_x" onClick="$(this).parent().parent().remove();return false;">&#xe60a;</i></button></a>';
	return image;
}
//删除入账申请信息tr
function getDel(k){
    $(k).parents('tr').remove();
    cleanPkid();
}

function checkReceiptNo(){
	var theSameFlag = true;
	var receiptNoArray = new Array();
		receiptNoArray = $(".forvalue");
	var reg = /^[0-9]*$/;
	for(var i=0; i<receiptNoArray.length; i++){	
		for(var j=i+1; j<receiptNoArray.length ;j++){
				if(receiptNoArray[i].value == receiptNoArray[j].value){
					theSameFlag=false;
					alert("贷记凭证编号不能重复！");
				}
				if(theSameFlag==false){
					//break;
					return  false;
				}
			}
		if(theSameFlag==false){
			//break;
			return  false;
		}
	}
	
	 $.each(receiptNoArray,function(i, item) {
			if (item.value != '') {
				//if(!reg.exec(item.value.trim())){
				if(!reg.test(item.value.trim())){
					alert("贷记凭证编号只能由数字组成！");
					theSameFlag = false;
					return theSameFlag;
				}				
			}
			if(theSameFlag==false){
				return  false;
			}
	 })
	 
	return theSameFlag;
}

function checkBankNoAndPayerAmount(){
	var regForBankNo = /^[0-9]*$/;
	var regForPayerAmount = /^\d+(\.\d+)?$/;
	//var r = new RegExp("^\\d+(\\.\\d+)?$");
	var bankNoArray = $(".forBankNo");
	var PayerAmountArray = $(".forPayerAmount");
	var flag = true;
	 $.each(bankNoArray,function(i, item) {
			if (item.value != '') {				
				if(!regForBankNo.test(item.value.trim())){
					alert("银行卡号只能由数字组成！");
					flag = false;
					return flag;
				}				
			}
			if(flag==false){
				return  false;
			}
	 })
	 
	 $.each(PayerAmountArray,function(i, item) {
			if (item.value != '') {
				//if(!reg.exec(item.value.trim())){
				if(!regForPayerAmount.test(item.value.trim())){
					alert("入职金额只能由数字和小数点组成！");
					flag = false;
					return flag;
				}				
			}
			if(flag==false){
				return  false;
			}
	 })
	return flag;
}
//提交
function sumbitRe(){
	if(!confirm("是否确定提交申请，开启流程！")){
	  return false;
    }
	//验证凭证编号不能重复和只能为数字
	if(!checkReceiptNo()){
		return;
	}	
	//银行卡号、金额等
	if(!checkBankNoAndPayerAmount()){
		return;
	}	
	//提交页面的参数
	var data = $("#teacForm").serialize();
	//console.log(data);
	var url = ctx+"/spv/task/cashflowIntApply/sumbitDate";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data : data,
		beforeSend:function(){  
         },
		success : function(data) {
			// window.location.href = ctx+"/task/myTaskList";
			if(data.success){
				alert("流程开启成功！");
			}else{
				alert("任务提交成功！"+data.message); 
			}
			window.location.href = ctx+"/task/myTaskList";
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}
//得到页面数据 
function getFormData(){
	var data = $("#teacForm").serialize();
}


//保存起草提交
function saveRe(){
	if(!confirm("保存入账申请信息数据！")){
	  return false;
    }
//提交页面的参数
	 //保存必填项
	/*if(!checkFormSave()){
		  return false;
	  }*/
	
	
	var data = $("#teacForm").serialize();
	var url = ctx+"/spv/task/cashflowIntApply/saveDate";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data : data,
		beforeSend:function(){  
         },
		success : function(data) {
			if(data.message){
				var strs= new Array();
				strs=data.message.split(";");
				for (i=0;i<strs.length ;i++ ){ 
					//alert(strs[i]); //分割后的字符输出 
					var s = strs[i].split(":");
					for (j=0;j<s.length ;j++ ){ 
						if("toSpvCashFlowApplyPkid" == s[j]){
							$("#toSpvCashFlowApplyPkid").val(s[j+1]);
						}
						if("ToSpvCashFlowPkid" == s[j])
							$("#ToSpvCashFlowPkid").val(s[j+1]);
						if("ToSpvReceiptPkid" == s[j])
							$("#ToSpvReceiptPkid").val(s[j+1]);
					}
				}
			}
			if(data.success){
				alert("保存数据成功！");
			}else{
				alert("数据保存出错!");
				//alert("数据保存出错!"+$("#toSpvCashFlowApplyPkid").val()+":"+$("#ToSpvCashFlowPkid").val()+":"+$("#ToSpvReceiptPkid").val());
			}
		},complete: function() { 
		},
		error : function(errors) {
			alert("数据保存出错!"+errors);
		}
	});
}

function cleanPkid(){
	$("#toSpvCashFlowApplyPkid").val("");
	$("#ToSpvCashFlowPkid").val("");
	$("#ToSpvReceiptPkid").val("")
}

//保存必填项
function checkFormSave(){
	
}
function rescCallbocak(){
	window.location.href = ctx+"/spv/spvList";
}




