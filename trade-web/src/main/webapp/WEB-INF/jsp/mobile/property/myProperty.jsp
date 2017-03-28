<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-touch-fullscreen" content="yes"  />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no">
	    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/aui.css" />
	    <link href="${ctx}/momedia/css/scrollpagination.css" rel="stylesheet" />
    	<link rel="stylesheet" href="${ctx}/static/momedia/iconfontmobile/iconfont.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/property/miuustyle.css" />
	</head>
	<body>
 	<header class="aui-bar aui-bar-nav">
 	   <!-- 
       <a class="aui-pull-left aui-btn head-mend">
            <span><i class="iconfont font-20">&#xe628;</i></span>
        </a>
         -->
        <div class="aui-title">我的产调</div>
    </header>

    <article class="aui-content">
        <div class="aui-searchbar" id="search">
        <div class="aui-searchbar-input aui-border-radius" tapmode="">
            <i class="aui-iconfont aui-icon-search"></i>
            <form action="javascript:search();">
                <input type="search" placeholder="产证地址" id="propertyAddr" onkeyup="initScrollPaggination();">
            </form>
        </div>
    </div>
        <ul class="aui-list aui-media-list" id="content">
            <!-- <li class="aui-list-item aui-mt-5">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font16 order-title left mr10">浦东新区</div>
                        <div class="aui-list-item-text font14 newgrey clear">
                            上海杨浦区平路街道（内环）鞍山八村29号
                        </div>
                        <div class="font12 newgrey mt5 sign">
                            <span><i class="sigin-label">申</i><time>2016-7-6</time></span>
                            <span><i class="sigin-label">受</i><time>2016-7-6</time></span>
                            <span><i class="sigin-label">完</i><time>2016-7-6</time></span>
                        </div>
                    </div>
                     <div class="aui-list-item-media listspace">
                    </div>
                    <div class="aui-btn aui-btn-info aui-center">查看</div>
                    <span  class="user-mark"></span>
                </div>
            </li>
            <li class="aui-list-item aui-mt-5">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font16 order-title left mr10">浦东新区</div>
                        <div class="aui-list-item-text font14 newgrey clear">
                            上海杨浦区平路街道（内环）鞍山八村29号
                        </div>
                        <div class="font12 newgrey mt5 sign">
                            <span><i class="sigin-label">申</i><time>2016-7-6</time></span>
                            <span><i class="sigin-label">受</i><time>2016-7-6</time></span>
                            <span><i class="sigin-label">完</i><time>2016-7-6</time></span>
                        </div>
                    </div>
                     <div class="aui-list-item-media listspace">
                    </div>
                    <div class="aui-btn aui-btn-info aui-center">查看</div>
                    <span class="nouser-mark"></span>
                </div>
            </li> -->

        </ul>
          <div style="display: block; height: 45px;">
				<div class="loading" id="loading">
					<div class="spinner" style="float: left;">
						<div class="spinner-container container1">
							<div class="circle1"></div>
							<div class="circle2"></div>
							<div class="circle3"></div>
							<div class="circle4"></div>
						</div>
						<div class="spinner-container container2">
							<div class="circle1"></div>
							<div class="circle2"></div>
							<div class="circle3"></div>
							<div class="circle4"></div>
						</div>
						<div class="spinner-container container3">
							<div class="circle1"></div>
							<div class="circle2"></div>
							<div class="circle3"></div>
							<div class="circle4"></div>
						</div>
					</div>
					<span class="" style="float: left;">正在加载数据...</span>
				</div>
				<div class="loading" id="nomoreresults">没有更多数据</div>
			</div>
    </article>
		<content tag="local_script">
		<script type="text/javascript" src="${ctx}/static/momedia/js/jquery-2.1.1.js"></script>
		<script src="${ctx}/momedia/js/template.js" type="text/javascript"></script>
		<script src="${ctx}/momedia/js/scrollpagination.js?v=1.1.1"></script>
		<script id="myPropertyList" type="text/html">
			{{if content.length>0}}
				{{each content as item}}
			<li class="aui-list-item aui-mt-5">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font16 order-title left mr10">{{item.DIST_CODE}}</div>
                        <div class="aui-list-item-text font14 newgrey clear">
                            {{item.PROPERTY_ADDR}}
                        </div>
                        <div class="font12 newgrey mt5 sign">
                            <span>
									{{if item.PR_APPLY_TIME == null}}
										<i class="invalid-label">申</i>
									{{else}}
										<i class="sigin-label">申</i><time>{{item.PR_APPLY_TIME}}</time>
									{{/if}}	
                            </span>
                            <span>
									{{if item.PR_ACCPET_TIME == null}}
										<i class="invalid-label">受</i>
									{{else}}
										<i class="sigin-label">受</i><time>{{item.PR_ACCPET_TIME}}</time>
									{{/if}}	
                            </span>
                            <span>
                                    {{if item.PR_COMPLETE_TIME == null}}
										<i class="invalid-label">完</i>
									{{else}}
										<i class="sigin-label">完</i><time>{{item.PR_COMPLETE_TIME}}</time>
									{{/if}}	
                            </span>
                        </div>
                    </div>
                     <div class="aui-list-item-media listspace">
                    </div>
					{{if item.PR_COMPLETE_TIME != null}}
                         <a href="#" class="aui-btn aui-btn-info aui-center" onclick="doProcess('{{item.prCode}}');">查看</a>
					{{/if}}	   
					{{if item.IS_SUCCESS == '是'}}
						 <span  class="user-mark"></span>
					{{else if item.IS_SUCCESS == '否'}}
						 <span  class="nouser-mark"></span>
					{{else}}
					{{/if}}
                </div>
            </li>
				{{/each}}
			{{/if}}
		</script>
		<script>
			var ctx = "${ctx}";
			$(function() {
				
				$('.top').hide();
				
				$(window).scroll(function() {
					if ($(window).scrollTop() > 100) {
						$('.top').fadeIn();
					} else {
						$('.top').fadeOut();
					}
				});
				
				$('.top').click(function() {
					$('html,body').animate({
						scrollTop : 0
					}, 500);
				});
				
				$(".search_btn").click(function(){
					initScrollPaggination();
				});				
				
				initScrollPaggination();
				
			});
			
			function doProcess(prcode) {
				location.href = ctx + '/mobile/property/box/show?prCode=' + prcode;
			};
			
			var postData = {
				queryId : "queryMyProperty",
				search_prAppliantId : '${prAppliantId}',
				rows : 10,
				page : 1
			};
			
			function initScrollPaggination() {
				postData.search_propertyAddr = $("#propertyAddr").val();
				$('#content').empty();
				$('#content').scrollPagination({
					'contentPage' : ctx + '/mobile/property/box/findMyPropertyList',
					'contentData' : postData, // these are the variables you can pass to the request, for example: children().size() to know which page you are
					'scrollTarget' : $(window), // who gonna scroll? in this example, the full window
					'heightOffset' : 10, // it gonna request when scroll is 10 pixels before the page ends
					'beforeLoad' : function() { // before load function, you can display a preloader div
						$('#loading').fadeIn();
					},
					'dataType' : 'json',
					'afterLoad' : function(elementsLoaded) { // after loading content, you can use this function to animate your new elements
						$('#loading').fadeOut();
						if($.isEmptyObject(elementsLoaded) || elementsLoaded.length == 0) {
							$('#nomoreresults').show();
							setTimeout(nomoreresultsOut, 2000);
						}else{
							postData.page += 1;
						}
						$(elementsLoaded).fadeInWithDelay();
					},
					render : function(data) {
						var html = template('myPropertyList', data);
						return html;
					}
				});
			};
			
			function nomoreresultsOut() {
				$('#nomoreresults').fadeOut()
			};
			
		</script>
		</content>
	</body>
</html>
