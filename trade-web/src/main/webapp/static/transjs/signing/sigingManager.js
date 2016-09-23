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
  	  
  	var orgId = $.trim($("select[name='rmSignRoom.orgId']").val());
  	var orgName = $("select[name='rmSignRoom.orgId']").find("option:selected").text();
  	
  	var roomNo = $.trim($("input[name='rmSignRoom.roomNo']").val());
  	var numbeOfAccommodatePeople = $.trim($("input[name='rmSignRoom.numbeOfAccommodatePeople']").val());
  	var roomType = $.trim($("input[name='rmSignRoom.roomType']").val());
  	var remark = $.trim($("textarea[name='rmSignRoom.remark']").val());
  	
  	var stragegyWeekVal=0 ;
  	$('input[type=checkbox][name=items]').each(function(){
        if($(this).prop("checked") == true && $(this).prop("id")!='CheckedAll'){
        	stragegyWeekVal |= 1<<$(this).val();
        }
    });
  	
  	var roomStatus = $.trim($("input[name='rmSignRoom.roomStatus']").val());
  	var pkid = $("#pkid").val();
  	
   	  $.ajax({
     		url:ctx+"/signroom/addOrUpdateSignRoom",
     		method:"post",
     		dataType:"json",
     		data : {
     			'orgId' : orgId,
     			'orgName' : orgName,
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
						alert(data.message);
						window.location.href = ctx+"/signroom/signingManage";
					}else{
						alert(data.message);
					}
				},		
			error : function(errors) {
					alert("数据保存出错:"+JSON.stringify(errors));
				}	 
      });
   });
    
    $("#addSignRoom").click(function(){
    	$("#title").html("添加签约室");
    	$("#btnName").html("添加");
    	
    	$("#pkid").val("");
    	$("select[name='rmSignRoom.orgId']").val("");
      	$("input[name='rmSignRoom.roomNo']").val("");
      	$("input[name='rmSignRoom.numbeOfAccommodatePeople']").val("");
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
	if($("select[name='rmSignRoom.orgId']").val()==''){
		alert("请选择签约中心！");
		$("select[name='rmSignRoom.orgId']").focus();
		return false;
	}
	if($.trim($("input[name='rmSignRoom.roomNo']").val())==''){
		alert("请输入房间编号！");
		$("input[name='rmSignRoom.roomNo']").focus();
		return false;
	}
	var numbeOfAccommodatePeople = $("input[name='rmSignRoom.numbeOfAccommodatePeople']").val();
	if($.trim(numbeOfAccommodatePeople)==''){
		alert("请输入可容纳人数！");
		$("input[name='rmSignRoom.numbeOfAccommodatePeople']").focus();
		return false;
	}else if(!checkZhengShu($.trim(numbeOfAccommodatePeople))){
		alert("请输入正确的可容纳人数！");
		$("input[name='rmSignRoom.numbeOfAccommodatePeople']").focus();
		return false;
	}
	
	var flag=false;
    $('input[type=checkbox][name=items]').each(function(){
        if($(this).prop("checked") == true){
            flag = true;
        }
    });
	if(!flag){
		alert("请选择开放策略！");
		return false;
	}
	return true;
	
}


function getParamsValue() {
	
	
	var roomType = $('#roomTypeSlot').val();
	var roomStatus = $('#roomStatus').val();
	var orgId = $('#orgId').val();
	
	//设置查询参数
	var params = {
			orgId : orgId,
			roomType : roomType,
			roomStatus : roomStatus
	};
	return params;
}

function ajaxSubmit() {
	$("#schedualable tbody tr").remove();
	var params = getParamsValue();
	$.ajax({
		url:ctx+"/signroom/signRoomShedualList",
		method:"post",
		dataType:"json",
		data : params,
		success:function(data){
			console.log(data);
			if(data.success){
				var th='';
				
				 for(var i=0;i<data.content.length;i++){
					 var roomType = $.trim(data.content[i].roomType)=='0'?'普通房间':'机动房间';
					 var roomStatus = $.trim(data.content[i].roomStatus)=='0'?'关闭':'开放';
					   th+="<tr><td><p class='big'>"+data.content[i].roomNo+"</p></td><td><p><i class='sign_blue'>"+roomType+"</i></p></td><td><p class='big'>"+data.content[i].orgName+"</p></td><td><p class='big'>"+data.content[i].numbeOfAccommodatePeople+"</p></td>";
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
					   th+="<td class='text-center'><button type='button' class='btn btn-success mr5' data-toggle='modal' data-target='#myModal' onclick=\"updateSignRoom('"+data.content[i].orgId+"','"+data.content[i].roomNo+"','"+data.content[i].numbeOfAccommodatePeople+"','"+data.content[i].roomType+"','"+data.content[i].remark+"','"+data.content[i].roomStatus+"','"+week+"','"+data.content[i].pkid+"')\">修改</button><button type='button' onclick=\"deleteSignRoom('"+data.content[i].pkid+"','"+data.content[i].stragegyPkid+"')\" class='btn btn-grey'>删除</button></td></tr>";
				 }
				
			   $("#schedualable tbody").append(th);
		    }else{
		    	alert(data.message);
		    }
		}
    });
}


function updateSignRoom(orgId,roomNo,numbeOfAccommodatePeople,roomType,remark,roomStatus,week,pkid){
	$("#title").html("修改签约室");
	$("#btnName").html("修改");
	$("#pkid").val(pkid);
	$("select[name='rmSignRoom.orgId']").val(orgId);
  	$("input[name='rmSignRoom.roomNo']").val(roomNo);
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
						alert(data.message);
						window.location.href = ctx+"/signroom/signingManage";
					}else{
						alert(data.message);
					}
				},		
			error : function(errors) {
					alert("数据保存出错:"+JSON.stringify(errors));
				}	 
    });
	
}



/**
 * 校验正整数
 */
function checkZhengShu(num)  
{  
     var te= /^[1-9]+[0-9]*]*$/;
     return te.test(num);
}



