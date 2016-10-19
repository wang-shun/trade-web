$(function(){
	//$("#addTr").html(getTR(0));
	//alert($("#select_direction").val());
	//$("#select_direction   option[value='"+province_2+"']").attr("selected",true);
	
});
$(window).load(function() {
	//alert($("#select_direction").val());
});
var handle = $("#handle").val();
var trindex = 0;
var imageSum = 0;
var imageSumb = 0;

//添加入账申请信息tr
function getTR(thisIndex){
	var nextIndex = thisIndex+1;
	var  $str='';
	$str+='<tr>                                                                                                                                                                                           ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<input class="table-input-one boderbbt" type="text" placeholder="请输入姓名" name="items['+thisIndex+'].payerName">                                                                                         ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<p><input class="table_input boderbbt" type="text"placeholder="请输入银行卡号"  onKeypress="if (!(event.keyCode > 47 && event.keyCode < 58)) event.returnValue = false;" name="items['+thisIndex+'].payerAcc"></p>                                                                                   ';
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="items['+thisIndex+'].payerBank"></p>                                                                                  ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td class="text-left">                                                                                                                                                                     ';
	$str+='		<input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if (!(event.keyCode > 45 && event.keyCode < 58 &&event.keyCode !=47 ) ) event.returnValue = false;" name="items['+thisIndex+'].payerAmount">万元                                                                        ';
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
	$str+='	<td> <div id="datepicker_'+thisIndex+'" class="input-medium date-picker input-daterange " data-date-format="yyyy-mm-dd">';
	$str+=' <input id="inputTime'+thisIndex+'" style="width:106px" name="items['+thisIndex+'].cashFlowCreateTime"class="form-control input-one" type="text" placeholder="入账日期"></div>' ;                                                                                                                                                                                     
	$str+='	</td> ';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+nextIndex+')">添加</a>';
	//if(thisIndex > 0){
		$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>                                                                                                           ';
	//}
	$str+='</tr>                                                                                                                                                                                          ';
	
	$("#addTr").append($str);
	render_fileupload(thisIndex);
	   // 日期控件
	$("#datepicker_"+thisIndex).datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked'
	})
}

function render_fileupload(thisIndex){
	$('#fileupload_'+thisIndex).fileupload({
		acceptFileTypes:'/(gif|jpg|jpeg|bmp|png|tif|tiff)/i',
		autoUpload: true,
        dataType: 'json',
        add:function(e,data){
        	var fileName = data.files[0].name;
        	if($("input[fileName='"+fileName+thisIndex+"']").size()==0){
        		data.submit();
        		imageSum ++;//记录上传附件的个数
        	}
        },
        done: function (e, data) {
        	if(data.result){
            	var fileId =  data.result.files[0].id;
            	var fileUrl = data.result.files[0].url;
            	var fileName = data.result.files[0].name;
            	var image = getUploadImage(thisIndex,fileUrl,fileId,fileName);
            	var $image = $(image);
            	$('#td_file'+thisIndex).prepend($image);
            	imageSumb++;////记录完成上传附件的个数
            	//$image.responsivegallery();
        	}
        }
    });
}

function getUploadImage(thisIndex,fileUrl,fileId,fileName){
	
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = '<img id="image_'+thisIndex+'" src="'+fileUrl+'" style="width:0px;height:0px;display: none;" class="viewer-toggle">';
	image += '<input type="hidden" name ="items['+thisIndex+'].fileId" value = "'+fileId+'" fileName="'+fileName+'"/>';
	image += '<input type="hidden" name ="items['+thisIndex+'].fileName" value = "'+fileName+'" />';
	image += '<button type="button" class="btn btn-sm btn-default" onClick="$(\'#image_'+thisIndex+'\').trigger(\'click\');">'+shortName+'<i class="icon iconfont icon_x">&#xe60a;</i></button>';
	return image;
}
//删除入账申请信息tr
function getDel(k){
    $(k).parents('tr').remove();
}
//删除入账申请信息tr
function getDelHtml(k,pkid){
	if(!confirm("是否删除！")){
		  return false;
	    }
	var data = {pkid:pkid};
	var url = ctx+"/spv/task/cashflowIntApply/dealAppDelete";
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
			$(k).parents('tr').remove();
			window.location.reload(); 
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}
//提交
function sumbitRe(){
	
	if(!confirm("是否确定重新提交申请！")){
		  return false;
	}
		
	if(!checkSumbitHtml()){
		return;
	}
	
	$('#chargeInAppr').val(true);
	//提交页面的参数
	var data = $("#teacForm").serialize();
	var url = ctx+"/spv/task/cashflowIntApply/deal";
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
			window.location.href = ctx+"/spv/spvList";
			/*
			alert(JSON.stringify(data));
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错1:"+data.ajaxResponse.message);
			}*/
			
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
	
	alert("保存数据成功！");
	rescCallbocak();
	
	$('#chargeInAppr').val(false);
	//提交页面的参数
	var data = $("#teacForm").serialize();
	//console.log(params);
	var url = ctx+"/spv/cashflowApply/deal";
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
			window.location.href = ctx+"/spv/spvList";
			//alert("保存数据成功！");
			/*alert(JSON.stringify(data));
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错1:"+data.ajaxResponse.message);
			}*/
			
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}
function rescCallbocak(){
	   window.opener.location.reload(); //刷新父窗口
	   window.close(); //关闭子窗口.
	}


function checkReceiptNo(){
	var theSameFlag = true;
	var receiptNoArray = new Array();
		receiptNoArray = $(".forvalue");
	
	var payerNameFlag = true;
	var payerNameEle;
	$("input[name$='payerName']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isName($(e).val()))){
			payerNameFlag = false;
			payerNameEle = $(e);
			 return false;
			 }
		});
	
	 if(!payerNameFlag){
    	alert("请填写有效的付款人姓名！");
	    changeClass(payerNameEle);
		return false;
	 }
	
	var payerAccFlag = true;
	var payerAccEle;
	$("input[name$='payerAcc']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber2($(e).val()))){
			payerAccFlag = false;
			payerAccEle = $(e);
			return false;
		}
	});
	 if(!payerAccFlag){
    	alert("请填写有效的付款人银行卡号！");
	    changeClass(payerAccEle);
		return false;
	 }
	var payerBankFlag = true;
	var payerBankEle;
	$("input[name$='payerBank']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isName($(e).val()))){
			payerBankFlag = false;
			payerBankEle = $(e);
			return false;
		}
	});
	 if(!payerBankFlag){
	    	alert("请填写有效的付款人银行名称！");
		    changeClass(payerAccEle);
			return false;
		 }
	
	var payerAmountFlag = true;
	var payerAmountEle;
	var sumAmount = 0;
	$("input[name$='payerAmount']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber($(e).val()))){
			payerAmountFlag = false;
			payerAmountEle = $(e);
			
			return false;
		}else{
			sumAmount = accAdd(sumAmount,$(e).val());
		}
		
	});
	if(!payerAmountFlag){
	    	alert("请填写有效的金额！");
		    changeClass(payerAmountEle);
			return false;
	}
	
    var amount = $("#amount").attr("value");
    if(parseFloat(sumAmount) > parseFloat(amount)){
    	alert("入账金额不能大于监管金额！");
    	return false;
    }
	 
	var receiptNoFlag = true;
	var receiptNoEle;
	$("input[name$='receiptNo']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber2($(e).val()))){
			receiptNoFlag = false;
			receiptNoEle = $(e);
			return false;
		}
	});
	 if(!receiptNoFlag){
	    	alert("请填写有效的贷记凭证编号！");
		    changeClass(receiptNoEle);
			return false;
		 }
	var voucherNoFlag = true;
	var voucherNoEle;
	$("select[name$='voucherNo']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '')){
			voucherNoFlag = false;
			voucherNoEle = $(e);
			return false;
		}
	});
	 if(!voucherNoFlag){
	    	alert("请选择有效的付款方式！");
		    changeClass(voucherNoEle);
			return false;
		 }
	 
		
	var reg = /^[0-9]*$/;
	if(receiptNoArray.length<0){
		alert("贷记凭证编号不能为空！");
		return  false;	
	}
		
	for(var i=0; i<receiptNoArray.length; i++){	
		if($.trim(receiptNoArray[i].value).length<1){
			alert("贷记凭证编号不能为空！");
			return  false;
		}
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
function checkSumbitHtml(){
	
	if(imageSumb <0 || imageSum != imageSumb){			//附件
		alert("请先上传图片成功后再提交");
		return false;
	}
	if(!checkReceiptNo()){				//验证凭证编号不能重复和只能为数字
		return;
	}
	if(!checkBankNoAndPayerAmount()){	//银行卡号、金额等
		return;
	}
	
	return true;
}

function changeClass(object){
	$(object).focus();
	$(object).addClass("borderClass").blur(function(){
		$(this).removeClass("borderClass");
	});	;
}

/**************************************验证************************************************/
//姓名验证(汉字和英文大小写)
function isName(name){
	name = name.replace(/\s/g,"");//去除中间空格
	reg = /((^[\u4E00-\u9FA5]{1,5}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
	if (!reg.test(name)) {
       return false; 
   }
   return true;
}
//金额验证(两位小数)
function isNumber(num){
	var reg=/^([1-9]{1}\d*|0)(\.\d{1,2})?$/;
	if(!reg.test(num)){
		return false;
	}
	return true;
}
//金额验证(整数)
function isNumber2(num){
	var reg=/^[1-9]{1}\d*$/;
	if(!reg.test(num)){
		return false;
	}
	return true;
}
/*****************************************************************************************/
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
var r1,r2,m;
try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
m=Math.pow(10,Math.max(r1,r2));
return ((arg1*m+arg2*m)/m).toFixed(2);
}

