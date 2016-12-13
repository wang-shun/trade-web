
$(function(){
	
	var ctx = $("#ctx").val();
	
	//页面初始化
	init();
	
	//点击更换签约室按钮事件
	$("#btnChangeRoom").click(function(){
		var resId = $("#signRoom #resId").val();
		var scheduleId = $("#signRoom tr td button[class='btn btn-transparent margin5 btn-lightblue']").attr("id");
		
		saveChangeRoom(resId,scheduleId,"change");
	});
	
	//点击更换签约室及启用按钮事件
	$("#btnChangeAndEnableRoom").click(function(){
		var resId = $("#signRoom #resId").val();
		var scheduleId = $("#signRoom tr td button[class='btn btn-transparent margin5 btn-lightblue']").attr("id");
		
		saveChangeRoom(resId,scheduleId,"changeAndSave");
	});
	
	
	//条件查询
	$("#searchButton").click(function(){
		reloadGrid();
		
		//初始化标题效果
		initTitle();
	});
	
	 //全选
    $("#CheckedAll").click(function(){
        var isChecked = $(this).prop("checked");
        $('input[name=items]').prop("checked", isChecked );
        $("#work").prop("checked", false );

    });
    
    $('input[type=checkbox][name=items]').click(function(){
        var flag=true;
        $('input[type=checkbox][name=items]').each(function(){
            if(!$(this).prop("checked") == true){
                flag = false;
            }
        });
        if( flag ){
            $('#CheckedAll').prop('checked', true );
        }else{
            $('#CheckedAll').prop('checked', false );
        }
    });
    
    //工作日选择
    $("#work").click(function() {
        var isChecked = $(this).prop("checked");
        if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
            $('.work1').prop("checked", isChecked );
            $(".zhoumo").prop("checked", false );
            $("#CheckedAll").prop("checked", false );
        }else{
            $('.work1').prop("checked", false );
        }
    });

    $('.work1').click(function(){
        var flag=true;
        $('.work1').each(function(){
            if(!$(this).prop("checked") == true){
                flag = false;
            }
        });
        if( !flag ){
            $("#work").prop('checked', false );
        }
    });
    
    $(".zhoumo").click(function(){
        if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
            $('#work').prop("checked", false );
        }
    });

    //日历控件
    $('.input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
    
  //选择切换
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
    });

    $(".datatime").val(show());

    $("#today").click(function(){
        $(".data_style").val(getDateWeek(0));
        reloadGrid();
        //$("#searchForm").submit();
    });
    
    $("#tommrow").click(function(){
        $(".data_style").val(getDateWeek(1));
        reloadGrid();
        //$("#searchForm").submit();
    });
    
    //添加跟进信息
    $("#btnAddFlowUpInfo").click(function(){
    	var data = saveFlowUpInfo();
    	
    	var result = data.result;  //保存结果
    	var createDateTime = data.createDateTime;   //保存最新跟进的时间
    	var realName = data.realName;   //跟进人名称
    	
    	if(result == "true"){
    		//保存跟进信息之后设置页面上的跟进信息
    		setFollowupInfo(createDateTime,realName);
    	}
    	
    });
    
  //清除
  $("#clear").click(function(){
	$("#searchForm input[name='resPersonId']").val("");
	$("#searchForm input[name='resPersonId']").attr("hval","");
	$("#searchForm input[name='resPeopleId']").val("");
	$("#searchForm input[name='resNo']").val("");
	$("#searchForm input[name='mobile']").val("");
	$("#searchForm input[name='startDateTime']").val("");
	$("#searchForm input[name='endDateTime']").val("");
	$("#searchForm #selResTime").val("");
	$("#searchForm input[name='resTime']").val("");
	$("#selResStatus").val("");
	$("#searchForm input[name='resStatus']").val("");
  });
  
  //选择状态事件
  $("#selResStatus").change(function(){
	  $("#searchForm input[name='resStatus']").val(this.value);
  });
  
  //选择预约时间事件
  $("#selResTime").change(function(){
	  $("#searchForm input[name='resTime']").val(this.value);
  });
  
});

//保存跟进信息之后设置页面上的跟进信息
function setFollowupInfo(createDateTime,realName){
	$("button[type='reset']").click();
	
	var resId = $("#flowupForm input[name='resId']").val();
	var oldComment = $("#flowupForm textarea[name='comment']").val();
	
	var $currentRow = $("#signinglist #" + resId);   //获取到当前行
	var $a = $currentRow.find(".latestComment");
	var $pLatestFollupTime = $currentRow.find(".latestFollupDateTime");
	
	var formatCreateDatetime = createDateTime.substring(0,createDateTime.lastIndexOf(":"));
	$pLatestFollupTime.html(formatCreateDatetime);   //更新最新跟进时间
	
	var formatComment = "";
	
	//如果跟进信息长度大于8,则截取
	if(oldComment.length > 8){
		formatComment = oldComment.substring(0,8) + "....";
	}
	else {
		formatComment = oldComment;
	}
	
	$a.html(formatComment);   //设置最近跟进信息
	
	var latestFollowupInfoHtml = realName + "&nbsp;&nbsp;" + createDateTime + "&nbsp;&nbsp;" + oldComment +  "</br>";
	var oldFollowupInfoHtml = $a.attr("title");
	
	var newFollowupInfoListHtml = latestFollowupInfoHtml + oldFollowupInfoHtml;    //获取所有跟进信息列表信息
	var arrayNewFollowupInfoList = newFollowupInfoListHtml.split("</br>");
	
	var newTitle = "";
	var length = arrayNewFollowupInfoList.length;
	for(var i=0;i<length;i++){
		if(i == 0){
			newTitle += (i + 1) + "." + arrayNewFollowupInfoList[0] + "</br>";
		}
		else if(i != (length - 1)){
			var followupInfoHtml = arrayNewFollowupInfoList[i];
			newTitle += (i + 1) + "." + followupInfoHtml.substring(2,followupInfoHtml.length) + "</br>";
		}
	}
	
	$a.attr("title",newTitle);
}

//变更签约室点击切换效果
function toggleClass(obj){
	$("#signRoom tr td button").removeClass('btn-lightblue');
    if(!$(obj).hasClass("btn-lightblue")) {
        $(obj).addClass('btn-lightblue');
    }
}

//变更房间
function changeRoom(obj,resId,tradeCenterId,resStartTime,resEndTime){
	$("#signRoom #resId").val(resId);
	$("#signRoom #resStartTime").val(resStartTime);
	$("#signRoom #resEndTime").val(resEndTime);
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/reservation/getUseableSignRoomList",
		data:{tradeCenterId:tradeCenterId,resStartTime:resStartTime,resEndTime:resEndTime},
		success:function(data){
			var strHtml = "";
			
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					var signroomInfo = data[i];
					
					strHtml += "<tr ><td style='padding:0px 8px;'><p>" + signroomInfo.numberOfPeople + "人间</p></td><td style='padding:0px 8px;'>";
					
					var roomPropList = signroomInfo.roomPropList;
					
					for(var j=0;j<roomPropList.length;j++){
						var roomProp = roomPropList[j];
						var roomType = roomProp.roomType;
						
						if(roomType == 0){
							strHtml += "<button id='" + roomProp.scheduleId + "' class='btn btn-transparent margin5' onClick='toggleClass(this)' accesskey='" + signroomInfo.numberOfPeople + "' lang='" +  roomProp.roomNo + "'>" + roomProp.roomNo + "</button>";
						}
						else if(roomType == 1){
							strHtml += "<button id='" + roomProp.scheduleId + "' class='btn btn-transparent margin5 btn-lightyellow' onClick='toggleClass(this)' accesskey='" + signroomInfo.numberOfPeople + "' lang='" +  roomProp.roomNo + "'>" + roomProp.roomNo + "（机动）</button>";
						}
						
					}
					
					strHtml += "</td></tr>";     
				}
			}
			else {
				strHtml = "<tr class='text-center'><td colspan='2'><font color='red'>无可预约房间信息!</font></td></tr>"
			}
			
			$("#changeRoom #signRoom tbody").html(strHtml);
			$("#changeRoom").show();
		}
	});
}

//当前时间点是否有空闲的同一房型的房间
function isHasFreeRoomByCurrentTimeAndRoomNo(roomId){
	var isExist = true;
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/reservation/isHasFreeRoomByCurrentTimeAndRoomNo",
		data:{roomId:roomId},
		success:function(data){
			if(data == "false"){
				isExist = false;
			}
		}
	});
	
	return isExist;
}

//签约室开始使用
function startUse(obj,resDate,startTime,endTime,roomId,resId,scheduleId){
	var $obj = $(obj);
	
	var strStartTime = resDate + " " + startTime;
	var strEndTime = resDate + " " + endTime;
	
	var startDateTime = new Date(strStartTime);
	var endDateTime = new Date(strEndTime);
	var currentDateTime = new Date();
	
	//如果在正常预约时间段内,走正常的签到流程
	if(currentDateTime >= startDateTime && currentDateTime <= endDateTime){
		
		//判断该预约上一个时间段是否签退
		var isPass = isOvertimeUse(scheduleId,roomId);
		
		//如果有签退,就开始签到
		if(isPass){
			startAndEndUse($obj,"startUse");
		}
		else {
			alert("该房间上个预约时间段未签退,不能开始使用！");
		}
	}
	
	//如果是提前签到,临时分配一个房间,判断在该时间内是否有同一房型的闲置房间
	if(currentDateTime < startDateTime){
		
		//获取当前日期
		var currentDate = getCurrentDate();
		
		//获取当天的预约9点时间
		var currentStartResTime = currentDate + " 09:00";
		
		//获取开始预约时间
		var resStartTime = resDate + " " + startTime;
		
		//提前使用的当天日期要与预约日期是同一天才能提前使用
		if(currentDate != resDate){
			alert("提前使用只限当天的预约！");
			return false;
		}
		
		//如果提前使用预约时间在9点到11点的预约信息,不需要临时分配房间,直接将该预约信息状态置为使用中
		if(currentStartResTime == resStartTime){
			startAndEndUse($obj,"startUse");
			return false;
		}
		
		//判断该预约上一个时间段是否签退
		var isPass = isOvertimeUse(scheduleId,roomId);
		if(!isPass){
			alert("该房间上个预约时间段未签退,不能开始使用！");
			return false;
		}
		
		//当前时间点是否有空闲的同一房间号房间
		var isExist = isHasFreeRoomByCurrentTimeAndRoomNo(roomId);
		if(!isExist){
			alert("当前时间没有可闲置房间信息,请联系值班经理进行临时分配！")
			return false;
		}
			
		//提前签到
		startUseInAdvance(resId,roomId);	
	}
	
	//如果当前时间超过预约结束时间
	if(currentDateTime > endDateTime){
		alert("当前时间已经超过预约结束时间,不能签到！");
	}
}

//判断上一个时间段该房间是否签退
function isOvertimeUse(scheduleId,roomId){
	var isPass = false;
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/reservation/isOvertimeUse",
		data:{roomId:roomId,scheduleId:scheduleId},
		success:function(data){
			if(data == "true"){
				isPass = true;
			}
		}
	});
	
	return isPass;
}

//提前签到
function startUseInAdvance(resId,roomId){
	
	if(confirm("请确定是否开始使用？")){
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"text",
			url:ctx+"/reservation/startUseInAdvance",
			data:{roomId:roomId,resId:resId},
			success:function(data){
				if(data == "true"){
					alert("已为您临时分配一个房间，请置顶查看！");
					reloadGrid();
				}
				else if(data == "false"){
					alert("操作失败！");
				}
			}
		});
	}
	
}

//签约室结束使用
function endUse(obj){
	var $obj = $(obj);
	
	startAndEndUse($obj,"endUse");
	
}

//打开跟进信息界面
function followup(obj){
	var $obj = $(obj);
	var resId = $obj.siblings("input[name='resId']").val();
	var roomType = $obj.siblings("input[name='roomType']").val();
	var roomNo = $obj.siblings("input[name='roomNo']").val();
	var resDateTime = $obj.siblings("input[name='resDateTime']").val();
	var startResDateTime = $obj.siblings("input[name='startResDateTime']").val();
	var endResDateTime = $obj.siblings("input[name='endResDateTime']").val();
	var realName = $obj.siblings("input[name='realName']").val();
	var mobile = $obj.siblings("input[name='mobile']").val();
	
	var strRoomNoHtml = roomNo;
	if(roomType == 1){
		strRoomNoHtml += "<em class='yellow_bg ml5'>机动</em>";
	}
	
	$("#flowupForm input[name='resId']").val(resId);
	$("#roomNo").html(strRoomNoHtml);
	$("#resDateTime").html(resDateTime);
	$("#resTime").html(startResDateTime + "-" + endResDateTime);
	$("#realname").html(realName);
	$("#mobile").html(mobile);
	$("#followUpDate").html(getCurrentTime());
	$("textarea[name='comment']").val("");
}


function init(){
	//加载预约时间
	getResTime();
	
	//加载签约室预约列表信息
	reloadGrid();
}

//开始使用
function startAndEndUse(obj,flag){
	var message = "";
	
	if(flag == "startUse"){
		message = "请确定是否开始使用？";
	}
	else if(flag == "endUse"){
		message = "请确定是否结束使用？";
	}
	
	
	if(confirm(message)){
		var resId = obj.parents(".dropdown-menu").find("input[name='resId']").val();
		
		$.ajax({
			cache:false,
			async:false,
			type:"POST",
			dataType:"json",
			url:ctx+"/reservation/startAndEndUse",
			data:{resId:resId,flag:flag},
			success:function(data){
				var result = data.result;  //返回结果
				//var operateTime = data.operateDateTime;  //操作时间
				
				if(result == "true"){
					reloadGrid();
					//设置操作时间及预约状态
					//setOperateTimeAndStatus(resId,operateTime,flag);
				}
			}
		});
	}
}

//设置操作时间及预约状态
function setOperateTimeAndStatus(resId,operateTime,flag){
	var $currentRow = $("#signinglist #" + resId);   //获取到当前行
	var $tdResStatus = $currentRow.find(".tdResStatus");
	var $menu = $currentRow.find(".tdOperation .dropdown-menu");
	var resStatus = $("#searchForm #selResStatus option:selected").val();
	
	//设置开始使用时间及结束使用时间
	if(flag == "startUse"){
		if(resStatus == ""){
			$currentRow.find(".checkInTime").html(operateTime);
			
			var timeDifference = $tdResStatus.attr("lang");
			
			if(timeDifference <= 0){
				$tdResStatus.html("<span class='big orange-hint text-center'>使用中</span>");
			}
			else if(timeDifference > 0){
				$tdResStatus.html("<span class='big red-hint text-center'>使用中</span>");
			}
			else {
				$tdResStatus.html("<span class='big text-center'>使用中</span>");
			}
			
			$("<li class='liEndUse'><a href='javascript:void(0);' onClick='endUse(this)'>结束使用</a></li>").insertBefore($menu.find(".liStartUse"));
			$menu.find(".liStartUse").remove();
			$menu.find(".liChangeRoom").remove();
		}
		else if(resStatus == "0"){
			$currentRow.remove();
		}
		
	}
	else if(flag == "endUse"){
		$currentRow.find(".checkOutTime").html(operateTime);
		
		if(resStatus == ""){
			$tdResStatus.html("已使用");
			
			$menu.find(".liEndUse").remove();
		}
		else if(resStatus == "1"){
			$currentRow.remove();
		}
	}
}

function saveChangeRoom(resId,scheduleId,flag){
	var isPass = false;
	var $selButton = $("#signRoom tbody tr td button[class='btn btn-transparent margin5 btn-lightblue']");
	var length = $selButton.length;
	
	if(length == 0){
		alert("请选择房间编号！");
		return false;
	}
	
	var numberOfPeople = $selButton.attr("accesskey");
	var roomNo = $selButton.attr("lang");
	
	var resStartTime = $("#signRoom #resStartTime").val();
	var resEndTime = $("#signRoom #resEndTime").val();
	
	var currentDateTime = new Date();
	var resStartDateTime = new Date(resStartTime);
	var resEndDateTime = new Date(resEndTime);
	
	if(flag == "changeAndSave"){
		if(currentDateTime < resStartDateTime || currentDateTime > resEndDateTime){
			alert("不能开始，不在预约时间内！");
			return false;
		}
	}
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/reservation/changeRoom",
		data:{resId:resId,scheduleId:scheduleId,flag:flag},
		success:function(data){
			var result = data.result;
			var operateTime = data.operateTime;
			
			if(result == "true"){
				//设置房间号和容纳人数
				setRoomNoAndNumberOfPeople(resId,flag,numberOfPeople,roomNo,operateTime);
			}
		}
	});
}

//设置房间号和容纳人数
function setRoomNoAndNumberOfPeople(resId,flag,numberOfPeople,roomNo,operateTime){
	$("#changeRoom button[type='reset']").click();
	
	var resStatus = $("#searchForm #selResStatus option:selected").val();
	
	var $currentRow = $("#signinglist #" + resId);   //获取到当前行
	var $menu = $currentRow.find(".tdOperation .dropdown-menu");
	var $tdResStatus = $currentRow.find(".tdResStatus");
	var $pRoomInfo = $currentRow.find(".tdRoomInfo .pRoomInfo");
	$pRoomInfo.html(roomNo + "（<span class='big'>" + numberOfPeople + "人间</span>） ");  //更改房间号及房间容纳数
	
	if(flag == "changeAndSave"){
		if(resStatus == "0"){
			$currentRow.remove();	
		}
		else if(resStatus == ""){
			$currentRow.find(".checkInTime").html(operateTime); //设置开始使用时间
			
			var timeDifference = $tdResStatus.attr("lang");
			
			//设置预约状态
			if(timeDifference <= 0){
				$tdResStatus.html("<span class='big orange-hint text-center'>使用中</span>");
			}
			else if(timeDifference > 0){
				$tdResStatus.html("<span class='big red-hint text-center'>使用中</span>");
			}
			else {
				$tdResStatus.html("<span class='big text-center'>使用中</span>");
			}
			
			$("<li class='liEndUse'><a href='javascript:void(0);' onClick='endUse(this)'>结束使用</a></li>").insertBefore($menu.find(".liStartUse"));  //添加结束使用操作
			$menu.find(".liStartUse").remove();  //清除开始使用操作
			$menu.find(".liChangeRoom").remove();  //清除变更签约室操作
		}
	}
	
	
}

//保存跟进信息
function saveFlowUpInfo(){
	var isSuccess = false;
	var resId = $("#flowupForm input[name='resId']").val();
	var comment = $("#flowupForm textarea[name='comment']").val();
	var result = [];
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/reservation/saveResFlowup",
		data:{resId:resId,comment:comment},
		success:function(data){
			result = data;
		}
	});
	
	return result;
}

function reloadGrid(){
	var data = getParams();
	
	$("#signinglist").reloadGrid({
    	ctx : ctx,
		queryId : "signingList",
	    templeteId : 'template_signingList',
	    data : data,
	    wrapperData : data
    });
	
	//重新初始化标题效果
	initTitle();
}

function getParams() {
	var distinctId = $("input[name='distinctId']").val();
	var resPersonId = $("input[name='resPersonId']").attr("hVal");
	var resNo = $.trim($("input[name='resNo']").val());
	var mobile = $.trim($("input[name='mobile']").val());
	var startDateTime = $("input[name='startDateTime']").val();
	var endDateTime = $("input[name='endDateTime']").val();
	var resTime = $("#selResTime option:selected").val();
	var resStatus = $("#selResStatus option:selected").val();
	
	var startResTime = '';
	var endResTime = '';
	if(resTime != ""){
		startResTime = resTime.substring(0,resTime.indexOf("-")) + ":00";
		endResTime = resTime.substring(resTime.indexOf("-") + 1,resTime.length) + ":00";
		
		var startNum = Number(startResTime.substring(0,startResTime.indexOf(":")));
		if(startNum < 10){
			startResTime = "0" + startResTime; 
		}
		
		var endNum = Number(endResTime.substring(0,endResTime.indexOf(":")));
		if(endNum < 10){
			endResTime = "0" + endResTime;
		}
	}
	
	var data = {};
	data.distinctId = distinctId;
	data.resPersonId = resPersonId;
	data.resNo = resNo;
	data.mobile = mobile;
	data.startDateTime = startDateTime;
	data.endDateTime = endDateTime;
	data.startResTime = startResTime;
	data.endResTime = endResTime;
	data.resStatus = resStatus;
	
	return data;
}

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

function signRoomSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#resPersonId").val(array[0].username);
		$("#resPersonId").attr('hVal', array[0].userId);
		$("#resPeopleId").val(array[0].userId);

	} else {
		$("#resPersonId").val("");
		$("#resPersonId").attr('hVal', "");
		$("#resPeopleId").val("");
	}
}

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

function show(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += "0" + (mydate.getMonth()+1) + "-";
    str += mydate.getDate();
    return str;
}

//获取预约时间
function getResTime(){
	var strHtml = "<option value=''>预约时间</option>";
	var resTime = $("#searchForm input[name='resTime']").val();
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(resTime == data[i]){
						strHtml += "<option value='"+ data[i] + "' selected='selected'>" + data[i] + "</option>";
					}
					else {
						strHtml += "<option value='"+ data[i] + "'>" + data[i] + "</option>";
					}
				}
			}
		}
	});
	
	$("#selResTime").html(strHtml);
}

function getCurrentTime(){
	var d = new Date()
	var vYear = d.getFullYear()
	var vMon = d.getMonth() + 1
	var vDay = d.getDate()
	var h = d.getHours(); 
	var m = d.getMinutes();
	
	var currentDateTime = vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0"+ vDay : vDay)+ " " + (h<10 ? "0"+ h : h) + ":"+ (m<10 ? "0" + m : m);
	
	return currentDateTime;
}

//获取当前日期
function getCurrentDate(){
	var d = new Date()
	var vYear = d.getFullYear()
	var vMon = d.getMonth() + 1
	var vDay = d.getDate();
	
	var currentDate = vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0"+ vDay : vDay);
	
	return currentDate;
}

//初始化标题效果
function initTitle(){
	$('.demo-right').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
		});
	
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});
}
