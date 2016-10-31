<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-touch-fullscreen" content="yes"  />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no">
		<link href="${ctx}/momedia/css/property/myproperty.css" rel="stylesheet">
		<link href="${ctx}/momedia/css/scrollpagination.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${ctx}/momedia/iconfont/iconfont.css" />
		<style>
			.uc_return_c {
			    float: left;
			    width: 80%;
			    color: #fff;
			}
		</style>
	</head>
	<body>
		<header class="u-header">
		    <div class="uc_return" style="position:relative;">
		        <div class="uc_return_c">我的产调</div>
        		<a href="${ctx}/weixin/property/toApply" ><i class="iconfont" style="position:absolute;top:0;right:25px;">&#xe68f;</i></a>
		    </div>
		</header>
		<section class="productul" id="productul">
	    	<div class="search">
	    		<input type="text" id="propertyAddr" class="search_input" placeholder="产证地址"/>
		        <div class="search_btn"></div>
		    </div>
		    <a href="javascript:;" class="top"><i class="pic-top"></i></a>
		    <article>
		    <ul id="content"></ul>
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
		</section>
		<content tag="local_script">
		<script src="${ctx}/momedia/js/template.js" type="text/javascript"></script>
		<script src="${ctx}/momedia/js/scrollpagination.js?v=1.1.1"></script>
		<script id="myPropertyList" type="text/html">
			{{if content.length>0}}
				{{each content as item}}
					<li>
						<div class="productcontent">
		                	<a href="#"><p class="pdes pl10"><strong>{{item.DIST_CODE}}</strong><a href="#" onclick="doProcess('{{item.prCode}}');">{{item.PROPERTY_ADDR}}</p></a>
		                	<p class="pdes pl10">
		                    	<span class="sign">
									{{if item.PR_APPLY_TIME == null}}
										<i class="invalid-label">申</i>
									{{else}}
										<i class="sq-label">申</i><time>{{item.PR_APPLY_TIME}}</time>
									{{/if}}	
								</span>
		                    	<span class="sign">
									{{if item.PR_ACCPET_TIME == null}}
										<i class="invalid-label">受</i>
									{{else}}
										<i class="sl-label">受</i><time>{{item.PR_ACCPET_TIME}}</time>
									{{/if}}	
								</span>
		                    	<span class="sign">
									{{if item.PR_COMPLETE_TIME == null}}
										<i class="invalid-label">完</i>
									{{else}}
										<i class="sl-label">完</i><time>{{item.PR_COMPLETE_TIME}}</time>
									{{/if}}	
								</span>
		                	</p>							 
		                	<p class="pprice pl10 pr10">
		                    	<span class="useno">
									{{if item.IS_SUCCESS == '是'}}
										<i class="sq-label">有效</i>
									{{else if item.IS_SUCCESS == '否'}}
										<i class="invalid-label">无效</i>
									{{else}}
									{{/if}}
								</span>
								{{if item.PR_COMPLETE_TIME != null}}
		                    		<span class="look">
										<a href="#" onclick="doProcess('{{item.prCode}}');">查看</a
									</span>
								{{/if}}	
		                	</p>
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
