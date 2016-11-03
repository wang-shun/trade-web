/**
 * calendar.js
 * 日历参数设置
 */
function Appendzero (obj) {
    if (obj < 10) return "0" + obj; else return obj;
}

function show(){
	var mydate = new Date();
	var str = "" + mydate.getFullYear() + "-";
	str += Appendzero((mydate.getMonth()+1)) + "-";
	str += Appendzero(mydate.getDate());
	return str;
}
$("#SelDate").val(show());

$(function () {
	var currYear = (new Date()).getFullYear();
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'android-ics light', //皮肤样式
		display: 'modal', //显示方式
		mode: 'scroller', //日期选择模式
		dateFormat: 'yyyy-mm-dd',
		lang: 'zh',
		showNow: true,
		dateOrder: 'yymmdd', //面板中日期排列格式(可以设置)
		nowText: "今天",
		startYear: currYear - 50, //开始年份
		endYear: currYear + 10 //结束年份
	};

	$("#SelDate").mobiscroll($.extend(opt['date'], opt['default']));

});