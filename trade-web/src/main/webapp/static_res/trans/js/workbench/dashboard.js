var isRTL = false;

if ($('body').css('direction') === 'rtl') {
	isRTL = true;
}
//
function setStaDetailDef() {
	$("tr[id^='sta_tr']").find('td:gt(0)').each(function(i) {
		$(this).html('金额：<span>0.00万</span>单数：<span>0</span>转化率：<span>0.00%</span>');
	});
}
function setStaVal(f, s, l) {
	setStaValItem(f, 'td:eq(1)');
	setStaValItem(s, 'td:eq(2)');
	setStaValItem(l, 'td:eq(3)');
}
/*
function setDonut(d1, d2) {
	$("#doughnutChart1").empty();
	$("#doughnutChart2").empty();
	if (!d1 || $.isEmptyObject(d1)) {
		 $("#bt_1").addClass("hide");
	} else {
		$("#bt_1").removeClass("hide");
	}
	if (!d2 || $.isEmptyObject(d2)) {
		$("#bt_2").addClass("hide");
	} else {
		$("#bt_2").removeClass("hide");
	}
	Morris.Donut({
		element : 'doughnutChart1',
		data : d1,
		resize : true,
		colors : [ '#87d6c6', '#54cdb4', '#1ab394' ],
	});
	Morris.Donut({
		element : 'doughnutChart2',
		data : d2,
		resize : true,
		colors : [ '#87d6c6', '#54cdb4', '#1ab394' ],
	});
}*/
function setStaValItem(d, c) {
	if (d) {
		$(d).each(function(i) {
			var _this = this;
/*			var showhtml = "金额：<span>" + _this.amount + "</span>单数：<span>"
				+ _this.count + "</span>转化率：<span>" + (_this.convRate?_this.convRate:'00.0') + "%</span>";*/
			var showhtml = "金额：<span>" + (_this.amount/10000).toFixed(2) + "万</span>单数：<span>"
			+ _this.count + "</span>转化率：<span>" + (_this.convRate?_this.convRate:'00.0') + "%</span>";
			$("#sta_tr_" + _this.staItem).find(c).html(showhtml);
		});
	}
}
/*
function toDonutData(d, it) {
	var dd = new Array();
	if (d) {
		$(d).each(function(i) {
			dd.push({
				label : this['staItemStr'],
				value : $.unformatMoney(this[it] + "")
			});
		});
	}
	return dd;
}*/
var handleScrollers = function() {
	$('.scroller').each(function() {
		$(this).slimScroll({
			size : '7px',
			color : '#a1b2bd',
			position : isRTL ? 'left' : 'right',
			height : $(this).attr("data-height"),
			alwaysVisible : ($(this).attr("data-always-visible") == "1" ? true : false),
			railVisible : ($(this).attr("data-rail-visible") == "1" ? true : false),
			disableFadeOut : true
		});
	});
};
handleScrollers();

//待办事项数据
$(document).ready(function() {	
	new messageGrid().init({
		e : $("#div_messagelist1"),
		u : appCtx['aist-message-web'],
		c : 'MSG_YU_RESPONSE',
		ctx : ctx
	});
	new messageGrid().init({
		e : $("#div_messagelist2"),
		u : appCtx['aist-message-web'],
		c : 'MSG_YU_WORK',
		ctx : ctx
	});
	new messageGrid().init({
		e : $("#div_messagelist3"),
		u : appCtx['aist-message-web'],
		c : 'MSG_YU_STOPLOSS',
		ctx : ctx
	});
	
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
	});

	$('#external-events div.external-event').each(function() {
		$(this).data('event', {
			title : $.trim($(this).text()), // use the element's
															// text as the event
															// title
			stick : true
						// maintain when user navigates (see docs on the
						// renderEvent method)
		});
		$(this).draggable({
			zIndex : 1111999,
			revert : true, // will cause the event to go back
											// to its
			revertDuration : 0
						// original position after the drag
		});
	});
	var backEvents = [];
	// 获取待办事项数据
	$.ajax({
		//url : ctx + '/transplan/getToTransPlan', //mybatis查询
		url : ctx + '/transplan/qqToGetTransPlan', //快速查询		
		data : "",
		type : "post",
		dataType : "json",
		async : true,
		success : function(data) {
			//console.log("！！！！！！！！！Result！！！！！！！！"+JSON.stringify(data));
			if (data.toTransPlanOrToPropertyInfoList == null)
				return;			
			
			var toTransPlanOrToPropertyInfoList = data.toTransPlanOrToPropertyInfoList;			
			//var events = [];		
			for (var i = 0; i < toTransPlanOrToPropertyInfoList.length; i++) {
				
				var calssstyle = "popy";
				var partCode = toTransPlanOrToPropertyInfoList[i].partCode;
				var estPartTimes = toTransPlanOrToPropertyInfoList[i].estPartTime;
				var propertyAddr = toTransPlanOrToPropertyInfoList[i].propertyAddr;
				
				if (partCode == undefined || partCode=='' || partCode==null) {
					continue;
				}
				if (propertyAddr == undefined || propertyAddr=='' || propertyAddr==null) {
					propertyAddr = "无";
				}
				if(estPartTimes == undefined || estPartTimes=='' || estPartTimes==null) {
					continue;
				}			
				backEvents.push({
					title : partCode + "\n 物业地址:" + propertyAddr,
					start : estPartTimes,
					backgroundColor : '#f8ac59',
					borderColor : "#f8ac59"
				});		
			}
			$('#calendar').fullCalendar({
				header : {
					left : 'prev,next,month,basicWeek,today',
					center : 'title',
					right : ''
				},
				editable : false,
				events : backEvents

			});	

			
		}
	});
	
	

	$("#ionrange_4").ionRangeSlider({
		values : [ "一月", "二月", "三月", "四月", "五月", "六月",
			"七月", "八月", "九月", "十月", "十一月", "十二月" 
		],
		dateType : 'single',
		hasGrid : true
	});

});
jQuery(document).ready(function() {
	$(".fancybox").fancybox({
		maxWidth : 650,
		maxHeight : 450,
		fitToView : false,
		width : '70%',
		height : '55%',
		autoSize : false,
		closeClick : false,
		openEffect : 'none',
		closeEffect : 'none'
	});

	$('#alertOper').fancybox({
		type : 'iframe',
		padding : 0,
		margin : 0,
		autoScale : false,
		fitToView : false,
		width : '70%',
		height : '55%',
		autoDimensions : true,
		showCloseButton : true,
		close : true,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		iframe : {
			preload : false
		},
		openEffect : 'none',
		closeEffect : 'none',
		afterClose : function() {
			jQuery("#table_knowledge_list").trigger("reloadGrid");// 刷新列表
		}
	});

});

function reloadMonth() {
	var month = new Date().getMonth();
	var maxMonths = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
			"九月", "十月", "十一月", "十二月");
	var w = $(".irs-line").width() / 12 * (month) + 12.5;
	// 月份位置/添加月份文字/光标位置
	$(".irs-single").attr("style", "left: " + (month / 12 * 100 + 3) + "%;");
	$(".irs-single").text(maxMonths[month]);
	$(".irs-slider").attr("style", "left: " + (month / 12 * 100 + 3) + "%;");
	$("#ionrange_4").val(month);

	// 未申请，面签，放款金额加上链接
	addLinkHref(month + 1, '');
}
