//获取jQuery 对象
var $roomlist = $("#roomList");
var $tdlist = $("#dayList tr td");
var $title = $(".date-title");
var $choice = $(".choiceoption");
var $choicelt = $(".choiceoption:lt(3)");
var defaultTradeCenterId = $("#bespeakForm input[name='defaultTradeCenterId']").val();
var agentCode = $("#bespeakForm input[name='agentCode']").val();
var ctx = $("#bespeakForm #ctx").val();

$(function() {
	
    //页面初始化
    init();  
    
    //点击领取预约号按钮事件
    $("#btnBespoke").click(function(){
    	
    	//如果表单验证通过
    	if(checkForm()){
    		//保存签约室预约信息
    		save("normal"); 
    	}
    });
    
    //预约中心选择事件
    $("#selTradeCenter").change(function(){
    	var value = this.value;
    	
    	$("#bespeakForm input[name='selTradeCenterId']").val(value);
    	
    	//重新选择预约中心 清空之前选择的预约信息
    	$("#bespeakForm #dateseLect").val("");
    	$("#bespeakForm #selBespeakTime").val("");
    	$("#bespeakForm #selResDate").val("");
    	$("#dayList tr td").removeClass("curr");
    	
    	var strHtml = "<div class='nodata'><img src='" + ctx + "/image/nodata.png' width='100%' alt=''>"
    					+ "<p class='text-center font16'>对不起，暂无数据！</p>"
    					+ "<p class='text-center'>(请选择你所要的预约时间)</p></div>";
    	
    	$("#signroomList").html(strHtml);
    	
    	//获取前台交易顾问列表
    	getFrontConsultantList(value,"change");
    });
    
    //产证地址文本框失去焦点获取对应的caseCode
	$("#propertyAddress").blur(function(){
		$("#propertyAddress").siblings(".autocompleter").hide();
		getServiceSpecialistByPropertyAddr(this.value);
	});
	
	$("#propertyAddress").focus(function(){
		$("#propertyAddress").siblings(".autocompleter").show();
	});
	
	$("#serviceSpecialist").focus(function(){
		if(this.value != ""){
			$("#serviceSpecialist").siblings(".autocompleter").show();
		}
		else {
			$("#serviceSpecialist").siblings(".autocompleter").hide();
		}
	});
	
	$("#serviceSpecialist").keyup(function(){
		
		if(this.value != ""){
			$("#serviceSpecialist").siblings(".autocompleter").show();
		}
		else {
			$("#serviceSpecialist").siblings(".autocompleter").hide();
		}
	});
	
	$("#serviceSpecialist").blur(function(){
		$("#serviceSpecialist").siblings(".autocompleter").hide();
	});
	
    
    //遮罩弹窗层隐藏显示
    $roomlist.hide();
    $("#dateseLect").on("click",function() {
    	$(this).blur();
    	popSelectResInfo();
    });
    
    $(".layui-m-layershade").on("click",function() {
        $roomlist.hide();
    });

    //循环日期找到”今天“并添加当前样式
    $tdlist.each(function(){
        var tt = $(this).attr('date');
        if(tt == getDateWeek()) {
            $(this).addClass('curr');
        }
    });

    //点击切换效果
    $choice.eq(3).click(function() {
        if($(this).hasClass("select-mark")) {
             $(".item").show();
        }
        else {
             $(".item").hide();
        }
        $(this).toggleClass("select-mark");
        $choicelt.removeClass("select-mark");
        
        $("#bespeakForm #propertyAddress").val("");
    	$("#bespeakForm #serviceSpecialist").val("");
    });
    
    $choicelt.click(function() {
        if($(this).hasClass("select-mark")) {
            $(this).removeClass("select-mark");
        }
        else {
            $(this).addClass("select-mark");
        }
        $choice.eq(3).removeClass('select-mark');
        $(".item").show();
    });
    
});

/**
 * 页面初始化数据
 */
function init(){
	
	//获取交易中心信息
	getTradeCenterList();
	
	//日期初始化
	initDate();

	//产证地址初始化
	getPropertyAddress();
	
	//初始化前台交易顾问列表信息
	getFrontConsultantList(defaultTradeCenterId,"init");
}

//获取交易中心信息列表
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

//日期初始化
function initDate(){
	//获取当天日期
    $title.text(getDateWeek());
	
	var currentArrayDate = getDateStr(0);
	var week = currentArrayDate[2];
	
	var strHtml = "<tr>";
	
	if(week == 0){
		week = 7;
	}
	
	var start = 1 - week;
	var end = start + 13;
	var count = 1;
	
	for(var i=start;i<=end;i++){
		var arrayDate = getDateStr(i);
		
		if(count == 8){
			strHtml += "</tr><tr>";
		}
		
		if(i >= 0 && i <= 6){
			strHtml += "<td date='" + arrayDate[0] + "' onClick='dateClickToggle(this)' class='usable-date'>" + arrayDate[1] + "</td>";
		}
		else {
			strHtml += "<td date='" + arrayDate[0] + "' onClick='dateClickToggle(this)'>" + arrayDate[1] + "</td>";
		}
		
		count++;
	}
	
	strHtml += "</tr>"
	
	$("#dayList").html(strHtml);
}

//获取产证地址列表
function getPropertyAddress(){
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
				       source: data,
				       callback: function (value, index, selected) {
				    	   getServiceSpecialistByPropertyAddr(value);
				      }
				  }); 
			}
		}
	});
}

//获取前台交易顾问列表
function getFrontConsultantList(tradeCenterId,flag){
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url: ctx + "/weixin/signroom/getConsultantListByTradecentId",
		data: {tradeCenterId : tradeCenterId},
		success:function(data){
			if(data.length > 0){
				 if(flag == "init"){
					 $("#serviceSpecialist").autocompleter({
					       source: data,
					       callback: function (value, index, selected) {
					    	   $("#serviceSpecialist").val(value);
					      }
					  }); 
				 }
				 else if(flag == "change"){
					 //销毁autocompleter
					 $('#serviceSpecialist').autocompleter('destroy');
					 $("#serviceSpecialist").autocompleter({
					       source: data,
					       callback: function (value, index, selected) {
					    	   $("#serviceSpecialist").val(value);
					      }
					  }); 
				 }
			}
		}
	});
}

//弹出选择预约房间弹出框
function popSelectResInfo(){
	var selResDate = $("#bespeakForm #selResDate").val();
	
	if(selResDate != ""){
		var selBespeakTime = $("#bespeakForm #selBespeakTime").val();
		
		$("#dayList tr td").removeClass("curr");
		$("#roomList .date-title").html(selResDate);
		
		$("#dayList tr td[date='" + selResDate + "']").addClass("curr");
		
		$(".aui-content .aui-list .aui-list-item").css("background-color","#fff");
		$(".aui-content .aui-list .aui-list-item[lang='" + selBespeakTime + "']").css("background-color","#f9f9f9");
	}
	
    $roomlist.show();
}

//当前日期点击切换效果
function dateClickToggle(obj){
	 //设置日期切换效果
	 var tdObj = $(obj);
	
     if(tdObj.hasClass("usable-date")){
         var selDate = tdObj.attr("date");
    	 $("#dayList tr td").removeClass("curr");
    	 $(obj).addClass("curr");
    	 $title.text(selDate);
    	 
    	 var selTradeCenterId = $("#bespeakForm input[name='selTradeCenterId']").val();
         getSignRoomList(selTradeCenterId,selDate);   //获取可预约房间信息
     }
}

//获取各个时间段房间列表信息
function getSignRoomList(selTradeCenterId,selDate){
	var strHtml = "<article class='aui-content'><ul class='aui-list aui-date-list'>";
	
	$.ajax({
		cache:false,
		async:true,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/getSignRoomInfoListByDate",
		data:{tradeCenterId:selTradeCenterId,selDate:selDate},
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					var signroomInfo = data[i];
					
					strHtml += "<li class='aui-list-item aui-padded-lr20' lang='" + signroomInfo.bespeakTime + "' onClick='selectSignroom(this)'>"
				 		+ "<div class='aui-info-item'>"
						+ "<i class='iconfont blue mr5'>&#xe605;</i>"
						+ "<span class='time'>" + signroomInfo.bespeakTime + "</span>"
						+ "</div>"
						+ "<div class='aui-info-item aui-text-grey'>";
			
					if(signroomInfo.signroomNumber == 0){
						strHtml += "会议室剩余间数<span class='aui-text-red mlr5'>0</span>间";
					}
					else {
						strHtml += "会议室剩余间数<span class='aui-text-blue mlr5'>" + signroomInfo.signroomNumber + "</span>间";
					}
					
					strHtml += "</div></li>";
				}
				
				strHtml += "</ul></article>";
			}
			else {
				strHtml = "<div class='nodata'><img src='" + ctx + "/image/nodata.png' width='100%' alt=''>"
						+ "<p class='text-center font16'>对不起，暂无数据！</p>"
						+ "<p class='text-center'>(请选择你所要的预约时间)</p></div>";
			}
			
			$("#signroomList").html(strHtml);
		}
	});
	
	return strHtml;
}

//选择某个时间段的签约室
function selectSignroom(obj){
	var $li = $(obj);
	
	var selDate;
	$("#dayList tr td").each(function(){
		if($(this).hasClass("curr")){
			selDate = $(this).attr("date");
			return false;
		}
	});
	
	var selBespeakTime = $li.find(".time").html();
	
	$("#bespeakForm #selBespeakTime").val(selBespeakTime);
	$("#bespeakForm #selResDate").val(selDate);
	
	$("#dateseLect").val(selDate + " " + selBespeakTime);
	$("#roomList").hide();
}

//保存签约室预约信息
function save(flag){
	var selTradeCenterId = $("#bespeakForm #selTradeCenter option:selected").val();
	var numberOfPeople = $("#bespeakForm #numberOfPeople").val();
	var selResDate = $("#bespeakForm #selResDate").val();
	var selBespeakTime = $("#bespeakForm #selBespeakTime").val();
	var propertyAddress = $("#bespeakForm #propertyAddress").val();
	var serviceSpecialist = $("#bespeakForm #serviceSpecialist").val();
	var specialRequirement = $("#bespeakForm #specialRequirement").val();
	
	var startTime = selBespeakTime.substring(0,selBespeakTime.indexOf("-"));
	var endTime = selBespeakTime.substring(selBespeakTime.indexOf("-") + 1,selBespeakTime.length);
	
	var startDate = selResDate + " " + startTime;
	var endDate = selResDate + " " + endTime;
	
	var transactItem = "";
	$(".transactItemCode").each(function(){
		if($(this).hasClass("select-mark")){
			transactItem += $(this).attr("id") + ",";
		}
	});
	
	transactItem = transactItem.substring(0,transactItem.lastIndexOf(","));
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/weixin/signroom/saveReservation",
		data: {flag:flag,startDate:startDate,endDate:endDate,serviceSpecialist:serviceSpecialist,resType:'0',propertyAddress:propertyAddress,numberOfParticipants:numberOfPeople,transactItemCode:transactItem,specialRequirement:specialRequirement,tradeCenterId:selTradeCenterId,selDate:selResDate,bespeakTime:selBespeakTime},
		success:function(data){
			var result = data.isSuccess;
			var operationHtml = "";
			
			if(result == "true"){
				$("#layuiFalse").hide();
				$("#layuiSuccess .yellow").html(data.resNo);
				$("#layuiSuccess #spnResDate").html(data.selDate);
				$("#layuiSuccess #spnResTime").html(data.bespeakTime);
				$("#layuiSuccess").show();
			}
			else if(result == "hasMinRoom"){
				operationHtml = "<div class='aui-btn aui-btn-info aui-btn-block aui-btn-mt10' onclick=\"save('accept')\">我愿意接受小房间</div>";
				operationHtml += "<div class='aui-btn aui-btn-grey aui-btn-block aui-btn-mt10' onClick='cancel();'>取消</div>";
				
				$("#layuiFalse #operation").html(operationHtml);
				$("#layuiFalse #message").html("该预约时段内没有符合条件的房间,是否愿意接受小房间？");
				$("#layuiFalse").show();
			}
			else if(result == "noRoom"){
				operationHtml = "<div class='aui-btn aui-btn-info aui-btn-block aui-btn-mt10' onClick='changeTime();'>换个时间试试</div>";
				operationHtml += "<div class='aui-btn aui-btn-grey aui-btn-block aui-btn-mt10' onClick='cancel();'>取消</div>";
				
				$("#layuiFalse #operation").html(operationHtml);
				$("#layuiFalse #message").html("该预约时段内没有可预约的房间");
				$("#layuiFalse").show();
			}
			else if(result == "false"){
				operationHtml = "<div class='aui-btn aui-btn-grey aui-btn-block aui-btn-mt10' onClick='cancel();'>取消</div>";
				
				$("#layuiFalse #operation").html(operationHtml);
				$("#layuiFalse #message").html("程序出现异常");
				$("#layuiFalse").show();
			}
			else if(result == "noBespeakNum"){
				operationHtml = "<div class='aui-btn aui-btn-grey aui-btn-block aui-btn-mt10' onClick='cancel();'>取消</div>";
				
				$("#layuiFalse #operation").html(operationHtml);
				$("#layuiFalse #message").html("您这两周的预约次数已用完");
				$("#layuiFalse").show();
			}
		}
	});
}

//验证表单
function checkForm(){
	var numberOfPeople = $("#bespeakForm #numberOfPeople").val();
	var selResDate = $("#bespeakForm #dateseLect").val();
	var propertyAddress = $("#bespeakForm #propertyAddress").val();
	var serviceSpecialist = $("#bespeakForm #serviceSpecialist").val();
	var transactItem = "";
	var isSelectTransactItem = false;
	
	if(numberOfPeople == ""){
		window.wxc.alert("请输入参与人数");
		//showTip("请输入参与人数");
		return false;
	}
	else {
		numberOfPeople = Number(numberOfPeople);
		
		if(numberOfPeople <= 0){
			showTip("请输入合法的参与人数");
    		return false;
		}
	}
	
	if(selResDate == ""){
		showTip("请选择预约时段");
		return false;
	}
	
	$(".transactItemCode").each(function(){
		if($(this).hasClass("select-mark")){
			transactItem += $(this).attr("id") + ",";
			isSelectTransactItem = true;
		}
	});
	
	if(!isSelectTransactItem){
		showTip("请选择办理事项");
		return false;
	}
	
	transactItem = transactItem.substring(0,transactItem.lastIndexOf(","));
	
	if(transactItem != "OpenRegularMeeting"){
		if(propertyAddress == ""){
    		showTip("请输入交易单地址");
    		return false;
    	}
    	
    	if(serviceSpecialist == ""){
    		showTip("请输入交易顾问");
    		return false;
    	}
	}
    	
	if(numberOfPeople == ""){
		showTip("请输入参与人数");
		return false;
	}
	else {
		numberOfPeople = Number(numberOfPeople);
		
		if(numberOfPeople <= 0){
			showTip("请输入合法的参与人数");
    		return false;
		}
	}
	
	return true;
}

//根据产证地址获取服务顾问
function getServiceSpecialistByPropertyAddr(propertyAddress){
	 $.ajax({
       		cache:false,
       		async:false,
       		type:"POST",
       		dataType:"text",
       		url:ctx+"/weixin/signroom/getServiceSpecialistByPropertyAddr",
       		data: {propertyAddress:propertyAddress},
       		success:function(data){
       			if(data != null && data != ""){
       				$("#serviceSpecialist").val(data);
	       			$("#serviceSpecialist").attr("readonly",true);
	       			$("#serviceSpecialist").css("color","#808080");
       			}
       			else {
       				var serviceSpecialist = $("#serviceSpecialist").val();
	       			$("#serviceSpecialist").attr("readonly",false);
       			}
       		}
       	});
}

//转向我的预约列表
function goToMyReservationList(){
	location.href = ctx + "/weixin/signroom/myReservationList";
}

//隐藏提示信息框
function cancel(){
	$("#layuiFalse").hide();
}

//换个时间试试
function changeTime(){
	$("#layuiFalse").hide();
	popSelectResInfo();
}


//显示提示信息
function showTip(message){
	$(".zvalid-resultformat").html(message);
	$(".field-tooltipWrap").show(300).delay(1500).hide(300); 
}

//获取当前时间前后N天前后日期
function getDateStr(AddDayCount) { 
   var arrayDate = [];
	
   var dd = new Date();  
   dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期  
   var y = dd.getFullYear();   
   var m = (dd.getMonth()+1) < 10 ? "0" + (dd.getMonth()+1) : (dd.getMonth()+1);//获取当前月份的日期，不足10补0  
   var day = dd.getDate();
   var week = dd.getDay();
   var day1 = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
   
   arrayDate[0] = y +"-" + m + "-" + day1;
   arrayDate[1] = day;
   arrayDate[2] = week;
   
   return arrayDate;   
} 

//日期函数补零
function Appendzero (obj) {
    if (obj < 10) return "0" + obj; else return obj;
}

function getDateWeek () {
    var now = new Date ();
    var year = now.getFullYear ();//获取四位数年数
    var month = now.getMonth () + 1;
    var date = now.getDate ();
    var weeknum = now.getDay ();
    var hour = now.getHours ();
    var minute = now.getMinutes ();
    var second = now.getSeconds ();
    var nowdate = year + "-" + Appendzero (month) + "-" + Appendzero (date);
    return nowdate;
};

