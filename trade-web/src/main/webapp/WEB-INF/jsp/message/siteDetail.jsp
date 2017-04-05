<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@page import="com.aist.common.utils.SpringUtils"%>
<%@page import="com.aist.uam.permission.remote.UamPermissionService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.aist.uam.permission.remote.vo.App"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="com.centaline.trans.user.web.MenuConstants"%>
<%@page import="com.aist.uam.permission.remote.vo.Menu"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%

if(pageContext.getSession().getAttribute("ctx")==null){
	UamPermissionService uamPermissionService = SpringUtils.getBean("uamPermissionServiceClient");
	String appName = pageContext.getServletContext().getInitParameter("appName");
	App app = uamPermissionService.getAppByAppName(appName);
	pageContext.getSession().setAttribute("ctx", app.genAbsoluteUrl());
	
	List<App> appList = uamPermissionService.getAllApp();
	pageContext.getSession().setAttribute("appCtxList", appList);
	Map<String, String> appCtx = new HashMap<String,String>(appList.size());
	for(App appitem : appList){
		appCtx.put(appitem.getAppName(), appitem.genAbsoluteUrl());
	}
	pageContext.getSession().setAttribute("appCtx", appCtx);
}
%>
<HTML>
<HEAD>
<TITLE>SOA</TITLE>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />

<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${ctx}/media/css/jquery.gritter.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/media/css/daterangepicker.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/media/css/fullcalendar.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/css/jqvmap.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="${ctx}/media/css/jquery.easy-pie-chart.css" rel="stylesheet"
	type="text/css" media="screen" />
	
<!-- START jqGrid STYLES -->
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/css/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/css/ui.jqgrid.css" />
<!-- END jqGrid STYLES-->
	
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="${ctx}/media/image/favicon.ico" />
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<content tag="local_script">
<script type="text/javascript">
        $(function(){
        	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
        	$("#siteIframe").attr("src",appCtx['aist-message-web']+"/site/siteDetail.html");
        });
        //保存
        function save(){
        	$("#form1").submit();
        }
        //ajax提交回调函数
        function formCallBack(data){
            if(data){
                closeDialog({dialogId:"appEditDialog"});
            }else{
            	$.ligerDialog.error('保存失败!');
            }
        }
    </script>
</HEAD>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>
<div class="contentbox top5">
	<iframe id="siteIframe"></iframe>
 </div>
</body>
</html>