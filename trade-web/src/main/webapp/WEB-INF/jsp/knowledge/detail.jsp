<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>知识库详情</title>

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Add fancyBox main JS and CSS files -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<style type="text/css">
.mainDiv{
	display:block;
	margin: 5 0 0 5;
}
body{font-family: 微软雅黑, Microsoft YaHei, Tahoma, Arial, Helvetica, Sans-Serif;
    color: #000000;}
</style>
</head>
<body>
	<!-- title start -->

	<!-- title end -->

	<input type="hidden" id="ctx" value="${ctx}" />
		<!-- post main content start -->
		<div class="ibox">
			<div class="ibox-title">
				<div class="">
					<h1 style="color: #000000">${data.knowledgeRepo.title}</h1>
					<div  >
						 <a href="#">${data.knowledgeRepo.pubiisherName}</a> 发表于<fmt:formatDate value="${data.knowledgeRepo.pbTime}" pattern="yyyy/MM/dd HH:mm:ss" /> •
						 ${data.knowledgeRepo.clickSum}人看过•${data.knowledgeRepo.likeSum}觉得很赞
					</div>
						<%-- <a href="#"> <img style="border-radius:50%;width: 80px;height: 80px;" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${data.knowledgeRepo.pubiisherCode}.jpg" onerror="javascript:this.src='${ctx}/img/a5.png'"
						alt="${data.knowledgeRepo.pubiisherName}"/> --%>
					</a>
				</div>
			</div>
			<div class="ibox-content">
					<p style="font-size: 18px;color: #000000">${data.knowledgeRepo.content}</p>
								<c:forEach items="${data.knowledgeRepoAttachmentList}" var="atta">
			<div class="row">
				<div class="col-lg-12"><a href="<aist:appCtx appName='aist-filesvr-web'/>/filesvr/downLoad?id=${atta.knowledgeFileCode}" download="${atta.fileName }">${atta.fileName }</a>
				</div>
			</div>
			</c:forEach>
			</div>

		</div>
		<!-- editor end -->
	<content tag="local_script"> </content>

</body>
</html>



