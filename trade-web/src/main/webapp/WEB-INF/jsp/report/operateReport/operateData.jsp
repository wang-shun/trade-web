<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>运营大数据</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/animate.css" rel="stylesheet"/>
    <link href="${ctx}/static/css/style.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/css/eachartdata/jquery.fullPage.css" />
    <link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css" />
    <script type="text/javascript">
		//用户动态选择的年月（月份要小1）
		var yearDisplay,monthDisplay;
		var now = new Date();
		//当前年月
		var yearNow = now.getFullYear();
		var monthNow = now.getMonth();
		if(monthNow == 0){
			monthDisplay = 11;
			yearDisplay = yearNow - 1;
		}else{
			monthDisplay = monthNow - 1;
			yearDisplay = yearNow;
		}
		//当前月份的上个月对应的年月
		var yearLast = yearDisplay;
		var monthLast = monthDisplay;
	</script>
</head>
<body style="background-color: #fff">

<ul id="menu">
    <li data-menuanchor="firstPage"><a href="#firstPage">贷款签约与过户对比</a></li>
    <li data-menuanchor="secondPage"><a href="#secondPage">过户数据</a></li>
    <li data-menuanchor="3rdPage"><a href="#3rdPage">签约贷款</a></li>
</ul>

<div id="fullpage">
    <div class="section " id="section0">
        <iframe id="iframe1" src="${ctx}/report/opreate1" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div  class="section" id="section1">
        <iframe id="iframe2" src="${ctx}/report/opreate2" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section2">
        <iframe id="iframe3" src="${ctx}/report/opreate3" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
</div>
<script src="${ctx}/js/jquery-2.1.1.js"></script>
<script src="${ctx}/js/eachartdata/jquery-ui.min.js"></script>
<script  src="${ctx}/js/eachartdata/scrolloverflow.js"></script>
<script  src="${ctx}/js/eachartdata/jquery.fullPage.js"></script>
<script  src="${ctx}/js/eachartdata/fullPage-setting.js"></script>
<script type="text/javascript">
//声明各iframe
var iframe1 = document.getElementById("iframe1");
var iframe2 = document.getElementById("iframe2"); 
var iframe3 = document.getElementById("iframe3");
var iframesArr = [iframe1,iframe2,iframe3];

$.each(iframesArr,function(i,item){
	item.onload = function(){
		if(item.id == 'iframe1'){
		   reRenderChart(item);
		}
	 
	    //年份加减
	   	$(item.contentWindow.document).find("#subtract").click(function(){
	   		yearDisplay--;
	   		changeBtnClass(item);
	   		item.contentWindow.reloadGrid();
	   	})
	   	$(item.contentWindow.document).find("#add").click(function(){
	   		if(yearDisplay == yearLast){
	   			return false;
	   		}
	   		yearDisplay++;
	   		changeBtnClass(item);
	   		item.contentWindow.reloadGrid();
	    })
	}
})

function reRenderChart(item) {
	//改变按钮样式
	changeBtnClass(item);
	//初始化
		$(item.contentWindow.document).find(".calendar-month span:eq("+monthDisplay+")").addClass("select-blue").siblings().removeClass("select-blue");
	item.contentWindow.reloadGrid();      
	//点击变换颜色&&默认当前月份
	//$(item.contentWindow.document).find(".calendar-month span:eq("+monthDisplay+")").addClass("select-blue").siblings().removeClass("select-blue");
	var $month_list = $(item.contentWindow.document).find(".calendar-month span");  
	$month_list.on("click",function() {
		if($(this).hasClass("disabled")){
			return false;
		}
		monthDisplay = parseInt($(this).attr("value")) - 1;
		$(this).addClass("select-blue").siblings().removeClass("select-blue");
		item.contentWindow.reloadGrid();
	});
}

function changeBtnClass(item){
	$(item.contentWindow.document).find(".calendar-year span").html(yearDisplay);
    //年份置灰
	if(yearDisplay == yearLast){
		$(item.contentWindow.document).find("#add em").addClass("disabled");
		$(item.contentWindow.document).find(".calendar-month span:gt("+monthLast+")").addClass("disabled");
	}else{
		$(item.contentWindow.document).find("#add em").removeClass("disabled");
		$(item.contentWindow.document).find(".calendar-month span:gt("+monthLast+")").removeClass("disabled");
	}
    //月份置灰
    if(yearDisplay == yearLast && monthDisplay == monthLast){
    	$(item.contentWindow.document).find(".calendar-month span:gt("+monthLast+")").removeClass("disabled").addClass("disabled");
    }
}
</script>
</body>
</html>
