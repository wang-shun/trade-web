<%@page import="com.aist.common.utils.SpringUtils"%>
<%@page import="com.aist.uam.permission.remote.UamPermissionService"%>
<%@page import="com.aist.uam.permission.remote.vo.App"%>
<%@page import="com.aist.uam.permission.remote.vo.Menu"%>
<%@page import="com.centaline.trans.user.web.MenuConstants"%>
<%@page import="com.centaline.trans.utils.URLAvailability"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="sitemesh"
           uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
Menu menu = MenuConstants.getMenu();

request.setAttribute("menuVO", menu.getChildren());

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
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

    <title>誉萃交易系统</title>
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/css/animate.css" rel="stylesheet">
    
    <sitemesh:head></sitemesh:head>
    
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
.m_menu>a>span{
	 display: block!important;
	 font-size:13px;
	 width:100%;
	 text-align:center
}
.m_menu>a{
	  padding: 14px 0px 14px 0px!important;
}
</style>
</head>

<body>
    <div id="wrapper">
    <div class="modal hide" id="logoutmodal" >
    <div class="modal-body">
     <img alt="" src="${ctx}/img/load.jpg"><span>正在退出</span>
    </div>
 
</div>
    	<!-- 左侧菜单栏 -->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="logo-element">
                            誉萃
                        </div>
                    </li>
                    <li class="m_menu">
				     <a href="${ctx}/workspace/mobile/mainPage"><span class="nav-label">首页</span></a>
		           </li>
		            <li class="m_menu">
				     <a href="${ctx}/workspace/mobile/dashboard"><span class="nav-label">消息</span></a>
		           </li>
		           <li class="m_menu">
				     <a href="${ctx}/attendCheck/mobile/attendManage"><span class="nav-label">考勤</span></a>	
				     <ul class="nav nav-second-level" >
						<li><a href="${ctx}/attendCheck/mobile/attendManage?<%=new Date().getTime() %>"><i class="fa "></i>
						考勤</a></li>
						<li><a href="${ctx}/attendCheck/mobile/attendList?<%=new Date().getTime() %>"><i class="fa "></i>
						考勤历史</a></li>
		
					</ul>	
		           </li>
		           <li class="m_menu">
				   <a href="#"><span class="nav-label">E估</span></a>
		           <ul class="nav nav-second-level" >
						<li><a href="${ctx}/task/mobile/pricingApply?<%=new Date().getTime() %>"><i class="fa "></i>
						E估询价</a></li>
						<li><a href="${ctx}/task/mobile/eguPricingList?<%=new Date().getTime() %>"><i class="fa "></i>
						询价历史记录</a></li>
						<li><a href="${ctx}/task/mobile/reportList?<%=new Date().getTime() %>"><i class="fa "></i>
						报告单历史记录</a></li>
					</ul>
		           </li> 
		           <li class="m_menu">
				     <a href="${ctx}/task/mobile/myTaskList"><span class="nav-label">任务</span></a>
		           </li>
		           <li class="m_menu">
				     <a href="${ctx}/property/mobile/propertyList"><span class="nav-label">产调</span></a>
		           </li> 
                </ul>

            </div>
        </nav>

		<!-- 右侧内容 -->
        <div id="page-wrapper" class="gray-bg dashbard-1">
        
        	<!-- 右侧页面抬头 -->
	        <div class="row border-bottom">
	        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
		        <div class="navbar-header">
		            <a class="navbar-minimalize minimalize-styl-2 btn btn-warning " href="#"><i class="fa fa-bars"></i> </a>
		           <%--  <form role="search" class="navbar-form-custom" action="search_results.html">
		                <div class="form-group">
		                    <input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">
		                </div>
		            </form> --%>
		            
		        </div>
		        
	            <ul class="nav navbar-top-links navbar-title">
	                <li>
	                    <span class="m-r-sm text-muted welcome-message">	</span>
	                </li>
	                <li>
	                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
	                	切换岗位
	                </a>
	                <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob">
	                
	                	<li><a href="${ctx}/user/userInfo">我的信息</a></li>
                                <li class="divider"></li>
                                <li><a href="javascript:void(0)" onclick="LogoutUtils.logout();return false;">注销</a></li>
	                </ul>
	                <li style="float:right;line-height:70px;margin-right:20px;">
		                    <span class="goHome">返回主页</span>
		            </li>
	            </ul>
	
	        </nav>
	        </div>
	        <!-- 右侧页面主体内容 -->
	        <sitemesh:body></sitemesh:body>  
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/js/bootstrap.js"></script>
    <!-- jQuery UI -->
    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <!-- 自适应大小 -->
    <script src="${ctx}/js/inspinia.js"></script>
    <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
    
    
    
    <script type="text/javascript" src="${ctx}/transjs/common/INSPINIA_template.js"></script>
    <script type="text/javascript">
    var ctx = '${ctx}';
    window.ctx=ctx;
         var appCtx={};
	  <c:forEach items="${appCtxList}" var="app">
	     appCtx['${app.appName}'] = '${app.genAbsoluteUrl()}';
	  </c:forEach>
	  	orgJob.getOrgJob();	
		message.getMessage();
		setInterval(message.getMessage,1000*60*5);
		
        $(function(){
        	$(".goHome").click(function(){
        		window.location.href = "${ctx}/workspace/mobile/mainPage";
        	});
        });
		
    </script>
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
 
    <script>
    </script>
</body>
</html>
