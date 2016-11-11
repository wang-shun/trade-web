<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>
   	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/momedia/css/plugins/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/momedia/css/common/style.css" rel="stylesheet">
    <link href="${ctx}/static/momedia/css/mobile-yc-style.css" rel="stylesheet">
    <link href="${ctx}/static/momedia/iconfontmobile/iconfont.css" rel="stylesheet">
</head>

<body style="background-color: #eee;">
    <div id="wrapper">
        <div  class="white-bg">
            <header class="aui-bar aui-bar-nav">
                <a class="aui-pull-left">
                    <span class="aui-iconfont aui-icon-left"><i class="iconfont font-20">&#xe628;</i></span>
                </a>
                <div class="aui-title font-18">产调结果</div>
            </header>
            <section class="case-info">
                <header class="font-16">${districtName}</header>
                <div class="miuu-info">
                    <div class="mt5"><i class="iconfont wathet mr5">&#xe60f;</i>${propertyResearch.prAddress }</div>
                    <div class="clearfix mt5">
                        <span class="info-minute"><i class="iconfont wathet mr5">&#xe677;</i>${propertyResearch.prCode }</span>
                        <span class="info-minute">
                            <i class="sigin-time">完</i>
                        	<fmt:formatDate value="${propertyResearch.prCompleteTime }" pattern="yyyy-MM-dd HH:mm" />
                        </span>
                    </div>
                </div>
                <span class="${propertyResearch.isSuccess==0?'nouser-mark':'user-mark' }"></span>
            </section>
            <section class="miuu-home clearfix">
                <p>执行人：${prAppliantName}</p>
                <p>区董：${prCostOrgMgr}</p>
            </section>
            <section class="project-info">
                <header class="font-16">产调项目</header>
                <div class="project-info-list">
                    <p>${propertyResearch.prCat }</p>
                </div>
            </section>
            <c:if test="${(propertyResearch.isSuccess==0)}">
	            <section class="project-info">
	                <header class="font-16">无效原因</header>
	                <div class="project-info-list">
	                    <p>${propertyResearch.unSuccessReason }</p>
	                </div>
	            </section>
			</c:if>
			<c:if test="${(propertyResearch.isSuccess==1)}">
				<section class="project-img">
					<c:forEach var="att" items="${attachments }">
						<img src="<aist:appCtx appName="img-centanet"/>/image/${att.preFileAdress}/1280_1024_f.jpg" width="100%">
					</c:forEach>
            	</section>
			</c:if>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/static/momedia/js/jquery-2.1.1.js"></script>


</body>
</html>
