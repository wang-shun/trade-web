$(function () {
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }

    });
    ajaxSubmit();
    $("#searchBtn").click(function(){
    	ajaxSubmit();
    });
    
    $("#clearBtn").click(function(){
    	$('#roomTypeSlot').val("");
    	$('#useStatus').val("");
    });
    
    $("#saveBtn").click(function(){
  	  //添加时校验
  	  if(!checkFormSave()){
  		  return;
  	  }
  	  
  	var centerId = $.trim($("select[name='rmSignRoom.centerId']").val());
  	var centerName = $("select[name='rmSignRoom.centerId']").find("option:selected").text();
  	
  	var roomNo = $.trim($("input[name='rmSignRoom.roomNo']").val());
  	var numbeOfAccommodatePeople = $.trim($("input[name='rmSignRoom.numbeOfAccommodatePeople']").val());
  	var roomType = 0;
  	$("input[name='rmSignRoom.roomType']").each(function(){
  		if($(this).prop("checked")){
  			roomType = $(this).prop("value");
  		}
     });
  	
  	var remark = $.trim($("textarea[name='rmSignRoom.remark']").val());
  	
  	var stragegyWeekVal=0 ;
  	$('input[type=checkbox][name=items]').each(function(){
        if($(this).prop("checked") == true && $(this).prop("id")!='CheckedAll'){
        	stragegyWeekVal |= 1<<$(this).val();
        }
    });
  	
  	var roomStatus = 1;
  	$("input[name='rmSignRoom.roomStatus']").each(function(){
  		if($(this).prop("checked")){
  			roomStatus = $(this).prop("value");
  		}
     });
  	var pkid = $("#pkid").val();
  	
   	  $.ajax({
     		url:ctx+"/signroom/addOrUpdateSignRoom",
     		method:"post",
     		dataType:"json",
     		data : {
     			'tradeCenterId' : centerId,
     			'tradeCenter' : centerName,
     			'roomNo' : roomNo,
     			'numbeOfAccommodatePeople' : numbeOfAccommodatePeople,
     			'roomType' : roomType,
     			'remark' : remark,
     			'roomStatus' : roomStatus,
     			'stragegyWeekVal' : stragegyWeekVal,
     			'pkid' : pkid
     			
     		},	 
			success : function(data) {   
					if(data.success){
						window.wxc.success(data.message);
						window.location.href = ctx+"/signroom/signingManage";
					}else{
						window.wxc.error(data.message);
					}
				},		
			error : function(errors) {
					window.wxc.error("数据保存出错:"+JSON.stringify(errors));
				}	 
      });
   });
    
    $("#addSignRoom").click(function(){
    	$("#title").html("添加签约室");
    	$("#btnName").html("添加");
    	
    	$("#pkid").val("");
    	$("select[name='rmSignRoom.centerId']").val("");
      	$("input[name='rmSignRoom.roomNo']").val("");
      	$("input[name='rmSignRoom.numbeOfAccommodatePeople']").val("");
      	
      	$("input[name='rmSignRoom.roomNo']").removeAttr("readonly");
      	
      	$("input[name='rmSignRoom.roomType']").each(function(){
           if($(this).prop("value")=="0"){
                $(this).prop("checked",true);
            }else{
            	$(this).prop("checked",false);
            }
        });
      	
      	$("textarea[name='rmSignRoom.remark']").val("");
      	
      	$('input[type=checkbox][name=items]').each(function(){
      		$(this).prop("checked",false);	
        });
      	
      	$("input[name='rmSignRoom.roomStatus']").each(function(){
           if($(this).prop("value")=="1"){
                $(this).prop("checked",true);
            }else{
            	$(this).prop("checked",false);
            }
        });
    	
    });
    
    
})

function checkFormSave(){
	if($("select[name='rmSignRoom.centerId']").val()==''){
		window.wxc.alert("请选择签约中心！");
		$("select[name='rmSignRoom.centerId']").focus();
		return false;
	}
	var roomNo = $.trim($("input[name='rmSignRoom.roomNo']").val());
	if(roomNo==''){
		window.wxc.alert("请输入房间编号！");
		$("input[name='rmSignRoom.roomNo']").focus();
		return false;
	}
	if(isChineseChar(roomNo)){
		window.wxc.alert("请输入正确的房间编号");
		$("input[name='rmSignRoom.roomNo']").focus();
		return false;
	}
	
	var numbeOfAccommodatePeople = $("input[name='rmSignRoom.numbeOfAccommodatePeople']").val();
	if($.trim(numbeOfAccommodatePeople)==''){
		window.wxc.alert("请输入可容纳人数！");
		$("input[name='rmSignRoom.numbeOfAccommodatePeople']").focus();
		return false;
	}else if(!checkZhengShu($.trim(numbeOfAccommodatePeople))){
		window.wxc.alert("请输入正确的可容纳人数！");
		$("input[name='rmSignRoom.numbeOfAccommodatePeople']").focus();
		return false;
	}
	
	var remark = $.trim($("textarea[name='rmSignRoom.remark']").val());
	if(remark != ''){
		if(remark.length<2 || remark.length>20){
			window.wxc.alert("请输入2~20个字备注！");
			$("textarea[name='rmSignRoom.remark']").focus();
			return false;
		}
	}
	
	var flag=false;
    $('input[type=checkbox][name=items]').each(function(){
        if($(this).prop("checked") == true){
            flag = true;
        }
    });
	if(!flag){
		window.wxc.alert("请选择开放策略！");
		return false;
	}
	return true;
	
}


function getParamsValue() {
	
	
	var roomType = $('#roomTypeSlot').val();
	var roomStatus = $('#roomStatus').val();
	var centerId = $('#centerId').val();
	
	//设置查询参数
	var params = {
			centerId : centerId,
			roomType : roomType,
			roomStatus : roomStatus
	};
	return params;
}

function ajaxSubmit() {
	$("#schedualable tbody tr").remove();
	var params = getParamsValue();
	$.ajax({
		async: true,
		url:ctx+"/signroom/signRoomShedualList",
		method:"post",
		dataType:"json",
		data : params,
		beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
		success:function(data){
			
			if(data.success){
				var th='';
				
				 for(var i=0;i<data.content.length;i++){
					 var roomType = $.trim(data.content[i].roomType)=='0'?'普通房间':'机动房间';
					 var roomStatus = $.trim(data.content[i].roomStatus)=='0'?'关闭':'开放';
					 th+="<tr><td><p class='big'>"+data.content[i].roomNo+"</p></td>";
					 if($.trim(data.content[i].roomType)=='0'){
						 th+="<td><p><i class='sign_blue'>"+roomType+"</i></p></td>";
					 }else{
						 th+="<td><p><i class='sign_yellow'>"+roomType+"</i></p></td>";
					 }
					   th+="<td><p class='big'>"+data.content[i].tradeCenter+"</p></td><td><p class='big'>"+data.content[i].numbeOfAccommodatePeople+"</p></td>";
					   th+="<td>";
					   var week='';
					   if(data.content[i].weeks[1]){
						   th+="<span class='blue-block'>一</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>一</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[2]){
						   th+="<span class='blue-block'>二</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>二</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[3]){
						   th+="<span class='blue-block'>三</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>三</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[4]){
						   th+="<span class='blue-block'>四</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>四</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[5]){
						   th+="<span class='blue-block'>五</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>五</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[6]){
						   th+="<span class='blue-block'>六</span>";
						   week+='true,';
					   }else{
						   th+="<span class='blue-block grey-block'>六</span>";
						   week+='false,';
					   }
					   if(data.content[i].weeks[7]){
						   th+="<span class='blue-block'>日</span>";
						   week+='true';
					   }else{
						   th+="<span class='blue-block grey-block'>日</span>";
						   week+='false';
					   }
					   th+= "</td>";
					   th+="<td><p class='big'>"+roomStatus+"</p></td><td><p class='big'>"+data.content[i].remark+"</p></td>";
					   th+="<td class='text-center'><button type='button' class='btn btn-success mr5' data-toggle='modal' data-target='#myModal' onclick=\"updateSignRoom('"+data.content[i].tradeCenterId+"','"+data.content[i].roomNo+"','"+data.content[i].numbeOfAccommodatePeople+"','"+data.content[i].roomType+"','"+data.content[i].remark+"','"+data.content[i].roomStatus+"','"+week+"','"+data.content[i].pkid+"')\">修改</button><button type='button' onclick=\"deleteSignRoom('"+data.content[i].pkid+"','"+data.content[i].stragegyPkid+"')\" class='btn btn-grey'>删除</button></td></tr>";
				 }
				
			   $("#schedualable tbody").append(th);
		    }else{
		    	window.wxc.error(data.message);
		    }
			$.unblockUI();
		},
		error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
    });
}


function updateSignRoom(centerId,roomNo,numbeOfAccommodatePeople,roomType,remark,roomStatus,week,pkid){
	$("#title").html("修改签约室");
	$("#btnName").html("修改");
	$("#pkid").val(pkid);
	$("select[name='rmSignRoom.centerId']").val(centerId);
  	$("input[name='rmSignRoom.roomNo']").val(roomNo);
  	$("input[name='rmSignRoom.roomNo']").prop("readonly","readonly");
  	$("input[name='rmSignRoom.numbeOfAccommodatePeople']").val(numbeOfAccommodatePeople);
  	
  	$("input[name='rmSignRoom.roomType']").each(function(){
       if($(this).prop("value")==roomType){
            $(this).prop("checked",true);
        }
    });
  	
  	$("textarea[name='rmSignRoom.remark']").val(remark);
  	
  	var weeks = week.split(",");
  	
  	$('input[type=checkbox][name=items]').each(function(){
        if($(this).prop("id")!='CheckedAll'){
        	var num = $(this).prop("value");
      		if(weeks[num-1]=="true"){
      			$(this).prop("checked",true);	
      		}else{
      			$(this).prop("checked",false);	
      		}
        }
    });
  	
  	
  	$("input[name='rmSignRoom.roomStatus']").each(function(){
       if($(this).prop("value")==roomStatus){
            $(this).prop("checked",true);
        }
    });
  	
	
}

function deleteSignRoom(pkid,stragegyPkid){
	window.wxc.confirm("是否确定解除？",{"wxcOk":function(){
		$.ajax({
	   		url:ctx+"/signroom/deleteSignRoom",
	   		method:"post",
	   		dataType:"json",
	   		data : {
	   			'pkid' : pkid,
	   			'stragegyPkid' : stragegyPkid
	   		},	 
				success : function(data) {   
						if(data.success){
							window.wxc.success(data.message);
							window.location.href = ctx+"/signroom/signingManage";
						}else{
							window.wxc.error(data.message);
						}
					},		
				error : function(errors) {
					window.wxc.error("数据保存出错:"+JSON.stringify(errors));
					}	 
	    });
	}});
}



/**
 * 校验正整数
 */
function checkZhengShu(num)  
{  
     var te= /^[1-9]+[0-9]*]*$/;
     return te.test(num);
}

/**
 *校验中文
 */
function isChineseChar(str){   
   var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
   return reg.test(str);
}


