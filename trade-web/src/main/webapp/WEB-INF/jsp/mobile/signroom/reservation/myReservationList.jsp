<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/mobile/signroom/aui.2.0.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/mobile/signroom/style.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/mobile/signroom/iconfont/iconfont.css' />" />

</head>
<body>
	<input type="hidden" id="ctx" value="${ctx }"/>
	<input type="hidden" id="agentCode" value="${agentCode }"/>
	
	<header class="aui-bar aui-bar-nav">
        <div class="aui-title">我的预约</div>
        <a class="aui-pull-right aui-btn " href="#" id="goToBespeakUI">
	        <span class="aui-iconfont aui-icon-plus"></span>
	    </a>
    </header>
	
    <article class="aui-content" id="myReservationList">
            
    </article>
</body>

<script type="text/javascript" src="<c:url value='/mobilejs/signroom/common/jquery-2.1.1.js' />"></script>
<script type="text/javascript" src="<c:url value='/mobilejs/signroom/common/api.js' />"></script>
<script type="text/javascript" src="<c:url value='/mobilejs/signroom/common/aui-dialog.js' />"></script>
<script type="text/javascript" src="<c:url value='/mobilejs/signroom/common/aui-main.js' />"></script>
<script type="text/javascript" src="<c:url value='/mobilejs/signroom/common/layer.js' />"></script>
<script type="text/javascript" src="<c:url value='/mobilejs/signroom/myReservationList.js' />"></script>
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script> 
<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
</html>
<script id="template_myReservationList" type="text/html">
	{{if rows.length > 0}}
		<ul class="aui-list aui-media-list">
			<li class="aui-list-header">
                <div class="aui-info pd0">
                    <div class="aui-info-item">
                       	<span class="aui-margin-l-5 book">您还剩余<span class="num">${remainBespeakNumber}</span>次预约机会</span>
                    </div>
                </div>
            </li>
      	{{each rows as item index}}
			
						<input type="hidden" value="{{item.currentDate}}" />
<input type="hidden" value="{{item.resDate}}" />
            <li class="aui-list-item" style="margin-bottom:8px">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title left mr10">{{item.tradeCenterName}}<span class="newgrey font12">(参与人数:{{item.numberOfParticipants}}人)</span></div>
                        <div class="aui-list-item-title font15 order-title left" >
                            	预约号：<em class="yellow">{{item.resNo}}</em>
                        </div>
                        <div class="aui-list-item-text font12 newgrey clear">
                            	房屋地址：{{item.propertyAddr}}
                        </div>
                        <div class="aui-list-item-text font12 newgrey">
                            	备注：{{item.specialReq}}
                        </div>
						<div class=" font14 newgrey">
                            <i class="iconfont blue mr5 font14">&#xe605;</i>{{item.resDate}} {{item.actStartDate}}-{{item.actEndDate}}
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace"></div>
						{{if item.resStatus == '0'}}
							<div class="aui-btn aui-center" style="z-index:1" id="{{item.resId}}" onclick="openDialog('text','{{item.resId}}',this)">取消预约</div>
						{{/if}}

						{{if item.resStatus == '1'}}
							<div class="aui-btn aui-center trans_bg">使用中</div>
						{{/if}}

						{{if item.resStatus == '2'}}
							<div class="aui-btn aui-center trans_bg">已使用</div>
						{{/if}}

						{{if item.resStatus == '3'}}
							<div class="aui-btn aui-center trans_bg">已过期</div>
						{{/if}}

						{{if item.resStatus == '4'}}
							<div class="aui-btn aui-center trans_bg red">已取消</div>
						{{/if}}


					{{if item.currentDate == item.resDate}}
						<span class="shuxing"></span>
					{{/if}}
                </div>
            </li>  
      {{/each}}
	</ul>
	{{else}}
		<section class="aui-content  reminder-login">
        	<img src="${ctx}/image/reminder.png" width="100%" alt="" />
        	<h3 style="text-align:center">对不起，<br/>当前无签约室预约信息！</h3>
    	</section>
	{{/if}}
</script> 