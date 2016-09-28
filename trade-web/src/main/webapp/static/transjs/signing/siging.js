$(function () {
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }

    });
    
  //今天明天选择
    function Appendzero (obj) {
        if (obj < 10) return "0" + obj; else return obj;
    }
    function getDateWeek (x) {
        var now = new Date();
        var year = now.getFullYear ();//获取四位数年数
        var month = now.getMonth () + 1;
        var date = now.getDate () + x;
        var s = year + "-" + Appendzero (month) + "-" + Appendzero (date) ;
        return s;
    }
    $("#today").click(function(){
        $(".datatime").val(getDateWeek(0));
        $('.datatime').datepicker('update');
        ajaxSubmit(1);
    });
    $("#tommrow").click(function(){
        $(".datatime").val(getDateWeek(1));
        $('.datatime').datepicker('update');
        ajaxSubmit(1);
    });
    
    ajaxSubmit(0);
    $("#searchBtn").click(function(){
    	var curDate = $.trim($('#curDate').val());
    	if(curDate==''){
    		alert("请选择预约日期！");
    		return;
    	}
    	ajaxSubmit(1);
    });
    //清空条件
    $("#clearBtn").click(function(){
    	$('#roomTypeSlot').val("");
    	$('#useStatus').val("");
    });
	
	//保存 临时分配数据
	$("#saveBtn").click(function(){
		
  	   //添加时校验
  	   if(!checkFormSave()){
  		  return;
  	   }
  	   $("#propertyAddress").blur();
	  	var caseCode = $("#caseCode").val();//案件编号
	  	var agentCode = $("#jjrName").attr('hVal'); //预约人id
    	var numberOfParticipants = $("#numberOfParticipants").val();//参与人数
    	var propertyAddress = $.trim($("#propertyAddress").val());//产证地址
    	var transactItemCode='';//办理事项编号
		$('.choices span').each(function(){
			if($(this).hasClass("selected")) {
				transactItemCode += $(this).prop("id")+",";
	        } 
	    });
		transactItemCode = transactItemCode.substring(0,transactItemCode.length-1);
		
		var scheduleId = $("#scheduleId").val(); //签约室安排id
		var signingCenter = $("#tradeCenter").val();  //签约中心
		var signingCenterId = $("#tradeCenterId").val();//签约中心id
		var roomId = $("#roomId").val(); //房间ID
		var resPersonOrgId = $("#resPersonOrgId").val();//预约人组织ID
		
		/*var slotTime = $("#slotTime").html();
		var curDate = $("#curDate").val();*/
		
		/*var startDate = curDate +' '+slotTime.substring(0,slotTime.indexOf('-'));
		var endDate = curDate +' '+slotTime.substr(slotTime.indexOf('-')+1);*/
	  	
	   	$.ajax({
	     		url:ctx+"/signroom/addReservation",
	     		method:"post",
	     		dataType:"json",
	     		data : { resType : '1',
	     			     resPersonId : agentCode,
	     			     caseCode : caseCode,
	     			     propertyAddress : propertyAddress,
	     			     numberOfParticipants : numberOfParticipants,
	     			     transactItemCode : transactItemCode,
	     			     scheduleId : scheduleId,
	     			     signingCenter : signingCenter,
	     			     signingCenterId : signingCenterId,
	     			     roomId : roomId,
	     			     resStatus : '0',
	     			     resPersonOrgId : resPersonOrgId
	     			     /*startDate : startDate,
	     			     endDate : endDate*/
	     		},	 
				success : function(data) {   
						if(data.success){
							alert(data.message);
							window.location.href = ctx+"/signroom/signRoomAllotList";
						}else{
							alert(data.message);
						}
					},		
				error : function(errors) {
						alert("数据保存出错:"+JSON.stringify(errors));
					}	 
	      });
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
    
    
})
//选取营业部经纪人
function chooseManager(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();
	userSelect({
		startOrgId : '1D29BB468F504774ACE653B946A393EE',//营业部
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
	if (array && array.length > 0) {
		$("#jjrName").val(array[0].username);
		$("#jjrName").attr('hVal', array[0].userId);
		$("#resPersonOrgId").val(array[0].orgId);//预约人组织ID

	} else {
		$("#jjrName").val("");
		$("#jjrName").attr('hVal', "");
		$("#resPersonOrgId").val("");
	}
	
	var agentCode = $("#jjrName").attr('hVal');
	
	//文本框自动填充
	   $("#propertyAddress").keyup(function(){
		   var inputValue = this.value;
		   
		   if(inputValue != ""){
			   $.ajax({
					cache:false,
					async:false,
					type:"POST",
					dataType:"json",
					url: ctx + "/mobile/reservation/getPropertyAddressList",
					data: {inputValue: $("#propertyAddress").val(),agentCode : agentCode},
					success:function(data){
						if(data.length > 0){
							 $("#propertyAddress").autocompleter({
							       highlightMatches: true,
							       source: data,
							       hint: true,
							       empty: false,
							       limit: 3,
							       callback: function (value, index, selected) {
							           if (selected) {
							               $('.icon').css('background-color');
							           }
							       }
							   });
						}
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
			console.log(data);
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
				  th += "<tr><td><p class='big'>"+data.content.signRooms[i].roomNo+"<em class='yellow_bg ml5'>"+roomType+"</em></p></td><td><p class='big'>"+data.content.signRooms[i].districtName+"</p></td><td><p class='smll_sign'>"+data.content.signRooms[i].numbeOfAccommodatePeople+"</p></td>" ;
				  for(var j=0;j<data.content.signRooms[i].rmRoomSchedules.length;j++){
					  if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='N'){
						  th+="<td><a href='#' onclick=\"goSlotRoom('" + data.content.signRooms[i].roomNo + "','" + roomType + "','" + data.content.signRooms[i].rmRoomSchedules[j].timeSlot +"','"+data.content.signRooms[i].rmRoomSchedules[j].pkid+"','"+data.content.signRooms[i].tradeCenter+"','"+data.content.signRooms[i].tradeCenterId+"','"+data.content.signRooms[i].pkid+"')\" class='underline big' data-toggle='modal' data-target='#myModal'>空置</a></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='0'){
						  th+="<td><span class='grey_no big'>预约中</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='1'){
						  th+="<td><span class='grey_no big'>使用中</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='2'){
						  th+="<td><span class='grey_no big'>已使用</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='3'){
						  th+="<td><span class='user_red'>已过期</span></td>";
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

function goSlotRoom(roomNo,roomType,slotTime,scheduleId,tradeCenter,tradeCenterId,roomId){
	$("#roomNo").html(roomNo);
	$("#roomType").html(roomType);
	$("#slotTime").html(slotTime);
	$("#scheduleId").val(scheduleId);
	$("#tradeCenter").val(tradeCenter);
	$("#tradeCenterId").val(tradeCenterId);
	$("#roomId").val(roomId);
	
	var curdate = $("#curDate").val();
	$("#signDate").html(curdate.substring(5,7)+'/'+curdate.substr(8));
	
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






