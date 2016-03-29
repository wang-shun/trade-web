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
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/css/animate.css" rel="stylesheet">
    
    <sitemesh:head></sitemesh:head>
    
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/font-awesome.min.css" rel="stylesheet">
    
    <link href="${ctx}/css/plugins/jqGrid/aist-jqgird-ui.custom.css" rel="stylesheet">
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
    	<!-- 左侧菜单栏 -->
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
								<c:choose><c:when test='${l1_menu.type=="MDL"}'>
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
		           <%--  <form role="search" class="navbar-form-custom" action="search_results.html">
		                <div class="form-group">
		                    <input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">
		                </div>
		            </form> --%>
		        </div>
		        <div class="navbar-header" style="margin-left: 30%;"><h2>欢迎使用誉萃交易平台！</h2></div>
	            <ul class="nav navbar-top-links navbar-right">
	                <li>
	                    <span class="m-r-sm text-muted welcome-message">	</span>
	                </li>
	                <li class="dropdown">
	                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
	                        <i class="fa fa-envelope"></i>  <span class="label label-warning" id="sp_badge">0</span>
	                    </a>
	                    <ul class="dropdown-menu dropdown-messages" id="messageul">
	                    </ul>
	                </li>
	                <li>
	                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
	                	切换岗位
	                </a>
	                <ul class="dropdown-menu animated fadeInRight m-t-xs" id="portalOrgJob">
	                </ul>
	            </ul>
	
	        </nav>
	        </div>
	        
	        <!-- 右侧页面主体内容 -->
	        <sitemesh:body></sitemesh:body>
                
        </div>
<!--         <div class="small-chat-box fadeInRight animated">

            <div class="heading" draggable="true">
                <small class="chat-date pull-right">
                    02.19.2015
                </small>
                Small chat
            </div>

            <div class="content">

                <div class="left">
                    <div class="author-name">
                        Monica Jackson <small class="chat-date">
                        10:02 am
                    </small>
                    </div>
                    <div class="chat-message active">
                        Lorem Ipsum is simply dummy text input.
                    </div>

                </div>
                <div class="right">
                    <div class="author-name">
                        Mick Smith
                        <small class="chat-date">
                            11:24 am
                        </small>
                    </div>
                    <div class="chat-message">
                        Lorem Ipsum is simpl.
                    </div>
                </div>
                <div class="left">
                    <div class="author-name">
                        Alice Novak
                        <small class="chat-date">
                            08:45 pm
                        </small>
                    </div>
                    <div class="chat-message active">
                        Check this stock char.
                    </div>
                </div>
                <div class="right">
                    <div class="author-name">
                        Anna Lamson
                        <small class="chat-date">
                            11:24 am
                        </small>
                    </div>
                    <div class="chat-message">
                        The standard chunk of Lorem Ipsum
                    </div>
                </div>
                <div class="left">
                    <div class="author-name">
                        Mick Lane
                        <small class="chat-date">
                            08:45 pm
                        </small>
                    </div>
                    <div class="chat-message active">
                        I belive that. Lorem Ipsum is simply dummy text.
                    </div>
                </div>


            </div>
            <div class="form-chat">
                <div class="input-group input-group-sm"><input type="text" class="form-control"> <span class="input-group-btn"> <button
                        class="btn btn-primary" type="button">Send
                </button> </span></div>
            </div>

        </div> -->
<!--         <div id="small-chat">

            <span class="badge badge-warning pull-right">5</span>
            <a class="open-small-chat">
                <i class="fa fa-comments"></i>

            </a>
        </div> -->
        
        
<%--         <div id="right-sidebar">
            <div class="sidebar-container">

                <ul class="nav nav-tabs navs-3">

                    <li class="active"><a data-toggle="tab" href="#tab-1">
                        Notes
                    </a></li>
                    <li><a data-toggle="tab" href="#tab-2">
                        Projects
                    </a></li>
                    <li class=""><a data-toggle="tab" href="#tab-3">
                        <i class="fa fa-gear"></i>
                    </a></li>
                </ul>

                <div class="tab-content">


                    <div id="tab-1" class="tab-pane active">

                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> Latest Notes</h3>
                            <small><i class="fa fa-tim"></i> You have 10 new message.</small>
                        </div>

                        <div>

                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a1.jpg">

                                        <div class="m-t-xs">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="media-body">

                                        There are many variations of passages of Lorem Ipsum available.
                                        <br>
                                        <small class="text-muted">Today 4:21 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a2.jpg">
                                    </div>
                                    <div class="media-body">
                                        The point of using Lorem Ipsum is that it has a more-or-less normal.
                                        <br>
                                        <small class="text-muted">Yesterday 2:45 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a3.jpg">

                                        <div class="m-t-xs">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="media-body">
                                        Mevolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
                                        <br>
                                        <small class="text-muted">Yesterday 1:10 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a4.jpg">
                                    </div>

                                    <div class="media-body">
                                        Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the
                                        <br>
                                        <small class="text-muted">Monday 8:37 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a8.jpg">
                                    </div>
                                    <div class="media-body">

                                        All the Lorem Ipsum generators on the Internet tend to repeat.
                                        <br>
                                        <small class="text-muted">Today 4:21 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a7.jpg">
                                    </div>
                                    <div class="media-body">
                                        Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
                                        <br>
                                        <small class="text-muted">Yesterday 2:45 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a3.jpg">

                                        <div class="m-t-xs">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="media-body">
                                        The standard chunk of Lorem Ipsum used since the 1500s is reproduced below.
                                        <br>
                                        <small class="text-muted">Yesterday 1:10 pm</small>
                                    </div>
                                </a>
                            </div>
                            <div class="sidebar-message">
                                <a href="#">
                                    <div class="pull-left text-center">
                                        <img alt="image" class="img-circle message-avatar" src="${ctx}/img/a4.jpg">
                                    </div>
                                    <div class="media-body">
                                        Uncover many web sites still in their infancy. Various versions have.
                                        <br>
                                        <small class="text-muted">Monday 8:37 pm</small>
                                    </div>
                                </a>
                            </div>
                        </div>

                    </div>

                    <div id="tab-2" class="tab-pane">

                        <div class="sidebar-title">
                            <h3> <i class="fa fa-cube"></i> Latest projects</h3>
                            <small><i class="fa fa-tim"></i> You have 14 projects. 10 not completed.</small>
                        </div>

                        <ul class="sidebar-list">
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Business valuation</h4>
                                    It is a long established fact that a reader will be distracted.

                                    <div class="small">Completion with: 22%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                    </div>
                                    <div class="small text-muted m-t-xs">Project end: 4:00 pm - 12.06.2014</div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Contract with Company </h4>
                                    Many desktop publishing packages and web page editors.

                                    <div class="small">Completion with: 48%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 48%;" class="progress-bar"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Meeting</h4>
                                    By the readable content of a page when looking at its layout.

                                    <div class="small">Completion with: 14%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="label label-primary pull-right">NEW</span>
                                    <h4>The generated</h4>
                                    <!--<div class="small pull-right m-t-xs">9 hours ago</div>-->
                                    There are many variations of passages of Lorem Ipsum available.
                                    <div class="small">Completion with: 22%</div>
                                    <div class="small text-muted m-t-xs">Project end: 4:00 pm - 12.06.2014</div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Business valuation</h4>
                                    It is a long established fact that a reader will be distracted.

                                    <div class="small">Completion with: 22%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                    </div>
                                    <div class="small text-muted m-t-xs">Project end: 4:00 pm - 12.06.2014</div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Contract with Company </h4>
                                    Many desktop publishing packages and web page editors.

                                    <div class="small">Completion with: 48%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 48%;" class="progress-bar"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <div class="small pull-right m-t-xs">9 hours ago</div>
                                    <h4>Meeting</h4>
                                    By the readable content of a page when looking at its layout.

                                    <div class="small">Completion with: 14%</div>
                                    <div class="progress progress-mini">
                                        <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="label label-primary pull-right">NEW</span>
                                    <h4>The generated</h4>
                                    <!--<div class="small pull-right m-t-xs">9 hours ago</div>-->
                                    There are many variations of passages of Lorem Ipsum available.
                                    <div class="small">Completion with: 22%</div>
                                    <div class="small text-muted m-t-xs">Project end: 4:00 pm - 12.06.2014</div>
                                </a>
                            </li>

                        </ul>

                    </div>

                    <div id="tab-3" class="tab-pane">

                        <div class="sidebar-title">
                            <h3><i class="fa fa-gears"></i> Settings</h3>
                            <small><i class="fa fa-tim"></i> You have 14 projects. 10 not completed.</small>
                        </div>

                        <div class="setings-item">
                    <span>
                        Show notifications
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="example">
                                    <label class="onoffswitch-label" for="example">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Disable Chat
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" checked class="onoffswitch-checkbox" id="example2">
                                    <label class="onoffswitch-label" for="example2">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Enable history
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="example3">
                                    <label class="onoffswitch-label" for="example3">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Show charts
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="example4">
                                    <label class="onoffswitch-label" for="example4">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Offline users
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" checked name="collapsemenu" class="onoffswitch-checkbox" id="example5">
                                    <label class="onoffswitch-label" for="example5">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Global search
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" checked name="collapsemenu" class="onoffswitch-checkbox" id="example6">
                                    <label class="onoffswitch-label" for="example6">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                    <span>
                        Update everyday
                    </span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="example7">
                                    <label class="onoffswitch-label" for="example7">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="sidebar-content">
                            <h4>Settings</h4>
                            <div class="small">
                                I belive that. Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                                And typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.
                                Over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
                            </div>
                        </div>

                    </div>
                </div>

            </div>



        </div> --%>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/js/bootstrap.js"></script>
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/js/inspinia.js"></script>
    <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
    <!-- jQuery UI -->
    <script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
    <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    
    <%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog.jsp"%> 
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

    </script>
    <sitemesh:getProperty property="page.local_script"></sitemesh:getProperty>
 
    <script>
    </script>
</body>
</html>
