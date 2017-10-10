<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title></title>

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />

<link rel="stylesheet"
	href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />"
	type="text/css" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<style type="text/css">
.font_base{
    font-family: "Microsoft Yahei";
    font-size: 14px;
    font-weight: normal;
}
.margin-left{
	margin-left:20px;
}
.base-height{
	height:30px;
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="ibox-content border-bottom clearfix space_box">
		<div class="row base-height">
			<div class="col-sm-8 ">
				<div class="title">案件基本信息</div>
			</div>
			<div class="col-sm-4">
				<label class="title ">编号&nbsp;&nbsp;
					<a href="${ctx }/ransomList/ransomDetail?ransomCode=${detailVo.ransomCode}" target="_blank">${detailVo.ransomCode}</a>	
				</label>
			</div>
		</div>
		<hr>
		<div class="row" >
			<div class="row margin-left">
				<label class="font_base col-sm-4 ">借款人：
					<a id="borrowName" data-toggle="popover" data-placement="right" data-content="${detailVo.borrowTel }" style="margin-left:18px;"> ${detailVo.borrowName }</a>
				</label>
				<label class="font_base col-sm-4 ">合作机构：&nbsp;&nbsp;${detailVo.comOrgName }</label>
				<label class="font_base col-sm-4 ">金融权证：&nbsp;&nbsp;
					<a data-toggle="popover" data-placement="right" data-content="${detailVo.financialTel }">${detailVo.financial }</a>						
				</label>
			</div>
			
			<div class="row margin-left">
				<label class="font_base col-sm-4 ">房屋地址：&nbsp;&nbsp;${detailVo.addr }</label>
				<label class="font_base col-sm-4 ">信贷员：
					<a data-toggle="popover" data-placement="right" data-content="${detailVo.creditTel }" style="margin-left:18px;"> ${detailVo.credit }</a>
				</label>
				<label class="font_base col-sm-4">经纪人：
					<a data-toggle="popover" data-placement="right" data-content="${detailVo.agentPhone }" style="margin-left:22px;">${detailVo.agentName }</a>
				</label>
			</div>
		</div>
	</div>




