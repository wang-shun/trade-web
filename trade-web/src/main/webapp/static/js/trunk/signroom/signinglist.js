
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
		$("#searchForm").submit();
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
        $("#searchForm").submit();
    });
    
    $("#tommrow").click(function(){
        $(".data_style").val(getDateWeek(1));
        $("#searchForm").submit();
    });
    
    //添加跟进信息
    $("#btnAddFlowUpInfo").click(function(){
    	var isSuccess = saveFlowUpInfo();
    	
    	if(isSuccess){
    		$("button[type='reset']").click();
    		$("#searchForm").submit();
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

//变更签约室点击切换效果
function toggleClass(obj){
	$("#signRoom tr td button").removeClass('btn-lightblue');
    if(!$(obj).hasClass("btn-lightblue")) {
        $(obj).addClass('btn-lightblue');
    }
}

//显示跟进信息
function showTip(obj){
	$(obj).poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
		});
}

//显示手机号信息
function showMobile(obj){
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
							strHtml += "<button id='" + roomProp.scheduleId + "' class='btn btn-transparent margin5' onClick='toggleClass(this)'>" + roomProp.roomNo + "</button>";
						}
						else if(roomType == 1){
							strHtml += "<button id='" + roomProp.scheduleId + "' class='btn btn-transparent margin5 btn-lightyellow' onClick='toggleClass(this)'>" + roomProp.roomNo + "（机动）</button>";
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

//签约室开始使用
function startUse(obj,resDate,startTime,endTime){
	var $obj = $(obj);
	
	var strStartTime = resDate + " " + startTime;
	var strEndTime = resDate + " " + endTime;
	
	var startDateTime = new Date(strStartTime);
	var endDateTime = new Date(strEndTime);
	var currentDateTime = new Date();
	
	if(currentDateTime >= startDateTime && currentDateTime <= endDateTime){
		startAndEndUse($obj,"startUse");
	}
	else {
		alert("不能开始，不在预约时间内！");
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
			dataType:"text",
			url:ctx+"/reservation/startAndEndUse",
			data:{resId:resId,flag:flag},
			success:function(data){
				if(data == "true"){
					$("#searchForm").submit();
				}
			}
		});
	}
}

function saveChangeRoom(resId,scheduleId,flag){
	var isPass = false;
	var length = $("#signRoom tbody tr td button[class='btn btn-transparent margin5 btn-lightblue']").length;
	
	if(length == 0){
		alert("请选择房间编号！");
		return false;
	}
	
	var resStartTime = $("#signRoom #resStartTime").val();
	var resEndTime = $("#signRoom #resEndTime").val();
	
	var currentDateTime = new Date();
	var resStartDateTime = new Date(resStartTime);
	var resEndDateTime = new Date(resEndTime);
	
	if(currentDateTime < resStartDateTime || currentDateTime > resEndDateTime){
		alert("不能开始，不在预约时间内！");
		return false;
	}
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/reservation/changeRoom",
		data:{resId:resId,scheduleId:scheduleId,flag:flag},
		success:function(data){
			if(data == "true"){
				$("#searchForm").submit();
			}
		}
	});
}

//保存跟进信息
function saveFlowUpInfo(){
	var isSuccess = false;
	var resId = $("#flowupForm input[name='resId']").val();
	var comment = $("#flowupForm textarea[name='comment']").val();
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/reservation/saveResFlowup",
		data:{resId:resId,comment:comment},
		success:function(data){
			if(data == "true"){
				isSuccess = true;
			}
		}
	});
	
	return isSuccess;
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
