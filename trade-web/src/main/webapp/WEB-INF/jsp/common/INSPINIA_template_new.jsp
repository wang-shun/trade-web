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
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
	Menu menu = MenuConstants.getMenu();
	String headImgUrl = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+SessionUserConstants.getSesstionUser().getEmployeeCode()+".jpg";
	URLAvailability urlAvailability = new URLAvailability();
	if(urlAvailability.isConnect(headImgUrl) == null) {
		headImgUrl=null;
	}
	request.setAttribute("headImgUrl", headImgUrl);

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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>誉萃交易系统</title>
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    
    <link href="${ctx}/static/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/aist-jqgird-ui.custom.css" rel="stylesheet">
    
    <sitemesh:head></sitemesh:head>
	<script type="text/javascript">
		try{
			var ctx="${ctx}";
			var productCtx = ctx;
			if(productCtx.value){
				productCtx=productCtx.value;
			}
	        var arr = productCtx.match(/http:\/\/[a-zA-Z0-9]+\.([a-zA-Z0-9.]+)[:]*[^:^/]*/);
			var domain = arr[1];
			if(domain.indexOf('sh.')==0){
				domain = domain.substr(3);
			}
			document.domain = domain;
	 		//document.domain = '';
	 	}catch(e){}
	</script>
</head>

<body>
    <div id="wrapper">
    <div class="modal hide" id="logoutmodal" >
    <div class="modal-body">
     <img alt="" src="${ctx}/img/load.jpg"><span>正在退出</span>
    </div>
 
</div>
    	<!-- 左侧菜单栏 01-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                        <c:choose>
	                    	<c:when test="${headImgUrl == '' or headImgUrl == null }">
	                    	 <span style="width: 48px;
							    height: 48px;
							    display: block;
							    border-radius: 50%;
							    background-image: url(${ctx }/img/a5.png);
							    background-size:48px 62px;">
                         </span>
                        </c:when>
                        	<c:otherwise>
	                    		<span style="width: 48px;
						    height: 48px;
						    display: block;
						    border-radius: 50%;
						    background-image: url('${headImgUrl}');
						    background-size:48px 62px;">
                         </span>
	                    	</c:otherwise>
                            </c:choose>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${sessionUser.realName}</strong>
                             </span> <span class="text-muted text-xs block">${sessionUser.serviceJobName} </span>
                             	<span class="text-muted text-xs block">${sessionUser.serviceDepName}<b class="caret"></b></span>
                              </span> </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a href="${ctx}/user/userInfo">我的信息</a></li>
                                <li class="divider"></li>
                                <li><a href="javascript:void(0)" onclick="LogoutUtils.logout();return false;">注销</a></li>
                            </ul>
                        </div>
                        <div class="logo-element">
                            YUCUI+
                        </div>
                    </li>
                   <c:forEach items="${menuVO}" var="l1_menu" >
						<li class="">
								<c:choose><c:when test='${l1_menu.type=="MEN"}'><a href="${ctx}${l1_menu.url}"><i class="fa ${l1_menu.icon}"></i> <span class="nav-label">${l1_menu.menuName }</span> <span class="fa arrow"></span></a></c:when>
								<c:otherwise><a href="#"><i class="fa ${l1_menu.icon}"></i> <span class="nav-label">${l1_menu.menuName }</span><span class="fa arrow"></span></a></c:otherwise>
								</c:choose> 
								<c:choose>
									<c:when test='${l1_menu.type=="MDL"}'>
										<ul class="nav nav-second-level">
											<c:forEach items="${l1_menu.children}" var="l2_menu" >
											<c:if test='${not empty l2_menu.resource.resourceCode}'>
													<li><a href="${ctx}${l2_menu.url}?<%=new Date().getTime() %>"><i class="fa ${l2_menu.icon}"></i>
													${l2_menu.menuName}</a></li>
											</c:if>
											</c:forEach>
										</ul>
									</c:when>
							</c:choose>
						</li>
					</c:forEach>
           
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
		        </div>
		        <div class="navbar-header" style="margin-left: 30%;"><h2 class="welcome">欢迎使用誉萃交易平台！</h2></div>
	            <ul class="nav navbar-top-links navbar-right">
	                <li>
	                    <span class="m-r-sm text-muted welcome-message">	</span>
	                </li>
	                <li class="dropdown">
	                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
	                        <i class="fa fa-envelope"></i>  <span class="label label-warning" id="sp_badge">0</span>
	                    </a>
	                    <ul class="dropdown-menu dropdown-messages" id="messageul" style="z-index:9999px;">
	                    </ul>
	                </li>
	                <li>
	                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
	                	切换岗位
	                </a>
	                <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob" style="z-index:9999px;">
	                </ul>
	            </ul>
	
	        </nav>
	        </div>
	        
	        <!-- 右侧页面主体内容 -->
	        <sitemesh:body></sitemesh:body>
                
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
    
    <!-- jQuery UI -->
    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    
    <%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog_new.jsp"%> 
    <script type="text/javascript" src="${ctx}/static/js/INSPINIA_template.js"></script>
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
		
		
		$(function() { 
			var side_menu = $("#side-menu");
			var aList = side_menu.find("a");
			var winHref = window.location.href;
			
			var winHrefEnd = winHref.indexOf("?");
			winHref = winHrefEnd < 0 ? winHref : winHref.substring(0,winHrefEnd );
			var i = 0;
			aList.each(function(){
				var aHref = $(this).attr("href");
				var end = aHref.indexOf("?");
				var liHref = end <0 ? aHref : aHref.substring(0,end);
			    if( liHref == winHref ){
			    	var li = $(this).parent();
			    	li.attr("class","active");
			    	
			    	var ul = $(this).parent().parent();
			    	if(ul.attr("id") != "side-menu" ){
			    		$(this).parent().parent().attr("class","nav nav-second-level collapse in");
			    	}
			    }
			});
		});
    </script>
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
    <script>
    </script>
</body>
</html>
