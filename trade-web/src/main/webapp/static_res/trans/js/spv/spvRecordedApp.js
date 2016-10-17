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
	$str+='	<td> <div id="datepicker_0" class="input-medium date-picker input-daterange " data-date-format="yyyy-mm-dd">';
	$str+=' <input id="inputTime'+thisIndex+'" style="width:106px" name="items['+thisIndex+'].inputTime"class="form-control input-one" type="text" value=""placeholder="入账日期"></div>' ;                                                                                                                                                                                     
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
	$('#chargeInAppr').val(true);
	//提交页面的参数
	var data = $("#teacForm").serialize();
	/*var a =1;
	return;*/
	//console.log(params);
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

