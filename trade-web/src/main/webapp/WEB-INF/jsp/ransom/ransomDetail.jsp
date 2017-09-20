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
	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="ibox-content" id="reportOne">
			<h2 class="title">赎楼清尾</h2>
			<div class="row">
				<div class="col-lg-9">
					<div class="row" id="">
						<div class="col-lg-5" id="cluster_info">
							<dl class="dl-horizontal">
								<dt>借款人</dt>
								<dd>${info.finOrgName}</dd>
								<dt>房屋地址</dt>
								<dd>${info.finOrgName}</dd>
							</dl>
						</div>
						<div class="col-lg-4" id="cluster_info">
							<dl class="dl-horizontal">
								<dt>合作机构</dt>
								<dd>${info.finOrgName}</dd>
								<dt>信贷员</dt>
								<dd>${info.finOrgName}</dd>
							</dl>
						</div>
						<div class="col-lg-3">
							<dl class="dl-horizontal">
								<dt>金融权证</dt>
								<dd>${info.finOrgName}</dd>
								<dt>经纪人</dt>
								<dd>${info.finOrgName}</dd>
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
								<strong><a>${eloanCase.caseCode }</a></strong><br />经办人 <strong>${info.jingbanName }</strong>
								<br />
								<!-- <small class="text-muted">2016-06-14 15:30 网签</small> -->
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
							<a href="javascript:void(0)">变更金融权证</a> <a
								href="${ctx }/ransomList/ransom/ransomDetailUpdate">修改赎楼单详情</a>
							<a href="${ctx }/ransomList/ransom/planTime">修改时间计划</a>
						</div>
						<hr>
						<table
							class="table table_blue table-striped table-bordered table-hover ">
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
							<tbody>
								<tr>
									<td>建设银行河西支行</td>
									<td>商业贷款</td>
									<td>一抵</td>
									<td>240万元</td>
									<td>40万元</td>
									<td>40万元</td>
								</tr>
							</tbody>
						</table>
						<div class="form_list">
							<div class="marinfo">
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 借款金额 </label> <input type="hidden"
											value="借款金额 " id="" name=""> <input type="text"
											value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />"
											class="input_type yuanwid" id="initAmount" name="initAmount"
											onkeyup="checkNum(this)"> <span class="date_icon">元</span>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 面签金额 </label> <input type="hidden"
											value="面签金额 " id="initPayName" name="initPayName"> <input
											type="text"
											value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />"
											class="input_type yuanwid" id="initAmount" name="initAmount"
											onkeyup="checkNum(this)"> <span class="date_icon">元</span>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 还贷金额</label> <input type="hidden"
											value="还贷金额" id="initPayName" name="initPayName"> <input
											type="text"
											value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />"
											class="input_type yuanwid" id="initAmount" name="initAmount"
											onkeyup="checkNum(this)"> <span class="date_icon">元</span>
									</div>
								</div>
								<div class="line">
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font> 贷款费用 </label> <input type="hidden"
											value="贷款费用" id="initPayName" name="initPayName"> <input
											type="text"
											value="<fmt:formatNumber value='${ transSign.initAmount}' type='number' pattern='#0.00' />"
											class="input_type yuanwid" id="initAmount" name="initAmount"
											onkeyup="checkNum(this)"> <span class="date_icon">元</span>
									</div>
									<div class="form_content">
										<label class="control-label sign_left_small"><font
											color=" red" class="mr5">*</font>是否委托公正 </label>
										<div class="controls isnowid"
											style="width: 180px; margin-left: 0px;">
											<select class="select_control data_style" readOnlydata='1'
												name="netPlace" id="netPlace">
												<option value="">请选择</option>
												<option value="1" ${transSign.netPlace=="1"?'selected':''}>是</option>
												<option value="0" ${transSign.netPlace=="0"?'selected':''}>否</option>
											</select>
										</div>
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
	<content tag="local_script"> <%-- 	   <script src="<c:url value='/js/inspinia.js' />"></script> 
	   <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script> --%>
	<!-- 开关按钮js --> <script
		src="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/common/textarea.js' />"></script> <script
		src="<c:url value='/js/eloan/eloancommon.js' />"></script> <script>
			jQuery(document).ready(function() {
								var eloanCode = "${eloanCase.eloanCode }";
								var pkId = "${pkId}";
								var userName = "${userName}";
								/* $(".riskControl-table").aistGrid({
									ctx : "${ctx}",
									queryId : 'riskControlListQuery',
								    templeteId : 'queryRiskControlList',
								    gridClass : 'table table-striped',
								    data : {eloanCode : eloanCode},
								    wrapperData : {pkId : pkId,userName:userName,eloanCode:eloanCode},
								    columns : [{
								    	           colName :"风控项目"
								    	      },{
								    	           colName :"执行时间"
								    	      },{
								    	           colName :"执行人"
								 	          },{
								    	           colName :"备注"
								    	      },{
								    	           colName :"操作"
								    	      }]
								
								});  */

								$(".cardButton")
										.click(
												function() {
													var type = "card";
													if (isExistsType(type,
															eloanCode)) {
														window.wxc
																.alert('已经存在该风控类型，你只能修改!');
													} else {
														window.location.href = "${ctx}/riskControl/guarantycards?pkid=${pkId}";
													}
												});

								$(".mortgageButton")
										.click(
												function() {
													var type = "mortgage";
													if (isExistsType(type,
															eloanCode)) {
														window.wxc
																.alert('已经存在该风控类型，你只能修改!');
													} else {
														window.location.href = "${ctx}/riskControl/guarantymortgage?pkid=${pkId}";
													}
												});

								$(".forceFairButton")
										.click(
												function() {
													var type = "forceRegister";
													if (isExistsType(type,
															eloanCode)) {
														window.wxc
																.alert('已经存在该风控类型，你只能修改!');
													} else {
														window.location.href = "${ctx}/riskControl/guarantyfair?pkid=${pkId}";
													}
												});

								//跟进信息
								$("#caseCommentList").eloanCaseCommentGrid({
									eloanCode : eloanCode,
									source : 'EPLUS'
								//type : 'TRACK'
								});
							})

			//点击浏览器任何位置隐藏提示信息
			$("body").bind("click", function(evt) {
				if ($(evt.target).attr("data-toggle") != 'popover') {
					$('a[data-toggle="popover"]').popover('hide');
				}
			});
			// 验证押卡是否已经存在
			function isExistsType(type, eloanCode) {
				var isExist = false;
				var url = "${ctx}/riskControl/validateRiskControlType";
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					//contentType:"application/json",  
					data : {
						'type' : type,
						'eloanCode' : eloanCode
					},
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '1900'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '1900'
						});
					},
					complete : function() {
						$.unblockUI();
					},
					success : function(data) {
						isExist = data.ajaxResponse.success;
					},
					error : function(errors) {
						window.wxc.error("数据保存出错");
					}
				});
				return isExist;
			}

			function deleteRiskControl(pkid, riskType, eloanPkId, eloanCode) {
				var url = "${ctx}/riskControl/deleteRiskControl?pkid=" + pkid
						+ "&riskType=" + riskType + "&eloanPkId=" + eloanPkId
						+ "&eloanCode=" + eloanCode;
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					//contentType:"application/json",  
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '1900'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '1900'
						});
					},
					complete : function() {
						$.unblockUI();
					},
					success : function(data) {
						setTimeout('refresh()', 1000);
					},
					error : function(errors) {
						window.wxc.error("数据保存出错");
					}
				});
			}

			function refresh() {
				window.location.reload();
			}
		</script> </content>
</body>
</html>



