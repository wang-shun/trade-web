<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/scrollpagination/scrollpagination.css" rel="stylesheet"/>
<link href="${ctx}/css/list.css" rel="stylesheet">

<style type="text/css">
.form-group label {
	text-align: right;
}
.form-control {
	margin-bottom: 5px;
	height:32px;
}
.col-sm-10{
	height:37px;
}
.col-md-2{
	width:12%
}
.list li span:first-child{color: #555;width: 65%}
.list li span:nth-child(2){width: 35%;min-width: 172.4px;}
.list li span:nth-child(3){width: 32%;text-align: right;}

</style>
</head>

<body>
	<div class="row">
		<div class="wrapper wrapper-content  animated fadeInRight" style="padding:0px">
			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>考勤记录列表</h5>
					</div>
					<div class="ibox-content">
					<a href="javascript:;" class="top"><i class="pic-top"></i></a>
						<article class="list">
            				<ul id="content">
          		 			 </ul>
          		 			 <div style="display: block;height: 45px;">
          		 			 <div class="loading" id="loading">
	                <div class="spinner" style="float:left;">
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
					<span class="" style="float:left;">正在加载数据...</span>
				</div>
    			<div class="loading" id="nomoreresults">没有更多数据</div>
    			</div>
        				</article>
        				                  
					</div>
				</div>
			</div>
		</div>
	</div>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script	src="${ctx}/js/plugins/scrollpagination/scrollpagination.js"></script>
		<script id="attendList" type="text/html">
					{{if rows.length>0}}
						{{ each rows as item }}
						<li>
                        	<div>
                            	<span><i class="icon-person"></i>{{item.REASON}}</span><span><i class="icon-calendar"></i>{{item.ATTEND_TIME}}</span>
                       		 </div>
                        	<p class="text-ellipsis"><i class="icon-address"></i>{{item.ADDRESS}}</p>
                    	</li>
						{{/each}}
					{{/if}}
	</script>

    <script>
    var ctx = "${ctx}";
   
    var postData={queryId:"queryAttendancePage",search_userId:'${userId}',rows:10,page:1,sortname:'TA.ATTEND_TIME',sortorder:'desc'};
    function initScrollPaggination(){
		postData.search_propertyAddr=$("#propertyAddr").val();
		postData.page=1;
		$('#content').empty();
    	$('#content').scrollPagination({
    		'contentPage': ctx+'/quickGrid/findPage',
    		'contentData': postData, // these are the variables you can pass to the request, for example: children().size() to know which page you are
    		'scrollTarget': $(window), // who gonna scroll? in this example, the full window
    		'heightOffset': 10, // it gonna request when scroll is 10 pixels before the page ends
    		'beforeLoad': function(){ // before load function, you can display a preloader div
    			$('#loading').show();	
    		},'dataType':'json',
    		'afterLoad': function(elementsLoaded){ // after loading content, you can use this function to animate your new elements
    			 $('#loading').fadeOut();
    			if($.isEmptyObject(elementsLoaded)||elementsLoaded.length==0){
    				$('#nomoreresults').show();	    							 	
    			}else{
    				postData.page+=1;
	   			}
    			setTimeout(nomoreresultsOut,2000);	
    			 $(elementsLoaded).fadeInWithDelay();

    		},render:function(data){
    			var html=template('attendList', data); 
    			return html;
    		}
    	});
	}
    function nomoreresultsOut(){
    	$('#nomoreresults').fadeOut()
    }
    $(function(){
    	initScrollPaggination();
    	$('.top').hide();
        $(window).scroll(function(){
            if($(window).scrollTop() > 100){
                $('.top').fadeIn();
            }else{
                $('.top').fadeOut();
            }
        });
        $('.top').click(function(){
            $('html,body').animate({scrollTop:0},500);
        });
    });
 
    </script>
    
    </content>
</body>
</html>
