<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>过户数据报表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/font-awesome.css" rel="stylesheet"/>
	<link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

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
    <li data-menuanchor="firstPage"><a href="#firstPage">过户基本数据统计</a></li>
    <li data-menuanchor="secondPage"><a href="#secondPage">过户贷款流失单数统计</a></li>
    <li data-menuanchor="3rdPage"><a href="#3rdPage">过户贷款流失金额统计</a></li>
    <li data-menuanchor="4thPage"><a href="#4thPage">过户贷款流失银行分布</a></li>
</ul>

<div id="fullpage">
    <div class="section">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData1"  id="iframe1" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData2"  id="iframe2" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData3"  id="iframe3" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div> 
    <div class="section">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData4"  id="iframe4" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
</div>
<script  src="${ctx }/js/jquery-2.1.1.js"></script>
<script  src="${ctx }/js/eachartdata/jquery-ui.min.js"></script>
<script  src="${ctx }/js/eachartdata/scrolloverflow.js"></script>
<script  src="${ctx }/js/eachartdata/jquery.fullPage.js"></script>
<script  src="${ctx }/js/eachartdata/fullPage-setting.js"></script>
</body>
</html>
