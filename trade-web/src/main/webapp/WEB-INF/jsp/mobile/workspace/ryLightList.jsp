<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">

<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/scrollpagination/scrollpagination.css" rel="stylesheet"/>
<link href="${ctx}/css/list.css" rel="stylesheet">
<link href="${ctx}/css/plugins/switch/bootstrap-switch.min.css" rel="stylesheet">

<style type="text/css">
.myicon-person {
    background-image: url(${ctx}/momedia/images/icon-person.png);
    display: inline-block;
    width: 13px;
    height: 12px;
    background-repeat: no-repeat;
    background-size:100%;
}
.myicon-person-active {
    background-image: url(${ctx}/momedia/images/icon-person-active.png)repeat;
}
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
.bootstrap-switch-red{
background-color: red;
    color: #fff;
}
.list li span:first-child{color: #555;width: 25%}
.list li span:nth-child(2){width: 43%}
.list li span:nth-child(3){width: 32%;text-align: right;}

</style>
<script type="text/javascript">
	var idList = [1];
	var taskitem = "";
	var caseCode = "";
	var prCode='';

</script>
</head>

<body >
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="parentOrgId" value="${parentOrgId}" />
	<input type="hidden" id="orgId" value="${orgId}" />
	<input type="hidden" id="userId" value="${userId}" />
	<input type="hidden" id="color" value="${color}" />



<div class="row" style="height: 110px;">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>我的任务列表</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<input type="checkbox" name="lampRadios" checked on-label='红灯' off-label='黄灯'	>
				</div>
				
			</div>
		</div>
	</div>
	 <a href="javascript:;" class="top"><i class="pic-top"></i></a>
<article class="list ibox-content" >
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

	<content tag="local_script"> <script
		 
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script	src="${ctx}/js/plugins/scrollpagination/scrollpagination.js"></script>
	<script	src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script>
	<script id="knowledgeListStyle1" type="text/html">
					{{if rows.length>0}}
						{{ each rows as item }}
						<li>
                        	<div>
                            	<span ><i class="icon-person"></i>{{item.taskName}}</span><span><i class="myicon-person"></i>{{item.realName}}</span> <span><i class="icon-calendar"></i>{{item.estPartTime}}</span>
                       		 </div>
                        	<p class="text-ellipsis"><i class="icon-address"></i>{{item.propertyAddress}}</p>
                    	</li>
						{{/each}}
					{{/if}}
	</script>
    <script>
  	var sw;
	var postData={queryId:"queryRyLightList",rows:20,page:1};
	function initScrollPaggination(){
			color=sw.bootstrapSwitch('state')?'0':'1';
			postData.search_color = color,
			postData.argu_parentOrgId = $("#parentOrgId").val(),
			postData.argu_orgId = $("#orgId").val(),
			postData.argu_uid =  $("#userId").val(),
			postData.sortname ="COLOR"
		postData.page=1;
		$('#content').empty();
    	$('#content').scrollPagination({
    		'contentPage': ctx+'/quickGrid/findPage',
    		'contentData': postData, // these are the variables you can pass to the request, for example: children().size() to know which page you are
    		'scrollTarget': $(window), // who gonna scroll? in this example, the full window
    		'heightOffset': 10, // it gonna request when scroll is 10 pixels before the page ends
    		'beforeLoad': function(){ // before load function, you can display a preloader div
    			$('#loading').fadeIn();	
    		},'dataType':'json',
    		'afterLoad': function(elementsLoaded){ // after loading content, you can use this function to animate your new elements
	   			$('#loading').fadeOut();
	 			if($.isEmptyObject(elementsLoaded)||elementsLoaded.length==0){
	 				$('#nomoreresults').show();	
	 				setTimeout(nomoreresultsOut,2000);			 	
	 			}else{
	 				postData.page+=1;
		   		}
	 			$(elementsLoaded).fadeInWithDelay();

    		},render:function(data){
    			var html=template('knowledgeListStyle1', data); 
    			return html;
    		}
    	});
	}
    function nomoreresultsOut(){
    	$('#nomoreresults').fadeOut()
    }
	
    $(function(){
    var st=$("#color").val()=='0';
    sw=$("[name='lampRadios']").bootstrapSwitch({'onText':"红灯",'offText':'黄灯',onColor:'red',offColor:'warning',state:st}).on('switchChange.bootstrapSwitch', function(event, state) {
  initScrollPaggination();
});
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
    	initScrollPaggination();
    });
 
    </script>
    
    </content>
</body>
</html>
