<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>经纪人案件列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/momedia/css/common/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/momedia/css/common/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/momedia/iconfont/iconfont.css" />
    <!--date css-->
    <link rel="stylesheet" type="text/css" href="${ctx}/momedia/css/plugins/mobiscroll/mobiscroll.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/momedia/css/plugins/mobiscroll/mobiscroll_date.css" />

</head>
<body>
    <div class="aui-searchbar aui-clearfix" id="search">
        <div class="aui-searchbar-input aui-border-radius" tapmode onclick="doSearch()">
            <i class="aui-iconfont aui-icon-search"></i>
            <form action="javascript:search();">
           		<input type="hidden" id="userId" value="${userId }">
                <input type="search" placeholder="查询姓名或地址" id="propertyAddr" >
            </form>
        </div>
        <div class="aui-searchbar-cancel white-text" tapmod style="margin-right: 0px;" id="btn_search">搜索</div>
    </div>
    <div class="aui-list aui-form-list aui-searchbar-margin">
        <div class="aui-item">
            <div class="aui-list-item-inner aui-input-list">
                    <div class="aui-col-xs-3 aui-input-element filter" value="30003003">
                        在途案件
                    </div>
                    <div class="aui-col-xs-3 aui-input-element filter" value="30003002">
                        结案案件
                    </div>
                    <div class="aui-col-xs-3 aui-input-element filter" value="30003005">
                        爆单案件
                    </div>
                    <div class="aui-col-xs-3 aui-input-element filter" value="30003001">
                        无效案件
                    </div>
            </div>
        </div>
    </div>
    <div class="aui-content aui-margin-b-15" id="wrapper">
   		<div id="scroller">
   			<div class="aui-list">
		   	<p class="aui-list-header newgrey">
		                     当前共有<span class="mlr5 deepgrey"><em id="e_count">0</em></span>条信息
		    </p>
		    </div>
	        <ul class="aui-list aui-media-list" id="content">
	            <!-- <li class="aui-list-item">
	                <div class="aui-media-list-item-inner">
	                    <div class="aui-list-item-inner">
	                        <div class="aui-list-item-text mb10">
	                            <div class="aui-list-item-title font-small aui-text-grey"><span class="filter-style">已签约</span>SHYPS609100004</div>
	                            <div class="aui-list-item-right aui-text-grey"><i class="iconfont iconfont12 mr5">&#xe611;</i>2016-09-25</div>
	                        </div>
	                        <div class="aui-list-item-text aui-ellipsis-2 font-small mb5 midgrey">
	                            上海市浦东而去祝桥片区航亭环路祝桥片区航亭环
	                        </div>
	                        <div class="aui-list-item-text">
	                            <div class="aui-list-item-title font-small midgrey">上家：刘佩佩</div>
	                            <div class="aui-list-item-right midgrey">下家：林么么</div>
	                        </div>
	                    </div>
	                </div>
	            </li> -->
	        </ul>
	        <div class="pullUp">
				<span class="pullUpLabel"></span>
			</div>
        </div>
    </div>
</body>

<script type="text/javascript" src="${ctx}/momedia/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/api.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/layer.js"></script>
<script src="${ctx}/momedia/js/template.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/iscroll-probe.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/iscroll.js?v=1.1.2"></script>
<!--date-->
<script type="text/javascript" src="${ctx}/momedia/js/plugins/mobiscroll/mobiscroll_date.js"></script>
<script type="text/javascript" src="${ctx}/momedia/js/plugins/mobiscroll/mobiscroll.js"></script>

<script type="text/javascript" src="${ctx}/momedia/js/calendar.js"></script>
<script id="castListTpl" type="text/html">
	{{if rows.length>0}}
		{{ each rows as item }}
 			<li class="aui-list-item">
                <div class="aui-media-list-item-inner">
                    <div class="aui-list-item-inner">
                        <div class="aui-list-item-text mb10">
                            <div class="aui-list-item-title font-small aui-text-grey"><span class="filter-style">{{item.STATUS}}</span><span onclick='javascript:goDetails("{{item.PKID}}");'><i class='icon-label'></i> {{item.CASE_CODE}}</span></div>
                            <div class="aui-list-item-right aui-text-grey"><i class="iconfont iconfont12 mr5">&#xe611;</i>{{item.create_time}}</div>
                        </div>
                        <div class="aui-list-item-text aui-ellipsis-2 font-small mb5 midgrey">
                            {{item.PROPERTY_ADDR}}
                        </div>
                        <div class="aui-list-item-text">
                            <div class="aui-list-item-title font-small midgrey">上家：{{item.SELLER}}</div>
                            <div class="aui-list-item-right midgrey">下家：{{item.BUYER}}</div>
                        </div>
                    </div>
                </div>
            </li>
		{{/each}}
   {{/if}}
</script>
<script>
var ctx="${ctx}";
var postData={queryId:"queryCastListItemListForMobile",search_prStatus:'1',search_agentId:'${userId}',rows:10,page:1};
$(function () {
	loaded("init");
    var $filter = $(".filter");
    $filter.click(function() {
        if($(this).hasClass("filter-mark")) {
            $(this).removeClass("filter-mark");
            
            postData={queryId:"queryCastListItemListForMobile",search_prStatus:'1',search_agentId:'${userId}',rows:10,page:1};
        }
        else {
            $(this).addClass("filter-mark");
        }
        var caseProperty = new Array();
        $(".filter").each(function(index,item){
        	if($(this).hasClass("filter-mark")) {
        		caseProperty.push($(this).attr("value"));
            }
        });
        postData={queryId:"queryCastListItemListForMobile",search_prStatus:'1',search_agentId:'${userId}',rows:10,page:1,search_caseProperty:caseProperty};
        loaded('init');
    });

    $("#btn_search").click(function(){
		loaded('init');
	});
});

function goDetails(caseId){
	location.href=ctx+'/weixin/case/progressDetailList?caseId='+caseId;
}
</script>
</html>
