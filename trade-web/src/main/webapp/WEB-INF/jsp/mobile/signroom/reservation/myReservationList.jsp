<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/signroom/iconfont/iconfont.css" />

</head>
<body>
	<input type="hidden" id="ctx" value="${ctx }"/>
	<input type="hidden" id="agentCode" value="${agentCode }"/>
    <header class="aui-bar aui-bar-nav">
        <div class="aui-title">我的预约</div>
        <a class="aui-pull-right aui-btn " href="${ctx }/weixin/signroom/list">
	        <span class="aui-iconfont aui-icon-plus"></span>
	    </a>
    </header>
    <article class="aui-content">
        <ul class="aui-list aui-media-list white" id="myReservationList">
            
        </ul>
    </article>




</body>

<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/api.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/common/layer.js"></script>
<script type="text/javascript" src="${ctx}/mobilejs/signroom/myReservationList.js"></script>
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
</html>
<script id="template_myReservationList" type="text/html">
      {{each rows as item index}}
  			<li class="aui-list-header header_grey_bg">
                <i class="iconfont blue mr5">&#xe605;</i>{{item.actStartDate}}-{{item.actEndDate}}&nbsp;<span class="color80">{{item.resDate}}</span>
            </li>
            <li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-title font15 order-title">{{item.distinctName}}<span class="ml10">预约号：<em class="yellow">{{item.resNo}}</em></span></div>
                        <div class="aui-list-item-text font12">
                            房屋地址：{{item.propertyAddr}}
                        </div>
                    </div>
                    <div class="aui-list-item-media listspace">
						{{if item.resStatus == '1'}}
							<div class="aui-btn trans_bg">使用中</div>
						{{/if}}

						{{if item.resStatus == '2'}}
							<div class="aui-btn trans_bg">使用完</div>
						{{/if}}

						{{if item.resStatus == '4'}}
							<div class="aui-btn trans_bg red">已取消</div>
						{{/if}}

						{{if item.resStatus == '0'}}
							{{if item.currentTime > item.endDate}}
								<div class="aui-btn trans_bg">已过期</div>
							{{else}}
								<div class="aui-btn cancel" id="{{item.resId}}" onclick="openDialog('text','{{item.resId}}')">取消预约</div>
							{{/if}}
						{{/if}}
                    </div>

					{{if item.currentDate == item.resDate}}
						<span class="shuxing"></span>
					{{/if}}
                </div>
            </li>  
      {{/each}}
</script> 