
var handle = $("#handle").val();
var sum = parseInt($("#sum").val());
var trindex = 0;
var imageSum = 0;
var imageSumb = 0;
var index = 0;

//添加入账申请信息tr
function getTR(thisIndex){
	thisIndex = sum;
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
	$str+='         <label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:32px;width:100px;margin-bottom:-14px;cursor:pointer; background:url('+$("#ctx").val()+'/static/trans/img/bnt-flie.png) no-repeat; background-size: 38%;" onClick="$(\'#fileupload_'+thisIndex+'\').trigger(\'click\');"/>';
	$str+='		</span>                                                                                                                                                                                ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td> <div id="datepicker_'+thisIndex+'" class="input-medium date-picker input-daterange " data-date-format="yyyy-mm-dd">';
	$str+=' <input id="inputTime'+thisIndex+'" style="width:106px" name="items['+thisIndex+'].cashFlowCreateTime"class="form-control input-one" type="text" placeholder="入账日期"></div>' ;                                                                                                                                                                                     
	$str+='	</td> ';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+sum+')">添加</a>';
	$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>                                                                                                           ';
	$str+='</tr>                                                                                                                                                                                          ';
	
	$("#addTr").append($str);
	
	sum++;
	$("#sum").val(sum);
	
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
        	}
        },
        progressall: function (e, data) {
        	$('#progress').show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css('width',progress+'%').find("span").css('color','red').text(progress+'%');
            if(progress == 100){
                setTimeout($('#progress').fadeOut(2000));
            }
        }
    });
}

function getUploadImage(thisIndex,fileUrl,fileId,fileName){
	index++;
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = "<span><img id='image_"+index+"' src='"+fileUrl+"' style='width:0px;height:0px;display: none;' class='viewer-toggle'>";
	image += '<input type="hidden" name ="items['+thisIndex+'].fileId" value = "'+fileId+'" fileName="'+fileName+thisIndex+'"/>';
	image += '<input type="hidden" name ="items['+thisIndex+'].fileName" value = "'+fileName+'" />';
	image += "<button type='button' class='btn btn-sm btn-default' style='margin-bottom: 5px;margin-right:5px;padding: 0 8px;' onClick=\"showImg('#image_"+index+"')\">"+shortName+"<i class='icon iconfont icon_x' onClick='removeImg(this,event);'>&#xe60a;</i></button></span>";
	return image;
}
//删除入账申请信息tr
function getDel(k){
	if($("input[name$='payerName']").size()==1){
		window.wxc.alert("入账申请信息不能少于一行数据！");
		return false;
	}
    $(k).parents('tr').remove();
}
function removeImg(object,event){
	 $(object).parent().parent().remove();
	 $('.wrapper-content').viewer('destroy');
	 $('.wrapper-content').viewer();
	 event.stopPropagation();
}
function showImg(imgId){
	$(imgId).trigger("click");
}
//删除入账申请信息tr
function getDelHtml(k,pkid){
	
	if($("input[name$='payerName']").size()==1){
		window.wxc.alert("入账申请信息不能少于一行数据！");
		return false;
	}
	
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
	
	if(!checkSumbitHtml()){
		return;
	}
	
	window.wxc.confirm("是否确定重新提交申请？",{"wxcOk":function(){
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
			   	if(data.success){
			   		window.wxc.success("提交成功！",{"wxcOk":function(){
			   			window.opener.location.reload(); //刷新父窗口
					   	window.close(); //关闭子窗口.
			   		}}); 
				}else{
					window.wxc.error("提交失败！"+data.message); 
				}
				
			},complete: function() { 
			},
			error : function(errors) {
			}
			
		});
	}});
}
//得到页面数据 
function getFormData(){
	var data = $("#teacForm").serialize();
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
		 window.wxc.alert("请填写有效的付款人姓名！");
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
		 window.wxc.alert("请填写有效的付款人银行卡号！");
	    changeClass(payerAccEle);
		return false;
	 }
	var payerBankFlag = true;
	var payerBankEle;
	$("input[name$='payerBank']").each(function(i,e){
		if($(e).val() == null || $(e).val() == ''){
			payerBankFlag = false;
			payerBankEle = $(e);
			return false;
		}
	});
	 if(!payerBankFlag){
		 	window.wxc.alert("请填写有效的付款人银行名称！");
		    changeClass(payerAccEle);
			return false;
		 }
	
	var payerAmountFlag = true;
	var payerAmountEle;
	$("input[name$='payerAmount']").each(function(i,e){
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
	 
	var receiptNoFlag = true;
	var receiptNoEle;
	$("input[name$='receiptNo']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '') || ($(e).val() != null && $(e).val() != '' && !isNumber3($(e).val()))){
			receiptNoFlag = false;
			receiptNoEle = $(e);
			return false;
		}
	});
	 if(!receiptNoFlag){
		 	window.wxc.alert("请填写有效的贷记凭证编号！");
		    changeClass(receiptNoEle);
			return false;
		 }
	 
	 var reg = /^[0-9]*$/;
		if(receiptNoArray.length<0){
			window.wxc.alert("贷记凭证编号不能为空！");
			return  false;	
		}
			
		for(var i=0; i<receiptNoArray.length; i++){	
			if($.trim(receiptNoArray[i].value).length<1){
				window.wxc.alert("贷记凭证编号不能为空！");
				return  false;
			}
			for(var j=i+1; j<receiptNoArray.length ;j++){
					if(receiptNoArray[i].value == receiptNoArray[j].value){
						theSameFlag=false;
						window.wxc.alert("贷记凭证编号不能重复！");
					}
					if(theSameFlag==false){
						return  false;
					}
				}
			if(theSameFlag==false){
				return  false;
			}
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
		window.wxc.alert("请选择有效的付款方式！");
	    changeClass(voucherNoEle);
		return false;
	 }
	
	var imgFlag = true;
    $("td[id^='td_file']").each(function(i,e){
    	var length = $(e).find("img").length;
    	if(length == 0){
    		imgFlag = false;
    		return false;
    	}
    });
    
    if(!imgFlag){
    	window.wxc.alert("需要上传至少一张附件！");
    	return false;
    }
	
	var cashFlowCreateTimeFlag = true;
	var cashFlowCreateTimeEle;
	$("input[name$='cashFlowCreateTime']").each(function(i,e){
		if(($(e).val() == null || $(e).val() == '')){
			cashFlowCreateTimeFlag = false;
			cashFlowCreateTimeEle = $(e);
			return false;
		}
	});
	if(!cashFlowCreateTimeFlag){
		window.wxc.alert("请选择有效的入账时间！");
		changeClass(cashFlowCreateTimeEle);
		return false;
	}
	return theSameFlag;
}

function checkBankNoAndPayerAmount(){
	var regForBankNo = /^[0-9]*$/;
	var regForPayerAmount = /^\d+(\.\d+)?$/;
	var bankNoArray = $(".forBankNo");
	var PayerAmountArray = $(".forPayerAmount");
	var flag = true;
	 $.each(bankNoArray,function(i, item) {
			if (item.value != '') {				
				if(!regForBankNo.test(item.value.trim())){
					window.wxc.alert("银行卡号只能由数字组成！");
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
				if(!regForPayerAmount.test(item.value.trim())){
					window.wxc.alert("入职金额只能由数字和小数点组成！");
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
		window.wxc.alert("请先上传图片成功后再提交");
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
//银行验证(汉字和英文大小写)
function isName2(name){
	name = name.replace(/\s/g,"");//去除中间空格
	reg = /((^[\u4E00-\u9FA5]{1,30}$)|(^[a-zA-Z]+[\s\.]?([a-zA-Z]+[\s\.]?){0,4}[a-zA-Z]$))/;
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
//凭证验证 数据字母
function isNumber3(num){
	var reg=/^[A-Za-z0-9]+$/;
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

