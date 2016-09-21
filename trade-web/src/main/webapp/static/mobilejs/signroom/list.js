
var ctx = $("#ctx").val();

var orgId = $("#orgId").val();
var selDate = $("#SelDate").val();
var selBespeakTime = $("#selBespeakTime option:selected").val();

$(function(){
	
	init();
	
	$("#selBespeakTime").change(function(){
		orgId = $("#selTradeCenter option:selected").val();
		selDate = $("#SelDate").val();
		selBespeakTime = $("#selBespeakTime option:selected").val();
		
		getSignRoomList(orgId,selDate,selBespeakTime);
	});
	
	$("#selTradeCenter").change(function(){
		orgId = this.value;
		selDate = $("#SelDate").val();
		selBespeakTime = $("#selBespeakTime option:selected").val();
		
		getSignRoomList(orgId,selDate,selBespeakTime);
	});
	
	//" + ctx + "/mobile/reservation/bespeakUI
	$(".quhao").click(function(){
		orgId = $("#selTradeCenter option:selected").val();
		selDate = $("#SelDate").val();
		selBespeakTime = $(this).siblings("input[name='actBespeakTime']").val();
		
		$("#orgId").val(orgId);
		$("#inputSelDate").val(selDate);
		$("#inputBespeakTime").val(selBespeakTime);
		
		$("#form1").submit();
	});
});

/**
 * 页面初始化数据
 */
function init(){
	//初始化日历
	initCalendar();
	
	//获取交易中心信息
	getTradeCenterList();
	
	//获取预约时间段
	getBespeakTime();
	
	//获取签约室预约列表
	getSignRoomList(orgId,selDate,selBespeakTime);
}

function initCalendar(){
	var currYear = (new Date()).getFullYear(); 
	start=currYear - 20;
	end= currYear + 20 ;
	$("#SelDate").mobiscroll().date({ 
		theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        showNow: true,
        dateOrder: 'yymmdd', //面板中日期排列格式(可以设置)
        nowText: "今天",
        dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
        yearText: '年', monthText: '月', dayText: '日', //面板中年月日文字
        headerText: function (valueText) { array = valueText.split('-'); return array[0] + "年" + array[1] + "月"+array[2]+"日"; }, //自定义弹出框头部格式
		//点击确定的事件
		onSelect:function(valueText,inst){
			orgId = $("#selTradeCenter option:selected").val();
			selDate = valueText;
			selBespeakTime = $("#selBespeakTime option:selected").val();
			
			getSignRoomList(orgId,selDate,selBespeakTime);
		}
	}); 
	
}

function getTradeCenterList(){
	var strHtml = "";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/mobile/reservation/getTradeCenterList",
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(orgId == data[i].orgId){
						strHtml += "<option value='"+ data[i].orgId + "' selected='selected'>" + data[i].centerName + "</option>";
					}
					else{
						strHtml += "<option value='"+ data[i].orgId + "'>" + data[i].centerName + "</option>";
					}
				}
			}
		}
	});
	
	$("#selTradeCenter").html(strHtml);
}

function getBespeakTime(){
	var strHtml = "<option value=''>预约时间</option>";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/mobile/reservation/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					strHtml += "<option value='"+ data[i] + "'>" + data[i] + "</option>";
				}
			}
		}
	});
	
	$("#selBespeakTime").html(strHtml);
}

function getSignRoomList(orgId,selDate,selBespeakTime){
	var strHtml = "";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/mobile/reservation/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				if(selBespeakTime == ""){
					for(var i=0;i<data.length;i++){
						strHtml += getSignRoomByTime(orgId,selDate,data[i]);
					}
				}
				else {
					strHtml += getSignRoomByTime(orgId,selDate,selBespeakTime);
				}
			}
		}
	});
	
	$("#reservationList").html(strHtml);
	
}

function getSignRoomByTime(orgId,selDate,realSelBespeakTime){
	var startTime = realSelBespeakTime.substring(0,realSelBespeakTime.indexOf("-"));
	var endTime = realSelBespeakTime.substring(realSelBespeakTime.indexOf("-") + 1,realSelBespeakTime.length);
	
	var startDate = selDate + " " + startTime + ":00";
	var endDate = selDate + " " + endTime + ":00";
	
	var subStrHtml = getSignRoomInfo(orgId,startDate,endDate,selDate,realSelBespeakTime);
	
	return subStrHtml;
}

function getSignRoomInfo(orgId,startTime,endTime,selDate,selBespeakTime){
	var subStrHtml = "";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/mobile/reservation/getSignRoomInfoList",
		data:{orgId:orgId,startTime:startTime,endTime:endTime},
		success:function(data){
			if(data.length > 0){
				subStrHtml = "<article class='aui-content'><ul class='aui-list aui-list-in white'>"
		            + " <li class='aui-list-header header_grey_bg'><i class='iconfont blue mr5'>&#xe605;</i>" + selBespeakTime + "&nbsp;<span class='color80;'>" + selDate + "</span></li>";
				
				for(var i=0;i<data.length;i++){
					subStrHtml += "<li class='aui-info ptd5 aui-padded-l-10 aui-padded-r-10 border-bottom'>"
						+ "<div class='aui-info-item'><div class='room'><i class='iconfont blue'>&#xe603;</i>"
						+ "<span class='num'>" + data[i].numberOfPeople + "人间</span></div></div>"
						+ "<div class='aui-info-item'>剩余间数：<span class='aui-text-warning'>" + data[i].residualNumber + "</span>";
                
                    if(data[i].residualNumber == 0){
                    	subStrHtml += "<div class='aui-btn ml20 trans_bg'>取号</div></div><span class='baoman'></span>";
                    } 
                    else {
                    	subStrHtml += "<a href='javascript:void(0);' class='quhao'><div class='aui-btn aui-btn-primary ml20'>取号</div></a><input type='hidden' name='actBespeakTime' value='" + selBespeakTime + "'/></div>";
                    }
                    
                    subStrHtml += "</li></ul></article>";     
				}
			}
		}
	});
	
	return subStrHtml;
}

