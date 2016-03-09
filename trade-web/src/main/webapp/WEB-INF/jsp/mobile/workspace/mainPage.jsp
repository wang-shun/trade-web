<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>	
<%@page import="com.centaline.trans.utils.URLAvailability"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="sitemesh"
           uri="http://www.opensymphony.com/sitemesh/decorator"%>

<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());

	String headImgUrl = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+SessionUserConstants.getSesstionUser().getEmployeeCode()+".jpg";
	URLAvailability urlAvailability = new URLAvailability();
	if(urlAvailability.isConnect(headImgUrl) == null) {
		headImgUrl=null;
	}
	request.setAttribute("headImgUrl", headImgUrl);
%>
<html>
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0" name="viewport" />
        <meta content="yes" name="apple-mobile-web-app-capable" />
        <meta content="telephone=no" name="format-detection">
        <title>首页</title>
        <link href="${ctx}/css/mobile/workspace/mainPage.css" rel="stylesheet">
        <style type="text/css">
        	.lazur-bg {
			    background: #ed5565;
			    margin-bottom: 1%;
			}
			.widget {
			    border-radius: 3px;
			    -webkit-border-radius: 3px;
			    position: relative;
			}
        </style>
    </head>
    <body>
        <header>
            <h1>欢迎登录誉萃移动平台</h1>
            <section class="profile">
               <%--  <span><img src="${headImgUrl}" alt="image" class="img-circle"></span><br> --%>
                <c:choose>
                <c:when test="${headImgUrl == '' or headImgUrl == null }">
               		<span  class="img-circle" style="background-image:url(${ctx }/img/a5.png);"></span>
                </c:when>
                <c:otherwise>
                	<span  class="img-circle" style="background-image:url(${headImgUrl});"></span>
                </c:otherwise>
                </c:choose>
                <br>
                ${sessionUser.realName}<br>${sessionUser.serviceJobName}<br>${sessionUser.serviceDepName}
            </section>
        </header>
        <article id="index-cont">
            <ul>
                    <li>
                        <div>
                            <a href="${ctx}/task/mobile/myTaskList">
                            <i class="desc-img img-task"></i>
                            <span>任务</span>
                        </a>
                        </div>
                    </li>
                    <li id="special">
                        <div>
                            <div class="widget lazur-bg">
                                <p>
                                    <i class="img-light"></i>
                                    <span>红灯任务<br><strong><a href="${ctx }/workspace/mobile/ryLightList?color=0" target="_blank"style="position: inherit;">${redLightCount}</a></strong></span>
                                </p>
                            </div>
                            <div class="widget yellow-bg">
                                <p>
                                    <i class="img-light"></i>
                                    <span>黄灯任务<br><strong><a href="${ctx }workspace/mobile/ryLightList?color=0" target="_blank"style="position: inherit;">${yeLightCount}</a></strong></span>
                                </p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div>
                            <a href="${ctx}/task/mobile/pricingApply">
                            <i class="desc-img img-e"></i>
                            <span>E估</span>
                        </a>
                        </div>
                    </li>
                    <li>
                        <div>
                            <a href="${ctx}/attendCheck/mobile/attendManage">
                            <i class="desc-img img-time"></i>
                            <span>考勤</span>
                        </a>
                        </div>
                    </li>
                    <li>
                        <div>
                            <a href="${ctx}/workspace/mobile/dashboard">
                            <i class="desc-img img-message"></i>
                            <span>消息</span>
                        </a>
                        </div>
                    </li>
                    <li>
                        <div>
                            <a href="${ctx}/property/mobile/propertyList">
                            <i class="desc-img img-pro"></i>
                            <span>产调</span>
                        </a>
                        </div>
                    </li>
                </ul>
        </article>
        <script type="text/javascript">
        window.onload = function(){
            var s_h = document.getElementById('special').offsetHeight,
                  l_h = s_h / 2,
                  m_b = s_h * 0.02;
            document.getElementsByClassName('widget')[0].style.height = (l_h - m_b) + 'px';
            document.getElementsByClassName('widget')[1].style.height = (l_h - m_b) + 'px';
        }
        </script>
    </body>
</html>