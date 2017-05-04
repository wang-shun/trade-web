<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/iconfont/iconfont.css" />
    <!--date css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/plugins/mobiscroll/mobiscroll.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/plugins/mobiscroll/mobiscroll_date.css" />
</head>

<body>
    <header class="aui-bar aui-bar-nav">
        <div class="aui-title">提交成功</div>
    </header>
    <section class="aui-content aui-margin-b-15">
        <div class="submit-result text-center">
            <i class="iconfont submit-success cyan">&#xe606;</i>
            <h2 class="dialog-head font22">提交成功</h2>
            <div class="dialog-text">
                <p>产调申请已提交誉萃人工处理，将于24小时内回复</p>
                <p>如有任何疑问请联系以下工作人员</p>
               	<c:forEach items="${userList}" var="item">
						<p class="dialog-text-tag">${item.realName }-<a href="tel:${item.mobile }">${item.mobile }</a></p>
				</c:forEach>
            </div>
        </div>
        <div class="plr15 mt28"><a href="${ctx}/weixin/property/myProperty" class="aui-btn aui-btn-info aui-btn-block aui-btn-user">返回</a></div>
    </section>
</body>
</html>
