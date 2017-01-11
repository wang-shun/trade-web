<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
	<link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="${ctx }/css/eachartdata/jquery.fullPage.css" />
    <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css" />

</head>
<body style="background-color: #fff">

<ul id="menu">
    <li data-menuanchor="firstPage"><a href="#firstPage">图表一</a></li>
    <li data-menuanchor="secondPage"><a href="#secondPage">图表二</a></li>
    <li data-menuanchor="3rdPage"><a href="#3rdPage">图表三</a></li>
    <li data-menuanchor="4thpage"><a href="#4thpage">图表四</a></li>
</ul>

<div id="fullpage">
    <div class="section " id="section0">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData1" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section1">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData2" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section2">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData3" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section3">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData4" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div> 
</div>
<script  src="${ctx }/js/jquery-2.1.1.js"></script>
<script  src="${ctx }/js/eachartdata/jquery-ui.min.js"></script>
<script  src="${ctx }/js/eachartdata/scrolloverflow.js"></script>
<script  src="${ctx }/js/eachartdata/jquery.fullPage.js"></script>
<script  src="${ctx }/js/eachartdata/fullPage-setting.js"></script>
</body>
</html>
