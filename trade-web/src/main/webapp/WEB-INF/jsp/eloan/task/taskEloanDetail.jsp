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
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>E+贷款详情</title>

<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<!-- index_css  -->
<link href="${ctx}/static/trans/css/eloan/eloan/eloan_detail.css"
	rel="stylesheet">
<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css"
	rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet" />
<link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet" />

</head>
<body>

	<div class="row">
		<div class="wrapper wrapper-content animated fadeInUp">
			<div class="ibox-content elan_detail">
				<div class="row">
					<div class="col-lg-9">
						<div class="row" id="">
							<div class="col-lg-12">
								<div class="m-b-md">
									<h4>
										<aist:dict id="loanSrvCode" name="loanSrvCode"
											clazz="select_control sign_right_two" display="onlyLabel"
											dictType="yu_serv_cat_code_tree" tag="Eloan"
											dictCode="${eloanCase.loanSrvCode}" ligerui='none'></aist:dict>
									</h4>
								</div>

							</div>
						</div>
						<div class="row" id="">
							<div class="col-lg-5" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>E+编号</dt>
									<dd>
										<a herf="#">${eloanCase.eloanCode }</a>
									</dd>
									<dt>产权地址</dt>
									<dd>${info.propertyAddr}</dd>
									<dt>合作机构</dt>
									<dd>${info.finOrgName}</dd>
								</dl>
							</div>
							<div class="col-lg-4" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>申请时间</dt>
									<dd>${info.applyTime}</dd>
									<dt>申请期数</dt>
									<dd>${eloanCase.month}</dd>
								</dl>
							</div>
							<div class="col-lg-3">
								<dl class="dl-horizontal">
									<dt>借款人</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right"
											data-content="手机：${eloanCase.custPhone}">${eloanCase.custName}</a>
									</dd>
									<dt>贷款专员</dt>
									<dd>
										<a data-container="body" data-toggle="popover"
											data-placement="right" data-content="手机：${info.excutorPhone}">${info.excutorName}</a>
									</dd>
									<dt>贷款转介人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.zjPhone}" --%>>${eloanCase.zjName}</span>
									</dd>
									<dt>合作信贷员</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.coName}</span>
									</dd>
									<dt>产品部合作人</dt>
									<dd>
										<span data-container="body" data-toggle="popover"
											data-placement="right"<%-- data-content="手机：${eloanCase.coPhone}" --%>>${eloanCase.pdName}</span>
									</dd>
								</dl>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="row bs-wizard"
									style="border-bottom: 0; margin-left: 15px">
									<div
										class="col-lg-4 bs-wizard-step 
										<c:choose>  
										    <c:when test="${info.status=='apply'}"> active
										   </c:when>  
										    <c:when test="${info.status!='apply'}"> complete
										   </c:when>   
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>申请</strong>
													${eloanCase.applyAmount>0?eloanCase.applyAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									<div
										class="col-lg-5 bs-wizard-step 
											   <c:choose>  
										    <c:when test="${info.status=='sign'}"> active
										   </c:when>  
										    <c:when test="${info.status=='confirmSign'||info.status=='release'}"> complete
										   </c:when>   
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>	
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>签约</strong>
													${eloanCase.signAmount>0?eloanCase.signAmount:0}万
												</dd>
											</dl>
										</div>
									</div>
									

									<div
										class="col-lg-2 bs-wizard-step 
										<c:choose>  
  
										   <c:when test="${info.status=='release'}"> complete
										　　complete
										   </c:when>  
										   <c:otherwise> 
										   disabled
										   </c:otherwise>  
										</c:choose>				
										">
										<div class="progress">
											<div class="progress-bar"></div>
										</div>
										<a href="#" class="bs-wizard-dot"></a>
										<div class="bs-wizard-info text-center">
											<dl>
												<dd>
													<strong>放款</strong>
													${info.releaseAmount>0?info.releaseAmount:0} 万
												</dd>
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
									<strong><a>${eloanCase.caseCode }</a></strong><br />经办人 <strong>${info.jingbanName }</strong>
									<br />
									<!-- <small class="text-muted">2016-06-14 15:30 网签</small> -->
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div class="ibox-content">
			<div class="row" style="margin-top:12px">
					<div class="col-lg-12">
						<div class="m-b-md">
							<h4>放款记录</h4>
						</div>

					</div>
					<div class="main_titile">
						<div class="case_content">
							<c:forEach items="${eloanRelList}" var="item">
								<div class="case_row">
									<div class="case_lump">
										<p>
											<em>放款金额</em><span class="span_one" id="content_caseCode">${item.releaseAmount}万</span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>放款时间</em><span class="span_one" id="content_propertyAddr"><fmt:formatDate
													value="${item.releaseTime}" pattern="yyyy-MM-dd" /></span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>放款状态</em><span class="" id="content_caseCode"> <c:if
													test="${item.confirmStatus==1}">
																		审批通过
																	</c:if> <c:if test="${item.confirmStatus==2}">
																		审批拒绝
																	</c:if> <c:if test="${item.confirmStatus==0}">
																		待确认
																	</c:if>
											</span>
										</p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- main End -->
	<content tag="local_script"> <script
		src="${ctx}/js/inspinia.js"></script> <script
		src="${ctx}/js/plugins/pace/pace.min.js"></script> <script>
			//点击浏览器任何位置隐藏提示信息
			$("body").bind("click", function(evt) {
				if ($(evt.target).attr("data-toggle") != 'popover') {
					$('a[data-toggle="popover"]').popover('hide');
				}
			});
		</script> </content>
</body>
</html>



