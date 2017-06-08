<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <title>一周核心数据报表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet"/>
	<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

    <link rel="stylesheet" href="<c:url value='/css/eachartdata/jquery.fullPage.css' />" />
    <link rel="stylesheet" href="<c:url value='/css/eachartdata/eachartdata.css' />" />
    <!--弹出框样式  -->
    <link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">

    <script type="text/javascript">
		//用户动态选择的年月（月份要小1）
		var yearDisplay,monthDisplay;
		var week = "";
		var weekParam = [];
		var now = new Date();	
		//上一个统计周的最后一天(周四)
		if(now.getDay() == 5 || now.getDay() == 6 || now.getDay() == 0){
			now.setDate(now.getDate()-(now.getDay() == 0?7:now.getDay())+4); 
		}else{
			now.setDate(now.getDate()-(now.getDay() == 0?7:now.getDay())-3); 
		}				
		var lastWeekEnd = now;
		//当前年月
		var yearNow = lastWeekEnd.getFullYear();
		var monthNow = lastWeekEnd.getMonth();
		monthDisplay = monthNow+1;
		yearDisplay = yearNow;
	</script>
</head>
<body style="background-color: #fff"  id="bd1">

<ul id="menu">
    <li attr="1" class="nav_move active" style="background: #faab58;"><a href="#firstPage">贷款流失率</a></li>
    <li attr="2" class="nav_move"><a href="#secondPage">评估转化率</a></li>
    <li attr="3" class="nav_move"><a href="#3rdPage">金融产品转化率-卡类</a></li>
    <li attr="4" class="nav_move"><a href="#4thPage">金融产品转化率-贷款</a></li>
</ul>

<div id="fullpage">
    <div class="section" id="section0">
        <iframe id="iframe1" src="${ctx}/report/elist1" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section1">
        <iframe id="iframe2" src="${ctx}/report/elist2" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section2">
        <iframe id="iframe3" src="${ctx}/report/elist3" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div> 
    <div class="section" id="section3">
        <iframe id="iframe4" src="${ctx}/report/elist4" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
</div>
<script  src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script  src="<c:url value='/js/eachartdata/jquery-ui.min.js' />"></script>
<script  src="<c:url value='/js/eachartdata/scrolloverflow.js' />"></script>
<script  src="<c:url value='/js/eachartdata/jquery.fullPage.js' />"></script>

<script  src="<c:url value='/js/eachartdata/getMonthWeek.js' />"></script>
<!-- 引入弹出框js文件 -->
<script src="<c:url value='/js/common/xcConfirm.js' />"></script>
<script  src="<c:url value='/js/eachartdata/fullPage-setting.js' />"></script>
<script type="text/javascript">
//声明各iframe
var iframe1 = $("#iframe1")[0];
var iframe2 = $("#iframe2")[0];
var iframe3 = $("#iframe3")[0];
var iframe4 = $("#iframe4")[0];
var iframesArr = [iframe1,iframe2,iframe3,iframe4];

//周索引
var weekIndex = getWeeks(yearDisplay, monthDisplay, lastWeekEnd.getDate()).length - 1;
//初始页面时的周索引
var defalutWeekIndex = weekIndex;

$.each(iframesArr,function(i,item){
	item.onload = function(){
		if(item.id == 'iframe1'){
		   reRenderChart(item);
		}
	 
	    //年份加减
	   	$(item.contentWindow.document).find("#subtract").click(function(){
	   		monthDisplay--;
	   		if(monthDisplay == 0){
	   			yearDisplay--;
	   			monthDisplay=12;
	   		}
	   		changeBtnClass(item);
	   	 	item.contentWindow.reloadGrid(); 
	   	})
	   	$(item.contentWindow.document).find("#add").click(function(){
	   		if(yearDisplay == yearNow && monthDisplay == (monthNow+1)){
	   			return false;
	   		}
	   		monthDisplay++;
	   		if(monthDisplay==13){
	   			yearDisplay++;
	   			monthDisplay=1;
	   		}
	   		changeBtnClass(item);
	   		item.contentWindow.reloadGrid();
	    })
	}
})

function reRenderChart(item) {	
	//改变按钮样式
	changeBtnClass(item);
	//初始化
	item.contentWindow.reloadGrid();
}

function changeBtnClass(item){
	//默认上周日所在年月
	$(item.contentWindow.document).find(".calendar-year span")
	                              .html(yearDisplay+'年'+(monthDisplay>9?monthDisplay:"0"+monthDisplay)+'月');
	
	//点击变换颜色
	var spanHtml = getSpanHtml(yearDisplay, monthDisplay, 0);	
	$(item.contentWindow.document).find(".calendar-month").html(spanHtml);
	
	//默认当前索引对应的周,没有就取最后一个月份
    var weekIndexSpan = $(item.contentWindow.document).find(".calendar-month span:eq("+weekIndex+")");
    if(weekIndexSpan.length > 0){
        weekIndexSpan.addClass("select-blue").siblings().removeClass("select-blue");
    }else{
        $(item.contentWindow.document).find(".calendar-month span:last").addClass("select-blue").siblings().removeClass("select-blue");
        weekIndex = $(item.contentWindow.document).find(".calendar-month span:last").index();
    }
	
	week = $(item.contentWindow.document).find(".select-blue").html();
	weekParam = convertWeekForSql(week);
	
	var $month_list = $(item.contentWindow.document).find(".calendar-month span");  
	$month_list.on("click",function() {
		if($(this).hasClass("disabled")){
			return false;
		}
		$(this).addClass("select-blue").siblings().removeClass("select-blue");
		week = $(this).text();
		weekParam = convertWeekForSql(week);
		weekIndex = $(this).index();
		item.contentWindow.reloadGrid();
	});	
	//年月置灰 + 星期置灰 
	if(yearDisplay == yearNow && monthDisplay == (monthNow+1)){
		$(item.contentWindow.document).find("#add em").addClass("disabled");
		$(item.contentWindow.document).find(".calendar-month span:gt("+defalutWeekIndex+")").addClass("disabled");
	}else{
		$(item.contentWindow.document).find("#add em").removeClass("disabled");
		$(item.contentWindow.document).find(".calendar-month span:gt("+defalutWeekIndex+")").removeClass("disabled");
	}   
}
</script>
</body>
</html>