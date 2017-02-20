<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>一周核心数据报表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/font-awesome.css" rel="stylesheet"/>
	<link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="${ctx}/css/eachartdata/jquery.fullPage.css" />
    <link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css" />
    <!--弹出框样式  -->
    <link href="${ctx}/css/common/xcConfirm.css" rel="stylesheet">

    <script type="text/javascript">
		//用户动态选择的年月（月份要小1）
		var yearDisplay,monthDisplay;
		var num=-1;
		var week="";
		var now = new Date();
		//当前年月
		var yearNow = now.getFullYear();
		var monthNow = now.getMonth();
			monthDisplay = (monthNow+1);
			yearDisplay = yearNow;
		//当前月份的上个月对应的年月
		var yearLast = yearDisplay;
		var monthLast = monthDisplay;
	</script>
</head>
<body style="background-color: #fff">

<ul id="menu">
    <li data-menuanchor="firstPage"><a href="#firstPage">贷款流失率</a></li>
    <li data-menuanchor="secondPage"><a href="#secondPage">评估转化率</a></li>
    <li data-menuanchor="3rdPage"><a href="#3rdPage">金融产品转化率-卡类</a></li>
    <li data-menuanchor="4thPage"><a href="#4thPage">金融产品转化率-贷款</a></li>
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
<script  src="${ctx }/js/jquery-2.1.1.js"></script>
<script  src="${ctx }/js/eachartdata/jquery-ui.min.js"></script>
<script  src="${ctx }/js/eachartdata/scrolloverflow.js"></script>
<script  src="${ctx }/js/eachartdata/jquery.fullPage.js"></script>
<script  src="${ctx }/js/eachartdata/fullPage-setting.js"></script>
<script  src="${ctx }/js/eachartdata/getMonthWeek.js"></script>
<!-- 引入弹出框js文件 -->
<script src="${ctx}/js/common/xcConfirm.js?v=1.0.1"></script>
<script type="text/javascript">
//声明各iframe
var iframe1 = document.getElementById("iframe1");
var iframe2 = document.getElementById("iframe2"); 
var iframe3 = document.getElementById("iframe3");
var iframe4 = document.getElementById("iframe4");
var iframesArr = [iframe1,iframe2,iframe3,iframe4];

$.each(iframesArr,function(i,item){
	item.onload = function(){
		if(item.id == 'iframe1'){
		   reRenderChart(item);
		}
	 
	    //年份加减
	   	$(item.contentWindow.document).find("#subtract").click(function(){
	   		monthDisplay--;
	   		if(monthDisplay==0){
	   			yearDisplay--;
	   			monthDisplay=12;
	   		}
	   		changeBtnClass(item);
	   	/* 	item.contentWindow.reloadGrid(); */
	   	})
	   	$(item.contentWindow.document).find("#add").click(function(){
	   		if(yearDisplay == yearLast&&monthDisplay==monthLast){
	   			return false;
	   		}
	   		monthDisplay++;
	   		if(monthDisplay==13){
	   			yearDisplay++;
	   			monthDisplay=1;
	   		}
	   		changeBtnClass(item);
	   	/* 	item.contentWindow.reloadGrid(); */
	    })
	}
})

function reRenderChart(item) {
	changeBtnClass(item)

	
}

function changeBtnClass(item){
	//点击变换颜色&&默认当前月份
	var spanHtml=getspanHtml(yearDisplay,monthDisplay,0);
	if(yearDisplay == yearLast&&monthDisplay == monthLast){
		spanHtml=getspanHtml(yearDisplay,monthDisplay,now.getDate());
		num=-1;
	}
	$(item.contentWindow.document).find(".calendar-month").html(spanHtml);
	$(item.contentWindow.document).find(".calendar-month span:eq("+num+")").addClass("select-blue").siblings().removeClass("select-blue");
	if(num==-1){
		$(item.contentWindow.document).find(".calendar-month span:last").addClass("select-blue").siblings().removeClass("select-blue");		
	} 
	week=$(item.contentWindow.document).find(".select-blue").html();
	var $month_list = $(item.contentWindow.document).find(".calendar-month span"); 
	$month_list.on("click",function() {
		if($(this).hasClass("disabled")){
			return false;
		}
 		num = parseInt($(this).attr("value")); 
		$(this).addClass("select-blue").siblings().removeClass("select-blue");
		week=$(this).context.innerHTML;
		item.contentWindow.reloadGrid();
	});
	$(item.contentWindow.document).find(".calendar-year span").html(yearDisplay+'年'+(monthDisplay>9?monthDisplay:"0"+monthDisplay)+'月');
	//年份置灰
	if(yearDisplay == yearLast&&monthDisplay == monthLast){
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
	item.contentWindow.reloadGrid(); 
}
</script>
</body>
</html>