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
<title>经理审核退费</title>
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
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script type="text/javascript">
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";

	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
<script type="text/javascript">
	var AttachmentList = (function() {
		return {
			init : function(ctx, url, gridTableId, gridPagerId, ctmCode,
					caseCode) {
				//jqGrid 初始化
				$("#" + gridTableId)
						.jqGrid(
								{
									url : ctx + url,
									mtype : 'GET',
									datatype : "json",
									height : 125,
									autowidth : true,
									shrinkToFit : true,
									rowNum : 3,
									/*   rowList: [10, 20, 30], */
									colNames : [ '审批人', '审批时间', '审批结果', '审批意见' ],
									colModel : [ {
										name : 'OPERATOR',
										index : 'OPERATOR',
										align : "center",
										width : 25,
										resizable : false
									}, {
										name : 'OPERATOR_TIME',
										index : 'OPERATOR_TIME',
										align : "center",
										width : 25,
										resizable : false
									}, {
										name : 'NOT_APPROVE',
										index : 'NOT_APPROVE',
										align : "center",
										width : 25,
										resizable : false
									//formatter : linkhouseInfo
									}, {
										name : 'CONTENT',
										index : 'CONTENT',
										align : "center",
										width : 25,
										resizable : false
									} ],
									multiselect : true,
									pager : "#" + gridPagerId,
									//sortname:'UPLOAD_DATE',
									//sortorder:'desc',
									viewrecords : true,
									pagebuttions : true,
									multiselect : false,
									hidegrid : false,
									recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
									pgtext : " {0} 共 {1} 页",
									gridComplete : function() {
										var ids = jQuery("#" + gridTableId)
												.jqGrid('getDataIDs');
										for (var i = 0; i < ids.length; i++) {
											var id = ids[i];
											var rowDatas = jQuery(
													"#" + gridTableId).jqGrid(
													'getRowData', ids[i]); // 获取当前行

											var auditResult = rowDatas['NOT_APPROVE'];
											var auditResultDisplay = null;
											if (!auditResult) {
												auditResultDisplay = "审批通过"
											} else {
												auditResultDisplay = auditResult;
											}
											jQuery("#" + gridTableId)
													.jqGrid(
															'setRowData',
															ids[i],
															{
																NOT_APPROVE : auditResultDisplay
															});
										}
									},
									postData : {
										queryId : "queryLoanlostApproveList",
										//caseCode : caseCode
										caseCode : 'caseCode'
									}

								});
			}
		};
	})();
</script>
<script type="text/javascript">

	//关闭
	/* $('#closeButton').click(function() {
		window.close();
	}); */

	//提交数据
	function submit() {
		save(true);
	}

	//保存数据
	function save(b) {
		var jsonData = $("#ManRefundform").serializeArray();
		var url = "${ctx}/task/evalRefund/submitManager";
	if(checkForm()){
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
					if (data.message) {
						window.wxc.alert("提交成功" + data.message);
					}
					var ctx = $("#ctx").val();
					window.location.href = ctx + "/task/myTaskList";
				} else {
					if (data.message) {
						window.wxc.alert("提交成功" + data.message);
					}
				}
			},
			error : function() {
				window.wxc.alert("提交信息出错。。");
			}
		});
	}
	}

	//验证控件checkUI();
	function checkForm() {
		if ($("#approveContent").val() == "") {
			window.wxc.alert("审批意见为必填项!");
			$("#approveContent").focus();
			$("#approveContent").css("border-color", "red");
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
<content tag="pagetitle">经理审核退费</content>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include> 

	<div class="">
		<div
			class="ibox-content border-bottom clearfix space_box noborder marginbot">
			<h2 class="newtitle title-mark" id="aboutInfo">退费信息</h2>
        	<div style="padding-left: 10px">
        		<table  width="100%">
        			<tr style="height: 40px;">
        				<td><label class="control-label sign_left_small">退费类别：${toEvaRefund.refundKinds eq '4'?'退评估费':'错误的类别' }</label></td>
        				<td><label class="control-label sign_left_small">申请人：${toEvaRefund.proposer }</label></td>
        				<td><label class="control-label sign_left_small">申请分行：${toEvaRefund.applyDepart }</label></td>
        				<td><label class="control-label sign_left_small">申请时间：<fmt:formatDate  value='${toEvaRefund.applyTime }' type='both' pattern='yyyy-MM-dd'/></label></td>      				
        			</tr>
        			<tr style="height: 40px;">
        			<td><label class="control-label sign_left_small">评估公司：${toEvaRefund.evalCompany }</label></td>        
        				<td><label class="control-label sign_left_small">退费金额：${toEvaRefund.refundAmount }</label></td>
        				<td><label class="control-label sign_left_small">退款对象：${toEvaRefund.refundTarget }</label></td>
        				<td><label class="control-label sign_left_small">退款原因：${toEvaRefund.refundCause }</label></td>
        			</tr>
        			<tr style="height: 40px;">
        				<td><label class="control-label sign_left_small">预计退款时间：<fmt:formatDate  value='${toEvaRefund.toRefundTime }' type='both' pattern='yyyy-MM-dd'/></label></td>
        				<td><label class="control-label sign_left_small">评估费时收金额：${toEvaRefund.evalRealCharges}</label></td>
        				<td><label class="control-label sign_left_small">评估费实收时间：</label></td>
        				<td><label class="control-label sign_left_small">出具评估报告时间：<fmt:formatDate  value='${toEvaReport.issueDate }' type='both' pattern='yyyy-MM-dd'/></label></td>      				
        			</tr>
        			<tr style="height: 40px;">
        				<td><label class="control-label sign_left_small">报告领取时间：<fmt:formatDate  value='${toEvaReport.reportRevDate }' type='both' pattern='yyyy-MM-dd'/></label></td>
        				<td><label class="control-label sign_left_small">报告领取人：${toEvaReport.receiver }</label></td>
        				<td><label class="control-label sign_left_small">评估报告份数：${toEvaReport.receiveNum }</label></td>
        				<td><label class="control-label sign_left_small">评估报告回收份数：${toEvaRefund.reportBackNum}</label></td>       				
        			</tr>
        		</table>
        	</div>

			<div class="title title-mark" >
				<strong style="font-weight: bold;">退费审批记录</strong>
			</div>

			<div class="view-content">
				<table id="gridTable" class=""></table>
				<div id="gridPager"></div>
			</div>
			<br>
			<hr>
			<div class="line">
				<div class="title title-mark" id="serviceFlow">
					<strong style="font-weight: bold;">填写审批任务</strong>
				</div>
			</div>
			<form method="get" class="form_list" id="ManRefundform"
				style="overflow: visible;">
				<input type="hidden" id="ctx" value="${ctx }" />
				<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"  value="${caseCode }">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
				<%-- 设置审批类型 --%>
				<input type="hidden" id="approveType" name="approveType" value="${approveType }"> 
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">
						<font color=" red" class="mr5">*</font>审批结果：</label> 
						<input type="radio" name="approveResult" value="0" checked="checked"><span>通过</span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="radio" name="approveResult" value="1"><span>驳回</span>
					</div>
				</div>

				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">
						<font color=" red" class="mr5">*</font>审批意见：</label> 
						<input type="text" class="input_type mendwidth" id="approveContent" name="approveContent" maxlength="64">
					</div>
				</div>
			</form>
			<div class="form-btn">
				<div class="text-center">
					<button class="btn btn-success btn-space" id="closeButton" onclick="javascript:self.close()">关闭</button>
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
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
	<script
		src="<c:url value='/js/common/common.js' />"></script> 
		<script
		src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script> 
		<script>
			$(document).ready(
					function() {
								var ctx = $("#ctx").val();
								/* 	if(!$("#caseCode").val()){
								           $("#caseCode").val("ZY-TJ-2017080038");						
								     } */
								var caseCode = $("#caseCode").val();
								AttachmentList.init('${ctx}','/quickGrid/findPage', 'gridTable',
										'gridPager', '${ctmCode}', caseCode);
								$("#caseCommentList").caseCommentGrid({
									caseCode : caseCode,
									srvCode : 'caseRecvFlow'
								});


							})//end ready function
		</script> </content>
</body>
</html>
