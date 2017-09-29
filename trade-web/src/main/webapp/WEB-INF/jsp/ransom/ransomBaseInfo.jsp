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
	.case-info{height: 25px}
	.dl-horizontal dd{color:blue;}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="ibox-content border-bottom clearfix space_box">
		<div class="col-lg-8 case-info">
			<h2 class="title">案件基本信息</h2>
		</div>
		<div class="col-lg-4 case-info">
			<dl class="dl-horizontal ">
				<dt><h2 class="title">编号</h2></dt>
				<dd>
					<a href="${ctx }/ransomList/ransomDetail?ransomCode=${detailVo.ransomCode}" target="_blank">${detailVo.ransomCode}</a>
				</dd>
			</dl>
		</div>
		<div class="col-lg-12"><hr></div>
		<div class="row" id="">
			<div class="col-lg-5" id="cluster_info">
				<dl class="dl-horizontal">
					<dt>借款人</dt>
					<dd>
						<a data-toggle="popover" data-placement="right" data-content="${detailVo.borrowTel }"> ${detailVo.borrowName }</a>
					</dd>
					<dt>房屋地址</dt>
					<dd>${detailVo.addr }</dd>
				</dl>
			</div>
			<div class="col-lg-4" id="cluster_info">
				<dl class="dl-horizontal">
					<dt>合作机构</dt>
					<dd>${detailVo.comOrgName }</dd>
					<dt>信贷员</dt>
					<dd>
						<a data-toggle="popover" data-placement="right" data-content="${detailVo.creditTel }"> ${detailVo.credit }</a>
					</dd>
				</dl>
			</div>
			<div class="col-lg-3">
				<dl class="dl-horizontal">
					<dt>金融权证</dt>
					<dd>
						<a data-toggle="popover" data-placement="right" data-content="${detailVo.financialTel }">${detailVo.financial }</a>	
					</dd>
					<dt>经纪人</dt>
					<dd>
						<a data-toggle="popover" data-placement="right" data-content="${detailVo.agentPhone }">${detailVo.agentName }</a>
					</dd>
				</dl>
			</div>
		</div>
	</div>




