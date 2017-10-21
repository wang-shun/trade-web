<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>评估返利审批---内勤确认</title>
	<!-- jdGrid相关 -->
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
	<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
	<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
	<link href="<c:url value='/css/style.css' />" rel="stylesheet">
	<!-- 展示相关 -->
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />" rel="stylesheet">
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">

	<!-- 新调整页面样式 -->
	<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
	<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
	<style>
		.text_list{
			margin-left: 20px;
		}
	</style>
	<content tag="pagetitle">评估返利审批---内勤确认</content>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
<!-- 服务流程 -->
<div class="row wrapper white-bg new-heading " id="serviceFlow">
	<div class="pl10">
		<h2 class="newtitle-big">
			评估返利审批
		</h2>
		<div class="mt20">
			<button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
				<i class="iconfont icon">&#xe600;</i> 在途单列表
			</button>
			<button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
				<i class="iconfont icon">&#xe63f;</i> 案件视图
			</button>
		</div>
	</div>
</div>

<div class="ibox-content border-bottom clearfix space_box noborder">
	<div>
		<h2 class="newtitle title-mark">评估返利报告申请信息</h2>
		<div class="text_list">
			<div class="row">
				<label class="col-sm-3 control-label">评估公司:${evalCompany.FIN_ORG_NAME}</label>
				<label class="col-sm-3 control-label">评估价:${eval.evaPrice}</label>
				<c:if test="${evalCompany.TAGS > 0}">
					<label class="col-sm-3 control-label">评估公司成本:${evalCompany.TAGS}</label>
				</c:if>
			</div>
			<div class="row">
				<label class="col-sm-3 control-label">评估费实收:${rebate.evalRealCharges}</label>
				<label class="col-sm-3 control-label">评估费应收:${rebate.evalDueCharges}</label>
				<label class="col-sm-3 control-label" id="centacomamount">中原分成金额:${rebate.evalRealCharges - evalCompany.TAGS}</label>
				<label class="col-sm-3 control-label" id="evacomamount">评估公司分成金额:${evalCompany.TAGS}</label>
			</div>
			<div class="row">
				<label class="col-sm-3 control-label">录入时间:<fmt:formatDate value='${rebate.inputTime}' pattern="yyyy-MM-dd" /></label>
			</div>
		</div>
	</div>

	<div>
		<h2 class="newtitle title-mark">财务确认信息</h2>
		<div class="text_list">
			<div class="row">
				<label class="col-sm-3 control-label" id="verify">评估费实收:无</label>
			</div>
		</div>
	</div>

	<div>
		<h2 class="newtitle title-mark">填写审批任务</h2>
		<div class="form_list">
			<form id="rebateForm" class="form-horizontal">
				<%--环节编码 --%>
				<input type="hidden" name="partCode" value="${taskitem}">
				<!-- 关联的交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" name="taskId" value="${taskId}">
				<input type="hidden" name="processInstance" value="${processInstanceId}">
				<input type="hidden" name="rebateId" value="${rebate.pkid}">

				<div class="marinfo">

						<div class="line" <c:if test="${evalCompany.TAGS != 0}"> style="display:none"</c:if>>
							<div class="form_content">
								<label class="control-label sign_left_small">评估公司成本</label>
								<input type="text" id="evalCost" class="input_type" name="evalCost" value="${evalCompany.TAGS}" onblur="cal()" real="${rebate.evalRealCharges}" >
							</div>
						</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">评估费收据</label>
							<input type="text" id="evalRecept" class="input_type" name="evalRecept">
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">审批结果</label>
							<div class="controls ">
								<label class="radio inline">
									<input type="radio" checked="checked" value="true" name="approve" >通过
								</label>
								<label class="radio inline">
									<input type="radio" value="false" name="approve" >驳回
							</label>
							</div>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">审批意见</label>
							<input type="text" class="input_type optionwid" name="response">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="view-content">
		<table id="gridTable" class=""></table>
		<div id="gridPager"></div>
	</div>
	<div class="form-btn">
		<div class="text-center">
			<button id="subBtn" class="btn btn-success btn-space" onclick="submit('${ctx}')">提交</button>
		</div>
	</div>
</div>

<content tag="local_script">

	<!-- Peity -->
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<!-- Custom and plugin javascript -->
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>

	<!-- 必须JS -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<!--公共信息-->
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>

	<script	src="<c:url value='/js/trunk/eval/evalRebate.js' />" type="text/javascript"></script>
	<
	<script type="application/javascript">
		$(function(){
		    //审批记录列表
            AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${caseCode}');
            loadVerifyFee('${ccaiCode}');
		});
	</script>
</content>
</body>


</html>