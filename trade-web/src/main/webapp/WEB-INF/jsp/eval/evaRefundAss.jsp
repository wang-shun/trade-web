<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>填写退费任务</title>
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />"rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />"rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />"rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />"rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />"rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">


<script type="text/javascript">
	var ctx = "${ctx}";
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
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

.product-type span {
	margin: 0 5px 5px 0
}

.product-type .selected, .product-type span:hover {
	border-color: #f8ac59
}

#corss_area {
	padding: 0;
}

#corss_area select {
	float: right;
	height: 34px;
	border-radius: 2px;
	margin-left: 20px;
}

.pb10 {
	padding-bottom: 15px;
}

.select_control {
	color: inherit;
}
</style>
<content tag="pagetitle">填写退费任务</content>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

	<div class="">
		<div
			class="ibox-content border-bottom clearfix space_box noborder marginbot">
			<h2 class="newtitle title-mark" id="aboutInfo">退费信息</h2>
			<div style="padding-left: 10px">
				<table width="100%">
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">退费类型：${toEvaRefund.refundKinds}</label></td>
						<td><label class="control-label sign_left_small">申请人：${toEvaRefund.proposer }</label></td>
						<td><label class="control-label sign_left_small">申请分行：${toEvaRefund.applyDepart }</label></td>
						<td><label class="control-label sign_left_small">申请时间：<fmt:formatDate
									value='${toEvaRefund.applyTime }' type='both' pattern='yyyy-MM-dd' /></label></td>
					</tr>
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">退费金额：${toEvaRefund.refundAmount }</label></td>
						<td><label class="control-label sign_left_small">退款对象：${toEvaRefund.refundTarget }</label></td>
						<td><label class="control-label sign_left_small">退款原因：${toEvaRefund.refundCause }</label></td>
						<td><label class="control-label sign_left_small">评估公司：${toEvaRefund.evalCompany }</label></td>
					</tr>
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">预计退款时间：<fmt:formatDate
									value='${toEvaRefund.toRefundTime }' type='both' pattern='yyyy-MM-dd' /></label></td>
						<td><label class="control-label sign_left_small">报告领取时间：<fmt:formatDate
									value='${toEvaReport.reportRevDate }' type='both' pattern='yyyy-MM-dd' /></label></td>
						<td><label class="control-label sign_left_small">报告领取人：${toEvaReport.receiver }</label></td>
						<td><label class="control-label sign_left_small">评估报告份数：${toEvaReport.receiveNum }</label></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="ibox-content border-bottom clearfix space_box noborder">
		<input type="hidden" name="needRecovery" value="${toEvaRefund.isNeedRecovery}">

			<form method="get" class="form-horizontal" id="evalRefundForm">
				<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"> 
				<input type="hidden" name="pkid" value="${toEvaRefund.pkid}">
				<h2 class="newtitle title-mark" id="serviceFlow">填写任务信息</h2>
				<div class="form_list">
					<div class="marinfo">
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small">
								<font color=" red" class="mr5">*</font>报告回收 :</label> 
								<label class="radio-inline"> 
									<input type="radio" name="isNeedRecovery" id="need" value="1" ${toEvaRefund.isNeedRecovery eq '1'?'checked="checked"':'' }>需要
								</label> 
								<label class="radio-inline">
								    <input type="radio" name="isNeedRecovery" id="noNeed" value="0" ${toEvaRefund.isNeedRecovery eq '0'?'checked="checked"':'' }> 不需要
								</label>
							</div>
						</div>

						<div class="line" id="isNeedDiv">
							<div class="form_content">
								<label class="control-label sign_left_small1">评估报告回收份数：</label> 
								<input type="text" class="input_type yuanwid" id="reportBackNum" maxlength="16" 
								  name="reportBackNum" value="${toEvaRefund.reportBackNum }">
							</div>

							<div class="form_content">
								<label class="control-label sign_left_small1">回收时间： </label> 
								<input class="input_type yuanwid date-picker" style="font-size: 13px;"
									type="text" id="backTime" name="backTime"
									value="<fmt:formatDate  value='${toEvaRefund.backTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>

							<div class="form_content">
								<label class="control-label sign_left_small1">未回收原因： </label> 
								<input type="text" id="backCause" class="input_type yuanwid"
									maxlength="150" name="backCause" value="${toEvaRefund.backCause }">
							</div>
						</div>

						<div class="line">
							<div class="form_content mt3">
								<label class="control-label sign_left_small1"><font
									color=" red" class="mr5">*</font>评估费实收金额： </label> <input type="text"
									class="input_type yuanwid" id="evalRealCharges"
									name="evalRealCharges" onkeyup="checkNum(this)"
									value="${toEvaRefund.evalRealCharges }"> <span>元</span>
							</div>
						</div>
					</div>
				</div>
			</form>

			<div class="form-btn">
				<div class="text-center">
					<button class="btn btn-success btn-space" onclick="save()">保存</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>

	</div>

	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> <!-- jqGrid --> <script
		src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script
		src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<!-- Custom and plugin javascript --> <script
		src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> <!-- Data picker -->
	<script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> <script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<!-- bank select --> <script
		src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>

	<script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<!-- Data picker --> <script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
    <script src="<c:url value='/js/stickUp.js' />"></script>
	<!-- 改版引入的新的js文件 --> <script
		src="<c:url value='/js/common/textarea.js' />"></script> <script
		src="<c:url value='/js/common/common.js' />"></script> <script
		src="<c:url value='/js/trunk/case/caseBaseInfo.js'/>"></script> <script>
			$(document).ready(function() {
				
				var ctx = $("#ctx").val();
				var caseCode = $("#caseCode").val();

				//日期控件
				$('#backTime').datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});

				//页面加载时div显示或隐藏
				if ($("[name=needRecovery]").val() == '1') {
					$("#isNeedDiv").css("display", "inherit");
				} else {
					$("#isNeedDiv").css("display", "none");
				}

				//点击事件时div显示或隐藏
				$("[name=isNeedRecovery]").click(function() {
					if ($(this).val() == '1') {
						$("#isNeedDiv").css("display", "inherit");
					} else {
						$("#isNeedDiv").css("display", "none");
					}
				});

			})//end ready function

			function save() {
				var b = true;
				if (checkForm($("#evalRefundForm"))) {
					var jsonData = $("#evalRefundForm").serializeArray();
					var url = "${ctx}/task/evalRefund/save";
					$.ajax({
						cache : true,
						async : true,//false同步，true异步
						type : "POST",
						url : url,
						dataType : "json",
						data : jsonData,
						beforeSend : function() {
						},
						complete : function() {
							if (b) {
							}
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
							window.wxc.success("保存成功！", {
								"wxcOk" : function() {
									//caseTaskCheck();
								}
							});
						},
						error : function(errors) {
							window.wxc.error("数据保存出错");
							//$.unblockUI();
						}
					});
				}
			}

			function submit() {
				var b = true;
				if (checkForm($("#evalRefundForm"))) {
					var jsonData = $("#evalRefundForm").serializeArray();
					var url = "${ctx}/task/evalRefund/submit";
					$.ajax({
						cache : true,
						async : true,//false同步，true异步
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

							$.unblockUI();
							if (b) {
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
							}
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
							window.wxc.success("提交成功！", {
								"wxcOk" : function() {
									caseTaskCheck();
								}
							});
						},
						error : function(errors) {
							window.wxc.error("数据保存出错");
							$.unblockUI();
						}
					});
				}
			}

			//double验证
			function checkNum(obj) {
				//先把非数字的都替换掉，除了数字和.
				obj.value = obj.value.replace(/[^\d.]/g, "");
				//必须保证第一个为数字而不是.
				obj.value = obj.value.replace(/^\./g, "");
				//保证只有出现一个.而没有多个.
				obj.value = obj.value.replace(/\.{2,}/g, ".");
				//保证.只出现一次，而不能出现两次以上
				obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
						.replace("$#$", ".");
			}

			//验证控件checkUI();
			/* function checkForm(formId) {
				if (formId.find("input[name='evalRealCharges']").val() == "") {
					window.wxc.alert("评估费实收金额为必填项!");
					formId, find("input[name='evalRealCharges']").focus();
					$("#evalRealCharges").css("border-color", "red");
					return false;
				}
				return true;
			} */
			function checkForm(formId) {
				if ($("#evalRealCharges").val() == "") {
					window.wxc.alert("评估费实收金额为必填项!");
					$("#evalRealCharges").focus();
					$("#evalRealCharges").css("border-color", "red");
					return false;
				}
				/* var r = /^\+?[1-9][0-9]*$/;
				if(!$("#reportBackNum").val()==""||!r.test($("#reportBackNum").val())){
					window.wxc.alert("评估报告回收份数只能输入数字或者为空");
					$("#reportBackNum").focus();
					$("#reportBackNum").css("border-color", "red");
					return false;
				} */
				return true;
			}

			$("input[type='text'],select").focus(function() {
				$(this).css("border-color", "rgb(229, 230, 231)");
			});
		</script> </content>
</body>
</html>