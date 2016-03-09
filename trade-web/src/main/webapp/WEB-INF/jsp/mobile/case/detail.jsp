<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>详情</title>
	<script src="${ctx}/momedia/js/template.js"></script>
	<link rel="stylesheet" href="${ctx}/momedia/css/caseDetailsStyle.css">
	<script type="text/javascript" src="${ctx}/momedia/js/jquery-2.1.1.js"></script>
</head>
<script type="text/html" id="timeLine">
	{{if rows.length>0}}
		{{ each rows as item }}
<div class="timeline-item">
<div class="date"><i class='icon-date'></i></i></div>
<div class="content">
	<p>{{item.real_time}}</p>
	<p><strong>{{item.NAME}}</strong><span><i class='icon-person'></i>负责人:{{item.ASSIGNEE}}</span></p>
</div>
</div>
{{/each}}
{{/if}}
</script>
<script type="text/javascript">
var ctx="${ctx}";
var postData={queryId:"queryTaskHistoryItemListForMobile",argu_casecode:'${toCase.caseCode }',rows:999,page:1};
$(document).ready(function(){
	$.ajax({
        type: 'POST',
        url: ctx+'/mobile/case/box/findPage',
        data: postData,
        success: function(data){
     	   var html=template('timeLine', data); 
				$("#content").append(html);
        },
        dataType: 'json',
        complete : function(){
        }
  });
});
</script>
<body>
	<header>
		<h1 class="text-ellipsis">${toPropertyInfo.propertyAddr }</h1>
		<p><i class='icon-label-white'></i> ${toCase.caseCode }&nbsp;&nbsp;<i class='icon-date-white'></i></i> <fmt:formatDate value="${toCase.createTime}" pattern="yyyy/MM/dd"/></p>
		
	</header>
	<ul class="desc">
			<li class="active"><i class='icon-person-active'></i>负责人： ${leading.realName }&nbsp;&nbsp;<i class='icon-phone-active'></i></i> ${leading.mobile }</li>
			    <c:if test="${!empty proList}">
                                            <c:forEach items="${proList}" var="pro"> 
                                            <li><i class='icon-person'></i>合作人：${pro.processorName}&nbsp;&nbsp;<i class='icon-phone'></i></i> ${pro.processorMobile}</li>
                                            </c:forEach>
                    </c:if>

		</ul>
	<div class="timeline" id="content">
		

	</div>
	<a href="#" class="btn-back" onclick="javascript:history.go(-1);return false;">返回</a>
</body>
</html>