<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>赎楼单详情</title>

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"rel="stylesheet" />
<link rel="stylesheet"href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet"href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet"href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet"href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet"href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet"href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet"href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />"type="text/css" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- stickUp fixed css -->
<link href="<c:url value='/static/trans/css/common/hint.css' />" rel="stylesheet" />
<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">

<link href="<c:url value='/css/plugins/jasny/jasny-bootstrap.min.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<style type="text/css">
	.interval{width:300px;}
	.row11{margin-left: 315px;}
	.time-up{color: blue;}    
	.userHead1{
		width: 80px;
		height: 80px;
		display: inline-block;
		border-radius: 50%;
		background-size: 80px 108px;
		vertical-align: middle;
		background-image:url(../static/img/a5.png);
	}
	.bs-wizard > .bs-wizard-step > .shuxian{
		position: absolute;
		height: 129px;
		width: 40%;
		top: 20px;
		left: 50%;
		border-left: 8px solid #5bc0de;
		border-bottom: 8px solid #5bc0de;
	}
	.bs-wizard > .bs-wizard-step.complete > .shuxian{
		border-left-color: #ccc;
		border-bottom-color: #ccc;
	}
	.bs-wizard > .bs-wizard-step > .change_shuxian{
		position: absolute;
		height: 129px;
		width: 85%;
		top: 20px;
		left: -190px;
		border-right: 8px solid #5bc0de;
		border-bottom: 8px solid #5bc0de;
	}
	.bs-wizard > .bs-wizard-step.complete > .change_shuxian{
		border-right-color: #ccc;
		border-bottom-color: #ccc;
	}
	.col-lg-1 .bs-wizard-info strong,.col-lg-1 .time-up strong{
		display: block;
		width: 200px;
	}
	.time-wapper {
		text-align: center;
	}
	.bs-wizard > .bs-wizard-step.complete > .progress > .progress-bar{
		background: #ccc;
	}
	.bs-wizard > .bs-wizard-step.active > .progress > .progress-bar{
		width: 100%;
	}
	.bs-wizard > .bs-wizard-step:first-child.active > .progress > .progress-bar{
		width: 100%;
	}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ransomCode" value="${detailVo.ransomCode}">
	<input type="hidden" id="caseCode" value="${detailVo.caseCode}">
	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="ibox-content" id="reportOne">
			<div class="row">
				<h2 class="col-sm-8 title">赎楼清尾</h2>
				<small class="col-sm-2 pull-right">编号：${detailVo.ransomCode}</small>
				<div class="col-sm-2 m-b-md">
					<h4>关联案件</h4>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4" id="cluster_info">
					<dl class="dl-horizontal">
						<dt>借款人</dt>
						<dd>
							<a data-toggle="popover" data-placement="right" data-content="${detailVo.borrowTel }" id="borrowerUser"> ${detailVo.borrowName }</a>
						</dd>
						<dt>房屋地址</dt>
						<dd>${detailVo.addr }</dd>
					</dl>
				</div>
				<div class="col-sm-3" id="cluster_info">
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
				<div class="col-lg-2">
					<div class="feed-activity-list">
						<div class="feed-element">
							<div class="pull-lef contract-icon-block">
								<div class="icon icon_blue iconfont">&#xe607;</div>
							</div>
							<div class="media-body">
								<strong><a>${detailVo.caseCode }</a></strong><br />经办人 <strong>${detailVo.leadingProcessName }</strong>
								<br />
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>

			<div class="row">
				<div class="col-lg-10">
					<br>
					<div class="row">
						<div class="col-lg-12">
							<div class="row bs-wizard"
								style="border-bottom: 0; margin-left: 15px">
								<div
									class="col-lg-1 bs-wizard-step 
										<c:choose>  
										    <c:when test=""> active
										   </c:when>  
										    <c:when test=""> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${tailinsVo.signTime }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd><strong>受理</strong></dd>
										</dl>
									</div>
								</div>
								<div class="col-lg-1 bs-wizard-step 
										 <c:choose>  
										    <c:when test="${empty actTasks['APPLY']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['APPLY']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	 
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${applyVo.applyTime }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong></dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										  <c:choose>  
										    <c:when test="${empty actTasks['SIGN']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['SIGN']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose> 	 
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<div class="shuxian"></div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${signVo.signTime }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>面签</strong></dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${empty actTasks['PAYLOAN_ONE']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['PAYLOAN_ONE']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${mortgageMap['PAYLOAN_ONE'] }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>陪同还贷(一抵)</strong></dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${empty actTasks['CANCELDIYA_ONE']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['CANCELDIYA_ONE']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${cancelMap['CANCELDIYA_ONE'] }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>注销抵押(一抵)</strong></dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${empty actTasks['RECEIVE_ONE']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['RECEIVE_ONE']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${permitMap['RECEIVE_ONE'] }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>领取产证(一抵)</strong></dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${empty actTasks['PAYCLEAR']}"> active
										   </c:when>  
										    <c:when test="${!empty actTasks['PAYCLEAR']}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<div class="change_shuxian"></div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="time-up">
										<dl>
											<dd><strong><fmt:formatDate value="${paymentVo.paymentTime }" pattern="yyyy-MM-dd"/></strong></dd>
										</dl>
									</div>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>回款结清</strong></dd>
										</dl>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row11">
							<div class="line"></div>
							<div class="col-lg-12">
								<div class="row bs-wizard"
									style="border-bottom: 0; margin-left: 15px">
									<div
										class="col-lg-3 bs-wizard-step 
											<c:choose>  
											    <c:when test="${empty actTasks['PAYLOAN_TWO']}"> active
											   </c:when>  
											    <c:when test="${!empty actTasks['PAYLOAN_TWO']}"> complete
											   </c:when>   
											   <c:otherwise> disabled</c:otherwise>  
											</c:choose>	
											">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="time-up">
											<dl>
												<dd><strong><fmt:formatDate value="${mortgageMap['PAYLOAN_TWO'] }" pattern="yyyy-MM-dd"/></strong></dd>
											</dl>
										</div>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>陪同还贷(二抵)</strong></dd>
											</dl>
										</div>
									</div>
									<div
										class="col-lg-3 bs-wizard-step 
											<c:choose>  
											    <c:when test="${empty actTasks['CANCELDIYA_TWO']}"> active
											   </c:when>  
											    <c:when test="${!empty actTasks['CANCELDIYA_TWO']}"> complete
											   </c:when>   
											   <c:otherwise> disabled</c:otherwise>  
											</c:choose>	
											">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="time-up">
											<dl>
												<dd><strong><fmt:formatDate value="${cancelMap['CANCELDIYA_TWO'] }" pattern="yyyy-MM-dd"/></strong></dd>
											</dl>
										</div>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>注销抵押(二抵)</strong></dd>
											</dl>
										</div>
									</div>
									<div
										class="col-lg-1 bs-wizard-step 
											<c:choose>  
											    <c:when test="${empty actTasks['RECEIVE_TWO']}"> active
											   </c:when>  
											    <c:when test="${!empty actTasks['RECEIVE_TWO']}"> complete
											   </c:when>   
											   <c:otherwise> disabled</c:otherwise>  
											</c:choose>	
											">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>

										<a href="#" class="bs-wizard-dot"></a>
										<div class="time-up">
											<dl>
												<dd><strong><fmt:formatDate value="${permitMap['RECEIVE_TWO'] }" pattern="yyyy-MM-dd"/></strong></dd>
											</dl>
										</div>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>领取产证(二抵)</strong></dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
				</div>
				<div class="col-lg-2">
				</div>
			</div>
		</div>
		<div class="ibox-content" id="reportTwo">
			<div class="row">
				<div class="fk-block col-lg-12">
					<div class="panel blank-panel">
						<h2 class="title">赎楼单详情</h2>
						<div class="details-update" style="float: right;">
							<input type="button" onclick="showOrgCp()" class="btn btn-success btn-space" value="变更金融权证" />
							<!-- <shiro:hasPermission name="TRADE.CASE.CASEDETAIL.LEADCHANGE">
								<a role="button" class="btn btn-primary btn-xm btn-activity" href="javascript:showOrgCp()">责任人变更</a>
							</shiro:hasPermission> -->
							<a class="btn btn-success btn-space" href="${ctx }/ransomList/updateRansomInfo?caseCode=${detailVo.caseCode}" target="_blank">修改赎楼单详情</a>
							<a class="btn btn-success btn-space" href="${ctx }/ransomList/planTime?ransomCode=${detailVo.ransomCode}" target="_blank">修改时间计划</a>
						</div>
						<hr>
						<div class="tab-pane active fade in" style="margin-top: 35px;">
							<div class="jqGrid_wrapper row">
								<table id="tails"></table>
								<div id="tails_pager"></div>
							</div>
						</div>
						<div class="form_list">
							<div class="marinfo">
								<div class="line">
									<div class="form_content interval">
										<label class="control-label sign_left_small">
											<font color=" red" class="mr5">*</font> 借款金额
							 			</label>
								 			&nbsp;<c:if test="${!empty moneyVo.borrowerMoney }">${moneyVo.borrowerMoney/10000 }&nbsp;&nbsp;万</c:if>
									</div>
									<div class="form_content interval">
										<label class="control-label sign_left_small"><fontcolor=" red" class="mr5">*</font> 面签金额 </label> 
											&nbsp;<c:if test="${!empty moneyVo.interViewMoney }">${moneyVo.interViewMoney/10000 }&nbsp;&nbsp;万</c:if>
									</div>
									<div class="form_content interval">
										<label class="control-label sign_left_small"><fontcolor=" red" class="mr5">*</font> 还贷金额</label> 
											&nbsp;<c:if test="${!empty moneyVo.repayLoanMoney }">${moneyVo.repayLoanMoney/10000 }&nbsp;&nbsp;万</c:if>	
									</div>
								</div>
								<div class="line">
									<div class="form_content interval">
										<label class="control-label sign_left_small"><fontcolor=" red" class="mr5">*</font> 贷款费用</label>
											&nbsp;  ${moneyVo.interest}&nbsp;‰。每天
									</div>
									<div class="form_content interval">
										<label class="control-label sign_left_small" style="width: 133px;"><font color=" red" class="mr5">*</font>是否委托公正&nbsp;&nbsp;</label>
											<c:if test="${moneyVo.isEntrust == 1 }">是</c:if>
											<c:if test="${moneyVo.isEntrust == 0 }">否</c:if>
									</div>
								</div>
								<div class="ibox-content">
									<h2 class="title">时间信息</h2>
									
									<table class="table table_blue table-striped table-bordered table-hover ">
										<thead >
											<tr>
												<th class="time-wapper" style="background-color:#00CED1 ;"></th>
												<th class="time-wapper" style="background-color:#00CED1 ;">实际办理时间</th>
												<th class="time-wapper" style="background-color:#00CED1 ;">预计完成时间</th>
											</tr>
										</thead>
										<tbody id="time-record">
												<tr>
													<td>受理时间</td><td><fmt:formatDate value="${tailinsVo.signTime }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${tailinsVo.planTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>申请时间</td><td><fmt:formatDate value="${applyVo.applyTime }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${appTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>面签时间</td><td><fmt:formatDate value="${signVo.signTime }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${signTime}" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>陪同还贷时间(一抵)</td><td><fmt:formatDate value="${mortgageMap['PAYLOAN_ONE'] }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${morTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>注销抵押时间(一抵)</td><td><fmt:formatDate value="${cancelMap['CANCELDIYA_ONE'] }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${canTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>领取产证时间(一抵)</td><td><fmt:formatDate value="${permitMap['RECEIVE_ONE'] }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${perTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
												<tr>
													<td>回款结清时间</td><td><fmt:formatDate value="${paymentVo.paymentTime }" pattern="yyyy-MM-dd"/></td><td><fmt:formatDate value="${payTime }" pattern="yyyy-MM-dd"/></td>
												</tr>
										</tbody>
									</table>
								</div>
								<div class="ibox-content" >
									<h2 class="title">操作记录</h2>
									<!-- <div>
										<button class="btn btn-success btn-space" onclick="getOperateLogRansom()">只看赎楼</button>
										<button class="btn btn-success btn-space" onclick="getOperateLogList()" id="btnSubmit">全部流程</button>
									</div> -->
									<div class="tab-content" style="margin-top: 35px">
										<div class="tab-pane active fade in">
											<div class="jqGrid_wrapper row">
												<table id="operation_history_table"></table>
												<div id="operation_history_pager"></div>
											</div>
										</div>
									</div>
								<div class="ibox-content">
									<h2 class="title">附件查看</h2>
									<div class="tab-pane fade" id="fujian_info">
										<div class="panel-body">
											<div id="imgShow" class=""></div>
										</div>
									</div>
								</div>
								<div class="ibox-content">
									<h2 class="title">历史申请记录</h2>
									<div class="tab-content">
										<div class="tab-pane active fade in" style="margin-top: 35px;">
											<div class="jqGrid_wrapper row">
												<table id="his_record"></table>
												<div id="his_record_pager"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="ibox-content border-bottom clearfix space_box noborder">
									<div id="caseCommentList" class="add_form"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 变更金融权证 -->
		<div id="leading-modal-form"class="modal fade" aria-labelledby="modal-title"
		 aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="leading-modal-title">
							请选择责任人</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 450px; overflow: auto;">
							<div class="col-lg-12 ">
								<h3 class="m-t-none m-b"></h3>
								<div class="wrapper wrapper-content animated fadeInRight">
									<div id="leading-modal-data-show" class="row"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>		
		
		
	</div>

	<!-- main End -->
	<content tag="local_script">
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jasny/jasny-bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/trunk/case/moduleSubscribe.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<script src="<c:url value='/js/ransom/ransomDetail.js'/>" type="text/javascript"></script>
	<!-- 各个环节的备注信息  -->
	<script src="<c:url value='/js/trunk/case/caseRemark.js' />"></script>
	<!-- 公共信息js -->	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
</content>

</body>
</html>



