
var ctx = $("#ctx").val();
var toast = new auiToast();
var isReturn;

var defaultTradeCenterId = $("#defaultTradeCenterId").val();
var selDate = $("#SelDate").val();
var selBespeakTime = $("#selBespeakTime option:selected").val();

$(function(){
	init();
	
	$("#selBespeakTime").change(function(){
		loading();
		defaultTradeCenterId = $("#selTradeCenter option:selected").val();
		selDate = $("#SelDate").val();
		selBespeakTime = $("#selBespeakTime option:selected").val();
		
		getSignRoomList(defaultTradeCenterId,selDate,selBespeakTime);
	});
	
	$("#selTradeCenter").change(function(){
		isReturn = false;
		
		loading();
		
		defaultTradeCenterId = this.value;
		selDate = $("#SelDate").val();
		selBespeakTime = $("#selBespeakTime option:selected").val();
		
		getSignRoomList(defaultTradeCenterId,selDate,selBespeakTime);
	});
});

//显示加载效果
function loading(){
    layer.open({
     className: 'popuo-loading',
     type: 1,
     shadeClose: false,
     shade: false,
   content: '<div class="item-loader-container">'
         + '<div class="la-line-spin-clockwise-fade la-2x left">'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '<div></div>'
         + '</div>'
         + '<p class="dialog-head left font16 ml10">加载中...</p>'

 })
 };

//取号
function quhao(obj,selBespeakTime,numberOfPeople){
	defaultTradeCenterId = $("#selTradeCenter option:selected").val();
	selDate = $("#SelDate").val();
	
	$("#defaultTradeCenterId").val(defaultTradeCenterId);
	$("#inputSelDate").val(selDate);
	$("#inputBespeakTime").val(selBespeakTime);
	$("#inputNumberOfPeople").val(numberOfPeople);
	
	$("#form1").submit();
}

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
	getSignRoomList(defaultTradeCenterId,selDate,selBespeakTime);
}

function initCalendar(){
	 var currYear = (new Date()).getFullYear(); // 获取年  
     var currMonth = (new Date()).getMonth(); // 获取月  
     var currDay = (new Date()).getDate(); // 获取日
     
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
        startDay: currDay,  
        startMonth: currMonth, // 开始月份  
        startYear: currYear, //开始年份 
        endYear: currYear + 20, //结束年份 
        headerText: function (valueText) { array = valueText.split('-'); return array[0] + "年" + array[1] + "月"+array[2]+"日"; }, //自定义弹出框头部格式
		//点击确定的事件
		onSelect:function(valueText,inst){
			loading();
			
			getBespeakTime();
			
			defaultTradeCenterId = $("#selTradeCenter option:selected").val();
			selDate = valueText;
			selBespeakTime = $("#selBespeakTime option:selected").val();
			
			getSignRoomList(defaultTradeCenterId,selDate,selBespeakTime);
		}
	}); 
	
}

// 求两个时间的天数差 日期格式为 YYYY-MM-dd
function daysBetween(strDateStart,strDateEnd){
	var date3= strDateEnd.getTime() - strDateStart.getTime();  //时间差的毫秒数
	//计算出相差天数
	var days=Math.floor(date3/(24*3600*1000));
	 
	//计算出小时数
	var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数
	var hours=Math.floor(leave1/(3600*1000));
	//计算相差分钟数
	var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
	var minutes=Math.floor(leave2/(60*1000));
	//计算相差秒数
	var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
	var seconds=Math.round(leave3/1000);
	
	return days + 1;
}

//获取当前日期
function getCurrentDate(){
	var d = new Date()
	var vYear = d.getFullYear()
	var vMon = d.getMonth() + 1
	var vDay = d.getDate()
	var h = d.getHours(); 
	var m = d.getMinutes(); 
	var se = d.getSeconds(); 
	
	var currentDate = vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0"+ vDay : vDay);
	
	return currentDate;
}

function getTradeCenterList(){
	var strHtml = "";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getTradeCenterList",
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(defaultTradeCenterId == data[i].pkid){
						strHtml += "<option value='"+ data[i].pkid + "' selected='selected'>" + data[i].centerName + "</option>";
					}
					else{
						strHtml += "<option value='"+ data[i].pkid + "'>" + data[i].centerName + "</option>";
					}
				}
			}
		}
	});
	
	$("#selTradeCenter").html(strHtml);
}

function getBespeakTime(){
	var selDate = $("#SelDate").val();
	var strHtml = "<option value=''>预约时间</option>";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				var currentDateTime = new Date()
				
				for(var i=0;i<data.length;i++){
					var resTime = data[i];
					var resStartTime = selDate + " " + resTime.substring(0,resTime.indexOf("-"));
					var resStartDateTime = new Date(Date.parse(resStartTime.replace(/-/g,   "/"))); 
					
					if(currentDateTime <= resStartDateTime){
						strHtml += "<option value='"+ data[i] + "'>" + data[i] + "</option>";
					}
				}
			}
		}
	});
	
	$("#selBespeakTime").html(strHtml);
}


function getSignRoomList(defaultTradeCenterId,selDate,selBespeakTime){
	var strHtml = "";
	var isHasBespeakTime = false;
	
	if(defaultTradeCenterId == 0){
		defaultTradeCenterId = 1;
	}
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				if(selBespeakTime == ""){
					var currentDateTime = new Date()
					
					for(var i=0;i<data.length;i++){
						var resTime = data[i];
						var resStartTime = selDate + " " + resTime.substring(0,resTime.indexOf("-"));
						var resStartDateTime = new Date(Date.parse(resStartTime.replace(/-/g,   "/"))); 
						
						if(currentDateTime <= resStartDateTime){
							isHasBespeakTime = true;
							strHtml += getSignRoomByTime(defaultTradeCenterId,selDate,data[i]);
						}
					}
				}
				else {
					strHtml += getSignRoomByTime(defaultTradeCenterId,selDate,selBespeakTime);
					isHasBespeakTime = true;
				}
			}
		}
	});
	
	if(!isHasBespeakTime){
		strHtml = "<section class='aui-content  reminder-login'><img src='" + ctx + "/static/image/reminder.png' width='100%' alt='' />" +
		"<h3 style='text-align:center'>对不起，<br/>当天没有可预约房间！</h3></section>";
	}
	
	$("#reservationList").html(strHtml);
	isReturn = true;
}


setInterval(function(){
	if(isReturn == true){
		layer.closeAll('loading');
	}
},500)

function getSignRoomByTime(defaultTradeCenterId,selDate,realSelBespeakTime){
	var startTime = realSelBespeakTime.substring(0,realSelBespeakTime.indexOf("-"));
	var endTime = realSelBespeakTime.substring(realSelBespeakTime.indexOf("-") + 1,realSelBespeakTime.length);
	
	var startDate = selDate + " " + startTime + ":00";
	var endDate = selDate + " " + endTime + ":00";
	
	var subStrHtml = getSignRoomInfo(defaultTradeCenterId,startDate,endDate,selDate,realSelBespeakTime);
	
	return subStrHtml;
}

function getSignRoomInfo(defaultTradeCenterId,startTime,endTime,selDate,selBespeakTime){
	var subStrHtml = "";
	var startDateTime;
	var endDateTime;
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getSignRoomInfoList",
		data:{tradeCenterId:defaultTradeCenterId,startTime:startTime,endTime:endTime},
		success:function(data){
			if(data.length > 0){
				var currentDateTime = new Date();
				
				//用来比较日期相差天数
				var currentDate = getCurrentDate();
				var selDateTime = new Date(Date.parse(selDate.replace(/-/g,   "/")));
				var currentTime = new Date(Date.parse(currentDate.replace(/-/g,   "/")));
				
				var days = daysBetween(currentTime,selDateTime);
				
				subStrHtml = "<article class='aui-content'><ul class='aui-list aui-list-in white'>"
		            + " <li class='aui-list-header header_grey_bg'><i class='iconfont blue mr5'>&#xe605;</i>" + selBespeakTime + "&nbsp;<span class='color80;'>" + selDate + "</span></li>";
				
				for(var i=0;i<data.length;i++){
					subStrHtml += "<li class='aui-info ptd5 aui-padded-l-10 aui-padded-r-10 border-bottom'>"
						+ "<div class='aui-info-item'><div class='room'>";
					
					if(data[i].numberOfPeople >= 1 && data[i].numberOfPeople <= 6){
						subStrHtml += "<i class='iconfont blue'>&#xe603;</i>";
					}
					else if(data[i].numberOfPeople >= 7 && data[i].numberOfPeople <= 9){
						subStrHtml += "<i class='iconfont cyan'>&#xe602;</i>";
					}
					else if(data[i].numberOfPeople >= 10 && data[i].numberOfPeople <= 12){
						subStrHtml += "<i class='iconfont yellow'>&#xe601;</i>";
					}
					else {
						subStrHtml += "<i class='iconfont orange'>&#xe600;</i>";
					}
					
					subStrHtml += "<span class='num'>" + data[i].numberOfPeople + "人间</span></div></div>"
						+ "<div class='aui-info-item'>";
					
					if(data[i].residualNumber == 0){
						subStrHtml += "剩余间数：<span class='aui-text-warning'>0</span>";
					}
					else {
						subStrHtml += "剩余间数：<span class='aui-text-primary'>" + data[i].residualNumber + "</span>";
					}
                
					if(selBespeakTime != ""){
						var strStartDateTime = selDate + " " + selBespeakTime.substring(0,selBespeakTime.indexOf("-"));
						var strEndDateTime = selDate + " " + selBespeakTime.substring(selBespeakTime.indexOf("-") + 1,selBespeakTime.length);
						
						startDateTime = new Date(Date.parse(strStartDateTime.replace(/-/g,   "/")));
						endDateTime = new Date(Date.parse(strEndDateTime.replace(/-/g,   "/")));
					}
					
					if(currentDateTime > endDateTime){
						subStrHtml += "<div class='aui-btn ml20 trans_bg'>取号</div></div>";
					}
					else {
						if(days <= 7){
							if(data[i].residualNumber == 0){
		                    	subStrHtml += "<div class='aui-btn ml20 trans_bg'>取号</div></div><span class='baoman'></span>";
		                    } 
		                    else {
		                    	subStrHtml += "<a href='javascript:void(0);' onClick=\"quhao(this,'" + selBespeakTime + "','" + data[i].numberOfPeople + "');\"><div class='aui-btn aui-btn-primary ml20'>取号</div></a></div>";
		                    }
						}
						else {
							subStrHtml += "<div class='aui-btn ml20 trans_bg'>取号</div></div>";
						}
					}
				}
				
				subStrHtml += "</li></ul></article>";  
			}
		}
	});
	
	return subStrHtml;
}

