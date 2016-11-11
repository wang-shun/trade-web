<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<meta charset="utf-8">
<style type="text/css">
body {
	background-color: #f3f3f4;
}
</style>
</head>

<body>
	<h2>测试微信扫码登录:</h2>
	<div id="login_container">
	</div>
	<content tag="local_script">
		<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
		<script>
		var obj = new WxLogin({
            id:"login_container", 
            appid: "wx7b47806a86fe27db", 
            scope: "snsapi_base", 
            redirect_uri: "http://trade.centaline.com:8083/trade-web/weixin/property/myProperty",
            state: "1",
            style: "",
            href: "222"
          });
		</script>
	</content>
</body>
</html>
