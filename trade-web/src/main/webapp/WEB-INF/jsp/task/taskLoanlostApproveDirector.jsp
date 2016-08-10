<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css"
	rel="stylesheet">
<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var attachmentCode = "LoanlostApply";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
</script>
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				<h2>流失审批（总监）</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>业务单信息</h5>
			<div id="collapseOne" class="panel-collapse collapse in">
				<div class="panel-body ibox-content" style="margin-left: 117px;">
					<div class="row ">
						<label class="col-sm-5 control-label">业务单编号：${caseDetail.caseCode}</label>
						<label class="col-sm-4 control-label">物业地址：${caseDetail.propertyAddr}</label>
					</div>
					<div class="row ">
						<label class="col-sm-5 control-label">主贷人：${custName}</label> <label
							class="col-sm-4 control-label">主贷人单位：${custCompany}</label>
					</div>
					<div class="row ">
						<label class="col-sm-5 control-label">承办银行：${caseDetail.lastLoanBank}</label>
						<label class="col-sm-4 control-label">贷款流失金额：${caseDetail.mortTotalAmount/10000}万</label>
					</div>
					<div class="row ">
						<label class="col-sm-10 control-label">流失原因：${caseDetail.content}</label>
					</div>
				</div>
			</div>
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="lamform">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"
						value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode"
						value="${caseCode}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						name="processInstanceId" value="${processInstanceId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType"
						value="${approveType }"> <input type="hidden" id="lapPkid"
						name="lapPkid" value="${toApproveRecord.pkid }"> <input
						type="hidden" id="operator" name="operator" value="${operator }">
					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="col-sm-3">
							<div class="radio i-checks radio-inline">
								<label> <input type="radio" checked="checked"
									value="true" id="optionsRadios1" name="LoanLost_director">审批通过
								</label> <label> <input type="radio" value="false"
									id="optionsRadios2" name="LoanLost_director">审批不通过
								</label>
							</div>
						</div>
					</div>

					<%-- 					<!-- 流失案件总监审核 -->
					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="radio i-checks radio-inline">
							<label> <input type="radio" checked="checked"
								value="true" id="optionsRadios1" name="LoanLost_director"
								onClick="$('#loanLostDirectorNotApproves').hide();">审批通过
							</label>
						</div>
						<div class="radio i-checks radio-inline">
							<label> <input type="radio" value="false"
								id="optionsRadios2" name="LoanLost_director"
								onClick="$('#loanLostDirectorNotApproves').show();getNotApproves();">审批未通过
							</label>
						</div>
						<div class="form_sign col-sm-12 clearfix" id="loanLostDirectorNotApproves"
							style="display: none">
							<c:forEach items="${loanLostNotApproves}"
								var="loanLostDirectorNotApprove">
								<div class="col-sm-6 sign">
									<input type="checkbox"
										value="${loanLostDirectorNotApprove.code}"
										name="loanLostNotApprove" class="btn btn-white"
										onClick="loanLostDirectorAppendNotApprove(this.checked,'${loanLostDirectorNotApprove.name}');">
									<label>${loanLostDirectorNotApprove.name}</label>
								</div>
							</c:forEach>
						</div>
					</div> --%>
					<div class="form-group">
						<label class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10">
							<input type="text" class="form-control"
								id="LoanLost_director_response"
								name="LoanLost_director_response" value="">
						</div>
					</div>
				</form>

			</div>
		</div>
		<h5>
			附件浏览：贷款流失确认函、贷款流失申请书<br>
		</h5>
		<div class="ibox-content">
			<div id="imgShow" class="lightBoxGallery"></div>
		</div>

		<div id="caseCommentList" class="add_form"></div>

		<div class="ibox-title">
			<h5>审批记录</h5>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>
				</div>
			</div>
		</div>
		<div class="ibox-title">
			<!-- <a href="#" class="btn" onclick="save()">保存</a> -->
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
	</div>
	<content tag="local_script"> <!-- jqGrid --> <script
		src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/transjs/task/loanlostApprove.js"></script> <script
		src="${ctx}/transjs/task/showAttachmentByLssp.js"></script> <%-- <script src="${ctx}/transjs/task/showAttachment.js"></script> --%>
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/trunk/comment/caseComment.js"></script> <script
		src="${ctx}/js/viewer/viewer.min.js"></script> <script>
/* 			function loanLostDirectorAppendNotApprove(isAppend, content) {
				if (isAppend) {
					var oldVal = $("#LoanLost_director_response").val();
					if (oldVal != '') {
						oldVal += '；';
					}
					$("#LoanLost_director_response").val(oldVal + content);
				}
			} */

			$(function() {
				getShowAttachment();
			});
			/**提交数据*/
			function submit() {
				save();
			}

			/**保存数据*/
			function save() {
				var jsonData = $("#lamform").serializeArray();

				var url = "${ctx}/task/loanlostApprove/loanlostApproveSecond";

				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					data : jsonData,
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '9999'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '9998'
						});
					},
					complete : function() {

						if (status == 'timeout') {//超时,status还有success,error等值的情况
							Modal.alert({
								msg : "抱歉，系统处理超时。"
							});
							$(".btn-primary").one("click", function() {
								parent.$.fancybox.close();
							});
						}
					},
					success : function(data) {
						if (window.opener) {
							window.close();
							window.opener.callback();
						} else {
							window.location.href = "${ctx }/task/myTaskList";
						}
						//window.location.href = "${ctx }/task/myTaskList";
					},
					error : function(errors) {
						alert("数据保存出错");
					}
				});
			}
		</script> </content>
</body>


</html>