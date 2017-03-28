<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>		
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/aui.2.0.css" />
	<link rel="stylesheet" href="${ctx}/static/css/mobile-yc-style3.css" >
	<link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/iconfont/iconfont.css" />
</head>
<body class="">
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">登录</div>
    </header>
    <section class="aui-content false-login">
        <img src="${ctx}/static/img/false.png" width="100%" alt="" />
        <h3 style="text-align:center">对不起，您暂无登录权限！</h3>
        <p style="text-align:center">请联系管理员！</p>
    </section>
</body>