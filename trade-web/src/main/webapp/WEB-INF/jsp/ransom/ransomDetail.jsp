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

<title>赎楼单详情</title>

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

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ransomCode" value="${detailVo.ransomCode}">
	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="ibox-content" id="reportOne">
			<h2 class="title">赎楼清尾</h2>  
			<small class="pull-right">编号：${detailVo.ransomCode}</small>
			<div class="row">
				<div class="col-lg-9">
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
					<div class="row">
						<div class="col-lg-12">
							<div class="row bs-wizard"
								style="border-bottom: 0; margin-left: 15px">
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
								<div
									class="col-lg-2 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> disabled</c:otherwise>  
										</c:choose>	
										">
									<div class="progress">
										<div class="progress-bar"></div>
									</div>
									<a href="#" class="bs-wizard-dot"></a>
									<div class="bs-wizard-info text-center">
										<dl>
											<dd>
												<strong>申请</strong>${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万</dd>
										</dl>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="row">
						<div class="col-lg-12">
							<div class="m-b-md">
								<h4>关联案件</h4>
							</div>
						</div>
					</div>
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
		</div>
		<div class="ibox-content" id="reportTwo">
			<div class="row">
				<div class="fk-block col-lg-12">
					<div class="panel blank-panel">
						<h2 class="title">赎楼单详情</h2>
						<div class="details-update">
							<a href="javascript:void(0)">变更金融权证</a> 
							<a href="${ctx }/ransomList/ransom/ransomDetailUpdate">修改赎楼单详情</a>
							<a href="${ctx }/ransomList/ransom/planTime">修改时间计划</a>
						</div>
						<hr>
						<table class="table table_blue table-striped table-bordered table-hover ">
							<thead>
								<tr>
									<th>尾款机构</th>
									<th>尾款类型</th>
									<th>抵押类型</th>
									<th>贷款金额</th>
									<th>剩余部分</th>
									<th>实际还款金额</th>
								</tr>
							</thead>
							<tbody id="tails"></tbody>
						</table>
						<div class="form_list">
							<div class="marinfo">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small">
											<font color=" red" class="mr5">*</font> 借款金额
								 			&nbsp;<c:if test="${!empty detailVo.borrowMoney }">${detailVo.borrowMoney/10000 }&nbsp;&nbsp;万</c:if>
							 			</label>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 面签金额 
											&nbsp;<c:if test="${!empty detailVo.interViewMoney }">${detailVo.interViewMoney/10000 }&nbsp;&nbsp;万</c:if>
										</label> 
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 还贷金额
											&nbsp;<c:if test="${!empty detailVo.repayLoanMoney }">${detailVo.repayLoanMoney/10000 }&nbsp;&nbsp;万</c:if>	
										</label> 
									</div>
								</div>
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 贷款费用&nbsp;  ${detailVo.interest}&nbsp;‰。每天
										
										</label>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font>是否委托公正&nbsp;&nbsp; 
												<c:if test="${detailVo.isEntrust == 1 }">是</c:if>
												<c:if test="${detailVo.isEntrust == 0 }">否</c:if>
										</label>
									</div>
								</div>
								<div class="ibox-content">
									<h2 class="title">时间信息</h2>
									<hr>
									<table
										class="table table_blue table-striped table-bordered table-hover ">
										<thead>
											<tr>
												<th></th>
												<th>实际办理时间</th>
												<th>预计完成时间</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>受理时间</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>申请时间</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>面签时间</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>陪同还贷时间(一抵)</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>注销抵押时间(一抵)</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>领取产证时间(一抵)</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
											<tr>
												<td>回款结清时间</td>
												<td>2017-01-01</td>
												<td>2017-01-01</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="ibox-content">
									<h2 class="title">操作记录</h2>
									<div>
										<button class="btn btn-success btn-space" onclick="">只看赎楼</button>
										<button class="btn btn-success btn-space" onclick=""
											id="btnSubmit">全部流程</button>
									</div>
									<table
										class="table table_blue table-striped table-bordered table-hover ">
										<thead>
											<tr>
												<th>红绿灯</th>
												<th>红灯记录</th>
												<th>流程环节</th>
												<th>所属流程</th>
												<th>执行人</th>
												<th>执行时间</th>
												<th>任务状态</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>红灯</td>
												<td>红灯</td>
												<td>申请</td>
												<td>赎楼流程</td>
												<td>交易部小罗</td>
												<td>2017-01-01</td>
												<td>完成</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="ibox-content">
									<h2 class="title">附件查看</h2>
									<table border="0">
										<tr>
											<td><img alt="" src="" width="60px" height="60px"></td>
											<td><img alt="" src="" width="60px" height="60px"></td>
										</tr>
										<tr>
											<td>身份证</td>
											<td>征信报告</td>
										</tr>
									</table>
								</div>
								<div class="ibox-content">
									<h2 class="title">历史申请记录</h2>
									<table
										class="table table_blue table-striped table-bordered table-hover ">
										<thead>
											<tr>
												<th>申请人</th>
												<th>申请时间</th>
												<th>申请金额</th>
												<th>申请机构</th>
												<th>终止原因</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>小王</td>
												<td>2017-01-01</td>
												<td>300万元</td>
												<td>天津xx典当公司</td>
												<td>客户征信差</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="ibox-content">
									<!-- 跟进信息 -->
									<h2 class="title">案件跟进</h2>
									<div id="caseCommentList" class="view-content"></div>
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
	<script src="<c:url value='/js/trunk/ransom/ransomDetail.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>

	<script id="template_ransomDetail" type= "text/html">	
		{{each tails as item index}}
			<tr>
				<td>
					<p>
						{{item.tailOrgName}}
					</p>
				</td>
				<td>
					<p>
						{{item.retaTypeName}}
					</p>
				</td>
				<td>
					<p>
						{{item.diyaTypeName}}
					</p>
				</td>
				<td>
					<p>
						{{if item.loanMoney !=null}}
							{{item.loanMoney/10000}}万元
						{{/if}}
					</p>
				</td>
				<td>
					<p>
						{{if item.restMoney !=null}}
							{{item.restMoney/10000}}万元
						{{/if}}
					</p>
				</td>
				<td>
					<p>
						{{if item.actualMoney !=null}}
							{{item.actualMoney/10000}}万元
						{{/if}}
					</p>
				</td>
			</tr>
		{{/each}}
	</script>

</content>
</body>
</html>



