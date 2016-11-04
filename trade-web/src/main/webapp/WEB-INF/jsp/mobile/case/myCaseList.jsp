<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>经纪人案件列表</title>
	<script type="text/javascript" src="${ctx}/momedia/js/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="${ctx}/momedia/js/iscroll-probe.js"></script>
	<script type="text/javascript" src="${ctx}/momedia/js/iscroll.js?v=1.1.2"></script>
	<script src="${ctx}/momedia/js/template.js"></script>
	<link rel="stylesheet" href="${ctx}/momedia/css/myCaseListStyle.css">
	<style type="text/css">
	.loading{display: block;}
	</style>
	</script>
	<script type="text/javascript">
	var ctx="${ctx}";
	var postData={queryId:"queryCastListItemListForMobile",search_prStatus:'1',search_agentId:'${userId}',rows:10,page:1};
	
    function goDetails(caseId){
    	location.href=ctx+'/weixin/case/progressDetailList?caseId='+caseId;
    }
    $(document).ready(function(){
    	loaded("init");
    	$("#btn_search").click(function(){
    		
    		loaded('init');
    	});
    });
	</script>
</head>
<body onload="">
	<form action="" class="search-box">
	<input type="hidden" id="userId" value="${userId }">
	<input type="text" id="propertyAddr" placeholder="输入地址" class="search-txt"><button onclick="" type="button" class="search-btn" id="btn_search"><i class="icon-search"></i>搜 索</button>
	</form>
	<script id="castListTpl" type="text/html">
	{{if rows.length>0}}
		{{ each rows as item }}
			<li><div><span class='text-ellipsis' onclick='javascript:goDetails("{{item.PKID}}");'>{{item.PROPERTY_ADDR}}</span><span><i class='icon-calendar'></i>{{item.create_time}}</span></div>
				<p><span onclick='javascript:goDetails("{{item.PKID}}");'><i class='icon-label'></i> {{item.CASE_CODE}}</span><span>下家：{{item.BUYER}}</span><span>上家：{{item.SELLER}}</span></p></li>
		{{/each}}
	{{/if}}
	</script>
	<h2>共有 <em id="e_count">0</em> 条信息</h2>
	<div id="wrapper">
		<div id="scroller">
			
			<ul id="content">

			</ul>
		<div class="pullUp">
			<span class="pullUpLabel"></span>
		</div>
		</div>
	</div>
	
</body>
</html>
