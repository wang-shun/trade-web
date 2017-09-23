<%@page import="com.aist.common.utils.SpringUtils"%>
<%@page import="com.aist.uam.permission.remote.UamPermissionService"%>
<%@page import="com.aist.uam.permission.remote.vo.App"%>
<%@page import="com.aist.uam.permission.remote.vo.Menu"%>
<%@page import="com.centaline.trans.user.web.MenuConstants"%>
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
	if(menu != null){
		request.setAttribute("menuVO", menu.getChildren());
	}
%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="${ctx}/static/image/favicon.ico" type="image/x-icon">  
    <link rel="shortcut icon" href="${ctx}/static/image/favicon.ico" type="image/x-icon">  
    <title>誉萃交易系统</title>
    <link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    <link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
    
    <link href="<c:url value='/static/css/style.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jqGrid/aist-jqgird-ui.custom.css' />" rel="stylesheet">
    <!--弹出框样式  -->
	<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
    <sitemesh:head></sitemesh:head>
   <script type="text/javascript">
	    var ctx = '${ctx}';
	    window.ctx=ctx;
    </script>
    <%-- <script src="${ctx}/js/plugins/required/require.js" data-main="${ctx}/js/plugins/required/config.js"></script> --%>
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
                        	<span>
							    <img src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${SESSION_USER.employeeCode}.jpg" 
							    	onerror="this.src='${ctx}/img/a5.png'" style="object-fit:cover; width: 48px;height: 48px; display: block;border-radius: 50%;"/>
                         	</span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${sessionUser.realName}</strong>
                             </span> <span class="text-muted text-xs block">${sessionUser.serviceJobName} </span>
                             	<span class="text-muted text-xs block">${sessionUser.serviceDepName}<b class="caret"></b></span>
                              </span> </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li id="myInfo"><a href="${ctx}/user/userInfo">我的信息</a></li>
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
								<c:choose><c:when test='${l1_menu.type=="MEN"}'><a href="${l1_menu.absoluteUrl}"  target="${l1_menu.target}" ><i class="fa ${l1_menu.icon}"></i> <span class="nav-label">${l1_menu.menuName }</span> <span class="fa arrow"></span></a></c:when>
								<c:otherwise><a href="#"><i class="fa ${l1_menu.icon}"></i> <span class="nav-label">${l1_menu.menuName }</span><span class="fa arrow"></span></a></c:otherwise>
								</c:choose> 
								<c:choose>
									<c:when test='${l1_menu.type=="MDL"}'>
										<ul class="nav nav-second-level">
											<c:forEach items="${l1_menu.children}" var="l2_menu" >
											<c:if test='${not empty l2_menu.resource.resourceCode}'>
													<li><a href="${l2_menu.absoluteUrl}?<%=new Date().getTime() %>"  target="${l2_menu.target}"><i class="fa ${l2_menu.icon}"></i>
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
		        <div class="navbar-header" style="margin-left: 30%;"><h2 class="welcome">欢迎使用金融交易平台！</h2></div>
	            <ul class="nav navbar-top-links navbar-right">
	                <li>
	                    <span class="m-r-sm text-muted welcome-message">	</span>
	                </li>
	                <li class="dropdown">
	                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
	                        <i class="fa fa-envelope"></i>  <span class="label label-warning" id="sp_badge">0</span>
	                    </a>
	                    <ul class="dropdown-menu dropdown-messages" id="messageul" style="z-index:9999;">
	                    </ul>
	                </li>
	                <li>
	                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
	                	切换岗位
	                </a>
	                <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob" style="z-index:9999;">
	                </ul>
	            </ul>
	
	        </nav>
	        </div>
	        
	        <!-- 右侧页面主体内容 -->
	        <sitemesh:body></sitemesh:body>
	         
			<div class="modal fade" id="myModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h4 class="modal-title" id="myModalLabel">
								切换用户
							</h4>
						</div>
						<div class="modal-body">
							用户名：<input type="text" name="username" id="username" >
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭
							</button>
							<button type="button" onclick="runAs()" class="btn btn-primary">
								切换
							</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal -->
			</div>
			
            <div class="copyrightstyle">
            	Copyright ©2017 AIST All rights reserved. Version : <%=com.aist.common.utils.ManifestUtils.getManifest().getMainAttributes().getValue("Implementation-Version")%> commit : <%=com.aist.common.utils.ManifestUtils.getManifest().getMainAttributes().getValue("commit")%> branch : <%=com.aist.common.utils.ManifestUtils.getManifest().getMainAttributes().getValue("branch")%> buildTime : <%=com.aist.common.utils.ManifestUtils.getManifest().getMainAttributes().getValue("buildTime")%> hostname: <%=java.net.InetAddress.getLocalHost().getHostName() %>  
            </div>    
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/static/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/static/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
      
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/static/js/inspinia.js' />"></script>
    <%--
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
     --%>
    <!-- jQuery UI -->
    <script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
    <!-- 引入弹出框js文件 -->
	<script src="<c:url value='/js/common/xcConfirm.js' />"></script>
	
	<script type="text/javascript" src="<c:url value='/js/jquery.fancybox.js' />"></script> <!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-buttons.js' />"></script> <!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-thumbs.js' />"></script> <!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="<c:url value='/js/jquery.fancybox-media.js' />"></script>
    <%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog_new.jsp"%> 
    <script type="text/javascript" src="<c:url value='/static/js/INSPINIA_template.js' />"></script>
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
			
			var username = "${sessionUser.username}";
			
			//切换用户
			var isRunAs= <%=org.apache.shiro.SecurityUtils.getSubject().isRunAs() %>;
			if(!isRunAs && "admin" == username){
				$("#myInfo").after(" <li class='divider'></li><li><a id='runasLink' href='javascript:void(0)' data-toggle='modal' data-target='#myModal'>切换用户</a></li>");
			}
			
			if(isRunAs){
				$("#myInfo").after(" <li class='divider'></li><li><a id='releaseRunasLink' onclick='releaseRunas();' href='javascript:void(0)'>返回原用户</a></li>");
			}
			
		});
		

		
		function runAs(){
			var username = $("#username").val();
			if(username == ""){
				alert("用户名不能为空！");
				return ;
			}
			var url = "${ctx}/switchUser/runAs";
			$.ajax({
			  type: 'POST',
			  url: url,
			  data: "username=" + username,
			  dataType: "json",
	   		  success: function(data){
	   			  alert(data.message);
	   			  if(data.success){
	   			 	window.location.reload();
	   			  }
	   		  }
			});
		}

		function releaseRunas(){
			var url = "${ctx}/switchUser/releaseRunAs";
			$.ajax({
			  type: 'POST',
			  url: url,
			  dataType: "json",
	   		  success: function(data){
	   			  alert(data.message);
	   			  if(data.success){
	   			 	window.location.reload();
	   			  }
	   		  }
			});
		}

    </script>
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
    <script src="<c:url value='/js/plugins/required/require.js' />" data-main="${ctx}/js/plugins/required/main.js"></script>
    <sitemesh:getProperty property="page.local_require"></sitemesh:getProperty>
    <script>
    </script>  
</body>
</html>
