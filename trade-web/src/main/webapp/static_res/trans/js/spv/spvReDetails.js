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
	$str+='		<p><input class="table_input boderbbt" type="text"placeholder="请输入银行卡号"  onKeypress="if (!(event.keyCode > 47 && event.keyCode < 58)) event.returnValue = false;" name="items['+thisIndex+'].payerAcc"></p>                                                                                   ';
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="items['+thisIndex+'].payerBank"></p>                                                                                  ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td class="text-left">                                                                                                                                                                     ';
	$str+='		<input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if (!(event.keyCode > 45 && event.keyCode < 58 &&event.keyCode !=47 ) ) event.returnValue = false;" name="items['+thisIndex+'].payerAmount">万元                                                                        ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<input class="table_input boderbbt" type="text" placeholder="请输入编号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="items['+thisIndex+'].receiptNo">                                                                                             ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<select name="items['+thisIndex+'].voucherNo" class="table-select boderbbt" onChange="this.value">                                                                                                                                ';
	$str+='			<option value="">请选择</option>                                                                                                                                                   ';
	$str+='			<option value="转账">转账</option>                                                                                                                                                 ';
	$str+='			<option value="刷卡">刷卡</option>                                                                                                                                                 ';
	$str+='			<option value="现金">现金</option>                                                                                                                                                 ';
	$str+='		</select>                                                                                                                                                                              ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
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
        done: function (e, data) {
        	console.log(JSON.stringify(data));
        }
    });

	cleanPkid();
}
var updFun22 = function(e) {
	var that = $(this).data('blueimp-fileupload')
			|| $(this).data('fileupload');
	that._resetFinishedDeferreds();
	that._transition($(this).find('.fileupload-progress'))
			.done(function() {
				that._trigger('started', e);
			});
};
//删除入账申请信息tr
function getDel(k){
    $(k).parents('tr').remove();
    cleanPkid();
}

//提交
function sumbitRe(){
	if(!confirm("是否确定提交申请！")){
	  return false;
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
			 window.location.href = ctx+"/task/myTaskList";
			/*if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错1:"+data.ajaxResponse.message);
			}*/
			// window.location.href = ctx+"/spv/task/cashflowIntApply/spvRecordShow?pkid=";
			
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
	/*if(!confirm("保存入账申请信息数据！")){
	  return false;
    }*/
//提交页面的参数
	 //保存必填项
	/*if(!checkFormSave()){
		  return false;
	  }*/
	
	
	var data = $("#teacForm").serialize();
	//console.log(params);
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
				 //保存附件
				/* if(!deleteAndModify()){
					  return false;
				  }*/
				 //window.opener.location.reload(); //刷新父窗口
			}
			if(data.success){
				alert("保存数据成功！");
			}else{
				alert("数据保存出错："+$("#toSpvCashFlowApplyPkid").val()+":"+$("#ToSpvCashFlowPkid").val()+":"+$("#ToSpvReceiptPkid").val());
			}
			//alert($("#toSpvCashFlowApplyPkid").val()+":"+$("#ToSpvCashFlowPkid").val()+":"+$("#ToSpvReceiptPkid").val());
		},complete: function() { 
		},
		error : function(errors) {
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




