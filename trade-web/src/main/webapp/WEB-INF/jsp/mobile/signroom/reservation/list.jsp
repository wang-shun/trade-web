<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/iconfont/iconfont.css" />
    <!--time css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/mobiscroll.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/mobiscroll_date.css" />

</head>
<body>

	<form id="form1" action="${ctx }/mobile/reservation/bespeakUI" method="post">
		<input type="hidden" id="orgId" name="orgId" value="${orgId }"/>
		<input type="hidden" id="inputSelDate" name="inputSelDate"/>
		<input type="hidden" id="inputBespeakTime" name="inputBespeakTime"/>
	</form>
	
	<input type="hidden" id="ctx" value="${ctx }"/>
	
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">签约室预约</div>
    </header>
    <section class="aui-row aui-bar-btn-full select-bar">
        <div class="aui-col-xs-4 ui-select select" id="selectCity">
            <select name="" id="selTradeCenter">
                <option value="">浦东新区</option>
                <option value="">普陀区</option>
                <option value="">长宁区</option>
            </select>
            <span class="seat">
                <i class="iconfont iconfont12">&#xe604;</i>
            </span>
        </div>
        <div class="aui-col-xs-4 select"><input type="text" name="seldate" id="SelDate" readonly class="input text-center seldate" value="2016-09-16" /></div>
        <div class="aui-col-xs-4 ui-select select" id="selectTime">
            <select name="" id="selBespeakTime">
                <option value="">预约时间</option>
                <option value="">9:00-11:00</option>
                <option value="">11:00-13:00</option>
                <option value="">13:00-15:00</option>
                <option value="">15:00-17:00</option>
                <option value="">17:00-19:00</option>
            </select>
            <span class="seat">
                <i class="iconfont iconfont12">&#xe604;</i>
            </span>
        </div>
    </section>
    
    <section id="reservationList">
    
    </section>
</body>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/api.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/layer.js"></script>

<!--time-->
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/mobiscroll_date.js" charset="gb2312"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/mobiscroll.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/calendar.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/list.js?v=1.0.3"></script>

</html>