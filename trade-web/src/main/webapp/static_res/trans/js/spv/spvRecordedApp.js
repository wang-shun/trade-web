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
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行卡号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="items['+thisIndex+'].payerAcc"></p>                                                                                   ';
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="items['+thisIndex+'].payerBank"></p>                                                                                  ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td class="text-left">                                                                                                                                                                     ';
	$str+='		<input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if ((event.keyCode > 32 && event.keyCode < 45) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="items['+thisIndex+'].payerAmount">万元                                                                        ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<input class="table_input boderbbt" type="text" placeholder="请输入编号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="items['+thisIndex+'].receiptNo">                                                                                             ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	$str+='		<select name="items['+thisIndex+'].voucherNo" class="table-select boderbbt">                                                                                                                                ';
	$str+='			<option value="">请选择</option>                                                                                                                                                   ';
	$str+='			<option value="">转账凭证</option>                                                                                                                                                 ';
	$str+='		</select>                                                                                                                                                                              ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td>                                                                                                                                                                                       ';
	//$str+='		<a class="response" href="../static/trans/img/uplody01.png" title="凭证3"><button type="button" class="btn btn-sm btn-default">凭证3<i class="icon iconfont icon_x"></i></button></a>';
	//$str+='		<a class="response" href="../static/trans/img/uplody02.png" title="凭证4"><button type="button" class="btn btn-sm btn-default">凭证4<i class="icon iconfont icon_x"></i></button></a>';
	$str+='		<span class="btn_file">                                                                                                                                                                ';
	$str+='			<input type="file" class="file">                                                                                                                                                   ';
	$str+='			<img class="bnt-flie" src="http://trade.centaline.com:8083/trade-web/static/trans/img/bnt-flie.png" alt="">                                                                        ';
	$str+='		</span>                                                                                                                                                                                ';
	$str+='	</td>                                                                                                                                                                                      ';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+nextIndex+')">添加</a>';
	if(thisIndex > 0)
	$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>                                                                                                           ';
	$str+='</tr>                                                                                                                                                                                          ';
	$("#addTr").append($str);
}
//删除入账申请信息tr
function getDel(k){
    $(k).parents('tr').remove();
}
//提交
function sumbitRe(){
	
	
	//提交页面的参数
	var data = getFormData();
	//console.log(params);
	var url = ctx+"/spv/task/cashflowIntApply/deal";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data : {handle:handle,taskId:$("#taskId").val(),chargeInAppr:true},
		beforeSend:function(){  
         },
		success : function(data) {
			 //window.location.href = ctx+"/spv/spvList";
			 //window.location.href = ctx+"/spv/task/cashflowIntApply/spvRecordShow?pkid=";
			
			alert(JSON.stringify(data));
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错1:"+data.ajaxResponse.message);
			}
			
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
	
	//提交页面的参数
	var data = getFormData();
	//console.log(params);
	var url = ctx+"/spv/cashflowApply/deal";
	$.ajax({
		cache : false,
		async : false,//false同步，true异步
		type : "POST",
		url : url,
		dataType : "json",
		data : {handle:handle},
		beforeSend:function(){  
         },
		success : function(data) {
			//alert("保存数据成功！");
			alert(JSON.stringify(data));
			if(data.ajaxResponse.success){
				if(!handle){
					alert("流程开启成功！");
				}else{
					alert("任务提交成功！");
				}
			}else{
				alert("数据保存出错1:"+data.ajaxResponse.message);
			}
			
		},complete: function() { 
		},
		error : function(errors) {
		}
		
	});
}





