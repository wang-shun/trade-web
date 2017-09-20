<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewpoart" content="width=device-width, initial-scale=1.0">

<title>新增赎楼单</title>
<!-- 上传相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
<!-- 展示相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/static/css/bootstrap.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- index_css  -->
<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/table.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/input.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/see.css' />" />
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/spv/spv.css' />" />
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/js/viewer/viewer.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />"
	rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />"
	rel="stylesheet">
<!-- stickUp fixed css -->
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "SpvApplyApprove";
	var source = "${source}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
	var accTypeOptions = '';
	var accSum = 1;
</script>
<style>
.borderClass {
	border: 1px solid red !important;
	resize: none;
}

.borderClass:focus {
	border: 1px solid red !important;
	resize: none;
}

table thead tr {
	border-bottom: 1px solid;
	text-align: center;
	line-height: 30px;
}

table tbody select, input {
	margin: 10px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<form id="extraInfo">
			<%-- 流程相关 --%>
			<input type="hidden" id="caseCode" name="caseCode"
				value="${caseCode}"> <input type="hidden" id="taskId"
				name="taskId" value="${taskId }"> <input type="hidden"
				id="taskitem" name="taskitem" value="${taskitem }"> <input
				type="hidden" id="instCode" name="instCode" value="${instCode}">
			<input type="hidden" id="source" name="source" value="${source}">
			<input type="hidden" id="urlType" name="urlType" value="${urlType}">
			<input type="hidden" name="spvCode"
				value="${spvBaseInfoVO.toSpv.spvCode }" />
		</form>

		<!-- main Start -->
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->
				<c:if
					test="${empty handle or handle eq 'SpvApply' or handle eq 'SpvAudit'}">
					<div class="ibox-content" id="case_info">
						<div class="main_titile" style="position: relative;">

							<h5>
								关联案件
								<button type="button" id="link_btn"
									class="btn btn-success btn-blue" data-toggle="modal"
									data-target="#myModal" onClick="showPop();">关联案件</button>
							</h5>
							<div class="case_content"
								${empty caseCode?'style="display:none;"':''}>

								<div class="case_row">
									<div class="case_lump">
										<p>
											<em>案件编号</em><span class="span_one" id="content_caseCode">${caseInfoMap['caseCode']}</span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>产证地址</em><span class="span_two" id="content_propertyAddr">${caseInfoMap['propertyAddr']}</span>
										</p>
									</div>
								</div>
								<div class="case_row">
									<div class="case_lump">
										<p>
											<em>交易顾问</em><span class="span_one" id="content_processorId">${caseInfoMap['processorName']}</span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>上家姓名</em><span class="span_two" id="content_seller">${caseInfoMap['sellerName']}</span>
										</p>
									</div>
								</div>
								<div class="case_row">
									<div class="case_lump">
										<p>
											<em>经纪人</em><span class="span_one" id="content_agentName">${caseInfoMap['agentName']}</span>
										</p>
									</div>
									<div class="case_lump">
										<p>
											<em>下家姓名</em><span class="span_two" id="content_buyer">${caseInfoMap['buyerName']}</span>
										</p>
									</div>
								</div>
							</div>
						</div>
						<div class="modal inmodal" id="myModal" tabindex="-1"
							role="dialog" aria-hidden="true">
							<div class="modal-dialog" style="width: 1070px;">
								<div
									class="modal-content animated fadeIn apply_box ibox-content">
									<form action="" class="form_list clearfix">
										<div class="modal_title">监管合约关联案件</div>
										<div class="line">
											<div class="form_content">
												<label class="control-label mr10"> 案件编码 </label> <input
													class="teamcode input_type" value="" placeholder="请输入"
													id="caseCodet" name="caseCodet">
											</div>
											<div class="form_content">
												<label class="control-label sign_left"> 产证地址 </label> <input
													class="input_type" value="" placeholder="请输入"
													style="width: 435px;" id="propertyAddr" name="propertyAddr">
											</div>
										</div>
										<div class="line">
											<div class="form_content">
												<label class="control-label mr10"> 上家姓名 </label> <input
													class="teamcode input_type" value="" placeholder="请输入"
													id="caseNamet" name="caseNamet">
											</div>
											<div class="form_content space">
												<div class="add_btn">
													<button type="button" class="btn btn-success"
														id="searchButton">
														<i class="icon iconfont"></i> 查询
													</button>

													<button type="button" class="btn btn-success"
														id="addNewCase">新增案件</button>
												</div>
											</div>
										</div>

									</form>
									<button type="button" class="close close_blue"
										data-dismiss="modal">
										<i class="iconfont icon_rong"> &#xe60a; </i>
									</button>
									<div class="eloanApply-table"></div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<!-- <div class="ibox"> -->
				<!-- value待修改  此处只是copy   saveSpvCase.jsp  样式 -->
				<div class="ibox-content" id="base_info">
					<form class="form-inline">
						<input type="hidden" id="handle" name="handle" value="${handle }">
						<div class="title">客户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<input type="hidden" name="spvCustList[0].pkid"
									value="${spvBaseInfoVO.spvCustList[0].pkid }" /> <input
									type="hidden" name="spvCustList[0].tradePosition" value="BUYER" />
								<label for="" class="lable-one"><i style="color: red;">*</i>主贷人姓名</label>
								<input name="spvCustList[1].name"
									value="${not empty spvBaseInfoVO.spvCustList[1].name?spvBaseInfoVO.spvCustList[1].name:caseInfoMap['sellerName'] }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>
									手机号码</label> <input name="spvCustList[1].phone"
									value="${not empty spvBaseInfoVO.spvCustList[1].phone?spvBaseInfoVO.spvCustList[1].phone:caseInfoMap['sellerMobil'] }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>
									受理时间</label> <input id="date-picker2" name="toSpv.signTime"
									class="form-control input-one date-picker"
									style="font-size: 13px;" type="text"
									value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>"
									placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>
									计划申请时间</label> <input id="date-picker1" name="toSpv.signTime"
									class="form-control input-one date-picker"
									style="font-size: 13px;" type="text"
									value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>"
									placeholder="">
							</div>
							<div class="hr-line-dashed"></div>

							<table>
								<thead>
									<tr>
										<td>尾款机构</td>
										<td>尾款类型</td>
										<td>抵押类型</td>
										<td>贷款金额</td>
										<td>剩余金额</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><select name="loanLostFinOrgName"
											id="loanLostFinOrgName" class="teamcode select_control "></select>
										</td>
										<td><aist:dict id="mortType" name="mortType"
												clazz=" select_control yuanwid " display="select"
												dictType="30016" defaultvalue="${toMortgage.mortType }" />
										</td>
										<td><aist:dict id="mortType" name="mortType"
												clazz=" select_control yuanwid " display="select"
												dictType="30016" defaultvalue="${toMortgage.mortType }" />
										</td>
										<td><input name="" value="" type="text"
											class="form-control input-one" placeholder=""></td>
										<td><input name="" value="" type="text"
											class="form-control input-one" placeholder=""></td>
									</tr>
								</tbody>
								<input type="hidden" id="addInput" />
							</table>
							<div id="addMoneyLine" class="input-group" style="">
								<a class="" href="javascript:addOrg();"><font>添加机构</font></a>
							</div>

							<div class="hr-line-dashed"></div>

							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color: red;">*</i>
									借款总金额</label> <input name="" value="" type="text"
									class="form-control input-one" placeholder=""> <span>万</span>
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvthree_info">
					<form class="form-inline">
						<div class="title">案件跟进</div>
						<div class="view-content">
							<div id="caseCommentList"></div>
						</div>
					</form>
				</div>

				<div class="ibox-content" id="spvfour_info">
					<div class="form-btn">
						<div>
							<a id="submitBtn" class="btn btn-success">提交</a> <a
								onclick="rescCallback()" class="btn btn-default">关闭</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- main End -->
	</div>
	<!-- Mainly scripts -->
	<content tag="local_script"> <script
		src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script>
	<script src="<c:url value='/static/js/morris/morris.js' />"></script> <script
		src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script
		src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<!-- 上传附件相关 --> <script
		src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> <script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script
		src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>

	<script
		src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/ransom/addRansom.js' />"></script>
	<script src="<c:url value='/js/trunk/task/loanerProcessList.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>
	<!-- 上传附件 结束 --> <!-- 附件保存修改相关 --> <script
		src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<!-- stickup plugin --> <script
		src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script>
	<%-- <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> --%>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script
		src="<c:url value='/static/tbsp/js/userorg/userOrgSelect.js' />"
		type="text/javascript"></script> <!-- 引入弹出框js文件 --> <script
		src="<c:url value='/js/common/xcConfirm.js' />"></script> <script
		src="<c:url value='/js/viewer/viewer.min.js' />"></script> <script
		id="queryCastListItemList2" type="text/html">
        {{each rows as item index}}
    	<tr>
        <td>
            <p class="big">
                <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
                   <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
                </a>
            </p>
            <p>
                <i class="tag_sign">c</i>
                 {{item.ctmCode}}
            </p>
        </td>
        <td>
            <p class="big">
           		<span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
            </p>
            <p class="tooltip-demo">
                <i class="salesman-icon">
                </i>
                <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title="">
                	{{item.AGENT_NAME}}/{{item.AGENT_ORG_NAME}}
                </a>
            </p>
        </td>
        <td>
            <p class="name">
                <span>交易顾问：</span><a href="#" class="a_blue" id="modal_processorId{{index}}">{{item.FONT_NAME}}</a>
            </p>
            
        </td>
        <td class="center">
            <p class="big">
           		<span id="modal_seller{{index}}" title="{{item.SELLER}}">{{item.SELLER.substring(0,item.SELLER.length >10?10:item.SELLER.length).concat(item.SELLER.length >10?'...':'')}}</span>
            </p>
        </td>
        <td class="center">
            <p class="big">
          	   <span id="modal_buyer{{index}}" title="{{item.BUYER}}">{{item.BUYER.substring(0,item.BUYER.length >10?10:item.BUYER.length).concat(item.BUYER.length >10?'...':'')}}</span>
            </p>
        </td>
        <td class="text-left">
            <button type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}">
                              关联
            </button>
        </td>
    </tr>
    {{/each}}		
		</script> <script>
			/**取最大索引 */
			var accTypeSum;//账户类型 

			$(document)
					.ready(
							function() {

								$(".eloanApply-table")
										.aistGrid(
												{
													ctx : "${ctx}",
													queryId : 'queryCastListItemList',
													templeteId : 'queryCastListItemList2',
													rows : '6',
													gridClass : 'table table_blue table-striped table-bordered table-hover',
													data : '',
													wrapperData : {
														ctx : ctx
													},
													columns : [
															{
																colName : "案件编号",
																sortColumn : "CASE_CODE",
																sord : "desc",
																sortActive : true
															},
															{
																colName : "产证地址"
															},
															{
																colName : "工作人员"
															}, {
																colName : "上家"
															}, {
																colName : "下家"
															}, {
																colName : "操作"
															} ]

												});

								// 查询
								$('#searchButton').click(function() {
									reloadGrid();
								});

								// 添加
								$('#addNewCase').click(
										function() {
											window.location.href = ctx
													+ "/caseMerge/addCase/spv";
										});
								// 提交新建赎楼单
								$('#submitBtn')
										.click(
												function() {
													window.location.href = ctx
															+ "/ransomList/newRansom/myRansom_list";
												});
								// 关联案件
								$('.eloanApply-table')
										.on(
												"click",
												'.linkCase',
												function() {
													var index = $(this).attr(
															"id");
													$
															.ajax({
																url : ctx
																		+ "/spv/queryByCaseCode",
																method : "post",
																dataType : "json",
																data : {
																	caseCode : $(
																			"#modal_caseCode"
																					+ index)
																			.html()
																},
																beforeSend : function() {
																	$
																			.blockUI({
																				message : $("#salesLoading"),
																				css : {
																					'border' : 'none',
																					'z-index' : '9999'
																				}
																			});
																	$(
																			".blockOverlay")
																			.css(
																					{
																						'z-index' : '9998'
																					});
																},
																complete : function() {
																	$
																			.unblockUI();
																	if (status == 'timeout') { //超时,status还有success,error等值的情况
																		Modal
																				.alert({
																					msg : "抱歉，系统处理超时。"
																				});
																	}
																},
																success : function(
																		data) {
																	if (data.success) {
																		var caseInfoMap = eval('('
																				+ data.content
																				+ ')');
																		$(
																				"#caseCode")
																				.val(
																						caseInfoMap['caseCode']);
																		fileUpload
																				.init({
																					caseCode : $(
																							'#caseCode')
																							.val(),
																					partCode : "SpvApplyApprove",
																					fileUploadContainer : "fileUploadContainer",
																					exclude : [ 'spv_contract' ]
																				});

																		$(
																				"#content_caseCode")
																				.html(
																						caseInfoMap['caseCode']);
																		$(
																				"#content_propertyAddr")
																				.html(
																						caseInfoMap['propertyAddr']);
																		$(
																				"#content_processorId")
																				.html(
																						caseInfoMap['processorName']);
																		$(
																				"#content_seller")
																				.html(
																						caseInfoMap['sellerName']);
																		$(
																				"#content_agentName")
																				.html(
																						caseInfoMap['agentName']);
																		$(
																				"#content_buyer")
																				.html(
																						caseInfoMap['buyerName']);
																		$(
																				"input[name='toSpv.caseCode']")
																				.val(
																						caseInfoMap['caseCode']);

																		if ($(
																				"input[name='spvCustList[0].name']")
																				.val() == '')
																			$(
																					"input[name='spvCustList[0].name']")
																					.val(
																							caseInfoMap['buyerName']
																									.substr(
																											0,
																											caseInfoMap['buyerName']
																													.indexOf("/") == -1 ? caseInfoMap['buyerName'].length
																													: caseInfoMap['buyerName']
																															.indexOf("/")));
																		if ($(
																				"input[name='spvCustList[0].phone']")
																				.val() == '')
																			$(
																					"input[name='spvCustList[0].phone']")
																					.val(
																							caseInfoMap['buyerMobil']);
																		if ($(
																				"input[name='spvCustList[1].name']")
																				.val() == '')
																			$(
																					"input[name='spvCustList[1].name']")
																					.val(
																							caseInfoMap['sellerName']
																									.substr(
																											0,
																											caseInfoMap['sellerName']
																													.indexOf("/") == -1 ? caseInfoMap['sellerName'].length
																													: caseInfoMap['sellerName']
																															.indexOf("/")));
																		if ($(
																				"input[name='spvCustList[1].phone']")
																				.val() == '')
																			$(
																					"input[name='spvCustList[1].phone']")
																					.val(
																							caseInfoMap['sellerMobil']);
																		if ($(
																				"input[name='toSpvProperty.prOwnerName']")
																				.val() == '')
																			$(
																					"input[name='toSpvProperty.prOwnerName']")
																					.val(
																							caseInfoMap['sellerName']
																									.substr(
																											0,
																											caseInfoMap['sellerName']
																													.indexOf("/") == -1 ? caseInfoMap['sellerName'].length
																													: caseInfoMap['sellerName']
																															.indexOf("/")));
																		if ($(
																				"input[name='toSpvProperty.prSize']")
																				.val() == '')
																			$(
																					"input[name='toSpvProperty.prSize']")
																					.val(
																							caseInfoMap['propertySquare']);
																		if ($(
																				"input[name='toSpvProperty.prAddr']")
																				.val() == '')
																			$(
																					"input[name='toSpvProperty.prAddr']")
																					.val(
																							caseInfoMap['propertyAddr']);

																		$(
																				'.case_content')
																				.show();
																		$(
																				".close")
																				.click();
																		$(
																				".modal-backdrop")
																				.hide();

																		//$('#myModal').modal('hide');
																	} else {
																		window.wxc
																				.error("案件关联失败！");
																	}

																	$
																			.unblockUI();
																},
																error : function(
																		errors) {
																	$
																			.unblockUI();
																	window.wxc
																			.error("数据保存出错！");
																}
															});
												});
							});

			function showPop() {
				$("#myModal").show();
				$(".modal-backdrop").show();
			}

			function reloadGrid() {
				var data = {};
				var propertyAddr = $.trim($("#propertyAddr").val());
				var caseCode = $.trim($("#caseCodet").val());
				var caseName = $.trim($("#caseNamet").val());

				data.propertyAddr = propertyAddr;
				data.caseCode = caseCode;
				data.sname = caseName;
				$(".eloanApply-table").reloadGrid({
					ctx : "${ctx}",
					rows : '6',
					queryId : 'queryCastListItemList',
					templeteId : 'queryCastListItemList2',
					wrapperData : {
						ctx : ctx
					},
					data : data
				})
			}

			/*******************************************************控件相关*********************************************************************/
			// 日期控件
			$('#date-picker2').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});
			$('#date-picker1').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			});

			//跟进信息
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : taskitem
			});

			//可变下拉选选择li时回调函数 
			function editSelectFunc(element, index) {
				var $nextUL = $(element).parent().next();
				var $input = $("input[name='toSpvAccountList[" + index
						+ "].branchBank']");
				$nextUL.empty();
				$input.val('');
				$
						.ajax({
							cache : true,
							url : ctx + "/manage/queryBankListByParentCode",
							method : "post",
							dataType : "json",
							async : false,
							data : {
								faFinOrgCode : $(element).val()
							},
							success : function(data) {
								for (var i = 0; i < data.length; i++) {
									var $li = $("<li coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"
											+ data[i].finOrgNameYc + "</li>");
									$li.mouseover(function() {
										$(this).addClass("selected");
									}).mouseleave(function() {
										$(this).removeClass("selected");
									}).click(function() {
										$input.val($(this).text());
									});
									$nextUL.append($li);
								}
							}
						});
			}
		</script> </content>

	<content tag="local_require"> <script>
		var fileUpload;
		require([ 'main' ], function() {
			requirejs([ 'jquery', 'aistFileUpload', 'validate', 'grid',
					'jqGrid', 'steps', 'ligerui', 'aistJquery', 'poshytip',
					'twbsPagination', 'bootstrapModal', 'modalmanager',
					'eselect' ], function($, aistFileUpload) {
				fileUpload = aistFileUpload;
				var handle = $("#handle").val();
				if (handle == "SpvApprove") {
					fileUpload.init({
						caseCode : $('#caseCode').val(),
						partCode : "SpvApplyApprove",
						fileUploadContainer : "fileUploadContainer",
						readonly : true,
						exclude : [ 'spv_contract' ]
					});
				} else if (handle == "" || handle == "SpvApply"
						|| handle == "SpvAudit") {
					if ($('#caseCode').val() != '') {
						fileUpload.init({
							caseCode : $('#caseCode').val(),
							partCode : "SpvApplyApprove",
							fileUploadContainer : "fileUploadContainer",
							exclude : [ 'spv_contract' ]
						});
					}
				} else if (handle == "SpvSign") {
					fileUpload.init({
						caseCode : $('#caseCode').val(),
						partCode : "SpvApplyApprove",
						fileUploadContainer : "fileUploadContainer"
					});
				}
			});
		});
	</script> </content>

</body>

</html>