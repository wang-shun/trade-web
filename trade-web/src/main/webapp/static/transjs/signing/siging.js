var time=300000;//定时器执行间隔时间 1min

$(function () {
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
            $("#propertyAddress").removeAttr("disabled");
        } else {
            $(this).addClass("selected");
        }
        if($(this).prop("id")=='OpenRegularMeeting' && $(this).hasClass("selected")){
        	$(this).siblings().removeClass("selected");
        	$("#propertyAddress").prop("disabled","disabled");
        	$("#propertyAddress").val("");
        	$("#tradeAddr").hide();
        }else{
        	$("#OpenRegularMeeting").removeClass("selected");
        	$("#propertyAddress").removeAttr("disabled");
        	$("#tradeAddr").show();
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
        signRommAjaxSubmit(1);
    });
    $("#tommrow").click(function(){
        $(".datatime").val(getDateWeek(1));
        $('.datatime').datepicker('update');
        signRommAjaxSubmit(1);
    });
    
  //产证地址文本框失去焦点获取对应的caseCode
	$("#propertyAddress").blur(function(){
		$(".autocompleter").hide();
	});
	
	$("#propertyAddress").focus(function(){
		$(".autocompleter").show();
	});
    
    signRommAjaxSubmit(0);
    
    $("#searchBtn").click(function(){
    	var curDate = $.trim($('#curDate').val());
    	if(curDate==''){
    		alert("请选择预约日期！");
    		return;
    	}
    	signRommAjaxSubmit(1);
    });
    //清空条件
    $("#clearBtn").click(function(){
    	$('#roomTypeSlot').val("");
    	$('#useStatus').val("");
    });
	
	//保存 临时分配数据
	$("#saveBtn").click(function(){
		
		var flag=false;
		$('.choices span').each(function(){
			if($(this).hasClass("selected") && $(this).prop("id")=='OpenRegularMeeting') {
				flag = true;
	        } 
	    });
		//添加时校验
  	    if(!checkFormSave(flag)){
  		  return;
  	    }
		
		
  	   
  	   $("#propertyAddress").blur();
	  	var caseCode = $("#caseCode").val();//案件编号
	  	var agentCode = $("#jjrName").attr('hVal'); //预约人id
	  	
    	var numberOfParticipants = $("#numberOfParticipants").val();//参与人数
    	var numberOfPeople = $("#numberOfPeople").val();//容纳人数
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
		var startDate = $("#startDate").val();//排期起始时间
		var resPersonMobile = $.trim($("#mobile").val());//预约人电话
	   	$.ajax({
	     		url:ctx+"/signroom/addReservation",
	     		method:"post",
	     		dataType:"json",
	     		data : { resType : '1',
	     			     resPersonId : agentCode,
	     			     caseCode : caseCode,
	     			     propertyAddress : propertyAddress,
	     			     numberOfPeople : numberOfPeople,
	     			     numberOfParticipants : numberOfParticipants,
	     			     transactItemCode : transactItemCode,
	     			     scheduleId : scheduleId,
	     			     signingCenter : signingCenter,
	     			     signingCenterId : signingCenterId,
	     			     roomId : roomId,
	     			     resStatus : '1',
	     			     resPersonOrgId : resPersonOrgId,
	     			     startDate : startDate,
	     			    resPersonMobile : resPersonMobile
	     		},	 
				success : function(data) {   
						if(data.success){
							alert(data.message);
							$("#closeBtn").click();
							signRommAjaxSubmit(1);
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
		var agentCode = $("#jjrName").attr('hVal'); //预约人id
		if(propertyAddress != ""){
			$.ajax({
				cache:false,
				async:false,
				type:"POST",
				dataType:"text",
				url:ctx+"/weixin/signroom/getCaseCodeByPropertyAddrBack",
				data: {propertyAddress:propertyAddress,agentCode:agentCode},
				success:function(data){
					$("#caseCode").val(data);
				}
			});
		}
	});
    
	
	setInterval(function() {
		signRommAjaxSubmit(1);
	}, time);
	
    
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
	console.log(array);
	if (array && array.length > 0) {
		$("#jjrName").val(array[0].username);
		$("#jjrName").attr('hVal', array[0].userId);
		$("#resPersonOrgId").val(array[0].orgId);//预约人组织ID
		$("#mobile").val(array[0].mobile);
	} else {
		$("#jjrName").val("");
		$("#jjrName").attr('hVal', "");
		$("#resPersonOrgId").val("");
		$("#mobile").val("");
	}
	
	var agentCode = $("#jjrName").attr('hVal');
	
	getPropertyAddress(agentCode);
	
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

function signRommAjaxSubmit(obj) {
	$("#signRoomTable tbody tr").remove();
	var params = getParamsValue();
	$.ajax({
		url:ctx+"/signroom/generatePageDate",
		method:"post",
		dataType:"json",
		data : params,
		beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
		success:function(data){
			$.unblockUI();
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
				   var roomType = $.trim(data.content.signRooms[i].roomType)=='0'?'普通':'机动';
				   if($.trim(data.content.signRooms[i].roomType)=='0'){
					   th += "<tr><td><p class='big'>"+data.content.signRooms[i].roomNo+"<em class='qing_bg ml5'>"+roomType+"</em></p></td>";
				   }else{
					   th += "<tr><td><p class='big'>"+data.content.signRooms[i].roomNo+"<em class='yellow_bg ml5'>"+roomType+"</em></p></td>";
				   }
				  	th+="<td><p class='big'>"+data.content.signRooms[i].districtName+"</p></td><td><p class='smll_sign'>"+data.content.signRooms[i].numbeOfAccommodatePeople+"</p></td>" ;
				  for(var j=0;j<data.content.signRooms[i].rmRoomSchedules.length;j++){
					  if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='N' && data.content.signRooms[i].rmRoomSchedules[j].zhiHui==false){
						  th+="<td><a href='#' onclick=\"goSlotRoom('" + data.content.signRooms[i].roomNo + "','" + roomType + "','" + data.content.signRooms[i].rmRoomSchedules[j].timeSlot +"','"+data.content.signRooms[i].rmRoomSchedules[j].pkid+"','"+data.content.signRooms[i].tradeCenter+"','"+data.content.signRooms[i].tradeCenterId+"','"+data.content.signRooms[i].pkid+"','"+data.content.signRooms[i].numbeOfAccommodatePeople+"','"+data.content.signRooms[i].rmRoomSchedules[j].startDate+"')\" class='underline big' data-toggle='modal' data-target='#myModal'>空置</a></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='N' && data.content.signRooms[i].rmRoomSchedules[j].zhiHui==true){
						  th+="<td><span class='grey_no big'>空置</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='0'){
						  th+="<td><span class='grey_no big'>预约中</span></td>";
					  }else if($.trim(data.content.signRooms[i].rmRoomSchedules[j].useStatus)=='1'){
						  th+="<td><span class='big'>使用中</span></td>";
					  }
				  }
				  th+="</tr>";
			   }
			   $("#signRoomTable tbody").append(th);
		    }else{
		    	alert(data.message);
		    }
		},
		error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
    });
}

function goSlotRoom(roomNo,roomType,slotTime,scheduleId,tradeCenter,tradeCenterId,roomId,numberOfPeople,startDate){
	$("#roomNo").html(roomNo);
	$("#roomType").html(roomType);
	$("#slotTime").html(slotTime);
	$("#scheduleId").val(scheduleId);
	$("#tradeCenter").val(tradeCenter);
	$("#tradeCenterId").val(tradeCenterId);
	$("#roomId").val(roomId);
	$("#numberOfPeople").val(numberOfPeople);
	$("#startDate").val(startDate);
	$("#jjrName").val("");
	$("#jjrName").attr('hVal', "");
	$("#mobile").val("");
	$("#resPersonOrgId").val("");
	$("#caseCode").val("");
	$("#propertyAddress").val("");
	$('#propertyAddress').autocompleter('destroy');
	$("#numberOfParticipants").val("");
	$('.choices span').each(function(){
		if($(this).hasClass("selected")) {
			$(this).click();
        } 
    });
	
	var curdate = $("#curDate").val();
	$("#signDate").html(curdate.substring(5,7)+'/'+curdate.substr(8));
	
}

function checkFormSave(isMeet){
	
	if($.trim($("#jjrName").val())==''){
		alert("请选择经纪人！");
		$("#jjrName").focus();
		return false;
	}
	var mobile = $.trim($("#mobile").val());
	if(mobile==''){
		alert("请输入经纪人电话！");
		$("#mobile").focus();
		return false;
	}
	if(!isMobile(mobile)){
		alert("请输入正确格式的经纪人电话！");
		$("#mobile").focus();
		return false;
	}
	if(!isMeet){
		if($.trim($("#propertyAddress").val())==''){
			alert("请输入交易地址！");
			$("#propertyAddress").focus();
			return false;
		}
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

//获取产证地址列表
function getPropertyAddress(agentCode){
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url: ctx + "/weixin/signroom/getPropertyAddressList",
		data: {agentCode : agentCode},
		success:function(data){
			if(data.length > 0){
				 $("#propertyAddress").autocompleter({
				       source: data
				   });
			}
		}
	});
}
//手机号码校验
function isMobile(number){
    reg = /^1[3|4|5|7|8]\d{9}$/;
    if (!reg.test(number)) {
        return false; 
    }
    return true;
}





