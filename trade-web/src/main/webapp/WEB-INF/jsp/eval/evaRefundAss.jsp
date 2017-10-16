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
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link
	href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />"
	rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

<script type="text/javascript">
	//提交数据
	function submit() {
		save(true);
	}

	//保存数据
	function save(b) {
		if (b) {
			if (!checkForm()) {
				return;
			}
		}

		var jsonData = $("#evalRefundForm").serializeArray();

		var url = "${ctx}/eval/addEvalRefund";

		$.ajax({
			cache : true,
			async : false,
			type : "POST",
			url : url,
			dataType : "json",
			data : jsonData,
			success : function(data) {
				console.log(data);
				if (b) {
					if (data) {
						window.wxc.alert("提交成功" + data);
					}
					var ctx = $("#ctx").val();
					window.location.href = ctx + "/eval/evaRefundShow";
				} else {
					if (data.message) {
						window.wxc.alert("提交成功" + data);
					}
				}
			},
			error : function() {
				window.wxc.alert("提交信息出错。。");
			}
		});
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
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace(
				"$#$", ".");
	}

	//验证控件checkUI();
	function checkForm() {
		var caseCode = $("#caseCode").val();

		if (!caseCode) {
			window.wxc.alert("缺少caseCode,请以正确的方式进入系统!");
			return false;
		}

		if (!$("[name=isNeedRecovery]").val()) {
			window.wxc.alert("评估报告是否回收为必选项!");
			return false;
		}

		if (!$("#evalRealCharges").val()) {
			window.wxc.alert("评估费实收金额为必填项!");
			$("#evalRealCharges").focus();
			$("#evalRealCharges").css("border-color", "red");
			return false;
		}

		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});
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

.ibox-content-task {
	padding-bottom: 40px !important;
}

#corss_area {
	padding: 0 8px 0 0;
	margin-left: 369px;
}

#corss_area select {
	height: 34px;
	border-radius: 2px;
	margin-left: 20px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx }" />
	<!-- 交易单编号 -->
	<%-- <input type="hidden" id="caseCode" name="caseCode" value="${caseCode}"> --%>
	<input type="hidden" id="caseCode" name="caseCode" value="ZY-TJ-2017100213">
	<div class="">
		<div
			class="ibox-content border-bottom clearfix space_box noborder marginbot">
			<h2 class="newtitle title-mark" id="aboutInfo">退费信息</h2>
			<div style="padding-left: 10px">
				<table width="100%">
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">退费类型：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">申请人：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">申请分行：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">申请时间：</label></td>
						<td></td>
					</tr>
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">退费金额：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">退款对象：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">退款原因：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">评估公司：</label></td>
						<td></td>
					</tr>
					<tr style="height: 40px;">
						<td><label class="control-label sign_left_small">预计退款时间：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">报告领取时间：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">报告领取人：</label></td>
						<td></td>
						<td><label class="control-label sign_left_small">评估报告份数：</label></td>
						<td></td>
					</tr>
				</table>
			</div>

			<h2 class="newtitle title-mark" id="serviceFlow">填写退费任务</h2>
			<form method="get" class="form_list" id="evalRefundForm"
				style="overflow: visible;">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>报告回收 :</label> <label
								class="radio-inline"> <input type="radio"
								name="isNeedRecovery" id="need" value="1"> 需要
							</label> <label class="radio-inline"> <input type="radio"
								name="isNeedRecovery" id="noNeed" value="0"> 不需要
							</label>
						</div>
					</div>

					<div class="line" id="isNeedDiv">

						<div class="form_content">
							<label class="control-label sign_left_small">评估报告回收份数： </label> <input
								type="text" class="input_type yuanwid" id="reportBackNum"
								maxlength="16" name="reportBackNum">
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">回收时间： </label> <input
								class="input_type yuanwid date-picker" style="font-size: 13px;"
								type="text" id="backTime" name="backTime">
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">未回收原因： </label> <input
								type="text" id="backCause" class="input_type yuanwid"
								maxlength="150" name="backCause">
						</div>
					</div>

					<div class="line">
						<div class="form_content mt3">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>评估费实收金额： </label> <input type="text"
								class="input_type yuanwid" id="evalRealCharges"
								name="evalRealCharges" onkeyup="checkNum(this)"> <span>元</span>
						</div>
					</div>
				</div>
				<div class="hr"></div>
			</form>

			<div class="form-btn">
				<div class="text-center">
					<button class="btn btn-success btn-space" onclick="save(false)" id="btnSave">保存</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> <script
		src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<%-- <script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script> --%>
	<%-- <script src="<c:url value='/transjs/task/showAttachment.js' />"></script>  --%>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> <script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
	<script src="<c:url value='/static/js/jquery.json.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script
		src="<c:url value='/js/trunk/task/taskFirstFollow.validate.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/layer.js' />"></script> <script
		src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />"></script> <script
		src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script> <!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script> <script
		src="<c:url value='/js/common/common.js' />"></script> <script>
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

				//设置div显示或隐藏

				$("[name=isNeedRecovery]").click(function() {
					if ($(this).val() == '1') {
						$("#isNeedDiv").css("display", "inherit");
					} else {
						$("#isNeedDiv").css("display", "none");
					}
				});

			})//end ready function
		</script> </content>
</body>
</html>
