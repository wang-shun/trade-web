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
    
    $("#clearBtn").click(function(){
    	$('#roomTypeSlot').val("");
    	$('#useStatus').val("");
    });
})

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

