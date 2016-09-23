$(function () {
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }

    });
    ajaxSubmit(0);
    $("#searchBtn").click(function(){
    	ajaxSubmit(1);
    });
    //清空条件
    $("#clearBtn").click(function(){
    	$('#roomTypeSlot').val("");
    	$('#useStatus').val("");
    });
    
  //产证地址文本框失去焦点获取对应的caseCode
	$("#propertyAddress").blur(function(){
		var propertyAddress = this.value;
		
		if(propertyAddress != ""){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				dataType:"text",
				url:ctx+"/mobile/reservation/getCaseCodeByPropertyAddr",
				data: {propertyAddress:propertyAddress},
				success:function(data){
					$("#caseCode").val(data);
				}
			});
		}
	});
	
	//保存 临时分配数据
	$("#saveBtn").click(function(){
	  	  //添加时校验
	  	  if(!checkFormSave()){
	  		  return;
	  	  }
	  	 return; 
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
	     		url:ctx+"/signroom/addReservation",
	     		method:"post",
	     		dataType:"json",
	     		data : { resType : '0',
	     			     resPersonId : agentCode,
	     			     caseCode : caseCode,
	     			     propertyAddress : propertyAddress,
	     			     numberOfParticipants : numberOfPeople,
	     			     transactItemCode : transactItem,
	     			     specialRequirement : specialRequirement,
	     			     orgId : orgId,
	     			     selDate : selDate,
	     			     bespeakTime:bespeakTime},	 
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
    
    
    
})
//选取营业部经纪人
function chooseManager(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();
	userSelect({
		startOrgId : '1D29BB468F504774ACE653B946A393EE',
		expandNodeId : '1D29BB468F504774ACE653B946A393EE',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : 'JWYGW',
		callBack : signRoomSelectUserBack
	});
}

//选取人员的回调函数
function signRoomSelectUserBack(array) {
	console.log(array);
	if (array && array.length > 0) {
		$("#jjrName").val(array[0].username);
		$("#jjrName").attr('hVal', array[0].userId);

	} else {
		$("#jjrName").val("");
		$("#jjrName").attr('hVal', "");
	}
	
	var agentCode = $("#jjrName").attr('hVal');
	   //文本框自动填充
	   $("#propertyAddress").autocomplete({
		 maxHeight:300,
		 source: function(request, response) {
			 $.ajax({
				 url: ctx + "/mobile/reservation/getPropertyAddressList",
				 dataType: "json",
				 data: {
				 inputValue: $("#propertyAddress").val(),
				 agentCode : 'E39F5661B6614F968F27E7BD24BA324A'
			 },
			 success: function(data) {
					 response($.map(data, function(item) {
						 return {
						 label: item.propertyAddress,
						 value: item.propertyAddress,
						 caseCode: item.caseCode
					 }
			 	}));
			 }
		 });
		 }
		 });
	
}

function getParamsValue() {
	
	var curDate = $('#curDate').val();
	var roomType = $('#roomTypeSlot').val();
	var useStatus = $('#useStatus').val();
	
	//设置查询参数
	var params = {
			curDate : curDate,
			roomType : roomType,
			useStatus : useStatus
	};
	return params;
}

function ajaxSubmit(obj) {
	$("#signRoomTable tbody tr").remove();
	var params = getParamsValue();
	$.ajax({
		url:ctx+"/signroom/generatePageDate",
		method:"post",
		dataType:"json",
		data : params,
		success:function(data){
			if(data.success){
				var th='';
				if(obj==0){
					 for(var i=0;i<data.content.timeslots.length;i++){
						   th+="<th>"+data.content.timeslots[i]+"</th>";
					   }
					   $("#signRoomTable thead tr").append(th);
					   th='';
				}
			   for(var i=0;i<data.content.signRooms.length;i++){
				   var roomType = $.trim(data.content.signRooms[i].roomType)=='0'?'普通房间':'机动房间';
				  th += "<tr><td><p class='big'>"+data.content.signRooms[i].roomNo+"<em class='yellow_bg ml5'>"+roomType+"</em></p></td><td><p class='big'>"+data.content.signRooms[i].orgName+"</p></td><td><p class='smll_sign'>"+data.content.signRooms[i].numbeOfAccommodatePeople+"</p></td>" ;
				  for(var j=0;j<data.content.signRooms[i].rmRoomSchedules.length;j++){
					  if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='N'){
						  th+="<td><a href='#' onclick=\"goSlotRoom('" + data.content.signRooms[i].roomNo + "','" + roomType + "','" + data.content.signRooms[i].rmRoomSchedules[j].timeSlot + "')\" class='underline big' data-toggle='modal' data-target='#myModal'>空置</a></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='0'){
						  th+="<td><span class='grey_no big'>预约中</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='1'){
						  th+="<td><span class='grey_no big'>已过期</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='2'){
						  th+="<td><span class='grey_no big'>已使用</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='3'){
						  th+="<td><span class='user_red'>使用中</span></td>";
					  }
				  }
				  th+="</tr>";
				  
			   }
			   $("#signRoomTable tbody").append(th);
		    }else{
		    	alert(data.message);
		    }
		}
    });
}

function goSlotRoom(obj1,obj2,obj3){
	$("#roomNo").html(obj1);
	$("#roomType").html(obj2);
	$("#slotTime").html(obj3);
}

function checkFormSave(){
	
	if($("#jjrName").val()==''){
		alert("请选择经纪人！");
		$("#jjrName").focus();
		return false;
	}
	if($.trim($("#propertyAddress").val())==''){
		alert("请输入交易地址！");
		$("#propertyAddress").focus();
		return false;
	}
	if($.trim($("#numberOfParticipants").val())==''){
		alert("请输入参与人数！");
		$("#numberOfParticipants").focus();
		return false;
	}else if(!checkZhengShu($("#numberOfParticipants").val())){
		alert("请输入正确的参与人数！");
		$("#numberOfParticipants").focus();
		return false;
	}
	
	var flag=false;
	$('.choices span').each(function(){
		if($(this).hasClass("selected")) {
			flag = true;
        } 
    });
	if(!flag){
		alert("请选择办理事项！");
		return false;
	}
	
	return true;
	
}

/**
 * 校验正整数
 */
function checkZhengShu(num)  
{  
     var te= /^[1-9]+[0-9]*]*$/;
     return te.test(num);
}

